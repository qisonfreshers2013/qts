function SearchProjectResults(data){
	Loader.loadHTML('#results', 'SearchProjectResults.html',false, function(){
		this.handleShow(data);
	}.ctx(this));
}

SearchProjectResults.prototype.handleShow=function(data){
	var approver=$('#approverDiv');
	var member=$('#memberDiv');
	var image='resources/img/employee.png';
	var name='';
	
	approver.empty();
	member.empty();
	approver.append('<div id="approverHeadLine"><h3>Approvers</h3></div>');
	member.append('<div id="memberHeadLine"><h3>Members</h3></div>');
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
		name=value.firstName;
		 if(roleNames.contains('APPROVER')){
			if(roleNames.contains('MEMBER')){
				approver.append('<div style="float:left;width:20%;margin-bottom:5%;text-align:center"><p>'
						+name+'</p><img  src='+image+'/></div>');
				
				member.append('<div style="float:left;width:20%;margin-bottom:5%;text-align:center"><p>'
						+name+'</p><img  src='+image+'/></div>');
			}else{
				approver.append('<div style="float:left;width:20%;margin-bottom:5%;text-align:center"><p>'
						+name+'</p><img  src='+image+'/></div>');
			}
			
		}else{
			member.append('<div style="float:left;width:20%;margin-bottom:5%;text-align:center"><p>'
					+name+'</p><img  src='+image+'/></div>');
		}
		
	});
}