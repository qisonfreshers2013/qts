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
import com.qts.model.ProjectUserRecords;
import com.qts.model.User;
import com.qts.service.annotations.RestService;
import com.qts.service.annotations.ServiceStatus;
import com.qts.service.common.WebserviceRequest;
import com.qts.service.descriptors.ProjectOutputDescriptor;
import com.qts.service.descriptors.UserListOutputDescriptor;
import com.qts.service.descriptors.UserOutputDescriptor;

@Path("/v1/project/")
public class ProjectService extends BaseService {
	
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
	@Path("/getProjectsForMember")
	public String getProjectsForMember(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
	throws Exception {

		List<Project> projectList = ProjectHandler.getInstance()
		.getProjectsForMember();

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
	@Path("/getProjectsForApprover")
	public String getProjectsForApprover(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
	throws Exception {

		List<Project> projectList = ProjectHandler.getInstance()
		.getProjectsForApprover();

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
		
		Project projectOutput = ProjectHandler.getInstance().addProjectAOP(project);
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
		ProjectUserRecords projectUserRecords = ProjectHandler.getInstance()
		.getProjectUsers(projectBean);
		
		return JsonUtil.getJsonBasedOnDescriptor(projectUserRecords,
				ProjectUserRecords.class);
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
		ProjectUserRecords projectUserRecords =ProjectHandler.getInstance()
		.allocateUsersToProjectAOP(projectBean);
		return JsonUtil.getJsonBasedOnDescriptor(projectUserRecords,
				ProjectUserRecords.class);
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
	  
	  ProjectUserRecords projectUserRecords=ProjectHandler.getInstance()
	  .deAllocateUsersFromProjectAOP(projectBean);
	  
	  return JsonUtil.getJsonBasedOnDescriptor(projectUserRecords,
				ProjectUserRecords.class);
	 }
	
	 
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getNonUsersOfProject")
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
