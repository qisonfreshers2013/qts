package com.qts.persistence.dao;

import java.util.List;

import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.ReleaseException;
import com.qts.model.Release;
import com.qts.model.ReleaseBean;

/**
 * 
 * @author Shiva
 * 
 */
/*
 * 
 */
public interface ReleaseDAO extends BaseDAO {

	public List<Release> getReleases(ReleaseBean releasesBean)
			throws ReleaseException;

	public Release addRelease(Release releases) throws ReleaseException;

	public Release deleteRelease(Release releases);

}
