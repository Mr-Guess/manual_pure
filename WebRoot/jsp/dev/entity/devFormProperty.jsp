<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String jquery_path = request.getContextPath();
	String jquery_basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ jquery_path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="<%=jquery_basePath%>resources/js/jquery/easyui-1.3/themes/cupertino/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=jquery_basePath%>resources/js/jquery/easyui-1.3/themes/icon.css">
<script type="text/javascript"
	src="<%=jquery_basePath%>resources/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript"
	src="<%=jquery_basePath%>resources/js/jquery/easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="<%=jquery_basePath%>resources/js/jquery/easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/validator.js"></script>
<title>定义表单属性</title>
<script type="text/javascript">
	var formType = "${param.formType}";      
	var grid = null;
	var dt = null;
	var formId = "${param.formId}";
	var actionUrl = '';
	if(Boolean(formId)){
		actionUrl = "${ctx}/form/formPropertyAction!pageList?formId="+formId;
	}
	var lastIndex;
	var editCount = 0;
	$(document).ready(
            function () {
            	dt=$("#data_list");
    	        dt.datagrid({
    	        	title:'表单属性定义[表单名:${param.formName}]',
    	        	iconCls:'icon-edit',
	        		width:700,
					height:450,
					nowrap: true,
					autoRowHeight: false,
					striped: true,
					collapsible:true,
					url:actionUrl,
					remoteSort: false,
					singleSelect:true,
					idField:'menuId',
					//pagination:true,
					rownumbers:false,
					sortName:"formOrder",
					sortOrder:"asc",
					toolbar:[{
						id:'back',
						text:'返回',
						iconCls:'icon-back',
						handler:goBack
					},'-',{
						id:'btncut',
						text:'重置',
						iconCls:'icon-reload',
						handler:reload
					},'-',{
						id:'btnsave',
						text:'保存',
						iconCls:'icon-save',
						handler:saveOpt
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
					}
					],
			        onBeforeEdit:function(index,row){
			            row.editing = true;
			            dt.datagrid("refreshRow", index);
			            var editRow = dt.datagrid("getEditors",index)[1];
			            
			        },
			        onAfterEdit:function(index,row){
			            row.editing = false;
			            dt.datagrid("refreshRow", index);
			        },
			        onCancelEdit:function(index,row){
			            row.editing = false;
			            dt.datagrid("refreshRow", index);
			        }
// 			        ,
// 			        onDblClickRow:function(index,row){
// 						if (lastIndex != index){
// 							dt.datagrid('endEdit', lastIndex);							
// 						}
// 						dt.datagrid('beginEdit', index);
// 						lastIndex = index;
// 					}
			     
    	        });
    	        
            });
	
	//新增一行权限
	function addOpt(){
		var count=dt.datagrid("getRows").length;
		dt.datagrid("appendRow",{
			optOrder:count+1
		});
		dt.datagrid("endEdit", lastIndex);
		dt.datagrid("beginEdit", count);
	}
	
	/** 重置数据*/
	function reload(){
		dt.datagrid("reload");
	}
	
	function goBack(){
		if(dt.datagrid("getChanges").length){
			$.messager.confirm("确认","你有修改没有保存，确认要离开页面吗？",function(r){
				if(r){
					document.location.href="devForm.jsp";
				}else{
					return;
				}
			});
		}else{
			document.location.href="devForm.jsp";
		}		
	}
	
	//删除一行权限
	function cutOpt(){
		var focusRow=dt.datagrid("getSelected");
		var focusIndex=dt.datagrid("getRowIndex", focusRow);
		if(focusIndex==-1){
			$.messager.alert("提示","你没有选中行");
		}else{
			$.messager.confirm("确认","是否真的删除?",function(r){
                if (r){
                	dt.datagrid("deleteRow",focusIndex);
					var rs=dt.datagrid("getRows");
					var k=focusIndex;
					for(k;k<rs.length;k++){
						rs[k].optOrder=k+1;
						dt.datagrid("refreshRow",k);
					}
                }
            });		
		}						
	}
	
	/** 保存数据*/
	function saveOpt(){
		var rows=dt.datagrid("getRows");
		var flag=true;
		for(var i=0;i<rows.length;i++){
			var row=rows[i];
			dt.datagrid("endEdit",i);
			if(row.editing){
				flag=false;
			}
		}
		if(flag){
			if(dt.datagrid("getChanges").length){
				var updated = dt.datagrid("getChanges","updated");
				var updatedList = null;
				if(updated.length){
					updatedList = JSON.stringify(updated);
					var re1 = /\%/g;
					updatedList = updatedList.replace(re1,"$");
				}
				$.ajax({
					url:"${ctx}/form/formPropertyAction!commit.action",
					type:"post",
					data:"updated="+updatedList,
					success:function(rsp){
						if(rsp){
							dt.datagrid("acceptChanges");
							$.messager.alert("提示","提交成功");							
						}else{
							$.messager.alert("提示","提交失败");
						}
					}
				});
			}else{
				$.messager.alert("提示","没有修改任何数据");
			}
		}else{
			$.messager.alert("警告","请填写完整");
		}
				
	}
	
	//移动行
    function MoveRow(fx) {
    	var gd = dt;
    	var gdData = gd.datagrid("getData");//获得所有数据
    	if (gdData.rows.length < 2)//只有一行无法移动
    		return;
    	var iMovedRowIndex = -1;
    	var FocusRow = gd.datagrid('getSelected'); //获得选中行
    	if (!FocusRow) {
    		$.messager.alert("提示","没有行被选中,无法执行移动操作");
    		return;
    	}
    	if(editCount>0){
    		$.messager.alert("提示","还有"+editCount+"行在编辑,无法执行移动操作");
    		return;
    	}
    	var iCurRowIndex = gd.datagrid('getRowIndex', FocusRow);//获得选中行的索引
    	if(iCurRowIndex==lastIndex){
    		gd.datagrid('endEdit',iCurRowIndex);
    	}
    	iMovedRowIndex = iCurRowIndex + fx;//获得移动目标行
    	if (iCurRowIndex === 0 && fx === -1)//如果选中的是第一行，并向上移动
    		iMovedRowIndex = gdData.rows.length - 1;
    	if (iCurRowIndex === gdData.rows.length - 1 && fx === 1)//如果选中的是最后一行，并向下移动
    		iMovedRowIndex = 0;
    	var RowTemp;
    	RowTemp = gdData.rows[iMovedRowIndex];
    	gdData.rows[iMovedRowIndex] = gdData.rows[iCurRowIndex];
    	gdData.rows[iMovedRowIndex].formOrder=iMovedRowIndex+1;
    	gdData.rows[iCurRowIndex]=RowTemp;
    	gdData.rows[iCurRowIndex].formOrder=iCurRowIndex+1;
    	gd.datagrid('unselectRow', iCurRowIndex);
    	gd.datagrid('selectRow', iMovedRowIndex);
		gd.datagrid('refreshRow',iCurRowIndex);
		gd.datagrid('refreshRow',iMovedRowIndex);
    	gd = null;
    	RowTemp=null;
    	OrderTemp=null;
    }
	
	
	function getOptType(value, rowData, rowIndex){
		for(var i in optType){
			if(value==optType[i].val){
				return optType[i].text;
			}
		}
	}
	function selected(value,oldValue){
//			for(var a in optType){
//				if(optType[a].val==value.val){
//					optType.splice(a,1);						
//				}					
//			}
//			alert(JSON.stringify(optType)+"===="+JSON.stringify(value));
	}
	
	var editType=[{val:'0',text:'只读'},{val:'1',text:'可写'},{val:'2',text:'不显示'}];
	var editType2=[{val:'0',text:'只读'},{val:'1',text:'可写'}];
	var orderType = [{val:'0',text:'不排序'},{val:'1',text:'排序'}];
	var searchType = [{val:'0',text:'模糊查询'},{val:'1',text:'等于'},{val:'2',text:'小于'},{val:'3',text:'小于等于'},{val:'4',text:'大于'},{val:'5',text:'大于等于'},{val:'6',text:'范围'}];
	var filterType = [{val:'0',text:'模糊查询'},{val:'1',text:'等于'},{val:'2',text:'小于'},{val:'3',text:'小于等于'},{val:'4',text:'大于'},{val:'5',text:'大于等于'}];
	var alignType = [{val:'0',text:'右对齐'},{val:'1',text:'左对齐'},{val:'2',text:'居中'}];
	var requiredType = [{val:'0',text:'不必输'},{val:'1',text:'必输'}];
	function initAlign(value, rowData, rowIndex){
		var flag = 0;
		for(var i in alignType){
			if(alignType[i].val==value){
				flag = 1;
				return alignType[i].text;
			}
		}
		if(flag==0){
			dt.datagrid("getRows")[rowIndex].algin='1';
			return alignType[1].text;
		}
	}
	function initEditType(value, rowData, rowIndex){
		var flag = 0;
		for(var i in editType){
			if(editType[i].val==value){
				flag = 1;
				return editType[i].text;
			}
		}
		if(flag==0){
			dt.datagrid("getRows")[rowIndex].editType='1';
			return editType[1].text;
		}
	}
	
	function loadEditType(){
		var v = $(this);
		var index = v.parents("tr.datagrid-row").attr("datagrid-row-index"); 
		var row = dt.datagrid("getRows")[index];
		if(row.isMust=="1"){
			v.combobox("loadData", editType2);
		}else{
			v.combobox("loadData", editType);
		}
	}
	
	function initRequired(value, rowData, rowIndex){		
		var row = dt.datagrid("getRows")[rowIndex];
		var isMust = row.isMust;
// 		if('1'==isMust){
// // 			dt.datagrid("getRows")[rowIndex].editType='1';			
// // 			return requiredType[1].text;
// 		}		
		for(var i in requiredType){
			if(requiredType[i].val==value){
				flag = 1;
				return requiredType[i].text;
			}
		}
		dt.datagrid("getRows")[rowIndex].editType='0';
		return requiredType[0].text;
	}
	
	function initOrderType(value, rowData, rowIndex){
		var flag = 0;
		for(var i in orderType){
			if(orderType[i].val==value){
				flag = 1;
				return orderType[i].text;
			}
		}
		if(flag==0){
			dt.datagrid("getRows")[rowIndex].orderType='0';
			return orderType[0].text;
		}
	}
	
	function initFilterType(value, rowData, rowIndex){
		var flag = 0;
		for(var i in filterType){
			if(filterType[i].val==value){
				flag = 1;
				return filterType[i].text;
			}
		}
		if(flag==0){
			dt.datagrid("getRows")[rowIndex].filterType='0';
			return filterType[0].text;
		}
	}
	function initSearchType(value, rowData, rowIndex){
		var flag = 0;
		for(var i in searchType){
			if(searchType[i].val==value){
				flag = 1;
				return searchType[i].text;
			}
		}
		if(flag==0){
			dt.datagrid("getRows")[rowIndex].searchType='0';
			return searchType[0].text;
		}
	}
	function initIsSearch(value, rowData, rowIndex){
		if(value){
			if(value=='0'){
				return '否';
			}
			if(value=='1'){
				return '是';
			}
		}
		else{
			dt.datagrid("getRows")[rowIndex].required='0';
			return '否';
		}
	}
	function initIsView(value, rowData, rowIndex){
		if(value){
			if(value=='0'){
				return '否';
			}
			if(value=='1'){
				return '是';
			}
		}
		else{
			dt.datagrid("getRows")[rowIndex].isView='0';
			return '否';
		}
	}
	
	function checkRequired(row){
		var opts = $(this).combobox('options');
		opts['disabled']=true;
	}
	function initIsSearchChange(value){
		var rowIndex = $(this).parents('tr').parents('tr').attr('datagrid-row-index');
		//alert(rowIndex);
		var ed = $('#data_list').datagrid('getEditor', {index:rowIndex,field:'searchType'});
		if(value=='0'){
			$(ed.target).combobox('disable');
		}else{
			$(ed.target).combobox('enable');
		}
	}
	function initIsViewChange(value){
		var rowIndex = $(this).parents('tr').parents('tr').attr('datagrid-row-index');
		//alert(rowIndex);
		var ed1 = $('#data_list').datagrid('getEditor', {index:rowIndex,field:'orderType'});
		var ed2 = $('#data_list').datagrid('getEditor', {index:rowIndex,field:'width'});
		var ed3 = $('#data_list').datagrid('getEditor', {index:rowIndex,field:'algin'});
		var ed4 = $('#data_list').datagrid('getEditor', {index:rowIndex,field:'isSearch'});
		var ed5 = $('#data_list').datagrid('getEditor', {index:rowIndex,field:'searchType'});
		//var ed6 = $('#data_list').datagrid('getEditor', {index:rowIndex,field:'relatedCode'});
		if(value=='0'){
			$(ed1.target).combobox('disable');
			$(ed2.target).attr('disabled','disabled');
			$(ed3.target).combobox('disable');
			$(ed4.target).combobox('disable');
			$(ed5.target).combobox('disable');
		}else{
			$(ed1.target).combobox('enable');
			$(ed2.target).removeAttr('disabled');
			$(ed3.target).combobox('enable');
			$(ed4.target).combobox('enable');
			$(ed5.target).combobox('enable');
			//这里让searchType重新初始化
			var oldValue = $(ed4.target).combobox('getValue');
			$(ed4.target).combobox('clear');
			$(ed4.target).combobox('setValue',oldValue);
		}
	}
	
	/** 渲染操作列*/
	function operateFormatter(val, row, index){

    	if (row.editing){
            var s = "<a href='#' onclick='saverow("+index+")'>确认</a> ";
            var c = "<a href='#' onclick='cancelrow("+index+")'>取消</a>";
            return s+c;
        } else {
            var e = "<a href='#' onclick='editrow("+index+")'>修改</a> ";
            return e;
        }
	}
	
	/** 保存行*/
	function saverow(index){
		selectRow(index);
		dt.datagrid("endEdit", index);
		if(dt.datagrid("getRows")[index].editing){
			return;
		}
		editCount--;
	}
	
	/** 取消编辑*/
	function cancelrow(index){
		selectRow(index);
		dt.datagrid("cancelEdit", index);
		editCount--;
	}
	
	/** 选中行*/
	function selectRow(index){
		if (lastIndex != index){
			if(lastIndex){
				dt.datagrid('unselectRow', lastIndex);							
			}
		}
		dt.datagrid('selectRow', index);
		lastIndex = index;
	}
	
	/** 编辑行*/
	function editrow(index){
		editCount++;
		selectRow(index);
		dt.datagrid("beginEdit", index);
		var editCom = dt.datagrid("getEditors",index)[9];
		var row = dt.datagrid("getRows")[index];
		if(row.isRelation){
			$(editCom.target).combobox("reload","${ctx}/form/formPropertyAction!fieldCodeList.action?formId="+formId);
		}else{
			$(editCom.target).combobox("disable");
		}						
		var editRow = dt.datagrid("getEditors",index)[2];
    	var isMust = row.isMust;
    	if("1"==isMust){
    		$(editRow.target).combobox("setValue", '1');
			$(editRow.target).combobox("disable");
    	}
	}
	

