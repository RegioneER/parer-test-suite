/*
 * Engineering Ingegneria Informatica S.p.A.
 *
 * Copyright (C) 2023 Regione Emilia-Romagna
 * <p/>
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package it.eng.parer.test.tx.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.xadisk.bridge.proxies.interfaces.XAFileOutputStream;
import org.xadisk.connector.outbound.XADiskConnection;
import org.xadisk.connector.outbound.XADiskConnectionFactory;

/**
 * Helper per scrivere file tramite XaDisk.
 *
 * @author Snidero_L
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class XaDiskHelper {

    private static final Logger LOG = Logger.getLogger(XaDiskHelper.class.getName());

    @Resource(mappedName = "java:/jca/xadiskLocal")
    private XADiskConnectionFactory xadCf;

    /**
     * Scrive <strong>in transazione</strong> il contenuto (content) nel file definito dal percordo filePath.
     *
     * @param fileName
     *            nome del file che verrà registrato nella temp di jboss
     * @param content
     *            stringa
     * 
     * @throws Exception
     *             eccezione non gestita
     */
    public void writeXadisk(String fileName, String content) throws Exception {
        XADiskConnection xaDiskConnection = null;
        XAFileOutputStream out = null;
        try {
            xaDiskConnection = xadCf.getConnection();

            final String tempDir = System.getProperty("jboss.server.temp.dir");

            File file = new File(tempDir + File.separator + fileName);
            if (file.exists()) {
                xaDiskConnection.deleteFile(file);
            }
            xaDiskConnection.createFile(file, false);

            out = xaDiskConnection.createXAFileOutputStream(file, false);
            out.write(content.getBytes());
            out.flush();

        } finally {
            if (out != null) {
                out.close();
            }

            if (xaDiskConnection != null) {
                xaDiskConnection.close();
            }
        }

    }

    /**
     * Legge il file al di fuori di Xadisk.
     *
     * @param fileName
     *            nome del file da leggere
     * 
     * @return stringa contenuto del file
     */
    public String readFile(String fileName) {
        String result = null;
        try {
            final String tempDir = System.getProperty("jboss.server.temp.dir");
            File file = new File(tempDir + File.separator + fileName);
            if (file.exists()) {
                byte[] allBytes = Files.readAllBytes(file.toPath());
                result = new String(allBytes);
            }
        } catch (IOException e) {
            LOG.severe("Errore nella lettura del file");
        }
        return result;

    }

}
