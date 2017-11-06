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
						    filters : [
						    	{
						    		field:"table_cname",
						    		value:$("#table_cname").val().trim(),
						    	},{
						    		field:"table_name",
						    		value:$("#table_name").val().trim()
						    	},{
						    		field:"gx_type",
						    		value:$("#gx_type").val().trim()
						    	},{
						    		field:"dept_name",
						    		value:$("#dept").val().trim()
						    	}
							]};
							
							map.page=1;
							go(map.page);
		});
	  $("#new").click(function(){
		  var url="tableLook?id=&t="+Date.parse(new Date());
			$("#tablelist").load(url);
	  })
     });  
	 var total; // zongshu
	function go(pages){
	//	$("#grid").html("<tr><td colspan='7' >加载中 ... </td></tr>");
		map.pageSize=$("#selectlimitnum").val();
		map.page=pages;
		   $.ajax({
		          url : "tableList.json",
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
	var url="tableLook?id="+obj+"&t="+Date.parse(new Date());
	$("#tablelist").load(url);
}
