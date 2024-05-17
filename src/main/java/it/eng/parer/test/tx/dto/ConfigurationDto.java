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

import it.eng.parer.test.tx.dto.step.ExceptionStep;
import it.eng.parer.test.tx.dto.step.MainOperationStep;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Bean di configurazione. Gli step di test sono definiti all'interno della lista steps.
 *
 * Uso: Istanziare il bean ed aggiungere gli step. Gli step verranno eseguiti <strong>nello stesso ordine</strong> con
 * cui sono stati inseriti. <br>
 * Esempio di utilizzo:
 * 
 * <pre>
 * <code>
 * // configurazione di un test che produce un'eccezione. (tutta la transazione va in rollback)
 *  ConfigurationDto config = new ConfigurationDto();
 *  String message = "Test simple con fallimento al passo 2";
 *
 *  // passo 1 - DB
 *  DBStep dbStep = new DBStep(message);
 *
 *  // passo 2 - Xadisk
 *  XadiskStep xaStep = new XadiskStep(message, "xaDiskException.txt");
 *
 *  // passo 3 - eccezione
 *  ExceptionStep excStep = new ExceptionStep(message);
 *
 *  config.addSteps(new Step[]{xaStep, excStep, dbStep});
 * </code>
 * </pre>
 *
 * @author Snidero_L
 */
public class ConfigurationDto implements Serializable {

    private static final long serialVersionUID = 6059707867308894312L;

    /**
     * Passi di test possibli. {@link MainOperationStep} ha senso solo nel caso di lavoro con le code.
     */
    private List<Step> steps;

    public ConfigurationDto() {
        this.steps = new ArrayList<>();
    }

    /**
     * Aggiunta di un passo di test.
     *
     * @param step
     *            passo singolo
     */
    public void addStep(Step step) {
        this.steps.add(step);
    }

    /**
     * Aggiunta dei passi di test.
     *
     * @param steps
     *            lista di passi. L'ordine di inserimento è garantito.
     */
    public void addSteps(Step[] steps) {
        this.steps.addAll(Arrays.asList(steps));
    }

    /**
     * Lista dei passi configurati.
     * 
     * @return Lista ordinata di passi
     */
    public List<Step> getSteps() {
        return steps;
    }

    /**
     * E' stata configurata almeno un eccezione?
     *
     * @return true o false
     */
    public boolean hasException() {
        // return steps.stream().anyMatch((step) -> (step instanceof ExceptionStep));
        for (Step step : steps) {
            if (step instanceof ExceptionStep) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        final String q = "\"";
        final String d = " : ";
        final String c = ",\n";

        StringBuilder sb = new StringBuilder("{");
        sb.append(q).append("Configurazione del test").append(q);
        sb.append(d);
        sb.append("\n{");
        sb.append(q).append("passi").append(q);
        sb.append(d);
        sb.append("\n[ ");
        boolean isFirst = true;
        for (Step step : this.steps) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(c);
            }
            sb.append(q).append(step.toString()).append(q);
        }
        sb.append("\n}");
        return sb.toString();
    }

}
