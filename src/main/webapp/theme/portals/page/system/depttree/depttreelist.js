
var treemap = {} ; 
	treemap.id="";
var thistreeNode;
var log, className = "dark";

$(document).ready(function() {
	go();// 获取菜单list
	$(".zTreeDemoBackground").css("height",document.body.clientHeight-200);
	$("#ztreeContent").css("height",document.body.clientHeight-200);
});

// list数据
function go(page) {
	$.ajax({
		url : linkpowerDeptTreeList,
		dataType : "json",
		data : {
			models : JSON.stringify(treemap)
		},
		type : "POST",
		success : function(result) {
			var setting = {
//					check: {
//						enable: true,
//					},
					view: {
						addHoverDom: addHoverDom,
						removeHoverDom: removeHoverDom,
						selectedMulti: false
					},
					edit: { 
						enable: true,
						editNameSelectAll: true,
						showRemoveBtn: showRemoveBtn,
						showRenameBtn: showRenameBtn,
						renameTitle: "编辑节点名称",
						removeTitle: "删除节点"
					},
					key: {
					name: "name"
					},
				    data : {
						simpleData : {
							enable : true,
							idKey: "id",
							pIdKey: "pId"
						},
					},
					callback: {
						//beforeDrag: beforeDrag,
						beforeEditName: beforeEditName,
						beforeRemove: beforeRemove,
						//beforeRename: beforeRename,
						//onRemove: onRemove,
						//onRename: onRename
					},
			};//end setting
			
			var zNodes =  result.list;
			var code;
			function showCode(str) {
				if (!code)
					code = $("#code");
				code.empty();
				code.append("<li>" + str + "</li>");
			}
			
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		}//end success
	});//end $.ajax
}//end go

//捕获click编辑按钮之前
function beforeEditName(treeId, treeNode) {
	 $.dialog.confirm('你确定要编辑这个消息吗？', function(){
		    $.dialog.tips('执行确定操作');
		    onRename(treeId, treeNode);
		}, function(){
		    $.dialog.tips('执行取消操作');
		});
	  return  false;//不进入直接编辑节点名称操作方法,   自定义了onRename();方法
}
function onRename(treeId, treeNode) {
	editS(treeNode.id,'POST',"ztreeContent")	;
	thistreeNode = treeNode;
}
//捕获编辑结束失去焦点之后
//function beforeRename(treeId, treeNode, newName, isCancel) {
//	console.log("beforeRename");
//    $.dialog.confirm('你确定要编辑这个消息吗？', function(){
//	    $.dialog.tips('执行确定操作');
//	    alert("onRename执行确定操作");
//	    return  true;
//	}, function(){
//	    $.dialog.tips('执行取消操作');
//		return false;
//	});
//}

//捕获click删除按钮之前
function beforeRemove(treeId, treeNode) {
	var f =false;
    $.dialog.confirm('你确定要删除这个消息吗？', function(){
	    $.dialog.tips('执行确定操作');
	    onRemove(treeId, treeNode);
	}, function(){
	    $.dialog.tips('执行取消操作');
	});
	return false;
}
function onRemove(treeId, treeNode) {
	console.log("onRemove");
	  $.post(linkdelS+"?id="+treeNode.id,null,function(result){ 
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.removeNode(treeNode);
			 //$.dialog.alert(result.message);
			$.dialog({
				title: '提示',
				icon: 'success.gif',
			    content: "       操作成功             ",
			    ok: function () {
			        return true;
			    },
			});
	   });  
}

//删除按钮的显示过滤
function showRemoveBtn(treeId, treeNode) {
	return !treeNode.isParent;
}
//编辑按钮的显示过滤
function showRenameBtn(treeId, treeNode) {
	return true;
}

function getTime() {
	var now= new Date(),
	h=now.getHours(),
	m=now.getMinutes(),
	s=now.getSeconds(),
	ms=now.getMilliseconds();
	return (h+":"+m+":"+s+ " " +ms);
}

//用于当鼠标移入节点时hover方法添加新增按钮及绑定新增方法,   同事编辑和删除已经自带了,
var newCount = 1;
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");//节点名称span
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='新增子节点' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btnAdd = $("#addBtn_"+treeNode.tId);
	var btnRemove = $("#"+treeNode.tId+"#_remove");
	if (btnAdd) {
		btnAdd.bind("click", 
				function(){
							thistreeNode = treeNode
							addS("ztreeContent",'POST')	;	
							return false;
						 });
	     }
};
//用于当鼠标移出节点时
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.tId).unbind().remove();
};


function selectAll() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
}
function submitan() {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getCheckedNodes(true);
	v = [];
	n ="";
	// 获取选中节点的值
	for (var i = 0; i < nodes.length; i++) {
		//v += nodes[i].id+ ",";
		v.push(nodes[i].id);
		n +=nodes[i].name+ ",";
	}
	if (v.length > 0) {
		//v = v.substring(0, v.length - 1);
		n = n.substring(0, n.length - 1);
	}
	treemap.ids=v;
	treemap.names=n;
	return treemap;
}
