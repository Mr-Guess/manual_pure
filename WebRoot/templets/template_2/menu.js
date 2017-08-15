/*
 * 创建读取XML文件的对象
 */
 //创建DOM对象
var navigationxml;



function CreateXMLHttpRequest()
{
    if(window.ActiveXObject)
    {
        //xmlHttp=new ActiveXObject("Microsoft.XMLHTTP");
        xmlhttp = new ActiveXObject("MSXML2.XMLHTTP.3.0"); //IE写法 
        navigationxml = new ActiveXObject("MSXML2.DOMDocument.3.0");
    }
    else if(window.XMLHttpRequest)
    {
       	xmlhttp=new XMLHttpRequest();//firefox写法
    	navigationxml = document.implementation.createDocument("","",null);//创建DOM对象
     
    }
    return xmlhttp;
}

var thisMenuNum = 0;
var openItemType = 0;

/*
 * 设置cookie
 */
function setCookie(name, value) {
	var Days = 30; // 此 cookie 将被保存 30 天
	var exp = new Date();
	exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
	document.cookie = name + "=" + escape(value) + ";expires="
			+ exp.toGMTString();
}
/*
 * 得到cookie
 */
function getCookie(name) {

	var arr = document.cookie
			.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (arr != null)
		return unescape(arr[2]);
	return null;
}
/*
 * 删除cookie
 */
function delCookie(name) {
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = getCookie(name);
	if (cval != null)
		document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}
/*
 * 设置打开菜单的方式
 */
function setOpenItemType(val) {
	openItemType = val;
	setCookie("openItemType01", val);
	openItem(thisMenuNum);
}
/*
 * 根据xml文件路径形成菜单
 */

function initMenu(filepath) {
	var openItemType01 = getCookie("openItemType01");
	openItemType = openItemType01 == null ? 1 : openItemType01;
	
	//var xmlhttp = new ActiveXObject("MSXML2.XMLHTTP.3.0"); //IE写法 
	var xmlhttp = CreateXMLHttpRequest();//firefox 写法
	//var  xmlhttp=new XMLHttpRequest();
	//alert(xmlhttp);
	xmlhttp.open("post", filepath, false);
	xmlhttp.send('');	
	//IE的load(Url); loadXML()方法可直接向XML DOM输入XML字符串，例如：xmldoc.loadXML("<root><son/></root>");
	//navigationxml.loadXML(xmlhttp.responseText);
	if (window.ActiveXObject) {
		navigationxml.loadXML(xmlhttp.responseText);
	}
	else if(window.XMLHttpRequest){
		var parser = new DOMParser(); 
		// 解析字符串，创建DOM对象 
		navigationxml= parser.parseFromString(xmlhttp.responseText, "text/xml"); 
	}
	openmenu();/*打开一级导航菜单*/
	setTimeout('openItem(1)', 500);
}
/*
 * 刷新菜单
 */
function refreshmenu(menuul) {
	var lis = menuul.children;
	for ( var i = 0; i < lis.length; i++) {
		lis[i].className = "nochose";
	}
}
/*
 * 展开菜单
 */
function openmenu() {
	var xml = navigationxml;

	var xsl;
	if (window.ActiveXObject) {//IE浏览器
		xsl = new ActiveXObject("MSXML2.DOMDocument.3.0");
	}
	else if(window.XMLHttpRequest){//fireFox浏览器	
		xsl = document.implementation.createDocument("","",null);
	}
	xsl.async = false;
	xsl.load("images/menu/menu.xsl");	
	if (window.ActiveXObject) {
		menudiv.innerHTML = (xml.transformNode(xsl));/*menudiv 一级菜单导航div*/
	}
	else if(window.XMLHttpRequest){
		 //定义XSLTProcesor对象
	    var xsltProcessor=new XSLTProcessor();
	    xsltProcessor.importStylesheet(xsl);
	    // transformToDocument方式
	    var result=xsltProcessor.transformToDocument(xml);
	    var xmls=new XMLSerializer();	  
	    menudiv.innerHTML=xmls.serializeToString(result);
	    
	}
	
	/*以下是控制左右滚动按钮的显示*/
	var mainwidth = ($("#procromain").get(0).scrollWidth);
	var menuwidth = ($("#menudiv").get(0).scrollWidth);
	
	if (menuwidth - $("#proccc").get(0).scrollLeft > mainwidth ) {
		$("#srbtn").get(0).style.backgroundImage = "url('images/menu/btn_right.gif')";
	}
	if ($("#proccc").get(0).scrollLeft > 0) {
		$("#slbtn").get(0).style.backgroundImage = "url('images/menu/btn_left.gif')";
	}
}
/*
 * 显示主菜单的第几栏
 */
