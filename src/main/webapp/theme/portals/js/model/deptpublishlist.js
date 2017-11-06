var api = frameElement.api, W = api.opener;	 //api 内容页中调用窗口实例对象的接口  W加载窗口组件页面的window对象
function checkThis(id,name){
	W.$("#MH_ZYDEPTNAME").val(  name  );
	W.$("#MH_ZYDEPTID").val(  id  );
	W.$("#MH_ZYDEPTNAME").focus();
	api.close();
	
}

//
$("#publishDepts").click(function(){
	//alert($("#grid").find("input[name='publishDepts']").length);
	
	var ids = [];
	$("#grid :checked").each(function(){
		//console.log(this.value);
		ids.push(this.value);
	});
	//console.log(ids);
	if(ids.length>0){
		
		
		
	}
	
	
});
var map = {};
// 得到父页面 属性

api.button({
	name : '确认发布',
	callback : auth,
	focus : true
}, {
	name : '取消',
});

$(document).ready(function() {
	go();// 获取菜单list
});
// list数据
function go(page) {
	var map = {};
	var idss = "";
	map.id = $("#model_ids").val();

//	W.$("input[name='ids']:checked").each(function() {
//		idss += $(this).val() + ",";
//		// 获取选中节点的值
//	});
//	if (idss.length > 0) {
//		idss = idss.substring(0, idss.length - 1);
//	}
//	map.id = idss;
	$.ajax({
		url : "getDeptPublishList.json",
		dataType : "json",
		data : {
			models : JSON.stringify(map)
		},
		type : "POST",
		success : function(result) {
			var setting = {
				check : {
					enable : true
				},
				data : {
					simpleData : {
						enable : true,
						idKey: "ID",
						pIdKey: "PID",
					},
					key: {
						name: "NAME"
					}
				}
			};
			var zNodes = result.list;
			var code;
			function showCode(str) {
				if (!code)
					code = $("#code");
				code.empty();
				code.append("<li>" + str + "</li>");
			}
			
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		}
	});
}

// callback方法
function auth() {
	var ids = "";
//	W.$("input[name='ids']:checked").each(function() {
//		// ids.push($(this).val());
//		ids += $(this).val() + ",";
//		// 获取选中节点的值
//	});
//
//	if (ids.length == 0) {
//		$.MsgBox.Alert("温馨提示", "未选则用户!");
//
//		return false;
//	}
	if (submitan().length == 0) {
		$.MsgBox.Alert("温馨提示", "未选中部门信息!");

		return false;
	}

	if ( submitan().length > 0) {
		ids = ids.substring(0, ids.length - 1);
		map.id = $("#model_ids").val();
		map.deptIds = submitan();
		map.isPublic = 'Y';
		$.ajax({
			url : "saveDeptPublishList.json",
			async : false,
			dataType : "json",
			data : {
				models : JSON.stringify(map)
			},
			type : "POST",
			success : function(result) {
				 flag='Y';
				 var menusId =  W.menusId;
				 W.$("#"+menusId).click();
    			 
    			 
			}
		});

	}

}
function submitan() {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	nodes = treeObj.getCheckedNodes(true);
	v = "";
	for (var i = 0; i < nodes.length; i++) {
		v += nodes[i].ID+ ",";
		// 获取选中节点的值
	}
	if (v.length > 0) {
		v = v.substring(0, v.length - 1);
	}
	// W.document.getElementById('xx').value = v;//给父页面id 传值
	// alert(v);
	return v;
}
