package com.qts.persistence.dao;
/**
 * Implementation class for TimeEntryDAO to perform Business Operation
 * @author Ajay
 */
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;



import org.hibernate.Query;
import org.hibernate.Session;

import com.qts.exception.ObjectNotFoundException;
import com.qts.model.TimeEntries;
import com.qts.model.TimeEntriesForm;

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
	 * @see com.qts.persistence.dao.TimeEntryDAO#parseDateToLong(java.lang.String)
	 * Method to convert String pattern of Date to Long
	 */
	public long parseDateToLong(String date) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(sdf.parse(date));
			return calendar.getTimeInMillis();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in TimeEntryDAOImpl.getDate");
		}
		return 0;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.qts.persistence.dao.TimeEntryDAO#addTimeEntry(com.qts.model.TimeEntriesForm, org.hibernate.Session)
	 * Method to Add TimeEntry to DATABASE TimeEntries Storage Table
	 */
	public boolean addTimeEntry(TimeEntriesForm timeEntry,Session session) {
       
		TimeEntries addentry = new TimeEntries();

		addentry.setUserId(timeEntry.getUserId());
		addentry.setDate(parseDateToLong(timeEntry.getDate()));
		addentry.setHours(timeEntry.getHours());
		addentry.setProjectId(timeEntry.getProjectId());
		addentry.setActivityId(timeEntry.getActivityId());
		addentry.setReleaseId(timeEntry.getReleaseId());
		addentry.setTask(timeEntry.getTask());
		addentry.setRemarks(timeEntry.getRemarks());
		addentry.setCts(parseDateToLong(timeEntry.getDate()));
		addentry.setMts(parseDateToLong(timeEntry.getDate()));
		addentry.setCreated_by(timeEntry.getUserId());
		addentry.setModified_by(timeEntry.getUserId());
		addentry.setStatus(0);
		TimeEntries timeentrydao=null; 
        if(session!=null){
        	timeentrydao= (TimeEntries) saveObject(addentry,session);
		}
        else{
        	timeentrydao=(TimeEntries) saveObject(addentry);
        }
		if (timeentrydao != null) {
			return true;
		}

		return false;
		
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.qts.persistence.dao.TimeEntryDAO#rejectTimeEntry(com.qts.model.TimeEntriesForm)
	 * Method Used by Approver to REJECT Submitted TimeEntry 
	 */
	public boolean rejectTimeEntry(TimeEntriesForm timeEntry) {
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session
					.createQuery("Update TimeEntries set status=:status where id="
							+ timeEntry.getId());
			query.setInteger("status", 3);
			query.executeUpdate();
			session.getTransaction().commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.qts.persistence.dao.TimeEntryDAO#approveTimeEntry(com.qts.model.TimeEntriesForm)
	 * Method Used by Approver to Approve An TimeEntry
	 */
	public boolean approveTimeEntry(TimeEntriesForm timeEntry) {
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session
					.createQuery("Update TimeEntries set status=:status where id="
							+ timeEntry.getId());
			query.setInteger("status", 2);
			query.executeUpdate();
			session.getTransaction().commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.qts.persistence.dao.TimeEntryDAO#deleteTimeEntry(com.qts.model.TimeEntriesForm)
	 *Method Used to Delete TimeEntry from DataBase 
	 */
	public boolean deleteTimeEntry(TimeEntriesForm deleteEntry) {
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("Delete TimeEntries where id="
					+ deleteEntry.getId());
			query.executeUpdate();
			session.getTransaction().commit();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.qts.persistence.dao.TimeEntryDAO#updateTimeEntry(com.qts.model.TimeEntriesForm)
	 * Method To update An TimeEntry
	 */
	public boolean updateTimeEntry(TimeEntriesForm updateWithData) {
		Session session = SessionFactoryUtil.getInstance().getCurrentSession();
		try {
			
			session.beginTransaction();
			session=deleteTimeEntryById(updateWithData.getId(),session);
			if(session!=null){
			addTimeEntry(updateWithData,session);
			session.getTransaction().commit();
			
			return true;}
		    }catch (Exception e) {
			e.printStackTrace();
		    }
           return false;
		}
	
	@SuppressWarnings("unchecked")
	/*
	 * (non-Javadoc)
	 * @see com.qts.persistence.dao.TimeEntryDAO#searchTimeEntriesForUser(com.qts.model.TimeEntriesForm)
	 * Method To Used by Search to Search The TimeEntries Submitted By User
	 */
	public List<TimeEntries> searchTimeEntriesForUser(TimeEntriesForm timeEntry)
	{
        Session session=SessionFactoryUtil.getInstance().getCurrentSession();
        try{
        	session.beginTransaction();
        	Query searchResult=null;
        	if(timeEntry.getFrom()==null && timeEntry.getTo()==null && timeEntry.getProjectId()==null){
        		searchResult=session.createQuery("from TimeEntries where userId="+timeEntry.getUserId());
        		return searchResult.list();
        	    }
        	else{
        		if(timeEntry.getFrom()==null && timeEntry.getTo()==null && timeEntry.getProjectId()!=null){
        			searchResult=session.createQuery("from TimeEntries where userId="+timeEntry.getUserId()+"  and projectId="+timeEntry.getProjectId());
        			return searchResult.list(); 
        		    }else{
                    	 if(timeEntry.getFrom()!=null && timeEntry.getTo()==null && timeEntry.getProjectId()!=null){
                    		 searchResult=session.createQuery("from TimeEntries where userId="+timeEntry.getUserId()+"  and projectId="+timeEntry.getProjectId()+"and date between "+parseDateToLong(timeEntry.getFrom())+" and "+new Date().getTime());
                    		 return searchResult.list();        
                    	     }else{
                    	            	 if(timeEntry.getFrom()!=null && timeEntry.getTo()==null && timeEntry.getProjectId()==null){
                    	            		 searchResult=session.createQuery("from TimeEntries where userId="+timeEntry.getUserId()+"and date between "+parseDateToLong(timeEntry.getFrom())+" and "+new Date().getTime());	 
                    	            		 return searchResult.list();
                    	            	 }else{
                    	            		 if(timeEntry.getFrom()!=null && timeEntry.getTo()!=null && timeEntry.getProjectId()!=null){
                        	            		 searchResult=session.createQuery("from TimeEntries where userId="+timeEntry.getUserId()+"and date between "+parseDateToLong(timeEntry.getFrom())+" and "+parseDateToLong(timeEntry.getTo()));	 
                        	            		 return searchResult.list();
                    	            	 }
                    	             }
                     }
        	}
        	}
           session.getTransaction().commit();
            }catch(Exception e){
        	e.printStackTrace();
            }
	return null;
		
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see com.qts.persistence.dao.TimeEntryDAO#getDateInString(long)
	 * Method Used to Convert Date from Long to String format of order DDMMYYYY
    */
	public String getDateInString(long timeinMilliSeconds) {
		try{
			Timestamp date=new Timestamp(timeinMilliSeconds);
		     String dateInString="";
		    dateInString=dateInString.concat(date.toString().substring(8,10)).concat(date.toString().substring(5,7)).concat(date.toString().substring(0,4));
		   return dateInString;
		}catch(Exception e){
			e.printStackTrace();
		
		}
		return null;
	}
	@Override
	/*
	 * (non-Javadoc)
	 * @see com.qts.persistence.dao.TimeEntryDAO#deleteTimeEntryById(int, org.hibernate.Session)
	 * Method used to Delete TimeEntry By Id
	 */
	public Session deleteTimeEntryById(int id,Session session)
	{
		try{
			
			Query query=session.createQuery("Delete from TimeEntries where id="+id);
			int deleted=query.executeUpdate();
		
			if(deleted!=0){
				return session;
			}
		   }catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<TimeEntries> searchTimeEntriesForApprover(TimeEntriesForm timeEntry)
		{
//        Session session=SessionFactoryUtil.getInstance().getCurrentSession();
//        try{
//        	session.beginTransaction();
//        	Query searchResult=null;
//        	if(timeEntry.getFrom()==null && timeEntry.getTo()==null && timeEntry.getProjectId()==null){
//        		searchResult=session.createQuery("from TimeEntries where userId="+UserHandler.getInstance().getUserByUserId(timeEntry.getUserId()));
//        		return searchResult.list();
//        	    }
//        	else{
//        		if(timeEntry.getFrom()==null && timeEntry.getTo()==null && timeEntry.getProjectId()!=null){
//        			searchResult=session.createQuery("from TimeEntries where userId="+UserHandler.getInstance().getUserByUserId(timeEntry.getUserId())+"  and projectId="+timeEntry.getProjectId());
//        			return searchResult.list(); 
//        		    }else{
//                    	 if(timeEntry.getFrom()!=null && timeEntry.getTo()==null && timeEntry.getProjectId()!=null){
//                    		 searchResult=session.createQuery("from TimeEntries where userId="+UserHandler.getInstance().getUserByUserId(timeEntry.getUserId())+"  and projectId="+timeEntry.getProjectId()+"and date between "+parseDateToLong(timeEntry.getFrom())+" and "+new Date().getTime());
//                    		 return searchResult.list();        
//                    	     }else{
//                    	            	 if(timeEntry.getFrom()!=null && timeEntry.getTo()==null && timeEntry.getProjectId()==null){
//                    	            		 searchResult=session.createQuery("from TimeEntries where userId="+UserHandler.getInstance().getUserByUserId(timeEntry.getUserId())+"and date between "+parseDateToLong(timeEntry.getFrom())+" and "+new Date().getTime());	 
//                    	            		 return searchResult.list();
//                    	            	 }if(timeEntry.getFrom()!=null && timeEntry.getTo()!=null && timeEntry.getProjectId()!=null){
//                    	            		 searchResult=session.createQuery("from TimeEntries where userId="+UserHandler.getInstance().getUserByUserId(timeEntry.getUserId())+"and date between "+parseDateToLong(timeEntry.getFrom())+" and "+parseDateToLong(timeEntry.getTo()));	 
//                    	            		 return searchResult.list();
//                    	             }
//                     }
//        	}
//        	}
//           session.getTransaction().commit();
//        
//            }catch(Exception e){
//        	e.printStackTrace();
//            }
	return null;
		}

	@Override
	public TimeEntries getTimeEntryObjectById(long id) {
		 Session session=SessionFactoryUtil.getInstance().getCurrentSession();
		 try{
			 session.beginTransaction();
			 List<TimeEntries> mapped=session.createQuery("from TimeEntries where releaseId="+id).list();
			 if(mapped.size()!=0){
		                  return mapped.get(0);
			 }
		   session.getTransaction().commit();
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return null;
	}
	@Override
	public boolean submitTimeEntries(TimeEntriesForm submitData){
         
		Session session=SessionFactoryUtil.getInstance().getCurrentSession();
		try{
			session.beginTransaction();
			Query query=session.createQuery("Update TimeEntries set status=:newStatus where id="+submitData.getId());
			query.setInteger("newStatus",1);
			int submitted=query.executeUpdate();
			session.getTransaction().commit();
			if(submitted==0){
				return false;
			}
			}catch(Exception e){
			e.printStackTrace();
		}
		
		return true;
	}
	
	
}
