/**
 * 
 */
function SearchProject(){
	Loader.loadHTML('.container', 'SearchProject.html',false, function(){
		this.getProjects();
		this.handleShow();
		
	}.ctx(this));
}


SearchProject.prototype.handleShow = function() {
	$("#ambiance-notification").empty();
	$('#project_name').focus();
	
	$('#project_name').keyup(function(event){
		
		$("#ambiance-notification").empty();
		
	  if (event.keyCode == 13) {
			$('#results').empty();
			this.getProjectUsers();
		}
	}.ctx(this));
	
	
	$('#project_name').change(function(){
		
		$("#ambiance-notification").empty();
		$('#results').empty();
		this.getProjectUsers();

	}.ctx(this));
	
}

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

SearchProject.prototype.getProjectUsers=function(){

	var projectId=$('select#project_name option:selected').attr('value');
	if(projectId!=0){
		RequestManager.getProjectUsers({"payload":{"projectId":projectId}}, function(data, success) {
			if(success){
				if(data.projectUserRecords!=null){
					$('#results').append('<div class="headline"><h2><img src="resources/img/u216_normal.png"/>Results<br><hr></h2></div>'+
										'<div id=approverDiv></div><div id=memberDiv></div><div id=noRoleDiv></div>');
					App.loadSearchProjectResults(data);
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
