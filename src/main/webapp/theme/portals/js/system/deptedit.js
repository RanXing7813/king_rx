$(function() {
		$("#back").click( function(){
			 flag_S='Y';
			 $("#"+menusId).click();
		});
});
	
	
		$.formValidator.initConfig({  submitButtonID:"next",debug:false,submitOnce:true, 
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
		
		
		
	 /*   $("#deptName").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入机构名称简称" });
	   $("#dept_fullname").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入机构名称全称" });
	   $("#dept_zzjgdm").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入机构代码" });
	   $("#deptDesc").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入机构描述信息" });
	   $("#remark").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入备注信息" }); */
	
	