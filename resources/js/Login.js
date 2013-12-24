/**
 * 
 */
function Login() {
	Loader.loadHTML('#rightContainer', 'Login.html', true, function() {
		this.handleShow();
	}.ctx(this));
}

Login.prototype.handleShow = function() {
	$('.signInBtn').click(function() {
		//$('.container').empty();
		this.testLogin();
	}.ctx(this));
};

Login.prototype.testLogin = function() {
	var userId=$('#userId').val();
	var pwd=$('#password').val();

	RequestManager.authenticate({"payload":{"authType":"REGULAR","email":userId,"password":pwd}}, function(data, success) {
		   if (success) {
			var roleIds=data.roleIds;
		    var  token = data.sessionToken;
		    setCookie('qtsSessionId', token, null);
			App.loadWelcome();
			App.loadOptions(roleIds);
		    }
		   else
			   alert(data.message);
	}.ctx(this));

}

Array.prototype.contains = function(k) {
    for(var p in this){
        if(this[p] === k){
            return true;
        }
    }
    return false;
}

var Login=new Login();
