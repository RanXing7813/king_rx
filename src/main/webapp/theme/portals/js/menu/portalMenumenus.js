function beforeDrag(treeId, treeNodes) {
			return false;
		}
		function beforeEditName(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.NAME);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			setTimeout(function() {
				var flag=false;
				$.dialog({
				    lock: true,
				    title:'编辑节点',
				    content: '进入节点 ' + treeNode.NAME + ' 的编辑状态吗？',
				    max:false,
				    min:false,
				    width: 200,
				    height: 80,
				    ok: function () {
				    	setTimeout(function() {
							$.dialog({
								lock: true,
								max:false,
								min:false,
								id : "menuadd",
								title : "菜单编辑",
								width : 340,
								height : 300,
								content : "url:yw_MenuEdit?id="+treeNode.ID
							});
						}, 0);
				        return true;
				    },
				    cancel: function(){
				    	return true;
				    }
				});
			}, 0);
		}
		function beforeRemove(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.NAME);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			var flag=false;
			$.dialog({
				    lock: true,
				    title:'删除节点',
				    content: '确认删除 节点' + treeNode.NAME + '吗？',
				    max:false,
				    min:false,
				    width: 200,
				    height: 80,
				    ok: function () {
				    	if(treeNode.isParent != true){//判断是不是父节点
								$.ajax({
									url:"yw_menudel.json",
									type:"POST",
									async: false,
									max:false,
									min:false,
									data:{
										models:treeNode.ID
									},
									success:function(data){
										test();
									}
								});
						}else{
							if(treeNode.children.length == 0){
									$.ajax({
										url:"yw_menudel.json",
										type:"POST",
										async: false,
										max:false,
										min:false,
										data:{
											models:treeNode.ID
										},
										success:function(data){
											test();
										}
									});
							}else{
								$.dialog({
								    title:'删除节点',
								    content: '该节点下有子节点，不能删除',
								    max:false,
								    min:false,
								    width: 200,
								    height: 80,
								    ok:function(){
								    	flag= false;
								    }
								});
							}
						}
				    	flag= false;;
				    },
				    cancel: function(){
				    	flag=false;
				    	return true;
				    }
				});
			return flag;
		}
		function onRemove(e, treeId, treeNode) {
			showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.NAME);
		}
		function beforeRename(treeId, treeNode, newName, isCancel) {
			className = (className === "dark" ? "":"dark");
			showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.NAME + (isCancel ? "</span>":""));
			if (newName.length == 0) {
				setTimeout(function() {
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					zTree.cancelEditName();
					alert("节点名称不能为空.");
				}, 0);
				return false;
			}
			return true;
		}
		function onRename(e, treeId, treeNode, isCancel) {
			showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.NAME + (isCancel ? "</span>":""));
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

		var newCount = 1;
		function addHoverDom(treeId, treeNode) {
			var tparent="N";
			if(treeNode.isParent == true){
				tparent="Y";
			}
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
			var delStr = "<span class='DiyDom' id='delBtn_" + treeNode.tId
			+ "' title='作废' onfocus='this.blur();' onclick='del(\""+treeNode.ID+"\",\""+treeNode.NAME+"\",\""+tparent+"\");'></span>";
			sObj.after(delStr);
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='新增' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.tId);
			if (btn) btn.bind("click", function(){
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				$.dialog({
				lock: true,
				max:false,
				min:false,
				id : "menuadd",
				title : "菜单新增",
				width : 340,
				height : 300,
				content : "url:yw_Menuadd?pId="+treeNode.ID
			});
			});
		};
		function test(){
			$("#"+menusId).click();
		}
		function del(id,name,parent){
			$.dialog({
			    lock: true,
			    title:'作废节点',
			    content: '确认作废 节点' + name + '吗？',
			    max:false,
			    min:false,
			    width: 200,
			    height: 80,
			    ok: function () {
			    	if(parent == "N"){//判断是不是父节点
							$.ajax({
								url:"yw_menudel.json",
								type:"POST",
								async: false,
								max:false,
								min:false,
								data:{
									models:id
								},
								success:function(data){
									test();
								}
							});
					}else{
							$.dialog({
							    title:'作废节点',
							    content: '该节点下有子节点，不能作废',
							    max:false,
							    min:false,
							    width: 200,
							    height: 80,
							    ok:function(){
							    	flag= false;
							    }
							});
						}
					}
			});
		}
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
			$("#delBtn_"+treeNode.tId).unbind().remove();
		};
		function selectAll() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
		}
		function nodes(){
			$.ajax({
				url:"yw_MenusZree.json",
				type:"POST",
				async: false,
				data:{
				},
				success: function(result) {if(result==null)   return;
					setting = {
							view: {
								addHoverDom: addHoverDom,
								removeHoverDom: removeHoverDom,
								selectedMulti: false
							},
							/* check: { //单选
								enable: true,
								chkStyle: "radio",
								radioType: "all" //all整个ztree为一组  level 每个节点为一组
							}, */
							edit: {
								enable: true,
								editNameSelectAll: true,
								removeTitle: "作废",
								renameTitle: "编辑",
								//showRemoveBtn: showRemoveBtn,  //此方法对最后一个节点不显示删除按钮
								//showRenameBtn: showRenameBtn	//此方法对第一个节点不显示编辑按钮
							},
							data: {
								simpleData: {
									enable: true,
									idKey: "ID",
									pIdKey: "PID",
								},
								key: {
									name: "NAME"
								}
							},
							callback: {
								beforeDrag: beforeDrag,
								beforeEditName: beforeEditName,
								beforeRemove: beforeRemove,
								beforeRename: beforeRename,
								onRemove: onRemove,
								onRename: onRename,
								onClick: onclick
								//onCheck:check //复选框点击事件
							}
						};
						zNodes =result.list;
						$(document).ready(function(){
							$.fn.zTree.init($("#treeDemo"), setting, zNodes);
							 var treeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
							 treeObj.expandAll(true);//展开所有节点 
						});
				}
				})
			}
		function onclick(event, treeId, treeNode,clickFlag) {
			$.ajax({
				url:"yw_MenuByone.json",
				type:"POST",
				async: false,
				data:{
					models:treeNode.ID
				},
				success:function(data){
					var dto=data.dto;
					$("#menuId").val(dto.menuId);
					$("#parentId").val(dto.parentId);
					$("#menuName").val(dto.menuName);
					$("#requestUrl").val(dto.requestUrl);
					$("#menuIcon").val(dto.menuIcon);
					$("#orderId").val(dto.orderId);
					$("#menuImg").val(dto.menuImg);
				}
			})
		};