/**
 * Ajay
 */
function DefaultTimeSheetPage(){
	Loader.loadHTML('#container', 'defaultTimeEntryPage.html',false, function() {
		this.searchUserTimeEntries();
        this.getProjects();
		this.handleShow();
	}.ctx(this))
}
DefaultTimeSheetPage.prototype.handleShow=function(){
	$(".container").show();
	$("#ambiance-notification").empty();
	this.loadTimeSheetFilling();
	  
    $(document).ready(function() {
        $(".searchByDate").datepicker({maxDate:new Date()});  
       
    });
  
    $("#calendarIcon").click(function(){$(".searchByDate").focus();}.ctx(this));
    
    
	//To Add A New TimeEntrySheet
	$('.addTimeEntry').click(function(){
		 $('.selectRelease').empty();
		 $('.savedTimeSheetsEntries').find('*').prop('disabled',true);
		 $('.selectRelease').append('<option>SELECT</option>');
		 $('#searchByDate').val('');
		var selectedCheckBox=$("input[type=checkbox]:checked").length;
		if(selectedCheckBox!=0){
			$(":checkbox").each(function(){
				if(this.checked==true){
					this.checked=false;}
				});
			}
		$("#clearTheFields").trigger("click");
		this.add();
	}.ctx(this));

	$('.deleteTimeEntry').click(function(){
		this.deleteTimeEntry();
		}.ctx(this));

	
	
	
	//To Edit an Existing TimeEntry
	$('.editTimeEntry').click(function(){
		this.editTimeEntry();
	}.ctx(this));
	
	//To submit multipleTimeEntries
	$('.submitTimeEntries').click(function(){
		//this.submitTimeEntries();
		this.submitTimeEntries();
	}.ctx(this));
	
	
	
	//Select All Functionality
	$('#selectAll').click(function(){
		this.selectAllCheckBoxes();
	}.ctx(this));
	
	$('.searchUserTimeEntries').click(function(){
		this.searchUserTimeEntries();
		}.ctx(this));
	
	$(".searchByProjectId,.searchByDate").keyup(function (event) {
  	  if (event.keyCode == 13) {
  	   $('.searchUserTimeEntries').trigger('click');
  	  }
  	 }.ctx(this));
	
}

DefaultTimeSheetPage.prototype.loadTimeSheetFilling=function(){
	$( "#loadTimeSheetFilling" ).hide();
	new TimeEntry();
}


DefaultTimeSheetPage.prototype.add = function() {
	document.body.style.overflow = "hidden";
	$('#loadTimeSheetFilling').modal({
		backdrop:"static"
		});
	
	  $( "#loadTimeSheetFilling" ).modal('show');
	 }

DefaultTimeSheetPage.prototype.deleteTimeEntry=function(){
	var selectedCheckBox=$("input[type=checkbox]:checked#checkboxForTableData").length;
	if(selectedCheckBox!=1){
		$.ambiance({
		    message : 'Select One Time Entry to Delete.',
		    type : 'error'
		   });
	}else{ 
	    	var id=$("input[type=checkbox]:checked#checkboxForTableData").val();
		 RequestManager.deleteTimeEntry({"payload":{"id":id}},function(data,success){
			if(success){
				if(data){
				$.ambiance({
    			    message : 'Deleted',
    			    type : 'success'
    			   });
				$("input[type=checkbox][value="+id+"]").empty();
				$(".searchUserTimeEntries").trigger("click");}else{
					$.ambiance({
	    			    message : 'Not Deleted',
	    			    type : 'error'
	    			   });
				}
			}
			else{
				$.ambiance({
    			    message :'TimeEntry Cannot Be Deleted.',
    			    type : 'error'
    			   });
			}
		 });
	         }
}
DefaultTimeSheetPage.prototype.submitTimeEntries=function(){
	var selectedCheckBox=$("input[type=checkbox]:checked").length;
	if(selectedCheckBox==0){
		$.ambiance({
		    message : 'Select  Time Entries to Submit.',
		    type : 'error'
		   });
	}else{
		var idOfTimeEntries=new Array();
		$(":checkbox").each(function(){
			if(this.checked==true){
				if($(this).val()!='on'){
				idOfTimeEntries.push($(this).val());}
			}
		});
		
		var ids=new Array();
		for(var i=0;i<idOfTimeEntries.length;i++){
			 ids.push({"id":idOfTimeEntries[i]});
		}
		if(ids.length==0){
			$.ambiance({
			    message : 'No Time Entries to Submit.',
			    type : 'error'
			   });
			if($("#selectAll").is('input[type=checkbox]:checked')){
				$("#selectAll").prop("checked",false);
			}
		}else{
		var input={"payload":{"timeEntries":ids}};
	RequestManager.submit(input,function(data,success){
		if(success){
		if(data){
			$.ambiance({
			    message : 'Submitted.',
			    type : 'success'
			   });
			$(".searchUserTimeEntries").trigger("click");			
		}
		else{
			$.ambiance({
			    message : 'Not Submitted',
			    type : 'error'
			   });
			}
		}else{
			$.ambiance({
			    message : data.message,
			    type : 'error'
			   });
		}
	});
	}
	}
}





