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
package it.eng.parer.test.tx.exceptions;

import javax.ejb.ApplicationException;

/**
 * Application Exception. Questa eccezione Dovrebbe arrivare così com'è al client.
 *
 * @author Snidero_L
 */
@ApplicationException(rollback = true)
public class EccezioneAttesa extends Exception {

    private static final long serialVersionUID = 3226145176715222397L;

    public EccezioneAttesa(String message, Throwable cause) {
        super(message, cause);
    }

    public EccezioneAttesa(String message) {
        super(message);
    }
}
