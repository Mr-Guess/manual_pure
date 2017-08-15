
/***************************************************************************************************
$ module name: CFloatEdit
$ file: floatedit.js
$ description：set an input edit controls text's format to float;
$
$ author: Lovely Life
$ date: 2007-11-7
$ support:  http://onlyaa.com [javascript经典专区]
$ update records:
$ update...
$
$
$ update date 2007-11-16 by Lovely Life
$ 1.修改按键按下时的处理, 对用户输入进行处理, 禁止不符合规则的输入
$ 2.解决了由于删除或者插入数字时光标位置的BUG
$
$ update date 2007-12-5 by Lovely Life
$ 1.修改焦点问题: 在失去焦点后,但移动到一个不能获得焦点的日期控件上, 则当有输入时,又返回到floatedit控件
$
$ update date 2007-12-12 by Lovely Life
$ 1.更改_create接口, 新增value初始化，并格式化
$ 2.增加_convert装换接口, 这样很容易的将普通的文本输入控件，转换成floatedit, 无需用_create创建
$ 3.增加_formatFloat接口, 格式化控件的数字
$
$ update date 2007-12-13 by Lovely Life
$ 1.去除"屏蔽TAB键"，恢复TAB键
$ 2.屏蔽当光标在"."之前一位的操作
$
$ update daate 2007-12-13 by Lovely Life
$ 1.当控件失去焦点时，应该使得keyup事件处理无效，否则当焦点从浮点控件移到无焦点的ActiveX时，有事件处理时，
$ 焦点又回到浮点控件上, 给操作上带来不便
$
$ update date 2007-12-18 by Lovely Life
$ 1.修正方向键的控制，解决光标在小数点后不能移动
$
$ update date 2007-12-26 by Lovely Life
$ 1.修正光标在","号前按下delete键时不能正常删除数字， 已经解决
$
$ update date 2007-12-27 by Lovely Life
$ 1.在控件内选定内容后，delete键不能删除选定内容，而且光标位置移动不正确
$ 2.扩展了Array的方法isInarray(), 功能是查找指定的value是否在该数组中，如果存在则返回true, 否则返回false
******************************************************************************************************/
String.prototype.left = function(nLength){
return this.substr(0, nLength);
};
String.prototype.right = function(nLength){
return this.substr(this.length - nLength, nLength);
};
String.prototype.mid = function(index, nLength){
return this.substr(index, nLength);
};
String.prototype.count = function(char){
var ar = this.split(char);
return (ar.length - 1);
}
Array.prototype.isInarray = function(v){
for( var i=0; i < this.length; i++ ) {
if( v == this[i] ){
return true;
}
}
return false;
}
var THIS;
var oFloatEdit = new CFloatEdit();
function CFloatEdit(){
this.initValue = "0.00";
this.bufTemp = "";		// 临时缓冲
this.mCount = 0;        // ","的个数
this.movePre = false;  // 光标是否向右移动
this.moveNext = false; // 光标是否向左移动
this.change = true;
this.ismoved = true;
this.isnextto = false;
this.moveto = 0;
THIS = this;
/*-------------------------------------------------------------
$ 函数名:	_create
$ 功能:	创建浮点格式输入控件:
$ 参数:	divid	-> 在所要添加该控件的地方插入以下代码:
$					 [<div id="eDivid"></div>],以前述代码为例,
$					 调用该函数并将 oDivid 传给 divid 参数.
$			id	-> 设置该编辑控件的id属性
$			id	-> 为该输入控件设置的id, 方便其他的地方调用
$			cls	-> 如果为空，使用默认样式，否则设置CSS样式
---------------------------------------------------------------*/
this._create = function(container, id, value, cls){
var div = this.$(container);
var floatedit = document.createElement("INPUT");
div.appendChild(floatedit);
floatedit.id = id;
if( cls != "" )
floatedit.className = cls;
floatedit.value = value;
floatedit.style.imeMode = "disabled";
floatedit.size = 22;
this._processInput(floatedit);
this._convert(floatedit);
};
this._convert = function(e){
e = this.$(e);
e.style.imeMode = "disabled";
if( e.size == "" )
e.size = 22;
this._processInput(e);
e.onfocus = function(){
//alert("onfocus"); load keydown keyup beforepaste event
THIS._bindKeyDown(this);
THIS._bindKeyUp(this);
//THIS._bindBeforePaste(this);
}
/*** 失去焦点事件
e.onblur = function(){
alert("onblur");
this.onkeydown = null;
this.onkeyup = null;
};
****/
// 初始化结束后将失去焦点，这里必须要加上
e.blur();
};
this._formatFloat = function(e){
this._processInput(e);
};
this._bindKeyUp = function(e){
e = this.$(e);
e.onkeyup = function(){
var pos = THIS._getCaret(this)
if( event.keyCode == 46 )
THIS.ismoved = false;
THIS._processInput(this);
THIS.ismoved = true;
};
};
this._bindBeforePaste = function(e){
e = this.$(e);
e.onbeforepaste = function() {
clipboardData.setData('text','')
};
};
this._bindKeyDown = function(e){
e = this.$(e);
e.onkeydown = function() {
if( event.shiftKey){
event.returnValue = false;
return;
}
if( [35, 36].isInarray(event.keyCode)){
return;
}
// 获得光标位置
var pos = THIS._getCaret(this);
// 处理控件选中内容不为空
if( document.selection.createRange().duplicate().text != "" ){
THIS.isnextto = true;
if( (event.keyCode > 47 && event.keyCode <58) || (event.keyCode > 95 && event.keyCode < 106 ) ) {
var len = document.selection.createRange().duplicate().text.length;
THIS.moveto = pos - len + 1;
return;
}
}
//处理TAB键
if( event.keyCode == 9 ){
return;
}
// 处理当"."按下时
if( [188, 116].isInarray(event.keyCode) ){
event.returnValue = false;
}
// 允许添加"-"在必须是在首位置
if( [109, 189].isInarray(event.keyCode)){
if( pos == 0 )
return;
else
event.returnValue = false;
}
// 处理DELETE和方向键
if( [46, 37, 38, 39, 40].isInarray(event.keyCode) ){
if( event.keyCode == 46 ){
if( this.value.mid(pos, 1) == '.' || this.value.mid(pos, 1) == ',' ){
if( document.selection.createRange().duplicate().text == "" ){
THIS._setCaret(this, pos+1);
}else{
THIS.ismoved = false;
}
}
return;
}
if( pos >= this.value.length && event.keyCode == 39 )
event.returnValue = false;
return;
}
// 处理退格键
if( event.keyCode == 8 ){
// 光标前一位是","
if( this.value.mid(pos-1, 1) == ','){
THIS._setCaret(this, pos - 1);
THIS.moveNext = true;
}
// 光标
if( this.value.mid(pos-1, 1) == '.'){
THIS._setCaret(this, pos - 1);
}
return;
}
//处理输入小数点"."
if( [190, 110].isInarray(event.keyCode)){
var cpos = this.value.indexOf('.');
// 当光标在小数点之后, 则将光标位置移至小数点下一位
if( pos > cpos ){
THIS._setCaret(this, cpos+1);
event.returnValue = false;
} else if( pos < cpos ) {
//THIS._setCaret(this, pos+2);
}
return;
}
// 处理数字
if( (event.keyCode > 47 && event.keyCode <58) || (event.keyCode > 95 && event.keyCode < 106 ) ) {
if( document.selection.createRange().duplicate().text != "" ){
THIS.isnext = true;
var len = document.selection.createRange().duplicate().text.length;
THIS.moveto = pos - len + 1;
}
return;
}else{
event.returnValue = false;
}
event.returnValue = false;
}
}
/*-------------------------------------------------------------
$ 函数名:	_processInput
$ 功能:	处理用户输入数据的格式， 必须符合以下规则:
1. 只能是数字，".";
2. 小数点只能存在一个，且小数部分长度只能是2;
3. 整数部分为货币格式, 如: 1234567  -> 1,234,567;
$ 参数:   e -> 输入控件句柄
---------------------------------------------------------------*/
this._processInput = function(e){
var intPart = "", floatPart = "";
var str = e.value;
var pos = this._getCaret(e);
if( str == "" ){
if(e.getAttribute("rel") == "int"){
e.value = "";
} else {
e.value = this.initValue;
}
return;//event.returnValue = false;
}
//处理符号
var signal = str.left(1);	// 取左边的一位字符串， 如果是负号则从字符串中删除
if( signal != "-")
signal = "";
else
str = str.right(str.length-1)
// 处理小数部分
str = str.replace(/[^(\d|\.|,)]/g, '')
var ar;
ar = str.split('.');
if( ar.length < 2 ){
floatPart = "00";
}else{
var strTemp;
strTemp = ar[ar.length-1].replace(/[^(\d)]/g, '');
var len = strTemp.length;
if( len == 0 ){
floatPart = "00";
}else if(len == 1 ){
floatPart = strTemp + "0";
}else if(len > 2 ){
floatPart = strTemp.left(2);
} else {
floatPart = strTemp;
}
}
//处理整数部分
var tint, str;
var icount = 0;
tint = ar[0];
var prepos = tint.count(',');	// 删除","号前的","个数
tint = tint.replace(/,/g, ''); // 删除","
// 过滤左边的连续的0
if( tint == "" ) tint == "0"
// 用","分割千分位
while( tint.length > 3 ){
intPart = "," + tint.right(3) + intPart;
icount++;
tint = tint.left( tint.length - 3 );
}
if( tint.length > 0 ) intPart = tint + intPart;
// 组合数字
if( e.getAttribute("rel") == "int" ){
str = signal + intPart;
} else {
str = signal + intPart + "." + floatPart;
}
e.value = str;
// 处理光标移动的位置
var ecount = 0;
ecount = str.count(',')
// 退格键时光标不变
if( this.ismoved == true ){
if( this.isnextto ){
// 当控件有选择内容时
if( ecount > prepos )
pos = this.moveto+1;
else
pos = this.moveto;
}else{
if( this.mCount != ecount ) {
// ","减少时候,光标向左移动["，"的变化的个数]位
if( this.mCount > ecount )	pos = pos-(this.mCount - ecount);
// ","增加,光标向右移动["，"的变化的个数]位
if( this.mCount < ecount )	pos=pos + ( ecount - this.mCount);
}
}
}
this.mCount = ecount;
//设置光标位置
if( this.moveNext ) pos++;
if( this.movePre )	pos--;
this._setCaret(e, pos);
this.movePre = false;
this.moveNext = false;
this.isnextto = false;
this.ismoved = true;
intPart = str = "";
};
/*-------------------------------------------------------------
$ 函数名:	_getCaret
$ 功能:	获得当前光标的位置
$ 参数:	element -> 输入控件句柄
---------------------------------------------------------------*/
this._getCaret = function (element){
element = this.$(element);
// 当控件是文本区或者文本框时并且可写才能有光标位置，否则直接返回
//alert(this.display(window));
if( !event ){
return;
}
if( event.srcElement != element )
return;
if( element.readOnly == true || element.disabled == true ){
return;
}
element.focus();
var rang = document.selection.createRange();
rang.setEndPoint("StartToStart", element.createTextRange())
return rang.text.length;
};
/*-------------------------------------------------------------
$ 函数名:	_setCaret
$ 功能:	设置当前光标的位置
$ 参数:	element -> 输入控件句柄
---------------------------------------------------------------*/
this._setCaret = function(element, pos){
if( !event )
return;
if( event.srcElement != element )
return;
try{
// 当控件是文本区或者文本框时并且可写才能有光标位置，否则直接返回
if( element.readOnly == true || element.disabled == true ){
return;
}
var r =element.createTextRange();
r.moveStart('character', pos);
r.collapse(true);
r.select();
}catch(e){
// show error message at here
}
};
this.$ = function(e) {
if( typeof e != 'object' )
return document.getElementById(e);
else
return e;
}
// code end
}
function convertToFloatEdit(id){
oFloatEdit._convert(id);
}
function createFloatEdit(container, id, value, className){
oFloatEdit._create(container, id, value, className);
}
function formatFloat(id){
oFloatEdit._formatFloat(id);
}
