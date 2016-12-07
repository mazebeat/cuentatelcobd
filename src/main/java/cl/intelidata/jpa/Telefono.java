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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "telefono")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Telefono.findAll", query = "SELECT t FROM Telefono t"),
    @NamedQuery(name = "Telefono.findById", query = "SELECT t FROM Telefono t WHERE t.id = :id"),
    @NamedQuery(name = "Telefono.findByIdTitularAdicional", query = "SELECT t FROM Telefono t WHERE t.idTitularAdicional = :idTitularAdicional"),
    @NamedQuery(name = "Telefono.findByIdProducto", query = "SELECT t FROM Telefono t WHERE t.idProducto = :idProducto"),
    @NamedQuery(name = "Telefono.findByNumero", query = "SELECT t FROM Telefono t WHERE t.numero = :numero"),
    @NamedQuery(name = "Telefono.findByInformacionAl", query = "SELECT t FROM Telefono t WHERE t.informacionAl = :informacionAl"),
    @NamedQuery(name = "Telefono.findByInicioFac", query = "SELECT t FROM Telefono t WHERE t.inicioFac = :inicioFac"),
    @NamedQuery(name = "Telefono.findByFinFac", query = "SELECT t FROM Telefono t WHERE t.finFac = :finFac"),
    @NamedQuery(name = "Telefono.findByCreatedAt", query = "SELECT t FROM Telefono t WHERE t.createdAt = :createdAt"),
    @NamedQuery(name = "Telefono.findByUpdatedAt", query = "SELECT t FROM Telefono t WHERE t.updatedAt = :updatedAt"),
    @NamedQuery(name = "Telefono.findByDeletedAt", query = "SELECT t FROM Telefono t WHERE t.deletedAt = :deletedAt"),
    @NamedQuery(name = "Telefono.findByEstado", query = "SELECT t FROM Telefono t WHERE t.estado = :estado")})
public class Telefono implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "id_titular_adicional")
    private int idTitularAdicional;
    @Basic(optional = false)
    @Column(name = "id_producto")
    private int idProducto;
    @Basic(optional = false)
    @Column(name = "numero")
    private String numero;
    @Column(name = "informacion_al")
    @Temporal(TemporalType.DATE)
    private Date informacionAl;
    @Column(name = "inicio_fac")
    @Temporal(TemporalType.DATE)
    private Date inicioFac;
    @Column(name = "fin_fac")
    @Temporal(TemporalType.DATE)
    private Date finFac;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    @Column(name = "estado")
    private Integer estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTelefono")
    private List<TelefonosServicios> telefonosServiciosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTelefono")
    private List<Total> totalList;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cliente idCliente;

    public Telefono() {
    }

    public Telefono(Integer id) {
        this.id = id;
    }

    public Telefono(Integer id, int idTitularAdicional, int idProducto, String numero, Date createdAt, Date updatedAt) {
        this.id = id;
        this.idTitularAdicional = idTitularAdicional;
        this.idProducto = idProducto;
        this.numero = numero;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdTitularAdicional() {
        return idTitularAdicional;
    }

    public void setIdTitularAdicional(int idTitularAdicional) {
        this.idTitularAdicional = idTitularAdicional;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getInformacionAl() {
        return informacionAl;
    }

    public void setInformacionAl(Date informacionAl) {
        this.informacionAl = informacionAl;
    }

    public Date getInicioFac() {
        return inicioFac;
    }

    public void setInicioFac(Date inicioFac) {
        this.inicioFac = inicioFac;
    }

    public Date getFinFac() {
        return finFac;
    }

    public void setFinFac(Date finFac) {
        this.finFac = finFac;
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

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<TelefonosServicios> getTelefonosServiciosList() {
        return telefonosServiciosList;
    }

    public void setTelefonosServiciosList(List<TelefonosServicios> telefonosServiciosList) {
        this.telefonosServiciosList = telefonosServiciosList;
    }

    @XmlTransient
    public List<Total> getTotalList() {
        return totalList;
    }

    public void setTotalList(List<Total> totalList) {
        this.totalList = totalList;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
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
        if (!(object instanceof Telefono)) {
            return false;
        }
        Telefono other = (Telefono) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.intelidata.jpa.Telefono[ id=" + id + " ]";
    }
    
}
