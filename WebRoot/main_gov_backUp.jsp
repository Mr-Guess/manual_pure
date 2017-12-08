<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN">

<html style=" overflow:hidden; height:100%; text-algin:center;">
<head>
<title>南京永坤信息科技有限公司</title>
<link rel="stylesheet" type="text/css"
	href="resources/js/jquery/easyui-1.3/themes/cupertino/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="resources/js/jquery/easyui-1.3/themes/icon.css" />
<script src="resources/js/jquery/jquery-1.7.2.js" type="text/javascript"></script>
<script type="text/javascript"
	src="resources/js/jquery/easyui-1.3/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="resources/js/jquery/easyui-1.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx}/js/GisUtil.js"></script>
<!-- <link href="templets/template_2/css/JS_QQMenuGroup2.css" rel="stylesheet" type="text/css" /> -->
<link href="templets/template_2/css/template_zf.css" rel="stylesheet"
	type="text/css" />
<link href="css/newMenu_zf.css" rel="stylesheet" type="text/css" />
<style>
#menuPosition {
	font-size: 12px;
}

#menuPosition .firstPosition {
	color: #5F6062;
	font-weight: normal;
	font-size: 12px;
}

#menuPosition .position3 {
	color: #5F6062;
	font-weight: normal;
	font-size: 12px;
}

#menuPosition .position2 {
	color: #5F6062;
	font-weight: normal;
	font-size: 12px;
}

#menuPosition .position1 {
	color: #5F6062;
	font-size: 12px;
	font-weight: blod;
}

#menuPosition .menuSplitStr {
	color: #5F6062;
	font-weight: normal;
}

#user_clor {
	color: #fff;
	font-family: SimHei;
	height: 13px;
	float: left;
	padding: 3px 0 0 25px;
	background: url(images/user.png) no-repeat center left;
}

#change_mode {
	height: 47px;
	float: left;
	border: none
}

#re_fresh {
	background: url(images/rfresh.png) 0 center no-repeat;
	padding: 10px 0 11px 35px;
}

#quite {
	background: url(images/exit.png) 0 1px no-repeat;
	padding: 10px 0 11px 35px;
	margin-top: 8px;
}

#yewu_mod {
	background: url(templets/template_2/top/Subfield.jpg) no-repeat right
		bottom;
	height: 47px;
	margin: 0 5px;
	display: block;
	padding: 0 5px
}

#yewu_mod:hover {
	background: url(templets/template_2/top/main_ahover.png) no-repeat top
		center;
	margin: 0 5px;
	display: block;
	padding: 0 5px;
	height: 47px;
}

#map_mod {
	background: url(templets/template_2/top/Subfield.jpg) no-repeat right
		bottom;
	height: 47px;
	margin: 0 5px;
	display: block;
	padding: 0 5px
}

#map_mod:hover {
	background: url(templets/template_2/top/main_ahover.png) no-repeat top
		center;
	margin: 0 5px;
	display: block;
	padding: 0 5px;
	height: 47px;
}

.dock-item {
	background: url(templets/template_2/top/Subfield.jpg) no-repeat right
		bottom;
	height: 47px;
	display: block;
	padding: 0 5px;
	float: left
}

.dock-item:hover {
	background: url(templets/template_2/top/main_ahover.png) no-repeat top
		center;
	display: block;
	padding: 0 5px;
	height: 47px;
}

