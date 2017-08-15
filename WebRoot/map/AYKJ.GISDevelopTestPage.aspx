<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head runat="server">
    <title>科技GIS平台</title>
    <style type="text/css">
    html, body {
	    height: 100%;
	    overflow: auto;
    }
    body {
	    padding: 0;
	    margin: 0;
    }
    #silverlightControlHost {
	    height: 100%;
	    text-align:center;
    }
    </style>
    <script type="text/javascript" src="Silverlight.js"></script>
    <script type="text/javascript">
        //类结构
        function clsAspxWxy(dwdm, wxyid, wxytype, remark, x, y) {
            var sp = new Object;
            sp.dwdm = dwdm;
            sp.wxyid = wxyid;
            sp.wxytype = wxytype;
            sp.remark = remark;
            sp.x = x;
            sp.y = y;
            return sp;
        }

        //响应外部业务的接口调用
        function linkFromBusinessPage(oAction, oStr, oArr, oCls, oArrStr, oArrArr, oArrCls) {
            //对于Array类型的非空参数需要重新处理
            var new_oArr = new Array();
            if (oArr != null) {
                for (var m = 0; m < oArr.length; m++) {
                    new_oArr[m] = oArr[m];
                }
            }

            //对于string的Array重新处理
            var new_oArrStr = new Array();
            if (oArrStr != null) {
                for (var i = 0; i < oArrStr.length; i++) {
                    new_oArrStr[i] = oArrStr[i];
                }
            }

            //对于Array的Array重新处理
            var new_oArrArr = new Array();
            if (oArrArr != null) {
                var tmp_oArr;
                for (var n = 0; n < oArrArr.length; n++) {
                    tmp_oArr = oArrArr[n];
                    //子Array。
                    var i_arr = new Array();
                    if (tmp_oArr != null) {
                        for (var p = 0; p < tmp_oArr.length; p++) {
                            i_arr[p] = tmp_oArr[p];
                        }
                        new_oArrArr[n] = i_arr;
                    }
                }
            }

            //对于class的Array重新处理
            var new_oArrCls = new Array();
            if (oArrCls != null) {
                for (var q = 0; q < oArrCls.length; q++) {
                    new_oArrCls[q] = oArrCls[q];
                }
            }
            //此处循环验证是否存在此控件，防止调用出错。
            var tId = setInterval(function () {

                var control = document.getElementById("zcobj");

                if (control != null) {

                    control.Content.Page.linkFromAspxPage(oAction, oStr, new_oArr, oCls, new_oArrStr, new_oArrArr, new_oArrCls);
                    clearInterval(tId);
                }

            },500);
        }
        
		function locPointFinished(rtnstr) {
            //alert(rtnstr);
        }
		
        function addPointFinished(rtnstr) {
            window.top.loadAccidentObject();
        }

        function QueryFinished(rtnstr) {
            //alert(rtnstr);
            //parent.queryrtn(rtnstr);
            window.top.searchCallBack(rtnstr);
        }

        //20120723zc:响应其他业务页面的调用并显示返回值(s1对应配置文件的"menu name",s2表示要传入的值)
        function linkGetFromBusinessPage(s1, s2) {
            alert("GetFromBusinessPage:" + "调用：" + s1 + "。传入值：" + s2);

            var control = document.getElementById("zcobj");
            var rs = control.Content.Page.LinkGetDataFromThisAspxPage(s1, s2);

            alert("GisPlatfromAspxPage_getReturn: " + rs);
        }

        //20120726zc:调用截图功能
        function fnCapture(imgName) {

            //alert(112);
            var obj_window = window.open('http://www.baidu.com', '_blank');
            obj_window.opener = window;
            obj_window.focus();
            //var xx = document.getElementById("tt");
            //xx.Capture(imgName);
            //alert('截图已保存到 ' + imgName.replace('.bmp', '.jpg'));
            //var control = document.getElementById("zcobj");
            //control.Content.Page.LinkSaveImg();
        }

        //20121217:点击气泡打开新页面
        function showNewPage(url) {
            alert(132);
        }

        function DownLoadShp(url) {
            var obj_window = window.open(url, '_blank');
//            obj_window.opener = window;
//            obj_window.focus();
        }
    </script>
    <script type="text/javascript">
        window.onload = function () {
            var url = window.location.href;
            var newurl = url.split("#");
            if (newurl.length == 2) {
                window.location.href = newurl[0];
            }
        }
        function onSilverlightError(sender, args) {
            var appSource = "";
            if (sender != null && sender != 0) {
              appSource = sender.getHost().Source;
            }
            
            var errorType = args.ErrorType;
            var iErrorCode = args.ErrorCode;

            if (errorType == "ImageError" || errorType == "MediaError") {
              return;
            }

            var errMsg = "Silverlight 应用程序中未处理的错误 " +  appSource + "\n" ;

            errMsg += "代码: "+ iErrorCode + "    \n";
            errMsg += "类别: " + errorType + "       \n";
            errMsg += "消息: " + args.ErrorMessage + "     \n";

            if (errorType == "ParserError") {
                errMsg += "文件: " + args.xamlFile + "     \n";
                errMsg += "行: " + args.lineNumber + "     \n";
                errMsg += "位置: " + args.charPosition + "     \n";
            }
            else if (errorType == "RuntimeError") {           
                if (args.lineNumber != 0) {
                    errMsg += "行: " + args.lineNumber + "     \n";
                    errMsg += "位置: " +  args.charPosition + "     \n";
                }
                errMsg += "方法名称: " + args.methodName + "     \n";
            }

			//引发新错误(errMsg);
        }
    </script>
</head>
<body  style="overflow:hidden;">
    <form id="form1" runat="server" style="height:100%;">
    <div id="silverlightControlHost">
        <object data="data:application/x-silverlight-2," type="application/x-silverlight-2" width="100%" height="100%" id="zcobj">
          <param name="enableHtmlAccess" value="true" />
		  <%--<param name="source" value="ClientBin/AYKJ.GISDevelop.xap"/>--%>
          <param name="source" value="http://10.0.0.119/weihai_gis/ClientBin/AYKJ.GISDevelop.xap"/>
		  <param name="onError" value="onSilverlightError" />
		  <param name="background" value="white" />
		  <param name="minRuntimeVersion" value="4.0.50826.0" />
		  <param name="autoUpgrade" value="false" />
          <param name="windowless" value="true" />
		  <a href="http://go.microsoft.com/fwlink/?LinkID=149156&v=4.0.50826.0" style="text-decoration:none">
 			  <img src="http://go.microsoft.com/fwlink/?LinkId=161376" alt="获取 Microsoft Silverlight" style="border-style:none"/>
		  </a>
	    </object>        
        <iframe id="_sl_historyFrame" style="visibility:hidden;height:0px;width:0px;border:0px"></iframe>        
        <!--<object classid="clsid:AB15B9A5-1573-448A-870C-2F60916CECD7" codebase="TestCab.CAB" style="visibility:hidden;"  width="0px"  height="0px" id="tt" name="tt"/>-->
    </div>
    </form>
</body>
</html>