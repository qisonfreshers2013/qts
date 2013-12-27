/**
 * 
 */
function Login() {
	Loader.loadHTML('.rightContainer', 'Login.html', true, function(){
		this.handleShow();
	}.ctx(this));
}

Login.prototype.handleShow = function() {
	$('.container').show();
	$('.rightContainer').show();
	$('#userId').focus();
	
	$(document.documentElement).keyup(function (event) {
		  if (event.keyCode == 13) {
		   this.testLogin();
		  }
		 }.ctx(this));
	
	
//	$("#userId").blur(function(){
//		//$(".error").hide();			
//	}.ctx(this));
//	
//	$("#password").blur(function(){
//		//$(".error").hide();		
//	}.ctx(this));	
	
	$(".submit").click(function(){
	//var isValidateUserId = this.validateEmail($('input.userId'));
	//	var isValidatePassword = this.validatePassword($('input.password'));		
		if(this.validateLogin($('input.userId').val(),$('input.password').val())){		
		this.authenticate();
		}
	}.ctx(this));
		
	$(".clear").click(function(){		
		$('#userId').focus();
		$(".error").hide();
	}.ctx(this));
	
	
	$(".forgotPasswordLink").click(function(){			
		this.openEmailDialogBox();
	}.ctx(this));	
	
	$("button.submitEmail").click(function(){	
		if(this.validateEmail( $('.emailToSend'))){	
		var email = $('.emailToSend').val();	
		this.sendMail(email);
		
		}
		else{
			 $('.emailToSend').focus();			
			console.log("Email not Validated");
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
		 var roleIds=data.roleIds;
		      var  token = data.sessionToken;
		      setCookie('qtsSessionId', token, null);
		      App.loadWelcome(data.user.nickName,roleIds);
		      App.loadOptions(roleIds);
		      App.loadQisonLogo(roleIds);

		}else{
			console.log('fail  :'+ data.message);
			alert('fail '+ data.message);
			$( "input#clear" ).trigger( "click");			
	}
	}.ctx(this));
}
	
Login.prototype.sendMail = function(email){
	var input = {"payload":{"email":email}};
	RequestManager.sendMail(input,function(data,success){
		if(success){
			alert("success mail is sent");
		$('#forgotPasswordModal').modal('hide');
		}
		else
		alert("fail :"+data.message);
		
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
    var emailVal = email;
    if(emailVal == "" || emailVal == null) {
    	$('.userId').focus();
    	$('.userId').after('<span class = "error" style = "color:red" >UserId can not be null</span>');
        isValid = false;
    }

    else if(!emailReg.test(email)) {
    	$('.userId').focus();
    	$('.userId').after('<span class = "error"  style = "color:red" >Enter the valid UserId</span>');
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
	    else
		{
		$(".error").hide();
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
    	$(".error").show();
    	emailRef.after('<span class = "error" style = "color:red" >Email can not be null</span>');
        isValid = false;
    }

    else if(!emailReg.test(emailVal)) {
    	$(".error").show();
    	emailRef.after('<span class = "error"  style = "color:red" >Enter the valid Email</span>');
        isValid = false;
    }	
	    else
		{
			$("p.error").hide();
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

