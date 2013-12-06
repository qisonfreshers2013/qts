package com.qts.persistence.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;


public class DAOConnection {
	
	private static final  SessionFactory sessionFactory;
	static {
		try {
			sessionFactory = SessionUtil.getSessionFactory();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static Session openSession(){
		return  sessionFactory.openSession();
	}
	
	public static void closeSession(Session session){
		session.close();
	}



}
