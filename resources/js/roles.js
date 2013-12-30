function Roles() {
	roles=new Array([]);
	$(".rolesContainer").remove();
	Loader.loadHTML('#container', 'Roles.html', false, function() {
		this.handleShow();
	}.ctx(this));
}

Roles.prototype.handleShow = function() {
	this.listProjects();
	this.getRoles();
	$("#projectList").change(function(){
		if($("#projectList").val()!="p0"){
			$('input:checkbox.avalRoles').removeAttr('checked');
			this.listUsers();
		}
		else{
			$("#userList").empty().append("<option value=\"u0\">select</option>");
			$('input:checkbox.avalRoles').removeAttr('checked'); 
		}
		
	}.ctx(this));
	$("#userList").change(function(){
		if($('#projectList').val()!="p0" && $('#userList').val()!="u0") {
		this.listUserRoles();
		}else{
			$.ambiance({
				 message : "Select project and user.(USERLIST)",
				 type : 'error'
				 });
		}
	}.ctx(this));
	$("#saveb").click(function(event) {
		if($('#projectList').val()!="p0" && $('#userList').val()!="u0") {
			event.preventDefault();
			this.deallocateRoles();
			
		}else{
			$.ambiance({
				 message : "Select project and user.",
				 type : 'error'
				 });
		}
	}.ctx(this));
	$("#cancelb").click(function(){
		this.listUserRoles();
	}.ctx(this));
};

Roles.prototype.listProjects = function() {
if($('#projectList').val()!="p0") {
	RequestManager.getProjects({}, function(data, success) {
	if (success) {
		var id=0;var name="";
		$("#projectList").empty().append("<option value=\"p0\">select</option>");
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
		$.ambiance({
			 message : data.message,
			 type : 'error'
			 });
	}
}.ctx(this));
}
else{
	$.ambiance({
		 message : "Select project to get users.",
		 type : 'error'
		 });
	}
};

Roles.prototype.listUsers = function() {
	if($('#projectList').val()!="p0") {
RequestManager.getProjectUsers({"payload":{"projectId":$("#projectList").val()}}, function(data, success) {
 if (success) {
	 $("#userList").empty().append("<option value=\"u0\">select</option>");
$.each(data,function(key,value){
	var email=value.email;
	$("#userList").append(
			"<option value="+ value.id
					+">"+email+"</option>");
});
	this.listUserRoles(); 
		}
 else{
	 $.ambiance({
		 message : data.message,
		 type : 'error'
		 });
 }
	 
}.ctx(this));
	}
	else{
		$.ambiance({
			 message : "Select project.",
			 type : 'error'
			 });
		}
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
	$.ambiance({
		 message : data.message,
		 type : 'error'
		 });
}
}.ctx(this));
};
Roles.prototype.listUserRoles = function() {
	if($('#projectList').val()!="p0" && $('#userList').val()!="u0") {
		input={"payload":{"projectId":$('#projectList').val(),"userId":$('#userList').val()}};
		RequestManager.listUserRoles(input, function(data, success) {
			if(success){
				roles =data.roleIds;
				$('input:checkbox').removeAttr('checked');
					$.each(roles,function(i,val){
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
					});
			}
			else{
				$.ambiance({
					 message : data.message,
					 type : 'error'
					 });
				$('input:checkbox').removeAttr('checked');
			}
	
		}.ctx(this));
	}
	else{
		$('input:checkbox.avalRoles').removeAttr('checked');
	}
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

Roles.prototype.sucessMessage=function(){
	$.ambiance({
		 message : "Roles allocated successfully.",
		 type : 'success'
		 });
};
Roles.prototype.allocateRoles=function(){
	var checked= $('input:checkbox:checked.avalRoles').map(function() {
		return parseInt(this.value);
	}).get();
	var allocate=new Array();
	$.each(checked,function(i,val){
		if($.inArray(val,roles)==-1){
			allocate.push(val);
		}
	});
	if(allocate.length>0){
	var inputToAllocate={"payload":{"projectId":$('#projectList').val(),"userId":$('#userList').val(),"roleIds":allocate}};
	RequestManager.allocateRoles(inputToAllocate,function(data,success){
		if(success){
			this.sucessMessage();
			this.roles=data.roleIds;
		}
		else{
			$.ambiance({
				 message : data.message,
				 type : 'error'
				 });
		}
	}.ctx(this));
	}else{
		this.sucessMessage();
	}
};
Roles.prototype.deallocateRoles=function(){
	var unchecked=$('input:checkbox:not(:checked).avalRoles').map(function(){
		return parseInt(this.value);
	}).get();
	var deallocate=new Array();
	$.each(unchecked,function(i,val){
		if($.inArray(val,roles)!=-1){
			deallocate.push(val);
		}
	});
	if(deallocate.length>0){
	var inputToDeallocate={"payload":{"projectId":$('#projectList').val(),"userId":$('#userList').val(),"roleIds":deallocate}};
	RequestManager.deallocateRoles(inputToDeallocate,function(data,success){
	if(success){
			this.allocateRoles();
			this.roles=data.roleIds;
	}else{
		$.ambiance({
			 message : data.message,
			 type : 'error'
			 });
	}
	}.ctx(this));
	}else{
			this.allocateRoles();
	}
};
var roles;
