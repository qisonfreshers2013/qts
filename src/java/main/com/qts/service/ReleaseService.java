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
import com.qts.handler.ReleaseHandler;
import com.qts.model.Release;
import com.qts.model.ReleaseBean;
import com.qts.service.annotations.RestService;
import com.qts.service.annotations.ServiceStatus;
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

@Path("/v1/release/")
public class ReleaseService extends BaseService {

	@POST
	@RestService(input = String.class, output = Boolean.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get")
	// Service to display list of Releases of the given project
	public String getReleases(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {

		ReleaseBean releaseBean = (ReleaseBean) JsonUtil.getObject(
				request.getPayload(), ReleaseBean.class);

		List<Release> releaseList = ReleaseHandler.getInstance().getReleases(
				releaseBean);

		return JsonUtil.getJsonForListBasedOnDescriptor(releaseList,
				Release.class, ReleaseOutputDescriptor.class);

	}

	@POST
	@RestService(input = String.class, output = Boolean.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/add")
	// Service to add Releases for a Project
	public String addRelease(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {

		ReleaseBean releaseBean = (ReleaseBean) JsonUtil.getObject(
				request.getPayload(), ReleaseBean.class);

		Release release = ReleaseHandler.getInstance().addReleaseAOP(releaseBean);

		return JsonUtil.getJsonBasedOnDescriptor(release, Release.class);

	}

	@POST
	@RestService(input = String.class, output = Boolean.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/delete")
	// Service to Delete Existing Releases
	public String deleteRelease(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {

		ReleaseBean releaseBean = (ReleaseBean) JsonUtil.getObject(
				request.getPayload(), ReleaseBean.class);

		Release release = ReleaseHandler.getInstance().deleteReleaseAOP(
				releaseBean);

		return JsonUtil.getJsonBasedOnDescriptor(release, Release.class);

	}

}
