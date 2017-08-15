<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html charset=UTF-8">
<title>元数据属性设置</title>

<!-- 把TREE对象引进JSP 页面-->
<script type="text/javascript" src="${ctx}/js/CharacterUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/Tree.js"></script>
<script type="text/javascript" src="${ctx}/js/validator.js"></script>
<script type="text/javascript">
        var parentId = null;
        var tree = null;
        var key = null;
        var idParam = "${param.idParam}";

        /**
         *回调函数
         */
        function doSearch(id, name, type) {
            //alert(id);
            if (id != '-1') {
                $.ajax({
                    url:'${ctx}/data/dataAction!getById.action?id=' + id,
                    success:function (data) {
                        parentId = data.parentId;
                        $('#id').val(data.id);
                        $('#parentId').val(data.parentId);
                        $('#name').val(data.dataName);
                        $('#order').val(data.dataOrder);
                        $('#code').val(data.dataCode);
                        $('#typeId').val(data.typeId);
                        var dataDesc=data.dataDesc==null?'':data.dataDesc;
                        $('#desc').val(dataDesc);
                    }
                });
            } else {
                parentId = id;
                $('#id').val('');
                $('#parentId').val('-1');
            }
        }

         // 添加方法
        function addNew() {
            var idval = $('#id').val();
            $('#id').val('');
            $('#name').val('');
            $('#order').val('');
            $('#code').val('');
            $('#desc').val('');
            $('#typeId').val(idParam);
            if (parentId == null) {
                $.messager.show({
                    title: '提示',
                    msg: '请选择父节点',
                    timeout: 2000,
                    showType:'show'
                });
                return;
            } else {
                if (idval == null || idval == '') {
                    $('#parendId').val('-1');
                } else {
                    $('#parentId').val(idval);
                }
            }
        }


        $(document).ready(function() {
            tree = new Tree('${ctx }/data/dataAction!reloadDataTree.action?idParam=' + idParam, 'data_tree', 'tree_search', 'key', doSearch);
            tree.loadTree();
            $('#addBtn').linkbutton();
            $('#removeBtn').linkbutton();
            $('#saveBtn').linkbutton();
            //alert(idParam);
        });

     // 更新或者是保存时调用的方法
        function saveForm() {
        	var idValue = $('#id').val();
        	var parentId = $('#parentId').val();
        	if (parentId == null) {
                $.messager.show({
                    title: '提示',
                    msg: '请选择父节点,然后点击新增',
                    timeout: 2000,
                    showType:'show'
                });
                return;
        	}
        	else if(idValue != '' )
        	{
        		$.messager.confirm("提示","您此次操作为更新菜单，继续请点击 确定，停止请点击取消！",function(data){
        			if(data){
        				saveSubmitForm();
        			}else{
        				return
        			}
        		});
        	}
        	else if(idValue =='' )
        	{
        		$.messager.confirm("提示","您此次操作为新增菜单，继续请点击 确定，停止请点击取消！",function(data){
        			if(data){
        				saveSubmitForm();
        			}else{
        				return
        			}
        		});
        	}
        	/*else if(parentId != -1 && (idValue !='' ))
        	{
        		var flag = window.confirm("您此次操作为更新菜单，继续请点击 确定，停止请点击取消！");
        		if(!flag){
        			return;
        		}
        	}*/
        }
     
     function saveSubmitForm(){
    	 $('#typeId').val(idParam);
         $('#dataForm').form ({
             url:'${ctx }/data/dataAction!saveOrUpdate.action',
             success :function(data){
                 var data = eval('(' + data + ')');
                 if (data.operateSuccess) {
                     $.messager.show({
                         title:'成功',
                         msg:data.operateMessage,
                         timeout:2000,
                         showType:'show'
                     });
                 } else {
                     $.messager.show({
                         title:'失败',
                         msg:data.operateMessage,
                         timeout:2000,
                         showType:'show'
                     });
                 }
                 tree.loadTree();
             }
         });
         $('#dataForm').submit();
     }
 


        // 删除一个data结点
        function removeDataNode() {
            var idVal = $('#id').val();
            
            $.messager.confirm('提示','您确定要删除此条数据吗？',function(data){
            	if(data){
            		$.ajax({
    	                url:'${ctx}/data/dataAction!deleteByIds.action?id=' + idVal,
    	                method:'POST',
    	                success:function (data) {
    	                	var data = eval('(' + data + ')');
    	                    if (data.operateSuccess) {
    	                        $.messager.show({
    	                            title:'成功',
    	                            msg:data.operateMessage,
    	                            timeout:2000,
    	                            showType:'show'
    	                        });
    	                    } else {
    	                        $.messager.show({
    	                            title:'失败',
    	                            msg:data.operateMessage,
    	                            timeout:2000,
    	                            showType:'show'
    	                        });
    	                    }
    	                    tree.loadTree();
    	                }
    	            });
            	}else{
            		return;
            	}
            });

        }

        //验证输入方法
        $(function (){
            //设置text输入框需要验证输入类型
            $('input[type=text]').validatebox();
        })

    </script>
