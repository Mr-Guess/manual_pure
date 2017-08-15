Grid = function (title, iconCls, url, key,backFunction, addFunction, saveFunction,reload) {
    this.title = title;
    this.iconCls = iconCls;
    this.url = url;
    this.keyInput = '#' + key;
    this.backFunction = backFunction;
    this.addFunction = addFunction;
    this.saveFunction = saveFunction;
    this.reload=reload;
};

Grid.prototype.loadGrid = function() {
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
        pagination:false,
        rownumbers:false,
        singleSelect:true,

        // 这里要变成从前台进行读取的操作
        toolbar:[
        	{
                text:'返回',
                iconCls:'icon-back',
                handler:this.backFunction
            },
            '-',
            {
                text:'新增表字段',
                iconCls:'icon-add',
                handler:this.addFunction
            },
            '-',
            {
                text:'保存',
                iconCls:'icon-save',
                handler:this.saveFunction
            },
            '-',
            {
				id : 'btnup',
				text : '上移',
				iconCls : 'icon-up',
				handler : function() {
					MoveRow(-1);
				}
			}, 
			'-',
			{
				id : 'btnnext',
				text : '下移',
				iconCls : 'icon-down',
				handler : function() {
					MoveRow(1);
				}
			},
			'-',
			{
				id : 'btnnext',
				text : '刷新',
				iconCls : 'icon-reload',
				handler : this.reload
			}
        ],
        onBeforeEdit:function(index,row){
            row.editing = true;
            $("#data_list").datagrid("refreshRow", index);
            editcount++;
        },
        onAfterEdit:function(index,row){
            row.editing = false;
            $("#data_list").datagrid("refreshRow", index);
            editcount--;
        },
        onCancelEdit:function(index,row){
            row.editing = false;
            $("#data_list").datagrid("refreshRow", index);
            editcount--;
        }
    };
	
    var pageSettings = {
        pageSize : 10,
        pageList:[10, 15, 20, 30],
        beforePageText:'第',
        afterPageText:'页    共{pages} 页',
        displayMsg:'当前显示 {from} - {to} 条记录  共{total}条记录'
    };
    
    $(this.keyInput).datagrid(setting);
    //var pageController = $(this.keyInput).datagrid('getPager');
    //$(pageController).pagination(pageSettings);
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

Grid.prototype.reloadGridById = function(id) {
	$(this.keyInput).datagrid('reload', id);
};