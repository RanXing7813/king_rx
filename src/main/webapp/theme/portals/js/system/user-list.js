	     var map={};
	    			map.pageSize=10;//每页多少条
	    			map.page=1;//当前页
	         $(document).ready(function(){
	    			go(1);
	    			/* fileslist(myobj); */
	        	 $("#add").click(function(){
	        			$("#right-container").load("user-add");
	        		});
	        	//弹出框 
	    		 $("#authRole").click(function(){ 
	    			 if( $("input[name='ids']:checked").length>0){
	    				 $.dialog({
	    					lock: true,
	    					id : "authRoles",
							max: false,
		    				min: false,
	    					title : "用户角色分配",
	    					width : 700,
	    					height : 600,
	    					content : "url:role-user-auth"
	    				});
	    			 }else{
	    				 alert("请先选中用户: 您即将重置用户关联的角色信息 并且 重新分配 ");
	    			 }
	    			 
	    		 });
	    		 //上传弹窗
		    	$("#fileup").click(function(){
		    			 $.dialog({
		    			    lock: true,
		    		    	id : "fileup",
		    		    	title : "上传页",
		    		    	width : 477,
							max: false,
		    				min: false,
		    		    	height : 357,
		    		    	content : "url:file-up",
		    			   });
		    	});	 
	      		$("#searchButton").click( 
        				function () { 
        					map.filter={
        						    logic : "and",
        						    filters : [
        							{
        								field : "loginName",
        								value : $("#loginName").val()
        							},{
        								field : "userName",
        								value : $("#userName").attr("value")
        							},{
        								field : "email",
        								value : $("#email").attr("value")
        							}
        							]};
								go();
        				});
	      		$("#resetB").click(function(){
	      			$("table tr td input[type=text]").val('');
	      		})

	});        
	function userEdit(id) {
		
		
		var url = "user-edit?userId=" + id;
		$("#right-container").load(url);
	}
	//弹出框 
	function userAuth(id){
		//var url = "role-authRoles?userId=" + id;
		 $.dialog({
				lock: true,
				id : "userAuth",
				title : "用户角色信息查看",
				width : 700,
				max: false,
				min: false,
				height : 600,
				content : "url:role-authRoles-details?userId="+id
			});
	}
	        	
	function userDelete(id) {
		var begin=Date.now();
		 confirm_ = confirm('此操作将删除当前记录！你确定吗？');
		 var end=Date.now();
		 if (end - begin < 10) {
			   // console.log('用户禁用了confirm弹窗');
		            $.ajax({
		                type:"POST",
		                url:'user-delete?id='+id,
		                success:function(msg){
		                	$("#content").load("user-list");
		                }
		            });
			}
		 //以上是判断是否禁用confirm
	        if(confirm_){
	            $.ajax({
	                type:"POST",
	                url:'user-delete?id='+id,
	                success:function(msg){
	                	$("#content").load("user-list");
	                }
	            });
	        }
	}
	//全选  
	function checkAll(obj){
		  if($("#ids").attr("checked")){
		      $("input[name='ids']").attr("checked",true);
			}else{
			  $("input[name='ids']").attr("checked",false);
			}
	}

	function go(page){
		map.page=page;
		   $.ajax({
		          url : "user-list.json",
		          dataType: "json", 
		          data:{
		        	  models : JSON.stringify(map)
		          },
		          type:"POST",
		          success: function(result) { if(result==null)   return;
      	 			 var myTemplate = Handlebars.compile($("#grid-template").html());
      				$('#grid').html(myTemplate(result));
      				//加载分页信息
      				var z = result.total%map.pageSize;
      				var pageNum;
      				if(z==0){
      					pageNum=result.total/map.pageSize;
      				}else{
      					pageNum=Math.floor(result.total/map.pageSize)+1;
      				}
      				setPage(document.getElementById("fy"),pageNum , map.page);
		          },
		          error: function(result) {
		          }
		        });
	}