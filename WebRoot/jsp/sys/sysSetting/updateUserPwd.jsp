<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人密码修改</title>

<script type="text/javascript" src="${ctx}/js/GridUtil.js"></script>
<script type="text/javascript" src="${ctx}/js/tree/Tree.js"></script>
<style type="text/css">
.top {
	border: 1px solid #aed0ea;
	background: #f2f5f7 url('images/ui-bg_glass_100_e4f1fb_1x400.png')
		repeat-x;
	line-height: 28px;
	padding-left: 15px;
	padding-right: 15px;
	font-size: 14px;
	font-weight: bold;
	color: #2779aa;
}

.table_title {
	border: 1px solid #cccccc;
	background: #efefef;
	font-size: 14px;
	font-weight: bold;
	color: #2779aa;
	line-height: 28px;
	padding-left: 15px;
}

.table_nemu {
	font-size: 13px;
	font-weight: bold;
	color: #222222;
	border: 0px;
	background: #EAF4FC;
	line-height: 28px;
	padding-left: 15px;
	width: 25%;
	text-align: right;
	padding-right: 20px;
}

.table_nemu span {
	font-size: 15px;
	color: red;
	font-weight: bold;
}

.table_con {
	font-size: 13px;
	border: 0px;
	background: #fff;
	line-height: 28px;
	padding-left: 15px;
	width: 80%
}

.btn1 {
	BORDER: #65b5e4 1px solid;
	PADDING-RIGHT: 15px;
	PADDING-LEFT: 15px;
	FONT-SIZE: 12px;
	FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0,
		StartColorStr=#ffffff, EndColorStr=#d7ebf9);
	CURSOR: hand;
	COLOR: black;
	PADDING-TOP: 3px;
}
</style>
<script type="text/javascript">

	// 验证密码确认输入正确
    $.extend($.fn.validatebox.defaults.rules, {  
          equals: {  
               validator: function(value,param){  
                return value == $(param[0]).val();  
                   },  
           message: '两次输入密码不一样.'  
                }  
         });  
    
	// 验证密码长度
    $.extend($.fn.validatebox.defaults.rules, {  
        minLength: {  
            validator: function(value, param){  
                return value.length >= param[0];  
            },  
            message: '密码长度不能小于8位.'  
        }  
    });  
    
    function updateSubmitForm() {
    	var oldPassword = $('#oldPassword').val();
    	var newPassword = $('#newPassword').val();
    	$.ajax({
    		url:'${ctx}/user/userUpdateAction!updateUserPwd.action?user.plainPassword='+newPassword+'&oldPassword='+oldPassword,
    		success:function (data) {
            	var data = eval('(' + data + ')');
                if (data.operateSuccess) {
                    $.messager.show({
                        title:'成功',
                        msg:data.operateMessage,
                        timeout:2000,
                        showType:'slide'
                    });
                    
                } else {
                    $.messager.show({
                        title:'失败',
                        msg:data.operateMessage,
                        timeout:2000,
                        showType:'slide'
                    });
                }
                $("#oldPassword").val('');
                $("#newPassword").val('');
                $("#validPassword").val('');
            }
    	});
//         $('#updateForm').form({
//             url:'${ctx}/user/userUpdateAction!updateUserPwd.action',
//             success:function (data) {
//             	var data = eval('(' + data + ')');
//                 if (data.operateSuccess) {
//                     $.messager.show({
//                         title:'成功',
//                         msg:data.operateMessage,
//                         timeout:2000,
//                         showType:'slide'
//                     });
                    
//                 } else {
//                     $.messager.show({
//                         title:'失败',
//                         msg:data.operateMessage,
//                         timeout:2000,
//                         showType:'slide'
//                     });
//                 }
//                 $("#oldPassword").val('');
//                 $("#newPassword").val('');
//                 $("#validPassword").val('');
//             }
//         });
//         var oldPassword=$("#oldPassword").val();
//         var validPassword=$("#validPassword").val();
//         var newPassword=$("#newPassword").val();
//         	$('#updateForm').submit();
        }	

        $(document).ready(
        		function(){
        			$.ajax({
                		url:'${ctx}/user/userUpdateAction!getByIdDTO',
                		method:'post',
                		success:function(data){
                			
                			$("#viewAccount").text(data.account);
                			$("#viewUserName").text(data.userName);
                		}
                	});  
        		}      	     
        );
</script>
</head>
<body>
	<!-- 查看窗口 -->

	<fieldset title="个人密码修改">
		<legend class="top">个人密码修改</legend>
		<div id="viewWin" iconCls="icon-save" title="个人密码修改">
			<form style="padding: 10px 20px 10px 20px;" id="updateForm">
				<center>
					<table style="font-size: 12px;" align="center" cellspacing="1"
						border="0" bgcolor="#aed0ea">
						<tr>
							<td class="table_nemu">登录账号</td>
							<td class="table_con"><label id="viewAccount"></label></td>
						</tr>
						<tr>
							<td class="table_nemu">用户名</td>
							<td class="table_con"><label id="viewUserName"></label></td>
						</tr>
						<tr>
							<td class="table_nemu">原始密码</td>
							<td class="table_con"><input type="password"
								class="easyui-validatebox" id="oldPassword" name="oldPassword"
								data-options="required: true" missingMessage="请输入原始密码" /></td>
						</tr>
						<tr>
							<td class="table_nemu">新密码</td>
							<td class="table_con"><input type="password"
								class="easyui-validatebox" id="newPassword"
								name="user.plainPassword"
								data-options="required: true, validType:'minLength[8]'"
								missingMessage="请输入新密码" /></td>
						</tr>
						<tr>
							<td class="table_nemu">确认密码</td>
							<td class="table_con"><input type="password"
								class="easyui-validatebox" id="validPassword"
								required="required" validType="equals['#newPassword']"
								missingMessage="请确认新密码" /></td>
						</tr>
					</table>
					<div style="margin: 10px;">
						<a href="#" class="easyui-linkbutton" icon="icon-save"
							onclick="updateSubmitForm();return false;">保存</a>
					</div>
				</center>
			</form>
		</div>
	</fieldset>

</body>
</html>