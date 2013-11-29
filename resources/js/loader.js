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

var Loader = new Loader();