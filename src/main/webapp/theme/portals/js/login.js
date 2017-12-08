//登录窗口
var api = null;
function login_new(method) {
	method = $("#method").val();
	var url = 'url:login_new';
	if (method == null || typeof (method) == "undefined") {
		url = "url:login_new";
	} else {
		url = "url:login_new?method="+method;
	}
	//console.log(method);
	api = $.dialog({
		id : 'LHG1976D',
		title : '欢迎登录广东信息中心运维管理平台',
		width : '400px',
		height : '300px',
		content : url,
		max : false,
		min : false,
		drag : false,
		resize : false,
		lock : true
	});
}

// 登录
function dengluAjax() {
	var nameStr = $("#username").val();
	var pasStr = $("#password").val();
	var codeStr = $("#vcode").val();
	// 显示基础信息
	$.ajax({
		url : "loginNew",
		dataType : "json",
		data : {
			"username" : nameStr,
			"password" : pasStr,
			"vcode" : codeStr,
			"method" : $("#method").val()
		},
		type : "POST",
		success : function(result) {
			
			console.log( result.changeIndex);

			if (result.errorMessage != '') {
				$("#errorMessage_span").html(result.errorMessage);
			} else if(  result.changeIndex!=  undefined && result.changeIndex!="undefined" && result.changeIndex != '' ){
				// 关闭登录窗口 并且调整到 指定页面
				$.dialog({
					id : 'createAlbum'
				}).close();
				//console.log(result.changeIndex+"1");
				window.parent.location.href = link+result.changeIndex;
			
			} else {
				var method = $("#method").val();
				if (method == '' || method == null || typeof (method) == "undefined") {
					//frameElement.api.reload(frameElement.lhgDG);
					alert(link+"systemIndex");
					$.dialog({
						id : 'createAlbum'
					}).close();
					window.parent.location.href = link+"systemIndex";
				} else {
					// 关闭登录窗口 并且调整到 指定页面
					$.dialog({
						id : 'createAlbum'
					}).close();
					window.parent.location.href = link+method;
					
				}
			}
		},
		error : function(result) {
		}
	});
}

//退出
function logout(){
	/*$.dialog.confirm('您确定要退出吗？', function(){
		window.parent.location.href = "logout";
	}, function(){
	});*/
	$.dialog({
		id : 'testID',
		title:'确认退出',
	    content: '您确定要退出吗？',
	    //ok: function(){
	    //	window.parent.location.href = "logout";
	    //},
	    //cancelVal: '取消',
	    cancel: false ,/*为true等价于function(){}*/
		width : '200px',
		height : '80px',
		max : false,
		min : false,
		drag : false,
		resize : false,
		lock : true,
	    button: [
					{
					    name: '确定',
					    callback: function(){
					        window.parent.location.href='logout';
					    }
					},
			        {
			            name: '取消'
			        }
			    ]
	});
}
function menu(){
	window.parent.location.href = "/nodeMonitorIndex";
}