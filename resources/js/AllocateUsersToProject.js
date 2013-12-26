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
				$('#projectName').append('<option value='+id+'>'+name+'</option>');
			});
		}else{
			alert(data.message);
		}
	}.ctx(this));
}


AllocateUsersToProject.prototype.handleShow=function(){

	$('#projectName').change(function(){
		this.getProjectUsersAndNonUsers();
	}.ctx(this));


	$('#forward').click(function(){
		var options = $('select#nonExistingUsers option:selected').clone();
		$('select#existingUsers').append(options);
		$('select#nonExistingUsers option:selected').remove();
	});


	$('#backward').click(function(){
		var options = $('select#existingUsers option:selected').clone();
		$('select#nonExistingUsers').append(options);
		$('select#existingUsers option:selected').remove();
	});


	$('.go').click(function(){
		newIds.length=0;
		newEmails.length=0;
		$('select#existingUsers option').each(function(){
			newIds.push(parseInt($(this).val()));
			newEmails.push($(this).text());
		});

//		alert('new Emails:'+newEmails);
//		alert('old Emails:'+oldEmails);
//		alert('new Ids:'+newIds);
//		alert('old Ids:'+oldIds);
		var projectId=$('select#projectName option:selected').attr('value');
		if(projectId==0){
			alert('select project nmae');
			$('#projectName').focus();
		}
		else{
			this.allocateUsersToProject(projectId,function(){
				$('.reset').trigger('click');
			});

		}
	}.ctx(this));

	$(".reset").click(function(){
		$('select#existingUsers').empty();
		$('select#nonExistingUsers').empty();
	});
}




AllocateUsersToProject.prototype.allocateUsersToProject=function(projectId,callBack){

	

	var allocateIds=new Array();
	var deAllocateIds=new Array();

	var allocateEmails=new Array();
	var deAllocateEmails=new Array();

	jQuery.grep(oldIds, function(el) {
		if (jQuery.inArray(el, newIds) == -1) 
			deAllocateIds.push(el);
	});

	jQuery.grep(newIds, function(el) {
		if (jQuery.inArray(el, oldIds) == -1) 
			allocateIds.push(el);
	});

	jQuery.grep(oldEmails, function(el) {
		if (jQuery.inArray(el, newEmails) == -1) 
			deAllocateEmails.push(el);
	});

	jQuery.grep(newEmails, function(el) {
		if (jQuery.inArray(el, oldEmails) == -1) 
			allocateEmails.push(el);
	});
	
	allocateIds=jQuery.unique(allocateIds);
	deAllocateIds=jQuery.unique(deAllocateIds);
//	alert('allocating:\n'+allocateIds);
//	alert('deAllocate:\n'+deAllocateIds);
//	alert('allocating:\n'+allocateEmails);
//	alert('deAllocate:\n'+deAllocateEmails);

	if(confirm('allocating users:\n'+allocateEmails+'\n\ndeAllocatingUsers:\n'+deAllocateEmails)){
		if(allocateIds.length>0){
			RequestManager.allocateUsersToProject({"payload":{ "projectId":projectId, "userIds":allocateIds}}, function(data, success) {
				if(success){
					//alert(data);
				}else{
					alert("failed");
				}
			}.ctx(this));
		}

		if(deAllocateIds.length>0){
			RequestManager.deAllocateUsersFromProject({"payload":{ "projectId":projectId, "userIds":deAllocateIds}}, function(data, success) {
				if(success){
					//alert(data);
				}else{
					alert("failed");
				}
			}.ctx(this));
		}
		
	}
	$('.reset').trigger('click');
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
				if(data.length>0){
					
					data=data.sort(function(a, b){
					    if (a.email.toLowerCase() == b.email.toLowerCase()) {
					        return 0;
					    } else if(a.email.toLowerCase() > b.email.toLowerCase()) {
					        return 1;
					    }
					    return -1;
					});
					
					$.each(data,function(key,value){
						oldEmails.push(value.email);
						oldIds.push( parseInt(value.id));
						existing.append('<option value='+value.id+'>'+value.email+'</option>');
					});
				}
			}else{
				alert('project Users'+data.message);
			}
		}.ctx(this));

		RequestManager.getProjectNonUsers({"payload":{"projectId":projectId}}, function(data, success) {
			var id='0';
			var email='';
			if(success){
					data=data.sort(function(a, b){
						if (a.email.toLowerCase() == b.email.toLowerCase()) {
					        return 0;
					    } else if (a.email.toLowerCase() > b.email.toLowerCase()) {
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
							email=value2;
						}
					});
					nonExisting.append('<option value='+id+'>'+email+'</option>');
				});
			}else{
				alert(data.message);
			}
		}.ctx(this));


	}

}
