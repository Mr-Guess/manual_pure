<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jqueryhead.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工作汇报</title>
<script type="text/javascript">
$(document).ready(function () {
	$('#tt').tabs({
	    border:false,
	    onSelect:function(record){
	    	if(record == "待办工作"){
	    		$("#stayFrame").attr('src', $('#stayFrame').attr('src'));
	    	}else if(record == "经办工作"){
	    		$("#doneFrame").attr('src', $('#doneFrame').attr('src'));
	    	}else if(record == "我的工作"){
	    		$("#myFrame").attr('src', $('#myFrame').attr('src'));
	    	}
	    }
	});
});
</script>
</head>
<body onresize="resizeGrid();">
	<div id="tt" class="easyui-tabs">
    <div title="待办工作" name="stayFrame" data-options="iconCls:'icon-add'"  style="padding:20px;">
		<iframe id="stayFrame" src="${ctx}/jsp/enterpriseManagement/report/doReporting.jsp" style="border: none; height: 800px;width: 100%;"></iframe>
    </div>
    <div title="经办工作" name="doneFrame" data-options="iconCls:'icon-search'" style="overflow:auto;padding:20px;">
		<iframe id="doneFrame" src="${ctx}/jsp/enterpriseManagement/report/doneReporting.jsp" style="border: none; height: 800px;width: 100%;"></iframe>
    </div>
    <div title="我的工作" name="myFrame" data-options="iconCls:'icon-cut'" style="padding:20px;">
		<iframe id="myFrame" src="${ctx}/jsp/enterpriseManagement/report/myReporting.jsp" style="border: none; height: 800px;width: 100%;"></iframe>
    </div>
</div>
</body>
</html>