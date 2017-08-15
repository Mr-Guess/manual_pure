<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jqueryhead.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企业基本信息</title>

<link rel="stylesheet" type="text/css" href="${ctx}/css/newCrud.css">
<script type="text/javascript" src="${ctx}/js/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/CrudUtil.js"></script>
<script type="text/javascript">
        var grid = null;
        var tree = null;
        var key = null;

   $(document).ready(function () {
		 grid = new Grid('${ctx}/enterprise/enterpriseAction!list','data_list');
		 grid.loadGrid();
		 crud = new Crud({
				 grid:grid,
				 addFormObject:$('#curdaddForm'),
				 viewFormObject:$('#curdviewForm'),
				 updateFormObject:$('#curdupdateForm'),
				 searchFormObject:$('#searchForm'),
				 entityName:'enterprise',
				 moduleName:'企业基本信息', 
				 dialogCss:{width:'900px',height:'auto'},
				 url:'${ctx}/enterprise/enterpriseAction'
				});
		});
        function gridFormatterLength(value, rowData, rowIndex) {
        	if(value==null || value == '' || value == 'undefined')
        		return '';
    		if(value.length > 25)
    			return value.substring(0, 25) + '...';
    		return value;
    	}

        function gridFormatter(value, rowData, rowIndex) {
        	var rowId = rowData.id;
        	var url = "";
        		<shiro:hasPermission name='${menuId}:view'> 
        	url += "<input type='button' class='btn1' onclick='crud.view(\"" + rowId + "\");' value='查看'/>&nbsp;&nbsp;";
        		</shiro:hasPermission>
        		<shiro:hasPermission name='${menuId}:update'>
        	url += "<input type='button' class='btn1' onclick='crud.update(\"" + rowId + "\");' value='修改'/>&nbsp;&nbsp;";
        		</shiro:hasPermission> 
        	return url;
        }
        
        function cleanFunction(){ 
        crud.clearSearch(); 
        crud.search(); 
        }
        
        function resizeGrid(){ 
        $('#data_list').datagrid('resize', { 
        width:function(){
        return document.body.clientWidth*0.9;
        } 
        }); 
        }
        
</script>
</head>
<body onresize="resizeGrid();">



