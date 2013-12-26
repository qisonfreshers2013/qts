/**
 * 
 */
function SearchUser(roles){
	Loader.loadHTML('.container', 'SearchUser.html',false, function(){
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
	//$('.container').show();
	$('.resultsContainer').show();
	$('button.search').click(function(){			
		var nickname = $('input.nickname').val();
		var email = $('input.email').val();
		var employeeId = $('select.employeeId').val();
		var designation = $('input.designation').val();
		var isNicknameValidated = false;
		var isDesignationValidated = false;	
		var isEmailValidated = false;
		if(designation == "" || designation.trim().length<1){
			 isDesignationValidated = true;
			 designation = null;
		}
		else{		
			isDesignationValidated = this.validateDesignation($('input.designation'));
		}
		if(email == "" || email.trim().length<1){
			email = null;	
			isEmailValidated = true;
		}			
		else{
			isEmailValidated = 	this.validateEmail( $('input.email'));
		}			
		if(employeeId == "" || employeeId.trim().length<1){
			employeeId = null;
		}		
		if(nickname == "" || nickname.trim().length<1){
			isNicknameValidated = true;
			nickname = null;
		}	
		else{
			isNicknameValidated = this.validateNickname($('input.nickname'));	
		}
		if(isNicknameValidated&&isEmailValidated&&isDesignationValidated){		
		this.search(roles,nickname,email,designation,employeeId);
		}
	}.ctx(this));
}
SearchUser.prototype.search = function(roles,nickname,email,designation,employeeId){
	
	var input = {"payload":{
		"nickName":nickname,		
		"email":email,
		"employeeId":employeeId,
		"designation":designation	
	}};
	RequestManager.search(input,function(data,success){		
		if(success){			
			console.log("success : search results");
			App.loadSearchResults(data,roles);			
		}	
		else{
			alert('failed  :'+data.message);
			$('#resultsContainer').hide();
			
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
			
			alert('failed  :'+data.message);		
			}
	}.ctx(this));	
}

SearchUser.prototype.validateNickname= function(nickname){
	var isValid = true;
	var nicknameReg = /^[A-Za-z\s]*$/;
       $('.error').hide();
	    var nicknameVal = nickname.val();
	    
	    if(!nicknameReg.test(nicknameVal)) {
	    	nickname.focus().css("border-color","red");
	        isValid = false;
	    }	
	    else 
	    	{
	    	 	$('.error').hide();
	    	 	isValid = true;
	    	}
	    return isValid;
}

SearchUser.prototype.validateEmail = function(email){
	var isValid = false;
	$(".error").hide();
    var isValid = false;
    var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,})?$/;

    var emailVal = email.val();
    if(emailVal == '') {
    	email.focus().css("border-color","red");
        isValid = false;
    }

    else if(!emailReg.test(emailVal)) {
    	email.focus();
        isValid = false;
    }	
	    else
		{
			$(".error").hide();
			isValid = true;    
		}
	return isValid; 
}



SearchUser.prototype.validateDesignation = function(designation){
	var isValid = false; 
	var designationReg = /^[A-Z]+$/;
       $('.error').hide();
	    var designationVal = designation.val();
	    if(!designationReg.test(designationVal)) {
	    	designation.focus().css("border-color","red");
	        isValid = false;
	    }	
	    else
		{
			$(".error").hide();
			isValid = true;    
		}
	    return isValid;
}

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