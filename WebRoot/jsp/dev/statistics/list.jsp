<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>统计管理</title>
<script type="text/javascript" src="${ctx}/js/GridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/Tree.js"></script>
<script type="text/javascript">
        var grid = null;
        var tree = null;
        var key = null;

        // 当点击添加按钮时做的操作
        function addFunction() {
            $('#addWin').window('open');
            clearAddForm();
        }

        // 提交添加菜单请求
        function addSubmitForm() {
            $('#addForm').form({
                url:'${ctx}/statistics/statisticsAction!addOrUpdate',
                success:function (data) {
                	var data = eval('(' + data + ')');
					closeAllWin();
                    if (data.operateSuccess) {
                        $.messager.show({
                            title:'成功',
                            msg:data.operateMessage,
                            timeout:2000,
                            showType:'slide'
                        });
                        grid.reloadGrid();
                    } else {
                        $.messager.show({
                            title:'失败',
                            msg:data.operateMessage,
                            timeout:2000,
                            showType:'slide'
                        });
                    }
                }
            });
            $('#addForm').submit();
        }

        function updateSubmitForm() {
            $('#updateForm').form({
                url:'${ctx}/statistics/statisticsAction!addOrUpdate',
                success:function (data) {
                	closeAllWin();
                	var data = eval('(' + data + ')');
                    if (data.operateSuccess) {
                        $.messager.show({
                            title:'成功',
                            msg:data.operateMessage,
                            timeout:2000,
                            showType:'slide'
                        });
                        grid.reloadGrid();
                    } else {
                        $.messager.show({
                            title:'失败',
                            msg:data.operateMessage,
                            timeout:2000,
                            showType:'slide'
                        });
                    }
                }
            });
            $('#updateForm').submit();
        }

        // 清除添加表单中的数据
        function clearAddForm() {
        	$('#addWin input').val('');
        }

        // 添加更新表单中的数据
        function clearUpdateForm() {
        	$('#updateWin input').val('');
        }

        function clearViewForm() {
        	$('#viewWin input').val('');
        }

        // 填充更新表单中的数据
        function fillUpdateForm(value) {
        	var rowsid;
        	if(typeof value=="undefined"){
            	var rows = grid.getSelectNode();
            	rowsid=rows.id;
        	}
        	else{
        		rowsid = value;
        	}
            $.ajax({
                url:'${ctx}/statistics/statisticsAction!init?id=' + rowsid,
                method:'POST',
                success:function (data) {
                	$.each(data,function(i,item){
	                	$('#updateWin [name="entity.'+i+'"]').val(item);
                    });
                }
            });
        }

        // 填充查看菜单的内容
        function fillViewForm(value) {
        	var rowsid;
        	if(typeof value=="undefined"){
            	var rows = grid.getSelectNode();
            	rowsid=rows.id;
        	}
        	else{
        		rowsid = value;
        	}
            $.ajax({
                url:'${ctx}/statistics/statisticsAction!init?id=' + rowsid,
                method:'POST',
                success:function (data) {
                	$.each(data,function(i,item){
	                	$('#viewWin span[name="entity.'+i+'"]').html(item);
                    });
                	$('#charttype_view').val(data.charttype);
                	$('#transposed_view').val(String(data.transposed));
    				$('#charttype_view').attr("disabled",true); 
    				$('#transposed_view').attr("disabled",true);    				
                }
            });
        }
        function editFunction(rowId) {
            $('#updateWin').window('open');
            clearUpdateForm();
            fillUpdateForm(rowId);
        }
        function viewFunction(rowId) {
        	$('#viewWin').window('open');
        	clearViewForm();
        	fillViewForm(rowId);
        }
        function viewPicFunction(rowId) {
        	$('#img_chart').attr('src','${ctx}/jfreechart/JFreeChartAction!randerChart?id='+rowId+'&d='+new Date());
        	$('#img_src').val('${ctx}/jfreechart/JFreeChartAction!randerChart?id='+rowId);
        	$('#picWin').window('open');
        }


        function removeFunction() {
            var rows = grid.getSelectNodes();
            if (rows.length == 0) {
                $.messager.show({
                    title:'提示',
                    msg:'请选择要删除的行',
                    timeout:2000,
                    showType:'slide'
                });
                return;
            } else {
            	var ids = new Array();
                $.messager.confirm('警告', '是否要删除该记录?', function (r) {
                    if (r) {
                    	var i = 0;
                    	for (i=0; i<rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        $.ajax({
                            url:'${ctx}/statistics/statisticsAction!delete?ids=' + ids,
                            method:'POST',
                            success:function(data) {
                            	var data = eval('(' + data + ')');
                                if (data.operateSuccess) {
                                    $.messager.show({
                                        title:'成功',
                                        msg:data.operateMessage,
                                        timeout:2000,
                                        showType:'slide'
                                    });
                                    grid.reloadGrid();
                                } else {
                                    $.messager.show({
                                        title:'失败',
                                        msg:data.operateMessage,
                                        timeout:2000,
                                        showType:'slide'
                                    });
                                }
                            }
                        });
                    }
                });

            }

        }

        function searchSubmitForm(){
        	//提交查询表单
       	    var chartTitle = $("#charttitle").val();
       	    grid = new Grid($("title").html()+'列表', 'icon-edit',
                       '${ctx}/statistics/statisticsAction!list?charttitle='+chartTitle,
                       'data_list', addFunction, editFunction,
                       viewFunction, removeFunction,searchFunction);
            grid.loadGrid();
       	    $('#searchWin').window('close');
        }

        

        function showDeptTreeWin() {
            $('#deptTreeWin').window('open');
        }
        $(document).ready(
                function () {
                    $('#addWin').window({
                        width:382,
                        height:485,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#viewWin').window({
                        width:382,
                        height:485,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#picWin').window({
                        width:600,
                        height:485,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#updateWin').window({
                        width:382,
                        height:485,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#searchWin').window({
                        width:382,
                        height:485,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#deptTreeWin').window({
                        width:382,
                        height:485,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#addWin').window('close');
                    $('#viewWin').window('close');
                    $('#picWin').window('close');
                    $('#updateWin').window('close');
                    $('#searchWin').window('close');
                    $('#deptTreeWin').window('close');
                    grid = new Grid($("title").html()+'列表', 'icon-edit',
                            '${ctx}/statistics/statisticsAction!list',
                            'data_list', addFunction, editFunction,
                            viewFunction, removeFunction,searchFunction);
                    grid.loadGrid();
                    removeSelect();
                });
        
      //去掉点击操作栏按钮也会选中的问题
        function removeSelect(){
       	 $('#data_list').datagrid({
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
    		    }
    		});
       }

        function closeAllWin() {
            $('#addWin').window('close');
            $('#viewWin').window('close');
            $('#updateWin').window('close');
            $('#searchWin').window('close');
            $('#picWin').window('close');
        }
        
        function delFunction(obj){
        	$.messager.confirm('警告', '是否要删除该记录?', function (r) {
                if (r) {
                	var ids="ids="+obj.rowId;
                    
                    $.ajax({
                        url:'${ctx}/statistics/statisticsAction!delete?' + ids,
                        method:'POST',
                        success:function(data) {
                        	var data = eval('(' + data + ')');
                            if (data.operateSuccess) {
                                $.messager.show({
                                    title:'成功',
                                    msg:data.operateMessage,
                                    timeout:2000,
                                    showType:'slide'
                                });
                                grid.reloadGrid();
                            } else {
                                $.messager.show({
                                    title:'失败',
                                    msg:data.operateMessage,
                                    timeout:2000,
                                    showType:'slide'
                                });
                            }
                        }
                    });
                }
            });
        }
        
        function searchFunction(){
        	$("#searchWin").window("open");        	
        }
        
        function gridFormatter(value, rowData, rowIndex) {
        	var rowId = rowData.id;
        	var rowName = rowData.entityCode;
        	var url = "<input type='button' onclick='editFunction(\""+rowId+"\");' value='修改' "
        	+"/>&nbsp;&nbsp;<input type='button' onclick='viewFunction(\""+rowId+"\");' value='查看' "
        	+"/>&nbsp;&nbsp;<input type='button' onclick='viewPicFunction(\""+rowId+"\");' value='查看统计图' "
        	+"/>";
        	return url;
        }
        function gridFormatterType(value, rowData, rowIndex) {
        	var chartTypeStr;
        	switch(value){
			case 1:
				chartTypeStr="2D饼图";
				break;
			case 2:
				chartTypeStr="3D饼图";
				break;
			case 3:
				chartTypeStr="2D柱状图";
				break;
			case 4:
				chartTypeStr="3D柱状图";
				break;
			case 5:
				chartTypeStr="折线图";
				break;
	        default:
	        	chartTypeStr="其它图";
	        	break;
			}
			return chartTypeStr;        	
        }
</script>
</head>
<body>

	<div style="height: 500px">
		<table id="data_list" class="easyui-datagrid" loadMsg="正在努力拉取数据中..."
			fit="true" fitColumns="true">
			<thead>
				<tr>
					<th field="charttitle" width="150" title="实体名称">统计图标题</th>
					<th field="xtitle" width="150" title="实体含义">x轴标题</th>
					<th field="ytitle" width="150" title="实体含义">y轴标题</th>
					<th field="charttype" width="150" title="实体类型"
						formatter="gridFormatterType">统计图类型</th>
					<th field="id" width="120" title="实体id" formatter="gridFormatter">操作</th>
				</tr>
			</thead>
		</table>
	</div>

	<!-- 添加窗口 -->
	<div id="addWin" iconCls="icon-save" title="添加统计信息">

		<form style="padding: 10px 20px 10px 85px;" id="addForm" method="post">
			<table cellpadding="5px" style="font-size: 12px;">
				<tr>
					<td>统计图标题</td>
					<td><input class="easyui-validatebox" name="entity.charttitle" /></td>
				</tr>
				<tr>
					<td>x轴标题</td>
					<td><input class="easyui-validatebox" name="entity.xtitle" /></td>
				</tr>
				<tr>
					<td>y轴标题</td>
					<td><input class="easyui-validatebox" name="entity.ytitle" /></td>
				</tr>
				<tr>
					<td>统计图类型</td>
					<td><select name="entity.charttype">
							<option value="1">2D饼图</option>
							<option value="2">3D饼图</option>
							<option value="3">2D柱状图</option>
							<option value="4">3D柱状图</option>
							<option value="5">折线图</option>
					</select></td>
				</tr>
				<tr>
					<td>调换顺序</td>
					<td><select name="entity.transposed">
							<option value="false">否</option>
							<option value="true">是</option>
					</select></td>
				</tr>
				<tr>
					<td>SQL语句</td>
					<td><textarea class="easyui-validatebox"
							name="entity.sqlcommand" rows="10"></textarea></td>
				</tr>
			</table>
			<div style="padding: 0px;">
				<input type="hidden" name="entity.entityType" value="0" /> <a
					href="#" class="easyui-linkbutton" icon="icon-ok"
					onclick="addSubmitForm();return false;">确定</a> <a href="#"
					class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>

	</div>

	<!-- 更新窗口 -->
	<div id="updateWin" iconCls="icon-save" title="更新信息">
		<form style="padding: 10px 20px 10px 85px;" id="updateForm"
			method="post">
			<table cellpadding="5px" style="font-size: 12px;">
				<tr>
					<td>统计图标题</td>
					<td><input class="easyui-validatebox" name="entity.charttitle" /></td>
				</tr>
				<tr>
					<td>x轴标题</td>
					<td><input class="easyui-validatebox" name="entity.xtitle" /></td>
				</tr>
				<tr>
					<td>y轴标题</td>
					<td><input class="easyui-validatebox" name="entity.ytitle" /></td>
				</tr>
				<tr>
					<td>统计图类型</td>
					<td><select name="entity.charttype">
							<option value="1">2D饼图</option>
							<option value="2">3D饼图</option>
							<option value="3">2D柱状图</option>
							<option value="4">3D柱状图</option>
							<option value="5">折线图</option>
					</select></td>
				</tr>
				<tr>
					<td>调换顺序</td>
					<td><select name="entity.transposed">
							<option value="false">否</option>
							<option value="true">是</option>
					</select></td>
				</tr>
				<tr>
					<td>SQL语句</td>
					<td><textarea class="easyui-validatebox"
							name="entity.sqlcommand" rows="10"></textarea></td>
				</tr>
			</table>
			<input type="hidden" name="entity.id" id="updateEntityId" />
			<div style="padding: 0px;">
				<a href="#" class="easyui-linkbutton" icon="icon-ok"
					onclick="updateSubmitForm();return false;">确定</a> <a href="#"
					class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>
	</div>

	<!-- 查看窗口 -->
	<div id="viewWin" iconCls="icon-save" title="查看信息">
		<div style="padding: 10px 20px 10px 85px;">
			<table cellpadding="5px" style="font-size: 12px;">
				<tr>
					<td>统计图标题</td>
					<td><span class="easyui-validatebox" name="entity.charttitle" /></td>
				</tr>
				<tr>
					<td>x轴标题</td>
					<td><span class="easyui-validatebox" name="entity.xtitle" /></td>
				</tr>
				<tr>
					<td>y轴标题</td>
					<td><span class="easyui-validatebox" name="entity.ytitle" /></td>
				</tr>
				<tr>
					<td>统计图类型</td>
					<td><select id="charttype_view" name="entity.charttype">
							<option value="1">2D饼图</option>
							<option value="2">3D饼图</option>
							<option value="3">2D柱状图</option>
							<option value="4">3D柱状图</option>
							<option value="5">折线图</option>
					</select></td>
				</tr>
				<tr>
					<td>调换顺序</td>
					<td><select id="transposed_view" name="entity.transposed">
							<option value="false">否</option>
							<option value="true">是</option>
					</select></td>
				</tr>
				<tr>
					<td>SQL语句</td>
					<td><span class="easyui-validatebox" name="entity.sqlcommand" /></td>
				</tr>
			</table>
			<input type="hidden" name="entity.id" id="updateEntityId" />
			<div style="padding: 0px;">
				<a href="#" class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">关闭</a>
			</div>
		</div>
	</div>

	<!-- 图片窗口 -->
	<div id="picWin" iconCls="icon-save" title="查看信息">
		<div style="padding: 10px 20px 10px 85px;">
			<iframe id="img_chart" frameborder="0" width="450px" height="350px"
				src="" scrolling="no"></iframe>
			<div style="padding: 0px;">
				<textarea id="img_src" cols="40" rows="4"></textarea>
				<br> <a href="#" class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">关闭</a>
			</div>
		</div>
	</div>

	<!-- 搜索窗口 -->
	<div id="searchWin" iconCls="icon-save" title="查看信息">
		<form style="padding: 10px 20px 10px 85px;">
			<table cellpadding="5px" style="font-size: 12px;">
				<tr>
					<td>统计图标题</td>
					<td><input class="easyui-validatebox" id="charttitle"
						name="charttitle" /></td>
				</tr>

			</table>
			<input type="hidden" name="entity.id" id="viewEntityId" />
			<div style="padding: 0px;">
				<a href="#" class="easyui-linkbutton" icon="icon-search"
					onclick="searchSubmitForm();return false;">确定</a> <a href="#"
					class="easyui-linkbutton" icon="icon-cancel"
					onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>
	</div>

</body>
</html>