</script>
</head>
<body>
	<div style="height: 500">
		<table id="data_list" loadMsg="正在努力拉取表单属性中..." fit="true"
			fitColumns="true">
			<thead>
				<tr>
					<th align="center" colspan="5">公共属性</th>
					<th align="center" colspan="2">编辑属性</th>
					<th align="center" colspan="7">列表属性</th>
					<th align="center" colspan="1">操作</th>
				</tr>
				<tr>
					<th field="formOrder" title="" width="150" align="center">序号</th>
					<th field="fieldCode" title="" width="150" align="center">字段名称</th>
					<th field="fieldType" title="" width="150" align="center">字段类型</th>
					<th field="viewName" title="" width="150" align="center"
						editor="{type:'validatebox',options:{required:true}}">显示名称</th>
					<th field="defaultValue" title="" width="150" align="center">默认值</th>
					<th field="editType" title="" width="150" align="center"
						editor="{type:'combobox',options:{required:true,data:editType,valueField:'val',textField:'text',editable:false,onBeforeLoad:loadEditType}}"
						formatter="initEditType">编辑状态</th>
					<th field="required" title="" width="150" align="center"
						editor="{type:'combobox',options:{required:true,data:requiredType,valueField:'val',textField:'text',editable:false}}"
						formatter="initRequired">必输</th>
					<th field="isView" title="" width="150" align="center"
						editor="{type:'combobox',options:{data:[{val:'1',text:'是'},{val:'0',text:'否'}]
						,valueField:'val',textField:'text',deitable:false,onChange:initIsViewChange}}"
						formatter="initIsView">是否显示</th>
					<th field="orderType" title="" width="150" align="center"
						editor="{type:'combobox',options:{required:true,data:orderType,valueField:'val',textField:'text',editable:false}}"
						formatter="initOrderType">是否排序</th>
					<th field="width" title="" width="150" align="center"
						editor="{type:'validatebox',options:{validType:'widthStr'}}">列宽</th>
					<th field="algin" title="" width="150" align="center"
						editor="{type:'combobox',options:{required:true,data:alignType,valueField:'val',textField:'text',deitable:false}}"
						formatter="initAlign">对齐</th>
					<th field="isSearch" title="" width="150" align="center"
						editor="{type:'combobox',options:{data:[{val:'1',text:'是'},{val:'0',text:'否'}]
						,valueField:'val',textField:'text',deitable:false,onChange:initIsSearchChange}}"
						formatter="initIsSearch">查询</th>
					<th field="searchType" title="" width="150" align="center"
						editor="{type:'combobox',options:{required:true,data:searchType,valueField:'val',textField:'text',editable:false,listHeight:100}}"
						formatter="initSearchType">查询类型</th>
					<!-- 					<th field="filterType"  title="" width="150" align="center" -->
					<!-- 						editor="{type:'combobox',options:{required:true,data:filterType,valueField:'val',textField:'text',editable:false}}" formatter="initFilterType">过滤逻辑符</th> -->
					<!-- 					<th field="filterValue" title="" width="150" align="center" -->
					<!-- 						editor="{type:'text'}">过滤值</th> -->
					<th field="relatedCode" title="关联主表字段"
						editor="{type:'combobox',options:{editable:false,valueField:'fieldCode',textField:'fieldCode',disable:true}}"
						formatter="">关联主表字段</th>
					<th field="id" width="150" align="center" title="操作"
						formatter="operateFormatter"></th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>