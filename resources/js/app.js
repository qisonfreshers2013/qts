function App() {
    this.userRole = "";
    this.communtyType = 0;
    this.categoryId = 0;
    this.termsAndConditions = '';

    $(document).ready(function () {
    	
    });
    this.handleShow();
    
}
App.prototype.handleShow = function () {
    $('.container').css('background-color', '#FFF');
    
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

App.prototype.loadWelcome=function(cb){
	Loader.loadWelcome(function(){
		new Welcome();
	});
}

App.prototype.loadReleaseResult=function(roles){
	Loader.loadReleaseResult(function(){
		new ReleaseResult(roles);
	});
}

var App = new App();