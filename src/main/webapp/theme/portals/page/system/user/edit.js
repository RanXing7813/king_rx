$(function(){
	$("#back").click( function(){
		 flag_S='Y';
		 $("#"+menusId).click();
	});
		
		$.formValidator.initConfig({submitButtonID:"next",debug:false,submitOnce:true,
	        onSuccess: function() {
	        	   if(flag){
	        		   $("#password").val(dto_.password);
	        		   $("#password2").val(dto_.password);
	        	   }
	      	  $.post(linksaveS,$("#userForm").serialize(),function(data){
							flag_S='Y';
							 $("#"+menusId).click();
				}); 
	           }
		});
		 $("#phoneNum").formValidator({empty:false,onShow:"",onFocus:"",onEmpty:"",onCorrect:""})
     		.inputValidator({min:11,max:11,onError:"请输入11位合法手机号码"})
     		.regexValidator({regExp:"mobile",dataType:"enum",onError:"请输入11位合法手机号码"});
		 $("#email").formValidator({empty:false,onShow:"",onFocus:"",onEmpty:"",onCorrect:""})
     		.regexValidator({regExp:"email",dataType:"enum",onError:"请输入正确的邮箱地址"}); 
		 //				                .inputValidator({min:6,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"不能为空,请确认"})

		 
		 if(dto_.id==null){
			 $("#loginName").formValidator({onShow:"",onFocus:"",onCorrect:""})
               .regexValidator({regExp:"loginName",dataType:"enum",onError:"请输入以字母开头,由数字、26个英文字母或者下划线组成的字符串,长度6-15位"})
               .ajaxValidator({
                   url : linkcheckS,
                   type : "post",
                   dataType : "json",
                   //async : true,
                   success : function(data){
                       if( data == 0){
                         return true;
                       }else{
                         return false;
                       } 
                   },
                   buttons: $("#button"),
                   error: function(jqXHR, textStatus, errorThrown){alert("服务器没有返回数据，可能服务器忙，请重试"+errorThrown);},
                   onError : "此用户名已被注册，请更换用户名",
               //    onWait : "正在对用户名进行合法性校验，请稍候..."
               });
		 }else{
			 $("#loginName").attr("readonly","readonly");//readonly="readonly"
		 }
		  
		                
		  $("#userName").formValidator({onShow:"",onFocus:"请输入中文,至少2个长度",onCorrect:""})
         			    .inputValidator({min:2,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"请输入中文,至少2个长度,请确认"})
		                .regexValidator({regExp:"username",dataType:"enum",onError:"请输入中文,长度2-10位"})

		    if(!flag){
		    	  $("#password").formValidator({onShow:"",onFocus:"",onCorrect:""})
	                .inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"请输入以字母开头,由数字、26个英文字母或者下划线组成的字符串,长度6-15位"})
	                .regexValidator({regExp:"loginName",dataType:"enum",onError:"请输入以字母开头,由数字、26个英文字母或者下划线组成的字符串,长度6-15位"});

	              $("#password2").formValidator({onShow:"",onFocus:"",onCorrect:"密码一致"})
	                .inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"重复密码两边不能有空符号"},onError:"重复密码不能为空,请确认"})
	                .compareValidator({desID:"password",operateor:"=",onError:"2次密码不一致,请确认"});
		    }
		  
		if(checked_==null){
			$("input[type='radio'][value='1']").attr('checked','checked');
		}
});

	
//		$.formValidator.initConfig({  submitButtonID:"next",debug:true,submitOnce:false, 
//			 onSuccess:  function() {
//			/**
//			 * 短暂提示
//			 * @param	{String}	提示内容
//			 * @param   {Number}    显示时间 (默认1.5秒)
//			 * @param	{String}	提示图标 (注意要加扩展名)
//			 * @param   {Function}  提示关闭时执行的回调函数
//			 */
//			 $.dialog.tips('数据加载中...',10 ,'loading.gif');
//			 errorS(10000);//连接超时提醒
//			 
//		      	 $.post(linksaveS,$("#deptForm").serialize(),function(result){
//		      			 clearInterval(timerS);//成功清除连接超时
//						 if(result==null)   return;
//						 $.dialog.tips('',0,false);
//			 	         var message = result.message ; 
//						 if( typeof(message)!='undefined'&&  message !='' && message !='SUCCESS'  ){
//							 $.MsgBox.Alert("温馨提示", message+"!!!");
//							 $("#next").removeAttr("disabled");  
//							 $("#back").removeAttr("disabled");
//						 }else if(message == 'SUCCESS') {
//							 $.MsgBox.Alert("温馨提示", "数据保存成功!!!");
//							 flag_S='Y';
//		        			 $("#"+menusId).click(); 
//						 }
//					}); 
//		      	 
//	           }
//		});
	//formValidator
		//onFocus:"获取焦点时提示" 
		//onShow:"初始化页面提示信息"
		//onCorrect:"验证正确后提示"
		//onEmpty:"获取焦点后为空提示"
	//regexValidator
		//onError:"验证错误时提示"
		
		
//	   $("#deptIndex").formValidator({empty:true,onShow:"输入正整数",onFocus:"输入正整数0-6位",onCorrect:"",onEmpty:""})
//	         .inputValidator({min:0,max:6,empty:{leftEmpty:false,rightEmpty:false,emptyError:"不能有空符号"},onError:"长度为0到6位"})
//	         .regexValidator({regExp: "intege1", dataType: "enum", onError: "请输入部门排序编号" });
//	   $("#deptName").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入机构名称简称" });
//	   $("#dept_fullname").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入机构名称全称" });
//	   $("#dept_zzjgdm").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入机构代码" });
//	   $("#deptDesc").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入机构描述信息" });
//	   $("#remark").formValidator({empty:false,onShow:"",onFocus:"",onCorrect:"",onEmpty:""}).regexValidator({ regExp: "notempty", dataType: "enum", onError: "请输入备注信息" }); 
		
	   

	   $("#password").val(dto_.password);
	   $("#password2").val(dto_.password);
	   var flag = false;
	   var password_ = dto_.password;
	   if(dto_.password!=null && dto_.password.length > 16){
	   	flag=true;
	   }
	   $("#password,#password2").change(function(){
	   	flag = false;
	   	  $("#password").formValidator({onShow:"",onFocus:"",onCorrect:""})
	         .inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"两边不能有空符号"},onError:"请输入以字母开头,由数字、26个英文字母或者下划线组成的字符串,长度6-15位"})
	         .regexValidator({regExp:"loginName",dataType:"enum",onError:"请输入以字母开头,由数字、26个英文字母或者下划线组成的字符串,长度6-15位"});

	       $("#password2").formValidator({onShow:"",onFocus:"",onCorrect:"密码一致"})
	         .inputValidator({min:1,empty:{leftEmpty:false,rightEmpty:false,emptyError:"重复密码两边不能有空符号"},onError:"重复密码不能为空,请确认"})
	         .compareValidator({desID:"password",operateor:"=",onError:"2次密码不一致,请确认"});
	   });
	   //发布
		function publishS (id){
			 $.dialog({
					lock: true,
					max: false,
				    min: false,
					id : "deptTree1",
					title : "机构部门选择",
					width : winWidth/2,
					height : winHeight/2,
					content : "url:"+linktodeptlisttreeS
					//content : "url:role-list"
					
				}).show(); 
		}	

	