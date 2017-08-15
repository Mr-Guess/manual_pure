<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%response.setStatus(200);%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>500 - 系统内部错误</title>
</head>
<style>
* {
	margin: 0;
	padding: 0;
}

html,body {
	height: 100%;
}

body {
	overflow: auto;
}

body {
	background: url(${ctx}/common/500.jpg) no-repeat center center;
}
</style>
<body>
</body>
</html>
