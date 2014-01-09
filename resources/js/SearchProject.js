/**
 * Mani kumar
 */
function SearchProject(){
	Loader.loadHTML('.container', 'SearchProject.html',false, function(){
		this.getProjects();
		this.handleShow();
		this.records='';
	}.ctx(this));
}


SearchProject.prototype.handleShow = function() {
	
	records=new Array();
	
	$("#ambiance-notification").empty();
	$('#project_name').focus();

	
	 if(! (navigator.userAgent.indexOf("Chrome") != -1 )) {
		 //key up events for arrow buttons
		$('#project_name').keyup(function(event){
			$("#ambiance-notification").empty();
			
			if (event.keyCode == 37||event.keyCode == 38||event.keyCode == 39||event.keyCode == 40) {
				$('#results').empty();
				this.getProjectUsers();
			}
			
		}.ctx(this));
	}
	
	
	$('#project_name').change(function(){
		$("#ambiance-notification").empty();
		$('#results').empty();
		this.getProjectUsers();

	}.ctx(this));
	
}

//fetching and appending projects to select box
SearchProject.prototype.getProjects=function(){
	$('#project_name').empty();
	$('#project_name').append('<option value=0>--select--</option>');
	RequestManager.getProjects({}, function(data, success) {
		if(success){
			var id='';
			var name='';
			$.each(data,function(key1,value1){
				$.each(value1,function(key2,value2){
					if(key2==0){
						id=value2;
					}else{
						name=value2;
					}
				});
				$('#project_name').append('<option value='+id+' title='+name+'>'+name.ellipses(15)+'</option>');
			});
	}else{
			alert(data.message);
		}
	}.ctx(this));
}


//fetching project users
SearchProject.prototype.getProjectUsers=function(){

	var projectId=$('select#project_name option:selected').attr('value');
	if(projectId!=0){
		RequestManager.getProjectUsers({"payload":{"projectId":projectId}}, function(data, success) {
			if(success){
				if(data.projectUserRecords!=null){
					this.searchProjectResults(data);
				}
				else{
					$.ambiance({
					    message :'No user is Associated with this project',
					    type : 'error'
					   });
				}
			}else{
				$.ambiance({
				    message :data.message,
				    type : 'error'
				   });
			}
		}.ctx(this));
	}else{
		$('#project_name').focus();
		$.ambiance({
		    message :'Please select project',
		    type : 'error'
		   });
	}
}


//displaying project users
SearchProject.prototype.searchProjectResults=function(data){

	$('#results').empty();
	
	$('#results').append('<div class="headline"><h2><img src="resources/img/u216_normal.png"/>Results<br><hr></h2></div>'+
	'<div id=approverDiv></div><div id=memberDiv></div><div id=noRoleDiv></div>');
	
	var approver=$('#approverDiv');
	var member=$('#memberDiv');
	var noRole=$('#noRoleDiv');
	var image='resources/img/employee.png';
	var name='';
	var approversStatus=false;
	var membersStatus=false;
	
	noRole.hide();
	approver.append('<div id="approverHeadLine"><h3>Approvers</h3></div>');
	member.append('<div id="memberHeadLine"><h3>Members</h3></div>');
	noRole.append('<div id="noRoleHeadLine"><h3>No Role</h3></div>');
	
	records=data.projectUserRecords;
	records=records.sort(function(a, b){
	    if (a.firstName.toLowerCase() == b.firstName.toLowerCase()) {
	        return 0;
	    } else if(a.firstName.toLowerCase() > b.firstName.toLowerCase()) {
	        return 1;
	    }
	    return -1;
	});
	
	$.each(records,function(key,value){
		var roleNames=value.roleNames;
		name=value.firstName.charAt(0).toUpperCase()+value.firstName.substr(1).toLowerCase().ellipses(14);
		 if(roleNames.contains('APPROVER')){
			 approversStatus=true;
			if(roleNames.contains('MEMBER')){
				 membersStatus=true;
				approver.append('<div id="usersImagesAndNames"><p title='+value.firstName+'>'
						+name+'</p><img  src='+image+' title='+value.email+'/></div>');
				
				member.append('<div id="usersImagesAndNames"><p title='+value.firstName+'>'
						+name+'</p><img  src='+image+' title='+value.email+'/></div>');
			}else{
				approver.append('<div id="usersImagesAndNames"><p title='+value.firstName+'>'
						+name+'</p><img  src='+image+' title='+value.email+'/></div>');
			}
			
		}else if(roleNames.contains('MEMBER')){
			membersStatus=true;
			member.append('<div id="usersImagesAndNames"><p title='+value.firstName+'>'
					+name+'</p><img  src='+image+' title='+value.email+'/></div>');
		}else{
			if(!roleNames.contains('ADMIN')){
				noRole.show();
				noRole.append('<div id="usersImagesAndNames"><p title='+value.firstName+'>'
						+name+'</p><img  src='+image+' title='+value.email+'/></div>');
			}
			
		}
		
	});
	if(!approversStatus){
		approver.append('<div style="float:left;margin-bottom:5%;text-align:center"><h4>No approvers for this project</h4></div>');
	}
	if(!membersStatus){
		member.append('<div style="float:left;margin-bottom:5%;text-align:center"><h4>No members for this project</h4></div>');
	}
	
	
}
