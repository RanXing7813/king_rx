/**
 * 
 */

function go(){
	var map={};var idss="";
		map.id=""; var names = "";
	var dailogCheckType = $("#dailogCheckType").val(dailogCheckType_);
  $.ajax({
         url : "yw_systemAllDeptUser.json",
         dataType: "json", 
         data:{
        	 models:dailogCheckType_
         },
         type:"POST",
         success: function(result) { if(result==null)   return;
        	// console.log(result);
				var setting = {
					check: {
						enable: true,
						nocheckInherit: false
					},
					data: {
						simpleData: {
							enable: true
						}
					},
					callback: {
	                onClick: onClick,
	                onExpand: onClick,
	                onClick: treenodeClick,
	                beforeCheck: beforeCheck,
					onCheck: onCheck

	               }
				};
				if(dailogCheckType_=='Y'){
					setting.check.chkStyle = "radio";
					setting.check.radioType = "all";
				}	 
				var zNodes =result.list;
				var code;
				function showCode(str) {
					if (!code) code = $("#code");
					code.empty();
					code.append("<li>"+str+"</li>");
				}
				$(document).ready(function(){
					$.fn.zTree.init($("#treeDemo"), setting, zNodes);
					 var treeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
//					 var nodes = treeObj.getNodes();
//			            for (var i = 0; i < nodes.length; i++) { //设置节点展开  只展开一级节点
//			                treeObj.expandNode(nodes[i], true, false, true);
//			            }
					// setting.check.radioType = "all";
					// treeObj.expandAll(true);//展开所有节点
				});
         }
       });
}

//点击checkbox之前触发
function beforeCheck(treeId, treeNode) {
	//	showLog("[ "+getTime()+" beforeCheck ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
	//return (treeNode.doCheck !== false);
	//alert(1);
}

//点击checkbox框
function onCheck(e, treeId, treeNode) {
	$("#getCheckeds").click();
}	

// 获取所有选中节点
$("#getCheckeds").click(function(e){
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	nodes = zTree.getCheckedNodes(true);
	var result='';var inner_='';
	for (var i = 0; i < nodes.length; i++) {
		if(nodes.length == 1){
			result +=  nodes[i].id+',';
			var userName = nodes[i].name ;//+"  ("+nodes[i].getParentNode().name+")" ;
			inner_ +=  " <div name='"+nodes[i].id+"' class='appends'>"
			+"<input   style='width: 20px; height: 15px;' type='hidden' name='copys' id='"+nodes[i].id+"' value='"+userName+"' >"+userName+"&nbsp<a href='javascript:void(0);' onClick='remove_P(this);'><i><img style='width:15px;height:15px' src='theme/img/icons/32/Error_Symbol.png'></img></i></a ></div>";
		}else{
			result +=  nodes[i].id+',';
			var userName = nodes[i].name ;//+"  ("+nodes[i].getParentNode().name+")" ;
			inner_ +=  " <div name='"+nodes[i].id+"' class='appends'>"
			+"<input   style='width: 20px; height: 15px;' type='hidden' name='copys' id='"+nodes[i].id+"' value='"+userName+"' >"+userName+"&nbsp<a href='javascript:void(0);' onClick='remove_P(this);'><i><img style='width:15px;height:15px' src='theme/img/icons/32/Error_Symbol.png'></img></i></a >;</div>  ";
		}
      }
	$("#checkedNodes").html(inner_);
		//alert(result);		
})
//获取选中节点  
function nocheckNode(e) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	nocheck = e.data.nocheck,
	nodes = zTree.getCheckedNodes(true);
	var result='';
	for (var i = 0; i < nodes.length; i++) {
		result +=  nodes[i].id+',';
          }
}
//获取当前节点的根节点(treeNode为当前节点)  
function getCurrentRoot(treeNode){  
	   if(treeNode.getParentNode()!=null){    
	      var parentNode = treeNode.getParentNode();    
	      return getCurrentRoot(parentNode);    
	   }else{    
	      return treeNode.id;   
	   }  
	} 
//点击父节点 全选
 var checkedType='N';
function treenodeClick(event, treeId, treeNode, clickFlag) {
    //此处treeNode 为当前节点
     var str ='' ;
     str = getAllChildrenNodes(treeNode,str);
     //alert(str); //所有叶子节点ID
}

//getAll节点
function getAllChildrenNodes(treeNode,result){
	// alert();
      if (treeNode.isParent) {	  
        var childrenNodes = treeNode.children;
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		//console.log(childrenNodes.length);
        if (childrenNodes) {
            for (var i = 0; i < childrenNodes.length; i++) {
                result +=  childrenNodes[i].id+',';
					if(checkedType=='N')
                    zTree.checkNode(childrenNodes[i], true, true);  
					if(checkedType=='Y')
                    zTree.checkNode(childrenNodes[i], false, true ); 
              //  result = getChildNodes(childrenNodes[i], result);
            }
            $("#getCheckeds").click();
        }
    }
	if(checkedType=='Y'){
		checkedType='N'
	}else{
		checkedType='Y';
	}
    return result;
}

//点击文本信息  加减号   触发 	
function onClick(event, treeId, treeNode,clickFlag) {
	//console.log(treeNode.isParent);
	var map = {};
	  if (treeNode.isParent) {	  
	        var obj = treeNode;
	        map.id="'"+obj.id+"'";
	        //console.log(childrenNodes.length);
	        if (obj!=null&&obj.children!=null&&obj.children.length!=null&&obj.children.length>0) {
	        	//alert();
	        }else{
	        	
	        	$.ajax({
	                url : "yw_systemSingerDeptUser.json",
	                dataType: "json", 
			          data:{
			        	  models : JSON.stringify(map)
			          },
	                type:"POST",
	                success: function(result) { if(result==null)   return;
	        				var zNodes =result.list;
	        				var code;
	        				function showCode(str) {
	        					if (!code) code = $("#code");
	        					code.empty();
	        					code.append("<li>"+str+"</li>");
	        				}
	        				
	        				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	        				var newNodes = [{name:"无用户", "nocheck":true}];
	        				if(zNodes.length>0){
	        					 treeObj.addNodes(treeNode, zNodes);
	        				}else{
	        					treeObj.addNodes(treeNode, newNodes);
	        				}
	        				
	                }
	              });
	        }
	  }
};
//删除
function remove_P(obj){
	 obj.parentNode.remove();
}
 

 
	           