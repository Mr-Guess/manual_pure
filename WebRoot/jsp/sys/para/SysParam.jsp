<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/jqueryhead.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统参数设置</title>
<!--引入JS -->
<script type="text/javascript" src="${ctx }/js/GridUtil.js"></script>
<script type="text/javascript" src="${ctx }/js/validator.js"></script>
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
	border: 0px solid #aed0ea;
	color: #222222;
	background: #EAF4FC;
	line-height: 28px;
	padding-left: 15px;
	width: 25%;
	text-align: right;
	padding-right: 20px;
}

.table_nemu span {
	font-size: 13px;
	color: #222222;
}

.table_nemu2 {
	font-size: 13px;
	font-weight: bold;
	border: 0px solid #aed0ea;
	color: #222222;
	background: #f4f7f8;
	line-height: 28px;
	padding-left: 15px;
	width: 15%;
	text-align: right;
	padding-right: 20px;
}

.table_nemu2 span {
	font-size: 13px;
	color: #222222;
}

.table_nemu1 {
	font-size: 13px;
	font-weight: bold;
	border: 0px solid #aed0ea;
	color: #222222;
	background: #f4f7f8;
	line-height: 28px;
	padding-left: 15px;
	width: 15%;
	text-align: right;
	padding-right: 20px;
}

.table_nemu1 span {
	font-size: 13px;
	color: #222222;
}

.table_con {
	font-size: 13px;
	border: 0px solid #aed0ea;
	background: #fff;
	line-height: 28px;
	padding-left: 15px;
}

.table_con_2 {
	font-size: 13px;
	border: 0px solid #aed0ea;
	background: #fff;
	line-height: 28px;
	padding-left: 15px;
}

.table_con_3 {
	font-size: 13px;
	border: 0px solid #aed0ea;
	background: #fff;
	line-height: 28px;
	padding-left: 15px;
}

