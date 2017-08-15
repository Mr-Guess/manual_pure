/*
 * ������ȡXML�ļ��Ķ���
 */
var navigationxml = new ActiveXObject("MSXML2.DOMDocument.3.0");
navigationxml.async = false;
var thisMenuNum = 0;
var openItemType = 0;

/*
 * ����cookie
 */
function setCookie(name, value) {
	var Days = 30; // �� cookie �������� 30 ��
	var exp = new Date();
	exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
	document.cookie = name + "=" + escape(value) + ";expires="
			+ exp.toGMTString();
}
/*
 * �õ�cookie
 */
function getCookie(name) {

	var arr = document.cookie
			.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (arr != null)
		return unescape(arr[2]);
	return null;
}
/*
 * ɾ��cookie
 */
function delCookie(name) {
	var exp = new Date();
	exp.setTime(exp.getTime() - 1);
	var cval = getCookie(name);
	if (cval != null)
		document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
}
/*
 * ���ô򿪲˵��ķ�ʽ
 */
function setOpenItemType(val) {
	openItemType = val;
	setCookie("openItemType01", val);
	openItem(thisMenuNum);
}
/*
 * ���xml�ļ�·���γɲ˵�
 */
function initMenu(filepath) {
	
	var openItemType01 = getCookie("openItemType01");
	openItemType = openItemType01 == null ? 1 : openItemType01;
	var xmlhttp = new ActiveXObject("MSXML2.XMLHTTP.3.0");
	xmlhttp.open("post", filepath, false);
	xmlhttp.send();		
	navigationxml.loadXML(xmlhttp.responseText);
	openmenu();
	setTimeout('openItem(1)', 500);
}
/*
 * ˢ�²˵�
 */
function refreshmenu(menuul) {
	var lis = menuul.children;
	for ( var i = 0; i < lis.length; i++) {
		lis[i].className = "nochose";
	}
}
/*
 * չ���˵�
 */
function openmenu() {
	var xml = navigationxml;
	
	var xsl = new ActiveXObject("MSXML2.DOMDocument.3.0");
	xsl.async = false;
	xsl.load("navigation/menu.xsl");
	menudiv.innerHTML = (xml.transformNode(xsl));
	
		
	var mainwidth = ($("#procromain").get(0).scrollWidth);
	var menuwidth = ($("#menudiv").get(0).scrollWidth);
	if (menuwidth - $("#proccc").get(0).scrollLeft > mainwidth - 186) {
		$("#srbtn").get(0).style.backgroundImage = "url(templets/template_1/top/roll_2.gif)";
	}
	if ($("#proccc").get(0).scrollLeft > 0) {
		$("#slbtn").get(0).style.backgroundImage = "url(templets/template_1/top/roll_1.gif)";
	}
}
/*
 * ��ʼ��ʱ��ʾ���˵��ĵڼ���
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
 * �����νṹ�򿪲˵�
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
 * չ���Ӳ˵�
 */