function openItem(num) {
	thisMenuNum = num;
	if (openItemType == 0) {
		opentree(num);
	} else if (openItemType == 1) {
		openoutlook(num);
	} else {
		opentree(num);
	}
}
/*
 * 以树形结构打开菜单
 */
function opentree(num) {

	var menu = navigationxml.selectSingleNode("/root/item[" + (num - 1) + "]");

	var xml = new ActiveXObject("MSXML2.DOMDocument.3.0");
	xml.async = false;

	xml.loadXML(menu.xml);

	var xsl = new ActiveXObject("MSXML2.DOMDocument.3.0");
	xsl.async = false;
	xsl.load("navigation/tree.xsl");
	treediv.innerHTML = (xml.transformNode(xsl));
}
/*
 * 展开子菜单
 */
function openoutlook(num) {
	var nodes =SelectNodes(navigationxml, "/root/item");
	var sub_nodes = SelectNodes(nodes[num - 1],"item");
	var QQLayerNumber = sub_nodes.length;/*获取所选中的一级菜单的二级菜单个数*/
	var MenuGroupList = new Array();
	for ( var i = 0; i < sub_nodes.length; i++) {
		var j = i + 1;
		var sub_item = "MenuGroupCaption4Group" + j;/*144行重复定义，暂时无意义*/
		var sub_remark = sub_nodes[i].getAttribute("remark");/*获取二级菜单名称*/
		if (sub_remark == null || sub_remark == "") {/*没取到remark,给默认值*/
			sub_remark = "菜单";
		}
		var a = sub_nodes[i].getAttribute("onclick");/*获取二级菜单onclick事件*/
		if (a == null || a == "") {/*没取到onclick,置空*/
			a = "";
		}

		var MenuGroupCaptionCSS = "MenuGroupCaption";

		var sub_item = [ "&nbsp;" + sub_remark + "&nbsp;",
				"MenuGroupCaption4Group" + j, MenuGroupCaptionCSS,
				"templets/template_2/leftmenu/spacer.gif", a ];

		var MenuGroup = "MenuGroup" + j;/*暂时无意义*/
		MenuGroup = new Array();
		MenuGroup.push(sub_item);
		var sub_sub_nodes = SelectNodes(sub_nodes[i],"item");/*获取三级菜单item*/
		for ( var m = 0; m < sub_sub_nodes.length; m++) {
			var a = sub_sub_nodes[m].getAttribute("onclick");//获得onclick属性
			var href = sub_sub_nodes[m].getAttribute("href");//获得href属性
			var target = sub_sub_nodes[m].getAttribute("target");//获得target属性
			var picture = sub_sub_nodes[m].getAttribute("ico");//获得图片属性
			if (a == null || a == ""||a == "null") {
				a = "";//设置onclick默认值
			}
			if (href == null || href == ""|| href == "null") {
				href = "";//设置href默认值
			}
			if (target == null || target == ""|| target == "null") {
				target = "";//设置target默认值
			}
			if (picture == null || picture == ""|| picture == "null") {
				
				picture = "templets/template_2/leftmenu/spacer.gif";//设置图片默认值
			}
			var method = "";
			//QQLayerNumber 2级菜单的个数 根据2级菜单下子菜单的个数 决定每行放几个三级菜单
			/*!!!QQLayerNumber>6 情况没考虑*/
			if (QQLayerNumber <= 4) {
				if (sub_sub_nodes.length <= 4) {
					method = "MenuGroupItem1";
				} else if (sub_sub_nodes.length > 4
						&& sub_sub_nodes.length <= 8) {
					method = "MenuGroupItem2";
				} else if (sub_sub_nodes.length > 8) {
					method = "MenuGroupItem3";
				}
			} else if (QQLayerNumber > 4 && QQLayerNumber <= 6) {
				if (sub_sub_nodes.length <= 3) {
					method = "MenuGroupItem1";
				} else if (sub_sub_nodes.length > 3
						&& sub_sub_nodes.length <= 8) {
					method = "MenuGroupItem2";
				} else if (sub_sub_nodes.length > 6
						&& sub_sub_nodes.length <= 12) {
					method = "MenuGroupItem3";
				} else if (sub_sub_nodes.length > 12) {
					method = "MenuGroupItem4";
				}
			}
			var n = m + 1;//m代表当前循环到第几次
			var sub_sub_item = "MenuGroupCaption4Group" + j + "_" + n;
			var sub_sub_remark = sub_sub_nodes[m].getAttribute("remark");
			if (sub_sub_remark == null || sub_sub_remark == ""|| sub_sub_remark == "null") {
				sub_sub_remark = "子菜单";
			}
			sub_sub_item = [ a, sub_sub_remark,
					"MenuGroupItem4Group" + j + "_" + n, method, picture, href,
					target ];
			MenuGroup.push(sub_sub_item);
		}
		MenuGroupList.push(MenuGroup);
	}
	treediv.innerHTML = CreateMenuGroup(MenuGroupList);
	MenuGroupExpand(preExpandMenuGroupID);
	rlbtnControl();
}
/*
 * 定义id为"isMapbutton的展开与否事件"
 */
