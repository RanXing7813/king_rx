	if(map.page>1){
				}else{
				map.page=1;
			}
	         $(document).ready(function(){
	    			go(map.page);// 角色list 
	         });	
	      		$("#searchButtons").click( 
        				function () { 
        					///console.log(map.filter.filters[0]["value"]);
        					map.filter={
        						    logic : "and",
        						    filters : [
        							{
        								field : "deptId",
        								value : $("#deptId").val()
        							},{
        								field : "deptName",
        								value : $("#deptName").attr("value")
        							}
        							]};
        							map.page=1;
									go(map.page);
        				});
	        
	
   	//设置编号
   	function numTR(){
   		var i=1;
   		 $("font[name='numTR']").each(function(){
   			$(this).text(i);
   			i++;
   		}) 
   	}
	//背景变色 
    function bgChange3(obj){
		 //var tr = document.getElementsByTagName("tr");
		 //for(var i=0;i<tr.length;i++){
		//	 tr[i].style.backgroundColor = "";
		// }
		// $(this).find("input[type='checkbox']").attr('checked','checked');
		//console.log(obj[tr]);
		    
		/*  if($(this).find("input[type='checkbox']").attr('checked')=='checked'){
			 obj.style.backgroundColor = "#00ccff";
		 }esle{
			 obj.style.backgroundColor = "";
		 } */
    	
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
	//背景变色 
    function bgChange(obj){
		/*  var tr = document.getElementsByTagName("tr");
		 for(var i=0;i<tr.length;i++){
			 tr[i].style.backgroundColor = "";
		 } */
		 var backgroundColor =  obj.style.backgroundColor ;
		 //console.log(obj.childNodes[1].childNodes[3].checked);//true  false
    	if(backgroundColor== "rgb(0, 204, 255)"){
    		 obj.style.backgroundColor="";
    	}else{
    		obj.style.backgroundColor="#00ccff";
    	}
	}
	//list数据 
	function go(pages){
		map.pageSize=$("#selectlimitnum").val();
		map.page=pages;
		map.sort=[{
			 field : "deptIndex",
			dir : "DESC"
		   }]; 
		   $.ajax({
		          url : "dept-list2.json",
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
	   					 /* $(':checkbox[name=ids]').each(function(){  
	   						 $(this).attr('onclick'); 
	   		            });  */ 
	   					//console.log($(this).find("input[type='checkbox']").attr('checked'));
	   					// var obj = $(this).find("input[type='checkbox']");
	   					// obj.click();
	   					/* if($(this).find("input[type='checkbox']").attr('checked')!='checked'){
	   						$(this).find("input[type='checkbox']").attr('checked','checked');
	   						$(this).css("backgroundColor","#00ccff");
	   						//alert(1);
	   					}else {
	   						$(this).find("input[type='checkbox']").prop('checked','');
	   						//alert(2);
	   						$(this).css("backgroundColor","");
	   					} */
		   					//$(this).find("input[type='checkbox']").attr('checked','checked');
		       	 	 });
	   				//加载分页信息
	   				var z = result.total%map.pageSize;
	   				var	total = result.total ;
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
	  					///console.log(map.filter.filters.length+"//");
	  					if( flag=='Y' && map.filter.filters.length==2){
	  						 $("#deptId").val(map.filter.filters[0]["value"]);
	  						 $("#deptName").val(map.filter.filters[1]["value"]);
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
					field : "deptId",
					value : $("#deptId").val()
				},{
					field : "deptName",
					value : $("#deptName").attr("value")
				}
				]};
	}