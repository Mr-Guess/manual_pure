<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String jquery_path = request.getContextPath();
	String jquery_basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ jquery_path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定义表字段</title>
<link rel="stylesheet" type="text/css"
	href="<%=jquery_basePath%>resources/js/jquery/easyui-1.3/themes/cupertino/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=jquery_basePath%>resources/js/jquery/easyui-1.3/themes/icon.css">
<script type="text/javascript"
	src="<%=jquery_basePath%>resources/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript"
	src="<%=jquery_basePath%>resources/js/jquery/easyui-1.3/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=jquery_basePath%>resources/js/jquery/easyui-1.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/EntityGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<script type="text/javascript">
        var grid = null;
        var tree = null;
        var key = null;
        var actionUrl=null;
        //获得当前实体id
        var entityId="${param.entityId}";
        
        var dataTypeList = null;
        var nullList = new Array();
        var controlList = null;
        var entityInfo = null;
        

        //判断是否
        var isMove =0;
        //字段类型数组
        var fieldTypeList=[{val:'bigint', text:'数字'},{val:'varchar',text:'文本'},{val:'datetime',text:'日期时间'},{val:'date',text:'日期'}];
        var cType=[];
        var isNullList=[{isNull:"null", val:"1"},{isNull:"not null",val:"0"}];
       	var isPkList=[{isPk:'主键', val:"1"}, {isPk:'非主键', val:"0"}];
        //当前的datagrid对象
        var dataList;
        
        if(entityId==""){
        	actionUrl="";//没有实体id就是新增，不与服务器交互
        }else{
        	actionUrl='${ctx}/entity/entityFieldAction!pageList.action?entityId=${param.entityId}';
        }
        
        var insertList=new Array();
        
        $(document).ready(
                function () {
                	// 这一步就是为了用javascript进行,定义窗口
                	$('#addEntity').window({
                		width:382,
                        height:385,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                	});
                	
                	$('#addEntity').window("close");
                    grid = new Grid('表详细信息[表名:${param.entityCode}]', 'icon-edit',
                            actionUrl,
                            'data_list', backFunction, addrow,
                            saveFunction,reload);
                    grid.loadGrid();
                    dataList=$("#data_list");
                    var options = dataList.datagrid("options");
                    options.onClickRow=clickRow;
                    $.ajax({
                    	dataType:"json",
                    	url:"${ctx}/entity/entityFieldAction!getAllDataType.action",
                    	type:"post",
                    	success:function(ret){
                    		dataTypeList=ret;
                    	}
                    });
                    
                    $.ajax({
                    	dataType:"json",
                    	url:"${ctx}/entity/entityFieldAction!getAllDataListByType.action",
                    	type:"post",
                    	success:function(ret){
                    		controlList=ret;
                    	}
                    });
                    $.ajax({
                    	dataType:"json",
                    	url:"${ctx}/entity/entityAction!getEntityById.action",
                    	data:"id="+entityId,
                    	type:"post",
                    	success:function(ret){
                    		entityInfo=ret;
                    	}
                    });
                    $("#dataWin").window({
                		width:520,
                		height:350,
                		modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                	});
                	$("#dataWin").window("close");
                });
        
        /** 单击行事件*/
        function clickRow(index, row){
        }
        //返回上一页
        function backFunction(){
        	window.location.href="declareEntity.jsp";
        }
        //当新增表时触发
        function addFunction() {
	       $('#addEntity').window("open");
    	}
        //清除弹出框数据
        function clearWin(){
        	$("#addEntityCode").val("");
			$("#addEntityName").val("");
        }
        
		//保存更改
        function saveFunction(){
        	if (editcount > 0){
                $.messager.alert("警告","当前还有"+editcount+"记录正在编辑，不能保存。");
                return;
            } 
        	var cccc = dataList.datagrid("getChanges");
        	if(!cccc.length&&isMove==0){
				$.messager.alert("警告", "您没有修改任何数据");        		
        		return;
        	}       	
        	//新增的数据对象列表
        	var inserted = dataList.datagrid("getChanges","inserted");
        	//更新的数据对象列表
        	var updated = dataList.datagrid("getChanges","updated");
        	//删除的数据对象列表
        	var deleted = dataList.datagrid("getChanges","deleted");
        	//被改变所有数据对象列表
        	var changes = new Object();
//         	if(isMove == 1){
        	changes.rows = dataList.datagrid("getRows");
//         	}       	
        	changes.inserted = inserted;
        	changes.updated = updated;
        	changes.deleted = deleted;        	       	
        	var changesJson = JSON.stringify(changes);
        	$.ajax( {
				type : 'POST',
				url : '${ctx}/entity/entityFieldAction!updateTable.action?entity.id='+entityId+"&entity.entityCode="+entityInfo.entityCode,
				data :'changesJson='+ changesJson,
				success : function(ret) {
					var data = eval('(' + ret + ')');
					if(data.operateSuccess){
						$.messager.show({
                            title:'成功',
                            msg:data.operateMessage,
                            timeout:2000,
                            showType:'slide'
                        });
					}else{
						$.messager.show({
                            title:'失败',
                            msg:data.operateMessage,
                            timeout:2000,
                            showType:'slide'
                        });
					}
                    grid.reloadGrid();
				}
			});
            isMove = 0;
        }
		
		//获得实体信息
		function getEntityInfo(){
			var entity=entityInfo;
			var list=new Array();
			list.push(entity);
			FieldTemplate.entity=list;
		}
		/** 重置datagrid*/
		function reload(){
			grid.reloadGrid();
		}
		
       	/** 渲染操作按钮*/
        function gridFormatter(value, rowData, rowIndex) {
       		if(rowData.controlType=="outkey_relate"){
       			return;
       		}
        	if (rowData.editing){
                var s = "<a href='#' onclick='saverow("+rowIndex+")'>确认</a> ";
                var c = "<a href='#' onclick='cancelrow("+rowIndex+")'>取消</a>";
                return s+c;
            } else {
                var e = "<a href='#' onclick='editrow("+rowIndex+")'>修改</a> ";
                var d = "<a href='#' onclick='deleterow("+rowIndex+")'>删除</a>";
                return e+d;
            }
        }
        
        var editcount = 0;
        function editrow(index){
        	dataList.datagrid("beginEdit", index);
        	var row = $("#data_list").datagrid("getRows")[index];      	
        	if(row.fieldType=="int"){
        		var fieldLength = $("#data_list").datagrid("getEditor",{index:index,field:'fieldLength'});
        		$(fieldLength.target).numberbox("disable");
        	}
        	row = null;
        }
        function deleterow(index){
            $.messager.confirm("确认","是否真的删除?",function(r){
                if (r){
                    $("#data_list").datagrid("deleteRow", index);
                }
            });
        }
        //直接删除行，别提示
        function fastdeleterow(index){
        	dataList.datagrid("deleteRow",index);
        }
        //保存行
        function saverow(index){
        	var ctEdit = dataList.datagrid("getEditor", {index:index, field:"controlType"});
        	var dtEdit = dataList.datagrid("getEditor", {index:index, field:"dataTypeId"});
        	var nv = $(ctEdit.target).combotree("getValue");
        	var dtv = $(dtEdit.target).combobox("getValue");
        	if((nv=="pulltexttype"||nv=='multicheckboxtype'||nv=="onecheckboxtype")&&dtv==""){
        		$(ctEdit.target).combotree("setValue",null);
        	}
        	dataList.datagrid("endEdit", index);
//         	var rows=dataList.datagrid("getRows");
//         	var row=rows[index]; 
        	//验证只能有一个主键
        }
        
        function cancelrow(index){
        	var rows=dataList.datagrid("getRows");
        	var row=rows[index];
         	var num=0;
        	for(var r in row){
        		if(Boolean(row[r])){
        			num++;
        		}
         	}
         	if(num<3){        		
        		fastdeleterow(index);
        	}else{
        		$("#data_list").datagrid("cancelEdit", index);
        	}
        }
        
        //添加行
        var lastIndex;
        function addrow(){
        	lastIndex=dataList.datagrid("getRows").length-1;
        	var num=lastIndex+2;
            if (editcount > 0){
                $.messager.alert("警告","当前还有"+editcount+"记录正在编辑，不能增加记录。");
                return;
            } 
            dataList.datagrid("appendRow",{
                	entityOrder:num.toString()
            });
            lastIndex=dataList.datagrid("getRows").length-1;
            dataList.datagrid('beginEdit',lastIndex);
            
        }
        function saveall(){
        	dataList.datagrid("acceptChanges");
        }
        function cancelall(){
        	dataList.datagrid("rejectChanges");
        }
        
        function closeAllWin(){
        	$("#addEntity").window("close");
        }
        
        //移动行
        function MoveRow(fx) {
        	 if (editcount > 0){
                 $.messager.alert("警告","当前还有"+editcount+"记录正在编辑，不能移动。");
                 return;
             } 
        	var gd = dataList;
        	var gdData = gd.datagrid("getData");//获得所有数据
        	if (gdData.rows.length < 2)//只有一行无法移动
        		return;
        	var iMovedRowIndex = -1;
        	var FocusRow = gd.datagrid('getSelected'); //获得选中行
        	if (!FocusRow) {
        		$.messager.alert("提示","没有行被选中,无法执行移动操作");
        		return;
        	}
        	var iCurRowIndex = gd.datagrid('getRowIndex', FocusRow);//获得选中行的索引
        	iMovedRowIndex = iCurRowIndex + fx;//获得移动目标行
        	if (iCurRowIndex === 0 && fx === -1)//如果选中的是第一行，并向上移动
        		iMovedRowIndex = gdData.rows.length - 1;
        	if (iCurRowIndex === gdData.rows.length - 1 && fx === 1)//如果选中的是最后一行，并向下移动
        		iMovedRowIndex = 0;
        	var RowTemp;
        	var orderCur;
        	var orderMov;
        	RowTemp = gdData.rows[iMovedRowIndex];
        	orderCur = gdData.rows[iCurRowIndex].entityOrder;
        	orderMov = gdData.rows[iMovedRowIndex].entityOrder;
        	gdData.rows[iMovedRowIndex] = gdData.rows[iCurRowIndex];
        	gdData.rows[iMovedRowIndex].entityOrder=orderMov;
        	gdData.rows[iCurRowIndex]=RowTemp;
        	gdData.rows[iCurRowIndex].entityOrder=orderCur;
        	gd.datagrid('unselectRow', iCurRowIndex);
        	gd.datagrid('selectRow', iMovedRowIndex);
			gd.datagrid('refreshRow',iCurRowIndex);
			gd.datagrid('refreshRow',iMovedRowIndex);
			isMove = 1;
        	gd = null;
        	RowTemp=null;
        	OrderTemp=null;
        }
        
        function showIsNull(value, rowData, rowIndex){
        	for(var va in isNullList){
        		if(isNullList[va].val==value){
        			return isNullList[va].isNull;
        		}
        	}
        }
        
        function showIsPk(value){
        	for(var va in isPkList){
        		if(isPkList[va].val==value){
        			return isPkList[va].isPk;
        		}
        	}
        }
        
        /** 渲染元数据类型*/
        function showDataType(val, row, indx){
        	for(var i in dataTypeList){
        		if(dataTypeList[i].id==val){
        			return dataTypeList[i].typeName;
        		}
        	}
        }
        
        /** 选中控件的当前行的id*/
        var selectControlIndex;
        
        /** 选择控件时判断*/
        function onChangeControlType(nv, ov){
        	var v =$(this);
        	var index = v.parents("tr.datagrid-row").attr("datagrid-row-index"); 
        	var row = $("#data_list").datagrid("getRows")[index];
        		     	
           	if(nv=="pulltexttype"||nv=='multicheckboxtype'||nv=="onecheckboxtype"){
           		if(row.controlType!=nv){
	     			showDataWin();
	      			selectControlIndex = index;
           		}
         	}else{
	           	var dataTypeIdEditor = $("#data_list").datagrid("getEditor", {index:index,field:"dataTypeId"});
	           	$(dataTypeIdEditor.target).combobox("setValue", "");
	           	row.controlType = nv;
      		}
			if(nv =="datetext"){
				var fieldTypeEditor = $("#data_list").datagrid("getEditor",{index:index,field:"fieldType"});
				$(fieldTypeEditor.target).combobox("setValue","date");
				fieldTypeEditor = null;
				row.controlType = nv;
			}
			if(nv == "datetimetext"){
				var fieldTypeEditor = $("#data_list").datagrid("getEditor",{index:index,field:"fieldType"});
				$(fieldTypeEditor.target).combobox("setValue","datetime");
				fieldTypeEditor = null;
				row.controlType = nv;
			}
        }
        
        /** 显示元数据选择窗体*/
        function showDataWin(index){
    		$("#dataWin").window("open");
    		$("#dataWin").window("options").
    		onClose=function(){
    			var row = $("#data_list").datagrid("getRows")[selectControlIndex];
	   			var selectValue = row.controlType;
	   			var controlTypeEditor = $("#data_list").datagrid("getEditor",{index:selectControlIndex,field:"controlType"});
	   			if(!selectValue){
	   				selectValue = "";    			
	   			}
  				$(controlTypeEditor.target).combotree("setValue", selectValue );
	   			var t = $(controlTypeEditor.target).combotree("tree");
	   			var node = t.tree("find", selectValue);
	   			t.tree("select", node.target);	    			
				
    		};
    		sourceDataGrid(index);
        }
        
       
        
        /** 选中元数据类型*/
        function selectData(o){
        	var controlTypeEditor = $("#data_list").datagrid("getEditor",{index:selectControlIndex,field:"controlType"});
        	var dataTypeIdEditor = $("#data_list").datagrid("getEditor",{index:selectControlIndex,field:"dataTypeId"});       	
        	$(dataTypeIdEditor.target).combobox("setValue", o.value);
        	var row = $("#data_list").datagrid("getRows")[selectControlIndex];
        	row.controlType = $(controlTypeEditor.target).combotree("getValue");
        	$("#dataWin").window("minimize");
        }
        
        
        /** 渲染controlType列*/
        function showControlType(val, row, index){
        	for(var i in controlList){
        		if(controlList[i].dataCode==val){
        			return controlList[i].dataName;
        		}
        	}
        }
        

        /** 渲染combobox显示值*/
        function comboDataType(row){
			return row.text;
        }
        
        /** 渲染dataType列的显示*/
        function showFieldType(val, row, index){
        	if(val){
        		for(var i in fieldTypeList){
            		var str = fieldTypeList[i].val.toLowerCase();
            		if(str==val.toLowerCase()){
            			return fieldTypeList[i].text;
            		}
            	}
        	}
        	
        }
        
        /** 根据选中的字段类型判断长度*/
        function selectedFieldType(val){
        	var v =$(this);
        	var index = v.parents("tr.datagrid-row").attr("datagrid-row-index");
    		var fieldLength = $("#data_list").datagrid("getEditor",{index:index,field:'fieldLength'});
    		var tar = $(fieldLength.target);
    		var row = dataList.datagrid("getRows")[index];
        	if(val.val=="int"||val.val=="datetime"||val.val=="date"){        		
        		tar.val("");
        		tar.numberbox("disable");        		
        	}else{
        		tar.val(row.fieldLength);
        		tar.numberbox("enable");
        	}
        	var controlType = $("#data_list").datagrid("getEditor",{index:index,field:'controlType'});
        	if(val.val=="datetime"||val.val=="date"){        		
        		$(controlType.target).combotree("reload","${ctx}/entity/entityFieldAction!getAllDataTreeByType.action?dataCode=datatimetype");
        	}else{
        		$(controlType.target).combotree("reload","${ctx}/entity/entityFieldAction!getAllDataTreeByType.action");
        	}
        	index = null;
        	tar = null;
        	v = null;
        	row = null;
        }
        
        /** 根据字段类型判断控件类型*/
        function onChangeFieldType(nv, ov){
        	var v =$(this);
        	//获得当前编辑框所在行索引
        	var index = v.parents("tr.datagrid-row").attr("datagrid-row-index");
        	var controlType = $("#data_list").datagrid("getEditor",{index:index,field:'controlType'});
        	var fieldLength = $("#data_list").datagrid("getEditor",{index:index,field:'fieldLength'});
    		var tar = $(fieldLength.target);
    		var row = dataList.datagrid("getRows")[index];
        	if(nv=="int"||nv=="datetime"||nv=="date"){        		
        		tar.val("");
        		tar.numberbox("disable");
        	}else{
        		tar.val(row.fieldLength);
        		tar.numberbox("setValue","32");
        		tar.numberbox("enable");
        	}
        	if(nv=="datetime"||nv=="date"){
        		$(controlType.target).combotree("reload","${ctx}/entity/entityFieldAction!getAllDataTreeByType.action?dataCode=datatimetype");
        		if(nv=="datetime"){
        			$(controlType.target).combotree("setValue", "datetimetext");
        		}else{
        			$(controlType.target).combotree("setValue", "datetext");
        		}
        	}else if(nv!=ov){
        		$(controlType.target).combotree("reload","${ctx}/entity/entityFieldAction!getAllDataTreeByType.action");
        		$(controlType.target).combotree("setValue", row.controlType);
        	}
        	nv=null;
        	ov=null;
        	v=null;
        	index=null;
        	controlType=null;
        }
        
        /** 渲染数字输入框*/
        function showFieldLength(val, row, index){
        	if(val!="0"){
        		return val;
        	}else{
        		row.fieldLength="";
        	}
        }
        
        function clickTree(node){
        }
        
        /** 渲染单选框*/
        function showDataRadio(val, row, index){
        	return "<input type='radio' value='"+val+"' name='dataTypeId' onClick='selectData(this,"+index+")'/ >";
        }
        
        /** 获取dataType的分页数据，并且创建datagrid*/
        function sourceDataGrid(){
        	$("#sourceDataTable").datagrid({
        		title : "元数据类型",
        		singleSelect : true,
        		iconCls : "icon-search",
        		nowrap : false,
        		striped : true,
        		border : true,
        		collapsible : false,
        		fit : true,
        		remoteSort : false,
        		url:"${ctx}/entity/entityFieldAction!getAllDataTypeCause.action",
        		rownumbers:true,
        		pagination:true,
        		width: 500,
        		idField:'id',
        		pagePosition:'bottom'
        	});
        }
        
        /** 条件搜索元数据*/
        function searchDataType(){
        	var remark = document.getElementById("txtRemark");
        	$("#sourceDataTable").datagrid("reload", {typeName:remark.value});
        }
        
        function beforeSelectControlType(node) {
        	var nv = node.id;
        	if(nv=="poptexttype"||nv=="datatimetype"||nv=="affixtype"||nv=="texttype"){
        		$.messager.show({
        			title:'提示',
                    msg:"只能选择子节点",
                    timeout:2000,
                    showType:'slide'
        		});
        		return false;
        	}
        }
        
