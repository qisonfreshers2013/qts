function ReleaseResult(roles) {	
	Loader.loadHTML('.result', 'ReleaseResult.html', true, function(){
		this.handleShow(roles);
	}.ctx(this));
}

ReleaseResult.prototype.handleShow = function(roles) {
	var projectId=parseInt($('select.SelectProject option:selected').attr('value'));
	var projectName=$('select.SelectProject option:selected').text();
	RequestManager.getProjectReleases({"payload":{"projectId":projectId}},function(data,success){
		if(success){
			if(roles.contains(1)){
				$('table').append('<th>Release Name</th><th>Project</th><th>Delete</th>');
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
					});
					$('table').append("<tr style='text-align:center'><td>"+name+"</td><td>"+projectName+"</td><td><img class='delete' id="+id+" src='resources/img/delete.png' style='width:40%;cursor:pointer'/></td></tr>");
				});
				$('.delete').click(function(event){
					var releaseId=event.target.id;
					RequestManager.deleteProjectRelease({"payload":{"releaseId":releaseId}},function(data,success){
						if(success){
							$(event.target).parent().parent().remove();
							if($('table tr').length==0){
								$('#resultTable').empty();
							}
							alert('deleted');
						}
						else{
							alert(data.message);
						}
					});
				}.ctx(this));
			}
			else{
			$('table').append('<th>Release Name</th><th>Project</th>');
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
				});
				$('table').append("<tr style='text-align:center'><td id="+id+">"+name+"</td><td>"+projectName+"</td></tr>");
			});
			}
		}
		else{
			alert(data.message);
		}
	}.ctx(this));


}



