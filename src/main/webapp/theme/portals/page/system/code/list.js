
$(document).ready(function(){
	go( );
});


//list数据 
function go( ){
	$.ajax({
	      url : linkgoS2+thistreeNode.id+".json" ,
	      dataType: "json", 
	      type:"POST",
	      success: function(result) { 
	    	  if(result==null)   return;
		    	  var list = '';
		    	  $("#grid").html(list);
		    	  console.log(list);
	    	  	  var obj = result.list;
	    	  		 $.each(obj,function(n,value){
	      	 			 list += 
	      		 		   '<tr >'+
	      	  	 			 '<td class="fn-text-center" title="'+value.code_show_name+'"><a href="#" onclick="editS2(\''+value.id+'\')" class="grid_a_blue"  >'+value.code_show_name+'</a></td>'+
	      	  	 			 '<td class="fn-text-center" title="'+value.code_varchar_value+'">'+value.code_varchar_value+'</td>'+
	      	  	 			 '<td class="fn-text-center" title="'+value.code_int_value+'">'+value.code_int_value+'</td>'+
	      	  	 			 '<td class="fn-text-center"><a href="#" onclick="delS2(\''+value.id+'\',this)" class="grid_a_button">删除</a></td>'+
	      	  			   '</tr>'
	    	   	 		  })
	    	   	 		  $("#grid").html(list);
	    	  	 
	      },
	      error: function(result) {
	      }
	    });
}


function editS(id) {
	  
			 $.dialog.confirm('你确定要编辑这个消息吗？', function(){
				    $.dialog.tips('执行确定操作');
				    var mp = {};
				    mp.id = "";	//
				    mp.boxType = 1 ; // 单选
						 $.dialog({
								lock: true,
								max: false,
							    min: false,
								id : "deptTree1",
								title : "字典数据",
								width : winWidth/2,
								height : winHeight-100,
								content : "url:"+linkeditS+"?id="+id,
							}).show(); 
				}, function(){
				    $.dialog.tips('执行取消操作');
				});
}

function delS(id,obj) {
	
	 $.dialog.confirm('你确定要删除这个消息吗？', function(){
		    $.dialog.tips('执行确定操作');
		    $.post(linkdelS+"?id="+id,null,function(result){ 
				  $(obj).parent().parent().remove();
				  $.dialog({
						title: '提示',
						icon: 'success.gif',
					    content: "       操作成功             ",
					    ok: function () {
					        return true;
					    },
					});
			   }); 
		}, function(){
		    $.dialog.tips('执行取消操作');
		});
	  
}
