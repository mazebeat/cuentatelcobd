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
import javax.persistence.Lob;
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
@Table(name = "api_logs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ApiLogs.findAll", query = "SELECT a FROM ApiLogs a"),
    @NamedQuery(name = "ApiLogs.findById", query = "SELECT a FROM ApiLogs a WHERE a.id = :id"),
    @NamedQuery(name = "ApiLogs.findByApiKeyId", query = "SELECT a FROM ApiLogs a WHERE a.apiKeyId = :apiKeyId"),
    @NamedQuery(name = "ApiLogs.findByRoute", query = "SELECT a FROM ApiLogs a WHERE a.route = :route"),
    @NamedQuery(name = "ApiLogs.findByMethod", query = "SELECT a FROM ApiLogs a WHERE a.method = :method"),
    @NamedQuery(name = "ApiLogs.findByIpAddress", query = "SELECT a FROM ApiLogs a WHERE a.ipAddress = :ipAddress"),
    @NamedQuery(name = "ApiLogs.findByCreatedAt", query = "SELECT a FROM ApiLogs a WHERE a.createdAt = :createdAt"),
    @NamedQuery(name = "ApiLogs.findByUpdatedAt", query = "SELECT a FROM ApiLogs a WHERE a.updatedAt = :updatedAt")})
public class ApiLogs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "api_key_id")
    private int apiKeyId;
    @Basic(optional = false)
    @Column(name = "route")
    private String route;
    @Basic(optional = false)
    @Column(name = "method")
    private String method;
    @Basic(optional = false)
    @Lob
    @Column(name = "params")
    private String params;
    @Basic(optional = false)
    @Column(name = "ip_address")
    private String ipAddress;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public ApiLogs() {
    }

    public ApiLogs(Integer id) {
        this.id = id;
    }

    public ApiLogs(Integer id, int apiKeyId, String route, String method, String params, String ipAddress, Date createdAt, Date updatedAt) {
        this.id = id;
        this.apiKeyId = apiKeyId;
        this.route = route;
        this.method = method;
        this.params = params;
        this.ipAddress = ipAddress;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getApiKeyId() {
        return apiKeyId;
    }

    public void setApiKeyId(int apiKeyId) {
        this.apiKeyId = apiKeyId;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ApiLogs)) {
            return false;
        }
        ApiLogs other = (ApiLogs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.intelidata.jpa.ApiLogs[ id=" + id + " ]";
    }
    
}
