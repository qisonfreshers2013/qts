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
            console.log(data)
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
        //PopupDialog.showErrorMsg(preferences);
        alert(preferences);
    }

}

RequestManager.prototype.authenticate=function(data,callback){
	this.sendToServer('user/authenticate', data, callback);
}

RequestManager.prototype.getProjects = function(data, callback) {
	this.sendToServer('project/getProjects', data, callback);
}

RequestManager.prototype.addProjectRelease = function(data, callback) {
	this.sendToServer('release/add', data, callback);
}

RequestManager.prototype.getProjectReleases = function(data, callback) {
	this.sendToServer('release/get', data, callback);
}

RequestManager.prototype.authenticate=function(data,callback){
	this.sendToServer('user/authenticate',data,callback);
}

RequestManager.prototype.deleteProjectRelease=function(data,callback){
	this.sendToServer('release/delete',data,callback);
}

RequestManager.prototype.listRoles=function(data,callback){
	this.sendToServer('role/get', data, callback);
}

RequestManager.prototype.listUserRoles=function(data,callback){
	this.sendToServer('role/getUserRoles', data, callback);
}

RequestManager.prototype.allocateRoles=function(data,callback){
	this.sendToServer('role/allocateRoles', data, callback);
}

RequestManager.prototype.deallocateRoles=function(data,callback){
	this.sendToServer('role/deallocateRoles', data, callback);
}

RequestManager.prototype.addTimeEntry = function(data, callback) {
	this.sendToServer('timeEntry/add', data, callback);
}
RequestManager.prototype.updateTimeEntry = function(data, callback) {
	this.sendToServer('timeEntry/update', data, callback);
}
RequestManager.prototype.approve = function(data, callback) {
	this.sendToServer('timeEntry/approveTimeEntry', data, callback);
}
RequestManager.prototype.reject = function(data, callback) {
	this.sendToServer('timeEntry/rejectTimeEntry', data, callback);
}
RequestManager.prototype.deleteTimeEntry = function(data, callback) {
	this.sendToServer('timeEntry/delete', data, callback);
}
RequestManager.prototype.searchTimeEntriesByUser = function(data, callback) {
	this.sendToServer('timeEntry/searchTimeEntriesByUser', data, callback);
}
RequestManager.prototype.searchTimeEntriesByApprover = function(data, callback) {
	this.sendToServer('timeEntry/searchTimeEntriesByApprover', data, callback);
}
RequestManager.prototype.getTimeEntryObjectById = function(data, callback) {
	this.sendToServer('timeEntry/getTimeEntry', data, callback);
}
RequestManager.prototype.submit = function(data, callback) {
	this.sendToServer('timeEntry/submitTimeEntries', data, callback);
}


var RequestManager = new RequestManager();