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
</style>
<style media=print>
.Noprint {
	display: none;
}

.PageNext {
	page-break-after: always;
}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		var title = $('#title_s').val();
		title = decodeURI("${param.title}");
		$('#title').html(title);
	});
</script>
</head>

<body style="background-color: #e4e7e8;">
	<center>
		<div>
			<br />
		</div>
		<form id="addForm" method="post" style="width: 900px; height: auto;">
			<!--	地区码	-->
			<input type="hidden" name="placeCode" value="${reseau.placeCode }" />
			<div style="margin: 30px auto;" align="center" name="addDiv">
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
						<td class="table_con" style="background-color: #f1f6f9"></td>
						<td class="table_con" style="background-color: #f1f6f9">姓名</td>
						<td class="table_con" style="background-color: #f1f6f9">安全职责</td>
					</tr>
					<tr>
						<td class="table_nemu_new" style="background-color: #f1f6f9"><font
							color="red">*</font>&nbsp;主要负责人:</td>
						<td class="table_con" height="60px">${reseau.mainTake}</td>
						<td class="table_con">${reseau.mainDuty}</td>
					</tr>
					<tr>
						<td class="table_nemu_new" style="background-color: #f1f6f9"><font
							color="red">*</font>&nbsp;分管负责人:</td>
						<td class="table_con" height="60px">${reseau.subTake}</td>
						<td class="table_con">${reseau.subDuty}</td>
					</tr>
					<tr>
						<td class="table_nemu_new" style="background-color: #f1f6f9">一岗双责负责人:</td>
						<td class="table_con" height="60px">${reseau.doubleTake}</td>
						<td class="table_con">${reseau.doubleDuty}</td>
					</tr>
					<tr>
						<td class="table_nemu_new" style="background-color: #f1f6f9"><font
							color="red">*</font>&nbsp;属地监管责任:</td>
						<td class="table_con" colspan="3" height="60px">
							${reseau.duties}</td>
					</tr>
				</table>
				<br>
				<center class="Noprint">
					<OBJECT id="WebBrowser"
						classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0"
						width="0"></OBJECT>
					<a href="#" onclick="javascript:window.print();" class="print2">
					</a> <br /> <br />
				</center>
			</div>
		</form>
	</center>
</body>
</html>
