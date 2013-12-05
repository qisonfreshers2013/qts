package com.qts.service;


//import java.io.StringWriter;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.qts.common.json.JsonUtil;
import com.qts.exception.UserException;
import com.qts.handler.UserHandler;
import com.qts.model.LoginBean;
import com.qts.model.SearchUserRecords;
import com.qts.model.User;
import com.qts.model.UserBean;
import com.qts.service.annotations.RestService;
import com.qts.service.annotations.ServiceStatus;
import com.qts.service.common.WebserviceRequest;
import com.qts.service.descriptors.OptionOutputDescriptor;



@Path("/UserService/")
public class UserService extends BaseService {
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")	
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/searchUser")
	public String searchUsers(@Context HttpHeaders headers, @Context UriInfo uriInfo,
			WebserviceRequest request) throws UserException {
		    UserBean searchUserBean = (UserBean)JsonUtil.getObject(request.getPayload(), UserBean.class);
	        SearchUserRecords userInfo = UserHandler.getInstance().searchUsers(searchUserBean);		    
	        return JsonUtil.getJsonForListBasedOnDescriptor(userInfo, SearchUserRecords.class,  OptionOutputDescriptor.class);
	
	}

	//{"payload":{"nickName":"anil","email":"anilram.laxmisetty@qison.com","employeeId":"68","designation":"APM"}}
	
	
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")	
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/login")
	public String login(@Context HttpHeaders headers,@Context UriInfo uriInfo,WebserviceRequest request) throws UserException{
		LoginBean loginBean =(LoginBean)JsonUtil.getObject(request.getPayload(),LoginBean.class);
		List<User> userInfo = UserHandler.getInstance().login(loginBean);
		return JsonUtil.getJsonForListBasedOnDescriptor(userInfo, User.class, OptionOutputDescriptor.class);//----
		}
	
	
	//{"payload":{"email":"ANILRAM.LAXMISETTY@QISON.COM","password":"ANIL@123"}}
	
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")	
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addUser")
	public String addUser(@Context HttpHeaders headers,@Context UriInfo uriInfo,
			          WebserviceRequest request) throws UserException{
		UserBean user = (UserBean)JsonUtil.getObject(request.getPayload(),UserBean.class);		
		UserHandler.getInstance().addUser(user);
		return "ok";
		}
	
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")	
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deleteUser")
	public boolean deleteUser(@Context HttpHeaders headers,@Context UriInfo uriInfo,
			WebserviceRequest request){
		boolean isDeleted = false;		
		return isDeleted;
	}	
}
