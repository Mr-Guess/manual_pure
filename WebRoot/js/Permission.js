/**
 * 获得一个Menu下所有能操作按钮的值
 * 
 * @param menuId menu的Id
 * @param url ${ctx}传入
 * @returns {MenuPermission}
 */
MenuPermission = function(menuId, url) {
	this.menuId = menuId;
	this.url = url;
	/**
	 * 获得能操作按钮的MenuOpt List转换的json对象
	 */
	this.getMenuOptPermission = function(callBack) {
		var query = {
				'menuId' : this.menuId
		};
		$.post(url + '/menuOptPermission/menuOptPermission!generateMenuOpt.action', query, function(data) {
			callBack(data);
			//return eval('(' + data + ')');
		});
	};
	
	
};