/**
 * Ajay
 */
function ApproverSearch(){
	Loader.loadHTML('.searchTimeEntries', 'approverSearch.html', true, function() {
		this.handleShow();
	}.ctx(this))
}

ApproverSearch.prototype.handleShow=function(){
	$(".container").show();
	
	$("#rejectedComments").hide();
	
	$(".userProjectId").change(function(){
		this.getUsers();
	}.ctx(this));
	
    $(document).ready(function() {
        $(".from").datepicker({maxDate:new Date()});
        $(".to").datepicker({maxDate:new Date()});
        this.getProjects();
        $(".approverTableHeader").hide();
        this.searchTimeEntriesByApprover();
    }.ctx(this));   
    
    $("#calendarForFrom").click(function(){$(".from").focus();}.ctx(this));
    $("#calendarForTo").click(function(){$(".to").focus();}.ctx(this));
    
    
    
	$('#searchTimeEntriesByApprover').click(function(event){
		if(this.validateSearchCriteria()){
		this.searchTimeEntriesByApprover();}
		}.ctx(this));
	
 	$(".submitComments").click(function(){
		$("#rejectedComments").hide();
		if(statusToApproveOrReject==1){
		this.approveTimeEntry(event);
		}else{
			if(statusToApproveOrReject==2){
			if($(".comments").val()!=''){
				$("#rejectedComments").hide();
				this.rejectTimeEntry(event);}else{
					$("#rejectedComments").modal('show');
					$.ambiance({
        			    message : 'Mention the Comments.',
        			    type : 'error'
        			   });
				}	
		}}
	}.ctx(this));
	
	
  	$('#cancel').click(function(event){
		$("#closeBtnForComments").trigger("click");
		}.ctx(this));
  	
    
}



ApproverSearch.prototype.getProjects=function(){
	 $('#userProjectId').empty();
	 $('#userProjectId').append('<option>SELECT</option>');
	 RequestManager.getProjectsForApprover({}, function(data, success) {
	  if(success){
	   var id=0;
	   var name='';
	   $.each(data,function(key1,value1){
	    $.each(value1,function(key2,value2){
	     if(key2=='id'){
	      id=value2;
	     }else{
	    	 if(key2=='name')
	      name=value2;
	     }
	    });
	    $('#userProjectId').append('<option value='+id+'>'+name+'</option>');
	   });
	  }else{
		  $.ambiance({
			    message : data.message,
			    type : 'error'
			   });
	  }
	 }.ctx(this));
	}
ApproverSearch.prototype.getUsers=function(){
	var projectId;
	if($(".userProjectId").val()!='SELECT'){
		projectId=$(".userProjectId").val();
		$('.userId').empty();
		 $('.userId').append('<option>SELECT</option>');
		 RequestManager.getProjectUsers({"payload":{"projectId":projectId}}, function(data, success) {
		  if(success){
		   var id=0;
		   var records=data.projectUserRecords;
		   var name='';
		   $.each(records,function(key1,value1){
		    $.each(value1,function(key2,value2){
		     if(key2=='id'){
		      id=value2;
		     }else{
		    	 if(key2=='firstName')
		      name=value2;
		     }
		    });
		    $('.userId').append('<option value='+id+'>'+name+'</option>');
		   });
		  }else{
			  $.ambiance({
  			    message : data.message,
  			    type : 'error'
  			   });
		  }
		 }.ctx(this));
	}else{
		 $('.userId').empty();
		 $('.userId').append('<option>SELECT</option>');
	     } 

	 }
