


function SearchResults(data){
	this.options;
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


	
	//$("#projectsIdSR").tooltip(this.options);
}

SearchResults.prototype.appendValues = function(data){
	var projectList ="";
	//$("#projectsIdSR"+data.records[i].id).tooltip(this.options);
	RequestManager.getLoginUserDetails({"payload":{}},function(Userdata,success){
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
						$("#resultsTable tbody").append("<tr style = 'text-align:center; id = "+ data.records[i].id+" class = 'rowcolor'>"+			        
								"<td id ="+data.records[i].photoFileUrl+"><img src='resources/img/defaultImage.png' alt = 'default image' class='defaultImage'/></td>"+
								"<td ><p style = 'font-size:12px;' title = '"+data.records[i].email+"'>"+data.records[i].email.ellipses(35)+"</p></td>"+
								"<td ><p style = 'font-size:12px' title = '"+data.records[i].employeeId+"' >"+data.records[i].employeeId.ellipses(10)+"</p></td>"+
								"<td ><p style = 'font-size:12px' title = '"+data.records[i].designation+"' >"+data.records[i].designation.ellipses(10)+"</p></td>"+			     
								"<td id = 'projectsIdSR"+data.records[i].id+"' title = '"+projectList+"'><p style = 'font-size:12px;'>"+projectList.ellipses(10)+"</p></td>"+
								"<td ><img src = 'resources/img/delete.png' alt = 'delete' style ='cursor:pointer;' title = 'delete user' class='deleteSymbol' id = "+ data.records[i].id +" /><img src ='resources/img/edit.png'  style ='cursor:pointer;' title = 'edit profile' alt = 'edit' id = "+data.records[i].id+" class='editSymbol'/></td>"+
						"</tr>"	); 		
					projectList = "";
					}
					else
						{
						$("#resultsTable tbody").append("<tr style = 'text-align:center; id = "+ data.records[i].id+" class = 'rowcolor'>"+			        
								"<td id ="+data.records[i].photoFileUrl+"><img src='resources/img/defaultImage.png' alt = 'default image' class='defaultImage'/></td>"+
								"<td ><p style = 'font-size:12px;' title = '"+data.records[i].email+"'>"+data.records[i].email.ellipses(35)+"</p></td>"+
								"<td ><p style = 'font-size:12px' title = '"+data.records[i].employeeId+"' >"+data.records[i].employeeId.ellipses(10)+"</p></td>"+
								"<td ><p style = 'font-size:12px' title = '"+data.records[i].designation+"' >"+data.records[i].designation.ellipses(10)+"</p></td>"+			     
								"<td id = 'projectsIdSR"+data.records[i].id+"' title = '"+projectList+"'><p style = 'font-size:12px;'>"+projectList.ellipses(10)+"</p></td>"+
								"<td ><img src = 'resources/img/delete.png' alt = 'delete' style ='cursor:pointer;' title = 'delete user' class='deleteSymbolSelf' id = "+ data.records[i].id +" /><img src ='resources/img/edit.png'  style ='cursor:pointer;' title = 'edit profile' alt = 'edit' id = "+data.records[i].id+" class='editSymbol'/></td>"+
						"</tr>"	); 	
						projectList = "";
						}
					
					
					}
				$('.editSymbol').click(function(event){		
					var id = event.target.id;
					$('#content').remove();
			    	App.loadUserProfile(id);
				}.ctx(this));
				
				$('.deleteSymbolSelf').click(function(event){
					$.ambiance({
					    message :"You are not allowed to perform this action",
					    type : 'error'
					   });
				}.ctx(this));			
				
				$('.deleteSymbol').click(function(event){	
					var id = event.target.id;
					var shouldDelete = confirm('Are you sure to delete this user');
					 var input = {"payload":{"id":id}};
					if(shouldDelete){
					RequestManager.deleteUser(input,function(data,success){
						if(success){
							$(event.target).parent().parent().remove();
							if($('table tr').length==0){
								$('#resultsTable').empty();
							}	
							
							var input = {"payload":{}};
							 $('select.employeeId').empty();
							 $('select.employeeId').append('<option value="">--select--</option>');
							RequestManager.getEmployeeIds(input,function(data,success){		
								if(success){			
									
									for(var i=0 ; i<data.length; i++){
										 $('select.employeeId').append('<option value='+data[i]+'>'+data[i]+'</option>');	
									}		
								}	
								else{
									$.ambiance({
									    message : "Fail : "+data.message,
									    type : 'error'
									   });	
									}
							}.ctx(this));							
							
							
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
				}.ctx(this));
				

				
				}
			else{
				 $('#resultsTable thead tr th:last').hide();
				for(var i = 0;i<data.records.length;i++){
					if(data.records[i].projects.length<1){
						data.records[i].projects[0] = "No projects";
						projectList = data.records[i].projects[0];
					}
					else{
					for(j = 0 ;j<data.records[i].projects.length-1 ;j++){
						projectList = projectList+data.records[i].projects[j]+",";
					}
					projectList = projectList+data.records[i].projects[data.records[i].projects.length-1]+" .";
					}
//					options = {"title":data.records[i].projects,
//							   "placement":"right"
//							};
				//	if(empId!=data.records[i].employeeId){
						$("#resultsTable tbody").append("<tr style = 'text-align:center;'>"+			        
								"<td id ="+data.records[i].photoFileUrl+"><img src='resources/img/defaultImage.png' alt = 'default image' class='defaultImage'/></td>"+
								"<td ><p style = 'font-size:12px;' title = '"+data.records[i].email+"'>"+data.records[i].email.ellipses(35)+"</p></td>"+
								"<td ><p style = 'font-size:12px' title = '"+data.records[i].employeeId+"'>"+data.records[i].employeeId.ellipses(10)+"</p></td>"+
								"<td ><p style = 'font-size:12px' title = '"+data.records[i].designation+"'>"+data.records[i].designation.ellipses(10)+"</p></td>"+			     
								"<td id = 'projectsIdSR"+data.records[i].id+"' title = '"+projectList+"' ><p style = 'font-size:12px;'>"+projectList.ellipses(10)+"</p></td>"+						
						"</tr>"	);
					projectList = "";
					//}
				}				
			}			
		}
		else{
			$.ambiance({
			    message : "Fail : "+data.message,
			    type : 'error'
			   });	
		}
	}.ctx(this));

}
//	$("#projectsIdSR").tooltip('show');


//SearchResults.prototype.deleteUser =  function(id){
//	var shouldDelete = confirm('Are you sure to delete this user');
//	 var input = {"payload":{"id":id}};
//	if(shouldDelete){
//	RequestManager.deleteUser(input,function(data,success){
//		if(success){
//	//		$(event.target).parent().parent().remove();
//			if($('table tr').length==0){
//				$('#resultsTable').empty();
//			}		
////			$("select.employeeIdText option[value="+id+"]").remove();
//			$.ambiance({
//				  message : "Success : user deleted", 
//				  type : 'success'
//				 });
//			}
//		else{
//			$.ambiance({
//			    message :"Fail : "+ data.message,
//			    type : 'error'
//			   });			
//		}
//	}.ctx(this));
//	}	
//}

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