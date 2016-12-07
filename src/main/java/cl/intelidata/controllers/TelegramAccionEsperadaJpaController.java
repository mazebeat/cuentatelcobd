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
import cl.intelidata.jpa.TelegramAccionEsperada;
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
public class TelegramAccionEsperadaJpaController implements Serializable {

    public TelegramAccionEsperadaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TelegramAccionEsperada telegramAccionEsperada) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios idUsuario = telegramAccionEsperada.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getId());
                telegramAccionEsperada.setIdUsuario(idUsuario);
            }
            em.persist(telegramAccionEsperada);
            if (idUsuario != null) {
                idUsuario.getTelegramAccionEsperadaList().add(telegramAccionEsperada);
                idUsuario = em.merge(idUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TelegramAccionEsperada telegramAccionEsperada) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TelegramAccionEsperada persistentTelegramAccionEsperada = em.find(TelegramAccionEsperada.class, telegramAccionEsperada.getId());
            Usuarios idUsuarioOld = persistentTelegramAccionEsperada.getIdUsuario();
            Usuarios idUsuarioNew = telegramAccionEsperada.getIdUsuario();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getId());
                telegramAccionEsperada.setIdUsuario(idUsuarioNew);
            }
            telegramAccionEsperada = em.merge(telegramAccionEsperada);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getTelegramAccionEsperadaList().remove(telegramAccionEsperada);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getTelegramAccionEsperadaList().add(telegramAccionEsperada);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = telegramAccionEsperada.getId();
                if (findTelegramAccionEsperada(id) == null) {
                    throw new NonexistentEntityException("The telegramAccionEsperada with id " + id + " no longer exists.");
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
            TelegramAccionEsperada telegramAccionEsperada;
            try {
                telegramAccionEsperada = em.getReference(TelegramAccionEsperada.class, id);
                telegramAccionEsperada.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telegramAccionEsperada with id " + id + " no longer exists.", enfe);
            }
            Usuarios idUsuario = telegramAccionEsperada.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getTelegramAccionEsperadaList().remove(telegramAccionEsperada);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(telegramAccionEsperada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TelegramAccionEsperada> findTelegramAccionEsperadaEntities() {
        return findTelegramAccionEsperadaEntities(true, -1, -1);
    }

    public List<TelegramAccionEsperada> findTelegramAccionEsperadaEntities(int maxResults, int firstResult) {
        return findTelegramAccionEsperadaEntities(false, maxResults, firstResult);
    }

    private List<TelegramAccionEsperada> findTelegramAccionEsperadaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TelegramAccionEsperada.class));
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

    public TelegramAccionEsperada findTelegramAccionEsperada(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TelegramAccionEsperada.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelegramAccionEsperadaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TelegramAccionEsperada> rt = cq.from(TelegramAccionEsperada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