ApproverSearch.prototype.getSearchCriteria=function(){
	
	var searchCriteria={"userId":$(".userId").val(),
			            "projectId":$(".userProjectId").val(),
			             "status":$(".status").val(),
			             "from":$(".from").val(),
			              "to":$(".to").val()};
	
	return searchCriteria;
	
	
	
}
ApproverSearch.prototype.getInputForSearchUserTimeEntriesByApprover=function(){
	var input;
	var searchCriteria=this.getSearchCriteria();
//	 if(searchCriteria.from!='' && searchCriteria.projectId!='SELECT'&& searchCriteria.status!=0 && searchCriteria.userId!='SELECT' && searchCriteria.to!='')
//		input={ "payload": { "projectId":searchCriteria.projectId,"status":searchCriteria.status,"userId":searchCriteria.userId,"from":searchCriteria.from,"to":searchCriteria.to}}; 
//	else if(searchCriteria.from=='' && searchCriteria.projectId=='SELECT'&& searchCriteria.status!=0 && searchCriteria.userId!='SELECT' && searchCriteria.to=='')
//		input={ "payload": {"status":searchCriteria.status,"userId":searchCriteria.userId}}; 
//	else if(searchCriteria.from=='' && searchCriteria.projectId!='SELECT'&& searchCriteria.status!=0 && searchCriteria.userId=='SELECT' && searchCriteria.to=='')
//		input={ "payload": { "projectId":searchCriteria.projectId,"status":searchCriteria.status}}; 
//	else if(searchCriteria.from=='' && searchCriteria.projectId!='SELECT'&& searchCriteria.status!=0 && searchCriteria.userId!='SELECT' && searchCriteria.to=='')
//		input={ "payload": { "projectId":searchCriteria.projectId,"status":searchCriteria.status,"userId":searchCriteria.userId}}; 
//	else if(searchCriteria.from!='' && searchCriteria.projectId!='SELECT'&& searchCriteria.status!=0 && searchCriteria.userId!='SELECT' && searchCriteria.to=='')
//		input={ "payload": { "projectId":searchCriteria.projectId,"status":searchCriteria.status,"userId":searchCriteria.userId,"from":searchCriteria.from}}; 
//	else if(searchCriteria.from!='' && searchCriteria.projectId=='SELECT'&& searchCriteria.status!=0 && searchCriteria.userId=='SELECT' && searchCriteria.to!='')
//		input={ "payload": { "status":searchCriteria.status,"from":searchCriteria.from,"to":searchCriteria.to}}; 
//	else if(searchCriteria.from=='' && searchCriteria.projectId=='SELECT'&& searchCriteria.status!=0 && searchCriteria.userId=='SELECT' && searchCriteria.to=='')
//		input={ "payload": {"status":searchCriteria.status}}; 
//	else if(searchCriteria.from=='' && searchCriteria.projectId!='SELECT'&& searchCriteria.status==0 && searchCriteria.userId=='SELECT' && searchCriteria.to=='')
//		input={ "payload": {"projectId":searchCriteria.projectId}};
//	else if(searchCriteria.from=='' && searchCriteria.projectId=='SELECT'&& searchCriteria.status==0 && searchCriteria.userId=='SELECT' && searchCriteria.to==''){
//		input={ "payload": {}};
//	}
	 var dataToSend='{"payload":{';
	 if(searchCriteria.from!=''){
	 dataToSend=dataToSend+'"from":'+'"'+searchCriteria.from+'"';
      }
	 if(searchCriteria.to!='' && searchCriteria.from!=''){
		 dataToSend=dataToSend+',"to":'+'"'+searchCriteria.to+'"';
	 }
	 
	 if(searchCriteria.projectId!='SELECT' && searchCriteria.from!=''){
		 dataToSend=dataToSend+',"projectId":'+searchCriteria.projectId;	

	 } else{
		 if(searchCriteria.projectId!='SELECT')
		 dataToSend=dataToSend+'"projectId":'+searchCriteria.projectId;
	 }
	 if(searchCriteria.userId!='SELECT'){
		 dataToSend=dataToSend+',"userId":'+searchCriteria.userId;
      }
	 if(searchCriteria.status!=0 || searchCriteria.from!='' || searchCriteria.projectId!='SELECT' || searchCriteria.userId!='SELECT'){
		 if(searchCriteria.status!=0){
		 dataToSend=dataToSend+',"status":'+searchCriteria.status;
		 }
	 }else
		 {
		 if(searchCriteria.status!=0){
			 dataToSend=dataToSend+'"status":'+searchCriteria.status;
		 }
		 
		 }
	    dataToSend=dataToSend+'}}';
	
	
	 var data=$.parseJSON(dataToSend);
	 input=data;
	 
	return input;
}





