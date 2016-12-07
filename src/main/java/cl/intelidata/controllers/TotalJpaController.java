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
import cl.intelidata.jpa.Total;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dev-DFeliu
 */
public class TotalJpaController implements Serializable {

    public TotalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Total total) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telefono idTelefono = total.getIdTelefono();
            if (idTelefono != null) {
                idTelefono = em.getReference(idTelefono.getClass(), idTelefono.getId());
                total.setIdTelefono(idTelefono);
            }
            em.persist(total);
            if (idTelefono != null) {
                idTelefono.getTotalList().add(total);
                idTelefono = em.merge(idTelefono);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Total total) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Total persistentTotal = em.find(Total.class, total.getId());
            Telefono idTelefonoOld = persistentTotal.getIdTelefono();
            Telefono idTelefonoNew = total.getIdTelefono();
            if (idTelefonoNew != null) {
                idTelefonoNew = em.getReference(idTelefonoNew.getClass(), idTelefonoNew.getId());
                total.setIdTelefono(idTelefonoNew);
            }
            total = em.merge(total);
            if (idTelefonoOld != null && !idTelefonoOld.equals(idTelefonoNew)) {
                idTelefonoOld.getTotalList().remove(total);
                idTelefonoOld = em.merge(idTelefonoOld);
            }
            if (idTelefonoNew != null && !idTelefonoNew.equals(idTelefonoOld)) {
                idTelefonoNew.getTotalList().add(total);
                idTelefonoNew = em.merge(idTelefonoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = total.getId();
                if (findTotal(id) == null) {
                    throw new NonexistentEntityException("The total with id " + id + " no longer exists.");
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
            Total total;
            try {
                total = em.getReference(Total.class, id);
                total.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The total with id " + id + " no longer exists.", enfe);
            }
            Telefono idTelefono = total.getIdTelefono();
            if (idTelefono != null) {
                idTelefono.getTotalList().remove(total);
                idTelefono = em.merge(idTelefono);
            }
            em.remove(total);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Total> findTotalEntities() {
        return findTotalEntities(true, -1, -1);
    }

    public List<Total> findTotalEntities(int maxResults, int firstResult) {
        return findTotalEntities(false, maxResults, firstResult);
    }

    private List<Total> findTotalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Total.class));
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

    public Total findTotal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Total.class, id);
        } finally {
            em.close();
        }
    }

    public int getTotalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Total> rt = cq.from(Total.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
