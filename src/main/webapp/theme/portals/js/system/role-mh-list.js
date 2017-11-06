	if(map.page>1){
				}else{
				map.page=1;
			}
	         $(document).ready(function(){
	    			go(map.page);// 角色list 
	      		$("#searchButtons").click( 
        				function () { 
        					///console.log(map.filter.filters[0]["value"]);
        					map.filter={
        						    logic : "and",
        						    filters : [
        							{
        								field : "roleName",
        								value : $("#roleName").val()
        							},{
        								field : "roleDesc",
        								value : $("#roleDesc").val()
        							}
        							]};
        							map.page=1;
									go(map.page);
        				});
	});        
	       //查看目录 
	    		function  menu_(id,type){
	    			 $.dialog({
	    					lock: true,
	    					id : "mhlist_user",
	    					title : "角色授权",
	    					max: false,
	    				    min: false,
	    					width : 500,
	    					height : 430,
	    					content : "url:role-auth?id="+id+"&type="+type
	    				});
	    		}
	     	//设置编号
	     	function numTR(){
	     		var i=1;
	     		 $("font[name='numTR']").each(function(){
	     			$(this).text(i);
	     			i++;
	     		}) 
	     	}
	//单选
	function singerCheck(obj){
            $(':checkbox[name=ids]').each(function(){  
                $(this).click(function(){  
                    if($(this).attr('checked')){  
                        $(':checkbox[name=ids]').removeAttr('checked');  
                        $(this).attr('checked','checked');  
                    }  
                });  
            });  
	}
	 // delete
	 function dels(id , status) {
	    	 var url = "role-delete?id="+id;   
	    	 $.MsgBox.Confirm("温馨提示", "此操作将删除当前角色！你确定吗？", testconfirm2 ,url);	  // Confirm: function (title, msg, callback,url) {
	 } 
	//背景变色 
    function bgChange(obj){
		 var tr = document.getElementsByTagName("tr");
		 for(var i=0;i<tr.length;i++){
			 tr[i].style.backgroundColor = "";
		 }
    	obj.style.backgroundColor = "#00ccff";
	}
    $("#new").click(function(){
    	var url = "role-edit?status=2";
		$("#tablelist").load(url);
    });	
    function roleEdit(id) {//alert(id);
		//obj.parent.style.backgroundColor = "#00ccff";
		//$(this).parent().style.backgroundColor = "#00ccff";
		var url = "role-edit?roleId="+id;
		$("#tablelist").load(url);
	}
	
	function roleDelete(id) {
		var begin=Date.now();
		 confirm_ = confirm('此操作将删除当前记录！你确定吗？');
		 var end=Date.now();
		 if (end - begin < 10) {
			    console.log('用户禁用了confirm弹窗');
			    $.ajax({
	                type:"POST",
	                url:'role-delete?id='+id,
	                success:function(msg){
	                	$("#content").load("role-list");
	                }
	            });
			}
        if(confirm_){
            $.ajax({
                type:"POST",
                url:'role-delete?id='+id,
                success:function(msg){
                	$("#content").load("role-list");
                }
            });
        } 
	}
	
	//list数据 
	function go(pages){
		map.pageSize=$("#selectlimitnum").val();
		map.page=pages;
		map.sort=[{
			 field : "roleIndex",
			dir : "DESC"
		   }]; 
		
		var filtersLength = map.filter.filters.length;
		var statusFlag = true;
		for( var n=0; n<filtersLength; n++ ){
			if(map.filter.filters[n]["field"]=="status"){
				statusFlag=false;
			}
		}
		if(statusFlag){
			var obj = {
					field : "status",
						value : 2  //  
					}
	    	 map.filter.filters.push(obj);
		}
		
		   $.ajax({
		          url : "role-list.json",
		          dataType: "json", 
		          data:{
		        	  models : JSON.stringify(map)
		          },
		          type:"POST",
		          success: function(result) { if(result==null)   return;
	   	 			 var myTemplate = Handlebars.compile($("#grid-template").html());
	   				$('#grid').html(myTemplate(result));
	   				//点击选中行 
	   				 $("#grid tr").click(function(){
	   					 $(':checkbox[name=ids]').each(function(){  
	   						 $(':checkbox[name=ids]').removeAttr('checked'); 
	   		            });  
	   					$(this).find("input[type='checkbox']").attr('checked','checked');
		       	 	 });
	   				//加载分页信息
	   				var z = result.total%map.pageSize;
	   			var	total = result.total ;
	   			//console.log(total);
	   				var pageNum;
	   				
	  				$('tbody tr:even').addClass("alt-row"); 
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
	  					
	  					if( flag=='Y' ){
	  						var filtersLength = map.filter.filters.length;
	  						for( var n=0; n<filtersLength; n++ ){
	  							if(map.filter.filters[n]["field"]=="roleName"){
		  							$("#roleName").val(map.filter.filters[n]["value"]);
		  						}else if(map.filter.filters[n]["field"]=="roleDesc"){
		  							$("#roleDesc").val(map.filter.filters[n]["value"]);
		  						}
	  						}
	  					}
	  					
	  					
	  					 flag='N';
	  					numTR();
	  				
		          },
		          error: function(result) {
		          }
		        });
	}
	
	function seachProduct(){
		map.filter={
				    logic : "and",
				    filters : [
					{
						field : "roleName",
						value : $("#roleName").val()
					},{
						field : "roleDesc",
						value : $("#roleDesc").attr("value")
					}
				]};
	}