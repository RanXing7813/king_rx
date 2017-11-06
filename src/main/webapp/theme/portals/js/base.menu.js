// JavaScript Document
$(document).ready(function(){
	$("#main-nav li a.no-submenu").click( // When a menu item with no sub menu is clicked...
			function () {
				window.location.href=(this.href); // Just open the link instead of a sub menu
				$("#main-nav li a.no-submenu").removeClass("current");
				$("#main-nav li a").removeClass("current");
				alert("2");
				$("#main-nav li a.current").removeClass("current");
				alert("2");
				$("#main-nav li ul li a.current").removeClass("current");
				$(this).addClass("current");
				alert("2");
				//mainframe.location.href=(this.href);
				return false;
			}
		);
	$("#main-nav li a").click( // When a menu item with no sub menu is clicked...
			function () {
				//window.location.href=(this.href); // Just open the link instead of a sub menu
				$("#main-nav li a.no-submenu").removeClass("current");
				$("#main-nav li a").removeClass("current");
				$("#main-nav li ul li a.current").removeClass("current");
				$(this).addClass("current");
				alert("3");
			}
		); 
	 
		
		 $("#main-nav li ul li .no-thirdmenu").click(// aileen add 鼠标点击三级级菜单项
			function(){
				//$("#main-nav li ul li a.current").removeClass("current");
				
				//$("#main-nav li ul li a").removeClass("current");
				//$("#main-nav li a").removeClass("current");
				$(this).addClass("current");
				alert("4");
			  //  mainframe.location.href=(this.href);
				return false;
				}	
		 );
	

      	$("#main-nav li ul li a").click(// aileen add 鼠标点击高亮二级菜单项
			function(){
				//$("#main-nav li ul li a.current").removeClass("current");
				
				
				$(this).addClass("current");
				
				$(this).parent().parent().parent().children("a").addClass("current");
			$(this).parent().parent().parent().parent().parent().children("a").addClass("current");
			alert("5");
				}	
		 );
		
});



