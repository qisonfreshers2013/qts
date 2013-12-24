/**
 * 
 */
function SearchUser(roles){
	Loader.loadHTML('.container', 'SearchUser.html', true, function(){
		 $('.resultsContainer').hide();		
		this.handleShow(roles);
	}.ctx(this));
}
SearchUser.prototype.handleShow = function(roles){
	// show loading symbol
	// get the data for the default search
	   // get the data from request manager call
	// show the data 
	// hide the hiding symbol
	
	this.loadEmployeeIds();
	$('.container').show();
	$('.resultsContainer').show();
	$('input.search').click(function(){		
		this.search();
	}.ctx(this));
}
SearchUser.prototype.search = function(){
	var nickname = $('input.nickname').val();
	var email = $('input.email').val();
	var employeeId = $('select.employeeId').val();
	var designation = $('input.designation').val();
	
	if(designation == "" || designation.trim().length<1)
		designation = null;
//	else
//		this.validateDesignation();
	if(email == "" || email.trim().length<1)
		email = null;
//	else
//		this.validateEmail();
	if(employeeId == "" || employeeId.trim().length<1)
		employeeId = null;
//	else
//		this.validateEmployeeId();
	if(nickname == "" || nickname.trim().length<1)
		nickname = null;	
//	else
//		this.validateNickname();
	
	
	var input = {"payload":{
		"nickName":nickname,		
		"email":email,
		"employeeId":employeeId,
		"designation":designation	
	}};
	RequestManager.search(input,function(data,success){		
		if(success){
			if(roles.contains(1)){
			console.log("success : search results");
			App.loadSearchResults(data);
			}
			else{

			}
		}	
		else{
			//App.loadSearchResults();
			alert('failed'+data.message);		
			}
	}.ctx(this));	
}

SearchUser.prototype.loadEmployeeIds = function(){
	var input = {"payload":{}};
	 $('select.employeeId').empty();
	 $('select.employeeId').append('<option value="">--select--</option>');
	RequestManager.getEmployeeIds(input,function(data,success){		
		if(success){
			console.log("success : search results"+data.message);
			for(var i=0 ; i<data.length; i++){
				 $('select.employeeId').append('<option value='+data[i]+'>'+data[i]+'</option>');	
			}
		
		}	
		else{
			//App.loadSearchResults();
			alert('failed');		
			}
	}.ctx(this));	
}

//
//Search.prototype.validateName = function(name){
//	var isValid = false;
//	 var nameReg = /^[A-Za-z\s]+$/;
//        $('.error').hide();
//	    var nameVal = name.val();
//	    if(nameVal.trim().lenth <= 0 && nameVal == null) {
//	    	name.after('<span class = "error"><img style = "height:2.5%;width:2.5%;"src = "wrong.png"></span>');
//	        isValid = false;
//	    }
//	    else if(!nameReg.test(nameVal)) {
//	    	name.after('<span class="error"><img style = "height:2.5%;width:2.5%;"src = "wrong.png"></span>');
//	        isValid = false;
//	    }	
//	    else
//		{
//			$(".error").hide();
//			isValid = true;    
//		}
//
//	    return isValid;
//}

//
// AllocateUsersToProject.prototype.getProjects=function(){
//	 $('#projectName').empty();
//	 $('#projectName').append('<option value=0>--select--</option>');
//	 RequestManager.getProjects({}, function(data, success) {
//	  if(success){
//	   var id=0;
//	   var name='';
//	   $.each(data,function(key1,value1){
//	    $.each(value1,function(key2,value2){
//	     if(key2==0){
//	      id=value2;
//	     }else{
//	      name=value2;
//	     }
//	    });
//	    $('#projectName').append('<option value='+id+'>'+name+'</option>');
//	   });
//	  }else{
//	   alert(data.message);
//	  }
//	 }.ctx(this));
//	}