<div style="margin: auto;">
	 <div id = "toolbar">
	<form id="searchForm" style="margin: 0;">
	
	&nbsp;&nbsp;企业名称:&nbsp;&nbsp;<input type="text" name="enterprise.qymc"/>
	&nbsp;&nbsp;工商注册号:&nbsp;&nbsp;<input type="text" name="enterprise.gszch"/>
	&nbsp;&nbsp;组织机构代码:&nbsp;&nbsp;<input type="text" name="enterprise.zzjgdm"/>
	&nbsp;&nbsp;成立日期:&nbsp;&nbsp;<input type="text" name="enterprise.clrq"/>
	&nbsp;&nbsp;法定代表人:&nbsp;&nbsp;<input type="text" name="enterprise.fddbr"/>
	&nbsp;&nbsp;联系电话:&nbsp;&nbsp;<input type="text" name="enterprise.lxdh"/>
	&nbsp;&nbsp;现用作审核意见:&nbsp;&nbsp;<input type="text" name="enterprise.czhm"/>
	&nbsp;&nbsp;电子邮箱:&nbsp;&nbsp;<input type="text" name="enterprise.dzyx"/>
	&nbsp;&nbsp;注册地址:&nbsp;&nbsp;<input type="text" name="enterprise.zcdz"/>
	&nbsp;&nbsp;邮政编码:&nbsp;&nbsp;<input type="text" name="enterprise.yzbm"/>
	&nbsp;&nbsp;行政区划省:&nbsp;&nbsp;<input type="text" name="enterprise.xzqhs"/>
	&nbsp;&nbsp;xzqhsName:&nbsp;&nbsp;<input type="text" name="enterprise.xzqhsName"/>
	&nbsp;&nbsp;行政区划市:&nbsp;&nbsp;<input type="text" name="enterprise.xzqh"/>
	&nbsp;&nbsp;xzqhName:&nbsp;&nbsp;<input type="text" name="enterprise.xzqhName"/>
	&nbsp;&nbsp;行政区划区县:&nbsp;&nbsp;<input type="text" name="enterprise.xzqhqx"/>
	&nbsp;&nbsp;xzqhqxName:&nbsp;&nbsp;<input type="text" name="enterprise.xzqhqxName"/>
	&nbsp;&nbsp;行政区划街道:&nbsp;&nbsp;<input type="text" name="enterprise.xzqhjd"/>
	&nbsp;&nbsp;xzqhjdName:&nbsp;&nbsp;<input type="text" name="enterprise.xzqhjdName"/>
	&nbsp;&nbsp;经济类型:&nbsp;&nbsp;<input type="text" name="enterprise.jjlx"/>
	&nbsp;&nbsp;jjlxName:&nbsp;&nbsp;<input type="text" name="enterprise.jjlxName"/>
	&nbsp;&nbsp;行业类别:&nbsp;&nbsp;<input type="text" name="enterprise.hylb"/>
	&nbsp;&nbsp;hylbName:&nbsp;&nbsp;<input type="text" name="enterprise.hylbName"/>
	&nbsp;&nbsp;隶属关系:&nbsp;&nbsp;<input type="text" name="enterprise.lsgx"/>
	&nbsp;&nbsp;lsgxName:&nbsp;&nbsp;<input type="text" name="enterprise.lsgxName"/>
	&nbsp;&nbsp;经营范围:&nbsp;&nbsp;<input type="text" name="enterprise.jyfw"/>
	&nbsp;&nbsp;注册资金（万）:&nbsp;&nbsp;<input type="text" name="enterprise.zczj"/>
	&nbsp;&nbsp;企业状态:&nbsp;&nbsp;<input type="text" name="enterprise.qyzt"/>
	&nbsp;&nbsp;qyztName:&nbsp;&nbsp;<input type="text" name="enterprise.qyztName"/>
	&nbsp;&nbsp;企业位置经度:&nbsp;&nbsp;<input type="text" name="enterprise.qywzjd"/>
	&nbsp;&nbsp;企业位置维度:&nbsp;&nbsp;<input type="text" name="enterprise.qywzwd"/>
	&nbsp;&nbsp;企业平面图:&nbsp;&nbsp;<input type="text" name="enterprise.qypmt"/>
	&nbsp;&nbsp;主要负责人:&nbsp;&nbsp;<input type="text" name="enterprise.zyfzr"/>
	&nbsp;&nbsp;主要负责人固定电话:&nbsp;&nbsp;<input type="text" name="enterprise.zyfzrgddh"/>
	&nbsp;&nbsp;主要负责人移动电话:&nbsp;&nbsp;<input type="text" name="enterprise.zyfzryddh"/>
	&nbsp;&nbsp;主要负责人电子邮箱:&nbsp;&nbsp;<input type="text" name="enterprise.zyfzrdzyx"/>
	&nbsp;&nbsp;安全负责人:&nbsp;&nbsp;<input type="text" name="enterprise.aqfzr"/>
	&nbsp;&nbsp;安全负责人固定电话:&nbsp;&nbsp;<input type="text" name="enterprise.aqfzrgddh"/>
	&nbsp;&nbsp;安全负责人移动电话:&nbsp;&nbsp;<input type="text" name="enterprise.aqfzryddh"/>
	&nbsp;&nbsp;安全负责人电子邮箱:&nbsp;&nbsp;<input type="text" name="enterprise.aqfzrdzyx"/>
	&nbsp;&nbsp;安全机构设置情况:&nbsp;&nbsp;<input type="text" name="enterprise.aqjgszqk"/>
	&nbsp;&nbsp;aqjgszqkName:&nbsp;&nbsp;<input type="text" name="enterprise.aqjgszqkName"/>
	&nbsp;&nbsp;从业人员数量:&nbsp;&nbsp;<input type="text" name="enterprise.cyrysl"/>
	&nbsp;&nbsp;特种作业人员数量:&nbsp;&nbsp;<input type="text" name="enterprise.tzzyrysl"/>
	&nbsp;&nbsp;专职安全生产管理人员数量:&nbsp;&nbsp;<input type="text" name="enterprise.zzaqscglrysl"/>
	&nbsp;&nbsp;专职应急管理人员数量:&nbsp;&nbsp;<input type="text" name="enterprise.zzyjglrysl"/>
	&nbsp;&nbsp;注册安全工程师人员数量:&nbsp;&nbsp;<input type="text" name="enterprise.zcaqgcsrys"/>
	&nbsp;&nbsp;安全监管监察机构:&nbsp;&nbsp;<input type="text" name="enterprise.aqjgjcjg"/>
	&nbsp;&nbsp;生产经营地址:&nbsp;&nbsp;<input type="text" name="enterprise.scjydz"/>
	&nbsp;&nbsp;企业规模:&nbsp;&nbsp;<input type="text" name="enterprise.qygm"/>
	&nbsp;&nbsp;qygmName:&nbsp;&nbsp;<input type="text" name="enterprise.qygmName"/>
	&nbsp;&nbsp;规模情况:&nbsp;&nbsp;<input type="text" name="enterprise.gmqk"/>
	&nbsp;&nbsp;gmqkName:&nbsp;&nbsp;<input type="text" name="enterprise.gmqkName"/>
	&nbsp;&nbsp;监管分类:&nbsp;&nbsp;<input type="text" name="enterprise.jgfl"/>
	&nbsp;&nbsp;jgflName:&nbsp;&nbsp;<input type="text" name="enterprise.jgflName"/>
	&nbsp;&nbsp;安全生产标准化等级:&nbsp;&nbsp;<input type="text" name="enterprise.aqscbzhdj"/>
	&nbsp;&nbsp;aqscbzhdjName:&nbsp;&nbsp;<input type="text" name="enterprise.aqscbzhdjName"/>
	&nbsp;&nbsp;安全生产标准化证书编号:&nbsp;&nbsp;<input type="text" name="enterprise.aqscbzhzsbh"/>
	&nbsp;&nbsp;安全生产标准化到期时间:&nbsp;&nbsp;<input type="text" name="enterprise.aqscbzhdqsj"/>
	&nbsp;&nbsp;安全生产许可证编号:&nbsp;&nbsp;<input type="text" name="enterprise.aqxkzbh"/>
	&nbsp;&nbsp;安全生产许可证到期时间:&nbsp;&nbsp;<input type="text" name="enterprise.aqscxkzdqsj"/>
	&nbsp;&nbsp;填表人:&nbsp;&nbsp;<input type="text" name="enterprise.tbr"/>
	&nbsp;&nbsp;填表人联系电话:&nbsp;&nbsp;<input type="text" name="enterprise.tbrlxdh"/>
	&nbsp;&nbsp;填表日期:&nbsp;&nbsp;<input type="text" name="enterprise.tbrq"/>
	&nbsp;&nbsp;监管部门树:&nbsp;&nbsp;<input type="text" name="enterprise.dept"/>
	&nbsp;&nbsp;显示的部门:&nbsp;&nbsp;<input type="text" name="enterprise.showDept"/>
	&nbsp;&nbsp;deptName:&nbsp;&nbsp;<input type="text" name="enterprise.deptName"/>
	&nbsp;&nbsp;hylbShow:&nbsp;&nbsp;<input type="text" name="enterprise.hylbShow"/>
	&nbsp;&nbsp;销售金额:&nbsp;&nbsp;<input type="text" name="enterprise.xsje"/>
	&nbsp;&nbsp;是否有隶属集团:&nbsp;&nbsp;<input type="text" name="enterprise.sfylsjt"/>
	&nbsp;&nbsp;隶属集团名称:&nbsp;&nbsp;<input type="text" name="enterprise.lsjtmc"/>
	&nbsp;&nbsp;安全管理人:&nbsp;&nbsp;<input type="text" name="enterprise.aqglr"/>
	&nbsp;&nbsp;安全管理人员电话:&nbsp;&nbsp;<input type="text" name="enterprise.aqglrydh"/>
	&nbsp;&nbsp;市监管部门:&nbsp;&nbsp;<input type="text" name="enterprise.sjgbm"/>
	&nbsp;&nbsp;安全生产管理机构:&nbsp;&nbsp;<input type="text" name="enterprise.aqscgljg"/>
	&nbsp;&nbsp;安全生产许可证发证日期:&nbsp;&nbsp;<input type="text" name="enterprise.aqscxkzfzrq"/>
	&nbsp;&nbsp;安全生产操作规程 :&nbsp;&nbsp;<input type="text" name="enterprise.aqscczgc"/>
	&nbsp;&nbsp;安全生产规章制度:&nbsp;&nbsp;<input type="text" name="enterprise.aqsczzzd"/>
	&nbsp;&nbsp;安全生产责任制度:&nbsp;&nbsp;<input type="text" name="enterprise.aqsczrzd"/>
	&nbsp;&nbsp;建设项目'三同时':&nbsp;&nbsp;<input type="text" name="enterprise.jsxmsts"/>
	&nbsp;&nbsp;专项监管大类:&nbsp;&nbsp;<input type="text" name="enterprise.zxjghya"/>
	&nbsp;&nbsp;专项监管小类:&nbsp;&nbsp;<input type="text" name="enterprise.zxjghyb"/>
	&nbsp;&nbsp;zxjghybName:&nbsp;&nbsp;<input type="text" name="enterprise.zxjghybName"/>
	&nbsp;&nbsp;zxjghyaName:&nbsp;&nbsp;<input type="text" name="enterprise.zxjghyaName"/>
	&nbsp;&nbsp;lsjtmcName:&nbsp;&nbsp;<input type="text" name="enterprise.lsjtmcName"/>
	&nbsp;&nbsp;是否是集团:&nbsp;&nbsp;<input type="text" name="enterprise.clique"/>
	&nbsp;&nbsp;行业类别小类:&nbsp;&nbsp;<input type="text" name="enterprise.hylbxl"/>
	&nbsp;&nbsp;经济类型小类:&nbsp;&nbsp;<input type="text" name="enterprise.jjlxxl"/>
	&nbsp;&nbsp;hylbxlName:&nbsp;&nbsp;<input type="text" name="enterprise.hylbxlName"/>
	&nbsp;&nbsp;jjlxxlName:&nbsp;&nbsp;<input type="text" name="enterprise.jjlxxlName"/>
	&nbsp;&nbsp;占地面积:&nbsp;&nbsp;<input type="text" name="enterprise.zdmj"/>
	&nbsp;&nbsp;是否是集团:&nbsp;&nbsp;<input type="text" name="enterprise.sfsjt"/>
	&nbsp;&nbsp;工商登记机关:&nbsp;&nbsp;<input type="text" name="enterprise.gszdjg"/>
	&nbsp;&nbsp;专项治理类型:&nbsp;&nbsp;<input type="text" name="enterprise.zxzllx"/>
	&nbsp;&nbsp;zxzllxname:&nbsp;&nbsp;<input type="text" name="enterprise.zxzllxname"/>
	&nbsp;&nbsp;xzLists:&nbsp;&nbsp;<input type="text" name="enterprise.xzLists"/>
	&nbsp;&nbsp;subManagerTypeLists:&nbsp;&nbsp;<input type="text" name="enterprise.subManagerTypeLists"/>
	&nbsp;&nbsp;qygmLists:&nbsp;&nbsp;<input type="text" name="enterprise.qygmLists"/>
	&nbsp;&nbsp;gmqkLists:&nbsp;&nbsp;<input type="text" name="enterprise.gmqkLists"/>
	&nbsp;&nbsp;yhcount:&nbsp;&nbsp;<input type="text" name="enterprise.yhcount"/>
	&nbsp;&nbsp;xzqhqxDept:&nbsp;&nbsp;<input type="text" name="enterprise.xzqhqxDept"/>
	&nbsp;&nbsp;联系人:&nbsp;&nbsp;<input type="text" name="enterprise.lxr"/>
	&nbsp;&nbsp;联系微信号:&nbsp;&nbsp;<input type="text" name="enterprise.lxwxh"/>
	&nbsp;&nbsp;联系QQ号:&nbsp;&nbsp;<input type="text" name="enterprise.lxqqh"/>
	&nbsp;&nbsp;企业官方网站地址:&nbsp;&nbsp;<input type="text" name="enterprise.qygfwzdz"/>
	&nbsp;&nbsp;单位传真:&nbsp;&nbsp;<input type="text" name="enterprise.dwcz"/>
	&nbsp;&nbsp;企业经营状态:&nbsp;&nbsp;<input type="text" name="enterprise.qyjyzt"/>
	&nbsp;&nbsp;年销售收入(万元):&nbsp;&nbsp;<input type="text" name="enterprise.nxssr"/>
	&nbsp;&nbsp;年利润(万元):&nbsp;&nbsp;<input type="text" name="enterprise.nlr"/>
	&nbsp;&nbsp;资产总额（万元）:&nbsp;&nbsp;<input type="text" name="enterprise.zcze"/>
	&nbsp;&nbsp;兼职安全生产管理人员数量:&nbsp;&nbsp;<input type="text" name="enterprise.jzaqscglrysl"/>
	&nbsp;&nbsp;营业执照类别:&nbsp;&nbsp;<input type="text" name="enterprise.yyzzlb"/>
	&nbsp;&nbsp;母公司名称:&nbsp;&nbsp;<input type="text" name="enterprise.sjgsmc"/>
	&nbsp;&nbsp;集团公司名称:&nbsp;&nbsp;<input type="text" name="enterprise.jtgsmc"/>
	&nbsp;&nbsp;标准化等级:&nbsp;&nbsp;<input type="text" name="enterprise.bzhdj"/>
	&nbsp;&nbsp;bzhdjName:&nbsp;&nbsp;<input type="text" name="enterprise.bzhdjName"/>
	&nbsp;&nbsp;安全监管等级:&nbsp;&nbsp;<input type="text" name="enterprise.aqjgdj"/>
	&nbsp;&nbsp;属地安监机构:&nbsp;&nbsp;<input type="text" name="enterprise.sdajjg"/>
	&nbsp;&nbsp;是否有专门安全机构:&nbsp;&nbsp;<input type="text" name="enterprise.sfyzzaqjg"/>
	&nbsp;&nbsp;是否有专职安全人员:&nbsp;&nbsp;<input type="text" name="enterprise.sfyzzaqry"/>
	&nbsp;&nbsp;是否存在重大危险源:&nbsp;&nbsp;<input type="text" name="enterprise.sfczzdwxy"/>
	&nbsp;&nbsp;隐患排查治理制度:&nbsp;&nbsp;<input type="text" name="enterprise.yhpczlzd"/>
	&nbsp;&nbsp;隐患排查治理计划:&nbsp;&nbsp;<input type="text" name="enterprise.yhpczljh"/>
	&nbsp;&nbsp;备注:&nbsp;&nbsp;<input type="text" name="enterprise.bz"/>
	&nbsp;&nbsp;行政区划社区:&nbsp;&nbsp;<input type="text" name="enterprise.xzqhsq"/>
	&nbsp;&nbsp;xzqhsqName:&nbsp;&nbsp;<input type="text" name="enterprise.xzqhsqName"/>
	&nbsp;&nbsp;专项治理类别:&nbsp;&nbsp;<input type="text" name="enterprise.zxzllb"/>
	&nbsp;&nbsp;分类监管:&nbsp;&nbsp;<input type="text" name="enterprise.fljg"/>
	&nbsp;&nbsp;fljgName:&nbsp;&nbsp;<input type="text" name="enterprise.fljgName"/>
	&nbsp;&nbsp;法定代表人固话:&nbsp;&nbsp;<input type="text" name="enterprise.fddbrgddh"/>
	&nbsp;&nbsp;法定代表人手机:&nbsp;&nbsp;<input type="text" name="enterprise.fddbrsj"/>
	&nbsp;&nbsp;注册安全工程师人员数量:&nbsp;&nbsp;<input type="text" name="enterprise.zcaqgcsrysl"/>
	&nbsp;&nbsp;监管行业大类:&nbsp;&nbsp;<input type="text" name="enterprise.jghydl"/>
	&nbsp;&nbsp;监管行业小类:&nbsp;&nbsp;<input type="text" name="enterprise.jghyxl"/>
	&nbsp;&nbsp;jghydlName:&nbsp;&nbsp;<input type="text" name="enterprise.jghydlName"/>
	&nbsp;&nbsp;jghyxlName:&nbsp;&nbsp;<input type="text" name="enterprise.jghyxlName"/>
	&nbsp;&nbsp;qyzpdj:&nbsp;&nbsp;<input type="text" name="enterprise.qyzpdj"/>
	
	&nbsp;&nbsp;
	</form>
