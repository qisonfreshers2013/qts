package com.qts.service;

/**
 * Activities Services Class
 * @author Ajay
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
import com.qts.handler.ActivityHandler;
import com.qts.model.Activities;
import com.qts.service.annotations.RestService;
import com.qts.service.annotations.ServiceStatus;
import com.qts.service.common.WebserviceRequest;
import com.qts.service.descriptors.ActivitiesOptionOutputDescriptor;



@Path("/v1/activities")
public class ActivitiesService extends BaseService {

	   /*
	    * AddEntry Service 
	    */
		@POST
		@RestService(input = String.class, output = String.class)
		@ServiceStatus(value = "complete")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@Path("/getActivities")
		public String getActivities(@Context HttpHeaders headers,
				@Context UriInfo uriInfo, WebserviceRequest request)
				throws Exception {
			List<Activities> getActivities=ActivityHandler.getInstance().getActivities();
			return  JsonUtil
					.getJsonForListBasedOnDescriptor(getActivities, Activities.class,
							ActivitiesOptionOutputDescriptor.class);
		}


}
