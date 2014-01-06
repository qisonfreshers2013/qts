function Roles() {
	roles = new Array([]);
	$(".rolesContainer").remove();
	Loader.loadHTML('#container', 'roles.html', false, function() {
		this.listProjects();
		this.getRoles();
		this.handleShow();
	}.ctx(this));
}

Roles.prototype.handleShow = function() {
	$("#projectList").focus();
	$("#projectList").change(
			function() {
				if ($("#projectList").val() != "p0") {
					$('input:checkbox.avalRoles').removeAttr('checked');
					this.listUsers();
				} else {
					$("#userList").empty().append(
							"<option value=\"u0\">select</option>");
					$('input:checkbox.avalRoles').removeAttr('checked');
				}

			}.ctx(this));
	$("#userList").change(function() {
		if ($('#projectList').val() != "p0" && $('#userList').val() != "u0") {
			this.listUserRoles();
		} else {
			$.ambiance({
				message : "Select project and user.",
				type : 'error'
			});
		}
	}.ctx(this));
	$("#saveb").click(function(event) {
		$("#ambiance-notification").empty();
		if ($('#projectList').val() != "p0" && $('#userList').val() != "u0") {
			event.preventDefault();
			this.deallocateRoles();

		} else if($('#projectList').val() != "p0" && $('#userList').val() == "u0"){
			$.ambiance({
				message : "Select User.",
				type : 'error'
			});
			$('#userList').focus();
		}else{
			$.ambiance({
				message : "Select project and user.",
				type : 'error'
			});
			$('#projectList').focus();
		}
	}.ctx(this));
	$("#cancelb").click(function() {
		$("#projectList option[value=\"p0\"]").attr("selected",true);
		$("#userList").empty().append("<option id=\"u0\">select</option>");
		$(".avalRoles").removeAttr("checked");
	});
};

Roles.prototype.listProjects = function() {
	if ($('#projectList').val() != "p0") {
		RequestManager.getProjects({}, function(data, success) {
			if (success) {
				var id = 0;
				var name = "";
				$("#projectList").empty().append(
						"<option value=\"p0\">select</option>");
				$.each(data, function(key1, value1) {
					$.each(value1, function(key2, value2) {
						if (key2 == 0)
							id = value2;
						else
							name = value2;
					});
					$("#projectList").append(
							"<option value=" + id + ">" + name + "</option>");
				});
			} else {
				$.ambiance({
					message : data.message,
					type : 'error'
				});
			}
		}.ctx(this));
	} else {
		$.ambiance({
			message : "Select project to get users.",
			type : 'error'
		});
	}
};

Roles.prototype.listUsers = function() {
	if ($('#projectList').val() != "p0") {
		RequestManager.getProjectUsers({
			"payload" : {
				"projectId" : $("#projectList").val()
			}
		}, function(data, success) {
			if (success) {
				var records=data.projectUserRecords;
				$("#userList").empty().append(
						"<option value=\"u0\">select</option>");
				$.each(records, function(key, value) {
					var email = value.email;
					$("#userList").append(
							"<option value=" + value.id + ">" + email
									+ "</option>");
				});
				this.listUserRoles();
			} else {
				$.ambiance({
					message : data.message,
					type : 'error'
				});
			}

		}.ctx(this));
	} else {
		$.ambiance({
			message : "Select project.",
			type : 'error'
		});
	}
};
Roles.prototype.getRoles = function() {
	RequestManager.listRoles({},function(data, success) {
		if (success) {
			$("#rolesList").empty();
			var id = 0;
			var name = "";
			$.each(data,function(key, value) {
				$.each(value,function(innerKey,innerValue) {
					if (innerKey == 0)
						id = innerValue;
					else
						name = innerValue.charAt(0).toUpperCase()+ innerValue.substr(1).toLowerCase();
			});
			if(id!=1){
			$("#role").append("<div id=\"roleRow\"><div id=\"rolesList\" class=\"rolesCheckbox\">"
														+ "<input type=\"checkbox\" id=role"
														+ id
														+ " class=\"avalRoles\" value="
														+ id
														+ ">"
														+ "</div>"
														+ "<div id=\"rolesList\" class=\"rolesName\"><p>"
														+ name
														+ "</p></div>"
								+ "</div>");
			$("#rolesList").css({"border-spacing" : "0",
				 "font-size" : "16px"
				});
			/*$(".rolesCheckbox").css({'margin' : '5%',
					 'position' : 'relative',
					 'width' : '10%'
				});
			$(".rolesName").css({"margin-top" : "-15%",
				 "margin-left" : "32%",
				 "position" : "relative",
				 "width" : "65%"
				});*/
			}
		});
		} else {
			$.ambiance({
				message : data.message,
				type : 'error'
			});
		}
	}.ctx(this));
};
Roles.prototype.listUserRoles = function() {
	if ($('#projectList').val() != "p0" && $('#userList').val() != "u0") {
		input = {
			"payload" : {
				"projectId" : $('#projectList').val(),
				"userId" : $('#userList').val()
			}
		};
		RequestManager.listUserRoles(input, function(data, success) {
			if (success) {
				roles = data.roleIds;
				$('input:checkbox').removeAttr('checked');
				$.each(roles, function(i, val) {
					$("input[value=" + val + "]").prop("checked", true);
				});
				
				
			} else {
				$.ambiance({
					message : data.message,
					type : 'error'
				});
				$('input:checkbox').removeAttr('checked');
			}

		}.ctx(this));
	} else {
		$('input:checkbox.avalRoles').removeAttr('checked');
	}
};

