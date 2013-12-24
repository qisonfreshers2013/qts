/**
 * Ajay
 */
function ApproverSearch(){
	Loader.loadHTML('.searchTimeEntries', 'approverSearch.html', true, function() {
		this.handleShow();
	}.ctx(this))
}

ApproverSearch.prototype.handleShow=function(){
	$(".container").show();
    $(document).ready(function() {
        $(".from").datepicker();
        $(".to").datepicker();
    });
}.ctx(this)
