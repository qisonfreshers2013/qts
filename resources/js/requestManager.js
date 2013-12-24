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
        //PopupDialog.showErrorMsg(preferences);
        alert(preferences);
    }

}
/*RequestManager.prototype.testService = function(data, callback) {
	this.sendToServer('demo/testService', data, callback);
}

RequestManager.prototype.testHandler = function(data, callback) {
	this.sendToServer('demo/testHandler', data, callback);
}*/
RequestManager.prototype.authenticate=function(data,callback){
	this.sendToServer('user/authenticate', data, callback);
}
RequestManager.prototype.listProjects=function(data,callback){
	this.sendToServer('project/getProjects', data, callback);
}
RequestManager.prototype.listUsers=function(data,callback){
	this.sendToServer('project/getProjectUsers', data, callback);
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
var RequestManager = new RequestManager();