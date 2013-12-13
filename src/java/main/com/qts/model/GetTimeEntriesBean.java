package com.qts.model;

import java.util.List;
/**
 * Pojo Class to get list of TimeEntriesForm Objects 
 * @author Ajay
 */
public class GetTimeEntriesBean {
   private List<TimeEntryBean> timeEntryBean;
   
      public GetTimeEntriesBean(){
    	  
         }
 

	public List<TimeEntryBean> getTimeEntriesform() {
		return timeEntryBean;
	}

	public void setTimeEntriesform(List<TimeEntryBean> timeEntryBean) {
		this.timeEntryBean = timeEntryBean;
	}
      
      
    	 
 }

