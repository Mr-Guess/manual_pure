<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传</title>
<script type="text/javascript">

	var uploadItems = new Array();
	
	/**判断文件是否上传状态位 0:未上传,1:正在上传,2:上传完成*/
	var uploadStatus = 0;
	
	var timeout;
	function formClick(){
		if($("#fileInput").val()){
			if(uploadStatus==1){
				$.messager.alert("警告","请等待上传完成！");
			}
			$("#form").form({
				type:"json",
				success:function(data){
					uploadStatus = 2;
					$("#reset").trigger("click");
					getFileList();
					$.messager.alert("提示",data);
					$("#form").show();
					
				}
			});
			$("#form").submit();
			process();
			uploadStatus==1;
			$("#form").hide();
		}else{
			$.messager.alert("警告","请选择上传的文件！");
		}
	}
	
	function downloadClick(fileId){
		$.messager.confirm("警告","是否要下载文件？",function(r){
			if(r){
				if(Boolean(timeout)){
					clearTimeout(timeout);
				}
				timeout = setTimeout(function(){
				$("#form"+fileId).form({
					url:"${ctx}/fileUpload/download?fileId="+fileId,
					success: function(data){
						if(data){
							$.messager.alert("提示",data);
						}
					}
				});
				$("#form"+fileId).submit();
				},300);
			}
		});
	}
	
	function removeClick(fileId,fileName){
		$.messager.confirm("警告","是否要删除文件:"+fileName,function(r){
			if(!r)
				return;
			$.post("${ctx}/fileUpload/removeFile",{"fileId":fileId},
					function(data){
				getFileList();
				$.messager.alert("提示",data);
			});
		});
	}
	
	/** 获得已经上传的所有文件*/
	function getFileList(){
		$.post("${ctx}/fileUpload/fileList",{relationId:"${param.relationId}"},
				function(data){
			var fileList = eval("("+data+")");
			var fileListDiv = $("#fileList");
			fileListDiv.html("");
			
			//遍历对象，获得文件列表
			for(var i in fileList){
				var file = fileList[i];
				var fileDiv = $("<div>").appendTo(fileListDiv);
				fileDiv.css({
					"width":"160px",
					"height":"100px",
					"float":"left",
					"background":"#fafafa",
					"margin":"20px",
					"border":"2px solid #ccc",
					"overflow-y":"hidden",
					"align":"center"
				});
				fileDiv.append("<div style='height:30px;align:right;'><a href='javascript:void(0);' onclick='removeClick(\""+file.id+"\",\""+file.fileName+"\")'><img src='${ctx}/images/delete.png'></img></a></div>");
				
				var fileForm = $("#downloadForm").clone();
				fileForm.attr("id","form"+file.id);
				fileForm.appendTo(fileDiv);
				var a = $("<a href='javascript:void(0)' onclick='downloadClick(\""+file.id+"\")' >"+file.fileName+"</a>");
				a.appendTo(fileDiv);				
			}
		});
	}
	
	/**
	 *	
	 *
	 */
	function process(){
		if(uploadStatus == 2){
			$("#progress").progressbar("setValue",100);
			uploadStatus = 0;
			return;
		}
		$.post("${ctx}/fileUpload/process",{ran:Math.random(100)+new Date()},
				function(data){
					$("#progress").progressbar("setValue",data);
					setTimeout(function(){
						process();
					},300);
		});
	}
	
	$(document).ready(function(){
		getFileList();
	});
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" doSize="false"
		style="width: 100%; height: 100%;">
		<div data-options="region:'center',title:'文件列表',iconCls:'icon-save'">
			<div id="fileList" style="width: 100%; margin: 0 auto;"></div>
			<div id="fileDiv"
				style="display: none; width: 160px; height: 100px; float: left; background: #fafafa; margin: 20px; border: 2px solid #ccc">
				<form id="downloadForm" method="post"></form>
			</div>
		</div>
	</div>

</body>
</html>