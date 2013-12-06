package com.qts.persistence.dao;


/**
 * DAO Factory. Handlers use this factory to get appropriate DAO.
 * 
 * @author Vinay Thandra
 * 
 */
public class DAOFactory {

 private static DAOFactory INSTANCE = null;

 private DAOFactory() {
 }
   
 public static DAOFactory getInstance() {
  if (INSTANCE == null) {
   INSTANCE = new DAOFactory();
  }
  return INSTANCE;
 }
 public ReleasesDAO getReleasesDAOImplInstance() {
  return ReleasesDAOImpl.getInstance();
 }
 public ProjectDAO getProjectDAOImplInstance(){
  return ProjectDAOImpl.getInstance();
 }

 // Implement all the factory methods, after this line.
 public UserDAO getUserDAO(){
  return UserDAOImpl.getInstance();
 }

}