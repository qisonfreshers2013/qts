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

App.prototype.loadMenu=function(){
	Loader.loadMenu(function(){
		new Menu();
	});
}

App.prototype.loadSearchProjectRelease=function(){
	Loader.loadSearchProjectRelease(function(){
		new SearchProjectRelease();
	});
}

App.prototype.loadOptions=function(){
	Loader.loadOptions(function(){
		new Options();
	});
}

App.prototype.loadWelcome=function(nickName,password){
	Loader.loadWelcome(function(){
		new Welcome(nickName,password);
	});
}

App.prototype.loadReleaseResult=function(){
	Loader.loadReleaseResult(function(){
		new ReleaseResult();
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

App.prototype.loadQisonLogo=function(){
Loader.loadQisonLogo(function(){
new QisonLogo();
});
	
}
App.prototype.loadRoles=function(){
Loader.loadRoles(function(){
new Roles();
});
}




































































































































App.prototype.loadSearchUser = function () {
	Loader.loadSearchUser(function (){
		new SearchUser();
	});	
}

App.prototype.loadSearchResults = function (data) {
	Loader.loadSearchResults(function (){
		new SearchResults(data);
	});
}

App.prototype.loadMyProfile = function (password) {
	Loader.loadMyProfile(function(){
		new MyProfile(password);
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