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
				    		field:"DEPT_NAME" ,
				    		value:$("#DEPT_NAME").val()
				    	},
				    	{
				    		field:"DEPT_FULLNAME" ,
				    		value:$("#DEPT_FULLNAME").val()
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
	map.tableName = tableName;
		$.ajax({
          url : "getCodeList.json",
          dataType: "json", 
          data:{
        	  models : JSON.stringify(map)
          },
          type:"POST",
          success: function(result) {
        	  var list = '';
        	  $("#grid").html(list);
        	  var obj = result.getCodeList;
        	 // console.log(result);
  	 		  $.each(obj,function(n,value){ 
  	 				list += '<tr>'+
//		  	 			 '<td class="fn-text-center" title="'+value[0]+'">'+value[0]+'</td>'+
		  	 			 '<td class="fn-text-center" title="'+value[1]+'" ><a href="#" onclick="edit(\''+value[5]+'\')">'+value[1]+'</a></td>'+
		  	 			 '<td class="fn-text-center" title="'+value[0]+'">'+value[0]+'</td>'+
		  	 			 '<td class="fn-text-center" ">'+(value[6]==null?"":value[6])+'</td>'+
		  	 			 '<td class="fn-text-center"><a href="#" onclick="dels(\''+value[5]+'\')">删除</a></td>'+
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

$("#new").click(function(){
	var time=Date.parse(new Date());
	var url = "newCode";
	utilLooks("tablelist",url)
	
});

//edit 
function edit(id) {
	var time=Date.parse(new Date());
	var url = "eidtCode?id="+id+"&t="+time;
	utilLooks("tablelist",url)
}
//looks 
function utilLooks(divId,loadUrl) {  //  var url = "yw_looksChange?id="+id+"&listType=A2" ;
	 	load1(divId,loadUrl);
	 }

//加载方法
function load1(divId,loadUrl){
		$("#"+divId).load(loadUrl,function(result){
			var message = $("#message").val() ; 
			 if( typeof(message)!='undefined'&&  message !='' && message !='SUCCESS'  ){
				 $("#back").click();
				 $.MsgBox.Alert("温馨提示", message+"!!!");
			 }
		});
}

//delete
function dels(id) {
	 var url = "removeCode.json?id="+id;   
	// window.location.href="codeList?tableName="+tableName;
	 $.MsgBox.Confirm("温馨提示", "此操作将删除当前记录！你确定吗？", testconfirm4 ,url);	  // Confirm: function (title, msg, callback,url) {
} 