

/**
 * 局部加载页面
 * @param href  
 * @param id		
 * @param type	
 * @returns
 */
function loadS(href, divId, type, status) {
		//console.log("id|type1:"+id+"|"+type);
		divId=divId?divId:"tablelist";
		type=type?type:"GET";
		//console.log("id|type2:"+id+"|"+type);
		$.ajax({//按钮栏显示
            url:href,
            dataType:"html",
            type:type,
            success:function(data){
                $("#"+divId).html(data);
               // $("#bodys").css({"height":"50px","background":"url('images/index/search_bg.png')"})
                if(status){
                	$("#"+divId+" input,checkbox,select,textarea").attr("readonly","readonly");
                }
                
                
            }
        });
}

//var linkgoS = /*[[@{/sys/DeptController!getList.json}]]*/   ;		//使用默认查询方法go(map.page)时参数必须
//var linkdelS 	 = /*[[@{/sys/DeptController!remove.json}]]*/	 ;	//使用默认删除方法时参数必须
//var linkaddS  = /*[[@{/sys/DeptController!to_add}]]*/		 ;	 	//使用默认新增方法onclick="addS(null,'POST')"时参数必须 
//var linkeditS    = /*[[@{/sys/DeptController!to_add}]]*/		 ;	//使用默认新增方法onclick="addS(null,'POST')"时参数必须 

/**
 * 新增
 * @param type  post or get
 * @param divId   $("#"+id).html(data);
 * @returns
 */
function addS(divId,type){
	loadS(linkaddS, divId, type);
}
/**
 * 编辑
 * @param id  数据id
 * @param type  post or get
 * @param divId   $("#"+id).html(data);
 * @returns
 */
function editS(id, type, divId){
	if(!type || type == null ){
		type = "POST";
	}
 	loadS(linkeditS+"?id="+id, divId, type);
}
/**
 * 删除
 * @param id  数据id
 */
function delS(id) {
	 if(linkdelS){
		 $.MsgBox.Confirm("温馨提示", "此操作将作废当前记录！你确定吗？", delMessage ,linkdelS+"?id="+id);	  
	 }
}

/**
 * 异常提示
 * @param num
 * @returns
 */

var timerS
function errorS(time_){
	timerS = 
    	setTimeout("codefanS()", time_); 
}
function clearErrorS(){
	clearInterval(timerS);
} 

function codefanS(num) {
	switch (num){
	case 1 :
		$.MsgBox.Alert("温馨提示", "请求失败!!!");
		break;
	case 2 :
		$.MsgBox.Alert("温馨提示", "请求异常!!!");
		break;
    default:
    	$.MsgBox.Alert("温馨提示", "连接异常，请确认登陆，并重新连接，如果仍发现此提示请联系管理员!!!");
		break;
	}
}

function Null2String(obj){
	if(obj==null || obj == undefined){
		obj = "";
	}
	return obj;
}