.main_back {
	padding: 4px 5px 2px 5px;
	background: #007263;
	border: 1px solid #01ad96;
	margin: 3px 0 0 10px
}
</style>
<script src="js/publicfun.js"></script>
<script type="text/javascript">
//向上取整
	function DivUp(number1, number2) {
	    var n1 = Math.round(number1);
	    var n2 = Math.round(number2);
	    var result = n1 / n2;
	    if (result >= 0) {
	    	result = Math.ceil(result);
	    } else {
	    	result = Math.floor(result);
	    }
	    return result;
	}
	//向下取整
	function DivDown(number1, number2) {
	    var n1 = Math.round(number1);
	    var n2 = Math.round(number2);
	   // alert(n1+"::::::::"+n2);
	    var result = n1 / n2;
	    if (result >= 0) {
	    	result = Math.floor(result);
	    	//alert(result);
	    
	    } else {
	    	result = Math.ceil(result);
	    	//alert(result);
	    }
	    return result;
	}
	//菜单等级
	var level = 0;
	//1级菜单数量
	var levelOneCount = 0;
	//1级菜单1组10个
	var groupCount = DivDown($(document).width(),290);
	//alert($(document).width);
	//当前显示的菜单组number:group0
	var groupShowNumber = 1;
	//一共多少组
	var groupTotalCount = 0;
	function showall(menu_list, parent) {
        for (var menu in menu_list) {
        	level++;
        	if(level==1){
        		levelOneCount++;
        	}
        	if(typeof(menu_list[menu].children) == "undefined" )
        		continue;
           	if(level==1){
           		var groupNumber = DivUp(levelOneCount,groupCount);
                var li = $("<li class=\"level"+level+" group"+groupNumber+"\"></li>");
           	}else{
                var li = $("<li class=\"level"+level+"\"></li>");
           	}
            var url = menu_list[menu].menuUrl == ''? 'javascript:void(0)' : menu_list[menu].menuUrl;
         	var menuId = menu_list[menu].menuId;
            if(menu_list[menu].menuUrl.indexOf(".action") == -1 && url.indexOf("javascript") == -1 && menu_list[menu].menuUrl.indexOf("Action") == -1){
           		if(menu_list[menu].menuUrl == ''){
            	$(li).append('<a href="'+url+'" target="main" >'+ menu_list[menu].menuName+'</a>')
            		.append("<ul></ul>").appendTo(parent);
	            } else {
	            	$(li).append('<a href="autoJump.action?menuId='+menuId+'&url='+url+'" target="main" onclick="addMenuInfo(this)">'+ menu_list[menu].menuName+'</a>')
	        		.append("<ul></ul>").appendTo(parent);
	            }
           	}else{
           		if(menu_list[menu].menuUrl == ''){
            	$(li).append('<a href="'+url+'" target="main" >'+ menu_list[menu].menuName+'</a>')
            		.append("<ul></ul>").appendTo(parent);
	            } else {
	            	$(li).append('<a href="'+url+'" target="main" onclick="addMenuInfo(this)">'+ menu_list[menu].menuName+'</a>')
	        		.append("<ul></ul>").appendTo(parent);
	            }
           	}
            if (menu_list[menu].children.length > 0) {
                showall(menu_list[menu].children, $(li).children().eq(1));
            }
            level--;
        }
    }
	//菜单位置中的菜单分隔符
	var menuSplitStr = '&nbsp;>&nbsp;';
	//菜单位置级别
	var menuPositionLevel = 0;
	//菜单点击操作记录
	var operationRecord = [];
	//菜单当前显示index
	var operationIndex = 0;
	//是否是前进后退的模拟点击操作
	var operationSimulator = false;
	//后退操作
	function goBack() {
		if(operationIndex<=1)
			return;
		operationIndex--;
		operationSimulator = true;
		var obj = operationRecord[operationIndex-1];
		obj.click();
	}
	//前进操作
	function goForward() {
		if(operationIndex>=operationRecord.length)
			return;
		operationIndex++;
		operationSimulator = true;
		var obj = operationRecord[operationIndex-1];
		obj.click();
	}
	//添加 当前位置信息
	function addMenuInfo(obj){
		gis.showMain();
		//判断是否为前进或后退操作
		if(!operationSimulator){
			if(operationIndex<operationRecord.length) {
				operationRecord.splice(operationIndex,operationRecord.length-operationIndex);
				operationIndex = operationRecord.length;
			}
			operationRecord.push(obj);
			operationIndex++;
		} else {
			operationSimulator = false;
		}
		$('#menuPosition').html('<span class="firstPosition">您当前的位置：</span>');
		addAllMenuInfo($(obj),$('#menuPosition'));
	}
	function addAllMenuInfo(obj,menuPosition){
		menuPositionLevel++;
		var parent = $(obj).parents('li').parents('li').children().eq(0);
		if($(parent).html() != null){
			addAllMenuInfo(parent,menuPosition);
			$(menuPosition).append('<span class="menuSplitStr">'+menuSplitStr+'</span>');
		}
		$(menuPosition).append('<span class="position' + menuPositionLevel + '">' + $(obj).html() +'</span>');
		menuPositionLevel--;
	}
	
	function toggleMenu(){
		for(var i =0 ; i<=groupTotalCount ;i++){
			if(i!=groupShowNumber)
				$(".group"+i).hide();
		}
		$(".group"+groupShowNumber).show();
	}
	//菜单下一页
	function goAfter(){
		if(groupShowNumber<groupTotalCount)
			groupShowNumber++;
		toggleMenu();
	}
	//菜单上一页
	function goBefore(){
		if(groupShowNumber>1)
			groupShowNumber--;
		toggleMenu();
	}
	$(function(){
		var url = '';
		
		<c:if test="${param.menuModule!=null}">url='&menuModule=${param.menuModule}';</c:if>
		
		$.post('menu/menuAction!loadMenu.action?menuKind=1'+url,function(data){
		//alert(data);
			var data = eval('(' + data + ')');
			var showlist = $('#nav');
			
			
            showall(data, showlist);
            //alert($("#menuModule").val());
            groupTotalCount = DivUp(levelOneCount,groupCount);
            if(groupTotalCount<=1){
            	$('#nav div.lee').hide();
            	return;
            }
            toggleMenu();
		});
		gis = new GisUtil({mainFrame:$('#main'),gisFrame:$('#GisMain')});
	});
	function gisInit() {
		//gis.setParams({gisFunction:window.frames['GisMain'].linkFromBusinessPage});
	}
	function mainInit() {
		//gis.setParams({gisFunction:window.frames['main'].linkFromBusinessPage});
	}
	function toggleBtn() {
		$('#yewu_mod').toggle();
		$('#map_mod').toggle();
	}
