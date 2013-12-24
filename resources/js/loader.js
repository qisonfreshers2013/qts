function Loader() {
    this.handleShow();
}

Loader.prototype.handleShow = function () {
};
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
Loader.prototype.loadLogin=function(cb){
	LazyLoad.js('resources/js/Login.js', cb);
	LazyLoad.css('resources/css/Login.css');
}
Loader.prototype.loadPhoto=function(cb){
	LazyLoad.js('resources/js/Photo.js', cb);
	LazyLoad.css('resources/css/Photo.css');
}
Loader.prototype.loadOptions=function(cb){
	LazyLoad.js('resources/js/Options.js',cb);
	LazyLoad.css('resources/css/Options.css');
}
Loader.prototype.loadWelcome=function(cb){
	LazyLoad.js('resources/js/Welcome.js',cb);
	LazyLoad.css('resources/css/Welcome.css');
}
Loader.prototype.loadMenu=function(cb){
	LazyLoad.js('resources/js/onlyAdminMenu.js', cb);
	LazyLoad.css('resources/css/onlyAdminMenu.css');
}
Loader.prototype.loadRoles = function(cb){
	LazyLoad.js('resources/js/roles.js', cb);
	LazyLoad.css('resources/css/roles.css');
}

var Loader = new Loader();