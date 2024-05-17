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

import it.eng.parer.test.tx.StepVisitor;
import java.io.Serializable;
import java.util.Objects;

/**
 * Passo generico. Le implementazioni standard sono contenuti nel sotto-package <strong>step</strong>
 *
 * @author Snidero_L
 */
public abstract class Step implements Serializable {

    private static final long serialVersionUID = 5860150015753154982L;

    protected String message;

    public Step(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Step{" + "message=" + message + '}';
    }

    public abstract void accept(StepVisitor visitor) throws Exception;

    public abstract boolean verify(StepVisitor verifier) throws Exception;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.message);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Step other = (Step) obj;
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        return true;
    }

}
