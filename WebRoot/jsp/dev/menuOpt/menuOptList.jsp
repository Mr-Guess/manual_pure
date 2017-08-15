
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
<script type="text/javascript" src="${ctx}/js/validator.js"></script>

<title>菜单权限管理</title>
<script type="text/javascript">
			var currentIndex = 0;
			var dt=$("#data_list");
			var optType=[{val:0,text:'基本查看'},{val:1,text:'单记录操作'},{val:2,text:'多记录操作'},{val:3,text:'记录无关操作'},{val:4,text:'详情操作'}];
			//初始化data_list
			var lastIndex;
			$(document).ready(function () {
						$('#urlPara').validatebox();
	                	$("#operateWin").window({
	                		width:520,
	                        height:385,
	                        modal:true
	                	});
	                	$("#funcName").validatebox({
	                		required:true,
	                		validType:"alpha"
	                	});
	                	$("#funcPara").validatebox({
	                		validType:"alpha"
	                	});
	                	/* 
	                	$("#funcContent").validatebox({
	                		validType:"alpha",
	                		required:true
	                	});
	                	 */
	                	$("#openType").combobox({
	                		data:[{val:'0',text:'弹出框'},{val:'1',text:'链接'}],
	                		valueField:'val',
	                		textField:'text',
	                		editable:false
	                	});
	                	$("#openType2").combobox({
	                		data:[{value:'0',text:'弹出框'},{value:'1',text:'链接'}],
	                		valueField:'value',
	                		textField:'text',
	                		editable:false
		                });
	                	$("#displayType").combobox({
	                		data:[{val:'0',text:'否'},{val:'1',text:'是'}],
	                		valueField:'val',
	                		textField:'text',
	                		editable:false,
	                		onSelect:function(value){
	                			if(value.val=='1'){
	                				$("#uploadTr").show();
	                			}else{
	                				$("#uploadTr").hide();
	                			}
	                		}
	                	});
	                	$("#formId").combobox({
	                		url:"${ctx}/form/formAction!formList.action",
	                		valueField:'id',
	                		textField:'formName',
	                		editable:false
	                	});
	                	$("#operateWin").window("close");
	                	dt=$("#data_list");
	        	        dt.datagrid({
	        	        	title:'菜单名称：${param.menuName}',
	        	        	iconCls:'icon-edit',
        	        		width:700,
        					height:450,
        					nowrap: true,
        					autoRowHeight: false,
        					striped: true,
        					collapsible:true,
        					url:"${ctx}/menuOpt/menuOpt!list.action?menuId=${param.menuId}&menuName=${param.menuName}",
        					remoteSort: false,
        					singleSelect:true,
        					idField:'menuId',
        					rownumbers:true,
        					toolbar:[{
        						id:'btnadd',
        						text:'新增',
        						iconCls:'icon-add',
        						handler:addOpt
        					},'-',{
        						id:'btncut',
        						text:'删除',
        						iconCls:'icon-cut',
        						handler:cutOpt
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
        			        },
        			        onAfterEdit:function(index,row){
        			            row.editing = false;
        			            dt.datagrid("refreshRow", index);
        			        },
        			        onCancelEdit:function(index,row){
        			            row.editing = false;
        			            dt.datagrid("refreshRow", index);
        			        },
        					onClickRow:function(index,row){
        						if (lastIndex != index){
        							dt.datagrid('endEdit', lastIndex);
        							
        						}
        						dt.datagrid('beginEdit', index);
        						lastIndex = index;
        					}
        			        
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
			
			//保存权限
			function saveOpt(){
				var rows=dt.datagrid("getRows");
				var flag=true;
				for(var i=0;i<rows.length;i++){
					var row=rows[i];
					dt.datagrid("endEdit",dt.datagrid("getRowIndex",row));
					if(row.editing){
						flag=false;
					}
					if(!Boolean(row.optUrl)){
						row.optUrl="";
					}
				}
				if(flag){
					var detailstr=optString(rows);
					$.ajax({
						url:"${ctx}/menuOpt/menuOpt!addMenuOpt.action",
						type:"post",
						data:"detail="+detailstr+"&menuId=${param.menuId}",
						error: function(XMLHttpRequest, textStatus, errorThrown) {
	                        XMLHttpRequest.status;
	                        XMLHttpRequest.readyState;
	                        textStatus;
	                    },
	                    complete: function(XMLHttpRequest, textStatus) {
	                        XMLHttpRequest.status;
	                        textStatus;// 调用本次AJAX请求时传递的options参数
	                    },
	                    success:function(ret,status){
	                    	if(ret=="ok"){
	                    		$.messager.show({
	                                title:'成功',
	                                msg:"权限保存成功",
	                                timeout:2000,
	                                showType:'slide'
	                            });
	                    	}else{
	                    		$.messager.show({
	                                title:'失败',
	                                msg:"权限保存失败",
	                                timeout:2000,
	                                showType:'slide'
	                            });
	                    	}
	                    	dt.datagrid("reload");
	                    }
					});		
				}else{
					$.messager.alert("警告","请填写完整");
				}
						
			}
			
			function optString(rows){
				var detailstr="";
				var detail = [];
				for(var i=0;i<rows.length;i++){
					var row=rows[i];				
		            var optCode = row.optCode;
		            var optName = row.optName;
		            var optUrl = row.optUrl;
		            var optType = row.optType;
		            var optOrder = row.optOrder;
		            var item = optCode + "," + optName + "," + optUrl + "," + optType+ "," + optOrder;
		            detail.push(item);
				}				
		        detailstr = detail.join(";");
				return detailstr;
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
	        	gdData.rows[iMovedRowIndex].optOrder=iMovedRowIndex+1;
	        	gdData.rows[iCurRowIndex]=RowTemp;
	        	gdData.rows[iCurRowIndex].optOrder=iCurRowIndex+1;
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
			
			/** 选中类型时触发*/
			function selected(nv,ov){
				if(nv=="2"){
					$(".typeTwo").show();
					$("#sqlContent").attr("disabled",false);
					$("#funcContent").attr("disabled",true);
					$("#funcName").attr("disabled",true);
					$(".typeOne").hide();
					$(".typeThree").hide();
				}
				else if(nv=="1"){
					$(".typeOne").show();
					$("#sqlContent").attr("disabled",true);
					$("#funcContent").attr("disabled",true);
					$("#funcName").attr("disabled",true);
					$(".typeTwo").hide();
					$(".typeThree").hide();
				}else{
					$(".typeThree").show();
					$("#sqlContent").attr("disabled",true);
					$("#funcContent").attr("disabled",false);
					$("#funcName").attr("disabled",false);
					$(".typeOne").hide();
					$(".typeTwo").hide();
				}					
			}
			
			/** 渲染操作列*/
			function operationFormatter(val, row, index){
				var code = $("#data_list").datagrid("getRows")[index].optCode;
				if(!code){
					return;
				}
				if(code=="add"||code=="delete"||code=="exp"||code=="print"||code=="imp"){
					return "";
				}
				if(code=="view"||code=="baseview"||code=="update"){
					return "<a href='#' onclick='operateFunction("+index+",1)'>操作</a>";
				}
				return "<a href='#' onclick='operateFunction("+index+",0)'>操作</a>";
			}
			
			/** 操作动作*/
			function operateFunction(index, type){
				var row = $("#data_list").datagrid("getRows")[index];
				if(row.fun)			
				$("#functionDiv").show();
				$("#uploadDiv").show();
				if(type=='1'){
					$("#functionDiv").hide();
				}
				if(type=="0"){
					$("#uploadDiv").hide();
				}
				currentIndex = index;
				$("#operateWin").window("open");			
				$("#menuOptId").val(row.id);
				$("#menuOptId2").val(row.id);
				$("#funcName").val(row.funcName);
				$("#funcPara").val(row.funcPara);
				$("#funcContent").val(row.funcContent);
				$("#openType").val(row.openType);
				$("#openType2").val(row.openType);
				$("#displayType").val(row.displayType);
				$("#openType").combobox("setValue", row.openType);
				$("#openType2").combobox("setValue", row.openType);
				$("#displayType").combobox("setValue", row.displayType);
				$("#formId").combobox("setValue", row.formId);
				$("#urlPara").val(row.urlPara);
				if(row.displayType=="1"){
					$("#uploadTr").show();
				}else{
					$("#uploadTr").hide();
				}
				if(type=='1'){
					$("#functionDiv").hide();
					$("#uploadDiv").show();
				}
				if(type=="0"){
					$("#functionDiv").show();
					$("#uploadDiv").hide();
				}
				
				if(row.optCode=="view"||row.optCode=="update"){
					
					$("#typeFour").show();
					$("#openType2").attr("disabled",false);
				}else{
					$(".typeFour").hide();
					$("#openType2").attr("disabled",true);
				}
				updateSaveUrl(row);				
			} 
			
			/** 更新模板名*/
			function updateSaveUrl(row){
				var saveUrl = row.designPageUrl;
				if(saveUrl!=null&&saveUrl.length>3){
					var start=saveUrl.lastIndexOf("/");
					if(start==-1)
						start=saveUrl.lastIndexOf("\\");
					var template = saveUrl.substring(start+1,saveUrl.lastIndexOf("."));
					$("#usedTemplate").show();
					$("#showTemplate").text(template);
				}else{
					$("#usedTemplate").hide();
					$("#showTemplate").text("");
				}
			}
			
			/** 关闭所有窗口*/
			function closeAllWin(){
				$("#operateWin").window("close");
				$("#menuOptId").val("");
				$("#menuOptId2").val("");		
				$("#funcName").val('');
				$("#funcPara").val('');
				$("#funcContent").val('');
				$("#openType").val('');
				$("#displayType").val('');
			}
			
			/** 提交上传*/
			function uploadSubmit(){
				$("#uploadForm").submit();
				closeAllWin();
			}
			
			/** 上传成功后返回*/
			function uploadSuccess(templateName){
				$.messager.alert("提示","操作成功");
				$("#data_list").datagrid("reload");
				var form = document.getElementById("uploadForm");
				var id = document.getElementById("menuOptId").value;
				form.reset();
				setUploadInfo(currentIndex);
				$("#menuOptId").val(id);
				$("#usedTemplate").show();
				var start=templateName.lastIndexOf("/");
				if(start==-1)
					start=templateName.lastIndexOf("\\");
				var template = templateName.substring(start+1,templateName.lastIndexOf("."));
				$("#showTemplate").text(template);
			}
			
			/** 刷新displayType下拉框内容*/
			function setUploadInfo(index){
				var row = $("#data_list").datagrid("getRows")[index];
				$("#displayType").combobox("setValue", row.displayType);
			}
			
			/** 提交表单*/
			function funcSubmit(){
				$('#functionForm').form({
	                url:'${ctx}/menuOpt/menuOpt!updateMenuOpt.action',
	                success:function (data) {
	                	var data = eval('(' + data + ')');
	                    if (data.operateSuccess) {
	                        $.messager.show({
	                            title:'成功',
	                            msg:data.operateMessage,
	                            timeout:2000,
	                            showType:'slide'
	                        });
	                        $("#data_list").datagrid("reload");
	                        operateFunction(currentIndex);
	                        $("#operateWin").window("close");
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
	            $('#functionForm').submit();
			}
			
			function showdisplayType(row){
				return row["text"];
			}
		</script>

</head>

<body style="background: #F1F6F9;">
	<div style="height: 500px">
		<table id="data_list" class="easyui-datagrid" loadMsg="正在努力拉取数据中..."
			fit="true" fitColumns="true">
			<thead>
				<tr>
					<th field="optCode" width="150" title="实体名称"
						editor="{type:'validatebox',options:{required:true}}">菜单权限编码</th>
					<th field="optName" width="150" title="实体名称"
						editor="{type:'validatebox',options:{required:true}}">菜单权限名称</th>
					<th field="optType" width="150" title="实体名称"
						editor="{type:'combobox',
	        				options:{valueField:'val'
			        				,textField:'text'
			        				,data:optType
			        				,required:true
			        				,editable:false
			        				,onChange:selected}}"
						formatter="getOptType">菜单权限类型</th>
					<th field="id" width="150" title="" formatter="operationFormatter">操作</th>
				</tr>
			</thead>
		</table>
	</div>

	<!-- 操作窗口 -->
	<div id="operateWin" iconCls="icon-save" title="操作">

		<div id="functionDiv">
			<form id="functionForm" style="padding: 10px 20px 10px 85px;">
				<table cellpadding="5px" style="font-size: 12px;" cellpadding="5px"
					style="font-size:12px;">
					<tr class="typeOne">
						<td>关联表单</td>
						<td><input id="formId" type="text" name="menuOpt.formId"
							style="width: 155px" /></td>
					</tr>
					<tr class="typeOne" id="funcParaTr">
						<td>Url参数</td>
						<td><input id="urlPara" type="text" name="menuOpt.urlPara"
							validtype="likeJsonStr" required="true" style="width: 155px" /></td>
					</tr>
					<tr class="typeThree">
						<td>事件名称</td>
						<td><input id="funcName" type="text" name="menuOpt.funcName" /></td>
					</tr>
					<tr class="typeTwo">
						<td>批量sql语句</td>
						<td><textarea style="width: 300px; height: 160px"
								id="sqlContent" name="menuOpt.funcContent"></textarea></td>
					</tr>
					<tr class="typeThree">
						<td>事件内容</td>
						<td><textarea style="width: 300px; height: 160px"
								id="funcContent" name="menuOpt.funcContent"></textarea></td>
					</tr>
					<tr class="typeOne">
						<td>打开方式</td>
						<td><input id="openType" type="text" name="menuOpt.openType"
							style="width: 155px" /></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="hidden" name="menuOpt.id" id="menuOptId2" />
							<a href="#" class="easyui-linkbutton" icon="icon-save"
							onclick="funcSubmit()">提交</a> <a href="#"
							class="easyui-linkbutton" icon="icon-cancel"
							onclick="closeAllWin()">取消</a></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="uploadDiv">
			<form id="uploadForm" method="post"
				action="${ctx }/menuOpt/upload.action" enctype="multipart/form-data"
				target="uploadFrame" style="padding: 10px 20px 10px 85px;">
				<table cellpadding="5px" style="font-size: 12px;">
					<tr>
						<td>是否定制页面</td>
						<td><input id="displayType" type="text"
							name="menuOpt.displayType" /></td>
					</tr>
					<tr class="typeFour">
						<td>打开方式</td>
						<td><input id="openType2" type="text" name="menuOpt.openType" /></td>
					</tr>
					<tr id="uploadTr">
						<td>上传模板</td>
						<td id="uploadTd"><input id="templateFile"
							style="width: 200px" type="file" name="templateFile" />
					</tr>
					<tr id="usedTemplate">
						<td>已有模板</td>
						<td id="showTemplate"></td>
					</tr>
					<tr>
						<td></td>
						<td><a href="#" class="easyui-linkbutton" icon="icon-save"
							onclick="uploadSubmit()">确认</a></td>
					</tr>
				</table>
				<input type="hidden" name="menuOpt.id" id="menuOptId" />
			</form>
		</div>
		<iframe name="uploadFrame" style="display: none"></iframe>
	</div>
</body>
</html>
