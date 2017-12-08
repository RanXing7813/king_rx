var api = frameElement.api, W = api.opener;	 //api 内容页中调用窗口实例对象的接口  W加载窗口组件页面的window对象

var map = {};

//api.button({
//	name : '确认',
//	callback : auth,
//	focus : true
//}, {
//	name : '取消',
//});

$(document).ready(function() {
	go();// 获取菜单list
});

//alert();

var lastValue = "", nodeList=[] , fontCss = {} , clickCount = 0 ;  
// list数据
function go(page) {

	map.id = W.mp.id;
	boxType = W.mp.boxType;  // 获取父页面设置的单选状态
	
	$.ajax({
		url : linkgetDeptTreeListS,
		dataType : "json",
		data : {
			models : JSON.stringify(map)
		},
		type : "POST",
		success : function(result) {
			var setting = {
					check: {
						enable: true,
//						chkStyle: "radio",
//						radioType: "all"
					},
				data : {
					simpleData : {
						enable : true,
//						idKey: "id",
//						pIdKey: "pId",
					},
//					key: {
//						name: "name"
//					}
				},
				callback: {
					 onClick: treenodeClick, //用于捕获节点被点击的事件回调函数   全选
					// onDblClick: zTreeOnDblClick, //用于捕获 zTree 上鼠标双击之后的事件回调函数
					 onCheck: zTreeOnCheck,  //用于捕获 checkbox / radio 被勾选 或 取消勾选的事件回调函数
					//beforeExpand: beforeExpand,
					//onExpand: onExpand,
				},
				view: {
					dblClickExpand: false, //禁用双击文本展开
				}
			};
			if(boxType == 1){
				setting.check.chkStyle="radio";
				setting.check.radioType="all";
				setting.callback.onClick= function (){};
			} 
			
			var zNodes = result.list;//JSON.stringify();
			var code;
			function showCode(str) {
				if (!code)
					code = $("#code");
				code.empty();
				code.append("<li>" + str + "</li>");
			}
			for(var i=0 ; i <zNodes.length ; i ++ ) {
				if(zNodes[i].pId == "0" ){
					zNodes[i].nocheck=true;
				}
				
			}		
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			
			  divShowDept();
			
			//如果是父节点  取消checkbox框
//			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
//			if(treeObj.getNodes()[0].isParent){
//				var a_1 = treeObj.getNodes()[0];
//				for(var i=0 ,  j = treeObj.getNodes()[0].children.length ; i < j ; i ++ ) {
//					if( treeObj.getNodes()[0].children[i].isParent  ){
//						treeObj.getNodes()[0].children[i].nocheck="true";
//					}
//				}
//			}
//			//刷新修改后的整棵树
//			treeObj.refresh();
			
		}
	});
}

//键盘释放：当输入框的键盘按键被松开时，把查询到的数据结果显示在标签中  
document.getElementById("key").value = ""; //清空搜索框中的内容  
//绑定事件  
key = $("#key");  
key.bind("focus", focusKey)  
    .bind("blur", blurKey)  
    .bind("propertychange", searchNode) //property(属性)change(改变)的时候，触发事件  
    .bind("input", searchNode);  

