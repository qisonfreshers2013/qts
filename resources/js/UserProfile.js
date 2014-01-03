/**
 * 
 */
function UserProfile(id) {
	Loader.loadHTML('#container', 'UserProfile.html', false, function(){
		this.handleShow(id);
	}.ctx(this));
}

UserProfile.prototype.handleShow = function(id) {
	
	var primaryId = id;
	$('.container').show();
	this.loadUserValues(id);
	
//	$('input.firstname').keyup(function(){
//		this.validateName();
//	}.ctx(this));
	
	
	$('div.userProfile').keyup(function(event){
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
									if(this.validateUserId($('input.userId'),$('input.email'))){
										if(this.validatePassword($('input.password'))){
											if(this.validateConfirmPassword($('input.confirmPassword'),$('input.password'))){
												
												this.saveUserProfile(primaryId);	
												
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
	
	
}


UserProfile.prototype.loadUserValues = function(id){	
	var input = {"payload":{"id":id}};
	console.log("user Id"+id);
	RequestManager.getUserDetails(input,function(data,success){
		if(success){
			console.log("success profile"+data);
			
			$('input.firstName').val(data.firstName);
			$('input.lastName').val(data.lastName);
			$('input.nickname').val(data.nickName);		
			if(data.gender)
				$('select.gender option[value=male]').attr('selected','selected');			
			else
				$('select.gender option[value=female]').attr('selected','selected');
			$('input.email').val(data.email);
			$('input.employeeId').val(data.employeeId);
			$('input.designation').val(data.designation);
			$('input.location').val(data.location);	
			$('input.userId').val(data.userId);
			$('input.password').val(data.password);
			$('input.confirmPassword').val(data.password);			
		}
		else{
			console.log("loaded my profile"+data.message);
			$.ambiance({
			    message : "Fail : "+data.message,
			    type : 'error'
			   });			
		}
		
	}.ctx(this));
}


UserProfile.prototype.saveUserProfile = function(id) {
			var input = {"payload":{
			"id":id,
			"firstName":$('.firstName').val(),
			"lastName":$('.lastName').val(),
			"nickName":$('input.nickname').val(),
			"gender":$( "select.gender option:selected").val(),
			"email":$('.email').val(),
			"employeeId":$('.employeeId').val(),
			"designation":$('.designation').val(),
			"location":$('.location').val(),
			"userId":$('.userId').val(),
			"password":$('.password').val(),
			"confirmPassword":$('.confirmPassword').val()
		}};
		
		RequestManager.updateUserDetails(input, function(data, success){
			if(success){
				alert('success : '+data);
				
			}
			else{				
				$.ambiance({
				    message :"Fail : "+ data.message,
				    type : 'error'
				   });
			}
		}.ctx(this));
	
}


UserProfile.prototype.validateName = function(name){
	var isValid = false;
//	 var nameReg = /^[A-Za-z\s]+$/;
	 var nameReg=/^[A-Za-z]+([ {1}][A-Za-z]+)*$/g;
       $('.error').hide();
	    var nameVal = name.val();
	    if(nameVal.trim().length <= 0 || nameVal == null) {
	    	name.after('<span class = "error" style = "color:red"  >Name can not be empty</span>');
	        isValid = false;
	    }
	    else if(!nameReg.test(nameVal)) {
	    	name.after('<span class="error" style = "color:red" >Invalid name</span>');
	        isValid = false;
	    }
	    else if(nameVal.length > 128){
	    	name.after('<span class="error" style = "color:red">maximum length of name is 128 characters</span>');
	        isValid = false;
	    }	    
	    else
		{
			$(".error").hide();
			isValid = true;    
		}

	    return isValid;
}

UserProfile.prototype.validateEmail = function(email){
	var isValid = false;
	$(".error").hide();
    var isValid = false;
    var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,})?$/;

    var emailVal = email.val();
    if(emailVal == '') {
    	email.after('<span class = "error" style = "color:red">Email is Empty</span>');
        isValid = false;
    }

    else if(!emailReg.test(emailVal)) {
    	email.after('<span class="error">Invalid email</span>');
        isValid = false;
    }	
	    else
		{
			$(".error").hide();
			isValid = true;    
		}
	return isValid; 
}

UserProfile.prototype.validateEmployeeId = function(empId){
	var isValid = false;
	var empIdReg = /^[A-Za-z0-9]+$/;
        $('.error').hide();
	    var empIdVal = empId.val();
	     if(!empIdReg.test(empIdVal)) {
	    	empId.after('<span class="error" style = "color:red" >EmployeeId is invalid</span>');
	        isValid = false;
	    }
	     else if (empIdVal.length > 128){	    
	    	 empId.after('<span class = "error"  style = "color:red" >Maximum length of employeeId is 128</span>');
	         isValid = false; 
	     }
	     else
			{
				$(".error").hide();
				isValid = true;    
			}
	    return isValid;
}



UserProfile.prototype.validateDesignation = function(designation){
	var designationReg = /^[A-Z]+$/;
    $('.error').hide();
	    var designationVal = designation.val();
	    if(designationVal.trim().length < 0 ){
	    	designation.after('<span class="error" style = "color:red">  Designation can not be empty</span>');
	    	isValid = false;
	    }
	    else if(!designationReg.test(designationVal)) {
	    	designation.after('<span class="error" style = "color:red">Designation must be in capitals</span>');
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

UserProfile.prototype.validateNickname= function(nickname){
	var isValid = true;
	var nicknameReg = /^[A-Za-z]*([ {1}][A-Za-z]*)*$/g;
       $('.error').hide();
	    var nicknameVal = nickname.val();
	    
	    if(!nicknameReg.test(nicknameVal)) {
	    	nickname.after('<span class="error" style = "color:red">Nickname is invalid</span>');
	        isValid = false;
	    }
	    else  if(nicknameVal.length > 128) {
	    	nickname.after('<span class="error" style = "color:red">Maximum length of nickname is 128</span>');
	        isValid = false;
	    }
	    else 
	    	{
	    	 	$('.error').hide();
	    	 	isValid = true;
	    	}
	    return isValid;
}

UserProfile.prototype.validateLocation= function(location){
	var isValid = false;
	$(".error").hide();
	var locationReg = /^[A-Za-z]*([ {1}][A-Za-z]*)*$/g;
       $('.error').hide();
	    var locationVal = location.val();	    
	    if(!locationReg.test(locationVal)) {
	    	location.after('<span class="error" style = "color:red">Location is invalid</span>');
	        isValid = false;
	    }
	    else  if(locationVal.length > 128) {
	    	nickname.after('<span class="error" style = "color:red">Maximum length of location is 128</span>');
	        isValid = false;
	    }
	    else
	    	isValid = true;
	    return isValid;
}

UserProfile.prototype.validateUserId = function(userId,email){
	var isValid = false;
	$(".error").hide();
	if(userId.val()!= email.val()){
		userId.after('<span class="error">User id must be equal to email</span>');
		isValid = false;
	}
	else
		isValid = true;
	return isValid;
}


UserProfile.prototype.validatePassword = function(password){
	console.log(password+" validation ");	
	$(".error").hide();
    var isValid = false;   
    if(password.val().trim().length < 6 ){
		password.after('<span  class = "error" style = "color:red" >minimum length of password is 6</span>');
        isValid = false;
	}
    else if(password.val().length > 128 ){
		password.after('<span  class = "error" style = "color:red" >Maximum length of password is 128</span>');
        isValid = false;
	}
	    else
		{
			$(".error").hide();
			isValid = true;    
		}
	return isValid; 
}

UserProfile.prototype.validateConfirmPassword = function(confirmPassword,password){
	var isValid = false;
	$(".error").hide();
	if(confirmPassword.val() != password.val()){
		confirmPassword.after('<span  class = "error">Confirm password and password must be equal</span>');
		isValid = false;
	}
	else
		isValid = true;
	
	return isValid;
	
}


//var UserProfile= new UserProfile();