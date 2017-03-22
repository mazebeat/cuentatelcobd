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

package cl.intelidata.utils;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * *
 * Clase Singleton para manejo de EntityManagerFactory<br>
 *
 * @author Juan Astorga
 */
public class EntityHelper {

    private static EntityHelper entityHelper = null;
    private final EntityManagerFactory entityManagerFactory;

    public EntityHelper() {
        Map<String, String> propiedades = new HashMap();
        propiedades.put("javax.persistence.jdbc.url", AppConfiguration.getInstance().getInitParameter("javax.persistence.jdbc.url"));
        propiedades.put("javax.persistence.jdbc.user", AppConfiguration.getInstance().getInitParameter("javax.persistence.jdbc.user"));
        propiedades.put("javax.persistence.jdbc.password", AppConfiguration.getInstance().getInitParameter("javax.persistence.jdbc.password"));
        entityManagerFactory = Persistence.createEntityManagerFactory("CtaTelco_PU", propiedades);
    }

    public synchronized static EntityHelper getInstance() {
        if (entityHelper == null) {
            entityHelper = new EntityHelper();
        }
        return entityHelper;
    }

    /**
     * @return the entityManagerFactory
     */
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    /**
     * *
     * Utiliza solo una instancia de EntityManagerFactory<br>
     * mas de una satura las conexiones a la BD
     *
     * @return EntityManagerFactory
     */
    public EntityManager getEntityManager() throws Exception {
        return getEntityManagerFactory().createEntityManager();
    }

}
