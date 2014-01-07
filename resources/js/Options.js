function Options() {
	Loader.loadHTML('.container', 'Options.html', true, function(){
		this.handleShow();
	}.ctx(this));
}

Options.prototype.handleShow = function() {

	if(roleNames.contains('ADMIN')){
		$('.extraOptions').append('<div class="UserManagement"><img class="umImg" src="resources/img/um.png"><h4 class="umText">User Management</h4></div>');
		$('.UserManagement').css( {"background-color": "#4638A7",
			"cursor": "pointer",
			"height": "162px",
			"margin": "5% 1.5% 1.5%",
			"position": "relative",
			"width": "30%"
		});
		$('.umImg').css({
			"position": "relative", "padding-top": "15%", "left": "37%"
		});
		$('.umText').css({
			"color": "white", "position": "relative", "padding-left": "4%", "padding-top": "6%"
		});
		$('.UserManagement').click(function(){
			App.loadMenu(function(){
				App.loadAddUser();
			}); 
			
		});
		if(roleNames.contains('APPROVER')){
			$('.extraOptions').append('<div class="TimeSheetApproval"><img class="tsaImg" src="resources/img/tsa.png"><h4 class="tsaText">Time Sheet Approval</h4></div>');

			$('.TimeSheetApproval').css( {"background-color": "#D24625",
				"cursor": "pointer",
				"height": "162px",
				"left":"33%",
				"margin": "-18.7% 2% 1.5%",
				"position": "relative",
				"width": "30%"
			});
			$('.tsaImg').css({
				"position": "relative", "padding-top": "15%", "left": "36%"
			});
			$('.tsaText').css({
				"color": "white", "position": "relative", "padding-left": "4%", "padding-top": "5%"
			});

			$('.TimeSheetApproval').click(function(){App.loadMenu(function(){
				App.loadApproverTimeSheetSearch();
				App.loadDefaultApproverPage();
			});
			
			});
		} 
		if(roleNames.contains('MEMBER')){
			$('.extraOptions').append('<div class="TimeSheetFilling"><img class="tsfImg" src="resources/img/tsf.png"><h4 class="tsfText">Time Sheet Filling</h4></div>');

			$('.TimeSheetFilling').css( {"background-color": "#2871F2",
				"cursor": "pointer",
				"height": "162px",
				"left": "67%",
				"margin": "-18.7% 2% 1.5%",
				"position": "relative",
				"width": "30%"
			});
			$('.tsfImg').css({
				"position": "relative", "padding-top": "19%", "left": "40%"
			});
			$('.tsfText').css({
				"color": "white", "position": "relative", "padding-left": "4%", "padding-top": "10%"
			});

			$('.TimeSheetFilling').click(function(){App.loadMenu(function(){
				App.loadTimeSheetFilling();
				App.loadDefaultTimeSheetPage();
			});


			});
		}
	}
	else if(roleNames.contains('MEMBER')){
		$('.extraOptions').append('<div class="TimeSheetFilling"><img class="tsfImg" src="resources/img/tsf.png"><h4 class="tsfText">Time Sheet Filling</h4></div>');

		$('.TimeSheetFilling').css( {"background-color": "#2871F2",
			"cursor": "pointer",
			"height": "162px",
			"margin": "5% 1.5% 1.5%",
			"position": "relative",
			"width": "30%"
		});
		$('.tsfImg').css({
			"position": "relative", "padding-top": "19%", "left": "40%"
		});
		$('.tsfText').css({
			"color": "white", "position": "relative", "padding-left": "4%", "padding-top": "10%"
		});

		$('.TimeSheetFilling').click(function(){App.loadMenu(function(){
			App.loadTimeSheetFilling();
			App.loadDefaultTimeSheetPage();
		});


		});
		if(roleNames.contains('APPROVER')){
			$('.extraOptions').append('<div class="TimeSheetApproval"><img class="tsaImg" src="resources/img/tsa.png"><h4 class="tsaText">Time Sheet Approval</h4></div>');

			$('.TimeSheetApproval').css( {"background-color": "#D24625",
				"cursor": "pointer",
				"height": "162px",
				"left":"33%",
				"margin": "-18.7% 2% 1.5%",
				"position": "relative",
				"width": "30%"
			});
			$('.tsaImg').css({
				"position": "relative", "padding-top": "15%", "left": "36%"
			});
			$('.tsaText').css({
				"color": "white", "position": "relative", "padding-left": "4%", "padding-top": "5%"
			});

			$('.TimeSheetApproval').click(function(){ App.loadMenu(function(){
				App.loadApproverTimeSheetSearch();
				App.loadDefaultApproverPage();
			}); 
			});
		}
	}
	else if(roleNames.contains('APPROVER')){
		$('.extraOptions').append('<div class="TimeSheetApproval"><img class="tsaImg" src="resources/img/tsa.png"><h4 class="tsaText">Time Sheet Approval</h4></div>');

		$('.TimeSheetApproval').css( {"background-color": "#D24625",
			"cursor": "pointer",
			"height": "162px",
			"margin": "5% 1.5% 1.5%",
			"position": "relative",
			"width": "30%"
		});
		$('.tsaImg').css({
			"position": "relative", "padding-top": "15%", "left": "36%"
		});
		$('.tsaText').css({
			"color": "white", "position": "relative", "padding-left": "4%", "padding-top": "5%"
		});


		$('.TimeSheetApproval').click(function(){ App.loadMenu(function(){
			 App.loadApproverTimeSheetSearch();
				App.loadDefaultApproverPage();
		});
		});
	}

	$('.ProjectReleaseManagement').click(function(){
		App.loadMenu(function(){
			App.loadSearchProjectRelease();
		});  
		
	}.ctx(this));

	$('.ProjectManagement').click(function(){
		App.loadMenu(function(){
			if(roleNames.contains('ADMIN')){
				App.loadAllocateUsersToProject();
			}
			else{
				App.loadSearchProject();
			}
		});  
		
	}.ctx(this));
	$('.SearchUser').click(function(){
		App.loadMenu(function(){
			App.loadSearchUser();
		});  
		
	}.ctx(this));


}

