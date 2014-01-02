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
			$('.error').empty();
		}	
		else{
			$('.error').empty();
			$.ambiance({
			    message : "Fail : "+data.message,
			    type : 'error'
			   });
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
			
			for(var i=0 ; i<data.length; i++){
				 $('select.employeeId').append('<option value='+data[i]+'>'+data[i]+'</option>');	
			}		
		}	
		else{
			$.ambiance({
			    message : "Fail : "+data.message,
			    type : 'error'
			   });	
			}
	}.ctx(this));	
}

SearchUser.prototype.validateNickname= function(nickname){
	var isValid = true;
	var nicknameReg = /^[A-Za-z]+([ {1}][A-Za-z]+)*$/g;
	//$(".error").show();
	    var nicknameVal = nickname.val();	    
	    if(!nicknameReg.test(nicknameVal)) {
	    	//nickname.focus().css("border-color","red");
	    	
	    	nickname.focus();
	    	$('p.error').text("Nickname is invalid");
	        isValid = false;
	    }
	    else if(nicknameVal.length > 128){
	    	//nickname.focus().css("border-color","red");
	    	$('p.error').text("Maximum length of nickname is 128");
	    }
	    else 
	    	{  // nickname.focus().css("border-color","blue");
	    	 	//$('.error').hide();
	    	 	isValid = true;
	    	}
	    return isValid;
}


SearchUser.prototype.validateEmail = function(email){
	var isValid = false;
	//$(".error").show();
 
    var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,})?$/;

    var emailVal = email.val();
    if(!emailReg.test(emailVal)) {
    	email.focus();
    	//email.addClass("focus");
    	
    	$('p.error').text("Invalid Email");
        isValid = false;
    }	
    else if (emailVal.length > 128){	  
    	//email.addClass("focus");
    	email.focus();
    	$('p.error').text("Maximum length of email is 128");
        isValid = false; 
    }
	    else
		{	//email.removeClass("focus");
			//$(".error").hide();			
			isValid = true;    
		}
    
	return isValid; 
}



SearchUser.prototype.validateDesignation = function(designation){
	var isValid = false; 
	var designationReg = /^[A-Z]+$/;
	//$(".error").show();
	    var designationVal = designation.val();
	    if(!designationReg.test(designationVal)) {
	    	$('p.error').text("Designation is invalid");
	    	//designation.focus().css("border-color","red");
	    	designation.focus();
	        isValid = false;
	    }
	    else if (designationVal.length > 128){	    
	    	//designation.focus().css("border-color","red");
	    	designation.focus();
	    	$('p.error').text("Maximum length of designation is 128");
	      isValid = false; 
	    }
	    else
		{  // designation.focus().css("border-color","blue");
			//$(".error").hide();
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