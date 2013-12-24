function Loader() {
	this.handleShow();
}

Loader.prototype.handleShow = function() {

}

 Loader.prototype.loadMenu = function (cb) {
	 LazyLoad.css('resources/css/Menu.css');
	 LazyLoad.js('resources/js/Menu.js', cb);
	}

	Loader.prototype.loadAddProject=function(cb){
	 LazyLoad.css('addProject.css');
	 LazyLoad.js('addProject.js',cb);

	}


	Loader.prototype.loadAllocateUsersToProject=function(cb){
	 LazyLoad.css('allocateUsersToProject.css');
	 LazyLoad.js('allocateUsersToProject.js',cb);
	 
	}

	Loader.prototype.loadSearchProject=function(cb){
	 LazyLoad.css('searchProject.css');
	 LazyLoad.js('searchProject.js',cb);
	 
	}

	Loader.prototype.loadSearchProjectResults=function(cb){
	 LazyLoad.css('searchProjectResults.css');
	 LazyLoad.js('searchProjectResults.js',cb);
	 
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



















































Loader.prototype.loadHeader = function(cb) {
	LazyLoad.css('header/header.css');
	LazyLoad.js('header/header.js', cb);
	
}

Loader.prototype.loadTestingSample = function(cb) {
	LazyLoad.js('sample.js', cb);
}

Loader.prototype.loadSearchResults = function(cb) {
	LazyLoad.css('resources/css/SearchResults.css');
	LazyLoad.js('resources/js/SearchResults.js', cb);
	
}



Loader.prototype.loadSearchUser = function(cb){
	LazyLoad.css('resources/css/SearchUser.css');
	LazyLoad.js('resources/js/SearchUser.js',cb);
	
}

Loader.prototype.loadAddUser = function(cb){
	LazyLoad.css('resources/css/AddUser.css');
	LazyLoad.js('resources/js/AddUser.js',cb);
	
}

Loader.prototype.loadChangePassword = function(cb){
	LazyLoad.css('resources/css/ChangePassword.css');
	LazyLoad.js('resources/js/ChangePassword.js',cb);
	
}

/*
Loader.prototype.loadLogin = function(cb){
	LazyLoad.js('Login.js',cb);
	LazyLoad.css('Login.css',cb);
}

Loader.prototype.loadPhoto = function(cb){
	LazyLoad.js('Photo.js',cb);
	LazyLoad.css('Photo.css',cb);
}*/


Loader.prototype.loadMyProfile = function(cb){
	LazyLoad.css('resources/css/MyProfile.css');
	LazyLoad.js('resources/js/MyProfile.js',cb);
	
}

Loader.prototype.loadUserProfile = function(cb){
	LazyLoad.css('resources/css/UserProfile.css');
	LazyLoad.js('resources/js/UserProfile.js',cb);
	
}



Loader.prototype.loadHTML = function(container, filePath, empty, callback) {
	var filePath = filePath;
	$.ajax({
		url : filePath,
		success : function(data) {
			if (empty) {
				$(container).empty();
			}
			$(container).append(data);

			if (callback) {
				callback();
				// console.log(container)
				$(container).msgkey();
			}
			/*
			 * var compiledTemplate = Ember.Handlebars.compile(data);
			 * Ember.View.create({ template: compiledTemplate
			 * }).appendTo(container);
			 */
		}
	});

}

var Loader = new Loader();