$.extend($.fn.validatebox.defaults.rules, {
	alpha : {
		validator : function(value, param) {
			if (value) {
				return /^[a-zA-Z\u00A1-\uFFFF]*$/.test(value);
			} else {
				return true;
			}
		},
		message : '只能输入字母.'
	},
	alphanum : {
		validator : function(value, param) {
			if (value) {
				return /^([a-zA-Z\u00A1-\uFFFF0-9])*$/.test(value);
			} else {
				return true;
			}
		},
		message : '只能输入字母和数字.'
	},
	positiveInt : {
		validator : function(value, param) {
			if (value) {
				return /^[0-9]*[1-9][0-9]*$/.test(value);
			} else {
				return true;
			}
		},
		message : '只能输入正整数.'
	},
	decimal : {
		validator : function(value, param) {
			if (value) {
				return /^[0-9]+([.]{1}[0-9]+){0,1}$/.test(value);
			} else {
				return true;
			}
		},
		message : '请输入整数或小数.'
	},
	
	systemInt : {
		validator : function(value, param) {
			if (value) {
				return /^([0-9]|([1-9][0-9]{1,7}))$/.test(value);
			} else {
				return true;
			}
		},
		message : '请输入9位以内的正整数.'
	},
	chsNoSymbol : {
		validator : function(value, param) {
			return /^[\u4E00-\u9FA5]+$/.test(value);
		},
		message : '只能输入中文.'
	},
	chs : {
		validator : function(value, param) {
			return /^[\u0391-\uFFE5]+$/.test(value);
		},
		message : '请输入汉字.'
	},
	zip : {
		validator : function(value, param) {
			return /^[1-9]\d{5}$/.test(value);
		},
		message : '邮政编码不存在.'
	},
	qq : {
		validator : function(value, param) {
			return /^[1-9]\d{4,10}$/.test(value);
		},
		message : 'QQ号码不正确.'
	},
	NO : {
		validator : function(value, param) {
			var reg0 = /^13\d{9}$/;
            var reg1 = /^15\d{9}$/;
            var reg2 = /^18\d{9}$/;
            var reg3 = /^0\d{9,11}$/;
            var reg4 = /^0\d{2,3}\-\d{7,8}$/;
            var my = false;
            if (reg0.test(value)) my = true;
            if (reg1.test(value)) my = true;
            if (reg2.test(value)) my = true;
            if (reg3.test(value)) my = true;
            if (reg4.test(value)) my = true;
            return my;
//			return /^\d{1}[\-\d]*$/.test(value);
		},
		message : '请输入正确格式的号码.'
	},
	phoneNumber : {
		validator : function(value, param) {
			return /^((((\(\d{2,3}\))|(\d{3}\-))?1(3|5|8)\d{9})|(((0[1-9]{2,3}\-)?\d{7,8})(\-[0-9]{1,5})?))$/.test(value);
		},
		message : '请输入正确的手机或者电话号码.'
	},
	mobile : {
		validator : function(value, param) {
			return /^((\(\d{2,3}\))|(\d{3}\-))?1(3|5|8)\d{9}$/.test(value);
		},
		message : '请输入正确的手机号码.'
	},
	phone :{
		validator : function(value, param) {
			return /^((0[1-9]{2})\-)?(\d{8})$/.test(value);
		},
		message : '请输入正确的电话号码'
	},
	loginName : {
		validator : function(value, param) {
			return /^[\u0391-\uFFE5\w]+$/.test(value);
		},
		message : '登录名称只允许汉字、英文字母、数字及下划线。.'
	},
	zxy : {
		validator : function(value, param) {
			return /^[\u0391-\uFFE5\w]+$/.test(value);
		},
		message : '请输入汉字、英文字母、数字及下划线。.'
	},
	safepass : {
		validator : function(value, param) {
			return safePassword(value);
		},
		message : '密码由字母和数字组成，至少6位.'
	},
	equalTo : {
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '两次输入的字符不一至'
	},
	number : {
		validator : function(value, param) {
			return /^\d+$/.test(value);
		},
		message : '请输入数字'
	},
	codechick : {
		validator : function(value, param) {
			if (value) {
				return /^\w+$/.test(value);
			} else {
				return true;
			}
		},
		message : '请输入字母数字和下划线.'
	},
	idcard : {
		validator : function(value, param) {
			return idCard(value);
		},
		message : '请输入正确的身份证号码'
	}
	,likeJsonStr : {
		validator : function(value, param) {
			return /^(([\w]+):([\w]+))(,([\w]+):([\w]+))*$/.test(value);
		},
		message : '请输入正确的格式：id:id,type:typeId'
	},
	widthStr : {
		validator : function(value, param) {
			return /^((([1-9]{1})([0-9]?)|100)\%|([1-9]{1}[0-9]*))$/.test(value);
		},
		message : '请输入正确的格式：10或者10%'
	},
	corpCode:{
		validator : function(value, param){
			return /^\d{1,8}\-\d{1}\-\d{4}$/.test(value);
		},
		message: "请输入正确的格式"
	},
	fax:{
		validator : function(value,param){
			return /^([0\+]{1}\d{2,3}(\-)?)?(0\d{2,3}(\-)?)(\d{7,8})(\-(\d{3,}))?$/.test(value);
		},
		message : "请输入正确格式的传真号码"
	},
	post:{
		validator : function(value,param){
			return /^[1-9]{1}[0-9]{5}$/.test(value);
		},
		message : "请输入正确的邮编"
	},
	currentDate : {
		validator : function(value, param){
			var a = value.split("-");
			var d = a.join("/");
			var d1 = new Date(d);
			var d2 = new Date();
			
			return d1<d2;
		},
		message : "大于当期日期！"
	},
	maxByteLength : {
		validator : function(value, param) {
			if (value) {
				var length = value.length; 
					for(var i = 0; i < value.length; i++){ 
						if(value.charCodeAt(i) > 127){ 
							length++; 
					} 
				}
				return length<=param;
			} else {
				return true;
			}
		},
		message : '输入内容的长度太长'
	},
	maxLength : {
		validator : function(value, param){
			return value.length<=param;
		},
		message : "输入内容长度不能超过{0}"
	},
	email : {
		validator : function(value, param){
			var em = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
			var flag = em.test(value);
			return flag&&param>value.length;
		},
		message : "输入的邮箱过长或者格式不正确"
	},
	pusheng : {
		validator : function(value, param){
			var em = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
			var flag = em.test(value);
			return flag&&param>value.length;
		},
		message : "输入的邮箱过长或者格式不正确"
	}
});

