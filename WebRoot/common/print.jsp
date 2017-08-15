<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/jqueryhead.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>打印</title>
<style media="print" type="text/css">
.noPrint {
	display: none;
}

.pageNext {
	page-break-after: always;
}
</style>
</head>

<body>
	<div id="printDiv" style="width: 100%;">${param.printContent}</div>
	<div class="noPrint">
		<input type="button" value="页面设置" onclick="config()" /> <input
			type="button" value="打印预览" onclick="view()" /> <input type="button"
			value="直接打印" onclick="print()" />
	</div>
	<script type="text/javascript">
	function print(){
		try{
			document.all.WebBrowser.ExecWB(6,6);
		}catch(e){
			window.print();
		}
	}
	function config(){
		try{
			document.all.WebBrowser.ExecWB(8,1);
		}catch(e){
			alert('不支持');
		}
	}
	function view(){
		try{
			document.all.WebBrowser.ExecWB(7,1);
		}catch(e){
			alert('不支持');
		}
	}
	$("#printDiv .noPrint").each(
		function(){			
			$(this).remove();
		}
	);		
	$("#printDiv :text,#printDiv select").each(
	function(){
		//alert(this.value);
		var s=$(this).val();
		$(this).before(s);
		$(this).remove();
	}
	);
	$("#printDiv a").each(
		function(){
			var s=$(this).html();
			//alert(s);		
			$(this).before(s);
			$(this).remove();
		}
	);		
	
	$("#printDiv :hidden,#printDiv :submit,#printDiv :checkbox,#printDiv :image,#printDiv :reset").each(
		function(){
		//alert($(this).val());
		$(this).remove();
		}
	);
	document.body.insertAdjacentHTML("beforeEnd",
	"<object id=\"WebBrowser\" width=0 height=0 \classid=\"clsid:8856F961-340A-11D0-A96B-00C04FD705A2\">");
</script>
</body>
</html>