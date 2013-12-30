

function SearchResults(data,roles){
	Loader.loadHTML('.resultsContainer', 'SearchResults.html', true, function(){
		this.handleShow(data,roles);
	}.ctx(this));
}

SearchResults.prototype.handleShow = function(data,roles){	
	this.appendValues(data,roles);	
	$('.editSymbol').click(function(event){		
		var id = event.target.id;
		$('#content').remove();
    	App.loadUserProfile(id);
	}.ctx(this));
	$('.deleteSymbol').click(function(event){	
		var id = event.target.id;
		this.deleteUser(id);
	}.ctx(this));
}

SearchResults.prototype.appendValues = function(data,roles){
	if(roles.contains(1)){
	for(var i = 0;i<data.records.length;i++){
		if(data.records[i].projects.length<1){
			data.records[i].projects[0] = "No projects";
		}
		if(i%2 == 0){
		for(var j=0;j<data.records[i].projects.length;j++){
			if(j==0){
			$("#resultsTable tbody").append("<tr style = 'text-align:center;background-color:#eeeeee;' id = "+ data.records[i].id+" class = 'rowcolorEven'>"+			        
					"<td rowspan = "+data.records[i].projects.length+" id ="+data.records[i].photoFileUrl+"><img src='resources/img/defaultImage.png' alt = 'default image' class='defaultImage'/></td>"+
					"<td rowspan = "+data.records[i].projects.length+"  ><p style = 'font-size:12px;'>"+data.records[i].email+"</p></td>"+
					"<td rowspan = "+data.records[i].projects.length+" ><p style = 'font-size:12px'>"+data.records[i].employeeId+"</p></td>"+
					"<td rowspan = "+data.records[i].projects.length+" ><p style = 'font-size:12px'>"+data.records[i].designation+"</p></td>"+			     
					"<td><p style = 'font-size:12px;'>"+data.records[i].projects[j]+"</p></td>"+
					"<td rowspan = "+data.records[i].projects.length+" ><img src='resources/img/delete.png' alt = 'delete' style ='cursor:pointer;' class='deleteSymbol' id = "+ data.records[i].id +" /></span><img src ='resources/img/edit.png'  style ='cursor:pointer;'  alt = 'edit' id = "+data.records[i].id+" class='editSymbol'/></td>"+
			"</tr>"	); 
			}
		
			else{
				$("#resultsTable tbody").append("<tr class = 'rowcolorEven' style = 'text-align:center; background-color:#eeeeee;' id = "+ data.records[i].id+">"+
						"<td><p style = 'font-size:12px;'>"+data.records[i].projects[j]+"</p></td></tr>");
			}
			}
			

		}

		else
			{
			for(var j=0;j<data.records[i].projects.length;j++){
				if(j==0){
				$("#resultsTable tbody").append("<tr style = 'text-align:center;background-color:#dddddd;' id = "+ data.records[i].id+" class = 'rowcolorOdd'>"+			        
						"<td rowspan = "+data.records[i].projects.length+" id ="+data.records[i].photoFileUrl+"><img src='resources/img/defaultImage.png' alt = 'default image' class='defaultImage'/></td>"+
						"<td rowspan = "+data.records[i].projects.length+"  ><p style = 'font-size:12px;'>"+data.records[i].email+"</p></td>"+
						"<td rowspan = "+data.records[i].projects.length+" ><p style = 'font-size:12px'>"+data.records[i].employeeId+"</p></td>"+
						"<td rowspan = "+data.records[i].projects.length+" ><p style = 'font-size:12px'>"+data.records[i].designation+"</p></td>"+			     
						"<td><p style = 'font-size:12px;'>"+data.records[i].projects[j]+"</p></td>"+
						"<td rowspan = "+data.records[i].projects.length+" ><img src='resources/img/delete.png' alt = 'delete' style ='cursor:pointer;' class='deleteSymbol' id = "+ data.records[i].id +" /></span><img src ='resources/img/edit.png'  style ='cursor:pointer;'  alt = 'edit' id = "+data.records[i].id+" class='editSymbol'/></td>"+
				"</tr>"	); 
				}
			
				else{
					$("#resultsTable tbody").append("<tr class = 'rowcolorOdd' style = 'text-align:center; background-color:#dddddd;' id = "+ data.records[i].id+">"+
							"<td><p style = 'font-size:12px;'>"+data.records[i].projects[j]+"</p></td></tr>");
				}
				}			
			}		
		}	
	}  
	
	else{
		 $('#resultsTable thead tr th:last').hide();
		 for(var i = 0;i<data.records.length;i++){
			if(data.records[i].projects.length<1){
				data.records[i].projects = "No projects";
			}
			$("#resultsTable tbody").append("<tr style = 'text-align:center;'>"+			        
					"<td id ="+data.records[i].photoFileUrl+"><img src='resources/img/defaultImage.png' alt = 'default image' class='defaultImage'/></td>"+
					"<td><p style = 'font-size:12px'>"+data.records[i].email+"</p></td>"+
					"<td><p style = 'font-size:12px'>"+data.records[i].employeeId+"</p></td>"+
					"<td><p style = 'font-size:12px'>"+data.records[i].designation+"</p></td>"+			     
					"<td><p style = 'font-size:12px'>"+data.records[i].projects+"</p></td>"+				
			"</tr>"); 
		}  
		
	}


}

SearchResults.prototype.deleteUser =  function(id){
	var shouldDelete = confirm('Are you sure to delete this user');
	 var input = {"payload":{"id":id}};
	if(shouldDelete){
	RequestManager.deleteUser(input,function(data,success){
		if(success){
			alert("success"+" user deleted");
			$("tr#"+id).remove();		
			$("#select.employeeIdText option[value="+id+"]").remove();
		}
		else{
			alert("fail "+ data.message);
			
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