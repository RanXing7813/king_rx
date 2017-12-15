
var treemap = {} ; 

treemap.id="";

$(document).ready(function() {
	go();// 获取菜单list
//	var o = document.getElementById("listmain");
//	var h = o.offsetHeight; //高度
//	var w = o.offsetWidth; //宽度
//	console.log(document.body.clientHeight);
//	o.style.heigth= (document.body.clientHeight-200);
//		$(".zTreeDemoBackground").attr("heigth","500px");
//		$("#listmain").style.height="300px";
//		$(".zTreeDemoBackground").css("heigh","300px");
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
						editNameSelectAll: false,
						showRemoveBtn: showRemoveBtn,
					//	showRenameBtn: showRenameBtn,
						renameTitle: "编辑节点名称",
						removeTitle: "删除节点"
					},
				    data : {
					simpleData : {
						enable : true,
						idKey: "id",
						pIdKey: "pId",
					},
					key: {
						name: "name"
					},
					
				}
			};
			var zNodes =  result.list;
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
var log, className = "dark";
function beforeDrag(treeId, treeNodes) {
	console.log("beforeDrag");
	return false;
}
function beforeEditName(treeId, treeNode) {
	console.log("beforeEditName");
	className = (className === "dark" ? "":"dark");
	showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	return confirm("Start node '" + treeNode.name + "' editorial status?");
}
function beforeRemove(treeId, treeNode) {
	console.log("beforeRemove");

	className = (className === "dark" ? "":"dark");
	showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	return confirm("Confirm delete node '" + treeNode.name + "' it?");
}
function onRemove(e, treeId, treeNode) {
	console.log("onRemove");
	
	showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
}
function beforeRename(treeId, treeNode, newName, isCancel) {
	console.log("beforeRename");

	className = (className === "dark" ? "":"dark");
	showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
	if (newName.length == 0) {
		alert("Node name can not be empty.");
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		setTimeout(function(){zTree.editName(treeNode)}, 10);
		return false;
	}
	return true;
}
function onRename(e, treeId, treeNode, isCancel) {
	console.log("onRename");

	showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
}
function showRemoveBtn(treeId, treeNode) {
	console.log("showRemoveBtn");

	//return !treeNode.isFirstNode;
	return !treeNode.isParent;
}
function showRenameBtn(treeId, treeNode) {
	console.log("showRenameBtn");

	return !treeNode.isLastNode;
}
function showLog(str) {
	if (!log) log = $("#log");
	log.append("<li class='"+className+"'>"+str+"</li>");
	if(log.children("li").length > 8) {
		log.get(0).removeChild(log.children("li")[0]);
	}
}
function getTime() {
	var now= new Date(),
	h=now.getHours(),
	m=now.getMinutes(),
	s=now.getSeconds(),
	ms=now.getMilliseconds();
	return (h+":"+m+":"+s+ " " +ms);
}


//鼠标hover方法
var newCount = 1;
function addHoverDom(treeId, treeNode) {
	console.log("addHoverDom");

	var sObj = $("#" + treeNode.tId + "_span");//节点名称span
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
		+ "' title='新增子节点' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.tId);
	if (btn) 
		btn.bind("click", function(){
//			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//			zTree.addNodes(treeNode, {
//				id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)  
//			});
			addS("ztreeContent",'POST')	;		
		return false;
	});
};
function removeHoverDom(treeId, treeNode) {
	console.log("removeHoverDom");

	$("#addBtn_"+treeNode.tId).unbind().remove();
};



function selectAll() {
	console.log("selectAll");

	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
}
function submitan() {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getCheckedNodes(true);
	v = "";
	n ="";
	for (var i = 0; i < nodes.length; i++) {
		v += nodes[i].id+ ",";
		// 获取选中节点的值
		n +=nodes[i].name+ ",";
	}
	if (v.length > 0) {
		v = v.substring(0, v.length - 1);
		n = n.substring(0, n.length - 1);
	}
	return treemap;
}
