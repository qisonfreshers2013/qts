package com.qts.handler;

import java.util.List;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.ReleasesException;
import com.qts.model.Releases;
import com.qts.model.ReleasesInput;
import com.qts.persistence.dao.DAOFactory;

/**
 * 
 * @author Shiva
 * 
 */
/*
 * Handler Class for Releases Services Handler(Singleton Class)
 */

public class ReleasesHandler extends AbstractHandler {
	private static ReleasesHandler INSTANCE = null;

	private ReleasesHandler() {
	}

	//Method to give Singleton Object Of ReleasesHandler
	public static ReleasesHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ReleasesHandler();
		}
		return INSTANCE;
	}


	// Request Comes from ReleasesService Class and calls Method
	// ReleasesDAOImplClass
	public List<Releases> listReleases(ReleasesInput releasesInput)throws ReleasesException, ObjectNotFoundException {

<<<<<<< HEAD
<<<<<<< HEAD
	public List<Releases> listReleases(ReleasesInput releasesInput)
			throws Exception {
		if (validateListReleases(releasesInput)) {
			return DAOFactory.getInstance().getReleasesDAOImplInstance().listReleases(
					releasesInput);
=======
		if (validateProjectId(releasesInput)) {
			return DAOFactory.getInstance().getReleasesDAOImplInstance().listReleases(releasesInput);
>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157
=======
		try {
			if (validateProjectId(releasesInput)) {
				return DAOFactory.getInstance().getReleasesDAOImplInstance().listReleases(releasesInput);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
>>>>>>> aa6fb43f09ad3c0280514b8e976f1af9f568cf71
		}
		return null;

<<<<<<< HEAD
	// validations before listing Releases of a project
	private boolean validateListReleases(ReleasesInput releasesInput)
			throws Exception {
		if (releasesInput == null)
			throw new ReleasesException(ExceptionCodes.RELEASESBEAN_NOT_NULL,
					ExceptionMessages.RELEASESBEAN_NOT_NULL);
		try {
			ProjectHandler.getInstance().getObjectById(
					releasesInput.getProjectId());
		} catch (ObjectNotFoundException e) {
			throw e;
		}
		return true;
=======
>>>>>>> 7719fa5f39d1939b4b46fc46756ffa075dd00157
	}

	// Request Comes from ReleasesService Class and calls Method of
	// ReleasesDAOImplClass
	// Creating Releases Object from ReleasesBean Object
	public Releases addReleases(ReleasesInput releasesInput)throws ReleasesException, ObjectNotFoundException {

		if (validateReleaseName(releasesInput)) {
			Releases releases = new Releases();
			releases.setName(releasesInput.getReleaseName());
			releases.setProjectId(releasesInput.getProjectId());
			return DAOFactory.getInstance().getReleasesDAOImplInstance().addReleases(releases);
		}
		return null;

	}

	public Releases deleteReleases(ReleasesInput releasesInput) throws Exception {

		if(validateReleaseId(releasesInput)){
			Releases releases=ReleasesHandler.getInstance().getObjectById(releasesInput.getId());
			return DAOFactory.getInstance().getReleasesDAOImplInstance().deleteReleases(releases);
		}
		return null;

	}

	//gives Releases Object when ID is given
	public Releases getObjectById(long id) throws ObjectNotFoundException{

		return (Releases) DAOFactory.getInstance().getReleasesDAOImplInstance().getObjectById(id);

	}


	// validations before listing Releases of a project
	private boolean validateProjectId(ReleasesInput releasesInput)throws ReleasesException, ObjectNotFoundException {

		if (releasesInput == null)
			throw new ReleasesException(ExceptionCodes.RELEASESBEAN_NOT_NULL,ExceptionMessages.RELEASESBEAN_NOT_NULL);
//		if(releasesInput.getProjectId()==0)
//			throw new ReleasesException(ExceptionCodes.PROJECT_ID_NOT_NULL,ExceptionMessages.PROJECT_ID_NOT_NULL);
		try {
			ProjectHandler.getInstance().getObjectById(releasesInput.getProjectId());
		} catch (ObjectNotFoundException e) {
			throw e;
		}
		return true;

	}

	// Validations Before adding Releases to a project
	private boolean validateReleaseName(ReleasesInput releasesInput)throws ReleasesException, ObjectNotFoundException {

		if (releasesInput.getReleaseName() == null || releasesInput.getReleaseName().equals(""))
			throw new ReleasesException(ExceptionCodes.RELEASES_NAME_NULL,ExceptionMessages.RELEASES_NAME_CANNOT_BE_EMPTY);
		if (!(releasesInput.getReleaseName().matches("^[\\w]+[\\.\\-_\\w]*([ {1}][\\.\\-_\\w]+)*$")))
			throw new ReleasesException(ExceptionCodes.RELEASES_NAME_CONTAINS_NONALPHANUMERIC,ExceptionMessages.RELEASES_NAME_CANNOT_CONTAIN_SPECIALCHARS);
		if (releasesInput.getReleaseName().length() > 128)
			throw new ReleasesException(ExceptionCodes.RELEASES_NAME_LENGTH,ExceptionMessages.RELEASES_NAME_LENGTH_IS_MORE);
		if(validateProjectId(releasesInput))
			return true;
		return false;

	}

	//Validations Before Deleting Releases
	private boolean validateReleaseId(ReleasesInput releasesInput) throws Exception {

		if (releasesInput == null)
			throw new ReleasesException(ExceptionCodes.RELEASESBEAN_NOT_NULL,ExceptionMessages.RELEASESBEAN_NOT_NULL);
		if(releasesInput.getId()==0)
			throw new ReleasesException(ExceptionCodes.RELEASE_ID_NULL, ExceptionMessages.RELEASE_ID_NULL);
		if(TimeEntryHandler.getInstance().isEntryMapped(releasesInput.getId()))
			throw new ReleasesException(ExceptionCodes.TIME_ENTRY_PRESENT,ExceptionMessages.TIME_ENTRY_PRESENT);
		return true;

	}

	public Releases deleteReleases(ReleasesInput releasesInput) throws Exception {
		if(validateDeleteReleases(releasesInput)){
			Releases releases=ReleasesHandler.getInstance().getObjectById(releasesInput.getId());
		return DAOFactory.getInstance().getReleasesDAOImplInstance()
				.deleteReleases(releases);
		}
		return null;
	}

	private boolean validateDeleteReleases(ReleasesInput releasesInput) throws Exception {
		if (releasesInput == null)
			throw new ReleasesException(ExceptionCodes.RELEASESBEAN_NOT_NULL,
					ExceptionMessages.RELEASESBEAN_NOT_NULL);
		if(TimeEntryHandler.getInstance().isEntryMapped(releasesInput.getId()))
			throw new ReleasesException(ExceptionCodes.TIME_ENTRY_PRESENT,ExceptionMessages.TIME_ENTRY_PRESENT);
		return true;
	}
	public Releases getObjectById(long id) throws ObjectNotFoundException{
		return (Releases) DAOFactory.getInstance().getReleasesDAOImplInstance()
		.getObjectById(id);
	}

}
