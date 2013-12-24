function RequestManager() {
    this.apiPrefix = '/services/v1/';
}
;



RequestManager.prototype.sendToServer = function (api, data, callback, options) {
    var serviceURL = this.apiPrefix + api;

    $.ajax({
        url:serviceURL,
        contentType:'application/json',
        dataType:'json',
        type:'POST',
        success:function (data) {
            //console.log(data)
            if (data.status && data.status == 'SUCCESS') {
                callback(data.payload, true);
            } else {
                if (data.payload.code == 0) {
                    handleFailure(data.payload);
                } else {
                    callback(data.payload, false);
                }
            }

        }, //TODO Instead of creating payload at the calls, create here.
        data:JSON.stringify(data),
        error:function (xhr, error, exception) {
            //TODO remove console.log
            console.log(xhr, error, exception);
        }
    });

    function handleFailure(data) {
        var errCode = data.code;
        if (errCode == 0) {
            errMsg = "UnKnown Error in : " + serviceURL;
        }
        var preferences = {
            "body":errMsg,
            "header":"Error"
        };
        PopupDialog.showErrorMsg(preferences);
    }

}

RequestManager.prototype.testService = function(data, callback) {
	this.sendToServer('demo/testService', data, callback);
}

RequestManager.prototype.testHandler = function(data, callback) {
	this.sendToServer('demo/testHandler', data, callback);
}


RequestManager.prototype.addProject=function(data,callback){
	 this.sendToServer('project/add', data, callback);
	}
	RequestManager.prototype.getProjects=function(data,callback){
	 this.sendToServer('project/getProjects', data, callback);
	}
	RequestManager.prototype.getProjectsForUser=function(data,callback){
	 this.sendToServer('project/getProjectsForUser', data, callback);
	}
	RequestManager.prototype.allocateUsersToProject=function(data,callback){
	 this.sendToServer('project/allocateUsersToProject', data, callback);
	}
	RequestManager.prototype.deAllocateUsersFromProject=function(data,callback){
	 this.sendToServer('project/deAllocateUsersFromProject', data, callback);
	}
	RequestManager.prototype.getProjectUsers=function(data,callback){
	 this.sendToServer('project/getProjectUsers', data, callback);
	}
	RequestManager.prototype.getProjectNonUsers=function(data,callback){
	 this.sendToServer('project/nonUsersOfProject', data, callback);
	}

	RequestManager.prototype.addProjectRelease = function(data, callback) {
	 this.sendToServer('release/add', data, callback);
	}

	RequestManager.prototype.getProjectReleases = function(data, callback) {
	 this.sendToServer('release/get', data, callback);
	}


	RequestManager.prototype.deleteProjectRelease=function(data,callback){
	 this.sendToServer('release/delete',data,callback);
	}






















































































































































































RequestManager.prototype.addUser = function(data, callback) {
	this.sendToServer('user/add', data, callback);
}



RequestManager.prototype.search = function(data,callback) {
	this.sendToServer('user/search', data, callback);
}

RequestManager.prototype.authenticate = function(data,callback){
	this.sendToServer('user/authenticate', data, callback);
}

RequestManager.prototype.getLoginUserDetails = function(data,callback){
	this.sendToServer('user/getLoginUserDetails', data, callback);
}


RequestManager.prototype.updateLoginUserDetails = function(data, callback) {
	this.sendToServer('user/updateLoginUserDetails', data, callback);
}

RequestManager.prototype.getUserDetails = function(data,callback){
	this.sendToServer('user/getUserDetails', data, callback);
}

RequestManager.prototype.getEmployeeIds = function(data, callback) {
	this.sendToServer('user/getEmployeeIds', data, callback);
}

RequestManager.prototype.updateUserDetails = function(data, callback) {
	this.sendToServer('user/updateUserDetails', data, callback);
}

RequestManager.prototype.deleteUser = function(data, callback) {
	this.sendToServer('user/delete', data, callback);
}

RequestManager.prototype.logout = function(data, callback) {
	this.sendToServer('user/logout', data, callback);
}
RequestManager.prototype.sendMail = function(data, callback) {
	this.sendToServer('user/forgotPassword', data, callback);
}


var RequestManager = new RequestManager();