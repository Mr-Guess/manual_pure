<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="gb2312"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" type="text/css" href="${ctx}/css/newCrud.css">
<title>企业信息</title>
	<style type="text/css">
	</style>
	<script type="text/javascript">
	function erenterpriseGrid_gridFormatt(value, rowData, rowIndex) {
		var rowId = rowData.id;
		var url = "";
		url += "<a title=\"选择\" onclick='window.parent.getEnt(\""+rowData.zzjgdm+"\",\""+rowData.id+"\",\""+rowData.qymc+"\",\""+rowData.fddbr+"\",\""+rowData.lxdh+"\",\""+rowData.yzbm+"\",\""+rowData.zcdz+"\");' class='btn2'><img src=\"${ctx}/images/choose.png\"></img></a>&nbsp;&nbsp;&nbsp;&nbsp;";
		return url;
	}
	function gridFormattmc(value, rowData, rowIndex) {
		var dm = rowData.zzjgdm;
		var url = "<span onclick=\"openNewWindow('"+dm+"')\">"+value+"</span>";
		//var url = value;
		return url;
	}
	
	function openNewWindow(zzjgdm){
		window.top.open("${ctx}/jsp/ent/baseSituation/entTree.jsp?zzjgdm="+zzjgdm);
	}
	
	function enterpriseGrid_searchForm() { 
		var params = {
				'mobileEnterprise.zzjgdm':$('input[name="enterprise.zzjgdm"]').val(),
				'mobileEnterprise.qymc':$('input[name="enterprise.qymc"]').val()
		};
		$('#dataList').datagrid('load', params);
	}
	function enterpriseGrid_clearSearchForm() {
		$('#enterpriseGrid_searchForm')[0].reset();
	}
	
	$(function(){
		var xzqhjd="${param.xzqhjd}";
		var url="${ctx}/mobileEnterprise/mobileEnterpriseAction!list.action";
		if(xzqhjd!=null&&xzqhjd!="")
			url+="?mobileEnterprise.xzqhjd="+xzqhjd;
		$("#dataList").datagrid({
			rownumbers:true,
			url:url,
			pagination:true,
			toolbar:"#toolbar",
			fitColumns:true,
			border:0
		});
	});
	</script>
</head>
<body style="margin:0 auto;width:100%;overflow: hidden;text-align: center;">
	<div class='toolbar_parent'>
	<div class='selectConditionDiv'>
		<form id="enterpriseGrid_searchForm" method="post">
				&nbsp;&nbsp;单位名称:&nbsp;&nbsp;<input style="width: 150px;"
					type="text" name="enterprise.qymc" /> &nbsp;&nbsp;<a
					href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-search" onclick="enterpriseGrid_searchForm();">查询</a>
				&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton"
					iconCls="icon-clear" onclick="enterpriseGrid_clearSearchForm();">清空</a>
		</form>
	</div>
	</div>
	
	<div class="data_list_div" style="height:60%">
		<table title="选择企业" loadMsg="正在加载,请稍后..." id="dataList" fit="true" fitColumns="true">
			<thead>
				<tr>
					<th align="center" field="qymc" width="150" formatter="gridFormattmc">单位名称</th>
					<th align="center" field="fddbr" width="150">法定代表人</th>
					<th align="center" field="lxdh" width="150">联系电话</th>
					<th align="center" field="id" width="50" formatter="erenterpriseGrid_gridFormatt">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</body>
</html>