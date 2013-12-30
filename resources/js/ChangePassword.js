/**
 * 
 */
/**
 * 
 */

function ChangePassword(){
	Loader.loadHTML('#changePasswordModal', 'ChangePassword.html', true, function(){		
		this.handleShow();			
	}.ctx(this));
}
ChangePassword.prototype.handleShow = function(){
	console.log('calling modelshow');	
	$('#changePasswordModal').modal('show');
	console.log('called modelshow');
	$('button#submitPassword').click(function(){
	
		var oldPassword = $('input.oldPasswordText');
		var password = $('input.passwordText');
		var confirmPassword = $('input.confirmPasswordText');
		if(this.validatePassword(oldPassword)){
			if(this.validatePassword(password)){
				if(this.validateConfirmPassword(confirmPassword,password)){
					this.changePassword(oldPassword.val(),password.val(),confirmPassword.val());
				}
			}
		}
		//var isOldPasswordValidated  = this.validatePassword(oldPassword);
		//var isPasswordValidated = this.validatePassword(password);
		//var isConfirmPasswordValidated = this.validateConfirmPassword(confirmPassword,password);
		
//		if(isOldPasswordValidated&&isPasswordValidated&&isConfirmPasswordValidated)
//		this.changePassword(oldPassword.val(),password.val(),confirmPassword.val());
		
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
		passwordRef.after('<span  class = "error" style = "color:red" >Minimum length of password is 6</span>');
        isValid = false;
	}
    else if(password.length > 128 ){
		$('.passwordRef').focus();
		passwordRef.after('<span  class = "error" style = "color:red;display:inline">Maximum length of password is 128</span>');
        isValid = false;
	}
    else{
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
		confirmPasswordRef.after('<span  class = "error" style = "color:red">Confirm password must equal to password</span>');
		isValid = false;
	}
	else
		isValid = true;	
	return isValid;	
}