package com.qts.handler;


import java.util.List;

import com.qts.exception.InternalTimeEntryDBException;
import com.qts.exception.InvalidTimeEntryDataException;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.SearchFailedException;
import com.qts.exception.TimeEntryDeletionFailedException;
import com.qts.exception.TimeEntrySubmissionFailedException;
import com.qts.exception.TimeEntryUpdateFailedException;
import com.qts.model.TimeEntries;
import com.qts.model.TimeEntriesForm;
import com.qts.persistence.dao.DAOFactory;
/**
 * Time Entry Handler Class Exposed to Services to perform Business Operartion
 * @author Ajay
 *
 */
public class TimeEntryHandler {

	private static TimeEntryHandler INSTANCE = null;

	private TimeEntryHandler() {
	}

	public static TimeEntryHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TimeEntryHandler();
		}
		return INSTANCE;
	}
  /*
   * Handler Method Used By AddEntry Service
  */
	public boolean addEntry(TimeEntriesForm formdata) throws InvalidTimeEntryDataException, TimeEntrySubmissionFailedException, Exception {
			if (ValidateData.validateDate(formdata.getDate())) {
				if(ValidateData.validate(formdata)){
	boolean added = DAOFactory.getTimeEntryDAOInstance().addTimeEntry(formdata,null);
			if (!added) {
					throw new TimeEntrySubmissionFailedException(); 
					}
			}
			}
		return true;
	}
	/*
	 * Handler Method Used By RejectEntry Service by Approver
	 */

	public boolean rejectEntry(List<TimeEntriesForm> formdata) throws Exception {
        
		for (TimeEntriesForm data : formdata) {
			if(data.getId()!=null){
			boolean rejected = DAOFactory.getTimeEntryDAOInstance()
					.rejectTimeEntry(data);
			if (!rejected) {
				throw new InternalTimeEntryDBException();// write exception code and message
			}
			}else{
				throw new InvalidTimeEntryDataException();
			}
		}
		return true;
	}

	/*
	 * Handler Method Used By ApproveEntry Service by Approver
	 */
	public boolean approveEntry(List<TimeEntriesForm> entrydata) throws Exception {
		for (TimeEntriesForm data : entrydata) {
			if(data.getId()!=null){
			boolean approved = DAOFactory.getTimeEntryDAOInstance()
					.approveTimeEntry(data);
			if (!approved) {
				throw new InternalTimeEntryDBException(); // write exception for this case
			}}else{
				throw new InvalidTimeEntryDataException();
			}

		}
		return true;
	}

	/*
	 * Handler Method Used By DeleteEntry Service
	 */
	public boolean deleteEntry(TimeEntriesForm deletedata) throws Exception {

			if(deletedata.getId()!=null){
			boolean deleted = DAOFactory.getTimeEntryDAOInstance()
					.deleteTimeEntry(deletedata);
			if (!deleted) {
				throw new TimeEntryDeletionFailedException(); // write exception for this case
			}}else{
				throw new InvalidTimeEntryDataException();
			}
		return true;
	}

	/*
	 * Handler Method Used By Update Service
	 */
	public boolean updateEntry(TimeEntriesForm dataToUpdate) throws Exception {
 
		
		boolean updated = DAOFactory.getTimeEntryDAOInstance().updateTimeEntry(
				dataToUpdate);
		if (!updated) {
			throw new TimeEntryUpdateFailedException(); // write exception for this case
		}
		return true;
		
	}

	/*
	 * Handler Method Used By Search Service
	 */
	public List<TimeEntries> searchUserEntries(TimeEntriesForm formdata)
			throws InvalidTimeEntryDataException, SearchFailedException {
		if (formdata.getUserId() == null) {
			throw new InvalidTimeEntryDataException();
		}
		List<TimeEntries> responseList = DAOFactory.getTimeEntryDAOInstance()
				.searchTimeEntriesForUser(formdata);
		if (responseList == null) {
			throw new SearchFailedException();		}
		
		return responseList;
	}
	
	/*
	 * Handler Method Used By Search Service for Approver
	 */
	public List<TimeEntries> searchUserEntriesForApprover(TimeEntriesForm formdata)
			throws InvalidTimeEntryDataException {
		if (formdata.getUserId() == null && formdata.getProjectId()==null) {
			throw new InvalidTimeEntryDataException();
		}
		
		List<TimeEntries> responseList = DAOFactory.getTimeEntryDAOInstance()
				.searchTimeEntriesForApprover(formdata);
		if (responseList != null) {
			return responseList;
		}
		return null;
	}
	
	/*
	 * Handler method to Check whether ReleaseId Mapped or not
	 */
	public boolean isEntryMapped(long id) throws Exception{
		if( DAOFactory.getTimeEntryDAOInstance().getTimeEntryObjectById(id)==null){
			throw new ObjectNotFoundException();
		}
		
		return true;
	}
	public boolean submitTimeEntries(List<TimeEntriesForm> submissionEntries) throws TimeEntrySubmissionFailedException, Exception
	{
		for(TimeEntriesForm formdata:submissionEntries)
		if (ValidateData.validateDate(formdata.getDate())) {
			if(ValidateData.validate(formdata)){
boolean added = DAOFactory.getTimeEntryDAOInstance().addTimeEntry(formdata,null);
		if (!added) {
				throw new TimeEntrySubmissionFailedException(); 
				}
		}
		}
	return true;
	}
}

