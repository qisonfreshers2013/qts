/*
package com.qts.persistence.dao;

import java.util.List;
import org.hibernate.Session;
import com.qts.exception.ObjectNotFoundException;
import com.qts.model.BaseObject;

*/
/**
 * @author vthandra
 *//*


public abstract class BaseDAOHibernateImpl implements BaseDAO {

 */
/**
  * Individual hibernate DAO Impls must implement this method to return right
  * type of object with the specified id
  *//*

 @Override
 public abstract BaseObject getObjectById(long id)
   throws ObjectNotFoundException;

 @Override
 public BaseObject saveObject(BaseObject persistentObject) {
  Session session = SessionFactoryUtil.getInstance().getNewSession();
  session.save(persistentObject);
  session.getTransaction().commit();
  return persistentObject;
 }

 @Override
 public BaseObject update(BaseObject persistentObject) {
  Session session = SessionFactoryUtil.getInstance().getNewSession();

  session.update(persistentObject);
  session.getTransaction().commit();
  return persistentObject;
 }

 @Override
 public List<BaseObject> save(List<BaseObject> objectList) {
  Session session = SessionFactoryUtil.getInstance().getNewSession();
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
}*/
