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
import cl.intelidata.jpa.ClientePreguntas;
import cl.intelidata.jpa.PreguntaRespuesta;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dev-DFeliu
 */
public class ClientePreguntasJpaController implements Serializable {

    public ClientePreguntasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClientePreguntas clientePreguntas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente idCliente = clientePreguntas.getIdCliente();
            if (idCliente != null) {
                idCliente = em.getReference(idCliente.getClass(), idCliente.getId());
                clientePreguntas.setIdCliente(idCliente);
            }
            PreguntaRespuesta idPreguntaRespuesta = clientePreguntas.getIdPreguntaRespuesta();
            if (idPreguntaRespuesta != null) {
                idPreguntaRespuesta = em.getReference(idPreguntaRespuesta.getClass(), idPreguntaRespuesta.getId());
                clientePreguntas.setIdPreguntaRespuesta(idPreguntaRespuesta);
            }
            em.persist(clientePreguntas);
            if (idCliente != null) {
                idCliente.getClientePreguntasList().add(clientePreguntas);
                idCliente = em.merge(idCliente);
            }
            if (idPreguntaRespuesta != null) {
                idPreguntaRespuesta.getClientePreguntasList().add(clientePreguntas);
                idPreguntaRespuesta = em.merge(idPreguntaRespuesta);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClientePreguntas clientePreguntas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClientePreguntas persistentClientePreguntas = em.find(ClientePreguntas.class, clientePreguntas.getId());
            Cliente idClienteOld = persistentClientePreguntas.getIdCliente();
            Cliente idClienteNew = clientePreguntas.getIdCliente();
            PreguntaRespuesta idPreguntaRespuestaOld = persistentClientePreguntas.getIdPreguntaRespuesta();
            PreguntaRespuesta idPreguntaRespuestaNew = clientePreguntas.getIdPreguntaRespuesta();
            if (idClienteNew != null) {
                idClienteNew = em.getReference(idClienteNew.getClass(), idClienteNew.getId());
                clientePreguntas.setIdCliente(idClienteNew);
            }
            if (idPreguntaRespuestaNew != null) {
                idPreguntaRespuestaNew = em.getReference(idPreguntaRespuestaNew.getClass(), idPreguntaRespuestaNew.getId());
                clientePreguntas.setIdPreguntaRespuesta(idPreguntaRespuestaNew);
            }
            clientePreguntas = em.merge(clientePreguntas);
            if (idClienteOld != null && !idClienteOld.equals(idClienteNew)) {
                idClienteOld.getClientePreguntasList().remove(clientePreguntas);
                idClienteOld = em.merge(idClienteOld);
            }
            if (idClienteNew != null && !idClienteNew.equals(idClienteOld)) {
                idClienteNew.getClientePreguntasList().add(clientePreguntas);
                idClienteNew = em.merge(idClienteNew);
            }
            if (idPreguntaRespuestaOld != null && !idPreguntaRespuestaOld.equals(idPreguntaRespuestaNew)) {
                idPreguntaRespuestaOld.getClientePreguntasList().remove(clientePreguntas);
                idPreguntaRespuestaOld = em.merge(idPreguntaRespuestaOld);
            }
            if (idPreguntaRespuestaNew != null && !idPreguntaRespuestaNew.equals(idPreguntaRespuestaOld)) {
                idPreguntaRespuestaNew.getClientePreguntasList().add(clientePreguntas);
                idPreguntaRespuestaNew = em.merge(idPreguntaRespuestaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = clientePreguntas.getId();
                if (findClientePreguntas(id) == null) {
                    throw new NonexistentEntityException("The clientePreguntas with id " + id + " no longer exists.");
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
            ClientePreguntas clientePreguntas;
            try {
                clientePreguntas = em.getReference(ClientePreguntas.class, id);
                clientePreguntas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientePreguntas with id " + id + " no longer exists.", enfe);
            }
            Cliente idCliente = clientePreguntas.getIdCliente();
            if (idCliente != null) {
                idCliente.getClientePreguntasList().remove(clientePreguntas);
                idCliente = em.merge(idCliente);
            }
            PreguntaRespuesta idPreguntaRespuesta = clientePreguntas.getIdPreguntaRespuesta();
            if (idPreguntaRespuesta != null) {
                idPreguntaRespuesta.getClientePreguntasList().remove(clientePreguntas);
                idPreguntaRespuesta = em.merge(idPreguntaRespuesta);
            }
            em.remove(clientePreguntas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClientePreguntas> findClientePreguntasEntities() {
        return findClientePreguntasEntities(true, -1, -1);
    }

    public List<ClientePreguntas> findClientePreguntasEntities(int maxResults, int firstResult) {
        return findClientePreguntasEntities(false, maxResults, firstResult);
    }

    private List<ClientePreguntas> findClientePreguntasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClientePreguntas.class));
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

    public ClientePreguntas findClientePreguntas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClientePreguntas.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientePreguntasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClientePreguntas> rt = cq.from(ClientePreguntas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
