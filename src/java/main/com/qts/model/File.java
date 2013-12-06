package com.qts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

//import org.hibernate.annotations.Cascade;

@Entity
@Table(name="FILE")
public class File extends AbstractObject{
	@Id
	@GeneratedValue
	@Column(name="ID")
	private long id;
	@Column(name="NAME")
	private String name;
	@Column(name="PATH")
	private String path;
	@Column(name="SERVER_URL")
	private String serverURL;
//	@OneToOne(mappedBy="photoFile",cascade=CascadeType.ALL)
	public File() {
		
	}
	
	public File(String name, String path, String serverURL) {
		super();
		this.name = name;
		this.path = path;
		this.serverURL = serverURL;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getServerURL() {
		return serverURL;
	}

	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}

	@Override
	public int getObjectType() {
		
		return ObjectTypes.FILE;
	}
	

}
