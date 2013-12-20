function Loader() {
    this.handleShow();
}

Loader.prototype.handleShow = function () {
}

Loader.prototype.loadHeader = function (cb) {
    LazyLoad.js('header/header.js', cb);
    LazyLoad.css('header/header.css');
}

Loader.prototype.loadTestingSample = function (cb) {
	LazyLoad.js('sample.js', cb);
}

Loader.prototype.loadHTML = function(container, filePath, empty, callback) {
	var filePath = filePath;

	$.ajax({
		url: filePath,
		success: function(data) {
			if (empty) {
				$(container).empty();
			}
			$(container).append(data);
			
			
			if (callback) {
				callback();
				//console.log(container)
				$(container).msgkey();
			}
			/*var compiledTemplate = Ember.Handlebars.compile(data);
			Ember.View.create({ template: compiledTemplate }).appendTo(container);*/
		}
	});
	
	
}
Loader.prototype.loadTimeSheetFilling=function(cb){
       LazyLoad.js("timeEntry.js",cb);
       LazyLoad.css("timeEntry.css",cb);
}
Loader.prototype.loadApproverTimeSheetSearch=function(cb){
    LazyLoad.js("approverSearch.js",cb);
    LazyLoad.css("approverSearch.css",cb);
}
Loader.prototype.loadDefaultTimeSheetPage=function(cb){
    LazyLoad.js("defaultTimeSheetPage.js",cb);
    LazyLoad.css("defaultTimeEntryPage.css",cb);
}
Loader.prototype.loadDefaultApproverPage=function(cb){
    LazyLoad.js("defaultApproverPage.js",cb);
    LazyLoad.css("defaultApproverPage.css",cb);
}
 Loader.prototype.loadLogin = function(cb){
	 LazyLoad.js('Login.js',cb);
	 LazyLoad.css('Login.css',cb);
	}

	Loader.prototype.loadPhoto = function(cb){
	 LazyLoad.js('Photo.js',cb);
	 LazyLoad.css('Photo.css',cb);
	}
	Loader.prototype.loadOptions = function(cb) {
	 LazyLoad.js('Options.js', cb);
	 LazyLoad.css('Options.css',cb);
	}

	Loader.prototype.loadWelcome = function(cb){
	 LazyLoad.js('Welcome.js', cb);
	 LazyLoad.css('Welcome.css',cb);
	}




var Loader = new Loader();