<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>组织结构管理</title>

<!-- 将Tree对象引入进来 -->
<script type="text/javascript" src="${ctx}/js/CharacterUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/Tree.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js">
    </script>
<style type="text/css">
.top {
	border: 1px solid #aed0ea;
	background: #f2f5f7 url('images/ui-bg_glass_100_e4f1fb_1x400.png')
		repeat-x;
	line-height: 28px;
	padding-left: 15px;
	font-size: 14px;
	font-weight: bold;
	color: #222222;
}

.table_title {
	border: 1px solid #cccccc;
	background: #efefef;
	font-size: 14px;
	font-weight: bold;
	color: #2779aa;
	line-height: 28px;
	padding-left: 15px;
}

.table_nemu {
	font-size: 13px;
	border: 0px;
	text-align: right;
	padding-right: 15px;
	background: #EAF4FC;
	line-height: 28px;
}

.table_con {
	font-size: 13px;
	border: 0px;
	background: #fff;
	line-height: 28px;
	padding-left: 15px;
	width: 75%;
}

.btn1 {
	BORDER: #65b5e4 1px solid;
	PADDING-RIGHT: 15px;
	PADDING-LEFT: 15px;
	FONT-SIZE: 12px;
	FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0,
		StartColorStr=#ffffff, EndColorStr=#d7ebf9);
	CURSOR: hand;
	COLOR: black;
	PADDING-TOP: 3px;
}
</style>
<script type="text/javascript">
        var parentId = null;

        /*
         * 回调函数
         */
        function doSearch(id, name, type) {
            if (id != '-1') {
                $.ajax({
                    url:'${ctx}/dept/deptAction!getById.action?id=' + id,
                    success:function (data) {
                    	clearForm();
                        parentId = data.parentId;
                        $('#id').val(data.id);
                        $('#parentId').val(data.parentId);
                        $('#name').combobox('setValue',data.deptName);
                        $('#order').val(data.deptOrder);
                        $('#deptUrl').val(data.deptUrl);
                        $("#industryType2Div").hide();
                    	$("#industryType3Div").hide();
                    	$("#industryType4Div").hide();
                    }
                });
            } else {
                $('#id').val('');
                $('#parentId').val('-1');
            }
        }
        
        function clearForm(){
        	$('#id').val("");
            $('#parentId').val("");
            $('#name').combobox("setValue","");
            $('#order').val("");
            $('#industryTypeNameStr').val("");
            $('#industryTypeStr').val("");
            $('#gridGrade').val("");
           	$("#addrCode").val("");
           	$('#deptUrl').val("");
           	
           	$("#area1").combobox("clear");
           	$("#area2").combobox("clear");
           	$("#area3").combobox("clear");
           	$("#area4").combobox("clear");
           	
        }

        // 添加新的部门
        function addNew() {
            var idVal = $('#id').val();
            $('#id').val('');
            $('#name').combobox('setValue','');
            $('#deptUrl').val('');
            $('#order').val('');
            $('#parentType').val(2);
            if (parentId == null && idVal != null && idVal != '') {
                $.messager.show({
                    title:'提示',
                    msg:'请选择父类结点',
                    timeout:2000,
                    showType:'show'

                })
                return;
            } else {
                if (idVal == null || idVal == '') {
                    $('#parentId').val('-1');
                } else {
                    $('#parentId').val(idVal);
                }
            }
        }

        var tree = null;
        var key = null;

        $(document).ready(function () {
            tree = new Tree('${ctx}/dept/deptAction!reloadDeptTree.action?dept.parentType=2', 'dept_tree', 'tree_search', 'key', doSearch);
            tree.loadTree();
            $('#name').combobox({
            	url:'${ctx}/data/dataAction!getDataByType?idParam=1373691c30c24e80b83c2c00346052c6',
            	valueField:'id',
                textField:'dataName'
            });
            $('#addBtn').linkbutton();
            $('#removeBtn').linkbutton();
            $('#saveBtn').linkbutton();
            
            $("#industryType4").combobox({
        		valueField:"id",
        		textField:"dataName",
        		onSelect:function (row){
        			$("#industryType").val(row.id);
        			$("#industryTypeName").val(row.dataName);
        		},
        		editable:false
        	});
        	$("#industryType3").combobox({
        		valueField:"id",
        		textField:"dataName",
        		onSelect:function (row){
        			$("#industryType4").combobox("clear");
        			$("#industryType4Div").hide();
        			$("#industryType").val(row.id);
        			$("#industryTypeName").val(row.dataName);
        			getAreaList("industryType4",row.id);
//         			$("#industryType4").combobox("reload", "${ctx}/sourceData/sourceDataAction!getDataByParent.action?pid="+row.id);
//         			$("#industryType4").combobox("showPanel");
        		},
        		editable:false
        	});
        	$("#industryType2").combobox({
        		valueField:"id",
        		textField:"dataName",
        		onSelect:function (row){
        			$("#industryType3").combobox("clear");
        			$("#industryType3Div").hide();
        			$("#industryType4").combobox("clear");
        			$("#industryType4Div").hide();
        			$("#industryType").val(row.id);
        			$("#industryTypeName").val(row.dataName);
        			getAreaList("industryType3",row.id);
//         			$("#industryType3").combobox("reload", "${ctx}/sourceData/sourceDataAction!getDataByParent.action?pid="+row.id);
//         			$("#industryType3").combobox("showPanel");
        		},
        		editable:false,
        		required:false
        	});
        	$("#industryType1").combobox({
        		url:"${ctx}/sourceData/sourceDataAction!firstLevelData.action?typeId=564bb95106d04970ae494b32982360ea",
        		valueField:"id",
        		textField:"dataName",
        		onSelect:function (row){
        			$("#industryType2").combobox("clear");
        			$("#industryType2Div").hide();
        			$("#industryType3").combobox("clear");
        			$("#industryType3Div").hide();
        			$("#industryType4").combobox("clear");
        			$("#industryType4Div").hide();
        			$("#industryType").val(row.id);
        			$("#industryTypeName").val(row.dataName);
        			getAreaList("industryType2",row.id);
//         			$("#industryType2").combobox("reload", "${ctx}/sourceData/sourceDataAction!getDataByParent.action?pid="+row.id);
//         			$("#industryType2").combobox("showPanel");
        		},
        		editable:false,
        		required:false
        	});
        	$("#industryType2Div").hide();
        	$("#industryType3Div").hide();
        	$("#industryType4Div").hide();
        	
        	$("#gridGrade").combobox({
        		url:"${ctx}/sourceData/sourceDataAction!firstLevelData.action?typeId=384743f705fa4ad0b9ad824ce406ec05",
        		valueField:"id",
        		textField:"dataName",
        		panelHeight:"auto",
        		onSelect:function (row){
        		},
        		editable:false,
        		required:true
        	});
        	
        	
        	$("#area4").combobox({
        		valueField:"id",
        		textField:"dataName",
        		onSelect:function (row){
        			$("#addrCode").val(row.id);
        		},
        		editable:false
        	});
//         	$("#area4").next(".combo").hide();
        	
        	$("#area3").combobox({
        		valueField:"id",
        		textField:"dataName",
        		onSelect:function (row){
        			$("#area4").combobox("clear");
        			$("#area4Div").hide();
        			$("#addrCode").val(row.id);
        			getAreaList("area4",row.id);
//         			$("#area4").combobox("reload", "${ctx}/sourceData/sourceDataAction!getDataByParent.action?pid="+row.id);
//         			$("#area4").combobox("showPanel");
        		},
        		editable:false
        	});
//         	$("#area3").next(".combo").hide();
        	
        	$("#area2").combobox({
        		url:"${ctx}/sourceData/sourceDataAction!getDataByParent.action?pid=371000000000",
        		valueField:"id",
        		textField:"dataName",
        		panelHeight:"auto",
        		onSelect:function (row){
        			$("#area3").combobox("clear");
        			$("#area3Div").hide();
        			$("#area4").combobox("clear");
        			$("#area4Div").hide();
        			$("#addrCode").val(row.id);
        			getAreaList("area3",row.id);
//         			$("#area3").combobox("reload", "${ctx}/sourceData/sourceDataAction!getDataByParent.action?pid="+row.id);
//         			$("#area3").combobox("showPanel");
        		},
        		editable:false
        	});
        	$("#area1").combobox({
        		url:"${ctx}/sourceData/sourceDataAction!firstLevelData.action?typeId=0b8415e08bf3474686e643318c0a497c",
        		valueField:"id",
        		textField:"dataName",
        		onSelect:function (row){
        			$("#area2").combobox("clear");
        			$("#addrCode").val(row.id);
        			$("#area2").combobox("reload", "${ctx}/sourceData/sourceDataAction!getDataByParent.action?pid="+row.id);
        			$("#area2").combobox("showPanel");
        		},
        		required:true,
        		editable:false,
        		disabled:true
        	});
        	$("#area1").combobox("setValue", "371000000000");
			$("#area3Div").hide();
			$("#area4Div").hide();
        });

        // 更新或者是保存时调用的方法
        function saveForm() {
            $('#deptForm').form({
                url:'${ctx}/dept/deptAction!saveOrUpdate.action?',
                success:function (data) {
                	var data = eval('(' + data + ')');
                    if (data.operateSuccess) {
                    	$.messager.alert('成功',data.operateMessage); 
                    } else {
                    	$.messager.alert('失败',data.operateMessage); 
                    }
                    tree.loadTree();
                }
            });
            $('#deptForm').submit();
        }

        // 删除一个部门结点
        function removeDeptNode() {
        	$.messager.confirm('警告', '是否要删除该记录?', function (r) {
                if (r) {
		            var idVal = $('#id').val();
		            $.ajax({
		                url:'${ctx}/dept/deptAction!deleteDeptById.action?id=' + idVal,
		                method:'POST',
		                success:function (data) {
		                    if (data.operateSuccess) {
		                    	$.messager.alert('成功',data.operateMessage); 
		                    } else {
		                    	$.messager.alert('失败',data.operateMessage); 
		                    }
		                    tree.loadTree();
		                }
		            });
        		}
        	});
        }
        
        function addIndustry(){
        	var industryTypeStr = $("#industryTypeStr").val();
        	var industryType = $("#industryType").val();
        	var industryTypeNameStr = $("#industryTypeNameStr").val();
			var strs =  industryTypeStr.split(";");  
			if(industryTypeStr!=''){
				for(var i=0;i<strs.length;i++){
	        		if(industryType==strs[i]){
	        			alert("行业已存在，请重新选择！");
	        			return false;
	        		}
	        	}
				$("#industryTypeStr").val(industryTypeStr+$("#industryType").val()+";");
				$("#industryTypeNameStr").val(industryTypeNameStr+$("#industryTypeName").val()+";");
			}else{
				$("#industryTypeStr").val(industryTypeStr+$("#industryType").val()+";");
				$("#industryTypeNameStr").val(industryTypeNameStr+$("#industryTypeName").val()+";");
			}

        }
        
        function deleteIndustry(){
        	$("#industryTypeStr").val("");
			$("#industryTypeNameStr").val("");
			$("#industryTypeStr").val("");
			$("#industryTypeNameStr").val("");
        }
        
        /** 设置联动下拉框的显示值*/
        function setSelectDiv(data){
        	if(null!=data.addressId){
        		$.ajax({
            		url:"${ctx}/sourceData/sourceDataAction!getDataChain.action?id="+data.addressId,
            		data:"post",
            		dataType:"json",
            		success:function(ret){
            			$("#area2Div").hide();
            			$("#area3Div").hide();
            			$("#area4Div").hide();
            			for(var i=0;i<ret.length;i++){
            				$("#area"+(i+2)).combobox("reload","${ctx}/sourceData/sourceDataAction!getDataByParent.action?pid="+ret[i].id);					
            				$("#area"+(i+1)).combobox("setValue",ret[i].id);
            				$("#area"+(i+1)+"Div").show();
            			}
            		}
            	});
        	}
        	
        }
        
        /** 根据参数显示地区下拉框*/
        function getAreaList(state,id){
        	$.post("${ctx}/sourceData/sourceDataAction!getDataByParent.action",
        			{pid:id},
        			function(data){
        				if(data.length){
        					$("#"+state).combobox("loadData",data);
        					$("#"+state+"Div").show();
        					$("#"+state).combobox("showPanel");
        				}else{
        					$("#"+state+"Div").hide();
        				}
        			}
        	);
        }
        function setValue(){
        	$("#order").val($("#order").val());
        }
		
    </script>
