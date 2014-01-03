/**
 * Ajay kumar

 */
function TimeEntry(){
	Loader.loadHTML('#loadTimeSheetFilling', 'timeEntry.html', true, function() {
		 this.getProjects();
	     this.getActivities();
		this.handleShow();
	}.ctx(this))
}

TimeEntry.prototype.handleShow=function(){
	$(".container").show();
    $(document).ready(function() {
        $(".datepicker").datepicker({ minDate: -30, maxDate:new Date()});
    });
    $("#calendarIcon").click(function(){$(".datepicker").focus();}.ctx(this));
    
	$('.save').click(function(event){
		var id=$("input[type=checkbox]:checked").val();
		if(this.validateTimeEntry()){
		this.getRequestParameters();
		event.preventDefault();
		if(id==null){
		this.saveTimeEntry();
		}else{
			this.updateTimeEntry();
		     }
		}
	}.ctx(this));
	
	$('.projectId').change(function(event){
	        this.getReleases();
	}.ctx(this));
	$('#closeTheModal').click(function(event){
		$(".cancel").trigger("click");
	}.ctx(this));	

}
 
TimeEntry.prototype.getRequestParameters=function(){
	 
	var requestParameters={"date":$('.datepicker').val(),
			               "projectId":$('.projectId').val(),
			               "task":$('.task').val(),
			               "hours":$('.hours').val(),
			               "minutes":$('.minutes').val(),
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
	                         "minutes":requestParameters.minutes,
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
			      $("#cancel").trigger("click");
			      $(".searchUserTimeEntries").trigger("click");
			      
			    } else {
			    	$.ambiance({
	    			    message : data.message,
	    			    type : 'error'
	    			   });
			}
		}.ctx(this));
		//DefaultTimeSheetPage.searchUserTimeEntries();
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
		  $.ambiance({
			    message : data.message,
			    type : 'error'
			   });
	  }
	 }.ctx(this));
	}
 
 
TimeEntry.prototype.getReleases=function(){
	 $('.selectRelease').empty();
	 $('.selectRelease').append('<option>SELECT</option>');
	 
	 var id=$(".projectId").val();
	 if(id!='SELECT'){
	 RequestManager.getProjectReleases({"payload":{"projectId":id}}, function(data, success) {
	  if(success){
		  if(data.length!=0){
	for(var i=0;i<data.length;i++){
		 $('.selectRelease').append('<option class=\"releaseValue\" value='+data[i][0]+'>'+data[i][1]+'</option>');
	          }}
		  else {alert("No Releases For This Project.");}
	  }else{
	   $("cancel").trigger("click");
	  }
	 }.ctx(this));}else{
		  $.ambiance({
			    message : 'Select the project to get releases.',
			    type : 'error'
			   });
	 }
	}
 
TimeEntry.prototype.getActivities=function(){
	 $('.selectActivity').empty();
	 $('.selectActivity').append('<option>SELECT</option>');
	 RequestManager.getActivities({"payload":{}}, function(data, success) {
	  if(success){
	for(var i=0;i<data.length;i++){
		 $('.selectActivity').append('<option class=\"activityValue\" value='+data[i].id+'>'+data[i].name+'</option>');
	}
	  }else{
	   $("cancel").trigger("click");
	  }
	 }.ctx(this));
	}
TimeEntry.prototype.updateTimeEntry=function(){
	 
	var id=$("input[type=checkbox]:checked").val();
	 
	var input={ "payload":{"id":id,
         "projectId":$('.projectId').val(),
         "releaseId":$('.selectRelease').val(),
         "activityId":$('.selectActivity').val(),
         "date":$('.datepicker').val(),
         "task":$('.task').val(),
         "hours":$('.hours').val(),
         "minutes":$('.minutes').val(),
         "userRemarks":$('.userRemarks').val(),
         "status":0
         } 
      }
	
	RequestManager.updateTimeEntry(input,function(data,success){
		if(success){
			if(data){
				$.ambiance({
				    message : 'Updated.',
				    type : 'success'
				   });
			$(".cancel").trigger("click");
			$(".searchUserTimeEntries").trigger("click");
			$( "#loadTimeSheetFilling" ).modal('hide');
			}else{
				$.ambiance({
				    message : 'Not Updated.',
				    type : 'error'
				   });
			}
		}
		else{
			$.ambiance({
			    message : data.message,
			    type : 'error'
			   });
		}
	});
}
 
  TimeEntry.prototype.validateTimeEntry=function(){
	  var date=$('.datepicker').val();
	  var userRemarks=$('.userRemarks').val();
	  var task=$('.task').val();
	  var isvalid=true;
	  $(".error").hide();
	  var dateRegex="^(0[1-9]|1[012])([-/])(0[1-9]|[12][0-9]|3[01])\\2([23]0)\\d\\d$";
	  var pattern=new RegExp(dateRegex);
	  if(date==''){
		  $.ambiance({
			    message :'Date is Required.',
			    type : 'error'
			   });
		  isvalid=false;
	  }
	  else if(!pattern.test(date)){
		  $.ambiance({
			    message :'Invalid Date(Format:mm/dd/yyyy).',
			    type : 'error'
			   });
		  isvalid=false;
	  }
	  else if( $('.projectId').val()=='SELECT'){
		  $.ambiance({
			    message :'Select a project.',
			    type : 'error'
			   });
		  isvalid=false;
	  }
	  else if( $('.selectRelease').val()=='SELECT'){
		  $.ambiance({
			    message :'Select the Release Version of the project.',
			    type : 'error'
			   });
		  isvalid=false;
	  }
	  else if($('.task').val()==''){
		  $.ambiance({
			    message :'Mention the Task Performed.',
			    type : 'error'
			   });
	      isvalid=false;
	  }
	  else if(task.length>512){
		  $.ambiance({
			    message :'Max of 512 characters is supported.',
			    type : 'error'
			   });  
		  isvalid=false;
	  }
	  else if( $('.selectActivity').val()=='SELECT'){
		  $.ambiance({
			    message :'Select the Activity Done.',
			    type : 'error'
			   });	
		  isvalid=false;
	  }
	  else if( $('.hours').val()=='SELECT'){
		  $.ambiance({
			    message :'Select Hours.',
			    type : 'error'
			   });
		 
		  isvalid=false;
	  }
	
	  else if(userRemarks.length>4096){
		  $.ambiance({
			    message :'Max of 4096 characters is supported.',
			    type : 'error'
			   });
		  isvalid=false;
	  }

	
	

	  return isvalid;
  }
