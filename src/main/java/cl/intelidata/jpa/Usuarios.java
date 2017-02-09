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
package cl.intelidata.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dev-DFeliu
 */
@Entity
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findById", query = "SELECT u FROM Usuarios u WHERE u.id = :id"),
    @NamedQuery(name = "Usuarios.findByUsername", query = "SELECT u FROM Usuarios u WHERE u.username = :username"),
    @NamedQuery(name = "Usuarios.findByPassword", query = "SELECT u FROM Usuarios u WHERE u.password = :password"),
    @NamedQuery(name = "Usuarios.findByIdCliente", query = "SELECT u FROM Usuarios u WHERE u.idCliente = :idCliente"),
    @NamedQuery(name = "Usuarios.findByIdPersona", query = "SELECT u FROM Usuarios u WHERE u.idPersona = :idPersona"),
    @NamedQuery(name = "Usuarios.findByEstado", query = "SELECT u FROM Usuarios u WHERE u.estado = :estado"),
    @NamedQuery(name = "Usuarios.findByUpdatedAt", query = "SELECT u FROM Usuarios u WHERE u.updatedAt = :updatedAt"),
    @NamedQuery(name = "Usuarios.findByCreatedAt", query = "SELECT u FROM Usuarios u WHERE u.createdAt = :createdAt"),
    @NamedQuery(name = "Usuarios.findByDeletedAt", query = "SELECT u FROM Usuarios u WHERE u.deletedAt = :deletedAt"),
    @NamedQuery(name = "Usuarios.findByLastLogin", query = "SELECT u FROM Usuarios u WHERE u.lastLogin = :lastLogin"),
    @NamedQuery(name = "Usuarios.findByRememberToken", query = "SELECT u FROM Usuarios u WHERE u.rememberToken = :rememberToken"),
    @NamedQuery(name = "Usuarios.validLogin", query = "SELECT u FROM Usuarios u WHERE u.username = :username"),
})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "id_cliente")
    private Integer idCliente;
    @Column(name = "id_persona")
    private Integer idPersona;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    @Column(name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
    @Column(name = "remember_token")
    private String rememberToken;
    @OneToMany(mappedBy = "idUsuario")
    private List<Apikeyuser> apikeyuserList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<FacebookAccionEsperada> facebookAccionEsperadaList;
    @OneToMany(mappedBy = "idUsuario")
    private List<FacebookUsuario> facebookUsuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<TelegramSugerencia> telegramSugerenciaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<TelegramAccionEsperada> telegramAccionEsperadaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    private List<TelegramReclamo> telegramReclamoList;
    @OneToMany(mappedBy = "idUsuario")
    private List<TelegramUsuario> telegramUsuarioList;
    @OneToMany(mappedBy = "idUsuario")
    private List<TelegramUsuarioIntegracion> telegramUsuarioIntegracionList;
    @OneToMany(mappedBy = "idUsuario")
    private List<FacebookUsuarioIntegracion> facebookUsuarioIntegracionList;

    public Usuarios() {
    }

    public Usuarios(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(String rememberToken) {
        this.rememberToken = rememberToken;
    }

    @XmlTransient
    public List<Apikeyuser> getApikeyuserList() {
        return apikeyuserList;
    }

    public void setApikeyuserList(List<Apikeyuser> apikeyuserList) {
        this.apikeyuserList = apikeyuserList;
    }

    @XmlTransient
    public List<FacebookAccionEsperada> getFacebookAccionEsperadaList() {
        return facebookAccionEsperadaList;
    }

    public void setFacebookAccionEsperadaList(List<FacebookAccionEsperada> facebookAccionEsperadaList) {
        this.facebookAccionEsperadaList = facebookAccionEsperadaList;
    }

    @XmlTransient
    public List<FacebookUsuario> getFacebookUsuarioList() {
        return facebookUsuarioList;
    }

    public void setFacebookUsuarioList(List<FacebookUsuario> facebookUsuarioList) {
        this.facebookUsuarioList = facebookUsuarioList;
    }

    @XmlTransient
    public List<TelegramSugerencia> getTelegramSugerenciaList() {
        return telegramSugerenciaList;
    }

    public void setTelegramSugerenciaList(List<TelegramSugerencia> telegramSugerenciaList) {
        this.telegramSugerenciaList = telegramSugerenciaList;
    }

    @XmlTransient
    public List<TelegramAccionEsperada> getTelegramAccionEsperadaList() {
        return telegramAccionEsperadaList;
    }

    public void setTelegramAccionEsperadaList(List<TelegramAccionEsperada> telegramAccionEsperadaList) {
        this.telegramAccionEsperadaList = telegramAccionEsperadaList;
    }

    @XmlTransient
    public List<TelegramReclamo> getTelegramReclamoList() {
        return telegramReclamoList;
    }

    public void setTelegramReclamoList(List<TelegramReclamo> telegramReclamoList) {
        this.telegramReclamoList = telegramReclamoList;
    }

    @XmlTransient
    public List<TelegramUsuario> getTelegramUsuarioList() {
        return telegramUsuarioList;
    }

    public void setTelegramUsuarioList(List<TelegramUsuario> telegramUsuarioList) {
        this.telegramUsuarioList = telegramUsuarioList;
    }

    @XmlTransient
    public List<TelegramUsuarioIntegracion> getTelegramUsuarioIntegracionList() {
        return telegramUsuarioIntegracionList;
    }

    public void setTelegramUsuarioIntegracionList(List<TelegramUsuarioIntegracion> telegramUsuarioIntegracionList) {
        this.telegramUsuarioIntegracionList = telegramUsuarioIntegracionList;
    }

    @XmlTransient
    public List<FacebookUsuarioIntegracion> getFacebookUsuarioIntegracionList() {
        return facebookUsuarioIntegracionList;
    }

    public void setFacebookUsuarioIntegracionList(List<FacebookUsuarioIntegracion> facebookUsuarioIntegracionList) {
        this.facebookUsuarioIntegracionList = facebookUsuarioIntegracionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.intelidata.jpa.Usuarios[ id=" + id + " ]";
    }

}
