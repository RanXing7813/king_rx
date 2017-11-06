if(map.page>1){
}else{
	map.page=1;
}

$(function(){
	go(map.page);
})
//查询
	$("#searchButtons").click( 
		function () { 
			map.filter={
				    logic : "and",
				    filters : [
//				    	{
//				    		field:"DEPT_FULLNAME" ,
//				    		value:$("#DEPT_FULLNAME").val()
//				    	},
				    	{
				    		field:"MH_MODELNAME" ,
				    		value:$("#MH_MODELNAME").val()
				    	},
				    	{
				    		field:"date_from" ,
				    		value:$("#date_from").val()
				    	},
				    	{
				    		field:"date_to" ,
				    		value:$("#date_to").val()
				    	}
					]};
			go(1);
//			$("#lastInput").val(1);
	});
// list
var total;
function go(pages){
	map.pageSize=$("#selectlimitnum").val();//每页多少条
	map.page=pages;
		$.ajax({
          url : "getModelImptDataList.json",
          dataType: "json", 
          data:{
        	  models : JSON.stringify(map)
          },
          type:"POST",
          success: function(result) {
        	  var list = '';
        	  $("#grid").html(list);
        	  var obj = result.getList;
  	 		  $.each(obj,function(n,value){ //dels(\''+value[0]+'\')  javascript:looks(\''+value[0]+'\')  javascript:imputS(\''+value[0]+'\')
  	 				list += '<tr>'+
/*		  	 			 '<td class="fn-text-center" title="'+value[2]+'">'+((value[2]==null)?"":value[2])+'</td>'+
*/		  	 			 '<td class="fn-text-center" title="'+value[1]+'" ><a href="javascript:looks(\''+value[0]+'\')" >'+value[1]+'</a></td>'+
		  	 			 '<td class="fn-text-center" title="'+value[3]+'">'+((value[3]==null)?"":value[3])+'</td>'+
		  	 			 '<td class="fn-text-center" title="'+value[4]+'">'+((value[4]==null)?"":value[4])+'</td>'+
		  	 			 '<td class="fn-text-center" title="'+value[7]+'">'+((value[7]==null)?"":value[7])+'</td>'+
		  	 			 '<td class="fn-text-center" name="status_">'+((value[6]==null)?"":value[6])+'</td>'+
		  	 			 '<td class="fn-text-center"  id="'+value[0]+'"> '+
		  	 			 		'<span name="sa'+value[6]+'"><a   href="javascript:void(0)"  onclick="dels(this)"    >删除</a></span>&nbsp;'+
		  	 			 		'<span name="sc'+value[6]+'"><a   href="javascript:void(0)"  onclick="looks(this)"   >校验</a></span>&nbsp;'+
		  	 			 		'<span name="sb'+value[6]+'"><a   href="javascript:void(0)"  onclick="imputS(this)"  >导入</a></span></td>'+
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
			
			//请求是否回填查询数据
			if( flag=='Y' ){
				var filtersLength = map.filter.filters.length;
				for( var n=0; n<filtersLength; n++ ){
//					if(map.filter.filters[n]["field"]=="DEPT_FULLNAME"){
//						$("#DEPT_FULLNAME").val(map.filter.filters[n]["value"]);
//					}else 
						if(map.filter.filters[n]["field"]=="MH_MODELNAME"){
						$("#MH_MODELNAME").val(map.filter.filters[n]["value"]);
					}
				}
			}
			flag='N'; 
			pageButten();
			fmtTime();dispalySys();
          },
          error: function(result) {
				$("#grid").html("<tr><td colspan='7' >数据异常 ... </td></tr>");
	          }
	        });
}

//delete
function dels(obj) {
	 var id = obj.parentNode.parentNode.id;
	 var url = "removeModelImptData.json?id="+id;   
	 $.MsgBox.Confirm("温馨提示", "此操作将删除当前记录！你确定吗？", testconfirm3 ,url);	  // Confirm: function (title, msg, callback,url) {
}

// looks 
function looks(obj) {
	var id = obj.parentNode.parentNode.id;
	var time=Date.parse(new Date());
	var url = "modelImptData?id="+id+"&t="+time; ; 
	utilLooks("tablelist",url)
}

//imputS
function imputS(obj){
	var id = obj.parentNode.parentNode.id;
	//alert(id);
	var time=Date.parse(new Date());
	var url = "imtModl?id="+id+"&t="+time; ; 
	$.MsgBox.Confirm("温馨提示", "导入当前文件数据！你确定吗？", testconfirm2 ,url);	
}


//looks 
function utilLooks(divId,loadUrl) {  //  var url = "yw_looksChange?id="+id+"&listType=A2" ;
	 	load1(divId,loadUrl);
	 }

//  加载方法
function load1(divId,loadUrl){
	    $.dialog.tips('数据加载中...',150,'loading.gif');
		$("#"+divId).load(loadUrl,function(result){
			var message = $("#message").val() ; 
			 $.dialog.tips('',1,false,null);
			 if( typeof(message)!='undefined'&&  message !='' && message !='SUCCESS'  ){
				 //$("#back").click();
				
				 $.MsgBox.Alert("温馨提示", message+"!!!");
			 }
		});
}

 

//数据处理
function fmtTime(){
	$("td[name='status_']").each(function(){
			$(this).text($(this).text().replace(1, "通过"));
			$(this).text($(this).text().replace(0, "未校验"));
			$(this).text($(this).text().replace(2, "未通过"));
			$(this).text($(this).text().replace(3, "已导入"));
	});
}
//数据处理
function dispalySys(){
//	
	$("span[name^='sb'][name!='sb1'] a").css('background','#c1c1c1');
	$("span[name^='sb'][name!='sb1'] a").removeAttr("onclick");
	$("span[name^='sb'][name!='sb1'] a").removeAttr("href");
	//$("span[name^='sb'][name!='sb1'] a").attr("onclick","javascript:imputS(this);");
	
	$("span[name='sa3'] a").css('background','#c1c1c1');
	$("span[name='sa3'] a").removeAttr("onclick");
	$("span[name='sa3'] a").removeAttr("href");
	
	$("span[name^='sc'][name!='sc0'] a").css('background','#c1c1c1');
	$("span[name^='sc'][name!='sc0'] a").removeAttr("onclick");
	$("span[name^='sc'][name!='sc0'] a").removeAttr("href");
	
	
}

//弹出部门列表
$("#MH_ZYDEPTNAME").click(function(){
	$.dialog({
		lock: true,
		id : "deptList",
		title : "部门列表",
		width : (winWidth-100)>800?winWidth-100: minWidth,
				height : (winHeight-100)>800?winHeight-100:minHeight,
		content : "url:deptList",
	});
});