ApproverSearch.prototype.searchTimeEntriesByApprover = function() {
	
	var input=this.getInputForSearchUserTimeEntriesByApprover();
//	if(input==null || input =={"payload":{"projectId":"SELECT"}}){
//		input={"payload":{}}
//	}
     RequestManager.searchTimeEntriesByApprover(input,function(data,success){
    		if(success){
    			var status;
    			var operations;
    			$(".approverTableData").empty();
    			if(data.length!=0){
    			$(".approverTableHeader").show();
    			for(var i=0;i<data.length;i++){
    				
    				if(data[i].status==2){
    					status="Approved";
    					operations="";
    				}
    				if(data[i].status==3){
    					status="Rejected";
    					operations="";
    				}
    				if(data[i].status==1){
    					status="Pending";
    					operations="<button class=\"approve approveTimeEntry\" id=\"approveTimeEntry"+data[i].id+"\" value=\""+data[i].id+"\">.</button><button class=\"reject rejectTimeEntry\" id=\"rejectTimeEntry"+data[i].id+"\" value=\""+data[i].id+"\">.</button>";
    				}
    				var tabledata="<tr class=\"approverTableData\">"+
                    "<td>"+$.datepicker.formatDate('mm/dd/yy', new Date(data[i].dateInLong))+"</td>"+
                    "<td title='"+data[i].projectName+"'>"+(data[i].projectName.charAt(0).toUpperCase()+data[i].projectName.substr(1).toLowerCase()).ellipses(10)+"</td>"+
                    "<td title='"+data[i].userName+"'>"+(data[i].userName.charAt(0).toUpperCase()+data[i].userName.substr(1).toLowerCase()).ellipses(10)+"</td>"+
                    "<td title='"+data[i].releaseVersion+"'>"+data[i].releaseVersion.ellipses(10)+"</td>"+
                    "<td title='"+data[i].task+"'>"+(data[i].task.charAt(0).toUpperCase()+data[i].task.substr(1).toLowerCase()).ellipses(10)+"</td>"+
                    "<td title='"+data[i].activity+"'>"+(data[i].activity.charAt(0).toUpperCase()+data[i].activity.substr(1).toLowerCase()).ellipses(10)+"</td>"+
                    "<td>"+data[i].hours+"</td>"+
                    "<td>"+status+"</td>"+
                    "<td>"+operations+"</td>";
                    //"<td><button class=\"approve approveTimeEntry\" id=\"approveTimeEntry\" value=\""+data[i][0]+"\">.</button><button class=\"reject rejectTimeEntry\" id=\"rejectTimeEntry\" value=\""+data[i][0]+"\">.</button></td>";
                    $(".approverTableHeader").after(tabledata);
//    	    			 $('#approveTimeEntry'+data[i].id).click(function(event){
//    					    	$("#rejectedComments").modal('show');
//    					    	$(".submitComments").click(function(){
//    								$("#rejectedComments").hide();
//    								this.approveTimeEntry(event);	
//    							}.ctx(this));
//    							}.ctx(this));
//    	    			 
//    						$('#rejectTimeEntry'+data[i].id).click(function(event){	
//    							$("#rejectedComments").modal('show');
//    							$(".submitComments").click(function(){
//    								$("#rejectedComments").hide();
//    								this.rejectTimeEntry(event);			
//    							}.ctx(this));
//    							}.ctx(this));
//    	
    			}
    			
    			 $('.approveTimeEntry').click(function(event){
				    	$("#rejectedComments").modal('show');
				    	statusToApproveOrReject=1;
//				    	$(".submitComments").click(function(){
//							$("#rejectedComments").hide();
//							statusToApproveOrReject=1;
//							//this.approveTimeEntry(event);	
//						}.ctx(this));
						}.ctx(this));
 			 
					$('.rejectTimeEntry').click(function(event){	
						$("#rejectedComments").modal('show');
						statusToApproveOrReject=2;
//						$(".submitComments").click(function(){
//							if($(".comments").val()!=''){
//							$("#rejectedComments").hide();
//							this.rejectTimeEntry(event);}else{
//								$.ambiance({
//			        			    message : 'Mention the Comments.',
//			        			    type : 'error'
//			        			   });
//							}			
//						}.ctx(this));
						}.ctx(this));
    			
    			
    			
//    			
//    			 $('.approveTimeEntry').click(function(event){
//				    	$("#rejectedComments").modal('show');
//				    	$('#closeBtnForComments').click(function(event){
//				    		$(".submitComments").trigger("click");
//				    		}.ctx(this));
//				    	$(".submitComments").click(function(){
//							$("#rejectedComments").hide();
//							this.approveTimeEntry(event);	
//						}.ctx(this));
//						}.ctx(this));
//					$('.rejectTimeEntry').click(function(event){	
//						$("#rejectedComments").modal('show');
//						$(".submitComments").click(function(){
//							$("#rejectedComments").hide();
//							this.rejectTimeEntry(event);			
//						}.ctx(this));
//						 
//						
//						}.ctx(this));
    			}
    			else{
    				$.ambiance({
        			    message : 'No TimeEntries Found',
        			    type : 'error'
        			   });
    				$(".approverTableHeader").hide();
    			}
    		}else {
    			$.ambiance({
    			    message : data.message,
    			    type : 'error'
    			   });
    		      }
    	}.ctx(this));
}

