if(map.page>1){
}else{
	map.page=1;
}

$(document).ready(function(){
	   go(map.page);
		  //查询
	  $("#searchButtons").click( 
				function () { 
							map.filter={
						    filters : [{
						    	field:"cj_name",
						    	value:$("#cj_name").val()
						    },{
						    	field:"cj_code",
						    	value:$("#cj_id").val()
						    }
							]};
							map.page=1;
							go(map.page);
		});
	  $("#new").click(function(){
		  var url="cjLook?id=&t="+Date.parse(new Date());
			$("#tablelist").load(url);
	  })
     });  
	 var total; // zongshu
	function go(pages){
	//	$("#grid").html("<tr><td colspan='7' >加载中 ... </td></tr>");
		map.pageSize=$("#selectlimitnum").val();
		map.page=pages;
		   $.ajax({
		          url : "cjList.json",
		          dataType: "json", 
		          data:{
		        	  models : JSON.stringify(map)
		          },
		          type:"POST",
		          success: function(result) { if(result==null)   return;
			      	 	 	var myTemplate = Handlebars.compile($("#backloglistpage-template").html());
		      				$("#grid").html(myTemplate(result));
		      				$('tbody tr:even').addClass("alt-row"); 
		      				$(".discolor tr:odd").addClass("tr-odd-bg"); //先排除第一行,然后给奇数行添加样式
		      				$(".discolor tr:even").addClass("tr-even-bg"); //先排除第一行,然后给偶数行添加样式
		      				//加载分页信息
		      				var z = result.total%map.pageSize;
		      				total = result.total ;
		      				var pageNum;
		      				if(z==0){
		      						pageNum=result.total/map.pageSize;
		      				}else{
		      					pageNum=Math.floor(result.total/map.pageSize)+1;
		      				}
		      				$("#lastInput").val(pageNum);
		      				$("#pageSpan").html("共"+result.total+"条记录，共"+map.page+"/"+pageNum+"页，第"+map.page+"页");
		      				pageButten();
								
								 if( flag=='Y'){
			  						var filtersLength = map.filter.filters.length;
									for( var n=0; n<filtersLength; n++ ){
										if(map.filter.filters[0]["field"]=="c.userName"){
				  							$("#userName").val(map.filter.filters[0]["value"]);
				  						}
									}
			 					  }
		 					 flag='N';
		          },
		          error: function(result) {
		        	//  login_new();
		          }
		        });
	}
	
function look(obj){
	var url="cjLook?id="+obj+"&t="+Date.parse(new Date());
	$("#tablelist").load(url);
}
function deptCount(obj,count){
	if(count == "0"){
		$.dialog({
			title : "提醒",
			width : 250,
			height : 120,
			lock: true,
		    max:false,
		    min:false,
		    resize:false,
			content : "当前涉及部门数量为0",
			ok:function(){
				return true;
			}
		});
	}else{
		$.dialog({
			title : "涉及部门",
			width : 745,
			height : 500,
			lock: true,
		    max:false,
		    min:false,
		    resize:false,
			content : "url:CjDept?id="+obj
		});
	}
}	
function xxlCount(obj,count){
	if(count == "0"){
		$.dialog({
			title : "提醒",
			width : 250,
			height : 120,
			lock: true,
		    max:false,
		    min:false,
		    resize:false,
			content : "当前涉及部门数量为0",
			ok:function(){
				return true;
			}
		});
	}else{
		$.dialog({
			title : "涉及信息类",
			width : 600,
			height : 500,
			lock: true,
		    max:false,
		    min:false,
		    resize:false,
			content : "url:CjXxl?id="+obj
		});
	}
}