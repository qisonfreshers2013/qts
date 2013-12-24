/**
 * Ajay
 */
function DefaultApproverPage(){
	Loader.loadHTML('#container', 'defaultApproverpage.html', true, function() {
		this.loadApproverSearch();
		this.handleShow();
		
	}.ctx(this))
}
DefaultApproverPage.prototype.handleShow=function(){
    
	this.getProjects();
	this.searchTimeEntriesByApprover();
	
	$('.searchTimeEntriesByApprover').click(function(){
		this.searchTimeEntriesByApprover();
		}.ctx(this));
	
	$('.approveTimeEntries').click(function(){
		this.approveTimeEntry();
		}.ctx(this));
	
	$('.rejectTimeEntries').click(function(){
		this.rejectTimeEntry();
		}.ctx(this));

}
DefaultApproverPage.prototype.loadApproverSearch=function(){
	new ApproverSearch();
}


DefaultApproverPage.prototype.getSearchCriteria=function(){
	
	var searchCriteria={"userId":$(".userId").val(),
			            "projectId":$(".projectId").val(),
			             "status":$(".status").val(),
			             "from":$(".from").val(),
			              "to":$(".to").val()};
	
	return searchCriteria;
	
	
	
}
DefaultApproverPage.prototype.getInputForSearchUserTimeEntriesByApprover=function(){
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





DefaultApproverPage.prototype.searchTimeEntriesByApprover = function() {
	
     var input=this.getInputForSearchUserTimeEntriesByApprover();
     if(input==null){
    	 input={"payload":{}};
     }
     RequestManager.searchTimeEntriesByApprover(input,function(data,success){
    		if(success){
    			$(".approverTableData").empty();
    			for(var i=0;i<data.length;i++){
    				 var tabledata="<tr>"+
    	                "<td>"+$.datepicker.formatDate('mm/dd/yy', new Date(data[i][1]))+"</td>"+
    	                "<td>"+data[i][3]+"</td>"+
    	                "<td>"+data[i][2]+"</td>"+
    	                "<td>"+data[i][4]+"</td>"+
    	                "<td>"+data[i][5]+"</td>"+
    	                "<td>"+data[i][6]+"</td>"+
    	                "<td>"+data[i][7]+"</td>"+
    	                "<td>"+data[i][8]+"</td>"+
    	                "<td><button class=\"approve approveTimeEntries\" value=\""+data[i][0]+"\">.</button><button class=\"reject rejectTimeEntries\" value=\""+data[i][0]+"\">.</button></td>";
    				    $(".approverTableHeader").after(tabledata);
    			}
    		}else {
    			alert(data.message);
    		      }
    	}.ctx(this));
}

DefaultApproverPage.prototype.approveTimeEntry=function(){
	RequestManager.approve({"payload":{"id":$(".approveTimeEntries").val()}},function(){
		if(success){
			alert("approved");
		}
		else{
			alert(data.message);
		}	
	});
	
	
}
DefaultApproverPage.prototype.rejectTimeEntry=function(){
	RequestManager.reject({"payload":{"id":$(".rejectTimeEntries").val()}},function(){
		if(success){
			alert("rejected");
		}
		else{
			alert(data.message);
		}	
	});
}
DefaultApproverPage.prototype.getProjects=function(){
	 $('#projectName').empty();
	 $('#projectName').append('<option></option>');
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

var DefaultApproverPage=new DefaultApproverPage();