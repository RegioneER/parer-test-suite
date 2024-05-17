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

package it.eng.parer.test.tx.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Esito del test.
 *
 * Ogni step definito in configurazione ha qui il suo esito. <strong>TODO:</strong> vedi il todo di
 * {@link ConfigurationDto}.
 *
 * @author Snidero_L
 */
public class OutcomeDto implements Serializable {

    private static final long serialVersionUID = 3785542401459609581L;

    private Map<Step, Boolean> outcomes;

    public OutcomeDto() {
        this.outcomes = new HashMap<>();
    }

    public Boolean get(Step step) {
        return outcomes.get(step);
    }

    public Boolean put(Step step, Boolean outcome) {
        return this.outcomes.put(step, outcome);
    }

    public Map<Step, Boolean> getOutcomes() {
        return outcomes;
    }

    @Override
    public String toString() {
        return "OutcomeDto{" + "outcomes=" + outcomes + '}';
    }

}
