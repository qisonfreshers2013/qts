package com.qts.persistence.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.qts.exception.ObjectNotFoundException;
import com.qts.model.BaseObject;

public class BaseDAOImpl implements BaseDAO{
	
	

	@Override
	public BaseObject saveObject(BaseObject persistentObject) {
		Session session = DAOConnection.openSession();
	    Transaction transaction = session.beginTransaction();
	    session.save(persistentObject);
	    transaction.commit();
	    DAOConnection.closeSession(session);
		return persistentObject;
	}

	@Override
	public BaseObject update(BaseObject persistentObject) {

		return null;
	}
	public BaseObject updateWithOutModifiedDate(BaseObject persistentObject) {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BaseObject> save(List<BaseObject> persistentObjects) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseObject getObjectById(long id) throws ObjectNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}
