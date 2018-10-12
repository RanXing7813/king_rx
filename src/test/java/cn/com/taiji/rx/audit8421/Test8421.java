package cn.com.taiji.rx.audit8421;

import cn.com.taiji.rx.audit8421.GlobalFinal.AuditStatus;

public class Test8421 {

	// 新增  编辑  删除  导入  导出  上传  下载
	
	static int  add = 1;
	static int  edit = 2;
	static int  remove = 4;
	static int  impt = 8;
	
	
 public static void main(String[] args) {
	System.out.println(  ((6 & 15)!=0)?true:false ); // 判断 编辑 删除权限     是否存在于权限集合中
	
	int auditStatus = 40; //赋予账号的权限集合
	int[] array = { 0,1,2,3,4,5,6}; //权限集合 2的次方
		//       1, 2, 4, 8, 16, 32, 64
	for(int i=0 ; i<array.length ; i ++ ){
		int temp_ = (int) Math.pow(2, array[i]);//2的次方
		if(  ( (auditStatus &  temp_ )  ==  temp_ ? true : false)){
			//$("a[name='"+array[i]+"']").remove();
			 System.out.println(temp_); // 1 2 4 8
		} 
	}
	 
	  System.out.println( " 获取属性编号值: "+ GlobalFinal.AuditStatus.ADMIN.getNumber() );
	  System.out.println( " 通过编号获取属性值: "+ GlobalFinal.AuditStatus.getAuditStatusName(8) );
 
}
	
	
	
	
	
