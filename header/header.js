function Header() {
	this.callback = null;
	this.routeFromHeader = true;
	Loader.loadHTML('.headerRegion', 'header/header.html', true, function() {
		this.handleShow();
	}.ctx(this));
}

Header.prototype.handleShow = function() {
	this.isUserLoggedIn();
	console.log("------------4")
	$(".loginLink").click(function(event){
		$("#loginModal").modal('show');
		$('.alert-error').hide();
		$('.alert-success').hide();
		$('.user_email').val('');
		$('.user_password').val('');
		$('.user_email').focus();	
	});
	
	$(".registerLink").click(function(event){
		$("#registerModal").modal('show');
		$('.user_first_name').val('');
		$('.user_first_name').focus();
		$('.user_last_name').val('');
		$('.user_register_email').val('');
		$('.user_pwd').val('');
		$('.user_conf_pwd').val('');
		$('.user_tags').val([]);
		$('.user_tags').multiselect('refresh');
		$('.alert-error').hide();
		$('.alert-success').hide();
		$('.termsAndConditions').removeAttr('checked'); 
	});
	console.log("------------5")
    $('.forgotPwdLink').click(function(event) {
    	$('#loginModal').modal('hide');
        $('#forgotPwdModal').modal('show');
		$('.forget_user_email').val('');
		$('.forget_user_email').focus();
		$('.alert-error').hide();
		$('.alert-success').hide();		      
		//$('.dvLoading').hide();
    });
    
    $(".user_password").keydown(function(event){
		var keyCode = (window.event) ? event.which : event.keyCode;
		if(keyCode == 13){
			$(".userLoginBtn").click();
		}
	});
    console.log("------------6")
	$('.termsNConditionsContent').html(App.termsAndConditions);
	
	$('.termsNCondsLink').click(function() {
		$('#termsNConditions').modal('show');
	});
	$('.termsCancelBtn').click(function() {
		$('#termsNConditions').modal('hide');
	});
	
	$(".logoutLink").click(function(event){
		RequestManager.userLogout({}, function(data, success) {
			if (success) {
				//routie('#home');
				$('.nav li').removeClass('active');
				this.userNotAuthenticated();
				$('.userName').text(Message.get('common.guest.label'));
				$('.userName').css('color', '#333333');
				$('.exclamationMark').css('color', '#333333');
				$('#profilePic').attr("src", "img/noImage.png");
				$('.welcomeUser').removeClass('.userDetails');
				if(App.communtyType == 2) {
					//App.clearContentBody();
					$('.container').empty()
					$('#privateLoginContainer').show();
					//location.reload();
					/*location.href =  "";
					App.loadLoginPage();*/
					document.location.reload();
				} else {
					document.location.reload();
					/*routie('home');
					App.loadIndexFiles();*/
				}
			} else{
				if(data.code == 109){
					document.location.reload();
				}
			}
	     }.ctx(this));
	}.ctx(this));
	console.log("------------7")
	$(".userLoginBtn").click(function(event){
		var user_email = $('.user_email').val();
		var user_password = $('.user_password').val();
		var authInput = {
			"payload" : {
				"authType" : "REGULAR",
				"email" : user_email,
				"password" : user_password
			}
		}
		if(this.validateLogin(authInput.payload)) {
			$('.alert-error').hide();
			RequestManager.authenticateUser(authInput, function(data, success) {
				if (success) {
					//document.location.reload();
					
					var url = document.URL;
					var hashMap = url.split('#');
					
					$('#loginModal').modal('hide');
					var  token = data.sessionToken;
					//console.log(token)
					setCookie('qtsSessionId', token, null);
					if(this.callback) {
						this.callback();
						this.callback = null;
					} 
					if(this.routeFromHeader != undefined) {
						if(hashMap.length == 1){
							routie('home');
						} else{
							routie(hashMap[1]+'/');
						}	
						this.routeFromHeader = true;
					}
					RequestManager.getUserDetails({}, function(data, success) {
						if (success) {
							var displayName = data.name;
							$('.userName').text(displayName);
							$('.userName').css('color', App.brandColor);
							$('.exclamationMark').css('color', App.brandColor);
							$('.welcomeUser').addClass("userDetails");
							this.userAuthenticated(data);
							if(data.file){
								$('#profilePic').attr("src", data.file.fileUrl);
							}
						}
					}.ctx(this));
				} else {
					$('.alert-success').hide();
					$('.user_email').val('');
					$('.user_email').focus();
					$('.user_password').val('');
					$('.alert-error').show();
					var errMessage = data.message;
					$('.errMsg').text(errMessage);
				}
			}.ctx(this));
	  }
	}.ctx(this));
	
	
/*	$('.fbLoginLink').click(function() {
		//window.fbAsyncInit = function() {
		    FB.init({ appId: "163044000416231", cookie: true, xfbml: true, oauth: true });
		    if (typeof facebookInit == 'function') {
		        facebookInit();
		    }
		//};
	});*/
	console.log("------------8")
	$(".forgotPwdRequestBtn").click(function(event){
		$('#loginModal').modal('hide');
		var user_email = $('.forget_user_email').val();	
		var data = {
			"payload": {
				"email": user_email			
			}
		}
		if(this.validateForgotEmail(data.payload)){
			$('#forgotPwdModal').modal('hide');	
			$('#forgotPwdRequestModal').modal('show');
			$('.forgotPwdRequestStatus').show();
			$('.statusMsg').text(Message.get('common.processrequest.message'));
			RequestManager.forgotPassword(data, function(data, success) {
				if (success) {
					$('#forgotPwdModal').modal('hide');	
					$('#forgotPwdRequestModal').modal('show');
					$('.forgotPwdRequestStatus').show();
					$('.statusMsg').text(Message.get('common.pwdresetmsg.message'));
				} else {
					if(data.code == 145) {
						$('#forgotPwdModal').modal('hide');	
						$('#forgotPwdRequestModal').modal('show');
						$('.forgotPwdRequestStatus').show();
						$('.statusMsg').text(data.message);
					} else {
						$('#forgotPwdModal').modal('show');	
						$('#forgotPwdRequestModal').modal('hide');
						$('.alert-success').hide();
						$('.alert-error').show();
						var errMessage = data.message;
						$('.errMsg').text(errMessage);
						$('.forget_user_email').val('');
					}
				}
				$('.forgotPwdRequestBtn').removeAttr("disabled");
			}.ctx(this));
		}
		$('.forget_user_email').focus();
		$('.anonymousChkBox').attr('checked' , false);
	}.ctx(this));

	$(".userRegisterBtn").click(function(event){
//		RequestManager.registrationType({}, function(regType, success) {
			this.saveRegistration();
//		}.ctx(this));
	}.ctx(this));
	this.userTags();
	console.log("------------9")
	$(".forgotPwdCancelBtn").click(function(event){
		$('.alert-error').hide();
		$('.alert-success').hide();
	}.ctx(this));
	
	$(".userRegisterCancelBtn").click(function(event){
		$('.alert-error').hide();
		$('.alert-success').hide();
	}.ctx(this));
	
	$(".uesrLoginCancelBtn").click(function(event){
		$('.alert-error').hide();
		$('.alert-success').hide();
	}.ctx(this));
	$(".logoLink").click(function(){
		//App.loadIndexFiles();
		routie('home')
	}.ctx(this));
	console.log("------------10")
	console.log(App.logoUrl+"------------1")
	if (App.logoUrl) {
		console.log(App.logoUrl+"------------2")
		$('.logoImgContainer').prop('src', App.logoUrl);
	} else {
		setTimeout(function() {
			console.log(App.logoUrl+"------------3")
			$('.logoImgContainer').prop('src', App.logoUrl);
		}, 2000);
	}
	
	/*var url = $('.logo').css('background-image').replace('url(', '').replace(')', '').replace("'", '').replace('"', '');
    url = url.replace('"', '');
    var bgImg = $('<img />');
    bgImg.hide();
    bgImg.bind('load', function()
    {
        var width = $(this).width();
        $('.logo').css('width', width);
        $('.logo').css('max-width', '98%');
    });
    $('.logo').append(bgImg);
    bgImg.attr('src', url);*/
	
	var facebookInput = {
			"payload" : {
				"provider": "facebook"
			}
		}
	RequestManager.getSocialIntegrationDetails(facebookInput, function(data, success) {
		if (success) {
			console.log(data)
			$('.faceBookLogin').show();
			$('.faceBookLogin').click(function () {
		        var url = "https://www.facebook.com/dialog/oauth/?client_id="+data.clientId+"&state=abcdef&scope=publish_stream,email,user_birthday,user_photos&display=popup&redirect_uri="+data.redirect_uri;
		        window.open(url, "facebook.com", "height=600,width=600,modal=yes,alwaysRaised=yes");
		    }.ctx(this));
		}
	}.ctx(this));
	
	var googleInput = {
			"payload" : {
				"provider": "google"
			}
		}
	RequestManager.getSocialIntegrationDetails(googleInput, function(data, success) {
		if (success) {
			console.log(data)
			$('.googleLogin').show()
			$('.googleLogin').click(function () {
		        var url = "https://accounts.google.com/o/oauth2/auth?" +
		            "redirect_uri="+data.redirect_uri+
		            "&response_type=code" +
		            "&client_id="+data.clientId +
		            // "&scope=https://www.googleapis.com/auth/plus.login+https://www.googleapis.com/auth/plus.me+https://www.googleapis.com/auth/userinfo.profile" +
		            "&scope=https://www.googleapis.com/auth/userinfo.email+https://www.googleapis.com/auth/userinfo.profile" +
		            // "&scope=openid, email "
		            "&access_type=offline" +
		            "&approval_prompt=force";
		        window.open(url, "google", "height=600,width=600,modal=yes,alwaysRaised=yes");
		    }.ctx(this));
		}
	})
};

