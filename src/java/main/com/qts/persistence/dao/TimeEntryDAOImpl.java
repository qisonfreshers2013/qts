package com.qts.persistence.dao;

/**
 * Implementation class for TimeEntryDAO to perform Business Operation
 * @author Ajay
 */
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.qts.common.Utils;
import com.qts.exception.ExceptionCodes;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.TimeEntryException;
import com.qts.model.TimeEntries;
import com.qts.model.TimeEntryBean;
import com.qts.service.common.ServiceRequestContextHolder;
import com.sun.xml.xsom.impl.scd.ParseException;

public class TimeEntryDAOImpl extends BaseDAOImpl implements TimeEntryDAO {

	private static TimeEntryDAOImpl Instance;

	private TimeEntryDAOImpl() {
	}

	public static TimeEntryDAO getTimeEntryDAOInstance() {
		if (Instance == null) {
			Instance = new TimeEntryDAOImpl();
		}
		return Instance;
	}



	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qts.persistence.dao.TimeEntryDAO#addTimeEntry(com.qts.model.
	 * TimeEntriesForm, org.hibernate.Session) Method to Add TimeEntry to
	 * DATABASE TimeEntries Storage Table
	 */
     public boolean add(TimeEntryBean timeEntry){
		   
		  TimeEntries addentry = new TimeEntries();

		  addentry.setUserId(timeEntry.getUserId());
		  try {
		   addentry.setCts(Utils.parseDateToLong((timeEntry.getDate())));
		   addentry.setDate(Utils.parseDateToLong(timeEntry.getDate()));
		   addentry.setMts(Utils.parseDateToLong((timeEntry.getDate())));
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		  addentry.setHours(timeEntry.getHours());
		  addentry.setProjectId(timeEntry.getProjectId());
		  addentry.setActivityId(timeEntry.getActivityId());
		  addentry.setReleaseId(timeEntry.getReleaseId());
		  addentry.setTask(timeEntry.getTask());
		  addentry.setRemarks(timeEntry.getUserRemarks());
		  addentry.setCreated_by(timeEntry.getUserId());
		  addentry.setModified_by(timeEntry.getUserId());
		  addentry.setStatus(0);
		  TimeEntries timeEntryAdded = null;

		  timeEntryAdded = (TimeEntries) saveObject(addentry);
		       if(timeEntryAdded!=null)
		        return true;

		  return false;

		 }

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qts.persistence.dao.TimeEntryDAO#rejectTimeEntry(com.qts.model.
	 * TimeEntriesForm) Method Used by Approver to REJECT Submitted TimeEntry
	 */

	public boolean reject(TimeEntryBean timeEntry) throws ObjectNotFoundException {
		Session session = getSession();
		try {
			Query query = session
					.createQuery("Update TimeEntries set status=:status,rejectedComments=:rejectedComments where id="
							+ timeEntry.getId());
			query.setInteger("status", 3);
			query.setString("rejectedComments", timeEntry.getRejectedComments());
			int rejectedCount=query.executeUpdate();
			
			if(rejectedCount!=0)
			return true;
			else
				throw new ObjectNotFoundException(ExceptionCodes.OBJECT_NOT_FOUND,"Invalid Id");
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qts.persistence.dao.TimeEntryDAO#approveTimeEntry(com.qts.model.
	 * TimeEntriesForm) Method Used by Approver to Approve An TimeEntry
	 */
	public boolean approve(TimeEntryBean timeEntry) throws ObjectNotFoundException {
		Session session = getSession();
		try {
			
			Query query = session
					.createQuery("Update TimeEntries set status=:status,approvedComments=:approvedComments where id="
							+ timeEntry.getId());
			query.setInteger("status", 2);
			query.setString("approvedComments", timeEntry.getApprovedComments());
			int approved=query.executeUpdate();
			if(approved!=0)
			return true;
			else 
				throw new ObjectNotFoundException(ExceptionCodes.OBJECT_NOT_FOUND,"Invalid Id");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
			
		} 
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qts.persistence.dao.TimeEntryDAO#deleteTimeEntry(com.qts.model.
	 * TimeEntriesForm)Method Used to Delete TimeEntry from DataBase
	 */
	public boolean delete(TimeEntryBean deleteTimeEntry) throws ObjectNotFoundException {
		Session session = getSession();
		try {
			
			Query query = session.createQuery("Delete TimeEntries where id="
					+ deleteTimeEntry.getId() + "and userId="
					+ deleteTimeEntry.getUserId() + "and status=" + 0);

			int isDeleted=query.executeUpdate();
			if(isDeleted!=0){
			return true;}
			else 
				throw new ObjectNotFoundException(ExceptionCodes.OBJECT_NOT_FOUND,"Invalid Id");
			
		} catch (ObjectNotFoundException e) {
			throw e;
		} 


	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qts.persistence.dao.TimeEntryDAO#updateTimeEntry(com.qts.model.
	 * TimeEntriesForm) Method To update An TimeEntry
	 */
	public boolean update(TimeEntryBean updateTimeEntry) throws ObjectNotFoundException {
		Session session = getSession();
		try{
			Query query = session
					.createQuery("Update TimeEntries set hours=:hours,projectId=:projectId,releaseId=:releaseId,task=:task,activityId=:activityId,remarks=:remarks,date=:date where id="
							+ updateTimeEntry.getId()
							+ "and userId="
							+ updateTimeEntry.getUserId() + "and status=" + 0);
			query.setInteger("hours", updateTimeEntry.getHours());
			query.setLong("projectId", updateTimeEntry.getProjectId());
			query.setLong("releaseId", updateTimeEntry.getReleaseId());
			query.setString("task", updateTimeEntry.getTask());
			query.setLong("activityId", updateTimeEntry.getActivityId());
			query.setString("remarks", updateTimeEntry.getUserRemarks());
				query.setLong("date", Utils.parseDateToLong((updateTimeEntry.getDate())));
		
			int updated = query.executeUpdate();
			if (updated != 0) {
				return true;
			}
			else{
				throw new ObjectNotFoundException(ExceptionCodes.OBJECT_NOT_FOUND,"Invalid Id");
			    }
		}catch(ObjectNotFoundException e){
			throw e;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
		
	}



	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qts.persistence.dao.TimeEntryDAO#deleteTimeEntryById(int,
	 * org.hibernate.Session) Method used to Delete TimeEntry By Id
	 */
	public Session deleteTimeEntryById(int id, Session session) throws ObjectNotFoundException {
		try {

			Query query = session
					.createQuery("Delete from TimeEntries where id=" + id);
			int deleted = query.executeUpdate();

			if (deleted != 0) {
				return session;
			}
			else 
				throw new ObjectNotFoundException(ExceptionCodes.OBJECT_NOT_FOUND,"Invalid Id");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isTimeEntryMappedToReleaseId(long id) throws ObjectNotFoundException {
		Session session = getSession();
		try {

			List<TimeEntries> mapped = session.createQuery(
					"from TimeEntries where release_Id=" + id).list();
			if (mapped.size() != 0) {
				return true;
				}
			else
				throw new ObjectNotFoundException(ExceptionCodes.OBJECT_NOT_FOUND,"Invalid Id");

		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	}

	
	public TimeEntries getTimeEntryObjectById(long id){
		Session session = getSession();
      List<TimeEntries> mapped = session.createQuery(
					"from TimeEntries where id=" + id).list();
			if (!mapped.isEmpty()) {
				return mapped.get(0);
				}
			else
				return null;

		} 
	
	@Override
	public boolean submit(TimeEntryBean submitTimeEntry) {
       
		Session session = getSession();
		try {
			Query query = session
					.createQuery("Update TimeEntries set status=:newStatus where id="
							+ submitTimeEntry.getId()
							+ "and userId="
							+ ServiceRequestContextHolder.getContext()
									.getUserSessionToken().getUserId());
			query.setInteger("newStatus", 1);
			int isSubmitted = query.executeUpdate();
			
			if (isSubmitted == 0) {
			  return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return true;
	}

	
	public long getPreviousWorkingDay(){
		Session session=getSession();
	Criteria sortingByDateForUser=session.createCriteria(TimeEntries.class);
	sortingByDateForUser.setProjection(Projections.projectionList()
			.add(Projections.property("date")));
	        sortingByDateForUser.addOrder(Order.desc("date"));
	        sortingByDateForUser.add(Restrictions.eq("userId", ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId()));
	        sortingByDateForUser.setMaxResults(1);
			List<Long> previousWorkingDayForUser=sortingByDateForUser.list();
			if(previousWorkingDayForUser.size()!=0)
		return previousWorkingDayForUser.get(0);
			return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	

	public List<TimeEntries> getUserTimeEntries(TimeEntryBean searchCriteria) {
		Session session=getSession();
		try {
             
			Criteria userSearchCriteria = session
					.createCriteria(TimeEntries.class);
			userSearchCriteria.setProjection(Projections.projectionList()
					.add(Projections.property("id"))
					.add(Projections.property("date"))
					.add(Projections.property("userId"))
					.add(Projections.property("projectId"))
					.add(Projections.property("releaseId"))
					.add(Projections.property("task"))
					.add(Projections.property("activityId"))
					.add(Projections.property("hours"))
					.add(Projections.property("status"))
					.add(Projections.property("approvedComments"))
					.add(Projections.property("rejectedComments"))
					.add(Projections.property("remarks")));
			if (searchCriteria.getDate() == null && searchCriteria.getProjectId() == null){
				userSearchCriteria.add(Restrictions.between("date",
								getPreviousWorkingDay(),Calendar.getInstance().getTimeInMillis()));
						userSearchCriteria.add(Restrictions.eq("userId",
											searchCriteria.getUserId()));
					    userSearchCriteria.addOrder(Order.desc("date"));
				}
						
			else if (searchCriteria.getDate() != null
					&& searchCriteria.getProjectId() == null)
				userSearchCriteria.add(Restrictions
						.conjunction()
						.add(Restrictions.eq("date",
								Utils.parseDateToLong((searchCriteria.getDate()))))
						.add(Restrictions.eq("userId",
								searchCriteria.getUserId())));
			else if (searchCriteria.getDate() == null
					&& searchCriteria.getProjectId() != null)
				userSearchCriteria.add(Restrictions
						.conjunction()
						.add(Restrictions.eq("projectId",
								searchCriteria.getProjectId()))
						.add(Restrictions.eq("userId",
								searchCriteria.getUserId())));
			else if (searchCriteria.getDate() != null
					&& searchCriteria.getProjectId() != null) {
				userSearchCriteria.add(Restrictions
						.conjunction()
						.add(Restrictions.eq("projectId",
								searchCriteria.getProjectId()))
						.add(Restrictions.eq("date",
								Utils.parseDateToLong((searchCriteria.getDate()))))
						.add(Restrictions.eq("userId",
								searchCriteria.getUserId())));
			}
			List<TimeEntries> submittedData = userSearchCriteria.list();
			
			return submittedData;

		} catch (Exception e) {
			e.printStackTrace();
		} 

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override

	public List<TimeEntries> getTimeEntriesForApprover(TimeEntryBean searchCriteria) {
		Session session = getSession();
		try {

			Calendar getPreviousWeekDate = GregorianCalendar.getInstance();
			getPreviousWeekDate.add(Calendar.DAY_OF_YEAR, -7);
			getPreviousWeekDate.getTimeInMillis();
			Criteria approverSearchCriteria = session
					.createCriteria(TimeEntries.class);
			approverSearchCriteria.setProjection(Projections.projectionList()
					.add(Projections.property("id"))
					.add(Projections.property("date"))
					.add(Projections.property("userId"))
					.add(Projections.property("projectId"))
					.add(Projections.property("releaseId"))
					.add(Projections.property("task"))
					.add(Projections.property("activityId"))
					.add(Projections.property("hours"))
					.add(Projections.property("status"))
					.add(Projections.property("remarks"))
					.add(Projections.property("approvedComments"))
					.add(Projections.property("rejectedComments")));
			if (searchCriteria.getFrom() == null && searchCriteria.getProjectId() == null
					&& searchCriteria.getUserId() == null
					&& searchCriteria.getTo() == null
					&& searchCriteria.getStatus() == null) {
				approverSearchCriteria.add(Restrictions.between("date",
						getPreviousWeekDate.getTimeInMillis(),
						new Date().getTime()));
				approverSearchCriteria.add(Restrictions.eq("status", 1));
			} else if (searchCriteria.getFrom() != null && searchCriteria.getTo() != null && searchCriteria.getUserId()!=null && searchCriteria.getProjectId()!=null && searchCriteria.getStatus()!=null) {
				approverSearchCriteria.add(Restrictions
						.conjunction()
						.add(Restrictions.between("date",
								Utils.parseDateToLong((searchCriteria.getFrom())),
								Utils.parseDateToLong((searchCriteria.getTo()))))
						.add(Restrictions.eq("projectId",
								searchCriteria.getProjectId()))
						.add(Restrictions.eq("userId", searchCriteria.getUserId()))
						.add(Restrictions.eq("status", searchCriteria.getStatus())));

			} else if (searchCriteria.getFrom() != null && searchCriteria.getTo() == null && searchCriteria.getUserId()!=null && searchCriteria.getProjectId()!=null && searchCriteria.getStatus()!=null) {
				approverSearchCriteria.add(Restrictions
						.conjunction()
				        .add(Restrictions.eq("projectId",
						searchCriteria.getProjectId()))
				        .add(Restrictions.eq("status",
						searchCriteria.getStatus()))
				        .add(Restrictions.between("date",
						Utils.parseDateToLong((searchCriteria.getFrom())),
						new Date().getTime()))
				        .add(Restrictions.eq("userId",
						searchCriteria.getUserId())));

			} else if (searchCriteria.getFrom() == null && searchCriteria.getTo() == null && searchCriteria.getUserId()!=null && searchCriteria.getProjectId()!=null && searchCriteria.getStatus()!=null) {
				approverSearchCriteria.add(Restrictions
						.conjunction()
						.add(Restrictions.eq("projectId",
								searchCriteria.getProjectId()))
						.add(Restrictions.eq("userId", searchCriteria.getUserId()))
						.add(Restrictions.eq("status", searchCriteria.getStatus())));
			}
			else if (searchCriteria.getFrom() == null && searchCriteria.getProjectId() == null
					&& searchCriteria.getUserId() == null
					&& searchCriteria.getTo() == null
					&& searchCriteria.getStatus() != null) {
				approverSearchCriteria.add(Restrictions.eq("status", searchCriteria.getStatus()));
			}
			else if (searchCriteria.getFrom() == null && searchCriteria.getTo() == null && searchCriteria.getUserId()==null && searchCriteria.getStatus()==null) {
				approverSearchCriteria.add(Restrictions
						.conjunction()
						.add(Restrictions.eq("projectId",
								searchCriteria.getProjectId()))
						.add(Restrictions.between("date",
						getPreviousWeekDate.getTimeInMillis(),
						new Date().getTime()))
						.add(Restrictions.eq("status",1)));
			}
			else if (searchCriteria.getFrom() == null && searchCriteria.getTo() == null && searchCriteria.getUserId()==null) {
				approverSearchCriteria.add(Restrictions
						.conjunction()
						.add(Restrictions.eq("projectId",
								searchCriteria.getProjectId()))
						.add(Restrictions.eq("status", searchCriteria.getStatus())));
			}
			else if (searchCriteria.getFrom() == null && searchCriteria.getTo() == null && searchCriteria.getProjectId()==null) {
				approverSearchCriteria.add(Restrictions
						.conjunction()
						.add(Restrictions.eq("userId",
								searchCriteria.getUserId()))
						.add(Restrictions.eq("status", searchCriteria.getStatus())));
			}
			else if(searchCriteria.getFrom()!=null && searchCriteria.getTo()!=null && searchCriteria.getStatus()!=null && searchCriteria.getUserId()==null && searchCriteria.getProjectId()==null){
				approverSearchCriteria.add(Restrictions
				.conjunction()
				.add(Restrictions.between("date",
						Utils.parseDateToLong((searchCriteria.getFrom())),
						Utils.parseDateToLong((searchCriteria.getTo()))))
						.add(Restrictions.eq("status", searchCriteria.getStatus())));
			}
		
			List<TimeEntries> submittedData = approverSearchCriteria.list();
			
			return submittedData;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public int getUserWorkingHoursperDay(String date) {
		Session session = getSession();
		int sum=0;
		try{
		Criteria getHours = session.createCriteria(TimeEntries.class);
		getHours.setProjection(Projections.projectionList().add(Projections.property("hours")));
		getHours.add(Restrictions
				.conjunction()
		        .add(Restrictions.eq("date",
				Utils.parseDateToLong((date))))
		        .add(Restrictions.eq("userId",
				ServiceRequestContextHolder.getContext().getUserSessionToken().getUserId())));
		List<Integer> userWorkingHoursPerDay=getHours.list();
		if(userWorkingHoursPerDay!=null){
			for(Integer value:userWorkingHoursPerDay){
				sum=sum+value;
			}
		}else {return -1;}
		}
		catch(Exception ex){
					ex.printStackTrace();
				}
		return sum;
		
	}

}
