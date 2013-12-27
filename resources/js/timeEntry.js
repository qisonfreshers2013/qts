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
        $(".datepicker").datepicker({ minDate: -30, maxDate:new Date()});
    });
    
    this.getProjects();
    
	$('.save').click(function(event){
		if(this.validateTimeEntry()){
		this.getRequestParameters();
		event.preventDefault();
		this.saveTimeEntry();
		}
	}.ctx(this));
	
	$('.projectId').change(function(event){
	        this.getReleases();
	}.ctx(this));
	

}
 
TimeEntry.prototype.getRequestParameters=function(){
	 
	var requestParameters={"date":$('.datepicker').val(),
			               "projectId":$('.projectId').val(),
			               "task":$('.task').val(),
			               "hours":$('.hours').val(),
			               "activityId":$('.selectActivity').val(),
			               "releaseId":$('.selectRelease').val(),
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
		
 }
 
 
TimeEntry.prototype.getProjects=function(){
	 $('.projectId').empty();
	 $('.projectId').append('<option class=\"projectValue\">SELECT</option>');
	 RequestManager.getProjectsForMember({}, function(data, success) {
	  if(success){
	   var id=0;
	   var name;
	   $.each(data,function(key1,value1){
	    $.each(value1,function(key2,value2){
	     if(key2=='id'){
	      id=value2;
	     }else{
	    	 if(key2=='name')
	      name=value2;
	     }
	    });
	    $('.projectId').append('<option class=\"projectValue\" value='+id+'>'+name+'</option>');
	   });
	  }else{
	   alert(data.message);
	  }
	 }.ctx(this));
	}
 
 
TimeEntry.prototype.getReleases=function(){
	 $('.selectRelease').empty();
	 var id=$(".projectId").val();
	 RequestManager.getProjectReleases({"payload":{"projectId":id}}, function(data, success) {
	  if(success){
	for(var i=0;i<data.length;i++){
		 $('.selectRelease').append('<option class=\"releaseValue\" value='+data[i][0]+'>'+data[i][1]+'</option>');
	}
	  }else{
	   $("cancel").trigger("click");
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
