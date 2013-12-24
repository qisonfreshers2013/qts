function Sample() {
	Loader.loadHTML('#contentRegion', 'sample.html', true, function(){
		this.handleShow();
	}.ctx(this));
}

Sample.prototype.handleShow = function() {
	$('.container').show();
	$('.testService').click(function(){
		this.testService();
	}.ctx(this));
	
	$('.testHandler').click(function(){
		this.testHandler();
	}.ctx(this));
}

Sample.prototype.testService = function() {
	RequestManager.testService({}, function(data, success) {
		if(success){
			alert(data);
		}else{
			alert("failed");
		}
	}.ctx(this));
}

Sample.prototype.testHandler = function() {
	RequestManager.testHandler({},function(data, success) {
		if(success){
			alert(data);
		}else{
			alert("failed");
		}
	}.ctx(this));
}

var Sample= new Sample();