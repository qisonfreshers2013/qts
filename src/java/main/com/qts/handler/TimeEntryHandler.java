package com.qts.handler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;

import com.qts.common.Utils;
import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.InvalidTimeEntryDataException;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.ProjectException;
import com.qts.exception.ReleasesException;
import com.qts.exception.RolesException;
import com.qts.exception.TimeEntryException;
import com.qts.model.RoleBean;
import com.qts.model.TimeEntries;
import com.qts.model.TimeEntryBean;
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

	// Validation Methods
	
	
	/*
	 * Validation method for TimeEntryBean
	 */

	private boolean validateTimeEntryBean(TimeEntryBean timeEntry) throws Exception {
		RoleBean roleBean = new RoleBean(ServiceRequestContextHolder.getContext()
				.getUserSessionToken().getUserId(),timeEntry.getProjectId());
		try{
		if (timeEntry.getReleaseId() == 0 || timeEntry.getActivityId() == 0
				|| timeEntry.getProjectId() == 0 || timeEntry.getHours() == 0
				|| timeEntry.getHours() > 12)
			throw new InvalidTimeEntryDataException(ExceptionCodes.MANDATORY_FIELDS_MISMATCH,ExceptionMessages.PASSED_DATA_IS_NOT_RELATED);
		else if (ReleaseHandler.getInstance().getObjectById(
				timeEntry.getReleaseId()) == null
				|| ProjectHandler.getInstance().getObjectById(
						timeEntry.getProjectId()) == null
				|| ActivityHandler.getInstance().getObjectById(
						timeEntry.getActivityId()) == null
				|| timeEntry.getTask() == null
				|| timeEntry.getUserRemarks().length() > 4096)
			throw new InvalidTimeEntryDataException(ExceptionCodes.MANDATORY_FIELDS_MISMATCH,ExceptionMessages.PASSED_DATA_IS_NOT_RELATED);


		else if (ReleaseHandler.getInstance()
				.getObjectById(timeEntry.getReleaseId()).getProjectId() != timeEntry
				.getProjectId())
			throw new InvalidTimeEntryDataException(ExceptionCodes.MANDATORY_FIELDS_MISMATCH,ExceptionMessages.PASSED_DATA_IS_NOT_RELATED);
       
		else if (!(RoleHandler.getInstance().listUserRoles(roleBean)
				.getRoleIds().contains(new Long(3))))
			throw new TimeEntryException(
					ExceptionCodes.TIMEENTRY_FILLING_IS_NOT_ALLOWED_FOR_APPROVER,
					ExceptionMessages.TIMEENTRY_FILLING_FOR_APPROVER);
       
		else if (getUserWorkedHoursPerDay(timeEntry.getDate()) > 24)
			throw new TimeEntryException(
					ExceptionCodes.ILLEGAL_ARGUMENT_HOURS_FIELD,
					ExceptionMessages.ILLEGAL_HOURS_ARGUMENT_PASSED);
       }
		catch(RolesException roleNotFound){
	    	   throw new InvalidTimeEntryDataException(ExceptionCodes.INVALID_ROLE_ID,ExceptionMessages.ROLE_ID_DOESNOT_EXISTS);
	       }
		catch(ProjectException projectNotFound){
			throw new InvalidTimeEntryDataException(ExceptionCodes.MANDATORY_FIELDS_MISMATCH,ExceptionMessages.PASSED_DATA_IS_NOT_RELATED);
		}
		catch(ReleasesException exceptionByReleasesHandler){
			throw new InvalidTimeEntryDataException(ExceptionCodes.MANDATORY_FIELDS_MISMATCH,ExceptionMessages.PASSED_DATA_IS_NOT_RELATED);
		}
		return true;

	}
	
	/*
	 * Validation method for Date taken as String 
	 */


	public static boolean validateDate(String date)
			throws InvalidTimeEntryDataException, ParseException {

		if (date == null) {
			throw new InvalidTimeEntryDataException(
					ExceptionCodes.DATE_CANNOT_BE_NULL,
					ExceptionMessages.DATE_CANNOT_BE_NULL);
		} else {
			if (date.length() > 10) {
				throw new InvalidTimeEntryDataException(
						ExceptionCodes.DATE_LENGTH_MISMATCH,
						ExceptionMessages.DATE_LENGTH_MISMATCH);
			} else {
				if (Integer.parseInt(date.substring(0, 2)) <= 12
						&& Integer.parseInt(date.substring(0, 2)) != 0) {
					if (!(Integer.parseInt(date.substring(0, 2)) >= (Calendar
							.getInstance().get(Calendar.MONTH)))) {
						throw new InvalidTimeEntryDataException(
								ExceptionCodes.DATE_FORMAT_EXCEPTION,
								ExceptionMessages.DATE_MONTH_EXCEPTION);
					}
				} else {
					throw new InvalidTimeEntryDataException(
							ExceptionCodes.DATE_FORMAT_EXCEPTION,
							ExceptionMessages.DATE_MONTH_EXCEPTION);
				}
				if (Integer.parseInt(date.substring(0, 2)) == 1
						|| Integer.parseInt(date.substring(0, 2)) == 3
						|| Integer.parseInt(date.substring(0, 2)) == 5
						|| Integer.parseInt(date.substring(0, 2)) == 7
						|| Integer.parseInt(date.substring(0, 2)) == 8
						|| Integer.parseInt(date.substring(0, 2)) == 10
						|| Integer.parseInt(date.substring(0, 2)) == 12) {
					if (Integer.parseInt(date.substring(3, 5)) <= 31) {
						if (Integer.parseInt(date.substring(0, 2)) == Calendar
								.getInstance().get(Calendar.MONTH)
								&& Integer.parseInt(date.substring(3, 5)) < Calendar
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
					if (Integer.parseInt(date.substring(0, 2)) == 2) {
						if (new GregorianCalendar().isLeapYear(Calendar
								.getInstance().get(Calendar.YEAR))) {
							if (Integer.parseInt(date.substring(3, 5)) > 29) {
								throw new InvalidTimeEntryDataException(
										ExceptionCodes.DATE_FORMAT_EXCEPTION,
										ExceptionMessages.DATE_EXCEPTION);
							}
						}
					} else {
						if (Integer.parseInt(date.substring(3, 5)) > 28) {
							throw new InvalidTimeEntryDataException(
									ExceptionCodes.DATE_FORMAT_EXCEPTION,
									ExceptionMessages.DATE_EXCEPTION);
						}
					}
					if (Integer.parseInt(date.substring(3, 5)) <= 30) {
						if (Integer.parseInt(date.substring(0, 2)) == Calendar
								.getInstance().get(Calendar.MONTH)
								&& Integer.parseInt(date.substring(3, 5)) < Calendar
										.getInstance().get(Calendar.DATE)) {
							throw new InvalidTimeEntryDataException(
									ExceptionCodes.DATE_FORMAT_EXCEPTION,
									ExceptionMessages.DATE_EXCEPTION);
						}
					}
				}
			}
			if (Calendar.getInstance().get(Calendar.MONTH) == 0) {
				if (Integer.parseInt(date.substring(0, 2)) == 12){
						if(Integer.parseInt(date.substring(6)) != Calendar
								.getInstance().get(Calendar.YEAR) - 1)
					 {
                        throw new InvalidTimeEntryDataException(ExceptionCodes.DATE_FORMAT_EXCEPTION,ExceptionMessages.ILLEGAL_YEAR_PASSED);
					}
				}      
			}
			else if(Integer.parseInt(date.substring(6)) != Calendar.getInstance()
					.get(Calendar.YEAR)) {
				throw new InvalidTimeEntryDataException(
						ExceptionCodes.DATE_FORMAT_EXCEPTION,
						ExceptionMessages.DATE_YEAR_EXCEPTION);
			}
			else if (!(date.matches(Utils.DATE_PATTTERN)))
				throw new InvalidTimeEntryDataException(
						ExceptionCodes.DATE_FORMAT_EXCEPTION,
						ExceptionMessages.INVALID_ARGUMENTS_FOR_DATE);
		}
		if(Integer.parseInt(date.substring(3, 5))>Calendar.getInstance().get(Calendar.DATE) && Integer.parseInt(date.substring(0,2))==(Calendar.getInstance().get(Calendar.MONTH)+1)){
			throw new InvalidTimeEntryDataException(ExceptionCodes.DATE_FORMAT_EXCEPTION,ExceptionMessages.SEARCH_FUTURE_SHEETS);
		}

		return true;
	}

	/*
	 * Validation method TimeEntryBean for Search operation by Approver
	 */
	
	private boolean validateSearchCriteria(TimeEntryBean searchCriteria) throws Exception{
		
		if(searchCriteria.getFrom()!=null)
		{
			 Utils.generalValidationsForDate(searchCriteria.getFrom());
		}
		if(searchCriteria.getTo()!=null && searchCriteria.getFrom()==null){
			throw new TimeEntryException(ExceptionCodes.SEARCH_RESULTS_NO_MATCH,ExceptionMessages.FORMFIELD_TO_NOT_PRESENT);
		}
	    if(searchCriteria.getTo()!=null){
	    	Utils.generalValidationsForDate(searchCriteria.getTo());  	
	    if(Utils.parseDateToLong(searchCriteria.getFrom())>Utils.parseDateToLong(searchCriteria.getTo())){
	    	throw new TimeEntryException(ExceptionCodes.SEARCH_NOT_ALLOWED,ExceptionMessages.SEARCH_NOT_ALLOWED);
	    }
	    }
	    if(searchCriteria.getUserId()!=null && searchCriteria.getProjectId()!=null){
	    	try{
	    	UserProjectHandler.getInstance().getUserProjectByIds(searchCriteria.getProjectId(),searchCriteria.getUserId());
	    	}catch(ProjectException ex){
	    		throw new TimeEntryException(ExceptionCodes.PROJECT_ID_INVALID,ExceptionMessages.USER_NOT_ASSOCIATED_WITH_PROJECT);
	    	}
	    }
	    if(searchCriteria.getFrom()!=null && searchCriteria.getUserId()==null && searchCriteria.getProjectId()==null && searchCriteria.getStatus()==null)
	    {
	    	throw new TimeEntryException(ExceptionCodes.SEARCH_NOT_ALLOWED,ExceptionMessages.SELECT_APPROPRIATE_FIELDS_FOR_SEARCH);
	    }
	    
		return true;
		}
	
	
	


	// End of Validation Methods

	/*
	 * Handler Method Used By AddEntry Service
	 */
	public boolean add(TimeEntryBean timeEntry) throws Exception {
		timeEntry.setUserId(ServiceRequestContextHolder.getContext()
				.getUserSessionToken().getUserId());
		boolean isTimeEntryAdded=false;
		//validateDate validates the DATE String 
		
		//validateTimeEntryBean validates the projectId,releaseId,ActivityId
		
		if (validateDate(timeEntry.getDate()) && validateTimeEntryBean(timeEntry)) {
			 isTimeEntryAdded = DAOFactory.getInstance().getTimeEntryDAOInstance()
					.add(timeEntry);
		}
		
		return isTimeEntryAdded;
	}

	/*
	 * Handler Method Used By RejectEntry Service by Approver
	 */

	public boolean reject(TimeEntryBean timeEntry) throws Exception {
		boolean isTimeEntryRejected =false;
		
		//validating whether given passed TimeEntryId(Primary key) is valid and making approver to give rejected comments  
		
		if (timeEntry.getId() == null
				&& timeEntry.getRejectedComments() == null){
			throw new InvalidTimeEntryDataException(ExceptionCodes.TIMEENTRY_REJECT_FAILED,ExceptionMessages.TIMEENTRY_REJECT_FAILED);
		}else{
		        isTimeEntryRejected = DAOFactory.getInstance().getTimeEntryDAOInstance().reject(timeEntry);
			  } 
			
		return isTimeEntryRejected;
	}

	/*
	 * Handler Method Used By ApproveEntry Service by Approver
	 */
	public boolean approve(TimeEntryBean timeEntry) throws Exception {
		
		boolean isTimeEntryApproved=false;
		if (timeEntry.getId() == null){ 
			throw new InvalidTimeEntryDataException(ExceptionCodes.TIMEENTRY_APPROVE_FAILED,ExceptionMessages.TIMEENTRY_APPROVE_FAILED);
		}else{
		isTimeEntryApproved = DAOFactory.getInstance().getTimeEntryDAOInstance().approve(timeEntry);
			} 
	
		return isTimeEntryApproved;
	}

	/*
	 * Handler Method Used By DeleteEntry Service
	 */
	public boolean delete(TimeEntryBean timeEntry) throws Exception {

		
		boolean isTimeEntryDeleted =false;
		timeEntry.setUserId(ServiceRequestContextHolder.getContext()
				.getUserSessionToken().getUserId());
		// validating whether Id is passed or not
		if (timeEntry.getId() != null) {
			 isTimeEntryDeleted = DAOFactory.getInstance()
					.getTimeEntryDAOInstance().delete(timeEntry);
			}
		 else {
			throw new InvalidTimeEntryDataException();
		}
		return isTimeEntryDeleted;
	}

	/*
	 * Handler Method Used By Update Service
	 */
	public boolean update(TimeEntryBean timeEntry) throws Exception {
		
		boolean updated=true;
		timeEntry.setUserId(ServiceRequestContextHolder.getContext()
				.getUserSessionToken().getUserId());
		//validateDate validates the DATE String 
		
	   //validateTimeEntryBean validates the projectId,releaseId,ActivityId
		
		if (validateDate(timeEntry.getDate()) && validateTimeEntryBean(timeEntry)) {
			 updated = DAOFactory.getInstance()
					.getTimeEntryDAOInstance().update(timeEntry);
		}
		return updated;

	}

	/*
	 * Handler Method Used By Search Service
	 */
	public List<TimeEntries> search(TimeEntryBean timeEntry) throws Exception {
        
		
		// if Date field is Given it is validated using generalValidatinsForDate method in Utils Class
		
		//if project name(taken as projectId) is given it is validated  for whether project is associated with this user
		
		if(timeEntry.getDate()!=null ){Utils.generalValidationsForDate(timeEntry.getDate());}
		if(timeEntry.getProjectId()!=null){
			try{
				UserProjectHandler.getInstance().getUserProjectByIds(timeEntry.getProjectId(),ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId());
			}catch(ProjectException ex){
				throw new TimeEntryException(ExceptionCodes.PROJECT_ID_INVALID,ExceptionMessages.USER_NOT_ASSOCIATED_WITH_PROJECT);
			}
		}
		timeEntry.setUserId(ServiceRequestContextHolder.getContext()
				.getUserSessionToken().getUserId());

		List<TimeEntries> responseList = DAOFactory.getInstance()
				.getTimeEntryDAOInstance().getUserTimeEntries(timeEntry);

		if (responseList == null || responseList.size() == 0) {
			throw new TimeEntryException(
					ExceptionCodes.TIMEENTRY_SEARCH_STATUS,
					ExceptionMessages.TIMEENTRY_USERSEARCH);
		}

		return responseList;
	}



	/*
	 * Handler method to Check whether ReleaseId Mapped or not
	 */
	
	
	public boolean isEntryMapped(long id) throws Exception {
		if (!(DAOFactory.getInstance().getTimeEntryDAOInstance()
				.getTimeEntryObjectById(id))) {
			throw new ObjectNotFoundException();
		}
		return true;
	}

	
	
	
	public boolean submit(List<TimeEntryBean> getTimeEntriesToSubmit)
			throws Exception {
		
		//Here validations is not necessary because TimeEntries which are in saved mode are already validated for the corresponding user
		
		for (TimeEntryBean timeEntry : getTimeEntriesToSubmit) {
			timeEntry.setUserId(ServiceRequestContextHolder.getContext()
					.getUserSessionToken().getUserId());
			boolean submitted = DAOFactory.getInstance()
					.getTimeEntryDAOInstance().submit(timeEntry);
			if (!submitted) {
				throw new TimeEntryException(
						ExceptionCodes.TIMEENTRY_SUBMISSION_FAILED,
						ExceptionMessages.TIMEENTRY_SUBMIT_FAILED);
			}
		}
		return true;
	}

	
	/*
	 * Handler method to  obtain ApproverId for a given project
	 */
	
	
	public List<Long> getApproversByProject(long projectId) throws Exception {
		
		List<UserProject> listUserProjects = UserProjectHandler.getInstance()
				.getListOfUserProjectByProjectId(projectId);
		List<UserProjectRoles> listUserProjectRoles = new ArrayList<UserProjectRoles>();
		for (UserProject userProject : listUserProjects) {
			if (UserProjectsRolesHandler.getInstance()
					.getUserProjectRolesByUserProjectId(userProject.getId()) != null) {
				listUserProjectRoles.addAll(UserProjectsRolesHandler
						.getInstance().getUserProjectRolesByUserProjectId(
								userProject.getId()));
			}
		}
		long id;
		for (UserProjectRoles userProjectRoles : listUserProjectRoles) {
			id = userProjectRoles.getRoleId();
			if (id == 2) {
				UserProject userProjectObj = (UserProject) UserProjectHandler
						.getInstance().getObjectById(
								userProjectRoles.getUserProjectId());
				listOfApprovers.add(userProjectObj.getUserId());
			}
		}
		return listOfApprovers;
	}

	/*
	 * Handler Method Used By Approver to search TimeEntries
	 */

	public List<TimeEntries> searchTimeEntriesByApprover(TimeEntryBean searchCriteria)
			throws Exception {
		int isApprover=0;
		//validating The SeaarchCriteria 
		if(validateSearchCriteria(searchCriteria)){	
     	List<TimeEntries> getTimeEntriesForApporver = new ArrayList<TimeEntries>();
		List<UserProject> associatedProjectList = UserProjectHandler.getInstance().getListOfUserProjectByUserId(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId());		
		for (UserProject associatedProject : associatedProjectList) {
			RoleBean roleBeanInput=new RoleBean(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId(),associatedProject.getProjectId());
			RoleBean roleBeanOutput=null;
			try{
				roleBeanOutput=RoleHandler.getInstance().listUserRoles(roleBeanInput);
				}catch(RolesException rolenotfound){
					roleBeanOutput.setRoleIds(null);
				}
			
		if((roleBeanOutput.getRoleIds()!=null && roleBeanOutput.getRoleIds().contains(new Long(2)))||listOfApprovers.contains(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId()))
			{
				List<TimeEntries> responseList = DAOFactory.getInstance()
						.getTimeEntryDAOInstance()
						.getTimeEntriesForApprover(searchCriteria);
					getTimeEntriesForApporver.addAll(responseList);
			}else{
				isApprover=isApprover+1;
			}
		}
	   if(isApprover==associatedProjectList.size()){
		   throw new TimeEntryException(ExceptionCodes.ACTION_DOESNOT_EXIST,ExceptionMessages.TIMEENTRY_USER_NOT_AUTHORIZED);
	   }

		return getTimeEntriesForApporver;
		}
		return null;
	}
	

	/*
	 * Handler Method used to obtain worked hours by a user for that date
	 */

	public int getUserWorkedHoursPerDay(String date) throws Exception {
		return DAOFactory.getInstance().getTimeEntryDAOInstance()
				.getUserWorkingHoursperDay(date);
	}
	
	}
