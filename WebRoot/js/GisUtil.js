GisUtil = function(setting) {
	var temp = this;
	temp.params = {
		gisFunction:function(){alert('地图未初始化');},
		mainFrame:$('<div>'),
		gisFrame:$('<div>')
	};
	if(setting !=null && typeof setting == 'object') {
		$.each(setting, function(i, v){
			temp.params[i] = v;
		});
	}
};
/**
 * 强制设置参数方法
 */
GisUtil.prototype.setParams = function(setting) {
	var temp = this;
	if(setting !=null && typeof setting == 'object') {
		$.each(setting, function(i, v){
			temp.params[i] = v;
		});
	}
};
GisUtil.prototype.showMain = function() {
	$(this.params.mainFrame).show();
	$(this.params.gisFrame).hide();
};
GisUtil.prototype.showGis = function() {
	$(this.params.mainFrame).hide();
	$(this.params.gisFrame).show();
};
GisUtil.prototype.toggle = function() {
	$(this.params.mainFrame).toggle();
	$(this.params.gisFrame).toggle();
};
GisUtil.prototype.addPoint = function(dwdm, wxyid, wxytype, remark) {
	this.showGis();
	var obj = this.getGisMapObject(dwdm, wxyid, wxytype, remark, '', '');
	this.params.gisFunction('无坐标打点', null, null, obj, null, null, null);
};
GisUtil.prototype.showPoint = function(dwdm, wxyid, wxytype, remark) {
	this.showGis();
	var obj = this.getGisMapObject(dwdm, wxyid, wxytype, remark, '', '');
	this.params.gisFunction('定位', null, null, obj, null, null, null);
};
/**
 * 火灾模拟(受体分析)
 * @param dwdm
 * @param wxyid
 * @param wxytype
 * @param r1 死亡半径
 * @param r2 重伤半径
 * @param r3 轻伤半径
 * @param r4 安全半径
 */
GisUtil.prototype.fireSimulate = function(dwdm, wxyid, wxytype, r1, r2, r3, r4) {
	this.showGis();
	var arr = [r1,r2,r3,r4];
	var remark = "{\"wxyid\":\"" + wxyid + "\",\"wxytype\":\"" + wxytype + "\",\"dwdm\":\"" + dwdm + "\"}";
	this.params.gisFunction('火灾模拟(受体分析)', remark, arr, obj, null, null, null);
};
/**
 * 爆炸模拟(受体分析)
 * @param dwdm
 * @param wxyid
 * @param wxytype
 * @param r1 死亡半径
 * @param r2 重伤半径
 * @param r3 轻伤半径
 * @param r4 安全半径
 */
GisUtil.prototype.explosionSimulate = function(dwdm, wxyid, wxytype, r1, r2, r3, r4) {
	this.showGis();
	var arr = [r1,r2,r3,r4];
	var remark = "{\"wxyid\":\"" + wxyid + "\",\"wxytype\":\"" + wxytype + "\",\"dwdm\":\"" + dwdm + "\"}";
	this.params.gisFunction('爆炸模拟(受体分析)', remark, arr, obj, null, null, null);
};
/**
 * 瞬时泄漏模拟(受体分析)
 * @param dwdm
 * @param wxyid
 * @param wxytype
 * @param radius 半径数组
 * @param coordinate 偏移量数组
 */
GisUtil.prototype.leakSsSimulate = function(dwdm, wxyid, wxytype, radius, coordinate) {
	this.showGis();
	var arr = [coordinate, radius];
	var remark = "{\"wxyid\":\"" + wxyid + "\",\"wxytype\":\"" + wxytype + "\",\"dwdm\":\"" + dwdm + "\"}";
	this.params.gisFunction('瞬时泄漏模拟(受体分析)', remark, [], null, null, arr, null);
};
/**
 * 连续泄漏模拟(受体分析)
 * @param dwdm
 * @param wxyid
 * @param wxytype
 * @param burnStep 燃烧步长
 * @param burnStart 燃烧偏移量
 * @param burnArea 燃烧区域
 * @param burnValue 燃烧区点位数组
 * @param poisonStep 中毒步长
 * @param poisonStart 中毒偏移量
 * @param poisonArea 中毒区域
 * @param poisonValue 中毒区点位数组
 * @param type 0则展示燃烧和中毒，1为燃烧，2为中毒（系统中为1：有毒且不燃 3：有毒且可燃 2：无毒且可燃）
 */
GisUtil.prototype.leakLxSimulate = function(dwdm, wxyid, wxytype, 
		burnStep, burnStart, burnArea, burnValue, 
		poisonStep, poisonStart, poisonArea, poisonValue, type) {
	var gisType = '0';
	switch(type) {
	case '1':
		gisType = '2';
		break;
	case '2':
		gisType = '1';
		break;
	case '3':
		gisType = '0';
		break;
	} 
	//step 步长， start 偏移量， Area 区域
	var arrStr = [burnStep, poisonStep, burnStart, burnArea, poisonStart, poisonArea, gisType];
	var arrValue = [burnValue, poisonValue];
	var remark = "{\"wxyid\":\"" + wxyid + "\",\"wxytype\":\"" + wxytype + "\",\"dwdm\":\"" + dwdm + "\"}";
	this.params.gisFunction('连续泄漏模拟(受体分析)', remark, null, null, arrStr, arrValue, null);
};
/**
 * 半径查询(不画圆)
 * @param dwdm
 * @param wxyid
 * @param wxytype
 * @param remark
 * @param radius 半径(m)
 * @param typeArr 类型数组['accident','cars']
 */
GisUtil.prototype.search = function(dwdm, wxyid, wxytype, remark, radius, typeArr) {
	var obj = this.getGisMapObject(dwdm, wxyid, wxytype, remark, '', '');
	this.params.gisFunction('半径查询(不画圆)', radius, typeArr, obj, null, null, null);
};

GisUtil.prototype.invoke = function() {
	this.showGis();
	this.params.gisFunction.apply(this,arguments);
};
GisUtil.prototype.getGisMapObject = function(dwdm, wxyid, wxytype, remark, x, y) {
	var gisMapObject = {};
	gisMapObject.dwdm = dwdm;
    gisMapObject.wxyid = wxyid;
    gisMapObject.wxytype = wxytype;
    gisMapObject.remark = remark;
    gisMapObject.x = x;
    gisMapObject.y = y;
    return gisMapObject;
};
GisUtil.prototype.getGisMapString = function(dwdm, wxyid, wxytype) {
	return "{\"wxyid\":\"" + wxyid + "\",\"wxytype\":\"" + wxytype + "\",\"dwdm\":\"" + dwdm + "\"}";
};
