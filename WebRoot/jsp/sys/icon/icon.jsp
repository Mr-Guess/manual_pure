<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jqueryhead.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片管理</title>

<link rel="stylesheet" type="text/css" href="${ctx}/css/newCrud.css">
<link rel="stylesheet" href="${ctx}/css/animate.css"/>
<script type="text/javascript" src="${ctx}/js/DataGridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/CrudUtil.js"></script>
<script type="text/javascript">
        var grid = null;
        var tree = null;
        var key = null;

       $(document).ready(function () {
   		 grid = new Grid('${ctx}/sysIcon/sysIconAction!list','data_list');
   		 grid.loadGrid();
   		 crud = new Crud({
   				 grid:grid,
   				 addFormObject:$('#curdaddForm'),
   				 searchFormObject:$('#searchForm'),
   				 entityName:'icon',
   				 moduleName:'系统图片', 
   				 dialogCss:{width:'900px',height:'auto'},
   				 url:'${ctx}/sysIcon/sysIconAction'
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
        	var rowId = rowData.iconNo;
        	var url = "";
        		/*<shiro:hasPermission name='${menuId}:view'> 
        	url += "<input type='button' class='btn1' onclick='crud.view(\"" + rowId + "\");' value='查看'/>&nbsp;&nbsp;";
        		</shiro:hasPermission>*/
        		<shiro:hasPermission name='${menuId}:update'>
        	url += "<input type='button' class='btn1' onclick='crud.update(\"" + rowId + "\");' value='修改'/>&nbsp;&nbsp;";
        		</shiro:hasPermission> 
        	return url;
        }
        
        function gridFormatterImage(value,rowData,rowIndex){
        	return '<img src="${ctx}/showIcon.action?menuId='+rowData.iconNo+'" width="20px;" height="20px;"/>'
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
        //重写删除
       function del(){
       	var rows=grid.getSelectNodes();
       	if (rows.length == 0) {
      	        $.messager.show({
      	            title:'提示',
      	            msg:'请选择要删除的行',
      	            timeout:2000,
      	            showType:'slide'
      	        });
       	     return;
       	  } else {
       	    var ids = new Array();
       	    $.messager.confirm('警告', '是否要删除该记录?', function (r) {
   	            if (r) {
   	            	var i = 0;
   	            	for (i=0; i<rows.length; i++) {
   	                    ids.push(rows[i].iconNo);
   	                }
   	            	crud.removeByIds(ids);
   	            }
       	     });
       	    }
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



<div id="toolbar">
	 <div>
	<form id="searchForm" style="margin: 0;">
	
	&nbsp;&nbsp;图标编号:&nbsp;&nbsp;<input type="text" name="icon.iconNo" onkeyup="crud.search();" onblur="crud.search();" />
	&nbsp;&nbsp;图标名称:&nbsp;&nbsp;<input type="text" name="icon.name" onkeyup="crud.search();" onblur="crud.search();" />
	&nbsp;&nbsp;图标类型:&nbsp;&nbsp;<input type="text" name="icon.type" onkeyup="crud.search();" onblur="crud.search();" />
	
<!-- 	&nbsp;&nbsp;<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="crud.search()">查询</a> -->
<!-- 	&nbsp;&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" onclick="crud.clearSearch();">清空</a> -->
	</form>
	</div>
<div class="search_add">
	 <shiro:hasPermission name="${menuId}:add">
		 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add', plain:true" onclick="crud.add();">添加</a>
	 </shiro:hasPermission>
	 <shiro:hasPermission name="${menuId}:delete">
		 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove', plain:true" onclick="del();">删除</a>
	 </shiro:hasPermission>
	 <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel', plain:true" onclick="cleanFunction();">清空</a>
</div>
</div>
<div style="height: 74%">
    <table id="data_list" loadMsg="正在努力拉取数据中..." toolbar="#toolbar" title="图片管理" fitColumns="true">
        <thead>
        <tr>
        	<th align="center" field="dd" width="100" formatter="gridFormatterImage" >图标预览</th>
            <th align="center" field="iconNo" width="150" formatter="gridFormatterLength" >图标编号</th>
            <th align="center" field="name" width="150" formatter="gridFormatterLength" >图标名称</th>
            <th align="center" field="type" width="150" formatter="gridFormatterLength" >图标类型</th>
            <th align="center" field="id" width="120" formatter="gridFormatter" >操作</th>         
        </tr>
        </thead>
    </table>
</div>

	<!-- 添加更新窗口 -->
	<div style="display: none;">
		 <div id="curdaddForm" style="width: 70%; height: 200px;" data-options="iconCls:'icon-save',modal:true, minimizable:true,maximizable:true,collapsible:true,draggable:false">
		<form id="addForm" method="post" enctype="multipart/form-data">
			<input type="hidden" name="icon.iconNo"/>
			<div style="padding: 10px 20px 10px 20px;" align="center" name="addDiv">
				<table class="detailTable" cellpadding="5px" style="font-size:12px;" cellspacing="1"  border="0" bgcolor="#aed0ea" width="95%">
						<tr>
							<td class="table_nemu_new" width="20%">图标:</td>
							<td class="table_con" width="30%">
							<input name="menuIcon" type="hidden" id="icon"/>
							 <div id="localImag"  style="width: 23px; height: 23px;border: 1px solid #9ab8d9;float: left;" >  
						 		<img id="preview"  src="" width="23px" height="23px" />  
		 					 </div>  
							<div  style="float: left;margin-left: 20px;">
							<input type="file" name="img" ID="PicLoad" value="上传" style="width: 250px;margin-top: 5px;"
							onchange="javascript:setImagePreview(this,localImag,preview);"  class="easyui-validatebox" 	data-options="required:true"/></div>
							</td>
						</tr>
						<tr>
							<td class="table_nemu_new" width="20%" width = "20%">图标描述:</td>
							<td class="table_con" width="70%" colspan="3">
							<textarea type="text" class="easyui-validatebox" name="icon.iconDesc" style="width: 80%;height: 50px;"
							data-options="validType:'maxByteLength[100]'" ></textarea>
							</td>
						</tr>
				</table>
			</div>
		</form>
	</div>
	</div>
</body>
</html>