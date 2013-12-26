/**
 * Ajay
 */
function ApproverSearch(){
	Loader.loadHTML('.searchTimeEntries', 'approverSearch.html', true, function() {
		this.handleShow();
	}.ctx(this))
}

ApproverSearch.prototype.handleShow=function(){
	$(".container").show();
	
	this.getProjects();
	this.getUsers();
	$(".userProjectId").click(function(){
		this.getUsers();
	});
    $(document).ready(function() {
        $(".from").datepicker();
        $(".to").datepicker();
    });
}.ctx(this)
ApproverSearch.prototype.getProjects=function(){
	 $('#userProjectId').empty();
	// $('#userProjectId').append('<option></option>');
	 RequestManager.getProjectsForUser({}, function(data, success) {
	  if(success){
	   var id=0;
	   var name='';
	   $.each(data,function(key1,value1){
	    $.each(value1,function(key2,value2){
	     if(key2=='id'){
	      id=value2;
	     }else{
	    	 if(key2=='name')
	      name=value2;
	     }
	    });
	    $('#userProjectId').append('<option value='+id+'>'+name+'</option>');
	   });
	  }else{
	   alert(data.message);
	  }
	 }.ctx(this));
	}
ApproverSearch.prototype.getUsers=function(){
	var projectId;
	if($(".userProjectId").val()!=''){
		projectId=$(".userProjectId").val();
		$('.userId').empty();
		 $('.userId').append('<option></option>');
		 RequestManager.getProjectUsers({"payload":{"projectId":projectId}}, function(data, success) {
		  if(success){
		   var id=0;
		   var name='';
		   $.each(data,function(key1,value1){
		    $.each(value1,function(key2,value2){
		     if(key2=='id'){
		      id=value2;
		     }else{
		    	 if(key2=='firstName')
		      name=value2;
		     }
		    });
		    $('.userId').append('<option value='+id+'>'+name+'</option>');
		   });
		  }else{
		   alert(data.message);
		  }
		 }.ctx(this));
	}else{
		alert("select Project");
	} 

	 }