Roles.prototype.sucessMessage = function() {
	if(!deallocateSuccess && !allocateSuccess){
		if(roles.length==0){
			$.ambiance({
				message : "perform allocation or deallocation.",
				type : 'error'
			});
		}else{
			$.ambiance({
				message : "Roles already exists.",
				type : 'error'
			});
		}
		
	}else if(deallocateSuccess || allocateSuccess){
		deallocateSuccess=false;
		allocateSuccess=false;
		$.ambiance({
			message : "Successfull.",
			type : 'success'
		});
	}
	
};
Roles.prototype.allocateRoles = function() {
	var checked = $('input:checkbox:checked.avalRoles').map(function() {
		return parseInt(this.value);
	}).get();
	var allocate = new Array();
	$.each(checked, function(i, val) {
		if ($.inArray(val, roles) == -1) {
			allocate.push(val);
		}
	});
	if (allocate.length > 0) {
		status=true;
		var inputToAllocate = {
			"payload" : {
				"projectId" : $('#projectList').val(),
				"userId" : $('#userList').val(),
				"roleIds" : allocate
			}
		};
		RequestManager.allocateRoles(inputToAllocate, function(data, success) {
			if (success) {
				status=false;
				roles = data.roleIds;
				allocateSuccess=true;
				this.sucessMessage();
			} else {
				$.ambiance({
					message : data.message,
					type : 'error'
				});
			}
		}.ctx(this));
	}else if(checked.length==0 && !deallocateSuccess){
		$.ambiance({
			message : "Select atleast one role to allocate.",
			type : 'error'
		});
	}else {
		this.sucessMessage();
	}
};
Roles.prototype.deallocateRoles = function() {
	var unchecked = $('input:checkbox:not(:checked).avalRoles').map(function() {
		return parseInt(this.value);
	}).get();
	var deallocate = new Array();
	$.each(unchecked, function(i, val) {
		if ($.inArray(val, roles) != -1) {
			deallocate.push(val);
		}
	});
	if (deallocate.length > 0) {
		//status=true;
		var inputToDeallocate = {
			"payload" : {
				"projectId" : $('#projectList').val(),
				"userId" : $('#userList').val(),
				"roleIds" : deallocate
			}
		};
		RequestManager.deallocateRoles(inputToDeallocate, function(data,
				success) {
			if (success) {
				//status=false;
				roles = data.roleIds;
				deallocateSuccess=true;
				this.allocateRoles();
			} else {
				$.ambiance({
					message : data.message,
					type : 'error'
				});
			}
		}.ctx(this));
	} else {
		
		this.allocateRoles();
	}
};

var roles;
var deallocateSuccess=false;
var allocateSuccess=false;
//var status=false;