function focusKey(e) {  
    if (key.hasClass("empty")) {  
        key.removeClass("empty");  
    }  
}  
function blurKey(e) {  
    if (key.get(0).value === "") {  
        key.addClass("empty");  
    }  
}  
function callNumber(){  
  var zTree = $.fn.zTree.getZTreeObj("treeDemo");  

  //如果结果有值，则显示比例；如果结果为0，则显示"[0/0]"；如果结果为空，则清空标签框；  
  if(nodeList.length){  
      //让结果集里边的第一个获取焦点（主要为了设置背景色），再把焦点返回给搜索框  
      zTree.selectNode(nodeList[0],false );  
      document.getElementById("key").focus();  

      clickCount=0; //防止重新输入的搜索信息的时候，没有清空上一次记录  

      //显示当前所在的是第一条  
      document.getElementById("number").innerHTML="["+ (clickCount+1) +"/"+nodeList.length+"]";  

  }else if(nodeList.length == 0){  
      document.getElementById("number").innerHTML="[0/0]";  
      zTree.cancelSelectedNode(); //取消焦点  
  }  

  //如果输入框中没有搜索内容，则清空标签框  
  if(document.getElementById("key").value ==""){  
      document.getElementById("number").innerHTML="";  
      zTree.cancelSelectedNode();  
  }  
} 
//搜索节点  
function searchNode(e) {  
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");  
    var value = $.trim(key.get(0).value);  
    var keyType = "name";  
  
    if (key.hasClass("empty")) {  
        value = "";  
    }  
    if (lastValue === value) return;  
    lastValue = value;  
    if (value === ""){  
        updateNodes(false);  
        return;  
    };  
    nodeList = zTree.getNodesByParamFuzzy(keyType, value, null); //调用ztree的模糊查询功能，得到符合条件的节点  
    updateNodes(true); //更新节点  
}  
//高亮显示被搜索到的节点  
function updateNodes(highlight) {  
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");  
    for( var i=0, l=nodeList.length; i<l; i++) {  
        nodeList[i].highlight = highlight; //高亮显示搜索到的节点(highlight是自己设置的一个属性)  
        //zTree.expandNode(nodeList[i].getParentNode(), true, false, false); //将搜索到的节点的父节点展开  
        zTree.updateNode(nodeList[i]); //更新节点数据，主要用于该节点显示属性的更新  
    }  
}  
//设置颜色  
function getFontCss(treeId, treeNode) {  
    return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};  
}  


//点击向上按钮时，将焦点移向上一条数据  
function clickUp(){  
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");  
    //如果焦点已经移动到了最后一条数据上，就返回第一条重新开始，否则继续移动到下一条  
    if(nodeList.length==0){  
        $.MsgBox.Alert("温馨提示", " 没有搜索结果！ ");
        return ;  
    }else if(clickCount==0) {  
        $.MsgBox.Alert("温馨提示", " 您已位于第一条记录上！ ");
        return;  
        //让结果集里边的下一个节点获取焦点（主要为了设置背景色），再把焦点返回给搜索框  
        //zTree.selectNode(nodeList[clickCount], false)  
    }else{  
        //让结果集里边的第一个获取焦点（主要为了设置背景色），再把焦点返回给搜索框  
    	//alert(clickCount);
        zTree.selectNode(nodeList[ --clickCount], false);  
       
    }  

    //document.getElementById("key").focus();  
    //显示当前所在的是条数  
    document.getElementById("number").innerHTML = "[" +  (clickCount+1) + "/" + nodeList.length + "]";  
    zTree.expandNode(nodeList[clickCount].getParentNode(), true, false, false ); //将搜索到的节点的父节点展开  
    nodeList[clickCount].highlight = true;
    zTree.updateNode(nodeList[clickCount]);
    
   
    
}  
//点击向上按钮时，将焦点移向下一条数据  
function clickDown(){  
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");  
    //如果焦点已经移动到了最后一条数据上，则提示用户（或者返回第一条重新开始），否则继续移动到下一条  
    if(nodeList.length==0){  
        alert("没有搜索结果！");  
        return ;  
    }else if(nodeList.length==(clickCount+1))  
    {  
        alert("您已位于最后一条记录上！")  
        return;  
    }else{  
        //让结果集里边的第一个获取焦点（主要为了设置背景色），再把焦点返回给搜索框  
        zTree.selectNode(nodeList[ ++clickCount], false , false)  
        //clickCount ++;  
    }  
   // document.getElementById("key").focus();  
    //显示当前所在的条数  
    document.getElementById("number").innerHTML = "[" + (clickCount+1) + "/" + nodeList.length + "]";  
    
    // zTree.expandAll(false);
//    for(var i=0 ,  j = zTree.getNodes()[0].children.length ; i < j ; i ++ ) {
//    	//console.log(i);
//        zTree.expandNode(zTree.getNodes()[0].children[i], false, true, true); //.children
//        zTree.showNode(zTree.getNodes()[0].children[i]);
//	}
    
    
   // zTree.refresh();
   // zTree.selectNode(nodeList[ clickCount], true , false)  
//    		if(zTree.getNodes()[0].isParent){
//				
//				var a_1 = treeObj.getNodes()[0];
//				for(var i=0 ,  j = treeObj.getNodes()[0].children.length ; i < j ; i ++ ) {
//					if( treeObj.getNodes()[0].children[i].isParent  ){
//						treeObj.getNodes()[0].children[i].nocheck="true";
//					}
//				}
//			}
    
    zTree.selectNode(nodeList[ clickCount], true)  
    zTree.expandNode(nodeList[clickCount].getParentNode(), true, false, false ); //将搜索到的节点的父节点展开  
    nodeList[clickCount].highlight = true;
    zTree.updateNode(nodeList[clickCount]);
    
   // nodeList[clickCount].click;
    
   // zTree.refresh();
} 

