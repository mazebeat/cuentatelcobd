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
import cl.intelidata.jpa.FacebookUsuarioIntegracion;
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
public class FacebookUsuarioIntegracionJpaController implements Serializable {

    public FacebookUsuarioIntegracionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FacebookUsuarioIntegracion facebookUsuarioIntegracion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios idUsuario = facebookUsuarioIntegracion.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getId());
                facebookUsuarioIntegracion.setIdUsuario(idUsuario);
            }
            em.persist(facebookUsuarioIntegracion);
            if (idUsuario != null) {
                idUsuario.getFacebookUsuarioIntegracionList().add(facebookUsuarioIntegracion);
                idUsuario = em.merge(idUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FacebookUsuarioIntegracion facebookUsuarioIntegracion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacebookUsuarioIntegracion persistentFacebookUsuarioIntegracion = em.find(FacebookUsuarioIntegracion.class, facebookUsuarioIntegracion.getId());
            Usuarios idUsuarioOld = persistentFacebookUsuarioIntegracion.getIdUsuario();
            Usuarios idUsuarioNew = facebookUsuarioIntegracion.getIdUsuario();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getId());
                facebookUsuarioIntegracion.setIdUsuario(idUsuarioNew);
            }
            facebookUsuarioIntegracion = em.merge(facebookUsuarioIntegracion);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getFacebookUsuarioIntegracionList().remove(facebookUsuarioIntegracion);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getFacebookUsuarioIntegracionList().add(facebookUsuarioIntegracion);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facebookUsuarioIntegracion.getId();
                if (findFacebookUsuarioIntegracion(id) == null) {
                    throw new NonexistentEntityException("The facebookUsuarioIntegracion with id " + id + " no longer exists.");
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
            FacebookUsuarioIntegracion facebookUsuarioIntegracion;
            try {
                facebookUsuarioIntegracion = em.getReference(FacebookUsuarioIntegracion.class, id);
                facebookUsuarioIntegracion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facebookUsuarioIntegracion with id " + id + " no longer exists.", enfe);
            }
            Usuarios idUsuario = facebookUsuarioIntegracion.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getFacebookUsuarioIntegracionList().remove(facebookUsuarioIntegracion);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(facebookUsuarioIntegracion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FacebookUsuarioIntegracion> findFacebookUsuarioIntegracionEntities() {
        return findFacebookUsuarioIntegracionEntities(true, -1, -1);
    }

    public List<FacebookUsuarioIntegracion> findFacebookUsuarioIntegracionEntities(int maxResults, int firstResult) {
        return findFacebookUsuarioIntegracionEntities(false, maxResults, firstResult);
    }

    private List<FacebookUsuarioIntegracion> findFacebookUsuarioIntegracionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FacebookUsuarioIntegracion.class));
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

    public FacebookUsuarioIntegracion findFacebookUsuarioIntegracion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FacebookUsuarioIntegracion.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacebookUsuarioIntegracionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FacebookUsuarioIntegracion> rt = cq.from(FacebookUsuarioIntegracion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
