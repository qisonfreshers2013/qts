/**
 * Ajay kumar

 */
function TimeEntry(){
	Loader.loadHTML('#loadTimeSheetFilling', 'timeEntry.html', true, function() {
		this.handleShow();
	}.ctx(this))
}

TimeEntry.prototype.handleShow=function(){
	$(".container").show();
    $(document).ready(function() {
        console.log("on ready")
        $(".datepicker").datepicker({ minDate: -30, maxDate:new Date()});
    });
    
    
	$('.save').click(function(event){
		if(this.validateTimeEntry()){
		this.getRequestParameters();
		event.preventDefault();
		this.saveTimeEntry();
		}
	}.ctx(this));

}
 
TimeEntry.prototype.getRequestParameters=function(){
	 
	var requestParameters={"date":$('.datepicker').val(),
			               "projectId":$('.projectId').val(),
			               "task":$('.task').val(),
			               "hours":$('.hours').val(),
			               "activityId":$('.selectActivity').val(),
			               "releaseId":$('.SelectRelease').val(),
			               "userRemarks":$('.userRemarks').val()
			               };
      return requestParameters;
} 

TimeEntry.prototype.getInputForTimeSheetFilling=function(){
	var requestParameters=this.getRequestParameters();
	var input={
			    "payload":{ "date":requestParameters.date,
			    	        "projectId":requestParameters.projectId,
			    	         "releaseId":requestParameters.releaseId,
			    	         "task":requestParameters.task,
	                         "activityId":requestParameters.activityId,
	                         "hours":requestParameters.hours,
	                         "status":"0",
	                         "userRemarks":requestParameters.userRemarks
			               }
	          };
	
	return input;
	
}
//
//TimeEntry.prototype.populateFields=function(id,callback){
//	RequestManager.getTimeEntryObjectById({"payload":id},function(data,success){
//		if(success){
//			$('.datepicker').val(data.date);
//			$('.projectId').val(data.projectId);
//			$('.task').val(data.task);
//			$('.hours').val(data.hours);
//			$('.selectActivity').val(data.activityId);
//			$('.SelectRelease').val(data.releaseId);
//			$('.userRemarks').val(data.userRemarks);
//		}else{
//			alert(data.message);
//		}
//	});
//	
//	return this.getInputForTimeSheetFilling();
//}

TimeEntry.prototype.setRequestParameters=function(updateRequestParameters){
	var requestParameters=TimeEntry.getRequestParameters;
	requestParameters.date=updateRequestParameters.date;
	requestParameters.projectId=updateRequestParameters.projectId;
	requestParameters.task=updateRequestParameters.task;
	requestParameters.hours=updateRequestParameters.hours;
	requestParameters.activityId=updateRequestParameters.activityId;
	requestParameters.releaseIdId=updateRequestParameter.releaseId;
	requestParameters.userRemarks=updateRequestParameters.userRemarks;
	return requestParameters;
}
 
     

 
 TimeEntry.prototype.saveTimeEntry=function(){
     var input=this.getInputForTimeSheetFilling();
		RequestManager.addTimeEntry(input, function(data, success) {
			if (success) {
			      alert("TimeEntry Saved");
			} else {
				alert(data.message);
			}
		}.ctx(this));
		DefaultTimeSheetPage.searchUserTimeEntries();
		$( "#loadTimeSheetFilling" ).modal( "hide" );
		$(".clearField").empty();
 }
 
 
TimeEntry.prototype.getProjects=function(){
	 $('#projectName').empty();
	 $('#projectName').append('<option value=0>--select--</option>');
	 RequestManager.getProjects({}, function(data, success) {
	  if(success){
	   var id=0;
	   var name='';
	   $.each(data,function(key1,value1){
	    $.each(value1,function(key2,value2){
	     if(key2==0){
	      id=value2;
	     }else{
	      name=value2;
	     }
	    });
	    $('#projectName').append('<option value='+id+'>'+name+'</option>');
	   });
	  }else{
	   alert(data.message);
	  }
	 }.ctx(this));
	}
 
 
 
 
 
 
  TimeEntry.prototype.validateTimeEntry=function(){
	  var date=$('.datepicker').val();
	  var isvalid=true;
	  $(".error").hide();
	  var dateRegex="^(0[1-9]|1[012])([-/])(0[1-9]|[12][0-9]|3[01])\\2([23]0)\\d\\d$";
	  var pattern=new RegExp(dateRegex);
	  if(date==''){
		  alert("Date is Required.");
		  isvalid=false;
	  }
	  else if(!pattern.test(date)){
		alert("Invalid Date(Format:mm/dd/yyyy).");
		  isvalid=false;
	  }
	  else if($('.task').val()==''){
		  alert("Mention the Task Performed.");
	      isvalid=false;
	  }
	  return isvalid;
  }
