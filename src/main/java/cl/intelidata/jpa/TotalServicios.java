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
@Table(name = "total_servicios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TotalServicios.findAll", query = "SELECT t FROM TotalServicios t"),
    @NamedQuery(name = "TotalServicios.findById", query = "SELECT t FROM TotalServicios t WHERE t.id = :id"),
    @NamedQuery(name = "TotalServicios.findByServiciosContratados", query = "SELECT t FROM TotalServicios t WHERE t.serviciosContratados = :serviciosContratados"),
    @NamedQuery(name = "TotalServicios.findByServiciosConsumidos", query = "SELECT t FROM TotalServicios t WHERE t.serviciosConsumidos = :serviciosConsumidos"),
    @NamedQuery(name = "TotalServicios.findByServiciosPcsNoIncluidos", query = "SELECT t FROM TotalServicios t WHERE t.serviciosPcsNoIncluidos = :serviciosPcsNoIncluidos"),
    @NamedQuery(name = "TotalServicios.findByServiciosDeTerceros", query = "SELECT t FROM TotalServicios t WHERE t.serviciosDeTerceros = :serviciosDeTerceros"),
    @NamedQuery(name = "TotalServicios.findByCobrosYDescuentos", query = "SELECT t FROM TotalServicios t WHERE t.cobrosYDescuentos = :cobrosYDescuentos"),
    @NamedQuery(name = "TotalServicios.findByTotalAfecto", query = "SELECT t FROM TotalServicios t WHERE t.totalAfecto = :totalAfecto"),
    @NamedQuery(name = "TotalServicios.findByIva", query = "SELECT t FROM TotalServicios t WHERE t.iva = :iva"),
    @NamedQuery(name = "TotalServicios.findByMontoBruto", query = "SELECT t FROM TotalServicios t WHERE t.montoBruto = :montoBruto"),
    @NamedQuery(name = "TotalServicios.findByTotalAPagar", query = "SELECT t FROM TotalServicios t WHERE t.totalAPagar = :totalAPagar"),
    @NamedQuery(name = "TotalServicios.findByUpdatedAt", query = "SELECT t FROM TotalServicios t WHERE t.updatedAt = :updatedAt"),
    @NamedQuery(name = "TotalServicios.findByCreatedAt", query = "SELECT t FROM TotalServicios t WHERE t.createdAt = :createdAt")})
public class TotalServicios implements Serializable {

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
    @Column(name = "total_afecto")
    private BigInteger totalAfecto;
    @Column(name = "iva")
    private BigInteger iva;
    @Column(name = "monto_bruto")
    private BigInteger montoBruto;
    @Column(name = "total_a_pagar")
    private BigInteger totalAPagar;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    @ManyToOne
    private Cliente idCliente;

    public TotalServicios() {
    }

    public TotalServicios(Integer id) {
        this.id = id;
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

    public BigInteger getTotalAfecto() {
        return totalAfecto;
    }

    public void setTotalAfecto(BigInteger totalAfecto) {
        this.totalAfecto = totalAfecto;
    }

    public BigInteger getIva() {
        return iva;
    }

    public void setIva(BigInteger iva) {
        this.iva = iva;
    }

    public BigInteger getMontoBruto() {
        return montoBruto;
    }

    public void setMontoBruto(BigInteger montoBruto) {
        this.montoBruto = montoBruto;
    }

    public BigInteger getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(BigInteger totalAPagar) {
        this.totalAPagar = totalAPagar;
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
        if (!(object instanceof TotalServicios)) {
            return false;
        }
        TotalServicios other = (TotalServicios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.intelidata.jpa.TotalServicios[ id=" + id + " ]";
    }
    
}
