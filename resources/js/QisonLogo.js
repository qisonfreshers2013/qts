function QisonLogo() {
	this.handleShow();
}

QisonLogo.prototype.handleShow = function() {
	$('#qisonLogoImg').css('cursor','pointer');
	$('#qisonLogoImg').click(function(){
		if(status){
			if(confirm('Changes are not saved. Do you want to leave page')){
				status=false;
				App.loadOptions();
			}
		}else{
			App.loadOptions();
		}
		
	});
}