ApproverSearch.prototype.approveTimeEntry=function(event){
	$("#rejectedComments").modal('hide');
	var timeEntryId=event.target.value;
	if($(".comments").val()==""){
		input={"payload":{"id":timeEntryId}};
	}
	else {
		input={"payload": {"id":timeEntryId,"approvedComments":$(".comments").val()} }
	}
	RequestManager.approve(input,function(data, success){
		if(success){
			if(data){
				$.ambiance({
				    message : "TimeEntry Approved.",
				    type : 'success'
				   });
			this.searchTimeEntriesByApprover();
			//$("#searchTimeEntriesByApprover").trigger("click");
			
			}else{
				$.ambiance({
				    message : "TimeEntry Not Approved.",
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
	}.ctx(this));
	
	
}
ApproverSearch.prototype.rejectTimeEntry=function(event){
	var timeEntryId=event.target.value;
	RequestManager.reject({ "payload": {"id":timeEntryId,"rejectedComments":$(".comments").val()} },function(data,success){
		if(success){
			if(data){
				$.ambiance({
				    message : "TimeEntry Rejected.",
				    type : 'success'
				   });
			this.searchTimeEntriesByApprover();
			}else{
				$.ambiance({
				    message : "Unable to reject.",
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
	}.ctx(this));
}

ApproverSearch.prototype.validateSearchCriteria=function(){
	  var fromDate=$('.from').val();
	  var toDate=$('.to').val();
	  var isvalid=true;
	  var dateRegex="^(0[1-9]|1[012])([-/])(0[1-9]|[12][0-9]|3[01])\\2([23]0)\\d\\d$";
	  var pattern=new RegExp(dateRegex);
	  if(fromDate!=null && fromDate!=''){
		  if(!pattern.test(fromDate)){
			  $.ambiance({
  			    message : 'Invalid Date(Format:mm/dd/yyyy).',
  			    type : 'error'
  			   });
				  isvalid=false;
			  }
	  }
	  if(toDate!=null && toDate!=''){
      if(!pattern.test(toDate)){
    	  $.ambiance({
			    message : 'Invalid Date(Format:mm/dd/yyyy).',
			    type : 'error'
			   });
		  isvalid=false;
	  }
      }
      if($('#to').val()!='' && $('#from').val()==''){
    	  $.ambiance({
			    message : 'Mention the From date.',
			    type : 'error'
			   });
  		  isvalid=false;
  	  }
	
	  return isvalid;
}
var statusToApproveOrReject;

