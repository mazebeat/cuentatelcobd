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
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.intelidata.jpa.Notificaciones;
import cl.intelidata.jpa.TipoServicio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dev-DFeliu
 */
public class TipoServicioJpaController implements Serializable {

    public TipoServicioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoServicio tipoServicio) {
        if (tipoServicio.getNotificacionesList() == null) {
            tipoServicio.setNotificacionesList(new ArrayList<Notificaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Notificaciones> attachedNotificacionesList = new ArrayList<Notificaciones>();
            for (Notificaciones notificacionesListNotificacionesToAttach : tipoServicio.getNotificacionesList()) {
                notificacionesListNotificacionesToAttach = em.getReference(notificacionesListNotificacionesToAttach.getClass(), notificacionesListNotificacionesToAttach.getId());
                attachedNotificacionesList.add(notificacionesListNotificacionesToAttach);
            }
            tipoServicio.setNotificacionesList(attachedNotificacionesList);
            em.persist(tipoServicio);
            for (Notificaciones notificacionesListNotificaciones : tipoServicio.getNotificacionesList()) {
                TipoServicio oldTipoIdOfNotificacionesListNotificaciones = notificacionesListNotificaciones.getTipoId();
                notificacionesListNotificaciones.setTipoId(tipoServicio);
                notificacionesListNotificaciones = em.merge(notificacionesListNotificaciones);
                if (oldTipoIdOfNotificacionesListNotificaciones != null) {
                    oldTipoIdOfNotificacionesListNotificaciones.getNotificacionesList().remove(notificacionesListNotificaciones);
                    oldTipoIdOfNotificacionesListNotificaciones = em.merge(oldTipoIdOfNotificacionesListNotificaciones);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoServicio tipoServicio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoServicio persistentTipoServicio = em.find(TipoServicio.class, tipoServicio.getId());
            List<Notificaciones> notificacionesListOld = persistentTipoServicio.getNotificacionesList();
            List<Notificaciones> notificacionesListNew = tipoServicio.getNotificacionesList();
            List<Notificaciones> attachedNotificacionesListNew = new ArrayList<Notificaciones>();
            for (Notificaciones notificacionesListNewNotificacionesToAttach : notificacionesListNew) {
                notificacionesListNewNotificacionesToAttach = em.getReference(notificacionesListNewNotificacionesToAttach.getClass(), notificacionesListNewNotificacionesToAttach.getId());
                attachedNotificacionesListNew.add(notificacionesListNewNotificacionesToAttach);
            }
            notificacionesListNew = attachedNotificacionesListNew;
            tipoServicio.setNotificacionesList(notificacionesListNew);
            tipoServicio = em.merge(tipoServicio);
            for (Notificaciones notificacionesListOldNotificaciones : notificacionesListOld) {
                if (!notificacionesListNew.contains(notificacionesListOldNotificaciones)) {
                    notificacionesListOldNotificaciones.setTipoId(null);
                    notificacionesListOldNotificaciones = em.merge(notificacionesListOldNotificaciones);
                }
            }
            for (Notificaciones notificacionesListNewNotificaciones : notificacionesListNew) {
                if (!notificacionesListOld.contains(notificacionesListNewNotificaciones)) {
                    TipoServicio oldTipoIdOfNotificacionesListNewNotificaciones = notificacionesListNewNotificaciones.getTipoId();
                    notificacionesListNewNotificaciones.setTipoId(tipoServicio);
                    notificacionesListNewNotificaciones = em.merge(notificacionesListNewNotificaciones);
                    if (oldTipoIdOfNotificacionesListNewNotificaciones != null && !oldTipoIdOfNotificacionesListNewNotificaciones.equals(tipoServicio)) {
                        oldTipoIdOfNotificacionesListNewNotificaciones.getNotificacionesList().remove(notificacionesListNewNotificaciones);
                        oldTipoIdOfNotificacionesListNewNotificaciones = em.merge(oldTipoIdOfNotificacionesListNewNotificaciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoServicio.getId();
                if (findTipoServicio(id) == null) {
                    throw new NonexistentEntityException("The tipoServicio with id " + id + " no longer exists.");
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
            TipoServicio tipoServicio;
            try {
                tipoServicio = em.getReference(TipoServicio.class, id);
                tipoServicio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoServicio with id " + id + " no longer exists.", enfe);
            }
            List<Notificaciones> notificacionesList = tipoServicio.getNotificacionesList();
            for (Notificaciones notificacionesListNotificaciones : notificacionesList) {
                notificacionesListNotificaciones.setTipoId(null);
                notificacionesListNotificaciones = em.merge(notificacionesListNotificaciones);
            }
            em.remove(tipoServicio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoServicio> findTipoServicioEntities() {
        return findTipoServicioEntities(true, -1, -1);
    }

    public List<TipoServicio> findTipoServicioEntities(int maxResults, int firstResult) {
        return findTipoServicioEntities(false, maxResults, firstResult);
    }

    private List<TipoServicio> findTipoServicioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoServicio.class));
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

    public TipoServicio findTipoServicio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoServicio.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoServicioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoServicio> rt = cq.from(TipoServicio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
