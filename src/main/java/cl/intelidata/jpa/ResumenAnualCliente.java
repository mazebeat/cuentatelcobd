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
@Table(name = "resumen_anual_cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ResumenAnualCliente.findAll", query = "SELECT r FROM ResumenAnualCliente r"),
    @NamedQuery(name = "ResumenAnualCliente.findById", query = "SELECT r FROM ResumenAnualCliente r WHERE r.id = :id"),
    @NamedQuery(name = "ResumenAnualCliente.findByTipoMes", query = "SELECT r FROM ResumenAnualCliente r WHERE r.tipoMes = :tipoMes"),
    @NamedQuery(name = "ResumenAnualCliente.findByMonto", query = "SELECT r FROM ResumenAnualCliente r WHERE r.monto = :monto"),
    @NamedQuery(name = "ResumenAnualCliente.findByEstado", query = "SELECT r FROM ResumenAnualCliente r WHERE r.estado = :estado"),
    @NamedQuery(name = "ResumenAnualCliente.findByUpdatedAt", query = "SELECT r FROM ResumenAnualCliente r WHERE r.updatedAt = :updatedAt"),
    @NamedQuery(name = "ResumenAnualCliente.findByDeletedAt", query = "SELECT r FROM ResumenAnualCliente r WHERE r.deletedAt = :deletedAt"),
    @NamedQuery(name = "ResumenAnualCliente.findByCreatedAt", query = "SELECT r FROM ResumenAnualCliente r WHERE r.createdAt = :createdAt")})
public class ResumenAnualCliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "tipo_mes")
    private String tipoMes;
    @Column(name = "monto")
    private BigInteger monto;
    @Column(name = "estado")
    private Integer estado;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    @ManyToOne
    private Cliente idCliente;
    @JoinColumn(name = "id_mes", referencedColumnName = "id")
    @ManyToOne
    private Meses idMes;

    public ResumenAnualCliente() {
    }

    public ResumenAnualCliente(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoMes() {
        return tipoMes;
    }

    public void setTipoMes(String tipoMes) {
        this.tipoMes = tipoMes;
    }

    public BigInteger getMonto() {
        return monto;
    }

    public void setMonto(BigInteger monto) {
        this.monto = monto;
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

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public Meses getIdMes() {
        return idMes;
    }

    public void setIdMes(Meses idMes) {
        this.idMes = idMes;
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
        if (!(object instanceof ResumenAnualCliente)) {
            return false;
        }
        ResumenAnualCliente other = (ResumenAnualCliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.intelidata.jpa.ResumenAnualCliente[ id=" + id + " ]";
    }
    
}
