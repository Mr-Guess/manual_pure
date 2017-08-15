var SUBTRACTWIDTH = 20;
var SUBTRACTHEIGHT = 20;
var FULLSCREEWIDTH = null;
var FULLSCREEHEIGHT = null;

//需要easyui、DataGridUtil、easyui-loding支持 
/**
 * Crud操作的封装类
 * @param setting  配置信息对象
 * 			autoInit  是否自动初始化查看窗口对象 default为true
 * 			grid 传入的Grid对象
 * 			addFormObject 添加窗口div对象
 *  		searchFormObject 查询窗口div对象
 *  		entityName 实体对象名称
 *  		moduleName 模块中文名
 *  		url 请求的action路径
 *  		css 窗口的样式，default为：{width:"900px",height:"450px"}
 *  		beforeSubmit 表单提交前的回调方法return false则为不提交
 * @returns {Crud}
 */
Crud = function(setting){
	var temp = this;
	temp.params = {
		autoInit: true,
		grid: null, //传入的Grid对象 没有列表时可空
		url: null, //请求的action路径
		addFormObject: null, //添加更新窗口div对象 必需
		viewFormObject: null, 
		updateFormObject: null,
		reViewFormObject: null,
		searchFormObject: null, //查询窗口div对象 必需
		entityName: null, //实体对象名称 必需
		moduleName: null, //模块中文名 必需
		addFormTitle: null,
		updateFormTitle: null,
		viewFormTitle: null,
		dialogCss:{width:"900px",height:"450px"},
		viewDialogCss: null,
		beforeSubmit: function(){return true;},
		afterUpdateLoadData: null,
		afterViewLoadData: null,
		afterAddSuccess: null,
		afterAddFail: null,
		afterUpdateSuccess: null,
		afterUpdateFail: null,
		afterRemoveSuccess: null,
		afterRemoveFail: null,
		afterAddDialogClose: null,
		afterUpdateDialogClose: null,
		afterViewDialogClose: null,
		afterDialogOpen: null,
		afterDialogClose: null,
		saveOrupdate:true
	};
	if(setting !=null && typeof setting == 'object') {
		$.each(setting, function(i, v){
			temp.params[i] = v;
		});
	}
	if(temp.params.grid == null) {
		temp.params.grid = {
			reloadGrid:function(){},
			getSelectNodes:function(){},
			doSearch:function(){}
		};
	}
	
	temp.initDiaCss();
	
	if(temp.params.updateFormObject == null)
		temp.params.updateFormObject = temp.params.addFormObject;
	if(temp.params.addFormTitle == null)
		temp.params.addFormTitle = '添加' + temp.params.moduleName + '信息';
	if(temp.params.updateFormTitle == null)
		temp.params.updateFormTitle = '修改' + temp.params.moduleName + '信息';
	if(temp.params.viewFormTitle == null)
		temp.params.viewFormTitle = '查看' + temp.params.moduleName + '信息';
	if(temp.params.reViewFormTitle == null)
		temp.params.reViewFormTitle = '查看' + temp.params.moduleName + '信息';
	if(temp.params.addUrl == null)
		temp.params.addUrl = temp.params.url + '!add';
	if(temp.params.getByIdUrl == null)
		temp.params.getByIdUrl = temp.params.url + '!getById';
	if(temp.params.updateUrl == null)
		temp.params.updateUrl = temp.params.url + '!update';
	if(temp.params.deleteUrl == null)
		temp.params.deleteUrl = temp.params.url + '!delete';
	if(temp.params.addSubmitUrl == null)
		temp.params.addSubmitUrl = temp.params.url + '!addSubmit';
	if(temp.params.updateSubmitUrl == null)
		temp.params.updateSubmitUrl = temp.params.url + '!updateSubmit';
	if(temp.params.reViewUrl == null)
		temp.params.reViewUrl = temp.params.url + '!seeReview';
	if(temp.params.viewDialogCss == null)
		temp.params.viewDialogCss = temp.params.dialogCss;
	$(temp.params.addFormObject).css(temp.params.dialogCss);
	$(temp.params.viewFormObject).css(temp.params.dialogCss);
	$(temp.params.updateFormObject).css(temp.params.dialogCss);
	$(temp.params.reViewFormObject).css(temp.params.dialogCss);
	if(temp.params.autoInit && temp.params.viewFormObject == null) {
		temp.initViewFormObject();
	}
};


