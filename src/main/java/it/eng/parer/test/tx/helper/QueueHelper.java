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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.eng.parer.test.tx.helper;

import it.eng.parer.test.tx.dto.ConfigurationDto;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import static it.eng.parer.test.tx.TestTransactions.QUEUE_TYPE;
import java.io.Serializable;

/**
 * Helper per il testing delle code.
 *
 * @author Snidero_L
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class QueueHelper {

    @Resource(mappedName = "java:/JmsXA")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "java:/jms/queue/ProducerCodaVersQueue")
    private Queue queue;

    /**
     * Accoda il messaggio. Nel caso del <strong>consumer</strong> il messaggio è di tipo {@link ConfigurationDto} .
     * <br>
     * Nel caso del <strong>producer</strong> il messaggio è di tipo {@link String}
     *
     * @param object
     *            Stringa oppure ConfigurationDto
     * 
     * @throws JMSException
     *             eccezione jms
     */
    public void enqueue(Serializable object) throws JMSException {
        MessageProducer messageProducer;
        ObjectMessage objmessage;
        Connection connection = connectionFactory.createConnection();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        messageProducer = session.createProducer(queue);
        objmessage = session.createObjectMessage();
        objmessage.setStringProperty("queueType", QUEUE_TYPE);

        objmessage.setObject(object);

        messageProducer.send(objmessage);

        messageProducer.close();
        // session.commit();
        session.close();
        connection.close();
    }

}
