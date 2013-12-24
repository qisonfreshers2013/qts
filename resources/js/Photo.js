/**
 * 
 */

function Photo(){
Loader.loadHTML('.leftContainer', 'Photo.html', true, function(){
	this.handleShow();
}.ctx(this));
}


Photo.prototype.handleShow = function() {	
	$('.container').show();
	$('.leftContainer').show();
}

var Photo = new Photo();