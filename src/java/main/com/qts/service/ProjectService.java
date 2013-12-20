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
import com.qts.model.Project;
import com.qts.model.ProjectBean;
import com.qts.model.User;
import com.qts.model.UserProject;
import com.qts.service.annotations.RestService;
import com.qts.service.annotations.ServiceStatus;
import com.qts.service.annotations.UnSecure;
import com.qts.service.common.WebserviceRequest;
import com.qts.service.descriptors.ProjectOutputDescriptor;
import com.qts.service.descriptors.UserListOutputDescriptor;
import com.qts.service.descriptors.UserOutputDescriptor;
import com.qts.service.descriptors.UserProjectOutputDescriptor;

@Path("/v1/project/")
public class ProjectService {

	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getProjects")
	public String getProject(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
	throws Exception {

		List<Project> projectList = ProjectHandler.getInstance()
		.getProjects();

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
	@Path("/getProjectsForUser")
	public String getProjectsForUser(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
	throws Exception {

		List<Project> projectList = ProjectHandler.getInstance()
		.getProjectsForUser();

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
	@Path("/add")
	public String addProject(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
	throws Exception {
		
		Project project = (Project) JsonUtil.getObject(request.getPayload(),
				Project.class);
		
		Project projectOutput = ProjectHandler.getInstance().addProject(project);
		String jsonForListBasedOnDescriptor = JsonUtil
		.getJsonBasedOnDescriptor( projectOutput,
				ProjectOutputDescriptor.class);
		
		return jsonForListBasedOnDescriptor;

	}

	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getProjectUsers")
	public String getProjectUsers(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
	throws Exception {
		ProjectBean projectBean = (ProjectBean) JsonUtil.getObject(request.getPayload(),
				ProjectBean.class);
		List<User> userList = ProjectHandler.getInstance()
		.getProjectUsers(projectBean);
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
	@Path("/allocateUsersToProject")
	public String allocateUsersToProject(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request) throws Exception {
		ProjectBean projectBean = (ProjectBean) JsonUtil.getObject(request.getPayload(),
				ProjectBean.class);
		List<UserProject> userProject=ProjectHandler.getInstance()
		.allocateUsersToProject(projectBean);
		String jsonForListBasedOnDescriptor = JsonUtil
		.getJsonForListBasedOnDescriptor(userProject,UserProject.class,
				UserProjectOutputDescriptor.class);
		return jsonForListBasedOnDescriptor;
	}
	
	
	 @POST
	 @RestService(input = String.class, output = String.class)
	 @ServiceStatus(value = "complete")
	 @Consumes(MediaType.APPLICATION_JSON)
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/deAllocateUsersFromProject")
	 public String deAllocateUsersFromProject(@Context HttpHeaders headers,
	   @Context UriInfo uriInfo, WebserviceRequest request) throws Exception{
		 
	  ProjectBean projectBean = (ProjectBean) JsonUtil.getObject(request.getPayload(),
	    ProjectBean.class);
	  
	  List<UserProject> userProject=ProjectHandler.getInstance()
	  .deAllocateUsersFromProject(projectBean);
	  
	  String jsonForListBasedOnDescriptor = JsonUtil
				.getJsonForListBasedOnDescriptor(userProject,UserProject.class,
						UserProjectOutputDescriptor.class);
				return jsonForListBasedOnDescriptor;
	 }
	
	 
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/nonUsersOfProject")
	public String nonUsersOfProject(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request) throws Exception{
		
		ProjectBean projectBean = (ProjectBean) JsonUtil.getObject(request.getPayload(),
				ProjectBean.class);
		
		List<User> usersList=ProjectHandler.getInstance()
		.nonUsersOfProject(projectBean);
		
		String jsonForListBasedOnDescriptor = JsonUtil
		.getJsonForListBasedOnDescriptor(usersList, User.class,
				UserOutputDescriptor.class);

		return jsonForListBasedOnDescriptor;
	}

}
