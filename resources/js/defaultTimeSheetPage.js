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
	this.loadTimeSheetFilling();
	  
    $(document).ready(function() {
        $(".searchByDate").datepicker({maxDate:new Date()});  
    });
  
	//To Add A New TimeEntrySheet
	$('.addTimeEntry').click(function(){
		$("cancel").trigger("click");
		this.add();
	}.ctx(this));

	$('.deleteTimeEntry').click(function(){
		this.deleteTimeEntry();
		}.ctx(this));
	
	$("input:checkbox").change(function(){
		if(this.checked==false){
			$("#selectAll").checked=false;
			}
		});
	
	
	
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
	
	

}

DefaultTimeSheetPage.prototype.loadTimeSheetFilling=function(){
	$( "#loadTimeSheetFilling" ).hide();
	new TimeEntry();
}


DefaultTimeSheetPage.prototype.add = function() {
	$('#loadTimeSheetFilling').modal({
		backdrop:"static"
		});
	  $( "#loadTimeSheetFilling" ).modal('show');
	 }

DefaultTimeSheetPage.prototype.deleteTimeEntry=function(){
	var selectedCheckBox=$("input[type=checkbox]:checked").length;
	if(selectedCheckBox!=1){
		alert("select 1 timeEntry to Delete ");
	}else if($("input[type=checkbox]:checked").val()=='on'){
		 alert("Select All CheckBox should be disabled");}
	    else{
	    	var id=$("input[type=checkbox]:checked").val();
		 RequestManager.deleteTimeEntry({"payload":{"id":id}},function(data,success){
			if(success){
				alert("Deleted");
				$("input[type=checkbox][value="+id+"]").empty();
				$(".searchUserTimeEntries").trigger("click");
			}
			else{
				alert(data.message);
			}
		 });
	        }
	}

DefaultTimeSheetPage.prototype.submitTimeEntries=function(){
	var selectedCheckBox=$("input[type=checkbox]:checked").length;
	if(selectedCheckBox==0){
		alert("select  TimeEntries to Submit ");
	}else{
		var idOfTimeEntries=new Array();
		$(":checkbox").each(function(){
			if(this.checked==true){
				if($(this).val()!='on'){
				idOfTimeEntries.push($(this).val());}
			}
		});
		alert(idOfTimeEntries);
		var ids=new Array();
		for(var i=0;i<idOfTimeEntries.length;i++){
			 ids.push({"id":idOfTimeEntries[i]});
		}
		var input={"payload":{"timeEntries":ids}};
	RequestManager.submit(input,function(data,success){
		if(success){
		if(data){
			alert("Submitted");
			$(".searchUserTimeEntries").trigger("click");
		}
		else{
			alert("Not Submitted");
			}
		}else{
			alert(data.message)
		}
	});
	this.searchUserTimeEntries();
	}
}





DefaultTimeSheetPage.prototype.populateFields=function(id){
	RequestManager.getTimeEntryObjectById({"payload":id},function(data,success){
		if(success){
			alert(data.date);
			$('.datepicker').val($.datepicker.formatDate('mm/dd/yy', new Date(data.date)));
			$('.projectId').val(data.projectId);
			$('.task').val(data.task);
			$('.hours').val(data.hours);
			$('.selectActivity').val(data.activityId);
			$('.SelectRelease').val(data.releaseId);
			$('.userRemarks').val(data.remarks);
		}else{
			alert(data.message);
		}
	});
}

