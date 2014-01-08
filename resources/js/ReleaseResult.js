/**
 * Author: N V Siva Reddy
 */

function ReleaseResult() {	
	Loader.loadHTML('.result', 'ReleaseResult.html', true, function(){
		this.handleShow();
	}.ctx(this));
}

ReleaseResult.prototype.handleShow = function() {
	var projectId=parseInt($('select.SelectProject option:selected').attr('value'));
	var projectName=$('select.SelectProject option:selected').attr('title');
	RequestManager.getProjectReleases({"payload":{"projectId":projectId}},function(data,success){
		if(success){
			if(roleNames.contains('ADMIN')){
				$('table').append('<tr><th>Release Name</th><th>Project</th><th>Delete</th></tr>');
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
					$('table').append("<tr style='text-align:center'><td title="+name+">"+name.ellipses(15)+"</td><td title="+projectName+">"+projectName.ellipses(15)+"</td><td><img class='deletePR' id="+id+" src='resources/img/delete.png' style='width:20px;cursor:pointer'/></td></tr>");
				});
				$('.deletePR').click(function(event){
					var releaseId=event.target.id;
					RequestManager.deleteProjectRelease({"payload":{"releaseId":releaseId}},function(data,success){
						if(success){
							$(event.target).parent().parent().remove();
							if($('table tr').length==1){
								$('#resultTable').empty();
							}
							$.ambiance({ 
							    message : "Deleted Successfully",
							    type : 'success'
							   });
						}
						else{
							$.ambiance({
							    message : "The Release which you are trying to delete is being referred by one of the Time Entry",
							    type : 'error'
							   });
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
				$('table').append("<tr style='text-align:center'><td id="+id+" title="+name+">"+name.ellipses(15)+"</td><td title="+projectName+">"+projectName.ellipses(15)+"</td></tr>");
			});
			}
		}
		else{
			$.ambiance({
			    message : data.message,
			    type : 'error'
			   });
		}
	}.ctx(this));


}



