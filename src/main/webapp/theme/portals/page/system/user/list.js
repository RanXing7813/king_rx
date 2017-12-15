

var mp = {};//给子页面用

if(map.page>1){
}else{
map.page=1;
}

$(document).ready(function(){
	go(map.page);
});


$("#searchButtons").click(function () { 
	map.filter={
		    logic : "and",
		    filters : [
			{
				field : "loginName",
				value : $("#loginName").val().trim()
			},{
				field : "userName",
				value : $("#userName").val().trim()
			}
			]};
			map.page=1;
			go(map.page);
});

//list数据 
function go(pages){
	map.pageSize = $("#selectlimitnum").val();
	
	$.ajax({
	      url : linkgoS ,
	      dataType: "json", 
	      data:{
	    	  models : JSON.stringify(map),
	    	  page : pages,
	    	  pageSize : $("#selectlimitnum").val(),
	      },
	      type:"POST",
	      success: function(result) { 
	    	  if(result==null)   return;
		    	  var list = '';
		    	  $("#grid").html(list);
	    	  	  var obj = result.queryObj;
	    	  	  if(obj==undefined){
	    	  		result.total = 0;
	    	  	  }else{
	    	  		 $.each(obj,function(n,value){
	      	 			 list += 
	      		 		   '<tr >'+
	      	  	 			 '<td class="fn-text-center"  ><span style="vertical-align: middle">'+(n+1)+'&nbsp;<input type="checkbox" class="checkbox5"    name="checkbox" value="'+value.id+'"   /></span></td>'+//序号
	      	  	 			 '<td class="fn-text-center" title="'+Null2String(value.loginName)+'"><a href="#" onclick="editS(\''+value.id+'\')" class="grid_a_blue"  >'+value.loginName+'</a></td>'+
	      	  	 			 '<td class="fn-text-center" title="'+Null2String(value.userName)+'">'+value.userName+'</td>'+
	      	  	 			 '<td class="fn-text-center" title="'+Null2String(value.email)+'">'+Null2String(value.email)+'</td>'+
	      	  	 			 '<td class="fn-text-center">'+
	      	  	 			    '<a class="grid_a_button" onclick="getDeptForUser(\''+value.id+'\')" href="#" >部门</a>'+
	      	  	 		    	'<a class="grid_a_button" onclick="userAuth(\''+value.id+'\')"  href="#"  >角色</a>'+
	      	  	 			    '<a href="#" onclick="delS(\''+value.id+'\')" class="grid_a_button">删除</a></td>'+
	      	  			   '</tr>'
	    	   	 		  })
	    	   	 		  $("#grid").html(list);
	    	  	  }
	    	  	 
	    	  	 paging_map(result, map, true);//分页信息
	    	  	 returnSearchData(map);//返回查询条件到查询框中
	      },
	      error: function(result) {
	      }
	    });
}

//查询条件返回
function returnSearchData(map){
	if(flag_S=='Y' &&  map.filter != undefined && map.filter.filters != undefined){
		var filtersLength = map.filter.filters.length;
		var filterObj = map.filter.filters;
		for( var n=0; n<filtersLength; n++ ){
			
			switch(filterObj[n]["field"]){
			case "deptId" :
				$("#deptId")
					.val(filterObj[n]["value"]);
				break;
				
			case "deptName" :
				$("#deptName")
					.val(filterObj[n]["value"]);
				break;
			}
		}
	}
	flag_S='N';
}

//分配角色
//指派角色 
$("#authRole").click(function(){ 
	  if($("input[name='checkbox']:checked").length>0){
		 $.dialog({
			lock: true,
			max: false,
		    min: false,
			id : "authRoles",
			title : "用户角色分配",
			width : 400,
			height : 500,
			content : "url:role-user-auth"
			//content : "url:role-list"
			
		});
	 }else{
		  $.MsgBox.Alert("温馨提示", "请先选中用户：您即将重置用户关联的角色信息 并且 重新分配 ！");
	 } 
	 
});

//分配部门
$("#deptRole").click(function(){ 
	  if($("input[name='checkbox']:checked").length>0){
		  
		    var userIds = ""
          	$("#grid input[name='checkbox']:checked").each(function() {
          		userIds += $(this).val() + ",";
          	});
          	if (userIds.length > 0) {
          		userIds = userIds.substring(0, userIds.length - 1);
          	}
          	
          	mp.id = userIds;	//
          	mp.boxType = 2 ;//多选
		  
		        $.dialog({
				lock: true,
				max: false,
			    min: false,
			    auths : function(){},
				id : "deptRole",
				title : "上级部门选择",
				width : (winWidth/2)>=800?(winWidth/2):800,
				height : 600,
				content : "url:"+linktodept_tree+"?deptType=1",
				 button: [
				        {
				            name: '确定',
				            callback: function(){
				            	var mp = this.auths();
//				            	console.log(mp.deptId);
//				            	mp.deptId = v ;
//				            	mp.name = n ;
//				            	mp.isPublic = 'Y';
				            	mp.id = userIds;
				            	$.ajax({
				        			url : linkpowerDeptTreeList,
				        			async : false,
				        			dataType : "json",
				        			data : {
				        				models : JSON.stringify(mp)
				        			},
				        			type : "POST",
				        			success : function(result) {
				        				 flag='Y';
				        				  $("#"+menusId).click();
				        				  return false;
				        			}
				        		});
				            	
				            	
				               
				            },
				            focus: true
				        },
				        {
				            name: '关闭'
				        }
				    ]
			}).show();
	 }else{
		  $.MsgBox.Alert("温馨提示", "请先选中用户：您即将重置用户关联的部门信息 并且 重新分配 ！");
	 } 
	 
});

//发布
function publishS (id){
	 $.dialog({
			lock: true,
			max: false,
		    min: false,
			id : "deptTree1",
			title : "上级部门选择",
			width : winWidth/2,
			height : winHeight/2,
			content : "url:"+linktodept_tree
			//content : "url:role-list"
			
		}).show(); 
}	
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

function getDeptForUser(id,name){
	$.dialog({
		lock: true,
		id : "deptTree2",
		title : "当前部门拥有用户",
		max: false,
	    min: false,
	    width : winWidth/2,
		height : winHeight/2,
		content : "url:"+linktodeptuser_tree+"?models="+id
	      //fitColumns:true,适应父容器的大小  
	});
}