DefaultTimeSheetPage.prototype.getRequestParameters=function(id){
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

DefaultTimeSheetPage.prototype.getInputForUpdate=function(id){
	
	 var timeSheetFillingInput=this.getRequestParameters();
	 var input= { "payload":{"id":id,
		                     "projectId":timeSheetFillingInput.projectId,
		                     "releaseId":timeSheetFillingInput.releaseId,
		                     "activityId":timeSheetFillingInput.activityId,
		                     "date":timeSheetFillingInput.date,
		                     "task":timeSheetFillingInput.task,
		                     "hours":timeSheetFillingInput.hours,
		                     "userRemarks":timeSheetFillingInput.userRemarks
		                     } 
	             }
	return input;
	
}
DefaultTimeSheetPage.prototype.editTimeEntry=function(){
	var selectedCheckBox=$("input[type=checkbox]:checked").length;
	if(selectedCheckBox!=1){
		alert("select 1 timeEntry to edit ");
	}else if($("input[type=checkbox]:checked").val()=='on'){
		 alert("SelectAll CheckBox should be disabled");}
	 else{
		 var id=$("input[type=checkbox]:checked").val();
		 this.populateFields(id);
		 var timeSheetFillingInput=this.getRequestParameters();
		 var input={ "payload":{"id":id,
             "projectId":$('.projectId').val(),
             "releaseId":$('.SelectRelease').val(),
             "activityId":$('.selectActivity').val(),
             "date":$('.datepicker').val(),
             "task":$('.task').val(),
             "hours":$('.hours').val(),
             "userRemarks":$('.userRemarks').val(),
             "status":0
             } 
          }
		 $( "#loadTimeSheetFilling" ).modal('show');
			$('.save').click(function(event){
				if(this.validateTimeEntry()){
				RequestManager.updateTimeEntry(input,function(data,success){
					if(success){
						if(data){
						alert("Updated");
						$(".cancel").trigger("click");
						$(".searchUserTimeEntries").trigger("click");
						}else{
							alert("Not Updated");
						}
					}
					else{
						alert(data.message);
					}
				});
				}
			}.ctx(this));
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
			if(data.length!=0){
			var status;
			var remarks;
			var checkbox;
			$(".userTableData").empty();
			for(var i=0;i<data.length;i++){
				if(data[i].status==0){
					 status="SAVED";
					 remarks="";
					 checkbox="<input type=\"checkbox\" id=\"checkboxForTableData\" class=\"checkboxForTableData\" value="+data[i].id+"></input>";
					if(data[i].userRemarks!=null && data[i].userRemarks!='' ){
					 remarks="<img  class=\"userRemarks\" src=\"resources/img/userRemarks.png\" title=\""+data[i].userRemarks+"\">";}
				 
				}
				else if(data[i].status==1){
					status="SUBMITTED";
					checkbox='';
                    remarks="";
					if(data[i].userRemarks!=null && data[i].userRemarks!='')
					remarks=remarks+"<img  class=\"userRemarks\" src=\"resources/img/userRemarks.png\" title=\""+data[i].userRemarks+"\">";
				}
				else if(data[i].status==2){
					status="APPROVED";
					checkbox='';
					 remarks="";
					if(data[i].userRemarks!=null){
					remarks=remarks+"<img  class=\"userRemarks\" src=\"resources/img/userRemarks.png\" title=\""+data[i].userRemarks+"\">";
					if(data[i].approvedComments!=null)
			           remarks=remarks+"<img  class=\"userRemarks\" src=\"resources/img/approvedComments.png\" title=\""+data[i].approvedComments+"\">";
						}
				}
				else if(data[i].status==3){
					status="REJECTED";
					checkbox="<input type=\"checkbox\" id=\"checkboxForTableData\" class=\"checkboxForTableData\" value="+data[i].id+"></input>";
					 remarks="<img  class=\"userRemarks\" src=\"resources/img/rejectedComments.png\" title=\""+data[i].rejectedComments+"\">";
					if(data[i].userRemarks!=null){
					remarks=remarks+"<img  class=\"userRemarks\" src=\"resources/img/userRemarks.png\" title=\""+data[i].userRemarks+"\">";}
				      }
				
				 var tabledata="<tr class=\"userTableData\">" +
				    "<td>"+checkbox+"</td>"+
	                "<td>"+$.datepicker.formatDate('mm/dd/yy', new Date(data[i].dateInLong))+"</td>"+
	                "<td>"+data[i].projectName+"</td>"+
	                "<td>"+data[i].releaseVersion+"</td>"+
	                "<td>"+data[i].task+"</td>"+
	                "<td>"+data[i].activity+"</td>"+
	                "<td>"+data[i].hours+"</td>"+
	                 "<td>"+status+"</td>"+
	                "<td>"+remarks+"</td>" +
	                "</tr>";
				    $("#tableheader").after(tabledata);
			}
			}else{
				alert("No TimeEntries Found");
				$(".userTableData").empty();
			     }
		}else {
			     alert(data.message);
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
	    $('#searchByProjectId').append('<option value='+id+'>'+name+'</option>');
	   });
	  }else{
	   alert(data.message);
	  }
	 }.ctx(this));
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