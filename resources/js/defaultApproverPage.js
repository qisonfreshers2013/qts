/**
 * Ajay
 */
function DefaultApproverPage(){
	Loader.loadHTML('#container', 'defaultApproverpage.html', false, function() {
		this.loadApproverSearch();
		this.handleShow();
		
	}.ctx(this))
}
DefaultApproverPage.prototype.handleShow=function(){
    

}
DefaultApproverPage.prototype.loadApproverSearch=function(){
	new ApproverSearch();
}


/*DefaultApproverPage.prototype.approveTimeEntry=function(){
	RequestManager.approve({"payload":{"id":$("#approveTimeEntry").val()}},function(){
		if(success){
			alert("approved");
		}
		else{
			alert(data.message);
		}	
	});
	
	
}
DefaultApproverPage.prototype.rejectTimeEntry=function(){
	RequestManager.reject({"payload":{"id":$("#rejectTimeEntry").val()}},function(){
		if(success){
			alert("rejected");
		}
		else{
			alert(data.message);
		}	
	});
}*/

var DefaultApproverPage=new DefaultApproverPage();