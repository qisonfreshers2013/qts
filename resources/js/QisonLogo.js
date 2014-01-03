function QisonLogo() {
	this.handleShow();
}

QisonLogo.prototype.handleShow = function() {
	$('#qisonLogoImg').css('cursor','pointer');
	$('#qisonLogoImg').click(function(){
		App.loadOptions();
	});
}