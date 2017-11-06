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
//				    		field:"MH_ZYID" ,
//				    		value:$("#MH_ZYID").val()
//				    	},
				    	{
				    		field:"MH_ZYNAME" ,
				    		value:$("#MH_ZYNAME").val()
				    	},
				    	{
				    		field:"MH_ZYDEPTNAME" ,
				    		value:$("#MH_ZYDEPTNAME").val()
				    	},
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
	map.page=pages;
	map.pageSize=$("#selectlimitnum").val();//每页多少条

		$.ajax({
          url : "getModelSystemList.json",
          dataType: "json", 
          data:{
        	  models : JSON.stringify(map)
          },
          type:"POST",
          success: function(result) {
        	  var list = '';
        	  $("#grid").html(list);
        	  var obj = result.list;
  	 		  $.each(obj,function(n,value){ 
  	 				list += '<tr>'+
//		  	 			 '<td class="fn-text-center" title="'+value[1]+'">'+((value[1]==null)?"":value[1])+'</td>'+
  	 				'<td class="fn-text-center" title="'+value[3]+'">'+((value[3]==null)?"":value[3])+'</td>'+
		  	 			 '<td class="fn-text-center" title="'+value[2]+'" ><a href="javascript:edit(\''+value[0]+'\')" >'+value[2]+'</a></td>'+
		  	 			 '<td class="fn-text-center" title="'+value[4]+'">'+((value[4]==null)?"":value[4])+'</td>'+
		  	 			 '<td class="fn-text-center" title="'+value[5]+'">'+value[5]+'</td>'+
		  	 			 '<td class="fn-text-center" name="public_">'+((value[14]==null)?"未发布":value[14])+'</td>'+
		  	 			 '<td class="fn-text-center"  ><a   href="javascript:dels(\''+value[0]+'\')" >删除</a>&nbsp;'+
		  	 			 '<a   href="javascript:dowload(\''+value[0]+'\')" >下载</a>&nbsp;'+
		  	 			 '<a   href="javascript:publishS(\''+value[0]+'\')" >发布</a>&nbsp;'+
		  	 			 '<a   href="javascript:revocationS(\''+value[0]+'\')" >撤销</a></td>'+
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
					if(map.filter.filters[n]["field"]=="MH_ZYID"){
						$("#MH_ZYID").val(map.filter.filters[n]["value"]);
					}else if(map.filter.filters[n]["field"]=="MH_ZYNAME"){
						$("#MH_ZYNAME").val(map.filter.filters[n]["value"]);
					}else if(map.filter.filters[n]["field"]=="MH_ZYDEPTNAME"){
						$("#MH_ZYDEPTNAME").val(map.filter.filters[n]["value"]);
					}else if(map.filter.filters[n]["field"]=="MH_MODELNAME"){
						$("#MH_MODELNAME").val(map.filter.filters[n]["value"]);
					}
				}
			}
			flag='N'; 
			pageButten();auditStatus();
          },
          error: function(result) {
				$("#grid").html("<tr><td colspan='7' >数据异常 ... </td></tr>");
	          }
	        });
}


//新建 按钮  
$("#new").click(function(){
	$("#tablelist").load("newModel");
});

// dowload 
function dowload(id) {
	var url = "dowload.json" ; 
	   $.post(url, {"id":id},
				function(data) {
  			     var message = data.message ; 
  			     
      			 	 if( typeof(message)!='undefined' &&  message !='' && message !='SUCCESS'  ){
      				 $.MsgBox.Alert("温馨提示", message);
					 }else{
						 var form = $("<form>"); //定义一个form表单
						 form.attr('style', 'display:none'); //在form表单中添加查询参数
						 form.attr('method', 'post');
						 form.attr('action', "files/download?file_id="+data.file_id);
						 $('body').append(form); //将表单放置在web中
						 form.submit();
						 
					 }
				}); 
}
//设置审核状体
function auditStatus(){
	$("td[name='public_']").each(function(){
		$(this).text();
		switch ($(this).text()) {
		case '1' :
			$(this).text("已发布");
		 	break ; 
		case '0' :
			$(this).text("未发布");
		 	break ; 
		default :
			$(this).text("未发布");
			break;
		}
	})
}

// edit 
function edit(id) {
	var time=Date.parse(new Date());
	var url = "editModel?id="+id+"&t="+time;
	utilLooks("tablelist",url)
}
//looks 
function utilLooks(divId,loadUrl) {  //  var url = "yw_looksChange?id="+id+"&listType=A2" ;
	 	load1(divId,loadUrl);
	 }

//  加载方法
function load1(divId,loadUrl){
		$("#"+divId).load(loadUrl,function(result){
			var message = $("#message").val() ; 
			 if( typeof(message)!='undefined'&&  message !='' && message !='SUCCESS'  ){
				 $("#back").click();
				 $.MsgBox.Alert("温馨提示", message+"!!!");
			 }
		});
}

// delete
function dels(id) {
	 var url = "removeModel.json?id="+id;   
	 $.MsgBox.Confirm("温馨提示", "此操作将删除当前记录！你确定吗？", testconfirm2 ,url);	  // Confirm: function (title, msg, callback,url) {
} 

//获取业务名称 List to  map
//var map_=new Object();  
//function getName(){
//	    //属性加个特殊字符，以区别方法名，统一加下划线_  
//	    map_.put=function(key,value){    map_[key]=value;}   
//	    map_.get=function(key){    return map_[key];}  
//	for(var i=0;i<ywlxid_.length;i++){
//		map_.put(ywlxid_[i][0],ywlxid_[i][1]);
//	 }
//}

//发布
function publishS (id){
	 $.dialog({
			lock: true,
			max: false,
		    min: false,
			id : "authRoless",
			title : "用户部门分配",
			width : 600,
			height : winHeight-200,
			content : "url:deptPublishList?id="+id
			//content : "url:role-list"
			
		}).show(); 
}
//撤销
function revocationS (id){
	 var xx = {} ;
	     xx.id = id;
	     xx.isPublic='N';
	  		
	 /// models : JSON.stringify(xx)
	 var url = "saveDeptPublishList.json?models="+JSON.stringify(xx);   
	 $.MsgBox.Confirm("温馨提示", "此操作将撤销发布！你确定吗？", testconfirm2 ,url);	  // Confirm: function (title, msg, callback,url) {
	
}
//弹出部门列表
$("#MH_ZYDEPTNAME").click(function(){
	$.dialog({
		lock: true,
		id : "deptList",
		title : "部门列表",
		width : (winWidth-100)>800?(winWidth/2): minWidth,
				height : (winHeight-100)>800?winHeight-100:minHeight,
		content : "url:deptList",
	});
});