/* 密码由字母和数字组成，至少6位 */
var safePassword = function(value) {
	return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/
			.test(value));
}

var idCard = function(value) {
	if (value.length == 18 && 18 != value.length)
		return false;
	var number = value.toLowerCase();
	var d, sum = 0, v = '10x98765432', w = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7,
			9, 10, 5, 8, 4, 2 ], a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';
	var re = number
			.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);
	if (re == null || a.indexOf(re[1]) < 0)
		return false;
	if (re[2].length == 9) {
		number = number.substr(0, 6) + '19' + number.substr(6);
		d = [ '19' + re[4], re[5], re[6] ].join('-');
	} else
		d = [ re[9], re[10], re[11] ].join('-');
	if (!isDateTime.call(d, 'yyyy-MM-dd'))
		return false;
	for ( var i = 0; i < 17; i++)
		sum += number.charAt(i) * w[i];
	return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));
}

var isDateTime = function(format, reObj) {
	format = format || 'yyyy-MM-dd';
	var input = this, o = {}, d = new Date();
	var f1 = format.split(/[^a-z]+/gi), f2 = input.split(/\D+/g), f3 = format
			.split(/[a-z]+/gi), f4 = input.split(/\d+/g);
	var len = f1.length, len1 = f3.length;
	if (len != f2.length || len1 != f4.length)
		return false;
	for ( var i = 0; i < len1; i++)
		if (f3[i] != f4[i])
			return false;
	for ( var i = 0; i < len; i++)
		o[f1[i]] = f2[i];
	o.yyyy = s(o.yyyy, o.yy, d.getFullYear(), 9999, 4);
	o.MM = s(o.MM, o.M, d.getMonth() + 1, 12);
	o.dd = s(o.dd, o.d, d.getDate(), 31);
	o.hh = s(o.hh, o.h, d.getHours(), 24);
	o.mm = s(o.mm, o.m, d.getMinutes());
	o.ss = s(o.ss, o.s, d.getSeconds());
	o.ms = s(o.ms, o.ms, d.getMilliseconds(), 999, 3);
	if (o.yyyy + o.MM + o.dd + o.hh + o.mm + o.ss + o.ms < 0)
		return false;
	if (o.yyyy < 100)
		o.yyyy += (o.yyyy > 30 ? 1900 : 2000);
	d = new Date(o.yyyy, o.MM - 1, o.dd, o.hh, o.mm, o.ss, o.ms);
	var reVal = d.getFullYear() == o.yyyy && d.getMonth() + 1 == o.MM
			&& d.getDate() == o.dd && d.getHours() == o.hh
			&& d.getMinutes() == o.mm && d.getSeconds() == o.ss
			&& d.getMilliseconds() == o.ms;
	return reVal && reObj ? d : reVal;
	function s(s1, s2, s3, s4, s5) {
		s4 = s4 || 60, s5 = s5 || 2;
		var reVal = s3;
		if (s1 != undefined && s1 != '' || !isNaN(s1))
			reVal = s1 * 1;
		if (s2 != undefined && s2 != '' && !isNaN(s2))
			reVal = s2 * 1;
		return (reVal == s1 && s1.length != s5 || reVal > s4) ? -10000 : reVal;
	}
};