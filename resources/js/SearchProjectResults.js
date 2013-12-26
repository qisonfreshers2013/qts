function SearchProjectResults(data){
	Loader.loadHTML('#results', 'SearchProjectResults.html',false, function(){
		this.handleShow(data);
	}.ctx(this));
}

SearchProjectResults.prototype.handleShow=function(data){
	var body=$('#load');
	var image='resources/img/employee.png';
	var name='';
	body.empty();
	
//	data=data.sort(function(a, b){
//	    if (a.email.toLowerCase() == b.email.toLowerCase()) {
//	        return 0;
//	    } else if(a.email.toLowerCase() > b.email.toLowerCase()) {
//	        return 1;
//	    }
//	    return -1;
//	});
	
	$.each(data,function(key1,value1){
		name=value1.firstName;
		body.append('<div style="float:left;width:20%;margin-top:5%"><p style="align:center;font-size:16px">'+name+'</p><img  src='+image+'/></div>');
	});
}