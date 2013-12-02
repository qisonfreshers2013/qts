package com.qts.persistence.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class SessionUtil {
	private static SessionFactory session; //"com/qison/hibernate/hibernate.cfg.xml"
	static
	{
		try
		{
			session=new AnnotationConfiguration().configure("com/qts/persistence/dao/hibernate.cfg.xml").buildSessionFactory();
		}
		catch (Throwable e) 
		{
			System.out.println(e);
		}
	}
	public static SessionFactory getSessionFactory() {
		return session;
	}

}