</script>
</head>
<body>
	<div style="height: 500px">

		<table id="data_list" loadMsg="正在努力拉取数据中..." fit="true"
			fitColumns="true">
			<thead>
				<tr>
					<th field="entityOrder" width="150" title="实体名称" align="center">序号</th>
					<th field="fieldCode" width="150" title="实体名称" align="center"
						editor="{type:'validatebox',
            			options:{required:true,
            					validType:'length[3,30]',
            					invalidMessage:'请输入长度为3到30的字符'}}">字段名称</th>
					<th field="fieldName" width="150" title="实体含义" align="center"
						editor="{type:'validatebox',
            			options:{required:true,
            					validType:'length[2,30]',
            					invalidMessage:'请输入长度为3到30的字符'}}">字段含义</th>
					<th field="fieldType" width="150" title="实体类型" align="center"
						editor="{type:'combobox',
            			options:{valueField:'val',
            					textField:'text',
            					data:fieldTypeList,
            					required:true,
            					editable:false,
            					formatter:comboDataType,
            					onSelect:selectedFieldType,
            					onChange:onChangeFieldType}}"
						formatter="showFieldType">字段类型</th>
					<th field="fieldLength" width="150" title="实体类型" align="center"
						editor="{type:'numberbox',options:{precision:0,max:8000,min:1,value:20}}"
						formatter="showFieldLength">字段长度</th>
					<th field="defaultValue" width="150" title="默认值" editor="text"
						align="center">缺省值</th>
					<th field="isNull" width="150" title="是否为空" align="center"
						editor="{type:'combobox',
            			options:{valueField:'val',
            				textField:'isNull',
            				data:isNullList,
            				required:true,
            				editable:false}}"
						formatter="showIsNull">可否为空</th>
					<th field="controlType" width="250" title="控件id" align="center"
						editor="{type:'combotree',
            			options:{
            					onChange:onChangeControlType,
            					onBeforeSelect:beforeSelectControlType,
            					editable:false,           
            					url:'${ctx}/entity/entityFieldAction!getAllDataTreeByType.action',
            					onSelect:clickTree}}"
						formatter="showControlType">控件类型</th>
					<th field="dataTypeId" width="150" title="元数据" align="center"
						editor="{type:'combobox',
            			options:{editable:false,
            					disabled:true,            					
            					textField:'typeName',
            					valueField:'id',
            					url:'${ctx}/entity/entityFieldAction!getAllDataType.action'}}"
						formatter="showDataType">元数据类型</th>
					<th field="id" width="120" title="实体id" formatter="gridFormatter"
						align="center">操作</th>
				</tr>
			</thead>
		</table>
	</div>

	<div id="dataWin">
		<table id="sourceDataTable" toolbar="#search">
			<thead>
				<tr>
					<th field="typeName" width="150" title="元数据类型">元数据类型</th>
					<th field="id" width="150" title="实体名称" formatter="showDataRadio">选择</th>
				</tr>
			</thead>
		</table>
		<div id="search" style="padding-top: 3px;">
			描述<input type="text" id="txtRemark" style="width: 80px"></input> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-search" plain="true" onclick="searchDataType();">查询</a>
		</div>
	</div>
</html>