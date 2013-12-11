package com.qts.persistence.dao;

/**
 * Implementation class for TimeEntryDAO to perform Business Operation
 * @author Ajay
 */
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.qts.model.TimeEntries;
import com.qts.model.TimeEntriesForm;
import com.qts.service.common.ServiceRequestContextHolder;

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
	 * @see
	 * com.qts.persistence.dao.TimeEntryDAO#parseDateToLong(java.lang.String)
	 * Method to convert String pattern of Date to Long
	 */
	public long parseDateToLong(String date) {

		try {
			Date dateObj=DateUtils.parseDate(date,"MM/dd/yyyy","MM-dd-yyyy");
	          return dateObj.getTime();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in TimeEntryDAOImpl.getDate");
		}
		return 0;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qts.persistence.dao.TimeEntryDAO#addTimeEntry(com.qts.model.
	 * TimeEntriesForm, org.hibernate.Session) Method to Add TimeEntry to
	 * DATABASE TimeEntries Storage Table
	 */
	public boolean addTimeEntry(TimeEntriesForm timeEntry, Session session) {

		TimeEntries addentry = new TimeEntries();

		addentry.setUserId(timeEntry.getUserId());
		addentry.setDate(parseDateToLong(timeEntry.getDate()));
		addentry.setHours(timeEntry.getHours());
		addentry.setProjectId(timeEntry.getProjectId());
		addentry.setActivityId(timeEntry.getActivityId());
		addentry.setReleaseId(timeEntry.getReleaseId());
		addentry.setTask(timeEntry.getTask());
		addentry.setRemarks(timeEntry.getUserRemarks());
		addentry.setCts(parseDateToLong(timeEntry.getDate()));
		addentry.setMts(parseDateToLong(timeEntry.getDate()));
		addentry.setCreated_by(timeEntry.getUserId());
		addentry.setModified_by(timeEntry.getUserId());
		addentry.setStatus(0);
		TimeEntries timeentrydao = null;

		timeentrydao = (TimeEntries) saveObject(addentry);

		if (timeentrydao != null) {
			return true;
		}

		return false;

	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qts.persistence.dao.TimeEntryDAO#rejectTimeEntry(com.qts.model.
	 * TimeEntriesForm) Method Used by Approver to REJECT Submitted TimeEntry
	 */
	public TimeEntries rejectTimeEntry(TimeEntriesForm timeEntry) {
		Session session = getSession();
		try {
			
			Query query = session
					.createQuery("Update TimeEntries set status=:status,rejectedComments=:rejectedComments where id="
							+ timeEntry.getId()+"and porjectId="+timeEntry.getProjectId());
			query.setInteger("status", 3);
			query.setString("rejectedComments", timeEntry.getRejectedComments());
			query.executeUpdate();
			List<TimeEntries> rejectedTimeEntry=session.createQuery("from TimeEntries where id="+timeEntry.getId()).list();
			
			return rejectedTimeEntry.get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qts.persistence.dao.TimeEntryDAO#approveTimeEntry(com.qts.model.
	 * TimeEntriesForm) Method Used by Approver to Approve An TimeEntry
	 */
	public boolean approveTimeEntry(TimeEntriesForm timeEntry) {
		Session session = getSession();
		try {
			
			Query query = session
					.createQuery("Update TimeEntries set status=:status,approvedComments=:approvedComments where id="
							+ timeEntry.getId()+"projectId"+timeEntry.getProjectId());
			query.setInteger("status", 2);
			query.setString("approvedComments",timeEntry.getApprovedComments());
			query.executeUpdate();
			
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qts.persistence.dao.TimeEntryDAO#deleteTimeEntry(com.qts.model.
	 * TimeEntriesForm)Method Used to Delete TimeEntry from DataBase
	 */
	public boolean deleteTimeEntry(TimeEntriesForm deleteEntry) {
		Session session = getSession();
		try {
			
			Query query = session.createQuery("Delete TimeEntries where id="
					+ deleteEntry.getId()+"and userId="+deleteEntry.getUserId()+"and status="+0);
			query.executeUpdate();
			
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qts.persistence.dao.TimeEntryDAO#updateTimeEntry(com.qts.model.
	 * TimeEntriesForm) Method To update An TimeEntry
	 */
	public boolean updateTimeEntry(TimeEntriesForm updateWithData) {
		Session session = getSession();
		try {
             
			
			Query query = session
					.createQuery("Update TimeEntries set hours=:hours,projectId=:projectId,releaseId=:releaseId,task=:task,activityId=:activityId,remarks=:remarks,date=:date where id="
							+ updateWithData.getId()+"and userId="+updateWithData.getUserId()+"and status="+0);
			query.setInteger("hours", updateWithData.getHours());
			query.setLong("projectId", updateWithData.getProjectId());
			query.setLong("releaseId", updateWithData.getReleaseId());
			query.setString("task", updateWithData.getTask());
			query.setLong("activityId", updateWithData.getActivityId());
			query.setString("remarks", updateWithData.getUserRemarks());
			query.setLong("date", parseDateToLong(updateWithData.getDate()));
			int updated=query.executeUpdate();
			
			if (updated != 0) {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qts.persistence.dao.TimeEntryDAO#getDateInString(long) Method
	 * Used to Convert Date from Long to String format of order DDMMYYYY
	 */
	public String getDateInString(long timeinMilliSeconds) {
		try {
			Timestamp date = new Timestamp(timeinMilliSeconds);
			String dateInString = "";
			dateInString = dateInString
					.concat(date.toString().substring(8, 10))
					.concat(date.toString().substring(5, 7))
					.concat(date.toString().substring(0, 4));
			return dateInString;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qts.persistence.dao.TimeEntryDAO#deleteTimeEntryById(int,
	 * org.hibernate.Session) Method used to Delete TimeEntry By Id
	 */
	public Session deleteTimeEntryById(int id, Session session) {
		try {

			Query query = session
					.createQuery("Delete from TimeEntries where id=" + id);
			int deleted = query.executeUpdate();

			if (deleted != 0) {
				return session;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean getTimeEntryObjectById(long id) {
		Session session = getSession();
		try {
			

			List<TimeEntries> mapped = session.createQuery("from TimeEntries where release_Id=" + id).list();

			if (mapped.size() != 0) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean submitTimeEntries(TimeEntriesForm submitData) {

		Session session = getSession();
		try {
			
			Query query = session
					.createQuery("Update TimeEntries set status=:newStatus where id="
							+ submitData.getId()
							+ "and userId="
							+ ServiceRequestContextHolder.getContext()
									.getUserSessionToken().getUserId());
			query.setInteger("newStatus", 1);
			int submitted = query.executeUpdate();
			
			if (submitted == 0) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}
	
	public long getPreviousWorkingDay(){
		Session session=getSession();
		try{
			Query query=session.createQuery("from TimeEntries order by date desc");
			query.setMaxResults(1);
			return ((TimeEntries) query.list().get(0)).getDate();
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<TimeEntries> listUserEntries(TimeEntriesForm timeEntry){
		Session session=getSession();
		try{
			
			Criteria searchUserCriteria = session.createCriteria(TimeEntries.class);
		  searchUserCriteria.setProjection(
		         Projections.projectionList().
		           add(Projections.property("id")).
		           add(Projections.property("date")).
		           add(Projections.property("projectId")).
		           add(Projections.property("releaseId")).
		           add(Projections.property("task")).
		           add(Projections.property("activityId")).
		           add(Projections.property("hours")).
		           add(Projections.property("status")).
		           add(Projections.property("remarks"))
		           );
		  if(timeEntry.getDate()==null && timeEntry.getProjectId()==null)
		  searchUserCriteria.add(Restrictions.eq("date",getPreviousWorkingDay()));
		  else if(timeEntry.getDate()!=null && timeEntry.getProjectId()!=null)
			  searchUserCriteria.add(Restrictions.eq("date",parseDateToLong(timeEntry.getDate())));
		  else if(timeEntry.getDate()==null && timeEntry.getProjectId()!=null)
			  searchUserCriteria.add(Restrictions.eq("projectId",timeEntry.getProjectId()));
		  else if(timeEntry.getDate()!=null && timeEntry.getProjectId()!=null){
			  searchUserCriteria.add(Restrictions.eq("date",parseDateToLong(timeEntry.getDate())));
			  searchUserCriteria.add(Restrictions.eq("projectId",timeEntry.getProjectId()));
		  }
		  List<TimeEntries> submittedData=searchUserCriteria.list();
		 
		  return submittedData;
		  
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
		}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TimeEntries> listEntriesToApprove(TimeEntriesForm timeEntry){
		Session session=getSession();
		try{

			Calendar getPreviousWeekDate=GregorianCalendar.getInstance();
			getPreviousWeekDate.add(Calendar.DAY_OF_YEAR,-7);
			getPreviousWeekDate.getTimeInMillis();

			
			Criteria searchUserCriteria = session.createCriteria(TimeEntries.class);
		  searchUserCriteria.setProjection(
		         Projections.projectionList().
		           add(Projections.property("id")).
		           add(Projections.property("date")).
                   add(Projections.property("userId")).
		           add(Projections.property("projectId")).
		           add(Projections.property("releaseId")).
		           add(Projections.property("task")).
		           add(Projections.property("activityId")).
		           add(Projections.property("hours")).
		           add(Projections.property("status")).
		           add(Projections.property("remarks")).
		           add(Projections.property("approvedComments")).
		           add(Projections.property("rejectedComments"))
		           );
		  if(timeEntry.getFrom()==null && timeEntry.getProjectId()==null && timeEntry.getUserId()==null && timeEntry.getTo()==null && timeEntry.getStatus()==null){
          searchUserCriteria.add(Restrictions.between("date",getPreviousWeekDate.getTimeInMillis(),new Date().getTime()));
          searchUserCriteria.add(Restrictions.eq("status",1));
		  }
		  else if(timeEntry.getFrom()!=null && timeEntry.getTo()!=null){
			  searchUserCriteria.add(Restrictions.between("date",parseDateToLong(timeEntry.getFrom()),parseDateToLong(timeEntry.getTo())));
		      searchUserCriteria.add(Restrictions.eq("projectId",timeEntry.getProjectId()));
		      searchUserCriteria.add(Restrictions.eq("userId",timeEntry.getUserId()));
		      searchUserCriteria.add(Restrictions.eq("status",timeEntry.getStatus()));
		       }
		  else if(timeEntry.getFrom()!=null && timeEntry.getTo()==null){
			  searchUserCriteria.add(Restrictions.eq("projectId",timeEntry.getProjectId()));
			  searchUserCriteria.add(Restrictions.eq("status",timeEntry.getStatus()));
			  searchUserCriteria.add(Restrictions.between("date",parseDateToLong(timeEntry.getFrom()),new Date().getTime()));
			  searchUserCriteria.add(Restrictions.eq("userId",timeEntry.getUserId()));

		  }
		  else if(timeEntry.getFrom()==null && timeEntry.getTo()!=null){
			  return null;
		  }
		  List<TimeEntries> submittedData=searchUserCriteria.list();
		 
		  return submittedData;	
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		}
	
	
	
	
}
