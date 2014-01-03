

function SearchResults(data,roles){
	this.options;
	Loader.loadHTML('.resultsContainer', 'SearchResults.html', true, function(){
		this.handleShow(data,roles);
	}.ctx(this));
}

SearchResults.prototype.handleShow = function(data,roles){	
//var position={my:'left center',at:'right+10 center'}
	
	this.appendValues(data,roles);	
//$('#projectsIdSR').tooltip();
	
//	$(document).tooltip('position',position);
	$('.editSymbol').click(function(event){		
		var id = event.target.id;
		$('#content').remove();
    	App.loadUserProfile(id);
	}.ctx(this));
	$('.deleteSymbol').click(function(event){	
		var id = event.target.id;
		this.deleteUser(parseInt(id));
	}.ctx(this));
	
	//$("#projectsIdSR").tooltip(this.options);
}

SearchResults.prototype.appendValues = function(data,roles){
	
	var projectList ="";

		if(roles.contains(1)){
			for(var i = 0;i<data.records.length;i++){
				if(data.records[i].projects.length<1){
					data.records[i].projects[0] = "No projects";
				}
			
				for(j = 0 ;j<data.records[i].projects.length-1 ;j++){
					projectList = projectList+data.records[i].projects[j]+" , ";
				}
				projectList = projectList+data.records[i].projects[data.records[i].projects.length-1]+" .";			
				this.options = {"title":projectList,
						   "placement":"right"
						};
				$("#resultsTable tbody").append("<tr style = 'text-align:center; id = "+ data.records[i].id+" class = 'rowcolor'>"+			        
						"<td id ="+data.records[i].photoFileUrl+"><img src='resources/img/defaultImage.png' alt = 'default image' class='defaultImage'/></td>"+
						"<td ><p style = 'font-size:12px;' title = '"+data.records[i].email+"'>"+data.records[i].email.ellipses(35)+"</p></td>"+
						"<td ><p style = 'font-size:12px' title = '"+data.records[i].employeeId+"' >"+data.records[i].employeeId.ellipses(10)+"</p></td>"+
						"<td ><p style = 'font-size:12px' title = '"+data.records[i].designation+"' >"+data.records[i].designation.ellipses(10)+"</p></td>"+			     
						"<td id = 'projectsIdSR"+data.records[i].id+"' title = '"+projectList+"'><p style = 'font-size:12px;'>"+projectList.ellipses(10)+"</p></td>"+
						"<td ><img src='resources/img/delete.png' alt = 'delete' style ='cursor:pointer;' title = 'delete user' class='deleteSymbol' id = "+ data.records[i].id +" /></span><img src ='resources/img/edit.png'  style ='cursor:pointer;' title = 'edit profile' alt = 'edit' id = "+data.records[i].id+" class='editSymbol'/></td>"+
				"</tr>"	); 				
				//$("#projectsIdSR"+data.records[i].id).tooltip(this.options);
				projectList = "";
				}			
			}
		else{
			 $('#resultsTable thead tr th:last').hide();
			for(var i = 0;i<data.records.length;i++){
				if(data.records[i].projects.length<1){
					data.records[i].projects[0] = "No projects";
				}			
				for(j = 0 ;j<data.records[i].projects.length-1 ;j++){
					projectList = projectList+data.records[i].projects[j]+" , ";
				}
				projectList = projectList+data.records[i].projects[data.records[i].projects.length-1]+" .";
//				options = {"title":data.records[i].projects,
//						   "placement":"right"
//						};
				$("#resultsTable tbody").append("<tr style = 'text-align:center;'>"+			        
						"<td id ="+data.records[i].photoFileUrl+"><img src='resources/img/defaultImage.png' alt = 'default image' class='defaultImage'/></td>"+
						"<td ><p style = 'font-size:12px;' title = '"+data.records[i].email+"'>"+data.records[i].email.ellipses(35)+"</p></td>"+
						"<td ><p style = 'font-size:12px' title = '"+data.records[i].employeeId+"'>"+data.records[i].employeeId.ellipses(10)+"</p></td>"+
						"<td ><p style = 'font-size:12px' title = '"+data.records[i].designation+"'>"+data.records[i].designation.ellipses(10)+"</p></td>"+			     
						"<td id = 'projectsIdSR"+data.records[i].id+"' title = '"+projectList+"' ><p style = 'font-size:12px;'>"+projectList.ellipses(10)+"</p></td>"+						
				"</tr>"	); 		
				//$("#projectsIdSR"+data.records[i].id).tooltip(this.options);
				projectList = "";
				}
			}		
	}
//	$("#projectsIdSR").tooltip('show');


SearchResults.prototype.deleteUser =  function(id){
	var shouldDelete = confirm('Are you sure to delete this user');
	 var input = {"payload":{"id":id}};
	if(shouldDelete){
	RequestManager.deleteUser(input,function(data,success){
		if(success){
			$("tr#"+id).remove();		
			$("select.employeeIdText option[value="+id+"]").remove();
			$.ambiance({
				  message : "Success : user deleted", 
				  type : 'success'
				 });
			}
		else{
			$.ambiance({
			    message :"Fail : "+ data.message,
			    type : 'error'
			   });			
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