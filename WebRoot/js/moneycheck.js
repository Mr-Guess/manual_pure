
/***************************************************************************************************
$ module name: CFloatEdit
$ file: floatedit.js
$ description��set an input edit controls text's format to float;
$
$ author: Lovely Life
$ date: 2007-11-7
$ support:  http://onlyaa.com [javascript����ר��]
$ update records:
$ update...
$
$
$ update date 2007-11-16 by Lovely Life
$ 1.�޸İ�������ʱ�Ĵ���, ���û�������д���, ��ֹ�����Ϲ��������
$ 2.���������ɾ�����߲�������ʱ���λ�õ�BUG
$
$ update date 2007-12-5 by Lovely Life
$ 1.�޸Ľ�������: ��ʧȥ�����,���ƶ���һ�����ܻ�ý�������ڿؼ���, ��������ʱ,�ַ��ص�floatedit�ؼ�
$
$ update date 2007-12-12 by Lovely Life
$ 1.����_create�ӿ�, ����value��ʼ��������ʽ��
$ 2.����_convertװ���ӿ�, ���������׵Ľ���ͨ���ı�����ؼ���ת����floatedit, ������_create����
$ 3.����_formatFloat�ӿ�, ��ʽ���ؼ�������
$
$ update date 2007-12-13 by Lovely Life
$ 1.ȥ��"����TAB��"���ָ�TAB��
$ 2.���ε������"."֮ǰһλ�Ĳ���
$
$ update daate 2007-12-13 by Lovely Life
$ 1.���ؼ�ʧȥ����ʱ��Ӧ��ʹ��keyup�¼�������Ч�����򵱽���Ӹ���ؼ��Ƶ��޽����ActiveXʱ�����¼�����ʱ��
$ �����ֻص�����ؼ���, �������ϴ�������
$
$ update date 2007-12-18 by Lovely Life
$ 1.����������Ŀ��ƣ���������С��������ƶ�
$
$ update date 2007-12-26 by Lovely Life
$ 1.���������","��ǰ����delete��ʱ��������ɾ�����֣� �Ѿ����
$
$ update date 2007-12-27 by Lovely Life
$ 1.�ڿؼ���ѡ�����ݺ�delete������ɾ��ѡ�����ݣ����ҹ��λ���ƶ�����ȷ
$ 2.��չ��Array�ķ���isInarray(), �����ǲ���ָ����value�Ƿ��ڸ������У���������򷵻�true, ���򷵻�false
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
this.bufTemp = "";		// ��ʱ����
this.mCount = 0;        // ","�ĸ���
this.movePre = false;  // ����Ƿ������ƶ�
this.moveNext = false; // ����Ƿ������ƶ�
this.change = true;
this.ismoved = true;
this.isnextto = false;
this.moveto = 0;
THIS = this;
/*-------------------------------------------------------------
$ ������:	_create
$ ����:	���������ʽ����ؼ�:
$ ����:	divid	-> ����Ҫ��Ӹÿؼ��ĵط��������´���:
$					 [<div id="eDivid"></div>],��ǰ������Ϊ��,
$					 ���øú������� oDivid ���� divid ����.
$			id	-> ���øñ༭�ؼ���id����
$			id	-> Ϊ������ؼ����õ�id, ���������ĵط�����
$			cls	-> ���Ϊ�գ�ʹ��Ĭ����ʽ����������CSS��ʽ
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
/*** ʧȥ�����¼�
e.onblur = function(){
alert("onblur");
this.onkeydown = null;
this.onkeyup = null;
};
****/
// ��ʼ��������ʧȥ���㣬�������Ҫ����
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
// ��ù��λ��
var pos = THIS._getCaret(this);
// ����ؼ�ѡ�����ݲ�Ϊ��
if( document.selection.createRange().duplicate().text != "" ){
THIS.isnextto = true;
if( (event.keyCode > 47 && event.keyCode <58) || (event.keyCode > 95 && event.keyCode < 106 ) ) {
var len = document.selection.createRange().duplicate().text.length;
THIS.moveto = pos - len + 1;
return;
}
}
//����TAB��
if( event.keyCode == 9 ){
return;
}
// ����"."����ʱ
if( [188, 116].isInarray(event.keyCode) ){
event.returnValue = false;
}
// �������"-"�ڱ���������λ��
if( [109, 189].isInarray(event.keyCode)){
if( pos == 0 )
return;
else
event.returnValue = false;
}
// ����DELETE�ͷ����
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
// �����˸��
if( event.keyCode == 8 ){
// ���ǰһλ��","
if( this.value.mid(pos-1, 1) == ','){
THIS._setCaret(this, pos - 1);
THIS.moveNext = true;
}
// ���
if( this.value.mid(pos-1, 1) == '.'){
THIS._setCaret(this, pos - 1);
}
return;
}
//��������С����"."
if( [190, 110].isInarray(event.keyCode)){
var cpos = this.value.indexOf('.');
// �������С����֮��, �򽫹��λ������С������һλ
if( pos > cpos ){
THIS._setCaret(this, cpos+1);
event.returnValue = false;
} else if( pos < cpos ) {
//THIS._setCaret(this, pos+2);
}
return;
}
// ��������
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
$ ������:	_processInput
$ ����:	�����û��������ݵĸ�ʽ�� ����������¹���:
1. ֻ�������֣�".";
2. С����ֻ�ܴ���һ������С�����ֳ���ֻ����2;
3. ��������Ϊ���Ҹ�ʽ, ��: 1234567  -> 1,234,567;
$ ����:   e -> ����ؼ����
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
//�������
var signal = str.left(1);	// ȡ��ߵ�һλ�ַ����� ����Ǹ�������ַ�����ɾ��
if( signal != "-")
signal = "";
else
str = str.right(str.length-1)
// ����С������
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
//������������
var tint, str;
var icount = 0;
tint = ar[0];
var prepos = tint.count(',');	// ɾ��","��ǰ��","����
tint = tint.replace(/,/g, ''); // ɾ��","
// ������ߵ�������0
if( tint == "" ) tint == "0"
// ��","�ָ�ǧ��λ
while( tint.length > 3 ){
intPart = "," + tint.right(3) + intPart;
icount++;
tint = tint.left( tint.length - 3 );
}
if( tint.length > 0 ) intPart = tint + intPart;
// �������
if( e.getAttribute("rel") == "int" ){
str = signal + intPart;
} else {
str = signal + intPart + "." + floatPart;
}
e.value = str;
// �������ƶ���λ��
var ecount = 0;
ecount = str.count(',')
// �˸��ʱ��겻��
if( this.ismoved == true ){
if( this.isnextto ){
// ���ؼ���ѡ������ʱ
if( ecount > prepos )
pos = this.moveto+1;
else
pos = this.moveto;
}else{
if( this.mCount != ecount ) {
// ","����ʱ��,��������ƶ�["��"�ı仯�ĸ���]λ
if( this.mCount > ecount )	pos = pos-(this.mCount - ecount);
// ","����,��������ƶ�["��"�ı仯�ĸ���]λ
if( this.mCount < ecount )	pos=pos + ( ecount - this.mCount);
}
}
}
this.mCount = ecount;
//���ù��λ��
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
$ ������:	_getCaret
$ ����:	��õ�ǰ����λ��
$ ����:	element -> ����ؼ����
---------------------------------------------------------------*/
this._getCaret = function (element){
element = this.$(element);
// ���ؼ����ı��������ı���ʱ���ҿ�д�����й��λ�ã�����ֱ�ӷ���
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
$ ������:	_setCaret
$ ����:	���õ�ǰ����λ��
$ ����:	element -> ����ؼ����
---------------------------------------------------------------*/
this._setCaret = function(element, pos){
if( !event )
return;
if( event.srcElement != element )
return;
try{
// ���ؼ����ı��������ı���ʱ���ҿ�д�����й��λ�ã�����ֱ�ӷ���
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
