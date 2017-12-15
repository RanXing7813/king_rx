var api = frameElement.api, W = api.opener;	 //api 内容页中调用窗口实例对象的接口  W加载窗口组件页面的window对象
function checkThis(id,name){
	W.$("#MH_ZYDEPTNAME").val(  name  );
	W.$("#MH_ZYDEPTID").val(  id  );
	W.$("#MH_ZYDEPTNAME").focus();
	api.close();
}
//
$("#publishDepts").click(function(){
	//alert($("#grid").find("input[name='publishDepts']").length);
	
	var ids = [];
	$("#grid :checked").each(function(){
		//console.log(this.value);
		ids.push(this.value);
	});
	//console.log(ids);
	if(ids.length>0){
		
		
		
	}
	
	
});
var map = {};
// 得到父页面 属性

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
// list数据
function go(page) {
	var map = W.mp;
	boxType = W.mp.boxType;  // 获取父页面设置的单选状态
	$.ajax({
		url : linktodept_tree,
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
						idKey: "id",
						pIdKey: "pId",
					},
					key: {
						name: "name"
					}
				}
			};
			var zNodes =  result.list;
//				[
//				{ id:1, pId:0, name:"can check 1", open:true},
//				{ id:11, pId:1, name:"can check 1-1", open:true},
//				{ id:111, pId:11, name:"can check 1-1-1"},
//				{ id:112, pId:11, name:"can check 1-1-2"},
//				{ id:12, pId:1, name:"can check 1-2", open:true},
//				{ id:121, pId:12, name:"can check 1-2-1"},
//				{ id:122, pId:12, name:"can check 1-2-2"},
//				{ id:2, pId:0, name:"can check 2", checked:true, open:true},
//				{ id:21, pId:2, name:"can check 2-1"},
//				{ id:22, pId:2, name:"can check 2-2", open:true},
//				{ id:221, pId:22, name:"can check 2-2-1", checked:true},
//				{ id:222, pId:22, name:"can check 2-2-2"},
//				{ id:23, pId:2, name:"can check 2-3"}
//			];
				//'[{"id":"sfgw","name":"广东省发展和改革委员会","pId":"0","dept_zzjgdm":"006939756","creator_id":null,"dept_name":"省发改委","dept_url":null,"remark":"省发改委","open":true,"checked":true}]';
				//'[{"id":"sfgw","name":"广东省发展和改革委员会","pId":"centerdb","dept_zzjgdm":"006939756","creator_id":null,"dept_name":"省发改委","dept_url":null,"remark":"省发改委","open":false,"checked":false},{"id":"srst","name":"广东省人力资源和社会保障厅","pId":"centerdb","dept_zzjgdm":"553612461","creator_id":null,"dept_name":"省人社厅","dept_url":null,"remark":"省人社厅","open":false,"checked":false},{"id":"sswt","name":"广东省商务厅","pId":"centerdb","dept_zzjgdm":"096927520","creator_id":null,"dept_name":"省商务厅","dept_url":null,"remark":"省商务厅","open":false,"checked":false},{"id":"sdsj","name":"广东省地方税务局","pId":"centerdb","dept_zzjgdm":"006941290","creator_id":null,"dept_name":"省地税局","dept_url":null,"remark":"省地税局","open":false,"checked":false},{"id":"stjj","name":"广东省统计局","pId":"centerdb","dept_zzjgdm":"00693981X","creator_id":null,"dept_name":"省统计局","dept_url":null,"remark":"省统计局","open":false,"checked":false},{"id":"sgongsj","name":"广东省工商行政管理局","pId":"centerdb","dept_zzjgdm":"00694001X","creator_id":null,"dept_name":"省工商局","dept_url":null,"remark":"省工商局","open":false,"checked":false},{"id":"szjj","name":"广东省质量技术监督局","pId":"centerdb","dept_zzjgdm":"006940298","creator_id":null,"dept_name":"省质监局","dept_url":null,"remark":"省质监局","open":false,"checked":false},{"id":"sgsj","name":"广东省国家税务局","pId":"centerdb","dept_zzjgdm":"006941354","creator_id":null,"dept_name":"省国税局","dept_url":null,"remark":"省国税局","open":false,"checked":false},{"id":"hgzsgdfs","name":"海关总署广东分署","pId":"centerdb","dept_zzjgdm":"006940693","creator_id":null,"dept_name":"广东海关","dept_url":"2","remark":"广东海关","open":false,"checked":false},{"id":"sgy","name":"广东省高级人民法院","pId":"centerdb","dept_zzjgdm":"006939713","creator_id":null,"dept_name":"省高院","dept_url":"1231","remark":"省高院","open":false,"checked":false},{"id":"sjct","name":"广东省监察厅","pId":"centerdb","dept_zzjgdm":"006940183","creator_id":null,"dept_name":"省监察厅","dept_url":"专业","remark":"省监察厅","open":false,"checked":false}]'
				//result.list;
			var code;
			function showCode(str) {
				if (!code)
					code = $("#code");
				code.empty();
				code.append("<li>" + str + "</li>");
			}
			if(boxType == 1){
				setting.check.chkStyle="radio";
				setting.check.radioType="all";
			} 
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		}
	});
}
var  MH_ZYDEPTNAME_ = "";
// callback方法
api.auths = function () {
	if ( submitan().length > 0) {
		map.id = $("#model_ids").val();
		map.deptIds = submitan();
		map.isPublic = 'Y';
		return map;
//		$.ajax({
//			url : linkpowerDeptTreeList,
//			async : false,
//			dataType : "json",
//			data : {
//				models : JSON.stringify(map)
//			},
//			type : "POST",
//			success : function(result) {
//				 flag='Y';
//				 var menusId =  W.menusId;
//				 W.$("#"+menusId).click();
//    			 
//    			 
//			}
//		});

	}

}

function submitan() {
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getCheckedNodes(true);
	console.log(nodes.length);
	v = "";
	MH_ZYDEPTNAME_="";
	for (var i = 0; i < nodes.length; i++) {
		v += nodes[i].id+ ",";
		// 获取选中节点的值
		MH_ZYDEPTNAME_ +=nodes[i].name+ ",";
	}
	if (v.length > 0) {
		v = v.substring(0, v.length - 1);
		MH_ZYDEPTNAME_ = MH_ZYDEPTNAME_.substring(0, MH_ZYDEPTNAME_.length - 1);
	}
	return v;
}
