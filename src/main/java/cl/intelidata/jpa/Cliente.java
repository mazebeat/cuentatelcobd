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
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findById", query = "SELECT c FROM Cliente c WHERE c.id = :id"),
    @NamedQuery(name = "Cliente.findByNumeroCliente", query = "SELECT c FROM Cliente c WHERE c.numeroCliente = :numeroCliente"),
    @NamedQuery(name = "Cliente.findByRut", query = "SELECT c FROM Cliente c WHERE c.rut = :rut"),
    @NamedQuery(name = "Cliente.findByTipo", query = "SELECT c FROM Cliente c WHERE c.tipo = :tipo"),
    @NamedQuery(name = "Cliente.findByClave", query = "SELECT c FROM Cliente c WHERE c.clave = :clave"),
    @NamedQuery(name = "Cliente.findByCantidadNumeros", query = "SELECT c FROM Cliente c WHERE c.cantidadNumeros = :cantidadNumeros"),
    @NamedQuery(name = "Cliente.findByRememberToken", query = "SELECT c FROM Cliente c WHERE c.rememberToken = :rememberToken"),
    @NamedQuery(name = "Cliente.findByLastLogin", query = "SELECT c FROM Cliente c WHERE c.lastLogin = :lastLogin"),
    @NamedQuery(name = "Cliente.findByCreatedAt", query = "SELECT c FROM Cliente c WHERE c.createdAt = :createdAt"),
    @NamedQuery(name = "Cliente.findByUpdatedAt", query = "SELECT c FROM Cliente c WHERE c.updatedAt = :updatedAt"),
    @NamedQuery(name = "Cliente.findByDeletedAt", query = "SELECT c FROM Cliente c WHERE c.deletedAt = :deletedAt"),
    @NamedQuery(name = "Cliente.findByFechaVencimiento", query = "SELECT c FROM Cliente c WHERE c.fechaVencimiento = :fechaVencimiento"),
    @NamedQuery(name = "Cliente.findByEstado", query = "SELECT c FROM Cliente c WHERE c.estado = :estado"),
    // TODO: Preguntar por qué es diferente a 1
    @NamedQuery(name = "Cliente.isRegister", query = "SELECT c FROM Cliente c WHERE c.personaId.id <> 1 AND c.id = :id")
})
public class Cliente implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCliente")
    private List<Settings> settingsList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "numero_cliente")
    private String numeroCliente;
    @Basic(optional = false)
    @Column(name = "rut")
    private String rut;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "clave")
    private String clave;
    @Column(name = "cantidad_numeros")
    private BigInteger cantidadNumeros;
    @Column(name = "remember_token")
    private String rememberToken;
    @Column(name = "last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;
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
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    @Column(name = "estado")
    private Integer estado;
    @OneToMany(mappedBy = "idCliente")
    private List<ResumenAnualCliente> resumenAnualClienteList;
    @OneToMany(mappedBy = "idCliente")
    private List<TotalServicios> totalServiciosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clienteId")
    private List<Hitos> hitosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCliente")
    private List<Telefono> telefonoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCliente")
    private List<ClientePreguntas> clientePreguntasList;
    @JoinColumn(name = "persona_id", referencedColumnName = "id")
    @ManyToOne
    private Persona personaId;
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empresa empresaId;
    @JoinColumn(name = "ejecutivo_id", referencedColumnName = "id")
    @ManyToOne
    private Ejecutivo ejecutivoId;

    public Cliente() {
    }

    public Cliente(Integer id) {
        this.id = id;
    }

    public Cliente(Integer id, String numeroCliente, String rut, Date createdAt, Date updatedAt) {
        this.id = id;
        this.numeroCliente = numeroCliente;
        this.rut = rut;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(String numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public BigInteger getCantidadNumeros() {
        return cantidadNumeros;
    }

    public void setCantidadNumeros(BigInteger cantidadNumeros) {
        this.cantidadNumeros = cantidadNumeros;
    }

    public String getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(String rememberToken) {
        this.rememberToken = rememberToken;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
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

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<ResumenAnualCliente> getResumenAnualClienteList() {
        return resumenAnualClienteList;
    }

    public void setResumenAnualClienteList(List<ResumenAnualCliente> resumenAnualClienteList) {
        this.resumenAnualClienteList = resumenAnualClienteList;
    }

    @XmlTransient
    public List<TotalServicios> getTotalServiciosList() {
        return totalServiciosList;
    }

    public void setTotalServiciosList(List<TotalServicios> totalServiciosList) {
        this.totalServiciosList = totalServiciosList;
    }

    @XmlTransient
    public List<Hitos> getHitosList() {
        return hitosList;
    }

    public void setHitosList(List<Hitos> hitosList) {
        this.hitosList = hitosList;
    }

    @XmlTransient
    public List<Telefono> getTelefonoList() {
        return telefonoList;
    }

    public void setTelefonoList(List<Telefono> telefonoList) {
        this.telefonoList = telefonoList;
    }

    @XmlTransient
    public List<ClientePreguntas> getClientePreguntasList() {
        return clientePreguntasList;
    }

    public void setClientePreguntasList(List<ClientePreguntas> clientePreguntasList) {
        this.clientePreguntasList = clientePreguntasList;
    }

    public Persona getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Persona personaId) {
        this.personaId = personaId;
    }

    public Empresa getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Empresa empresaId) {
        this.empresaId = empresaId;
    }

    public Ejecutivo getEjecutivoId() {
        return ejecutivoId;
    }

    public void setEjecutivoId(Ejecutivo ejecutivoId) {
        this.ejecutivoId = ejecutivoId;
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
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.intelidata.jpa.Cliente[ id=" + id + " ]";
    }

    @XmlTransient
    public List<Settings> getSettingsList() {
        return settingsList;
    }

    public void setSettingsList(List<Settings> settingsList) {
        this.settingsList = settingsList;
    }
}
