function Router() {
	this.handleShow(function() {
		console.log("Router loaded")
		this.routeManager();
	}.ctx(this));	
}

Router.prototype.handleShow = function(callBack) {
	callBack();
}

Router.prototype.routeManager = function() {
	var self = this;
	routie({
	    '': function() {
	    	routie('login');
	    },
	    'login' : function(){
	    	//  TODO write a method to check user login
	    	// if false, load login page : App.loadLogin();  	App.loadPhoto();
	    	// if logged in, load corresponding methods
	    	console.log("login page")
	    	App.loadLogin();
	    	App.loadPhoto();
	    },
	    'home': function() {
	    	this.routeHome();
	    }
		/*'category/:categoryId/:engagementModel/:pageNo/:pageSize/:tag?': function(categoryId, engagementModel, pageNo, pageSize, tag) {
			self.routeCategories(categoryId, engagementModel, pageNo, pageSize, tag);
	    },
	    'pollCategory/:pageNo/:pageSize': function(pageNo, pageSize) {
	    	self.routePollCategories(pageNo, pageSize);
	    },
	    'article/:id/:categoryId/:selectedCommentId/:rightPanelCommentReplyFlag/:reload': function(id, categoryId, selectedCommentId, rightPanelCommentReplyFlag, reload) {
	    	self.routeArticles(id, categoryId,selectedCommentId, rightPanelCommentReplyFlag, reload);
	    },
	    'tagItems/:tagName': function(tagName) {
	    	self.routeTagItems(tagName);
	    },
	    'marker/:markerId/:pageNo/:pageSize': function(markerId, pageNo, pageSize) {
	    	self.routeMarkerItems(markerId, pageNo, pageSize);
	    },
	    'poll/:pollId': function(pollId) {
	    	self.routePolls(pollId)
	    },
	    'myAccount': function() {
	    	App.MyAccount()
	    },
	    'pollResult/:pollId': function(pollId) {
	    	self.routePollResults(pollId)
		},
		'staticContent/:staticContentId': function(staticContentId) {
			App.loadStaticPage(null, staticContentId);
		}*/
	});
}

Router.prototype.routeHome = function() {
	RequestManager.getLoginUser(input, function(data, success) {
		if(success){
			if (data != null ){
				App.loadOptions();
				App.loadWelcome();//3 params
				App.loadQisonLogo();
			}
			else{
				App.loadLogin();
		    	App.loadPhoto();
			}
		}else{			
			App.loadLogin();
	    	App.loadPhoto();
		}
	}.ctx(this));
}

	
	
}

Router.prototype.isUserLoggedIn = function() {
	App.loadOptions()
	
}

Router.prototype.routePollCategories = function(pageNo, pageSize) {
	pageNo = parseInt(pageNo)
	pageSize = parseInt(pageSize)
	var options = {};

	options.pageNo = pageNo;
	options.pageSize = pageSize;
	
	App.loadPollsIndex(options);
}

Router.prototype.routeArticles = function(id, categoryId, selectedCommentId, rightPanelCommentReplyFlag, reload) {
	id = parseInt(id);
	selectedCommentId = parseInt(selectedCommentId);
	categoryId = parseInt(categoryId);
	App.categoryId = categoryId;
	rightPanelCommentReplyFlag = (rightPanelCommentReplyFlag == "true") ? true : false;
	reload = (reload == "true") ? true : false;
	App.getArticleDetails(id, categoryId, selectedCommentId, rightPanelCommentReplyFlag, reload);
}

Router.prototype.routeTagItems = function(tagName) {
	App.getTagItems(tagName);
}

Router.prototype.routeMarkerItems = function(markerId, pageNo, pageSize) {
	markerId = parseInt(markerId)
	pageNo = parseInt(pageNo)
	pageSize = parseInt(pageSize)
	var options = {};

	options.markerId = markerId;
	options.pageNo = pageNo;
	options.pageSize = pageSize;
		
	App.articlesIndex(options);
}

Router.prototype.routePolls = function(pollId) {
	App.loadPollDetailsPage(pollId);
}

Router.prototype.routePollResults = function(pollId) {
	App.pollResults(pollId);
}