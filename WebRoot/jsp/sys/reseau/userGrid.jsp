<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网格职责</title>
<!-- Bootstrap css file v2.2.1 -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/js/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/js/bootstrap/css/bootstrap-modal.css">
<!--[if lte IE 6]>
<link rel="stylesheet" type="text/css" href="${ctx}/js/bootstrap/css/bootstrap-ie6.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/bootstrap/css/ie.css">
<![endif]-->
<link rel="stylesheet" href="${ctx}/css/newCrud.css" type="text/css"></link>
<style type="text/css">
input[readonly],textarea[readonly] {
	cursor: default;
}

.combo {
	border: 0px;
}

.combo-arrow {
	position: relative;
	left: -22px;
	vertical-align: middle;
}

input[type="text"],textarea {
	margin-bottom: 0px;
}

.table td {
	vertical-align: middle;
}

p {
	margin: 0px;
}

.btn {
	display: inline-block;
	font-weight: bold;
	margin: 8px;
}

.btn:hover {
	display: inline-block;
}

table td p,.btn {
	font-size: 18px;
}
</style>
</head>
<body>
	<div id="viewWin" style="padding: 10px 20px 10px 20px;" align="center"
		name="addDiv">
		<div id="tableWrap">
			<h3>网格职责</h3>
			<table id="detailTable" cellpadding="5px" style="font-size: 12px;"
				cellspacing="1" border="0" bgcolor="#aed0ea" width="95%"
				class="table table-bordered">
				<tbody>
					<tr>
						<td width="20%"><p align="center">主要负责网格</p></td>
						<td width="50%"><c:forEach var="l"
								items="${valueMap[\"rus\"]}">
								<c:if test="${l.dutyType=='主要'}">
									<button onclick="viewTree(${l.gridconfig.id})"
										class="btn btn-large">${l.gridconfig.wgmc}</button>
								</c:if>
							</c:forEach></td>
					</tr>
					<tr>
						<td><p align="center">分管负责网格</p></td>
						<td><c:forEach var="l" items="${valueMap[\"rus\"]}">
								<c:if test="${l.dutyType=='分管'}">
									<button onclick="viewTree(${l.gridconfig.id})"
										class="btn btn-large">${l.gridconfig.wgmc}</button>
								</c:if>
							</c:forEach></td>
					</tr>
					<tr>
						<td><p align="center">一岗双责网格</p></td>
						<td><c:forEach var="l" items="${valueMap[\"rus\"]}">
								<c:if test="${l.dutyType=='一岗双责'}">
									<button onclick="viewTree(${l.gridconfig.id})"
										class="btn btn-large">${l.gridconfig.wgmc}</button>
								</c:if>
							</c:forEach></td>
					</tr>
					<tr>
						<td><p align="center">监管行业</p></td>
						<td><c:forEach var="l" items="${valueMap[\"industrys\"]}">
								<button onclick="" class="btn btn-large disabled">${l["industryName"]}</button>
							</c:forEach></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<script type="text/javascript">
	function viewTree(id) {
		$.openWindow('${ctx}/enterprise/enterpriseAction!gridEntTree?id='+id);
	}
	</script>
</body>
</html>