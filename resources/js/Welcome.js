
function Welcome(nickname) {
	Loader.loadHTML('.welcome', 'Welcome.html', true, function(){
		this.handleShow(nickname);
	}.ctx(this));
}

Welcome.prototype.handleShow = function(nickname) {	
	this.loadNickname(nickname);
	$('.logout').click(function(){	
		this.logout();
	}.ctx(this));
	$('.myAccount').click(function(){
		$('.content').remove();
		App.loadMyProfile();		
	}.ctx(this));
	$('.logout').click(function(){		 
		console.log("logout");
	}.ctx(this));
}

Welcome.prototype.loadNickname = function(nickname){
	$('span.nicknameCursor').text(nickname);
}

Welcome.prototype.logout = function(){
	var input = {"payload":{}};
	RequestManager.logout(input, function(data,success){
		if(success){
			alert('logout success');
			document.location.reload();
		}else
			alert('logout '+' fail'+data.message);
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
