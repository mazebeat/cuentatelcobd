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
import cl.intelidata.jpa.Apikeyuser;
import java.util.ArrayList;
import java.util.List;
import cl.intelidata.jpa.FacebookAccionEsperada;
import cl.intelidata.jpa.FacebookUsuario;
import cl.intelidata.jpa.TelegramSugerencia;
import cl.intelidata.jpa.TelegramAccionEsperada;
import cl.intelidata.jpa.TelegramReclamo;
import cl.intelidata.jpa.TelegramUsuario;
import cl.intelidata.jpa.TelegramUsuarioIntegracion;
import cl.intelidata.jpa.FacebookUsuarioIntegracion;
import cl.intelidata.jpa.Usuarios;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Dev-DFeliu
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) {
        if (usuarios.getApikeyuserList() == null) {
            usuarios.setApikeyuserList(new ArrayList<Apikeyuser>());
        }
        if (usuarios.getFacebookAccionEsperadaList() == null) {
            usuarios.setFacebookAccionEsperadaList(new ArrayList<FacebookAccionEsperada>());
        }
        if (usuarios.getFacebookUsuarioList() == null) {
            usuarios.setFacebookUsuarioList(new ArrayList<FacebookUsuario>());
        }
        if (usuarios.getTelegramSugerenciaList() == null) {
            usuarios.setTelegramSugerenciaList(new ArrayList<TelegramSugerencia>());
        }
        if (usuarios.getTelegramAccionEsperadaList() == null) {
            usuarios.setTelegramAccionEsperadaList(new ArrayList<TelegramAccionEsperada>());
        }
        if (usuarios.getTelegramReclamoList() == null) {
            usuarios.setTelegramReclamoList(new ArrayList<TelegramReclamo>());
        }
        if (usuarios.getTelegramUsuarioList() == null) {
            usuarios.setTelegramUsuarioList(new ArrayList<TelegramUsuario>());
        }
        if (usuarios.getTelegramUsuarioIntegracionList() == null) {
            usuarios.setTelegramUsuarioIntegracionList(new ArrayList<TelegramUsuarioIntegracion>());
        }
        if (usuarios.getFacebookUsuarioIntegracionList() == null) {
            usuarios.setFacebookUsuarioIntegracionList(new ArrayList<FacebookUsuarioIntegracion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Apikeyuser> attachedApikeyuserList = new ArrayList<Apikeyuser>();
            for (Apikeyuser apikeyuserListApikeyuserToAttach : usuarios.getApikeyuserList()) {
                apikeyuserListApikeyuserToAttach = em.getReference(apikeyuserListApikeyuserToAttach.getClass(), apikeyuserListApikeyuserToAttach.getId());
                attachedApikeyuserList.add(apikeyuserListApikeyuserToAttach);
            }
            usuarios.setApikeyuserList(attachedApikeyuserList);
            List<FacebookAccionEsperada> attachedFacebookAccionEsperadaList = new ArrayList<FacebookAccionEsperada>();
            for (FacebookAccionEsperada facebookAccionEsperadaListFacebookAccionEsperadaToAttach : usuarios.getFacebookAccionEsperadaList()) {
                facebookAccionEsperadaListFacebookAccionEsperadaToAttach = em.getReference(facebookAccionEsperadaListFacebookAccionEsperadaToAttach.getClass(), facebookAccionEsperadaListFacebookAccionEsperadaToAttach.getId());
                attachedFacebookAccionEsperadaList.add(facebookAccionEsperadaListFacebookAccionEsperadaToAttach);
            }
            usuarios.setFacebookAccionEsperadaList(attachedFacebookAccionEsperadaList);
            List<FacebookUsuario> attachedFacebookUsuarioList = new ArrayList<FacebookUsuario>();
            for (FacebookUsuario facebookUsuarioListFacebookUsuarioToAttach : usuarios.getFacebookUsuarioList()) {
                facebookUsuarioListFacebookUsuarioToAttach = em.getReference(facebookUsuarioListFacebookUsuarioToAttach.getClass(), facebookUsuarioListFacebookUsuarioToAttach.getId());
                attachedFacebookUsuarioList.add(facebookUsuarioListFacebookUsuarioToAttach);
            }
            usuarios.setFacebookUsuarioList(attachedFacebookUsuarioList);
            List<TelegramSugerencia> attachedTelegramSugerenciaList = new ArrayList<TelegramSugerencia>();
            for (TelegramSugerencia telegramSugerenciaListTelegramSugerenciaToAttach : usuarios.getTelegramSugerenciaList()) {
                telegramSugerenciaListTelegramSugerenciaToAttach = em.getReference(telegramSugerenciaListTelegramSugerenciaToAttach.getClass(), telegramSugerenciaListTelegramSugerenciaToAttach.getId());
                attachedTelegramSugerenciaList.add(telegramSugerenciaListTelegramSugerenciaToAttach);
            }
            usuarios.setTelegramSugerenciaList(attachedTelegramSugerenciaList);
            List<TelegramAccionEsperada> attachedTelegramAccionEsperadaList = new ArrayList<TelegramAccionEsperada>();
            for (TelegramAccionEsperada telegramAccionEsperadaListTelegramAccionEsperadaToAttach : usuarios.getTelegramAccionEsperadaList()) {
                telegramAccionEsperadaListTelegramAccionEsperadaToAttach = em.getReference(telegramAccionEsperadaListTelegramAccionEsperadaToAttach.getClass(), telegramAccionEsperadaListTelegramAccionEsperadaToAttach.getId());
                attachedTelegramAccionEsperadaList.add(telegramAccionEsperadaListTelegramAccionEsperadaToAttach);
            }
            usuarios.setTelegramAccionEsperadaList(attachedTelegramAccionEsperadaList);
            List<TelegramReclamo> attachedTelegramReclamoList = new ArrayList<TelegramReclamo>();
            for (TelegramReclamo telegramReclamoListTelegramReclamoToAttach : usuarios.getTelegramReclamoList()) {
                telegramReclamoListTelegramReclamoToAttach = em.getReference(telegramReclamoListTelegramReclamoToAttach.getClass(), telegramReclamoListTelegramReclamoToAttach.getId());
                attachedTelegramReclamoList.add(telegramReclamoListTelegramReclamoToAttach);
            }
            usuarios.setTelegramReclamoList(attachedTelegramReclamoList);
            List<TelegramUsuario> attachedTelegramUsuarioList = new ArrayList<TelegramUsuario>();
            for (TelegramUsuario telegramUsuarioListTelegramUsuarioToAttach : usuarios.getTelegramUsuarioList()) {
                telegramUsuarioListTelegramUsuarioToAttach = em.getReference(telegramUsuarioListTelegramUsuarioToAttach.getClass(), telegramUsuarioListTelegramUsuarioToAttach.getId());
                attachedTelegramUsuarioList.add(telegramUsuarioListTelegramUsuarioToAttach);
            }
            usuarios.setTelegramUsuarioList(attachedTelegramUsuarioList);
            List<TelegramUsuarioIntegracion> attachedTelegramUsuarioIntegracionList = new ArrayList<TelegramUsuarioIntegracion>();
            for (TelegramUsuarioIntegracion telegramUsuarioIntegracionListTelegramUsuarioIntegracionToAttach : usuarios.getTelegramUsuarioIntegracionList()) {
                telegramUsuarioIntegracionListTelegramUsuarioIntegracionToAttach = em.getReference(telegramUsuarioIntegracionListTelegramUsuarioIntegracionToAttach.getClass(), telegramUsuarioIntegracionListTelegramUsuarioIntegracionToAttach.getId());
                attachedTelegramUsuarioIntegracionList.add(telegramUsuarioIntegracionListTelegramUsuarioIntegracionToAttach);
            }
            usuarios.setTelegramUsuarioIntegracionList(attachedTelegramUsuarioIntegracionList);
            List<FacebookUsuarioIntegracion> attachedFacebookUsuarioIntegracionList = new ArrayList<FacebookUsuarioIntegracion>();
            for (FacebookUsuarioIntegracion facebookUsuarioIntegracionListFacebookUsuarioIntegracionToAttach : usuarios.getFacebookUsuarioIntegracionList()) {
                facebookUsuarioIntegracionListFacebookUsuarioIntegracionToAttach = em.getReference(facebookUsuarioIntegracionListFacebookUsuarioIntegracionToAttach.getClass(), facebookUsuarioIntegracionListFacebookUsuarioIntegracionToAttach.getId());
                attachedFacebookUsuarioIntegracionList.add(facebookUsuarioIntegracionListFacebookUsuarioIntegracionToAttach);
            }
            usuarios.setFacebookUsuarioIntegracionList(attachedFacebookUsuarioIntegracionList);
            em.persist(usuarios);
            for (Apikeyuser apikeyuserListApikeyuser : usuarios.getApikeyuserList()) {
                Usuarios oldIdUsuarioOfApikeyuserListApikeyuser = apikeyuserListApikeyuser.getIdUsuario();
                apikeyuserListApikeyuser.setIdUsuario(usuarios);
                apikeyuserListApikeyuser = em.merge(apikeyuserListApikeyuser);
                if (oldIdUsuarioOfApikeyuserListApikeyuser != null) {
                    oldIdUsuarioOfApikeyuserListApikeyuser.getApikeyuserList().remove(apikeyuserListApikeyuser);
                    oldIdUsuarioOfApikeyuserListApikeyuser = em.merge(oldIdUsuarioOfApikeyuserListApikeyuser);
                }
            }
            for (FacebookAccionEsperada facebookAccionEsperadaListFacebookAccionEsperada : usuarios.getFacebookAccionEsperadaList()) {
                Usuarios oldIdUsuarioOfFacebookAccionEsperadaListFacebookAccionEsperada = facebookAccionEsperadaListFacebookAccionEsperada.getIdUsuario();
                facebookAccionEsperadaListFacebookAccionEsperada.setIdUsuario(usuarios);
                facebookAccionEsperadaListFacebookAccionEsperada = em.merge(facebookAccionEsperadaListFacebookAccionEsperada);
                if (oldIdUsuarioOfFacebookAccionEsperadaListFacebookAccionEsperada != null) {
                    oldIdUsuarioOfFacebookAccionEsperadaListFacebookAccionEsperada.getFacebookAccionEsperadaList().remove(facebookAccionEsperadaListFacebookAccionEsperada);
                    oldIdUsuarioOfFacebookAccionEsperadaListFacebookAccionEsperada = em.merge(oldIdUsuarioOfFacebookAccionEsperadaListFacebookAccionEsperada);
                }
            }
            for (FacebookUsuario facebookUsuarioListFacebookUsuario : usuarios.getFacebookUsuarioList()) {
                Usuarios oldIdUsuarioOfFacebookUsuarioListFacebookUsuario = facebookUsuarioListFacebookUsuario.getIdUsuario();
                facebookUsuarioListFacebookUsuario.setIdUsuario(usuarios);
                facebookUsuarioListFacebookUsuario = em.merge(facebookUsuarioListFacebookUsuario);
                if (oldIdUsuarioOfFacebookUsuarioListFacebookUsuario != null) {
                    oldIdUsuarioOfFacebookUsuarioListFacebookUsuario.getFacebookUsuarioList().remove(facebookUsuarioListFacebookUsuario);
                    oldIdUsuarioOfFacebookUsuarioListFacebookUsuario = em.merge(oldIdUsuarioOfFacebookUsuarioListFacebookUsuario);
                }
            }
            for (TelegramSugerencia telegramSugerenciaListTelegramSugerencia : usuarios.getTelegramSugerenciaList()) {
                Usuarios oldIdUsuarioOfTelegramSugerenciaListTelegramSugerencia = telegramSugerenciaListTelegramSugerencia.getIdUsuario();
                telegramSugerenciaListTelegramSugerencia.setIdUsuario(usuarios);
                telegramSugerenciaListTelegramSugerencia = em.merge(telegramSugerenciaListTelegramSugerencia);
                if (oldIdUsuarioOfTelegramSugerenciaListTelegramSugerencia != null) {
                    oldIdUsuarioOfTelegramSugerenciaListTelegramSugerencia.getTelegramSugerenciaList().remove(telegramSugerenciaListTelegramSugerencia);
                    oldIdUsuarioOfTelegramSugerenciaListTelegramSugerencia = em.merge(oldIdUsuarioOfTelegramSugerenciaListTelegramSugerencia);
                }
            }
            for (TelegramAccionEsperada telegramAccionEsperadaListTelegramAccionEsperada : usuarios.getTelegramAccionEsperadaList()) {
                Usuarios oldIdUsuarioOfTelegramAccionEsperadaListTelegramAccionEsperada = telegramAccionEsperadaListTelegramAccionEsperada.getIdUsuario();
                telegramAccionEsperadaListTelegramAccionEsperada.setIdUsuario(usuarios);
                telegramAccionEsperadaListTelegramAccionEsperada = em.merge(telegramAccionEsperadaListTelegramAccionEsperada);
                if (oldIdUsuarioOfTelegramAccionEsperadaListTelegramAccionEsperada != null) {
                    oldIdUsuarioOfTelegramAccionEsperadaListTelegramAccionEsperada.getTelegramAccionEsperadaList().remove(telegramAccionEsperadaListTelegramAccionEsperada);
                    oldIdUsuarioOfTelegramAccionEsperadaListTelegramAccionEsperada = em.merge(oldIdUsuarioOfTelegramAccionEsperadaListTelegramAccionEsperada);
                }
            }
            for (TelegramReclamo telegramReclamoListTelegramReclamo : usuarios.getTelegramReclamoList()) {
                Usuarios oldIdUsuarioOfTelegramReclamoListTelegramReclamo = telegramReclamoListTelegramReclamo.getIdUsuario();
                telegramReclamoListTelegramReclamo.setIdUsuario(usuarios);
                telegramReclamoListTelegramReclamo = em.merge(telegramReclamoListTelegramReclamo);
                if (oldIdUsuarioOfTelegramReclamoListTelegramReclamo != null) {
                    oldIdUsuarioOfTelegramReclamoListTelegramReclamo.getTelegramReclamoList().remove(telegramReclamoListTelegramReclamo);
                    oldIdUsuarioOfTelegramReclamoListTelegramReclamo = em.merge(oldIdUsuarioOfTelegramReclamoListTelegramReclamo);
                }
            }
            for (TelegramUsuario telegramUsuarioListTelegramUsuario : usuarios.getTelegramUsuarioList()) {
                Usuarios oldIdUsuarioOfTelegramUsuarioListTelegramUsuario = telegramUsuarioListTelegramUsuario.getIdUsuario();
                telegramUsuarioListTelegramUsuario.setIdUsuario(usuarios);
                telegramUsuarioListTelegramUsuario = em.merge(telegramUsuarioListTelegramUsuario);
                if (oldIdUsuarioOfTelegramUsuarioListTelegramUsuario != null) {
                    oldIdUsuarioOfTelegramUsuarioListTelegramUsuario.getTelegramUsuarioList().remove(telegramUsuarioListTelegramUsuario);
                    oldIdUsuarioOfTelegramUsuarioListTelegramUsuario = em.merge(oldIdUsuarioOfTelegramUsuarioListTelegramUsuario);
                }
            }
            for (TelegramUsuarioIntegracion telegramUsuarioIntegracionListTelegramUsuarioIntegracion : usuarios.getTelegramUsuarioIntegracionList()) {
                Usuarios oldIdUsuarioOfTelegramUsuarioIntegracionListTelegramUsuarioIntegracion = telegramUsuarioIntegracionListTelegramUsuarioIntegracion.getIdUsuario();
                telegramUsuarioIntegracionListTelegramUsuarioIntegracion.setIdUsuario(usuarios);
                telegramUsuarioIntegracionListTelegramUsuarioIntegracion = em.merge(telegramUsuarioIntegracionListTelegramUsuarioIntegracion);
                if (oldIdUsuarioOfTelegramUsuarioIntegracionListTelegramUsuarioIntegracion != null) {
                    oldIdUsuarioOfTelegramUsuarioIntegracionListTelegramUsuarioIntegracion.getTelegramUsuarioIntegracionList().remove(telegramUsuarioIntegracionListTelegramUsuarioIntegracion);
                    oldIdUsuarioOfTelegramUsuarioIntegracionListTelegramUsuarioIntegracion = em.merge(oldIdUsuarioOfTelegramUsuarioIntegracionListTelegramUsuarioIntegracion);
                }
            }
            for (FacebookUsuarioIntegracion facebookUsuarioIntegracionListFacebookUsuarioIntegracion : usuarios.getFacebookUsuarioIntegracionList()) {
                Usuarios oldIdUsuarioOfFacebookUsuarioIntegracionListFacebookUsuarioIntegracion = facebookUsuarioIntegracionListFacebookUsuarioIntegracion.getIdUsuario();
                facebookUsuarioIntegracionListFacebookUsuarioIntegracion.setIdUsuario(usuarios);
                facebookUsuarioIntegracionListFacebookUsuarioIntegracion = em.merge(facebookUsuarioIntegracionListFacebookUsuarioIntegracion);
                if (oldIdUsuarioOfFacebookUsuarioIntegracionListFacebookUsuarioIntegracion != null) {
                    oldIdUsuarioOfFacebookUsuarioIntegracionListFacebookUsuarioIntegracion.getFacebookUsuarioIntegracionList().remove(facebookUsuarioIntegracionListFacebookUsuarioIntegracion);
                    oldIdUsuarioOfFacebookUsuarioIntegracionListFacebookUsuarioIntegracion = em.merge(oldIdUsuarioOfFacebookUsuarioIntegracionListFacebookUsuarioIntegracion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getId());
            List<Apikeyuser> apikeyuserListOld = persistentUsuarios.getApikeyuserList();
            List<Apikeyuser> apikeyuserListNew = usuarios.getApikeyuserList();
            List<FacebookAccionEsperada> facebookAccionEsperadaListOld = persistentUsuarios.getFacebookAccionEsperadaList();
            List<FacebookAccionEsperada> facebookAccionEsperadaListNew = usuarios.getFacebookAccionEsperadaList();
            List<FacebookUsuario> facebookUsuarioListOld = persistentUsuarios.getFacebookUsuarioList();
            List<FacebookUsuario> facebookUsuarioListNew = usuarios.getFacebookUsuarioList();
            List<TelegramSugerencia> telegramSugerenciaListOld = persistentUsuarios.getTelegramSugerenciaList();
            List<TelegramSugerencia> telegramSugerenciaListNew = usuarios.getTelegramSugerenciaList();
            List<TelegramAccionEsperada> telegramAccionEsperadaListOld = persistentUsuarios.getTelegramAccionEsperadaList();
            List<TelegramAccionEsperada> telegramAccionEsperadaListNew = usuarios.getTelegramAccionEsperadaList();
            List<TelegramReclamo> telegramReclamoListOld = persistentUsuarios.getTelegramReclamoList();
            List<TelegramReclamo> telegramReclamoListNew = usuarios.getTelegramReclamoList();
            List<TelegramUsuario> telegramUsuarioListOld = persistentUsuarios.getTelegramUsuarioList();
            List<TelegramUsuario> telegramUsuarioListNew = usuarios.getTelegramUsuarioList();
            List<TelegramUsuarioIntegracion> telegramUsuarioIntegracionListOld = persistentUsuarios.getTelegramUsuarioIntegracionList();
            List<TelegramUsuarioIntegracion> telegramUsuarioIntegracionListNew = usuarios.getTelegramUsuarioIntegracionList();
            List<FacebookUsuarioIntegracion> facebookUsuarioIntegracionListOld = persistentUsuarios.getFacebookUsuarioIntegracionList();
            List<FacebookUsuarioIntegracion> facebookUsuarioIntegracionListNew = usuarios.getFacebookUsuarioIntegracionList();
            List<String> illegalOrphanMessages = null;
            for (FacebookAccionEsperada facebookAccionEsperadaListOldFacebookAccionEsperada : facebookAccionEsperadaListOld) {
                if (!facebookAccionEsperadaListNew.contains(facebookAccionEsperadaListOldFacebookAccionEsperada)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FacebookAccionEsperada " + facebookAccionEsperadaListOldFacebookAccionEsperada + " since its idUsuario field is not nullable.");
                }
            }
            for (TelegramSugerencia telegramSugerenciaListOldTelegramSugerencia : telegramSugerenciaListOld) {
                if (!telegramSugerenciaListNew.contains(telegramSugerenciaListOldTelegramSugerencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TelegramSugerencia " + telegramSugerenciaListOldTelegramSugerencia + " since its idUsuario field is not nullable.");
                }
            }
            for (TelegramAccionEsperada telegramAccionEsperadaListOldTelegramAccionEsperada : telegramAccionEsperadaListOld) {
                if (!telegramAccionEsperadaListNew.contains(telegramAccionEsperadaListOldTelegramAccionEsperada)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TelegramAccionEsperada " + telegramAccionEsperadaListOldTelegramAccionEsperada + " since its idUsuario field is not nullable.");
                }
            }
            for (TelegramReclamo telegramReclamoListOldTelegramReclamo : telegramReclamoListOld) {
                if (!telegramReclamoListNew.contains(telegramReclamoListOldTelegramReclamo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TelegramReclamo " + telegramReclamoListOldTelegramReclamo + " since its idUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Apikeyuser> attachedApikeyuserListNew = new ArrayList<Apikeyuser>();
            for (Apikeyuser apikeyuserListNewApikeyuserToAttach : apikeyuserListNew) {
                apikeyuserListNewApikeyuserToAttach = em.getReference(apikeyuserListNewApikeyuserToAttach.getClass(), apikeyuserListNewApikeyuserToAttach.getId());
                attachedApikeyuserListNew.add(apikeyuserListNewApikeyuserToAttach);
            }
            apikeyuserListNew = attachedApikeyuserListNew;
            usuarios.setApikeyuserList(apikeyuserListNew);
            List<FacebookAccionEsperada> attachedFacebookAccionEsperadaListNew = new ArrayList<FacebookAccionEsperada>();
            for (FacebookAccionEsperada facebookAccionEsperadaListNewFacebookAccionEsperadaToAttach : facebookAccionEsperadaListNew) {
                facebookAccionEsperadaListNewFacebookAccionEsperadaToAttach = em.getReference(facebookAccionEsperadaListNewFacebookAccionEsperadaToAttach.getClass(), facebookAccionEsperadaListNewFacebookAccionEsperadaToAttach.getId());
                attachedFacebookAccionEsperadaListNew.add(facebookAccionEsperadaListNewFacebookAccionEsperadaToAttach);
            }
            facebookAccionEsperadaListNew = attachedFacebookAccionEsperadaListNew;
            usuarios.setFacebookAccionEsperadaList(facebookAccionEsperadaListNew);
            List<FacebookUsuario> attachedFacebookUsuarioListNew = new ArrayList<FacebookUsuario>();
            for (FacebookUsuario facebookUsuarioListNewFacebookUsuarioToAttach : facebookUsuarioListNew) {
                facebookUsuarioListNewFacebookUsuarioToAttach = em.getReference(facebookUsuarioListNewFacebookUsuarioToAttach.getClass(), facebookUsuarioListNewFacebookUsuarioToAttach.getId());
                attachedFacebookUsuarioListNew.add(facebookUsuarioListNewFacebookUsuarioToAttach);
            }
            facebookUsuarioListNew = attachedFacebookUsuarioListNew;
            usuarios.setFacebookUsuarioList(facebookUsuarioListNew);
            List<TelegramSugerencia> attachedTelegramSugerenciaListNew = new ArrayList<TelegramSugerencia>();
            for (TelegramSugerencia telegramSugerenciaListNewTelegramSugerenciaToAttach : telegramSugerenciaListNew) {
                telegramSugerenciaListNewTelegramSugerenciaToAttach = em.getReference(telegramSugerenciaListNewTelegramSugerenciaToAttach.getClass(), telegramSugerenciaListNewTelegramSugerenciaToAttach.getId());
                attachedTelegramSugerenciaListNew.add(telegramSugerenciaListNewTelegramSugerenciaToAttach);
            }
            telegramSugerenciaListNew = attachedTelegramSugerenciaListNew;
            usuarios.setTelegramSugerenciaList(telegramSugerenciaListNew);
            List<TelegramAccionEsperada> attachedTelegramAccionEsperadaListNew = new ArrayList<TelegramAccionEsperada>();
            for (TelegramAccionEsperada telegramAccionEsperadaListNewTelegramAccionEsperadaToAttach : telegramAccionEsperadaListNew) {
                telegramAccionEsperadaListNewTelegramAccionEsperadaToAttach = em.getReference(telegramAccionEsperadaListNewTelegramAccionEsperadaToAttach.getClass(), telegramAccionEsperadaListNewTelegramAccionEsperadaToAttach.getId());
                attachedTelegramAccionEsperadaListNew.add(telegramAccionEsperadaListNewTelegramAccionEsperadaToAttach);
            }
            telegramAccionEsperadaListNew = attachedTelegramAccionEsperadaListNew;
            usuarios.setTelegramAccionEsperadaList(telegramAccionEsperadaListNew);
            List<TelegramReclamo> attachedTelegramReclamoListNew = new ArrayList<TelegramReclamo>();
            for (TelegramReclamo telegramReclamoListNewTelegramReclamoToAttach : telegramReclamoListNew) {
                telegramReclamoListNewTelegramReclamoToAttach = em.getReference(telegramReclamoListNewTelegramReclamoToAttach.getClass(), telegramReclamoListNewTelegramReclamoToAttach.getId());
                attachedTelegramReclamoListNew.add(telegramReclamoListNewTelegramReclamoToAttach);
            }
            telegramReclamoListNew = attachedTelegramReclamoListNew;
            usuarios.setTelegramReclamoList(telegramReclamoListNew);
            List<TelegramUsuario> attachedTelegramUsuarioListNew = new ArrayList<TelegramUsuario>();
            for (TelegramUsuario telegramUsuarioListNewTelegramUsuarioToAttach : telegramUsuarioListNew) {
                telegramUsuarioListNewTelegramUsuarioToAttach = em.getReference(telegramUsuarioListNewTelegramUsuarioToAttach.getClass(), telegramUsuarioListNewTelegramUsuarioToAttach.getId());
                attachedTelegramUsuarioListNew.add(telegramUsuarioListNewTelegramUsuarioToAttach);
            }
            telegramUsuarioListNew = attachedTelegramUsuarioListNew;
            usuarios.setTelegramUsuarioList(telegramUsuarioListNew);
            List<TelegramUsuarioIntegracion> attachedTelegramUsuarioIntegracionListNew = new ArrayList<TelegramUsuarioIntegracion>();
            for (TelegramUsuarioIntegracion telegramUsuarioIntegracionListNewTelegramUsuarioIntegracionToAttach : telegramUsuarioIntegracionListNew) {
                telegramUsuarioIntegracionListNewTelegramUsuarioIntegracionToAttach = em.getReference(telegramUsuarioIntegracionListNewTelegramUsuarioIntegracionToAttach.getClass(), telegramUsuarioIntegracionListNewTelegramUsuarioIntegracionToAttach.getId());
                attachedTelegramUsuarioIntegracionListNew.add(telegramUsuarioIntegracionListNewTelegramUsuarioIntegracionToAttach);
            }
            telegramUsuarioIntegracionListNew = attachedTelegramUsuarioIntegracionListNew;
            usuarios.setTelegramUsuarioIntegracionList(telegramUsuarioIntegracionListNew);
            List<FacebookUsuarioIntegracion> attachedFacebookUsuarioIntegracionListNew = new ArrayList<FacebookUsuarioIntegracion>();
            for (FacebookUsuarioIntegracion facebookUsuarioIntegracionListNewFacebookUsuarioIntegracionToAttach : facebookUsuarioIntegracionListNew) {
                facebookUsuarioIntegracionListNewFacebookUsuarioIntegracionToAttach = em.getReference(facebookUsuarioIntegracionListNewFacebookUsuarioIntegracionToAttach.getClass(), facebookUsuarioIntegracionListNewFacebookUsuarioIntegracionToAttach.getId());
                attachedFacebookUsuarioIntegracionListNew.add(facebookUsuarioIntegracionListNewFacebookUsuarioIntegracionToAttach);
            }
            facebookUsuarioIntegracionListNew = attachedFacebookUsuarioIntegracionListNew;
            usuarios.setFacebookUsuarioIntegracionList(facebookUsuarioIntegracionListNew);
            usuarios = em.merge(usuarios);
            for (Apikeyuser apikeyuserListOldApikeyuser : apikeyuserListOld) {
                if (!apikeyuserListNew.contains(apikeyuserListOldApikeyuser)) {
                    apikeyuserListOldApikeyuser.setIdUsuario(null);
                    apikeyuserListOldApikeyuser = em.merge(apikeyuserListOldApikeyuser);
                }
            }
            for (Apikeyuser apikeyuserListNewApikeyuser : apikeyuserListNew) {
                if (!apikeyuserListOld.contains(apikeyuserListNewApikeyuser)) {
                    Usuarios oldIdUsuarioOfApikeyuserListNewApikeyuser = apikeyuserListNewApikeyuser.getIdUsuario();
                    apikeyuserListNewApikeyuser.setIdUsuario(usuarios);
                    apikeyuserListNewApikeyuser = em.merge(apikeyuserListNewApikeyuser);
                    if (oldIdUsuarioOfApikeyuserListNewApikeyuser != null && !oldIdUsuarioOfApikeyuserListNewApikeyuser.equals(usuarios)) {
                        oldIdUsuarioOfApikeyuserListNewApikeyuser.getApikeyuserList().remove(apikeyuserListNewApikeyuser);
                        oldIdUsuarioOfApikeyuserListNewApikeyuser = em.merge(oldIdUsuarioOfApikeyuserListNewApikeyuser);
                    }
                }
            }
            for (FacebookAccionEsperada facebookAccionEsperadaListNewFacebookAccionEsperada : facebookAccionEsperadaListNew) {
                if (!facebookAccionEsperadaListOld.contains(facebookAccionEsperadaListNewFacebookAccionEsperada)) {
                    Usuarios oldIdUsuarioOfFacebookAccionEsperadaListNewFacebookAccionEsperada = facebookAccionEsperadaListNewFacebookAccionEsperada.getIdUsuario();
                    facebookAccionEsperadaListNewFacebookAccionEsperada.setIdUsuario(usuarios);
                    facebookAccionEsperadaListNewFacebookAccionEsperada = em.merge(facebookAccionEsperadaListNewFacebookAccionEsperada);
                    if (oldIdUsuarioOfFacebookAccionEsperadaListNewFacebookAccionEsperada != null && !oldIdUsuarioOfFacebookAccionEsperadaListNewFacebookAccionEsperada.equals(usuarios)) {
                        oldIdUsuarioOfFacebookAccionEsperadaListNewFacebookAccionEsperada.getFacebookAccionEsperadaList().remove(facebookAccionEsperadaListNewFacebookAccionEsperada);
                        oldIdUsuarioOfFacebookAccionEsperadaListNewFacebookAccionEsperada = em.merge(oldIdUsuarioOfFacebookAccionEsperadaListNewFacebookAccionEsperada);
                    }
                }
            }
            for (FacebookUsuario facebookUsuarioListOldFacebookUsuario : facebookUsuarioListOld) {
                if (!facebookUsuarioListNew.contains(facebookUsuarioListOldFacebookUsuario)) {
                    facebookUsuarioListOldFacebookUsuario.setIdUsuario(null);
                    facebookUsuarioListOldFacebookUsuario = em.merge(facebookUsuarioListOldFacebookUsuario);
                }
            }
            for (FacebookUsuario facebookUsuarioListNewFacebookUsuario : facebookUsuarioListNew) {
                if (!facebookUsuarioListOld.contains(facebookUsuarioListNewFacebookUsuario)) {
                    Usuarios oldIdUsuarioOfFacebookUsuarioListNewFacebookUsuario = facebookUsuarioListNewFacebookUsuario.getIdUsuario();
                    facebookUsuarioListNewFacebookUsuario.setIdUsuario(usuarios);
                    facebookUsuarioListNewFacebookUsuario = em.merge(facebookUsuarioListNewFacebookUsuario);
                    if (oldIdUsuarioOfFacebookUsuarioListNewFacebookUsuario != null && !oldIdUsuarioOfFacebookUsuarioListNewFacebookUsuario.equals(usuarios)) {
                        oldIdUsuarioOfFacebookUsuarioListNewFacebookUsuario.getFacebookUsuarioList().remove(facebookUsuarioListNewFacebookUsuario);
                        oldIdUsuarioOfFacebookUsuarioListNewFacebookUsuario = em.merge(oldIdUsuarioOfFacebookUsuarioListNewFacebookUsuario);
                    }
                }
            }
            for (TelegramSugerencia telegramSugerenciaListNewTelegramSugerencia : telegramSugerenciaListNew) {
                if (!telegramSugerenciaListOld.contains(telegramSugerenciaListNewTelegramSugerencia)) {
                    Usuarios oldIdUsuarioOfTelegramSugerenciaListNewTelegramSugerencia = telegramSugerenciaListNewTelegramSugerencia.getIdUsuario();
                    telegramSugerenciaListNewTelegramSugerencia.setIdUsuario(usuarios);
                    telegramSugerenciaListNewTelegramSugerencia = em.merge(telegramSugerenciaListNewTelegramSugerencia);
                    if (oldIdUsuarioOfTelegramSugerenciaListNewTelegramSugerencia != null && !oldIdUsuarioOfTelegramSugerenciaListNewTelegramSugerencia.equals(usuarios)) {
                        oldIdUsuarioOfTelegramSugerenciaListNewTelegramSugerencia.getTelegramSugerenciaList().remove(telegramSugerenciaListNewTelegramSugerencia);
                        oldIdUsuarioOfTelegramSugerenciaListNewTelegramSugerencia = em.merge(oldIdUsuarioOfTelegramSugerenciaListNewTelegramSugerencia);
                    }
                }
            }
            for (TelegramAccionEsperada telegramAccionEsperadaListNewTelegramAccionEsperada : telegramAccionEsperadaListNew) {
                if (!telegramAccionEsperadaListOld.contains(telegramAccionEsperadaListNewTelegramAccionEsperada)) {
                    Usuarios oldIdUsuarioOfTelegramAccionEsperadaListNewTelegramAccionEsperada = telegramAccionEsperadaListNewTelegramAccionEsperada.getIdUsuario();
                    telegramAccionEsperadaListNewTelegramAccionEsperada.setIdUsuario(usuarios);
                    telegramAccionEsperadaListNewTelegramAccionEsperada = em.merge(telegramAccionEsperadaListNewTelegramAccionEsperada);
                    if (oldIdUsuarioOfTelegramAccionEsperadaListNewTelegramAccionEsperada != null && !oldIdUsuarioOfTelegramAccionEsperadaListNewTelegramAccionEsperada.equals(usuarios)) {
                        oldIdUsuarioOfTelegramAccionEsperadaListNewTelegramAccionEsperada.getTelegramAccionEsperadaList().remove(telegramAccionEsperadaListNewTelegramAccionEsperada);
                        oldIdUsuarioOfTelegramAccionEsperadaListNewTelegramAccionEsperada = em.merge(oldIdUsuarioOfTelegramAccionEsperadaListNewTelegramAccionEsperada);
                    }
                }
            }
            for (TelegramReclamo telegramReclamoListNewTelegramReclamo : telegramReclamoListNew) {
                if (!telegramReclamoListOld.contains(telegramReclamoListNewTelegramReclamo)) {
                    Usuarios oldIdUsuarioOfTelegramReclamoListNewTelegramReclamo = telegramReclamoListNewTelegramReclamo.getIdUsuario();
                    telegramReclamoListNewTelegramReclamo.setIdUsuario(usuarios);
                    telegramReclamoListNewTelegramReclamo = em.merge(telegramReclamoListNewTelegramReclamo);
                    if (oldIdUsuarioOfTelegramReclamoListNewTelegramReclamo != null && !oldIdUsuarioOfTelegramReclamoListNewTelegramReclamo.equals(usuarios)) {
                        oldIdUsuarioOfTelegramReclamoListNewTelegramReclamo.getTelegramReclamoList().remove(telegramReclamoListNewTelegramReclamo);
                        oldIdUsuarioOfTelegramReclamoListNewTelegramReclamo = em.merge(oldIdUsuarioOfTelegramReclamoListNewTelegramReclamo);
                    }
                }
            }
            for (TelegramUsuario telegramUsuarioListOldTelegramUsuario : telegramUsuarioListOld) {
                if (!telegramUsuarioListNew.contains(telegramUsuarioListOldTelegramUsuario)) {
                    telegramUsuarioListOldTelegramUsuario.setIdUsuario(null);
                    telegramUsuarioListOldTelegramUsuario = em.merge(telegramUsuarioListOldTelegramUsuario);
                }
            }
            for (TelegramUsuario telegramUsuarioListNewTelegramUsuario : telegramUsuarioListNew) {
                if (!telegramUsuarioListOld.contains(telegramUsuarioListNewTelegramUsuario)) {
                    Usuarios oldIdUsuarioOfTelegramUsuarioListNewTelegramUsuario = telegramUsuarioListNewTelegramUsuario.getIdUsuario();
                    telegramUsuarioListNewTelegramUsuario.setIdUsuario(usuarios);
                    telegramUsuarioListNewTelegramUsuario = em.merge(telegramUsuarioListNewTelegramUsuario);
                    if (oldIdUsuarioOfTelegramUsuarioListNewTelegramUsuario != null && !oldIdUsuarioOfTelegramUsuarioListNewTelegramUsuario.equals(usuarios)) {
                        oldIdUsuarioOfTelegramUsuarioListNewTelegramUsuario.getTelegramUsuarioList().remove(telegramUsuarioListNewTelegramUsuario);
                        oldIdUsuarioOfTelegramUsuarioListNewTelegramUsuario = em.merge(oldIdUsuarioOfTelegramUsuarioListNewTelegramUsuario);
                    }
                }
            }
            for (TelegramUsuarioIntegracion telegramUsuarioIntegracionListOldTelegramUsuarioIntegracion : telegramUsuarioIntegracionListOld) {
                if (!telegramUsuarioIntegracionListNew.contains(telegramUsuarioIntegracionListOldTelegramUsuarioIntegracion)) {
                    telegramUsuarioIntegracionListOldTelegramUsuarioIntegracion.setIdUsuario(null);
                    telegramUsuarioIntegracionListOldTelegramUsuarioIntegracion = em.merge(telegramUsuarioIntegracionListOldTelegramUsuarioIntegracion);
                }
            }
            for (TelegramUsuarioIntegracion telegramUsuarioIntegracionListNewTelegramUsuarioIntegracion : telegramUsuarioIntegracionListNew) {
                if (!telegramUsuarioIntegracionListOld.contains(telegramUsuarioIntegracionListNewTelegramUsuarioIntegracion)) {
                    Usuarios oldIdUsuarioOfTelegramUsuarioIntegracionListNewTelegramUsuarioIntegracion = telegramUsuarioIntegracionListNewTelegramUsuarioIntegracion.getIdUsuario();
                    telegramUsuarioIntegracionListNewTelegramUsuarioIntegracion.setIdUsuario(usuarios);
                    telegramUsuarioIntegracionListNewTelegramUsuarioIntegracion = em.merge(telegramUsuarioIntegracionListNewTelegramUsuarioIntegracion);
                    if (oldIdUsuarioOfTelegramUsuarioIntegracionListNewTelegramUsuarioIntegracion != null && !oldIdUsuarioOfTelegramUsuarioIntegracionListNewTelegramUsuarioIntegracion.equals(usuarios)) {
                        oldIdUsuarioOfTelegramUsuarioIntegracionListNewTelegramUsuarioIntegracion.getTelegramUsuarioIntegracionList().remove(telegramUsuarioIntegracionListNewTelegramUsuarioIntegracion);
                        oldIdUsuarioOfTelegramUsuarioIntegracionListNewTelegramUsuarioIntegracion = em.merge(oldIdUsuarioOfTelegramUsuarioIntegracionListNewTelegramUsuarioIntegracion);
                    }
                }
            }
            for (FacebookUsuarioIntegracion facebookUsuarioIntegracionListOldFacebookUsuarioIntegracion : facebookUsuarioIntegracionListOld) {
                if (!facebookUsuarioIntegracionListNew.contains(facebookUsuarioIntegracionListOldFacebookUsuarioIntegracion)) {
                    facebookUsuarioIntegracionListOldFacebookUsuarioIntegracion.setIdUsuario(null);
                    facebookUsuarioIntegracionListOldFacebookUsuarioIntegracion = em.merge(facebookUsuarioIntegracionListOldFacebookUsuarioIntegracion);
                }
            }
            for (FacebookUsuarioIntegracion facebookUsuarioIntegracionListNewFacebookUsuarioIntegracion : facebookUsuarioIntegracionListNew) {
                if (!facebookUsuarioIntegracionListOld.contains(facebookUsuarioIntegracionListNewFacebookUsuarioIntegracion)) {
                    Usuarios oldIdUsuarioOfFacebookUsuarioIntegracionListNewFacebookUsuarioIntegracion = facebookUsuarioIntegracionListNewFacebookUsuarioIntegracion.getIdUsuario();
                    facebookUsuarioIntegracionListNewFacebookUsuarioIntegracion.setIdUsuario(usuarios);
                    facebookUsuarioIntegracionListNewFacebookUsuarioIntegracion = em.merge(facebookUsuarioIntegracionListNewFacebookUsuarioIntegracion);
                    if (oldIdUsuarioOfFacebookUsuarioIntegracionListNewFacebookUsuarioIntegracion != null && !oldIdUsuarioOfFacebookUsuarioIntegracionListNewFacebookUsuarioIntegracion.equals(usuarios)) {
                        oldIdUsuarioOfFacebookUsuarioIntegracionListNewFacebookUsuarioIntegracion.getFacebookUsuarioIntegracionList().remove(facebookUsuarioIntegracionListNewFacebookUsuarioIntegracion);
                        oldIdUsuarioOfFacebookUsuarioIntegracionListNewFacebookUsuarioIntegracion = em.merge(oldIdUsuarioOfFacebookUsuarioIntegracionListNewFacebookUsuarioIntegracion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getId();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<FacebookAccionEsperada> facebookAccionEsperadaListOrphanCheck = usuarios.getFacebookAccionEsperadaList();
            for (FacebookAccionEsperada facebookAccionEsperadaListOrphanCheckFacebookAccionEsperada : facebookAccionEsperadaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the FacebookAccionEsperada " + facebookAccionEsperadaListOrphanCheckFacebookAccionEsperada + " in its facebookAccionEsperadaList field has a non-nullable idUsuario field.");
            }
            List<TelegramSugerencia> telegramSugerenciaListOrphanCheck = usuarios.getTelegramSugerenciaList();
            for (TelegramSugerencia telegramSugerenciaListOrphanCheckTelegramSugerencia : telegramSugerenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the TelegramSugerencia " + telegramSugerenciaListOrphanCheckTelegramSugerencia + " in its telegramSugerenciaList field has a non-nullable idUsuario field.");
            }
            List<TelegramAccionEsperada> telegramAccionEsperadaListOrphanCheck = usuarios.getTelegramAccionEsperadaList();
            for (TelegramAccionEsperada telegramAccionEsperadaListOrphanCheckTelegramAccionEsperada : telegramAccionEsperadaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the TelegramAccionEsperada " + telegramAccionEsperadaListOrphanCheckTelegramAccionEsperada + " in its telegramAccionEsperadaList field has a non-nullable idUsuario field.");
            }
            List<TelegramReclamo> telegramReclamoListOrphanCheck = usuarios.getTelegramReclamoList();
            for (TelegramReclamo telegramReclamoListOrphanCheckTelegramReclamo : telegramReclamoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the TelegramReclamo " + telegramReclamoListOrphanCheckTelegramReclamo + " in its telegramReclamoList field has a non-nullable idUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Apikeyuser> apikeyuserList = usuarios.getApikeyuserList();
            for (Apikeyuser apikeyuserListApikeyuser : apikeyuserList) {
                apikeyuserListApikeyuser.setIdUsuario(null);
                apikeyuserListApikeyuser = em.merge(apikeyuserListApikeyuser);
            }
            List<FacebookUsuario> facebookUsuarioList = usuarios.getFacebookUsuarioList();
            for (FacebookUsuario facebookUsuarioListFacebookUsuario : facebookUsuarioList) {
                facebookUsuarioListFacebookUsuario.setIdUsuario(null);
                facebookUsuarioListFacebookUsuario = em.merge(facebookUsuarioListFacebookUsuario);
            }
            List<TelegramUsuario> telegramUsuarioList = usuarios.getTelegramUsuarioList();
            for (TelegramUsuario telegramUsuarioListTelegramUsuario : telegramUsuarioList) {
                telegramUsuarioListTelegramUsuario.setIdUsuario(null);
                telegramUsuarioListTelegramUsuario = em.merge(telegramUsuarioListTelegramUsuario);
            }
            List<TelegramUsuarioIntegracion> telegramUsuarioIntegracionList = usuarios.getTelegramUsuarioIntegracionList();
            for (TelegramUsuarioIntegracion telegramUsuarioIntegracionListTelegramUsuarioIntegracion : telegramUsuarioIntegracionList) {
                telegramUsuarioIntegracionListTelegramUsuarioIntegracion.setIdUsuario(null);
                telegramUsuarioIntegracionListTelegramUsuarioIntegracion = em.merge(telegramUsuarioIntegracionListTelegramUsuarioIntegracion);
            }
            List<FacebookUsuarioIntegracion> facebookUsuarioIntegracionList = usuarios.getFacebookUsuarioIntegracionList();
            for (FacebookUsuarioIntegracion facebookUsuarioIntegracionListFacebookUsuarioIntegracion : facebookUsuarioIntegracionList) {
                facebookUsuarioIntegracionListFacebookUsuarioIntegracion.setIdUsuario(null);
                facebookUsuarioIntegracionListFacebookUsuarioIntegracion = em.merge(facebookUsuarioIntegracionListFacebookUsuarioIntegracion);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Usuarios findUsuario(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
