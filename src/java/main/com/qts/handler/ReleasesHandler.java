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

	public static ReleasesHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ReleasesHandler();
		}
		return INSTANCE;
	}

	// Request Comes from ReleasesService Class and calls Method
	// ReleasesDAOImplClass

	public List<Releases> listReleases(ReleasesInput releasesBean)
			throws ReleasesException, ObjectNotFoundException {
		if (validateListReleases(releasesBean)) {
			return DAOFactory.getInstance().getReleasesDAOImplInstance().listReleases(
					releasesBean);
		}
		return null;
	}

	// validations before listing Releases of a project
	private boolean validateListReleases(ReleasesInput releasesBean)
			throws ReleasesException, ObjectNotFoundException {
		if (releasesBean == null)
			throw new ReleasesException(ExceptionCodes.RELEASESBEAN_NOT_NULL,
					ExceptionMessages.RELEASESBEAN_NOT_NULL);
		try {
			ProjectHandler.getInstance().getObjectById(
					releasesBean.getProjectId());
		} catch (ObjectNotFoundException e) {
			throw e;
		}
		return true;
	}

	// Request Comes from ReleasesService Class and calls Method of
	// ReleasesDAOImplClass
	// Creating Releases Object from ReleasesBean Object
	public Releases addReleases(ReleasesInput releasesBean)
			throws ReleasesException {
		if (validateReleaseName(releasesBean)) {
			Releases releases = new Releases();
			releases.setName(releasesBean.getReleaseName());
			releases.setProjectId(releasesBean.getProjectId());

			return DAOFactory.getInstance().getReleasesDAOImplInstance()
					.addReleases(releases);
		}
		return null;
	}

	// Validations Before adding Releases to a project
	private boolean validateReleaseName(ReleasesInput r1)
			throws ReleasesException {
		if (r1.getReleaseName() == null || r1.getReleaseName().equals(""))
			throw new ReleasesException(ExceptionCodes.RELEASES_NAME_NULL,
					ExceptionMessages.RELEASES_NAME_CANNOT_BE_EMPTY);
		if (!r1.getReleaseName().matches(
				"^[\\w]+[\\.\\-_\\w]*([ {1}][\\.\\-_\\w]+)*$"))
			throw new ReleasesException(
					ExceptionCodes.RELEASES_NAME_CONTAINS_NONALPHANUMERIC,
					ExceptionMessages.RELEASES_NAME_CANNOT_CONTAIN_SPECIALCHARS);
		else if (r1.getReleaseName().length() > 128)
			throw new ReleasesException(ExceptionCodes.RELEASES_NAME_LENGTH,
					ExceptionMessages.RELEASES_NAME_LENGTH_IS_MORE);
		return true;
	}

}
