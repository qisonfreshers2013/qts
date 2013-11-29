package com.qts.model;

/**
 * Abstract Object is the implementation for common attributes of all objects
 * in the system. And also all model objects in the system will inherit this class.
 * @author Vinay Thandra
 * 
 **/
public abstract class AbstractObject implements BaseObject{

    private long id;
    private long cts;
    private long mts;
    private long creatorId;
    private long modifierId;
    private short isDeleted;

    public static final String LABEL_ID = "id";
    public static final String LABEL_CREATED_TIME = "cts";
    public static final String LABEL_CREATOR_ID = "creatorId";
    public static final String LABEL_MODIFIED_TIME = "mts";
    public static final String LABEL_MODIFIER_ID = "modifierId";
    public static final String LABEL_IS_DELETED = "isDeleted";
    
    public AbstractObject() {
    	super();
    }
    
    public AbstractObject(AbstractObject abstractObject) {
    	this.id = abstractObject.id;
    	this.cts = abstractObject.cts;
    	this.creatorId = abstractObject.creatorId;
    	this.modifierId = abstractObject.modifierId;
    	this.mts = abstractObject.mts;
    }
    
    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getCts() {
        return cts;
    }

    @Override
    public void setCts(long cts) {
        this.cts = cts;
    }

    @Override
    public long getCreatorId() {
        return creatorId;
    }

    @Override
    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public long getMts() {
        return mts;
    }

    @Override
    public void setMts(long mts) {
        this.mts = mts;
    }

    @Override
    public long getModifierId() {
        return modifierId;
    }

    @Override
    public void setModifierId(long modifierId) {
        this.modifierId = modifierId;
    }

    public short getDeleted() {
        return isDeleted;
    }

    public void setDeleted(short deleted) {
        isDeleted = deleted;
    }
}