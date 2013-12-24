function Roles() {
	this.roles=new Array();
	$(".rolesContainer").remove();
	Loader.loadHTML('#container', 'roles.html', false, function() {
		this.handleShow();
	}.ctx(this));
}

Roles.prototype.handleShow = function() {
	this.listProjects();
	this.getRoles();
	$("#projectList").change(function(){
			this.listUsers();
	}.ctx(this));
	$("#userList").change(function(){
		this.listUserRoles();
	}.ctx(this));
	$("#saveb").click(function(event) {
		event.preventDefault();
		this.allotRoles();
	}.ctx(this));
	$("#cancelb").click(function(){
		this.listUserRoles();
	}.ctx(this));
};

Roles.prototype.listProjects = function() {
RequestManager.listProjects({}, function(data, success) {
if (success) {
	var id=0;var name="";
	$("#projectList").empty().append("<option>select</option>");
	$.each(data,function(key1,value1){
		$.each(value1,function(key2,value2){
			if(key2==0)
				id=value2;
			else
				name=value2;
		});
		$("#projectList").append("<option value="+id+">"+name+"</option>");	
	});
	}
else{
		alert(data.message);
	}
}.ctx(this));
};

Roles.prototype.listUsers = function() {
RequestManager.listUsers({"payload":{"projectId":$("#projectList").val()}}, function(data, success) {
 if (success) {
	 $("#userList").empty();//.append("<option>select</option>");
$.each(data,function(key,value){
	var fname=(value.firstName+" ").concat(value.lastName);
	$("#userList").append(
			"<option value="+ value.id
					+">"+fname+"</option>");
});
	this.listUserRoles(); 
		}
 else{
	 alert(data.message);
 }
	 
}.ctx(this));
};
Roles.prototype.getRoles = function() {
RequestManager.listRoles({}, function(data, success) {
if (success) {
	$("#rolesList").empty();
		var id=0;var name="";
		$.each(data,function(key,value){
			$.each(value,function(innerKey,innerValue){
				if(innerKey==0)
					id=innerValue;
				else
					name=innerValue.charAt(0).toUpperCase()+innerValue.substr(1).toLowerCase();
			});
			$("#rolesList").append(
					"<tr>" +
					"<td><input type=\"checkbox\" id=role"+id+" class=\"avalRoles\" value="+id+"></td>"+
					"<td>"+name+"</td>" +
					"</tr>"
			);
		});
	}
else{
	alert(data.message);
}
}.ctx(this));
};
Roles.prototype.listUserRoles = function() {
var input={"payload":{"projectId":$('#projectList').val(),"userId":$('#userList').val()}};
RequestManager.listUserRoles(input, function(data, success) {
	if(success){
		roles =data.roleIds;
		$('input:checkbox').removeAttr('checked');
		for(var val of roles){
			switch (val) {
			case 1:
				$("input[value=3]").prop("disabled",true);
				$("input[value=1]").prop("disabled",false);
				break;
			case 3:
				$("input[value=1]").prop("disabled",true);
				$("input[value=3]").prop("disabled",false);
				break;
			}
			$("input[value="+val+"]").prop("checked", true);
		}
	}
	else{
		alert(data.message);
	}
}.ctx(this));

$("#role3").change(function() {
    if(this.checked) {
    	$("input[value=1]").prop("disabled",true);
    }else{
    	$("input[value=1]").prop("disabled",false);
    }
});
$("#role1").change(function() {
    if(this.checked) {
    	$("input[value=3]").prop("disabled",true);
    }else{
    	$("input[value=3]").prop("disabled",false);
    }
});
};

Roles.prototype.allotRoles = function() {
	this.allocateRoles();
	this.deallocateRoles();
};
Roles.prototype.allocateRoles=function(cb){
	var checked= $('input:checkbox:checked.avalRoles').map(function() {
		return this.value;
	}).get();
	/*var allocate=new Array();
	for(val of checked){
		if(jQuery.inArray(val,roles)===-1)
			allocate.push(val);
	}*/
	//alert("allocate: "+allocate);
	alert("checked: "+checked);
	var inputToAllocate={"payload":{"projectId":$('#projectList').val(),"userId":$('#userList').val(),"roleIds":checked}};
	RequestManager.allocateRoles(inputToAllocate,function(data,success){
		if(success){
			/*$.ambiance({
			    message : allocate,
			    type : 'success'
			   });*/
			//alert(data.roleIds+" roles are available after allocating.");
		}
		else{
			alert(data.message);
		}
	}.ctx(this));
};
Roles.prototype.deallocateRoles=function(){
	var unchecked=$('input:checkbox:not(:checked).avalRoles').map(function(){
		return this.value;
	}).get();
	/*var deallocate=new Array();
	for(val of unchecked){
		if(jQuery.inArray(val,roles)===-1){
			alert("inside")
		}
		else{
			deallocate.push(val);
		}*/
	alert("unchecked: "+unchecked);
	var inputToDeallocate={"payload":{"projectId":$('#projectList').val(),"userId":$('#userList').val(),"roleIds":unchecked}};
	RequestManager.deallocateRoles(inputToDeallocate,function(data,success){
	if(success){
		alert(data.roleIds+" roles are available after deallocating.");	
	}
	else{
		alert(data.message);
	}
	}.ctx(this));
};

// var Roles = new Roles();
