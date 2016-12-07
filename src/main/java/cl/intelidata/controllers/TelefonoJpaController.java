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

import cl.intelidata.controllers.exceptions.IllegalOrphanException;
import cl.intelidata.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.intelidata.jpa.Cliente;
import cl.intelidata.jpa.Telefono;
import cl.intelidata.jpa.TelefonosServicios;
import java.util.ArrayList;
import java.util.List;
import cl.intelidata.jpa.Total;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dev-DFeliu
 */
public class TelefonoJpaController implements Serializable {

    public TelefonoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Telefono telefono) {
        if (telefono.getTelefonosServiciosList() == null) {
            telefono.setTelefonosServiciosList(new ArrayList<TelefonosServicios>());
        }
        if (telefono.getTotalList() == null) {
            telefono.setTotalList(new ArrayList<Total>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente idCliente = telefono.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getId());
                telefono.setIdCliente(idCliente);
            }
            List<TelefonosServicios> attachedTelefonosServiciosList = new ArrayList<TelefonosServicios>();
            for (TelefonosServicios telefonosServiciosListTelefonosServiciosToAttach : telefono.getTelefonosServiciosList()) {
                telefonosServiciosListTelefonosServiciosToAttach = em.getReference(telefonosServiciosListTelefonosServiciosToAttach.getClass(), telefonosServiciosListTelefonosServiciosToAttach.getId());
                attachedTelefonosServiciosList.add(telefonosServiciosListTelefonosServiciosToAttach);
            }
            telefono.setTelefonosServiciosList(attachedTelefonosServiciosList);
            List<Total> attachedTotalList = new ArrayList<Total>();
            for (Total totalListTotalToAttach : telefono.getTotalList()) {
                totalListTotalToAttach = em.getReference(totalListTotalToAttach.getClass(), totalListTotalToAttach.getId());
                attachedTotalList.add(totalListTotalToAttach);
            }
            telefono.setTotalList(attachedTotalList);
            em.persist(telefono);
            if (idCliente != null) {
                idCliente.getTelefonoList().add(telefono);
                idCliente = em.merge(idCliente);
            }
            for (TelefonosServicios telefonosServiciosListTelefonosServicios : telefono.getTelefonosServiciosList()) {
                Telefono oldIdTelefonoOfTelefonosServiciosListTelefonosServicios = telefonosServiciosListTelefonosServicios.getIdTelefono();
                telefonosServiciosListTelefonosServicios.setIdTelefono(telefono);
                telefonosServiciosListTelefonosServicios = em.merge(telefonosServiciosListTelefonosServicios);
                if (oldIdTelefonoOfTelefonosServiciosListTelefonosServicios != null) {
                    oldIdTelefonoOfTelefonosServiciosListTelefonosServicios.getTelefonosServiciosList().remove(telefonosServiciosListTelefonosServicios);
                    oldIdTelefonoOfTelefonosServiciosListTelefonosServicios = em.merge(oldIdTelefonoOfTelefonosServiciosListTelefonosServicios);
                }
            }
            for (Total totalListTotal : telefono.getTotalList()) {
                Telefono oldIdTelefonoOfTotalListTotal = totalListTotal.getIdTelefono();
                totalListTotal.setIdTelefono(telefono);
                totalListTotal = em.merge(totalListTotal);
                if (oldIdTelefonoOfTotalListTotal != null) {
                    oldIdTelefonoOfTotalListTotal.getTotalList().remove(totalListTotal);
                    oldIdTelefonoOfTotalListTotal = em.merge(oldIdTelefonoOfTotalListTotal);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Telefono telefono) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Telefono persistentTelefono = em.find(Telefono.class, telefono.getId());
            Cliente idClienteOld = persistentTelefono.getIdCliente();
            Cliente idClienteNew = telefono.getIdCliente();
            List<TelefonosServicios> telefonosServiciosListOld = persistentTelefono.getTelefonosServiciosList();
            List<TelefonosServicios> telefonosServiciosListNew = telefono.getTelefonosServiciosList();
            List<Total> totalListOld = persistentTelefono.getTotalList();
            List<Total> totalListNew = telefono.getTotalList();
            List<String> illegalOrphanMessages = null;
            for (TelefonosServicios telefonosServiciosListOldTelefonosServicios : telefonosServiciosListOld) {
                if (!telefonosServiciosListNew.contains(telefonosServiciosListOldTelefonosServicios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TelefonosServicios " + telefonosServiciosListOldTelefonosServicios + " since its idTelefono field is not nullable.");
                }
            }
            for (Total totalListOldTotal : totalListOld) {
                if (!totalListNew.contains(totalListOldTotal)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Total " + totalListOldTotal + " since its idTelefono field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getId());
                telefono.setIdCliente(idClienteNew);
            }
            List<TelefonosServicios> attachedTelefonosServiciosListNew = new ArrayList<TelefonosServicios>();
            for (TelefonosServicios telefonosServiciosListNewTelefonosServiciosToAttach : telefonosServiciosListNew) {
                telefonosServiciosListNewTelefonosServiciosToAttach = em.getReference(telefonosServiciosListNewTelefonosServiciosToAttach.getClass(), telefonosServiciosListNewTelefonosServiciosToAttach.getId());
                attachedTelefonosServiciosListNew.add(telefonosServiciosListNewTelefonosServiciosToAttach);
            }
            telefonosServiciosListNew = attachedTelefonosServiciosListNew;
            telefono.setTelefonosServiciosList(telefonosServiciosListNew);
            List<Total> attachedTotalListNew = new ArrayList<Total>();
            for (Total totalListNewTotalToAttach : totalListNew) {
                totalListNewTotalToAttach = em.getReference(totalListNewTotalToAttach.getClass(), totalListNewTotalToAttach.getId());
                attachedTotalListNew.add(totalListNewTotalToAttach);
            }
            totalListNew = attachedTotalListNew;
            telefono.setTotalList(totalListNew);
            telefono = em.merge(telefono);
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getTelefonoList().remove(telefono);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getTelefonoList().add(telefono);
                idClienteNew = em.merge(idClienteNew);
            }
            for (TelefonosServicios telefonosServiciosListNewTelefonosServicios : telefonosServiciosListNew) {
                if (!telefonosServiciosListOld.contains(telefonosServiciosListNewTelefonosServicios)) {
                    Telefono oldIdTelefonoOfTelefonosServiciosListNewTelefonosServicios = telefonosServiciosListNewTelefonosServicios.getIdTelefono();
                    telefonosServiciosListNewTelefonosServicios.setIdTelefono(telefono);
                    telefonosServiciosListNewTelefonosServicios = em.merge(telefonosServiciosListNewTelefonosServicios);
                    if (oldIdTelefonoOfTelefonosServiciosListNewTelefonosServicios != null && !oldIdTelefonoOfTelefonosServiciosListNewTelefonosServicios.equals(telefono)) {
                        oldIdTelefonoOfTelefonosServiciosListNewTelefonosServicios.getTelefonosServiciosList().remove(telefonosServiciosListNewTelefonosServicios);
                        oldIdTelefonoOfTelefonosServiciosListNewTelefonosServicios = em.merge(oldIdTelefonoOfTelefonosServiciosListNewTelefonosServicios);
                    }
                }
            }
            for (Total totalListNewTotal : totalListNew) {
                if (!totalListOld.contains(totalListNewTotal)) {
                    Telefono oldIdTelefonoOfTotalListNewTotal = totalListNewTotal.getIdTelefono();
                    totalListNewTotal.setIdTelefono(telefono);
                    totalListNewTotal = em.merge(totalListNewTotal);
                    if (oldIdTelefonoOfTotalListNewTotal != null && !oldIdTelefonoOfTotalListNewTotal.equals(telefono)) {
                        oldIdTelefonoOfTotalListNewTotal.getTotalList().remove(totalListNewTotal);
                        oldIdTelefonoOfTotalListNewTotal = em.merge(oldIdTelefonoOfTotalListNewTotal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = telefono.getId();
                if (findTelefono(id) == null) {
                    throw new NonexistentEntityException("The telefono with id " + id + " no longer exists.");
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
            Telefono telefono;
            try {
                telefono = em.getReference(Telefono.class, id);
                telefono.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telefono with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TelefonosServicios> telefonosServiciosListOrphanCheck = telefono.getTelefonosServiciosList();
            for (TelefonosServicios telefonosServiciosListOrphanCheckTelefonosServicios : telefonosServiciosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Telefono (" + telefono + ") cannot be destroyed since the TelefonosServicios " + telefonosServiciosListOrphanCheckTelefonosServicios + " in its telefonosServiciosList field has a non-nullable idTelefono field.");
            }
            List<Total> totalListOrphanCheck = telefono.getTotalList();
            for (Total totalListOrphanCheckTotal : totalListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Telefono (" + telefono + ") cannot be destroyed since the Total " + totalListOrphanCheckTotal + " in its totalList field has a non-nullable idTelefono field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cliente idCliente = telefono.getIdCliente();
            if (idCliente != null) {
                idCliente.getTelefonoList().remove(telefono);
                idCliente = em.merge(idCliente);
            }
            em.remove(telefono);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Telefono> findTelefonoEntities() {
        return findTelefonoEntities(true, -1, -1);
    }

    public List<Telefono> findTelefonoEntities(int maxResults, int firstResult) {
        return findTelefonoEntities(false, maxResults, firstResult);
    }

    private List<Telefono> findTelefonoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Telefono.class));
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

    public Telefono findTelefono(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Telefono.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelefonoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Telefono> rt = cq.from(Telefono.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
