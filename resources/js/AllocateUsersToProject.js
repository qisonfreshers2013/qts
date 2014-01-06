function AllocateUsersToProject(){
	Loader.loadHTML('.container', 'AllocateUsersToProject.html',false, function(){
		this.getProjects();
		this.handleShow();
	}.ctx(this));
}


var oldEmails=new Array();
var oldIds=new Array();
var newEmails=new Array();
var newIds=new Array();
var projectName=$('#projectName');
var status=false;

AllocateUsersToProject.prototype.getProjects=function(){
	$('#projectName').empty();
	$('#projectName').append('<option value=0>--select--</option>');
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
				$('#projectName').append('<option value='+id+' title='+name+'>'+name.ellipses(15)+'</option>');
			});
		}else{
			alert(data.message);
		}
	}.ctx(this));
}


AllocateUsersToProject.prototype.handleShow=function(){
	
	$("#text").keyup(function (event) {
		if (event.keyCode == 13) {
			$('.go').trigger('click');
		}
  }.ctx(this));
	
	$('#projectName').focus();
	$('#projectName').change(function(){
		status=false;
		this.getProjectUsersAndNonUsers();
	}.ctx(this));
	
	
	$('#forward').click(function(){
		var projectId=parseInt($('select#projectName option:selected').attr('value'));
		var options = $('select#nonExistingUsers option:selected').clone();
		if(options.length>0){
			$('select#existingUsers').append(options);
			$('select#nonExistingUsers option:selected').remove();
			status=true;
		}else if(projectId==0){
			$.ambiance({
			    message : 'please select project',
			    type : 'error'
			   });
		}else{
			$.ambiance({
			    message :'please select atleast one user for allocation',
			    type : 'error'
			   });
		}

	});


	$('#backward').click(function(){
		var projectId=parseInt($('select#projectName option:selected').attr('value'));
		var options = $('select#existingUsers option:selected').clone();
		if(options.length>0){
			$('select#nonExistingUsers').append(options);
			$('select#existingUsers option:selected').remove();
			status=true;
		}else if(projectId==0){
			$.ambiance({
			    message :'please select project',
			    type : 'error'
			   });
		}else{
			$.ambiance({
			    message :'please select atleast one user for deallocation',
			    type : 'error'
			   });
		}

	});


	$('.go').click(function(){
		newIds.length=0;
		newEmails.length=0;
		$('select#existingUsers option').each(function(){
			newIds.push(parseInt($(this).val()));
			newEmails.push($(this).text());
		});

		var projectId=$('select#projectName option:selected').attr('value');
		if(projectId==0){
			$.ambiance({
			    message :'select project name',
			    type : 'error'
			   });
			$('#projectName').focus();
		}
		else{
			this.allocateUsersToProject(projectId,function(){
					this.getProjectUsersAndNonUsers();
					status=false;
				
			}.ctx(this));
			
		}

	}.ctx(this));

	$(".reset").click(function(){
		this.getProjectUsersAndNonUsers();
		 $("#ambiance-notification").empty();
	}.ctx(this));
}



