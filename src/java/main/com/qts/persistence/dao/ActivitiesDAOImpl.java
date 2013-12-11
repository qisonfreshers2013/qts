package com.qts.persistence.dao;

import java.util.List;

import org.hibernate.Session;

import com.qts.model.Activities;

public class ActivitiesDAOImpl {

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
		Session session=SessionFactoryUtil.getInstance().getNewSession();
		try{
	         session.beginTransaction();
			List<Activities> activityobj=session.createQuery("from Activities where id="+id).list();
			if(activityobj!=null && activityobj.size()!=0)
			{
				return activityobj.get(0);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{session.close();}
		
		return null;
	}
	
	
	
	
	
	
	
	
}
