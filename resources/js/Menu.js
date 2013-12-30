function Menu(roleIds) {
	Loader.loadHTML('.container', 'Menu.html', true, function(){
		this.handleShow(roleIds);
	}.ctx(this));
}
Menu.prototype.handleShow=function(roleIds){
	
	if(roleIds.contains(1)){
		$('#main').append('<li><a id="um">User Management</a></li>');	
		$('#pm').append('<ul id="projectOptions">'+
		        '<li id="addProject" class="dropDown"><a>Add Project</a></li>'+
				'<li id="allocateUsers" class="dropDown"><a>Allocate Users</a></li>'+
				'<li id="addroleIds" class="dropDown"><a>Add Roles</a></li>'+
				'<li id="searchProject" class="dropDown"><a>Search Project</a></li></ul>');
		
		
		
		$('a:hover').css( 'background-color','#CCCCCC').css( 'cursor','pointer');	
		$('#projectOptions').css( 'background-color', 'rgb(238, 238, 238');	
		
		
		$('#su').click(function(){
			$('#content').remove();
			App.loadSearchUser(roleIds);
		});
		
		$('#um').click(function(){
			$('#content').remove();
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
		
		$('#addroleIds').click(function(){
			$('#content').remove();
			App.loadRoles();
			
		});
		
		$('#searchProject').click(function(){
			$('#content').remove();
			App.loadSearchProject();
			
		});
		$('#prm').click(function(){
			$('#content').remove();
			App.loadSearchProjectRelease(roleIds);
			if(roleIds.contains(1)){
				App.loadAddProjectRelease();
			}
		});
		
		
		
		if(roleIds.contains(2)){
			$('#main').append('<li><a id="tsa">Time Sheet Approval</a></li>');
			
			
			$('#projectOptions').css( 'width', '17.5%').css('margin-left','-2%');
			
			$('#tsa').click(function(){
				$('#content').remove();
				App.loadApproverTimeSheetSearch();
				App.loadDefaultApproverPage();
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
			$('#content').remove();
			App.loadApproverTimeSheetSearch();
			App.loadDefaultApproverPage();
		});
		
		$('#pm').click(function(){
			$('#content').remove();
			App.loadSearchProject();
		});
		
		$('#prm').click(function(){
			$('#content').remove();
			App.loadSearchProjectRelease(roleIds);
			if(roleIds.contains(1)){
				App.loadAddProjectRelease();
			}
			
		});
		
		$('#su').click(function(){
			$('#content').remove();
			App.loadSearchUser(roleIds);
		});
		
		if(roleIds.contains(3)){
			$('#main').append('<li><a id="tsf">Time Sheet Filling</a>');
			
			$('#tsf').click(function(){
				$('#content').remove();
				App.loadTimeSheetFilling();
				App.loadDefaultTimeSheetPage();
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
			$('#content').remove();
			App.loadSearchProject();
		});
		
		$('#prm').click(function(){
			$('#content').remove();
			App.loadSearchProjectRelease(roleIds);
			if(roleIds.contains(1)){
				App.loadAddProjectRelease();
			}
			
		});
		
		$('#su').click(function(){
			$('#content').remove();
			App.loadSearchUser(roleIds);
		});
		
		$('#tsf').click(function(){
			$('#content').remove();
			App.loadTimeSheetFilling();
			App.loadDefaultTimeSheetPage();
		});
	}
	
}