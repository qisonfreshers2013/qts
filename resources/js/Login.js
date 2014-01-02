/**
 * 
 */
function Login() {
	
	Loader.loadHTML('.rightContainer', 'Login.html', true, function(){
		this.handleShow();
	}.ctx(this));
}

var roleNames=new Array();

Login.prototype.handleShow = function() {
	$('.container').show();
	$('.rightContainer').show();
	$('#userId').focus();
	
	$('.rightContainer').keyup(function (event) {
		  if (event.keyCode == 13) {
			  if(this.validateLogin($('input.userId').val(),$('input.password').val())){		
					this.authenticate();
					}
		  }
		 }.ctx(this));
	

	
//	$("#userId").blur(function(){
//		//$(".error").hide();			
//	}.ctx(this));
//	
//	$("#password").blur(function(){
//		//$(".error").hide();		
//	}.ctx(this));	
	
	$(".submitLogin").click(function(){
		$(".error").hide();
	//var isValidateUserId = this.validateEmail($('input.userId'));
	//	var isValidatePassword = this.validatePassword($('input.password'));		
		if(this.validateLogin($('input.userId').val(),$('input.password').val())){		
		this.authenticate();
		}
	}.ctx(this));
		
	$(".clearLogin").click(function(){		
		$('#userId').focus();
		$(".error").hide();
	}.ctx(this));
	
	
	$(".forgotPasswordLink").click(function(){	
		$(".error").empty();
		this.openEmailDialogBox();
	}.ctx(this));	
	
	$("button.submitEmail").click(function(){
		$(".error").hide();
		if(this.validateEmail( $('.emailToSend'))){	
		$(".error").hide();
		var email = $('.emailToSend').val();	
		this.sendMail(email);
		
		}
		else{
			 $('.emailToSend').focus();			
			console.log("Email not Validated");
		}
	}.ctx(this));
	
	$("button.forgotButton").click(function(){
		$("input.emailToSend" ).val("");
	}.ctx(this));
	$("#forgotPasswordModal.documentElement").keyup(function (event) {
		  if (event.keyCode == 13) {
			  if(this.validateLogin($('input.userId').val(),$('input.password').val())){		
					this.authenticate();
					}
		  }
		 }.ctx(this));
	
	
}
Login.prototype.authenticate = function() {	
	
	var input = {"payload":{"authType":"REGULAR",
							"email":$('.userId').val(),
							"password":$('.password').val()
							}};
	
	RequestManager.authenticate(input, function(data, success) {
		if(success){
		 roleNames=data.roleNames;
		      var  token = data.sessionToken;
		      setCookie('qtsSessionId', token, null);
		      if(data.user.nickName == null  || data.user.nickName.trim().length < 1){
		    	 App.loadWelcome(data.user.lastName);
		      }
		      else{		    	
		    	  	App.loadWelcome(data.user.nickName);
		    	  }		     
		      App.loadOptions();
		      App.loadQisonLogo();

		}else{
			
			//alert('Fail to login :'+ data.message);
			$.ambiance({
			    message : "Fail : "+data.message,
			    type : 'error'
			   });
			$( "button#clearLogin" ).trigger("click");			
	}
		
		
	}.ctx(this));
}
	
Login.prototype.sendMail = function(email){
	var input = {"payload":{"email":email}};
	RequestManager.sendMail(input,function(data,success){
		if(success){
			$("input.emailToSend" ).val("");	
			$.ambiance({
				  message : "Success : Mail is sent = "+data,
				  type : 'success'
				});
		$('#forgotPasswordModal').modal('hide');
		
		}
		else{
			$.ambiance({
			    message : "Fail : "+data.message,
			    type : 'error'
			   });
		$("input.emailToSend" ).val("");
		}
	}.ctx(this));
}

Login.prototype.openEmailDialogBox = function() {	
	$(".error").hide();
	console.log("modal");
	$('#forgotPasswordModal').modal('show');	
}



Login.prototype.validateLogin = function(email,password){
	$(".error").hide();
	console.log(email+" validation "+password);
    var isValid = false;
    var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,})?$/;
  //  var emailVal = email;
    if(email == "" || email == null) {
    	$('.userId').focus();
    	$('.userId').after('<span class = "error" style = "color:red" >UserId can not be null</span>');
        isValid = false;
    }

    else if(!emailReg.test(email)) {
    	$('.userId').focus();
    	$('.userId').after('<span class = "error"  style = "color:red" >Enter the valid UserId</span>');
        isValid = false;
    }
    else if (email.length > 128){
    	$('.userId').focus();
    	$('.userId').after('<span class = "error"  style = "color:red" >Maximum length of Email is 128</span>');
        isValid = false;    	
    }
	 else if(password.trim().length < 6 ){
		$('.password').focus();
		$('.password').after('<span class = "error" style = "color:red" >Minimum length of passsword is 6</span>');
//{
//			  $( this ).after.css( "display", "inline" ).fadeOut( 1000 );
//			});
        isValid = false;
	}
	else if(password.trim().length  > 128)
		{
			$('.password').focus();
			$('.password').after('<span class = "error" style = "color:red" >Maximum length of passsword is 128</span>');		
		}
	    else
		{
		$(".error").empty();
		isValid = true;    
		}
	return isValid; 
}

Login.prototype.validateEmail = function(emailRef){
	var emailVal = emailRef.val();
	console.log(emailVal+" validation ");
	$(".error").hide();
    var isValid = false;
    var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,})?$/;
   
    if(emailVal == '') {
    	emailRef.focus();
    	emailRef.after('<span class = "error" style = "color:red" >Email can not be null</span>');
        isValid = false;
    }

    else if(!emailReg.test(emailVal)) {
    	emailRef.focus();
    	emailRef.after('<span class = "error"  style = "color:red" >Enter the valid Email</span>');
        isValid = false;
    }	else if (emailVal.length > 128){
    	emailRef.focus();
    	emailRef.after('<span class = "error"  style = "color:red" >Maximum length of Email is 128</span>');
        isValid = false;    	
    }
	    else
		{
			$(".error").empty();
			isValid = true;    
		}
	return isValid;  
}

Array.prototype.contains = function(k) {
    for(var p in this){
        if(this[p] === k){
            return true;
        }
    }
    return false;
}
var Login= new Login();




//Login.prototype.validatePassword = function(password){
//console.log(password+" validation ");
//$(".error").hide();
//    var isValid = false;   
//    if(password.length < 6 ){
//    	$(".error").show();
//		$('p.error').text("minimum password length is 6 ");
//        isValid = false;
//	}
//	    else
//		{   
//			$(".error").hide();
//			isValid = true;    
//		}
//	return isValid; 
//}

