/**
 * 
 */
function AddUser() {
	Loader.loadHTML('#container', 'AddUser.html', false, function(){
		this.handleShow();
	}.ctx(this));
}

AddUser.prototype.handleShow = function() {
//	$('.container').show();
	
//	$('input.firstname').keyup(function(){
//		this.validateName();
//	}.ctx(this));
//	$('input.email').blur(function(){
//			$('input.userId').value = $('input.email').value ; }.ctx(this));
	
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
	
	$('.testHandler').click(function(){
		this.testHandler();
	}.ctx(this));
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
		"userId":$('.userId').val(),
		"password":$('.password').val(),
		"confirmPassword":$('.confirmPassword').val()
	}};
	
	RequestManager.addUser(input, function(data, success) {
		if(success){
			alert("success" + "  user added  "+data);
			$( "input#clear" ).trigger( "click" );
		}else{
			
			alert("failed"+data.message);
		}
	}.ctx(this));
}

AddUser.prototype.testHandler = function() {
	RequestManager.testHandler({},function(data, success) {
		if(success){
			alert(data);
		}else{
			alert("failed");
		}
	}.ctx(this));
}

AddUser.prototype.validateName = function(name){
	var isValid = false;
	 var nameReg = /^[A-Za-z\s]+$/;
        $('.error').hide();
	    var nameVal = name.val();
	    if(nameVal.trim().lenth <= 0 && nameVal == null) {
	    	name.after('<span class = "error"><img style = "height:2.5%;width:2.5%;"src = "resources/img/wrong.png"></span>');
	        isValid = false;
	    }
	    else if(!nameReg.test(nameVal)) {
	    	name.after('<span class="error"><img style = "height:2.5%;width:2.5%;"src = "resources/img/wrong.png"></span>');
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
    var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,})?$/;

    var emailVal = email.val();
    if(emailVal == '') {
    	email.after('<span class = "error"><img style = "height:2.5%;width:2.5%;"src = "resources/img/wrong.png"></span>');
        isValid = false;
    }

    else if(!emailReg.test(emailVal)) {
    	email.after('<span class="error"><img style = "height:2.5%;width:2.5%;"src = "resources/img/wrong.png"></span>');
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
	     if(!empIdReg.test(empIdVal)) {
	    	empId.after('<span class="error"><img style = "height:2.5%;width:2.5%;"src = "resources/img/wrong.png"></span>');
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
	var designationReg = /^[A-Z]+$/;
       $('.error').hide();
	    var designationVal = designation.val();
	    if(!designationReg.test(designationVal)) {
	    	designation.after('<span class="error"><img style = "height:2.5%;width:2.5%;"src = "resources/img/wrong.png"></span>');
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
	var isValid = true;
	var nicknameReg = /^[A-Za-z\s]*$/;
       $('.error').hide();
	    var nicknameVal = nickname.val();
	    
	    if(!nicknameReg.test(nicknameVal)) {
	    	nickname.after('<span class="error"><img style = "height:2.5%;width:2.5%;"src = "resources/img/wrong.png"></span>');
	        isValid = false;
	    }	
	    else 
	    	{
	    	 	$('.error').hide();
	    	 	isValid = true;
	    	}
	    return isValid;
}

AddUser.prototype.validateLocation= function(location){
	var isValid = false;
	$(".error").hide();
	var locationReg = /^[A-Za-z\s]*$/;
       $('.error').hide();
	    var locationVal = location.val();	    
	    if(!locationReg.test(locationVal)) {
	    	location.after('<span class="error"><img style = "height:2.5%;width:2.5%;" src = "resources/img/wrong.png"></span>');
	        isValid = false;
	    }	
	    else
	    	isValid = true;
	    return isValid;
}

AddUser.prototype.validateUserId = function(userId,email){
	var isValid = false;
	$(".error").hide();
	if(userId.val()!= email.val()){
		userId.after('<span class="error"><img style = "height:2.5%;width:2.5%;" src = "resources/img/wrong.png"></span>');
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
    if(password.val().trim().length < 6 ){
		password.after('<span  class = "error"><img style = "height:2.5%;width:2.5%;" src = "resources/img/wrong.png"></span>');
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
		confirmPassword.after('<span  class = "error"><img style = "height:2.5%;width:2.5%;" src = "resources/img/wrong.png"></span>');
		isValid = false;
	}
	else
		isValid = true;
	
	return isValid;
	
}


//var AddUser= new AddUser();