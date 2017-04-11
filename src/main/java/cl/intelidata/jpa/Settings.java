/*
 * Copyright (c) 2017, Intelidata S.A.
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dev-DFeliu
 */
@Entity
@Table(name = "settings")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Settings.findAll", query = "SELECT s FROM Settings s"),
    @NamedQuery(name = "Settings.findById", query = "SELECT s FROM Settings s WHERE s.id = :id"),
    @NamedQuery(name = "Settings.findByLabel1", query = "SELECT s FROM Settings s WHERE s.label1 = :label1"),
    @NamedQuery(name = "Settings.findByLabel2", query = "SELECT s FROM Settings s WHERE s.label2 = :label2"),
    @NamedQuery(name = "Settings.findByDimension1", query = "SELECT s FROM Settings s WHERE s.dimension1 = :dimension1"),
    @NamedQuery(name = "Settings.findByDimension2", query = "SELECT s FROM Settings s WHERE s.dimension2 = :dimension2"),
    @NamedQuery(name = "Settings.findByView", query = "SELECT s FROM Settings s WHERE s.view = :view"),
    @NamedQuery(name = "Settings.findByUploaded", query = "SELECT s FROM Settings s WHERE s.uploaded = :uploaded"),
    @NamedQuery(name = "Settings.findByIdClienteView", query = "SELECT s FROM Settings s WHERE s.idCliente = :idCliente AND s.view = :view")})
public class Settings implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "label1")
    private String label1;
    @Basic(optional = false)
    @Column(name = "label2")
    private String label2;
    @Basic(optional = false)
    @Column(name = "dimension1")
    private String dimension1;
    @Basic(optional = false)
    @Column(name = "dimension2")
    private String dimension2;
    @Basic(optional = false)
    @Column(name = "view")
    private String view;
    @Basic(optional = false)
    @Column(name = "uploaded")
    private short uploaded;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Cliente idCliente;

    public Settings() {
    }

    public Settings(Integer id) {
        this.id = id;
    }

    public Settings(Integer id, String label1, String label2, String dimension1, String dimension2, String view, short uploaded) {
        this.id = id;
        this.label1 = label1;
        this.label2 = label2;
        this.dimension1 = dimension1;
        this.dimension2 = dimension2;
        this.view = view;
        this.uploaded = uploaded;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabel1() {
        return label1;
    }

    public void setLabel1(String label1) {
        this.label1 = label1;
    }

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String label2) {
        this.label2 = label2;
    }

    public String getDimension1() {
        return dimension1;
    }

    public void setDimension1(String dimension1) {
        this.dimension1 = dimension1;
    }

    public String getDimension2() {
        return dimension2;
    }

    public void setDimension2(String dimension2) {
        this.dimension2 = dimension2;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public short getUploaded() {
        return uploaded;
    }

    public void setUploaded(short uploaded) {
        this.uploaded = uploaded;
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
        if (!(object instanceof Settings)) {
            return false;
        }
        Settings other = (Settings) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.intelidata.jpa.Settings[ id=" + id + " ]";
    }
    
}
