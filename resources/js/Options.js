function Options(roleIds) {
	Loader.loadHTML('.container', 'Options.html', true, function(){
		this.handleShow(roleIds);
	}.ctx(this));
}
Options.prototype.handleShow = function(roleIds) {

	if(roleIds.contains(1)){
		$('.container').append('<div class="UserManagement"><img class="umImg" src="resources/img/um.png"><h4 class="umText">User Management</h4></div>');
		if(roleIds.contains(2)){
			$('.container').append('<div class="TimeSheetApproval"><img class="tsaImg" src="resources/img/tsa.png"><h4 class="tsaText">Time Sheet Approval</h4></div>');
		}		
	}
	else if(roleIds.contains(3)){
		$('.container').append('<div class="TimeSheetFilling"><img class="tsfImage" src="resources/img/tsf.png"><h4 class="tsfText">Time Sheet Filling</h4></div>');		
		if(roleIds.contains(2)){
			$('.container').append('<div class="TimeSheetApproval"><img class="tsaImg" src="resources/img/tsa.png"><h4 class="tsaText">Time Sheet Approval</h4></div>');
		}
	}
	else{
		$('.container').append('<div class="Illumination" style="background-color: #FFFFFF;height: 162px;margin: 5% 1.5% 1.5%;position: relative;width: 30%;"></div>');
		$('.container').append('<div class="TimeSheetApproval"><img class="tsaImg" src="resources/img/tsa.png"><h4 class="tsaText">Time Sheet Approval</h4></div>');
	}
	
	$('.ProjectReleaseManagement').click(function(){	
		App.loadSearchProjectRelease(roles);
		if(roleIds.contains(1)){
			App.loadAddProjectRelease();
		}
	}.ctx(this));
	
	$('.ProjectManagement').click(function(){
		App.loadMenu(roleIds);
		if(roleIds.contains(1)){
		App.loadAllocateUsersToProject();
		}
		else{
			App.loadSearchProject();
		}
	}.ctx(this));


}

