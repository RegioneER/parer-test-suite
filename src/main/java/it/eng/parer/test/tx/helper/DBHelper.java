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

import it.eng.parer.test.tx.entity.PigParamApplic;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Helper per le connessioni al db.
 *
 * @author Snidero_L
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class DBHelper {

    private static final String TEST_NM_PARAM_APPLIC = "TEST_PARAM";
    private static final String TEST_DS_VALORE_PARAM = "d-_-b";
    private static final String TEST_TI_PARAM_APPLIC = "CONFIG_CODE";

    @EJB
    private DBHelper me;

    @PersistenceContext(name = "PingJPA")
    private EntityManager em;

    /**
     * Toglie dalla tabella <strong>PIG_PARAM_APPLIC</strong> la riga con la configurazione di test (NM_PARAM_APPLIC =
     * {@link #TEST_NM_PARAM_APPLIC})
     */
    public void cleanDb() {
        PigParamApplic param = me.getParam();
        if (param != null) {
            em.remove(param);
            em.flush();
        }
    }

    /**
     * Scrive sulla tabella <strong>PIG_PARAM_APPLIC</strong> il parametro con NM_PARAM_APPLIC =
     * {@link #TEST_NM_PARAM_APPLIC} .
     *
     * @param message
     *            stringa da scrivere sulla tabella
     */
    public void writeParam(String message) {
        PigParamApplic newParam = new PigParamApplic();
        newParam.setNmParamApplic(TEST_NM_PARAM_APPLIC);
        newParam.setDsParamApplic(message);
        newParam.setDsValoreParamApplic(TEST_DS_VALORE_PARAM);
        newParam.setTiParamApplic(TEST_TI_PARAM_APPLIC);
        em.persist(newParam);
    }

    /**
     * Ottieni, se c'è, l'entity.
     *
     * @return entity PigParamApplic
     */
    public PigParamApplic getParam() {
        PigParamApplic found = null;
        TypedQuery<PigParamApplic> query = em.createQuery(
                "Select p from PigParamApplic p Where p.nmParamApplic = :nmParamApplic", PigParamApplic.class);
        query.setParameter("nmParamApplic", TEST_NM_PARAM_APPLIC);
        List<PigParamApplic> resultList = query.getResultList();
        if (resultList.size() == 1) {
            found = resultList.get(0);
        }
        return found;
    }

}
