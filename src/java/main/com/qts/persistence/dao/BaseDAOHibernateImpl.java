package com.qts.persistence.dao;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.qts.exception.ObjectNotFoundException;
import com.qts.model.BaseObject;
import com.qts.model.Roles;

public class BaseDAOHibernateImpl implements BaseDAO
{
	Session session;
	@Override
	public BaseObject saveObject(BaseObject persistentObject) {
		if(persistentObject!=null)
		{
			session=SessionFactoryUtil.getInstance().getCurrentSession();
			session.beginTransaction();
			session.save(persistentObject);
			session.getTransaction().commit();
		}
		return persistentObject;
	}

	@Override
	public BaseObject update(BaseObject persistentObject) {
//		if(persistentObject!=null)
//		{
//			session=Connection.openSession();
//			session.beginTransaction();
//			session.update(persistentObject);
//			session.getTransaction().commit();
//		}
//		return persistentObject;
		Session dbSession=null; 
        Transaction tx=SessionFactoryUtil.getCurrentTransaction();
        if (dbSession == null) { // since here we can come from upload servlet directly w/o authentication aspect
            // so db session can be null
        	 dbSession = SessionFactoryUtil.getInstance().getCurrentSession();
        }
        session.update(persistentObject);
            tx.commit();
        return persistentObject;
	}

	@Override
	public BaseObject updateWithOutModifiedDate(BaseObject persistentObject) {
		if(persistentObject!=null)
		{
			session=SessionFactoryUtil.getInstance().getCurrentSession();
			session.beginTransaction();
			session.update(persistentObject);
			session.getTransaction().commit();
		}
		return persistentObject;
	}

	@Override
	public List<BaseObject> save(List<BaseObject> persistentObjects) {
		for (Iterator<BaseObject> iterator = persistentObjects.iterator(); iterator.hasNext();) 
		{
			BaseObject baseObject = (BaseObject) iterator.next();
			session=SessionFactoryUtil.getCurrentSession();
			session.beginTransaction();
			session.save(baseObject);
			session.getTransaction().commit();
		}
		return persistentObjects;
	}

	@Override
	public BaseObject getObjectById(long id) throws ObjectNotFoundException {
		session=SessionFactoryUtil.getCurrentSession();
		return (BaseObject)session.get(Roles.class, id);
	}

	@Override
	public BaseObject updateWithoutModifiedTime(BaseObject persistentObject) {
		if(persistentObject!=null)
		{
			session=SessionFactoryUtil.getCurrentSession();
			session.beginTransaction();
			session.update(persistentObject);
			persistentObject.setMts(Calendar.getInstance().getTimeInMillis());
			session.getTransaction().commit();
		}
		return persistentObject;
	}
}
