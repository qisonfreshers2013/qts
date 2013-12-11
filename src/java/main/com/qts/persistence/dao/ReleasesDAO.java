package com.qts.persistence.dao;

import java.util.List;

import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.ReleasesException;
import com.qts.model.Releases;
import com.qts.model.ReleasesInput;

/**
 * 
 * @author Shiva
 *
 */
/*
 * 
 */
public interface ReleasesDAO extends BaseDAO {
	
	public List<Releases> listReleases(ReleasesInput releasesBean) throws ReleasesException,ObjectNotFoundException;

	public Releases deleteReleases(Releases releases)throws Exception;

	

}
