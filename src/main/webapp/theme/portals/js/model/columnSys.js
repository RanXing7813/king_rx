var api = frameElement.api, W = api.opener;	
$("#saveTable").hide(); 
$(document).ready(function(){
	
	var setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			check: {
				enable: true,
				chkStyle: "radio",
				radioType: "level"
			},
			callback: {
	           // beforeCheck: zTreeBeforeClick
	          //  onMouseDown: zTreeOnMouseDown,
	            onCheck: zTreeOnCheck
			}
		};
	var setting2 = {
			data: {
				simpleData: {
					enable: true
				}
			},
			check: {
				enable: true
			},
			callback: {
            onClick: onClick,
            beforeCheck: zTreeBeforeClick2
			}
		};
			var zNodes = list_ ;
			var zNodes2 = list2_ ;
			 $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			 $.fn.zTree.init($("#treeDemo2"), setting2, zNodes2);
			 var treeObj =  $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			 var treeObj2 = $.fn.zTree.init($("#treeDemo2"), setting2, zNodes2);
			 treeObj.expandAll(true);//展开所有节点
			 treeObj2.expandAll(true);//展开所有节点
			 
			 
			 
			 
			 $("#saveTreeDemo").click(function(){
					var models = {
						tableName : tableName,
						nodes : getTreeCheckNode('treeDemo'),
						isId : 'Y'
					};
					$.post("saveTree.json",{models:JSON.stringify(models)},function(data){
//						console.log(data.message);
//						// $.MsgBox.Alert("温馨提示", data.message+"!!!");
//						if(data.message=='1'){
//							$.dialog({
//							    id: 'LHG1976D',
//							    zIndex:99999,
//							    lock:true,
//							    title:'温馨提示',
//							    content: '保存成功',
//							    max: false,
//							    min: false,
//							    ok: function(){
//							    	
//							    },
//							});
//						}
						
						 var models = {
									tableName : tableName,
									nodes : getTree2CheckNode('treeDemo2'),
									isId : 'N'
								};
						
						 $.post("saveTree.json",{models:JSON.stringify(models)},function(data){
								console.log(data);
								// $.MsgBox.Alert("温馨提示", data.message+"!!!");
								
								 var zNodes = data.ztreeList ;
								 var zNodes2 = data.ztree2List ;
								 $.fn.zTree.init($("#treeDemo"), setting, zNodes);
								 $.fn.zTree.init($("#treeDemo2"), setting2, zNodes2);
								 var treeObj =  $.fn.zTree.init($("#treeDemo"), setting, zNodes);
								 var treeObj2 = $.fn.zTree.init($("#treeDemo2"), setting2, zNodes2);
								 treeObj.expandAll(true);//展开所有节点
								 treeObj2.expandAll(true);//展开所有节点
								
								if(data.message=='1'){
									$.dialog({
									    id: 'LHG1976D',
									    zIndex:99999,
									    lock:true,
									    title:'温馨提示',
									    content: '保存成功',
									    max: false,
									    min: false,
									    ok: function(){
									    	
									    },
									});
								}
							});
					});
			 });
			 $("#saveTreeDemo2").click(function(){
				 var name1_ =  getTreeCheckNode('treeDemo') ;
				 if(name1_==null ||  name1_.length == 0){
						$.dialog({
						    id: 'LHG1976D',
						    zIndex:99999,
						    lock:true,
						    title:'温馨提示',
						    content: '请先执行第一步操作',
						    max: false,
						    min: false,
						    ok: function(){
						    	
						    },
						});
						return false;
					}
			 	  $("#saveTreeDemo").click();
			 });
			 
			 $("#saveTable").click(function(){
				  var models = {
						  tableName : tableName,
						  JY_TYPE : $("#JY_TYPE").val(),
						  EXPT_TYPE : $("#EXPORT_TYPE").val(),
						  COLUMN_NAME : $("#COLUMN_NAME").val()
						};
				 	    $.post("saveTreeSinger.json",{models:JSON.stringify(models)},function(data){
							//console.log(data.message);
							
							
							 var zNodes = data.ztreeList ;
							 var zNodes2 = data.ztree2List ;
							 $.fn.zTree.init($("#treeDemo"), setting, zNodes);
							 $.fn.zTree.init($("#treeDemo2"), setting2, zNodes2);
							 var treeObj =  $.fn.zTree.init($("#treeDemo"), setting, zNodes);
							 var treeObj2 = $.fn.zTree.init($("#treeDemo2"), setting2, zNodes2);
							 treeObj.expandAll(true);//展开所有节点
							 treeObj2.expandAll(true);//展开所有节点
							
							// $.MsgBox.Alert("温馨提示", data.message+"!!!");
//							if(data.message=='1'){
//								$.dialog({
//								    id: 'LHG1976D',
//								    zIndex:99999,
//								    lock:true,
//								    title:'温馨提示',
//								    content: '保存成功',
//								    max: false,
//								    min: false,
//								    ok: function(){
//								    	
//								    },
//								});
//							}
						});
				 
				 
			 });
			 
});        
//list数据 
//function go(page){
//	var map={};    var idss="'"+$("#deptId").val()+"'";
//		map.id=""; var names = $("#deptName").val();
///* W.$("input[name='ids']:checked").each(function(){
//		 	//ids.push($(this).val());
//		       	 idss += "'"+$(this).val()+"',";
//		       	names = $(this).attr("id");
//		       	//console.log($(this));
//		        //获取选中节点的值
//	 });
//	 if(idss.length>0 ){
//	     	idss=idss.substring(0,idss.length-1); 
//	 }
//		 map.id=idss;
// */
//  $.ajax({
//         url : "getColumnSys.json",
//         dataType: "json", 
//         data:{
//        	 tableName:idss,
//         },
//         type:"POST",
//         success: function(result) { if(result==null)   return;
				