Header.prototype.saveRegistration = function() {
//	var regType = type;
	$('#loginModal').modal('hide');
	var user_fname = $('.user_first_name').val();
	var user_lname = $('.user_last_name').val();
	var user_email = $('.user_register_email').val();
	var user_pwd = $('.user_pwd').val();
	var user_conf_pwd = $('.user_conf_pwd').val();
	
	var tags = [];
	$(".user_tags option:selected").each(function (index, element) {
		var val = $(element).val();
		tags.push(val);
	});
	
	var isAnonymous = 0;
	if($('.anonymousChkBox').is(':checked')){
		isAnonymous = 1;
	}

	var data = {
		"payload": {
			"firstName": user_fname,
			"lastName": user_lname,
			"email": user_email,
			"password": user_pwd,
			"confirmPassword": user_conf_pwd,
			"tags" : tags,
			"isAnonymous" : isAnonymous
		}
	};
	if (this.validateRegister(data.payload)) {
		delete data.payload.confirmPassword;
//	if(regType == 1){
				RequestManager.registerUser(data, function(data, success) {
					if (success) {
						$('#registerModal').modal('hide');
						$('#loginModal').modal('show');
						$('.user_email').val('');
						$('.user_password').val('');
						$('.successMsg').text(data);
						//text(Message.get('common.registeredsucessfully.message'));
						$('.alert-success').show();	
						}else {
						var errMessage = data.message;
						$('.errMsg').text(errMessage);
						$('.alert-error').show();
						$('.alert-success').hide();
					}
				}.ctx(this));
//			}
	   }
}