</head>
<body class="easyui-layout" id="main_layout">
	<div region="center" style="overflow: hidden;" border="true"
		plain="true" id="center">
		<div>
			<br> <a href="#" id="addBtn" iconCls="icon-save"
				onclick="addNew()" style="margin-left: 10px;">新增</a> <a href="#"
				id="removeBtn" iconCls="icon-remove" onclick="removeDeptNode();">删除</a>
			<a href="#" id="saveBtn" iconCls="icon-save" onclick="saveForm()">保存</a>
		</div>
		<div style="margin-top: 5px; margin-left: 10px;">
			<form id="deptForm" method="post">
				<input type="hidden" id="id" name="dept.id" /> <input type="hidden"
					id="parentId" name="dept.parentId" /> <input type="hidden"
					id="parentType" name="dept.parentType" />
				<table cellspacing="1" border="0" bgcolor="#aed0ea" width="70%">
					<tr>
						<td class="table_nemu" width="20%">企业类型</td>
						<td class="table_con" width="80%"><input
							class="easyui-combobox" style="width: 177px;"
							data-options="required:true" id="name" name="dept.deptName"
							panelHeight="125px;" /></td>
					</tr>
					<tr>
						<td class="table_nemu">排序</td>
						<td class="table_con"><input class="easyui-validatebox"
							validtype="positiveInt" onfocus="setValue();"
							style="width: 177px;" data-options="required:true" id="order"
							name="dept.deptOrder" /></td>
					</tr>
					<tr>
						<td class="table_nemu">对应表单</td>
						<td class="table_con"><input class="easyui-validatebox"
							style="width: 177px;" id='deptUrl' data-options="required:true"
							name="dept.deptUrl" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div region="west" split="true" id="main_west" border="true"
		plain="true" style="width: 220px; padding1: 1px; overflow: hidden;">

		<div id="p" class="easyui-panel" title="&nbsp;&nbsp;企业表单管理" fit="true"
			tools="#tt" border="false" plain="true">
			<div id="tree_search" style="display: none">
				关键字:&nbsp;&nbsp;<input type="text" id="key" name="key"
					style="width: 80px; margin-top: 3px; height: 25px;" />&nbsp;&nbsp;<a
					href="#" class="easyui-linkbutton" iconCls="icon-search"
					plain="true" onclick="tree.searchNode();">搜索</a>
			</div>
			<div id="dept_tree" class="ztree"></div>
		</div>
		<div id="tt">
			<a href="#" class="icon-treeSearch"
				onclick="javascript:tree.changeTreeSearchPanelShow();" title="搜索"></a>
			<a href="#" class="icon-treeRefresh"
				onclick="javascript:tree.loadTree();" title="刷新"></a> <a href="#"
				class="icon-treeAdd" onclick="javascript:tree.expandAllNode(false);"
				title="收缩所有"></a> <a href="#" class="icon-treeDelete"
				onclick="javascript:tree.expandAllNode(true);" title="展开所有"
				style="margin-right: 10px;"></a>
		</div>
	</div>
</body>
</html>