DefaultTimeSheetPage.prototype.populateFields=function(id){
	RequestManager.getTimeEntryObjectById({"payload":id},function(data,success){
		if(success){
			var releaseId=data.releaseId;
			$('.datepicker').val($.datepicker.formatDate('mm/dd/yy', new Date(data.dateInLong)));
			$('.projectId').val(data.projectId);
			$('.task').val(data.task);
			$('.hours').val(parseInt(data.minutes/60));
			$('.minutes').val(parseInt(data.minutes%60));
			this.getReleases(data.releaseId);
			$('.selectActivity').val(data.activityId);
			$('.userRemarksInModal').val(data.userRemarks);
		}else{
			$.ambiance({
			    message : data.message,
			    type : 'error'
			   });
		}
	}.ctx(this));
}

DefaultTimeSheetPage.prototype.getRequestParameters=function(id){
	var requestParameters={"date":$('.datepicker').val(),
			               "projectId":$('.projectId').val(),
			               "task":$('.task').val(),
			               "hours":$('.hours').val(),
			               "minutes":$('.minutes').val(),
			               "activityId":$('.selectActivity').val(),
			               "releaseId":$('.selectRelease').val(),
			               "userRemarks":$('.userRemarksInModal').val()
			               };
	
      return requestParameters;
} 


DefaultTimeSheetPage.prototype.editTimeEntry=function(){
	var selectedCheckBox=$("input[type=checkbox]:checked#checkboxForTableData").length;
	if(selectedCheckBox!=1){
		$.ambiance({
		    message : 'Select One Time Entry to Edit.',
		    type : 'error'
		   });
	}else{
		 var id=$("input[type=checkbox]:checked#checkboxForTableData").val();
		 this.populateFields(id);
		 $( "#loadTimeSheetFilling" ).modal('show');
			}
	 }
 	

DefaultTimeSheetPage.prototype.selectAllCheckBoxes=function(){      
	if($("#selectAll").is('input[type=checkbox]:checked'))
		$("input:checkbox").each(function(){ this.checked=true;});
	else
		$("input:checkbox").each(function(){ this.checked=false;});
}

DefaultTimeSheetPage.prototype.getInputForSearchUserTimeEntries=function(){
	var input;
	if($(".searchByDate").val()!='' && $(".searchByProjectId").val()!=null && $(".searchByProjectId").val()!="SELECT"){
	input={"payload":{"date":$(".searchByDate").val(),
		               "projectId": $(".searchByProjectId").val()}};
	}
	else if($(".searchByDate").val()!='' && $(".searchByProjectId").val()=="SELECT"){
		input={"payload":{"date":$(".searchByDate").val()}};}
	else if($(".searchByDate").val()=='' && $(".searchByProjectId").val()!=null && $(".searchByProjectId").val()!="SELECT"){
		input={"payload":{"projectId": $(".searchByProjectId").val()}};}
	else if($(".searchByDate").val()=='' && $(".searchByProjectId").val()!=null && $(".searchByProjectId").val()=="SELECT"){
		input={"payload":{}};}
	
     return input;	
}


