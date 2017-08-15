package generate.tool.template;

import java.util.*;

import com.ay.framework.util.BeanUtil;

public class BaseActionTemplate implements ITemplate
{
  protected static String nl;
  public static synchronized ITemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ITemplate result = new BaseActionTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";" + NL + "" + NL + "import java.util.Date;" + NL + "import java.util.List;" + NL + "import java.util.Map;" + NL + "" + NL + "import org.apache.shiro.SecurityUtils;" + NL + "" + NL + "import com.ay.framework.bean.DataStore;" + NL + "import com.ay.framework.bean.OperateInfo;" + NL + "import com.ay.framework.core.dao.pagination.Page;" + NL + "import com.ay.framework.core.utils.mapper.JsonMapper;" + NL + "import com.ay.framework.core.utils.web.struts.Struts2Utils;" + NL + "import com.ay.framework.util.BeanUtil;" + NL + "import com.ay.framework.util.EncodingHeaderUtil;" + NL + "import ";
  protected final String TEXT_3 = ";" + NL + "import ";
  protected final String TEXT_4 = ".";
  protected final String TEXT_5 = "Service;" + NL + "import com.ay.framework.core.action.BaseAction;" + NL + "" + NL + "@SuppressWarnings(\"all\")" + NL + "public class ";
  protected final String TEXT_6 = "Action extends BaseAction {" + NL + "\tprivate ";
  protected final String TEXT_7 = "Service ";
  protected final String TEXT_8 = "Service;" + NL + "\tprivate ";
  protected final String TEXT_9 = " ";
  protected final String TEXT_10 = ";" + NL + "\tprivate String page;" + NL + "\tprivate String rows;" + NL + "\tprivate String sort;" + NL + "\tprivate String order;" + NL + "\tprivate String id;" + NL + "\tprivate String ids;" + NL + "\t" + NL + "\tpublic void add() {" + NL + "\t\tOperateInfo operateInfo = new OperateInfo(true);" + NL + "\t\ttry {" + NL + "\t\t\t";
  protected final String TEXT_11 = "Service.insert(";
  protected final String TEXT_12 = ");" + NL + "\t\t\toperateInfo.setOperateMessage(\"添加成功\");" + NL + "\t\t\toperateInfo.setOperateSuccess(true);" + NL + "\t\t} catch (Exception e) {" + NL + "\t\t\toperateInfo.setOperateMessage(\"添加失败\");" + NL + "\t\t\toperateInfo.setOperateSuccess(false);" + NL + "\t\t}" + NL + "\t\t" + NL + "\t\tString json = new JsonMapper().toJson(operateInfo);" + NL + "\t\tStruts2Utils.renderText(json);" + NL + "\t}" + NL + "\t" + NL + "\tpublic void update() {" + NL + "\t\tOperateInfo operateInfo = new OperateInfo();" + NL + "\t\tboolean flag = ";
  protected final String TEXT_13 = "Service.update(";
  protected final String TEXT_14 = ");" + NL + "\t\tif (flag) {" + NL + "\t\t\toperateInfo.setOperateMessage(\"更新成功\");" + NL + "\t\t\toperateInfo.setOperateSuccess(true);" + NL + "\t\t} else {" + NL + "\t\t\toperateInfo.setOperateMessage(\"更新失败\");" + NL + "\t\t\toperateInfo.setOperateSuccess(false);" + NL + "\t\t}" + NL + "\t\t" + NL + "\t\tString json = new JsonMapper().toJson(operateInfo);" + NL + "\t\tStruts2Utils.renderText(json);" + NL + "\t}" + NL + "\t" + NL + "\tpublic void delete() {" + NL + "\t\tOperateInfo operateInfo = new OperateInfo();" + NL + "\t\tboolean flag = false;" + NL + "\t\tif (ids.contains(\",\")) {" + NL + "\t\t\tString idsParam[] = ids.split(\",\");" + NL + "\t\t\tflag = ";
  protected final String TEXT_15 = "Service.delete(idsParam);" + NL + "\t\t} else {" + NL + "\t\t\tflag = ";
  protected final String TEXT_16 = "Service.delete(ids);" + NL + "\t\t}" + NL + "\t\tif (flag) {" + NL + "\t\t\toperateInfo.setOperateMessage(\"删除成功\");" + NL + "\t\t\toperateInfo.setOperateSuccess(true);" + NL + "\t\t} else {" + NL + "\t\t\toperateInfo.setOperateMessage(\"删除失败\");" + NL + "\t\t\toperateInfo.setOperateSuccess(false);" + NL + "\t\t}" + NL + "\t\tString json = new JsonMapper().toJson(operateInfo);" + NL + "\t\tStruts2Utils.renderText(json);\t\t" + NL + "\t}" + NL + "\t" + NL + "\tpublic void list() {" + NL + "\t\tDataStore<";
  protected final String TEXT_17 = "> dataStore = new DataStore<";
  protected final String TEXT_18 = ">();" + NL + "\t\tPage pageTemp = new Page();" + NL + "\t\t//当前页 " + NL + "\t\tint intPage = Integer.parseInt((page == null || page == \"0\") ? \"1\" : page);" + NL + "\t\t//每页显示条数  " + NL + "\t\tint number = Integer.parseInt((rows == null || rows == \"0\") ? \"10\" : rows);" + NL + "\t\tint start = (intPage -1) * number;" + NL + "\t\tpageTemp.setCurrentPage(intPage);" + NL + "\t\tpageTemp.setRecPerPage(number);" + NL + "\t\tpageTemp.setFrom(start);" + NL + "\t\tMap paramMap = BeanUtil.Bean2Map(";
  protected final String TEXT_19 = ");" + NL + "\t\tPage resultPage = ";
  protected final String TEXT_20 = "Service.pageQuery(paramMap, pageTemp);" + NL + "\t\tList<";
  protected final String TEXT_21 = "> resultList = (List<";
  protected final String TEXT_22 = ">) resultPage.getCollection();" + NL + "\t\tdataStore.setTotal(new Long(resultPage.getCount()));" + NL + "\t\tdataStore.setRows(resultList);" + NL + "\t\tString json = new JsonMapper().toJson(dataStore);" + NL + "\t\tStruts2Utils.renderText(json);" + NL + "\t}" + NL + "\t" + NL + "\tpublic void getById() {" + NL + "\t\tStruts2Utils.renderJson(";
  protected final String TEXT_23 = "Service.getById(id)," + NL + "\t\t\t\tEncodingHeaderUtil.HEADERENCODING," + NL + "\t\t\t\tEncodingHeaderUtil.CACHEENCODING);\t\t" + NL + "\t}" + NL;
  protected final String TEXT_23_01 ="public String exp(){" + NL + "\t\tString where = \" where 1=1 \";"+ NL +"\t\t Map map = BeanUtil.Bean2Map(";
  protected final String TEXT_23_02 =");" + NL + "\t\tif (map != null){" + NL + "\t\t\tfor (Object o : map.keySet()){" + NL + "\t\t\t\twhere += \" and \" + o.toString() + \" like '%\" + map.get(o) + \"%' \";" + NL + "\t\t\t}" + NL + "\t\t}" + NL + "excelQuerySql = \"\t\tselect * from sys_user\" + where;" + NL + "\t\tSystem.out.println(excelQuerySql);" + NL + "\t\texcelSheetName = \"测试名称\";"+ NL + "\t\texcelHeads = new String[]{ \"名字1\", \"名字2\", \"名字3\" };" + NL + "\t\treturn \"exp\";" + NL +"}"; 
  protected final String TEXT_23_1 = "\t" + NL + "    public ";
  protected final String TEXT_24 = " get";
  protected final String TEXT_25 = "() {" + NL + "\t\treturn ";
  protected final String TEXT_26 = ";" + NL + "\t}" + NL + "" + NL + "\tpublic void set";
  protected final String TEXT_27 = "(";
  protected final String TEXT_28 = " ";
  protected final String TEXT_29 = ") {" + NL + "\t\tthis.";
  protected final String TEXT_30 = " = ";
  protected final String TEXT_31 = ";" + NL + "\t}" + NL + "" + NL + "\tpublic ";
  protected final String TEXT_32 = "Service get";
  protected final String TEXT_33 = "Service() {" + NL + "\t\treturn ";
  protected final String TEXT_34 = "Service;" + NL + "\t}" + NL + "" + NL + "\tpublic void set";
  protected final String TEXT_35 = "Service(";
  protected final String TEXT_36 = "Service ";
  protected final String TEXT_37 = "Service) {" + NL + "\t\tthis.";
  protected final String TEXT_38 = "Service = ";
  protected final String TEXT_39 = "Service;" + NL + "\t}" + NL + "" + NL + "\tpublic String getPage() {" + NL + "\t\treturn page;" + NL + "\t}" + NL + "" + NL + "\tpublic void setPage(String page) {" + NL + "\t\tthis.page = page;" + NL + "\t}" + NL + "" + NL + "\tpublic String getRows() {" + NL + "\t\treturn rows;" + NL + "\t}" + NL + "" + NL + "\tpublic void setRows(String rows) {" + NL + "\t\tthis.rows = rows;" + NL + "\t}" + NL + "" + NL + "\tpublic String getSort() {" + NL + "\t\treturn sort;" + NL + "\t}" + NL + "" + NL + "\tpublic void setSort(String sort) {" + NL + "\t\tthis.sort = sort;" + NL + "\t}" + NL + "" + NL + "\tpublic String getOrder() {" + NL + "\t\treturn order;" + NL + "\t}" + NL + "" + NL + "\tpublic void setOrder(String order) {" + NL + "\t\tthis.order = order;" + NL + "\t}" + NL + "" + NL + "\tpublic String getId() {" + NL + "\t\treturn id;" + NL + "\t}" + NL + "" + NL + "\tpublic void setId(String id) {" + NL + "\t\tthis.id = id;" + NL + "\t}" + NL + "" + NL + "\tpublic String getIds() {" + NL + "\t\treturn ids;" + NL + "\t}" + NL + "" + NL + "\tpublic void setIds(String ids) {" + NL + "\t\tthis.ids = ids;" + NL + "\t}" + NL + "\t" + NL + "}";
  protected final String TEXT_40 = NL;

  /* (non-Javadoc)
 * @see generate.template.ITemplate#generate(java.lang.Object)
 */
@Override
public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
	Object[] o=(Object[])argument;
	String classFullName = o[1].toString();
	String className = o[2].toString();
	String generatePackage = o[6].toString();
	String lowerClassName = o[7].toString();
	boolean hasPackage = (Boolean)o[9];

    stringBuffer.append(TEXT_1);
    stringBuffer.append(generatePackage);
    stringBuffer.append(!hasPackage?"":".action");
    stringBuffer.append(TEXT_2);
    stringBuffer.append(classFullName);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(generatePackage);
    stringBuffer.append(!hasPackage?"":".service");
    stringBuffer.append(TEXT_4);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_11);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(TEXT_23_01);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_23_02);
    stringBuffer.append(TEXT_23_1);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_25);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_27);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_28);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_38);
    stringBuffer.append(lowerClassName);
    stringBuffer.append(TEXT_39);
    stringBuffer.append(TEXT_40);
    return stringBuffer.toString();
  }
}
