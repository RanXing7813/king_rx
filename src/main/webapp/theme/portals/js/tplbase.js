// JavaScript Document
$(function(){
		$(".discolor tr:odd").addClass("tr-odd-bg"); //先排除第一行,然后给奇数行添加样式
		$(".discolor tr:even").addClass("tr-even-bg"); //先排除第一行,然后给偶数行添加样式
	})


//选卡
function showCard(cardid, infoid, clsName){
    var cardList = cardid.parentNode.getElementsByTagName("li");
    for (i = 0; i < cardList.length; i++) {
        if (cardid == cardList[i]) {
            cardList[i].className = clsName + "-on";
            document.getElementById(infoid + i).style.display = "block";
        }
        else {
            cardList[i].className = clsName + "-off";
            document.getElementById(infoid + i).style.display = "none";
        }
    }
}
//折叠表格
function slidefolde(obj,layer){
				if($(layer).css("display")=="block"){
	//	$("#obj1").attr("src","../themes_blue/images/icons/panel_display.png");		
		}
	else{
		$(layer).css("display")=="none";
	//	$("#obj1").attr("src","../themes_blue/images/icons/panel_hide.png");
		}
		$(layer).slideToggle(500);//窗帘效果的切换,点一下收,点一下开,参数可以无,参数说
			}



function getMenuXX(menuid,url,name){
	var data = {} ;
	data.parentURL = menuid;
	
	$.ajax({
	    url : "getMainMenu.json",
	    dataType: "json", 
	    data:{models:JSON.stringify(data) ,  },
	    type:"POST",
	    async:false, 
	    success: function(result) {
	  	  var content = "<ul id='main-nav'>";
	  	  var obj = result.queryObj.menuValue;
  		  if(obj != null){
 			 $.each(obj,function(n,value){//select t.menu_name, t.parent_id,  t.menu_icon , t.menu_img ,t.request_url,t.menu_id 
 				 if(value.request_url!=null){// value.menu_name != "模板系统" && value.menu_name != "场景管理" && value.menu_name != "在线填报"
 					 content += " <li><a href='javascript:void(0)' name='ywmenuli_"+n+"' class='"+value.menu_icon+"' id='"+value.menu_id+"_' "+
		  				" onclick=tabList('"+value.parent_id+"','"+value.menu_id+"','"+name+value.menu_name+"','"+url+"') >"+
		  				"<img src='theme/portals/images/icon_mainnav01.png' /><span>"+value.menu_name+"</span></a>";
       
 				 }else{
 					 content += " <li id='ywmenuli_"+n+"'><a href='javascript:void(0)' class='"+value.menu_icon+"' id='ywmenuli_a_"+n+"' >"+
		  				"<img src='theme/portals/images/icon_mainnav01.png' /><span>"+value.menu_name+"</span></a>";
       
 				 }
 				  var menuList = value.thirdMenu;
	              if(menuList != null){
	            	  content += "<ul>";
	            	  $.each(menuList,function(n,value){
		            	  content += "<li><a href='javascript:void(0);' name='ywmenuliChild_"+n+"'  class='submenu-item'  id='"+value.menu_id+"_'"+
		              			" onclick=tabList('"+value.parent_id+"','"+value.menu_id+"','"+name+"&nbsp;-&nbsp;"+value.menu_name+"','"+url+"')"+
		    		 			">"+value.menu_name+"</a></li>";
		            	  
		              })
		              content += "</ul>";
	              }
	              
	              content +="</li>";
 			 })
 		  }
	  	  content += "</ul>";
	  	  $("#sidebar-wrapper").html(content);
	    }
	 })
}