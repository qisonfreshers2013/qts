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
import com.qts.model.BaseObject;
import com.qts.model.GetListOfTimeEntryBeans;
import com.qts.model.TimeEntries;
import com.qts.model.TimeEntryBean;
import com.qts.service.annotations.RestService;
import com.qts.service.annotations.ServiceStatus;
import com.qts.service.common.WebserviceRequest;
import com.qts.service.descriptors.TimeEntriesOptionOutputDescriptor;

@Path("/v1/timeEntry")
public class TimeEntryService extends BaseService{
   /*
    * AddEntry Service 
    */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/add")
	public String add(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		TimeEntryBean timeEntry = (TimeEntryBean) JsonUtil
				.getObject(request.getPayload(), TimeEntryBean.class);
		
       boolean addedTimeEntry=TimeEntryHandler.getInstance().add(timeEntry);
        
        return JsonUtil.getJsonBasedOnDescriptor(addedTimeEntry,Boolean.class);
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
	public String reject(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		TimeEntryBean timeEntry = (TimeEntryBean) JsonUtil
				.getObject(request.getPayload(), TimeEntryBean.class);
		
		  Boolean isRejected=TimeEntryHandler.getInstance().reject(timeEntry);
		  
		  return JsonUtil.getJsonBasedOnDescriptor(isRejected,Boolean.class); 
	}
	 /*
	  * UpdateEntry Service
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/update")
	public String update(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		TimeEntryBean update = (TimeEntryBean) JsonUtil
				.getObject(request.getPayload(), TimeEntryBean.class);
		
		Boolean isUpdated=TimeEntryHandler.getInstance().update(update); 
		
	     return JsonUtil.getJsonBasedOnDescriptor(isUpdated,Boolean.class);
	}
	 /*
	  * DeleteEntry Service
	 */
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/delete")
	public String delete(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		TimeEntryBean timeEntry = (TimeEntryBean) JsonUtil
				.getObject(request.getPayload(), TimeEntryBean.class);
		
		Boolean isDeleted=TimeEntryHandler.getInstance().delete(timeEntry);
		
	     return JsonUtil.getJsonBasedOnDescriptor(isDeleted,Boolean.class);
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
	public String approveTimeEntry(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		TimeEntryBean timeEntry = (TimeEntryBean) JsonUtil
				.getObject(request.getPayload(), TimeEntryBean.class);
		
		Boolean isApproved=TimeEntryHandler.getInstance().approve(timeEntry);
		
	     return JsonUtil.getJsonBasedOnDescriptor(isApproved,Boolean.class);     
		
		}
	 /*
	  * SearchTimeSheet For User
	 */

	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/searchTimeEntriesByUser")
	public String searchTimeEntriesByUser(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		//Here searchCriteria can be date,ProjectId and blank search
	   
		TimeEntryBean searchCriteria = (TimeEntryBean) JsonUtil.getObject(request.getPayload(), TimeEntryBean.class);
	
		List<TimeEntries> searchResult=TimeEntryHandler.getInstance().search(searchCriteria);
		
	return JsonUtil.getJsonForListBasedOnDescriptor(searchResult, TimeEntries.class, TimeEntriesOptionOutputDescriptor.class);
		

		
	}
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/searchTimeEntriesByApprover")
	public String searchTimeEntriesByApprover(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		
		//Here searchCriteria can be UserId,ProjectId,From,To,Status and blank search
		
		TimeEntryBean searchCriteria = (TimeEntryBean) JsonUtil.getObject(request.getPayload(), TimeEntryBean.class);
		
		List<TimeEntries> searchResult=TimeEntryHandler.getInstance().searchTimeEntriesByApprover(searchCriteria);
		
		return JsonUtil.getJsonForListBasedOnDescriptor(searchResult, TimeEntries.class, TimeEntriesOptionOutputDescriptor.class);
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
		
		GetListOfTimeEntryBeans getTimeEntriesToSubmit = (GetListOfTimeEntryBeans) JsonUtil
				.getObject(request.getPayload(), GetListOfTimeEntryBeans.class);
		
		boolean isSubmitted=TimeEntryHandler.getInstance().submit(getTimeEntriesToSubmit.getTimeEntries());
		
	     return JsonUtil.getJsonBasedOnDescriptor(isSubmitted, Boolean.class);
	}
	
	
	@POST
	@RestService(input = String.class, output = String.class)
	@ServiceStatus(value = "complete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getTimeEntry")
	public String getTimeEntryObjectById(@Context HttpHeaders headers,
			@Context UriInfo uriInfo, WebserviceRequest request)
			throws Exception {
		Long id = (Long) JsonUtil
				.getObject(request.getPayload(), Long.class);
		
		BaseObject timeEntry=TimeEntryHandler.getInstance().getObjectById(id);
		
	     return JsonUtil.getJsonBasedOnDescriptor(timeEntry,BaseObject.class);     
		
		}
	
	
	
}
