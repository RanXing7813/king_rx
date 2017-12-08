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
							]};
							seachProduct()
							map.page=1;
							go(map.page);
		});
     });  
	// looks 
	 var total; // zongshu
	//list
	function go(pages){
	//	$("#grid").html("<tr><td colspan='7' >加载中 ... </td></tr>");
		map.pageSize=$("#selectlimitnum").val();
		map.page=pages;
		//排序map sort 
		map.sort=[{
				 field : "c.userName",
				dir : "DESC"
			   }]; 
		
		
		   $.ajax({
		          url : "yw_systemUserList.json",
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
	
	
	var api = null;	
	//新建
	$("#new").click(function(){
		var url = "user-add";
		$("#tablelist").load(url);
	});
	//设置编号
	function numTR(){
		var i=1;
		 $("font[name='numTR']").each(function(){
			$(this).text(i);
			i++;
		}) 
	}
	
	//获取查询   
	function seachProduct(){
       	map.filter={
			    filters : [
				{
					field : "c.userName",
					value : $("#userName").val()
				},{
					field:"c.loginName",
					value:$("#loginName").val()
				}
				]};
    }
	
	//全选  
	 $("#ids").click(function(){ 
			  if($("#ids").attr("checked")){
			      $("input[name='ids']").attr("checked",true);
				}else{
				  $("input[name='ids']").attr("checked",false);
				}
	 });

	//弹出框 
	 function looks(id) {
		var url = "user-add?userId="+id
		$("#tablelist").load(url);
	}
	//指派角色 
	 $("#authRole").click(function(){ 
		  if($("input[name='ids']:checked").length>0){
			 $.dialog({
				lock: true,
				max: false,
			    min: false,
				id : "authRoles",
				title : "用户角色分配",
				width : 400,
				height : 600,
				content : "url:role-user-auth"
				//content : "url:role-list"
				
			});
		 }else{
			  $.MsgBox.Alert("温馨提示", "请先选中用户：您即将重置用户关联的角色信息 并且 重新分配 ！");
		 } 
		 
	 });
	//指派部门
	 $("#deptRole").click(function(){ 
		  if($("input[name='ids']:checked").length>0){
			// 获取窗口高度
			
		 	 $.dialog({
				lock: true,
				max: false,
			    min: false,
				id : "authRoless",
				title : "用户部门分配",
				width : 600,
				height : winHeight,
				content : "url:dept-list3"
				//content : "url:role-list"
				
			}).show(); 
		 }else{
			  $.MsgBox.Alert("温馨提示", "请先选中用户：您即将重置用户关联的部门信息 并且 重新分配 ！");
		 } 
		 
	 });
	 
	//查看用户角色
	function userAuth(id){
		//var url = "role-authRoles?userId=" + id;
		 $.dialog({
				lock: true,
				max: false,
			    min: false,
				id : "userAuth",
				title : "用户权限查看",
				width : 700,
				height : 600,
				content : "url:role-authRoles-details?userId="+id
				///content : "url:role-authRoles-details?userId="+id
			});
	}	
	
	//查看用户部门
	 function getDeptForUser(id){
		 $.dialog({
				lock: true,
				max: false,
			    min: false,
				title : " 用户部门信息查看",
			 	id : "DeptForUser",
			    content: 'url:dept-list4?id='+id,
				width : 300,
				height : 300,
			    cancelVal: '关闭',
			    cancel: true /*为true等价于function(){}*/
			});
	 }
	// delete
	 function dels(id) {
	    	 var url = "user-delete.json?id="+id;   
	    	 $.MsgBox.Confirm("温馨提示", "此操作将作废当前记录！你确定吗？", testconfirm2 ,url);	  // Confirm: function (title, msg, callback,url) {
	 } 
	 