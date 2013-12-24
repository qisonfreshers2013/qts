package com.qts.handler;

import java.util.List;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.ReleaseException;
import com.qts.handler.annotations.AuthorizeEntity;
import com.qts.model.Release;
import com.qts.model.ReleaseBean;
import com.qts.model.Roles;
import com.qts.persistence.dao.DAOFactory;

/**
 * 
 * @author Shiva
 * 
 */
/*
 * Handler Class for Releases Services Handler(Singleton Class)
 */

public class ReleaseHandler extends AbstractHandler {
	private static ReleaseHandler INSTANCE = null;

	private ReleaseHandler() {
	}

	// Method to give Singleton Object Of ReleasesHandler
	public static ReleaseHandler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ReleaseHandler();
		}
		return INSTANCE;
	}

	// Request Comes from ReleasesService Class and calls Method
	// ReleasesDAOImplClass
	public List<Release> getReleases(ReleaseBean releaseBean)
			throws ReleaseException, ObjectNotFoundException {

		List<Release> releaseList = null;

		try {

			// validating project Id
			if (releaseBean == null)
				throw new ReleaseException(
						ExceptionCodes.RELEASESBEAN_NOT_NULL,
						ExceptionMessages.RELEASESBEAN_NOT_NULL);
			else if (releaseBean.getProjectId() == 0)
				throw new ReleaseException(ExceptionCodes.PROJECT_ID_NOT_NULL,
						ExceptionMessages.PROJECT_ID_NOT_NULL);
			else
				try {
					ProjectHandler.getInstance().getObjectById(
							releaseBean.getProjectId());
				} catch (ObjectNotFoundException e) {
					throw e;
				}

			// Listing Releases of the Project
			releaseList = DAOFactory.getInstance().getReleaseDAOImplInstance()
					.getReleases(releaseBean);

		} catch (ReleaseException | ObjectNotFoundException e) {
			throw e;
		}
		return releaseList;

	}

	// Request Comes from ReleasesService Class and calls Method of
	// ReleasesDAOImplClass
	// Creating Releases Object from ReleasesBean Object
	@AuthorizeEntity(roles = {Roles.ROLE_ADMIN}, entity = "Release.java")
	public Release addReleaseAOP(ReleaseBean releaseBean) throws ReleaseException,
			ObjectNotFoundException {

		Release release = null;

		try {

			// validating Input
			if (releaseBean == null)
				throw new ReleaseException(
						ExceptionCodes.RELEASESBEAN_NOT_NULL,
						ExceptionMessages.RELEASESBEAN_NOT_NULL);

			// validating Project Id
			else if (releaseBean.getProjectId() == 0)
				throw new ReleaseException(ExceptionCodes.PROJECT_ID_NOT_NULL,
						ExceptionMessages.PROJECT_ID_NOT_NULL);
			else if (releaseBean.getProjectId() != 0) {
				try {
					ProjectHandler.getInstance().getObjectById(
							releaseBean.getProjectId());
				} catch (ObjectNotFoundException e) {
					throw e;
				}
			}

			// validating Release Name
			if (releaseBean.getReleaseName() == null
					|| releaseBean.getReleaseName().equals(""))
				throw new ReleaseException(ExceptionCodes.RELEASES_NAME_NULL,
						ExceptionMessages.RELEASES_NAME_CANNOT_BE_EMPTY);
			else if (!(releaseBean.getReleaseName()
					.matches("^[\\w]+[\\.\\-_\\w]*([ {1}][\\.\\-_\\w]+)*$")))
				throw new ReleaseException(
						ExceptionCodes.RELEASES_NAME_CONTAINS_NONALPHANUMERIC,
						ExceptionMessages.RELEASES_NAME_CANNOT_CONTAIN_SPECIALCHARS);
			else if (releaseBean.getReleaseName().length() > 128)
				throw new ReleaseException(ExceptionCodes.RELEASES_NAME_LENGTH,
						ExceptionMessages.RELEASES_NAME_LENGTH_IS_MORE);

			// Creating and Saving Release Object
			release = new Release();
			release.setName(releaseBean.getReleaseName());
			release.setProjectId(releaseBean.getProjectId());
			release = (Release) DAOFactory.getInstance()
					.getReleaseDAOImplInstance().addRelease(release);

		} catch (ReleaseException | ObjectNotFoundException e) {
			throw e;
		}
		return release;

	}

	// delete Releases with the given id
	@AuthorizeEntity(roles = {Roles.ROLE_ADMIN}, entity = "Release.java")
	public Release deleteReleaseAOP(ReleaseBean releaseBean)
			throws ReleaseException, ObjectNotFoundException {

		Release release = null;

		try {

			// validating Input
			if (releaseBean == null)
				throw new ReleaseException(
						ExceptionCodes.RELEASESBEAN_NOT_NULL,
						ExceptionMessages.RELEASESBEAN_NOT_NULL);

			// validating Release Id
			else if (releaseBean.getReleaseId() == 0)
				throw new ReleaseException(ExceptionCodes.RELEASE_ID_NULL,
						ExceptionMessages.RELEASE_ID_NULL);
			else {
				try {
					if (TimeEntryHandler.getInstance().isEntryMapped(
							releaseBean.getReleaseId()))
						throw new ReleaseException(
								ExceptionCodes.TIME_ENTRY_PRESENT,
								ExceptionMessages.TIME_ENTRY_PRESENT);
				} catch (ObjectNotFoundException e) {
					release = new Release();
					try {
						release = ReleaseHandler.getInstance().getObjectById(
								releaseBean.getReleaseId());
					} catch (ObjectNotFoundException oe) {
						throw oe;
					}

					// Deleting Release Object
					release = DAOFactory.getInstance()
							.getReleaseDAOImplInstance().deleteRelease(release);
				}
			}
		} catch (ReleaseException | ObjectNotFoundException e) {
			throw e;
		}

		return release;

	}

	// gives Releases Object when ID is given
	public Release getObjectById(long id) throws ObjectNotFoundException {

		Release release = null;

		try {
			release = (Release) DAOFactory.getInstance()
					.getReleaseDAOImplInstance().getObjectById(id);
		} catch (ObjectNotFoundException e) {
			throw e;
		}

		return release;

	}

}
