$(function() {
		$("#back").click( function(){
			 $("#ztreeContent").html("");
//			 flag_S='Y';
//			 $("#"+menusId).click();
		});		
		
		if($("#id").val().length==0) {
			 //新增
			$("#parentId").val(thistreeNode.id);
		 }
});
	
	
		$.formValidator.initConfig({  submitButtonID:"next",debug:true,submitOnce:true, 
			 onSuccess:  function() {
			/**
			 * 短暂提示
			 * @param	{String}	提示内容
			 * @param   {Number}    显示时间 (默认1.5秒)
			 * @param	{String}	提示图标 (注意要加扩展名)
			 * @param   {Function}  提示关闭时执行的回调函数
			 */
			 $.dialog.tips('数据加载中...',10 ,'loading.gif');
			 errorS(10000);//连接超时提醒
		      	 $.post(linksaveS,$("#deptForm").serialize(),function(result){
		      			 clearInterval(timerS);//成功清除连接超时
						 if(result==null)   return;
						 $.dialog.tips('',0,false);
			 	         var message = result.message ; 
						 if( typeof(message)!='undefined'&&  message !='' && message !='SUCCESS'  ){
							 $.MsgBox.Alert("温馨提示", message+"!!!");
							 $("#next").removeAttr("disabled");  
							 $("#back").removeAttr("disabled");
						 }else if(message == 'SUCCESS' && $("#id").val().length==0) {
							 //新增
							 $.MsgBox.Alert("温馨提示", "数据保存成功!!!");
							 
							 var zTree = $.fn.zTree.getZTreeObj("treeDemo");
								zTree.addNodes(thistreeNode, {
									id:result.orderStr, pId:thistreeNode.id, name: $("#dept_fullname").val()  
								});
						 }else if($("#id").val().length>0) {
							 //新增
							 $.MsgBox.Alert("温馨提示", "数据保存成功!!!");
							 var zTree = $.fn.zTree.getZTreeObj("treeDemo");
							    nodes = zTree.getNodesByParam("id", thistreeNode.id, null);
							    nodes[0].name=    $("input[name='dept_fullname']").val();
								zTree.updateNode(nodes[0]);
								 $("#ztreeContent").html("");
						 }
					}); 
		      	 
	           }
		});
	//formValidator
		//onFocus:"获取焦点时提示" 
		//onShow:"初始化页面提示信息"
		//onCorrect:"验证正确后提示"
		//onEmpty:"获取焦点后为空提示"
	//regexValidator
		//onError:"验证错误时提示"
	   $("#remark").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入备注信息" }); 
	   $("#deptName").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入机构名称简称" });
	   $("#deptIndex").formValidator({empty:true,onShow:"",onFocus:"输入正整数0-6位",onCorrect:"",onEmpty:""})
	         .inputValidator({min:0,max:6,empty:{leftEmpty:false,rightEmpty:false,emptyError:"不能有空符号"},onError:"长度为0到6位数字"})
	         .regexValidator({regExp: "intege1", dataType: "enum", onError: "请输入部门排序编号" });
	   $("#dept_fullname").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入机构名称全称" });
	 //  $("#dept_zzjgdm").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入机构代码" });
	   $("#deptDesc").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入机构描述信息" });
		
	   
	   
	   //发布
	   var mp = {};
	    mp.id = "";	//
	    mp.boxType = 1 ; // 单选
		function publishS (id){
			 $.dialog({
					lock: true,
					max: false,
				    min: false,
					id : "deptTree1",
					title : "上级部门选择",
					width : winWidth/2,
					height : winHeight/2,
					content : "url:"+linktodeptlisttreeS,
					 button: [
					        {
					            name: '确定',
					            callback: function(){
					            	var mp = this.auths();
//					            	console.log(mp.deptId);
//					            	mp.deptId = v ;
//					            	mp.name = n ;
//					            	mp.isPublic = 'Y';
					            	mp.id = userIds;
					            	$("#parentId").val(mp.deptId);
					            	return false;
					               
					            },
					            focus: true
					        },
					        {
					            name: '关闭'
					        }
					    ]
					//content : "url:role-list"
					
				}).show(); 
		}	
	