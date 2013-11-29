
(function($) {
	
	/**
	 * example usage 
	 * $('.articleTitle', newEle).qtsLink('App', 'getArticleDetails', params);
	 * 
	 */
	
	// TODO this plugin doesn't work fine for IE browser, need to change the code
	
	$.fn.qtsLink = function(constructor, method, params) {
		return this.each(function(index, element) {
			var j = $(this);
			var action = 'javascript: ' + constructor + '.' + method;
			if (typeof params == 'undefined') {
				action += '();';
			} else {
				action += '(' + params + ');';
			}
			if (j.is("a")) {
				j.attr('href', action);
			} else {
				j.attr('onClick', action);
				j.css('cursor', 'pointer');
			}
		});
	}
	
		
	$.fn.scrollMinimal = function(smooth) {
		  var cTop = this.offset().top;
		  var cHeight = this.outerHeight(true);
		  var windowTop = $(window).scrollTop();
		  var visibleHeight = $(window).height();

		  if (cTop < windowTop) {
		    if (smooth) {
		      $('body').animate({'scrollTop': cTop}, 'slow', 'swing');
		    } else {
		      $(window).scrollTop(cTop);
		    }
		  } else if (cTop + cHeight > windowTop + visibleHeight) {
		    if (smooth) {
		      $('body').animate({'scrollTop': cTop - visibleHeight + cHeight}, 'slow', 'swing');
		    } else {
		      $(window).scrollTop(cTop - visibleHeight + cHeight);
		    }
		  }
	}
	

	$.fn.qtsDate = function(timeStamp, onlyDate) {
		var date = new Date(timeStamp);
		var dayOfMonth = date.getDate();			
		var fullYear = date.getFullYear();			
		var month=new Array();
		month[0]="Jan";
		month[1]="Feb";
		month[2]="Mar";
		month[3]="Apr";
		month[4]="May";
		month[5]="Jun";
		month[6]="Jul";
		month[7]="Aug";
		month[8]="Sep";
		month[9]="Oct";
		month[10]="Nov";
		month[11]="Dec";
		var monthOfYear = month[date.getMonth()]; 			
		var hours= date.getHours();				
		if(hours <= 9){
			hours = "0" + hours;				
		} 			
		var minutes = date.getMinutes();
		if(minutes > 0 && minutes < 10){
			minutes = "0" + minutes;
		}
		if(minutes == 0){
			minutes = "00";
		}
		var uiTimeFormat = '';
		if(onlyDate) {
			uiTimeFormat = dayOfMonth +"-" +monthOfYear+ "-" +fullYear;
		} else {
			uiTimeFormat = dayOfMonth +"-" +monthOfYear+ "-" +fullYear+"  "+hours+":"+minutes+" " ;
		}
		$(this).text(uiTimeFormat);
	}

	
	$.fn.msgkey = function() {
		var args = [];
		for (var i=0; i<arguments.length; i++) {
			args.push(arguments[i]);
		}

		/*return this.each(function(index, element) {
			var key = $(this).attr('msgkey');
			if (key!=null && key!="") {
				var msg = Message.get(key, args);
				$(this).text(msg);
			}
		});*/
		return $('*').each(function(index, element) {
			var key = $(this).attr('msgkey');
			if (key!=null && key!="") {
				var msg = Message.get(key, args);
				$(this).text(msg);
			}
		});
	}
	
	
	/**
	 * Selecting Text in an Element
	 * 
	 * usage : $(this).selText();
	 * 
	 * */
	$.fn.selText = function() {
        var obj = this[0];
        if ($.browser.msie) {
            var range = obj.offsetParent.createTextRange();
            range.moveToElementText(obj);
            range.select();
        } else if ($.browser.mozilla || $.browser.opera) {
            var selection = obj.ownerDocument.defaultView.getSelection();
            var range = obj.ownerDocument.createRange();
            range.selectNodeContents(obj);
            selection.removeAllRanges();
            selection.addRange(range);
        } else if ($.browser.safari) {
            var selection = obj.ownerDocument.defaultView.getSelection();
            selection.setBaseAndExtent(obj, 0, obj, 1);
        }
        return this;
    }
	
	
	$.fn.isOnScreen = function(){
	    
	    var win = $(window);
	    
	    var viewport = {
	        top : win.scrollTop(),
	        left : win.scrollLeft()
	    };
	    viewport.right = viewport.left + win.width();
	    viewport.bottom = viewport.top + win.height();
	    
	    var bounds = this.offset();
	    if (bounds) {
	    	bounds.right = bounds.left + this.outerWidth();
		    bounds.bottom = bounds.top + this.outerHeight();
		    
		    return (!(viewport.right < bounds.left || viewport.left > bounds.right || viewport.bottom < bounds.top || viewport.top > bounds.bottom));
	    }
	}
	
	
	$.fn.datePickerVal = function(timeStamp) {
		var date = new Date(timeStamp);
		var dayOfMonth = date.getDate();			
		var fullYear = date.getFullYear();			
		var month=new Array();
		month[0]="Jan";
		month[1]="Feb";
		month[2]="Mar";
		month[3]="Apr";
		month[4]="May";
		month[5]="Jun";
		month[6]="Jul";
		month[7]="Aug";
		month[8]="Sep";
		month[9]="Oct";
		month[10]="Nov";
		month[11]="Dec";
		var monthOfYear = month[date.getMonth()]; 			
		var hours= date.getHours();				
		if(hours <= 9){
			hours ="0"+hours;				
		} 			
		var minutes = date.getMinutes();
		if(minutes > 0 && minutes < 10){
			minutes = "0" + minutes;
		}
		if(minutes == 0){
			minutes = "00";
		}
		
		/*var seconds = date.getSeconds();
		if(seconds > 0 && seconds < 10){
			seconds = "0" + seconds;
		}
		if(seconds == 0){
			seconds = "00";
		}
		
		var	uiTimeFormat = dayOfMonth + " " + monthOfYear + ", " + fullYear + "  " + hours + ":" + minutes + ":" + seconds;*/
		var	uiTimeFormat = dayOfMonth + " " + monthOfYear + ", " + fullYear + "  " + hours + ":" + minutes;
		$(this).val(uiTimeFormat);
	}
	
	
})(jQuery);