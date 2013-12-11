package com.qts.persistence.dao;

import java.util.List;

import org.hibernate.Session;

import com.qts.common.Utils;
import com.qts.model.Activities;

<<<<<<< HEAD
public class ActivitiesDAOImpl extends BaseDAOImpl{
=======
public class ActivitiesDAOImpl extends BaseDAOHibernateImpl implements ActivitiesDAO{
>>>>>>> 1d587748b60786fcc68b6d96e67c4674b59bea17

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
<<<<<<< HEAD
		Session session=getSession();
=======
<<<<<<< HEAD
		Session session=SessionFactoryUtil.getInstance().getNewSession();
=======
<<<<<<< HEAD
		Session session=SessionFactoryUtil.getInstance().getCurrentSession();
=======

		Session session=SessionFactoryUtil.getInstance().getNewSession();

		Session session=SessionFactoryUtil.getInstance().getCurrentSession();

>>>>>>> aa6fb43f09ad3c0280514b8e976f1af9f568cf71
>>>>>>> 9ae18a307a9c45e084cfb89503b486037b6846d4
>>>>>>> 1d587748b60786fcc68b6d96e67c4674b59bea17
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
	
	
	
	
	
	
	
	
}
