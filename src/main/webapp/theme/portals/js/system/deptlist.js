

if(map.page>1){
}else{
map.page=1;
}

$(document).ready(function(){
	go(map.page);
});


$("#searchButtons").click(function () { 
	///console.log(map.filter.filters[0]["value"]);
	map.filter={
		    logic : "and",
		    filters : [
			{
				field : "dept_zzjgdm",
				value : $("#dept_zzjgdm").val().trim()
			},{
				field : "deptName",
				value : $("#deptName").val().trim()
			}
			]};
			map.page=1;
			go(map.page);
});

//list数据 
function go(pages){
	map.pageSize = $("#selectlimitnum").val();
	map.sort=[{
	 	field : "deptIndex",
		dir : "DESC"
		}]; 
	
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
	//			 var myTemplate = Handlebars.compile($("#grid-template").html());
	//			 $('#grid').html(myTemplate(result));
		    	  var list = '';
		    	  $("#grid").html(list);
	    	  	  var obj = result.queryObj;
	    	  	  if(obj==undefined){
	    	  		result.total = 0;
	    	  	  }else{
	    	  		 $.each(obj,function(n,value){
	      	 			 list += 
	      		 		   '<tr >'+
	      	  	 			 '<td class="fn-text-center"  >'+(n+1)+'<input type="hidden" name="checkbox" value="'+value.deptId+'"/></td>'+//序号
	      	  	 			 '<td class="fn-text-center" title="'+value.dept_fullname+'"><a href="#" onclick="editS(\''+value.deptId+'\')" class="grid_a_blue"  >'+value.dept_fullname+'</a></td>'+
	      	  	 			 '<td class="fn-text-center" title="'+value.deptName+'">'+value.deptName+value.deptName+value.deptName+'</td>'+
	      	  	 			 '<td class="fn-text-center" title="'+value.dept_zzjgdm+'">'+value.dept_zzjgdm+'</td>'+
	      	  	 			 '<td class="fn-text-center"><a href="#" onclick="delS(\''+value.deptId+'\')" class="grid_a_button">删除</a></td>'+
	      	  			   '</tr>'
	    	   	 		  })
	    	   	 		  $("#grid").html(list);
	    	  	  }
	    	  	 
	    	  	 paging_map(result, map);//分页信息
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
			
//			if(map.filter.filters[n]["field"]=="deptId"){
//				$("#deptId").val(map.filter.filters[n]["value"]);
//			}else if(map.filter.filters[n]["field"]=="deptName"){
//				$("#deptName").val(map.filter.filters[n]["value"]);
//			}
		}
	}
	flag_S='N';
}

//弹出框 
$("#auth").click(function(){
		 $.dialog({
			lock: true,
			max: false,
		    min: false,
			id : "jsdeptsname",
			title : "角色目录分配",
			width : 600,
			height : 430,
			content : "url:role-auth"
		});
});
function getDeptUsers(id,name){
	$.dialog({
		lock: true,
		id : "jsdeptsname",
		title : "当前部门拥有用户",
		content : "url:dept-users?deptId="+id+"&deptName="+encodeURIComponent(name),
		max: false,
	    min: false,
		width : 720,
		height : 500,
	      //fitColumns:true,适应父容器的大小  
	});
}
