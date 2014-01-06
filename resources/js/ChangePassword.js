/**
 * 
 */
/**
 * 
 */

function ChangePassword(password){
	Loader.loadHTML('#changePasswordModal', 'ChangePassword.html', true, function(){	
		$('input.oldPasswordTextCP').focus();	
		this.handleShow(password);			
	}.ctx(this));
}
ChangePassword.prototype.handleShow = function(dbPassword){
	console.log('calling modelshow');	
	
	$('#changePasswordModal').modal('show');
	
	$('#changePasswordModal').on('show', function () {
		$('#userId').focus();
	  //  if (!data) return e.preventDefault() // stops modal from being shown
	});


	$('#changePasswordModal').keyup(function (event) {
		  if (event.keyCode == 13) {
				$(".error").remove();
				$( "button#submitPassword" ).trigger("click");
		}
	
		}.ctx(this));

	$('#changePasswordModal').keyup(function (event) {
		  if (event.keyCode == 27) {
				$(".error").remove();
				$("button#closeCPbutton" ).trigger("click");	
				$( "button#clearCP" ).trigger("click");
					
		}		
		}.ctx(this));
	
	console.log('called modelshow');
	$('button#submitPassword').click(function(){
		
		  var oldPassword = $('input.oldPasswordTextCP');
		  var password = $('input.passwordTextCP');
		  var confirmPassword = $('input.confirmPasswordTextCP');
		  if(oldPassword.val().length == 0){
			  $.ambiance({
			       message : "Old password can not be empty",
			       type : 'error'
			      }); 
		  }else if(dbPassword!=oldPassword.val()){
			   $.ambiance({
			       message : "Invalid old password",
			       type : 'error'
			      }); 
			   
			  }
		  else if(password.val().length<1){
			  $.ambiance({
			       message : "New Password can not be empty",
			       type : 'error'
			      });
		  }
			  else if(password.val().length<6){
			   $.ambiance({
			       message : "Minimum length of new password is 6 characters",
			       type : 'error'
			      }); 
			   
			  }else if(oldPassword.val().length>128){
			   $.ambiance({
			       message : "Maximum length of new password is 128 characters",
			       type : 'error'
			      });
			   
			  }else if(password.val()!=confirmPassword.val()){
			   $.ambiance({
			       message : "Confirm password must equal to password",
			       type : 'error'
			      });
			   
			  }else if(dbPassword==password.val()&&dbPassword==confirmPassword.val()){
			   $.ambiance({
			       message : " Old password and new password are same",
			       type : 'error'
			      });
			   
			  }else{
			   this.changePassword(oldPassword.val(),password.val(),confirmPassword.val());
			  }
//var isOldPasswordValidated  = this.validatePassword(oldPassword);
//var isPasswordValidated = this.validatePassword(password);
//var isConfirmPasswordValidated = this.validateConfirmPassword(confirmPassword,password);
//if(isOldPasswordValidated&&isPasswordValidated&&isConfirmPasswordValidated)
//this.changePassword(oldPassword.val(),password.val(),confirmPassword.val());
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
			$.ambiance({
				  message : "Success : Password is changed",
				  type : 'success'
				});
		$('#changePasswordModal').modal('hide');
		$( "input.closeCPbutton" ).trigger( "click");
		}
		else{
			$.ambiance({
			    message : "Fail : "+data.message,
			    type : 'error'
			   });	
		}
	}).ctx(this);
}


ChangePassword.prototype.validatePassword = function(passwordRef){
	var password = passwordRef.val();
	console.log(password+" validation ");
	$(".error").hide();
    var isValid = false; 
    if(password.length < 1){
    	$('.passwordRef').focus();
		passwordRef.after('<span  class = "error" style = "color:red" >Password can not be null</span>');
        isValid = false;
    }
    else if(password.length < 6 ){
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