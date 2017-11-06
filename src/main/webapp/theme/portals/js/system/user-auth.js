var api = frameElement.api, W = api.opener;	
	
$(document).ready(function(){
		go();// 获取菜单list 
});        
//list数据 
function go(page){
	var map={};var idss="";
		map.id="";
	 W.$("input[name='ids']:checked").each(function(){
		       	 idss += $(this).val()+",";
		        //获取选中节点的值
	 });
	 if(idss.length>0 ){
	     	idss=idss.substring(0,idss.length-1); 
	 }
		 map.id=idss;
  $.ajax({
         url : "role-user-authRoles.json",
         dataType: "json", 
         data:{
       	  models : JSON.stringify(map)
         },
         type:"POST",
         success: function(result) { if(result==null)   return;
				var setting = {
					check: {
						enable: true
					},
					data: {
						simpleData: {
							enable: true
						}
					}
				};
				var zNodes =result.list;
				var code;
				function showCode(str) {
					if (!code) code = $("#code");
					code.empty();
					code.append("<li>"+str+"</li>");
				}
				$(document).ready(function(){
					$.fn.zTree.init($("#treeDemo"), setting, zNodes);
				});
         }
       });
}

// 得到父页面 属性 

api.button({
	 name: '同意',
     callback: auth,
     focus: true
},
{
    name: '取消',
}); 
//callback方法 
 function auth(){
	 var ids="";var map={};
	 W.$("input[name='ids']:checked").each(function(){
		 	//ids.push($(this).val());
		       	 ids += $(this).val()+",";
		        //获取选中节点的值
	 });
	 
 	if(ids.length==0){
		  $.MsgBox.Alert("温馨提示", "未选则用户!");
		 return ;
	 }
 	
 	if(submitan().length==0){
		  $.MsgBox.Alert("温馨提示", "未选中角色信息!");
 		 return ;
 	}
	 
	 if(ids.length>0 && submitan().length>0){
     	ids=ids.substring(0,ids.length-1); 
     	 //alert(	ids ); alert(	submitan() );
		 map.id=ids;
		 map.roleIds=submitan(); 
		   $.ajax({
		         url : "role-authRoles-save.json",
		         dataType: "json",
		         async: false,
		         data:{
		       	  models : JSON.stringify(map)
		         },
		         type:"POST",
		         success: function(result) { if(result==null)   return; 
		         }
		  }); 
     }
 }
 function submitan(){
        var treeObj=$.fn.zTree.getZTreeObj("treeDemo"),
        //nodes=treeObj.getCheckedNodes(true),
           nodes=treeObj.getCheckedNodes(true);
        v="";
        for(var i=0;i<nodes.length;i++){
       	 v+=nodes[i].id + ",";
        //获取选中节点的值
        }
        if(v.length>0){
        	v=v.substring(0,v.length-1); 
        }
        //W.document.getElementById('xx').value = v;//给父页面id 传值 
       // alert(v);
        return v;
 }