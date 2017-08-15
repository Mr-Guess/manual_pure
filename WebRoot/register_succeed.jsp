<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ay.framework.shiro.SystemParameter"%>
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link rel="stylesheet" href="${ctx}/css/register_succeed.css"
	type="text/css" />
<script type="text/javascript"
	src="${ctx}/resources/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript">
	$(function(){
		var username="${param.username}";
		var password="${param.password}";
		$("#login").click(function(){
			window.top.location.href="${ctx}/login!login.action?username="+username+"&password="+password;
		});
	});
</script>
</head>

<body>
	<div id="body">
		<div id="top">
			<img src="images/zc_name.png" /> <a href="login.jsp">返回首页</a>
		</div>
		<div id="main">
			<img src="images/symbol_check.png" />
			<div id="hint">您的帐号已成功注册！</div>
		</div>
		<div id="foot">
			<a href="javascript:void(0);" id="login"></a>
		</div>
	</div>
</body>
</html>

