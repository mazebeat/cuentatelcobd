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

import cl.intelidata.controllers.exceptions.IllegalOrphanException;
import cl.intelidata.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.intelidata.jpa.CentroDeAyuda;
import java.util.ArrayList;
import java.util.List;
import cl.intelidata.jpa.Cliente;
import cl.intelidata.jpa.Empresa;
import cl.intelidata.jpa.ServicioEmpresa;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dev-DFeliu
 */
public class EmpresaJpaController1 implements Serializable {

    public EmpresaJpaController1(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empresa empresa) {
        if (empresa.getCentroDeAyudaList() == null) {
            empresa.setCentroDeAyudaList(new ArrayList<CentroDeAyuda>());
        }
        if (empresa.getClienteList() == null) {
            empresa.setClienteList(new ArrayList<Cliente>());
        }
        if (empresa.getServicioEmpresaList() == null) {
            empresa.setServicioEmpresaList(new ArrayList<ServicioEmpresa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CentroDeAyuda> attachedCentroDeAyudaList = new ArrayList<CentroDeAyuda>();
            for (CentroDeAyuda centroDeAyudaListCentroDeAyudaToAttach : empresa.getCentroDeAyudaList()) {
                centroDeAyudaListCentroDeAyudaToAttach = em.getReference(centroDeAyudaListCentroDeAyudaToAttach.getClass(), centroDeAyudaListCentroDeAyudaToAttach.getId());
                attachedCentroDeAyudaList.add(centroDeAyudaListCentroDeAyudaToAttach);
            }
            empresa.setCentroDeAyudaList(attachedCentroDeAyudaList);
            List<Cliente> attachedClienteList = new ArrayList<Cliente>();
            for (Cliente clienteListClienteToAttach : empresa.getClienteList()) {
                clienteListClienteToAttach = em.getReference(clienteListClienteToAttach.getClass(), clienteListClienteToAttach.getId());
                attachedClienteList.add(clienteListClienteToAttach);
            }
            empresa.setClienteList(attachedClienteList);
            List<ServicioEmpresa> attachedServicioEmpresaList = new ArrayList<ServicioEmpresa>();
            for (ServicioEmpresa servicioEmpresaListServicioEmpresaToAttach : empresa.getServicioEmpresaList()) {
                servicioEmpresaListServicioEmpresaToAttach = em.getReference(servicioEmpresaListServicioEmpresaToAttach.getClass(), servicioEmpresaListServicioEmpresaToAttach.getId());
                attachedServicioEmpresaList.add(servicioEmpresaListServicioEmpresaToAttach);
            }
            empresa.setServicioEmpresaList(attachedServicioEmpresaList);
            em.persist(empresa);
            for (CentroDeAyuda centroDeAyudaListCentroDeAyuda : empresa.getCentroDeAyudaList()) {
                Empresa oldIdEmpresaOfCentroDeAyudaListCentroDeAyuda = centroDeAyudaListCentroDeAyuda.getIdEmpresa();
                centroDeAyudaListCentroDeAyuda.setIdEmpresa(empresa);
                centroDeAyudaListCentroDeAyuda = em.merge(centroDeAyudaListCentroDeAyuda);
                if (oldIdEmpresaOfCentroDeAyudaListCentroDeAyuda != null) {
                    oldIdEmpresaOfCentroDeAyudaListCentroDeAyuda.getCentroDeAyudaList().remove(centroDeAyudaListCentroDeAyuda);
                    oldIdEmpresaOfCentroDeAyudaListCentroDeAyuda = em.merge(oldIdEmpresaOfCentroDeAyudaListCentroDeAyuda);
                }
            }
            for (Cliente clienteListCliente : empresa.getClienteList()) {
                Empresa oldEmpresaIdOfClienteListCliente = clienteListCliente.getEmpresaId();
                clienteListCliente.setEmpresaId(empresa);
                clienteListCliente = em.merge(clienteListCliente);
                if (oldEmpresaIdOfClienteListCliente != null) {
                    oldEmpresaIdOfClienteListCliente.getClienteList().remove(clienteListCliente);
                    oldEmpresaIdOfClienteListCliente = em.merge(oldEmpresaIdOfClienteListCliente);
                }
            }
            for (ServicioEmpresa servicioEmpresaListServicioEmpresa : empresa.getServicioEmpresaList()) {
                Empresa oldIdEmpresaOfServicioEmpresaListServicioEmpresa = servicioEmpresaListServicioEmpresa.getIdEmpresa();
                servicioEmpresaListServicioEmpresa.setIdEmpresa(empresa);
                servicioEmpresaListServicioEmpresa = em.merge(servicioEmpresaListServicioEmpresa);
                if (oldIdEmpresaOfServicioEmpresaListServicioEmpresa != null) {
                    oldIdEmpresaOfServicioEmpresaListServicioEmpresa.getServicioEmpresaList().remove(servicioEmpresaListServicioEmpresa);
                    oldIdEmpresaOfServicioEmpresaListServicioEmpresa = em.merge(oldIdEmpresaOfServicioEmpresaListServicioEmpresa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empresa empresa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa persistentEmpresa = em.find(Empresa.class, empresa.getId());
            List<CentroDeAyuda> centroDeAyudaListOld = persistentEmpresa.getCentroDeAyudaList();
            List<CentroDeAyuda> centroDeAyudaListNew = empresa.getCentroDeAyudaList();
            List<Cliente> clienteListOld = persistentEmpresa.getClienteList();
            List<Cliente> clienteListNew = empresa.getClienteList();
            List<ServicioEmpresa> servicioEmpresaListOld = persistentEmpresa.getServicioEmpresaList();
            List<ServicioEmpresa> servicioEmpresaListNew = empresa.getServicioEmpresaList();
            List<String> illegalOrphanMessages = null;
            for (Cliente clienteListOldCliente : clienteListOld) {
                if (!clienteListNew.contains(clienteListOldCliente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cliente " + clienteListOldCliente + " since its empresaId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<CentroDeAyuda> attachedCentroDeAyudaListNew = new ArrayList<CentroDeAyuda>();
            for (CentroDeAyuda centroDeAyudaListNewCentroDeAyudaToAttach : centroDeAyudaListNew) {
                centroDeAyudaListNewCentroDeAyudaToAttach = em.getReference(centroDeAyudaListNewCentroDeAyudaToAttach.getClass(), centroDeAyudaListNewCentroDeAyudaToAttach.getId());
                attachedCentroDeAyudaListNew.add(centroDeAyudaListNewCentroDeAyudaToAttach);
            }
            centroDeAyudaListNew = attachedCentroDeAyudaListNew;
            empresa.setCentroDeAyudaList(centroDeAyudaListNew);
            List<Cliente> attachedClienteListNew = new ArrayList<Cliente>();
            for (Cliente clienteListNewClienteToAttach : clienteListNew) {
                clienteListNewClienteToAttach = em.getReference(clienteListNewClienteToAttach.getClass(), clienteListNewClienteToAttach.getId());
                attachedClienteListNew.add(clienteListNewClienteToAttach);
            }
            clienteListNew = attachedClienteListNew;
            empresa.setClienteList(clienteListNew);
            List<ServicioEmpresa> attachedServicioEmpresaListNew = new ArrayList<ServicioEmpresa>();
            for (ServicioEmpresa servicioEmpresaListNewServicioEmpresaToAttach : servicioEmpresaListNew) {
                servicioEmpresaListNewServicioEmpresaToAttach = em.getReference(servicioEmpresaListNewServicioEmpresaToAttach.getClass(), servicioEmpresaListNewServicioEmpresaToAttach.getId());
                attachedServicioEmpresaListNew.add(servicioEmpresaListNewServicioEmpresaToAttach);
            }
            servicioEmpresaListNew = attachedServicioEmpresaListNew;
            empresa.setServicioEmpresaList(servicioEmpresaListNew);
            empresa = em.merge(empresa);
            for (CentroDeAyuda centroDeAyudaListOldCentroDeAyuda : centroDeAyudaListOld) {
                if (!centroDeAyudaListNew.contains(centroDeAyudaListOldCentroDeAyuda)) {
                    centroDeAyudaListOldCentroDeAyuda.setIdEmpresa(null);
                    centroDeAyudaListOldCentroDeAyuda = em.merge(centroDeAyudaListOldCentroDeAyuda);
                }
            }
            for (CentroDeAyuda centroDeAyudaListNewCentroDeAyuda : centroDeAyudaListNew) {
                if (!centroDeAyudaListOld.contains(centroDeAyudaListNewCentroDeAyuda)) {
                    Empresa oldIdEmpresaOfCentroDeAyudaListNewCentroDeAyuda = centroDeAyudaListNewCentroDeAyuda.getIdEmpresa();
                    centroDeAyudaListNewCentroDeAyuda.setIdEmpresa(empresa);
                    centroDeAyudaListNewCentroDeAyuda = em.merge(centroDeAyudaListNewCentroDeAyuda);
                    if (oldIdEmpresaOfCentroDeAyudaListNewCentroDeAyuda != null && !oldIdEmpresaOfCentroDeAyudaListNewCentroDeAyuda.equals(empresa)) {
                        oldIdEmpresaOfCentroDeAyudaListNewCentroDeAyuda.getCentroDeAyudaList().remove(centroDeAyudaListNewCentroDeAyuda);
                        oldIdEmpresaOfCentroDeAyudaListNewCentroDeAyuda = em.merge(oldIdEmpresaOfCentroDeAyudaListNewCentroDeAyuda);
                    }
                }
            }
            for (Cliente clienteListNewCliente : clienteListNew) {
                if (!clienteListOld.contains(clienteListNewCliente)) {
                    Empresa oldEmpresaIdOfClienteListNewCliente = clienteListNewCliente.getEmpresaId();
                    clienteListNewCliente.setEmpresaId(empresa);
                    clienteListNewCliente = em.merge(clienteListNewCliente);
                    if (oldEmpresaIdOfClienteListNewCliente != null && !oldEmpresaIdOfClienteListNewCliente.equals(empresa)) {
                        oldEmpresaIdOfClienteListNewCliente.getClienteList().remove(clienteListNewCliente);
                        oldEmpresaIdOfClienteListNewCliente = em.merge(oldEmpresaIdOfClienteListNewCliente);
                    }
                }
            }
            for (ServicioEmpresa servicioEmpresaListOldServicioEmpresa : servicioEmpresaListOld) {
                if (!servicioEmpresaListNew.contains(servicioEmpresaListOldServicioEmpresa)) {
                    servicioEmpresaListOldServicioEmpresa.setIdEmpresa(null);
                    servicioEmpresaListOldServicioEmpresa = em.merge(servicioEmpresaListOldServicioEmpresa);
                }
            }
            for (ServicioEmpresa servicioEmpresaListNewServicioEmpresa : servicioEmpresaListNew) {
                if (!servicioEmpresaListOld.contains(servicioEmpresaListNewServicioEmpresa)) {
                    Empresa oldIdEmpresaOfServicioEmpresaListNewServicioEmpresa = servicioEmpresaListNewServicioEmpresa.getIdEmpresa();
                    servicioEmpresaListNewServicioEmpresa.setIdEmpresa(empresa);
                    servicioEmpresaListNewServicioEmpresa = em.merge(servicioEmpresaListNewServicioEmpresa);
                    if (oldIdEmpresaOfServicioEmpresaListNewServicioEmpresa != null && !oldIdEmpresaOfServicioEmpresaListNewServicioEmpresa.equals(empresa)) {
                        oldIdEmpresaOfServicioEmpresaListNewServicioEmpresa.getServicioEmpresaList().remove(servicioEmpresaListNewServicioEmpresa);
                        oldIdEmpresaOfServicioEmpresaListNewServicioEmpresa = em.merge(oldIdEmpresaOfServicioEmpresaListNewServicioEmpresa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empresa.getId();
                if (findEmpresa(id) == null) {
                    throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa empresa;
            try {
                empresa = em.getReference(Empresa.class, id);
                empresa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cliente> clienteListOrphanCheck = empresa.getClienteList();
            for (Cliente clienteListOrphanCheckCliente : clienteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresa (" + empresa + ") cannot be destroyed since the Cliente " + clienteListOrphanCheckCliente + " in its clienteList field has a non-nullable empresaId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<CentroDeAyuda> centroDeAyudaList = empresa.getCentroDeAyudaList();
            for (CentroDeAyuda centroDeAyudaListCentroDeAyuda : centroDeAyudaList) {
                centroDeAyudaListCentroDeAyuda.setIdEmpresa(null);
                centroDeAyudaListCentroDeAyuda = em.merge(centroDeAyudaListCentroDeAyuda);
            }
            List<ServicioEmpresa> servicioEmpresaList = empresa.getServicioEmpresaList();
            for (ServicioEmpresa servicioEmpresaListServicioEmpresa : servicioEmpresaList) {
                servicioEmpresaListServicioEmpresa.setIdEmpresa(null);
                servicioEmpresaListServicioEmpresa = em.merge(servicioEmpresaListServicioEmpresa);
            }
            em.remove(empresa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empresa> findEmpresaEntities() {
        return findEmpresaEntities(true, -1, -1);
    }

    public List<Empresa> findEmpresaEntities(int maxResults, int firstResult) {
        return findEmpresaEntities(false, maxResults, firstResult);
    }

    private List<Empresa> findEmpresaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empresa.class));
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

    public Empresa findEmpresa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empresa.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empresa> rt = cq.from(Empresa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
