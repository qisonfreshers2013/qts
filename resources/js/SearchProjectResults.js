function SearchProjectResults(data){
	Loader.loadHTML('#results', 'SearchProjectResults.html',false, function(){
		this.handleShow(data);
	}.ctx(this));
}

SearchProjectResults.prototype.handleShow=function(data){
	var admin=$('#adminDiv');
	var approver=$('#approverDiv');
	var member=$('#memberDiv');
	var image='resources/img/employee.png';
	var name='';
	
	admin.empty();
	approver.empty();
	member.empty();
	admin.append('<div style="border-bottom:1px solid #99CC2F;margin-bottom:1%" ><h3 style=color:#99CC2F>Admin</h3></div>');
	approver.append('<div style="border-bottom:1px solid #2E85A3;margin-bottom:1%" ><h3 style=color:#2E85A3>Approvers</h3></div>');
	member.append('<div style="border-bottom:1px solid #F77E0D;margin-bottom:1%" ><h3 style=color:#F77E0D>Members</h3></div>');
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
		var roles=value.roles;
		name=value.firstName;
		if(roles.contains('Admin')){
			if(roles.contains('Approver')){
				admin.append('<div style="float:left;width:20%;margin-bottom:5%"><p style="align:center;font-size:16px;">'
						+name+'</p><img  src='+image+'/></div>');
				
				approver.append('<div style="float:left;width:20%;margin-bottom:5%"><p style="align:center;font-size:16px;">'
						+name+'</p><img  src='+image+'/></div>');
			}else{
				admin.append('<div style="float:left;width:20%;margin-bottom:5%"><p style="align:center;font-size:16px">'
						+name+'</p><img  src='+image+'/></div>');
			}
			
		}else if(roles.contains('Approver')){
			if(roles.contains('member')){
				approver.append('<div style="float:left;width:20%;margin-bottom:5%"><p style="align:center;font-size:16px">'
						+name+'</p><img  src='+image+'/></div>');
				
				member.append('<div style="float:left;width:20%;margin-bottom:5%"><p style="align:center;font-size:16px">'
						+name+'</p><img  src='+image+'/></div>');
			}else{
				approver.append('<div style="float:left;width:20%;margin-bottom:5%"><p style="align:center;font-size:16px">'
						+name+'</p><img  src='+image+'/></div>');
			}
			
		}else{
			member.append('<div style="float:left;width:20%;margin-bottom:5%"><p style="align:center;font-size:16px">'
					+name+'</p><img  src='+image+'/></div>');
		}
		
	});
}