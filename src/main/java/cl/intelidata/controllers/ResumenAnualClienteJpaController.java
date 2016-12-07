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
import cl.intelidata.jpa.Cliente;
import cl.intelidata.jpa.Meses;
import cl.intelidata.jpa.ResumenAnualCliente;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dev-DFeliu
 */
public class ResumenAnualClienteJpaController implements Serializable {

    public ResumenAnualClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ResumenAnualCliente resumenAnualCliente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente idCliente = resumenAnualCliente.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getId());
                resumenAnualCliente.setIdCliente(idCliente);
            }
            Meses idMes = resumenAnualCliente.getIdMes();
            if (idMes != null) {
                idMes = em.getReference(idMes.getClass(), idMes.getId());
                resumenAnualCliente.setIdMes(idMes);
            }
            em.persist(resumenAnualCliente);
            if (idCliente != null) {
                idCliente.getResumenAnualClienteList().add(resumenAnualCliente);
                idCliente = em.merge(idCliente);
            }
            if (idMes != null) {
                idMes.getResumenAnualClienteList().add(resumenAnualCliente);
                idMes = em.merge(idMes);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ResumenAnualCliente resumenAnualCliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ResumenAnualCliente persistentResumenAnualCliente = em.find(ResumenAnualCliente.class, resumenAnualCliente.getId());
            Cliente idClienteOld = persistentResumenAnualCliente.getIdCliente();
            Cliente idClienteNew = resumenAnualCliente.getIdCliente();
            Meses idMesOld = persistentResumenAnualCliente.getIdMes();
            Meses idMesNew = resumenAnualCliente.getIdMes();
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getId());
                resumenAnualCliente.setIdCliente(idClienteNew);
            }
            if (idMesNew != null) {
                idMesNew = em.getReference(idMesNew.getClass(), idMesNew.getId());
                resumenAnualCliente.setIdMes(idMesNew);
            }
            resumenAnualCliente = em.merge(resumenAnualCliente);
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getResumenAnualClienteList().remove(resumenAnualCliente);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getResumenAnualClienteList().add(resumenAnualCliente);
                idClienteNew = em.merge(idClienteNew);
            }
            if (idMesOld != null && !idMesOld.equals(idMesNew)) {
                idMesOld.getResumenAnualClienteList().remove(resumenAnualCliente);
                idMesOld = em.merge(idMesOld);
            }
            if (idMesNew != null && !idMesNew.equals(idMesOld)) {
                idMesNew.getResumenAnualClienteList().add(resumenAnualCliente);
                idMesNew = em.merge(idMesNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = resumenAnualCliente.getId();
                if (findResumenAnualCliente(id) == null) {
                    throw new NonexistentEntityException("The resumenAnualCliente with id " + id + " no longer exists.");
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
            ResumenAnualCliente resumenAnualCliente;
            try {
                resumenAnualCliente = em.getReference(ResumenAnualCliente.class, id);
                resumenAnualCliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The resumenAnualCliente with id " + id + " no longer exists.", enfe);
            }
            Cliente idCliente = resumenAnualCliente.getIdCliente();
            if (idCliente != null) {
                idCliente.getResumenAnualClienteList().remove(resumenAnualCliente);
                idCliente = em.merge(idCliente);
            }
            Meses idMes = resumenAnualCliente.getIdMes();
            if (idMes != null) {
                idMes.getResumenAnualClienteList().remove(resumenAnualCliente);
                idMes = em.merge(idMes);
            }
            em.remove(resumenAnualCliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ResumenAnualCliente> findResumenAnualClienteEntities() {
        return findResumenAnualClienteEntities(true, -1, -1);
    }

    public List<ResumenAnualCliente> findResumenAnualClienteEntities(int maxResults, int firstResult) {
        return findResumenAnualClienteEntities(false, maxResults, firstResult);
    }

    private List<ResumenAnualCliente> findResumenAnualClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ResumenAnualCliente.class));
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

    public ResumenAnualCliente findResumenAnualCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ResumenAnualCliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getResumenAnualClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ResumenAnualCliente> rt = cq.from(ResumenAnualCliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
