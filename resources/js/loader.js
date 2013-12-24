function Loader() {
    this.handleShow();
}

Loader.prototype.handleShow = function () {
};

Loader.prototype.loadMenu = function (cb) {
	LazyLoad.css('resources/css/Menu.css');
	LazyLoad.js('resources/js/Menu.js', cb);
}

Loader.prototype.loadAddProjectRelease=function(cb){
	LazyLoad.css('resources/css/AddProjectRelease.css');
	LazyLoad.js('resources/js/AddProjectRelease.js',cb);
}

Loader.prototype.loadSearchProjectRelease=function(cb){
	LazyLoad.css('resources/css/SearchProjectRelease.css');
	LazyLoad.js('resources/js/SearchProjectRelease.js',cb);	
}

Loader.prototype.loadReleaseResult=function(cb){
	LazyLoad.css('resources/css/ReleaseResult.css');
	LazyLoad.js('resources/js/ReleaseResult.js',cb);
}

Loader.prototype.loadOptions=function(cb){
	LazyLoad.css('resources/css/Options.css');
	LazyLoad.js('resources/js/Options.js',cb);
	
}

Loader.prototype.loadWelcome=function(cb){
	LazyLoad.css('resources/css/Welcome.css');
	LazyLoad.js('resources/js/Welcome.js',cb);
	
}

Loader.prototype.loadRoles = function(cb){
	LazyLoad.js('resources/js/roles.js', cb);
	LazyLoad.css('resources/css/roles.css');
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
				//$(container).msgkey();
			}
			/*var compiledTemplate = Ember.Handlebars.compile(data);
			Ember.View.create({ template: compiledTemplate }).appendTo(container);*/
		}
	});
	
	
}

var Loader = new Loader();