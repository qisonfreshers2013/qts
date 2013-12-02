package com.qts.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.qts.handler.RoleHandler;
import com.qts.service.annotations.RestService;
import com.qts.service.annotations.ServiceStatus;
import com.qts.service.common.WebserviceRequest;

@Path("/roleservice")
public class RoleService extends BaseService{

	@POST
	@RestService(input=String.class,output=String.class)
	@ServiceStatus(value="complete")
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/listroles")
	public String listRoles(@Context HttpHeaders headers,@Context UriInfo info,
			WebserviceRequest request) throws Exception {
		String s=RoleHandler.getInstance().listRoles();
		return "{\"status\":\"SUCCESS\", \"payload\":"+s+"}";
	}
	@POST
	@RestService(input=String.class,output=String.class)
	@ServiceStatus(value="complete")
	public String allocateRole()
	{
		return "";
	}
}
