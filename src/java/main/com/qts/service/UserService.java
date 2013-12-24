package com.qts.service;
import java.util.List;

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
import com.qts.handler.RoleHandler;
import com.qts.handler.UserHandler;
import com.qts.handler.user.AuthenticationHandlerFactory;
import com.qts.model.ChangePasswordBean;
import com.qts.model.LoginBean;
import com.qts.model.Roles;
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
import com.qts.service.descriptors.OptionOutputDescriptor;


/**
 * @author AnilRam
 *
 */

@Path("/v1/user/")
public class UserService extends BaseService {		
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param request
	 * @return
	 * @throws UserException
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Public(input = Constants.LOGIN)
	@Path("/login")
	public String login(@Context HttpHeaders headers, @Context UriInfo uriInfo,
			WebserviceRequest request) throws UserException {
		
		LoginBean loginBean = (LoginBean) JsonUtil.getObject(
				request.getPayload(), LoginBean.class);
		
		User userInfo = UserHandler.getInstance().getLoginUser(loginBean);
		
		return JsonUtil.getJsonBasedOnDescriptor(userInfo, User.class);
	}

	

	/**
	 * @param headers
	 * @param uriInfo
	 * @param request
	 * @return
	 * @throws UserException
	 * @throws Exception
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/search")
	public String search(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws UserException, Exception {
		
		UserBean searchUserBean = (UserBean) JsonUtil.getObject(
				request.getPayload(), UserBean.class);
		
		SearchUserRecords userInfo = UserHandler.getInstance().searchUsers(
				searchUserBean);
		
		return JsonUtil.getJsonBasedOnDescriptor(userInfo,
				SearchUserRecords.class);
	}

	

	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param request
	 * @return String
	 * @throws ObjectNotFoundException
	 * @throws BusinessException
	 * @throws EncryptionException
	 */
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

	/**
	 * @param headers
	 * @param uriInfo
	 * @param request
	 * @return String
	 * @throws Exception
	 * this service work only for admin
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/add")
	public String add(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws UserException {
		
		UserBean user = (UserBean) JsonUtil.getObject(request.getPayload(),
				UserBean.class);
		
		long primaryUserId = UserHandler.getInstance().addUserAOP(user);
		
		return JsonUtil.getJsonBasedOnDescriptor(primaryUserId,Long.class);
	}


		
	/**
	 * @param headers
	 * @param uriInfo
	 * @param request
	 * @return String
	 * @throws UserException
	 * @throws Exception
	 * this service work only for admin
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/delete")	
	public String delete(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws UserException , Exception{
		
		UserBean bean = (UserBean) JsonUtil.getObject(request.getPayload(),
				UserBean.class);
		
		boolean isDeleted = UserHandler.getInstance().deleteUserAOP(bean);
		
		return JsonUtil.getJsonBasedOnDescriptor(isDeleted, Boolean.class);
	}

	// Update User
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param request
	 * @return String
	 * @throws UserException
	 * this service work only for admin
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateUserDetails")
	public String updateUser(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws UserException {
		
		UserBean userBean = (UserBean) JsonUtil.getObject(request.getPayload(),
				UserBean.class);
		
		User userInfo = UserHandler.getInstance().updateUserAOP(userBean);
		
		return JsonUtil.getJsonBasedOnDescriptor(userInfo, User.class);
	}
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param request
	 * @return String
	 * @throws UserException
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/changePassword")	
	public String changePassword(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws UserException {
		
		ChangePasswordBean userBean = (ChangePasswordBean) JsonUtil.getObject(
				request.getPayload(), ChangePasswordBean.class);
		
		boolean isChanged = UserHandler.getInstance().changePassword(userBean);
		
		return JsonUtil.getJsonBasedOnDescriptor(isChanged,Boolean.class);
	}

	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param request
	 * @return String
	 * @throws UserException
	 * 
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/forgotPassword")
	@UnSecure
	public String forgotPassword(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws UserException {
		
		UserBean bean = (UserBean) JsonUtil.getObject(request.getPayload(),
				UserBean.class);
		
		boolean isSend = UserHandler.getInstance().forgotPassword(bean);
		
		return JsonUtil.getJsonBasedOnDescriptor(isSend, Boolean.class);
	}
	
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param request
	 * @return String
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/logout")
	public String logout(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request) {
			boolean isLogout = UserHandler.getInstance().logout();		
		return JsonUtil.getJsonBasedOnDescriptor(isLogout,Boolean.class);
	}
	
	/**
	 * @param headers
	 * @param info
	 * @param request
	 * @return String
	 * @throws UserException
	 * this service work only for admin
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getUserDetails")	
	public String getUserDetails(@Context HttpHeaders headers,@Context UriInfo info,WebserviceRequest request) throws UserException{
		UserBean bean = (UserBean) JsonUtil.getObject(request.getPayload(),
				UserBean.class);
		User user = UserHandler.getInstance().getUserById(bean.getId());
		return JsonUtil.getJsonBasedOnDescriptor(user, User.class);		
	}
	
	/**
	 * @param headers
	 * @param info
	 * @param request
	 * @return String
	 * @throws UserException
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getLoginUserDetails")
	public String getLoginUserDetails(@Context HttpHeaders headers,@Context UriInfo info,WebserviceRequest request) throws UserException{

		User user = UserHandler.getInstance().getLoginUserDetails();
		
		return JsonUtil.getJsonBasedOnDescriptor(user, User.class);		
	}

	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param request
	 * @return String
	 * @throws UserException
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateLoginUserDetails")
	public String updateLoginUser(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws UserException {
		
		UserBean userBean = (UserBean) JsonUtil.getObject(request.getPayload(),
				UserBean.class);
		
		User userInfo = UserHandler.getInstance().updateLoginUser(userBean);
		
		return JsonUtil.getJsonBasedOnDescriptor(userInfo, User.class);
	}
	
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getEmployeeIds")
	public String getEmployeeIds(@Context HttpHeaders headers,
			@Context UriInfo info, WebserviceRequest request) throws Exception {
		List<String> listEmployeeIds = UserHandler.getInstance().getEmployeeIds();
		String employeeeIds = JsonUtil.getJsonForListBasedOnDescriptor(listEmployeeIds,
				User.class, OptionOutputDescriptor.class);
		return employeeeIds;
	}
}