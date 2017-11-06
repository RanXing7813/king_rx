/**
 * 部门列表
 * 
 */

var api = frameElement.api, W = api.opener;
// 得到父页面 属性

api.button({
	name : '同意',
	callback : auth,
	focus : true
}, {
	name : '取消',
});

$(document).ready(function() {
	go();// 获取菜单list
	$(".content_wrap").css("height",W.winHeight-250);
	
});
// list数据
function go(page) {
	var map = {};
	var idss = "";
	map.id = "";

	W.$("input[name='ids']:checked").each(function() {
		idss += $(this).val() + ",";
		// 获取选中节点的值
	});
	if (idss.length > 0) {
		idss = idss.substring(0, idss.length - 1);
	}
	map.id = idss;
	$.ajax({
		url : "dept-list3.json",
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
			$(document).ready(function() {
				$.fn.zTree.init($("#treeDemo"), setting, zNodes);
				 var treeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
				 var nodes = treeObj.getNodes();
	             for (var i = 0; i < nodes.length; i++) { //设置节点展开  只展开一级节点
	                treeObj.expandNode(nodes[i], true, false, true);
	             }
			});
		}
	});
}

// callback方法
function auth() {
	var ids = "";
	W.$("input[name='ids']:checked").each(function() {
		// ids.push($(this).val());
		ids += $(this).val() + ",";
		// 获取选中节点的值
	});

	if (ids.length == 0) {
		$.MsgBox.Alert("温馨提示", "未选则用户!");

		return false;
	}

	if (submitan().length == 0) {
		$.MsgBox.Alert("温馨提示", "未选中部门信息!");

		return false;
	}

	if (ids.length > 0 && submitan().length > 0) {
		ids = ids.substring(0, ids.length - 1);
		map.id = ids;
		map.deptIds = submitan();
		$.ajax({
			url : "dept-authRolesSave.json",
			async : false,
			dataType : "json",
			data : {
				models : JSON.stringify(map)
			},
			type : "POST",
			success : function(result) {
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
