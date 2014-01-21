/**
 * Author: N V Siva Reddy
 */

function SearchProjectRelease() {
	Loader.loadHTML('.container', 'SearchProjectRelease.html', false, function(){
		this.handleShow();
	}.ctx(this));
}

SearchProjectRelease.prototype.handleShow = function() {
	$("#ambiance-notification").empty();
	$('.SelectProject').focus();
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
				$('.SelectProject').append("<option value="+id+" title="+name+">"+name.ellipses(15)+"</option>");
			});
		}
		else{
			$.ambiance({
				message : data.message,
				type : 'error'
			});
		}
	}.ctx(this));
	
	
	$('#SelectProject').keyup(function(event){
		$("#ambiance-notification").empty();
		if(event.keyCode==37||event.keyCode==38||event.keyCode==39||event.keyCode==40){
//			$('.add').empty();
			$('.SelectProject').trigger('change');
//			this.results();	
		}
		
	}.ctx(this));

	$('.SelectProject').change(function(){
		$("#ambiance-notification").empty();
		$('.add').empty();
		$('.result').empty();
		this.results();
	}.ctx(this));
	

}

SearchProjectRelease.prototype.results=function(){
	var projectId=parseInt($('select.SelectProject option:selected').attr('value'));
	
	if(projectId!=0){
	if(roleNames.contains('ADMIN')){


			
			$('.add').append('<div><button type="button" class="AddPR" id="AddPR">Add Release</button></div>');
			$('#AddPR').css({
				"background-color": "#669933",
				"border": "medium none",
				"border-radius":"5px",
				"color": "#FFFFFF",
				"font-weight": "bold",
				"left":"43%",
				"margin-top":"-10.6%",
				"position": "relative",
				"text-align": "center",
				"width": "15%",
				"height":"28px",
				"font-size":"16px"
			});
			$('.AddPR').click(function(){
				$("#ambiance-notification").empty();
				App.loadAddProjectRelease();
			}.ctx(this));
			
		}
	App.loadReleaseResult();
		
	}
	else{
		$.ambiance({
			message : "Please Select One Project",
			type : 'error'
		});
	}
	
	
}

