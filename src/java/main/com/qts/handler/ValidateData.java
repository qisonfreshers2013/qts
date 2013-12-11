package com.qts.handler;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.InvalidTimeEntryDataException;
import com.qts.exception.ObjectNotFoundException;
import com.qts.model.TimeEntriesForm;

public class ValidateData {

	public ValidateData() {
		// TODO Auto-generated constructor stub
	}

	public static boolean validateDate(String formdata)
			throws InvalidTimeEntryDataException {
		if (formdata == null) {
			throw new InvalidTimeEntryDataException(
					ExceptionCodes.DATE_CANNOT_BE_NULL,
					ExceptionMessages.DATE_CANNOT_BE_NULL);
		} else {
			if (formdata.length() != 10) {
				throw new InvalidTimeEntryDataException(
						ExceptionCodes.DATE_LENGTH_MISMATCH,
						ExceptionMessages.DATE_LENGTH_MISMATCH);
			} else { // Exception for format
				if (formdata.charAt(2) == '-'
						|| formdata.charAt(5) == '-') {
					throw new InvalidTimeEntryDataException(
							ExceptionCodes.DATE_FORMAT_EXCEPTION,
							ExceptionMessages.INVALID_DATE_PATTERN);
				}
				if (Integer.parseInt(formdata.substring(0, 2)) <= 12
						&& Integer.parseInt(formdata.substring(0, 2)) != 0) {
					if (!(Integer.parseInt(formdata.substring(0, 2)) >= (Calendar
							.getInstance().get(Calendar.MONTH)))) {
						throw new InvalidTimeEntryDataException(
								ExceptionCodes.DATE_FORMAT_EXCEPTION,
								ExceptionMessages.DATE_MONTH_EXCEPTION);// Exception
																		// for
					} // invalid
				} // Month
				else {
					throw new InvalidTimeEntryDataException(
							ExceptionCodes.DATE_FORMAT_EXCEPTION,
							ExceptionMessages.DATE_MONTH_EXCEPTION);
				}
				if (Integer.parseInt(formdata.substring(0, 2)) == 1
						|| Integer.parseInt(formdata.substring(0, 2)) == 3
						|| Integer.parseInt(formdata.substring(0, 2)) == 5
						|| Integer.parseInt(formdata.substring(0, 2)) == 7
						|| Integer.parseInt(formdata.substring(0, 2)) == 8
						|| Integer.parseInt(formdata.substring(0, 2)) == 10
						|| Integer.parseInt(formdata.substring(0, 2)) == 12) {
					if (Integer.parseInt(formdata.substring(3, 5)) <= 31) {
						if (Integer
								.parseInt(formdata.substring(0, 2)) == Calendar
								.getInstance().get(Calendar.MONTH)
								&& Integer.parseInt(formdata
										.substring(3, 5)) < Calendar
										.getInstance().get(Calendar.DATE)) {
							throw new InvalidTimeEntryDataException(
									ExceptionCodes.DATE_FORMAT_EXCEPTION,
									ExceptionMessages.DATE_EXCEPTION);
						}
					} else {
						throw new InvalidTimeEntryDataException(
								ExceptionCodes.DATE_FORMAT_EXCEPTION,
								ExceptionMessages.DATE_EXCEPTION);
					}
				} else {
					if (Integer.parseInt(formdata.substring(0, 2)) == 2) {
						if (new GregorianCalendar().isLeapYear(Calendar
								.getInstance().get(Calendar.YEAR))) {
							if (Integer.parseInt(formdata.substring(
									3, 5)) > 29) {
								throw new InvalidTimeEntryDataException(
										ExceptionCodes.DATE_FORMAT_EXCEPTION,
										ExceptionMessages.DATE_EXCEPTION);
							}
						}
					} else {
						if (Integer
								.parseInt(formdata.substring(3, 5)) > 28) {
							throw new InvalidTimeEntryDataException(
									ExceptionCodes.DATE_FORMAT_EXCEPTION,
									ExceptionMessages.DATE_EXCEPTION);
						}
					}
					if (Integer.parseInt(formdata.substring(3, 5)) <= 30) {
						if (Integer
								.parseInt(formdata.substring(0, 2)) == Calendar
								.getInstance().get(Calendar.MONTH)
								&& Integer.parseInt(formdata
										.substring(3, 5)) < Calendar
										.getInstance().get(Calendar.DATE)) {
							throw new InvalidTimeEntryDataException(
									ExceptionCodes.DATE_FORMAT_EXCEPTION,
									ExceptionMessages.DATE_EXCEPTION);
						}
					}
				}
			}

			if (Integer.parseInt(formdata.substring(6)) != Calendar
					.getInstance().get(Calendar.YEAR)) {
				throw new InvalidTimeEntryDataException(
						ExceptionCodes.DATE_FORMAT_EXCEPTION,
						ExceptionMessages.DATE_YEAR_EXCEPTION);
			}

		}

		return true;
	}

	public static boolean validate(TimeEntriesForm entry) throws Exception {
		if (entry.getReleaseId() == 0 || entry.getActivityId() == 0
				|| entry.getProjectId() == 0 || entry.getHours()==0)
			throw new ObjectNotFoundException();
		else if (ReleasesHandler.getInstance().getObjectById(
				entry.getReleaseId()) == null
				|| ProjectHandler.getInstance().getObjectById(
						entry.getProjectId()) == null
				|| ActivityHandler.getInstance().getObjectById(
						entry.getActivityId()) == null || entry.getTask()==null)
			throw new ObjectNotFoundException();
		else if (ReleasesHandler.getInstance()
				.getObjectById(entry.getReleaseId()).getProjectId() != entry
				.getProjectId())
			throw new ObjectNotFoundException();

		return true;
	}
	
	
	
}