//         }
//       });
//}

// 得到父页面 属性 

api.button(
{
    name: '关闭',
}); 
//获取当前节点的根节点(treeNode为当前节点)  
function getCurrentRoot(treeNode){  
	   if(treeNode.getParentNode()!=null){    
	      var parentNode = treeNode.getParentNode();    
	      return getCurrentRoot(parentNode);    
	   }else{    
	      return treeNode.id;   //父节点id = 0 ;
	   }  
	} 
//点击文本信息 触发 	
function onClick(event, treeId, treeNode,clickFlag) {
   // alert(treeNode.id);//点击直接返回节点对象treeNode
	//$.fn.zTree.getZTreeObj("treeDiv").getSelectedNodes()[0].myAttr
	//<!-- //COLUMN_NAME, COMMENTS, DATA_TYPE, DATA_LENGTH -->
//	  console.log(getCurrentRoot(parentNode));
	
	//需要获取选中节点状态
	
      if(getCurrentRoot(treeNode) != treeNode.id  && treeNode.checked ==true ){//非根节点
    	  $("#saveTable").show();
    	$("#COLUMN_NAME").val(treeNode.COLUMN_NAME);
    	$("#COMMENTS").val(treeNode.COMMENTS);
    	$("#DATA_TYPE").val(treeNode.DATA_TYPE);
    	$("#DATA_LENGTH").val(treeNode.DATA_LENGTH);
    	$("#JY_TYPE").val(treeNode.JY_TYPE);
    	$("#EXPORT_TYPE").val(treeNode.EXPORT_TYPE);
    	
      } else {
    	$("#COLUMN_NAME").val('');
      	$("#COMMENTS").val('');
      	$("#DATA_TYPE").val('');
      	$("#DATA_LENGTH").val('');
      	$("#JY_TYPE").val(0);
      	$("#EXPORT_TYPE").val(0);
    	$("#saveTable").hide();
      }
      
      
      
      
};
//获取选中节点  
function getTreeCheckNode(id) {
	var zTree = $.fn.zTree.getZTreeObj(id),
	nodes = zTree.getCheckedNodes(true);
	var result='';
	for (var i = 0; i < nodes.length; i++) {
		result +=  nodes[i].COLUMN_NAME;
     }
	return result;
}
//获取选中节点  
function getTree2CheckNode(id) {
	var zTree = $.fn.zTree.getZTreeObj(id),
	nodes = zTree.getCheckedNodes(true);
	var result='';
	for (var i = 0; i < nodes.length; i++) {
		result +=  nodes[i].COLUMN_NAME+',';
    }
	if(result!=''){
		result= result.substring(0,result.length-1);
	}
	
	return result;
}
var  o = 0 ;
function zTreeBeforeClick2(treeId, treeNode) {
	$("#saveTable").hide(); 
	//获取第一步选中状态
	var name1_ = getTree2CheckNode("treeDemo");
	//点击第二步当前点击节点
	var name2_ = treeNode.COLUMN_NAME;
	
	if(name1_==null ||  name1_.length == 0){
		$.dialog({
		    id: 'LHG1976D',
		    zIndex:99999,
		    lock:true,
		    title:'温馨提示',
		    content: '请先执行第一步操作',
		    max: false,
		    min: false,
		    ok: function(){
		    	
		    },
		});
		return false;
	}else if(name1_ == name2_  && !flag ){
		
		$.dialog({
		    id: 'LHG1976D',
		    zIndex:99999,
		    lock:true,
		    title:'温馨提示',
		    content: '不能选中相同于第一步的字段',
		    max: false,
		    min: false,
		    ok: function(){
		    	
		    },
		});
//		o++;
//		if(o%2==0){
//			return true;
//		}else{
			return false;
//		}
		
	}
	
};

