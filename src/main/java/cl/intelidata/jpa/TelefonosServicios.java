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
@Table(name = "telefonos_servicios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TelefonosServicios.findAll", query = "SELECT t FROM TelefonosServicios t"),
    @NamedQuery(name = "TelefonosServicios.findById", query = "SELECT t FROM TelefonosServicios t WHERE t.id = :id"),
    @NamedQuery(name = "TelefonosServicios.findByServiciosContratados", query = "SELECT t FROM TelefonosServicios t WHERE t.serviciosContratados = :serviciosContratados"),
    @NamedQuery(name = "TelefonosServicios.findByServiciosConsumidos", query = "SELECT t FROM TelefonosServicios t WHERE t.serviciosConsumidos = :serviciosConsumidos"),
    @NamedQuery(name = "TelefonosServicios.findByServiciosPcsNoIncluidos", query = "SELECT t FROM TelefonosServicios t WHERE t.serviciosPcsNoIncluidos = :serviciosPcsNoIncluidos"),
    @NamedQuery(name = "TelefonosServicios.findByServiciosDeTerceros", query = "SELECT t FROM TelefonosServicios t WHERE t.serviciosDeTerceros = :serviciosDeTerceros"),
    @NamedQuery(name = "TelefonosServicios.findByCobrosYDescuentos", query = "SELECT t FROM TelefonosServicios t WHERE t.cobrosYDescuentos = :cobrosYDescuentos"),
    @NamedQuery(name = "TelefonosServicios.findByFecha", query = "SELECT t FROM TelefonosServicios t WHERE t.fecha = :fecha"),
    @NamedQuery(name = "TelefonosServicios.findByCreatedAt", query = "SELECT t FROM TelefonosServicios t WHERE t.createdAt = :createdAt"),
    @NamedQuery(name = "TelefonosServicios.findByUpdatedAt", query = "SELECT t FROM TelefonosServicios t WHERE t.updatedAt = :updatedAt"),
    @NamedQuery(name = "TelefonosServicios.findByDeletedAt", query = "SELECT t FROM TelefonosServicios t WHERE t.deletedAt = :deletedAt")})
public class TelefonosServicios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "servicios_contratados")
    private BigInteger serviciosContratados;
    @Column(name = "servicios_consumidos")
    private BigInteger serviciosConsumidos;
    @Column(name = "servicios_pcs_no_incluidos")
    private BigInteger serviciosPcsNoIncluidos;
    @Column(name = "servicios_de_terceros")
    private BigInteger serviciosDeTerceros;
    @Column(name = "cobros_y_descuentos")
    private BigInteger cobrosYDescuentos;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
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
    @JoinColumn(name = "id_telefono", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Telefono idTelefono;

    public TelefonosServicios() {
    }

    public TelefonosServicios(Integer id) {
        this.id = id;
    }

    public TelefonosServicios(Integer id, Date createdAt, Date updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigInteger getServiciosContratados() {
        return serviciosContratados;
    }

    public void setServiciosContratados(BigInteger serviciosContratados) {
        this.serviciosContratados = serviciosContratados;
    }

    public BigInteger getServiciosConsumidos() {
        return serviciosConsumidos;
    }

    public void setServiciosConsumidos(BigInteger serviciosConsumidos) {
        this.serviciosConsumidos = serviciosConsumidos;
    }

    public BigInteger getServiciosPcsNoIncluidos() {
        return serviciosPcsNoIncluidos;
    }

    public void setServiciosPcsNoIncluidos(BigInteger serviciosPcsNoIncluidos) {
        this.serviciosPcsNoIncluidos = serviciosPcsNoIncluidos;
    }

    public BigInteger getServiciosDeTerceros() {
        return serviciosDeTerceros;
    }

    public void setServiciosDeTerceros(BigInteger serviciosDeTerceros) {
        this.serviciosDeTerceros = serviciosDeTerceros;
    }

    public BigInteger getCobrosYDescuentos() {
        return cobrosYDescuentos;
    }

    public void setCobrosYDescuentos(BigInteger cobrosYDescuentos) {
        this.cobrosYDescuentos = cobrosYDescuentos;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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

    public Telefono getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(Telefono idTelefono) {
        this.idTelefono = idTelefono;
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
        if (!(object instanceof TelefonosServicios)) {
            return false;
        }
        TelefonosServicios other = (TelefonosServicios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.intelidata.jpa.TelefonosServicios[ id=" + id + " ]";
    }
    
}
