

function SearchResults(data){
	Loader.loadHTML('.resultsContainer', 'SearchResults.html', true, function(){
		this.handleShow(data);
	}.ctx(this));
}

SearchResults.prototype.handleShow = function(data){	
	//var position={my:'left center',at:'right+10 center'}
	
	this.appendValues(data);	
	//$('#projectsIdSR').tooltip();
//	var options = null;
//	$(document).tooltip('position',position);
	$('.editSymbol').click(function(event){		
		var id = event.target.id;
		$('#content').remove();
    	App.loadUserProfile(id);
	}.ctx(this));
	$('.deleteSymbol').click(function(event){	
		var id = event.target.id;
		this.deleteUser(id);
	}.ctx(this));
	
//	$("#projectsIdSR").tooltip(options);
}

SearchResults.prototype.appendValues = function(data){
	var projectList ="";
	RequestManager.getLoggedInUserDetails({"payload":{}},function(Userdata,success){
		if(success){
			var empId=Userdata.employeeId;
			
			if(roleNames.contains('ADMIN')){
				for(var i = 0;i<data.records.length;i++){
					if(data.records[i].projects.length<1){
						data.records[i].projects[0] = "No projects";
					}
				
					for(j = 0 ;j<data.records[i].projects.length-1 ;j++){
						projectList = projectList+data.records[i].projects[j]+" ,";
					}
					projectList = projectList+data.records[i].projects[data.records[i].projects.length-1]+" .";
				
					if(empId!=data.records[i].employeeId){
					$("#resultsTable tbody").append("<tr style = 'text-align:center; id = "+ data.records[i].id+" class = 'rowcolorEven'>"+			        
							"<td id ="+data.records[i].photoFileUrl+"><img src='resources/img/defaultImage.png' alt = 'default image' class='defaultImage'/></td>"+
							"<td ><p style = 'font-size:12px;'>"+data.records[i].email+"</p></td>"+
							"<td ><p style = 'font-size:12px'>"+data.records[i].employeeId+"</p></td>"+
							"<td ><p style = 'font-size:12px'>"+data.records[i].designation+"</p></td>"+			     
							"<td id = 'projectsIdSR'><p style = 'font-size:12px;' title = '"+projectList+"'>"+projectList.ellipses(10)+"</p></td>"+
							"<td ><img src='resources/img/delete.png' alt = 'delete' style ='cursor:pointer;' class='deleteSymbol' id = "+ data.records[i].id +" /></span><img src ='resources/img/edit.png'  style ='cursor:pointer;'  alt = 'edit' id = "+data.records[i].id+" class='editSymbol'/></td>"+
					"</tr>"	); 
					projectList = "";
					}
					}
				
				}
			else{
				 $('#resultsTable thead tr th:last').hide();
				for(var i = 0;i<data.records.length;i++){
					if(data.records[i].projects.length<1){
						data.records[i].projects[0] = "No projects";
					}
					else{
					for(j = 0 ;j<data.records[i].projects.length-1 ;j++){
						projectList = projectList+data.records[i].projects[j]+" , ";
					}
					projectList = projectList+data.records[i].projects[data.records[i].projects.length-1]+" .";
					}
//					options = {"title":data.records[i].projects,
//							   "placement":"right"
//							};
					if(empId!=data.records[i].employeeId){
					$("#resultsTable tbody").append("<tr style = 'text-align:center;'>"+			        
							"<td id ="+data.records[i].photoFileUrl+"><img src='resources/img/defaultImage.png' alt = 'default image' class='defaultImage'/></td>"+
							"<td ><p style = 'font-size:12px;'>"+data.records[i].email+"</p></td>"+
							"<td ><p style = 'font-size:12px'>"+data.records[i].employeeId+"</p></td>"+
							"<td ><p style = 'font-size:12px'>"+data.records[i].designation+"</p></td>"+			     
							"<td id = 'projectsIdSR'><p style = 'font-size:12px;' title = '"+projectList+"'>"+projectList.ellipses(10)+"</p></td>"+						
					"</tr>"	); 
					projectList = "";
					}
				}
				
			}
			
			
			
		}
		else{
			alert('failed');
		}
	});	

//	$("#projectsIdSR").tooltip('show');
}

SearchResults.prototype.deleteUser =  function(id){
	var shouldDelete = confirm('Are you sure to delete this user');
	 var input = {"payload":{"id":id}};
	if(shouldDelete){
	RequestManager.deleteUser(input,function(data,success){
		if(success){
			$.ambiance({
				  message : "Success : user deleted", 
				  type : 'success'
				 });
			$("tr#"+id).remove();		
			$("#select.employeeIdText option[value="+id+"]").remove();
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