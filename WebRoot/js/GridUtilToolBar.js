Grid = function(title, iconCls, url, key, toolBar) {
	this.title = title;
	this.iconCls = iconCls;
	this.url = url;
	this.keyInput = '#' + key;
	this.toolBar = toolBar;
};

Grid.prototype.loadGrid = function() {
	var frozenColumns =[];
	if(this.isShowCheckbox !=false) {
		frozenColumns[0] = [{field:'ck',checkbox:true}];
	}
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
		loadMsg:"正在努力拉取数据中...",
		fit : true,
	    fitColumns:true,
		frozenColumns : frozenColumns,
		toolbar : this.toolBar,
	
	    onLoadSuccess: function(data){
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
	            if (!data.rows.length) {
	            	var body = $(this).data().datagrid.dc.body2;
	            	body.find('table tbody').append('<tr><td width="' + body.width() + '" style="height:' + body.height() + '; text-align: center;">没有数据</td></tr>');
	            }
	    }
		
	};
	$(this.keyInput).datagrid(setting);
	var pageController = $(this.keyInput).datagrid('getPager');

	var pageSettings = {
		pageSize : 10,
		pageList : [ 10, 15, 20, 30 ],
		beforePageText : '第',
		afterPageText : '页    共{pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录  共{total}条记录',
		buttons :[{
			iconCls:"icon-search",
			handler : function(){
				var num = pageController.data().pagination.bb.num.val();
				pageController.pagination("select",num);
			}
		}]
		
	};
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

