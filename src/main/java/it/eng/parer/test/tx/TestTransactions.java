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
import it.eng.parer.test.tx.exceptions.EccezioneAttesa;

/**
 * Strumento per testare la corretta gestione delle transazioni 2pc.
 *
 * I casi da testare sono tutte le interazioni possibili tra <strong>db</strong>, <strong>filesystem (XaDisk)</strong> e
 * le <strong>code JMS</strong>. <br>
 * L'idea è che, tramite una configurazione descritta su {@link ConfigurationDto}, sia possibile simulare tutte le
 * possibili interazioni tra i 3 elementi di cui sopra e delle eccezioni applicative.
 *
 * I tipi di test sono pensati per verificare gli scenari seguenti:
 * <ul>
 * <li>Test delle transazioni dal punto di vista della <strong>produzione</strong> di un messaggio per la coda JMS</li>
 * <li>Test delle transazioni del punti vista del <strong>consumo</strong> di un messaggio della coda JMS</li>
 * <li>Test delle transazioni senza coninvolgere le code.</li>
 * </ul>
 *
 * @author Snidero_L
 */
public interface TestTransactions {

    /**
     * Nome del queueType per la coda utilizzata in questi test.
     */
    public static final String QUEUE_TYPE = "CODA-TEST-TYPE";

    /**
     * Test per capire se esiste un istanza attiva dell'ejb.
     *
     * Siccome la lookup di un ejb remoto non fornisce alcuna garanzia sul fatto che esista veramente un'istanza
     * dell'ejb stesso, questo metodo mi permette di saperlo. Questo modello mi è stato suggerito dal buon <strong>Luigi
     * Antenucci</strong>
     *
     * @return true sempre vero
     */
    public boolean isAlive();

    /**
     * Test sulle transazioni dal punto di vista del <strong>produttore</strong> della coda.
     *
     * @param config
     *            configurazione del test
     * 
     * @throws it.eng.parer.test.tx.exceptions.EccezioneAttesa
     *             eccezione descritta nella configurazione
     */
    public void testQueueProducer(ConfigurationDto config) throws EccezioneAttesa;

    /**
     * Test sulle transazioni dal punto di vista del <strong>consumatore</strong> della coda. Per poter effettuare il
     * test sul consumatore, nella coda ci deve essere anche almeno un messaggio da consumare. L'implementazione di
     * questo caso dovrà prevedere l'inserimento di un messaggio in coda.
     *
     * @param config
     *            configurazione del test
     */
    public void testQueueConsumer(ConfigurationDto config);

    /**
     * Test sulle transazioni senza coinvolgere le code.
     *
     * @param config
     *            configurazione del test
     * 
     * @throws it.eng.parer.test.tx.exceptions.EccezioneAttesa
     *             eccezione descritta nella configurazione
     */
    public void testSimple(ConfigurationDto config) throws EccezioneAttesa;

}
