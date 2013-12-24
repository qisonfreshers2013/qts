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
	
	$(document.documentElement).keyup(function (event) {
		  if (event.keyCode == 13) {
		   this.testLogin();
		  }
		 }.ctx(this));
	
	
	$("#userId").blur(function(){
		this.validateEmail( $('#userId').val())	;			
	}.ctx(this));
	
	$("#password").blur(function(){
		this.validatePassword( $('#password').val())	;			
	}.ctx(this));	
	
	$(".submit").click(function(){
		if(this.validateLogin( $('.userId').val(), $('.password').val())){				
		this.authenticate();
		}
	}.ctx(this));
	
	$(".forgotPasswordLink").click(function(){			
		this.openEmailDialogBox();
	}.ctx(this));	
	
	$("button.submitEmail").click(function(){	
		if(this.validateEmail( $('.emailToSend').val())){	
		var email = $('.emailToSend').val();	
		this.sendMail(email);
		//$('.emailToSend').empty();
		//console.log("sendTo requsestManager"+email);
		//$('#forgotPasswordModal').modal('hide');
		}
		else{
			console.log("notValidated");
		}
	}.ctx(this));
	
}
Login.prototype.authenticate = function() {	
	
	var input = {"payload":{"authType":"REGULAR",
							"email":$('.userId').val(),
							"password":$('.password').val()}};
	
	RequestManager.authenticate(input, function(data, success) {
		if(success){
		 var roleIds=data.roleIds;
		      var  token = data.sessionToken;
		      setCookie('qtsSessionId', token, null);
		      App.loadWelcome(data.user.nickName);
		   App.loadOptions(roleIds);
		}else{
			console.log('fail '+ data.message);
			alert('fail '+ data.message);
			$( "input#clear" ).trigger( "click");
			
	}
	}.ctx(this));
}
	
Login.prototype.sendMail = function(email){
	var input = {"payload":{"email":email}};
	RequestManager.sendMail(input,function(data,success){
		if(success)
			alert("success mail is sent");
		else
			alert("fail to send"+data.message);
	});
}

Login.prototype.openEmailDialogBox = function() {	
	$(".error").hide();
	console.log("modal");
	$('#forgotPasswordModal').modal('show');	
}



Login.prototype.validateLogin = function(email,password){
	console.log(email+" validation "+password);

    var isValid = false;
    var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,})?$/;

    var emailVal = email;
    if(emailVal == '') {
    	//$('.userId').after('<span class = "error">Please enter your email address.</span>');
        isValid = false;
    }

    else if(!emailReg.test(email)) {
    	//$('.userId').after('<span class="error">Enter a valid email address.</span>');
        isValid = false;
    }
	 else if(password.length < 6 ){
		//$('.password').after('<span  class = "error">PASSWORD INVALID</span>');
        isValid = false;
	}
	    else
		{
		$(".error").hide();
		isValid = true;    
		}
	return isValid; 
}

Login.prototype.validateEmail = function(email){
	console.log(email+" validation ");
	$(".error").hide();
    var isValid = false;
    var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,})?$/;

    var emailVal = email;
    if(emailVal == '') {
    	$('.userId').after('<span class = "error"><img style = "height:5%;width:5%;"src = "resources/img/wrong.png"></span>');
        isValid = false;
    }

    else if(!emailReg.test(email)) {
    	$('.userId').after('<span class="error"><img style = "height:5%;width:5%;"src = "resources/img/wrong.png"></span>');
        isValid = false;
    }	
	    else
		{
			$(".error").hide();
			isValid = true;    
		}
	return isValid; 
}

Login.prototype.validatePassword = function(password){
	console.log(password+" validation ");
	
    var isValid = false;   
    if(password.length < 6 ){
		$('.password').after('<span  class = "error"><img style = "height:5%;width:5%;"src = "resources/img/wrong.png"></span>');
        isValid = false;
	}
	    else
		{
			$(".error").hide();
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
