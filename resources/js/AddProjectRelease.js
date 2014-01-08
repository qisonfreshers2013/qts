/**
 * Author:N V Siva Reddy
 */
function AddProjectRelease() {
	Loader.loadHTML('.add', 'AddProjectRelease.html', true, function(){
		this.handleShow();
	}.ctx(this));
}

AddProjectRelease.prototype.handleShow = function() {
	$("#ambiance-notification").empty();
	$('.ReleaseName').keyup(function(event){
		$("#ambiance-notification").empty();
		if(event.keyCode == 13){
			event.preventDefault();
			$('.saveAPR').trigger("click");
		}
	}.ctx(this));
	
	
	$('.saveAPR').click(function(){
		$("#ambiance-notification").empty();
		this.addProjectRelease();
		
	}.ctx(this));
	
	$('.cancelAPR').click(function(){
		$("#ambiance-notification").empty();
		$('select.SelectProject').find('option[value="0"]').attr("selected",true);
		$('.add').empty();
		$('.result').empty();
	}.ctx(this));

}

AddProjectRelease.prototype.addProjectRelease = function() {
	var projectId=parseInt($('select.SelectProject option:selected').attr('value'));
	var releaseName=$('.ReleaseName').val();
	var rNamePattern=/^[\w]+[\\.-_\w]*([ {1}][\\.-_\w]+)*$/g;
	if(releaseName.length==0){
		$.ambiance({
		    message : "Release Name Cannot be Empty",
		    type : 'error'
		   });
	}
	
	else if(releaseName.length>128){
		$.ambiance({
		    message : "Maximum Characters Allowed For Release Name are 128",
		    type : 'error'
		   });
	}
	
	else if(!(rNamePattern.test(releaseName))){
		$.ambiance({
		    message : "Release Name Format Exception",
		    type : 'error'
		   });
	}

	else{		
		RequestManager.addProjectRelease({"payload":{"projectId":projectId,"releaseName":releaseName}}, function(data, success) {
			if(success){
				$.ambiance({
				    message : "Successfully Added",
				    type : 'success'
				   });
					App.loadReleaseResult();
			}else{
				$.ambiance({
				    message : data.message,
				    type : 'error'
				   });
			}
			$('.ReleaseName').val('');
		}.ctx(this));
		
	}
}
