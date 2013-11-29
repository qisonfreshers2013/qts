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
    $('.contentBody').css('background-color', '#FFF')
    this.loadSampleContent();
}

App.prototype.loadSampleContent = function () {
	Loader.loadTestingSample();
}
var App = new App();