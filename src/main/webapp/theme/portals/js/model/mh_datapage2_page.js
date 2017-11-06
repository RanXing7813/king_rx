
  function page(num){
		pageButten();
		map1.page += num ;
		map1.pageSize = $("#selectlimitnum").val();
		go(map1.page);
		pageButten();
	}
	
	function pageButten(){
		var z = parseInt(parseInt($("#lastInput").val())>1?$("#lastInput").val():1);
		//console.log(z ); //alert(z+"/"+map1.page);
		//console.log(map1.page ); 
		if(map1.page>=z){
			$(".ui-page-item.ui-page-next").addClass("ui-page-item-disable");
			$(".ui-page-item.ui-page-next").removeAttr("onclick");//.attr("onclick","page(1);");
		}else{
			$(".ui-page-item.ui-page-next").removeClass("ui-page-item-disable");
			$(".ui-page-item.ui-page-next").attr("onclick","page(1);");
		}
		if(map1.page>1){
			$(".ui-page-item.ui-page-item-prev").removeClass("ui-page-item-disable");
			$(".ui-page-item.ui-page-item-prev").attr("onclick","page(-1);");
		}else{
			$(".ui-page-item.ui-page-item-prev").addClass("ui-page-item-disable");
			$(".ui-page-item.ui-page-item-prev").removeAttr("onclick");//.attr("onclick","page(1);");
		}
	}
	//跳转 
	function turnTo(){
		var pageStr = $("#page").val();
		if(pageStr != ""){
			var nowPage = parseInt(pageStr);
				map1.page = nowPage;
		}else{
			map1.page = 1;
		}	
		$("#page").val("");
		page(0);
	}
	 $(document).ready(function(){
     	 pageButten();
     	 
     	 $("#reset").click(function(){
     			$("#infolinkTitle").val('请输入要检索的内容');
     			$("#infolinkTitle").css("color","#999");
     	 })
     	 //首页
		 $("#frist").click(function(){
 				map1.page=0;
  					page(1);
  					return;
  			})
  			//尾页 
		 $("#last").click(function(){
			map1.page=0;
			if(parseInt($("#lastInput").val())>0){
				page(parseInt($("#lastInput").val()));
			}
			return;
		})
		 $("#page").keyup(function(){
			var val = $(this).val();  
            if(val.length ==1){  
               // console.log(val); 
                $('#page').val(val.replace(/[^1-9]/g,''));  
            }else{          // console.log(val); 
                $('#page').val(val.replace(/\D/g,''));  
            }  
            if(parseInt($('#page').val())>parseInt($("#lastInput").val())){
            	$('#page').val($("#lastInput").val());
            }
            
		});    	  

	 })
	 
Date.prototype.format = function (fmt) {  
    var o = {  
        "M+": this.getMonth() + 1, //月份   
        "d+": this.getDate(), //日   
        "h+": this.getHours(), //小时   
        "m+": this.getMinutes(), //分   
        "s+": this.getSeconds(), //秒   
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度   
        "S": this.getMilliseconds() //毫秒   
    };  
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
    for (var k in o)  
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));  
    return fmt;  
}  