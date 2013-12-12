package com.qts.persistence.dao;
import static com.qts.common.Utils.getDBSession;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.qts.exception.ObjectNotFoundException;
import com.qts.model.BaseObject;

public class BaseDAOImpl implements BaseDAO{
	
	public Session getSession(){
		return getDBSession();
	}

	@Override
	public BaseObject saveObject(BaseObject persistentObject) {
		Session session = getSession();
	    session.save(persistentObject);
		return persistentObject;
	}
	@Override
	 public  BaseObject getObjectById(long id)
	   throws ObjectNotFoundException{
		return null;
	}

//	 @Override
//	 public BaseObject saveObject(BaseObject persistentObject) {
//		 
//		 
//		 
//	  session = getSession();
//	  session.save(persistentObject);
//	  return persistentObject;
//	 }

	 @Override
	 public BaseObject update(BaseObject persistentObject) {
		 Session session = getSession();
			//session.beginTransaction();
	  session.update(persistentObject);
	  //session.getTransaction().commit();
	  return persistentObject;
	 }

	 @Override
	 public List<BaseObject> save(List<BaseObject> objectList) {
		Session  session = getSession();
	  if (null != objectList && objectList.size() > 0) {
	   short count = 0;
	   for (BaseObject object : objectList) {
	    session.save(object);
	    count++;
	    if (count == 1000) {// batch update for each 30 records
	     session.flush();
	     session.clear();
	     count = 0;
	    }
	   }
	  }
	  return objectList;
	 }
}
