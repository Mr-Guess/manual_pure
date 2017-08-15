<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企业信息统计</title>

<link rel="stylesheet" type="text/css" href="${ctx}/css/newCrud.css">
<script type="text/javascript" src="${ctx}/js/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/CrudUtil.js"></script>
<script type="text/javascript"
	src="${ctx}/jsp/statistics/js/FusionCharts.js"></script>
<style type="text/css">
a {
	text-decoration: none;
}
tr{
	cursor: pointer;
}
</style>
<script type="text/javascript">
	var type='jjlx';	//第一次加载时按照经济类型加载
	var code;			//搜索时按照Code
	var hchars=[];		//数据
	var staticType="Column3D.swf"; //统计图类型 默认为柱状图
	var dataCount=0;	//统计数量
	var preType="column";//默认饼状图
	//加载表单数据
	function loadDataList(data){
		if(type=='jjlx'){
			$(".typeName").text("经济类型");
		}else if(type=='hylb'){
			$(".typeName").text("企业类型");
		}else{
			$(".typeName").text("所在地区");
		}
		$("#data").find("tbody").remove();
		var tbody=$("<tbody>");
		$("#data").append(tbody);
		$(data).each(function(i){
			var tr;
			if(i%2==0){
				tr=$("<tr>").attr("style","background-color: #FAFAFA;");
			}else{
				tr=$("<tr>");
			}
			
			var name=$("<td>").attr("style","border: 1px solid #B2DCF2;").text(this.name);
			$(name).append('<input value="'+this.typeCode+'" type="hidden"/>');
			var qyCount=$("<td>").attr("style","border: 1px solid #B2DCF2;").text(this.qyCount);
			var percent=$("<td>").attr("style","border: 1px solid #B2DCF2;").text(this.percent+"%");
			$(tr).append(name).append(qyCount).append(percent);
			$(tbody).append(tr);
			$(tr).click(function(){
				var entType=$(this).find('td:eq(0)').find('input[type="hidden"]').val();
				loadEnt(entType);
			});
			$(tr).mousemove(function(){
				$(this).attr('style','background-color:#E3F1FA');
			});
			
			$(tr).mouseout(function(){
				var index=$(this).index();
				var currtr=$(this);
				setTimeout(function(){
					if(index%2==0){
						$(currtr).attr('style','background-color:#FAFAFA');
					}else{
						$(currtr).attr('style','');
					}
				},200);
			});
		});
	}
	//格式化字符串
	function fromatDate(data){
		//rotateValues 此属性表示是否旋转图形上显示的柱子的值 0 1
		var rotateValue=0;
		var palette=5;	//图标默认样式
		dataCount=data.length;
		if(dataCount>15&&preType=="column"){
			rotateValue=1;//当统计数量超过15 旋转图形上显示的值
			staticType="Bar2D.swf";
			palette=3;
		}else if(preType=="column"){
			staticType="Column3D.swf";
			palette=5;
		}else{
			staticType="Pie3D.swf";
			palette=5;
		}
		
		var chartDiv='<chart baseFontSize="12" bgColor="ffffff" showBorder="0" borderThickness="0" numberSuffix="%" decimals="2" slantLabels="1" outCnvBaseFontColor="000000"  labelDisplay="ROTATE" slantLabels="0" rotateValues="'+rotateValue+'" palette="'+palette+'" caption="'+$(".typeName").text()+'统计(百分比)" formatNumberScale="0"   sNumberSuffix=" pcs." showValues="1" labelDisplay="STAGGER" encoding="UTF-8"';
		chartDiv+=' useRoundEdges="1" exportShowMenuItem="1" exportAtCilent="1" exportHandler="remind.jsp" exportEnabled="1" exportTargetWindow="_blank" showExportDialog="1">';
		$(data).each(function(i){
			var toolText=this.name+","+this.qyCount;
			chartDiv+='<set  alpha="80" label="'+this.name+'" toolText="'+toolText+'"  link="javascript:show(&quot;'+this.typeCode+'&quot;)" value="'+this.percent+'" />';
		});
		chartDiv+='<styles>'+
		'<definition><style type="font" name="CaptionFont" size="16" color="000000" />'+
		'<style  type="font" name="SubCaptionFont" bold="1" />'+
		'</definition><application>'+
		'<apply toObject="caption" styles="CaptionFont" />'+
		'<apply toObject="SubCaption" styles="SubCaptionFont" />'+
		'</application>'+
		'</styles>'+
		'</chart>';
		return chartDiv;
	}
	//类型
	function show(jjlx){
		loadEnt(jjlx);
		return;
	}
	
	//加载柱状图FusionCharts
	function loadPrie(objId,chartDiv){
		var height=500;
		if(dataCount>15&&preType=="column"){
			height=dataCount*37;
		}
		var chart = new FusionCharts("${ctx}/jsp/statistics/swf/"+staticType, "myChartId", "100%", height, "0", "0");
	    chart.setXMLData(chartDiv);
	    chart.render(objId);
	}
	
	function autoPrie(objId,chartDiv,priType){
		var chart = new FusionCharts("${ctx}/jsp/statistics/swf/"+priType, "myChartId", "770", "500", "0", "0");
	    chart.setXMLData(chartDiv);
	    chart.render(objId);
	}
	var status=1;
	$(function(){
		 loadTypeData();	//加载fchars
		 $("#qygm").combobox({
			   url:'${ctx}/data/dataAction!findByType?parentId=-1&typeId=qygm',
			   valueField:'dataCode',
			   textField:'dataName'
		  });
		 $('#entList').dialog({
				title:'企业详细信息',
				modal:true,
				draggable:false,
				maximized:true,
				maximizable: true,
				minimizable: true,
				width:900,
				height:450,
				buttons:[{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						$('#entList').dialog('close');
					}
				}]
			});
		$('#dd').dialog({
				title:'企业详细信息',
				modal:true,
				draggable:false,
				maximized:true,
				maximizable: true, 
				minimizable: true,
				width:900,
				height:450,
				buttons:[{
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						$('#dd').dialog('close');
					}
				}]
			});
		 $("#entList").dialog("close");
		 $("#dd").dialog("close");
	});
	//按照类型统计
	function loadTypeData(){
		$.loding();
		var chartData="";
		$.ajax({
			url:'${ctx}/enterprise/enterpriseAction!calcPercen?type='+type,
			success:function(data){
				var data=eval('('+data+')');
				loadDataList(data);//加载表格
				chartData=fromatDate(data);//格式化数据
				/*formatHeighDate(data);	//格式化hi数据
				//loadHighCharts();//加载图形*/
				loadPrie("container",chartData);//加载图形
				$.loded();
			}
		});
		$("#list").hide();
	}
	function loadEnt(typeCode){
		//按照分类查询
		 $("#entList").dialog("open");
		code=typeCode;
		var grid = null;
		grid = new Grid('${ctx}/enterprise/enterpriseAction!list?enterprise.'+type+'='+typeCode,'data_list');
		grid.loadGrid();
		crud = new Crud({
			 grid:grid,
			 searchFormObject:$('#search'),
			 entityName:'enterprise',
			 url : '${ctx}/enterprise/enterpriseAction'
		});
		
	}
	function gridFormatterLength(value, rowData, rowIndex) {
		if (value == null || value == '' || value == 'undefined')
			return '';
		if (value.length > 25)
			return value.substring(0, 25) + '...';
		return value;
	}
	function gridFormatterLink(value,rowData,rowIndex){
		var url='<a href="#" onclick="showInfo('+rowData.zzjgdm+');">'+rowData.qymc+'</a>';
		return url;
	}
	
	function showInfo(account){
		var entInfo=$("#entInfo").length;
		if(entInfo==0){
			$('#ifrma').append('<iframe id="entInfo" width="100%" frameborder="0" height="100%" src="${ctx}/jsp/gov/baseInfo/enterpriseView.jsp?account='+account+'"></iframe>');
		}else{
			$("#entInfo").attr("src","${ctx}/jsp/gov/baseInfo/enterpriseView.jsp?account="+account);
		}
		$("#dd").dialog("open");
	}
	
	function check(){
		var val=$('#searchForm input[type=radio]:checked').val();
		type=val;
		loadTypeData();
	}
	//清空查询
	function clearSearch(){
		$('#search').find("input").each(function(){
			$(this).val('');
		});
	}
	//柱状图和饼状图切换
	function pieClonm(o){
		if(o=='1'){
			staticType="Column3D.swf";
			preType="column";
		}else{
			staticType="Pie3D.swf";
			preType="pie";
		}
		loadTypeData();
	}
	//切换统计图
	function viewGen(){
		if($("#highchar").is(":visible")==false){
			$("#highchar").show();
			$("#container").hide();
			loadHighCharts();
		}else{
			$("#highchar").hide();
			$("#container").show();
			loadTypeData();
		}
	}
