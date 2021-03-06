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
import cl.intelidata.jpa.CentroDeAyuda;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.intelidata.jpa.Empresa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dev-DFeliu
 */
public class CentroDeAyudaJpaController implements Serializable {

    public CentroDeAyudaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CentroDeAyuda centroDeAyuda) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa idEmpresa = centroDeAyuda.getIdEmpresa();
            if (idEmpresa != null) {
                idEmpresa = em.getReference(idEmpresa.getClass(), idEmpresa.getId());
                centroDeAyuda.setIdEmpresa(idEmpresa);
            }
            em.persist(centroDeAyuda);
            if (idEmpresa != null) {
                idEmpresa.getCentroDeAyudaList().add(centroDeAyuda);
                idEmpresa = em.merge(idEmpresa);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CentroDeAyuda centroDeAyuda) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CentroDeAyuda persistentCentroDeAyuda = em.find(CentroDeAyuda.class, centroDeAyuda.getId());
            Empresa idEmpresaOld = persistentCentroDeAyuda.getIdEmpresa();
            Empresa idEmpresaNew = centroDeAyuda.getIdEmpresa();
            if (idEmpresaNew != null) {
                idEmpresaNew = em.getReference(idEmpresaNew.getClass(), idEmpresaNew.getId());
                centroDeAyuda.setIdEmpresa(idEmpresaNew);
            }
            centroDeAyuda = em.merge(centroDeAyuda);
            if (idEmpresaOld != null && !idEmpresaOld.equals(idEmpresaNew)) {
                idEmpresaOld.getCentroDeAyudaList().remove(centroDeAyuda);
                idEmpresaOld = em.merge(idEmpresaOld);
            }
            if (idEmpresaNew != null && !idEmpresaNew.equals(idEmpresaOld)) {
                idEmpresaNew.getCentroDeAyudaList().add(centroDeAyuda);
                idEmpresaNew = em.merge(idEmpresaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = centroDeAyuda.getId();
                if (findCentroDeAyuda(id) == null) {
                    throw new NonexistentEntityException("The centroDeAyuda with id " + id + " no longer exists.");
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
            CentroDeAyuda centroDeAyuda;
            try {
                centroDeAyuda = em.getReference(CentroDeAyuda.class, id);
                centroDeAyuda.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The centroDeAyuda with id " + id + " no longer exists.", enfe);
            }
            Empresa idEmpresa = centroDeAyuda.getIdEmpresa();
            if (idEmpresa != null) {
                idEmpresa.getCentroDeAyudaList().remove(centroDeAyuda);
                idEmpresa = em.merge(idEmpresa);
            }
            em.remove(centroDeAyuda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CentroDeAyuda> findCentroDeAyudaEntities() {
        return findCentroDeAyudaEntities(true, -1, -1);
    }

    public List<CentroDeAyuda> findCentroDeAyudaEntities(int maxResults, int firstResult) {
        return findCentroDeAyudaEntities(false, maxResults, firstResult);
    }

    private List<CentroDeAyuda> findCentroDeAyudaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CentroDeAyuda.class));
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

    public CentroDeAyuda findCentroDeAyuda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CentroDeAyuda.class, id);
        } finally {
            em.close();
        }
    }

    public int getCentroDeAyudaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CentroDeAyuda> rt = cq.from(CentroDeAyuda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
