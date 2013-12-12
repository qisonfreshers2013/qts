package com.qts.handler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import com.qts.exception.AuthenticateException;
import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.InvalidTimeEntryDataException;
import com.qts.exception.ObjectNotFoundException;
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

	//Validation Methods
	
	private boolean validate(TimeEntryBean timeEntry) throws Exception {
		RoleBean roleBean=new RoleBean();
		roleBean.setProjectId(timeEntry.getProjectId());
		roleBean.setUserId(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId());
		if (timeEntry.getReleaseId() == 0 || timeEntry.getActivityId() == 0
				|| timeEntry.getProjectId() == 0 || timeEntry.getHours()==0 || timeEntry.getHours()>12)
			throw new ObjectNotFoundException();
		else if (ReleasesHandler.getInstance().getObjectById(
				timeEntry.getReleaseId()) == null
				|| ProjectHandler.getInstance().getObjectById(
						timeEntry.getProjectId()) == null
				|| ActivityHandler.getInstance().getObjectById(
						timeEntry.getActivityId()) == null || timeEntry.getTask()==null || timeEntry.getUserRemarks().length()>4096)
			throw new ObjectNotFoundException();
		
		else if (ReleasesHandler.getInstance()
				.getObjectById(timeEntry.getReleaseId()).getProjectId() != timeEntry
				.getProjectId())
			throw new ObjectNotFoundException();
		
		else if(!(RoleHandler.getInstance().listUserRoles(roleBean).getRoleIds().contains(new Long(3))))
			throw new TimeEntryException(ExceptionCodes.TIMEENTRY_FILLING_IS_NOT_ALLOWED_FOR_APPROVER,ExceptionMessages.TIMEENTRY_FILLING_FOR_APPROVER);
		
		else if(getUserWorkingHoursPerDay(timeEntry.getDate())>24)
			throw new TimeEntryException(ExceptionCodes.ILLEGAL_ARGUMENT_HOURS_FIELD,ExceptionMessages.ILLEGAL_HOURS_ARGUMENT_PASSED);
		
		return true;
		
	}
	public int getUserWorkingHoursPerDay(String date){
		return DAOFactory.getInstance().getTimeEntryDAOInstance().getUserWorkingHoursperDay(date);
	}
	
	public static boolean validateDate(String date)
			throws InvalidTimeEntryDataException, ParseException {
		

		if (date == null) {
			throw new InvalidTimeEntryDataException(
				 ExceptionCodes.DATE_CANNOT_BE_NULL,
					ExceptionMessages.DATE_CANNOT_BE_NULL);
		} else {
			if (date.length() > 10 || date.length() < 8) {
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
				} 
				else {
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
						if (Integer
								.parseInt(date.substring(0, 2)) == Calendar
								.getInstance().get(Calendar.MONTH)
								&& Integer.parseInt(date
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
					if (Integer.parseInt(date.substring(0, 2)) == 2) {
						if (new GregorianCalendar().isLeapYear(Calendar
								.getInstance().get(Calendar.YEAR))) {
							if (Integer.parseInt(date.substring(
									3, 5)) > 29) {
								throw new InvalidTimeEntryDataException(
										ExceptionCodes.DATE_FORMAT_EXCEPTION,
										ExceptionMessages.DATE_EXCEPTION);
							}
						}
					} else {
						if (Integer
								.parseInt(date.substring(3, 5)) > 28) {
							throw new InvalidTimeEntryDataException(
									ExceptionCodes.DATE_FORMAT_EXCEPTION,
									ExceptionMessages.DATE_EXCEPTION);
						}
					}
					if (Integer.parseInt(date.substring(3, 5)) <= 30) {
						if (Integer
								.parseInt(date.substring(0, 2)) == Calendar
								.getInstance().get(Calendar.MONTH)
								&& Integer.parseInt(date
										.substring(3, 5)) < Calendar
										.getInstance().get(Calendar.DATE)) {
							throw new InvalidTimeEntryDataException(
									ExceptionCodes.DATE_FORMAT_EXCEPTION,
									ExceptionMessages.DATE_EXCEPTION);
						}
					}
				}
			}

			if (Integer.parseInt(date.substring(6)) != Calendar
					.getInstance().get(Calendar.YEAR)) {
				throw new InvalidTimeEntryDataException(
						ExceptionCodes.DATE_FORMAT_EXCEPTION,
						ExceptionMessages.DATE_YEAR_EXCEPTION);
			}

		}

		return true;
	}

	//End of Validation Methods
	
	
	
	/*
	 * Handler Method Used By AddEntry Service
	 */
	public boolean add(TimeEntryBean timeEntry) throws Exception {
		timeEntry.setUserId(ServiceRequestContextHolder.getContext()
				.getUserSessionToken().getUserId());
		if (validateDate(timeEntry.getDate()) && validate(timeEntry)) {
				boolean added = DAOFactory.getInstance()
						.getTimeEntryDAOInstance().add(timeEntry);
				if (!added) {
					throw new TimeEntryException(
							ExceptionCodes.TIMEENTRY_ADDITION_FAILED,
							ExceptionMessages.TIEMENTRY_ADD); // SubmissionFailedException
				}
			}
		return true;
	}

	/*
	 * Handler Method Used By RejectEntry Service by Approver
	 */

	public boolean reject(TimeEntryBean timeEntry)
			throws Exception {
		List<UserProject> associatedProjectList=UserProjectHandler.getInstance().getListOfUserProjectByUserId(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId());
		for(UserProject associatedProject:associatedProjectList){
			if (getApprovers(associatedProject.getProjectId()).contains(
					ServiceRequestContextHolder.getContext()
							.getUserSessionToken().getUserId())) {
				if (timeEntry.getId() != null && timeEntry.getRejectedComments() != null) {
					timeEntry.setProjectId(associatedProject.getProjectId());
					boolean rejected = DAOFactory.getInstance()
							.getTimeEntryDAOInstance().reject(timeEntry);
					if (rejected == false) {
						throw new TimeEntryException(
								ExceptionCodes.TIMEENTRY_REJECT_FAILED,
								ExceptionMessages.TIMEENTRY_REJECT);
					} else
					          return rejected;
				} else {
					throw new InvalidTimeEntryDataException();
				}
			}
		}
		return false;
	}

	/*
	 * Handler Method Used By ApproveEntry Service by Approver
	 */
	public boolean approve(TimeEntryBean timeEntry)
			throws Exception {
		List<UserProject> associatedProjectList=UserProjectHandler.getInstance().getListOfUserProjectByUserId(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId());
		for(UserProject associatedProject:associatedProjectList){
			if (getApprovers(associatedProject.getProjectId()).contains(
					ServiceRequestContextHolder.getContext()
							.getUserSessionToken().getUserId())) {
				if (timeEntry.getId() != null) {
					timeEntry.setProjectId(associatedProject.getProjectId());
                 boolean approved = DAOFactory.getInstance()
							.getTimeEntryDAOInstance().approve(timeEntry);
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
	public boolean delete(TimeEntryBean timeEntry) throws Exception {

        timeEntry.setUserId(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId());
		if (timeEntry.getId() != null) {
			boolean deleted = DAOFactory.getInstance()
					.getTimeEntryDAOInstance().delete(timeEntry);
			if (!deleted) {
				throw new TimeEntryException(
						ExceptionCodes.TIMEENTRYDELETIONFAILED,
						ExceptionMessages.TIMEENTRY_DELETE); 
			}
		} else {
			throw new InvalidTimeEntryDataException();
		}
		return true;
	}

	/*
	 * Handler Method Used By Update Service
	 */
	public boolean update(TimeEntryBean timeEntry) throws Exception {
		timeEntry.setUserId(ServiceRequestContextHolder.getContext()
				.getUserSessionToken().getUserId());
		if (validateDate(timeEntry.getDate()) && validate(timeEntry)) {
				boolean updated = DAOFactory.getInstance()
						.getTimeEntryDAOInstance()
						.update(timeEntry);
				if (!updated) {
					throw new TimeEntryException(
							ExceptionCodes.TIMEENTRYUPDATEFAILED,
							ExceptionMessages.TIMEENTRY_UPDATE); 
				}	
		}
		return true;

	}

	/*
	 * Handler Method Used By Search Service
	 */
	public List<TimeEntries> search(TimeEntryBean timeEntry)
			throws Exception {
		timeEntry.setUserId(ServiceRequestContextHolder.getContext()
				.getUserSessionToken().getUserId());
		List<TimeEntries> responseList = DAOFactory.getInstance()
				.getTimeEntryDAOInstance().listUserEntries(timeEntry);
		if (responseList == null) {
			throw new TimeEntryException(
					ExceptionCodes.TIMEENTRY_SEARCH_FAILED,
					ExceptionMessages.TIMEENTRY_USERSEARCH);
		}

		return responseList;
	}

	/*
	 * Handler Method Used By Search Service for Approver
	 */


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

	public boolean submit(List<TimeEntryBean> listOfTimeEntriesToSubmit)
			throws Exception {
		for (TimeEntryBean timeEntry : listOfTimeEntriesToSubmit) {
			timeEntry.setUserId(ServiceRequestContextHolder.getContext()
					.getUserSessionToken().getUserId());
			boolean submitted = DAOFactory.getInstance()
					.getTimeEntryDAOInstance().submit(timeEntry);
			if (!submitted) {
				throw new TimeEntryException(
						ExceptionCodes.TIMEENTRYSUBMISSIONFAILED,
						ExceptionMessages.TIMEENTRY_SUBMIT);
			}
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
	
	
	
	/*
	 * Handler Method Used By Search Service for Approver
	 */
	
	public List<TimeEntries> approverSearch(
			TimeEntryBean timeEntry) throws Exception {
		if(timeEntry.getFrom()!=null && timeEntry.getTo()==null && timeEntry.getUserId()==null && timeEntry.getStatus()==null && timeEntry.getProjectId()==null){
			throw new InvalidTimeEntryDataException(ExceptionCodes.TIMEENTRY_SEARCH_FAILED,ExceptionMessages.FORMFIELD_TO_NOT_PRESENT);
		}
	List<TimeEntries> timeEntriesToApporve=new ArrayList<TimeEntries>();
	List<UserProject> associatedProjectList=UserProjectHandler.getInstance().getListOfUserProjectByUserId(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId());
	for(UserProject associatedProject:associatedProjectList){
		if(getApprovers(associatedProject.getProjectId()).contains(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId())){
		         
			List<TimeEntries> responseList = DAOFactory.getInstance()
					.getTimeEntryDAOInstance()
					.listEntriesToApprove(timeEntry);
			if(responseList!=null){
				timeEntriesToApporve.addAll(responseList);
			}
			listOfApprovers.remove(ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId());
		}
	}
	if(timeEntriesToApporve.size()==0){
		throw new TimeEntryException(ExceptionCodes.TIMEENTRY_SEARCH_FAILED,ExceptionMessages.TIMEENTRY_APPROVER_NOT_AUTHORIZED);
	}
	return timeEntriesToApporve;
	}
		
	
		
}
