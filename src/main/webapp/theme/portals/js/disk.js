var options;
(function ($){
	var defaults = {
		title:null,				//涓婃柟鐨勬爣棰�
		titleColor:'#999',
		titleFontSize:'13px',
		width:'100%',			//涓€鍏辩殑瀹藉害
		speed:1000,				//鏄剧ず鏁堟灉鐨勬椂闂达紙姣锛�
		hazard:9999999999,				//棰勮鍊�
		data:[],				//鏁版嵁 json
		itemStyle:{
			fontSize:'12px',			//姣忔潯鏂囧瓧鐨勫ぇ灏�
			bgColor:'#E6E6E6',			//姣忔潯鐨勮儗鏅鑹�
			borderColor:'#BCBCBC',		//杈规绾块鑹�
			normalBgColor:'#26A0DA',	//姝ｅ父 鐧惧垎姣旈鑹�
			waringBgColor:'#F50000'		//瓒呰繃棰勮鍊� 鐧惧垎姣旈鑹�
		}
	};
	
	$.fn.disk = function (id,options){
		$(this).children().remove();
		options = $.extend(defaults,options);
		var dataObj = options.data;//鏁版嵁JSON
		var o = this;
		var total=0;
// var data3 = {total:'1319243805',users:'1 ',peruser:'50%',disks:[{id:'2',name:'省人社厅',value:'1319243805',total:'1319243805'}
		//,{id:'3',name:'省地税局',value:'358680518',total:'1319243805'}]};

		$.each(dataObj.disks,function (i,n){//缁熻鎬绘暟
			total+=parseInt(n.value);
			//total+=parseInt(n.total);
		});
		//alert(total);
		//alert(JSON.stringify($(this).find("<table>")));
		//$(this).find("<table>").remove();
		$(this).append("<table id='"+id+"' class='tb-disk-list' cellpadding='0' cellspacing='0' style='font-size:"+options.itemStyle.fontSize+";' width='"+defaults.width+"'></table>");//璁剧疆TABLE鐨勯暱搴�
		//鏄惁鏄剧ず鏍囬
		if(null != options.title){
			$("table",this).append("<tr><td  align='left'><span style='color:"+options.titleColor+";font-size:"+options.titleFontSize+";'>"+options.title+"</span></td>"
										+"<td  align='left'><span style='color:"+options.titleColor+";font-size:"+options.titleFontSize+";'>"+options.title+"</span></td><td></td></tr>");
									  //+"<td colspan='3' align='left'><span style='color:"+options.titleColor+";font-size:"+options.titleFontSize+";'>这是<span id='totals'>"+dataObj.total+"</span>锛屽凡浣跨敤锛�<span  style='color: red;'>"+dataObj.users+"</span>锛屼娇鐢ㄧ巼锛�<span style='color: red;'>"+dataObj.peruser+"</span></span></td></tr>");			
		} 
		
		var itemDiv="";
		var trs="";
		$.each(dataObj.disks,function (i,n){
		    var index=0;//褰撳墠涓暟锛岃秴杩�5涓惊鐜彇
			var percentage = (n.value*1);//.toFixed(2);//小数
			if(isNaN(percentage)){
				percentage=0;
			}
			var imgWidth = parseFloat(percentage);
			imgWidth = imgWidth/total*100 ;
			//alert(imgWidth);
			if(imgWidth>0 && 90>imgWidth){
			imgWidth+=10;
//				//alert(imgWidth);
			}
			
			//var imgWidth = parseFloat(100);
			if(imgWidth>0)
			{
				if(i>(options.itelTotal-1))
					index = i-(options.itelTotal-1);
				else{
					index = i;
					itemDiv="<div style='border:1px solid "+ options.itemStyle.borderColor+";background-color:"+options.itemStyle.bgColor+";font-size:"+options.itemStyle.fontSize+"'>";
					if(percentage>=options.hazard){
						itemDiv+="<div divWidth='"+imgWidth+"' style='width:0%;height:28px;background-color:"+options.itemStyle.waringBgColor+";' class='poll_plan"+index+"' >";
						itemDiv+="<div class='plan_e' style='background-color:"+options.itemStyle.waringBgColor+";'><div class='plan_c'  style='background-color:"+options.itemStyle.waringBgColor+";'></div></div>";
					}else{
						itemDiv+="<div divWidth='"+imgWidth+"' style='width:0%;height:28px;background-color:"+options.itemStyle.normalBgColor+";' class='poll_plan"+index+"' >";
						itemDiv+="<div class='plan_e' style='background-color:"+options.itemStyle.normalBgColor+";'><div class='plan_c'  style='background-color:"+options.itemStyle.normalBgColor+";'></div></div>";
					}
					itemDiv+="&nbsp;&nbsp;"+n.total+"</div>";
					itemDiv+="</div>";
			
				}
			}
			else{
				itemDiv='';
			}
			//"<tr></tr>"
			var tds="<td width='20%' height='28px' align='right' ><strong style=' margin-right: 13px;'>"+n.name+"</strong></td><td width='80%' style='bgcolor:"+options.itemStyle.bgColor+";'>"+itemDiv+"</td><td width='12%'></td>";//"+percentage+"
			//if(i%2==0){
				trs+="<tr>"+tds+"</tr><tr><td>&nbsp;  </td><td>  </td></tr>";
		//	}else{
			//	trs+=tds+"</tr>";
		//	}
			
		});
		//if(dataObj.disks.length%2==1){
		//	trs+="<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";	
		//}
		$("table",o).append(trs);
		$("div",o).each(function(i,n){
			if($(n).attr('divWidth'))
			{
				$(n).animate( { width: $(n).attr('divWidth')+'%'}, options.speed );
				$(n).removeAttr("divWidth");
			}
		});
		return this;
	};
})(jQuery);