function AddProject() {
	Loader.loadHTML('.container', 'AddProject.html', false, function(){
		this.handleShow();
	}.ctx(this));
}

AddProject.prototype.handleShow = function() {
	var flag=true;
	$("#ambiance-notification").empty();
	$('#projectName').focus();
	
	$(".enterActionAp").keyup(function(event) {
		if (event.keyCode == 13) {
			$('#go').trigger('click');
		}
    }.ctx(this));
	
	
	
	$('#go').click(function(){
		var projectNameField=$('#projectName');
		var technologiesField=$('#technologies');
		
		var projectName=projectNameField.val();
		var technologies=technologiesField.val();
		
		//validations for project name and technologies
		var pattern=/^[a-zA-Z0-9]+[\s\._\w]*/;
		if(projectName.length==0){
			$.ambiance({
			    message : "Project name can't be empty.",
			    type : 'error'
			   });
			projectNameField.focus();
		}
		else if(projectName.length>128){
			$.ambiance({
			    message : "Project name length can't be more than 128 characters.",
			    type : 'error'
			   });
			projectNameField.focus();
		}
		else if(technologies.length>512){
			$.ambiance({
			    message : "Technologies  length can't be more than 512 characters.",
			    type : 'error'
			   });
			technologiesField.focus();
		}
		else if(!pattern.test(projectName)){
			$.ambiance({
			    message : "Invalid pattern for project Name",
			    type : 'error'
			   });
			projectNameField.focus();

		}
		else{
			this.addProject();
		}
	}.ctx(this));
	
	$('#resetAP').click(function(){
		 $("#ambiance-notification").empty();
	});

}

AddProject.prototype.addProject = function() {
	var projectNameField=$('#projectName').val();
	var technologiesField=$('#technologies').val();
	RequestManager.addProject({"payload":{"name":projectNameField,"technologies":technologiesField}}, function(data, success) {
		if(success){
			var output=data.name;
			$.ambiance({
			    message : 'Project sucessfully added\n'+'project name: '+output,
			    type : 'success'
			   });
			$('#projectName').val('');
			$('#technologies').val('');
	}else{
		$.ambiance({
		    message : data.message,
		    type : 'error'
		   });
		}
	}.ctx(this));
}


