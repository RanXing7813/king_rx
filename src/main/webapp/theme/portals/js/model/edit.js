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
	$.formValidator.initConfig({submitButtonID:"next",debug:false,submitOnce:false,
        onSuccess: function() {
        		   var  url = "saveModel.json";
        		   $.post(url, $("#form-wizard").serialize(),
		 					function(data) {
		        			     var message = data.message ; 
			        			 if( typeof(message)!='undefined' &&  message !='' && message !='SUCCESS'  ){
			        				 $.MsgBox.Alert("温馨提示", message);
								 }else{
									 flag='Y';
				        			 $("#"+menusId).click();
								 }
		 					}); 
           }
	});
    $("#MH_ZYNAME").formValidator({onShow:"",onFocus:"" ,onCorrect:""}).inputValidator({min:2,max:50,onError:"长度为2至50个字符,不能有空格"}).regexValidator({regExp: "notempty", dataType: "enum", onError: "长度为2至50个字符,不能有空格" });
    $("#MH_ZYDEPTNAME").formValidator({onShow:"",onFocus:"" ,onCorrect:""}).inputValidator({min:2,max:50,onError:"长度为2至50个字符,不能有空格"}).regexValidator({regExp: "notempty", dataType: "enum", onError: "长度为2至50个字符,不能有空格" });
    $("#MH_XXTABLECNAME").formValidator({onShow:"",onFocus:"" ,onCorrect:""}).inputValidator({min:2,max:50,onError:"长度为2至50个字符,不能有空格"}).regexValidator({regExp: "notempty", dataType: "enum", onError: "长度为2至50个字符,不能有空格" });
	//返回
	$("#back").click(function(){
		 flag='Y';
		 $("#"+menusId).click();
	});
	
	$("#columnSys").click(function(){
		$.dialog({
			lock: true,
			id : "columnSys",
			title : "信息类编目表设置",
			content : "url:getColumnSys?tableName="+$("#MH_XXTABLENAME").val(),
			max: false,
		    min: false,
			width : 920,
			height : 500,
		      //fitColumns:true,适应父容器的大小  
		});
	});
	
	$("#exportModelTitle").click(function(){
		//条件 
		var models={
				tableName : $("#MH_XXTABLENAME").val(),
				zyName : $("#MH_ZYNAME").val(),
			};
//		   $.ajax({
//		          url : "yw_getChangeList.json",
//		          dataType: "json", 
//		          data:{
//		        	  models : JSON.stringify(map)
//		          },
//		          type:"POST",
//		          success : function(result) {//返回数据根据结果进行相应的处理    
		                var form=$("<form>");//定义一个form表单  
		                form.attr("style","display:none");  
		                form.attr("target","");  
		                form.attr("method","post");  
		                models = JSON.stringify(models);
		                form.attr("action","modelExptTitile?models="+encodeURIComponent(models));  //encodeURIComponent(models)
		                var input1=$("<input>");  
		                input1.attr("type","hidden");  
		                input1.attr("name","exportData");  
		                input1.attr("value",models);  
		                $("body").append(form);//将表单放置在web中  
		                form.append(input1);  
		                form.submit();//表单提交   
//		            }    ,
//		          error: function(result) {
//		          }
//		        });

		
	});
	$("#MH_ZYDEPTNAME").click(function(){
		$.dialog({
			lock: true,
			id : "deptList",
			title : "部门列表",
			width : (winWidth-100)>800?winWidth/2: minWidth,
			height : (winHeight-100)>800?winHeight-100:minHeight,
			content : "url:deptList",
		});
	});
	$("#MH_XXTABLECNAME").click(function(){
		$.dialog({
			lock: true,
			id : "modelTableList",
			title : "信息类编目表名",
			width : (winWidth-100)>800?winWidth/2: minWidth,
					height : (winHeight-100)>800?winHeight-100:minHeight,
			content : "url:modelTableList",
		});
	});
	