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

import it.eng.parer.test.tx.dto.step.DBStep;
import it.eng.parer.test.tx.dto.step.ExceptionStep;
import it.eng.parer.test.tx.dto.step.MainOperationStep;
import it.eng.parer.test.tx.dto.step.XadiskStep;
import it.eng.parer.test.tx.entity.PigParamApplic;
import it.eng.parer.test.tx.exceptions.EccezioneAttesa;
import it.eng.parer.test.tx.helper.DBHelper;
import it.eng.parer.test.tx.helper.QueueHelper;
import it.eng.parer.test.tx.helper.XaDiskHelper;
import java.util.logging.Logger;

/**
 * Esecuzione e validazione delle operazioni.
 *
 * Implementazione utilizzando il pattern Visitor:
 *
 * {
 *
 * @see <a href="https://it.wikipedia.org/wiki/Visitor">Pattern Visitor</a>
 *
 * @see <a href=
 *      "http://stackoverflow.com/questions/29458676/how-to-avoid-instanceof-when-implementing-factory-design-pattern">Implementazione
 *      del pattern</a>
 *
 * @author Snidero_L
 */
public class ExecuteOperation implements StepVisitor {

    private static final Logger LOG = Logger.getLogger(ExecuteOperation.class.getName());

    private DBHelper dbHelper;
    private XaDiskHelper xaDiskHelper;
    private QueueHelper queueHelper;

    public ExecuteOperation(DBHelper dbHelper, XaDiskHelper xaDiskHelper, QueueHelper queueHelper) {
        this.dbHelper = dbHelper;
        this.xaDiskHelper = xaDiskHelper;
        this.queueHelper = queueHelper;
    }

    @Override
    public void visit(DBStep step) throws Exception {
        dbHelper.cleanDb();
        dbHelper.writeParam(step.getMessage());
    }

    @Override
    public void visit(XadiskStep step) throws Exception {
        xaDiskHelper.writeXadisk(step.getFileName(), step.getMessage());
    }

    @Override
    public void visit(MainOperationStep step) throws Exception {
        if (step.getOperationType().equals(MainOperationStep.OperationType.CONSUME)) {
            LOG.info(step.getMessage());
            return;
        }
        if (step.getOperationType().equals(MainOperationStep.OperationType.PRODUCE)) {
            queueHelper.enqueue(step.getMessage());
        }

    }

    @Override
    public void visit(ExceptionStep step) throws Exception {
        throw new EccezioneAttesa("Eccezione (pilotata) al passo ");
    }

    @Override
    public boolean verify(DBStep step) throws Exception {
        PigParamApplic param = dbHelper.getParam();
        boolean result = param != null && param.getDsParamApplic().equals(step.getMessage());
        return result;
    }

    @Override
    public boolean verify(XadiskStep step) throws Exception {
        String readFile = xaDiskHelper.readFile(step.getFileName());
        boolean result = readFile != null && readFile.equals(step.getMessage());
        return result;
    }

    @Override
    public boolean verify(MainOperationStep step) throws Exception {
        // FIXME: scrivere sul db ?
        return true;
    }

    @Override
    public boolean verify(ExceptionStep step) throws Exception {
        return true;
    }

}
