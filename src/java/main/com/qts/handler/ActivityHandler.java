package com.qts.handler;

import java.util.List;

import com.qts.model.Activities;
import com.qts.persistence.dao.ActivitiesDAOImpl;

public class ActivityHandler {

	private static ActivityHandler INSTANCE;
	public ActivityHandler() {
		
	}

	public static ActivityHandler getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ActivityHandler();
		return INSTANCE;
	}
	
	public Activities getObjectById(long id){
		return ActivitiesDAOImpl.getInstance().getObjectById(id);
	  }
	public List<Activities> getActivities(){
		return ActivitiesDAOImpl.getInstance().getActivities();
	}
	
}