</head>
<body class="easyui-layout" id="main_layout">
	<div region="center" style="overflow: hidden" border="true"
		plain="true" id="center">
		<div>
			<a href="#" id="addBtn" iconCls="icon-save" onclick="addNew()">新增</a>
			<a href="#" id="removeBtn" iconCls="icon-undo"
				onclick="removeDataNode()">删除</a>
		</div>

		<div>
			<form id="dataForm" method="post">
				<input type="hidden" id="id" name="data.id" /> <input type="hidden"
					id="parentId" name="data.parentId" /> <input type="hidden"
					id="status" value="1" /> <input type="hidden" name="data.typeId"
					id="typeId" />
				<table style="font-size: 12px;">
					<tr>
						<td>名称：</td>
						<td><input class="easyui-validatebox"
							data-options="required:true" id="name" name="data.dataName" /></td>
					</tr>
					<tr>
						<td>编码：</td>
						<td><input type="text" validtype="codechick" id="code"
							name="data.dataCode" required="true" /></td>
					</tr>
					<tr>
						<td>排序：</td>
						<td><input type="text" validtype="number" id="order"
							name="data.dataOrder" required="true" /></td>
					</tr>
					<tr>
						<td>描述：</td>
						<td><textarea id="desc" style="width: 300px; height: 100px;"
								name="data.dataDesc" /></textarea></td>
					</tr>
					<tr>
						<td colspan="2"><a style="margin-left: 80px;" href="#"
							id="saveBtn" iconCls="icon-save" onclick="saveForm()">保存</a></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<div region="west" split="true" id="main_west" border="true"
		plain="true" style="width: 270px; padding1: 1px; overflow: hidden;">
		<div id="p" class="easyui-panel" title="元数据" fit="true" tools="#tt"
			border="false" plain="true">
			<div id="tree_search" style="display: none">
				关键字:<input type="text" id="key" name="key" style="width: 80px;" /><a
					href="#" class="easyui-linkbutton" iconCls="icon-search"
					plain="true" onclick="tree.searchNode();">搜索</a>
			</div>
			<div id="data_tree" class="ztree"></div>
		</div>
		<div id="tt">
			<a href="#" class="icon-treeSearch"
				onclick="javascript:tree.changeTreeSearchPanelShow();" title="搜索"></a>
			<a href="#" class="icon-treeRefresh"
				onclick="javascript:tree.loadTree();" title="刷新"></a> <a href="#"
				class="icon-treeAdd" onclick="javascript:tree.expandAllNode(false);"
				title="收缩所有"></a> <a href="#" class="icon-treeDelete"
				onclick="javascript:tree.expandAllNode(true);" title="展开所有"></a>
		</div>
	</div>
</body>
</html>