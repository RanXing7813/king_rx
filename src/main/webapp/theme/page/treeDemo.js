
function treeDeptAll(){
	
		$.dialog({
			id : "ids",
			title : '数据元信息',
			lock : true,
			content : 'url:../tree/toDeptAllTreeView' 
		}).max();
}
function treeDeptFristNode(){
	
	$.dialog({
		id : "ids",
		title : '数据元信息',
		lock : true,
		content : 'url:../tree/toDeptFristNodeTreeView' 
	}).max();
}