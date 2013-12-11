package com.qts.persistence.dao;

/**
 * TimeEntryDAO Interface exposed to TimeEntry Handler to Perform Business Operation
 * @author Ajay
 */
import java.util.List;

import org.hibernate.Session;

import com.qts.model.TimeEntries;
import com.qts.model.TimeEntriesForm;

public interface TimeEntryDAO {

	
    public boolean addTimeEntry(TimeEntriesForm timeEntry,Session session);
    public boolean rejectTimeEntry(TimeEntriesForm timeEntry);
    public boolean approveTimeEntry(TimeEntriesForm timeEntry);
    public boolean deleteTimeEntry(TimeEntriesForm deleteEntry);
    public boolean updateTimeEntry(TimeEntriesForm updateWithData);
    public List<TimeEntries> searchTimeEntriesForUser(TimeEntriesForm timeEntry);
	public long parseDateToLong(String date);
	public Session deleteTimeEntryById(int id,Session session);
	public String getDateInString(long timeinMilliSeconds);
	public List<TimeEntries> searchTimeEntriesForApprover(TimeEntriesForm timeEntry);
	public boolean getTimeEntryObjectById(long id);
	public boolean submitTimeEntries(TimeEntriesForm submitData);
}
