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
package org.vaccine.manager;

import org.generaldata.MessageBundle;
import org.utils.exeption.OHDataIntegrityViolationException;
import org.utils.exeption.OHDataValidationException;
import org.utils.exeption.OHServiceException;
import org.utils.exeption.model.OHExceptionMessage;
import org.vaccine.model.Vaccine;
import org.vaccine.service.VaccineIoOperations;

import java.util.ArrayList;
import java.util.List;

public class VaccineBrowserManager {

	private VaccineIoOperations ioOperations;

	public VaccineBrowserManager(VaccineIoOperations vaccineIoOperations) {
		this.ioOperations = vaccineIoOperations;
	}

	/**
	 * Verify if the object is valid for CRUD and return a list of errors, if any.
	 *
	 * @param vaccine the {@link Vaccine object to validate
	 * @param insert {@code true} or updated {@code false}
	 * @throws OHServiceException
	 */
	protected void validateVaccine(Vaccine vaccine, boolean insert) throws OHServiceException {
		String key = vaccine.getCode();
		String description = vaccine.getDescription();
		List<OHExceptionMessage> errors = new ArrayList<>();
		if (key.isEmpty()) {
			errors.add(new OHExceptionMessage(MessageBundle.getMessage("angal.common.pleaseinsertacode.msg")));
		}
		if (key.length() > 10) {
			errors.add(new OHExceptionMessage(MessageBundle.formatMessage("angal.common.thecodeistoolongmaxchars.fmt.msg", 10)));
		}
		if (description.isEmpty()) {
			errors.add(new OHExceptionMessage(MessageBundle.getMessage("angal.common.pleaseinsertavaliddescription.msg")));
		}
		if (insert && isCodePresent(vaccine.getCode())) {
			throw new OHDataIntegrityViolationException(new OHExceptionMessage(MessageBundle.getMessage("angal.common.thecodeisalreadyinuse.msg")));
		}
		if (!errors.isEmpty()) {
			throw new OHDataValidationException(errors);
		}
	}

	/**
	 * Returns the list of {@link Vaccine}s in the DB.
	 *
	 * @return the list of {@link Vaccine}s
	 */
	public List<Vaccine> getVaccine() throws OHServiceException {
		return getVaccine(null);
	}

	/**
	 * Returns the list of {@link Vaccine}s based on vaccine type code.
	 *
	 * @param vaccineTypeCode - the type code.
	 * @return the list of {@link Vaccine}s
	 */
	public List<Vaccine> getVaccine(String vaccineTypeCode) throws OHServiceException {
		return ioOperations.getVaccine(vaccineTypeCode);
	}

	/**
	 * Inserts a new {@link Vaccine} into the DB.
	 *
	 * @param vaccine - the {@link Vaccine} object to insert
	 * @return the newly inserted {@link Vaccine} object.
	 */
	public Vaccine newVaccine(Vaccine vaccine) throws OHServiceException {
		validateVaccine(vaccine, true);
		return ioOperations.newVaccine(vaccine);
	}

	/**
	 * Updates the specified {@link Vaccine} object.
	 *
	 * @param vaccine - the {@link Vaccine} object to update.
	 * @return the updated {@link Vaccine} object.
	 */
	public Vaccine updateVaccine(Vaccine vaccine) throws OHServiceException {
		validateVaccine(vaccine, false);
		return ioOperations.updateVaccine(vaccine);
	}

	/**
	 * Deletes a {@link Vaccine} in the DB.
	 *
	 * @param vaccine - the item to delete
	 */
	public void deleteVaccine(Vaccine vaccine) throws OHServiceException {
		ioOperations.deleteVaccine(vaccine);
	}

	/**
	 * Checks if the code is already in use.
	 *
	 * @param code - the vaccine code
	 * @return {@code true} if the code is already in use, {@code false} otherwise
	 */
	public boolean isCodePresent(String code) throws OHServiceException {
		return ioOperations.isCodePresent(code);
	}

	/**
	 * Returns the {@link Vaccine} based on vaccine code.
	 *
	 * @param code - the {@link Vaccine} code.
	 * @return the {@link Vaccine}
	 */
	public Vaccine findVaccine(String code) throws OHServiceException {
		return ioOperations.findVaccine(code);
	}

}
