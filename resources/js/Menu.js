function Menu() {
	Loader.loadHTML('.container', 'Menu.html', true, function(){
		this.handleShow();
	}.ctx(this));
}
Menu.prototype.handleShow=function(){
	
	if(roleNames.contains('ADMIN')){
		$('#main').append('<li><a id="um">User Management</a></li>');	
		$('#pm').append('<ul id="projectOptions">'+
		        '<li id="addProject" class="dropDown"><a>Add Project</a></li>'+
				'<li id="allocateUsers" class="dropDown"><a>Allocate Users</a></li>'+
				'<li id="addroleIds" class="dropDown"><a>Add Roles</a></li>'+
				'<li id="searchProject" class="dropDown"><a>Search Project</a></li></ul>');
		
		
		
		$('a:hover').css( 'background-color','#CCCCCC').css( 'cursor','pointer');	
		$('#projectOptions').css( 'background-color', 'rgb(238, 238, 238');	
		
		$('#projectOptions').css( 'width', '24%').css('margin-left','-2%');
		$('#main > li').css('width','24%');
		
		$('#um').click(function(){
			if(status){
				if(confirm('Do you want to leave the page. Do you want to leave page')){
					status=false;
					$('#content').remove();
					App.loadAddUser();
				}
			}else{
				$('#content').remove();
				App.loadAddUser();
			}
			
		});
		
		$('#addProject').click(function(){
			if(status){
				if(confirm('Do you want to leave the page. Do you want to leave page')){
					status=false;
					$('#content').remove();
					App.loadAddProject();
				}
			}else{
				$('#content').remove();
				App.loadAddProject();
			}
			
			
		});
		
		$('#allocateUsers').click(function(){
			if(status){
				if(confirm('Do you want to leave the page. Do you want to leave page')){
					status=false;
					$('#content').remove();
					App.loadAllocateUsersToProject();
				}
			}else{
				$('#content').remove();
				App.loadAllocateUsersToProject();
			}
			
			
		});
		
		$('#addroleIds').click(function(){
			if(status){
				if(confirm('Do you want to leave the page. Do you want to leave page')){
					status=false;
					$('#content').remove();
					App.loadRoles();
				}
			}else{
				$('#content').remove();
				App.loadRoles();
			}
			
			
		});
		
		$('#searchProject').click(function(){
			if(status){
				if(confirm('Do you want to leave the page. Do you want to leave page')){
					status=false;
					$('#content').remove();
					App.loadSearchProject();
				}
			}else{
				$('#content').remove();
				App.loadSearchProject();
			}
			
			
		});
		
		
		
		if(roleNames.contains('APPROVER')){
			$('#main').append('<li><a id="tsa">Time Sheet Approval</a></li>');
			
			
			$('#projectOptions').css( 'width', '17.5%').css('margin-left','-2%');
			$('#main > li').css('width','auto');
			
			$('#tsa').click(function(){
				if(status){
					if(confirm('Do you want to leave the page. Do you want to leave page')){
						status=false;
						$('#content').remove();
						App.loadApproverTimeSheetSearch();
						App.loadDefaultApproverPage();
					}
				}else{
					$('#content').remove();
					App.loadApproverTimeSheetSearch();
					App.loadDefaultApproverPage();
				}
				
			});
			
			if(roleNames.contains('MEMBER')){
				$('#main').append('<li><a id="tsf">Time Sheet Filling</a>');
				
				$('#projectOptions').css( 'width', '17%').css('margin-left','-1.8%');
				$('#main > li').css('width','auto');
				$('#main > li > a').css('padding','0 1.1em');
				
				$('#tsf').click(function(){
					if(status){
						if(confirm('Do you want to leave the page. Do you want to leave page')){
							status=false;
							$('#content').remove();
							App.loadTimeSheetFilling();
							App.loadDefaultTimeSheetPage();
						}
					}else{
						$('#content').remove();
						App.loadTimeSheetFilling();
						App.loadDefaultTimeSheetPage();
					}
					
				});
			}
		}
		
		else if(roleNames.contains('MEMBER')){
			$('#main').append('<li><a id="tsf">Time Sheet Filling</a>');
			
			$('#projectOptions').css( 'width', '17.5%').css('margin-left','-2%');
			$('#main > li').css('width','auto');
			
			$('#tsf').click(function(){
				if(status){
					if(confirm('Do you want to leave the page. Do you want to leave page')){
						status=false;
						$('#content').remove();
						App.loadTimeSheetFilling();
						App.loadDefaultTimeSheetPage();
					}
				}else{
					$('#content').remove();
					App.loadTimeSheetFilling();
					App.loadDefaultTimeSheetPage();
				}
				
			});
		}
			
	}
	
	
	
	else if(roleNames.contains('APPROVER')){
		$('#main').append('<li><a id="tsa">Time Sheet Approval</a></li>');
		
		//click events for approver
		$('#tsa').click(function(){
			if(status){
				if(confirm('Do you want to leave the page. Do you want to leave page')){
					status=false;
					$('#content').remove();
					App.loadApproverTimeSheetSearch();
					App.loadDefaultApproverPage();
				}
			}else{
				$('#content').remove();
				App.loadApproverTimeSheetSearch();
				App.loadDefaultApproverPage();
			}
			
		});
		
		if(roleNames.contains('MEMBER')){
			$('#main').append('<li><a id="tsf">Time Sheet Filling</a>');
			
			$('#tsf').click(function(){
				if(status){
					if(confirm('Do you want to leave the page. Do you want to leave page')){
						status=false;
						$('#content').remove();
						App.loadTimeSheetFilling();
						App.loadDefaultTimeSheetPage();
					}
				}else{
					$('#content').remove();
					App.loadTimeSheetFilling();
					App.loadDefaultTimeSheetPage();
				}
				
			});
			
		}
		else{
			$('#main > li').css('width','24%');
		}
	}else if(roleNames.contains('MEMBER')){
		
		
		$('#main').append('<li><a id="tsf">Time Sheet Filling</a>');
		$('#main > li').css('width','24%');
		
		
		//click events for member role
		
		$('#tsf').click(function(){
			if(status){
				if(confirm('Do you want to leave the page. Do you want to leave page')){
					status=false;
					$('#content').remove();
					App.loadTimeSheetFilling();
					App.loadDefaultTimeSheetPage();
				}
			}else{
				$('#content').remove();
				App.loadTimeSheetFilling();
				App.loadDefaultTimeSheetPage();
			}
			
		});
	}
	else{
		$('#main > li').css('width','30%');
	}
	
	if(!(roleNames.contains('ADMIN'))){
	$('#pm').click(function(){
		if(status){
			if(confirm('Do you want to leave the page. Do you want to leave page')){
				status=false;
				$('#content').remove();
				App.loadSearchProject();
			}
		}else{
			$('#content').remove();
			App.loadSearchProject();
		}
		
	});
	}
	
	$('#prm').click(function(){
		if(status){
			if(confirm('Do you want to leave the page. Do you want to leave page')){
				status=false;
				$('#content').remove();
				App.loadSearchProjectRelease();
			}
		}else{
			$('#content').remove();
			App.loadSearchProjectRelease();
		}
		
		
	});
	
	$('#su').click(function(){
		if(status){
			if(confirm('Do you want to leave the page. Do you want to leave page')){
				status=false;
				$('#content').remove();
				App.loadSearchUser();
			}
		}else{
			$('#content').remove();
			App.loadSearchUser();
		}
		
	});
	
}