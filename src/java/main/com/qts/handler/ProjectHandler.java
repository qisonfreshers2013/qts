package com.qts.handler;


	import com.qts.exception.ObjectNotFoundException;
import com.qts.model.BaseObject;
import com.qts.persistence.dao.DAOFactory;

	public class ProjectHandler extends AbstractHandler {
		private static ProjectHandler INSTANCE = null;

		private ProjectHandler() {
		}

		public static ProjectHandler getInstance() {
			if (INSTANCE == null) {
				INSTANCE = new ProjectHandler();
			}
			return INSTANCE;
		}
//		
//		public  List<BaseObject> getProjectList(){
//			return DAOFactory.getProjectDAOImplInstance().getListObjects();
//		}
		
		public  BaseObject saveObject(BaseObject project){
			
			return DAOFactory.getInstance().getProjectDAOImplInstance().saveObject(project);
			
		}
		
		public BaseObject getObjectById(long Id) throws ObjectNotFoundException{
			return DAOFactory.getInstance().getProjectDAOImplInstance().getObjectById(Id);
		}
		
		
		
//		public  List<BaseObject> listOfProjectUsers(String name){
//			 BaseObject project=DAOFactory.getProjectDAOImplInstance().getProjectByName(name);
//			 return null;
//			
//		}
		
//		public void allocateUserToProject(String name,List<String> userNames){
//			BaseObject project=DAOFactory.getProjectDAOImplInstance().getProjectByName(name);
//			Iterator<String> list=userNames.iterator();
//			List<User1> user1=new LinkedList();
//			while(list.hasNext()){
//				//user1.add(User1Handler.getInstance().getUserByName(list.next()));
//			}
//			//DAOFactory.getUserProjectDAOImplInstance().addUserProject(project,user1);
//			
//			
//		}
//		
		
		

	}



