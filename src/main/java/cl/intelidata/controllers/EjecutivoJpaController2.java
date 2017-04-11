/*
 * Copyright (c) 2017, Intelidata S.A.
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
import cl.intelidata.jpa.Ejecutivo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dev-DFeliu
 */
public class EjecutivoJpaController2 implements Serializable {

    public EjecutivoJpaController2(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ejecutivo ejecutivo) {
        if (ejecutivo.getClienteList() == null) {
            ejecutivo.setClienteList(new ArrayList<Cliente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : ejecutivo.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getId());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            ejecutivo.setClienteList(attachedClienteList);
            em.persist(ejecutivo);
            for (Cliente clienteListCliente : ejecutivo.getClienteList()) {
                Ejecutivo oldEjecutivoIdOfClienteListCliente = clienteListCliente.getEjecutivoId();
                clienteListCliente.setEjecutivoId(ejecutivo);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldEjecutivoIdOfClienteListCliente != null) {
                    oldEjecutivoIdOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldEjecutivoIdOfClienteListCliente = em.merge(oldEjecutivoIdOfClienteListCliente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ejecutivo ejecutivo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ejecutivo persistentEjecutivo = em.find(Ejecutivo.class, ejecutivo.getId());
            List<Cliente> clienteListOld = persistentEjecutivo.getClienteList();
            List<Cliente> clienteListNew = ejecutivo.getClienteList();
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getId());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            ejecutivo.setClienteList(clienteListNew);
            ejecutivo = em.merge(ejecutivo);
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    clienteListOldCliente.setEjecutivoId(null);
                    clienteListOldCliente = em.merge(clienteListOldCliente);
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Ejecutivo oldEjecutivoIdOfClienteListNewCliente = clienteListNewCliente.getEjecutivoId();
                    clienteListNewCliente.setEjecutivoId(ejecutivo);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldEjecutivoIdOfClienteListNewCliente != null && !oldEjecutivoIdOfClienteListNewCliente.equals(ejecutivo)) {
                        oldEjecutivoIdOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldEjecutivoIdOfClienteListNewCliente = em.merge(oldEjecutivoIdOfClienteListNewCliente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ejecutivo.getId();
                if (findEjecutivo(id) == null) {
                    throw new NonexistentEntityException("The ejecutivo with id " + id + " no longer exists.");
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
            Ejecutivo ejecutivo;
            try {
                ejecutivo = em.getReference(Ejecutivo.class, id);
                ejecutivo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ejecutivo with id " + id + " no longer exists.", enfe);
            }
            List<Cliente> clienteList = ejecutivo.getClienteList();
            for (Cliente clienteListCliente : clienteList) {
                clienteListCliente.setEjecutivoId(null);
                clienteListCliente = em.merge(clienteListCliente);
            }
            em.remove(ejecutivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ejecutivo> findEjecutivoEntities() {
        return findEjecutivoEntities(true, -1, -1);
    }

    public List<Ejecutivo> findEjecutivoEntities(int maxResults, int firstResult) {
        return findEjecutivoEntities(false, maxResults, firstResult);
    }

    private List<Ejecutivo> findEjecutivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ejecutivo.class));
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

    public Ejecutivo findEjecutivo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ejecutivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getEjecutivoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ejecutivo> rt = cq.from(Ejecutivo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
