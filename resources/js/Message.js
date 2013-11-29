var moduleNamespace = {};
function Message() {
	this.messages = {};
};

Message.prototype.add = function(key, message) {
	if (typeof message == 'undefined' || message == null)
		message = '';
	if (typeof message == 'number')
		message = "" + message;
	var parts = [];
	var regexp = /({\d+})/gi;
	var tmp = message.split(regexp);

	// some browsers don't include delimiter
	var matches = [];
	for ( var i = 0; i < tmp.length; i++) {
		var index = message.indexOf(tmp[i]);
		if (index > 0) {
			matches.push(message.substring(0, index));
		}
		var endIndex = index + tmp[i].length;
		matches.push(message.substring(index, endIndex));
		message = message.substring(endIndex);
	}
	matches.push(message);
	tmp = matches;
	var escaped = false;
	for ( var i = 0; i < tmp.length; i++) {
		var t = tmp[i];
		regexp = /({\d+})/gi;
		if (t == "") {
		} else if (!escaped && regexp.test(t)) {
			var index = parseInt(t.substring(1), 10);
			parts.push({
			    message : t,
			    variable : index
			});
		} else {
			parts.push({
				message : t
			});
		}
	}
	this.messages[key] = parts;
};

/**
 * performs a bulk add on a object with key/value pairs, ie: { key1: "value1",
 * key2: "value2" }
 */
Message.prototype.addAll = function(obj) {
	for (key in obj) {
		if (typeof obj[key] == 'string' || typeof obj[key] == 'number') {
			this.add(key, obj[key]);
		}
	}
};

/**
 * method to help find messages loaded into memory, it returns an array of matched keys and values
 */
Message.prototype.find = function(regexp) {
	var ret = [];
	for (var key in this.messages) {
		var msg = this.messages[key];
		for (var i=0; i<msg.length; i++) {
			if (regexp.test(this.messages[key][i].message)) {
				ret.push({key: key, message: msg});
				break;
			}
		}
	}
	return ret;
};

/**
 * retrieves a message any params after key are interprted as {0}, {1}, ..., {n}
 * if the message isn't defined, the key is returned
 */
Message.prototype.get = function(key) {
	var ret = this.messages[key];
	var args = [];
	if (arguments[1] != null && arguments[1] instanceof Array) {
		args = arguments[1];
	} else {
		for ( var i = 1; i < arguments.length; i++) {
			args.push(arguments[i]);
		}
	}

	if (typeof ret == 'undefined' || ret == null) {
		ret = '%' + key + '%';
		if (args.length > 0) ret += '{' + (args.length - 1) + '}';
	} else {
		// variable substitution code goes here
		// clone message object
		var parts = [];
		for ( var i = 0; i < ret.length; i++) {
			parts.push({
			    message : ret[i].message,
			    variable : ret[i].variable
			});
		}

		// set variables on parts
		for ( var i = 0; i < args.length; i++) {
			for ( var j = 0; j < parts.length; j++) {
				if (parts[j].variable == i) {
					parts[j].message = args[i];
					delete parts[j].variable;
				}
			}
		}

		var tmpMsg = [];
		// now actually build the message
		for ( var i = 0; i < parts.length; i++) {
			tmpMsg.push(parts[i].message);
		}
		ret = tmpMsg.join('');
	}
	return ret;
};

Message.prototype.error = function(key) {
	var args = arguments;
	args[0] = "error." + args[0];
	return Message.get.apply(Message, args);
};

Message.prototype.load = function(msgGroup, callback) {
	msgGroup = {};
	RequestManager.loadMessages(msgGroup, function(data, status) {
		this.addAll(data);
		if (status) {
		} else {
			var errMessage = data.message;
			var preferences = {
	                "body":errMessage,
	                "header":"Validation Error"
	            };
			PopupDialog.showErrorMsg(preferences);
		}
		if (callback) callback();
	}.ctx(this));
	/*var data = {
		"status": "SUCCESS",
		"payload": {
			"article.title.label": "Article title...."
		}
	}
	this.addAll(data.payload);*/
};

Message = new Message();

if (typeof YAHOO != 'undefined') {
	if (YAHOO.lang.isValue(labels) && labels.length > 0) {
		for(var i =0; i < labels.length; i++ ){
			window[labels[i].key] = labels[i].value;
			Message.add(labels[i].key, labels[i].value);
		}
	}
}
