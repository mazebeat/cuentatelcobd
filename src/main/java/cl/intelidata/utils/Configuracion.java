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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuracion {

    private Properties datos = new Properties();
    private String nameFileConf;
    private static Configuracion conf = null;
    private static Logger logger = LoggerFactory.getLogger(Configuracion.class);
    private static String archConf = "/apps/CtaTelco/config/CuentaTelco.properties";

    /**
     * *
     * Para ser utilizado dentro de una app standalone
     *
     * @param nameFileConfiguracion
     * @return
     */
    public synchronized static Configuracion getInstance() {
        if (conf == null) {
            try {
                if (!System.getProperty("file.separator").equals("/")) {
                    archConf = System.getProperty("disco", "c:") + archConf;
                }

                conf = new Configuracion(archConf);

                logger.info("Leyendo archivo de configuracion: " + archConf);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        }
        return conf;
    }

    private Configuracion(String nameFileConf) throws IOException {
        this.nameFileConf = nameFileConf;
        try (FileInputStream fi = new FileInputStream(nameFileConf)) {
            datos.load(fi);
        }
    }

    public String getInitParameter(String name) {
        if (!datos.containsKey(name.toLowerCase(new Locale("es", "CL")))) {
            logger.error(String.format("GRAVE Error en configuracion llave %s no definida en archivo %s", name, nameFileConf));
            return null;
        }
        String valor = datos.getProperty(name.toLowerCase(new Locale("es", "CL")));
        logger.debug(String.format("%s=%s", name, valor));
        return valor;
    }

    /**
     * Agrega el parametro y actualiza el archivo de configuracion
     *
     * @param key
     * @param value
     */
    public void setInitParameter(String key, String value) {
        datos.put(key.toLowerCase(new Locale("es", "CL")), value);

        try (FileOutputStream fw = new FileOutputStream(archConf)) {
            datos.store(fw, "Modif by appp - " + new Date());
        } catch (IOException ex) {
            logger.error("Error modif properties by app " + archConf, ex);
        }

    }
}
