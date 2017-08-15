Grid = function(title, iconCls, url, key, addFunction, editFunction,
		viewFunction, removeFunction, searchFunction, expFunction, impFunction) {
	this.title = title;
	this.iconCls = iconCls;
	this.url = url;
	this.keyInput = '#' + key;
	this.addFunction = addFunction;
	this.editFunction = editFunction;
	this.viewFunction = viewFunction;
	this.removeFunction = removeFunction;
	this.searchFunction = searchFunction;
	this.expFunction = expFunction;
	this.impFunction = impFunction;
};

Grid.prototype.loadGrid = function() {
	var setting = {
		title : this.title,
		iconCls : this.iconCls,
		nowrap : false,
		striped : true,
		border : true,
		collapsible : false,
		fit : true,
		url : this.url,
		remoteSort : false,
		idField : 'id',
		singleSelect : false,
		pagination : true,
		rownumbers : true,
		frozenColumns : [ [ {
			field : 'ck',
			checkbox : true
		} ] ],

		// 这里要变成从前台进行读取的操作
		toolbar : [ {
			text : '添加',
			iconCls : 'icon-add',
			handler : this.addFunction
		},
		// '-',
		// {
		// text:'查看',
		// iconCls:'icon-search',
		// handler:this.viewFunction
		// },
		// '-',
		// {
		// text:'修改',
		// iconCls:'icon-edit',
		// handler:this.editFunction
		// },
		'-', {
			text : '删除',
			iconCls : 'icon-remove',
			handler : this.removeFunction
		}, '-', {
			text : '查询',
			iconCls : 'icon-search',
			handler : this.searchFunction
		}, '-', {
			text : '导出',
			iconCls : 'icon-search',
			handler : this.expFunction
		}, '-', {
			text : '导入',
			iconCls : 'icon-search',
			handler : this.impFunction
		}

		]
	};

	var pageSettings = {
		pageSize : 10,
		pageList : [ 10, 15, 20, 30 ],
		beforePageText : '第',
		afterPageText : '页    共{pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录  共{total}条记录'
	};
	$(this.keyInput).datagrid(setting);
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
}