.table_con_4 {
	font-size: 13px;
	border: 0px solid #aed0ea;
	background: #fff;
	line-height: 28px;
	padding-left: 15px;
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

element.style {
	
}
</style>
<script type="text/javascript">

	/*
	这里直接使用EASYUI-NUMBERBOX 强制只能输入数字
	$(function (){
	    //设置text输入框需要验证输入类型
	    $('input[type=text]').validatebox();
	})
	 */
	 $('#login_limit_times').validatebox();
	function updateSubmitForm() {
		$('#updateForm').form({
							url :"${ctx}/sysParameter/sysParameterAction!updateSysParameter.action",
							method: 'POST',
							success : function(data) {
								var data = eval('(' + data + ')');
								if (data.operateSuccess) {
									$.messager.show({
										title : '成功',
										msg : data.operateMessage,
										timeout : 2000,
										showType : 'fade'
									});
								} else {
									$.messager.show({
										title : '失败',
										msg : data.operateMessage,
										timeout : 2000,
										showType : "fade"
									});
								}
							}
						});
		$('#updateForm').submit();
	}

	$(document).ready(function() {
		$.ajax({
			url : '${ctx}/sysParameter/sysParameterAction!getAllObject',
			method : 'POST',
			success: function(data) {
				var dataObj = eval('(' + data + ')');
				$.each(dataObj, function(index, element) {
					if(element.id == 1) {
						$('#login_limit_times').numberbox('setValue', element.paraValue);
					} else {
						if(element.id == 2) {
							$('#login_limit_period').numberbox('setValue', element.paraValue);
						} else {
							if(element.id == 3) {
								$('#login_limit_switch').combobox('setValue', element.paraValue);
									if (element.paraValue == 1) {
										$('#login_limit_switch').combobox('setText', '是');
									} else {
										$('#login_limit_switch').combobox('setText', '否');
										$('.table_nemu1').hide();
										$(".table_con_3").hide();
									}
							} else {
								if (element.id == 4) {
									$('#login_onlyone_switch').combobox('setValue', element.paraValue);
										if (element.paraValue == 1) {
											$('#login_onlyone_switch').combobox('setText', '是');
										} else {
											$('#login_onlyone_switch').combobox('setText', '否');
										}
								} else {
									if (element.id == 5) {
										$('#login_authcode_digit').combobox('setValue', element.paraValue);
											if(element.paraValue == 4) {
												$('#login_authcode_digit').combobox('setText', '4');
											} else {
												if (element.paraValue == 5) {
												$('#login_authcode_digit').combobox('setText', '5');
												} else {
													if (element.paraValue == 6) {
														$('#login_authcode_digit').combobox('setText', '6');
													}
												}
											}
									} else {
										if (element.id == 6) {
											$('#login_authcode_type').combobox('setValue', element.paraValue);
											if (element.paraValue == 1) {
												$('#login_authcode_type').combobox('setText', '字母');
											} else {
												if (element.paraValue == 0) {
													$('#login_authcode_type').combobox('setText', '数字');
												} else {
													if (element.paraValue == 2) {
														$ ('#login_authcode_type').combobox('setText', '混合');
													}
												}
											}
											
										} else {
											if (element.id == 7) {
												$('#login_authcode_switch').combobox('setValue', element.paraValue);
												if ( element.paraValue == 1) {
													$('#login_authcode_switch').combobox('setText', '是');
												} else {
													$('#login_authcode_switch').combobox('setText', '否');
													$('.table_nemu2').hide();
													$(".table_con_4").hide();
												}
											}
										}
									}
								}
							}
						}
					}
				});
			}
		});
	});
	function onSelected(node){
		if(node.val == 0){
			$(".table_nemu1").hide();
			$(".table_con_3").hide();
		}else{
			$(".table_nemu1").show();
			$(".table_con_3").show();
		}
	}
	
	function onSelectedLogin(data){
		if(data.val == 0) {
			$(".table_nemu2").hide();
			$(".table_con_4").hide();
		} else {
			$(".table_nemu2").show();
			$(".table_con_4").show();
		}
	}
	
	
	
</script>
</head>
<body>
	<fieldset title="参数设置">
		<legend class="top">系统参数设置</legend>
		<div id="viewWin" iconCls="icon-save" title="系统参数设置">
			<form style="padding: 10px 20px 10px 20px" id="updateForm"
				method="POST">
				<table style="font-size: 12px;" align="center" cellspacing="1"
					border="0" bgcolor="#aed0ea">
					<tr>
						<td class="table_nemu">是否允许账号多处同时登陆:</td>
						<td colspan="5" class="table_con_2"><input
							id="login_onlyone_switch" name="paraDTO.login_onlyone_switch"
							class="easyui-combobox"
							data-options="panelHeight:'auto',editable:false, valueField:'val',textField:'text', data:[{val:'1',text:'是'},{val:'0', text:'否'}]"
							style="width: 100px;" /></td>
					</tr>
					<tr>
						<td class="table_nemu">是否限制用户登录次数:</td>
						<td class="table_con"><input id="login_limit_switch"
							name="paraDTO.login_limit_switch" class="easyui-combobox"
							data-options="panelHeight:'auto',editable:false, onSelect:onSelected, valueField:'val',textField:'text', data:[{val:'1',text:'是'},{val:'0', text:'否'}]"
							style="width: 100px; " /></td>
						<td class="table_nemu1"><span id="limit_times">限制次数:</span></td>
						<td class="table_con_3"><input id="login_limit_times"
							name="paraDTO.login_limit_times" type="text"
							class="easyui-numberbox" validtype="positiveInt"
							style="width: 100px" /></td>
						<td class="table_nemu1"><span id="limit_period">限制时长:</span></td>
						<td class="table_con_3"><input id="login_limit_period"
							name="paraDTO.login_limit_period" class="easyui-numberbox"
							data-options="panelHeight:'auto',max:100000000000000000000000000000"
							style="width: 100px" /><span id="fenzhong">分钟</span></td>
					</tr>
					<tr>
						<td class="table_nemu">是否使用登录验证码:</td>
						<td class="table_con_2"><input id="login_authcode_switch"
							name="paraDTO.login_authcode_switch" class="easyui-combobox"
							data-options="panelHeight:'auto',valueField:'val',textField:'text', onSelect:onSelectedLogin, editable:false, data:[{val:'1',text:'是'},{val:'0', text:'否'}]"
							style="width: 100px;" /></td>
						<td class="table_nemu2"><span id="authcode_digit">验证码位数:</span></td>
						<td class="table_con_4"><input id="login_authcode_digit"
							name="paraDTO.login_authcode_digit" class="easyui-combobox"
							data-options="panelHeight:'auto',valueField:'val',textField:'text', editable:false, data:[{val:'4',text:'4'},{val: '5', text:'5'},{val:'6', text:'6'}]"
							style="width: 100px;" /></td>
						<td class="table_nemu2"><span id="authcode_type">验证码形式:</span></td>
						<td class="table_con_4"><input id="login_authcode_type"
							name="paraDTO.login_authcode_type" class="easyui-combobox"
							data-options="panelHeight:'auto',valueField:'val',textField:'text', editable:false, data:[{val:'0', text:'数字'},{val:'1', text:'字母'},{val:'2', text:'混合'}]"
							style="width: 100px;" /></td>
					</tr>
				</table>
				<center style="margin: 10px;">
					<a href="#" class="easyui-linkbutton" icon="icon-save"
						onclick="updateSubmitForm();return false;">保存</a>
				</center>
			</form>
		</div>
	</fieldset>

	</div>
</body>
</html>