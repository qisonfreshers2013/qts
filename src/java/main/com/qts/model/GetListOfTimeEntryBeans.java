package com.qts.model;

import java.util.List;
/**
 * Pojo Class to get list of TimeEntriesForm Objects 
 * @author Ajay
 */
public class GetListOfTimeEntryBeans {
   private List<TimeEntryBean> timeEntries;
   
      public GetListOfTimeEntryBeans(){
    	  
         }

	public List<TimeEntryBean> getTimeEntries() {
		return timeEntries;
	}

	public void setTimeEntries(List<TimeEntryBean> timeEntries) {
		this.timeEntries = timeEntries;
	}
 
      
      
    	 
 }

