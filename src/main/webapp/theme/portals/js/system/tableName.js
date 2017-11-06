var map={};
map.page=1;
var api = frameElement.api, W = api.opener;
$(document).ready(function(){
		api.button({
			 name: '确定',
		     callback: onClick,
		     focus: true
		},
		{
		    name: '取消',
		});
	   go(map.page);
		  //查询
	  $("#searchButtons").click( 
				function () { 
							map.filter={
						    filters : [
						    	{
						    		field:"a.table_name",
						    		value:$("#table_name").val()
						    	},{
						    		field:"a.table_cname",
						    		value:$("#table_cname").val()
						    	}
							]};
							
							map.page=1;
							go(map.page);
		});
     });  
	 var total; // zongshu
	function go(pages){
	//	$("#grid").html("<tr><td colspan='7' >加载中 ... </td></tr>");
		map.pageSize=$("#selectlimitnum").val();
		map.page=pages;
		   $.ajax({
		          url : "table.json",
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
		          },
		          error: function(result) {
		          }
		        });
	}
	function onClick(){
		$.ajax({
			url:"tableByid.json",
			type:"POST",
			async: false,
			data:{
				id:$('input[type="radio"][name="radio"]:checked').val()
			},
		success:function(result){
			W.$("#tableName").val(result.dto.tableName);
			W.$("#tableCname").val(result.dto.tableCname);
			W.$("#tableid").val(result.dto.id);
		}
		})
		return true;
	}