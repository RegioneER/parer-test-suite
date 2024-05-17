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
import it.eng.parer.test.tx.dto.OutcomeDto;

/**
 * Verifica delle operazioni.
 *
 * Tramite i metodi esposti da questa interfaccia è possibile verificare la corretta esecuzione dei test sulle
 * transazioni.
 *
 * @author Snidero_L
 */
public interface TestVerifier {

    /**
     * Verifica che le operazioni specificate nella configurazione siano state eseguite correttamente.
     *
     * @param config
     *            configurazione del test
     * 
     * @return esito vedi {@link OutcomeDto}
     */
    public OutcomeDto verifyTest(ConfigurationDto config);
}
