/**
 * 
 */

function SearchProjectRelease(roles) {
	Loader.loadHTML('.container', 'SearchProjectRelease.html', false, function(){
		this.handleShow(roles);
	}.ctx(this));
}

SearchProjectRelease.prototype.handleShow = function(roles) {

	RequestManager.getProjects({},function(data,success){
		if(success){
			$.each(data,function(key1,value1){
				var id=0;
				var name='';
				$.each(value1,function(key2,value2){
					if(key2==0){
						id=value2;
					}
					else{
						name=value2;
					}
				});
				$('.SelectProject').append("<option value="+id+">"+name+"</option>");
			});
		}
		else
			alert(data.message);
	}.ctx(this));

	$('.SelectProject').change(function(){
		App.loadReleaseResult(roles);

	}.ctx(this));

}

