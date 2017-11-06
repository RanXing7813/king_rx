	$("#exportModelTitle").click(function(){
		//条件 
		var models={
				tableName : $("#MH_XXTABLENAME").val(),
				zyName : $("#MH_ZYNAME").val(),
			};
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
	});
	//返回
	$("#back").click(function(){
		 flag='Y';
		 $("#"+menusId).click();
	});