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
@Table(name = "persona")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM Persona p"),
    @NamedQuery(name = "Persona.findById", query = "SELECT p FROM Persona p WHERE p.id = :id"),
    @NamedQuery(name = "Persona.findByNombre", query = "SELECT p FROM Persona p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Persona.findByApellidos", query = "SELECT p FROM Persona p WHERE p.apellidos = :apellidos"),
    @NamedQuery(name = "Persona.findByEmailPersonal", query = "SELECT p FROM Persona p WHERE p.emailPersonal = :emailPersonal"),
    @NamedQuery(name = "Persona.findByDireccionPersonal", query = "SELECT p FROM Persona p WHERE p.direccionPersonal = :direccionPersonal"),
    @NamedQuery(name = "Persona.findByDireccionWork", query = "SELECT p FROM Persona p WHERE p.direccionWork = :direccionWork"),
    @NamedQuery(name = "Persona.findByTelefonoFijoPersonal", query = "SELECT p FROM Persona p WHERE p.telefonoFijoPersonal = :telefonoFijoPersonal"),
    @NamedQuery(name = "Persona.findByTelefonoFijoWork", query = "SELECT p FROM Persona p WHERE p.telefonoFijoWork = :telefonoFijoWork"),
    @NamedQuery(name = "Persona.findByCelularPersonal", query = "SELECT p FROM Persona p WHERE p.celularPersonal = :celularPersonal"),
    @NamedQuery(name = "Persona.findByCelularWork", query = "SELECT p FROM Persona p WHERE p.celularWork = :celularWork"),
    @NamedQuery(name = "Persona.findByEmailWork", query = "SELECT p FROM Persona p WHERE p.emailWork = :emailWork"),
    @NamedQuery(name = "Persona.findByFacebook", query = "SELECT p FROM Persona p WHERE p.facebook = :facebook"),
    @NamedQuery(name = "Persona.findByTwitter", query = "SELECT p FROM Persona p WHERE p.twitter = :twitter"),
    @NamedQuery(name = "Persona.findBySkype", query = "SELECT p FROM Persona p WHERE p.skype = :skype"),
    @NamedQuery(name = "Persona.findByCreatedAt", query = "SELECT p FROM Persona p WHERE p.createdAt = :createdAt"),
    @NamedQuery(name = "Persona.findByUpdatedAt", query = "SELECT p FROM Persona p WHERE p.updatedAt = :updatedAt"),
    @NamedQuery(name = "Persona.findByDeletedAt", query = "SELECT p FROM Persona p WHERE p.deletedAt = :deletedAt"),
    @NamedQuery(name = "Persona.findByUrlImagen", query = "SELECT p FROM Persona p WHERE p.urlImagen = :urlImagen")})
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "email_personal")
    private String emailPersonal;
    @Column(name = "direccion_personal")
    private String direccionPersonal;
    @Column(name = "direccion_work")
    private String direccionWork;
    @Column(name = "telefono_fijo_personal")
    private String telefonoFijoPersonal;
    @Column(name = "telefono_fijo_work")
    private String telefonoFijoWork;
    @Column(name = "celular_personal")
    private String celularPersonal;
    @Column(name = "celular_work")
    private String celularWork;
    @Column(name = "email_work")
    private String emailWork;
    @Column(name = "facebook")
    private String facebook;
    @Column(name = "twitter")
    private String twitter;
    @Column(name = "skype")
    private String skype;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Basic(optional = false)
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    @Column(name = "url_imagen")
    private String urlImagen;
    @OneToMany(mappedBy = "personaId")
    private List<Cliente> clienteList;

    public Persona() {
    }

    public Persona(Integer id) {
        this.id = id;
    }

    public Persona(Integer id, Date createdAt, Date updatedAt, Date deletedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmailPersonal() {
        return emailPersonal;
    }

    public void setEmailPersonal(String emailPersonal) {
        this.emailPersonal = emailPersonal;
    }

    public String getDireccionPersonal() {
        return direccionPersonal;
    }

    public void setDireccionPersonal(String direccionPersonal) {
        this.direccionPersonal = direccionPersonal;
    }

    public String getDireccionWork() {
        return direccionWork;
    }

    public void setDireccionWork(String direccionWork) {
        this.direccionWork = direccionWork;
    }

    public String getTelefonoFijoPersonal() {
        return telefonoFijoPersonal;
    }

    public void setTelefonoFijoPersonal(String telefonoFijoPersonal) {
        this.telefonoFijoPersonal = telefonoFijoPersonal;
    }

    public String getTelefonoFijoWork() {
        return telefonoFijoWork;
    }

    public void setTelefonoFijoWork(String telefonoFijoWork) {
        this.telefonoFijoWork = telefonoFijoWork;
    }

    public String getCelularPersonal() {
        return celularPersonal;
    }

    public void setCelularPersonal(String celularPersonal) {
        this.celularPersonal = celularPersonal;
    }

    public String getCelularWork() {
        return celularWork;
    }

    public void setCelularWork(String celularWork) {
        this.celularWork = celularWork;
    }

    public String getEmailWork() {
        return emailWork;
    }

    public void setEmailWork(String emailWork) {
        this.emailWork = emailWork;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
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

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
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
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.intelidata.jpa.Persona[ id=" + id + " ]";
    }
    
}