DefaultTimeSheetPage.prototype.searchUserTimeEntries=function(){
	var input=this.getInputForSearchUserTimeEntries();
	if(input==null || input=={"payload":{"projectId":"SELECT"}}){
		input={"payload":{}}
	}
	RequestManager.searchTimeEntriesByUser(input,function(data,success){
		if(success){
			$("#selectAll").attr("checked",false);
			if(data.length!=0){
			var count=0;
			var status;
			var remarks;
			var checkbox;
			$("#tableheader").show();
			$("#editTimeEntry").show();
			$("#deleteTimeEntry").show();
			$("#submitTimeEntries").show();
			$(".userTableData").empty();
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
				if(data[i].status==0){
					 status="Saved";
					 remarks="";
					 checkbox="<input type=\"checkbox\" id=\"checkboxForTableData\" class=\"checkboxForTableData\" value="+data[i].id+"></input>";
					if(data[i].userRemarks!=null && data[i].userRemarks!='' ){
					 remarks="<img  class=\"userRemarks\" src=\"resources/img/userRemarks.png\" title=\""+data[i].userRemarks+"\">";}
				      count++;
				}
				else if(data[i].status==1){
					status="Submitted";
					checkbox='';
                    remarks="";
					if(data[i].userRemarks!=null && data[i].userRemarks!='')
					remarks=remarks+"<img  class=\"userRemarks\" src=\"resources/img/userRemarks.png\" title=\""+data[i].userRemarks+"\">";
				}
				else if(data[i].status==2){
					status="Approved";
					checkbox='';
					 remarks="";
					if(data[i].userRemarks!=null && data[i].userRemarks!=""){
					remarks=remarks+"<img  class=\"userRemarks\" src=\"resources/img/userRemarks.png\" title=\""+data[i].userRemarks+"\">";}
					if(data[i].approvedComments!=null && data[i].approvedComments!=""){
			           remarks=remarks+"<img  class=\"userRemarks\" src=\"resources/img/approvedComments.png\" title=\""+data[i].approvedComments+"\">";
						}
				}
				else if(data[i].status==3){
					status="Rejected";
					checkbox="<input type=\"checkbox\" id=\"checkboxForTableData\" class=\"checkboxForTableData\" value="+data[i].id+"></input>";
					 remarks="<img  class=\"userRemarks\" src=\"resources/img/rejectedComments.png\" title=\""+data[i].rejectedComments+"\">";
					if(data[i].userRemarks!=null && data[i].userRemarks!=""){
					remarks=remarks+"<img  class=\"userRemarks\" src=\"resources/img/userRemarks.png\" title=\""+data[i].userRemarks+"\">";}
				   count++;     
				}
				
				 var tabledata="<tr class=\"userTableData\" id=\"userTableData\">" +
				    "<td>"+checkbox+"</td>"+
	                "<td>"+$.datepicker.formatDate('mm/dd/yy', new Date(data[i].dateInLong))+"</td>"+
	                "<td title='"+data[i].projectName+"'>"+(data[i].projectName.charAt(0).toUpperCase()+data[i].projectName.substr(1).toLowerCase()).ellipses(10)+"</td>"+
	                "<td title='"+data[i].releaseVersion+"'>"+data[i].releaseVersion.ellipses(10)+"</td>"+
	                "<td title='"+data[i].task+"'>"+(data[i].task.charAt(0).toUpperCase()+data[i].task.substr(1).toLowerCase()).ellipses(10)+"</td>"+
	                "<td title='"+data[i].activity+"'>"+(data[i].activity.charAt(0).toUpperCase()+data[i].activity.substr(1).toLowerCase()).ellipses(10)+"</td>"+
	                "<td>"+workedHoursInInteger+":"+workedMinutes+"</td>"+
	                "<td value=\""+data[i].status+"\">"+status+"</td>"+
	                "<td>"+remarks+"</td>" +
	                "</tr>";
				    $("#tableheader").after(tabledata);
				    $("#userTableData").css({"border-spacing" : "0",
				         "font-size" : "16px",
				          "padding": "1%",
				          "text-align": "center"
				        });
				    

					$('#checkboxForTableData').change(function(event){
					    if($(event.target).prop("checked")){
					    	var n=$('input[type="checkbox"]:checked').length;
							if(count==n)
								$("#selectAll").attr("checked",true);
					    }
					    if(!($(event.target).prop("checked"))){
					    	$("#selectAll").attr("checked",false);
					    }
						}.ctx(this));	    
			}
			//$('#searchByDate').val($.datepicker.formatDate('mm/dd/yy', new Date(data[0].dateInLong)));
			}else{
   				$.ambiance({
    			    message : 'No TimeEntries Found',
    			    type : 'error'
    			   });
				
			     }
		}else {
			$(".userTableData").empty();
			$("#tableheader").hide();
			$("#editTimeEntry").hide();
			$("#deleteTimeEntry").hide();
			$("#submitTimeEntries").hide();
			$.ambiance({
			    message : data.message,
			    type : 'error'
			   });
		      }
	}.ctx(this));
}

DefaultTimeSheetPage.prototype.getProjects=function(){
	 $('#searchByProjectId').empty();
	 $('#searchByProjectId').append('<option>SELECT</option>');
	
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
	    
	    $('#searchByProjectId').append('<option value='+id+' title='+name+'>'+name.ellipses(15)+'</option>');
	   });
	  }else{
		  $.ambiance({
			    message : data.message,
			    type : 'error'
			   });
	  }
	 }.ctx(this));
	}
DefaultTimeSheetPage.prototype.getReleases=function(releaseIdToGetReleaseVersion){
	 $('.selectRelease').empty();
	 $('.selectRelease').append('<option>SELECT</option>');
	 
	 var id=$(".projectId").val();
	 if(id!='SELECT'){
	 RequestManager.getProjectReleases({"payload":{"projectId":id}}, function(data, success) {
	  if(success){
		  if(data.length!=0){
	for(var i=0;i<data.length;i++){
		 $('.selectRelease').append('<option class=\"releaseValue\" value='+parseInt(data[i][0])+' title='+data[i][1]+'>'+data[i][1].ellipses(15)+'</option>');
	          }
	   $('#selectRelease option[value="'+releaseIdToGetReleaseVersion+'"]').attr("selected",true);
		  }
		  else {  $.ambiance({
			    message :'No Releases For this Project',
			    type : 'error'
			   });}
	  }else{ 
		  $.ambiance({
		    message : data.message,
		    type : 'error'
		   });
	   $("cancel").trigger("click");
	  }
	 }.ctx(this));}
	}

DefaultTimeSheetPage.prototype.validateTimeEntry=function(){
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
	  return isvalid;
}





var DefaultTimeSheetPage=new DefaultTimeSheetPage();