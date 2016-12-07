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
import cl.intelidata.jpa.PreguntaRespuesta;
import cl.intelidata.jpa.Respuestas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dev-DFeliu
 */
public class RespuestasJpaController implements Serializable {

    public RespuestasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Respuestas respuestas) {
        if (respuestas.getPreguntaRespuestaList() == null) {
            respuestas.setPreguntaRespuestaList(new ArrayList<PreguntaRespuesta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PreguntaRespuesta> attachedPreguntaRespuestaList = new ArrayList<PreguntaRespuesta>();
            for (PreguntaRespuesta preguntaRespuestaListPreguntaRespuestaToAttach : respuestas.getPreguntaRespuestaList()) {
                preguntaRespuestaListPreguntaRespuestaToAttach = em.getReference(preguntaRespuestaListPreguntaRespuestaToAttach.getClass(), preguntaRespuestaListPreguntaRespuestaToAttach.getId());
                attachedPreguntaRespuestaList.add(preguntaRespuestaListPreguntaRespuestaToAttach);
            }
            respuestas.setPreguntaRespuestaList(attachedPreguntaRespuestaList);
            em.persist(respuestas);
            for (PreguntaRespuesta preguntaRespuestaListPreguntaRespuesta : respuestas.getPreguntaRespuestaList()) {
                Respuestas oldIdRespuestaOfPreguntaRespuestaListPreguntaRespuesta = preguntaRespuestaListPreguntaRespuesta.getIdRespuesta();
                preguntaRespuestaListPreguntaRespuesta.setIdRespuesta(respuestas);
                preguntaRespuestaListPreguntaRespuesta = em.merge(preguntaRespuestaListPreguntaRespuesta);
                if (oldIdRespuestaOfPreguntaRespuestaListPreguntaRespuesta != null) {
                    oldIdRespuestaOfPreguntaRespuestaListPreguntaRespuesta.getPreguntaRespuestaList().remove(preguntaRespuestaListPreguntaRespuesta);
                    oldIdRespuestaOfPreguntaRespuestaListPreguntaRespuesta = em.merge(oldIdRespuestaOfPreguntaRespuestaListPreguntaRespuesta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Respuestas respuestas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Respuestas persistentRespuestas = em.find(Respuestas.class, respuestas.getId());
            List<PreguntaRespuesta> preguntaRespuestaListOld = persistentRespuestas.getPreguntaRespuestaList();
            List<PreguntaRespuesta> preguntaRespuestaListNew = respuestas.getPreguntaRespuestaList();
            List<String> illegalOrphanMessages = null;
            for (PreguntaRespuesta preguntaRespuestaListOldPreguntaRespuesta : preguntaRespuestaListOld) {
                if (!preguntaRespuestaListNew.contains(preguntaRespuestaListOldPreguntaRespuesta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PreguntaRespuesta " + preguntaRespuestaListOldPreguntaRespuesta + " since its idRespuesta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PreguntaRespuesta> attachedPreguntaRespuestaListNew = new ArrayList<PreguntaRespuesta>();
            for (PreguntaRespuesta preguntaRespuestaListNewPreguntaRespuestaToAttach : preguntaRespuestaListNew) {
                preguntaRespuestaListNewPreguntaRespuestaToAttach = em.getReference(preguntaRespuestaListNewPreguntaRespuestaToAttach.getClass(), preguntaRespuestaListNewPreguntaRespuestaToAttach.getId());
                attachedPreguntaRespuestaListNew.add(preguntaRespuestaListNewPreguntaRespuestaToAttach);
            }
            preguntaRespuestaListNew = attachedPreguntaRespuestaListNew;
            respuestas.setPreguntaRespuestaList(preguntaRespuestaListNew);
            respuestas = em.merge(respuestas);
            for (PreguntaRespuesta preguntaRespuestaListNewPreguntaRespuesta : preguntaRespuestaListNew) {
                if (!preguntaRespuestaListOld.contains(preguntaRespuestaListNewPreguntaRespuesta)) {
                    Respuestas oldIdRespuestaOfPreguntaRespuestaListNewPreguntaRespuesta = preguntaRespuestaListNewPreguntaRespuesta.getIdRespuesta();
                    preguntaRespuestaListNewPreguntaRespuesta.setIdRespuesta(respuestas);
                    preguntaRespuestaListNewPreguntaRespuesta = em.merge(preguntaRespuestaListNewPreguntaRespuesta);
                    if (oldIdRespuestaOfPreguntaRespuestaListNewPreguntaRespuesta != null && !oldIdRespuestaOfPreguntaRespuestaListNewPreguntaRespuesta.equals(respuestas)) {
                        oldIdRespuestaOfPreguntaRespuestaListNewPreguntaRespuesta.getPreguntaRespuestaList().remove(preguntaRespuestaListNewPreguntaRespuesta);
                        oldIdRespuestaOfPreguntaRespuestaListNewPreguntaRespuesta = em.merge(oldIdRespuestaOfPreguntaRespuestaListNewPreguntaRespuesta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = respuestas.getId();
                if (findRespuestas(id) == null) {
                    throw new NonexistentEntityException("The respuestas with id " + id + " no longer exists.");
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
            Respuestas respuestas;
            try {
                respuestas = em.getReference(Respuestas.class, id);
                respuestas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The respuestas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PreguntaRespuesta> preguntaRespuestaListOrphanCheck = respuestas.getPreguntaRespuestaList();
            for (PreguntaRespuesta preguntaRespuestaListOrphanCheckPreguntaRespuesta : preguntaRespuestaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Respuestas (" + respuestas + ") cannot be destroyed since the PreguntaRespuesta " + preguntaRespuestaListOrphanCheckPreguntaRespuesta + " in its preguntaRespuestaList field has a non-nullable idRespuesta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(respuestas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Respuestas> findRespuestasEntities() {
        return findRespuestasEntities(true, -1, -1);
    }

    public List<Respuestas> findRespuestasEntities(int maxResults, int firstResult) {
        return findRespuestasEntities(false, maxResults, firstResult);
    }

    private List<Respuestas> findRespuestasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Respuestas.class));
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

    public Respuestas findRespuestas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Respuestas.class, id);
        } finally {
            em.close();
        }
    }

    public int getRespuestasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Respuestas> rt = cq.from(Respuestas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
