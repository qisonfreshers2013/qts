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

	
    public boolean add(TimeEntryBean timeEntry);
    public boolean reject(TimeEntryBean timeEntry);
    public boolean approve(TimeEntryBean timeEntry);
    public boolean delete(TimeEntryBean deleteEntry);
    public boolean update(TimeEntryBean updateWithData);
	public long parseDateToLong(String date);
	public Session deleteTimeEntryById(int id,Session session);
	public String getDateInString(long timeinMilliSeconds);
	public boolean getTimeEntryObjectById(long id);
	public boolean submit(TimeEntryBean submitData);
	public List<TimeEntries> listUserEntries(TimeEntryBean timeEntry);
	public List<TimeEntries> listEntriesToApprove(TimeEntryBean timeEntry);
	public int getUserWorkingHoursperDay(String date);
}
