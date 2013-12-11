package com.qts.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

<<<<<<< HEAD





=======
import com.qts.exception.AuthenticateException;
>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157
import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.InvalidTimeEntryDataException;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.TimeEntryException;
import com.qts.model.TimeEntries;
import com.qts.model.TimeEntriesForm;
import com.qts.model.UserProject;
import com.qts.model.UserProjectRoles;
import com.qts.persistence.dao.DAOFactory;
import com.qts.service.common.ServiceRequestContextHolder;

/**
 * Time Entry Handler Class Exposed to Services to perform Business Operartion
 * 
 * @author Ajay
 * 
 */
public class TimeEntryHandler {

	private static TimeEntryHandler INSTANCE = null;
	private List<Long> listOfApprovers = new ArrayList<Long>();

	private TimeEntryHandler() {
		listOfApprovers.add(new Long(10));
		listOfApprovers.add(new Long(11));
	}

	public static TimeEntryHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TimeEntryHandler();
		}
		return INSTANCE;
	}
<<<<<<< HEAD
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
=======

	/*
	 * Handler Method Used By AddEntry Service
	 */
	public boolean addEntry(TimeEntriesForm formdata) throws Exception {
		formdata.setUserId(ServiceRequestContextHolder.getContext()
				.getUserSessionToken().getUserId());
		if (ValidateData.validateDate(formdata.getDate())) {
			if (ValidateData.validate(formdata)) {
				boolean added = DAOFactory.getInstance()
						.getTimeEntryDAOInstance().addTimeEntry(formdata, null);
				if (!added) {
					throw new TimeEntryException(
							ExceptionCodes.TIMEENTRY_ADDITION_FAILED,
							ExceptionMessages.TIEMENTRY_ADD); // SubmissionFailedException
				}
>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157
			}
		}
		return true;
	}

	/*
	 * Handler Method Used By RejectEntry Service by Approver
	 */

	public List<TimeEntries> rejectEntry(List<TimeEntriesForm> formdata)
			throws Exception {
		List<TimeEntries> rejectedSet = new ArrayList<TimeEntries>();

		for (TimeEntriesForm data : formdata) {
<<<<<<< HEAD
			if(data.getId()!=null){
			boolean rejected = DAOFactory.getInstance().getTimeEntryDAOInstance()
					.rejectTimeEntry(data);
			if (!rejected) {
			throw new TimeEntryException(ExceptionCodes.TIMEENTRY_REJECT_FAILED,ExceptionMessages.TIMEENTRY_REJECT);// write exception code and message DBException
			}
			}else{
				throw new InvalidTimeEntryDataException();
=======
			if (getApprovers(data.getProjectId()).contains(
					ServiceRequestContextHolder.getContext()
							.getUserSessionToken().getUserId())) {
				if (data.getId() != null && data.getRejectedComments() != null) {
					TimeEntries rejected = DAOFactory.getInstance()
							.getTimeEntryDAOInstance().rejectTimeEntry(data);
					if (rejected == null) {
						throw new TimeEntryException(
								ExceptionCodes.TIMEENTRY_REJECT_FAILED,
								ExceptionMessages.TIMEENTRY_REJECT);// write
																	// exception
																	// code and
																	// message
																	// DBException
					} else
						rejectedSet.add(rejected);
				} else {
					throw new InvalidTimeEntryDataException();
				}
>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157
			}
		}
		return rejectedSet;
	}

	/*
	 * Handler Method Used By ApproveEntry Service by Approver
	 */
	public boolean approveEntry(List<TimeEntriesForm> entrydata)
			throws Exception {
		for (TimeEntriesForm data : entrydata) {
<<<<<<< HEAD
			if(data.getId()!=null){
			boolean approved = DAOFactory.getInstance().getTimeEntryDAOInstance()
					.approveTimeEntry(data);
			if (!approved) {
				throw new TimeEntryException(ExceptionCodes.TIMEENTRY_APPROVE_FAILED,ExceptionMessages.TIMEENTRY_APPROVE); // write exception for this case( InternalTimeEntryDB)
			}}else{
				throw new InvalidTimeEntryDataException();
			}
=======
			if (getApprovers(data.getProjectId()).contains(
					ServiceRequestContextHolder.getContext()
							.getUserSessionToken().getUserId())) {
				if (data.getId() != null) {
>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157

					boolean approved = DAOFactory.getInstance()
							.getTimeEntryDAOInstance().approveTimeEntry(data);
					if (!approved) {
						throw new TimeEntryException(
								ExceptionCodes.TIMEENTRY_APPROVE_FAILED,
								ExceptionMessages.TIMEENTRY_APPROVE); 
					}
				} else {
				throw new InvalidTimeEntryDataException();}
			}
		}
		return true;
	}

	/*
	 * Handler Method Used By DeleteEntry Service
	 */
	public boolean deleteEntry(TimeEntriesForm deletedata) throws Exception {

<<<<<<< HEAD
<<<<<<< HEAD
			if(deletedata.getId()!=null){
			boolean deleted = DAOFactory.getInstance().getTimeEntryDAOInstance()
					.deleteTimeEntry(deletedata);
			if (!deleted) {
				throw new TimeEntryException(ExceptionCodes.TIMEENTRYDELETIONFAILED,ExceptionMessages.TIMEENTRY_DELETE); // write exception for this case(TimeEntryDeletionFailed)
			}}else{
				throw new InvalidTimeEntryDataException();
=======
=======
                 deletedata.setUserId(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId());



>>>>>>> aa6fb43f09ad3c0280514b8e976f1af9f568cf71
		if (deletedata.getId() != null) {
			boolean deleted = DAOFactory.getInstance()
					.getTimeEntryDAOInstance().deleteTimeEntry(deletedata);
			if (!deleted) {
				throw new TimeEntryException(
						ExceptionCodes.TIMEENTRYDELETIONFAILED,
						ExceptionMessages.TIMEENTRY_DELETE); // write exception
																// for this
																// case(TimeEntryDeletionFailed)
>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157
			}
		} else {
			throw new InvalidTimeEntryDataException();
		}
		return true;
	}

	/*
	 * Handler Method Used By Update Service
	 */
	public boolean updateEntry(TimeEntriesForm dataToUpdate) throws Exception {
<<<<<<< HEAD
 
		
		boolean updated = DAOFactory.getInstance().getTimeEntryDAOInstance().updateTimeEntry(
				dataToUpdate);
		if (!updated) {
			throw new TimeEntryException(ExceptionCodes.TIMEENTRYUPDATEFAILED,ExceptionMessages.TIMEENTRY_UPDATE); // write exception for this case(TimeEntryUpdateFailed)
=======
		dataToUpdate.setUserId(ServiceRequestContextHolder.getContext()
				.getUserSessionToken().getUserId());
		if (ValidateData.validateDate(dataToUpdate.getDate())) {
			if (ValidateData.validate(dataToUpdate)) {
				boolean updated = DAOFactory.getInstance()
						.getTimeEntryDAOInstance()
						.updateTimeEntry(dataToUpdate);
				if (!updated) {
					throw new TimeEntryException(
							ExceptionCodes.TIMEENTRYUPDATEFAILED,
							ExceptionMessages.TIMEENTRY_UPDATE); // write
																	// exception
																	// for this
																	// case(TimeEntryUpdateFailed)
				}
			}
>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157
		}
		return true;

	}

	/*
	 * Handler Method Used By Search Service
	 */
<<<<<<< HEAD
	public List<TimeEntries> searchUserEntries(TimeEntriesForm formdata)
			throws Exception{
		if (formdata.getUserId() == null) {
			throw new InvalidTimeEntryDataException();
		}
		List<TimeEntries> responseList = DAOFactory.getInstance().getTimeEntryDAOInstance()
				.searchTimeEntriesForUser(formdata);
		if (responseList == null) {
			throw new TimeEntryException(ExceptionCodes.TIMEENTRY_SEARCH_FAILED,ExceptionMessages.TIMEENTRY_USERSEARCH);		} //(SearchFailed)
		
=======
	public List<TimeEntries> searchUserEntries(TimeEntriesForm formData)
			throws Exception {

		formData.setUserId(ServiceRequestContextHolder.getContext()
				.getUserSessionToken().getUserId());
		List<TimeEntries> responseList = DAOFactory.getInstance()
				.getTimeEntryDAOInstance().listUserEntries(formData);
		if (responseList == null) {
			throw new TimeEntryException(
					ExceptionCodes.TIMEENTRY_SEARCH_FAILED,
					ExceptionMessages.TIMEENTRY_USERSEARCH);
		} // (SearchFailed)

>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157
		return responseList;
	}

	/*
	 * Handler Method Used By Search Service for Approver
	 */
<<<<<<< HEAD
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
=======
//	public List<TimeEntries> searchUserEntriesForApprover(
//			TimeEntriesForm formdata) throws Exception {
//		// if (formdata.getUserId() == null && formdata.getProjectId()==null) {
//		// throw new InvalidTimeEntryDataException();
//		// }
//		List<UserProject> associatedProjectList=UserProjectHandler.getInstance().getListOfUserProjectByUserId(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId());
//		//Set<Long> approversList = UserProjectHandler.getInstance().getApproversListByProjectId(formdata.getProjectId());
//	    for(UserProject getProjectId:associatedProjectList){
//				List<TimeEntries> responseList = DAOFactory.getInstance()
//				.getTimeEntryDAOInstance()
//				.listEntriesToApprove(formdata, approversList);
//		if (responseList != null) {
//			return responseList;
//		} else
//			throw new AuthenticationException(
//					"User is Not allowed to perform this operation");
//	}

	/*
	 * Handler method to Check whether ReleaseId Mapped or not
	 */
	public boolean isEntryMapped(long id) throws Exception {
		if (DAOFactory.getInstance().getTimeEntryDAOInstance()
				.getTimeEntryObjectById(id) == false) {
<<<<<<< HEAD
>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157
=======
>>>>>>> aa6fb43f09ad3c0280514b8e976f1af9f568cf71
			throw new ObjectNotFoundException();
		}

		return true;
	}
<<<<<<< HEAD
	public boolean submitTimeEntries(List<TimeEntriesForm> entriesToSubmit) throws  Exception
	{
		for(TimeEntriesForm formdata:entriesToSubmit)
		if (ValidateData.validateDate(formdata.getDate())) {
			if(ValidateData.validate(formdata)){
boolean submitted = DAOFactory.getInstance().getTimeEntryDAOInstance().submitTimeEntries(formdata);
		if (!submitted) {
				throw new TimeEntryException(ExceptionCodes.TIMEENTRYSUBMISSIONFAILED,ExceptionMessages.TIMEENTRY_SUBMIT); 
				}
=======

	public boolean submitTimeEntries(List<TimeEntriesForm> entriesToSubmit)
			throws Exception {
		for (TimeEntriesForm formData : entriesToSubmit) {
			formData.setUserId(ServiceRequestContextHolder.getContext()
					.getUserSessionToken().getUserId());
			boolean submitted = DAOFactory.getInstance()
					.getTimeEntryDAOInstance().submitTimeEntries(formData);
			if (!submitted) {
				throw new TimeEntryException(
						ExceptionCodes.TIMEENTRYSUBMISSIONFAILED,
						ExceptionMessages.TIMEENTRY_SUBMIT);
			}
>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157
		}
		return true;
	}

	public List<Long> getApprovers(long projectId) throws Exception {
		List<UserProject> listUserProjects = UserProjectHandler.getInstance()
				.getListOfUserProjectByProjectId(projectId);
		List<UserProjectRoles> listUserProjectRoles = new ArrayList<UserProjectRoles>();

		for (UserProject userProject : listUserProjects) {
			if(UserProjectsRolesHandler.getInstance()
					.getUserProjectRolesByUserProjectId(userProject.getId())!=null){
			listUserProjectRoles.addAll(UserProjectsRolesHandler.getInstance()
					.getUserProjectRolesByUserProjectId(userProject.getId()));
			}
		}
		long id;
		
		for (UserProjectRoles userProjectRoles : listUserProjectRoles) {
			id = userProjectRoles.getRoleId();
			if (id == 2){
				UserProject userProjectObj=(UserProject) UserProjectHandler.getInstance().getObjectById(userProjectRoles.getUserProjectId());
				listOfApprovers.add(userProjectObj.getUserId());}
		}
		return listOfApprovers;
	}
	
	public List<TimeEntries> searchUserEntriesForApprover(
			TimeEntriesForm formdata) throws Exception {
	List<TimeEntries> timeEntriesToApporve=new ArrayList<TimeEntries>();
	List<UserProject> associatedProjectList=UserProjectHandler.getInstance().getListOfUserProjectByUserId(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId());
	for(UserProject associatedProject:associatedProjectList){
		if(getApprovers(associatedProject.getProjectId()).contains(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId())){
		         formdata.setProjectId(associatedProject.getProjectId());
			List<TimeEntries> responseList = DAOFactory.getInstance()
					.getTimeEntryDAOInstance()
					.listEntriesToApprove(formdata);
			if(responseList!=null){
				timeEntriesToApporve.addAll(responseList);
			}
			listOfApprovers.remove(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId());
		}
	}
	if(timeEntriesToApporve.size()==0){
		throw new AuthenticateException();
	}
	return timeEntriesToApporve;
	}
		
}