<div>
	 <shiro:hasPermission name="${menuId}:add">
		 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="crud.add();">添加</a>
	 </shiro:hasPermission>
	 <shiro:hasPermission name="${menuId}:delete">
		 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="crud.remove();">删除</a>
	 </shiro:hasPermission>
	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search', plain:true" onclick="crud.search();">查询</a>
	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel', plain:true" onclick="cleanFunction();">清空</a>
	</div>
</div>

<div style="height: auto;">
    <table id="data_list" title = "企业基本信息" loadMsg="正在努力拉取数据中..." toolbar="#toolbar" fitColumns="true">
        <thead>
        <tr>
			
            <th align="center" field="qymc" width="150" formatter="gridFormatterLength" >企业名称</th>
            <th align="center" field="gszch" width="150" formatter="gridFormatterLength" >工商注册号</th>
            <th align="center" field="zzjgdm" width="150" formatter="gridFormatterLength" >组织机构代码</th>
            <th align="center" field="clrq" width="150" formatter="gridFormatterLength" >成立日期</th>
            <th align="center" field="fddbr" width="150" formatter="gridFormatterLength" >法定代表人</th>
            <th align="center" field="lxdh" width="150" formatter="gridFormatterLength" >联系电话</th>
            <th align="center" field="czhm" width="150" formatter="gridFormatterLength" >现用作审核意见</th>
            <th align="center" field="dzyx" width="150" formatter="gridFormatterLength" >电子邮箱</th>
            <th align="center" field="zcdz" width="150" formatter="gridFormatterLength" >注册地址</th>
            <th align="center" field="yzbm" width="150" formatter="gridFormatterLength" >邮政编码</th>
            <th align="center" field="xzqhs" width="150" formatter="gridFormatterLength" >行政区划省</th>
            <th align="center" field="xzqhsName" width="150" formatter="gridFormatterLength" >xzqhsName</th>
            <th align="center" field="xzqh" width="150" formatter="gridFormatterLength" >行政区划市</th>
            <th align="center" field="xzqhName" width="150" formatter="gridFormatterLength" >xzqhName</th>
            <th align="center" field="xzqhqx" width="150" formatter="gridFormatterLength" >行政区划区县</th>
            <th align="center" field="xzqhqxName" width="150" formatter="gridFormatterLength" >xzqhqxName</th>
            <th align="center" field="xzqhjd" width="150" formatter="gridFormatterLength" >行政区划街道</th>
            <th align="center" field="xzqhjdName" width="150" formatter="gridFormatterLength" >xzqhjdName</th>
            <th align="center" field="jjlx" width="150" formatter="gridFormatterLength" >经济类型</th>
            <th align="center" field="jjlxName" width="150" formatter="gridFormatterLength" >jjlxName</th>
            <th align="center" field="hylb" width="150" formatter="gridFormatterLength" >行业类别</th>
            <th align="center" field="hylbName" width="150" formatter="gridFormatterLength" >hylbName</th>
            <th align="center" field="lsgx" width="150" formatter="gridFormatterLength" >隶属关系</th>
            <th align="center" field="lsgxName" width="150" formatter="gridFormatterLength" >lsgxName</th>
            <th align="center" field="jyfw" width="150" formatter="gridFormatterLength" >经营范围</th>
            <th align="center" field="zczj" width="150" formatter="gridFormatterLength" >注册资金（万）</th>
            <th align="center" field="qyzt" width="150" formatter="gridFormatterLength" >企业状态</th>
            <th align="center" field="qyztName" width="150" formatter="gridFormatterLength" >qyztName</th>
            <th align="center" field="qywzjd" width="150" formatter="gridFormatterLength" >企业位置经度</th>
            <th align="center" field="qywzwd" width="150" formatter="gridFormatterLength" >企业位置维度</th>
            <th align="center" field="qypmt" width="150" formatter="gridFormatterLength" >企业平面图</th>
            <th align="center" field="zyfzr" width="150" formatter="gridFormatterLength" >主要负责人</th>
            <th align="center" field="zyfzrgddh" width="150" formatter="gridFormatterLength" >主要负责人固定电话</th>
            <th align="center" field="zyfzryddh" width="150" formatter="gridFormatterLength" >主要负责人移动电话</th>
            <th align="center" field="zyfzrdzyx" width="150" formatter="gridFormatterLength" >主要负责人电子邮箱</th>
            <th align="center" field="aqfzr" width="150" formatter="gridFormatterLength" >安全负责人</th>
            <th align="center" field="aqfzrgddh" width="150" formatter="gridFormatterLength" >安全负责人固定电话</th>
            <th align="center" field="aqfzryddh" width="150" formatter="gridFormatterLength" >安全负责人移动电话</th>
            <th align="center" field="aqfzrdzyx" width="150" formatter="gridFormatterLength" >安全负责人电子邮箱</th>
            <th align="center" field="aqjgszqk" width="150" formatter="gridFormatterLength" >安全机构设置情况</th>
            <th align="center" field="aqjgszqkName" width="150" formatter="gridFormatterLength" >aqjgszqkName</th>
            <th align="center" field="cyrysl" width="150" formatter="gridFormatterLength" >从业人员数量</th>
            <th align="center" field="tzzyrysl" width="150" formatter="gridFormatterLength" >特种作业人员数量</th>
            <th align="center" field="zzaqscglrysl" width="150" formatter="gridFormatterLength" >专职安全生产管理人员数量</th>
            <th align="center" field="zzyjglrysl" width="150" formatter="gridFormatterLength" >专职应急管理人员数量</th>
            <th align="center" field="zcaqgcsrys" width="150" formatter="gridFormatterLength" >注册安全工程师人员数量</th>
            <th align="center" field="aqjgjcjg" width="150" formatter="gridFormatterLength" >安全监管监察机构</th>
            <th align="center" field="scjydz" width="150" formatter="gridFormatterLength" >生产经营地址</th>
            <th align="center" field="qygm" width="150" formatter="gridFormatterLength" >企业规模</th>
            <th align="center" field="qygmName" width="150" formatter="gridFormatterLength" >qygmName</th>
            <th align="center" field="gmqk" width="150" formatter="gridFormatterLength" >规模情况</th>
            <th align="center" field="gmqkName" width="150" formatter="gridFormatterLength" >gmqkName</th>
            <th align="center" field="jgfl" width="150" formatter="gridFormatterLength" >监管分类</th>
            <th align="center" field="jgflName" width="150" formatter="gridFormatterLength" >jgflName</th>
            <th align="center" field="aqscbzhdj" width="150" formatter="gridFormatterLength" >安全生产标准化等级</th>
            <th align="center" field="aqscbzhdjName" width="150" formatter="gridFormatterLength" >aqscbzhdjName</th>
            <th align="center" field="aqscbzhzsbh" width="150" formatter="gridFormatterLength" >安全生产标准化证书编号</th>
            <th align="center" field="aqscbzhdqsj" width="150" formatter="gridFormatterLength" >安全生产标准化到期时间</th>
            <th align="center" field="aqxkzbh" width="150" formatter="gridFormatterLength" >安全生产许可证编号</th>
            <th align="center" field="aqscxkzdqsj" width="150" formatter="gridFormatterLength" >安全生产许可证到期时间</th>
            <th align="center" field="tbr" width="150" formatter="gridFormatterLength" >填表人</th>
            <th align="center" field="tbrlxdh" width="150" formatter="gridFormatterLength" >填表人联系电话</th>
            <th align="center" field="tbrq" width="150" formatter="gridFormatterLength" >填表日期</th>
            <th align="center" field="dept" width="150" formatter="gridFormatterLength" >监管部门树</th>
            <th align="center" field="showDept" width="150" formatter="gridFormatterLength" >显示的部门</th>
            <th align="center" field="deptName" width="150" formatter="gridFormatterLength" >deptName</th>
            <th align="center" field="hylbShow" width="150" formatter="gridFormatterLength" >hylbShow</th>
            <th align="center" field="xsje" width="150" formatter="gridFormatterLength" >销售金额</th>
            <th align="center" field="sfylsjt" width="150" formatter="gridFormatterLength" >是否有隶属集团</th>
            <th align="center" field="lsjtmc" width="150" formatter="gridFormatterLength" >隶属集团名称</th>
            <th align="center" field="aqglr" width="150" formatter="gridFormatterLength" >安全管理人</th>
            <th align="center" field="aqglrydh" width="150" formatter="gridFormatterLength" >安全管理人员电话</th>
            <th align="center" field="sjgbm" width="150" formatter="gridFormatterLength" >市监管部门</th>
            <th align="center" field="aqscgljg" width="150" formatter="gridFormatterLength" >安全生产管理机构</th>
            <th align="center" field="aqscxkzfzrq" width="150" formatter="gridFormatterLength" >安全生产许可证发证日期</th>
            <th align="center" field="aqscczgc" width="150" formatter="gridFormatterLength" >安全生产操作规程 </th>
            <th align="center" field="aqsczzzd" width="150" formatter="gridFormatterLength" >安全生产规章制度</th>
            <th align="center" field="aqsczrzd" width="150" formatter="gridFormatterLength" >安全生产责任制度</th>
            <th align="center" field="jsxmsts" width="150" formatter="gridFormatterLength" >建设项目'三同时'</th>
            <th align="center" field="zxjghya" width="150" formatter="gridFormatterLength" >专项监管大类</th>
            <th align="center" field="zxjghyb" width="150" formatter="gridFormatterLength" >专项监管小类</th>
            <th align="center" field="zxjghybName" width="150" formatter="gridFormatterLength" >zxjghybName</th>
            <th align="center" field="zxjghyaName" width="150" formatter="gridFormatterLength" >zxjghyaName</th>
            <th align="center" field="lsjtmcName" width="150" formatter="gridFormatterLength" >lsjtmcName</th>
            <th align="center" field="clique" width="150" formatter="gridFormatterLength" >是否是集团</th>
            <th align="center" field="hylbxl" width="150" formatter="gridFormatterLength" >行业类别小类</th>
            <th align="center" field="jjlxxl" width="150" formatter="gridFormatterLength" >经济类型小类</th>
            <th align="center" field="hylbxlName" width="150" formatter="gridFormatterLength" >hylbxlName</th>
            <th align="center" field="jjlxxlName" width="150" formatter="gridFormatterLength" >jjlxxlName</th>
            <th align="center" field="zdmj" width="150" formatter="gridFormatterLength" >占地面积</th>
            <th align="center" field="sfsjt" width="150" formatter="gridFormatterLength" >是否是集团</th>
            <th align="center" field="gszdjg" width="150" formatter="gridFormatterLength" >工商登记机关</th>
            <th align="center" field="zxzllx" width="150" formatter="gridFormatterLength" >专项治理类型</th>
            <th align="center" field="zxzllxname" width="150" formatter="gridFormatterLength" >zxzllxname</th>
            <th align="center" field="xzLists" width="150" formatter="gridFormatterLength" >xzLists</th>
            <th align="center" field="subManagerTypeLists" width="150" formatter="gridFormatterLength" >subManagerTypeLists</th>
            <th align="center" field="qygmLists" width="150" formatter="gridFormatterLength" >qygmLists</th>
            <th align="center" field="gmqkLists" width="150" formatter="gridFormatterLength" >gmqkLists</th>
            <th align="center" field="yhcount" width="150" formatter="gridFormatterLength" >yhcount</th>
            <th align="center" field="xzqhqxDept" width="150" formatter="gridFormatterLength" >xzqhqxDept</th>
            <th align="center" field="lxr" width="150" formatter="gridFormatterLength" >联系人</th>
            <th align="center" field="lxwxh" width="150" formatter="gridFormatterLength" >联系微信号</th>
            <th align="center" field="lxqqh" width="150" formatter="gridFormatterLength" >联系QQ号</th>
            <th align="center" field="qygfwzdz" width="150" formatter="gridFormatterLength" >企业官方网站地址</th>
            <th align="center" field="dwcz" width="150" formatter="gridFormatterLength" >单位传真</th>
            <th align="center" field="qyjyzt" width="150" formatter="gridFormatterLength" >企业经营状态</th>
            <th align="center" field="nxssr" width="150" formatter="gridFormatterLength" >年销售收入(万元)</th>
            <th align="center" field="nlr" width="150" formatter="gridFormatterLength" >年利润(万元)</th>
            <th align="center" field="zcze" width="150" formatter="gridFormatterLength" >资产总额（万元）</th>
            <th align="center" field="jzaqscglrysl" width="150" formatter="gridFormatterLength" >兼职安全生产管理人员数量</th>
            <th align="center" field="yyzzlb" width="150" formatter="gridFormatterLength" >营业执照类别</th>
            <th align="center" field="sjgsmc" width="150" formatter="gridFormatterLength" >母公司名称</th>
            <th align="center" field="jtgsmc" width="150" formatter="gridFormatterLength" >集团公司名称</th>
            <th align="center" field="bzhdj" width="150" formatter="gridFormatterLength" >标准化等级</th>
            <th align="center" field="bzhdjName" width="150" formatter="gridFormatterLength" >bzhdjName</th>
            <th align="center" field="aqjgdj" width="150" formatter="gridFormatterLength" >安全监管等级</th>
            <th align="center" field="sdajjg" width="150" formatter="gridFormatterLength" >属地安监机构</th>
            <th align="center" field="sfyzzaqjg" width="150" formatter="gridFormatterLength" >是否有专门安全机构</th>
            <th align="center" field="sfyzzaqry" width="150" formatter="gridFormatterLength" >是否有专职安全人员</th>
            <th align="center" field="sfczzdwxy" width="150" formatter="gridFormatterLength" >是否存在重大危险源</th>
            <th align="center" field="yhpczlzd" width="150" formatter="gridFormatterLength" >隐患排查治理制度</th>
            <th align="center" field="yhpczljh" width="150" formatter="gridFormatterLength" >隐患排查治理计划</th>
            <th align="center" field="bz" width="150" formatter="gridFormatterLength" >备注</th>
            <th align="center" field="xzqhsq" width="150" formatter="gridFormatterLength" >行政区划社区</th>
            <th align="center" field="xzqhsqName" width="150" formatter="gridFormatterLength" >xzqhsqName</th>
            <th align="center" field="zxzllb" width="150" formatter="gridFormatterLength" >专项治理类别</th>
            <th align="center" field="fljg" width="150" formatter="gridFormatterLength" >分类监管</th>
            <th align="center" field="fljgName" width="150" formatter="gridFormatterLength" >fljgName</th>
            <th align="center" field="fddbrgddh" width="150" formatter="gridFormatterLength" >法定代表人固话</th>
            <th align="center" field="fddbrsj" width="150" formatter="gridFormatterLength" >法定代表人手机</th>
            <th align="center" field="zcaqgcsrysl" width="150" formatter="gridFormatterLength" >注册安全工程师人员数量</th>
            <th align="center" field="jghydl" width="150" formatter="gridFormatterLength" >监管行业大类</th>
            <th align="center" field="jghyxl" width="150" formatter="gridFormatterLength" >监管行业小类</th>
            <th align="center" field="jghydlName" width="150" formatter="gridFormatterLength" >jghydlName</th>
            <th align="center" field="jghyxlName" width="150" formatter="gridFormatterLength" >jghyxlName</th>
            <th align="center" field="qyzpdj" width="150" formatter="gridFormatterLength" >qyzpdj</th>
            <th align="center" field="id" width="120" formatter="gridFormatter">操作</th>         
        </tr>
        </thead>
    </table>
