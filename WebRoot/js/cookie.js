Cookie = function() {
	// 添加cookie
	this.add = function(name, value, hours) {
		var life = new Date().getTime();
		life += hours * 1000 * 60;
		var cookieStr = name + "=" + escape(value) + ";expires="
				+ new Date(life).toGMTString();
		document.cookie = cookieStr;
	};
	// 获取cookie值
	this.get = function(name) {
		var cookies = document.cookie.split(";");
		if (cookies.length > 0) {
			for(var i=0;i<cookies.length;i++){				
				var cookie = cookies[i].split("=");
				if (cookie[0].trim() == name)
					return unescape(cookie[1]);
			}
		}
		return null;
	};
	// 删除cookie
	this.remove = function(name) {
		var cookieStr = name + "=" + escape('') + ";expires="
				+ new Date().toGMTString();
		document.cookie = cookieStr;
	};
};
