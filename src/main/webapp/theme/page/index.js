  $.ajaxSetup( {    
        //设置ajax请求结束后的执行动作    
        complete : function(XMLHttpRequest, textStatus) {  
            // 通过XMLHttpRequest取得响应头，REDIRECT    
            var redirect = XMLHttpRequest.getResponseHeader("REDIRECT");//若HEADER中含有REDIRECT说明后端想重定向  
            if (redirect == "REDIRECT") {  
                var win = window;    
                while (win != win.top){  
                    win = win.top;  
                }  
              //将后端重定向的地址取出来,使用win.location.href去实现重定向的要求  
              win.location.href= XMLHttpRequest.getResponseHeader("CONTEXTPATH");    
            }  
        }    
    });   	
	function dd(){
		 $('#dg').datagrid({    
			    url: link2,  
			   // width : 500,
			    title : '巡检配置',
			    iconCls : 'icon-search',
			    columns:[[    
			        {field:'inspectionName',title:'巡检名称',width:100},
			        {field:'createdTime',title:'创建时间',width:100,formatter: function (unixTime) {
			        	var newDate = new Date();
			    		newDate.setTime(unixTime);
			        	return newDate.format('yyyy-MM-dd hh:mm:ss');
			        }},
			        {field:'createdName',title:'创建人',width:100},
			        {field:'url',title:'地址',width:100},
			    ]],
			    loadMsg : '努力展开中...', 
			    fit : false, 
			    fitColumns : true, 
			    pagination : true, 
			    idField : 'config_id', 
			    pageSize : 10, 
			    pageList : [10, 20, 30, 40], 
			    sortName : 'inspectionName', 
			    sortOrder : 'DESC', 
			    method : 'post', //multiSort : true, remoteSort : false,    
			}); 
	}	 

	function ff(){
		 $('#dg').datagrid({    
			    url: link1,  
			   // width : 500,
			    title : '巡检配置',
			    iconCls : 'icon-search',
			    
			    columns:[[  
			    	//增加编号，并设置为复选按钮
					{ field : 'configId', title : '编号', sortable : true, checkbox : true,
						width : 100,
					},
			    	{
			    	    field: 'inspectionName',
			    	    title: '巡检名称',
			    	    width: 100,
			    	    editor:{
			    	    	type: 'validatebox',
					    	options:{
					    	    	required:true,
					    	},
			    	    },
			    	},
			    	{
			    	    field: 'createdTime',
			    	    title: '创建时间',
			    	    sortable: true,
			    	    width: 100,
			    	    formatter: function (unixTime) {
			    	    	if(typeof(unixTime)==='number'){
					        	var newDate = new Date();
					    		newDate.setTime(unixTime);
					        	return newDate.format('yyyy-MM-dd hh:mm:ss');
			    	    	}else{
			    	    		return unixTime;
			    	    	}
				        },
			    	    editor: {
			    	        type: 'datetimebox',
			    	        options: {
			    	            required: true
			    	        },
			    	    },
			    	},
			    	{
			    	    field: 'createdName',
			    	    title: '创建人',
			    	    width: 100
			    	},
			    	{
			    	    field: 'url',
			    	    title: '地址',
			    	    width: 100,
			    	    editor:{
			    	    	type: 'validatebox',
					    	options:{
					    	    required:true,
					    	    validType: 'url',
					    	    invalidMessage:"mmp"
					    	},
			    	    },
			    	},

			    ]],
			    loadMsg : '加载中',
			    rownumbers : true,
			    fit : false, 
			    fitColumns : true, 
			    pagination : true, 
			    idField : 'config_id', 
			   // singleSelect : true,
			    pageSize : 10, 
			    pageList : [10, 20, 30, 40], 
			    sortName : 'inspectionName', 
			    sortOrder : 'DESC', 
			    method : 'post', //multiSort : true, remoteSort : false,    
			    onAfterEdit: function(rowIndex, rowData, changes) {//新增  编辑触发
			    	console.log("1:add/edit");
					$('#save,#redo').hide();
					
					var inserted = $("#dg").datagrid("getChanges","inserted");
					var updated = $("#dg").datagrid("getChanges","updated");
					
					if(inserted.length>0){
						$.ajax({
							type: "POST",
						     url: linkNewData,
						    data: {
						    	models: JSON.stringify(inserted[0])
						    	
						    },
				      beforeSend: function(){
					        $('#dg').datagrid('loading');
				      },//beforeSend
				      	 success: function(data) {
						      		if(data==1){
						      			 $('#dg').datagrid('loaded');
				      					 $('#dg').datagrid('load');
				      					 $('#dg').datagrid('unselectAll');
				      					 $.messager.show({
				      						 title: '提示',
				      						   msg: data+'条数据被新增',
				      						 timeout: 2000,
				      					 });
						      		}else if(data==0){
						      			 $('#dg').datagrid('loaded');
				      					 $('#dg').datagrid('load');
				      					 $('#dg').datagrid('unselectAll');
				      			         $.messager.alert('提示！', '数据新增失败！', 'warning');
						      		} 
				      	 },//success
				      	error: function(e) { 
				      			 $('#dg').datagrid('loaded');
		      					 $('#dg').datagrid('load');
		      					 $('#dg').datagrid('unselectAll');
		      			         $.messager.alert('提示！', '数据新增失败！', 'warning');
				      	}, //error
					 });//$.ajax
					}
					
					if(updated.length>0){
						$.ajax({
							type: "POST",
						     url: linkNewData,
						    data: {
						    	models: JSON.stringify(updated[0])
						    	
						    },
				      beforeSend: function(){
					        $('#dg').datagrid('loading');
				      },//beforeSend
				      	 success: function(data) {
						      		if(data){
						      			 $('#dg').datagrid('loaded');
				      					 $('#dg').datagrid('load');
				      					 $('#dg').datagrid('unselectAll');
				      					 $.messager.show({
				      						 title: '提示',
				      						   msg: data+'条数据被修改',
				      						 timeout: 2000,
				      					 });
						      		} else if(data==0){
						      			 $('#dg').datagrid('loaded');
				      					 $('#dg').datagrid('load');
				      					 $('#dg').datagrid('unselectAll');
				      			         $.messager.alert('提示！', '数据编辑失败！', 'warning');
						      		} 
				      	 },//success
				      	error: function(e) { 
			      			 $('#dg').datagrid('loaded');
	      					 $('#dg').datagrid('load');
	      					 $('#dg').datagrid('unselectAll');
	      			         $.messager.alert('提示！', '数据修改保持失败！', 'warning');
			      	}, //error
					 });//$.ajax
					}
				    obj.editRow = undefined;//完成后赋值
			    },
				//双击进入编辑状态
				onDblClickRow: function(rowIndex, rowData) {
					if (obj.editRow != undefined) {
						$('#dg').datagrid('endEdit',obj.editRow);
					}
				
					if (obj.editRow == undefined) {
						$('#save,#redo').show();
				        $('#dg').datagrid('beginEdit', rowIndex); //将第一行设置为可编辑状态
						obj.editRow = rowIndex;
					}
				},
			}); 
	}
	
	
	
	obj = { 
	  //查询
	  search : function () {
		   map.filter={
				    logic : "and",
				    filters : [
				    	{
				    		field:"inspectionName" ,
				    		value:$('input[name="inspectionName"]').val().trim()
				    	}
				    	,
				    	{
				    		field:"date_from" ,
				    		value:$('input[name="date_from"]').val()
				    	}
				    	,{
				    		field:"date_to" ,
				    		value:$('input[name="date_to"]').val()
				    	}
					]};
			var models = JSON.stringify(map);
			$('#dg').datagrid('load',{ 
				models : models,
			});
		}, 
		//复位查询条件
		reset : function () {
			$("#serchFrom").form("clear");
		},
		//声明一个非编辑属性
		editRow: undefined,
		//在第一行新增数据
		add: function() {
		    $("#save").show();
		    $("#redo").show();
		    if (this.editRow!=undefined) {
		        $("#dg").datagrid('endEdit',this.editRow);
		    } else {
		        $("#dg").datagrid('insertRow', {
		            index: 0,
		            row: {
		                //date:(newDate()).Format("yyyy-MM-ddhh:mm:ss"),
		            },
		        });
		        $('#dg').datagrid('beginEdit', 0); //将第一行设置为可编辑状态
		        this.editRow = 0;
		    }
		},
		del: function() {
			//修改时要获取选择到的行
		    var rows = $('#dg').datagrid('getSelections');
			//如果选择了一行则可以进行修改，否则不操作
			if (rows.length > 0) {
				$.messager.confirm("确定操作","您正在要删除所选的记录吗？",function(flag){
					if(flag){
						var ids =[];
						for(var i=0; i<rows.length; i++){
							ids.push(rows[i].configId);
						}
						$.ajax({
							type: "POST",
						     url: linkRemove,
						    data: {
						     ids: ids.join(',')//JSON.stringify(ids.join(','))
						    },
				      beforeSend: function(){
					        $('#dg').datagrid('loading');
				      },//beforeSend
				      	 success: function(data) {
						      		if(data){
						      			 $('#dg').datagrid('loaded');
				      					 $('#dg').datagrid('load');
				      					 $('#dg').datagrid('unselectAll');
				      					 $.messager.show({
				      						 title: '提示',
				      						   msg: data+'条数据被删除',
				      						 timeout: 2000,
				      					 });

						      		} 
				      	 },//success
					 });//$.ajax
					}//if(flag)
				});//$.messager.confirm
			}else{
		        $.messager.alert('提示！', '请选择要删除的记录！', 'info');

			}
		},
		//编辑
		edit: function() {
			
			//修改时要获取选择到的行
		    var rows = $('#dg').datagrid('getSelections');
			//如果只选择了一行则可以进行修改，否则不操作
			if (rows.length == 1) {
			//修改之前先关闭已经开启的编辑行，当调用endEdit该方法时会触发onAfterEdit事件
				if (this.editRow != undefined) {
		            $('#dg').datagrid('endEdit',this.editRow);
				}
			//当无编辑行时
		        if (this.editRow == undefined) {
			//获取到当前选择行的下标
		            var index = $('#dg').datagrid('getRowIndex',rows[0]);
			//开启编辑
		            $('#dg').datagrid('beginEdit',index);
		            $('#redo,#save').show();
			//把当前开启编辑的行赋值给全局变量editRow
			        this.editRow = index;
			//当开启了当前选择行的编辑状态之后，
			//应该取消当前列表的所有选择行，要不然双击之后无法再选择其他行进行编辑
		            $('#dg').datagrid('unselectRow',index);
			        }
			    } else {
			        $.messager.alert('警告！', '修改时需要或只能选择一行！', 'warning');
			    }
			},
		//保存
		save: function() {
			
		    $('#dg').datagrid('endEdit', this.editRow);//将新增的一行设置为结束编辑状态
		},
		//取消编辑状态
		redo: function() {
		    $('#save,#redo').hide();
		    this.editRow = undefined;
		    $('#dg').datagrid('rejectChanges');
	        $('#dg').datagrid('unselectAll');


		},
	}; 

	//扩展dateTimeBox
	$.extend($.fn.datagrid.defaults.editors, {
	    datetimebox: {
	        init: function(container, options) {
	            var input = $('<input type="text">').appendTo(container);
	            options.editable = false;
	            input.datetimebox(options);
	            return input;
	        },
	        getValue: function(target) {
	            return $(target).datetimebox('getValue');
	        },
	        setValue: function(target, value) {
		        	//var newDate = new Date();
		    		//newDate.setTime(unixTime);
		    		value = new Date().format('yyyy-MM-dd hh:mm:ss');
	            $(target).datetimebox('setValue', value);
	        },
	        resize: function(target, width) {
	            $(target).datetimebox('resize', width);
	        },
	        destroy: function(target) {
	            $(target).datetimebox('destroy');
	        },
	    }
	});
	
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