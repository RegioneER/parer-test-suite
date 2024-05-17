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

import it.eng.parer.test.tx.dto.Step;
import it.eng.parer.test.tx.dto.step.DBStep;
import it.eng.parer.test.tx.dto.step.ExceptionStep;
import it.eng.parer.test.tx.dto.step.MainOperationStep;
import it.eng.parer.test.tx.dto.step.XadiskStep;
import it.eng.parer.test.tx.exceptions.EccezioneAttesa;

/**
 * Operazioni sui passi.
 *
 * Operazioni di <strong>visita</strong> e <strong>verifica</strong> sui tipi di {@link Step} supportati.
 *
 *
 * @author Snidero_L
 */
public interface StepVisitor {

    /**
     * Operazione associata al passo {@link DBStep}
     *
     * @param step
     *            passo DB
     * 
     * @throws Exception
     *             nel caso di errore non gestito
     */
    public void visit(DBStep step) throws Exception;

    /**
     * Operazione associata al passo {@link XadiskStep}
     *
     * @param step
     *            passo Xadisk
     * 
     * @throws Exception
     *             nel caso di errore non gestito
     */
    public void visit(XadiskStep step) throws Exception;

    /**
     * Operazione associata al passo {@link MainOperationStep}
     *
     * @param step
     *            passo operazione principale (accodamento o consumo)
     * 
     * @throws Exception
     *             nel caso di errore non gestito
     */
    public void visit(MainOperationStep step) throws Exception;

    /**
     * Operazione associata al passo {@link ExceptionStep}
     *
     * @param step
     *            passo eccezione
     * 
     * @throws Exception
     *             {@link EccezioneAttesa} oppure errore non gestito
     */
    public void visit(ExceptionStep step) throws Exception;

    /**
     * Verifica del passo DB.
     *
     * @param step
     *            passo DB
     * 
     * @return vero o falso
     * 
     * @throws Exception
     *             nel caso di eccezione non gestita
     */
    public boolean verify(DBStep step) throws Exception;

    /**
     * Verifica del passo DB.
     *
     * @param step
     *            passo DB
     * 
     * @return vero o falso
     * 
     * @throws Exception
     *             nel caso di eccezione non gestita
     */
    public boolean verify(XadiskStep step) throws Exception;

    /**
     * Verifica del passo MainOperation.
     *
     * @param step
     *            passo MainOperation
     * 
     * @return vero o falso
     * 
     * @throws Exception
     *             nel caso di eccezione non gestita
     */
    public boolean verify(MainOperationStep step) throws Exception;

    /**
     * Verifica del passo Eccezione.
     *
     * @param step
     *            passo Eccezione
     * 
     * @return vero o falso
     * 
     * @throws Exception
     *             Eccezione nel caso di eccezione non gestita, {@link EccezioneAttesa} altrimenti.
     */
    public boolean verify(ExceptionStep step) throws Exception;
}
