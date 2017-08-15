<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jqueryhead.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择图片</title>
<link rel="stylesheet" href="${ctx}/css/animate.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/css/newCrud.css">
<script type="text/javascript" src="${ctx}/js/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/CrudUtil.js"></script>

<script type="text/javascript">
	var rows=21;//每页显示数量 默认10条
	var currPage=1;	//当前页
	var queryPage=0;//总页数
	$(function(){
		loadImgs();//加载图片
		$('#addForm').dialog({
				title:'添加图标',
				modal:true,
				draggable:false,
				maximized:true,
				width:300,
				height:300,
				buttons:[
				         {
				        	 text:'保存',
				        	 iconCls:'icon-save',
				        	 handler:function(){
				        		 save();
				        	 }
				         },
				         {
					text:'取消',
					iconCls:'icon-cancel',
					handler:function(){
						$('#addForm').dialog('close');
					}
				}]
			});
		$('#addForm').dialog('close');
	});	
	function loadImgs(type){
		$.loding();
		$('#imgList').html('');
		$.ajax({
			url:'${ctx}/sysIcon/sysIconAction!list',
			data:{'rows':rows,'page':currPage},
			type:'post',
			success:function(data){
				data=eval('('+data+')');
				calcQueryPage(data.total);
				$(data.rows).each(function(){
					var iconNo=this.iconNo;
					var li=$('<li>');
					var img=$('<img>').attr('src','showIcon.action?menuId='+this.iconNo).attr('title',this.name).attr('alt',iconNo);
					$('#imgList').append($(li).html(img));
					$(li).click(function(){
						window.parent.getIconNo(iconNo);
					});
				});
				$.loded();
				butDisable();
				
				if(type=="next"){
					$("#imgList").addClass('animated bounceInRight').delay(2000).queue(function(next){
					    $(this).removeClass('animated bounceInRight');
					    next();
					});	
				}else{
					$("#imgList").addClass('animated bounceInLeft').delay(2000).queue(function(next){
					    $(this).removeClass('animated bounceInLeft');
					    next();
					});	
				}
			}
		});
		
	}
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
	//计算总页数
	function calcQueryPage(total){
		queryPage=DivUp(total,rows);
	}
	//下一页
	function next(){
		if(currPage<queryPage){
			currPage++;
			loadImgs('next');
		}
	}
	//上一页
	function upPage(){
		if(currPage>1){
			currPage--;
			loadImgs('up');
		}
	}
	
	//按钮控制
	function butDisable(){
		if(currPage>=queryPage){
			$('#next').linkbutton('disable');
		}else{
			$('#next').linkbutton('enable');
		}
		if(currPage<=1){
			$('#upPage').linkbutton('disable');
		}else{
			$('#upPage').linkbutton('enable');
		}
	}
	function addOpen(){
		$('#preview').attr('src','');
		$('#txt').text('');
		$('#PicLoad').val('');
		$('#addForm').dialog("open");
	}
	 var allSuffix='jpg,gif,bmp,png,jpeg';//上传格式
     function setImagePreview(docObj,localImagId,imgObjPreview)   
     {  
     	var suffix=docObj.value.substring(docObj.value.lastIndexOf('.')+1,docObj.value.length);//获取文件类型
     	$('#myfile').val(docObj.value);
     	suffix=suffix.toLowerCase();	//转换成小写
     	if(-1==allSuffix.indexOf(suffix)){
     		$.messager.alert('警告','只能上传'+allSuffix+'格式文件');
     		docObj.value="";
     		return;
     	}
     	$('#icon').val(docObj.value);
         if(docObj.files && docObj.files[0])  
         {  
             //火狐下，直接设img属性  
             imgObjPreview.style.display = 'block';  
             imgObjPreview.style.width = '23px';  
             imgObjPreview.style.height = '23px';                      
             //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式    
             imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);  
         }  
         else  
         {  
             //IE下，使用滤镜  
             docObj.select();  
             var imgSrc = document.selection.createRange().text;  
             //必须设置初始大小  
             localImagId.style.width = "23px";  
             localImagId.style.height = "23px";  
               
             //图片异常的捕捉，防止用户修改后缀来伪造图片  
             try  
             {  
                 localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";  
                 localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;  
              }  
              catch(e)  
              {  
                 $.messager.alert("警告","您上传的图片格式不正确，请重新选择!");  
                 return false;  
               }                            
               imgObjPreview.style.display = 'none';  
               document.selection.empty();  
         }  
         return true;  
     } 
	 function save(){
		 if($('#PicLoad').val().length==0){
			 $.messager.alert("警告","请选择图标");
			 return;
		 }
		 $('#formIcon').form('submit',{
             url: '${ctx}/sysIcon/sysIconAction!myAdd',
             success:function (data) {
            	 data=eval('('+data+')');
            	 if(data.operateSuccess){
            		 $.messager.show({
                         title:'添加成功',
                         msg:data.operateMessage,
                         timeout:2000,
                         showType:'slide'
                     });
            		 $('#addForm').dialog("close");
            		 loadImgs();
            		 window.parent.getIconNo(data.uuid);
            	 }else{
            		 $.messager.show({
                         title:'添加失败',
                         msg:data.operateMessage,
                         timeout:2000,
                         showType:'slide'
                     });
            	 }
             }
		 });
	 }
