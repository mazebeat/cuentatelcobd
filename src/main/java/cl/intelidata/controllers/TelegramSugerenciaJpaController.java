/*
 * Copyright (c) 2016, Intelidata S.A.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package cl.intelidata.controllers;

import cl.intelidata.controllers.exceptions.NonexistentEntityException;
import cl.intelidata.jpa.TelegramSugerencia;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.intelidata.jpa.Usuarios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dev-DFeliu
 */
public class TelegramSugerenciaJpaController implements Serializable {

    public TelegramSugerenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TelegramSugerencia telegramSugerencia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios idUsuario = telegramSugerencia.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getId());
                telegramSugerencia.setIdUsuario(idUsuario);
            }
            em.persist(telegramSugerencia);
            if (idUsuario != null) {
                idUsuario.getTelegramSugerenciaList().add(telegramSugerencia);
                idUsuario = em.merge(idUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TelegramSugerencia telegramSugerencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TelegramSugerencia persistentTelegramSugerencia = em.find(TelegramSugerencia.class, telegramSugerencia.getId());
            Usuarios idUsuarioOld = persistentTelegramSugerencia.getIdUsuario();
            Usuarios idUsuarioNew = telegramSugerencia.getIdUsuario();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getId());
                telegramSugerencia.setIdUsuario(idUsuarioNew);
            }
            telegramSugerencia = em.merge(telegramSugerencia);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getTelegramSugerenciaList().remove(telegramSugerencia);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getTelegramSugerenciaList().add(telegramSugerencia);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = telegramSugerencia.getId();
                if (findTelegramSugerencia(id) == null) {
                    throw new NonexistentEntityException("The telegramSugerencia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TelegramSugerencia telegramSugerencia;
            try {
                telegramSugerencia = em.getReference(TelegramSugerencia.class, id);
                telegramSugerencia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telegramSugerencia with id " + id + " no longer exists.", enfe);
            }
            Usuarios idUsuario = telegramSugerencia.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getTelegramSugerenciaList().remove(telegramSugerencia);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(telegramSugerencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TelegramSugerencia> findTelegramSugerenciaEntities() {
        return findTelegramSugerenciaEntities(true, -1, -1);
    }

    public List<TelegramSugerencia> findTelegramSugerenciaEntities(int maxResults, int firstResult) {
        return findTelegramSugerenciaEntities(false, maxResults, firstResult);
    }

    private List<TelegramSugerencia> findTelegramSugerenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TelegramSugerencia.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TelegramSugerencia findTelegramSugerencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TelegramSugerencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelegramSugerenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TelegramSugerencia> rt = cq.from(TelegramSugerencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
