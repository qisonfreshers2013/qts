/**
 * 
 */
function AddProjectRelease(roles) {
	Loader.loadHTML('.add', 'AddProjectRelease.html', true, function(){
		this.handleShow(roles);
	}.ctx(this));
}

AddProjectRelease.prototype.handleShow = function(roles) {

	RequestManager.getProjects({},function(data,success){
		if(success){
			var id=0;
			var name='';
			$.each(data,function(key1,value1){
				$.each(value1,function(key2,value2){
					if(key2==0){
						id=value2;
					}
					else{
						name=value2;
					}
				})
//				$('.SelectProject').append("<option value="+id+">"+name+"</option>");
			})
		}
		else
			alert(data.message);
	}.ctx(this));
	$('.save').click(function(){
		this.addProjectRelease(roles);
		$('.cancelAPR').trigger('click');
	}.ctx(this));

}

AddProjectRelease.prototype.addProjectRelease = function(roles) {
	var projectId=parseInt($('select.SelectProject option:selected').attr('value'));
	var releaseName=$('.ReleaseName').val();
//	var searchProjectName=$('select.SelectProject option:selected').text();
//	var addProjectName=$('select.SelectProjectAdd option:selected').text();
	var rNamePattern=/^[\w]+[\\.-_\w]*([ {1}][\\.-_\w]+)*$/g;
	if(rNamePattern.test(releaseName) && releaseName.length<=128){
		RequestManager.addProjectRelease({"payload":{"projectId":projectId,"releaseName":releaseName}}, function(data, success) {
			if(success){
				$.ambiance({
				    message : "Successfully Added",
				    type : 'success'
				   });
					App.loadReleaseResult(roles);
			}else{
				$.ambiance({
				    message : data.message,
				    type : 'error'
				   });
			}
		}.ctx(this));
	}
	else if(releaseName.length>128){
		$.ambiance({
		    message : "Maximum Characters Allowed For Release Name are 128",
		    type : 'error'
		   });
	}
	else{
		$.ambiance({
		    message : "Release Name Format Exception",
		    type : 'error'
		   });
	}
}