</script>
</head>

<body style="overflow: hidden;">
	<div class="toptitle">
		<div class="topright">
			<div class="topshe">
				<div class=""></div>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td align="left">
							<div id="user_clor">当前用户：${user_name}</div>
						</td>
						<td align="left"><script type="text/javascript"
								language="javascript">
			    today = new Date();
			    function initArray()
			    {
				    this.length=initArray.arguments.length                
				    for(var i=0;i<this.length;i++)
				    this[i+1]=initArray.arguments[i]
			    }       
			    var d = new initArray
			    (
				    "星期日",                    
				    "星期一",                 
				    "星期二",
				    "星期三",
				    "星期四",    
				    "星期五",                       
				    "星期六"
			    );                                                             
	
			    document.write
			    (
				    "<span class='datewords'> ",
				    today.getFullYear()," 年 ",
				    today.getMonth()+1," 月 ",
				    today.getDate()," 日 ",
				    d[today.getDay()+1],
				    "</span>" 
			    );
		    </script></td>
						<td>
							<div id="change_mode">
								<a id="yewu_mod" href="javascript:gis.toggle();toggleBtn();"
									style="display: none;"><img
									src="templets/template_2/top/main_yw.png" /></a><a id="map_mod"
									href="javascript:gis.toggle();toggleBtn();"><img
									src="templets/template_2/top/main_dt.png" /></a>
							</div>
							<div id="link_clor">
								<a class="dock-item" href="${ctx}/jsp/gov/baseInfo/release.jsp"
									style="margin-right: 10px;"> <!-- href="${ctx}/jsp/menhu/index.jsp"-->
									<img src="templets/template_2/top/main_sy.png" /></a> <a
									class="dock-item" href="#" onClick="location.reload(true)"><img
									src="templets/template_2/top/main_sx.png" /></a><a
									class="dock-item" href="${ctx}/logout!logout.action"
									style="margin-right: 10px;"><img
									src="templets/template_2/top/main_tc.png" /></a>
							</div>
						</td>
					</tr>
					<input type="hidden" id="yhm" value="${user_name} ">
				</table>
			</div>
		</div>
	</div>
	<div class="datewordspos" id='ces'>
		<%-- <div class="userinfo">当前用户：${user_name}</div> --%>
	</div>
	<div>
		<!-- 这里是菜单位置 -->
		<ul id="nav">

			<div class="lee"
				style="position: absolute; float: left; margin: 5px 10px 0 0; right: 0; color: #FFF; height: 30px;">
				<a href="#" onclick="goBefore();return false;" class="main_back"><img
					src="templets/template_2/top/main_back.png" /></a><a href="#"
					onclick="goAfter();return false;" class="main_back"><img
					src="templets/template_2/top/main_fowt.png" /></a>
			</div>
		</ul>
		<div class="top_bottom">
			<img src="templets/template_2/top/house.png" style="float: left;" />
			<h2 id="menuPosition">
				<span class="firstPosition">您当前的位置</span>
			</h2>
			<div style="float: right; margin: 5px; line-height: 20px;">
				<a href="javascript:goBack();" class="back"><img
					src="images/weihai_index/back2.png" id="Image1"
					style="vertical-align: middle; margin: 0px 5px 0 5px;" />后退</a>&nbsp;&nbsp;
				<a href="javascript:goForward();" class="forward">前进<span
					class="forward"><img src="images/weihai_index/forward2.png"
						id="Image2" style="vertical-align: middle; margin: 0px 0px 0 5px;" /></span></a>

			</div>
		</div>
	</div>
	<div style="height: 80%; margin: 0; padding: 0; width: 100%;">
		<div id="iframeTd" style="height: 100%; margin: 0; padding: 0;">
			<script>
            function resize(){
                 document.getElementById("main").style.height=(document.getElementById('iframeTd').offsetHeight - 3)+'px';            
            }
            </script>
			<iframe id="main" name="main" width="100%"
				src="${ctx}/jsp/statistics/entStatistics.jsp" frameborder="0"
				onload="mainInit()"></iframe>
			<iframe id="GisMain" name="GisMain" width="100%" src=""
				frameborder="0" onload="gisInit()" style="display: none;"></iframe>
		</div>
	</div>
	<div id="topWindow" style="display: none;"></div>
	<input type="hidden" value="<%=request.getParameter("menuModule") %>" id="menuModule" />
</body>
</html>