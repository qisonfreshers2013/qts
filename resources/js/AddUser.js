/**
 * 
 */
function AddUser() {	
	Loader.loadHTML('#container', 'AddUser.html', false, function(){
		this.handleShow();
	}.ctx(this));
}

AddUser.prototype.handleShow = function() {
	 $("#ambiance-notification").empty();
	
	$('input.email').keyup(function(){
			$('input.userIdTextAU').val($('input.email').val()) ; 
			}.ctx(this));
	$('input.email').blur(function(){
		$('input.userIdTextAU').val($('input.email').val()) ; 
		}.ctx(this));
	$('div#innerAddUserFormRight').keyup(function(event){
		if(event.keyCode == 13){
			$('button#submit').trigger("click");
		}
	}.ctx(this));
	
	$('.submit').click(function(){
		if(this.validateName($('input.firstName'))){
			if(this.validateName($('input.lastName'))){
				if(this.validateNickname($('input.nickname'))){
					if(this.validateEmail($('input.email'))){
						if(this.validateEmployeeId($('input.employeeId'))){
							if(this.validateDesignation($('input.designation'))){
								if(this.validateLocation($('input.location'))){
									if(this.validateUserId($('input.userIdTextAU'),$('input.email'))){
										if(this.validatePassword($('input.password'))){
											if(this.validateConfirmPassword($('input.confirmPassword'),$('input.password'))){
												this.addUser();													
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
	}.ctx(this));
	$('.clear').click(function() {
		 $("#ambiance-notification").empty();
		$('.error').remove();
	});
	
}

AddUser.prototype.addUser = function() {
	var input = {"payload":{"firstName":$('.firstName').val(),
		"lastName":$('.lastName').val(),
		"nickName":$('input.nickname').val(),
		"gender":$( "select.gender option:selected").val(),
		"email":$('.email').val(),
		"employeeId":$('.employeeId').val(),
		"designation":$('.designation').val(),
		"location":$('.location').val(),
		"userId":$('.userIdTextAU').val(),
		"password":$('.password').val(),
		"confirmPassword":$('.confirmPassword').val()
	}};
	
	RequestManager.addUser(input, function(data, success) {
		if(success){
			$( "select.gender option").val("male");
			$("#innerAddUserFormRight").find('input').val('');
			$.ambiance({
				  message : "Successfully Added",
				  type : 'success'
				});
			
		}else{
			
			//alert("failed"+data.message);
			$.ambiance({
			    message : "Fail : "+data.message,
			    type : 'error'
			   });
		}
	}.ctx(this));
}


AddUser.prototype.validateName = function(name){
	var isValid = false;
//	 var nameReg = /^[A-Za-z\s]+$/;
	 var nameReg = /^[A-Za-z]+([ {1}][A-Za-z]+)*$/g;
        $('.error').hide();
	    var nameVal = name.val();
	    if(nameVal.trim().length <= 0 || nameVal == null) {
	    	name.after('<span class = "error" style = "color:red"  >Name can'+"'"+'t be empty</span>');
	        isValid = false;
	    }
	    else if(!nameReg.test(nameVal)) {
	    	name.after('<span class="error" style = "color:red" >Invalid name</span>');
	        isValid = false;
	    }
	    else if(nameVal.length > 128){
	    	name.after('<span class="error" style = "color:red">Maximum length of name is 128 characters</span>');
	        isValid = false;
	    }	    
	    else
		{
			$(".error").hide();
			isValid = true;    
		}

	    return isValid;
}

AddUser.prototype.validateEmail = function(email){
	var isValid = false;
	$(".error").hide();
    var isValid = false;
    var emailReg = /^[_A-Za-z]+[_A-Za-z0-9-]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$/; /*// /^([\w-\.]+@([\w-]+\.)+[\w-]{2,})?$/;*/

    var emailVal = email.val();
    if(emailVal.trim().length <= 0 || emailVal == null) {
    	email.after('<span class = "error" style = "color:red" >Email can'+"'"+'t  be null</span>');
        isValid = false;
    }

    else if(!emailReg.test(emailVal)) {
    	email.after('<span class="error" style = "color:red" >Email is invalid</span>');
        isValid = false;
    }
    else if (emailVal.length > 128){
    	
    	email.after('<span class = "error"  style = "color:red" >Maximum length of Email is 128</span>');
        isValid = false; 
    }
	    else
		{
			$(".error").hide();
			isValid = true;    
		}
	return isValid; 
}

AddUser.prototype.validateEmployeeId = function(empId){
	var isValid = false;
	var empIdReg = /^[A-Za-z0-9]+$/;
        $('.error').hide();
	    var empIdVal = empId.val();
	   if(empIdVal.length<1){
		   empId.after('<span class="error" style = "color:red" >Employee id can'+"'"+'t  be empty</span>');
	        isValid = false;
	   }
		   
	   else if(!empIdReg.test(empIdVal)) {
	    	empId.after('<span class="error" style = "color:red" >Employee id is invalid</span>');
	        isValid = false;
	     }
	     else if (empIdVal.length > 128){	    
	    	 empId.after('<span class = "error"  style = "color:red" >Maximum length of employee id is 128</span>');
	         isValid = false; 
	     }
	     else
			{
				$(".error").hide();
				isValid = true;    
			}
	    return isValid;
}

AddUser.prototype.validateDesignation = function(designation){
	var isValid = false; 
	var designationReg = /^[A-Z]+([ {1}][A-Z]+)*$/;
       $('.error').hide();
	    var designationVal = designation.val();
	    if(designationVal.trim().length < 1 ){
	    	designation.after('<span class="error" style = "color:red">Designation can'+"'"+'t  be null</span>');
	    	isValid = false;
	    }
	    else if(!designationReg.test(designationVal)) {
	    	designation.after('<span class="error" style = "color:red">Invalid Designation,it  must be in capital letters</span>');
	        isValid = false;
	    }
	    else if(designationVal.length > 128 ){
	    	designation.after('<span class="error" style = "color:red">Maximum length of designation is 128</span>');
	    	isValid = false;
	    }	    
	    else
		{
			$(".error").hide();
			isValid = true;    
		}
	    return isValid;
}

AddUser.prototype.validateNickname= function(nickname){
	var isValid = false;
	var nicknameReg = /^[A-Za-z]+([ {1}][A-Za-z]+)*$/g;
       $('.error').hide();
	    var nicknameVal = nickname.val();
	    if(nicknameVal.length < 1){
	    	$('.error').hide();
    	 	isValid = true;
	    }
	    else if(!nicknameReg.test(nicknameVal)) {
	    	nickname.after('<span class="error" style = "color:red">Nickname is invalid</span>');
	        isValid = false;
	    }
	    else  if(nicknameVal.length > 128) {
	    	nickname.after('<span class="error" style = "color:red">Maximum length of nickname is 128</span>');
	        isValid = false;
	    }
	    else {
	    	 	$('.error').hide();
	    	 	isValid = true;
	    	}
	    return isValid;
}

AddUser.prototype.validateLocation= function(location){
	var isValid = false;
	$(".error").hide();
	var locationReg =  /^[A-Za-z]+([ {1}][A-Za-z]+)*$/g;
     
	    var locationVal = location.val();	
	    if(locationVal.length < 1){
	    	  $('.error').hide();
	    	isValid = true;
	    }
	    else if(!locationReg.test(locationVal)) {
	    	location.after('<span class="error" style = "color:red">Location is invalid</span>');
	        isValid = false;
	    }
	    else  if(locationVal.length > 128) {
	    	
	    	location.after('<span class="error" style = "color:red">Maximum length of location is 128</span>');
	        isValid = false;
	    }
	    else{
	    	$('.error').hide();
	    	isValid = true;
	    }
	    return isValid;
}

AddUser.prototype.validateUserId = function(userId,email){
	var isValid = false;
	$(".error").hide();
	if(userId.val()!= email.val()){
		userId.after('<span class="error" style = "color:red" >User id must be equal to email </span>');
		isValid = false;
	}
	else
		isValid = true;
	return isValid;
}


AddUser.prototype.validatePassword = function(password){
	console.log(password+" validation ");	
	$(".error").hide();
    var isValid = false;  
    if(password.val().length < 1)
    	{
    	isValid = false;
    	password.after('<span  class = "error" style = "color:red" >Password can'+"'"+'t  be empty</span>');
    	}    
    else if(password.val().length < 6 ){
		password.after('<span  class = "error" style = "color:red" >Minimum length of password is 6</span>');
        isValid = false;
	}    
    else if(password.val().length > 128 ){
		password.after('<span  class = "error" style = "color:red" >Maximum length of password is 128</span>');
        isValid = false;
	}
    else if(password.val().indexOf(' ') >= 0){
    	password.after('<span  class = "error" style = "color:red" >Invalid Password</span>');
        isValid = false;
    }
	    else		
		{
			$(".error").hide();
			isValid = true;    
		}
	return isValid; 
}

AddUser.prototype.validateConfirmPassword = function(confirmPassword,password){
	var isValid = false;
	$(".error").hide();
	if(confirmPassword.val() != password.val()){
		confirmPassword.after('<span  class = "error" style = "color:red" >Confirm password must be equal to password</span>');
		isValid = false;
	}
	else
		isValid = true;	
	return isValid;
	
}


//var AddUser= new AddUser();