</script>
<style type="text/css">
th {
	background-color: #E3F1FA;
	font-size: 14px;
	font-weight: bold;
	height: 25px;
}
#data{
	border-color:#BFC1C2;
	box-shadow:0 0 10px #BFC1C2;
	border-radius:15px;
}
table,tr,td,th {
	border: 1px solid #BFC1C2;
	border-collapse: collapse;
	text-align: center;
	font-size: 12px;
	height: 30px;
}
.column,.pie{
	font-size: 12px;
	font-weight: bold;
	border: 1px solid #BFC1C2;
	cursor: pointer;
	color: #2779AA;
	height: 25px;
	width: 70px;
	text-align:right;
	margin-bottom: 5px;
	background-repeat: no-repeat;
	background-position: 4px 4px; 
}
.column{
	background-image: url("${ctx}/jsp/statistics/images/chart_bar.png");
}
.pie{
	background-image: url("${ctx}/jsp/statistics/images/chart_pie.png");
}
</style>
</head>
<body style="overflow: auto">
	<center>
		<div class="datagrid-toolbar" id="tb" style="padding-left: 0px; height: 50px;">
			<div>
				<div style="float: left;margin-left: 20px;"><input type="button" class="column" onclick="pieClonm('1');" value="柱状图"/>&nbsp;&nbsp;&nbsp;&nbsp;<input class="pie"  onclick="pieClonm('0');" type="button" value="饼状图"/></div>
				<form id="searchForm" style="margin: 0;">
					&nbsp;&nbsp;<input type="radio" value="jjlx" checked="checked"
						name="type" />&nbsp;经济类型 &nbsp;&nbsp;<input type="radio"
						value="hylb" name="type" />&nbsp;企业类型 &nbsp;&nbsp;<input
						type="radio" value="xzqhqx" name="type" />&nbsp;所在地区
					&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-search" onclick="check();">统计</a>
				</form>
			</div>
		</div>
		<div style="width: 100%;height: 100%;" >
			<!-- 	这是柱形图 -->
			<div id="container" style="width: 60%; float: left;height: 100%;background-color: white;" ></div>
			<!-- 	表格数据 -->
			<div id="statistic"
				style="height: 100%;overflow:auto; font-size: 12px; padding-top: 5px; width: 40%; float: right; margin-top: 40px;">
				<table id="data" width="90%" height="100%;">
					<thead>
						<tr>
							<th align="center" width="150" class="typeName">经济类型</th>
							<th align="center" width="150">企业数量</th>
							<th align="center" width="150">所占百分比</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
		<!-- 	类型表格 -->
		<div id="entList">
			<div class="datagrid-toolbar" id="tb"
				style="padding-left: 0px; height: 10px; text-align: left;">
				<div>
					<form id="search" style="margin: 0;" method="post">
						&nbsp;&nbsp;企业名称:&nbsp;&nbsp;<input type="text"
							name="enterprise.qymc" id="qymc" />
						&nbsp;&nbsp;法定代表人:&nbsp;&nbsp;<input type="text"
							name="enterprise.fddbr" /> &nbsp;&nbsp;企业规模:&nbsp;&nbsp;<input
							class="easyui-combobox"
							data-options="editable:false,panelHeight:'auto'" type="text"
							name="enterprise.qygm" id="qygm" /> &nbsp;&nbsp;<a
							href="javascript:void(0);" class="easyui-linkbutton"
							iconCls="icon-search" onclick="crud.search();">查询</a>
						&nbsp;&nbsp;<a href="javascript:void(0);"
							class="easyui-linkbutton" iconCls="icon-clear"
							onclick="clearSearch()">清空</a>
					</form>
				</div>
			</div>
			<div style="margin-top: 5px; height: 80%;">
				<table id="data_list" width="80%" loadMsg="正在努力拉取数据中..." fit="true"
					fitColumns="true">
					<thead>
						<tr>
							<th align="center" field="qymc" width="150"
								formatter="gridFormatterLink">企业名称</th>
							<th align="center" field="fddbr" width="150"
								formatter="gridFormatterLength">法定代表人</th>
							<th align="center" field="lxdh" width="150"
								formatter="gridFormatterLength">联系电话</th>
							<th align="center" field="qygmName" width="150"
								formatter="gridFormatterLength">企业规模</th>
							<!--             <th align="center" field="id" width="120" formatter="gridFormatter">操作</th>          -->
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</center>
	<div id="dd">
		<div id="ifrma" style="height: 100%; width: 100%;"></div>
	</div>
	<div style="height: 100px; width: 100%;float: left;"></div>
</body>
</html>