
  function page(num){
		pageButten();
		map.page += num ;
		map.pageSize = $("#selectlimitnum").val();
		go(map.page);
		pageButten();
	}
	
	function pageButten(){
		var z = parseInt(parseInt($("#lastInput").val())>1?$("#lastInput").val():1);
		//console.log(z ); //alert(z+"/"+map.page);
		//console.log(map.page ); 
		if(map.page>=z){
			$(".ui-page-item.ui-page-next").addClass("ui-page-item-disable");
			$(".ui-page-item.ui-page-next").removeAttr("onclick");//.attr("onclick","page(1);");
		}else{
			$(".ui-page-item.ui-page-next").removeClass("ui-page-item-disable");
			$(".ui-page-item.ui-page-next").attr("onclick","page(1);");
		}
		if(map.page>1){
			$(".ui-page-item.ui-page-item-prev").removeClass("ui-page-item-disable");
			$(".ui-page-item.ui-page-item-prev").attr("onclick","page(-1);");
		}else{
			$(".ui-page-item.ui-page-item-prev").addClass("ui-page-item-disable");
			$(".ui-page-item.ui-page-item-prev").removeAttr("onclick");//.attr("onclick","page(1);");
		}
		$("#page").val(map.page);
	}
	//跳转 
	function turnTo(){
		var pageStr = $("#page").val();
		if(pageStr != ""){
			var nowPage = parseInt(pageStr);
				map.page = nowPage;
		}else{
			map.page = 1;
		}	
		$("#page").val("");
		page(0);
	}
	 $(document).ready(function(){
//     	 pageButten();
//     	 //首页
		 $("#frist").click(function(){
 				map.page=0;
  					page(1);
  					return;
  			})
  			//尾页 
		 $("#last").click(function(){
			map.page=0;
			if(parseInt($("#lastInput").val())>0){
				page(parseInt($("#lastInput").val()));
			}
			return;
		})
		 $("#page").keyup(function(){
			var val = $(this).val();  
            if(val.length ==1){  
                $('#page').val(val.replace(/[^1-9]/g,''));  
            }else{          // console.log(val); 
                $('#page').val(val.replace(/\D/g,''));  
            }  
            if(parseInt($('#page').val())>parseInt($("#lastInput").val())){
            	$('#page').val($("#lastInput").val());
            }
            
		});    	  

	 })

var	totalS =0;	 //删除时进行计数时用
function paging_map(result, map, num){
	//点击选中行 
	 if(num){//多选
		 $("#grid tr").click(function(){
			 //$("#grid tr").css("background","");
			 if($(this).css("background-color")=="rgb(51, 187, 238)"){
				 $(this).css("background-color","");
				 $(this).find("input[type='checkbox']").prop("checked","");
			 }else{
				 $(this).css("background-color","#33bbee");
				 $(this).find("input[type='checkbox']").prop("checked","checked");
			 }
		 });
		 
	 }else{//单选
		 $("#grid tr").click(function(){
			 $("#grid tr").css("background","");
			 $(this).css("background","#33bbee");
		 });
	 }
	
	 
	//加载分页信息
	var z = result.total%map.pageSize;
	totalS = result.total ;
	var pageNum;
	
	$(".discolor tr:odd").addClass("tr-odd-bg"); //先排除第一行,然后给奇数行添加样式
	$(".discolor tr:even").addClass("tr-even-bg"); //先排除第一行,然后给偶数行添加样式
	if(z==0){
		pageNum=result.total/map.pageSize;
	}else{
		pageNum=Math.floor(result.total/map.pageSize)+1;
	}
	
	$("#lastInput").val(pageNum);
	$("#pageSpan").html("共"+result.total+"条记录，共"+map.page+"/"+pageNum+"页，第"+map.page+"页");
	pageButten();
	
}
	 
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