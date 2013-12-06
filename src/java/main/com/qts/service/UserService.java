package com.qts.service;


//import java.io.StringWriter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.qts.common.Constants;
import com.qts.common.json.JsonUtil;
import com.qts.exception.BusinessException;
import com.qts.exception.EncryptionException;
import com.qts.exception.ObjectNotFoundException;
import com.qts.exception.UserException;
import com.qts.handler.UserHandler;
import com.qts.handler.user.AuthenticationHandlerFactory;
import com.qts.model.LoginBean;
import com.qts.model.SearchUserRecords;
import com.qts.model.User;
import com.qts.model.UserBean;
import com.qts.model.user.AuthenticationInput;
import com.qts.model.user.AuthenticationOutput;
import com.qts.service.annotations.Public;
import com.qts.service.annotations.RestService;
import com.qts.service.annotations.ServiceStatus;
import com.qts.service.annotations.UnSecure;
import com.qts.service.common.WebserviceRequest;
import com.qts.service.descriptors.DeleteUserOutputDescriptor;
import com.qts.service.descriptors.OptionOutputDescriptor;



@Path("/userService/")
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
	@Public(input = Constants.LOGIN)
	@Path("/login")
	public String login(@Context HttpHeaders headers,@Context UriInfo uriInfo,WebserviceRequest request) throws UserException{
		LoginBean loginBean =(LoginBean)JsonUtil.getObject(request.getPayload(),LoginBean.class);
		User userInfo = UserHandler.getInstance().login(loginBean);
		return JsonUtil.getJsonForListBasedOnDescriptor(userInfo, User.class, OptionOutputDescriptor.class);//----
		}
	
	//{"payload":{"email":"ANILRAM.LAXMISETTY@QISON.COM","password":"ANIL@123"}}
	
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Public(input = Constants.LOGIN)
	@Path("/authenticate")
	public String authenticate(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws ObjectNotFoundException, BusinessException,
			EncryptionException {

		AuthenticationInput input = (AuthenticationInput) JsonUtil.getObject(
				request.getPayload(), AuthenticationInput.class);

		AuthenticationOutput output = AuthenticationHandlerFactory
				.getAuthenticationHandler(input.getAuthType()).authenticate(
						input);
		return JsonUtil.getJsonBasedOnDescriptor(output,
				AuthenticationOutput.class);
	}
	
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")	
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addUser")
//	@UnSecure
	public String addUser(@Context HttpHeaders headers,@Context UriInfo uriInfo,
			          WebserviceRequest request) throws UserException{
		UserBean user = (UserBean)JsonUtil.getObject(request.getPayload(),UserBean.class);		
		long primaryUserId = UserHandler.getInstance().addUser(user);
		return JsonUtil.getJsonForListBasedOnDescriptor(primaryUserId,User.class,OptionOutputDescriptor.class);
		}
	
	//{"payload":{"firstName":"Ramesh","lastName":"Pasupulati","nickName":"rammi","gender":"Male","email":"pasu@gmail.com","employeeId":"1234","designation":"APM","location":"hyd","userId":"pasu@gmail.com","password":"pas@123","confirmPassword":"pas@123"}}
	
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")	
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deleteUser")
	@UnSecure
	public String deleteUser(@Context HttpHeaders headers,@Context UriInfo uriInfo,
			WebserviceRequest request){
		UserBean bean = (UserBean)JsonUtil.getObject(request.getPayload(),UserBean.class);
		boolean isDeleted = UserHandler.getInstance().deleteUser(bean);
		return JsonUtil.getJsonForListBasedOnDescriptor(isDeleted, User.class, DeleteUserOutputDescriptor.class);		
	}
	
	
	//Update User
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")	
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateUser")
//	@UnSecure
	public String updateUser(@Context HttpHeaders headers,@Context UriInfo uriInfo,WebserviceRequest request) throws UserException{
		UserBean userBean = (UserBean)JsonUtil.getObject(request.getPayload(),UserBean.class);	
		boolean isUpdated = UserHandler.getInstance().updateUser(userBean);
		return JsonUtil.getJsonForListBasedOnDescriptor(isUpdated, User.class,OptionOutputDescriptor.class);
	}
	
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")	
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/changePassword")
	@UnSecure
	public String changePassword(@Context HttpHeaders headers,@Context UriInfo uriInfo,WebserviceRequest request) throws UserException{
		UserBean userBean = (UserBean)JsonUtil.getObject(request.getPayload(),UserBean.class);	
		boolean isChanged = UserHandler.getInstance().changePassword(userBean);
		return JsonUtil.getJsonForListBasedOnDescriptor(isChanged, User.class,OptionOutputDescriptor.class);
	}
	
	
	
	
}
