function App() {
    this.userRole = "";
    this.communtyType = 0;
    this.categoryId = 0;
    this.termsAndConditions = '';

    $(document).ready(function () {
        $('.container').show();
    })
    this.handleShow();
}

App.prototype.handleShow = function () {
    this.loadPhoto();
    this.loadLogin();
//    this.loadOptions();
//    this.loadMenu();
//    this.loadRoles();
}

//App.prototype.loadSampleContent = function () {
//	Loader.loadTestingSample();
//}
App.prototype.loadLogin=function(){
	Loader.loadLogin();
}
App.prototype.loadPhoto=function(){
	Loader.loadPhoto();
}
App.prototype.loadOptions=function(){
	Loader.loadOptions(function(){
		new Options();
	});
}
App.prototype.loadWelcome=function(){
	Loader.loadWelcome(function() {
		new Welcome();
	});
}
App.prototype.loadMenu=function(){
	Loader.loadMenu(function(){
		new OnlyAdminMenu();
	});
	//App.loadRoles();
}
App.prototype.loadRoles = function () {
	Loader.loadRoles(function() {
		new Roles();
	});
}

//App.prototype.loadHeader = function () {
//	Loader.loadHeader();
//}
var App = new App();