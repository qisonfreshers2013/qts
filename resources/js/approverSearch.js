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
		$("#rejectedComments").modal('hide');
		if(statusToApproveOrReject==1 && this.validateComments()){
		this.approveTimeEntry();
		}else{
			if(statusToApproveOrReject==2 && this.validateComments()){
			if($(".comments").val()!=''){
				$("#rejectedComments").modal('hide');
				this.rejectTimeEntry();}else{
					$("#rejectedComments").modal('show');
					$.ambiance({
        			    message : 'Mention the Comments.',
        			    type : 'error'
        			   });
				}	
		}}
	}.ctx(this));
	
	$('#clearTheComments').click(function(event){
	    $('#comments').val('');
	}.ctx(this));
	
	
  	$('#closeBtnForComments').click(function(event){
  		 document.body.style.overflow = "visible";
  		 $('#searchCriteria').find('*').prop('disabled',false);
		}.ctx(this));
  	
  	$("#rejectedComments").keyup(function (event) {
  	  if (event.keyCode == 13) {
  	   $('.submitComments').trigger('click');
  	  }
  	 }.ctx(this));
  	
 	$(document.documentElement).keyup(function (event) {
  	  if (event.keyCode == 27) {
  		  document.body.style.overflow = "visible";
  		 $('#searchCriteria').find('*').prop('disabled',false);
  	  }
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
	    $('#userProjectId').append('<option title='+name+' value='+id+'>'+name.ellipses(15)+'</option>');
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
		    $('.userId').append('<option value='+id+' title='+name+'>'+name.ellipses(15)+'</option>');
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
		 if(searchCriteria.status!=0 && searchCriteria.from =='' && searchCriteria.projectId=='SELECT' && searchCriteria.userId=='SELECT'){
		 dataToSend=dataToSend+'"status":'+searchCriteria.status;
		 }else{
			 if(searchCriteria.status!=0)
			 dataToSend=dataToSend+',"status":'+searchCriteria.status;
		 }
	 }
	    dataToSend=dataToSend+'}}';
	
	
	 var data=$.parseJSON(dataToSend);
	 input=data;
	 
	return input;
}