Crud.prototype.initDiaCss=function(){
	
	var temp = this;
	$("#fullScreenDiv").remove();
	$("body").append('<div id="fullScreenDiv" style="position: absolute;margin:0px 0px;padding:0px 0px;height:90%;width:100%;left:0px;top:0px;z-index:-1;"></div>');
	
	var maxWidth = Number($("#fullScreenDiv").css("width").replace("px",""));
	var maxHeight = Number($("#fullScreenDiv").css("height").replace("px",""));
	FULLSCREEWIDTH = maxWidth;
	FULLSCREEHEIGHT = maxHeight;
	var diaWidth = Number(temp.params.dialogCss.width.replace("px",""));
	
	if(temp.params.dialogCss.height!="auto"){
		var diaHeight = Number(temp.params.dialogCss.height.replace("px",""));
		temp.params.dialogCss.height = diaHeight+"px";
		if(diaHeight>maxHeight){
			temp.params.dialogCss.height = (maxHeight-SUBTRACTHEIGHT)+"px";
		}
	}
	
	temp.params.dialogCss.width = diaWidth+"px";
	
	if(diaWidth>maxWidth){
		temp.params.dialogCss.width = (maxWidth-SUBTRACTWIDTH)+"px";
	}
	
};

/**
 * 强制设置参数方法
 */
Crud.prototype.setParams = function(setting) {
	var temp = this;
	if(setting !=null && typeof setting == 'object') {
		$.each(setting, function(i, v){
			temp.params[i] = v;
		});
	}
};

/**
 * 初始化查看页面渲染方法
 */
Crud.prototype.initViewFormObject = function() {
	$('#addForm').find('table').attr('style','font-size:12px;WORD-WRAP: break-word;TABLE-LAYOUT:fixed;word-break:break-all');
	var temp = this;
	//viewFormInit
	var tempDiv_ = $('<div style="display: none;"></div>');
	var isMax = "false";
	if(temp.params.dialogCss.height.substring(0,temp.params.dialogCss.height.length-2) >= 600){
		isMax = "true";
	}
	var tempForm = $('<div data-options="iconCls:\'icon-save\',modal:true,minimizable:true,maximizable:true,maximized:' + isMax + ',collapsible:true,draggable:false"></div>');
	temp.params.viewFormObject = tempForm;
	$(tempForm).css(temp.params.viewDialogCss).appendTo(tempDiv_);
	$(tempDiv_).appendTo("body");
	$(tempForm).append($(temp.params.addFormObject).find('[name="addDiv"]').clone());
	$(tempForm).find('td').find('input[name]').each(function(){
		if($(this).attr("id")=="dbys"){
			$(this).remove();
		}
		if($(this).attr("id")=="aqxc"){
			$(this).remove();
		}
		var attrName = $(this).attr('name');
		var span = $('<span>').attr('class', 'addStyle_')
			 .attr('name', attrName);
		if($(this).parent().is('td')) {
			$(this).after(span);
		} else {
			$(this).parents('td').append(span);
		}
		var parent = $(this).parents('td');
		parent.find('span').each(function(){
			if($(this).attr('class')!= 'addStyle_')
				$(this).remove();
		});
		$(this).remove();
		parent.find('input').each(function(){
			if($(this).attr('name') == null) {
				$(this).remove();
				return;
			}
			if($(this).attr('type') != 'text' && $(this).attr('type') != 'hidden')
				$(this).remove();
		});
	});
	$(tempForm).find('td textarea').each(function(){
		$(this).after($('<span></span>')
			 .attr('name', $(this).attr('name'))
			 .attr('style', 'width:100%;height:85px;overflow-y:auto;')
			 );
		$(this).remove();
	});
};

/**
 * 添加方法
 */
