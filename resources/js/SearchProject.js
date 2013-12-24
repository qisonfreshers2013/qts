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
	$('#project_name').change(function(){
		$('#results').empty();
		var projectId=$('select#project_name option:selected').attr('value');
		if(projectId!=0){
			RequestManager.getProjectUsers({"payload":{"projectId":projectId}}, function(data, success) {
				if(success){
					if(data.length>0){
						App.loadSearchProjectResults(data);
					}
					else{
						alert('No user is Associated with this project');
					}
				}else{
					alert("failed");
				}
			}.ctx(this));
		}
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
				$('#project_name').append('<option value='+id+'>'+name+'</option>');
			});
	}else{
			alert(data.message);
		}
	}.ctx(this));
}