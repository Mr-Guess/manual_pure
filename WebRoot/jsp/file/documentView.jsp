<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">	
    <head> 
        <title></title>         
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
        <style type="text/css" media="screen"> 
			html, body	{ height:100%; }
			body { margin:0; padding:0; overflow:auto; }   
/* 			#flashContent{display: none;} */
        </style> 
		<script type="text/javascript" src="js/swfobject/swfobject.js"></script>
		<script type="text/javascript" src="js/flexpaper_flash.js"></script>
		<script type="text/javascript">
		$(function(){
				//var fileUrl="${param.url}";	//原附件路径
				var fileUrl="E:/upload/execl.xls";
				var fileSuffix=fileUrl.substring(fileUrl.lastIndexOf(".")+1,fileUrl.length);	//获取文件名后缀
				var unConvers="exe,jar,zip";	//不能转换(预览)的类型
				var jgpConvers="jgp,png";	//可以直接预览的
				if(unConvers.indexOf(fileSuffix)!=-1){
					alert("不支持"+fileSuffix+"格式预览");
				}else{
					convert(fileUrl,jgpConvers.indexOf(fileSuffix)!=-1);
				}
				
		});
		
		function loadJpg(url){
			$("#flashContent").append("<img src="+url+"/>");
		}
		
		function convert(fileUrl,bit){
			$.loding();
			$.ajax({
				url:'${ctx}/filePreview/filePreviewAction!filePreview',
				type:'post',
				data:{"url":fileUrl},
				success:function(data){
					var url="${ctx}/upload/"+data;
					if(bit){
						loadJpg(url);	//直接预览 不需要转换
					}else{
						loadDocument(url);	//设置要预览的swf 只能使用相对路径
					}
					$.loded();
				}
			});
		}
		function loadDocument(url){
			var swfVersionStr = "9.0";	//版本
            var xiSwfUrlStr = "playerProductInstall.swf";//播放器
            var flashvars={};
            flashvars = { 
                  SwfFile : escape('../../upload/20140516140802.swf'),
				  Scale : 0.8,	//Scale是0-1之间的数，表示显示的放大参数  
				  ZoomTransition : "easeOut",
				  ZoomTime : 0.5,
  				  ZoomInterval : 0.1,
  				  FitPageOnLoad : false,
  				  FitWidthOnLoad : true,
  				  //PrintEnabled : false,
  				  FullScreenAsMaxWindow : false,
  				  ProgressiveLoading : true,
  				  
  				 // PrintToolsVisible : false,
  				  ViewModeToolsVisible : true,
  				  ZoomToolsVisible : true,
  				  FullScreenVisible : true,
  				  NavToolsVisible : true,
  				  CursorToolsVisible : true,
  				  SearchToolsVisible : true,
  				  localeChain: "en_US"
				  };
			 var params = {
				
			    };
            params.quality = "high";
            params.bgcolor = "#999999";
            params.allowscriptaccess = "sameDomain";
            params.allowfullscreen = "true";
            var attributes = {};
            attributes.id = "FlexPaperViewer";
            attributes.name = "FlexPaperViewer";
            swfobject.embedSWF(
                "FlexPaperViewer.swf", "flashContent", 
                "900", "650",
                swfVersionStr, xiSwfUrlStr, 
                flashvars, params, attributes);
			swfobject.createCSS("#flashContent", "display:block;text-align:left;");
		}
        </script> 
    </head> 
    <body>
    <center>
    	<div>
	        <div id="flashContent" >
	        	
	        </div>
	        <div id="errNoDocument" style="padding-top:10px;">
	        </div> 
        </div>
          <a href="/upload/20140516140802.swf">dddddddddddd</a>
        </center>
   </body> 
</html> 