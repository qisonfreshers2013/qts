function SearchProjectResults(data){
		this.handleShow(data);
}

SearchProjectResults.prototype.handleShow=function(data){
	$("#ambiance-notification").empty();
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
	
	var records=data.projectUserRecords;
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
						+name+'</p><img  src='+image+'/></div>');
				
				member.append('<div id="usersImagesAndNames"><p title='+value.firstName+'>'
						+name+'</p><img  src='+image+'/></div>');
			}else{
				approver.append('<div id="usersImagesAndNames"><p title='+value.firstName+'>'
						+name+'</p><img  src='+image+'/></div>');
			}
			
		}else if(roleNames.contains('MEMBER')){
			membersStatus=true;
			member.append('<div id="usersImagesAndNames"><p title='+value.firstName+'>'
					+name+'</p><img  src='+image+'/></div>');
		}else{
			if(!roleNames.contains('ADMIN')){
				noRole.show();
				noRole.append('<div id="usersImagesAndNames"><p title='+value.firstName+'>'
						+name+'</p><img  src='+image+'/></div>');
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