function QisonLogo() {
	this.handleShow();
}

QisonLogo.prototype.handleShow = function() {
	$('#qisonLogoImg').css('cursor','pointer');
	$('#qisonLogoImg').click(function(){
		if(allocateUsersToProjectStatus==true){
			if(confirm('Changes are not saved. Do you want to leave page?')){
				allocateUsersToProjectStatus=false;
				App.loadOptions();
			}
		}else{
			App.loadOptions();
		}
		
	});
}