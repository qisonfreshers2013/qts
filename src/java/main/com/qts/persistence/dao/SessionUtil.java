package com.qts.persistence.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

@SuppressWarnings("deprecation")
public class SessionUtil {
	private static final SessionFactory session; //"com/qison/hibernate/hibernate.cfg.xml"
	static
	{
		try
		{
			session=new AnnotationConfiguration().configure().buildSessionFactory();
		}
		catch (Throwable e) 
		{
			System.err.println("Initial SessionFactory creation Failed"+e);
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
	}
	public static SessionFactory getSessionFactory() {
		return session;
	}

}
