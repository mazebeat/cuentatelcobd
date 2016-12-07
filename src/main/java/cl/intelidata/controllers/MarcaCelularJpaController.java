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
import cl.intelidata.jpa.MarcaCelular;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cl.intelidata.jpa.Modelo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dev-DFeliu
 */
public class MarcaCelularJpaController implements Serializable {

    public MarcaCelularJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MarcaCelular marcaCelular) {
        if (marcaCelular.getModeloList() == null) {
            marcaCelular.setModeloList(new ArrayList<Modelo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Modelo> attachedModeloList = new ArrayList<Modelo>();
            for (Modelo modeloListModeloToAttach : marcaCelular.getModeloList()) {
                modeloListModeloToAttach = em.getReference(modeloListModeloToAttach.getClass(), modeloListModeloToAttach.getId());
                attachedModeloList.add(modeloListModeloToAttach);
            }
            marcaCelular.setModeloList(attachedModeloList);
            em.persist(marcaCelular);
            for (Modelo modeloListModelo : marcaCelular.getModeloList()) {
                MarcaCelular oldIdMarcaOfModeloListModelo = modeloListModelo.getIdMarca();
                modeloListModelo.setIdMarca(marcaCelular);
                modeloListModelo = em.merge(modeloListModelo);
                if (oldIdMarcaOfModeloListModelo != null) {
                    oldIdMarcaOfModeloListModelo.getModeloList().remove(modeloListModelo);
                    oldIdMarcaOfModeloListModelo = em.merge(oldIdMarcaOfModeloListModelo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MarcaCelular marcaCelular) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MarcaCelular persistentMarcaCelular = em.find(MarcaCelular.class, marcaCelular.getId());
            List<Modelo> modeloListOld = persistentMarcaCelular.getModeloList();
            List<Modelo> modeloListNew = marcaCelular.getModeloList();
            List<Modelo> attachedModeloListNew = new ArrayList<Modelo>();
            for (Modelo modeloListNewModeloToAttach : modeloListNew) {
                modeloListNewModeloToAttach = em.getReference(modeloListNewModeloToAttach.getClass(), modeloListNewModeloToAttach.getId());
                attachedModeloListNew.add(modeloListNewModeloToAttach);
            }
            modeloListNew = attachedModeloListNew;
            marcaCelular.setModeloList(modeloListNew);
            marcaCelular = em.merge(marcaCelular);
            for (Modelo modeloListOldModelo : modeloListOld) {
                if (!modeloListNew.contains(modeloListOldModelo)) {
                    modeloListOldModelo.setIdMarca(null);
                    modeloListOldModelo = em.merge(modeloListOldModelo);
                }
            }
            for (Modelo modeloListNewModelo : modeloListNew) {
                if (!modeloListOld.contains(modeloListNewModelo)) {
                    MarcaCelular oldIdMarcaOfModeloListNewModelo = modeloListNewModelo.getIdMarca();
                    modeloListNewModelo.setIdMarca(marcaCelular);
                    modeloListNewModelo = em.merge(modeloListNewModelo);
                    if (oldIdMarcaOfModeloListNewModelo != null && !oldIdMarcaOfModeloListNewModelo.equals(marcaCelular)) {
                        oldIdMarcaOfModeloListNewModelo.getModeloList().remove(modeloListNewModelo);
                        oldIdMarcaOfModeloListNewModelo = em.merge(oldIdMarcaOfModeloListNewModelo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = marcaCelular.getId();
                if (findMarcaCelular(id) == null) {
                    throw new NonexistentEntityException("The marcaCelular with id " + id + " no longer exists.");
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
            MarcaCelular marcaCelular;
            try {
                marcaCelular = em.getReference(MarcaCelular.class, id);
                marcaCelular.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The marcaCelular with id " + id + " no longer exists.", enfe);
            }
            List<Modelo> modeloList = marcaCelular.getModeloList();
            for (Modelo modeloListModelo : modeloList) {
                modeloListModelo.setIdMarca(null);
                modeloListModelo = em.merge(modeloListModelo);
            }
            em.remove(marcaCelular);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MarcaCelular> findMarcaCelularEntities() {
        return findMarcaCelularEntities(true, -1, -1);
    }

    public List<MarcaCelular> findMarcaCelularEntities(int maxResults, int firstResult) {
        return findMarcaCelularEntities(false, maxResults, firstResult);
    }

    private List<MarcaCelular> findMarcaCelularEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MarcaCelular.class));
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

    public MarcaCelular findMarcaCelular(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MarcaCelular.class, id);
        } finally {
            em.close();
        }
    }

    public int getMarcaCelularCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MarcaCelular> rt = cq.from(MarcaCelular.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
