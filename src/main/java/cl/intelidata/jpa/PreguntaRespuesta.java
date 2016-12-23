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
@Table(name = "pregunta_respuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PreguntaRespuesta.findAll", query = "SELECT p FROM PreguntaRespuesta p"),
    @NamedQuery(name = "PreguntaRespuesta.findById", query = "SELECT p FROM PreguntaRespuesta p WHERE p.id = :id"),
    @NamedQuery(name = "PreguntaRespuesta.findByCreatedAt", query = "SELECT p FROM PreguntaRespuesta p WHERE p.createdAt = :createdAt"),
    @NamedQuery(name = "PreguntaRespuesta.findByUpdatedAt", query = "SELECT p FROM PreguntaRespuesta p WHERE p.updatedAt = :updatedAt"),
    @NamedQuery(name = "PreguntaRespuesta.findByDeletedAt", query = "SELECT p FROM PreguntaRespuesta p WHERE p.deletedAt = :deletedAt"),
    @NamedQuery(name = "PreguntaRespuesta.findByIdQuestionIdAnswer", query = "SELECT p FROM PreguntaRespuesta p WHERE p.idPregunta.id = :idPregunta AND p.idRespuesta.id = :idRespuesta")})
public class PreguntaRespuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPreguntaRespuesta")
    private List<ClientePreguntas> clientePreguntasList;
    @JoinColumn(name = "id_pregunta", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Preguntas idPregunta;
    @JoinColumn(name = "id_respuesta", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Respuestas idRespuesta;

    public PreguntaRespuesta() {
    }

    public PreguntaRespuesta(Integer id) {
        this.id = id;
    }

    public PreguntaRespuesta(Integer id, Date createdAt, Date updatedAt) {
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

    @XmlTransient
    public List<ClientePreguntas> getClientePreguntasList() {
        return clientePreguntasList;
    }

    public void setClientePreguntasList(List<ClientePreguntas> clientePreguntasList) {
        this.clientePreguntasList = clientePreguntasList;
    }

    public Preguntas getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Preguntas idPregunta) {
        this.idPregunta = idPregunta;
    }

    public Respuestas getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(Respuestas idRespuesta) {
        this.idRespuesta = idRespuesta;
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
        if (!(object instanceof PreguntaRespuesta)) {
            return false;
        }
        PreguntaRespuesta other = (PreguntaRespuesta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.intelidata.jpa.PreguntaRespuesta[ id=" + id + " ]";
    }

}
