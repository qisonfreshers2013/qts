String.prototype.equals = function(toCompare) {
	return toCompare && (this == toCompare);
};

String.prototype.equalsIgnoreCase = function(toCompare) {
	return toCompare && (this.toLowerCase() === toCompare.toLowerCase());
};

/**
 * returned a shortened version of the string and adds ellipses
 * between words if it's close to the requested length.
 */
String.prototype.ellipses = function(length) {
	var val = this.substring(0);
	var threshold = length < 20 ? 4 : 10;
	if (val.length < length + threshold || length <= 0)
		return val;

	//find spaces to add ellilpses to
	var space = length;
	for (var i=length+threshold; i>0 && i>length-threshold; i--) {
		var tmp = val.substring(i,i+1);
		if (tmp == ' ') {
			space = i;
			if (i < length) break;
		}
	}
	var ret = val.substring(0, space);
	if (ret.length < val.length)
		ret = ret + '...';
	return ret;
};

/**
 * removes protocol/host from an absolute url that breaks dev b/c
 * server always uses full urls
 */
String.prototype.makeUrlRelative = function() {
	var s = this;
	//remove host name from src
	if (s.indexOf("http://")>=0) {
		s = s.substring(8);
		s = s.substring(s.indexOf('/'));
	}
	return s;
};

if(typeof String.prototype.trim !== 'function') {
	String.prototype.trim = function() {
		return this.replace(/^\s+|\s+$/g, '');
	}
}