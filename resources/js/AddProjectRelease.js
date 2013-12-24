/**
 * 
 */
function AddProjectRelease() {
	Loader.loadHTML('.add', 'AddProjectRelease.html', true, function(){
		this.handleShow();
	}.ctx(this));
}

AddProjectRelease.prototype.handleShow = function() {

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
				$('.SelectProjectAdd').append("<option value="+id+">"+name+"</option>");
			})
		}
		else
			alert(data.message);
	}.ctx(this));
	$('.save').click(function(){
		this.addProjectRelease();		
	}.ctx(this));

}

AddProjectRelease.prototype.addProjectRelease = function() {
	var projectId=parseInt($('select.SelectProjectAdd option:selected').attr('value'));
	var releaseName=$('.ReleaseName').val();
	$('.ReleaseName').empty();
	var rNamePattern=/^[\w]+[\\.-_\w]*([ {1}][\\.-_\w]+)*$/g;
	if(rNamePattern.test(releaseName) && releaseName.length<=128){
		RequestManager.addProjectRelease({"payload":{"projectId":projectId,"releaseName":releaseName}}, function(data, success) {
			if(success){
				
				alert("success");
			}else{
				alert(data.message);
			}
		}.ctx(this));
	}
	else{
		alert('Release Name Format Exception');
	}
}
