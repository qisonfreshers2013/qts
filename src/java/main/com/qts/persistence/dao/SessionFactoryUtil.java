package com.qts.persistence.dao;

import org.hibernate.Criteria;
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
	if(session==null){
			session= new AnnotationConfiguration().configure().buildSessionFactory().openSession();
		return session;
		}
		return session;
	}
	public static Criteria query(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
