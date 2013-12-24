function Loader() {
    this.handleShow();
}

Loader.prototype.handleShow = function () {
}

Loader.prototype.loadPhoto=function(cb){
	LazyLoad.js('resources/js/Photo.js',cb);
	LazyLoad.css('resources/css/Photo.css',cb);
}
Loader.prototype.loadLogin=function(cb){
	LazyLoad.js('resources/js/Login.js',cb);
	LazyLoad.css('resources/css/Login.css',cb);
}

Loader.prototype.loadWelcome=function(cb){
	LazyLoad.css('resources/css/Welcome.css');
	LazyLoad.js('resources/js/Welcome.js',cb);
	
}

Loader.prototype.loadOptions=function(cb){
	LazyLoad.css('resources/css/Options.css');
	LazyLoad.js('resources/js/Options.js',cb);
}


Loader.prototype.loadMenu=function(cb){
	LazyLoad.css('resources/css/Menu.css');
	LazyLoad.js('resources/js/Menu.js',cb);
	
}


Loader.prototype.loadAddProject=function(cb){
	LazyLoad.css('resources/css/AddProject.css');
	LazyLoad.js('resources/js/AddProject.js',cb);

}


Loader.prototype.loadAllocateUsersToProject=function(cb){
	LazyLoad.css('resources/css/AllocateUsersToProject.css');
	LazyLoad.js('resources/js/AllocateUsersToProject.js',cb);
	
}

Loader.prototype.loadSearchProject=function(cb){
	LazyLoad.css('resources/css/SearchProject.css');
	LazyLoad.js('resources/js/SearchProject.js',cb);
	
}

Loader.prototype.loadSearchProjectResults=function(cb){
	LazyLoad.css('resources/css/SearchProjectResults.css');
	LazyLoad.js('resources/js/SearchProjectResults.js',cb);
	
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
       LazyLoad.js("resources/js/timeEntry.js",cb);
       LazyLoad.css("resources/css/timeEntry.css",cb);
}
Loader.prototype.loadApproverTimeSheetSearch=function(cb){
    LazyLoad.js("resources/js/approverSearch.js",cb);
    LazyLoad.css("resources/css/approverSearch.css",cb);
}
Loader.prototype.loadDefaultTimeSheetPage=function(cb){
    LazyLoad.js("resources/js/defaultTimeSheetPage.js",cb);
    LazyLoad.css("resources/css/defaultTimeEntryPage.css",cb);
}
Loader.prototype.loadDefaultApproverPage=function(cb){
    LazyLoad.js("resources/js/defaultApproverPage.js",cb);
    LazyLoad.css("resources/css/defaultApproverPage.css",cb);
}
 Loader.prototype.loadLogin = function(cb){
	 LazyLoad.js('resources/js/Login.js',cb);
	 LazyLoad.css('resources/css/Login.css',cb);
	}

	Loader.prototype.loadPhoto = function(cb){
	 LazyLoad.js('resources/js/Photo.js',cb);
	 LazyLoad.css('resources/css/Photo.css',cb);
	}
	Loader.prototype.loadOptions = function(cb) {
	 LazyLoad.js('resources/js/Options.js', cb);
	 LazyLoad.css('resources/css/Options.css',cb);
	}

	Loader.prototype.loadWelcome = function(cb){
	 LazyLoad.js('resources/js/Welcome.js', cb);
	 LazyLoad.css('resources/css/Welcome.css',cb);
	}




var Loader = new Loader();