package com.qts.model;

import java.util.List;
/**
 * Pojo Class to get list of TimeEntriesForm Objects 
 * @author Ajay
 */
public class GetListOfTimeEntryBean {
   private List<TimeEntryBean> timeEntries;
   
      public GetListOfTimeEntryBean(){
    	  
         }
 

	public List<TimeEntryBean> getTimeEntriesform() {
		return timeEntries;
	}

	public void setTimeEntriesform(List<TimeEntryBean> timeEntryBean) {
		this.timeEntries = timeEntries;
	}
      
      
    	 
 }