function isMapbuttonEvent(obj) {
	if ($("#main").get(0).style.display == "none") {
		$("#main").get(0).style.display = "block";
	} else {
		$("#main").get(0).style.display = "none";
	}
}

var LastExpandMenuGroup = null;

var MenuGroup_length = 0;

var preExpandMenuGroupID = "";
/*
 * 根据LIST创建菜单序列
 * 左侧菜单高度设置如下
 * if (menuGroupList.length == 1) {
		height = 98;//只有一个菜单
	} else {
		height = 98;//多个菜单
	}
 */
function CreateMenuGroup(menuGroupList) {
	var str = "";

	var menuGroupCaptionDiv = "";
	var menuGroupCaptionDivID = "";
	var menuGroupPanelDiv = "";
	var menuGroupPanelDivID = "";
	var menuGroupItemDiv = "";
	var menuGroupItemDivID = "";

	// getPanels
	top.MenuGroup_length = menuGroupList.length;

	var MenuGroup, MenuGroupCaption, MenuGroupItem;
	var height;
	for ( var i = 0; i < menuGroupList.length; i++) {

		MenuGroup = menuGroupList[i];

		MenuGroupCaption = MenuGroup[0];

		menuGroupCaptionDivID = "MenuGroupCaption_" + MenuGroupCaption[1];
		menuGroupPanelDivID = "menuGroupPanelDiv_" + MenuGroupCaption[1];

		if (i == 0) {
			preExpandMenuGroupID = menuGroupPanelDivID;
		}

		menuGroupCaptionDiv = "";
		menuGroupPanelDiv = "";
		menuGroupItemDiv = "";
		//生成左边栏上的2级菜单
		menuGroupCaptionDiv = '<div' + ' id="' + menuGroupCaptionDivID + '"'
				+ ' class="' + MenuGroupCaption[2] + '"'
				+ ' onclick=changeClassName(this);MenuGroupExpand("'
				+ menuGroupPanelDivID + '");' + MenuGroupCaption[4] + ';'
				+ ' >' + ' <img src="' + MenuGroupCaption[3]
				+ '" align="absmiddle"> ' + MenuGroupCaption[0] + '</div>';

		// 为了解决一个菜单有竖滚动条,同时控制整体左侧树形菜单的高度
		if (menuGroupList.length == 1) {
			height = 98;// 原123
		} else {
			height = 98;// 原133
		}

		menuGroupPanelDiv = '<div'
				+ ' style="display:none;height:'
				+ (self.document.body.offsetHeight - menuGroupList.length * 28 - height)
				+ 'px;"' + ' class="MenuGroupPanel"' + ' id="'
				+ menuGroupPanelDivID + '"' + ' >';
		for ( var j = 1; j < MenuGroup.length; j++) {
			MenuGroupItem = MenuGroup[j];
			menuGroupItemDivID = "menuGroupItemDiv_" + MenuGroupItem[2];
			menuGroupItemDiv += '<div' + ' id="' + menuGroupItemDivID + '"'
					+ ' class="' + MenuGroupItem[3] + '"'
					+ ' onmouseenter=MenuGroupChangeCss("' + MenuGroupItem[3]
					+ '_MSOVER",this);';
			menuGroupItemDiv += ' onmouseleave=MenuGroupChangeCss("' + MenuGroupItem[3] + '",this);';
			if (MenuGroupItem[0] != "") {
				menuGroupItemDiv += ' onclick="' + MenuGroupItem[0] + ';refreshLevel3('+ menuGroupPanelDivID+','+MenuGroupItem[3]+');"';
			}
			menuGroupItemDiv += ' >';
			if (MenuGroupItem[5] != "") {
				menuGroupItemDiv += '<a href="' + MenuGroupItem[5]
						+ '" target="' + MenuGroupItem[6] + '">';
			}
			menuGroupItemDiv += '<img border="0" src="' + MenuGroupItem[4] + '" align="absmiddle"><br> ';
			menuGroupItemDiv += MenuGroupItem[1];
			if (MenuGroupItem[5] != "") {
				menuGroupItemDiv += '</a>';
			}
			menuGroupItemDiv += '</div>';
		}

		menuGroupPanelDiv += menuGroupItemDiv + '</div>';

		str += menuGroupCaptionDiv + menuGroupPanelDiv;
	}

	return str;
}