Crud.prototype.add = function() {
	$('#addForm').find('table').attr('style','font-size:12px;');
	var temp = this;
	temp.params.saveOrupdate = true;
	Crud.clearForm($(temp.params.addFormObject).find('form'));      	
	$(temp.params.addFormObject).dialog({
		title:temp.params.addFormTitle,
		modal:true,
		draggable:true,
		closed:true,
		buttons:[{
			text:'保存',
			iconCls:'icon-save',
			handler:function() { 
				temp.addSaveHandler();
			}
		}, {
			text:'提交',
			iconCls:'icon-cancel',
			handler:function() {
				temp.addSubmitSaveHandler();
			}
		}, {
			text:'取消',
			iconCls:'icon-cancel',
			handler:function() {
				Crud.closeWindowsAnimate($(temp.params.addFormObject));
				//$(temp.params.addFormObject).dialog('close');
				if(typeof temp.params.afterAddDialogClose == 'function') {
                	temp.params.afterAddDialogClose();
                }
			}
		}]
	});
	$(temp.params.addFormObject).dialog('open');
	if(typeof temp.params.afterDialogOpen == 'function') {
    	temp.params.afterDialogOpen();
    }
	Crud.openWindowsAnimate();
};
/**
 * 添加保存操作方法
 */
Crud.prototype.addSaveHandler = function() {
	$('#addForm').find('table:first').attr('style','font-size:12px;');
	var temp = this;
	if(!temp.params.beforeSubmit()) return;
	if($(temp.params.addFormObject).find('form').form('validate')){
		if(temp.params.saveOrupdate){
			temp.params.saveOrupdate = false;
		}else{
			return;
		}
		
	}
	$(temp.params.addFormObject).find('form').form('submit',{
        url: temp.params.addUrl,
        success:function (data) {
        	 Crud.closeWindowsAnimate($(temp.params.addFormObject));
        	//$(temp.params.addFormObject).dialog('close');
        	var data = eval('(' + data + ')');
            if (data.operateSuccess) {
                $.messager.show({
                    title:'成功',
                    msg:data.operateMessage,
                    timeout:2000,
                    showType:'slide'
                });
                temp.params.grid.reloadGrid();
                if(typeof temp.params.afterAddSuccess == 'function') {
                	temp.params.afterAddSuccess(data);
                }
            } else {
                $.messager.show({
                    title:'失败',
                    msg:data.operateMessage,
                    timeout:2000,
                    showType:'slide'
                });
                if(typeof temp.params.afterAddSuccess == 'function') {
                	temp.params.afterAddFail(data);
                }
            }
        }
    });
};

/**
 * 添加提交操作方法
 */
Crud.prototype.addSubmitSaveHandler = function() {
	$('#addForm').find('table:first').attr('style','font-size:12px;');
	var temp = this;
	if(!temp.params.beforeSubmit()) return;
	if($(temp.params.addFormObject).find('form').form('validate')){
		if(temp.params.saveOrupdate){
			temp.params.saveOrupdate = false;
		}else{
			return;
		}
		
	}
	$(temp.params.addFormObject).find('form').form('submit',{
        url: temp.params.addSubmitUrl,
        success:function (data) {
        	 Crud.closeWindowsAnimate($(temp.params.addFormObject));
        	//$(temp.params.addFormObject).dialog('close');
        	var data = eval('(' + data + ')');
            if (data.operateSuccess) {
                $.messager.show({
                    title:'成功',
                    msg:data.operateMessage,
                    timeout:2000,
                    showType:'slide'
                });
                temp.params.grid.reloadGrid();
                if(typeof temp.params.afterAddSuccess == 'function') {
                	temp.params.afterAddSuccess(data);
                }
            } else {
                $.messager.show({
                    title:'失败',
                    msg:data.operateMessage,
                    timeout:2000,
                    showType:'slide'
                });
                if(typeof temp.params.afterAddSuccess == 'function') {
                	temp.params.afterAddFail(data);
                }
            }
        }
    });
};

/**
 * 更新方法
 * @param rowId
 */
