function getCookie(c_name) {
	var i,x,y,ARRcookies=document.cookie.split(";");
	for (i=0;i<ARRcookies.length;i++) {
		x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
		y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
		x=x.replace(/^\s+|\s+$/g,"");
		if (x==c_name) {
			return unescape(y);
		}
	}
}

function setCookie(c_name, value, exdays) {
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + exdays);
	//console.log("===="+value)
	var c_value = escape(value) + ((exdays == null) ? "" : "; expires="+exdate.toUTCString());
	var domainName = location.host.indexOf("localhost");
//	indexOf(substr, [start])
	var status = location.host
			.match("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");

	if (domainName != -1 || status == null) {
		document.cookie = c_name + "=" + c_value + ";path=/";
	} else {
		document.cookie = c_name + "=" + c_value + ";path=/;domain="
				+ location.host;
	}
	// alert(location.host);
	//console.log("===="+getCookie("qtsSessionId"))
}

// Todo: method to appaend  link to some text


function getParameterByName(name) {
	name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
	var regexS = "[\\?&]"+name+"=([^&#]*)";
	var regex = new RegExp( regexS );
	var results = regex.exec( window.location.href );
	if( results == null )
		return "";
	else
		return decodeURIComponent(results[1].replace(/\+/g, " "));
}

function IsValidImageUrl(url, container, imgExpected) {
    $("<img>", {
        src: url,
        error: function() {
        	if (imgExpected) {
        		$(container).attr('src', imgExpected);
        	} else {
        		$(container).attr('src', 'img/upload.png');
        		$(container).unbind("click");
        	}
        },
        load: function() {
        	$(container).attr('src', url);
        }
    });
}

function getFileType(filename) {
	var imageTypeArray = ['jpg', 'jpeg', 'gif', 'bmp', 'psd', 'tif'];
	var videoTypeArray = ['mpeg', 'avi', 'rm', 'mp4', '3gp', 'ogm', 'mkv'];
	var audioTypeArray = ['mp3', 'wav', 'itdb'];
	
	var fileExt = filename.split('.').pop();
	fileExt = fileExt.toLowerCase()
	
	var isFileType = $.inArray(fileExt, imageTypeArray);
	if (isFileType != -1) {
		console.log('its an image');
		return isFileType;
	}
	
	isFileType = $.inArray(fileExt, videoTypeArray);
	if (isFileType != -1) {
		console.log('its video');
		return isFileType;
	}
	
	isFileType = $.inArray(fileExt, audioTypeArray);
	if (isFileType != -1) {
		console.log('its audio');
		return isFileType;
	}
	
	//return filename.split('.').pop();
}

function getFileTypeFromFileUrl(fileUrl) {
	var imageTypeArray = [ 'jpg', 'jpeg', 'gif', 'bmp', 'psd', 'tif', 'png' ];
	var videoTypeArray = [ 'mpeg', 'avi', 'rm', 'mp4', '3gp', 'ogm', 'mkv',
			"webm" ];
	var audioTypeArray = [ 'mp3', 'wav', 'itdb' ];
	
	// var fileExt = fileUrl.split('.').pop();
	if(fileUrl != null){
		var fileName = fileUrl[0];
	}
	fileExt = typeof fileName != "undefined" ? fileName.substring(
			fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase()
		: false;
		
	if ($.inArray(fileExt, imageTypeArray) != -1) {
		return "image";
	} else if ($.inArray(fileExt, videoTypeArray) != -1) {
		return "video";
	} else if ($.inArray(fileExt, audioTypeArray) != -1) {
		return "audio";
	} else {
		return "other";
	}

}