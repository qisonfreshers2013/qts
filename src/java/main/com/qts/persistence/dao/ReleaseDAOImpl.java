package com.qts.persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.ReleaseException;
import com.qts.model.Release;
import com.qts.model.ReleaseBean;

/**
 * 
 * @author Shiva
 * 
 */
public class ReleaseDAOImpl extends BaseDAOImpl implements ReleaseDAO {

	private static ReleaseDAO RELEASEDAO_INSTANCE = null;

	private ReleaseDAOImpl() {

	}

	// getInstance method to give singleton object of ReleasesDAO
	public static ReleaseDAO getInstance() {
		if (RELEASEDAO_INSTANCE == null) {
			RELEASEDAO_INSTANCE = new ReleaseDAOImpl();
		}
		return RELEASEDAO_INSTANCE;
	}

	@SuppressWarnings("unchecked")
	@Override
	//lists Releases for the given project
	public List<Release> getReleases(ReleaseBean releaseBean)
			throws ReleaseException {

		List<Release> releasesList = null;
		try {
			Session session = getSession();
			Criteria releasesCriteria = session.createCriteria(Release.class);
			releasesCriteria.setProjection(Projections.projectionList()
					.add(Projections.property("id"))
					.add(Projections.property("name")));
			releasesCriteria.add(Restrictions.eq("projectId",
					releaseBean.getProjectId()));
			releasesCriteria.addOrder(Order.asc("id"));
			releasesList = releasesCriteria.list();
			if (releasesList.isEmpty()) {
				throw new ReleaseException(ExceptionCodes.RELEASES_EMPTY,
						ExceptionMessages.RELEASES_EMPTY_FOR_THE_PROJECT);
			}
		} catch (ReleaseException e) {
			throw e;
		}
		return releasesList;

	}

	@Override
	//method to add Releases for a project
	public Release addRelease(Release release) throws ReleaseException {

		try {
			Session session = getSession();
			session.save(release);
		} catch (ConstraintViolationException e) {
			throw new ReleaseException(ExceptionCodes.RELEASES_CANNOT_BE_ADDED,
					ExceptionMessages.RELEASES_CANNOT_BE_ADDED_FOR_THE_PROJECT);
		}
		return release;

	}

	@Override
	//Deletes the Release with the given Id
	public Release deleteRelease(Release release) {
		Session session = getSession();
		session.delete(release);
		return release;

	}

	@Override
	//Returns the Release Object when Id is given
	public Release getObjectById(long releaseId) throws ObjectNotFoundException {

		List<Release> releaseList;
		try {
			Session session = getSession();
			releaseList = session.createQuery(
					"from Release where id=" + releaseId).list();
			if (releaseList.isEmpty()) {
				throw new ObjectNotFoundException(
						ExceptionCodes.RELEASES_ID_DOES_NOT_EXISTS,
						ExceptionMessages.RELEASES_ID_INVALID);
			}
		} catch (ObjectNotFoundException e) {
			throw e;
		}
		return (Release) releaseList.get(0);

	}

}