Crud.prototype.update = function(rowId) {
	$('#updateForm').find('table').attr('style','font-size:12px;');
	var temp = this;
	temp.params.saveOrupdate = true;
	Crud.clearForm($(temp.params.updateFormObject).find('form'));
	$(temp.params.updateFormObject).dialog({
		title:temp.params.updateFormTitle,
		modal:true,
		draggable:true,
		closed:true,
		buttons:[{
			text:'保存',
			iconCls:'icon-save',
			handler:function() {
				temp.updateSaveHandler();
			}
		},{
			text:'提交',
			iconCls:'icon-save',
			handler:function() {
				temp.updateSubmitSaveHandler();
			}
		}, {
			text:'取消',
			iconCls:'icon-cancel',
			handler:function() {
				Crud.closeWindowsAnimate($(temp.params.updateFormObject));
				//$(temp.params.updateFormObject).dialog('close');
				if(typeof temp.params.afterUpdateDialogClose == 'function') {
                	temp.params.afterUpdateDialogClose(rowId);
                }
			}
		}]
	});
	$.loding();
	var d=null;
	$.ajax({
        url: temp.params.getByIdUrl + '?id=' + rowId,
        method:'POST',
        async:false,
        success:function (data) {
        	d=data;
	        temp.updateLoadData(data);
	        if(typeof temp.params.afterUpdateLoadData == 'function') {
	        	temp.params.afterUpdateLoadData($(temp.params.updateFormObject), data);
	        } 
        	$.loded();
        	$(temp.params.updateFormObject).dialog('open');
        	if(typeof temp.params.afterDialogOpen == 'function') {
            	temp.params.afterDialogOpen();
            }
        	Crud.openWindowsAnimate();
        	//$(temp.params.updateFormObject).form('validate');
        }
    });
	return d;
};
/**
 * 更新保存操作方法
 */
Crud.prototype.updateSaveHandler = function() {
	$('#updateForm').find('table').attr('style','font-size:12px;');
	var temp = this;
	if(!temp.params.beforeSubmit()) return;
	if($(temp.params.updateFormObject).find('form').form('validate')){
		if(temp.params.saveOrupdate){
			temp.params.saveOrupdate = false;
		}else{
			return;
		}
	}
	$(temp.params.updateFormObject).find('form').form('submit',{
		url: temp.params.updateUrl,
        success:function(data) {
		//alert(data);
        Crud.closeWindowsAnimate($(temp.params.updateFormObject));	//添加动画
		//$(temp.params.updateFormObject).dialog('close');
        	var data = eval('(' + data + ')');
            if (data.operateSuccess) {
                $.messager.show({
                    title:'成功',
                    msg:data.operateMessage,
                    timeout:2000,
                    showType:'slide'
                });
                temp.params.grid.reloadGrid();
                if(typeof temp.params.afterUpdateSuccess == 'function') {
                	temp.params.afterUpdateSuccess(data);
                }
            } else {
                $.messager.show({
                    title:'失败',
                    msg:data.operateMessage,
                    timeout:2000,
                    showType:'slide'
                });
                if(typeof temp.params.afterUpdateFail == 'function') {
                	temp.params.afterUpdteFail(data);
                }
            }
        }
    });
};

/**
 * 更新提交操作方法
 */
Crud.prototype.updateSubmitSaveHandler = function() {
	$('#updateForm').find('table').attr('style','font-size:12px;');
	var temp = this;
	if(!temp.params.beforeSubmit()) return;
	if($(temp.params.updateFormObject).find('form').form('validate')){
		if(temp.params.saveOrupdate){
			temp.params.saveOrupdate = false;
		}else{
			return;
		}
	}
	$(temp.params.updateFormObject).find('form').form('submit',{
		url: temp.params.updateSubmitUrl,
        success:function(data) {
		//alert(data);
        Crud.closeWindowsAnimate($(temp.params.updateFormObject));	//添加动画
		//$(temp.params.updateFormObject).dialog('close');
        	var data = eval('(' + data + ')');
            if (data.operateSuccess) {
                $.messager.show({
                    title:'成功',
                    msg:data.operateMessage,
                    timeout:2000,
                    showType:'slide'
                });
                temp.params.grid.reloadGrid();
                if(typeof temp.params.afterUpdateSuccess == 'function') {
                	temp.params.afterUpdateSuccess(data);
                }
            } else {
                $.messager.show({
                    title:'失败',
                    msg:data.operateMessage,
                    timeout:2000,
                    showType:'slide'
                });
                if(typeof temp.params.afterUpdateFail == 'function') {
                	temp.params.afterUpdteFail(data);
                }
            }
        }
    });
};

/**
 * 更新Dialog加载数据方法
 * @param data
 */
