Function.prototype.ctx = function(context) {
	var self = this;
	return function() {
		return self.apply(context, arguments);
	};
};