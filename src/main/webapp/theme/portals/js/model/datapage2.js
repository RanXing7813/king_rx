


$("#back").click(function(){
	 flag='Y';
	 $("#"+menusId).click();
});
$(function(){
	go(map1.page);
	//返回
})
// list
var total1;
function go(pages){
	map1.page=pages;
	map1.id=$("#dataid").val();
		$.ajax({
          url : "modelImptDataList.json",
          dataType: "json", 
          data:{
        	  models : JSON.stringify(map1)
          },
          type:"POST",
          success: function(result) {
        	  var list = '';
        	  $("#grid").html(list);
        	  var obj = result.list;
        	  if(obj==null) return;
  	 		  $.each(obj,function(n,value2){ 
  	 			list += '<tr>';
		  	 			 $.each(value2,function(n,value){ 
		   	 				list += '<td class="fn-text-center" title="'+value+'">'+value+'</td>';
		   	 			})
  	 			list += '</tr>';
  	 			})
  	 		$("#grid").html(list);
  	 		//加载分页信息
			var z = result.total%map1.pageSize;
			total1 = result.total;
			if(z==0){
				pageNum1=result.total/map1.pageSize;
			}else{
				pageNum1=Math.floor(result.total/map1.pageSize)+1;
			}
			$("#lastInput").val(pageNum1);
			$("#pageSpan").html("共"+result.total+"条记录，共"+map1.page+"/"+pageNum1+"页，第"+map1.page+"页");
			
			pageButten();
          },
          error: function(result) {
				$("#grid").html("<tr><td colspan='7' >数据异常 ... </td></tr>");
	          }
	        });
}