Crud.prototype.updateLoadData = function(data) {
	var temp = this;
	var obj = {};
	$.each(data,function(i,item){
		obj[temp.params.entityName + '.' + i] = item;
    });
	$(temp.params.updateFormObject).find('form').form('load',obj);
};
/**
 * 删除方法
 */
Crud.prototype.remove = function () {
	var temp = this;
    var rows = temp.params.grid.getSelectNodes();
    if (rows.length == 0) {
        $.messager.show({
            title:'提示',
            msg:'请选择要删除的行',
            timeout:2000,
            showType:'slide'
        });
        return;
    } else {
    	var ids = new Array();
        $.messager.confirm('警告', '是否要删除该记录?', function (r) {
            if (r) {
            	var i = 0;
            	for (i=0; i<rows.length; i++) {
                    ids.push(rows[i].id);
                }
                temp.removeByIds(ids);
            }
        });
    }
};
/**
 * 通过ID数组删除方法
 * @param ids
 */
Crud.prototype.removeByIds = function(ids) {
	var temp = this;
	$.ajax({
        url:temp.params.deleteUrl + '?ids=' + ids,
        method:'POST',
        success:function(data) {
        	var data = eval('(' + data + ')');
            if (data.operateSuccess) {
                $.messager.show({
                    title:'成功',
                    msg:data.operateMessage,
                    timeout:2000,
                    showType:'slide'
                });
                temp.params.grid.reloadGrid();
                if(typeof temp.params.afterRemoveSuccess == 'function') {
                	temp.params.afterRemoveSuccess(data);
                }
            } else {
                $.messager.show({
                    title:'失败',
                    msg:data.operateMessage,
                    timeout:2000,
                    showType:'slide'
                });
                if(typeof temp.params.afterRemoveFail == 'function') {
                	temp.params.afterRemoveFail(data);
                }
            }
        }
    });
};
/**
 * 查看方法
 * @param rowId
 */
Crud.prototype.view = function (rowId) {
	$('#viewForm').find('table').attr('style','font-size:12px;WORD-WRAP: break-word;TABLE-LAYOUT:fixed;word-break:break-all');
	var temp = this;
	$.loding();
	$.ajax({
        url: temp.params.getByIdUrl + '?id=' + rowId,
        method:'POST',
        success:function (data) {
	        temp.viewLoadData(data);
        	if(typeof temp.params.afterViewLoadData == 'function') {
	        	temp.params.afterViewLoadData($(temp.params.viewFormObject), data);
	        } 
        	$.loded();
        	$(temp.params.viewFormObject).dialog({
				title:temp.params.viewFormTitle,
				modal:true,
				draggable:true,
				buttons:[{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function() {
						Crud.closeWindowsAnimate($(temp.params.viewFormObject));
						//$(temp.params.viewFormObject).dialog('close');
						if(typeof temp.params.afterViewDialogClose == 'function') {
    	                	temp.params.afterViewDialogClose(rowId);
    	                }
					}
				}]
			});
        	Crud.openWindowsAnimate();
        }
	
    });
};


/**
 * 批复方法
 * @param rowId
 */
Crud.prototype.reView = function (rowId) {
	$('#viewForm').find('table').attr('style','font-size:12px;WORD-WRAP: break-word;TABLE-LAYOUT:fixed;word-break:break-all');
	var temp = this;
	$.loding();
	$.ajax({
        url: temp.params.getByIdUrl + '?id=' + rowId,
        method:'POST',
        success:function (data) {
	        temp.viewLoadData(data);
        	if(typeof temp.params.afterViewLoadData == 'function') {
	        	temp.params.afterViewLoadData($(temp.params.viewFormObject), data);
	        } 
        	$.loded();
        	$(temp.params.viewFormObject).dialog({
				title:temp.params.viewFormTitle,
				modal:true,
				draggable:true,
				buttons:[{
					text:'批复',
					iconCls:'icon-search',
					handler:function() {
						doReversion(data);
					}
				},{
					text:'关闭',
					iconCls:'icon-cancel',
					handler:function() {
						Crud.closeWindowsAnimate($(temp.params.viewFormObject));
						//$(temp.params.viewFormObject).dialog('close');
						if(typeof temp.params.afterViewDialogClose == 'function') {
    	                	temp.params.afterViewDialogClose(rowId);
    	                }
					}
				}]
			});
        	Crud.openWindowsAnimate();
        }
	
    });
};


