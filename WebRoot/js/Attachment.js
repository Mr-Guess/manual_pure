//Attachment.url='${ctx}';Attachment.id='d195a82f6080413386d69287f90afa7d';
//Attachment.upload();Attachment.view();
Attachment = {};
Attachment.init=function() {
	var tempDiv_ = $('<div style="display: none;"></div>');
	var tempDialog = $('<div data-options="iconCls:\'icon-save\',modal:true,minimizable:true,maximizable:true,collapsible:true,draggable:false"></div>');
	var tempIframe = $('<iframe frameborder="0" width="100%" height="98%" scrolling-x="no"></iframe>');
	Attachment.dialog = tempDialog;//可以重新指定
	Attachment.iframe = tempIframe;
	$(tempIframe).appendTo(tempDialog);
	$(tempDialog).css({width:"500px",height:"400px"}).appendTo(tempDiv_);
	$(tempDiv_).appendTo("body");
};
Attachment.upload=function(){
	Attachment.iframe.attr('src', Attachment.url +'/jsp/sys/fileUpload/uploadFile.jsp?relationId=' + Attachment.id);
	Attachment.dialog.dialog({
		title:'上传附件',
		modal:true,
		draggable:false,
		minimizable:true,
		maximizable:true,
		maximized:false,
		buttons:[{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function() {
				Attachment.dialog.dialog('close');
			}
		}]
	});
};
Attachment.view=function(){
	Attachment.iframe.attr('src', Attachment.url +'/jsp/sys/fileUpload/uploadFileView.jsp?relationId=' + Attachment.id);
	Attachment.dialog.dialog({
		title:'查看附件',
		modal:true,
		draggable:false,
		minimizable:true,
		maximizable:true,
		maximized:false,
		buttons:[{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function() {
				Attachment.dialog.dialog('close');
			}
		}]
	});
};
Attachment.init();