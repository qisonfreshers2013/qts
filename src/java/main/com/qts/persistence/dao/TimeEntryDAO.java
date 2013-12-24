package com.qts.persistence.dao;

/**
 * TimeEntryDAO Interface exposed to TimeEntry Handler to Perform Business Operation
 * @author Ajay
 */
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import com.qts.model.TimeEntries;
import com.qts.model.TimeEntryBean;

public interface TimeEntryDAO {

	
    public boolean add(TimeEntryBean timeEntry) throws Exception;
    public boolean reject(TimeEntryBean timeEntry) throws Exception;
    public boolean approve(TimeEntryBean timeEntry) throws Exception;
    public boolean delete(TimeEntryBean deleteEntry) throws Exception;
    public boolean update(TimeEntryBean updateWithData) throws Exception;
	public Session deleteTimeEntryById(int id,Session session) throws Exception;
	public boolean getTimeEntryObjectById(long id) throws Exception;
	public boolean submit(TimeEntryBean submitData) throws Exception;
	public List<TimeEntries> getUserTimeEntries(TimeEntryBean timeEntry) throws Exception;
	public List<TimeEntries> getTimeEntriesForApprover(TimeEntryBean timeEntry) throws Exception;
	public int getUserWorkingHoursperDay(String date) throws Exception;
}
