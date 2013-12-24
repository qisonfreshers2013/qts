/**
 * 
 */

function Photo(){
	console.log("photo");
Loader.loadHTML('#leftContainer', 'Photo.html', true, function(){
	this.handleShow();
}.ctx(this));
}


Photo.prototype.handleShow = function() {	

}
var Photo=new Photo();
