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
public class ReleasesDAOImpl extends BaseDAOImpl implements ReleasesDAO {
	
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
		
<<<<<<< HEAD
		
		Session session= getSession();
=======
		Session session= SessionFactoryUtil.getInstance().getNewSession();
>>>>>>> 1d587748b60786fcc68b6d96e67c4674b59bea17
		try{
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
<<<<<<< HEAD
		}finally{
			session.close();
		}

	}


	@Override
	public Releases addReleases(Releases releases) throws ReleasesException {
		
		try {
			Session session = getSession();
			session.save(releases);
			return releases;
		} catch (Exception e) {
			throw new ReleasesException(ExceptionCodes.RELEASES_CANNOT_BE_ADDED,ExceptionMessages.RELEASES_CANNOT_BE_ADDED_FOR_THE_PROJECT);
=======
>>>>>>> 1d587748b60786fcc68b6d96e67c4674b59bea17
		}
		finally{
			session.close();
		}
		

	}

	@Override
	public Releases deleteReleases(Releases releases) throws Exception {
		
<<<<<<< HEAD
		
		try{
			Session session=getSession();
=======
		Session session=SessionFactoryUtil.getInstance().getNewSession();
		try{		
			Transaction txn=session.beginTransaction();
>>>>>>> 1d587748b60786fcc68b6d96e67c4674b59bea17
			session.delete(releases);
			return releases;
		}
		catch(Exception e){
			throw e;
		}
		finally{
			session.close();
		}

	}

	@SuppressWarnings("unchecked")
	public Releases getObjectById(long releaseId)throws ObjectNotFoundException {
		
		Session session = SessionFactoryUtil.getInstance().getNewSession();
		List<Releases> releasesList;
<<<<<<< HEAD
		try {
			Session session = getSession();
=======
		try {		
			session.beginTransaction();
>>>>>>> 1d587748b60786fcc68b6d96e67c4674b59bea17
			releasesList = session.createQuery("from Releases where id=" + releaseId).list();
			if (releasesList.isEmpty()) {
				throw new ObjectNotFoundException(ExceptionCodes.RELEASES_ID_DOES_NOT_EXISTS,ExceptionMessages.RELEASES_ID_INVALID);
			}
			return (Releases) releasesList.get(0);
		} catch (ObjectNotFoundException e) {
			throw e;
		}
<<<<<<< HEAD
		return (Releases) releasesList.get(0);
=======
		finally{
			session.close();
		}

		
>>>>>>> 1d587748b60786fcc68b6d96e67c4674b59bea17

	}

}
