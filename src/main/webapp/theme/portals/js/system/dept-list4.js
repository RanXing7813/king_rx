/**
 * 部门列表
 * 
 */

var api = frameElement.api, W = api.opener;	
	// 得到父页面 属性 
$(document).ready(function(){
	$("#user_id").val(xx);
		go(xx);
 });
 function go(xx){
		var map={};
			map.id=xx;
	  $.ajax({
	         url : "dept-list4.json",
	         dataType: "json", 
	         data:{
	       	  models : JSON.stringify(map)
	         },
	         type:"POST",
	         success: function(result) { if(result==null)   return;
					var setting = {
						data: {
							simpleData: {
								enable: true,
								idKey: "ID",
								pIdKey: "PID",
							},
							key: {
								name: "NAME"
							}
						}
					};
					var zNodes =result.list;
					var code;
					function showCode(str) {
						if (!code) code = $("#code");
						code.empty();
						code.append("<li>"+str+"</li>");
					}
					$(document).ready(function(){
						$.fn.zTree.init($("#treeDemo"), setting, zNodes);
					});
	         }
	       });
	}


 
	           