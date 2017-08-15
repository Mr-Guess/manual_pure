<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>监管网格</title>
<link rel="stylesheet" href="${ctx}/css/newCrud.css" type="text/css"></link>
<script type="text/javascript" src="${ctx}/js/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/CrudUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/SelectUtil.js"></script>
<style type="text/css">
.table_nemu_new {
	text-align: center;
}

.btn {
	height: 30px;
	background: #6baccf center no-repeat;
	-moz-border-radius: 4px;
	-webkit-border-radius: 4px;
	border-radius: 4px;
	cursor: pointer;
	BORDER: #65b5e4 1px solid;
}
</style>
<script type="text/javascript">
function forUpdate(){
	var id = $('#id').val();
	var name = $('#print_name').val();
	name = decodeURI(name);
	location.href = '${ctx}/reseau/reseauAction!forUpdate?id='+id+'&title='+encodeURI(encodeURI(name));
}
//调用附件页面
function attachmentFunction(rowId) {
		$('#attachmentFrame').attr('src', '${ctx}/jsp/sys/fileUpload/uploadFile.jsp?relationId=' + rowId);
		$('#viewAttachmentForm').dialog({
			title:'查看安全责任书',
			modal:true,
			draggable:false,
			minimizable:true,
			maximizable:true,
			maximized:false,
			buttons:[{
				text:'取消',
				iconCls:'icon-cancel',
				handler:function() {
					$('#viewAttachmentForm').dialog('close');
				}
			}]
		});
}

function print() {
	var id = $('#id').val();
	var name = $('#print_name').val();
	name = decodeURI(name);
	var url = '${ctx}/reseau/reseauAction!view_print?id='+id+'&title='+encodeURI(encodeURI(name));
	var win=window.open(url);       //打开一个空页面 
	win.moveTo(100,100);                      //移动到指定位置 
}
function viewTree() {
	window.open('${ctx}/enterprise/enterpriseAction!gridEntTree?id=${reseau.id}','_blank','Width='+ (screen.width) + ',Height=' + (screen.height) + ',Left=0,Top=0,status=yes,menubar=no, location=no,scrollbars=yes,resizable=yes');
}
$(document).ready(function(){
	var title = $('#print_name').val();
	title = decodeURI(title);
	$('#title').html(title);
});
</script>
</head>

<body>
	<center>
		<div>
			<br />
		</div>
		<form id="addForm" method="post">
			<input id="id" type="hidden" name="id" value="${reseau.id }" /> <input
				id="print_name" type="hidden" value="${param.title}" />
			<!--	地区码	-->
			<input type="hidden" name="placeCode" value="${reseau.placeCode}" />
			<div style="margin: 0 auto;" align="center" name="addDiv">
				<table class="detailTable" cellpadding="5px"
					style="font-size: 12px;" cellspacing="1" border="0"
					bgcolor="#aed0ea" width="95%">
					<tr align="center">
						<th class="table_th" colspan="3"
							style="background-color: #f1f6f9; height: 35px; margin: 0; padding-bottom: 0">
							<b id="title"></b>
						</th>
					</tr>
					<tr>
						<td class="table_con"
							style="background-color: #f1f6f9; width: 20%"></td>
						<td class="table_con"
							style="background-color: #f1f6f9; width: 25%">姓名</td>
						<td class="table_con" style="background-color: #f1f6f9">安全职责</td>
					</tr>
					<tr>
						<td class="table_nemu_new" style="background-color: #f1f6f9">&nbsp;主要负责人:</td>
						<td class="table_con" height="60px">${reseau.mainTake}</td>
						<td class="table_con">${reseau.mainDuty}</td>
					</tr>
					<tr>
						<td class="table_nemu_new" style="background-color: #f1f6f9">&nbsp;分管负责人:</td>
						<td class="table_con" height="60px">${reseau.subTake}</td>
						<td class="table_con">${reseau.subDuty}</td>
					</tr>
					<tr>
						<td class="table_nemu_new" style="background-color: #f1f6f9">一岗双责负责人:</td>
						<td class="table_con" height="60px">${reseau.doubleTake}</td>
						<td class="table_con">${reseau.doubleDuty}</td>
					</tr>
					<tr>
						<td class="table_nemu_new" style="background-color: #f1f6f9">&nbsp;属地监管责任:</td>
						<td class="table_con" colspan="3" height="60px">
							${reseau.duties}</td>
					</tr>
					<tr>
						<td class="table_nemu_new" style="background-color: #f1f6f9">目标责任书:</td>
						<td class="table_con" colspan="3"><input type="button"
							class='zrs' value="责任书查看"
							onclick="attachmentFunction('${reseau.id}')" /></td>
					</tr>
				</table>
				<br>
				<div id="divButton">
					<c:if test="${valueMap[\"hasPower\"]}">
						<input type="button" id="wgqyck" class="btn" value=""
							onclick="viewTree()" />
					</c:if>
					<shiro:hasPermission name="${menuId}:update">
						<input type="button" value="" onclick="forUpdate()" class="update" />
					</shiro:hasPermission>
					<input type="button" value="" onclick="print()" class="pirt" />
				</div>
			</div>
		</form>
	</center>
	<!--附件层 -->
	<div style="display: none;">
		<div id="viewAttachmentForm" style="width: 500px; height: 400px;"
			data-options="iconCls:'icon-save'">
			<iframe frameborder="0" id="attachmentFrame" width="100%"
				height="98%" scrolling-x="no"></iframe>
		</div>
	</div>
</body>
</html>
