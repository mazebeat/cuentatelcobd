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
import cl.intelidata.jpa.Meses;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.intelidata.jpa.ResumenAnualCliente;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dev-DFeliu
 */
public class MesesJpaController implements Serializable {

    public MesesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Meses meses) {
        if (meses.getResumenAnualClienteList() == null) {
            meses.setResumenAnualClienteList(new ArrayList<ResumenAnualCliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ResumenAnualCliente> attachedResumenAnualClienteList = new ArrayList<ResumenAnualCliente>();
            for (ResumenAnualCliente resumenAnualClienteListResumenAnualClienteToAttach : meses.getResumenAnualClienteList()) {
                resumenAnualClienteListResumenAnualClienteToAttach = em.getReference(resumenAnualClienteListResumenAnualClienteToAttach.getClass(), resumenAnualClienteListResumenAnualClienteToAttach.getId());
                attachedResumenAnualClienteList.add(resumenAnualClienteListResumenAnualClienteToAttach);
            }
            meses.setResumenAnualClienteList(attachedResumenAnualClienteList);
            em.persist(meses);
            for (ResumenAnualCliente resumenAnualClienteListResumenAnualCliente : meses.getResumenAnualClienteList()) {
                Meses oldIdMesOfResumenAnualClienteListResumenAnualCliente = resumenAnualClienteListResumenAnualCliente.getIdMes();
                resumenAnualClienteListResumenAnualCliente.setIdMes(meses);
                resumenAnualClienteListResumenAnualCliente = em.merge(resumenAnualClienteListResumenAnualCliente);
                if (oldIdMesOfResumenAnualClienteListResumenAnualCliente != null) {
                    oldIdMesOfResumenAnualClienteListResumenAnualCliente.getResumenAnualClienteList().remove(resumenAnualClienteListResumenAnualCliente);
                    oldIdMesOfResumenAnualClienteListResumenAnualCliente = em.merge(oldIdMesOfResumenAnualClienteListResumenAnualCliente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Meses meses) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Meses persistentMeses = em.find(Meses.class, meses.getId());
            List<ResumenAnualCliente> resumenAnualClienteListOld = persistentMeses.getResumenAnualClienteList();
            List<ResumenAnualCliente> resumenAnualClienteListNew = meses.getResumenAnualClienteList();
            List<ResumenAnualCliente> attachedResumenAnualClienteListNew = new ArrayList<ResumenAnualCliente>();
            for (ResumenAnualCliente resumenAnualClienteListNewResumenAnualClienteToAttach : resumenAnualClienteListNew) {
                resumenAnualClienteListNewResumenAnualClienteToAttach = em.getReference(resumenAnualClienteListNewResumenAnualClienteToAttach.getClass(), resumenAnualClienteListNewResumenAnualClienteToAttach.getId());
                attachedResumenAnualClienteListNew.add(resumenAnualClienteListNewResumenAnualClienteToAttach);
            }
            resumenAnualClienteListNew = attachedResumenAnualClienteListNew;
            meses.setResumenAnualClienteList(resumenAnualClienteListNew);
            meses = em.merge(meses);
            for (ResumenAnualCliente resumenAnualClienteListOldResumenAnualCliente : resumenAnualClienteListOld) {
                if (!resumenAnualClienteListNew.contains(resumenAnualClienteListOldResumenAnualCliente)) {
                    resumenAnualClienteListOldResumenAnualCliente.setIdMes(null);
                    resumenAnualClienteListOldResumenAnualCliente = em.merge(resumenAnualClienteListOldResumenAnualCliente);
                }
            }
            for (ResumenAnualCliente resumenAnualClienteListNewResumenAnualCliente : resumenAnualClienteListNew) {
                if (!resumenAnualClienteListOld.contains(resumenAnualClienteListNewResumenAnualCliente)) {
                    Meses oldIdMesOfResumenAnualClienteListNewResumenAnualCliente = resumenAnualClienteListNewResumenAnualCliente.getIdMes();
                    resumenAnualClienteListNewResumenAnualCliente.setIdMes(meses);
                    resumenAnualClienteListNewResumenAnualCliente = em.merge(resumenAnualClienteListNewResumenAnualCliente);
                    if (oldIdMesOfResumenAnualClienteListNewResumenAnualCliente != null && !oldIdMesOfResumenAnualClienteListNewResumenAnualCliente.equals(meses)) {
                        oldIdMesOfResumenAnualClienteListNewResumenAnualCliente.getResumenAnualClienteList().remove(resumenAnualClienteListNewResumenAnualCliente);
                        oldIdMesOfResumenAnualClienteListNewResumenAnualCliente = em.merge(oldIdMesOfResumenAnualClienteListNewResumenAnualCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = meses.getId();
                if (findMeses(id) == null) {
                    throw new NonexistentEntityException("The meses with id " + id + " no longer exists.");
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
            Meses meses;
            try {
                meses = em.getReference(Meses.class, id);
                meses.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The meses with id " + id + " no longer exists.", enfe);
            }
            List<ResumenAnualCliente> resumenAnualClienteList = meses.getResumenAnualClienteList();
            for (ResumenAnualCliente resumenAnualClienteListResumenAnualCliente : resumenAnualClienteList) {
                resumenAnualClienteListResumenAnualCliente.setIdMes(null);
                resumenAnualClienteListResumenAnualCliente = em.merge(resumenAnualClienteListResumenAnualCliente);
            }
            em.remove(meses);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Meses> findMesesEntities() {
        return findMesesEntities(true, -1, -1);
    }

    public List<Meses> findMesesEntities(int maxResults, int firstResult) {
        return findMesesEntities(false, maxResults, firstResult);
    }

    private List<Meses> findMesesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Meses.class));
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

    public Meses findMeses(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Meses.class, id);
        } finally {
            em.close();
        }
    }

    public int getMesesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Meses> rt = cq.from(Meses.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
