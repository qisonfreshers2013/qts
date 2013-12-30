function App() {
	this.userRole = "";
	this.communtyType = 0;
	this.categoryId = 0;
	this.termsAndConditions = '';
	$(document).ready(function () {
		$('.container').hide();
		$('.loadingAnimation').show();
	})
	this.handleShow();
}
App.prototype.handleShow = function () {
	$('.container').css('background-color', '#FFF');     
}

App.prototype.loadAddProject=function(){
	Loader.loadAddProject(function(){
		new AddProject();
	});
}

App.prototype.loadAllocateUsersToProject=function(){
	Loader.loadAllocateUsersToProject(function(){
		new AllocateUsersToProject();
	});
}

App.prototype.loadSearchProject=function(){
	Loader.loadSearchProject(function(){
		new SearchProject();
	});
}
App.prototype.loadSearchProjectResults=function(data){
	Loader.loadSearchProjectResults(function(){
		new SearchProjectResults(data);
	});
}

App.prototype.loadAddProjectRelease=function(){
	Loader.loadAddProjectRelease(function(){
		new AddProjectRelease();
	});
}

App.prototype.loadMenu=function(roles){
	Loader.loadMenu(function(){
		new Menu(roles);
	});
}

App.prototype.loadSearchProjectRelease=function(roles){
	Loader.loadSearchProjectRelease(function(){
		new SearchProjectRelease(roles);
	});
}

App.prototype.loadOptions=function(roles){
	Loader.loadOptions(function(){
		new Options(roles);
	});
}

App.prototype.loadWelcome=function(nickName,roleIds){
	Loader.loadWelcome(function(){
		new Welcome(nickName,roleIds);
	});
}

App.prototype.loadReleaseResult=function(roles){
	Loader.loadReleaseResult(function(){
		new ReleaseResult(roles);
	});
}

App.prototype.loadTimeSheetFilling=function(){
	Loader.loadTimeSheetFilling();
}

App.prototype.loadApproverTimeSheetSearch=function(){
	Loader.loadApproverTimeSheetSearch();
}
App.prototype.loadDefaultTimeSheetPage=function(){
	Loader.loadDefaultTimeSheetPage();
}
App.prototype.loadDefaultApproverPage=function(){
	Loader.loadDefaultApproverPage();
}

App.prototype.loadQisonLogo=function(roleIds){
Loader.loadQisonLogo(function(){
new QisonLogo(roleIds);
});
	
}
App.prototype.loadRoles=function(){
Loader.loadRoles(function(){
new Roles();
});
}




































































































































App.prototype.loadSearchUser = function (roles) {
	Loader.loadSearchUser(function (){
		new SearchUser(roles);
	});	
}

App.prototype.loadSearchResults = function (data,roles) {
	Loader.loadSearchResults(function (){
		new SearchResults(data,roles);
	});
}

App.prototype.loadMyProfile = function () {
	Loader.loadMyProfile(function(){
		new MyProfile();
	});
}


App.prototype.loadAddUser = function () {
	Loader.loadAddUser(function(){
		new AddUser();
	});
}

App.prototype.loadChangePassword = function(){
	Loader.loadChangePassword(function(){
		new ChangePassword();
	});
}	

App.prototype.loadUserProfile = function (id) {
	Loader.loadUserProfile(function(){
		new UserProfile(id);
	});
}





var App = new App();