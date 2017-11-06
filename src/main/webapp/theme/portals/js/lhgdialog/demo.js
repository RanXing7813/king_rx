/*!
 * lhgcore Dialog Plugin Demo
 * Copyright (c) 2009 - 2011 By Li Hui Gang
 * URL : http://lhgcore.com/
 * BLOG : http://t.qq.com/lhgcore/
 */

// 运行代码
$.fn.runCode = function () {
	var getText = function(elems) {
		var ret = "", elem;

		for ( var i = 0; elems[i]; i++ ) {
			elem = elems[i];
			if ( elem.nodeType === 3 || elem.nodeType === 4 ) {
				ret += elem.nodeValue;
			} else if ( elem.nodeType !== 8 ) {
				ret += getText( elem.childNodes );
			};
		};

		return ret;
	};
	
	var code = getText(this);
	new Function(code).call(window);
	
	return this;
};

// 皮肤选择	
window._demoSkin = function () {
	$.dialog({
		id: 'demoSkin',
		padding: '15px',
		title: 'lhgDialog皮肤展示',
		content: _demoSkin.tmpl
	});
};
_demoSkin.tmpl = function (data) {
	var html = ['<table class="zebra" style="width:480px"><tbody>'];
	for (var i = 0, length = data.length; i < length; i ++) {
		html.push('<tr class="');
		html.push(i%2 ? 'odd' : '');
		html.push('"><th style="width:7em"><a href="?demoSkin=');
		html.push(data[i].name);
		html.push('">');
		html.push(data[i].name);
		html.push('</a></th><td>');
		html.push(data[i].about);
		html.push('</td></tr>');
	};
	html.push('</tbody></table>');
	return html.join('');
}([
	{name: 'default', about: 'lhgDialog默认灰色皮肤，简洁，纯CSS设计，无图片，采用css3渐进增强'},
	{name: 'discuz', about: 'discuz论坛登录窗口样式皮，简洁，纯CSS设计，无图片，采用css3渐进增强'},
	{name: 'qq2011', about: '仿QQ2011弹出窗口的蓝色渐变皮肤，纯CSS设计，无图片，采用css3渐进增强'},
	{name: 'j', about: '蓝色简约风格皮肤，大气十足，纯CSS设计，无图片，采用css3渐进增强'},
	{name: 'jtop', about: '仿cmstop内容管理系统窗口皮肤，蓝色简约风格，纯CSS设计，无图片，采用css3渐进增强'},
	{name: 'idialog', about: '苹果风格，iPad Safari或Mac Safari关闭按钮将在左边显示'}
]);

$(function(){
	// 按钮触发代码运行
	$(document).bind('click', function(event){
		var target = event.target,
			$target = $(target);

		if ($target.hasClass('runcode')) {
			$('#' + target.name).runCode();
		};
	});
	
	var $skin = $('#demo_skin');
	if (!$skin[0]) return;
	
	$skin.bind('click', function () {
		_demoSkin();
		return false;
	});
});
