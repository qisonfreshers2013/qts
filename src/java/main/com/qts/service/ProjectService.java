package com.qts.service;
/*
 * Author Mani kumar
 * WebServices related to Project 
 * like add Project, list Project 
 * 
 */

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
import com.qts.handler.ProjectHandler;
import com.qts.model.BaseObject;
import com.qts.model.Project;
import com.qts.model.ProjectBean;
import com.qts.model.User;
import com.qts.service.annotations.RestService;
import com.qts.service.annotations.ServiceStatus;
import com.qts.service.annotations.UnSecure;
import com.qts.service.common.WebserviceRequest;
import com.qts.service.descriptors.ProjectBeanDescriptor;
import com.qts.service.descriptors.ProjectOutputDescriptor;
import com.qts.service.descriptors.UserListOutputDescriptor;

@Path("/v1/project")
public class ProjectService {

	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/listProjects")
	public String listOfProjects(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
	throws Exception {
		ProjectBean projectBean = (ProjectBean) JsonUtil.getObject(request.getPayload(),
				ProjectBean.class);

		List<Project> projectList = ProjectHandler.getInstance()
		.getProjectList(projectBean);

		String jsonForListBasedOnDescriptor = JsonUtil
		.getJsonForListBasedOnDescriptor(projectList, Project.class,
				ProjectOutputDescriptor.class);

		return jsonForListBasedOnDescriptor;
	}

	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addProject")
	public String addProject(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
	throws Exception {
		Project project = (Project) JsonUtil.getObject(request.getPayload(),
				Project.class);
		BaseObject baseObject = ProjectHandler.getInstance().addProject(project);
		String jsonForListBasedOnDescriptor = JsonUtil
		.getJsonBasedOnDescriptor( baseObject,
				ProjectOutputDescriptor.class);
		return jsonForListBasedOnDescriptor;

	}

	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/listProjectUsers")
	public String listProjectUsers(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
	throws Exception {
		Project project = (Project) JsonUtil.getObject(request.getPayload(),
				Project.class);
		List<User> userList = ProjectHandler.getInstance()
		.listOfProjectUsers(project);
		String jsonForListBasedOnDescriptor = JsonUtil
		.getJsonForListBasedOnDescriptor(userList, User.class,
				UserListOutputDescriptor.class);

		return jsonForListBasedOnDescriptor;
	}


	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/allocateUserToProject")
	public String allocateUserToProject(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request) throws Exception {
		ProjectBean projectBean = (ProjectBean) JsonUtil.getObject(request.getPayload(),
				ProjectBean.class);
		projectBean=ProjectHandler.getInstance()
		.allocateUserToProject(projectBean);
		String jsonForListBasedOnDescriptor = JsonUtil
		.getJsonBasedOnDescriptor(projectBean,
				ProjectBeanDescriptor.class);
		return jsonForListBasedOnDescriptor;
	}
	
	

	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deAllocateUserFromProject")
	public String deAllocateUsersFromProject(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request) throws Exception{
		ProjectBean projectBean = (ProjectBean) JsonUtil.getObject(request.getPayload(),
				ProjectBean.class);
		projectBean=ProjectHandler.getInstance()
		.deAllocateUsersFromProject(projectBean);
		return "success";
	}
	
	
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/nonMembersOfProject")
	public String nonMembersOfProject(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request) throws Exception{
		Project project = (Project) JsonUtil.getObject(request.getPayload(),
				Project.class);
		List<User> usersList=ProjectHandler.getInstance()
		.nonMembersOfProject(project);
		String jsonForListBasedOnDescriptor = JsonUtil
		.getJsonForListBasedOnDescriptor(usersList, User.class,
				UserListOutputDescriptor.class);

		return jsonForListBasedOnDescriptor;
	}

}
