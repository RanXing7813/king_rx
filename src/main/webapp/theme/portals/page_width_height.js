/**
 * 初始化获取浏览器窗口宽高
 */


var winHeight=$(window).height();
var winWidth=$(window).width();   
$(window).resize(function(){
	winHeight=$(window).height();
	winWidth=$(window).width(); 
});
var minWidth = 800;  
var minHeight = 600; 
  // alert(winWidth+"|"+winHeight)
//
//网页可见区域宽：document.body.clientWidth 
//网页可见区域高：document.body.clientHeight 
//网页可见区域宽：document.body.offsetWidth (包括边线的宽) 
//网页可见区域高：document.body.offsetHeight (包括边线的宽) 
//网页正文全文宽：document.body.scrollWidth 
//网页正文全文高：document.body.scrollHeight 
//网页被卷去的高：document.body.scrollTop 
//网页被卷去的左：document.body.scrollLeft 
//网页正文部分上：window.screenTop 
//网页正文部分左：window.screenLeft 
//屏幕分辨率的高：window.screen.height 
//屏幕分辨率的宽：window.screen.width 
//屏幕可用工作区高度：window.screen.availHeight 
//屏幕可用工作区宽度：window.screen.availWidth 
//
//
//HTML精确定位:scrollLeft,scrollWidth,clientWidth,offsetWidth 
//scrollHeight: 获取对象的滚动高度。 
//scrollLeft:设置或获取位于对象左边界和窗口中目前可见内容的最左端之间的距离 
//scrollTop:设置或获取位于对象最顶端和窗口中可见内容的最顶端之间的距离 
//scrollWidth:获取对象的滚动宽度 
//offsetHeight:获取对象相对于版面或由父坐标 offsetParent 属性指定的父坐标的高度 
//offsetLeft:获取对象相对于版面或由 offsetParent 属性指定的父坐标的计算左侧位置 
//offsetTop:获取对象相对于版面或由 offsetTop 属性指定的父坐标的计算顶端位置 
//event.clientX 相对文档的水平座标 
//event.clientY 相对文档的垂直座标 
//event.offsetX 相对容器的水平坐标 
//event.offsetY 相对容器的垂直坐标 
//document.documentElement.scrollTop 垂直方向滚动的值 
//event.clientX+document.documentElement.scrollTop 相对文档的水平座标+垂直方向滚动的量 
//
//IE，FireFox 差异如下： 
//
//IE6.0、FF1.06+： 
//clientWidth = width + padding 
//clientHeight = height + padding 
//offsetWidth = width + padding + border 
//offsetHeight = height + padding + border 
//
//IE5.0/5.5： 
//clientWidth = width - border 
//clientHeight = height - border 
//offsetWidth = width 
//offsetHeight = height 
//
//(需要提一下：CSS中的margin属性，与clientWidth、offsetWidth、clientHeight、offsetHeight均无关)
//网页可见区域宽： document.body.clientWidth
//网页可见区域高： document.body.clientHeight
//网页可见区域宽： document.body.offsetWidth (包括边线的宽)
//网页可见区域高： document.body.offsetHeight (包括边线的高)
//网页正文全文宽： document.body.scrollWidth
//网页正文全文高： document.body.scrollHeight
//网页被卷去的高： document.body.scrollTop
//网页被卷去的左： document.body.scrollLeft
//网页正文部分上： window.screenTop
//网页正文部分左： window.screenLeft
//屏幕分辨率的高： window.screen.height
//屏幕分辨率的宽： window.screen.width
//屏幕可用工作区高度： window.screen.availHeight
//屏幕可用工作区宽度： window.screen.availWidth
//－－－－－－－－－－－－－－－－－－－
//技术要点
//本节代码主要使用了Document对象关于窗口的一些属性，这些属性的主要功能和用法如下。
//要得到窗口的尺寸，对于不同的浏览器，需要使用不同的属性和方法：若要检测窗口的真实尺寸，在Netscape下需要使用Window的属性；在IE下需要 深入Document内部对body进行检测；在DOM环境下，若要得到窗口的尺寸，需要注意根元素的尺寸，而不是元素。
//Window对象的innerWidth属性包含当前窗口的内部宽度。Window对象的innerHeight属性包含当前窗口的内部高度。
//Document对象的body属性对应HTML文档的标签。Document对象的documentElement属性则表示HTML文档的根节点。
//document.body.clientHeight表示HTML文档所在窗口的当前高度。document.body. clientWidth表示HTML文档所在窗口的当前宽度。
//
//实现代码
//<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
//"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
//<html xmlns="http://www.w3.org/1999/xhtml">
//<head>
//<title>请调整浏览器窗口</title>
//<meta http-equiv="content-type" content="text/html; charset=gb2312">
//</head>
//<body>
//<h2 align="center">请调整浏览器窗口大小</h2><hr>
//<form action="#" method="get" name="form1" id="form1">
//<!--显示浏览器窗口的实际尺寸-->
//浏览器窗口 的 实际高度: <input type="text" name="availHeight" size="4"><br>
//浏览器窗口 的 实际宽度: <input type="text" name="availWidth" size="4"><br>
//</form>
//<script type="text/javascript">
//<!-- 
//var winWidth = 0;
//var winHeight = 0;
//function findDimensions() //函数：获取尺寸
//{
////获取窗口宽度
//if (window.innerWidth)
//winWidth = window.innerWidth;
//else if ((document.body) && (document.body.clientWidth))
//winWidth = document.body.clientWidth;
////获取窗口高度
//if (window.innerHeight)
//winHeight = window.innerHeight;
//else if ((document.body) && (document.body.clientHeight))
//winHeight = document.body.clientHeight;
////通过深入Document内部对body进行检测，获取窗口大小
//if (document.documentElement  && document.documentElement.clientHeight && document.documentElement.clientWidth)
//{
//winHeight = document.documentElement.clientHeight;
//winWidth = document.documentElement.clientWidth;
//}
////结果输出至两个文本框
//document.form1.availHeight.value= winHeight;
//document.form1.availWidth.value= winWidth;
//}
//findDimensions();
////调用函数，获取数值
//window.onresize=findDimensions;
////-->
//</script>
//</body>
//</html> 
//
//源程序解读
//
//（1）程序首先建立一个表单，包含两个文本框，用于显示窗口当前的宽度和高度，并且，其数值会随窗口大小的改变而变化。
//（2）在随后的JavaScript代码中，首先定义了两个变量winWidth和winHeight，用于保存窗口的高度值和宽度值。
//（3）然后，在函数findDimensions ( )中，使用window.innerHeight和window.innerWidth得到窗口的高度和宽度，并将二者保存在前述两个变量中。
//（4）再通过深入Document内部对body进行检测，获取窗口大小，并存储在前述两个变量中。
//（5）在函数的最后，通过按名称访问表单元素，结果输出至两个文本框。
//（6）在JavaScript代码的最后，通过调用findDimensions ( )函数，完成整个操作。