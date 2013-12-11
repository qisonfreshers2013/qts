package com.qts.handler;


import java.util.List;






import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.InvalidTimeEntryDataException;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.TimeEntryException;
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
	public boolean addEntry(TimeEntriesForm formdata) throws Exception{
			if (ValidateData.validateDate(formdata.getDate())) {
				if(ValidateData.validate(formdata)){
	boolean added = DAOFactory.getInstance().getTimeEntryDAOInstance().addTimeEntry(formdata,null);
			if (!added) {
					throw new TimeEntryException(ExceptionCodes.TIMEENTRY_ADDITION_FAILED,ExceptionMessages.TIEMENTRY_ADD); //SubmissionFailedException 
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
			boolean rejected = DAOFactory.getInstance().getTimeEntryDAOInstance()
					.rejectTimeEntry(data);
			if (!rejected) {
			throw new TimeEntryException(ExceptionCodes.TIMEENTRY_REJECT_FAILED,ExceptionMessages.TIMEENTRY_REJECT);// write exception code and message DBException
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
			boolean approved = DAOFactory.getInstance().getTimeEntryDAOInstance()
					.approveTimeEntry(data);
			if (!approved) {
				throw new TimeEntryException(ExceptionCodes.TIMEENTRY_APPROVE_FAILED,ExceptionMessages.TIMEENTRY_APPROVE); // write exception for this case( InternalTimeEntryDB)
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
			boolean deleted = DAOFactory.getInstance().getTimeEntryDAOInstance()
					.deleteTimeEntry(deletedata);
			if (!deleted) {
				throw new TimeEntryException(ExceptionCodes.TIMEENTRYDELETIONFAILED,ExceptionMessages.TIMEENTRY_DELETE); // write exception for this case(TimeEntryDeletionFailed)
			}}else{
				throw new InvalidTimeEntryDataException();
			}
		return true;
	}

	/*
	 * Handler Method Used By Update Service
	 */
	public boolean updateEntry(TimeEntriesForm dataToUpdate) throws Exception {
 
		
		boolean updated = DAOFactory.getInstance().getTimeEntryDAOInstance().updateTimeEntry(
				dataToUpdate);
		if (!updated) {
			throw new TimeEntryException(ExceptionCodes.TIMEENTRYUPDATEFAILED,ExceptionMessages.TIMEENTRY_UPDATE); // write exception for this case(TimeEntryUpdateFailed)
		}
		return true;
		
	}

	/*
	 * Handler Method Used By Search Service
	 */
	public List<TimeEntries> searchUserEntries(TimeEntriesForm formdata)
			throws Exception{
		if (formdata.getUserId() == null) {
			throw new InvalidTimeEntryDataException();
		}
		List<TimeEntries> responseList = DAOFactory.getInstance().getTimeEntryDAOInstance()
				.searchTimeEntriesForUser(formdata);
		if (responseList == null) {
			throw new TimeEntryException(ExceptionCodes.TIMEENTRY_SEARCH_FAILED,ExceptionMessages.TIMEENTRY_USERSEARCH);		} //(SearchFailed)
		
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
		
		List<TimeEntries> responseList = DAOFactory.getInstance().getTimeEntryDAOInstance()
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
		if( DAOFactory.getInstance().getTimeEntryDAOInstance().getTimeEntryObjectById(id)==null){
			throw new ObjectNotFoundException();
		}
		
		return true;
	}
	public boolean submitTimeEntries(List<TimeEntriesForm> entriesToSubmit) throws  Exception
	{
		for(TimeEntriesForm formdata:entriesToSubmit)
		if (ValidateData.validateDate(formdata.getDate())) {
			if(ValidateData.validate(formdata)){
boolean submitted = DAOFactory.getInstance().getTimeEntryDAOInstance().submitTimeEntries(formdata);
		if (!submitted) {
				throw new TimeEntryException(ExceptionCodes.TIMEENTRYSUBMISSIONFAILED,ExceptionMessages.TIMEENTRY_SUBMIT); 
				}
		}
		}
	return true;
	}
}

