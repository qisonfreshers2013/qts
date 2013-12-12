package com.qts.persistence.dao;

/*import org.hibernate.Query;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.Transaction;
 import org.hibernate.cfg.AnnotationConfiguration;


 @SuppressWarnings("deprecation")
 public class SessionFactoryUtil {
 private static Session session=null;
 private static Transaction transaction=null;
 private static  SessionFactoryUtil sessionFactoryUtil=null; 

 private SessionFactoryUtil(){

 }
 public static SessionFactoryUtil getInstance() {
 if(sessionFactoryUtil==null){
 sessionFactoryUtil= new SessionFactoryUtil();
 return sessionFactoryUtil;
 }
 return sessionFactoryUtil;
 }

 public synchronized Session getNewSession() {
 if(null==session)	

 session= new AnnotationConfiguration().configure().buildSessionFactory().openSession();
 return session;
 }


 }*/

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import com.qts.exception.ExceptionCodes;
import com.qts.exception.SystemException;

/**
*
*/
public class SessionFactoryUtil {

	/** The single instance of hibernate SessionFactory */
	private static org.hibernate.SessionFactory sessionFactory;
	private static SessionFactoryUtil INSTANCE = null;

	private SessionFactoryUtil() {
	}

	static {
		sessionFactory = new AnnotationConfiguration().configure()
				.buildSessionFactory();
	}

	public static SessionFactoryUtil getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SessionFactoryUtil();
		}
		return INSTANCE;
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Opens a session and will not bind it to a session context
	 * 
	 * @return the session
	 */
	public Session openSession() {
		Session session = sessionFactory.openSession();

		if (session.isConnected() == false) {
			throw new SystemException(ExceptionCodes.DB_EXCEPTION);
		}

		return session;
	}

	/**
	 * Returns a session from the session context. If there is no session in the
	 * context it opens a session, stores it in the context and returns it. This
	 * factory is intended to be used with a hibernate.cfg.xml including the
	 * following property <property
	 * name="current_session_context_class">thread</property> This would return
	 * the current open session or if this does not exist, will create a new
	 * session
	 * 
	 * @return the session
	 */
	public Session getCurrentSession() {
		Session session = sessionFactory.getCurrentSession();

		return session;
	}

	public Transaction beginTransaction(Session session) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			if (session.isConnected() == false) {
				throw new SystemException(ExceptionCodes.DB_EXCEPTION);
			}

		} catch (Exception e) {
			throw new SystemException(ExceptionCodes.DB_EXCEPTION);
		}

		return tx;
	}

	/**
	 * closes the session factory
	 */
	public static void close() {
		if (sessionFactory != null)
			sessionFactory.close();
		sessionFactory = null;

	}

}