/*三级菜单选中时，更换选中的图片样式...处理中为完成*/
function refreshLevel3(id,classname){
	//$("#"+id > div).toggleClass(classname);
	//$(this).attr("class",classname+"_MSOVER");
}
/*
 * 菜单扩张
 */
function MenuGroupExpand(sMenuGpCpID) {
	var currObj = document.all[sMenuGpCpID];

	if (currObj.style.display == "none") {
		(currObj.style.display == "none") ? MenuGroupShow(currObj)
				: MenuGroupHide(currObj);
	}
}
/*
 * 显示菜单
 */
function MenuGroupShow(el) {
	if (LastExpandMenuGroup != null) {
		MenuGroupHide(LastExpandMenuGroup);
	}
	el.style.display = "block";
	LastExpandMenuGroup = el;
}
function MenuGroupHide(el) {
	el.style.display = "none";
}
/*
 * 改变菜单的CSS
 */
function MenuGroupChangeCss(sClassName, obj) {
	obj.className = sClassName;
}

/*
 * 改变类名
 */
function changeClassName(obj) {
	for ( var i = 1; i <= top.MenuGroup_length; i++) {
		document.getElementById("MenuGroupCaption_MenuGroupCaption4Group" + i).className = 'MenuGroupCaption';
	}
	obj.className = "MenuGroupCaption2";
}

//初始化查看左右按钮
function rlbtnControl(){
	var mainwidth=($("#procromain").get(0).scrollWidth);
	var menuwidth=($("#menudiv").get(0).scrollWidth);
	if(menuwidth<mainwidth){
		$("#srbtn").get(0).style.backgroundImage = "url('images/btn_rightbg.gif')";
		$("#slbtn").get(0).style.backgroundImage = "url('images/btn_leftbg.gif')";
	}
}

// 用来隐藏或者打开左边菜单栏 移入main.html中
var buttonwidth = 128;	//1级导航每个按钮所占宽度
var buttonnum = 6;		//1级导航每屏幕显示个数

/*
 * 菜单往右滑动事件（mainwidth-x=每个菜单所占的宽度*每屏显示的菜单个数）
 */
function prorgt() {
	var mainwidth = ($("#procromain").get(0).scrollWidth);
	var menuwidth = ($("#menudiv").get(0).scrollWidth);
	if (mainwidth +$("#proccc").get(0).scrollLeft-150 < menuwidth  ) {
		for ( var i = 0; i < buttonwidth; i++) {
			$("#proccc").get(0).scrollLeft = $("#proccc").get(0).scrollLeft + buttonnum;
		}
		$("#slbtn").get(0).style.backgroundImage = "url('images/btn_left.gif')";
	}
	if (mainwidth - 150 >= menuwidth - $("#proccc").get(0).scrollLeft) {
		$("#srbtn").get(0).style.backgroundImage = "url('images/btn_rightbg.gif')";
	}
	

}
/*
 * 菜单往左滑动事件
 */
