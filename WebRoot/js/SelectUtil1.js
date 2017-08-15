//需要easyui支持
var Select = function(title, url, css, callBackFunction){
	this.title = title;
	this.url = url;
	this.css = css;
	this.callBackFunction = callBackFunction;
	this.init();
};
Select.prototype.init = function() {
	var tempSelectDiv_ = $('<div style="display: none;"></div>');
	var tempCss = {width:"960px",height:"500px"};
	if(this.css != null) tempCss = this.css;
	var selectDialog = $('<div data-options="iconCls:\'icon-save\'"></div>');
	this.selectDialog = selectDialog;
	$(selectDialog).css(tempCss).appendTo(tempSelectDiv_);
	$(tempSelectDiv_).appendTo("body");
	$(selectDialog).dialog({  
	    title: this.title,
	    closed: true,  
	    cache: false,  
	    href: this.url,  
	    modal: true  
	});
};
Select.prototype.open = function() {
	$(this.selectDialog).dialog('open');
	$(this.selectDialog).dialog('refresh', this.url);  
};
Select.prototype.close = function() {
	$(this.selectDialog).dialog('close');
};
Select.prototype.callBack = function() {
	this.callBackFunction.apply(this,arguments);
	this.close();
};