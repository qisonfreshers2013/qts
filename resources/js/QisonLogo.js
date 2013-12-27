function QisonLogo(roleIds) {
	this.handleShow(roleIds);
}

QisonLogo.prototype.handleShow = function(roleIds) {
	$('#qisonLogoImg').css('cursor','pointer');
	$('#qisonLogoImg').click(function(){
		App.loadOptions(roleIds);
	});
}