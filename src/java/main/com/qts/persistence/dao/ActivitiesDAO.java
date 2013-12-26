package com.qts.persistence.dao;

import java.util.List;

import com.qts.model.Activities;

public interface ActivitiesDAO {
	public Activities getObjectById(long id);
	public List<Activities> getActivities();
}
