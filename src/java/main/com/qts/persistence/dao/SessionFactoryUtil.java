package com.qts.persistence.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;


@SuppressWarnings("deprecation")
public class SessionFactoryUtil {
	private static Session session;
	private static Transaction transaction;
	private static  SessionFactory sessionFactory; 
	public static SessionFactory getInstance() {
		try{
	  if(sessionFactory==null){
		  sessionFactory= new AnnotationConfiguration().configure().buildSessionFactory();
	  return sessionFactory;
	  }
	  return sessionFactory;
		}
		catch(Throwable ex){
			System.err.println("Initial SessionFactory creation failed." + ex);
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	 }
	public Session getCurrentSession() {
		  if(session==null){
		  session= sessionFactory.openSession();
		  return session;
		  }
		  return session;
		 }
	public Transaction getCurrentTransaction(){
		 if(transaction==null){
			 transaction= session.beginTransaction();
			  return transaction;
			  }
			  return transaction;
	}

}
