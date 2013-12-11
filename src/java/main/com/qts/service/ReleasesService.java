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
import com.qts.handler.ReleasesHandler;
import com.qts.model.Releases;
import com.qts.model.ReleasesInput;
import com.qts.service.annotations.RestService;
import com.qts.service.annotations.ServiceStatus;
import com.qts.service.annotations.UnSecure;
import com.qts.service.common.WebserviceRequest;
import com.qts.service.descriptors.ReleaseOutputDescriptor;

/**
 * 
 * @author Shiva
 * 
 */

/*
 * To provide the Services Regarding Releases The Methods are listReleases and
 * addReleases
 */

@Path("/v1/releasesService")
public class ReleasesService extends BaseService {

	// Service to display list of Releases of the given project
	@POST
	@RestService(input = String.class, output = Boolean.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/listReleases")
//	@UnSecure
	public String listReleases(@Context HttpHeaders headers,@Context UriInfo uriInfo, WebserviceRequest request)throws Exception {

		ReleasesInput releasesInput = (ReleasesInput) JsonUtil.getObject(request.getPayload(), ReleasesInput.class);
		List<Releases> releasesList = ReleasesHandler.getInstance().listReleases(releasesInput);
		return JsonUtil.getJsonForListBasedOnDescriptor(releasesList,Releases.class, ReleaseOutputDescriptor.class);

	}

	// Service to add Releases for a Project
	@POST
	@RestService(input = String.class, output = Boolean.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addReleases")
//	@UnSecure
	public String addReleases(@Context HttpHeaders headers,@Context UriInfo uriInfo, WebserviceRequest request)throws Exception {

		ReleasesInput releasesInput = (ReleasesInput) JsonUtil.getObject(request.getPayload(), ReleasesInput.class);
		Releases releases = ReleasesHandler.getInstance().addReleases(releasesInput);
		return JsonUtil.getJsonForListBasedOnDescriptor(releases,Releases.class, ReleaseOutputDescriptor.class);

	}

	//Service to Delete Releases based On the ID
	@POST
	@RestService(input = String.class, output = Boolean.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deleteReleases")
//	@UnSecure
	public String deleteReleases(@Context HttpHeaders headers,@Context UriInfo uriInfo, WebserviceRequest request)throws Exception {

		ReleasesInput releasesInput = (ReleasesInput) JsonUtil.getObject(request.getPayload(), ReleasesInput.class);
		Releases releases=ReleasesHandler.getInstance().deleteReleases(releasesInput);
		return JsonUtil.getJsonForListBasedOnDescriptor(releases,Releases.class, ReleaseOutputDescriptor.class);

	}

}
