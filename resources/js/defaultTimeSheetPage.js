/**
 * Ajay
 */
function DefaultTimeSheetPage(){
	Loader.loadHTML('#container', 'defaultTimeEntryPage.html',true, function() {
		this.handleShow();
	}.ctx(this))
}
DefaultTimeSheetPage.prototype.handleShow=function(){
	$(".container").show();
	this.loadTimeSheetFilling();
    $(document).ready(function() {
        console.log("on ready")
        $(".searchByDate").datepicker({maxDate:new Date()});
    });
    this.getProjects();
	this.searchUserTimeEntries();
	//To Add A New TimeEntrySheet
	$('.addTimeEntry').click(function(){
		$("cancel").trigger("click");
		this.add();
	}.ctx(this));

	$('.deleteTimeEntry').click(function(){
		this.deleteTimeEntry();
		}.ctx(this));
	
	$("input:checkbox").click(function(){
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
				idOfTimeEntries.push($(this).val());
			}
		});
		alert(idOfTimeEntries);
		for(var i=0;i<idOfTimeEntries.length;i++){
			 ids={"id":idOfTimeEntries[i]};
		}
		var input={"payload":{"timeEntries":[ids]}};
	RequestManager.submit(input,function(data,success){
		if(success){
			alert("Submitted");
		}
		else{
			alert(data.message);
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
		 var input=this.getInputForUpdate();
		 $( "#loadTimeSheetFilling" ).modal('show');
			$('.save').click(function(event){
				if(this.validateTimeEntry()){
				event.preventDefault();
				RequestManager.updateTimeEntry(input,function(data,success){
					if(success){
						alert("Updated");
						$("cancel").trigger("click");
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
	if($(".searchByDate").val()!='' && $(".searchByProjectId").val()!=null){
	input={"payload":{"date":$(".searchByDate").val(),
		               "projectId": $(".searchByProjectId").val()}};
	}
	else if($(".searchByDate").val()!='' && $(".searchByProjectId").val()==null){
		input={"payload":{"date":$(".searchByDate").val()}};}
	else if($(".searchByDate").val()=='' && $(".searchByProjectId").val()!=null && $(".searchByProjectId").val()!=""){
		input={"payload":{"projectId": $(".searchByProjectId").val()}};}
	else if($(".searchByDate").val()=='' && $(".searchByProjectId").val()!=null && $(".searchByProjectId").val()==""){
		input={"payload":{}};}
	
     return input;	
}


DefaultTimeSheetPage.prototype.searchUserTimeEntries=function(){
	var input=this.getInputForSearchUserTimeEntries();
	RequestManager.searchTimeEntriesByUser(input,function(data,success){
		if(success){
			var status;
			$(".userTableData").empty();
			for(var i=0;i<data.length;i++){
				if(data[i][8]==0){
					 status="SAVED";
					 checkbox="<input type=\"checkbox\" id=\"checkboxForTableData\" class=\"checkboxForTableData\" value="+data[i][0]+"></input>";
					 remarks="";
					if(data[i][11]!=null)
					 remarks="<img  class=\"userRemarks\" src=\"resources/img/userRemarks.png\" title=\""+data[i][9]+"\">";
				 
				}
				else if(data[i][8]==1){
					status="SUBMITTED";
					checkbox='';
                     remarks="";
					if(data[i][11]!=null)
					remarks=remarks+"<img  class=\"userRemarks\" src=\"resources/img/userRemarks.png\" title=\""+data[i][11]+"\">";
				}
				else if(data[i][8]==2){
					status="APPROVED";
					checkbox='';
					 remarks="";
					if(data[i][11]!=null){
					remarks=remarks+"<img  class=\"userRemarks\" src=\"resources/img/userRemarks.png\" title=\""+data[i][11]+"\">";
					if(data[i][9]!=null)
			           remarks=reamarks+"<img  class=\"userRemarks\" src=\"resources/img/approvedComments.png\" title=\""+data[i][9]+"\">";
						}
				}
				else if(data[i][8]==3){
					status="REJECTED";
					checkbox="<input type=\"checkbox\" id=\"checkboxForTableData\" class=\"checkboxForTableData\" value="+data[i][0]+"></input>";
					 remarks="";
					if(data[i][11]!=null){
					remarks=remarks+"<img  class=\"userRemarks\" src=\"resources/img/userRemarks.png\" title=\""+data[i][11]+"\">"+
			           "<img  class=\"userRemarks\" src=\"resources/img/rejectedComments.png\" title=\""+data[i][10]+"\">";}
				}
				
				 var tabledata="<tr class=\"userTableData\">" +
				    "<td>"+checkbox+"</td>"+
	                "<td>"+$.datepicker.formatDate('mm/dd/yy', new Date(data[i][1]))+"</td>"+
	                "<td>"+data[i][3]+"</td>"+
	                "<td>"+data[i][4]+"</td>"+
	                "<td>"+data[i][5]+"</td>"+
	                "<td>"+data[i][6]+"</td>"+
	                "<td>"+data[i][7]+"</td>"+
	                 "<td>"+status+"</td>"+
	                "<td>"+remarks+"</td>" +
	                "</tr>";
				    $("#tableheader").after(tabledata);
			}
		}else {
			alert(data.message);
		      }
	}.ctx(this));
}

DefaultTimeSheetPage.prototype.getProjects=function(){
	 $('#searchByProjectId').empty();
	 RequestManager.getProjectsForUser({}, function(data, success) {
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
	  else if($('.task').val()==''){
		  alert("Mention the Task Performed.");
	      isvalid=false;
	  }
	  return isvalid;
}





var DefaultTimeSheetPage=new DefaultTimeSheetPage();