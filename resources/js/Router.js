function Router() {
	this.handleShow(function() {
		this.routeManager();
	}.ctx(this));	
}

Router.prototype.handleShow = function(callBack) {
	App.loadHeader();
	
	App.whiteLabelConfigs();
	/*if (getParameterByName("action") == "resetPassword") {
		Loader.loadResetPassword(function() {
			setTimeout('new ResetPassword(getParameterByName("resetPasswordToken"))', 1000);
		});
	}*/
	callBack();
}

Router.prototype.routeManager = function() {
	var self = this;
	routie({
	    '': function() {
	    	//$('#contentRegion').empty();
	    	App.loadRightPanel();
	    	App.loadIndexFiles();
	    },
	    'home': function() {
	    	App.reloadIndex();
	    },
		'category/:categoryId/:engagementModel/:pageNo/:pageSize/:tag?': function(categoryId, engagementModel, pageNo, pageSize, tag) {
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
		},
		'login' : function() {
			App.reloadIndex(function() {
				if(App.communtyType == 1) {
					$('#loginModal').modal('show');
					$('.user_email').val('');
					$('.user_password').val('');
					$('.alert-success').show();
					$('.successMsg').text(Message.get('common.pwdchangedmsg.message'));
				} else {
					$('.loginPanel').show();
					$('.userEmail').val('');
					$('.userPassword').val('');
					$('.login .alert-success').show();
					$('.login .successMsg').text(Message.get('common.pwdchangedmsg.message'));
				}
			});
		}
	});
}

Router.prototype.routeCategories = function(categoryId, engagementModel, pageNo, pageSize, tag) {
	categoryId = parseInt(categoryId);
	App.categoryId = categoryId;
	engagementModel = parseInt(engagementModel);
	pageNo = parseInt(pageNo);
	pageSize = parseInt(pageSize);
	var options = {};

	options.engagementModel = engagementModel;
	options.categoryId = categoryId;
	options.pageNo = pageNo;
	options.pageSize = pageSize;
	if(tag) {
		options.tagName = tag;
	}
	
	switch (engagementModel) {
	case 100:
		App.articlesIndex(options);
		break;
	case 200:
		App.loadPollsIndex(options);
		break;
	case 300:
		break;
	case 5000:
		App.loadStaticPage(categoryId);
		break;
	}
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