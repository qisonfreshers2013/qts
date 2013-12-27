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

var DefaultApproverPage=new DefaultApproverPage();