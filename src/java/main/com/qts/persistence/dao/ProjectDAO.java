package com.qts.persistence.dao;

import com.qts.exception.ObjectNotFoundException;
import com.qts.model.BaseObject;

public interface ProjectDAO extends BaseDAO{
	//public List<BaseObject> getListObjects();
	public BaseObject getObjectById(long id) throws ObjectNotFoundException;

}
