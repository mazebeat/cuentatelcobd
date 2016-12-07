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
import cl.intelidata.jpa.Telefono;
import cl.intelidata.jpa.TelefonosServicios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dev-DFeliu
 */
public class TelefonosServiciosJpaController implements Serializable {

    public TelefonosServiciosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TelefonosServicios telefonosServicios) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telefono idTelefono = telefonosServicios.getIdTelefono();
            if (idTelefono != null) {
                idTelefono = em.getReference(idTelefono.getClass(), idTelefono.getId());
                telefonosServicios.setIdTelefono(idTelefono);
            }
            em.persist(telefonosServicios);
            if (idTelefono != null) {
                idTelefono.getTelefonosServiciosList().add(telefonosServicios);
                idTelefono = em.merge(idTelefono);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TelefonosServicios telefonosServicios) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TelefonosServicios persistentTelefonosServicios = em.find(TelefonosServicios.class, telefonosServicios.getId());
            Telefono idTelefonoOld = persistentTelefonosServicios.getIdTelefono();
            Telefono idTelefonoNew = telefonosServicios.getIdTelefono();
            if (idTelefonoNew != null) {
                idTelefonoNew = em.getReference(idTelefonoNew.getClass(), idTelefonoNew.getId());
                telefonosServicios.setIdTelefono(idTelefonoNew);
            }
            telefonosServicios = em.merge(telefonosServicios);
            if (idTelefonoOld != null && !idTelefonoOld.equals(idTelefonoNew)) {
                idTelefonoOld.getTelefonosServiciosList().remove(telefonosServicios);
                idTelefonoOld = em.merge(idTelefonoOld);
            }
            if (idTelefonoNew != null && !idTelefonoNew.equals(idTelefonoOld)) {
                idTelefonoNew.getTelefonosServiciosList().add(telefonosServicios);
                idTelefonoNew = em.merge(idTelefonoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = telefonosServicios.getId();
                if (findTelefonosServicios(id) == null) {
                    throw new NonexistentEntityException("The telefonosServicios with id " + id + " no longer exists.");
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
            TelefonosServicios telefonosServicios;
            try {
                telefonosServicios = em.getReference(TelefonosServicios.class, id);
                telefonosServicios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telefonosServicios with id " + id + " no longer exists.", enfe);
            }
            Telefono idTelefono = telefonosServicios.getIdTelefono();
            if (idTelefono != null) {
                idTelefono.getTelefonosServiciosList().remove(telefonosServicios);
                idTelefono = em.merge(idTelefono);
            }
            em.remove(telefonosServicios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TelefonosServicios> findTelefonosServiciosEntities() {
        return findTelefonosServiciosEntities(true, -1, -1);
    }

    public List<TelefonosServicios> findTelefonosServiciosEntities(int maxResults, int firstResult) {
        return findTelefonosServiciosEntities(false, maxResults, firstResult);
    }

    private List<TelefonosServicios> findTelefonosServiciosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TelefonosServicios.class));
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

    public TelefonosServicios findTelefonosServicios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TelefonosServicios.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelefonosServiciosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TelefonosServicios> rt = cq.from(TelefonosServicios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
