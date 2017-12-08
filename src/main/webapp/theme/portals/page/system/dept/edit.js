$(function() {
		$("#back").click( function(){
			 flag_S='Y';
			 $("#"+menusId).click();
		});
});
	
	
		$.formValidator.initConfig({  submitButtonID:"next",debug:true,submitOnce:false, 
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
						 }else if(message == 'SUCCESS') {
							 $.MsgBox.Alert("温馨提示", "数据保存成功!!!");
							 flag_S='Y';
		        			 $("#"+menusId).click(); 
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
		
		
	   $("#deptIndex").formValidator({empty:true,onShow:"输入正整数",onFocus:"输入正整数0-6位",onCorrect:"",onEmpty:""})
	         .inputValidator({min:0,max:6,empty:{leftEmpty:false,rightEmpty:false,emptyError:"不能有空符号"},onError:"长度为0到6位"})
	         .regexValidator({regExp: "intege1", dataType: "enum", onError: "请输入部门排序编号" });
	   $("#deptName").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入机构名称简称" });
	   $("#dept_fullname").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入机构名称全称" });
	   $("#dept_zzjgdm").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入机构代码" });
	   $("#deptDesc").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入机构描述信息" });
	   $("#remark").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入备注信息" }); 
		
	   
	   
	   //发布
		function publishS (id){
			 $.dialog({
					lock: true,
					max: false,
				    min: false,
					id : "deptTree1",
					title : "上级部门选择",
					width : winWidth/2,
					height : winHeight/2,
					content : "url:"+linktodeptlisttreeS
					//content : "url:role-list"
					
				}).show(); 
		}	
	