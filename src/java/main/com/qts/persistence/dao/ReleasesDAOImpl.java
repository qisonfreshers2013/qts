package com.qts.persistence.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.ExceptionMessages;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.ReleasesException;
import com.qts.model.Releases;
import com.qts.model.ReleasesInput;

/**
 * 
 * @author Shiva
 * 
 */
public class ReleasesDAOImpl extends BaseDAOHibernateImpl implements ReleasesDAO {
	
	private static ReleasesDAO RELEASES_INSTANCE = null;

	private ReleasesDAOImpl() {

	}
	
	//getInstance method to give singleton object of ReleasesDAO
	public static ReleasesDAO getInstance() {
		if (RELEASES_INSTANCE == null) {
			RELEASES_INSTANCE = new ReleasesDAOImpl();
		}
		return RELEASES_INSTANCE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Releases> listReleases(ReleasesInput releasesBean)throws ReleasesException,ObjectNotFoundException {
		
		
		Session session= SessionFactoryUtil.getInstance().getNewSession();
		Criteria releasesCriteria = session.createCriteria(Releases.class);
		releasesCriteria.setProjection(Projections.projectionList()
				.add(Projections.property("id"))
				.add(Projections.property("name")));
		releasesCriteria.add(Restrictions.eq("projectId",releasesBean.getProjectId()));
		List<Releases> releasesList = releasesCriteria.list();
		if (releasesList.isEmpty()) {
			throw new ReleasesException(ExceptionCodes.RELEASES_EMPTY,ExceptionMessages.RELEASES_EMPTY_FOR_THE_PROJECT);
		}
		return releasesList;

	}


	@Override
	public Releases addReleases(Releases releases) throws ReleasesException {
		
		try {
			Session session = SessionFactoryUtil.getInstance().getNewSession();
			Transaction txn=session.beginTransaction();
			session.save(releases);
			txn.commit();
			return releases;
		} catch (Exception e) {
			throw new ReleasesException(ExceptionCodes.RELEASES_CANNOT_BE_ADDED,ExceptionMessages.RELEASES_CANNOT_BE_ADDED_FOR_THE_PROJECT);
		}

	}

	@Override
	public Releases deleteReleases(Releases releases) throws Exception {
		
		
		try{
			Session session=SessionFactoryUtil.getInstance().getNewSession();
			Transaction txn=session.beginTransaction();
			session.delete(releases);
			txn.commit();
			return releases;
		}
		catch(Exception e){
			throw e;
		}

	}

	@SuppressWarnings("unchecked")
	public Releases getObjectById(long releaseId)throws ObjectNotFoundException {
		
		
		List<Releases> releasesList;
		try {
			Session session = SessionFactoryUtil.getInstance().getNewSession();
			session.beginTransaction();
			releasesList = session.createQuery("from Releases where id=" + releaseId).list();
			if (releasesList.isEmpty()) {
				throw new ObjectNotFoundException(ExceptionCodes.RELEASES_ID_DOES_NOT_EXISTS,ExceptionMessages.RELEASES_ID_INVALID);
			}
		} catch (ObjectNotFoundException e) {
			throw e;
		}

		return (Releases) releasesList.get(0);

	}

}