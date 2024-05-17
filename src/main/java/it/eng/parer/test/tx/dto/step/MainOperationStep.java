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

package it.eng.parer.test.tx.dto.step;

import it.eng.parer.test.tx.StepVisitor;
import it.eng.parer.test.tx.dto.Step;

/**
 *
 * @author Snidero_L
 */
public class MainOperationStep extends Step {

    private static final long serialVersionUID = 4121142510567998163L;

    public enum OperationType {
        PRODUCE, CONSUME
    };

    private OperationType operationType;

    public MainOperationStep(String message, OperationType operationType) {
        super(message);
        this.operationType = operationType;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    @Override
    public void accept(StepVisitor visitor) throws Exception {
        visitor.visit(this);
    }

    @Override
    public boolean verify(StepVisitor verifier) throws Exception {
        return verifier.verify(this);
    }

    @Override
    public String toString() {
        return "Step{" + "message=" + message + ",operationType=" + operationType.name() + "}";
    }

}
