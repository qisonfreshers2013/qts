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
 

	public List<TimeEntryBean> getListOfTimeEntryBeans() {
		return timeEntries;
	}

	public void setListOfTimeEntryBeans(List<TimeEntryBean> timeEntryBean) {
		this.timeEntries = timeEntryBean;
	}
      
      
    	 
 }

