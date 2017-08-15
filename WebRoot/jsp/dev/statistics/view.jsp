<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>统计</title>
</head>
<body>
	<div align="center">
		<%-- 	<img src="/whxm/jfreechart/JFreeChartAction!randerChart?id=${param.imageId}" style="width:650px;height:400px" border="0" align="center"></img> --%>
		<iframe
			src="${ctx}/jfreechart/JFreeChartAction!randerChart?id=${param.imageId}"
			style="width: 700px; height: 450px" frameborder="0" scrolling="no"></iframe>
	</div>
</body>
</html>