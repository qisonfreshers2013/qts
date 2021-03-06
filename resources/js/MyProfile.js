/**
 * 
 */
function MyProfile(){	
	Loader.loadHTML('.container', 'MyProfile.html', false, function(){
			this.handleShow();
	}.ctx(this));
}

MyProfile.prototype.handleShow = function(){
	$('#changePasswordModal').hide();	
	this.loadValues();
	$('form#myProfileInnerForm').keyup(function(event){
		if(event.keyCode == 13){
			$('.error').remove();
			$('button#submit').trigger("click");
		}
	}.ctx(this));
	
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
		$('#editProfileDiv').find('*').prop('disabled',true);
		App.loadChangePassword();		
	}.ctx(this));
	
	$('#clear').click(function() {
		 $("#ambiance-notification").empty();
		$('.error').remove();
	});
}

MyProfile.prototype.saveValues = function(firstNameRef,lastNameRef,nicknameRef,locationRef){
	var input = {"payload":{"firstName":firstNameRef.val(),
		"lastName":lastNameRef.val(),
		"nickName":nicknameRef.val(),
		"location":locationRef.val()
		}};
	RequestManager.updateLoginUserDetails(input,function(data,success){
		if(success){
			$.ambiance({
				  message : "Updation is Successfull",
				  type : 'success'
				});
			if((data.nickName==null) || (data.nickName.trim().length < 1)){
				
				$('span.nicknameCursor').text(data.lastName.ellipses(15));
				  $('span.nicknameCursor').attr('title',data.lastName);
				$('h1.nicknameHeader').text(data.lastName.ellipses(15));
				  $('h1.nicknameHeader').attr('title',data.lastName);
			}
			else{
				$('h1.nicknameHeader').text(data.nickName.ellipses(15));
				 $('h1.nicknameHeader').attr('title',data.nickName);
			    $('span.nicknameCursor').text(data.nickName.ellipses(15));
			    $('span.nicknameCursor').attr('title',data.nickName);
			}
			$('p.locationHeader').text(data.location.ellipses(15));	
			$('p.locationHeader').text(data.location.ellipses(15));
			$('p.upperDesignationField').text(data.designation.ellipses(10));	
			$('p.upperDesignationField').text(data.designation.ellipses(15));
			
		}
		else
			//alert("Fail to updateMyProfile : "+data.message);
			$.ambiance({
			    message : "Fail : "+data.message,
			    type : 'error'
			   });
		}.ctx(this));
		
	
}

MyProfile.prototype.loadValues = function(){	
	var input = {"payload":{}};
	RequestManager.getLoginUserDetails(input,function(data,success){
		if(success){
			console.log("success profile" + data.payload);
			if(data.nickName == null || data.nickName.trim().length<1 ){
			
			   $('h1.nicknameHeader').text(data.lastName.ellipses(15));
			   $('h1.nicknameHeader').attr('title',data.lastName);
			}
			else{
				$('h1.nicknameHeader').text(data.nickName.ellipses(15));
				$('h1.nicknameHeader').attr('title',data.nickName);				
			}
			 $('p.locationHeader').text(data.location.ellipses(15));	
			 $('p.locationHeader').attr('title',data.location);
			 
			 $('p.upperDesignationField').text(data.designation.ellipses(10));
			 $('p.upperDesignationField').attr('title',data.designation);
			 
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
			$.ambiance({
			    message : "Fail : "+data.message,
			    type : 'error'
			   });				
		}
		
	}.ctx(this));
}


MyProfile.prototype.validateName = function(name){
	var isValid = false;

	 var nameReg=/^[A-Za-z]+([ {1}][A-Za-z]+)*$/g;
       $('.error').hide();
	    var nameVal = name.val();
	    if(nameVal.trim().length <= 0 || nameVal == null) {
	    	name.after('<span class = "error" style = "color:red"  >Name can'+"'"+'t  be null</span>');
	        isValid = false;
	    }
	    else if(!nameReg.test(nameVal)) {
	    	name.after('<span class="error" style = "color:red" >Invalid name</span>');
	        isValid = false;
	    }
	    else if(nameVal.length > 128){
	    	name.after('<span class="error" style = "color:red">Maximum length of name is 128 characters</span>');
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
	var nicknameReg =/^[A-Za-z]+([ {1}][A-Za-z]+)*$/g;
       $('.error').hide();
	    var nicknameVal = nickname.val();
	    if(nicknameVal.length < 1){
	    	$('.error').hide();
    	 	isValid = true;
	    }
	    else if(!nicknameReg.test(nicknameVal)) {
	    	nickname.after('<span class="error" style = "color:red">Nickname is invalid</span>');
	        isValid = false;
	    }
	    else  if(nicknameVal.length > 128) {
	    	nickname.after('<span class="error" style = "color:red">Maximum length of nickname is 128</span>');
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
	var locationReg =/^[A-Za-z]+([ {1}][A-Za-z]+)*$/g;
      
	    var locationVal = location.val();	 
	    if(locationVal.length < 1){
	    	 $('.error').hide();
	    	isValid = true;
	    }
	    else if(!locationReg.test(locationVal)) {
	    	location.after('<span class="error" style = "color:red">Location is invalid</span>');
	        isValid = false;
	    }
	    else  if(locationVal.length > 128) {
	    	nickname.after('<span class="error" style = "color:red">Maximum length of location is 128</span>');
	        isValid = false;
	    }
	    else
	    	isValid = true;
	    return isValid;
}
