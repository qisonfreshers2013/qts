function Options(roles) {
	Loader.loadHTML('.container', 'Options.html', true, function(){
		this.handleShow(roles);
	}.ctx(this));
}

Options.prototype.handleShow = function(roles) {

	if(roles.contains(1)){
		$('.container').append('<div class="UserManagement"><img class="umImg" src="resources/img/um.png"><h4 class="umText">User Management</h4></div>');
		$('.UserManagement').css( {"background-color": "#4638A7",
	    "cursor": "pointer",
	    "height": "162px",
	    "margin": "5% 1.5% 1.5%",
	    "position": "relative",
	    "width": "30%"
	});
		$('.umImg').css({
			"position": "relative", "top": "25%", "left": "37%"
		});
		$('.umText').css({
	 "color": "white", "position": "relative", "left": "4%", "top": "40%"
		});
		$('.UserManagement').click(function(){
			App.loadMenu(roles); 
			App.loadAddUser();
		});
		if(roles.contains(2)){
			$('.container').append('<div class="TimeSheetApproval"><img class="tsaImg" src="resources/img/tsa.png"><h4 class="tsaText">Time Sheet Approval</h4></div>');
			
			$('.TimeSheetApproval').css( {"background-color": "#D24625",
		    "cursor": "pointer",
		    "height": "162px",
			"left":"33%",
		    "margin": "-18.7% 2% 1.5%",
		    "position": "relative",
		    "width": "30%"
		});
			$('.tsaImg').css({
				"position": "relative", "top": "30%", "left": "36%"
			});
			$('.tsaText').css({
				 "color": "white", "position": "relative", "left": "4%", "top": "39%"
					});
			
			$('.TimeSheetApproval').click(function(){App.loadMenu(roles);  App.loadApproverTimeSheetSearch();
			App.loadDefaultApproverPage();});
		}  
	}
	else if(roles.contains(3)){
		$('.container').append('<div class="TimeSheetFilling"><img class="tsfImage" src="resources/img/tsf.png"><h4 class="tsfText">Time Sheet Filling</h4></div>');
		
		$('.TimeSheetFilling').css( {"background-color": "#2871F2",
	    "cursor": "pointer",
	    "height": "162px",
	    "margin": "5% 1.5% 1.5%",
	    "position": "relative",
	    "width": "30%"
	});
		$('.tsfImg').css({
			"position": "relative", "top": "36%", "left": "40%"
		});
		$('.tsfText').css({
			 "color": "white", "position": "relative", "left": "4%", "top": "55%"
				});
		
		$('.TimeSheetFilling').click(function(){App.loadMenu(roles); App.loadTimeSheetFilling();
		App.loadDefaultTimeSheetPage();
		});
		if(roles.contains(2)){
			$('.container').append('<div class="TimeSheetApproval"><img class="tsaImg" src="resources/img/tsa.png"><h4 class="tsaText">Time Sheet Approval</h4></div>');
			
			$('.TimeSheetApproval').css( {"background-color": "#D24625",
		    "cursor": "pointer",
		    "height": "162px",
			"left":"33%",
		    "margin": "-18.7% 2% 1.5%",
		    "position": "relative",
		    "width": "30%"
		});
			$('.tsaImg').css({
				"position": "relative", "top": "30%", "left": "36%"
			});
			$('.tsaText').css({
				 "color": "white", "position": "relative", "left": "4%", "top": "39%"
					});
			
			$('.TimeSheetApproval').click(function(){ App.loadMenu(roles); App.loadApproverTimeSheetSearch();
			App.loadDefaultApproverPage();});
		}
	}
	else{
		$('.container').append('<div class="TimeSheetApproval"><img class="tsaImg" src="resources/img/tsa.png"><h4 class="tsaText">Time Sheet Approval</h4></div>');
		
		$('.TimeSheetApproval').css( {"background-color": "#D24625",
	    "cursor": "pointer",
	    "height": "162px",
	    "margin": "5% 1.5% 1.5%",
	    "position": "relative",
	    "width": "30%"
	});
		$('.tsaImg').css({
			"position": "relative", "top": "30%", "left": "36%"
		});
		$('.tsaText').css({
			 "color": "white", "position": "relative", "left": "4%", "top": "39%"
				});
		
		
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

