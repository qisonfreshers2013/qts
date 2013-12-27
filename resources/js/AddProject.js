function AddProject() {
	Loader.loadHTML('.container', 'AddProject.html', false, function(){
		this.handleShow();
	}.ctx(this));
}

AddProject.prototype.handleShow = function() {
	$('.go').click(function(){
		
		var projectNameField=$('#projectName');
		var technologiesField=$('#technologies');
		
		var projectName=projectNameField.val();
		var technologies=technologiesField.val();
		
		//validations for project name and technologies
		var pattern=/^[a-zA-Z0-9]+[\s\._\w]*/;
		if(projectName.length==0){
			alert("Project name can't be empty.");
			projectNameField.focus();
		}
		else if(projectName.length>127){
			alert("Project name length can't be more than 128 characters.");
			projectNameField.focus();
		}
		else if(technologies.length>511){
			alert("Technologies  length can't be more than 512 characters.");
			technologiesField.focus();
		}
		else if(!pattern.test(projectName)){
			alert("invalid pattern for project Name");
			projectNameField.focus();

		}
		else{
			this.addProject();
		}
	}.ctx(this));

}

AddProject.prototype.addProject = function() {
	var projectNameField=$('#projectName').val();
	var technologiesField=$('#technologies').val();
	RequestManager.addProject({"payload":{"name":projectNameField,"technologies":technologiesField}}, function(data, success) {
		if(success){
			var output=data.name;
		alert('project sucessfully added\n'+'project name: '+output);
	}else{
			alert(data.message);
		}
	}.ctx(this));
}


