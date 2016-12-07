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
import cl.intelidata.jpa.Preguntas;
import cl.intelidata.jpa.Respuestas;
import cl.intelidata.jpa.ClientePreguntas;
import cl.intelidata.jpa.PreguntaRespuesta;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dev-DFeliu
 */
public class PreguntaRespuestaJpaController implements Serializable {

    public PreguntaRespuestaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PreguntaRespuesta preguntaRespuesta) {
        if (preguntaRespuesta.getClientePreguntasList() == null) {
            preguntaRespuesta.setClientePreguntasList(new ArrayList<ClientePreguntas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Preguntas idPregunta = preguntaRespuesta.getIdPregunta();
            if (idPregunta != null) {
                idPregunta = em.getReference(idPregunta.getClass(), idPregunta.getId());
                preguntaRespuesta.setIdPregunta(idPregunta);
            }
            Respuestas idRespuesta = preguntaRespuesta.getIdRespuesta();
            if (idRespuesta != null) {
                idRespuesta = em.getReference(idRespuesta.getClass(), idRespuesta.getId());
                preguntaRespuesta.setIdRespuesta(idRespuesta);
            }
            List<ClientePreguntas> attachedClientePreguntasList = new ArrayList<ClientePreguntas>();
            for (ClientePreguntas clientePreguntasListClientePreguntasToAttach : preguntaRespuesta.getClientePreguntasList()) {
                clientePreguntasListClientePreguntasToAttach = em.getReference(clientePreguntasListClientePreguntasToAttach.getClass(), clientePreguntasListClientePreguntasToAttach.getId());
                attachedClientePreguntasList.add(clientePreguntasListClientePreguntasToAttach);
            }
            preguntaRespuesta.setClientePreguntasList(attachedClientePreguntasList);
            em.persist(preguntaRespuesta);
            if (idPregunta != null) {
                idPregunta.getPreguntaRespuestaList().add(preguntaRespuesta);
                idPregunta = em.merge(idPregunta);
            }
            if (idRespuesta != null) {
                idRespuesta.getPreguntaRespuestaList().add(preguntaRespuesta);
                idRespuesta = em.merge(idRespuesta);
            }
            for (ClientePreguntas clientePreguntasListClientePreguntas : preguntaRespuesta.getClientePreguntasList()) {
                PreguntaRespuesta oldIdPreguntaRespuestaOfClientePreguntasListClientePreguntas = clientePreguntasListClientePreguntas.getIdPreguntaRespuesta();
                clientePreguntasListClientePreguntas.setIdPreguntaRespuesta(preguntaRespuesta);
                clientePreguntasListClientePreguntas = em.merge(clientePreguntasListClientePreguntas);
                if (oldIdPreguntaRespuestaOfClientePreguntasListClientePreguntas != null) {
                    oldIdPreguntaRespuestaOfClientePreguntasListClientePreguntas.getClientePreguntasList().remove(clientePreguntasListClientePreguntas);
                    oldIdPreguntaRespuestaOfClientePreguntasListClientePreguntas = em.merge(oldIdPreguntaRespuestaOfClientePreguntasListClientePreguntas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PreguntaRespuesta preguntaRespuesta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PreguntaRespuesta persistentPreguntaRespuesta = em.find(PreguntaRespuesta.class, preguntaRespuesta.getId());
            Preguntas idPreguntaOld = persistentPreguntaRespuesta.getIdPregunta();
            Preguntas idPreguntaNew = preguntaRespuesta.getIdPregunta();
            Respuestas idRespuestaOld = persistentPreguntaRespuesta.getIdRespuesta();
            Respuestas idRespuestaNew = preguntaRespuesta.getIdRespuesta();
            List<ClientePreguntas> clientePreguntasListOld = persistentPreguntaRespuesta.getClientePreguntasList();
            List<ClientePreguntas> clientePreguntasListNew = preguntaRespuesta.getClientePreguntasList();
            List<String> illegalOrphanMessages = null;
            for (ClientePreguntas clientePreguntasListOldClientePreguntas : clientePreguntasListOld) {
                if (!clientePreguntasListNew.contains(clientePreguntasListOldClientePreguntas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClientePreguntas " + clientePreguntasListOldClientePreguntas + " since its idPreguntaRespuesta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idPreguntaNew != null) {
                idPreguntaNew = em.getReference(idPreguntaNew.getClass(), idPreguntaNew.getId());
                preguntaRespuesta.setIdPregunta(idPreguntaNew);
            }
            if (idRespuestaNew != null) {
                idRespuestaNew = em.getReference(idRespuestaNew.getClass(), idRespuestaNew.getId());
                preguntaRespuesta.setIdRespuesta(idRespuestaNew);
            }
            List<ClientePreguntas> attachedClientePreguntasListNew = new ArrayList<ClientePreguntas>();
            for (ClientePreguntas clientePreguntasListNewClientePreguntasToAttach : clientePreguntasListNew) {
                clientePreguntasListNewClientePreguntasToAttach = em.getReference(clientePreguntasListNewClientePreguntasToAttach.getClass(), clientePreguntasListNewClientePreguntasToAttach.getId());
                attachedClientePreguntasListNew.add(clientePreguntasListNewClientePreguntasToAttach);
            }
            clientePreguntasListNew = attachedClientePreguntasListNew;
            preguntaRespuesta.setClientePreguntasList(clientePreguntasListNew);
            preguntaRespuesta = em.merge(preguntaRespuesta);
            if (idPreguntaOld != null && !idPreguntaOld.equals(idPreguntaNew)) {
                idPreguntaOld.getPreguntaRespuestaList().remove(preguntaRespuesta);
                idPreguntaOld = em.merge(idPreguntaOld);
            }
            if (idPreguntaNew != null && !idPreguntaNew.equals(idPreguntaOld)) {
                idPreguntaNew.getPreguntaRespuestaList().add(preguntaRespuesta);
                idPreguntaNew = em.merge(idPreguntaNew);
            }
            if (idRespuestaOld != null && !idRespuestaOld.equals(idRespuestaNew)) {
                idRespuestaOld.getPreguntaRespuestaList().remove(preguntaRespuesta);
                idRespuestaOld = em.merge(idRespuestaOld);
            }
            if (idRespuestaNew != null && !idRespuestaNew.equals(idRespuestaOld)) {
                idRespuestaNew.getPreguntaRespuestaList().add(preguntaRespuesta);
                idRespuestaNew = em.merge(idRespuestaNew);
            }
            for (ClientePreguntas clientePreguntasListNewClientePreguntas : clientePreguntasListNew) {
                if (!clientePreguntasListOld.contains(clientePreguntasListNewClientePreguntas)) {
                    PreguntaRespuesta oldIdPreguntaRespuestaOfClientePreguntasListNewClientePreguntas = clientePreguntasListNewClientePreguntas.getIdPreguntaRespuesta();
                    clientePreguntasListNewClientePreguntas.setIdPreguntaRespuesta(preguntaRespuesta);
                    clientePreguntasListNewClientePreguntas = em.merge(clientePreguntasListNewClientePreguntas);
                    if (oldIdPreguntaRespuestaOfClientePreguntasListNewClientePreguntas != null && !oldIdPreguntaRespuestaOfClientePreguntasListNewClientePreguntas.equals(preguntaRespuesta)) {
                        oldIdPreguntaRespuestaOfClientePreguntasListNewClientePreguntas.getClientePreguntasList().remove(clientePreguntasListNewClientePreguntas);
                        oldIdPreguntaRespuestaOfClientePreguntasListNewClientePreguntas = em.merge(oldIdPreguntaRespuestaOfClientePreguntasListNewClientePreguntas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = preguntaRespuesta.getId();
                if (findPreguntaRespuesta(id) == null) {
                    throw new NonexistentEntityException("The preguntaRespuesta with id " + id + " no longer exists.");
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
            PreguntaRespuesta preguntaRespuesta;
            try {
                preguntaRespuesta = em.getReference(PreguntaRespuesta.class, id);
                preguntaRespuesta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preguntaRespuesta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ClientePreguntas> clientePreguntasListOrphanCheck = preguntaRespuesta.getClientePreguntasList();
            for (ClientePreguntas clientePreguntasListOrphanCheckClientePreguntas : clientePreguntasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PreguntaRespuesta (" + preguntaRespuesta + ") cannot be destroyed since the ClientePreguntas " + clientePreguntasListOrphanCheckClientePreguntas + " in its clientePreguntasList field has a non-nullable idPreguntaRespuesta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Preguntas idPregunta = preguntaRespuesta.getIdPregunta();
            if (idPregunta != null) {
                idPregunta.getPreguntaRespuestaList().remove(preguntaRespuesta);
                idPregunta = em.merge(idPregunta);
            }
            Respuestas idRespuesta = preguntaRespuesta.getIdRespuesta();
            if (idRespuesta != null) {
                idRespuesta.getPreguntaRespuestaList().remove(preguntaRespuesta);
                idRespuesta = em.merge(idRespuesta);
            }
            em.remove(preguntaRespuesta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PreguntaRespuesta> findPreguntaRespuestaEntities() {
        return findPreguntaRespuestaEntities(true, -1, -1);
    }

    public List<PreguntaRespuesta> findPreguntaRespuestaEntities(int maxResults, int firstResult) {
        return findPreguntaRespuestaEntities(false, maxResults, firstResult);
    }

    private List<PreguntaRespuesta> findPreguntaRespuestaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PreguntaRespuesta.class));
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

    public PreguntaRespuesta findPreguntaRespuesta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PreguntaRespuesta.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreguntaRespuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PreguntaRespuesta> rt = cq.from(PreguntaRespuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
