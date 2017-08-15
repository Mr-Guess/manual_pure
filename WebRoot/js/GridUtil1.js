Grid1 = function (title, iconCls, url, key, addFunction, editFunction, viewFunction, removeFunction, searchFunction) {
    this.title = title;
    this.iconCls = iconCls;
    this.url = url;
    this.keyInput = '#' + key;
    this.addFunction = addFunction;
    this.editFunction = editFunction;
    this.viewFunction = viewFunction;
    this.removeFunction = removeFunction;
    this.searchFunction = searchFunction;
};

Grid1.prototype.loadGrid = function() {
    var setting = {
        title:this.title,
        iconCls:this.iconCls,
        nowrap:false,
        striped:true,
        border:true,
        collapsible:false,
        fit:true,
        url:this.url,
        remoteSort:false,
        idField:'id',
        singleSelect:false,
        pagination:true,
        rownumbers:true,
        frozenColumns:[
            [
                {field:'ck', checkbox:true}
            ]
        ],
        toolbar:[
            {
                text:'添加',
                iconCls:'icon-add',
                handler:this.addFunction
            },
            '-',
//            {
//                text:'查看元数据',
//                iconCls:'icon-search',
//                handler:this.viewFunction
//            },
//            '-',
//            {
//                text:'修改',
//                iconCls:'icon-edit',
//                handler:this.editFunction
//            },
//            '-',
            {
                text:'删除',
                iconCls:'icon-remove',
                handler:this.removeFunction
            },
            '-',
            {
                text:'查询',
                iconCls:'icon-search',
                handler:this.searchFunction
            }
        ]
    };

    var pageSettings = {
        pageSize : 10,
        pageList:[10, 15, 20, 30],
        beforePageText:'第',
        afterPageText:'页    共{pages} 页',
        displayMsg:'当前显示 {from} - {to} 条记录  共{total}条记录'
    };
    $(this.keyInput).datagrid(setting);
    var pageController = $(this.keyInput).datagrid('getPager');
    $(pageController).pagination(pageSettings);
};

Grid1.prototype.getSelectNode = function() {
    var rows = $(this.keyInput).datagrid('getSelected');
    return rows;
};

Grid1.prototype.getSelectNodes = function() {
    var rows = $(this.keyInput).datagrid('getChecked');
    return rows;
};

Grid1.prototype.reloadGrid = function() {
	$(this.keyInput).datagrid('reload');
};

Grid1.prototype.reloadGridById = function(param) {
	$(this.keyInput).datagrid('reload',param);
};