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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dev-DFeliu
 */
@Entity
@Table(name = "conv_users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConvUsers.findAll", query = "SELECT c FROM ConvUsers c"),
    @NamedQuery(name = "ConvUsers.findByConvId", query = "SELECT c FROM ConvUsers c WHERE c.convUsersPK.convId = :convId"),
    @NamedQuery(name = "ConvUsers.findByUserId", query = "SELECT c FROM ConvUsers c WHERE c.convUsersPK.userId = :userId")})
public class ConvUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConvUsersPK convUsersPK;

    public ConvUsers() {
    }

    public ConvUsers(ConvUsersPK convUsersPK) {
        this.convUsersPK = convUsersPK;
    }

    public ConvUsers(int convId, int userId) {
        this.convUsersPK = new ConvUsersPK(convId, userId);
    }

    public ConvUsersPK getConvUsersPK() {
        return convUsersPK;
    }

    public void setConvUsersPK(ConvUsersPK convUsersPK) {
        this.convUsersPK = convUsersPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (convUsersPK != null ? convUsersPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConvUsers)) {
            return false;
        }
        ConvUsers other = (ConvUsers) object;
        if ((this.convUsersPK == null && other.convUsersPK != null) || (this.convUsersPK != null && !this.convUsersPK.equals(other.convUsersPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.intelidata.jpa.ConvUsers[ convUsersPK=" + convUsersPK + " ]";
    }
    
}
