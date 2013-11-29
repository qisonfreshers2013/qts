package com.qts.model;

/**
 * 
 * Defines all the common attributes across all model objects
 * @author Vinay Thandra
 *
 * */
public interface BaseObject {

	long getId();

	void setId(long id);
	
	/**
	 * CTS --> creation time stamp
	 * @return
	 */
	long getCts();

	void setCts(long cts);

	long getCreatorId();

	void setCreatorId(long creatorId);

	/**
	 * MTS --> last modified time stamp
	 * @return
	 */	
	long getMts();

	void setMts(long mts);

	long getModifierId();

	void setModifierId(long modifierId);

	/**
	 * Gets the type of object. The references for this is found in 
	 * Object types are defined in ObjectType interface
	 * @return
	 */
	int getObjectType();

}
