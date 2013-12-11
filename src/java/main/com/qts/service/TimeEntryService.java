package com.qts.service;

/**
 * TimeEntry Services Class
 * @author Ajay
 */

import java.util.List;
import java.util.Set;

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
import com.qts.model.GetTimeEntriesForm;
import com.qts.model.TimeEntries;
import com.qts.model.TimeEntriesForm;
import com.qts.service.annotations.RestService;
import com.qts.service.annotations.ServiceStatus;
import com.qts.service.common.WebserviceRequest;
import com.qts.service.descriptors.TimeEntriesOptionOutputDescriptor;

@Path("/TimeEntryService")
public class TimeEntryService {
   /*
    * AddEntry Service 
    */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/AddEntry")
	public String addEntry(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		TimeEntriesForm form = (TimeEntriesForm) JsonUtil
				.getObject(request.getPayload(), TimeEntriesForm.class);
        Boolean added=TimeEntryHandler.getInstance().addEntry(form);
		return "STATUS "+added;
		}

	 /*
	  * RejectEntry Service For Approver
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/RejectEntries")
	public String rejectEntry(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		GetTimeEntriesForm timeEntries = (GetTimeEntriesForm) JsonUtil
				.getObject(request.getPayload(), GetTimeEntriesForm.class);
		List<TimeEntries> rejectedTimeEntries=TimeEntryHandler.getInstance().rejectEntry(timeEntries.getTimeEntriesform());
             return JsonUtil.getJsonForListBasedOnDescriptor(rejectedTimeEntries, TimeEntries.class, TimeEntriesOptionOutputDescriptor.class);
	}
	 /*
	  * UpdateEntry Service
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/UpdateEntry")
	public String updateEntry(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		TimeEntriesForm updateWithData = (TimeEntriesForm) JsonUtil
				.getObject(request.getPayload(), TimeEntriesForm.class);
		Boolean update=TimeEntryHandler.getInstance().updateEntry(updateWithData);
          
		return "Status"+update;
	}
	 /*
	  * DeleteEntry Service
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/DeleteEntry")
	public String deleteEntry(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		TimeEntriesForm timeEntries = (TimeEntriesForm) JsonUtil
				.getObject(request.getPayload(), TimeEntriesForm.class);
		Boolean deleted=TimeEntryHandler.getInstance().deleteEntry(timeEntries);
		return "STATUS"+deleted;
	}
	 /*
	  * ApproveEntries Service For Approver
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/ApproveEntries")
	public String approveEntries(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		GetTimeEntriesForm timeEntries = (GetTimeEntriesForm) JsonUtil
				.getObject(request.getPayload(), GetTimeEntriesForm.class);
		Boolean approved=TimeEntryHandler.getInstance().approveEntry(timeEntries.getTimeEntriesform());
         return "Status"+approved;     
		
		
	}
	 /*
	  * SearchTimeSheet For User
	 */

	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/SearchUserEntries")
	public String GetTimeEntries(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
	TimeEntriesForm form = (TimeEntriesForm) JsonUtil
				.getObject(request.getPayload(), TimeEntriesForm.class);
		List<TimeEntries> entriesList=TimeEntryHandler.getInstance().searchUserEntries(form);
		
			return JsonUtil.getJsonForListBasedOnDescriptor(entriesList, TimeEntries.class, TimeEntriesOptionOutputDescriptor.class);
		

		
	}
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/SearchUserEntriesForApprover")
	public String GetTimeEntriesForApprover(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		TimeEntriesForm form = (TimeEntriesForm) JsonUtil
				.getObject(request.getPayload(), TimeEntriesForm.class);
		List<TimeEntries> entriesList=TimeEntryHandler.getInstance().searchUserEntriesForApprover(form);
		return JsonUtil.getJsonForListBasedOnDescriptor(entriesList, TimeEntries.class, TimeEntriesOptionOutputDescriptor.class);
		}


	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/SubmitTimeEntries")
	public String submitTimeEntries(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		GetTimeEntriesForm submitEntries = (GetTimeEntriesForm) JsonUtil
				.getObject(request.getPayload(), GetTimeEntriesForm.class);
		
		boolean submitted=TimeEntryHandler.getInstance().submitTimeEntries(submitEntries.getTimeEntriesform());
		return "status"+submitted;
	
	}
}