</div>

	<!-- 添加窗口 -->
	<div style="display: none;">
		 <div id="curdaddForm" style="width: 70%; height: 200px;" data-options="iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false">
		<form id="addForm" method="post">
			<input type="hidden" name="enterprise.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="addDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">企业名称:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qymc"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">工商注册号:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.gszch"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">组织机构代码:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zzjgdm"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">成立日期:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.clrq"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">法定代表人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.fddbr"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">联系电话:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.lxdh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">现用作审核意见:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.czhm"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">电子邮箱:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.dzyx"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">注册地址:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zcdz"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">邮政编码:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.yzbm"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">行政区划省:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhs"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">xzqhsName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhsName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">行政区划市:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">xzqhName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">行政区划区县:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhqx"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">xzqhqxName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhqxName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">行政区划街道:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhjd"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">xzqhjdName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhjdName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">经济类型:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jjlx"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">jjlxName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jjlxName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">行业类别:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.hylb"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">hylbName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.hylbName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">隶属关系:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.lsgx"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">lsgxName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.lsgxName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">经营范围:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jyfw"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">注册资金（万）:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zczj"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">企业状态:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qyzt"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">qyztName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qyztName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">企业位置经度:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qywzjd"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">企业位置维度:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qywzwd"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">企业平面图:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qypmt"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">主要负责人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zyfzr"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">主要负责人固定电话:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zyfzrgddh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">主要负责人移动电话:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zyfzryddh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">主要负责人电子邮箱:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zyfzrdzyx"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全负责人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqfzr"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全负责人固定电话:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqfzrgddh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全负责人移动电话:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqfzryddh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全负责人电子邮箱:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqfzrdzyx"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全机构设置情况:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqjgszqk"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">aqjgszqkName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqjgszqkName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">从业人员数量:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.cyrysl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">特种作业人员数量:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.tzzyrysl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">专职安全生产管理人员数量:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zzaqscglrysl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">专职应急管理人员数量:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zzyjglrysl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">注册安全工程师人员数量:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zcaqgcsrys"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全监管监察机构:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqjgjcjg"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">生产经营地址:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.scjydz"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">企业规模:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qygm"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">qygmName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qygmName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">规模情况:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.gmqk"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">gmqkName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.gmqkName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">监管分类:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jgfl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">jgflName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jgflName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全生产标准化等级:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqscbzhdj"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">aqscbzhdjName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqscbzhdjName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全生产标准化证书编号:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqscbzhzsbh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全生产标准化到期时间:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqscbzhdqsj"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全生产许可证编号:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqxkzbh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全生产许可证到期时间:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqscxkzdqsj"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">填表人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.tbr"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">填表人联系电话:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.tbrlxdh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">填表日期:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.tbrq"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">监管部门树:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.dept"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">显示的部门:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.showDept"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">deptName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.deptName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">hylbShow:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.hylbShow"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">销售金额:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xsje"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">是否有隶属集团:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.sfylsjt"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">隶属集团名称:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.lsjtmc"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全管理人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqglr"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全管理人员电话:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqglrydh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">市监管部门:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.sjgbm"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全生产管理机构:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqscgljg"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全生产许可证发证日期:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqscxkzfzrq"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全生产操作规程 :</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqscczgc"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全生产规章制度:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqsczzzd"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全生产责任制度:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqsczrzd"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">建设项目'三同时':</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jsxmsts"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">专项监管大类:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zxjghya"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">专项监管小类:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zxjghyb"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">zxjghybName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zxjghybName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">zxjghyaName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zxjghyaName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">lsjtmcName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.lsjtmcName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">是否是集团:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.clique"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">行业类别小类:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.hylbxl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">经济类型小类:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jjlxxl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">hylbxlName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.hylbxlName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">jjlxxlName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jjlxxlName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">占地面积:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zdmj"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">是否是集团:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.sfsjt"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">工商登记机关:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.gszdjg"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">专项治理类型:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zxzllx"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">zxzllxname:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zxzllxname"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">xzLists:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzLists"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">subManagerTypeLists:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.subManagerTypeLists"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">qygmLists:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qygmLists"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">gmqkLists:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.gmqkLists"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">yhcount:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.yhcount"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">xzqhqxDept:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhqxDept"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">联系人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.lxr"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">联系微信号:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.lxwxh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">联系QQ号:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.lxqqh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">企业官方网站地址:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qygfwzdz"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">单位传真:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.dwcz"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">企业经营状态:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qyjyzt"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">年销售收入(万元):</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.nxssr"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">年利润(万元):</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.nlr"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">资产总额（万元）:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zcze"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">兼职安全生产管理人员数量:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jzaqscglrysl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">营业执照类别:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.yyzzlb"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">母公司名称:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.sjgsmc"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">集团公司名称:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jtgsmc"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">标准化等级:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.bzhdj"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">bzhdjName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.bzhdjName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全监管等级:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqjgdj"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">属地安监机构:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.sdajjg"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">是否有专门安全机构:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.sfyzzaqjg"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">是否有专职安全人员:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.sfyzzaqry"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">是否存在重大危险源:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.sfczzdwxy"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">隐患排查治理制度:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.yhpczlzd"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">隐患排查治理计划:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.yhpczljh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">备注:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.bz"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">行政区划社区:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhsq"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">xzqhsqName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhsqName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">专项治理类别:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zxzllb"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">分类监管:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.fljg"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">fljgName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.fljgName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">法定代表人固话:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.fddbrgddh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">法定代表人手机:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.fddbrsj"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">注册安全工程师人员数量:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zcaqgcsrysl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">监管行业大类:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jghydl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">监管行业小类:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jghyxl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">jghydlName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jghydlName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">jghyxlName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jghyxlName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">qyzpdj:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qyzpdj"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
				</table>
			</div>
		</form>
	</div>
	
	</div>