ApproverSearch.prototype.searchTimeEntriesByApprover = function() {
	
	var input=this.getInputForSearchUserTimeEntriesByApprover();
     RequestManager.searchTimeEntriesByApprover(input,function(data,success){
    		if(success){
    			var status;
    			var operations;	
    			$(".approverTableData").empty();
    			var oneWeekAgo=new Date();
    			oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);
    			 $('#to').val($.datepicker.formatDate('mm/dd/yy', new Date()));
    			 $('#from').val($.datepicker.formatDate('mm/dd/yy',oneWeekAgo));
    			 $('#status').val(1);
    			if(data.length!=0){
    			$(".approverTableHeader").show();
    			data=data.sort(function(a, b){
   		         if (a.dateInLong == b.dateInLong) {
   		             return 0;
   		         } else if(a.dateInLong > b.dateInLong) {
   		             return 1;
   		         }
   		         return -1;
   		          });
    			for(var i=0;i<data.length;i++){
    				var workedMinutes=data[i].minutes%60;
    				var workedHours=data[i].minutes/60;
    				var workedHoursInInteger=parseInt(workedHours);
   				 if(data[i].status==1){
					status="Pending";
					operations="";
					if(data[i].userRemarks!=null && data[i].userRemarks!='')
					operations=operations+"<img  class=\"userRemarks\" src=\"resources/img/userRemarks.png\" title=\""+data[i].userRemarks+"\">";
					operations=operations+"<button class=\"approve approveTimeEntry\" id=\"approveTimeEntry"+data[i].id+"\" value=\""+data[i].id+"\">.</button><button class=\"reject rejectTimeEntry\" id=\"rejectTimeEntry"+data[i].id+"\" value=\""+data[i].id+"\">.</button>";
   				 }
				else if(data[i].status==2){
					status="Approved";
					operations="";
					if(data[i].userRemarks!=null && data[i].userRemarks!=""){
					operations=operations+"<img  class=\"userRemarks\" src=\"resources/img/userRemarks.png\" title=\""+data[i].userRemarks+"\">";}
					if(data[i].approvedComments!=null && data[i].approvedComments!=""){
					operations=operations+"<img  class=\"userRemarks\" src=\"resources/img/approvedComments.png\" title=\""+data[i].approvedComments+"\">";
						}
				}
				else if(data[i].status==3){
					status="Rejected";
					 operations="<img  class=\"userRemarks\" src=\"resources/img/rejectedComments.png\" title=\""+data[i].rejectedComments+"\">";
					if(data[i].userRemarks!=null && data[i].userRemarks!=""){
					operations=operations+"<img  class=\"userRemarks\" src=\"resources/img/userRemarks.png\" title=\""+data[i].userRemarks+"\">";}
				       
				}
				
    				var tabledata="<tr class=\"approverTableData\">"+
                    "<td>"+$.datepicker.formatDate('mm/dd/yy', new Date(data[i].dateInLong))+"</td>"+
                    "<td title='"+data[i].projectName+"'>"+(data[i].projectName.charAt(0).toUpperCase()+data[i].projectName.substr(1).toLowerCase()).ellipses(10)+"</td>"+
                    "<td title='"+data[i].userName+"'>"+(data[i].userName.charAt(0).toUpperCase()+data[i].userName.substr(1).toLowerCase()).ellipses(10)+"</td>"+
                    "<td title='"+data[i].releaseVersion+"'>"+data[i].releaseVersion.ellipses(10)+"</td>"+
                    "<td title='"+data[i].task+"'>"+(data[i].task.charAt(0).toUpperCase()+data[i].task.substr(1).toLowerCase()).ellipses(10)+"</td>"+
                    "<td title='"+data[i].activity+"'>"+(data[i].activity.charAt(0).toUpperCase()+data[i].activity.substr(1).toLowerCase()).ellipses(10)+"</td>"+
                    "<td>"+workedHoursInInteger+":"+workedMinutes+"</td>"+
                    "<td>"+status+"</td>"+
                    "<td>"+operations+"</td>";
                    $(".approverTableHeader").after(tabledata);	
    			}
    			 $('.approveTimeEntry').click(function(event){
    				 document.body.style.overflow = "hidden";
    				 $('#searchCriteria').find('*').prop('disabled',true);
    				 $("#rejectedComments").modal({
							backdrop:"static"
						});
    				   $("#clearTheComments").trigger("click");
				    	$("#rejectedComments").modal('show');
				    	statusToApproveOrReject=1;
				    	timeEntryIdToApproveOrReject=event.target.value;
						}.ctx(this));
 			 
					$('.rejectTimeEntry').click(function(event){
						document.body.style.overflow = "hidden";
						$('#searchCriteria').find('*').prop('disabled',true);
						$("#rejectedComments").modal({
							backdrop:"static"
						});
						$("#clearTheComments").trigger("click");
						$("#rejectedComments").modal('show');
						statusToApproveOrReject=2;
						timeEntryIdToApproveOrReject=event.target.value;
						}.ctx(this));
    			
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

ApproverSearch.prototype.approveTimeEntry=function(){
	$("#rejectedComments").modal('hide');
	var timeEntryId=timeEntryIdToApproveOrReject;
	if($(".comments").val()==""){
		input={"payload":{"id":timeEntryId}};
	}
	else {
		input={"payload": {"id":timeEntryId,"approvedComments":$(".comments").val()} }
	}
	RequestManager.approve(input,function(data, success){
		if(success){
			document.body.style.overflow = "visible";
	  		 $('#searchCriteria').find('*').prop('disabled',false);
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
			document.body.style.overflow = "visible";
	  		 $('#searchCriteria').find('*').prop('disabled',false);
			$.ambiance({
			    message : data.message,
			    type : 'error'
			   });
		}	
	}.ctx(this));
	
	
}
ApproverSearch.prototype.rejectTimeEntry=function(){
	var timeEntryId=timeEntryIdToApproveOrReject;
	RequestManager.reject({ "payload": {"id":timeEntryId,"rejectedComments":$(".comments").val()} },function(data,success){
		if(success){
			 document.body.style.overflow = "visible";
	  		 $('#searchCriteria').find('*').prop('disabled',false);
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
			 document.body.style.overflow = "visible";
	  		 $('#searchCriteria').find('*').prop('disabled',false);
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
ApproverSearch.prototype.validateComments=function(){
    var comments=$('#comments').val();
    var isvalid=true;
	 if(comments.length>128){
		  $.ambiance({
			    message :'Maximum of 128 characters is supported.',
			    type : 'error'
			   });
		  isvalid=false;
	  }
	 return isvalid;
}
var statusToApproveOrReject;
var timeEntryIdToApproveOrReject;

