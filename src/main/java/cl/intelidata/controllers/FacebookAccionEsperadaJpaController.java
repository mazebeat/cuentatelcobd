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
import cl.intelidata.jpa.FacebookAccionEsperada;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.intelidata.jpa.Usuarios;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dev-DFeliu
 */
public class FacebookAccionEsperadaJpaController implements Serializable {

    public FacebookAccionEsperadaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FacebookAccionEsperada facebookAccionEsperada) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios idUsuario = facebookAccionEsperada.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getId());
                facebookAccionEsperada.setIdUsuario(idUsuario);
            }
            em.persist(facebookAccionEsperada);
            if (idUsuario != null) {
                idUsuario.getFacebookAccionEsperadaList().add(facebookAccionEsperada);
                idUsuario = em.merge(idUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FacebookAccionEsperada facebookAccionEsperada) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacebookAccionEsperada persistentFacebookAccionEsperada = em.find(FacebookAccionEsperada.class, facebookAccionEsperada.getId());
            Usuarios idUsuarioOld = persistentFacebookAccionEsperada.getIdUsuario();
            Usuarios idUsuarioNew = facebookAccionEsperada.getIdUsuario();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getId());
                facebookAccionEsperada.setIdUsuario(idUsuarioNew);
            }
            facebookAccionEsperada = em.merge(facebookAccionEsperada);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getFacebookAccionEsperadaList().remove(facebookAccionEsperada);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getFacebookAccionEsperadaList().add(facebookAccionEsperada);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facebookAccionEsperada.getId();
                if (findFacebookAccionEsperada(id) == null) {
                    throw new NonexistentEntityException("The facebookAccionEsperada with id " + id + " no longer exists.");
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
            FacebookAccionEsperada facebookAccionEsperada;
            try {
                facebookAccionEsperada = em.getReference(FacebookAccionEsperada.class, id);
                facebookAccionEsperada.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facebookAccionEsperada with id " + id + " no longer exists.", enfe);
            }
            Usuarios idUsuario = facebookAccionEsperada.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getFacebookAccionEsperadaList().remove(facebookAccionEsperada);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(facebookAccionEsperada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FacebookAccionEsperada> findFacebookAccionEsperadaEntities() {
        return findFacebookAccionEsperadaEntities(true, -1, -1);
    }

    public List<FacebookAccionEsperada> findFacebookAccionEsperadaEntities(int maxResults, int firstResult) {
        return findFacebookAccionEsperadaEntities(false, maxResults, firstResult);
    }

    private List<FacebookAccionEsperada> findFacebookAccionEsperadaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FacebookAccionEsperada.class));
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

    public FacebookAccionEsperada findFacebookAccionEsperada(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FacebookAccionEsperada.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacebookAccionEsperadaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FacebookAccionEsperada> rt = cq.from(FacebookAccionEsperada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
