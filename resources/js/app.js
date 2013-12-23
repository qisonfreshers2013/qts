function App() {
    this.userRole = "";
    this.communtyType = 0;
    this.categoryId = 0;
    this.termsAndConditions = '';

    $(document).ready(function () {
       // $('.container').hide();
        $('.loadingAnimation').show();
    })
    this.handleShow();
}
App.prototype.handleShow = function () {
    $('.contentBody').css('background-color', 'white')
    this.loadLogin();
    this.loadPhoto();
}

App.prototype.loadSampleContent = function () {
	Loader.loadTestingSample();
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
App.prototype.loadLogin=function(){
	Loader.loadLogin();
}
App.prototype.loadPhoto=function(){
	Loader.loadPhoto();
}
App.prototype.loadOptions=function(){
	console.log("In loadOptions");
	Loader.loadOptions();
}
App.prototype.loadWelcome=function(){
	console.log("In welcome");
	Loader.loadWelcome();
}
var App = new App();