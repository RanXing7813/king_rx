var api = frameElement.api, W = api.opener;	
	
$(document).ready(function(){
		go();// 获取菜单list 
});        
//list数据 
function go(page){
	var map={};    var idss="'"+$("#deptId").val()+"'";
		map.id=""; var names = $("#deptName").val();
/* W.$("input[name='ids']:checked").each(function(){
		 	//ids.push($(this).val());
		       	 idss += "'"+$(this).val()+"',";
		       	names = $(this).attr("id");
		       	//console.log($(this));
		        //获取选中节点的值
	 });
	 if(idss.length>0 ){
	     	idss=idss.substring(0,idss.length-1); 
	 }
		 map.id=idss;
 */
  $.ajax({
         url : "dept-users.json",
         dataType: "json", 
         data:{
        	 deptId:idss,
        	 deptName:names
         },
         type:"POST",
         success: function(result) { if(result==null)   return;
				var setting = {
					
					data: {
						simpleData: {
							enable: true,
							idKey:"ID",
							pIdKey:"PID",
						},
						key:{
							name:"NAME",
						}
					},
					callback: {
	                onClick: onClick
	            }
				};
				//console.log(result);
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
					 treeObj.expandAll(true);//展开所有节点
				});
         }
       });
}

// 得到父页面 属性 

api.button(
{
    name: '取消',
}); 
//获取当前节点的根节点(treeNode为当前节点)  
function getCurrentRoot(treeNode){  
	   if(treeNode.getParentNode()!=null){    
	      var parentNode = treeNode.getParentNode();    
	      return getCurrentRoot(parentNode);    
	   }else{    
	      return treeNode.id;   
	   }  
	} 
//点击文本信息 触发 	
function onClick(event, treeId, treeNode,clickFlag) {
   // alert(treeNode.id);//点击直接返回节点对象treeNode
      if(getCurrentRoot(treeNode) != treeNode.ID ){
    	var roleId = "'"+treeNode.ID+"'" ;
    	var name = treeNode.NAME ;
    	//console.log(treeNode);
    	$("#login_name").val(treeNode.LOGIN_NAME);
    	$("#email").val(treeNode.EMAIL);
    	$("#phone_num").val(treeNode.PHONE_NUM);
    	$("#remark").val(treeNode.REMARK);
    	
      }
};
 