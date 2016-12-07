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
@Table(name = "saldos_anteriores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaldosAnteriores.findAll", query = "SELECT s FROM SaldosAnteriores s"),
    @NamedQuery(name = "SaldosAnteriores.findById", query = "SELECT s FROM SaldosAnteriores s WHERE s.id = :id"),
    @NamedQuery(name = "SaldosAnteriores.findByDocumento", query = "SELECT s FROM SaldosAnteriores s WHERE s.documento = :documento"),
    @NamedQuery(name = "SaldosAnteriores.findByFolio", query = "SELECT s FROM SaldosAnteriores s WHERE s.folio = :folio"),
    @NamedQuery(name = "SaldosAnteriores.findByFechaVencimiento", query = "SELECT s FROM SaldosAnteriores s WHERE s.fechaVencimiento = :fechaVencimiento"),
    @NamedQuery(name = "SaldosAnteriores.findByTotal", query = "SELECT s FROM SaldosAnteriores s WHERE s.total = :total"),
    @NamedQuery(name = "SaldosAnteriores.findByIdCliente", query = "SELECT s FROM SaldosAnteriores s WHERE s.idCliente = :idCliente"),
    @NamedQuery(name = "SaldosAnteriores.findByUpdatedAt", query = "SELECT s FROM SaldosAnteriores s WHERE s.updatedAt = :updatedAt"),
    @NamedQuery(name = "SaldosAnteriores.findByCreatedAt", query = "SELECT s FROM SaldosAnteriores s WHERE s.createdAt = :createdAt"),
    @NamedQuery(name = "SaldosAnteriores.findByDeletedAt", query = "SELECT s FROM SaldosAnteriores s WHERE s.deletedAt = :deletedAt")})
public class SaldosAnteriores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "documento")
    private String documento;
    @Column(name = "folio")
    private String folio;
    @Column(name = "fecha_vencimiento")
    private String fechaVencimiento;
    @Column(name = "total")
    private BigInteger total;
    @Column(name = "id_cliente")
    private Integer idCliente;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    public SaldosAnteriores() {
    }

    public SaldosAnteriores(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigInteger getTotal() {
        return total;
    }

    public void setTotal(BigInteger total) {
        this.total = total;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaldosAnteriores)) {
            return false;
        }
        SaldosAnteriores other = (SaldosAnteriores) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.intelidata.jpa.SaldosAnteriores[ id=" + id + " ]";
    }
    
}
