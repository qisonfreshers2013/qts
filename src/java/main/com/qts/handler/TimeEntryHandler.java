package com.qts.handler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.qts.common.Utils;
import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.InvalidTimeEntryDataException;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.ProjectException;
import com.qts.exception.ReleaseException;
import com.qts.exception.RolesException;
import com.qts.exception.TimeEntryException;
import com.qts.model.BaseObject;
import com.qts.model.Project;
import com.qts.model.Release;
import com.qts.model.RoleBean;
import com.qts.model.TimeEntries;
import com.qts.model.TimeEntryBean;
import com.qts.model.UserProject;
import com.qts.model.UserProjectsRoles;
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

	private boolean validateTimeEntryBean(TimeEntryBean timeEntry) throws InvalidTimeEntryDataException,ObjectNotFoundException,TimeEntryException,Exception {
		RoleBean roleBean = new RoleBean(ServiceRequestContextHolder.getContext()
				.getUserSessionToken().getUserId(),timeEntry.getProjectId());
		try{
			if (ReleaseHandler.getInstance().getObjectById(
					timeEntry.getReleaseId()) == null
					|| ProjectHandler.getInstance().getObjectById(
							timeEntry.getProjectId()) == null
					|| ActivityHandler.getInstance().getObjectById(
							timeEntry.getActivityId()) == null
					|| timeEntry.getTask() == null
					|| timeEntry.getUserRemarks().length() > 4096)
				throw new InvalidTimeEntryDataException(ExceptionCodes.MANDATORY_FIELDS_MISMATCH,ExceptionMessages.PASSED_DATA_IS_NOT_RELATED);
		else if (timeEntry.getReleaseId() == 0 || timeEntry.getActivityId() == 0
				|| timeEntry.getProjectId() == 0 || timeEntry.getHours() == 0
				|| timeEntry.getHours() > 12)
			throw new InvalidTimeEntryDataException(ExceptionCodes.MANDATORY_FIELDS_MISMATCH,ExceptionMessages.PASSED_DATA_IS_NOT_RELATED);
		else if (ReleaseHandler.getInstance()
				.getObjectById(timeEntry.getReleaseId()).getProjectId() != timeEntry
				.getProjectId())
			throw new InvalidTimeEntryDataException(ExceptionCodes.MANDATORY_FIELDS_MISMATCH,ExceptionMessages.PASSED_DATA_IS_NOT_RELATED);
		else if((UserProjectHandler.getInstance().getUserProjectByIds(timeEntry.getProjectId(), ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId()))==null){
			throw new TimeEntryException(ExceptionCodes.PROJECT_ID_INVALID,ExceptionMessages.USER_NOT_ASSOCIATED_WITH_PROJECT);
		}
		else if (!(RoleHandler.getInstance().getUserRoles(roleBean)
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
		catch(ReleaseException exceptionByReleasesHandler){
			throw new InvalidTimeEntryDataException(ExceptionCodes.MANDATORY_FIELDS_MISMATCH,ExceptionMessages.PASSED_DATA_IS_NOT_RELATED);
		}
		return true;

	}
	
	/*
	 * Validation method for Date taken as String 
	 */


	private static boolean validateDate(String date)
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
		if(searchCriteria.getStatus()!=null){
		if(!(searchCriteria.getStatus()==1 || searchCriteria.getStatus()==2 || searchCriteria.getStatus()==3)){
		throw new TimeEntryException();	
		}}
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
	    	UserProjectHandler.getInstance().getUserProjectByIds(searchCriteria.getProjectId(),ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId());
	    	UserProjectHandler.getInstance().getUserProjectByIds(searchCriteria.getProjectId(),searchCriteria.getUserId());
	    	RoleBean roleBeanInput=new RoleBean(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId(),searchCriteria.getProjectId());
			RoleBean roleBeanOutput=RoleHandler.getInstance().getUserRoles(roleBeanInput);
			
			if(roleBeanOutput.getRoleIds().isEmpty() && !roleBeanOutput.getRoleIds().contains(2)){
				throw new TimeEntryException(ExceptionCodes.PROJECT_ID_INVALID,ExceptionMessages.APPROVER_NOTAUTHORIZED);
			}
	    	}catch(ProjectException ex){
	    		throw new TimeEntryException(ExceptionCodes.PROJECT_ID_INVALID,ExceptionMessages.APPROVER_NOTAUTHORIZED);
	    	}
	    }
//	    if(searchCriteria.getFrom()!=null && searchCriteria.getUserId()==null && searchCriteria.getProjectId()==null && searchCriteria.getStatus()==null)
//	    {
//	    	throw new TimeEntryException(ExceptionCodes.SEARCH_NOT_ALLOWED,ExceptionMessages.SELECT_APPROPRIATE_FIELDS_FOR_SEARCH);
//	    }
	    
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
			TimeEntryBean timeEntryToApprove=(TimeEntryBean) TimeEntryHandler.getInstance().getObjectById(timeEntry.getId());
			RoleBean roleBeanInput=new RoleBean(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId(),timeEntryToApprove.getProjectId());
			RoleBean roleBeanOutput=RoleHandler.getInstance().getUserRoles(roleBeanInput);
			if(roleBeanOutput.getRoleIds().contains(new Long(2))){
		        isTimeEntryRejected = DAOFactory.getInstance().getTimeEntryDAOInstance().reject(timeEntry);
			  } 
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
			TimeEntryBean timeEntryToApprove=(TimeEntryBean) TimeEntryHandler.getInstance().getObjectById(timeEntry.getId());
			RoleBean roleBeanInput=new RoleBean(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId(),timeEntryToApprove.getProjectId());
			RoleBean roleBeanOutput=RoleHandler.getInstance().getUserRoles(roleBeanInput);
			if(roleBeanOutput.getRoleIds().contains(new Long(2))){
		    isTimeEntryApproved = DAOFactory.getInstance().getTimeEntryDAOInstance().approve(timeEntry);
		      }
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
	public boolean update(TimeEntryBean timeEntry) throws ParseException, ObjectNotFoundException,InvalidTimeEntryDataException,Exception {
		
		boolean updated=true;
		timeEntry.setUserId(ServiceRequestContextHolder.getContext()
				.getUserSessionToken().getUserId());
		//validateDate validates the DATE String 
		
	   //validateTimeEntryBean validates the projectId,releaseId,ActivityId
		  try{
			if (validateDate(timeEntry.getDate()) && validateTimeEntryBean(timeEntry)) {
				 updated = DAOFactory.getInstance()
						.getTimeEntryDAOInstance().update(timeEntry);
			}
			}catch(TimeEntryException ex){
				//For TimeEntries if got Rejected to Edit Them if user has already submitted
				TimeEntryBean timeEntryToUpdate=(TimeEntryBean) getObjectById(timeEntry.getId());
				if(getUserWorkedHoursPerDay(timeEntry.getDate())>24){
					 int workedHoursWithOutThisTimeEntry=getUserWorkedHoursPerDay(timeEntry.getDate())-timeEntryToUpdate.getHours();
					if(workedHoursWithOutThisTimeEntry+timeEntry.getHours()<=24){
					 updated = DAOFactory.getInstance()
							.getTimeEntryDAOInstance().update(timeEntry);
					 }else{
							throw new TimeEntryException(ExceptionCodes.ILLEGAL_ARGUMENT_HOURS_FIELD,
										ExceptionMessages.ILLEGAL_HOURS_ARGUMENT_PASSED); 
					      }
				}else {
					throw ex;
				}
			}
	        
          return updated;
	}

	/*
	 * Handler Method Used By Search Service
	 */
	public List<TimeEntryBean> search(TimeEntryBean timeEntry) throws Exception {
        
		
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

		List<TimeEntryBean> responseList = DAOFactory.getInstance()
				.getTimeEntryDAOInstance().getUserTimeEntries(timeEntry);
		if (responseList == null || responseList.size() == 0) {
			throw new TimeEntryException(
					ExceptionCodes.TIMEENTRY_SEARCH_STATUS,
					ExceptionMessages.TIMEENTRY_USERSEARCH);
		}
		for(TimeEntryBean timeEntryBean:responseList){
			timeEntryBean.setProjectName(ProjectHandler.getInstance().getObjectById(timeEntryBean.getProjectId()).getName());
			timeEntryBean.setActivity(ActivityHandler.getInstance().getObjectById(timeEntryBean.getActivityId()).getName());
			timeEntryBean.setReleaseVersion(ReleaseHandler.getInstance().getObjectById(timeEntryBean.getReleaseId()).getName());
			timeEntryBean.setUserName(UserHandler.getInstance().getUserName(timeEntryBean.getUserId()));	
		}
		return responseList;
	}



	/*
	 * Handler method to Check whether ReleaseId Mapped or not
	 */
	
	
	public boolean isEntryMapped(long id) throws ObjectNotFoundException {
		try {
			if (!(DAOFactory.getInstance().getTimeEntryDAOInstance()
					.isTimeEntryMappedToReleaseId(id))) {
				throw new ObjectNotFoundException();
			}
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
		return true;
	}

	
	
	
	public boolean submit(List<TimeEntryBean> getTimeEntriesToSubmit) throws ObjectNotFoundException{
		
		//Here validations is not necessary because TimeEntries which are in saved mode are already validated for the corresponding user
		List<TimeEntryBean> notSubmittedEntriesList=new ArrayList<TimeEntryBean>();
		
		for (TimeEntryBean timeEntry : getTimeEntriesToSubmit) {
			timeEntry.setUserId(ServiceRequestContextHolder.getContext()
					.getUserSessionToken().getUserId());
			if(DAOFactory.getInstance()
					.getTimeEntryDAOInstance().getTimeEntryObjectById(timeEntry.getId())!=null && DAOFactory.getInstance()
							.getTimeEntryDAOInstance().getTimeEntryObjectById(timeEntry.getId()).getStatus()==0 ){
			boolean submitted = DAOFactory.getInstance()
					.getTimeEntryDAOInstance().submit(timeEntry);
			if (!submitted) {
				notSubmittedEntriesList.add(timeEntry);
			}
			}else{
				notSubmittedEntriesList.add(timeEntry);
			}
		}
		if(notSubmittedEntriesList.isEmpty()){
		return true;}
		return false;
	}


	/*
	 * Handler Method Used By Approver to search TimeEntries
	 */

	public List<TimeEntryBean> searchTimeEntriesByApprover(TimeEntryBean searchCriteria)
			throws Exception {
		int isApprover=0;
		//validating The SeaarchCriteria 
		if(validateSearchCriteria(searchCriteria)){
     	List<TimeEntryBean> getTimeEntryBeans=new ArrayList<TimeEntryBean>();
		List<Project> associatedProjectList = ProjectHandler.getInstance().getProjectsForApprover();		
		
		for (Project associatedProject : associatedProjectList) {
			RoleBean roleBeanInput=new RoleBean(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId(),associatedProject.getId());
			RoleBean roleBeanOutput=RoleHandler.getInstance().getUserRoles(roleBeanInput);
				
		if((roleBeanOutput.getRoleIds()!=null && roleBeanOutput.getRoleIds().contains(new Long(2)))){
			    if(searchCriteria.getProjectId()==null || searchCriteria.getProjectId()==0){
			    searchCriteria.setProjectId(associatedProject.getId());
			    if(searchCriteria.getStatus()==null){
			    searchCriteria.setStatus(1);}
			    }
			   
				List<TimeEntryBean> responseList =getResultsForApprover(searchCriteria);
				for(TimeEntryBean timeEntryBean:responseList){
					timeEntryBean.setProjectName(ProjectHandler.getInstance().getObjectById(timeEntryBean.getProjectId()).getName());
					timeEntryBean.setActivity(ActivityHandler.getInstance().getObjectById(timeEntryBean.getActivityId()).getName());
					timeEntryBean.setReleaseVersion(ReleaseHandler.getInstance().getObjectById(timeEntryBean.getReleaseId()).getName());
					timeEntryBean.setUserName(UserHandler.getInstance().getUserName(timeEntryBean.getUserId()));		
				}
			    getTimeEntryBeans.addAll(responseList);
			}else{
				isApprover=isApprover+1;
			}
		  }
		//End of For Loop
		
		   if(isApprover==associatedProjectList.size()){
			   throw new TimeEntryException(ExceptionCodes.ACTION_DOESNOT_EXIST,ExceptionMessages.TIMEENTRY_USER_NOT_AUTHORIZED);
		   }
		
		return getTimeEntryBeans;
		}
		return null;
	}
	
	private List<TimeEntryBean> getResultsForApprover(TimeEntryBean searchCriteria){
		return DAOFactory.getInstance()
				.getTimeEntryDAOInstance()
				.getTimeEntriesForApprover(searchCriteria);
		
		}
	
	public TimeEntryBean getObjectById(long id) throws ObjectNotFoundException{
		
		TimeEntries timeEntry= DAOFactory.getInstance()
				.getTimeEntryDAOInstance()
				.getTimeEntryObjectById(id);
		TimeEntryBean timeEntryBean=new TimeEntryBean(timeEntry);
		timeEntryBean.setProjectName(ProjectHandler.getInstance().getObjectById(timeEntryBean.getProjectId()).getName());
		timeEntryBean.setActivity(ActivityHandler.getInstance().getObjectById(timeEntryBean.getActivityId()).getName());
		timeEntryBean.setReleaseVersion(ReleaseHandler.getInstance().getObjectById(timeEntryBean.getReleaseId()).getName());
		timeEntryBean.setUserName(UserHandler.getInstance().getUserName(timeEntryBean.getUserId()));	
		;
		if(timeEntry!=null){
			return timeEntryBean;
		}else{
			throw new ObjectNotFoundException();
			}
	}
	
	
	

	/*
	 * Handler Method used to obtain worked hours by a user for that date
	 */

	public int getUserWorkedHoursPerDay(String date) throws Exception {
		return DAOFactory.getInstance().getTimeEntryDAOInstance()
				.getUserWorkingHoursperDay(date);
	}
	
	public boolean deleteUserTimeEntriesByUserId(long id) throws ObjectNotFoundException{
		return DAOFactory.getInstance().getTimeEntryDAOInstance()
		.deleteUserTimeEntriesByUserId(id);
	}
	
	
	
	}
