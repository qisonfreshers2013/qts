package com.qts.persistence.dao;

/**
 * @author vthandra
 */


import java.util.List;

import com.qts.exception.ObjectNotFoundException;
import com.qts.model.BaseObject;

/*
 * These are the methods we want to expose to business handlers for direct use. So they can call e.g. saveObject method
 * on the corresponding DAOs
 */
public interface BaseDAO {

 public BaseObject saveObject(BaseObject persistentObject);

 public BaseObject update(BaseObject persistentObject);

 public List<BaseObject> save(List<BaseObject> persistentObjects);

 public BaseObject getObjectById(long id) throws ObjectNotFoundException;
}