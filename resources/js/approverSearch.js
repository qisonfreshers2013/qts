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
        this.searchTimeEntriesByApprover();
    }.ctx(this));   
    
	
	
	$('#searchTimeEntriesByApprover').click(function(event){
		if(this.validateSearchCriteria()){
		this.searchTimeEntriesByApprover();}
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
	   alert(data.message);
	  }
	 }.ctx(this));
	}
ApproverSearch.prototype.getUsers=function(){
	var projectId;
	if($(".userProjectId").val()!=''){
		projectId=$(".userProjectId").val();
		$('.userId').empty();
		 $('.userId').append('<option>SELECT</option>');
		 RequestManager.getProjectUsers({"payload":{"projectId":projectId}}, function(data, success) {
		  if(success){
		   var id=0;
		   var name='';
		   $.each(data,function(key1,value1){
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
		   alert(data.message);
		  }
		 }.ctx(this));
	}else{
		alert("select Project");
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
	 if(searchCriteria.from!='' && searchCriteria.projectId!=''&& searchCriteria.status!=''&& searchCriteria.userId!='' && searchCriteria.to!='')
		input={ "payload": { "projectId":searchCriteria.projectId,"status":searchCriteria.status,"userId":searchCriteria.userId,"from":searchCriteria.from,"to":searchCriteria.to}}; 
	else if(searchCriteria.from=='' && searchCriteria.projectId==''&& searchCriteria.status!=''&& searchCriteria.userId!='' && searchCriteria.to=='')
		input={ "payload": {"status":searchCriteria.status,"userId":searchCriteria.userId}}; 
	else if(searchCriteria.from=='' && searchCriteria.projectId!=''&& searchCriteria.status!=''&& searchCriteria.userId=='' && searchCriteria.to=='')
		input={ "payload": { "projectId":searchCriteria.projectId,"status":searchCriteria.status}}; 
	else if(searchCriteria.from=='' && searchCriteria.projectId!=''&& searchCriteria.status!=''&& searchCriteria.userId!='' && searchCriteria.to=='')
		input={ "payload": { "projectId":searchCriteria.projectId,"status":searchCriteria.status,"userId":searchCriteria.userId}}; 
	else if(searchCriteria.from!='' && searchCriteria.projectId!=''&& searchCriteria.status!=''&& searchCriteria.userId!='' && searchCriteria.to=='')
		input={ "payload": { "projectId":searchCriteria.projectId,"status":searchCriteria.status,"userId":searchCriteria.userId,"from":searchCriteria.from}}; 
	else if(searchCriteria.from!='' && searchCriteria.projectId==''&& searchCriteria.status!=''&& searchCriteria.userId=='' && searchCriteria.to!='')
		input={ "payload": { "status":searchCriteria.status,"from":searchCriteria.from,"to":searchCriteria.to}}; 
	else if(searchCriteria.from=='' && searchCriteria.projectId==''&& searchCriteria.status!=''&& searchCriteria.userId=='' && searchCriteria.to=='')
		input={ "payload": {"status":searchCriteria.status}}; 

	return input;
}





ApproverSearch.prototype.searchTimeEntriesByApprover = function() {
	
     var input=this.getInputForSearchUserTimeEntriesByApprover();
     if(input={"payload":{"projectId":"SELECT"}}){
    	 input={"payload":{}};
     }
     RequestManager.searchTimeEntriesByApprover(input,function(data,success){
    		if(success){
    			$(".approverTableData").empty();
    			for(var i=0;i<data.length;i++){
    				 var tabledata="<tr class=\"approverTableData\">"+
    	                "<td>"+$.datepicker.formatDate('mm/dd/yy', new Date(data[i][1]))+"</td>"+
    	                "<td>"+data[i][3]+"</td>"+
    	                "<td>"+data[i][2]+"</td>"+
    	                "<td>"+data[i][4]+"</td>"+
    	                "<td>"+data[i][5]+"</td>"+
    	                "<td>"+data[i][6]+"</td>"+
    	                "<td>"+data[i][7]+"</td>"+
    	                "<td>"+data[i][8]+"</td>"+
    	                "<td><button class=\"approve approveTimeEntry\" id=\"approveTimeEntry\" value=\""+data[i][0]+"\">.</button><button class=\"reject rejectTimeEntry\" id=\"rejectTimeEntry\" value=\""+data[i][0]+"\">.</button></td>";
    				    $(".approverTableHeader").after(tabledata);
    				    $('#approveTimeEntry').click(function(event){
    						console.log("clickHappened");
    						this.approveTimeEntry(event);
    						}.ctx(this));
    					$('#rejectTimeEntry').click(function(event){
    						console.log("reject btn clicked");
    						$("#rejectedComments").modal('show');
    						$(".submitComments").click(function(){
    							$("#rejectedComments").hide();
    							this.rejectTimeEntry(event);			
    						}.ctx(this));
    						
    						
    						}.ctx(this));
    			}
    		}else {
    			alert(data.message);
    		      }
    	}.ctx(this));
}

ApproverSearch.prototype.approveTimeEntry=function(event){
	var timeEntryId=event.target.value;
	RequestManager.approve({"payload":{"id":timeEntryId}},function(data, success){
		if(success){
			alert("approved");
		}
		else{
			alert(data.message);
		}	
	});
	
	
}
ApproverSearch.prototype.rejectTimeEntry=function(event){
	var timeEntryId=event.target.value;
	RequestManager.reject({ "payload": {"id":timeEntryId,"rejectedComments":$(".comments").val()} },function(data,success){
		if(success){
			if(data){
			alert("rejected");
			}else{
				alert("not Rejected");
			}
		}
		else{
			alert(data.message);
		}	
	});
}

ApproverSearch.prototype.validateSearchCriteria=function(){
	  var fromDate=$('.from').val();
	  var toDate=$('.to').val();
	  var isvalid=true;
	  var dateRegex="^(0[1-9]|1[012])([-/])(0[1-9]|[12][0-9]|3[01])\\2([23]0)\\d\\d$";
	  var pattern=new RegExp(dateRegex);
	  if(fromDate!=null && fromDate!=''){
		  if(!pattern.test(fromDate)){
				alert("Invalid Date(Format:mm/dd/yyyy).");
				  isvalid=false;
			  }
	  }
	  if(toDate!=null && toDate!=''){
      if(!pattern.test(toDate)){
		alert("Invalid Date(Format:mm/dd/yyyy).");
		  isvalid=false;
	  }
	  }
	  else if($('.status').val()==''){
		  alert("specify the status");
	      isvalid=false;
	  }
	  return isvalid;
}


