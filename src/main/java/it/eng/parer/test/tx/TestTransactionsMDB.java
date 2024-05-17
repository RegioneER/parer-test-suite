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

import it.eng.parer.test.tx.dto.ConfigurationDto;
import it.eng.parer.test.tx.dto.Step;
import it.eng.parer.test.tx.helper.DBHelper;
import it.eng.parer.test.tx.helper.QueueHelper;
import it.eng.parer.test.tx.helper.XaDiskHelper;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.transaction.UserTransaction;

/**
 * Consumer per la coda di TEST {@link TestTransactions#QUEUE_TYPE}
 *
 * @author Snidero_L
 */
@MessageDriven(name = "TestTransactionsMDB", activationConfig = {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "queueType = '"
                + TestTransactions.QUEUE_TYPE + "'"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/ProducerCodaVersQueue") })
@TransactionManagement(TransactionManagementType.BEAN)
public class TestTransactionsMDB implements MessageListener {

    private static final Logger LOG = Logger.getLogger(TestTransactionsMDB.class.getName());

    @Resource
    private UserTransaction userTransaction;

    @EJB
    private XaDiskHelper xaDiskHelper;

    @EJB
    private DBHelper dbHelper;

    @EJB
    private QueueHelper queueHelper;

    @Override
    public void onMessage(Message message) {
        try {
            userTransaction.begin();
            try {

                if (message instanceof ObjectMessage) {
                    ObjectMessage objMessage = (ObjectMessage) message;
                    Object realObject = objMessage.getObject();

                    // Se l'oggetto è di tipo ConfigurationDto, sto testando il consumer
                    if (realObject instanceof ConfigurationDto) {
                        ConfigurationDto config = (ConfigurationDto) realObject;

                        ExecuteOperation executeOperation = new ExecuteOperation(dbHelper, xaDiskHelper, queueHelper);
                        for (Step step : config.getSteps()) {
                            step.accept(executeOperation);
                        }
                    } else {
                        // Se l'oggetto è di tipo String, sto testando il producer
                        if (realObject instanceof String) {
                            // FIXME: scrivere sul db ?
                            LOG.info((String) realObject);
                        }
                    }

                    userTransaction.commit();
                }
            } catch (JMSException ex) {
                userTransaction.rollback();
                throw ex;
            }
        } catch (Exception ex) {
            // questa eccezione arriva al container
            throw new EJBException(ex);
        }

    }

}
