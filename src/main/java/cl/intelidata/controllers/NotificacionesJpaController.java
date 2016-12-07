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
import cl.intelidata.jpa.Notificaciones;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.intelidata.jpa.TipoServicio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dev-DFeliu
 */
public class NotificacionesJpaController implements Serializable {

    public NotificacionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Notificaciones notificaciones) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoServicio tipoId = notificaciones.getTipoId();
            if (tipoId != null) {
                tipoId = em.getReference(tipoId.getClass(), tipoId.getId());
                notificaciones.setTipoId(tipoId);
            }
            em.persist(notificaciones);
            if (tipoId != null) {
                tipoId.getNotificacionesList().add(notificaciones);
                tipoId = em.merge(tipoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Notificaciones notificaciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notificaciones persistentNotificaciones = em.find(Notificaciones.class, notificaciones.getId());
            TipoServicio tipoIdOld = persistentNotificaciones.getTipoId();
            TipoServicio tipoIdNew = notificaciones.getTipoId();
            if (tipoIdNew != null) {
                tipoIdNew = em.getReference(tipoIdNew.getClass(), tipoIdNew.getId());
                notificaciones.setTipoId(tipoIdNew);
            }
            notificaciones = em.merge(notificaciones);
            if (tipoIdOld != null && !tipoIdOld.equals(tipoIdNew)) {
                tipoIdOld.getNotificacionesList().remove(notificaciones);
                tipoIdOld = em.merge(tipoIdOld);
            }
            if (tipoIdNew != null && !tipoIdNew.equals(tipoIdOld)) {
                tipoIdNew.getNotificacionesList().add(notificaciones);
                tipoIdNew = em.merge(tipoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = notificaciones.getId();
                if (findNotificaciones(id) == null) {
                    throw new NonexistentEntityException("The notificaciones with id " + id + " no longer exists.");
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
            Notificaciones notificaciones;
            try {
                notificaciones = em.getReference(Notificaciones.class, id);
                notificaciones.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notificaciones with id " + id + " no longer exists.", enfe);
            }
            TipoServicio tipoId = notificaciones.getTipoId();
            if (tipoId != null) {
                tipoId.getNotificacionesList().remove(notificaciones);
                tipoId = em.merge(tipoId);
            }
            em.remove(notificaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Notificaciones> findNotificacionesEntities() {
        return findNotificacionesEntities(true, -1, -1);
    }

    public List<Notificaciones> findNotificacionesEntities(int maxResults, int firstResult) {
        return findNotificacionesEntities(false, maxResults, firstResult);
    }

    private List<Notificaciones> findNotificacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Notificaciones.class));
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

    public Notificaciones findNotificaciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notificaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotificacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Notificaciones> rt = cq.from(Notificaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