AllocateUsersToProject.prototype.allocateUsersToProject=function(projectId,callBack){
	var allocateIds=new Array();
	var deAllocateIds=new Array();
	
	var allocateEmailMessage='';
	var deAllocateEmailMessage='';

	$.each(oldIds,function(key,value){
		if (jQuery.inArray(value, newIds) == -1) 
			deAllocateIds.push(value);
	});

	$.each(newIds,function(key,value){
		if (jQuery.inArray(value, oldIds) == -1) 
			allocateIds.push(value);
	});
	$.each(oldEmails,function(key,value){
		if (jQuery.inArray(value, newEmails) == -1) {
			deAllocateEmailMessage+='\t'+value+'\n';
		}
	});
	
	$.each(newEmails,function(key,value){
		if (jQuery.inArray(value, oldEmails) == -1){
			allocateEmailMessage+='\t'+value+'\n';
		} 
	});

	allocateIds=jQuery.unique(allocateIds);
	deAllocateIds=jQuery.unique(deAllocateIds);
	var allocatingIdsLength=allocateIds.length;
	var daAllocatingIdsLength=deAllocateIds.length;
	var message='';
	if(allocatingIdsLength>0&&daAllocatingIdsLength>0){
		message='ALLOCATING USERS:\n'+allocateEmailMessage+'\n\nDEALLOCATING USERS:\n'+deAllocateEmailMessage;
	}else if(allocatingIdsLength>0){
		message='ALLOCATING USERS:\n'+allocateEmailMessage;
	}else{
		message='DEALLOCATING USERS:\n'+deAllocateEmailMessage;
	}
	if(allocatingIdsLength>0 || daAllocatingIdsLength>0){
		if(confirm(message)){
			if(allocatingIdsLength>0&&daAllocatingIdsLength>0){
				RequestManager.allocateUsersToProject({"payload":{ "projectId":projectId, "userIds":allocateIds}}, function(data, success) {
					if(success){
						RequestManager.deAllocateUsersFromProject({"payload":{ "projectId":projectId, "userIds":deAllocateIds}}, function(data, success) {
							if(success){
								callBack();
							}else{
								$.ambiance({
								    message :data.message,
								    type : 'error'
								   });
							}
						}.ctx(this));
					}else{
						$.ambiance({
						    message :data.message,
						    type : 'error'
						   });
					}
				}.ctx(this));


			}else if(allocatingIdsLength>0){
				RequestManager.allocateUsersToProject({"payload":{ "projectId":projectId, "userIds":allocateIds}}, function(data, success) {
					if(success){
						callBack();
					}else{
						$.ambiance({
						    message :data.message,
						    type : 'error'
						   });
					}
				}.ctx(this));
			}else{
				RequestManager.deAllocateUsersFromProject({"payload":{ "projectId":projectId, "userIds":deAllocateIds}}, function(data, success) {
					if(success){
						callBack();
					}else{
						$.ambiance({
						    message :data.message,
						    type : 'error'
						   });
					}
				}.ctx(this));
			}
		}
	}else{
		$.ambiance({
		    message :'please select atleast one user for allocation or deallocation',
		    type : 'error'
		   });
	}

}


AllocateUsersToProject.prototype.getProjectUsersAndNonUsers=function(){
	var nonExisting=$('#nonExistingUsers');
	var existing=$('#existingUsers');
	nonExisting.empty();
	existing.empty();
	oldEmails.length=0;
	oldIds.length=0;
	var projectId=$('select#projectName option:selected').attr('value');
	if(projectId!=0){
		RequestManager.getProjectUsers({"payload":{"projectId":projectId}}, function(data, success) {
			if(success){
				var records=data.projectUserRecords;
				records=records.sort(function(a, b){
					    if (a.email.toLowerCase() == b.email.toLowerCase()) {
					        return 0;
					    } else if(a.email.toLowerCase() > b.email.toLowerCase()) {
					        return 1;
					    }
					    return -1;
					});
					
					$.each(records,function(key,value){
						oldEmails.push(value.email.toLowerCase());
						oldIds.push( parseInt(value.id));
						existing.append('<option value='+value.id+'title='+value.email+'>'+value.email.toLowerCase().ellipses(25)+'</option>');
					});
			}else{
				$.ambiance({
				    message :data.message,
				    type : 'error'
				   });
			}
		}.ctx(this));

		RequestManager.getProjectNonUsers({"payload":{"projectId":projectId}}, function(data, success) {
			var id='0';
			var email='';
			if(success){
//					data=data.sort(function(a, b){
//						if (a.email.toLowerCase() == b.email.toLowerCase()) {
//					        return 0;
//					    } else if (a.email.toLowerCase() > b.email.toLowerCase()) {
//					        return 1;
//					    }
//					    return -1;
//					});
				$.each(data,function(key1,value1){
					$.each(value1,function(key2,value2){
						if(key2==0){
							id=value2;
						}
						if(key2==1){
							email=value2;
						}
					});
					nonExisting.append('<option value='+id+'title='+email+'>'+email.toLowerCase().ellipses(25)+'</option>');
				});
			}else{
				$.ambiance({
				    message :data.message,
				    type : 'error'
				   });
			}
		}.ctx(this));


	}

}
