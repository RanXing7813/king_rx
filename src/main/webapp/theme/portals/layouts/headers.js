var storage = window.localStorage;
			if($("#LogindeptId").val() != "" &&typeof ($("#LogindeptId").val()) != "undefined"){
				$("#loginstatus").val("1");//登录过
				//登录后样式
				var span1='<li class="openlogin"><a href="#" style="width:150px;color:#ffffff;">'+$("#LogindeptName").val()+'，您好！</a></li>';
				span1=span1+'<li ><a href="#"  onclick = "logout();"  class="topnav-login" >退出登录</a></li>';
				$("#loginuserli").html(span1);
			}else{
				if($("#loginstatus").val()==0){
					//未登录过
					$("#loginuserli").html('<li><a href="javascript:login_new();"  class="topnav-login">登录</a></li>');
				}
			}
			
			
			function showmenus() {
				//根据权限获取菜单
				var data = {} ;
				data.parentURL = "mainnav";
				
			   $.ajax({
			          url : link1, //getMainMenu
			          dataType: "json", 
			          data:{
			        	models:JSON.stringify(data) ,  
			          },
			          type:"POST",
			          async:false,
			          success: function(result) { if(result==null)   return;
			        	  var content = "<ul>";
			        	  var obj = result.queryObj.mainmenu;
			  	 		  $.each(obj,function(n,value){
			  	 			  content += "<li><a href='"+value.request_url+"' class='"+value.menu_icon+"' id='"+value.menu_icon+"'>"+value.menu_name+"</a></li>"
			  	 		  })
			  	 		  content += "</ul>";
			  	 		  $("#div_menu").html(content);
			  	 		  var menus = $("#menuinit").val();
			  	 		  switch (menus) {
			  	 		  case 'dataService_Index' :
							$("#mainnav02").attr("class","mainnav02-on");loadS(menus, "bodys");
						 	break ; 
						  case 'serviceMenu_Index' :
							$("#mainnav03").attr("class","mainnav03-on");loadS(menus, "bodys");
						 	break ; 
						  case 'projectMenu_Index' :
							$("#mainnav04").attr("class","mainnav04-on");loadS(menus, "bodys");
						 	break ; 
						  case 'runningMonitor_Index' :
							$("#mainnav05").attr("class","mainnav05-on");loadS(menus, "bodys");
						 	break ; 
						  case 'system_Index' :
							$("#mainnav06").attr("class","mainnav06-on");loadS(menus, "bodys");
						 	break ; 
						  case 'modelSystem_Index' :
							  $("#mainnav03").attr("class","mainnav03-on");loadS(menus, "bodys");
							  break ; 
						  default :
							//$("#mainnav01").attr("class","mainnav01-on");load(menus); // 首页 
							  login_new();
							break;
						}
			          }
			       })
			}

