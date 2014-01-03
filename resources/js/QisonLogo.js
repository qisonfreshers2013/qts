function QisonLogo() {
	this.handleShow();
}

QisonLogo.prototype.handleShow = function() {
	$('#qisonLogoImg').css('cursor','pointer');
	$('#qisonLogoImg').click(function(){
		if(status){
			if(confirm('Do you want to leave the page. Do you want to leave page')){
				status=false;
				App.loadOptions();
			}
		}else{
			App.loadOptions();
		}
		
	});
}