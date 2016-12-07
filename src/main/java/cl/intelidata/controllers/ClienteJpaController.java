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
import cl.intelidata.jpa.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.intelidata.jpa.Persona;
import cl.intelidata.jpa.Empresa;
import cl.intelidata.jpa.Ejecutivo;
import cl.intelidata.jpa.ResumenAnualCliente;
import java.util.ArrayList;
import java.util.List;
import cl.intelidata.jpa.TotalServicios;
import cl.intelidata.jpa.Hitos;
import cl.intelidata.jpa.Telefono;
import cl.intelidata.jpa.ClientePreguntas;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dev-DFeliu
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getResumenAnualClienteList() == null) {
            cliente.setResumenAnualClienteList(new ArrayList<ResumenAnualCliente>());
        }
        if (cliente.getTotalServiciosList() == null) {
            cliente.setTotalServiciosList(new ArrayList<TotalServicios>());
        }
        if (cliente.getHitosList() == null) {
            cliente.setHitosList(new ArrayList<Hitos>());
        }
        if (cliente.getTelefonoList() == null) {
            cliente.setTelefonoList(new ArrayList<Telefono>());
        }
        if (cliente.getClientePreguntasList() == null) {
            cliente.setClientePreguntasList(new ArrayList<ClientePreguntas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona personaId = cliente.getPersonaId();
            if (personaId != null) {
                personaId = em.getReference(personaId.getClass(), personaId.getId());
                cliente.setPersonaId(personaId);
            }
            Empresa empresaId = cliente.getEmpresaId();
            if (empresaId != null) {
                empresaId = em.getReference(empresaId.getClass(), empresaId.getId());
                cliente.setEmpresaId(empresaId);
            }
            Ejecutivo ejecutivoId = cliente.getEjecutivoId();
            if (ejecutivoId != null) {
                ejecutivoId = em.getReference(ejecutivoId.getClass(), ejecutivoId.getId());
                cliente.setEjecutivoId(ejecutivoId);
            }
            List<ResumenAnualCliente> attachedResumenAnualClienteList = new ArrayList<ResumenAnualCliente>();
            for (ResumenAnualCliente resumenAnualClienteListResumenAnualClienteToAttach : cliente.getResumenAnualClienteList()) {
                resumenAnualClienteListResumenAnualClienteToAttach = em.getReference(resumenAnualClienteListResumenAnualClienteToAttach.getClass(), resumenAnualClienteListResumenAnualClienteToAttach.getId());
                attachedResumenAnualClienteList.add(resumenAnualClienteListResumenAnualClienteToAttach);
            }
            cliente.setResumenAnualClienteList(attachedResumenAnualClienteList);
            List<TotalServicios> attachedTotalServiciosList = new ArrayList<TotalServicios>();
            for (TotalServicios totalServiciosListTotalServiciosToAttach : cliente.getTotalServiciosList()) {
                totalServiciosListTotalServiciosToAttach = em.getReference(totalServiciosListTotalServiciosToAttach.getClass(), totalServiciosListTotalServiciosToAttach.getId());
                attachedTotalServiciosList.add(totalServiciosListTotalServiciosToAttach);
            }
            cliente.setTotalServiciosList(attachedTotalServiciosList);
            List<Hitos> attachedHitosList = new ArrayList<Hitos>();
            for (Hitos hitosListHitosToAttach : cliente.getHitosList()) {
                hitosListHitosToAttach = em.getReference(hitosListHitosToAttach.getClass(), hitosListHitosToAttach.getId());
                attachedHitosList.add(hitosListHitosToAttach);
            }
            cliente.setHitosList(attachedHitosList);
            List<Telefono> attachedTelefonoList = new ArrayList<Telefono>();
            for (Telefono telefonoListTelefonoToAttach : cliente.getTelefonoList()) {
                telefonoListTelefonoToAttach = em.getReference(telefonoListTelefonoToAttach.getClass(), telefonoListTelefonoToAttach.getId());
                attachedTelefonoList.add(telefonoListTelefonoToAttach);
            }
            cliente.setTelefonoList(attachedTelefonoList);
            List<ClientePreguntas> attachedClientePreguntasList = new ArrayList<ClientePreguntas>();
            for (ClientePreguntas clientePreguntasListClientePreguntasToAttach : cliente.getClientePreguntasList()) {
                clientePreguntasListClientePreguntasToAttach = em.getReference(clientePreguntasListClientePreguntasToAttach.getClass(), clientePreguntasListClientePreguntasToAttach.getId());
                attachedClientePreguntasList.add(clientePreguntasListClientePreguntasToAttach);
            }
            cliente.setClientePreguntasList(attachedClientePreguntasList);
            em.persist(cliente);
            if (personaId != null) {
                personaId.getClienteList().add(cliente);
                personaId = em.merge(personaId);
            }
            if (empresaId != null) {
                empresaId.getClienteList().add(cliente);
                empresaId = em.merge(empresaId);
            }
            if (ejecutivoId != null) {
                ejecutivoId.getClienteList().add(cliente);
                ejecutivoId = em.merge(ejecutivoId);
            }
            for (ResumenAnualCliente resumenAnualClienteListResumenAnualCliente : cliente.getResumenAnualClienteList()) {
                Cliente oldIdClienteOfResumenAnualClienteListResumenAnualCliente = resumenAnualClienteListResumenAnualCliente.getIdCliente();
                resumenAnualClienteListResumenAnualCliente.setIdCliente(cliente);
                resumenAnualClienteListResumenAnualCliente = em.merge(resumenAnualClienteListResumenAnualCliente);
                if (oldIdClienteOfResumenAnualClienteListResumenAnualCliente != null) {
                    oldIdClienteOfResumenAnualClienteListResumenAnualCliente.getResumenAnualClienteList().remove(resumenAnualClienteListResumenAnualCliente);
                    oldIdClienteOfResumenAnualClienteListResumenAnualCliente = em.merge(oldIdClienteOfResumenAnualClienteListResumenAnualCliente);
                }
            }
            for (TotalServicios totalServiciosListTotalServicios : cliente.getTotalServiciosList()) {
                Cliente oldIdClienteOfTotalServiciosListTotalServicios = totalServiciosListTotalServicios.getIdCliente();
                totalServiciosListTotalServicios.setIdCliente(cliente);
                totalServiciosListTotalServicios = em.merge(totalServiciosListTotalServicios);
                if (oldIdClienteOfTotalServiciosListTotalServicios != null) {
                    oldIdClienteOfTotalServiciosListTotalServicios.getTotalServiciosList().remove(totalServiciosListTotalServicios);
                    oldIdClienteOfTotalServiciosListTotalServicios = em.merge(oldIdClienteOfTotalServiciosListTotalServicios);
                }
            }
            for (Hitos hitosListHitos : cliente.getHitosList()) {
                Cliente oldClienteIdOfHitosListHitos = hitosListHitos.getClienteId();
                hitosListHitos.setClienteId(cliente);
                hitosListHitos = em.merge(hitosListHitos);
                if (oldClienteIdOfHitosListHitos != null) {
                    oldClienteIdOfHitosListHitos.getHitosList().remove(hitosListHitos);
                    oldClienteIdOfHitosListHitos = em.merge(oldClienteIdOfHitosListHitos);
                }
            }
            for (Telefono telefonoListTelefono : cliente.getTelefonoList()) {
                Cliente oldIdClienteOfTelefonoListTelefono = telefonoListTelefono.getIdCliente();
                telefonoListTelefono.setIdCliente(cliente);
                telefonoListTelefono = em.merge(telefonoListTelefono);
                if (oldIdClienteOfTelefonoListTelefono != null) {
                    oldIdClienteOfTelefonoListTelefono.getTelefonoList().remove(telefonoListTelefono);
                    oldIdClienteOfTelefonoListTelefono = em.merge(oldIdClienteOfTelefonoListTelefono);
                }
            }
            for (ClientePreguntas clientePreguntasListClientePreguntas : cliente.getClientePreguntasList()) {
                Cliente oldIdClienteOfClientePreguntasListClientePreguntas = clientePreguntasListClientePreguntas.getIdCliente();
                clientePreguntasListClientePreguntas.setIdCliente(cliente);
                clientePreguntasListClientePreguntas = em.merge(clientePreguntasListClientePreguntas);
                if (oldIdClienteOfClientePreguntasListClientePreguntas != null) {
                    oldIdClienteOfClientePreguntasListClientePreguntas.getClientePreguntasList().remove(clientePreguntasListClientePreguntas);
                    oldIdClienteOfClientePreguntasListClientePreguntas = em.merge(oldIdClienteOfClientePreguntasListClientePreguntas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            Persona personaIdOld = persistentCliente.getPersonaId();
            Persona personaIdNew = cliente.getPersonaId();
            Empresa empresaIdOld = persistentCliente.getEmpresaId();
            Empresa empresaIdNew = cliente.getEmpresaId();
            Ejecutivo ejecutivoIdOld = persistentCliente.getEjecutivoId();
            Ejecutivo ejecutivoIdNew = cliente.getEjecutivoId();
            List<ResumenAnualCliente> resumenAnualClienteListOld = persistentCliente.getResumenAnualClienteList();
            List<ResumenAnualCliente> resumenAnualClienteListNew = cliente.getResumenAnualClienteList();
            List<TotalServicios> totalServiciosListOld = persistentCliente.getTotalServiciosList();
            List<TotalServicios> totalServiciosListNew = cliente.getTotalServiciosList();
            List<Hitos> hitosListOld = persistentCliente.getHitosList();
            List<Hitos> hitosListNew = cliente.getHitosList();
            List<Telefono> telefonoListOld = persistentCliente.getTelefonoList();
            List<Telefono> telefonoListNew = cliente.getTelefonoList();
            List<ClientePreguntas> clientePreguntasListOld = persistentCliente.getClientePreguntasList();
            List<ClientePreguntas> clientePreguntasListNew = cliente.getClientePreguntasList();
            List<String> illegalOrphanMessages = null;
            for (Hitos hitosListOldHitos : hitosListOld) {
                if (!hitosListNew.contains(hitosListOldHitos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Hitos " + hitosListOldHitos + " since its clienteId field is not nullable.");
                }
            }
            for (Telefono telefonoListOldTelefono : telefonoListOld) {
                if (!telefonoListNew.contains(telefonoListOldTelefono)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Telefono " + telefonoListOldTelefono + " since its idCliente field is not nullable.");
                }
            }
            for (ClientePreguntas clientePreguntasListOldClientePreguntas : clientePreguntasListOld) {
                if (!clientePreguntasListNew.contains(clientePreguntasListOldClientePreguntas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClientePreguntas " + clientePreguntasListOldClientePreguntas + " since its idCliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (personaIdNew != null) {
                personaIdNew = em.getReference(personaIdNew.getClass(), personaIdNew.getId());
                cliente.setPersonaId(personaIdNew);
            }
            if (empresaIdNew != null) {
                empresaIdNew = em.getReference(empresaIdNew.getClass(), empresaIdNew.getId());
                cliente.setEmpresaId(empresaIdNew);
            }
            if (ejecutivoIdNew != null) {
                ejecutivoIdNew = em.getReference(ejecutivoIdNew.getClass(), ejecutivoIdNew.getId());
                cliente.setEjecutivoId(ejecutivoIdNew);
            }
            List<ResumenAnualCliente> attachedResumenAnualClienteListNew = new ArrayList<ResumenAnualCliente>();
            for (ResumenAnualCliente resumenAnualClienteListNewResumenAnualClienteToAttach : resumenAnualClienteListNew) {
                resumenAnualClienteListNewResumenAnualClienteToAttach = em.getReference(resumenAnualClienteListNewResumenAnualClienteToAttach.getClass(), resumenAnualClienteListNewResumenAnualClienteToAttach.getId());
                attachedResumenAnualClienteListNew.add(resumenAnualClienteListNewResumenAnualClienteToAttach);
            }
            resumenAnualClienteListNew = attachedResumenAnualClienteListNew;
            cliente.setResumenAnualClienteList(resumenAnualClienteListNew);
            List<TotalServicios> attachedTotalServiciosListNew = new ArrayList<TotalServicios>();
            for (TotalServicios totalServiciosListNewTotalServiciosToAttach : totalServiciosListNew) {
                totalServiciosListNewTotalServiciosToAttach = em.getReference(totalServiciosListNewTotalServiciosToAttach.getClass(), totalServiciosListNewTotalServiciosToAttach.getId());
                attachedTotalServiciosListNew.add(totalServiciosListNewTotalServiciosToAttach);
            }
            totalServiciosListNew = attachedTotalServiciosListNew;
            cliente.setTotalServiciosList(totalServiciosListNew);
            List<Hitos> attachedHitosListNew = new ArrayList<Hitos>();
            for (Hitos hitosListNewHitosToAttach : hitosListNew) {
                hitosListNewHitosToAttach = em.getReference(hitosListNewHitosToAttach.getClass(), hitosListNewHitosToAttach.getId());
                attachedHitosListNew.add(hitosListNewHitosToAttach);
            }
            hitosListNew = attachedHitosListNew;
            cliente.setHitosList(hitosListNew);
            List<Telefono> attachedTelefonoListNew = new ArrayList<Telefono>();
            for (Telefono telefonoListNewTelefonoToAttach : telefonoListNew) {
                telefonoListNewTelefonoToAttach = em.getReference(telefonoListNewTelefonoToAttach.getClass(), telefonoListNewTelefonoToAttach.getId());
                attachedTelefonoListNew.add(telefonoListNewTelefonoToAttach);
            }
            telefonoListNew = attachedTelefonoListNew;
            cliente.setTelefonoList(telefonoListNew);
            List<ClientePreguntas> attachedClientePreguntasListNew = new ArrayList<ClientePreguntas>();
            for (ClientePreguntas clientePreguntasListNewClientePreguntasToAttach : clientePreguntasListNew) {
                clientePreguntasListNewClientePreguntasToAttach = em.getReference(clientePreguntasListNewClientePreguntasToAttach.getClass(), clientePreguntasListNewClientePreguntasToAttach.getId());
                attachedClientePreguntasListNew.add(clientePreguntasListNewClientePreguntasToAttach);
            }
            clientePreguntasListNew = attachedClientePreguntasListNew;
            cliente.setClientePreguntasList(clientePreguntasListNew);
            cliente = em.merge(cliente);
            if (personaIdOld != null && !personaIdOld.equals(personaIdNew)) {
                personaIdOld.getClienteList().remove(cliente);
                personaIdOld = em.merge(personaIdOld);
            }
            if (personaIdNew != null && !personaIdNew.equals(personaIdOld)) {
                personaIdNew.getClienteList().add(cliente);
                personaIdNew = em.merge(personaIdNew);
            }
            if (empresaIdOld != null && !empresaIdOld.equals(empresaIdNew)) {
                empresaIdOld.getClienteList().remove(cliente);
                empresaIdOld = em.merge(empresaIdOld);
            }
            if (empresaIdNew != null && !empresaIdNew.equals(empresaIdOld)) {
                empresaIdNew.getClienteList().add(cliente);
                empresaIdNew = em.merge(empresaIdNew);
            }
            if (ejecutivoIdOld != null && !ejecutivoIdOld.equals(ejecutivoIdNew)) {
                ejecutivoIdOld.getClienteList().remove(cliente);
                ejecutivoIdOld = em.merge(ejecutivoIdOld);
            }
            if (ejecutivoIdNew != null && !ejecutivoIdNew.equals(ejecutivoIdOld)) {
                ejecutivoIdNew.getClienteList().add(cliente);
                ejecutivoIdNew = em.merge(ejecutivoIdNew);
            }
            for (ResumenAnualCliente resumenAnualClienteListOldResumenAnualCliente : resumenAnualClienteListOld) {
                if (!resumenAnualClienteListNew.contains(resumenAnualClienteListOldResumenAnualCliente)) {
                    resumenAnualClienteListOldResumenAnualCliente.setIdCliente(null);
                    resumenAnualClienteListOldResumenAnualCliente = em.merge(resumenAnualClienteListOldResumenAnualCliente);
                }
            }
            for (ResumenAnualCliente resumenAnualClienteListNewResumenAnualCliente : resumenAnualClienteListNew) {
                if (!resumenAnualClienteListOld.contains(resumenAnualClienteListNewResumenAnualCliente)) {
                    Cliente oldIdClienteOfResumenAnualClienteListNewResumenAnualCliente = resumenAnualClienteListNewResumenAnualCliente.getIdCliente();
                    resumenAnualClienteListNewResumenAnualCliente.setIdCliente(cliente);
                    resumenAnualClienteListNewResumenAnualCliente = em.merge(resumenAnualClienteListNewResumenAnualCliente);
                    if (oldIdClienteOfResumenAnualClienteListNewResumenAnualCliente != null && !oldIdClienteOfResumenAnualClienteListNewResumenAnualCliente.equals(cliente)) {
                        oldIdClienteOfResumenAnualClienteListNewResumenAnualCliente.getResumenAnualClienteList().remove(resumenAnualClienteListNewResumenAnualCliente);
                        oldIdClienteOfResumenAnualClienteListNewResumenAnualCliente = em.merge(oldIdClienteOfResumenAnualClienteListNewResumenAnualCliente);
                    }
                }
            }
            for (TotalServicios totalServiciosListOldTotalServicios : totalServiciosListOld) {
                if (!totalServiciosListNew.contains(totalServiciosListOldTotalServicios)) {
                    totalServiciosListOldTotalServicios.setIdCliente(null);
                    totalServiciosListOldTotalServicios = em.merge(totalServiciosListOldTotalServicios);
                }
            }
            for (TotalServicios totalServiciosListNewTotalServicios : totalServiciosListNew) {
                if (!totalServiciosListOld.contains(totalServiciosListNewTotalServicios)) {
                    Cliente oldIdClienteOfTotalServiciosListNewTotalServicios = totalServiciosListNewTotalServicios.getIdCliente();
                    totalServiciosListNewTotalServicios.setIdCliente(cliente);
                    totalServiciosListNewTotalServicios = em.merge(totalServiciosListNewTotalServicios);
                    if (oldIdClienteOfTotalServiciosListNewTotalServicios != null && !oldIdClienteOfTotalServiciosListNewTotalServicios.equals(cliente)) {
                        oldIdClienteOfTotalServiciosListNewTotalServicios.getTotalServiciosList().remove(totalServiciosListNewTotalServicios);
                        oldIdClienteOfTotalServiciosListNewTotalServicios = em.merge(oldIdClienteOfTotalServiciosListNewTotalServicios);
                    }
                }
            }
            for (Hitos hitosListNewHitos : hitosListNew) {
                if (!hitosListOld.contains(hitosListNewHitos)) {
                    Cliente oldClienteIdOfHitosListNewHitos = hitosListNewHitos.getClienteId();
                    hitosListNewHitos.setClienteId(cliente);
                    hitosListNewHitos = em.merge(hitosListNewHitos);
                    if (oldClienteIdOfHitosListNewHitos != null && !oldClienteIdOfHitosListNewHitos.equals(cliente)) {
                        oldClienteIdOfHitosListNewHitos.getHitosList().remove(hitosListNewHitos);
                        oldClienteIdOfHitosListNewHitos = em.merge(oldClienteIdOfHitosListNewHitos);
                    }
                }
            }
            for (Telefono telefonoListNewTelefono : telefonoListNew) {
                if (!telefonoListOld.contains(telefonoListNewTelefono)) {
                    Cliente oldIdClienteOfTelefonoListNewTelefono = telefonoListNewTelefono.getIdCliente();
                    telefonoListNewTelefono.setIdCliente(cliente);
                    telefonoListNewTelefono = em.merge(telefonoListNewTelefono);
                    if (oldIdClienteOfTelefonoListNewTelefono != null && !oldIdClienteOfTelefonoListNewTelefono.equals(cliente)) {
                        oldIdClienteOfTelefonoListNewTelefono.getTelefonoList().remove(telefonoListNewTelefono);
                        oldIdClienteOfTelefonoListNewTelefono = em.merge(oldIdClienteOfTelefonoListNewTelefono);
                    }
                }
            }
            for (ClientePreguntas clientePreguntasListNewClientePreguntas : clientePreguntasListNew) {
                if (!clientePreguntasListOld.contains(clientePreguntasListNewClientePreguntas)) {
                    Cliente oldIdClienteOfClientePreguntasListNewClientePreguntas = clientePreguntasListNewClientePreguntas.getIdCliente();
                    clientePreguntasListNewClientePreguntas.setIdCliente(cliente);
                    clientePreguntasListNewClientePreguntas = em.merge(clientePreguntasListNewClientePreguntas);
                    if (oldIdClienteOfClientePreguntasListNewClientePreguntas != null && !oldIdClienteOfClientePreguntasListNewClientePreguntas.equals(cliente)) {
                        oldIdClienteOfClientePreguntasListNewClientePreguntas.getClientePreguntasList().remove(clientePreguntasListNewClientePreguntas);
                        oldIdClienteOfClientePreguntasListNewClientePreguntas = em.merge(oldIdClienteOfClientePreguntasListNewClientePreguntas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Hitos> hitosListOrphanCheck = cliente.getHitosList();
            for (Hitos hitosListOrphanCheckHitos : hitosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Hitos " + hitosListOrphanCheckHitos + " in its hitosList field has a non-nullable clienteId field.");
            }
            List<Telefono> telefonoListOrphanCheck = cliente.getTelefonoList();
            for (Telefono telefonoListOrphanCheckTelefono : telefonoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Telefono " + telefonoListOrphanCheckTelefono + " in its telefonoList field has a non-nullable idCliente field.");
            }
            List<ClientePreguntas> clientePreguntasListOrphanCheck = cliente.getClientePreguntasList();
            for (ClientePreguntas clientePreguntasListOrphanCheckClientePreguntas : clientePreguntasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the ClientePreguntas " + clientePreguntasListOrphanCheckClientePreguntas + " in its clientePreguntasList field has a non-nullable idCliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Persona personaId = cliente.getPersonaId();
            if (personaId != null) {
                personaId.getClienteList().remove(cliente);
                personaId = em.merge(personaId);
            }
            Empresa empresaId = cliente.getEmpresaId();
            if (empresaId != null) {
                empresaId.getClienteList().remove(cliente);
                empresaId = em.merge(empresaId);
            }
            Ejecutivo ejecutivoId = cliente.getEjecutivoId();
            if (ejecutivoId != null) {
                ejecutivoId.getClienteList().remove(cliente);
                ejecutivoId = em.merge(ejecutivoId);
            }
            List<ResumenAnualCliente> resumenAnualClienteList = cliente.getResumenAnualClienteList();
            for (ResumenAnualCliente resumenAnualClienteListResumenAnualCliente : resumenAnualClienteList) {
                resumenAnualClienteListResumenAnualCliente.setIdCliente(null);
                resumenAnualClienteListResumenAnualCliente = em.merge(resumenAnualClienteListResumenAnualCliente);
            }
            List<TotalServicios> totalServiciosList = cliente.getTotalServiciosList();
            for (TotalServicios totalServiciosListTotalServicios : totalServiciosList) {
                totalServiciosListTotalServicios.setIdCliente(null);
                totalServiciosListTotalServicios = em.merge(totalServiciosListTotalServicios);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
