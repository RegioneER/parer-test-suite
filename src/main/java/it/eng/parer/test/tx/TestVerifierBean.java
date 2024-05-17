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
import it.eng.parer.test.tx.dto.Step;
import it.eng.parer.test.tx.helper.DBHelper;
import it.eng.parer.test.tx.helper.QueueHelper;
import it.eng.parer.test.tx.helper.XaDiskHelper;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Implementazione predefinita di {@link TestVerifier}
 *
 * @author Snidero_L
 */
@Stateless
@Remote(TestVerifier.class)
public class TestVerifierBean implements TestVerifier {

    @EJB
    private DBHelper dbHelper;

    @EJB
    private XaDiskHelper xaDiskHelper;

    @EJB
    private QueueHelper queueHelper;

    private ExecuteOperation validator;

    @PostConstruct
    public void initValidator() {
        validator = new ExecuteOperation(dbHelper, xaDiskHelper, queueHelper);
    }

    @Override
    public OutcomeDto verifyTest(ConfigurationDto config) {

        OutcomeDto outcome = new OutcomeDto();
        try {

            final boolean hasException = config.hasException();

            for (Step step : config.getSteps()) {
                // TODO: spiegare...
                boolean result = step.verify(validator);
                if (!hasException) {
                    outcome.put(step, result);
                } else {
                    outcome.put(step, !result);
                }
            }
        } catch (Exception e) {
            throw new EJBException(TestTransactionsBeanBMT.ERR_INASPETTATO, e);
        }
        return outcome;
    }
}
