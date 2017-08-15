$.loding = function() {
	var lodingDiv = $('<div id="loding-mask_" class="panel"></div>');
	$("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo(lodingDiv); 
	$("<div class=\"datagrid-mask-msg\"></div>").html("正在加载，请稍等...").appendTo(lodingDiv).css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});
	$(lodingDiv).appendTo("body");
};
$.loded = function() {
	$('#loding-mask_').remove();
};
	 
 