/*
 * Open Hospital (www.open-hospital.org)
 * Copyright © 2006-2023 Informatici Senza Frontiere (info@informaticisenzafrontiere.org)
 *
 * Open Hospital is a free and open source software for healthcare data management.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * https://www.gnu.org/licenses/gpl-3.0-standalone.html
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package org.vaccine.model;



public class VaccineType{


    private String code;


    private String description;


    private volatile int hashCode;

    public VaccineType()
    {
        super();
    }

    public VaccineType(String code, String description) {
        super();
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object anObject) {
        return anObject instanceof VaccineType
                && (getCode().equals(((VaccineType) anObject).getCode())
                && getDescription().equalsIgnoreCase(((VaccineType) anObject).getDescription()));
    }

    public String print() {
        return "vaccineType code=." + getCode() + ". description=." + getDescription() + '.';
    }

    @Override
    public String toString() {
        return getDescription();
    }

    @Override
    public int hashCode() {
        if (this.hashCode == 0) {
            final int m = 23;
            int c = 133;

            c = m * c + code.hashCode();

            this.hashCode = c;
        }

        return this.hashCode;
    }
}
