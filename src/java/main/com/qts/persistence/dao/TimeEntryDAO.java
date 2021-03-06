package com.qts.persistence.dao;

/**
 * TimeEntryDAO Interface exposed to TimeEntry Handler to Perform Business Operation
 * @author Ajay
 */
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import com.qts.exception.ObjectNotFoundException;
import com.qts.model.TimeEntries;
import com.qts.model.TimeEntryBean;

public interface TimeEntryDAO {

	
    public boolean add(TimeEntryBean timeEntry);
    public boolean reject(TimeEntryBean timeEntry) throws ObjectNotFoundException;
    public boolean approve(TimeEntryBean timeEntry) throws ObjectNotFoundException;
    public boolean delete(TimeEntryBean deleteEntry) throws ObjectNotFoundException;

    public boolean update(TimeEntryBean updateWithData) throws ObjectNotFoundException, Exception;
	public Session deleteTimeEntryById(int id,Session session) throws ObjectNotFoundException;
	public TimeEntries getTimeEntryObjectById(long id) throws ObjectNotFoundException;
	public boolean isTimeEntryMappedToReleaseId(long id) throws ObjectNotFoundException;
	public boolean submit(TimeEntryBean submitData);

	public List<TimeEntryBean> getUserTimeEntries(TimeEntryBean timeEntry) ;
	public List<TimeEntryBean> getTimeEntriesForApprover(TimeEntryBean timeEntry);
	public int getUserWorkingHoursperDay(String date);
	public boolean deleteUserTimeEntriesByUserId(long id) throws ObjectNotFoundException;
}
