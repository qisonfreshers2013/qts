package com.qts.persistence.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.qts.exception.ObjectNotFoundException;
import com.qts.model.BaseObject;

/**
 * @author vthandra
 */

public abstract class BaseDAOHibernateImpl implements BaseDAO {

 /**
  * Individual hibernate DAO Impls must implement this method to return right
  * type of object with the specified id
  */
	public Session session = null;
	public Transaction transaction=null;
 @Override
 public abstract BaseObject getObjectById(long id)
   throws ObjectNotFoundException;

 @Override
 public BaseObject saveObject(BaseObject persistentObject) {
	 session = SessionFactoryUtil.getInstance().getNewSession();
		session.beginTransaction();
  session.save(persistentObject);
  session.getTransaction().commit();
  return persistentObject;
 }

 @Override
 public BaseObject update(BaseObject persistentObject) {
	 session = SessionFactoryUtil.getInstance().getNewSession();
		session.beginTransaction();
  session.update(persistentObject);
  session.getTransaction().commit();
  return persistentObject;
 }

 @Override
 public List<BaseObject> save(List<BaseObject> objectList) {
	 session = SessionFactoryUtil.getInstance().getNewSession();
	session.beginTransaction();
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
   session.getTransaction().commit();
  }
  return objectList;
 }
}