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
//				    	{
//				    		field:"MH_ZYDEPTNAME" ,
//				    		value:$("#MH_ZYDEPTNAME").val()
//				    	},
//				    	{
//				    		field:"MH_MODELNAME" ,
//				    		value:$("#MH_MODELNAME").val()
//				    	},
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
		$.ajax({
          url : "getModelPublishList.json",
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
  	 				'<td class="fn-text-center" title="'+value[3]+'">'+((value[3]==null)?"":value[3])+'</td>'+
	  	 			 '<td class="fn-text-center" title="'+value[2]+'" ><a href="javascript:looks(\''+value[0]+'\')" >'+value[2]+'</a></td>'+
	  	 			 '<td class="fn-text-center" title="'+value[4]+'">'+((value[4]==null)?"":value[4])+'</td>'+
	  	 			 '<td class="fn-text-center" title="'+value[5]+'">'+value[5]+'</td>'+
	  	 			 '<td class="fn-text-center"  >'+
	  	 			 '<a   href="javascript:dowload(\''+value[0]+'\')" >下载</a>&nbsp;'+
	  	 			 '</td>'+
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
//					if(map.filter.filters[n]["field"]=="MH_ZYID"){
//						$("#MH_ZYID").val(map.filter.filters[n]["value"]);
//					}else
						if(map.filter.filters[n]["field"]=="MH_ZYNAME"){
						$("#MH_ZYNAME").val(map.filter.filters[n]["value"]);
					}
//						else if(map.filter.filters[n]["field"]=="MH_ZYDEPTNAME"){
//						$("#MH_ZYDEPTNAME").val(map.filter.filters[n]["value"]);
//					}else if(map.filter.filters[n]["field"]=="MH_MODELNAME"){
//						$("#MH_MODELNAME").val(map.filter.filters[n]["value"]);
//					}
				}
			}
			flag='N'; 
			pageButten();
          },
          error: function(result) {
				$("#grid").html("<tr><td colspan='7' >数据异常 ... </td></tr>");
	          }
	        });
}


// looks 
function looks(id) {
	var time=Date.parse(new Date());
	var url = "looksModel?id="+id+"&t="+time;
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