var  flag = false;
function zTreeOnCheck(event, treeId, treeNode) {
	var zTree1 = $.fn.zTree.getZTreeObj("treeDemo"),
	nodes = zTree1.getCheckedNodes(true);
	//console.log(nodes);
	if(nodes.length>0){
		var tId_ = treeNode.tId;
		tId_.replace("treeDemo","treeDemo2");
		//console.log(tId_.replace("treeDemo","treeDemo2"));
		
		var tId2_ = tId_.replace("treeDemo","treeDemo2")
		var zTree2 = $.fn.zTree.getZTreeObj("treeDemo2"),
		nodes2 = zTree2.getCheckedNodes(true);
		//console.log(nodes2);
		//zTree2.selectNode(tId2_,false);
		for (var i = 0; i < nodes2.length; i++) {
			//alert(nodes2[i].tId == tId2_);
			//console.log(nodes2[i].tId); 
			if(nodes2[i].tId == tId2_){
				//alert(nodes2[i].checked);
				//nodes2[i].checked==false;
				flag = true;
				$("#"+nodes2[i].tId+"_check").click();
				flag = false;
				
			}
	    }
		
	}
	//获取第一步选中状态
//	var name1_ = getTree2CheckNode("treeDemo");
//	$("#treeDemo2_8_check")
};
	
//function zTreeOnMouseDown(event, treeId, treeNode) {
//   // alert(treeNode ? treeNode.tId + ", " + treeNode.name : "isRoot");
//	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
//	nodes = zTree.getCheckedNodes(true);
//	console.log(nodes);
//};
function zTreeBeforeClick(treeId, treeNode) {
	
	//获取第一步选中状态
	var name1_ = getTree2CheckNode("treeDemo");
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	nodes = zTree.getCheckedNodes(true);
	//点击第二步当前点击节点
	var name2_ = treeNode.COLUMN_NAME;
//	var treeObj = $("#treeDemo");  
//    $.fn.zTree.init(treeObj, setting, Znode1);  
//    zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");  
//    var node = zTree_Menu.getNodeByParam("id",pid );  
//    zTree_Menu.selectNode(node,true);//指定选中ID的节点  
    //zTree_Menu.expandNode(node, true, false);//指定选中ID节点展开 
	
};
function zTreeBeforeCheck(treeId, treeNode) {
    return false;
};


















 