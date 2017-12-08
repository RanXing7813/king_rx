	var map = {};
	//map.pageSize = $("#selectlimitnum").val();//每页多少条
	map.page = 1;//当前页
	map.name = '';
	var flag_S;//判断不进入 首页list  'Y'不进入首页不清空map信息,  'N'进入首页
	var menusId;
	function tabList(pId, id, name) {
		
		if(id==null||id==''){
			alert("正在建设中。。。");
		}else{
			menusId = id + "_";
			map.name = name;
			var url = "getBackPage?pId=" + pId + "&id=" + id;
			if (flag_S == 'Y') {
				//$("#tablelist").load(url);
				loadS(url);
			} else {
				map.page = 1;
				map.filter = {
					filters : []
				};
				map.sort= null; 
				//$("#tablelist").load(url);
				loadS(url);//重写的/theme/portals/publicS.js中的方法
			}
		}
	}
	
	$(document).ready(function(){
		/* $("a[name='ywmenuli_1']").click();
		  $("a[name='ywmenuli_1']") .addClass("current"); */
		  
		 /*  if($("#ywmenuli_1").text() == ""){
			  $("a[name='ywmenuli_1']").click();
			  $("a[name='ywmenuli_1']") .addClass("current");
		  }else{
			 $("#ywmenuli_a_1").click();
			 $("#ywmenuli_a_1").addClass("current");
			 $("a[name='ywmenuliChild_0']").click();
			 $("a[name='ywmenuliChild_0']").addClass("current");
		  } */
	}); 