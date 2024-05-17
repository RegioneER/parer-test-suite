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

package it.eng.parer.test.tx;

import it.eng.parer.test.tx.helper.QueueHelper;
import it.eng.parer.test.tx.helper.XaDiskHelper;
import it.eng.parer.test.tx.helper.DBHelper;
import it.eng.parer.test.tx.dto.ConfigurationDto;
import it.eng.parer.test.tx.dto.Step;
import it.eng.parer.test.tx.exceptions.EccezioneAttesa;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;

/**
 * Implementazione predefinita di {@link TestTransactions}
 *
 * @author Snidero_L
 */
@Stateless
@Remote(TestTransactions.class)
@TransactionManagement(TransactionManagementType.BEAN)
public class TestTransactionsBeanBMT implements TestTransactions {

    /**
     * Messaggi delle eccezioni
     */
    public static final String ERR_INASPETTATO = "Errore inatteso";

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private DBHelper dbHelper;

    @EJB
    private XaDiskHelper xaDiskHelper;

    @EJB
    private QueueHelper queueHelper;

    private ExecuteOperation executeOperation;

    @PostConstruct
    public void init() {
        executeOperation = new ExecuteOperation(dbHelper, xaDiskHelper, queueHelper);
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public void testQueueProducer(ConfigurationDto config) throws EccezioneAttesa {
        try {
            userTransaction.begin();
            try {

                for (Step step : config.getSteps()) {
                    step.accept(executeOperation);
                }

                userTransaction.commit();
            } catch (Exception e) {
                userTransaction.rollback();
                throw e;
            }
        } catch (EccezioneAttesa e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(ERR_INASPETTATO, e);
        }
    }

    @Override
    public void testQueueConsumer(ConfigurationDto config) {
        try {
            userTransaction.begin();
            queueHelper.enqueue(config);
            userTransaction.commit();
        } catch (Exception ex) {
            throw new EJBException(ERR_INASPETTATO, ex);
        }
    }

    @Override
    public void testSimple(ConfigurationDto config) throws EccezioneAttesa {
        try {
            userTransaction.begin();
            try {

                for (Step step : config.getSteps()) {
                    step.accept(executeOperation);
                }

                userTransaction.commit();
            } catch (Exception e) {
                userTransaction.rollback();
                throw e;
            }
        } catch (EccezioneAttesa e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(ERR_INASPETTATO, e);
        }
    }

}
