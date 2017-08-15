<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>定义表结构</title>

<script type="text/javascript" src="${ctx}/js/GridUtilNoToolBar.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/Tree.js"></script>
<script type="text/javascript" src="${ctx}/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/js/validator.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/jquery/easyui-1.3.1/datagrid-detailview.js"></script>
<script type="text/javascript"><!--
        var grid = null;
        var tree = null;
        var key = null;
        
        /** 原始数据*/
        var oldData = null;

        // 当点击添加按钮时做的操作
        function addFunction() {
            $('#addWin').window('open');
            clearAddForm();
			//window.location.href="declareEntityField.jsp";
        }
        
        /* 是否是从表,0:主表,1:从表**/
        var tableType = 0;

        // 提交添加菜单请求
        function addSubmitForm(o) {
        	var tableTypeList = document.getElementsByName("tableType");
			var submitForm = $(o).parents("form");
			submitForm.form({
                url:'${ctx}/entity/entityAction!addTable.action',
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
                        $("#data_list").datagrid("reload");
                        $("#tableSelect").combobox("reload");
                        $("#data_tree").treegrid("reload");
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
            submitForm.submit();
        }

       	/** 提交更新窗口*/
        function updateSubmitForm() {
        	var code = $('#updateEntityCode').val();
            var name = $('#updateEntityName').val();
            if(code!=oldData.entityCode||name!=oldData.entityName){
            	$('#updateForm').form({
                    url:'${ctx}/entity/entityAction!updateEntity.action',
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
                            $("#data_list").datagrid("reload");
                            $("#data_tree").treegrid("reload");
                         	 //刷新combobox的表列表
                            $("#tableSelect").combobox("reload");
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
        }

        // 清除添加表单中的数据
        function clearAddForm() {
            $('#addEntityCode').val('');
            $('#addEntityName').val('');
            $("#addTreeEntityCode").val("");
            $("#addTreeEntityName").val("");
        }

        // 添加更新表单中的数据
        function clearUpdateForm() {
            $('#updateId').val('');
            $('#updateAccount').val('');
            $('#updatePassword').val('');
            $('#updateUserName').val('');
            $('#updateDeptId').val('');
            $('#updateDeptName').val('');
        }

        function clearViewForm() {
            $('#viewAccount').val('');
            $('#viewPassword').val('');
            $('#viewUserName').val('');
            $('#viewDeptName').val('');
            $("#viewParentCode").text("");
        }

        // 填充更新表单中的数据
        function fillUpdateForm() {
            var rows = $("#data_list").datagrid("getSelected");
            $.ajax({
                url:'${ctx}/entity/entityAction!getEntityById.action?id=' + rows.id,
                method:'POST',
                success:function (data) {
                    $('#updateEntityId').val(data.id);
                    $('#updateEntityCode').val(data.entityCode);
                    $('#updateEntityName').val(data.entityName);
                    
                }
            });
        }

        // 填充查看菜单的内容
        function fillViewForm(row) {        	
        	$('#viewEntityId').val(row.id);
            $('#viewEntityCode').val(row.entityCode);
            $('#viewEntityName').val(row.entityName);
            try{           	
            	var parentCode = $("#data_tree").treegrid("find", row.parentId).entityCode;
	            $("#viewParentCode").text(parentCode);
            }catch(e){
            	$("#viewParentCode").text("无父表");
            }
        }

        function editFunction() {
            var rows = $("#data_list").datagrid("getSelected");
            if (rows.length == 0) {
                $.messager.show({
                    title:'警告',
                    msg:'请选择一列数据进行修改操作!',
                    timeout:2000,
                    showType:'slide'
                });
                return;
            }
            if (rows.length > 1) {
                $.messager.show({
                    title:'警告',
                    msg:'每次更改数据只能选择一条数据!',
                    timeout:2000,
                    showType:'slide'
                });

                return;
            }
            $('#updateWin').window('open');
            clearUpdateForm();
            fillUpdateForm();
        }


        function removeFunction() {
            var rows = $("#data_list").datagrid("getChecked");
            if (rows.length == 0) {
                $.messager.show({
                    title:'提示',
                    msg:'请选择要删除的行',
                    timeout:2000,
                    showType:'slide'
                });
                return;
            } else {
                $.messager.confirm('警告', '是否要删除该记录?', function (r) {
                    if (r) {
                    	var ids="";
                        var i = 0;
                        for (i=0; i<rows.length; i++) {
                           
                            if(i!=0){
                            	 ids+="&ids="+rows[i].id;
                            }else{
                            	ids+="ids="+rows[i].id;
                            }
                        }
                        $.ajax({
                            url:'${ctx}/entity/entityAction!deleteEntity?' + ids+"&entityType=0",
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
                                    $("#data_list").datagrid("reload");
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
       	    var entityCode = $("#searchEntityCode").val();
       	    var entityName = $("#searchEntityName").val();
       	    $.post('${ctx}/entity/entityAction!treeGrid.action',{"entityCode":entityCode, "entityName":entityName},
       	    	function(ret){
       	    		$("#data_tree").treegrid("loadData",ret);
       	    	}		
       	    );
//            	$("#data_list").datagrid("reload", {"entityCode":entityCode, "entityName":entityName});
//            	var options = $("#data_tree").treegrid("options");
//            	options.url='${ctx}/entity/entityAction!treeGrid.action?entityCode='+entityCode+'&entityName='+entityName;
//            	$("#data_tree").treegrid("reload");
       	    $('#searchWin').window('close');
       	 	$("#tableSelect").combobox("reload");
        }

        function viewFunction(obj) {
            var index = obj.rowId;
            var row;
            try{
	           	row = $("#data_list").datagrid("getRows")[index];
            }catch(e){
            	row = $("#data_tree").treegrid("find", index);
            }            
            $('#viewWin').window('open');
            clearViewForm();
            fillViewForm(row);
        }

        function showDeptTreeWin() {
            $('#deptTreeWin').window('open');
        }
        function callBack(data){
//         	alert(data);
//         	var jsonData=eval(data);
//         	$.each(jsonData, function(index, value) {
//         		alert(value.optName);
//         	});
        }
        $(document).ready(
                function () {
                	//alert('${param.menuId}');
                	var permission = new MenuPermission('${param.menuId}','${ctx}');
                	permission.getMenuOptPermission(callBack);
                	
                    $('#addWin').window({
                        width:482,
                        height:285,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#updateWin').window({
                        width:382,
                        height:285,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#searchWin').window({
                        width:382,
                        height:285,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#viewWin').window({
                        width:382,
                        height:285,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $('#deptTreeWin').window({
                        width:382,
                        height:285,
                        modal:true,
                        onOpen:function(){
                        	$(".window-mask").css("height","100%");
                        }
                    });
                    $("#addTreeEntityCode").validatebox({
                    	required:true,
                    	validType:"codechick"
                    });
                    $("#addTreeEntityName").validatebox({
                    	required:true
                    });
                    $("#updateEntityCode").validatebox({
                    	required:true,
                    	validType:"codechick"
                    });
                    $("#updateEntityName").validatebox({
                    	required:true
                    });
                    $("#tableSelect").combobox({
                    	url:"${ctx}/entity/entityAction!getEntityJson.action?entityType=0",
                    	valueField:"id",
                    	textField:"entityCode"
                    });
                    $('#addWin').window('close');
                    $('#updateWin').window('close');
                    $('#searchWin').window('close');
                    $('#deptTreeWin').window('close');
                    $('#viewWin').window('close');
                    /*
                    $("#data_list").datagrid({
                    	url:'${ctx}/entity/entityAction!pageList.action?entityType=0',
                    	title : "",
                		iconCls : "icon-edit",
                		nowrap : false,
                		striped : true,
                		border : true,
                		collapsible : false,
                		fit : true,                		
                		remoteSort : false,
                		idField : 'id',
                		singleSelect : false,
                		pagination : true,
                		rownumbers : true,
                		loadMsg:"正在努力拉取数据中...",
                	    fitColumns:true,
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
            	    	},
                		frozenColumns : [ [ {
                			field : 'ck',
                			checkbox : true
                		} ] ]
                    	,
                    	view: detailview,
                    	detailFormatter: function(index, row){  
                   	        return "<div style='padding:2px'><table id='ddv-" + index + "'></table></div>";  
                   	    }  ,  
                        onExpandRow: function(index, row){  
                            $('#ddv-'+index).datagrid({  
                                url:'${ctx}/entity/entityAction!pageList.action',
                                queryParams:{"parentTableId":row.id,"entityType":"0"},
                                fitColumns:true,  
                                singleSelect:true,  
                                rownumbers:true,  
                                loadMsg:'正在努力获取数据',  
                                height:'auto',  
                                columns:[[  
                                    {field:'entityCode',title:'表名',width:100, align:'center'},  
                                    {field:'entityName',title:'表含义',width:100, align:'center'},  
                                    {field:'formCount',title:'表单数',width:100, align:'center'}  
                                ]]
                                ,  
                                onResize:function(){  
                                    $('#dg').datagrid('fixDetailRowHeight',index);  
                                },  
                                onLoadSuccess:function(){  
                                    setTimeout(function(){  
                                        $('#dg').datagrid('fixDetailRowHeight',index);  
                                    },0);  
                                }  
                            });  
                            $('#dg').datagrid('fixDetailRowHeight',index);  
                        }       	
                    });*/
                });
        

        function closeAllWin() {
            $('#addWin').window('close');
            $('#updateWin').window('close');
            $('#searchWin').window('close');
            $('#viewWin').window('close');
            $("#addTreeNodeWin").window("close");
        }
        
        function updFunction(obj) {
            $('#updateWin').window('open');
            clearUpdateForm();
            $.ajax({
                url:'${ctx}/entity/entityAction!getEntityById.action?id=' + obj.rowId,
                method:'POST',
                success:function (data) {
                	oldData = data;
                    $('#updateEntityId').val(data.id);
                    $('#updateEntityCode').val(data.entityCode);
                    $('#updateEntityName').val(data.entityName);
                    try{           	
                    	var parentCode = $("#data_tree").treegrid("find", data.parentId).entityCode;
        	            $("#updateParentCode").text(parentCode);
                    }catch(e){
                    	$("#updateParentCode").text("无父表");
                    }
                }
            });
        }
        
        /** 获得要删除的数据*/
        function getDelRows(){
        	var rows = null;
        	try {
        		rows = $("#data_list").datagrid("getChecked");
			} catch (e) {
				rows = new Array();
				var checks = document.getElementsByName("entityId");
				for(var i=0;i<checks.length;i++){						
					var ck = checks[i];
					if(ck.checked){
						var row = $("#data_tree").treegrid("find", ck.id.substring(ck.id.lastIndexOf("_")+1));
						rows.push(row);
					}
				}
			}
			return rows;
        }
        
        /** 生成删除参数*/
        function getDelPara(rows, notDeleted){
        	var ids = new Array();
            for (var i in rows){
               if(rows[i].formCount==0){
            	   var isBreak = false;
            	   try{
            		   var children = $("#data_tree").treegrid("getChildren", rows[i].id);
            		   for(var ii in children){
            			   var count = children[ii].formCount;
            			   if(count>0){
            				   isBreak = true;
            				   break;
            			   }
            		   }
            	   }catch(e){}
            	   if(isBreak){
            		    notDeleted.push(rows[i]);
            			continue;
            	   }
            	   var str = "ids="+ rows[i].id;
				   ids.push(str);
               }else{
            	   notDeleted.push(rows[i]);
               }                           
            }
            return ids;
        }
        
        /**删除表，可以多行删除*/
        function delFunction(list){  
        	var rows = new Array();
        	if(list){
        		rows = list;
        	}else{
        		rows = getDelRows();
        	}
        	var notDeleted = new Array();
            if (!rows.length) {
                $.messager.show({
                    title:'提示',
                    msg:'请选择要删除的行',
                    timeout:2000,
                    showType:'slide'
                });
                return;
            } else {
                $.messager.confirm('警告', '会删除选中表下的所有子表！且无法恢复！请谨慎操作！', function (r) {
                    if (r) {
                    	var ids = null;
						ids = getDelPara(rows, notDeleted);
                        if(!ids.length){
                        	if(notDeleted.length){
                        		var messages = "";
                        		if(notDeleted.length){
                            		for(var aa in notDeleted){
                            			messages +=notDeleted[aa].entityCode+",";
                            		}
                            		messages+="表无法删除，该表或其子表已有表单关联！";
                            	}
                        		$.messager.alert("警告", messages);
                        	}
                        	return;
                        }
                        var idsStr = ids.join("&");
                        $.ajax({
                            url:'${ctx}/entity/entityAction!deleteEntity?' + idsStr + "&entityType=0",
                            method:'POST',
                            success:function(data) {
                            	var data = eval('(' + data + ')');
                                if (data.operateSuccess) {
                                	var messages = "";
                                	if(notDeleted.length){
                                		for(var aa in notDeleted){
                                			messages +=notDeleted[aa].entityCode+",";
                                		}
                                		messages+="这些表没有删除，该表或其子表已有表单关联！";
                                	}
                                    $.messager.show({
                                        title:'成功',
                                        msg:data.operateMessage+" "+messages,
                                        timeout:2000,
                                        showType:'slide'
                                    });
                                    $("#data_list").datagrid("reload");
                                    $("#tableSelect").combobox("reload");
                                    $("#data_tree").treegrid("reload");
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
        
        function searchFunction(){
        	$("#searchWin").window("open");
        	
        }
        
       	function setFunction(obj){
       		var rowId=$(obj).attr("rowId");
       		var rowName=$(obj).attr("rowName");
       		window.location.href="declareEntityField.jsp?entityId="+rowId+"&entityCode="+rowName;
       	}
        function gridFormatter(value, rowData, rowIndex) {
        	var rowId = rowData.id;
        	var rowName = rowData.entityCode;
        	var url = ""
        	<shiro:hasPermission name="43:view">
        	+"<input type='button' onclick='viewFunction(this);' value='查看'  rowId='"+rowIndex+"' rowName='"+rowName+"'/>&nbsp;&nbsp"
			</shiro:hasPermission>
			<shiro:hasPermission name="43:update">
        	+"<input type='button' onclick='updFunction(this);' value='修改'  rowId='"+rowId+"' rowName='"+rowName+"'/>&nbsp;&nbsp"
			</shiro:hasPermission>
			<shiro:hasPermission name="43:custom1">
        	+"<input type='button' onclick='setFunction(this);' value='设置字段'  rowId='"+rowId+"' rowName='"+rowName+"'/>&nbsp;&nbsp";
			</shiro:hasPermission>
        	return url;
        }
        
        /** 选择表类型事件*/
        function setTableType(num){
        	if(num=="0"){
        		$('#selectTableTr').hide();
        	}else{
        		$("#selectTableTr").show();
        	}
        }
        
        $(function(){
        	$("#addTreeNodeWin").window({
        		width:482,
                height:285,
                modal:true
        	});
        	$("#addTreeNodeWin").window("close");
			$('#data_tree').treegrid({
				iconCls:'icon-save',
				striped : true,
        		border : true,
				height:350,
				width:"100%",
				nowrap: false, 
				rownumbers: true,
				animate:false,
				collapsible:true,
				singleSelect:false,
        		loadMsg:"正在努力拉取数据中...",
				url:'${ctx}/entity/entityAction!treeGrid.action',
				idField:'id',
				treeField:'entityCode',
				frozenColumns:[[
	                {title:'表名称',field:'entityCode',width:400,
		                formatter:function(value, row){
		                	return '<input class="checkboxClass_'+row.parentId+'" id="checkbox_'+row.id+'" type="checkbox" value="'+row.id+'" name="entityId" onClick="treeChecked(this)"/><span style="color:orange;font-size:15px">'+value+'</span>';
		                }
	                }
				]],
				columns:[[
					{field:'entityName',title:'表含义',width:150,rowspan:2,align:'center'},
					{field:'formCount',title:'表单数',width:150,align:'center'},
					{field:'id',title:'操作',width:300,align:'center', 
						formatter:function(value , row){
							var rowId = row.id;
				        	var rowName = row.entityCode;
				        	var url = ""
				        	<shiro:hasPermission name="43:view">
				        	+"<input type='button' onclick='viewFunction(this);' value='查看'  rowId='"+rowId+"' rowName='"+rowName+"'/>&nbsp;&nbsp"
							</shiro:hasPermission>
							<shiro:hasPermission name="43:update">
				        	+"<input type='button' onclick='updFunction(this);' value='修改'  rowId='"+rowId+"' rowName='"+rowName+"'/>&nbsp;&nbsp"
							</shiro:hasPermission>
							<shiro:hasPermission name="43:custom1">
				        	+"<input type='button' onclick='setFunction(this);' value='设置字段'  rowId='"+rowId+"' rowName='"+rowName+"'/>&nbsp;&nbsp";
							</shiro:hasPermission>
				        	return url;
		                }
					}
				]],
				onContextMenu: function(e,row){
					e.preventDefault();
					var checks = document.getElementsByName("entityId");
					for(var i=0;i<checks.length;i++){						
						var ck = checks[i];
						if(ck.checked){
							ck.checked=false;
						}
						if(ck.id=="checkbox_"+row.id){
							ck.checked=true;
						}
					}
					$('#mm').menu('show', {
						left: e.pageX,
						top: e.pageY
					});
				}
			});
		});       
        
        /** 当点击checkbox时触发*/
        function treeChecked(o){
        	var checkbox =  $(o);
        	var id = checkbox.attr("id");
        	var id = id.substring(id.lastIndexOf("_")+1);
        	if(checkbox.attr("checked")){
       			$("#data_tree").treegrid("expandAll", id);     		
       			var rows = $("#data_tree").treegrid("getChildren", id);
       			for(var i in rows){
       				$("#checkbox_"+rows[i].id).attr("checked", true);
       				$("data_tree").treegrid("select",rows[i].id);
       			}
        	}else{
        		var rows = $("#data_tree").treegrid("getChildren", id);
       			for(var i in rows){
       				$("#checkbox_"+rows[i].id).attr("checked", false);
       				$("data_tree").treegrid("unselect",rows[i].id);
       			}
        	}
        	
        }
		
        function appendTreeChild(){

        	var id = null;
        	var checks = document.getElementsByName("entityId");
        	for(var i=0;i<checks.length;i++){						
				var ck = checks[i];
				if(ck.checked){
					id = ck.id.substring(ck.id.lastIndexOf("_")+1);
				}
			}
        	if(id!=null){
        		addTreeFunction(id);
        	}
        }
        
        /** 右键添加事件*/
        function addTreeFunction(id){
        	var node = $("#data_tree").treegrid("find", id);
        	$("#treeParentTableName").text(node.entityCode);
        	$("#treeParentTableId").val(node.id);
        	clearAddForm();
        	$("#addTreeNodeWin").window("open");
        }
        
        /** 右键删除事件*/
        function removeTreeNode(){
			var rows = new Array();
			var checks = document.getElementsByName("entityId");
			for(var i=0;i<checks.length;i++){						
				var ck = checks[i];
				if(ck.checked){
					var node = $("#data_tree").treegrid("find", ck.id.substring(ck.id.lastIndexOf("_")+1));
					rows = $("#data_tree").treegrid("getChildren", node.id);
					rows.push(node);
					break;
				}
			}
			delFunction(rows);
        }
        
        /** 全部展开*/
        function expandAll(){
        	var checks = document.getElementsByName("entityId");
			for(var i=0;i<checks.length;i++){						
				var ck = checks[i];
				if(ck.checked){
					$("#data_tree").treegrid("expandAll", ck.id.substring(ck.id.lastIndexOf("_")+1));
				}
			}
        }
        
        /** 全部关闭*/
        function collapseAll(){
        	var checks = document.getElementsByName("entityId");
			for(var i=0;i<checks.length;i++){						
				var ck = checks[i];
				if(ck.checked){
					$("#data_tree").treegrid("collapseAll", ck.id.substring(ck.id.lastIndexOf("_")+1));
				}
			}
        }
--></script>
</head>
<body>
	<div class="panel-header" style="width: 100%;">
		<div class="panel-title panel-with-icon">表列表</div>
		<div class="panel-icon icon-edit"></div>
		<div class="panel-tool"></div>
	</div>
	<div class="datagrid-toolbar">
		<shiro:hasPermission name="43:add">
			<a href="javascript:addFunction()" style="float: left;"
				class="l-btn l-btn-plain"> <span class="l-btn-left"> <span
					class="l-btn-text icon-add" style="padding-left: 20px;">添加</span>
			</span>
			</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="43:delete">
			<a href="javascript:delFunction()" style="float: left;"
				class="l-btn l-btn-plain"> <span class="l-btn-left"> <span
					class="l-btn-text icon-remove" style="padding-left: 20px;">删除</span>
			</span>
			</a>
		</shiro:hasPermission>
		<a href="javascript:searchFunction()" style="float: left;"
			class="l-btn l-btn-plain"> <span class="l-btn-left"> <span
				class="l-btn-text icon-search" style="padding-left: 20px;">查询</span>
		</span>
		</a>
	</div>
	<div style="height: 80%; width: 100%">
		<!--     <table id="data_list"> -->
		<!--         <thead> -->
		<!--         <tr> -->
		<!--             <th field="entityCode" width="20%" title="表名称">表名称</th> -->
		<!--             <th field="entityName" width="30%" title="表含义">表含义</th> -->
		<!--             <th field="formCount" width="20%" title="表单数">表单数</th> -->
		<!--             <th field="id" width="30%" title="实体id" formatter="gridFormatter">操作</th> -->
		<!--         </tr> -->
		<!--         </thead> -->
		<!--     </table> -->
		<table id="data_tree"></table>
	</div>



	<div id="mm" class="easyui-menu" style="width: 120px;">
		<div onclick="appendTreeChild()">添加子表</div>
		<div onclick="removeTreeNode()">删除</div>
		<div onclick="expandAll()">全部展开</div>
		<div onclick="collapseAll()">全部关闭</div>
	</div>

	<!-- 添加窗口 start -->
	<div id="addWin" iconCls="icon-save" title="添加表">

		<form style="padding: 10px 20px 10px 85px;" id="addForm" method="post">
			<table cellpadding="5px" style="font-size: 12px;">
				<tr>
					<td>选择表类型</td>
					<td>主表<input name="tableType" type="radio" value="0"
						checked="checked" onclick="setTableType(0)" />&nbsp;&nbsp;从表<input
						name="tableType" type="radio" value="1" onclick="setTableType(1)" /></td>
				</tr>
				<tr id="selectTableTr" style="display: none">
					<td>选择主表</td>
					<td><input id="tableSelect" name="parentTableId"
						style="width: 155px"></td>
				</tr>
				<tr>
					<td>表名</td>
					<td><input id="addTreeEntityCode" name="entity.entityCode" /></td>
				</tr>
				<tr>
					<td>表含义</td>
					<td><input class="easyui-validatebox" id="addTreeEntityName"
						name="entity.entityName" /></td>
				</tr>
			</table>
			<div style="padding: 0px;">
				<input type="hidden" name="entity.entityType" value="0" /> <a
					href="javascript:void(0);" class="easyui-linkbutton" icon="icon-ok"
					onclick="addSubmitForm(this);return false;">确定</a> <a
					href="javascript:void(0);" class="easyui-linkbutton"
					icon="icon-cancel" onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>

	</div>
	<!-- 添加窗口 end -->

	<!-- 添加treegrid子表 start-->
	<div id="addTreeNodeWin">
		<form style="padding: 10px 20px 10px 85px" id="addTreeForm"
			method="post">
			<table cellpadding="5px" style="font-size: 12px;">
				<tr>
					<td>主表</td>
					<td><label id="treeParentTableName"></label><input
						name="tableType" type="hidden" value="1" /><input
						id="treeParentTableId" name="parentTableId" type="hidden" /></td>
				</tr>
				<tr>
					<td>表名</td>
					<td><input class="easyui-validatebox" id="addEntityCode"
						name="entity.entityCode" /></td>
				</tr>
				<tr>
					<td>表含义</td>
					<td><input class="easyui-validatebox" id="addEntityName"
						name="entity.entityName" /></td>
				</tr>
			</table>
			<div style="padding: 0px;">
				<input type="hidden" name="entity.entityType" value="0" /> <a
					href="javascript:void(0);" class="easyui-linkbutton" icon="icon-ok"
					onclick="addSubmitForm(this);return false;">确定</a> <a
					href="javascript:void(0);" class="easyui-linkbutton"
					icon="icon-cancel" onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>
	</div>
	<!-- 添加treegrid子表 end-->

	<!-- 更新窗口 start-->
	<div id="updateWin" iconCls="icon-save" title="更新表">
		<form style="padding: 10px 20px 10px 85px;" id="updateForm"
			method="post">
			<table cellpadding="5px" style="font-size: 12px;">
				<tr>
					<td>父表名</td>
					<td><label id="updateParentCode"></label></td>
				</tr>
				<tr>
					<td>表名</td>
					<td><input class="easyui-validatebox" id="updateEntityCode"
						name="entity.entityCode" /></td>
				</tr>
				<tr>
					<td>表含义</td>
					<td><input class="easyui-validatebox" id="updateEntityName"
						name="entity.entityName" /></td>
				</tr>
			</table>
			<input type="hidden" name="entity.id" id="updateEntityId" />
			<div style="padding: 0px;">
				<a href="javascript:void(0);" class="easyui-linkbutton"
					icon="icon-ok" onclick="updateSubmitForm();return false;">确定</a> <a
					href="javascript:void(0);" class="easyui-linkbutton"
					icon="icon-cancel" onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>
	</div>
	<!-- 更新窗口 end-->

	<!-- 查看窗口  start-->
	<div id="viewWin" iconCls="icon-save" title="查看表信息">
		<form style="padding: 10px 20px 10px 85px;" id="updateForm"
			method="post">
			<table cellpadding="5px" style="font-size: 12px;">
				<tr>
					<td>父表名</td>
					<td><label id="viewParentCode"></label></td>
				</tr>
				<tr>
					<td>表名</td>
					<td><input class="easyui-validatebox" id="viewEntityCode"
						name="entity.entityCode" /></td>
				</tr>
				<tr>
					<td>表含义</td>
					<td><input class="easyui-validatebox" id="viewEntityName"
						name="entity.entityName" /></td>
				</tr>
			</table>
			<div style="padding: 0px;">
				<a href="javascript:void(0);" class="easyui-linkbutton"
					icon="icon-cancel" onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>
	</div>
	<!-- 查看窗口  end-->

	<!-- 搜索窗口 start-->
	<div id="searchWin" iconCls="icon-save" title="搜索表">
		<form style="padding: 10px 20px 10px 85px;">
			<table cellpadding="5px" style="font-size: 12px;">
				<tr>
					<td>表&nbsp;&nbsp;&nbsp;&nbsp;名:</td>
					<td><input class="easyui-validatebox" id="searchEntityCode"
						name="entity.entityCode" /></td>
				</tr>
				<tr>
					<td>表含义:</td>
					<td><input class="easyui-validatebox" id="searchEntityName"
						name="entity.entityName" /></td>
				</tr>
			</table>
			<input type="hidden" name="entity.id" id="viewEntityId" />


			<div style="padding: 0px;">
				<a href="javascript:void(0);" class="easyui-linkbutton"
					icon="icon-search" onclick="searchSubmitForm();return false;">确定</a>
				<a href="javascript:void(0);" class="easyui-linkbutton"
					icon="icon-cancel" onclick="closeAllWin();return false;">取消</a>
			</div>
		</form>
	</div>
	<!-- 搜索窗口 -->
</body>
</html>