	// 指派1   解决2   关闭4   归档8      编辑16  和  删除32  撤销64
	// 指派0   解决1   关闭2   归档3      编辑4      和  删除5     撤销6
	
	
//	<div style="float: right;margin-right: 36px;margin-top: -12px;" th:if="${dto2.status != '4'}">
//	<a id="resets_"   name="6"  href="javascript:resetss()" ><span  title="撤回到草稿"   class="chehui"></span></a>
//	<a id="audio_"     name="5"  href="javascript:audios()" ><span   title="审核"  class="shenhe"></span></a><!--<img  width="16px" height="16px" src="/theme/img/icons/32/undo2.png" title="撤销到草稿" /><img  width="20px" height="20px" src="/theme/img/icons/32/audio.png" title="审核" />  -->
//	<a id="archive_"  name="3"  href="javascript:archive()" ><span title="归档"    class="guidang"></span></a><!-- <img width="20px" height="20px"  src="/theme/img/u577.png" title="归档"  /> -->
//	<a id="closes_"   name="2"  href="javascript:closes()" ><span  title="关闭"  class="guanbi"></span></a><!-- <img width="20px" height="20px" src="/theme/img/u579.png"   name="closes_{{[1].status}}"  title="关闭"/> -->
//	<a id="resolves_" name="1"   href="javascript:resolves()" ><span title="解决"  class="jiejue"></span></a><!-- <img width="20px" height="20px"  src="/theme/img/u581.png"  name="resolves_{{[1].status}}" title="解决" /> -->
//	<a id="appoints_" name="0"  href="javascript:appoints()" ><span title="指派"   class="zhipai"></span></a><!-- <img width="20px" height="20px"  src="/theme/img/u573.png" title="指派"   /> -->
//	<span style="float: right;margin-top: 3px;" >操作 ：&nbsp;</span>
//	</div>
//	
//	//审核状态 
//	var auditStatus = /*[[${auditStatus}]]*/; 
//	var array = [0,1,2,3,4,5,6];
//	for(var i=0 ; i<array.length ; i ++ ){
//		 if(!((auditStatus &  Math.pow(2, array[i])) ==  Math.pow(2, array[i]) ? true : false)){
//			$("a[name='"+array[i]+"']").remove();
//		} 
//	}
	
	
//	<script th:inline="javascript">
//	/*<![CDATA[*/
//				 var id = /*[[${_questionId}]]*/;
//			     var status = /*[[${_status}]]*/;
//			     var appointPeopleId = /*[[${_appointPeopleId}]]*/;
//			     var createdId = /*[[${_createdId}]]*/;
//		 $(document).ready(function(){
//			     // 加载固定值   及  获取相关全局参数  
//				 var container = /*[[${dto.contents}]]*/; $("#container").val(container);
//			     var souceDeptname = /*[[${dto.souceDeptname}]]*/;   $("option[value='"+souceDeptname+"']").attr('selected','selected') ;
//			     var level = /*[[${dto.level}]]*/;   $("#level option[value='"+level+"']").attr('selected','selected') ; 
//			     var product = /*[[${dto.product}]]*/;   $("#product option[value='"+product+"']").attr('selected','selected') ; 
//			     var module = /*[[${dto.module}]]*/; 
//					//处理二级联动   begin -- 
//					var allSelect_ = /*[[${allSelect}]]*/;
//				   // console.log(allSelect_);
//					initSelect();
//				    $("#module option[value='"+module+"']").attr('selected','selected') ; 
//				    $("#product").change(function(){
//				    	initSelect();
//					});
//				    $("#product").click();
//					$("#module").click();
//					//初始化  二级联动下拉列表值  
//					function initSelect(){
//						 var id_ ;
//						 for(var i=0;i<allSelect_.length;i++){
//							 if($("#product").val()==allSelect_[i].id){
//								 //alert(allSelect_[i].id);
//								 id_= allSelect_[i].id;
//							 }
//						 }
//						 var inner_ = "<option value=''>请选择</option>";
//						 for(var i=0;i<allSelect_.length;i++){
//							 if(id_==allSelect_[i].pId){
//								 inner_ += "<option value='"+allSelect_[i].id+"'>"+allSelect_[i].name+"</option>"; 
//							 }
//						 }
//						 $("#module").html(inner_);
//					}//二级联动   end --
//		     });  
//	
//				 
//	    /*  反写人员数据    */
//		  var $userCopyPeopleList_ = /*[[${userCopyPeopleList}]]*/; 
//	      var $result='';var $inner_='';
//	      if($userCopyPeopleList_!=null && $userCopyPeopleList_!=''){
//	    	  for (var i = 0; i < $userCopyPeopleList_.length; i++) {
//	  	 		$result +=  $userCopyPeopleList_[i].id+',';
//	  	 		var $userName = $userCopyPeopleList_[i].user_name  ;
//	  	 		$inner_ +=  " <div name='"+$userCopyPeopleList_[i].id+"' style=' margin:5;padding:5; border:none;background:transparent;float:left;' >"
//	  	 		+"<input   style='width: 20px; height: 15px;' type='hidden' name='copys' id='"+$userCopyPeopleList_[i].id+"' value='"+$userName+"' >"+$userName+"<a href='javascript:void(0);' onClick='remove_P(this);'><i style='float:right;'>,</i><i class='chacha'></i></a></div>  ";
//	  	       } 
//	  	 	  $("#copyPeople__").html($inner_);
//	      }
//	 	  
//	      var $userAppointPeopleList = /*[[${userAppointPeopleList}]]*/; 
//	      var $result='';var $inner_='';
//	      if($userAppointPeopleList!=null && $userAppointPeopleList!=''){
//	    	  for (var i = 0; i < $userAppointPeopleList.length; i++) {
//	  	 		$result +=  $userAppointPeopleList[i].id+',';
//	  	 		var $userName = $userAppointPeopleList[i].user_name  ;
//	  	 		$inner_ +=  " <div name='"+$userAppointPeopleList[i].id+"' style=' margin:5;padding:5; border:none;background:transparent;float:left;' >"
//	  	 		+"<input   style='width: 20px; height: 15px;' type='hidden' name='copys' id='"+$userAppointPeopleList[i].id+"' value='"+$userName+"' >"+$userName+"<a href='javascript:void(0);' onClick='remove_P(this);'><i style='float:right;'>,</i><i class='chacha'></i></a ></div>  ";
//	  	       } 
//	  	 	  $("#appointPeople__").html($inner_);
//	      }
///*]]>*/
//</script>
}
