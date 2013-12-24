function Options(roles) {
	Loader.loadHTML('.container', 'Options.html', true, function(){
		this.handleShow(roles);
	}.ctx(this));
}

Options.prototype.handleShow = function(roles) {

	if(roles.contains(1)){
		$('.container').append('<div class="UserManagement"><img class="umImg" src="resources/img/um.png"><h4 class="umText">User Management</h4></div>');
		$('.UserManagement').click(function(){
			App.loadMenu(roles); 
			App.loadAddUser();
		});
		if(roles.contains(2)){
			$('.container').append('<div class="TimeSheetApproval"><img class="tsaImg" src="resources/img/tsa.png"><h4 class="tsaText">Time Sheet Approval</h4></div>');
			$('.TimeSheetApproval').click(function(){App.loadMenu(roles);  App.loadApproverTimeSheetSearch();
			App.loadDefaultApproverPage();});
		}  
	}
	else if(roles.contains(3)){
		$('.container').append('<div class="TimeSheetFilling"><img class="tsfImage" src="resources/img/tsf.png"><h4 class="tsfText">Time Sheet Filling</h4></div>');
		$('.TimeSheetFilling').click(function(){App.loadMenu(roles); App.loadTimeSheetFilling();
		App.loadDefaultTimeSheetPage();
		});
		if(roles.contains(2)){
			$('.container').append('<div class="TimeSheetApproval"><img class="tsaImg" src="resources/img/tsa.png"><h4 class="tsaText">Time Sheet Approval</h4></div>');
			$('.TimeSheetApproval').click(function(){ App.loadMenu(roles); App.loadApproverTimeSheetSearch();
			App.loadDefaultApproverPage();});
		}
	}
	else{
		$('.container').append('<div class="Illumination" style="background-color: #FFFFFF;height: 162px;margin: 5% 1.5% 1.5%;position: relative;width: 30%;"></div>');
		$('.container').append('<div class="TimeSheetApproval"><img class="tsaImg" src="resources/img/tsa.png"><h4 class="tsaText">Time Sheet Approval</h4></div>');
		$('.TimeSheetApproval').click(function(){ App.loadMenu(roles); App.loadApproverTimeSheetSearch();
		App.loadDefaultApproverPage();});
	}

	$('.ProjectReleaseManagement').click(function(){
		App.loadMenu(roles);  
		App.loadSearchProjectRelease(roles);
		if(roles.contains(1)){
			App.loadAddProjectRelease();
		}
	}.ctx(this));

	$('.ProjectManagement').click(function(){
		App.loadMenu(roles);  
		if(roles.contains(1)){
			App.loadAllocateUsersToProject();
		}
		else{
			App.loadSearchProject();
		}
	}.ctx(this));
	$('.SearchUser').click(function(){
		App.loadMenu(roles);  
		App.loadSearchUser(roles);
	}.ctx(this));


}

