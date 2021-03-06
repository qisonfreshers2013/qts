
function Welcome(nickname) {
	Loader.loadHTML('.welcome', 'Welcome.html', true, function(){
		this.handleShow(nickname);
	}.ctx(this));
}

Welcome.prototype.handleShow = function(nickname) {	
	this.loadNickname(nickname);
	$('.logout').click(function(){	
		if(allocateUsersToProjectStatus==true){
			if(confirm('Changes are not saved. Do you want to leave the page?')){
				allocateUsersToProjectStatus=false;
				this.logout();
			}
		}else{
			this.logout();
		}
		
	}.ctx(this));
	$('.myAccount').click(function(){
		if(allocateUsersToProjectStatus==true){
			if(confirm('Changes are not saved. Do you want to leave page?')){
				allocateUsersToProjectStatus=false;
				App.loadMenu(function(){
					App.loadMyProfile();
				});
				
			}
		}else{
			App.loadMenu(function(){
				App.loadMyProfile();
			});
			
		}
				
	}.ctx(this));
	$('.logout').click(function(){		 
		console.log("logout");
	}.ctx(this));
}

Welcome.prototype.loadNickname = function(nickname){
	 $('span.nicknameCursor').text(nickname.ellipses(15));
	 $('span.nicknameCursor').attr('title',nickname);
}

Welcome.prototype.logout = function(){
	var input = {"payload":{}};
	RequestManager.logout(input, function(data,success){
		if(success){			
			document.location.reload();
		}else
			$.ambiance({
			    message : "Fail : "+data.message,
			    type : 'error'
			   });
	}.ctx(this));
}

//
//$(".logoutLink").click(function(event){
// RequestManager.userLogout({}, function(data, success) {
//  if (success) {
//   //routie('#home');
//   $('.nav li').removeClass('active');
//   this.userNotAuthenticated();
//   $('.userName').text(Message.get('common.guest.label'));
//   $('.userName').css('color', '#333333');
//   $('.exclamationMark').css('color', '#333333');
//   $('#profilePic').attr("src", "img/noImage.png");
//   $('.welcomeUser').removeClass('.userDetails');
//   if(App.communtyType == 2) {
//    //App.clearContentBody();
//    $('.container').empty()
//    $('#privateLoginContainer').show();
//    //location.reload();
//    /*location.href =  "";
//    App.loadLoginPage();*/
//    document.location.reload();
//   } else {
//    document.location.reload();
//    /*routie('home');
//    App.loadIndexFiles();*/
//   }
//  } else{
//   if(data.code == 109){
//    document.location.reload();
//   }
//  }
//     }.ctx(this));
//}.ctx(this));
