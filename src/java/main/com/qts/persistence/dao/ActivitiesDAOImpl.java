package com.qts.persistence.dao;

import java.util.List;

import org.hibernate.Session;

import com.qts.common.Utils;
import com.qts.model.Activities;


public class ActivitiesDAOImpl extends BaseDAOImpl implements ActivitiesDAO{


	private static ActivitiesDAOImpl INSTANCE;
	public ActivitiesDAOImpl() {
		// TODO Auto-generated constructor stub
	}

	public static ActivitiesDAOImpl getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ActivitiesDAOImpl();
		return INSTANCE;
	}
	
	  @SuppressWarnings("unchecked")
	public Activities getObjectById(long id)
	{

		Session session=getSession();
		try{
			List<Activities> activityobj=session.createQuery("from Activities where id="+id).list();
			if(activityobj!=null && activityobj.size()!=0)
			{
				return activityobj.get(0);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
@SuppressWarnings("unchecked")
public List<Activities> getActivities(){
	Session session=getSession();
	try{
		List<Activities> activitieslist=session.createQuery("from Activities").list();
		return activitieslist;
	}catch(Exception e){
		e.printStackTrace();
		throw e;
	}
}
	
	
	
	
	
	
}