</script>
<style>
img{
	width:30px;
	height:30px;
	cursor: pointer;
	margin-top: 15px;
}
li{
	width:50px;
	height:50px;
	float: left;
	text-align: center;
	margin-left: 5px;
	cursor: pointer;
	list-style: none;
}
#page{
	width:100px;
	height: 100px;
	position: absolute;
	z-index:9000;
	right: 0px;
	top:40%;
}
.page{
	width: 100px;
	text-align: center;
	
}
#images{
	width: 90%;
	height: 90%;
	text-align: center;
	
}
#imgList{
	height: 90%;
	width: 80%;
}
body{
	background-color: #D0F2F4;
}
</style>
</head>
<body >
<center>
	<div id="images">
		<ul id="imgList">
		</ul>
	</div>
	
</center>
<div id="page" >
	<a href="javascript:void(0);" class="easyui-linkbutton" id="upPage" onclick="upPage();" style="float: left;">上&nbsp;&nbsp;&nbsp;页</a>
	<a style="float: left;width: 100px;height: 10px;">&nbsp;&nbsp;</a>
	<a href="javascript:void(0);" class="easyui-linkbutton " id="next" onclick="next();"style="float: left;">下&nbsp;&nbsp;&nbsp;页</a>
	<a style="float: left;width: 100px;height: 10px;">&nbsp;&nbsp;</a>
	<a href="javascript:void(0);" class="easyui-linkbutton "  onclick="addOpen();"style="float: left;">添&nbsp;&nbsp;&nbsp;加</a>
</div>
	<div id="addForm">
		<form method="post" enctype="multipart/form-data" id="formIcon">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
						<tr>
							<td class="table_nemu_new" width="20%">图片:</td>
							<td class="table_con" width="30%">
							<input name="menuIcon" type="hidden" id="icon"/>
							 <div id="localImag"  style="width: 23px; height: 23px;border: 1px solid #9ab8d9;float: left;" >  
						 		<img id="preview"  src="" width="23px" height="23px" style="margin: 0px;border:0px;" />  
		 					 </div>  
							<div  style="float: left;margin-left: 20px;">
							<input type="file" name="img" ID="PicLoad" value="上传" style="width: 250px;margin-top: 5px;"
							onchange="javascript:setImagePreview(this,localImag,preview);" data-options="required:true"/></div>
							</td>
						</tr>
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">图片描述:</td>
							<td class="table_con" width="70%" colspan="3">
							<textarea id="txt" type="text" class="easyui-validatebox" name="icon.iconDesc" style="width: 80%;height: 50px;"
							data-options="validType:'maxByteLength[100]'" ></textarea>
							</td>
						</tr>
				</table>
		</form>
	</div>
</body>
</html>