Header.prototype.isUserLoggedIn = function() {
	RequestManager.isUserLoggedIn({}, function(data, success) {
		if (success) {
			RequestManager.getUserDetails({}, function(data, success) {
				if (success) {
					this.userAuthenticated(data);
				}
			}.ctx(this));
		}
     }.ctx(this));
}

Header.prototype.userAuthenticated = function(userInfo) {
	$('.userName').removeAttr('msgkey');
	$('.userName').text(userInfo.name);
	$('.userName').css('color', App.brandColor);
	$('.exclamationMark').css('color', App.brandColor);
	$('.loginLinks').hide();
	//$('.registerLink').hide();
	$('.logoutLink').show();
	$('.myAccountLink').show();
	if(userInfo.file) {
		var file = userInfo.file;
		$('#profilePic').attr('src', file.fileUrl);
	}
}

Header.prototype.userNotAuthenticated = function() {
	/*$('.userName').attr('msgkey', Message.get('common.guest.label'));*/
	/*$('.userName').msgkey();*/
	$('.loginLinks').show();
	$('.logoutLink').hide();
	$('.myAccountLink').hide();
}

Header.prototype.userTags = function() {
	var data = {};
	$('.user_tags').empty();
	RequestManager.getAllUserTags(data, function(data, success) {
		if (success) {
			if (data && data.length > 0) {
				for ( var i = 0; i < data.length; i++) {
					var tag = data[i];
					var tWidget = $('<option></option>').val(tag).text(
							tag);
					$('.user_tags').append(tWidget);
				}
				$('.user_tags').multiselect({
					noneSelectedText : 'Select Tags',
					beforeopen : function() {
						$('.ui-multiselect-menu').css('position', '');
						$('.ui-multiselect-menu').css('top', '340px');
					}
				});
			} else {
				$(".user_tags").multiselect({
					noneSelectedText : 'No Tags',
					header : 'No Tags',
					beforeopen : function() {
						$('.ui-multiselect-menu').css('position', '');
						$('.ui-multiselect-menu').css('top', '340px');
					}
				});
			}
		}
	}.ctx(this));
}

