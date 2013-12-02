package com.qts.handler;

import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;

import com.qts.model.Roles;
import com.qts.persistence.dao.SessionUtil;
/**
 * 
 * @author Jagadish
 *
 */

public class RoleHandler extends AbstractHandler {

	public static RoleHandler INSTANCE=null;
	private RoleHandler() {
		
	}
	public static RoleHandler getInstance() {
		if(INSTANCE==null)
			INSTANCE=new RoleHandler();
		return INSTANCE;
	}
	@SuppressWarnings("unchecked")
	public String listRoles() {
		Session session=SessionUtil.getSessionFactory().openSession();
		Query query=session.createQuery("from Roles");
		Roles r;
		String s="";
		//System.out.println("ID\tRole");
		for(Iterator<Roles> iroles=query.list().listIterator();iroles.hasNext();)
		{
			r=iroles.next();
			s+=r.getId()+" "+r.getName()+"\n";
		}
		return s;
	}
}