function openoutlook(num) {
	var nodes = navigationxml.selectNodes("/root/item");
	var sub_nodes = nodes[num - 1].selectNodes("item");
	var QQLayerNumber = sub_nodes.length;
	var MenuGroupList = new Array();
	for ( var i = 0; i < sub_nodes.length; i++) {
		var j = i + 1;
		var sub_item = "MenuGroupCaption4Group" + j;
		var sub_remark = sub_nodes[i].getAttribute("remark");
		if (sub_remark == null || sub_remark == "") {
			sub_remark = "�˵�";
		}
		var a = sub_nodes[i].getAttribute("onclick");
		if (a == null || a == "") {
			a = "";
		}

		var MenuGroupCaptionCSS = "MenuGroupCaption";

		var sub_item = [ "&nbsp;" + sub_remark + "&nbsp;",
				"MenuGroupCaption4Group" + j, MenuGroupCaptionCSS,
				"templets/template_1/leftmenu/spacer.gif", a ];

		var MenuGroup = "MenuGroup" + j;
		MenuGroup = new Array();
		MenuGroup.push(sub_item);
		var sub_sub_nodes = sub_nodes[i].selectNodes("item");
		for ( var m = 0; m < sub_sub_nodes.length; m++) {
			var a = sub_sub_nodes[m].getAttribute("onclick");
			var href = sub_sub_nodes[m].getAttribute("href");
			var target = sub_sub_nodes[m].getAttribute("target");
			var picture = sub_sub_nodes[m].getAttribute("ico");
			if (a == null || a == "") {
				a = "";
			}
			if (href == null || href == "") {
				href = "";
			}
			if (target == null || target == "") {
				target = "";
			}
			if (picture == null || picture == "") {
				picture = "templets/template_1/leftmenu/spacer.gif";
			}
			var method = "";
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
			var n = m + 1;
			var sub_sub_item = "MenuGroupCaption4Group" + j + "_" + n;
			var sub_sub_remark = sub_sub_nodes[m].getAttribute("remark");
			if (sub_sub_remark == null || sub_sub_remark == "") {
				sub_sub_remark = "�Ӳ˵�";
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
}
/*
 * ����idΪ"isMapbutton��չ������¼�"
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
 * ���LIST�����˵�����
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

		menuGroupCaptionDiv = '<div' + ' id="' + menuGroupCaptionDivID + '"'
				+ ' class="' + MenuGroupCaption[2] + '"'
				+ ' onclick=changeClassName(this);MenuGroupExpand("'
				+ menuGroupPanelDivID + '");' + MenuGroupCaption[4] + ';'
				+ ' >' + ' <img src="' + MenuGroupCaption[3]
				+ '" align="absmiddle"> ' + MenuGroupCaption[0] + '</div>';

		// Ϊ�˽��һ���˵����������
		if (menuGroupList.length == 1) {
			height = 133;// ԭ125
		} else {
			height = 133;// ԭ118
		}

		menuGroupPanelDiv = '<div'
				+ ' style="display:none;height:'
				+ (self.document.body.offsetHeight - menuGroupList.length * 32 - height)
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
				menuGroupItemDiv += ' onclick="' + MenuGroupItem[0] + '"';
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
/*
 * �˵�����
 */
function MenuGroupExpand(sMenuGpCpID) {
	var currObj = document.all[sMenuGpCpID];

	if (currObj.style.display == "none") {
		(currObj.style.display == "none") ? MenuGroupShow(currObj)
				: MenuGroupHide(currObj);
	}
}
/*
 * ��ʾ�˵�
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
 * �ı�˵���CSS
 */
function MenuGroupChangeCss(sClassName, obj) {
	obj.className = sClassName;
}

/*
 * �ı�����
 */
function changeClassName(obj) {
	for ( var i = 1; i <= top.MenuGroup_length; i++) {
		document.getElementById("MenuGroupCaption_MenuGroupCaption4Group" + i).className = 'MenuGroupCaption';
	}
	obj.className = "MenuGroupCaption2";
}
// �������ػ��ߴ���߲˵��� ����main.html��

/*
 * �˵����һ����¼�
 */
function prorgt() {
	var mainwidth = ($("#procromain").get(0).scrollWidth);
	var menuwidth = ($("#menudiv").get(0).scrollWidth);
	if (mainwidth - 186 < menuwidth - $("#proccc").get(0).scrollLeft) {
		for ( var i = 0; i < 119; i++) {
			$("#proccc").get(0).scrollLeft = $("#proccc").get(0).scrollLeft + 6;
		}
		$("#slbtn").get(0).style.backgroundImage = "url(templets/template_1/top/roll_1.gif)";
	}
	if (mainwidth - 186 >= menuwidth - $("#proccc").get(0).scrollLeft) {
		$("#srbtn").get(0).style.backgroundImage = "url('#')";
	}
}
/*
 * �˵����󻬶��¼�
 */
function prolft() {
	var mainwidth = ($("#procromain").get(0).scrollWidth);
	var menuwidth = ($("#menudiv").get(0).scrollWidth);
	if ($("#proccc").get(0).scrollLeft > 0) {
		for ( var i = 0; i < 119; i++) {
			$("#proccc").get(0).scrollLeft = $("#proccc").get(0).scrollLeft - 6;
		}
		$("#srbtn").get(0).style.backgroundImage = "url(templets/template_1/top/roll_2.gif)";
	}
	if ($("#proccc").get(0).scrollLeft <= 0) {
		$("#proccc").get(0).scrollLeft = 0;
		$("#slbtn").get(0).style.backgroundImage = "url('#')";
	}
}
//ƽ���Ƴ�,���ಽ�Ƴ�
function moveOutSmooth()
{
	//ʹ��style.left����,Ҫͨ��parseInt()����ȡ��ֵ
	var now_pos = parseInt($('#menuteam').css('left'));
	if(window.movingBack)
	{
	clearTimeout(movingBack);	//ֹͣ���붯��
	}
	//�ж��Ƿ���ȫ�Ƴ�
	if(now_pos < 0)
	{
	//�õ���ǰλ����Ŀ��λ��֮��ľ���dx
	dx = 0-now_pos;
	//���dx��ֵ����ÿ���ƶ����پ���
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
	clearTimeout(window.movingOut);	//�Ƶ�λ��,ֹͣ�Ƴ�
	}
}
//ƽ������,���ಽ����
function moveBackSmooth()
{
	if(window.movingOut)
	{
	clearTimeout(movingOut);		//ֹͣ�Ƴ�����
	}
	//�õ���ǰλ����Ŀ��λ�õľ���dx
	var dx = 224- $('#menuteam').css('pixelLeft');
	//�ж��Ƿ����ص�λ
	if(document.getElementById('menuteam').style.pixelLeft>eval(0-224))
	{	
	//��ݾ���dx�Ĵ�С,����ÿ���ƶ�������
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
	clearTimeout(window.movingBack);	//�Ƶ�λ��,ֹͣ����
	}
}