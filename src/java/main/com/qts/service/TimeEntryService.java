package com.qts.service;

/**
 * TimeEntry Services Class
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
import com.qts.handler.TimeEntryHandler;
import com.qts.model.GetTimeEntriesBean;
import com.qts.model.TimeEntries;
import com.qts.model.TimeEntryBean;
import com.qts.service.annotations.RestService;
import com.qts.service.annotations.ServiceStatus;
import com.qts.service.common.WebserviceRequest;
import com.qts.service.descriptors.TimeEntriesOptionOutputDescriptor;

@Path("/v1/timeEntryService")
public class TimeEntryService {
   /*
    * AddEntry Service 
    */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addTimeEntry")
	public String addEntry(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		TimeEntryBean timeEntry = (TimeEntryBean) JsonUtil
				.getObject(request.getPayload(), TimeEntryBean.class);
		
        Boolean added=TimeEntryHandler.getInstance().add(timeEntry);
        
        return JsonUtil.getJsonBasedOnDescriptor(added,Boolean.class);
		}

	 /*
	  * RejectEntry Service For Approver
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/rejectTimeEntry")
	public String rejectEntry(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		TimeEntryBean timeEntry = (TimeEntryBean) JsonUtil
				.getObject(request.getPayload(), TimeEntryBean.class);
		
		  Boolean rejected=TimeEntryHandler.getInstance().reject(timeEntry);
		  
		  return JsonUtil.getJsonBasedOnDescriptor(rejected,Boolean.class); 
	}
	 /*
	  * UpdateEntry Service
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateTimeEntry")
	public String updateEntry(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		TimeEntryBean updateWithData = (TimeEntryBean) JsonUtil
				.getObject(request.getPayload(), TimeEntryBean.class);
		
		Boolean update=TimeEntryHandler.getInstance().update(updateWithData); 
		
	     return JsonUtil.getJsonBasedOnDescriptor(update,Boolean.class);
	}
	 /*
	  * DeleteEntry Service
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deleteTimeEntry")
	public String deleteEntry(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		TimeEntryBean timeEntries = (TimeEntryBean) JsonUtil
				.getObject(request.getPayload(), TimeEntryBean.class);
		
		Boolean deleted=TimeEntryHandler.getInstance().delete(timeEntries);
		
	     return JsonUtil.getJsonBasedOnDescriptor(deleted,Boolean.class);
	}
	 /*
	  * ApproveEntries Service For Approver
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/approveTimeEntry")
	public String approveEntries(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		TimeEntryBean timeEntry = (TimeEntryBean) JsonUtil
				.getObject(request.getPayload(), TimeEntryBean.class);
		
		Boolean approved=TimeEntryHandler.getInstance().approve(timeEntry);
		
	     return JsonUtil.getJsonBasedOnDescriptor(approved,Boolean.class);     
		
		
	}
	 /*
	  * SearchTimeSheet For User
	 */

	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/searchUserEntries")
	public String GetTimeEntries(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
	TimeEntryBean form = (TimeEntryBean) JsonUtil
				.getObject(request.getPayload(), TimeEntryBean.class);
	
		List<TimeEntries> entriesList=TimeEntryHandler.getInstance().search(form);
		
	return JsonUtil.getJsonForListBasedOnDescriptor(entriesList, TimeEntries.class, TimeEntriesOptionOutputDescriptor.class);
		

		
	}
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/searchUserEntriesForApprover")
	public String GetTimeEntriesForApprover(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		TimeEntryBean timeEntry = (TimeEntryBean) JsonUtil
				.getObject(request.getPayload(), TimeEntryBean.class);
		
		List<TimeEntries> entriesList=TimeEntryHandler.getInstance().approverSearch(timeEntry);
		
		return JsonUtil.getJsonForListBasedOnDescriptor(entriesList, TimeEntries.class, TimeEntriesOptionOutputDescriptor.class);
		}
                     

	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/submitTimeEntries")
	public String submitTimeEntries(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		
		GetTimeEntriesBean timeEntriesToSubmit = (GetTimeEntriesBean) JsonUtil
				.getObject(request.getPayload(), GetTimeEntriesBean.class);
		
		boolean submitted=TimeEntryHandler.getInstance().submit(timeEntriesToSubmit.getTimeEntriesform());
		
	     return JsonUtil.getJsonBasedOnDescriptor(submitted, Boolean.class);
					
	
	}
}