/**
 * 查看批复方法
 * @param rowId
 */
Crud.prototype.seeReView = function (rowId) {
	$('#reViewForm').find('table').attr('style','font-size:12px;WORD-WRAP: break-word;TABLE-LAYOUT:fixed;word-break:break-all');
	var temp = this;
	$.loding();
	$.ajax({
        url: temp.params.reViewUrl + '?id=' + rowId,
        method:'POST',
        success:function (data) {
	        temp.reViewLoadData(data);
        	if(typeof temp.params.afterViewLoadData == 'function') {
	        	temp.params.afterViewLoadData($(temp.params.reViewFormObject), data);
	        } 
        	$.loded();
        	$(temp.params.reViewFormObject).dialog({
				title:temp.params.reViewFormTitle,
				modal:true,
				draggable:true,
				buttons:[{
					text:'关闭',
					iconCls:'icon-cancel',
					handler:function() {
						Crud.closeWindowsAnimate($(temp.params.reViewFormObject));
						//$(temp.params.viewFormObject).dialog('close');
						if(typeof temp.params.afterViewDialogClose == 'function') {
    	                	temp.params.afterViewDialogClose(rowId);
    	                }
					}
				}]
			});
        	Crud.openWindowsAnimate();
        }
	
    });
};


/**
 * 查看Dialog加载数据方法
 * @param data
 */
Crud.prototype.viewLoadData = function(data) {
	var temp=this;
	if(data == null) return;
	$.each(data,function(i,item){
		$(temp.params.viewFormObject).find('span[name="' + temp.params.entityName + '.' + i + '"]').html(item);
    });
};
/**
 * reViewDialog加载数据方法
 * @param data
 */
Crud.prototype.reViewLoadData = function(data) {
	var temp=this;
	if(data == null) return;
	$.each(data,function(i,item){
		$(temp.params.reViewFormObject).find('span[name="' + temp.params.entityName + '.' + i + '"]').html(item);
    });
};
/**
 * 查询方法
 */
Crud.prototype.search = function() {
	var temp = this;
	var form = $.form2Json(temp.params.searchFormObject);
	temp.params.grid.doSearch(form);
};
/**
 * 清空查询表单方法
 */
Crud.prototype.clearSearch = function(){
	var temp = this;
	var formObjectTemp = null;
	if($(temp.params.searchFormObject).is('form')) {
		formObjectTemp = $(temp.params.searchFormObject);
	} else {
		formObjectTemp = $(temp.params.searchFormObject).find('form');
	}
	Crud.clearForm(formObjectTemp);
};
/**
 * 清空表单
 * @param formObject jquery或DOM表单对象
 */
Crud.clearForm = function(formObject) {
	if(typeof formObject != 'object') return;
	var formObjectTemp = null;
	if($(formObject).is('form')) {
		formObjectTemp = $(formObject);
	} else if($(formObject).find('form').is('form')) {
		formObjectTemp = $(formObject).find('form');
	} else {
		return;
	}
	if(formObjectTemp.jquery) {
		if(formObjectTemp.length == 0) return;
		$(formObjectTemp).each(function(){
			$(this).find('input[name]').val('');
			$(this)[0].reset();
		});
	} else {
		formObjectTemp.reset();
	}
};
/**
 * 窗体打开动画
 */
Crud.openWindowsAnimate=function(){
	$('.window-shadow').hide();
	//  fadeInDown  bounceIn
	$('.window').addClass('animated fadeInDown').delay(500).queue(function(next){
	    $(this).removeClass('animated fadeInDown');
	    next();
	});
}
/**
 * 窗体关闭动画
 * */
Crud.closeWindowsAnimate=function(objForm){
	$('.window-shadow').hide();
	//   fadeOutUp bounceOut
	$('.window').addClass('animated fadeOutUp').delay(500).queue(function(next){
	    $(this).removeClass('animated fadeOutUp');
	    $(objForm).dialog("close");
	    if(typeof temp.params.afterDialogClose == 'function') {
        	temp.params.afterDialogClose();
        }
	    next();
	});
}