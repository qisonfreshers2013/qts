package com.qts.persistence.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;


@SuppressWarnings("deprecation")
public class SessionFactoryUtil {
	private static Session session=null;
	private static Transaction transaction=null;
	private static  SessionFactoryUtil sessionFactoryUtil=null; 
	public SessionFactoryUtil(){
		
	}
	public static SessionFactoryUtil getInstance() {
			if(sessionFactoryUtil==null){
				sessionFactoryUtil= new SessionFactoryUtil();
				return sessionFactoryUtil;
			}
			return sessionFactoryUtil;
	}
		
	public Session getNewSession() {
			session= new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		return session;
	}
	
	
}
