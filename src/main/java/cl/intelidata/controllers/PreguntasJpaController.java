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
import cl.intelidata.jpa.Preguntas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dev-DFeliu
 */
public class PreguntasJpaController implements Serializable {

    public PreguntasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Preguntas preguntas) {
        if (preguntas.getPreguntaRespuestaList() == null) {
            preguntas.setPreguntaRespuestaList(new ArrayList<PreguntaRespuesta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PreguntaRespuesta> attachedPreguntaRespuestaList = new ArrayList<PreguntaRespuesta>();
            for (PreguntaRespuesta preguntaRespuestaListPreguntaRespuestaToAttach : preguntas.getPreguntaRespuestaList()) {
                preguntaRespuestaListPreguntaRespuestaToAttach = em.getReference(preguntaRespuestaListPreguntaRespuestaToAttach.getClass(), preguntaRespuestaListPreguntaRespuestaToAttach.getId());
                attachedPreguntaRespuestaList.add(preguntaRespuestaListPreguntaRespuestaToAttach);
            }
            preguntas.setPreguntaRespuestaList(attachedPreguntaRespuestaList);
            em.persist(preguntas);
            for (PreguntaRespuesta preguntaRespuestaListPreguntaRespuesta : preguntas.getPreguntaRespuestaList()) {
                Preguntas oldIdPreguntaOfPreguntaRespuestaListPreguntaRespuesta = preguntaRespuestaListPreguntaRespuesta.getIdPregunta();
                preguntaRespuestaListPreguntaRespuesta.setIdPregunta(preguntas);
                preguntaRespuestaListPreguntaRespuesta = em.merge(preguntaRespuestaListPreguntaRespuesta);
                if (oldIdPreguntaOfPreguntaRespuestaListPreguntaRespuesta != null) {
                    oldIdPreguntaOfPreguntaRespuestaListPreguntaRespuesta.getPreguntaRespuestaList().remove(preguntaRespuestaListPreguntaRespuesta);
                    oldIdPreguntaOfPreguntaRespuestaListPreguntaRespuesta = em.merge(oldIdPreguntaOfPreguntaRespuestaListPreguntaRespuesta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Preguntas preguntas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Preguntas persistentPreguntas = em.find(Preguntas.class, preguntas.getId());
            List<PreguntaRespuesta> preguntaRespuestaListOld = persistentPreguntas.getPreguntaRespuestaList();
            List<PreguntaRespuesta> preguntaRespuestaListNew = preguntas.getPreguntaRespuestaList();
            List<String> illegalOrphanMessages = null;
            for (PreguntaRespuesta preguntaRespuestaListOldPreguntaRespuesta : preguntaRespuestaListOld) {
                if (!preguntaRespuestaListNew.contains(preguntaRespuestaListOldPreguntaRespuesta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PreguntaRespuesta " + preguntaRespuestaListOldPreguntaRespuesta + " since its idPregunta field is not nullable.");
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
            preguntas.setPreguntaRespuestaList(preguntaRespuestaListNew);
            preguntas = em.merge(preguntas);
            for (PreguntaRespuesta preguntaRespuestaListNewPreguntaRespuesta : preguntaRespuestaListNew) {
                if (!preguntaRespuestaListOld.contains(preguntaRespuestaListNewPreguntaRespuesta)) {
                    Preguntas oldIdPreguntaOfPreguntaRespuestaListNewPreguntaRespuesta = preguntaRespuestaListNewPreguntaRespuesta.getIdPregunta();
                    preguntaRespuestaListNewPreguntaRespuesta.setIdPregunta(preguntas);
                    preguntaRespuestaListNewPreguntaRespuesta = em.merge(preguntaRespuestaListNewPreguntaRespuesta);
                    if (oldIdPreguntaOfPreguntaRespuestaListNewPreguntaRespuesta != null && !oldIdPreguntaOfPreguntaRespuestaListNewPreguntaRespuesta.equals(preguntas)) {
                        oldIdPreguntaOfPreguntaRespuestaListNewPreguntaRespuesta.getPreguntaRespuestaList().remove(preguntaRespuestaListNewPreguntaRespuesta);
                        oldIdPreguntaOfPreguntaRespuestaListNewPreguntaRespuesta = em.merge(oldIdPreguntaOfPreguntaRespuestaListNewPreguntaRespuesta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = preguntas.getId();
                if (findPreguntas(id) == null) {
                    throw new NonexistentEntityException("The preguntas with id " + id + " no longer exists.");
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
            Preguntas preguntas;
            try {
                preguntas = em.getReference(Preguntas.class, id);
                preguntas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preguntas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PreguntaRespuesta> preguntaRespuestaListOrphanCheck = preguntas.getPreguntaRespuestaList();
            for (PreguntaRespuesta preguntaRespuestaListOrphanCheckPreguntaRespuesta : preguntaRespuestaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Preguntas (" + preguntas + ") cannot be destroyed since the PreguntaRespuesta " + preguntaRespuestaListOrphanCheckPreguntaRespuesta + " in its preguntaRespuestaList field has a non-nullable idPregunta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(preguntas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Preguntas> findPreguntasEntities() {
        return findPreguntasEntities(true, -1, -1);
    }

    public List<Preguntas> findPreguntasEntities(int maxResults, int firstResult) {
        return findPreguntasEntities(false, maxResults, firstResult);
    }

    private List<Preguntas> findPreguntasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Preguntas.class));
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

    public Preguntas findPreguntas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Preguntas.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreguntasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Preguntas> rt = cq.from(Preguntas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
