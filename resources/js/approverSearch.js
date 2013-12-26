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
	
	this.getProjects();
//	this.getUsers();
	$(".userProjectId").change(function(){
		this.getUsers();
	}.ctx(this));
	
    $(document).ready(function() {
        $(".from").datepicker();
        $(".to").datepicker();
    }.ctx(this));   
    
	this.searchTimeEntriesByApprover();
	
	$('#search').click(function(event){
		console.log("Search btn clicked");
		this.searchTimeEntriesByApprover();
		}.ctx(this));
	
	/*$('#approveTimeEntry').click(function(event){
		console.log("clickHappened");
		this.approveTimeEntry(event);
		}.ctx(this));
	
	$('#rejectTimeEntry').click(function(event){
		console.log("reject btn clicked");
		this.rejectTimeEntry(event);
		}.ctx(this));*/
    
    
}



ApproverSearch.prototype.getProjects=function(){
	 $('#userProjectId').empty();
	// $('#userProjectId').append('<option></option>');
	 RequestManager.getProjectsForUser({}, function(data, success) {
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
		 $('.userId').append('<option></option>');
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
     if(input==null){
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
    						$("#rejectedComments").show();
    						$(".submitComments").click(function(event){
    							var rejectedComments=$(".comments").val();
    							this.rejectTimeEntry(event);
    							$("#rejectedComments").hide();
    						}.ctx(this));
    						
    						
    						}.ctx(this));
    			}
    		}else {
    			alert(data.message);
    		      }
    	}.ctx(this));
}

ApproverSearch.prototype.approveTimeEntry=function(event){
	var id=parseInt(event.target.value);
	RequestManager.approve({"payload":{"id":id}},function(data, success){
		if(success){
			alert("approved");
		}
		else{
			alert(data.message);
		}	
	});
	
	
}
ApproverSearch.prototype.rejectTimeEntry=function(event){
	var id=parseInt(event.target.value);
	RequestManager.reject({ "payload": {"id":id,"rejectedComments":$(".comments").val()} },function(data,success){
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


