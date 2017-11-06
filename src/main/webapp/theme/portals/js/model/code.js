 // looks 
 function utilLooks(divId,loadUrl) {  //  var url = "yw_looksChange?id="+id+"&listType=A2" ;
	 load1(divId,loadUrl)
 }
 
 //  加载方法
 function load1(divId,loadUrl){
		$("#"+divId).load(loadUrl,function(result){
			var message = $("#message").val() ; 
			//console.log(result);
			//console.log("message:"+message);
			 if( typeof(message)!='undefined'&&  message !='' && message !='SUCCESS'  ){
				 $("#back").click();
				 $.MsgBox.Alert("温馨提示", message+"!!!");
			 }
		});
 }
 
	//  暂存    分组验证  1
	$.formValidator.initConfig({submitButtonID:"next",debug:true,submitOnce:true,
        onSuccess: function() {
        		   var  url = "saveCode.json";
        		   $.post(url, $("#form-wizard").serialize(),
		 					function(data) {
		        			     var message = data.message ; 
			        			 if( typeof(message)!='undefined' &&  message !='' && message !='SUCCESS'  ){
			        				 $.MsgBox.Alert("温馨提示", message);
								 }else{
									 window.location.href="codeList?tableName="+tableName;
								 }
		 					}); 
           }
	});
    $("#codeCode").formValidator({onShow:"请输入对应应写入数据库值",onFocus:"" ,onCorrect:""}).inputValidator({min:1,max:50,onError:"长度为1至50个字符,不能有空格"}).regexValidator({regExp: "notempty", dataType: "enum", onError: "长度为1至50个字符,不能有空格" });
    $("#codeName").formValidator({onShow:"请输入Excel对应下拉列表数据",onFocus:"" ,onCorrect:""}).inputValidator({min:1,max:50,onError:"长度为1至50个字符,不能有空格"}).regexValidator({regExp: "notempty", dataType: "enum", onError: "长度为1至50个字符,不能有空格" });
    $("#MH_XXTABLECNAME").formValidator({onShow:"",onFocus:"" ,onCorrect:""}).inputValidator({min:2,max:50,onError:"长度为1至6个字符,不能有空格"}).regexValidator({regExp: "notempty", dataType: "enum", onError: "长度为2至50个字符,不能有空格" });
	//返回
	$("#back").click(function(){
		window.location.href="codeList?tableName="+tableName;
	});