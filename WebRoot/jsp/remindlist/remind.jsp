<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jqueryhead.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作站</title>
<style type="text/css">
.contact{width:100px;height:15px;
margin:20px 0 0 50px;background-color:#CCCCCC;
text-align:center;
}
.us{display:none;width:300px;height:40px;
padding:10px;position:relative;top:0px;left:50px;
background-color:#0099FF;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
	$(".contact").mouseover(function(){
		$(".us").show("slow");
		$(".contact").mouseout(function(){
			$(".us").hide("slow");
		});
	});
})
</script>
</head>
<body>
<div class="contact">联系我们</div>
<div class="us">提示内容！提示内容！提示内容！</div>
</body>
</html>
</html>