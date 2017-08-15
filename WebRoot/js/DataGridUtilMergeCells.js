/**
 * easyui DataGrid的封装类
 * @param url 请求的url
 * @param key div的id
 * @param setting 配置对象（可以重写或添加DataGrid Properties Events）例： {iconCls:'icon-add'}
 * @returns {Grid}
 */
Grid = function(url, key,setting,cell) {
	var temp = this;
	this.url = url;
	this.keyInput = '#' + key;
	this.cell = cell;
	this.setting = {
			title : '',//去除title
			iconCls : 'icon-edit',
			nowrap : false,
			striped : true,
			border : true,
			collapsible : false,
			fit : true,
			fitColumns:true,
			url : this.url,
			remoteSort : false,
			idField : 'id',
			singleSelect : false,
			pagination : true,
			rownumbers : true,
			loadMsg:"数据加载中...",
			frozenColumns : [ [ {
				field : 'ck',
				checkbox : true
			} ] ],
			onLoadSuccess: function(){
		            function bindRowsEvent(){
		                var panel = $('#data_list').datagrid('getPanel');
		                var rows = panel.find('tr[datagrid-row-index]');
		                rows.unbind('click').bind('click',function(e){
		                    return false;
		                });
		                rows.find('div.datagrid-cell-check input[type=checkbox]').unbind().bind('click', function(e){
		                    var index = $(this).parent().parent().parent().attr('datagrid-row-index');
		                    if ($(this).attr('checked')){
		                        $('#data_list').datagrid('selectRow', index);
		                    } else {
		                        $('#data_list').datagrid('unselectRow', index);
		                    }
		                    e.stopPropagation();
		                });
		            }
		            setTimeout(function(){
		                bindRowsEvent();
		            }, 1); 
		            $(this).datagrid("autoMergeCells",[cell]);
		    }
		};
	if(setting !=null && typeof setting == 'object') {
		$.each(setting, function(i, v){
			temp.setting[i] = v;
		});
	}
};
/**
 * 暴露easyui DataGrid提供的datagrid方法
 */
Grid.prototype.datagrid = function() {
	$(this.keyInput).datagrid.apply($(this.keyInput), arguments);
};
/**
 * 获取easyui DataGrid的操作对象
 * @returns
 */
Grid.prototype.getGridObject = function() {
	return $(this.keyInput);
};

Grid.prototype.loadGrid = function() {
	var pageSettings = {
		pageSize : 10,
		pageList : [ 10, 15, 20, 30 ],
		beforePageText : '第',
		afterPageText : '页    共{pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录  共{total}条记录'
	};
	$(this.keyInput).datagrid(this.setting);
	var pageController = $(this.keyInput).datagrid('getPager');
	$(pageController).pagination(pageSettings);
};

Grid.prototype.getSelectNode = function() {
	var rows = $(this.keyInput).datagrid('getSelected');
	return rows;
};

Grid.prototype.getSelectNodes = function() {
	var rows = $(this.keyInput).datagrid('getChecked');
	return rows;
};

Grid.prototype.reloadGrid = function() {
	$(this.keyInput).datagrid('reload');
};

Grid.prototype.reloadGridById = function(param) {
	$(this.keyInput).datagrid('reload', param);
};

Grid.prototype.reloadGridByData=function(data){
	$(this.keyInput).datagrid('loadData',data);
};

Grid.prototype.doSearch = function(params) {
	$(this.keyInput).datagrid('load', params);
};

/**
 * 合并列
 */
$.extend($.fn.datagrid.methods, {
	autoMergeCells : function (jq, fields) {
		return jq.each(function () {
			var target = $(this);
			if (!fields) {
				fields = target.datagrid("getColumnFields");
			}
			var rows = target.datagrid("getRows");
			var i = 0,
			j = 0,
			temp = {};
			for (i; i < rows.length; i++) {
				var row = rows[i];
				j = 0;
				for (j; j < fields.length; j++) {
					var field = fields[j];
					var tf = temp[field];
					if (!tf) {
						tf = temp[field] = {};
						tf[row[field]] = [i];
					} else {
						var tfv = tf[row[field]];
						if (tfv) {
							tfv.push(i);
						} else {
							tfv = tf[row[field]] = [i];
						}
					}
				}
			}
			$.each(temp, function (field, colunm) {
				$.each(colunm, function () {
					var group = this;
					
					if (group.length > 1) {
						var before,
						after,
						megerIndex = group[0];
						for (var i = 0; i < group.length; i++) {
							before = group[i];
							after = group[i + 1];
							if (after && (after - before) == 1) {
								continue;
							}
							var rowspan = before - megerIndex + 1;
							if (rowspan > 1) {
								target.datagrid('mergeCells', {
									index : megerIndex,
									field : field,
									rowspan : rowspan
								});
							}
							if (after && (after - before) != 1) {
								megerIndex = after;
							}
						}
					}
				});
			});
		});
	}
});
