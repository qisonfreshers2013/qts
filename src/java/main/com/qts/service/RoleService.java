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

import com.qts.common.json.JsonUtil;
import com.qts.handler.RoleHandler;
import com.qts.model.RoleBean;
import com.qts.model.Roles;
import com.qts.service.annotations.RestService;
import com.qts.service.annotations.ServiceStatus;
import com.qts.service.common.WebserviceRequest;
import com.qts.service.descriptors.OptionOutputDescriptor;

@Path("/roleService")
public class RoleService extends BaseService{

	@POST
	@RestService(input=String.class,output=String.class)
	@ServiceStatus(value="complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/listRoles")
	public String listRoles(@Context HttpHeaders headers,@Context UriInfo info,
			WebserviceRequest request) throws Exception {
		List<Roles> listRoles=RoleHandler.getInstance().listRoles();
		String output=JsonUtil.getJsonForListBasedOnDescriptor(listRoles, Roles.class, OptionOutputDescriptor.class);
		return output;
	}
	@POST
	@RestService(input=String.class,output=String.class)
	@ServiceStatus(value="complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/listUserRoles")
	public String listUserRoles(@Context HttpHeaders headers,@Context UriInfo info,
			WebserviceRequest request) throws Exception {
		RoleBean roleBeanInput=(RoleBean)JsonUtil.getObject(request.getPayload(), RoleBean.class);
		RoleBean rolBeanOutput=RoleHandler.getInstance().listUserRoles(roleBeanInput);
		String output=JsonUtil.getJsonBasedOnDescriptor(rolBeanOutput, RoleBean.class);
		return output;
	}
	@POST
	@RestService(input=String.class,output=String.class)
	@ServiceStatus(value="complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/allocateRoles")
	public String allocateRole(@Context HttpHeaders headers,@Context UriInfo info,
			WebserviceRequest request) throws Exception 
	{
		RoleBean roleBeanInput=(RoleBean)JsonUtil.getObject(request.getPayload(),RoleBean.class);
		RoleBean roleBeanOutput=RoleHandler.getInstance().allocateRole(roleBeanInput);
		String output=JsonUtil.getJsonBasedOnDescriptor(roleBeanOutput, Roles.class);
		return output;
	}
	@POST
	@RestService(input=String.class,output=String.class)
	@ServiceStatus(value="complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deallocateRoles")
	public String deallocateRole(@Context HttpHeaders headers,@Context UriInfo info,
			WebserviceRequest request) throws Exception 
	{
		RoleBean roleBeanInput=(RoleBean)JsonUtil.getObject(request.getPayload(),RoleBean.class);
		RoleBean roleBeanOutput=RoleHandler.getInstance().deallocateRole(roleBeanInput);
		String output=JsonUtil.getJsonBasedOnDescriptor(roleBeanOutput, Roles.class);
		return output;
	}
}
