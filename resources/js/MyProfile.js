/**
 * 
 */
function MyProfile(){
	Loader.loadHTML('.container', 'MyProfile.html', true, function(){
			this.handleShow();
	}.ctx(this));
}

MyProfile.prototype.handleShow = function(){
	this.loadValues();
	$('button.submit').click(function(){	
		var firstNameRef = $('input.firstNameText');
		var lastNameRef = $('input.lastNameText');
		var nicknameRef = $('input.nicknameText');
		var locationRef = $('input.locationText');
		if(this.validateName(firstNameRef)){
			if(this.validateName(lastNameRef)){
				if(this.validateNickname(nicknameRef)){
					if(this.validateLocation(locationRef)){
						console.log('validations success');
						this.saveValues(firstNameRef,lastNameRef,nicknameRef,locationRef);
					}
				}
			}
		}
		
	}.ctx(this));
	$('.changePassword').click(function(){
		App.loadChangePassword();
	}.ctx(this));
	
}

MyProfile.prototype.saveValues = function(firstNameRef,lastNameRef,nicknameRef,locationRef){
	var input = {"payload":{"firstName":firstNameRef.val(),
		"lastName":lastNameRef.val(),
		"nickName":nicknameRef.val(),
		"location":locationRef.val()
		}};
	RequestManager.updateLoginUserDetails(input,function(data,success){
		if(success){
		//alert(data.message?"sucess"+data.message:"fail"+data.message);
			alert("success");
		}
		else
			alert("MyProfile "+data.message);
		}.ctx(this));
		
	
}

MyProfile.prototype.loadValues = function(){	
	var input = {"payload":{}};
	RequestManager.getLoginUserDetails(input,function(data,success){
		if(success){
			console.log("success profile"+data);
			if(data.nickName!=null)
				$('h1.nicknameHeader').text(data.nickName);
			else
				$('h1.nicknameHeader').text(data.lastName);
			$('p.locationHeader').text(data.location);	
			var gender = (data.gender)?"Male":"Female";
			$('input.employeeIdText').val(data.employeeId);
			$('input.emailText').val(data.email);
			$('input.userIdText').val(data.userId);
			$('input.designationText').val(data.designation);
			$('input.genderText').val(gender);
			$('input.firstNameText').val(data.firstName);
			$('input.lastNameText').val(data.lastName);
			$('input.nicknameText').val(data.nickName);
			$('input.locationText').val(data.location);					
		}
		else{
			console.log("loaded my profile"+data.message);
			alert("my profile" +" failed" + data.message);				
		}
		
	}.ctx(this));
}


MyProfile.prototype.validateName = function(name){
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


MyProfile.prototype.validateNickname= function(nickname){
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


MyProfile.prototype.validateLocation= function(location){
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
