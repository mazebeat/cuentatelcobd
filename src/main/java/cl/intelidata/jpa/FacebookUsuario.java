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
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dev-DFeliu
 */
@Entity
@Table(name = "facebook_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacebookUsuario.findAll", query = "SELECT f FROM FacebookUsuario f"),
    @NamedQuery(name = "FacebookUsuario.findById", query = "SELECT f FROM FacebookUsuario f WHERE f.id = :id"),
    @NamedQuery(name = "FacebookUsuario.findByIdUsuarioFacebook", query = "SELECT f FROM FacebookUsuario f WHERE f.idUsuarioFacebook = :idUsuarioFacebook"),
    @NamedQuery(name = "FacebookUsuario.findByUsernameFacebook", query = "SELECT f FROM FacebookUsuario f WHERE f.usernameFacebook = :usernameFacebook"),
    @NamedQuery(name = "FacebookUsuario.findByEstado", query = "SELECT f FROM FacebookUsuario f WHERE f.estado = :estado"),
    @NamedQuery(name = "FacebookUsuario.findByCreatedAt", query = "SELECT f FROM FacebookUsuario f WHERE f.createdAt = :createdAt"),
    @NamedQuery(name = "FacebookUsuario.findByUpdatedAt", query = "SELECT f FROM FacebookUsuario f WHERE f.updatedAt = :updatedAt")})
public class FacebookUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_usuario_facebook")
    private BigInteger idUsuarioFacebook;
    @Column(name = "username_facebook")
    private String usernameFacebook;
    @Column(name = "estado")
    private Short estado;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    @ManyToOne
    private Usuarios idUsuario;

    public FacebookUsuario() {
    }

    public FacebookUsuario(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigInteger getIdUsuarioFacebook() {
        return idUsuarioFacebook;
    }

    public void setIdUsuarioFacebook(BigInteger idUsuarioFacebook) {
        this.idUsuarioFacebook = idUsuarioFacebook;
    }

    public String getUsernameFacebook() {
        return usernameFacebook;
    }

    public void setUsernameFacebook(String usernameFacebook) {
        this.usernameFacebook = usernameFacebook;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Usuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuarios idUsuario) {
        this.idUsuario = idUsuario;
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
        if (!(object instanceof FacebookUsuario)) {
            return false;
        }
        FacebookUsuario other = (FacebookUsuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.intelidata.jpa.FacebookUsuario[ id=" + id + " ]";
    }
    
}