</div>
	
	<!-- 更新窗口 -->
	<div style="display: none;">
		 <div id="curdupdateForm" style="width: 70%; height: 200px;" data-options="iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false">
		<form id="updateForm" method="post">
			<input type="hidden" name="enterprise.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="updateDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">企业名称:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qymc"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">工商注册号:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.gszch"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">组织机构代码:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zzjgdm"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">成立日期:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.clrq"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">法定代表人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.fddbr"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">联系电话:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.lxdh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">现用作审核意见:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.czhm"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">电子邮箱:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.dzyx"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">注册地址:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zcdz"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">邮政编码:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.yzbm"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">行政区划省:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhs"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">xzqhsName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhsName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">行政区划市:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">xzqhName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">行政区划区县:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhqx"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">xzqhqxName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhqxName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">行政区划街道:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhjd"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">xzqhjdName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhjdName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">经济类型:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jjlx"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">jjlxName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jjlxName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">行业类别:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.hylb"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">hylbName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.hylbName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">隶属关系:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.lsgx"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">lsgxName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.lsgxName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">经营范围:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jyfw"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">注册资金（万）:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zczj"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">企业状态:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qyzt"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">qyztName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qyztName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">企业位置经度:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qywzjd"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">企业位置维度:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qywzwd"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">企业平面图:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qypmt"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">主要负责人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zyfzr"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">主要负责人固定电话:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zyfzrgddh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">主要负责人移动电话:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zyfzryddh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">主要负责人电子邮箱:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zyfzrdzyx"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全负责人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqfzr"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全负责人固定电话:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqfzrgddh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全负责人移动电话:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqfzryddh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全负责人电子邮箱:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqfzrdzyx"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全机构设置情况:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqjgszqk"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">aqjgszqkName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqjgszqkName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">从业人员数量:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.cyrysl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">特种作业人员数量:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.tzzyrysl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">专职安全生产管理人员数量:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zzaqscglrysl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">专职应急管理人员数量:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zzyjglrysl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">注册安全工程师人员数量:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zcaqgcsrys"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全监管监察机构:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqjgjcjg"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">生产经营地址:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.scjydz"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">企业规模:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qygm"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">qygmName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qygmName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">规模情况:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.gmqk"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">gmqkName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.gmqkName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">监管分类:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jgfl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">jgflName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jgflName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全生产标准化等级:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqscbzhdj"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">aqscbzhdjName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqscbzhdjName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全生产标准化证书编号:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqscbzhzsbh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全生产标准化到期时间:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqscbzhdqsj"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全生产许可证编号:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqxkzbh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全生产许可证到期时间:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqscxkzdqsj"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">填表人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.tbr"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">填表人联系电话:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.tbrlxdh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">填表日期:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.tbrq"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">监管部门树:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.dept"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">显示的部门:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.showDept"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">deptName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.deptName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">hylbShow:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.hylbShow"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">销售金额:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xsje"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">是否有隶属集团:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.sfylsjt"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">隶属集团名称:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.lsjtmc"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全管理人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqglr"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全管理人员电话:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqglrydh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">市监管部门:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.sjgbm"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全生产管理机构:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqscgljg"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全生产许可证发证日期:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqscxkzfzrq"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全生产操作规程 :</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqscczgc"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全生产规章制度:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqsczzzd"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全生产责任制度:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqsczrzd"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">建设项目'三同时':</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jsxmsts"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">专项监管大类:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zxjghya"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">专项监管小类:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zxjghyb"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">zxjghybName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zxjghybName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">zxjghyaName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zxjghyaName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">lsjtmcName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.lsjtmcName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">是否是集团:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.clique"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">行业类别小类:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.hylbxl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">经济类型小类:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jjlxxl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">hylbxlName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.hylbxlName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">jjlxxlName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jjlxxlName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">占地面积:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zdmj"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">是否是集团:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.sfsjt"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">工商登记机关:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.gszdjg"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">专项治理类型:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zxzllx"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">zxzllxname:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zxzllxname"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">xzLists:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzLists"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">subManagerTypeLists:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.subManagerTypeLists"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">qygmLists:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qygmLists"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">gmqkLists:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.gmqkLists"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">yhcount:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.yhcount"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">xzqhqxDept:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhqxDept"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">联系人:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.lxr"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">联系微信号:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.lxwxh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">联系QQ号:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.lxqqh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">企业官方网站地址:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qygfwzdz"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">单位传真:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.dwcz"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">企业经营状态:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qyjyzt"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">年销售收入(万元):</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.nxssr"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">年利润(万元):</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.nlr"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">资产总额（万元）:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zcze"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">兼职安全生产管理人员数量:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jzaqscglrysl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">营业执照类别:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.yyzzlb"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">母公司名称:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.sjgsmc"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">集团公司名称:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jtgsmc"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">标准化等级:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.bzhdj"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">bzhdjName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.bzhdjName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">安全监管等级:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.aqjgdj"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">属地安监机构:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.sdajjg"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">是否有专门安全机构:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.sfyzzaqjg"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">是否有专职安全人员:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.sfyzzaqry"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">是否存在重大危险源:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.sfczzdwxy"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">隐患排查治理制度:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.yhpczlzd"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">隐患排查治理计划:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.yhpczljh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">备注:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.bz"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">行政区划社区:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhsq"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">xzqhsqName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.xzqhsqName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">专项治理类别:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zxzllb"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">分类监管:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.fljg"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">fljgName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.fljgName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">法定代表人固话:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.fddbrgddh"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">法定代表人手机:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.fddbrsj"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">注册安全工程师人员数量:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.zcaqgcsrysl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">监管行业大类:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jghydl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">监管行业小类:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jghyxl"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">jghydlName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jghydlName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">jghyxlName:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.jghyxlName"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						
							<td class="table_nemu_new" width="20%">qyzpdj:</td>
							<td class="table_con" width="30%">
							<input type="text" class="easyui-validatebox" name="enterprise.qyzpdj"
							data-options="validType:'maxByteLength[100]'" />
							</td>
						</tr>
						
				</table>
			</div>
		</form>
	</div>
	
	</div>
	
	<!-- 查看窗口 -->
	<div style="display: none;">
		 <div id="curdviewForm" style="width: 70%; height: 200px;" data-options="iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false">
		<form id="viewForm" method="post">
			<input type="hidden" name="enterprise.id"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="viewDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
					
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">企业名称:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.qymc"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">工商注册号:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.gszch"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">组织机构代码:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zzjgdm"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">成立日期:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.clrq"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">法定代表人:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.fddbr"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">联系电话:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.lxdh"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">现用作审核意见:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.czhm"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">电子邮箱:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.dzyx"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">注册地址:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zcdz"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">邮政编码:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.yzbm"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">行政区划省:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.xzqhs"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">xzqhsName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.xzqhsName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">行政区划市:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.xzqh"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">xzqhName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.xzqhName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">行政区划区县:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.xzqhqx"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">xzqhqxName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.xzqhqxName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">行政区划街道:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.xzqhjd"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">xzqhjdName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.xzqhjdName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">经济类型:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.jjlx"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">jjlxName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.jjlxName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">行业类别:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.hylb"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">hylbName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.hylbName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">隶属关系:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.lsgx"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">lsgxName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.lsgxName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">经营范围:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.jyfw"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">注册资金（万）:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zczj"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">企业状态:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.qyzt"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">qyztName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.qyztName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">企业位置经度:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.qywzjd"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">企业位置维度:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.qywzwd"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">企业平面图:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.qypmt"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">主要负责人:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zyfzr"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">主要负责人固定电话:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zyfzrgddh"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">主要负责人移动电话:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zyfzryddh"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">主要负责人电子邮箱:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zyfzrdzyx"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">安全负责人:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqfzr"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全负责人固定电话:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqfzrgddh"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">安全负责人移动电话:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqfzryddh"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全负责人电子邮箱:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqfzrdzyx"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">安全机构设置情况:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqjgszqk"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">aqjgszqkName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqjgszqkName"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">从业人员数量:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.cyrysl"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">特种作业人员数量:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.tzzyrysl"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">专职安全生产管理人员数量:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zzaqscglrysl"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">专职应急管理人员数量:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zzyjglrysl"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">注册安全工程师人员数量:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zcaqgcsrys"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全监管监察机构:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqjgjcjg"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">生产经营地址:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.scjydz"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">企业规模:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.qygm"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">qygmName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.qygmName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">规模情况:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.gmqk"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">gmqkName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.gmqkName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">监管分类:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.jgfl"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">jgflName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.jgflName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全生产标准化等级:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqscbzhdj"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">aqscbzhdjName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqscbzhdjName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全生产标准化证书编号:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqscbzhzsbh"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">安全生产标准化到期时间:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqscbzhdqsj"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全生产许可证编号:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqxkzbh"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">安全生产许可证到期时间:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqscxkzdqsj"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">填表人:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.tbr"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">填表人联系电话:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.tbrlxdh"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">填表日期:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.tbrq"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">监管部门树:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.dept"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">显示的部门:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.showDept"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">deptName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.deptName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">hylbShow:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.hylbShow"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">销售金额:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.xsje"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">是否有隶属集团:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.sfylsjt"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">隶属集团名称:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.lsjtmc"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全管理人:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqglr"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">安全管理人员电话:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqglrydh"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">市监管部门:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.sjgbm"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">安全生产管理机构:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqscgljg"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全生产许可证发证日期:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqscxkzfzrq"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">安全生产操作规程 :</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqscczgc"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">安全生产规章制度:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqsczzzd"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">安全生产责任制度:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqsczrzd"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">建设项目'三同时':</td>
							<td class="table_con" width="30%">
							<span name="enterprise.jsxmsts"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">专项监管大类:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zxjghya"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">专项监管小类:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zxjghyb"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">zxjghybName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zxjghybName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">zxjghyaName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zxjghyaName"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">lsjtmcName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.lsjtmcName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">是否是集团:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.clique"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">行业类别小类:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.hylbxl"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">经济类型小类:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.jjlxxl"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">hylbxlName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.hylbxlName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">jjlxxlName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.jjlxxlName"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">占地面积:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zdmj"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">是否是集团:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.sfsjt"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">工商登记机关:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.gszdjg"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">专项治理类型:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zxzllx"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">zxzllxname:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zxzllxname"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">xzLists:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.xzLists"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">subManagerTypeLists:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.subManagerTypeLists"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">qygmLists:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.qygmLists"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">gmqkLists:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.gmqkLists"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">yhcount:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.yhcount"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">xzqhqxDept:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.xzqhqxDept"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">联系人:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.lxr"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">联系微信号:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.lxwxh"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">联系QQ号:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.lxqqh"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">企业官方网站地址:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.qygfwzdz"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">单位传真:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.dwcz"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">企业经营状态:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.qyjyzt"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">年销售收入(万元):</td>
							<td class="table_con" width="30%">
							<span name="enterprise.nxssr"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">年利润(万元):</td>
							<td class="table_con" width="30%">
							<span name="enterprise.nlr"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">资产总额（万元）:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zcze"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">兼职安全生产管理人员数量:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.jzaqscglrysl"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">营业执照类别:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.yyzzlb"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">母公司名称:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.sjgsmc"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">集团公司名称:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.jtgsmc"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">标准化等级:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.bzhdj"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">bzhdjName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.bzhdjName"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">安全监管等级:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.aqjgdj"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">属地安监机构:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.sdajjg"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">是否有专门安全机构:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.sfyzzaqjg"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">是否有专职安全人员:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.sfyzzaqry"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">是否存在重大危险源:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.sfczzdwxy"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">隐患排查治理制度:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.yhpczlzd"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">隐患排查治理计划:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.yhpczljh"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">备注:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.bz"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">行政区划社区:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.xzqhsq"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">xzqhsqName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.xzqhsqName"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">专项治理类别:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zxzllb"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">分类监管:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.fljg"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">fljgName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.fljgName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">法定代表人固话:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.fddbrgddh"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">法定代表人手机:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.fddbrsj"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">注册安全工程师人员数量:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.zcaqgcsrysl"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">监管行业大类:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.jghydl"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">监管行业小类:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.jghyxl"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">jghydlName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.jghydlName"></span>
							</td>
						</tr>
						
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">jghyxlName:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.jghyxlName"></span>
							</td>
						
							<td class="table_nemu_new" width="20%">qyzpdj:</td>
							<td class="table_con" width="30%">
							<span name="enterprise.qyzpdj"></span>
							</td>
						</tr>
						
				</table>
			</div>
		</form>
	</div>
	
</div>
	
</body>
</html>