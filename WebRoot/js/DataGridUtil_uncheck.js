/**
 * easyui DataGrid的封装类
 * @param url 请求的url
 * @param key div的id
 * @param setting 配置对象（可以重写或添加DataGrid Properties Events）例： {iconCls:'icon-add'}
 * @returns {Grid}
 */
Grid = function(url, key, setting) {
	var temp = this;
	this.url = url;
	this.keyInput = '#' + key;
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
			rownumbers : true
			 
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
