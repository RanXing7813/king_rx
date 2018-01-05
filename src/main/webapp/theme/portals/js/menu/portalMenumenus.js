
var treemap = {} ; 
	treemap.id="";
var thistreeNode ;

function go() {
	$.ajax({
		url : linkgetDeptTreeList ,
		type : "POST",
		async : false,
		data : {},
		success : function(result) {
			if (result == null)
				return;
			setting = {
				view : {
					addHoverDom : addHoverDom,
					removeHoverDom : removeHoverDom,
					selectedMulti : false
				},
				/*
				 * check: { //单选 enable: true, chkStyle: "radio", radioType:
				 * "all" //all整个ztree为一组 level 每个节点为一组 },
				 */
				edit : {
					enable : true,
					editNameSelectAll : true,
					removeTitle : "删除",
					renameTitle : "编辑",
					showRemoveBtn: showRemoveBtn, //此方法对最后一个节点不显示删除按钮
					showRenameBtn: showRenameBtn //此方法对第一个节点不显示编辑按钮
				},
				data : {
					simpleData : {
						enable : true,
						idKey : "id",
						pIdKey : "pId",
					},
					key : {
						name : "name"
					}
				},
				callback : {
					beforeEditName : beforeEditName,
					beforeRemove : beforeRemove,
//					beforeRename : beforeRename,
//					onRemove : onRemove,
//					onRename : onRename,
					onClick : onclick
				// onCheck:check //复选框点击事件
				}
			};
			zNodes = result.list;
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			var treeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			treeObj.expandAll(true);// 展开所有节点
		}
	})
}
//删除按钮的显示过滤
function showRemoveBtn(treeId, treeNode) {
	return !treeNode.isParent;
}
//编辑按钮的显示过滤
function showRenameBtn(treeId, treeNode) {
	return true;
}
//function beforeEditName(treeId, treeNode) {
//
//	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//	zTree.selectNode(treeNode);
//	setTimeout(function() {
//		var flag = false;
//		$.dialog({
//			lock : true,
//			title : '编辑节点',
//			content : '进入节点 ' + treeNode.name + ' 的编辑状态吗？',
//			max : false,
//			min : false,
//			width : 200,
//			height : 80,
//			ok : function() {
//				setTimeout(function() {
//					$.dialog({
//						lock : true,
//						max : false,
//						min : false,
//						id : "menuadd",
//						title : "菜单编辑",
//						width : 340,
//						height : 300,
//						content : "url:yw_MenuEdit?id=" + treeNode.id
//					});
//				}, 0);
//				return true;
//			},
//			cancel : function() {
//				return true;
//			}
//		});
//	}, 0);
//}
function beforeRemove(treeId, treeNode) {
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
				// $.dialog.alert(result.message);
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
	
function getTime() {
	var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now
			.getSeconds(), ms = now.getMilliseconds();
	return (h + ":" + m + ":" + s + " " + ms);
}

var newCount = 1;
function addHoverDom(treeId, treeNode) {
	var tparent = "N";
	if (treeNode.isParent == true) {
		tparent = "Y";
	}
	var sObj = $("#" + treeNode.tId + "_span");
	
	if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
		return;
	var updStateStr = "<span class='DiyDom' id='updStateBtn_" + treeNode.tId
			+ "' title='作废' onfocus='this.blur();' ></span>";  //onclick='updState(\"" +treeNode.id+"\","+treeNode.isParent+");'
	sObj.after(updStateStr);
	var updbtn = $("#updStateBtn_" + treeNode.tId);
	if (updbtn)
		updbtn.bind("click", function() {
			//updtreeNode = treeNode;
			updState(treeNode.id , treeNode.isParent);
			return false;
		});
	
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='新增' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_" + treeNode.tId);
	if (btn)
		btn.bind("click", function() {
			thistreeNode = treeNode
			addS("ztreeContent",'POST')	;	
			return false;
		});
};


function updState(id , isParent) {
	if(!isParent){
			$.dialog.confirm('你确定要编辑这个消息吗？', function(){
				 $("#ztreeContent").html("");
			    $.dialog.tips('执行确定操作');
			    $.post(linkupdS+"?id="+id,null,function(result){ 
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					var nodes = zTree.getNodesByParam("id", id, null);
					zTree.removeNode(nodes[0],false);
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
		
		
	} else {
		$.dialog({
			title : '作废节点',
			content : '该节点下有子节点，不能作废',
			max : false,
			min : false,
			width : 200,
			height : 80,
			ok : function() {
				flag = false;
			}
		});
	}
}

function removeHoverDom(treeId, treeNode) {
	$("#addBtn_" + treeNode.tId).unbind().remove();
	$("#updStateBtn_" + treeNode.tId).unbind().remove();
};
