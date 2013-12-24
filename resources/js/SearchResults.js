

function SearchResults(data){
	Loader.loadHTML('.resultsContainer', 'SearchResults.html', true, function(){
		this.handleShow(data);
	}.ctx(this));
}

SearchResults.prototype.handleShow = function(data){	
	this.appendValues(data);	
	$('.editSymbol').click(function(event){		
		var id = event.target.id;
    	App.loadUserProfile(id);
	}.ctx(this));
	$('.deleteSymbol').click(function(event){	
		var id = event.target.id;
		this.deleteUser(id);
	}.ctx(this));
}

SearchResults.prototype.appendValues = function(data){
	for(var i = 0;i<data.records.length;i++){
		if(data.records[i].projects.length<1){
			data.records[i].projects = "No projects";
		}
		$("#resultsTable tbody").append("<tr>"+			        
				"<td id ="+data.records[i].photoFileUrl+"><img src='resources/img/defaultImage.png' alt = 'default image' class='defaultImage'/></td>"+
				"<td><p style = 'font-size:12px'>"+data.records[i].email+"</p></td>"+
				"<td><p style = 'font-size:12px'>"+data.records[i].employeeId+"</p></td>"+
				"<td><p style = 'font-size:12px'>"+data.records[i].designation+"</p></td>"+			     
				"<td><p style = 'font-size:12px'>"+data.records[i].projects+"</p></td>"+
				"<td><img src='resources/img/delete.png' alt = 'delete' style ='cursor:ponter;' class='deleteSymbol' id = "+ data.records[i].id +" /></span><img src ='resources/img/edit.png'  style ='cursor:ponter;'  alt = 'edit' id = "+data.records[i].id+" class='editSymbol'/></td>"+
		"</tr>"); 
	}  	


}

SearchResults.prototype.deleteUser =  function(id){
	var shouldDelete = confirm('Are you sure to delete this user');
	 var input = {"payload":{"id":id}};
	if(shouldDelete){
	RequestManager.deleteUser(input,function(data,success){
		if(success){
			alert("success"+" user deleted")
		}else{
			alert("fail "+ data.message)
		}
	}.ctx(this));
	}
	
		
	
}



//
//SearchResults.prtotype.appendRows = function(){
//	    $("#resultsTable tbody").append(
//	        "<tr>"+
//	        "<td><input type='text'/></td>"+
//	        "<td><input type='text'/></td>"+
//	        "<td><input type='text'/></td>"+
//	        "<td><input type='text'/></td>"+
//	        "<td><input type='text'/></td>"+
//	        "<td><img src='delete.png' class='deleteSymbol'/><img src='edit.png' class='editSymbol'/></td>"+
//	        "</tr>"); 
//	       
//	}; 
//}

//
//for(var j = 0;j<data.records[i].projects.length){
//	data.records[i].projects[j]
//}