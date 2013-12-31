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
		else{
			$.ambiance({
				message : data.message,
				type : 'error'
			});
		}
	}.ctx(this));

	$('.SelectProject').change(function(){
		var projectId=parseInt($('select.SelectProject option:selected').attr('value'));
		if(roles.contains(1)){
			$('.add').empty();
			if(projectId!=0){
				
				$('.add').append('<div><button class="AddPR" id="AddPR">Add Release</button></div>');
				$('#AddPR').css({
					"background-color": "#669933",
					"border": "medium none",
					"color": "#FFFFFF",
					"font-weight": "bold",
					"left":"60%",
					"margin-top":"-11%",
					"position": "relative",
					"text-align": "center",
					"width": "15%"
				});
				$('.AddPR').click(function(){
					App.loadAddProjectRelease(roles);
				}.ctx(this));
			}
		}
		App.loadReleaseResult(roles);
	}.ctx(this));

}

