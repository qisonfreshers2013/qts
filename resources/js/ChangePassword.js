/**
 * 
 */
/**
 * 
 */

function ChangePassword(){
	Loader.loadHTML('#changePasswordModal', 'ChangePassword.html', true, function(){
		document.body.style.overflow = "hidden";
		this.dbPassword = "";
		this.handleShow();			
	}.ctx(this));
}

ChangePassword.prototype.handleShow = function(){	
	this.getPassword();
//	var input = {"payload":{}};
//	
//	RequestManager.getLoginUserDetails(input,function(data,success){
//		if(success){
//			dbPassword = data.password;	
//		}
//		else{
//			$.ambiance({
//			    message : "Fail : "+data.message,
//			    type : 'error'
//			   });
//			}
//		}.ctx(this));		
	$('#changePasswordModal').modal('show');	
	$('#changePasswordModal').on('show', function () {
		$('#oldPasswordTextCP').focus();
//		document.body.style.overflow = "hidden";
	  //  if (!data) return e.preventDefault() // stops modal from being shown
	}.ctx(this));
	$('#formDiv').keyup(function (event) {
		  if (event.keyCode == 13) {
				$(".error").remove();
				$( "button#submitPassword" ).trigger("click");
		}
		  if (event.keyCode == 27) {
				$(".error").remove();
				$('#myProfileInnerForm,#submitMyProfileDiv,#clearMyProfileDiv,#changePasswordDiv').find('*').prop('disabled',false);
				$("button#closeCPbutton" ).trigger("click");	
			//	document.body.style.overflow = "visible";
				//$( "button#clearCP" ).trigger("click");					
		}
		  
		}.ctx(this));	
	$('button#submitPassword').click(function(){	
		
		  var oldPassword = $('input.oldPasswordTextCP');
		  var password = $('input.passwordTextCP');
		  var confirmPassword = $('input.confirmPasswordTextCP');
		  if(oldPassword.val().length == 0){
			  $.ambiance({
			       message : "Old password can't be null",
			       type : 'error'
			      }); 
		  }else if(oldPassword.val() != dbPassword){	
			  
			   $.ambiance({
			       message : "Invalid old password",
			       type : 'error'
			      }); 
		  }				  
		  else if(password.val().length<1){
			  $.ambiance({
			       message : "New Password can't be null",
			       type : 'error'
			      });
		  }
			  else if(password.val().length<6){
			   $.ambiance({
			       message : "Minimum length of new password is 6 characters",
			       type : 'error'
			      }); 			   
			  } else if(oldPassword.val().length>128){
			   $.ambiance({
			       message : "Maximum length of new password is 128 characters",
			       type : 'error'
			      });			   
			  }
			  else if(password.val().indexOf(' ') >= 0){
				  $.ambiance({
				       message : "Invalid new Password",
				       type : 'error'
				      });
			    }else if(password.val()!=confirmPassword.val()){
			     $.ambiance({
			       message : "Confirm password must equal to password",
			       type : 'error'
			      });
			   
			  }else if(dbPassword==password.val()){
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
	
	$("button#closeCPbutton" ).click(function(){		
		document.body.style.overflow = "visible";
		$('#myProfileInnerForm,#submitMyProfileDiv,#clearMyProfileDiv,#changePasswordDiv').find('*').prop('disabled',false);
		$( "button#clearCP" ).trigger("click");
		 $("#ambiance-notification").empty();
	}.ctx(this));
		
	$(document.documentElement).keyup(function (event) {
	    if (event.keyCode == 27)  {
			$(".error").remove();
			$('#myProfileInnerForm,#submitMyProfileDiv,#clearMyProfileDiv,#changePasswordDiv').find('*').prop('disabled',false);
			document.body.style.overflow = "visible";			
	}	    
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
			document.body.style.overflow = "visible";	   
		$('#changePasswordModal').modal('hide');
		$( "input.closeCPbutton" ).trigger( "click");		
		$.ambiance({
			  message : "Success : Password is changed,Please login with New Password",
			  type : 'success'
			});
		this.logout();
	
		}
		else{
			$.ambiance({
			    message : "Fail : "+data.message,
			    type : 'error'
			   });	
		}
	}.ctx(this));
}

ChangePassword.prototype.getPassword = function(){	
	var input = {"payload":{}};
	RequestManager.getLoginUserDetails(input,function(data,success){
		if(success){
			dbPassword = data.password;	
		}
		else{
			$.ambiance({
			    message : "Fail : "+data.message,
			    type : 'error'
			   });
			}
		}.ctx(this));
			
	}

ChangePassword.prototype.logout = function(){
	RequestManager.logout({},function(data,success){
		if(success){
			setTimeout(function(){
				document.location.reload();
				},1000);
				}
		else{
			$.ambiance({
			    message : "Fail : "+data.message,
			    type : 'error'
			   });		
		}
	}.ctx(this));
}

ChangePassword.prototype.validatePassword = function(passwordRef){
	var password = passwordRef.val();
	
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