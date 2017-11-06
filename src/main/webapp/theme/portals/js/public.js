
//取ID-------------------------------------------------------------------------
function getEbyId(objectId){
    if (document.getElementById && document.getElementById(objectId)) {
        return document.getElementById(objectId)
    }
    else 
        if (document.all && document.all(objectId)) {
            return document.all(objectId)
        }
        else 
            if (document.layers && document.layers[objectId]) {
                return document.layers[objectId]
            }
            else {
                return false
            }
}

//通过样式取对象-------------------------------------------------------------
function getElementsByClassName(className, tagName){
    var ele = [], all = document.getElementsByTagName(tagName || "*");
    for (var i = 0; i < all.length; i++) {
        if (all[i].className == className) {
            ele[ele.length] = all[i];
        }
    }
    return ele;
}

//infoid 为选卡内容区域DIV的ID。cardid传this。调用是注意''的使用。
//---------------------------------------------------
function showCard(cardid, infoid, clsName){
    var cardList = cardid.parentNode.getElementsByTagName("li");
    for (i = 0; i < cardList.length; i++) {
        if (cardid == cardList[i]) {
            cardList[i].className = clsName + "_on";
            document.getElementById(infoid + i).style.display = "block";
        }
        else {
            cardList[i].className = clsName + "_off";
            document.getElementById(infoid + i).style.display = "none";
        }
    }
}


//选项卡-换背景
 function showCardBg(tabObj,liObj){
      var tabList = document.getElementById(tabObj).getElementsByTagName("li");
		  
        for(i=0; i <tabList.length; i++)
        {
                if (tabList[i].id == liObj)
                {
						document.getElementById(tabObj+"_tit"+i).className = tabObj+"_on"; 
						document.getElementById(tabObj+"_content"+i).style.display = "block";
						document.getElementById(tabObj+'_bg').className = tabObj+'_bg'+i;
							
                }else{
                        document.getElementById(tabObj+"_tit"+i).className = tabObj+"_off"; 
                        document.getElementById(tabObj+"_content"+i).style.display = "none";
                }
        } 
      }



$(function(){

	//政民互动
	$('#z_btn_tslb').click(function(){
		$('#z_opt_tslb').slideToggle('300');
		});
	$('#z_btn_szs').click(function(){
		$('#z_opt_szs').slideToggle('300');
		});
	$('#z_btn_szq').click(function(){
		$('#z_opt_szq').slideToggle('300');
		});
				
	$('.z_sel_options>ul>li').hover(
		function(){$(this).addClass('z_sel_opt_hover');},
		function(){$(this).removeClass('z_sel_opt_hover')}
		);	
		
		
	
	//友情链接
	    $('.links_content_wrap').hide();							
		$('.links_sel_btn').click(function(e){
			//阻止冒泡
			var e=window.event || event;
			if(e.stopPropagation){
				e.stopPropagation();
			}else{
				e.cancelBubble = true;
			} 
				
		var indexWrap=$('.links_sel_btn').index(this);	
		var thisObj=$('.links_content_wrap').eq(indexWrap);	
		$('.links_content_wrap').not(thisObj).hide();	
		thisObj.slideToggle();
			});
			
	document.onclick = function(){
		//点击空白处某个层关闭
		$('.links_content_wrap').each(function(){
			$(this).slideUp();
			});
	};


	});
	
	
//下拉层------------------------------------------------------
function getId(id) {
    return document.getElementById(id);
}

function showLayer(baseID, divID,clsName){
    baseID = getEbyId(baseID);
    divID = getEbyId(divID);
    baseID.className = clsName+"_on";
    divID.style.display = "block";
    baseID.onmouseout = function(){
        this.className = clsName+"_off";
        divID.style.display = "none";
    }
    divID.onmouseover = function(){
        baseID.className = clsName+"_on";
        this.style.display = "block";
    }
    divID.onmouseout = function(){
        baseID.className = clsName+"_off";
        this.style.display = "none";
    }
}

/*第一种形式 第二种形式 更换显示样式*/
function setTab(name,cursel,n){
for(i=1;i<=n;i++){
var menu=document.getElementById(name+i);
var con=document.getElementById("con_"+name+"_"+i);
menu.className=i==cursel?"hover":"";
con.style.display=i==cursel?"block":"none";
}
}

	//模拟Select
	$(function(){
		
		$('#xz_btn').click(function(){
			$('.options').slideToggle('300');
			});
		$('.options>ul>li').hover(
			function(){$(this).addClass('options_hover');},
			function(){$(this).removeClass('options_hover')}
			);
	});
	
	//字号大中小控制 
	$(function(){
	$('.f_size_s').click(function(){
		$('.article').css({'font-size':'14px'});
		});
	$('.f_size_m').click(function(){
		$('.article').css({'font-size':'16px'});
		});
	$('.f_size_b').click(function(){
		$('.article').css({'font-size':'18px'});
		});
	});


	
//右下角弹出消息关闭-----------------------------------------------
function closeDiv(o){
	document.getElementById(o).style.display="none";
	}
	
// 右下角向左弹出提示层----------------------------------------
$(function (){
$('.popDiv').animate({right:'10px'},500);
$('.popDiv .close').click(function(){
   $('.popDiv').hide();
});
});


$(function(){
	var lanren = $(".mainLcard a");
	lanren.click(function(){
		$(this).addClass("thisclass").siblings().removeClass("thisclass");
	});
});

$(function(){
	var lanren = $(".ywxtCard a");
	lanren.click(function(){
		$(this).addClass("thisclass").siblings().removeClass("thisclass");
	});
});
$(function(){
	var lanren = $(".topcard a");
	lanren.click(function(){
		$(this).addClass("thisclass").siblings().removeClass("thisclass");
	});
});
$(function(){
	var lanren = $(".m_mod_botcard a");
	lanren.click(function(){
		$(this).addClass("thisclass").siblings().removeClass("thisclass");
	});
});
$(function(){
	var lanren = $(".l_mod_ztleft a");
	lanren.click(function(){
		$(this).addClass("thisclass").siblings().removeClass("thisclass");
	});
});
$(function(){
	var lanren = $(".mainM_dataCard a");
	lanren.click(function(){
		$(this).addClass("thisclass").siblings().removeClass("thisclass");
	});
});

function showClass(id){
	$("#"+id).addClass("thisclass").siblings().removeClass("thisclass");
}


