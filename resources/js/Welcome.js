/**
 * 
 */
function Welcome() {
	Loader.loadHTML('.welcome', 'Welcome.html', true, function(){
		this.handleShow();
	}.ctx(this));
}

Welcome.prototype.handleShow = function() {	
	$('.nickname').click(function(){		
	}.ctx(this));
	$('.logout').click(function(){		
	}.ctx(this));
	$('.myAccount').click(function(){
	}.ctx(this));	
}
