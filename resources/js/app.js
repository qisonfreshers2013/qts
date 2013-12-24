function App() {
    this.userRole = "";
    this.communtyType = 0;
    this.categoryId = 0;
    this.termsAndConditions = '';

    $(document).ready(function () {
        $('.loadingAnimation').show();
    })
    this.handleShow();
}
App.prototype.handleShow = function () {
	$('.container').show();
    $('.contentBody').css('background-color', '#AA0011');
    this.loadPhoto();
    this.loadLogin();
}



App.prototype.loadPhoto=function(){
	Loader.loadPhoto();
}
App.prototype.loadLogin=function(){
	Loader.loadLogin();
}


App.prototype.loadWelcome=function(){
	Loader.loadWelcome(function(){
		new Welcome();
	});
}

App.prototype.loadSearchProject=function(){
	Loader.loadSearchProject(function(){
		new SearchProject();
	});
}

App.prototype.loadMenu=function(roleIds){
	Loader.loadMenu(function(){
		new Menu(roleIds);
	})
}

App.prototype.loadOptions=function(roleIds){
	Loader.loadOptions(function(){
		new Options(roleIds);
	})
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


App.prototype.loadSearchProjectResults=function(data){
	Loader.loadSearchProjectResults(function(){
		new SearchProjectResults(data);
	});
}
var App = new App();