function prolft() {
	var mainwidth = ($("#procromain").get(0).scrollWidth);
	var menuwidth = ($("#menudiv").get(0).scrollWidth);
	if ($("#proccc").get(0).scrollLeft > 0) {
		for ( var i = 0; i < buttonwidth; i++) {
			$("#proccc").get(0).scrollLeft = $("#proccc").get(0).scrollLeft - buttonnum;
		}
		$("#srbtn").get(0).style.backgroundImage = "url('images/btn_right.gif')";
	}
	if ($("#proccc").get(0).scrollLeft <= 0) {
		$("#proccc").get(0).scrollLeft = 0;
		$("#slbtn").get(0).style.backgroundImage = "url('images/btn_leftbg.gif')";
	}
}
//平滑移出,即多步移出
function moveOutSmooth()
{
	//使用style.left属性,要通过parseInt()方法取数值
	var now_pos = parseInt($('#menuteam').css('left'));
	if(window.movingBack)
	{
	clearTimeout(movingBack);	//停止移入动作
	}
	//判断是否完全移出
	if(now_pos < 0)
	{
	//得到当前位置与目标位置之间的距离dx
	dx = 0-now_pos;
	//根据dx的值决定每步移动多少距离
	if(dx>30)
		 $('#menuteam').css('left',now_pos+5);
	else if(dx>10)
	     $('#menuteam').css('left',now_pos+2);
	else
		 $('#menuteam').css('left',now_pos+1);
	movingOut = setTimeout("moveOutSmooth()",5);
	}
	else
	{
	clearTimeout(window.movingOut);	//移到位后,停止移出
	}
}
//平滑移入,即多步移入
function moveBackSmooth()
{
	if(window.movingOut)
	{
	clearTimeout(movingOut);		//停止移出动作
	}
	//得到当前位置与目标位置的距离dx
	var dx = 180- $('#menuteam').css('pixelLeft');
	//判断是否隐藏到位
	if(document.getElementById('menuteam').style.pixelLeft>eval(0-180))
	{	
	//根据距离dx的大小,决定每步移动多大距离
	if(dx>30)
	document.getElementById('menuteam').style.pixelLeft -=5;
	else if(dx>10)
	document.getElementById('menuteam').style.pixelLeft -=2;
	else
	document.getElementById('menuteam').style.pixelLeft -=1;
	movingBack = setTimeout("moveBackSmooth()",5);
	}
	else
	{
	clearTimeout(window.movingBack);	//移到位后,停止移入
	}
}

//
function SelectSingleNode(xmlDoc, elementPath) 
{ 
    if(window.ActiveXObject) 
     { 
        return xmlDoc.selectSingleNode(elementPath); 
     } 
    else 
     { 
        var xpe = new XPathEvaluator(); 
        var nsResolver = xpe.createNSResolver( xmlDoc.ownerDocument == null ? xmlDoc.documentElement : xmlDoc.ownerDocument.documentElement); 
        var results = xpe.evaluate(elementPath,xmlDoc,nsResolver,XPathResult.FIRST_ORDERED_NODE_TYPE, null); 
        return results.singleNodeValue; 
     } 
} 
     
function SelectNodes(xmlDoc, elementPath) 
{ 
    if(window.ActiveXObject) 
     { 
        return xmlDoc.selectNodes(elementPath); 
     } 
    else 
     { 
        var xpe = new XPathEvaluator(); 
        var nsResolver = xpe.createNSResolver( xmlDoc.ownerDocument == null ? xmlDoc.documentElement : xmlDoc.ownerDocument.documentElement); 
        var result = xpe.evaluate(elementPath, xmlDoc, nsResolver, 0, null); 
        var found = []; 
        var res; 
        while   (res = result.iterateNext()) 
             found.push(res); 
        return found; 
     } 
}

function showMapEvent(){
	$("#main").get(0).style.display="none";
	$("#mainframe").get(0).style.display="block";
	//$("#btn_map").get(0).style.backgroundImage = "url(images/nomap.gif)";
}
function hideMapEvent(){
	$("#main").get(0).style.display="block";
	$("#mainframe").get(0).style.display="none";
	//$("#btn_map").get(0).style.backgroundImage = "url(images/hasmap.gif)";
}
function toggleMap(){
	if($("#main").get(0).style.display=="block"){
		showMapEvent();
	}else{
		hideMapEvent();
	}
}
