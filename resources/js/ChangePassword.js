/**
 * 
 */

function ChangePassword(){
	Loader.loadHTML('.container', 'ChangePassword.html', false, function(){		
		this.handleShow();			
	}.ctx(this));
}
ChangePassword.prototype.handleShow = function(){
	console.log('calling modelshow');
	
	$('.changePasswordContainer').modal('show');

	console.log('called modelshow');
}