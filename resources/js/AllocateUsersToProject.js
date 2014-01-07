function AllocateUsersToProject(){
	Loader.loadHTML('.container', 'AllocateUsersToProject.html',false, function(){
		this.oldEmails='';
		this.oldIds='';
		this.newEmails='';
		this.newIds='';
		this.existingOptions='';
		this.nonExistingOptions='';
		this.projectName=$('#projectName');
		this.getProjects();
		this.handleShow();
	}.ctx(this));
}



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
	
	oldEmails=new Array();
	oldIds=new Array();
	newEmails=new Array();
	newIds=new Array();
	
	$('#projectName').focus();
	
	$("#text").keyup(function (event) {
		if (event.keyCode == 13) {
			$('.go').trigger('click');
		}
	}.ctx(this));


	$('#projectName').change(function(){
		allocateUsersToProjectStatus=false;
		this.getProjectUsersAndNonUsers();
	}.ctx(this));
	
	
	$('#forward').click(function(){
		var projectId=parseInt($('select#projectName option:selected').attr('value'));
		var options = $('select#nonExistingUsers option:selected').clone();
		if(options.length>0){
			$('select#existingUsers').append(options);
			$('select#nonExistingUsers option:selected').remove();
			allocateUsersToProjectStatus=true;
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
			allocateUsersToProjectStatus=true;
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
				oldIds.length=0;
				oldEmails.length=0;
				
				this.sortSelectBoxeOptions($('select#nonExistingUsers'));
				this.sortSelectBoxeOptions($('select#existingUsers'));

				$('select#existingUsers option').each(function(){
					oldIds.push(parseInt($(this).val()));
					oldEmails.push($(this).text());
				});
				
				existingOptions= $('select#existingUsers option').clone();
				nonExistingOptions= $('select#nonExistingUsers option').clone();
				allocateUsersToProjectStatus=false;
				
			}.ctx(this));

		}

	}.ctx(this));

	$(".reset").click(function(){
		
		allocateUsersToProjectStatus=false;
		$("#ambiance-notification").empty();
		$('select#existingUsers').empty().append(existingOptions);
		$('select#nonExistingUsers').empty().append(nonExistingOptions);
		
		Array.prototype.forEach.call(document.querySelectorAll("select#existingUsers :checked"), function(el) { el.selected = false });
		Array.prototype.forEach.call(document.querySelectorAll("select#nonExistingUsers :checked"), function(el) { el.selected = false });
		
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
					var email=value.email.toLowerCase();
					oldEmails.push(email.ellipses(30));
					oldIds.push( parseInt(value.id));
					existing.append('<option value='+value.id+' title='+email+'>'+email.ellipses(30)+'</option>');
				});
				existingOptions=$('select#existingUsers option').clone();
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

				data=data.sort(function(a, b){
					if (a[1].toLowerCase() == b[1].toLowerCase()) {
						return 0;
					} else if(a[1].toLowerCase() > b[1].toLowerCase()) {
						return 1;
					}
					return -1;
				});


				$.each(data,function(key1,value1){
					$.each(value1,function(key2,value2){
						if(key2==0){
							id=value2;
						}
						if(key2==1){
							email=value2.toLowerCase();
						}
					});
					nonExisting.append('<option value='+id+' title='+email+'>'+email.ellipses(30)+'</option>');
				});
				nonExistingOptions=$('select#nonExistingUsers option').clone();
			}else{
				$.ambiance({
					message :data.message,
					type : 'error'
				});
			}
		}.ctx(this));


	}

}

AllocateUsersToProject.prototype.sortSelectBoxeOptions=function(selectBoxReference){
	var options =selectBoxReference.find("option").sort(function (a, b) {
		if (a.text < b.text) return -1;
		if (a.text > b.text) return 1;
		return 0;
	});
	selectBoxReference.empty().append(options);
}
