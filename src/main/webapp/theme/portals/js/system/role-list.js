if (map.page > 1) {
} else {
	map.page = 1;
}
$(document).ready(function() {
	go(map.page);// 角色list 
	$("#searchButtons").click(function() {
		///console.log(map.filter.filters[0]["value"]);
		map.filter = {
			logic : "and",
			filters : [ {
				field : "roleName",
				value : $("#roleName").val()
			}, {
				field : "roleDesc",
				value : $("#roleDesc").val()
			}, {
				field : "status",
				value : $("#status").val()
			} ]
		};
		map.page = 1;
		go(map.page);
	});
});
//查看目录 
function menu_(id, type) {
	$.dialog({
		lock : true,
		max : false,
		min : false,
		id : "jsdeptsnames",
		title : "角色授权",
		width : 500,
		height : 430,
		content : "url:"+linkauthS+"?id=" + id + "&type=" + type
	});
}
//设置编号
function numTR() {
	var i = 1;
	$("font[name='numTR']").each(function() {
		$(this).text(i);
		i++;
	})
}
//单选
function singerCheck(obj) {
	$(':checkbox[name=ids]').each(function() {
		$(this).click(function() {
			if ($(this).attr('checked')) {
				$(':checkbox[name=ids]').removeAttr('checked');
				$(this).attr('checked', 'checked');
			}
		});
	});
}
//背景变色 
function bgChange(obj) {
	var tr = document.getElementsByTagName("tr");
	for (var i = 0; i < tr.length; i++) {
		tr[i].style.backgroundColor = "";
	}
	obj.style.backgroundColor = "#00ccff";
}

function roleEdit(id) {
	var url = "role-edit?roleId=" + id;
	$("#tablelist").load(url);
}

$("#new").click(function() {
	//var url = "role-edit?status=1";
	//$("#tablelist").load(url);
	addS();
});


function delsss(id) {
	var begin = Date.now();
	confirm_ = confirm('此操作将删除当前记录！你确定吗？');
	var end = Date.now();
	if (end - begin < 10) {
		console.log('用户禁用了confirm弹窗');
		$.ajax({
			type : "POST",
			url : 'role-delete?id=' + id,
			success : function(msg) {
				$("#content").load("role-list");
			}
		});
	}
	if (confirm_) {
		$.ajax({
			type : "POST",
			url : 'role-delete?id=' + id,
			success : function(msg) {
				$("#content").load("role-list");
			}
		});
	}
}

//list数据 
function go(pages) {

	map.pageSize = $("#selectlimitnum").val();
	map.page = pages;
	map.sort = [ {
		field : "roleIndex",
		dir : "DESC"
	} ];
//	var filtersLength = map.filter.filters.length;
//	var statusFlag = true;
//	for (var n = 0; n < filtersLength; n++) {
//		if (map.filter.filters[n]["field"] == "status") {
//			statusFlag = false;
//		}
//	}
//	if (statusFlag) {
//		var obj = {
//			field : "status",
//			value : 1
//		//  
//		}
//		map.filter.filters.push(obj);
//	}
	$.ajax({
		url : linkRoleList,
		dataType : "json",
		data : {
			models : JSON.stringify(map)
		},
		type : "POST",
		success : function(result) {
			if (result == null)
				return;
			
			//@index 是从0开始计数,  定义一个方法addOne ,返回值是@index +1;
			Handlebars.registerHelper('addOne',function(index){
				return index+1;
			});
		   　Handlebars.registerHelper("compare",function(status){
		　　　　if(3==status){
		　　　　　　return "管理员角色";
		　　　　}else if(2==status){
		　　　　　　//不满足执行{{else}}部分
		　　　　　　return "运维角色";
		　　　　}else if(1==status){
		　　　　　　//不满足执行{{else}}部分
		　　　　　　return "门户角色";
		　　　　}
		　 　});
			var myTemplate = Handlebars.compile($("#grid-template").html());
			$('#grid').html(myTemplate(result));
			//点击选中行 
			$("#grid tr").click(
					function() {
						$(':checkbox[name=ids]').each(function() {
							$(':checkbox[name=ids]').removeAttr('checked');
						});
						$(this).find("input[type='checkbox']").attr('checked',
								'checked');
					});
			//加载分页信息
			var z = result.total % map.pageSize;
			var total = result.total;
			//console.log(total);
			var pageNum;

			$('tbody tr:even').addClass("alt-row");
			$(".discolor tr:odd").addClass("tr-odd-bg"); //先排除第一行,然后给奇数行添加样式
			$(".discolor tr:even").addClass("tr-even-bg"); //先排除第一行,然后给偶数行添加样式

			if (z == 0) {
				pageNum = result.total / map.pageSize;
			} else {
				pageNum = Math.floor(result.total / map.pageSize) + 1;
			}

			$("#lastInput").val(pageNum);
			$("#pageSpan").html(
					"共" + result.total + "条记录，共" + map.page + "/" + pageNum
							+ "页，第" + map.page + "页");
			pageButten();

			if ('Y'==flag_S) {
				var filtersLength = map.filter.filters.length;
				for (var n = 0; n < filtersLength; n++) {
					if (map.filter.filters[n]["field"] == "roleName") {
						$("#roleName").val(map.filter.filters[n]["value"]);
					} else if (map.filter.filters[n]["field"] == "roleDesc") {
						$("#roleDesc").val(map.filter.filters[n]["value"]);
					}else if (map.filter.filters[n]["field"] == "status") {
							$("#status").val(map.filter.filters[n]["value"]);
					}
				}
			}
			flag_S = 'N';
			numTR();

		},
		error : function(result) {
		}
	});
}

function seachProduct() {
	map.filter = {
		logic : "and",
		filters : [ {
			field : "roleName",
			value : $("#roleName").val()
		}, {
			field : "roleDesc",
			value : $("#roleDesc").attr("value")
		} ]
	};
}