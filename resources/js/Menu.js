function Menu(roleIds) {
	Loader.loadHTML('.container', 'Menu.html', true, function(){
		this.handleShow(roleIds);
	}.ctx(this));
}
Menu.prototype.handleShow=function(roleIds){
	
	if(roleIds.contains(1)){
		$('#main').append('<li><a id="um">User Management</a></li>');	
		$('#pm').append('<ul id="projectOptions">'+
		        '<li id="addProject" class="dropDown">Add Project</li>'+
				'<li id="allocateUsers" class="dropDown">Allocate Users</li>'+
				'<li id="addRoles" class="dropDown">Add Roles</li>'+
				'<li id="searchProject" class="dropDown">Search project</li></ul>');
		
		
		
		$('#projectOptions > li:hover').css( 'background-color','#CCCCCC').css( 'cursor','pointer');	
		$('#projectOptions').css( 'background-color', 'rgb(238, 238, 238');	
		
		
		$('#su').click(function(){
			App.loadSearchUser();
		});
		
		$('#um').click(function(){
			App.loadAddUser();
		});
		
		$('#addProject').click(function(){
			$('#content').remove();
			App.loadAddProject();
			
		});
		
		$('#allocateUsers').click(function(){
			$('#content').remove();
			App.loadAllocateUsersToProject();
			
		});
		
		$('#addRoles').click(function(){
			$('#content').remove();
			App.loadAddRoles();
			
		});
		
		$('#searchProject').click(function(){
			$('#content').remove();
			App.loadSearchProject();
			
		});
		$('#prm').click(function(){
			App.loadAddProjectRelease();
			App.loadSearchProjectRelease(roleIds);
		});
		
		
		
		if(roleIds.contains(2)){
			$('#main').append('<li><a id="tsa">Time Sheet Approval</a></li>');
			
			
			$('#projectOptions').css( 'width', '17.5%').css('margin-left','-2%');
			
			$('#tsa').click(function(){
				App.loadTimeSheetApproval();
			});
		}
		else{
			$('#projectOptions').css( 'width', '24%').css('margin-left','-2%');
			$('#main > li').css('width','24%');
		}
	}
	
	
	
	else if(roleIds.contains(2)){
		$('#main').append('<li><a id="tsa">Time Sheet Approval</a></li>');
		
		//click events for approver
		$('#tsa').click(function(){
			App.loadTimeSheetApproval();
		});
		
		$('#pm').click(function(){
			App.loadSearchProject();
		});
		
		$('#prm').click(function(){
			App.loadSearchProjectRelease(roleIds);
		});
		
		$('#su').click(function(){
			App.loadSearchUser();
		});
		
		if(roleIds.contains(3)){
			$('#main').append('<li><a id="tsf">Time Sheet Filling</a>');
			
			$('#tsf').click(function(){
				App.loadTimeSheetFilling();
			});
			
		}
		else{
			$('#main > li').css('width','24%');
		}
	}else{
		
		
		$('#main').append('<li><a id="tsf">Time Sheet Filling</a>');
		$('#main > li').css('width','24%');
		
		
		//click events for member role
		$('#pm').click(function(){
			App.loadSearchProject();
		});
		
		$('#prm').click(function(){
			App.loadSearchProjectRelease(roleIds);
		});
		
		$('#su').click(function(){
			App.loadSearchUser();
		});
		
		$('#tsf').click(function(){
			App.loadTimeSheetFilling();
		});
	}
	
}