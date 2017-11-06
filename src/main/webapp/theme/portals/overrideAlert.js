/**
 *  复写  Alert  Confirm
 * 
 */

 	(function () {
 					// $.MsgBox.Alert("温馨提示", "请先选中用户：您即将重置用户关联的部门信息 并且 重新分配 ！");	
 					// $.MsgBox.Confirm("温馨提示", "此操作将作废当前记录！你确定吗？", testconfirm2 ,url);
				  $.MsgBox = {
				    Alert: function (title, msg) {
				     // var time = 3000 ;
				      //timer = setTimeout("codefans()",3000);//3秒，可以改动
				      GenerateHtml("alert", title, msg);
				      btnOk(); //alert只是弹出消息，因此没必要用到回调函数callback
				      btnNo();
				     // timer_fn = setInterval(fn, 1000);
				    },
				    Confirm: function (title, msg, callback,url) {
				      GenerateHtml("confirm", title, msg);
				      btnOk(callback,url);
				      btnNo();
				    }
				  }
				 
				  //生成Html
				  var GenerateHtml = function (type, title, msg) {
				 
				    var _html = "";
				 
				    _html += '<div id="mb_box"></div><div id="mb_con"><span id="mb_tit">' + title + '</span>';
				    _html += '<a id="mb_ico">x</a><div id="mb_msg">' + msg + '</div><div id="mb_btnbox">';
				 
				    if (type == "alert") {
				      _html += '<input id="mb_btn_ok" type="button" value="确定" />';
				    }
				    if (type == "confirm") {
				      _html += '<input id="mb_btn_ok" type="button" value="确定" />';
				      _html += '<input id="mb_btn_no" type="button" value="取消" />';
				    }
				    _html += '</div></div>';
				 
				    //必须先将_html添加到body，再设置Css样式
				    $("body").append(_html); GenerateCss();
				  }
				 
				  //生成Css
				  var GenerateCss = function () {
				 
				    $("#mb_box").css({ width: '100%', height: '100%', zIndex: '99999', position: 'fixed',
				      filter: 'Alpha(opacity=60)', backgroundColor: '#a0a6c0', top: '0', left: '0', opacity: '0.6'
				    });
				 
				    $("#mb_con").css({ zIndex: '999999', width: '400px', position: 'fixed',
				      backgroundColor: 'White', borderRadius: '15px'
				    });
				 
				    $("#mb_tit").css({ display: 'block', fontSize: '14px', color: '#444', padding: '10px 15px',
				      backgroundColor: '#DDD', borderRadius: '15px 15px 0 0',
				      borderBottom: '3px solid #009BFE', fontWeight: 'bold'
				    });
				 
				    $("#mb_msg").css({ padding: '20px', lineHeight: '20px',
				      borderBottom: '1px dashed #DDD', fontSize: '13px'
				    });
				 
				    $("#mb_ico").css({ display: 'block', position: 'absolute', right: '10px', top: '9px',
				      border: '1px solid Gray', width: '18px', height: '18px', textAlign: 'center',
				      lineHeight: '16px', cursor: 'pointer', borderRadius: '12px', fontFamily: '微软雅黑'
				    });
				 
				    $("#mb_btnbox").css({ margin: '15px 0 10px 0', textAlign: 'center' });
				    $("#mb_btn_ok,#mb_btn_no").css({ width: '85px', height: '30px', color: 'white', border: 'none' });
				    $("#mb_btn_ok").css({ backgroundColor: '#168bbb' });
				    $("#mb_btn_no").css({ backgroundColor: 'gray', marginLeft: '20px' });
				 
				 
				    //右上角关闭按钮hover样式
				    $("#mb_ico").hover(function () {
				      $(this).css({ backgroundColor: 'Red', color: 'White' });
				    }, function () {
				      $(this).css({ backgroundColor: '#DDD', color: 'black' });
				    });
				 
				    var _widht = document.documentElement.clientWidth; //屏幕宽
				    var _height = document.documentElement.clientHeight; //屏幕高
				 
				    var boxWidth = $("#mb_con").width();
				    var boxHeight = $("#mb_con").height();
				 
				    //让提示框居中
				    $("#mb_con").css({ top: (_height - boxHeight) / 2 + "px", left: (_widht - boxWidth) / 2 + "px" });
				  }
				 
				  //确定按钮事件
				  var btnOk = function (callback,url) {
				    $("#mb_btn_ok").click(function () {
				      $("#mb_box,#mb_con").remove();
				 	  
				      if (typeof (callback) == 'function') {
				        callback(url);
				      }else{
				    	 // clearInterval(timer);
					 	 // clearInterval(timer_fn);
					     // isTimeNum = 2 ;
				      }
				    });
				  }
				 
				  //取消按钮事件
				  var btnNo = function () {
				    $("#mb_btn_no,#mb_ico").click(function () {
				      $("#mb_box,#mb_con").remove();
				    });
				  }
				  
				  
				})();  
 	
 	
 	
 	
 				//提供一个模板删除
			 	function delData(url){
			 	   $.post(url,null,function(){ 
			 		  if(map.page==parseInt($("#lastInput").val()) && totalS == (1+(map.page-1)*parseInt($("#selectlimitnum").val())) && map.page>1){
				    		  map.page = map.page-1;
				    		  $("#searchButtons").click();
				    	 }else{
				    		  $("#searchButtons").click();
				    	 }
				      });   
				}
			 	
				//提供一个模板删除
			 	function delMessage(url){
			 		$.ajax({url:url, type:'Post', success: function(result) { if(result==null)   return;
			 	         var message = result.message ; 
						 if( typeof(message)!='undefined'&&  message !='' && message !='SUCCESS'  ){
							 $.MsgBox.Alert("温馨提示", message+"!!!");
							 //最后一页最后一条数据进行删除时    页数-1  ==>> eg: totalS = 21 == 1 + 20;  
							 if(map.page==parseInt($("#lastInput").val()) && totalS == (1+(map.page-1)*parseInt($("#selectlimitnum").val())) && map.page>1){
						   		  map.page = map.page-1;
						   	 }
						 }
						 flag_S='Y';
	        			 $("#"+menusId).click();
			 		 }
			 		});
				}
			 	
			 	
			 	//提供一个模板删除
			 	function testconfirm4(url){
			 		 //$.MsgBox.Alert("温馨提示", url);
			 		$.ajax({url:url, type:'Post', success: function(result) { if(result==null)   return;
			 	        $("#message").val(result.message);
			 	        var message = $("#message").val() ; 
						 if( typeof(message)!='undefined'&&  message !='' && message !='SUCCESS'  ){
							 window.location.href="codeList?tableName="+tableName;
						 }else{
							 window.location.href="codeList?tableName="+tableName;
						 }
			 		 }
			 		});
				}
			 	
			 	
			 	
			 	//点击确认
			 	function codefans(){
		        	  $("#mb_btn_ok").click();
		        	  //console.log("codefans_isTimeNum:"+isTimeNum);
		        }
			 	function changeNum(){
		        	  var box=document.getElementById("mb_con");
		        	  box.style.display="none"; 
		        	  $("#mb_btn_ok").click();
		        }
    		    var that = '';
    		   var  isTimeNum = 2 ;
		        var fn = function () {
		        	 // console.log("fn_isTimeNum:"+isTimeNum);
		        	if(isTimeNum>0){
		        		that=isTimeNum + '秒后关闭';
		        		$("#mb_tit").text(that);
		            }//
		            if(isTimeNum<=0){
		            	isTimeNum=2; 
		            	clearInterval(timer_fn);
		            	return;
		            }//
		            isTimeNum --;
		        };	
		        