var curExpandNode = null;
function beforeExpand(treeId, treeNode) {
	var pNode = curExpandNode ? curExpandNode.getParentNode():null;
	var treeNodeP = treeNode.parentTId ? treeNode.getParentNode():null;
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	for(var i=0, l=!treeNodeP ? 0:treeNodeP.children.length; i<l; i++ ) {
		if (treeNode !== treeNodeP.children[i]) {
			zTree.expandNode(treeNodeP.children[i], false);
		}
	}
	while (pNode) {
		if (pNode === treeNode) {
			break;
		}
		pNode = pNode.getParentNode();
	}
	if (!pNode) {
		singlePath(treeNode);
	}

}
function onExpand(event, treeId, treeNode) {
	curExpandNode = treeNode;
}
function singlePath(newNode) {
	if (newNode === curExpandNode) return;

    var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            rootNodes, tmpRoot, tmpTId, i, j, n;

    if (!curExpandNode) {
        tmpRoot = newNode;
        while (tmpRoot) {
            tmpTId = tmpRoot.tId;
            tmpRoot = tmpRoot.getParentNode();
        }
        rootNodes = zTree.getNodes();
        for (i=0, j=rootNodes.length; i<j; i++) {
            n = rootNodes[i];
            if (n.tId != tmpTId) {
                zTree.expandNode(n, false);
            }
        }
    } else if (curExpandNode && curExpandNode.open) {
		if (newNode.parentTId === curExpandNode.parentTId) {
			zTree.expandNode(curExpandNode, false);
		} else {
			var newParents = [];
			while (newNode) {
				newNode = newNode.getParentNode();
				if (newNode === curExpandNode) {
					newParents = null;
					break;
				} else if (newNode) {
					newParents.push(newNode);
				}
			}
			if (newParents!=null) {
				var oldNode = curExpandNode;
				var oldParents = [];
				while (oldNode) {
					oldNode = oldNode.getParentNode();
					if (oldNode) {
						oldParents.push(oldNode);
					}
				}
				if (newParents.length>0) {
					zTree.expandNode(oldParents[Math.abs(oldParents.length-newParents.length)-1], false);
				} else {
					zTree.expandNode(oldParents[oldParents.length-1], false);
				}
			}
		}
	}
	curExpandNode = newNode;
}








//声明一个属性方法作为确定的返回
api.auths = function(){
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getCheckedNodes(true);
	v = ""; n = "" ;
	if(nodes){
		for (var i = 0; i < nodes.length; i++) {
			v += nodes[i].id+ ",";
			n += nodes[i].name+ ",";
		}
	}
	map.deptId = v ;
	map.name = n ;
	map.isPublic = 'Y';
	return map;
}




