/**
 * 
 */

function ChangePassword(){
	Loader.loadHTML('.container', 'ChangePassword.html', false, function(){		
		this.handleShow();			
	}.ctx(this));
}
ChangePassword.prototype.handleShow = function(){
	console.log('calling modelshow');	
	$('.changePasswordContainer').modal('show');
	console.log('called modelshow');
	$('button#submitPassword').click(function(){
		var oldPassword = $('input.oldPasswordText');
		var password = $('input.passwordText');
		var confirmPassword = $('input.confirmPasswordText');
		
		var isOldPasswordValidated  = this.validatePassword(oldPassword);
		var isPasswordValidated = this.validatePassword(password);
		var isConfirmPasswordValidated = this.validateConfirmPassword(confirmPassword,password);
		
		if(isOldPasswordValidated&&isPasswordValidated&&isConfirmPasswordValidated)
		this.changePassword(oldPassword.val(),password.val(),confirmPassword.val());
		
	}.ctx(this));	
	
}


ChangePassword.prototype.changePassword = function(oldPassword,password,confirmPassword){
	
	var input = {"payload":
				{"oldPassword":oldPassword,
				"password":password,
				"confirmPassword":confirmPassword}
				};
	
	RequestManager.changePassword(input,function(data,success){
		if(success){
			alert("password is changed");
		$('#changePasswordContainer').modal('hide');
		}
		else
			alert("fail to change : "+data.message);
		
	});
}


ChangePassword.prototype.validatePassword = function(passwordRef){
	var password = passwordRef.val();
	console.log(password+" validation ");
	$(".error").hide();
    var isValid = false;   
    if(password.length < 6 ){
		$('.passwordRef').focus();
		passwordRef.after('<span  class = "error"><img style = "height:2.5%;width:2.5%;" src = "resources/img/wrong.png"></span>');
        isValid = false;
	}else{
			$(".error").hide();
			isValid = true;    
	}
	return isValid; 
	
}
ChangePassword.prototype.validateConfirmPassword = function(confirmPasswordRef,passwordRef){
	var isValid = false;
	$(".error").hide();
	if(confirmPasswordRef.val() != passwordRef.val()){
		$('.confirmPasswordRef').focus();
		confirmPasswordRef.after('<span  class = "error"><img style = "height:2.5%;width:2.5%;" src = "resources/img/wrong.png"></span>');
		isValid = false;
	}
	else
		isValid = true;	
	return isValid;
	
}