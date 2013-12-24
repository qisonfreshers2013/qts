function SearchResultsForMember(data){
	Loader.loadHTML('.resultsContainer', 'SearchResultsForMember.html', true, function(){
		this.handleShow(data);
	}.ctx(this));
}
SearchResultsForMember.prototype.handleShow = function(data){	
	this.appendValues(data);	
	
}

SearchResultsForMember.prototype.appendValues = function(data){
	for(var i = 0;i<data.records.length;i++){		
		$("#resultsTable tbody").append(
		"<tr>"+			        
				"<td id ="+data.records[i].photoFileUrl+"><img src='defaultImage.png' alt = 'default image' class='defaultImage'/></td>"+
				"<td><p style = 'font-size:12px'>"+data.records[i].email+"</p></td>"+
				"<td><p style = 'font-size:12px'>"+data.records[i].employeeId+"</p></td>"+
				"<td><p style = 'font-size:12px'>"+data.records[i].designation+"</p></td>"+			     
				"<td><p style = 'font-size:12px'>"+data.records[i].projects+"</p></td>"+				
		"</tr>"); 
	}  	


}