function submitan() {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getCheckedNodes(true);
	v = ""; n = "" ;
	if(nodes){
		for (var i = 0; i < nodes.length; i++) {
			v += nodes[i].id+ ",";
			n += nodes[i].name+ ",";
		}
	}
	map.deptId = v ;
	map.name = n ;
	
	return map;
}
//
//// callback方法
//function auth() {
//W.l="11111111111";
//	if (submitan().length == 0) {
//		return false;
//	}
//	if ( submitan().length > 0) {
//		map.deptIds = submitan();
//		map.isPublic = 'Y';
//		$.ajax({
//			url : linksaveDeptTreeListS,
//			async : false,
//			dataType : "json",
//			data : {
//				models : JSON.stringify(map)
//			},
//			type : "POST",
//			success : function(result) {
////				 flag_S='Y';
////				 var menusId =  W.menusId;
////				 W.$("#"+menusId).click();
//				W.$.MsgBox.Alert("温馨提示",result.message+"!!");
//    			 
//			}
//		});
//
//	}
//
//}


//点击父节点 全选
var checkedType='N';
function treenodeClick(event, treeId, treeNode, clickFlag) {
   //此处treeNode 为当前节点
    var str ='' ;
    if(treeNode.open){
    	 str = getAllChildrenNodes(treeNode,str);
    }
}

//getAll节点
function getAllChildrenNodes(treeNode,result){
	// alert();
     if (treeNode.isParent) {	
       var childrenNodes = treeNode.children;
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		
		zTree.checkNode( treeNode, true, true);
		
		
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
           
           divShowDept();
       }
   }
	if(checkedType=='Y'){
		checkedType='N'
	}else{
		checkedType='Y';
	}
   return result;
}

//获取所有选中节点展示到div中
function divShowDept(){
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
	nodes = zTree.getCheckedNodes(true);
	var result='';var inner_='';
	for (var i = 0; i < nodes.length; i++) {
		if(nodes.length == 1){
			result +=  nodes[i].id+',';
			var userName = nodes[i].name ;//+"  ("+nodes[i].getParentNode().name+")" ;
			inner_ +=  " <div name='"+nodes[i].id+"' class='appends'>"
			+"<a href='javascript:void(0);' onClick='remove_P(this) '  ><i><img style='width:15px;height:15px' src='/theme/zTree/img/Error_Symbol.png'></img></i></a ><input   style='width: 20px; height: 15px;' type='hidden' name='copys' id='"+nodes[i].id+"' value='"+userName+"' >"+userName+"&nbsp;</div>";
		}else{
			result +=  nodes[i].id+',';
			var userName = nodes[i].name ;//+"  ("+nodes[i].getParentNode().name+")" ;
			inner_ +=  " <div name='"+nodes[i].id+"' class='appends'>"
			+"<a href='javascript:void(0);'   onClick='remove_P(this) '  ><i><img style='width:15px;height:15px' src='/theme/zTree/img/Error_Symbol.png'></img></i></a ><input   style='width: 20px; height: 15px;' type='hidden' name='copys' id='"+nodes[i].id+"' value='"+userName+"' >"+userName+"&nbsp;</div>  ";
		}
      }
	$("#checkedNodes").html(inner_);
	$("#checkedNodesNum").html(nodes.length);
	
		//alert(result);	
	//onClick='remove_P(this);other_P('"+nodes[i].id+"\");"  
}


function zTreeOnDblClick(event, treeId, treeNode) {
   // alert(treeNode ? treeNode.tId + ", " + treeNode.name : "isRoot");
};

//删除
function remove_P(obj ){
	 obj.parentNode.remove();
	 var d = obj.parentNode;
	 other_P( $(d).attr("name") );
	
}
//通过id取消选中  触发点击事件
function other_P( id ){
	 var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	 var nodes  = zTree.getCheckedNodes(true);
		for (var i = 0; i < nodes.length; i++) {
			if(nodes[i].id == id ){
				$("#"+nodes[i].tId+"_check").click();
			}
	    }
		 $("#checkedNodesNum").html(nodes.length-1);
}

//用于捕获 checkbox / radio 被勾选 或 取消勾选的事件回调函数
function zTreeOnCheck(event, treeId, treeNode) {
   // alert(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked);
		divShowDept();
};