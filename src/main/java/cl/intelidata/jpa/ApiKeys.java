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
@Table(name = "api_keys")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ApiKeys.findAll", query = "SELECT a FROM ApiKeys a"),
    @NamedQuery(name = "ApiKeys.findById", query = "SELECT a FROM ApiKeys a WHERE a.id = :id"),
    @NamedQuery(name = "ApiKeys.findByUserId", query = "SELECT a FROM ApiKeys a WHERE a.userId = :userId"),
    @NamedQuery(name = "ApiKeys.findByKey", query = "SELECT a FROM ApiKeys a WHERE a.key = :key"),
    @NamedQuery(name = "ApiKeys.findByLevel", query = "SELECT a FROM ApiKeys a WHERE a.level = :level"),
    @NamedQuery(name = "ApiKeys.findByIgnoreLimits", query = "SELECT a FROM ApiKeys a WHERE a.ignoreLimits = :ignoreLimits"),
    @NamedQuery(name = "ApiKeys.findByCreatedAt", query = "SELECT a FROM ApiKeys a WHERE a.createdAt = :createdAt"),
    @NamedQuery(name = "ApiKeys.findByUpdatedAt", query = "SELECT a FROM ApiKeys a WHERE a.updatedAt = :updatedAt"),
    @NamedQuery(name = "ApiKeys.findByDeletedAt", query = "SELECT a FROM ApiKeys a WHERE a.deletedAt = :deletedAt")})
public class ApiKeys implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "user_id")
    private int userId;
    @Basic(optional = false)
    @Column(name = "key")
    private String key;
    @Basic(optional = false)
    @Column(name = "level")
    private short level;
    @Basic(optional = false)
    @Column(name = "ignore_limits")
    private boolean ignoreLimits;
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

    public ApiKeys() {
    }

    public ApiKeys(Integer id) {
        this.id = id;
    }

    public ApiKeys(Integer id, int userId, String key, short level, boolean ignoreLimits, Date createdAt, Date updatedAt, Date deletedAt) {
        this.id = id;
        this.userId = userId;
        this.key = key;
        this.level = level;
        this.ignoreLimits = ignoreLimits;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public boolean getIgnoreLimits() {
        return ignoreLimits;
    }

    public void setIgnoreLimits(boolean ignoreLimits) {
        this.ignoreLimits = ignoreLimits;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ApiKeys)) {
            return false;
        }
        ApiKeys other = (ApiKeys) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.intelidata.jpa.ApiKeys[ id=" + id + " ]";
    }
    
}
