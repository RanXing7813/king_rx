var map = {};
	map.page=1;
$(function(){
	go(map.page);
})
//查询
	$("#searchButtons").click( 
		function () { 
			map.filter={
				    logic : "and",
				    filters : [
				    	{
				    		field:"TABLE_NAME" ,
				    		value:$("#TABLE_NAME").val()
				    	},
				    	{
				    		field:"TABLE_CNAME" ,
				    		value:$("#TABLE_CNAME").val()
				    	}
					]};
			go(1);
			$("#lastInput").val(1);
	});
// list
var total;
function go(pages){
	map.pageSize = $("#selectlimitnum").val();
	map.page=pages;
		$.ajax({
          url : "getModelTableList.json",
          dataType: "json", 
          data:{
        	  models : JSON.stringify(map)
          },
          type:"POST",
          success: function(result) {
        	  var list = '';
        	  $("#grid").html(list);
        	  var obj = result.getList;
  	 		  $.each(obj,function(n,value){ 
 //Array(14) 0:"MH_ID"1:"MH_ZYID"2:"MH_ZYNAME"3:"MH_ZYDEPTID"4:"MH_XXTABLENAME"5:"CREATE_TIME"6:UPDATE_TIME 7:"CREATE_ID"8:"UPDATE_ID"9:"MH_MODELNAME"10:"MH_MODELADDRES"11:"MH_MODELSTATUS"12:UPLOAD_TIME 13:ISDELETE length:14
  	 				list += '<tr>'+
//		  	 			 '<td class="fn-text-center" title="'+value[0]+'">'+value[0]+'</td>'+
		  	 			 '<td class="fn-text-center" title="'+value[1]+'" >'+value[1]+'</td>'+
		  	 			 '<td class="fn-text-center" title="'+value[2]+'">'+value[2]+'</td>'+
		  	 			 '<td class="fn-text-center"><a href="#" onclick="checkThis(\''+value[1]+'\',\''+value[2]+'\')">选择</a></td>'+
		  			   '</tr>'
  	 			})
  	 		$("#grid").html(list);
  	 		//加载分页信息
			var z = result.total%map.pageSize;
			total = result.total;
			if(z==0){
				pageNum=result.total/map.pageSize;
			}else{
				pageNum=Math.floor(result.total/map.pageSize)+1;
			}
			$("#lastInput").val(pageNum);
			$("#pageSpan").html("共"+result.total+"条记录，共"+map.page+"/"+pageNum+"页，第"+map.page+"页");
			pageButten();
          },
          error: function(result) {
				$("#grid").html("<tr><td colspan='7' >数据异常 ... </td></tr>");
	          }
	        });
}
var api = frameElement.api, W = api.opener;	 //api 内容页中调用窗口实例对象的接口  W加载窗口组件页面的window对象
function checkThis(id,name){
	W.$("#MH_XXTABLECNAME").val(  name  );
	W.$("#MH_XXTABLENAME").val(  id  );
	W.$("#MH_XXTABLECNAME").focus();
	api.close();
	
}