Header.prototype.validateRegister = function(object) {
	//console.log(object);
	 $('.alert-error').show();
	 $('.alert-success').hide();
	 var fname = object.firstName.trim();
	 var lname = object.lastName.trim();

	 if (fname == "") {
		 	var errMessage = "Please provide your name.";
		 	$('.errMsg').text(errMessage);
		 	$('.user_first_name').focus();
		 	return false;
		}
		if (fname.match(/^[a-zA-Z]+$/) == null) {
			var errMessage = "Please provide a valid name.";
			$('.errMsg').text(errMessage);
		 	$('.user_first_name').focus();
			return false;
		}
		if (lname == "") {
			var errMessage = "Please provide your last name.";
			$('.errMsg').text(errMessage);
			$('.user_last_name').focus();
			return false;
		}
		if (lname.match(/^[a-zA-Z ]+$/) == null) {
			var errMessage = "Please provide a valid last name.";
			$('.errMsg').text(errMessage);
			$('.user_last_name').focus();
			return false;
		}
		if (object.email.trim() == "") {
			var errMessage = "Please provide your email.";
			$('.errMsg').text(errMessage);
		 	$('.user_register_email').focus();
			return false;
		}
		if ((object.email.match(/^[_A-Za-z]+[_A-Za-z0-9.-]+[_A-Za-z0-9]*[@]{1}[A-Za-z0-9.-]+[.]+[A-Za-z]{2,}$/)==null)) {
			var errMessage = "Please provide a valid email.";
			$('.errMsg').text(errMessage);
			$('.user_register_email').focus();
			return false;
		}
		if (object.password == "") {
			var errMessage = "Please enter your password.";
			$('.errMsg').text(errMessage);
			$('.user_pwd').focus();
			return false;
		}
		if (object.password.length < 6) {
			var errMessage = "Please provide a stronger password with minimum 6 characters.";
			$('.errMsg').text(errMessage);
			$('.user_pwd').focus();
			return false;
		}
		if (object.confirmPassword == "") {
			var errMessage = "Please re-enter your password.";
			$('.errMsg').text(errMessage);
			$('.user_conf_pwd').focus();
			return false;
		}
		if (object.password != object.confirmPassword) {
			var errMessage = "Password does not match with confirm password.";
			$('.errMsg').text(errMessage);
			$('.user_pwd').focus();
			$('.user_pwd').val('');
			$('.user_conf_pwd').val('');
			return false;
		}
		if(!$('.termsAndConditions').is(':checked')) {
			var errMessage = "In order to use our services, you must agree to our Terms and Conditions.";
			$('.errMsg').text(errMessage);
			return false;
		}
		$('.alert-error').hide();
		return true;
}

Header.prototype.validateLogin = function(object) {
	//console.log(object);
	 $('.alert-error').show();
	 $('.alert-success').hide();
	if (object.email.trim() == "") {
		var errMessage = "Please enter your registered email.";
		$('.errMsg').text(errMessage);
		$('.user_email').focus();
		return false;
	}
	if ((object.email.match(/^[_A-Za-z]+[_A-Za-z0-9.-]+[_A-Za-z0-9]*[@]{1}[A-Za-z0-9.-]+[.]+[A-Za-z]{2,}$/)==null)) {
		var errMessage = "Please provide a valid email.";
		$('.errMsg').text(errMessage);
		$('.user_email').focus();
		return false;
	}
	if (object.password == "") {
		var errMessage = "Please enter your password.";
		$('.errMsg').text(errMessage);
		$('.user_password').focus();
		return false;
	}
	$('.alert-error').hide();
	return true;
}

Header.prototype.validateForgotEmail = function(object) {
	$('.alert-error').show();
	 $('.alert-success').hide();
	if (object.email.trim() == "") {
		var errMessage = "Please enter your registered email.";
		$('.errMsg').text(errMessage);
		return false;
	}
	if ((object.email.match(/^[_A-Za-z]+[_A-Za-z0-9.-]+[_A-Za-z0-9]*[@]{1}[A-Za-z0-9.-]+[.]+[A-Za-z]{2,}$/)==null)) {
		var errMessage = "Please provide a valid email.";
		$('.errMsg').text(errMessage);
		return false;
	}
	$('.alert-error').hide();
	return true;
}

var Header = new Header();