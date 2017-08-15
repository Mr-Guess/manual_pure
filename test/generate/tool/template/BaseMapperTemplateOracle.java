package generate.tool.template;

import java.util.*;

public class BaseMapperTemplateOracle implements ITemplate
{
  protected static String nl;
  public static synchronized BaseMapperTemplateOracle create(String lineSeparator)
  {
    nl = lineSeparator;
    BaseMapperTemplateOracle result = new BaseMapperTemplateOracle();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + NL + "<!DOCTYPE sqlMap PUBLIC \"-//ibatis.apache.org//DTD SQL Map 2.0//EN\" \"http://ibatis.apache.org/dtd/sql-map-2.dtd\">" + NL + "" + NL + "<!--sqlMapper-->" + NL + "<sqlMap namespace=\"";
  protected final String TEXT_2 = "\">" + NL + "\t<!-- 查询条件 -->" + NL + "\t<sql id=\"mapWhereClause\">" + NL + "\t\t<isParameterPresent>" + NL + "\t\t\t <isNotEmpty prepend=\"and\" property=\"condition\"> $condition$ </isNotEmpty>" + NL + NL + "\t\t\t <isNotEmpty prepend=\"and\" property=\"endDataProg\"> $endDataProg$ </isNotEmpty>" + NL + "\t\t\t<isNotEmpty prepend=\"and\" property=\"id\"> ";
  protected final String TEXT_3 = ".ID=#id#</isNotEmpty>" + NL + "\t\t\t<isNotEmpty prepend=\"and\" property=\"status\"> ";
  protected final String TEXT_4 = ".STATUS=#status#</isNotEmpty>" + NL + "\t\t\t<isNotEmpty prepend=\"and\" property=\"created\"> ";
  protected final String TEXT_5 = ".CREATED=#created#</isNotEmpty>" + NL + "\t\t\t<isNotEmpty prepend=\"and\" property=\"createTime\"> ";
  protected final String TEXT_6 = ".CREATE_TIME=#createTime#</isNotEmpty>" + NL + "\t\t\t<isNotEmpty prepend=\"and\" property=\"updated\"> ";
  protected final String TEXT_7 = ".UPDATED=#updated#</isNotEmpty>" + NL + "\t\t\t<isNotEmpty prepend=\"and\" property=\"updateTime\"> ";
  protected final String TEXT_8 = ".UPDATE_TIME=#updateTime#</isNotEmpty>" + NL + "\t\t\t" + NL + "\t\t\t";
  protected final String TEXT_9 = NL + "\t\t\t<isNotEmpty prepend=\"and\" property=\"";
  protected final String TEXT_10 = "\"> ";
  protected final String TEXT_11 = ".";
  protected final String TEXT_12 = " like '%'||#";
  protected final String TEXT_13 = "#||'%'</isNotEmpty>";
  protected final String TEXT_14 = NL + "\t\t\t" + NL + "\t\t</isParameterPresent>" + NL + "\t</sql>" + NL + "\t<!-- add method insert-->" + NL + "\t<insert id=\"insert\" parameterClass=\"";
  protected final String TEXT_15 = "\">" + NL + "  \t\tINSERT INTO ";
  protected final String TEXT_16 = "(" + NL + "\t\t\t\t";
  protected final String TEXT_17 = NL + "\t\t\t\t";
  protected final String TEXT_18 = ",";
  protected final String TEXT_19 = NL + "\t\t\t\t" + NL + "\t\t\t\tID," + NL + "\t\t\t\tSTATUS," + NL + "\t\t\t\tCREATED," + NL + "\t\t\t\tCREATE_TIME," + NL + "\t\t\t\tUPDATED," + NL + "\t\t\t\tUPDATE_TIME," + NL + "\t\t\t\tORG," + NL + "\t\t\t\tORG_TREE) values(" + NL + "\t\t\t\t";
  protected final String TEXT_20 = NL + "\t\t\t\t#";
  protected final String TEXT_21 = "#,";
  protected final String TEXT_22 = NL + "\t\t\t\t" + NL + "\t\t\t\t#id#," + NL + "\t\t\t\t#status#," + NL + "\t\t\t\t#created#," + NL + "\t\t\t\t#createTime#," + NL + "\t\t\t\t#updated#," + NL + "\t\t\t\t#updateTime#," + NL + "\t\t\t\t#org#," + NL + "\t\t\t\t#orgTree#)" + NL + "\t</insert>" + NL + "\t<!-- add method delete-->" + NL + "\t<delete id=\"delete\" parameterClass=\"java.lang.String\">" + NL + "\t\tUPDATE ";
  protected final String TEXT_23 = " SET STATUS='0' " + NL + "  \t\tWHERE " + NL + " \t \t\tID=#id#</delete>" + NL + "\t<!-- add method getById-->" + NL + "\t<select id=\"getById\" resultClass=\"";
  protected final String TEXT_24 = "\" parameterClass=\"java.lang.String\">" + NL + " \t\tSELECT " + NL + "\t\t\t";
  protected final String TEXT_25 = NL + "\t\t\t";
  protected final String TEXT_26 = ".";
  protected final String TEXT_27 = " AS ";
  protected final String TEXT_28 = ",";
  protected final String TEXT_29 = NL + "\t\t\t" + NL + "\t\t\t";
  protected final String TEXT_30 = ".ID AS id," + NL + "\t\t\t";
  protected final String TEXT_31 = ".STATUS AS status," + NL + "\t\t\t";
  protected final String TEXT_32 = ".CREATED AS created," + NL + "\t\t\t";
  protected final String TEXT_33 = ".CREATE_TIME AS createTime," + NL + "\t\t\t";
  protected final String TEXT_34 = ".UPDATED AS updated," + NL + "\t\t\t";
  protected final String TEXT_35 = ".UPDATE_TIME as updateTime" + NL + "\t\tfrom" + NL + "\t\t";
  protected final String TEXT_36 = NL + "  \t\twhere " + NL + " \t \t\t";
  protected final String TEXT_37 = ".ID=#id#" + NL + "\t</select>" + NL + "\t<!-- add method update-->" + NL + "\t<update id=\"update\" parameterClass=\"";
  protected final String TEXT_38 = "\">" + NL + "\t\tUPDATE ";
  protected final String TEXT_39 = " " + NL + "\t\t<dynamic prepend=\"set\">" + NL + "\t\t\t";
  protected final String TEXT_40 = NL + "\t\t\t<isNotEmpty prepend=\",\" property=\"";
  protected final String TEXT_41 = "\"> ";
  protected final String TEXT_42 = "=#";
  protected final String TEXT_43 = "#</isNotEmpty>";
  protected final String TEXT_44 = NL + "\t\t\t" + NL + "\t\t\t<isNotEmpty prepend=\",\" property=\"id\"> ID=#id#</isNotEmpty>" + NL + "\t\t\t<isNotEmpty prepend=\",\" property=\"status\"> STATUS=#status#</isNotEmpty>" + NL + "\t\t\t<isNotEmpty prepend=\",\" property=\"created\"> CREATED=#created#</isNotEmpty>" + NL + "\t\t\t<isNotEmpty prepend=\",\" property=\"createTime\"> CREATE_TIME=#createTime#</isNotEmpty>" + NL + "\t\t\t<isNotEmpty prepend=\",\" property=\"updated\"> UPDATED=#updated#</isNotEmpty>" + NL + "\t\t\t<isNotEmpty prepend=\",\" property=\"updateTime\"> UPDATE_TIME=#updateTime#</isNotEmpty>" + NL + "\t\t</dynamic>" + NL + "  \t\tWHERE " + NL + " \t \t\tID=#id#" + NL + "\t</update>" + NL + "\t" + NL + "\t<!-- add method find-->" + NL + "\t<select id=\"find\" resultClass=\"";
  protected final String TEXT_45 = "\" parameterClass=\"java.util.Map\">" + NL + " \t\tSELECT" + NL + "\t\t\t";
  protected final String TEXT_46 = NL + "\t\t\t";
  protected final String TEXT_47 = ".";
  protected final String TEXT_48 = " AS ";
  protected final String TEXT_49 = ",";
  protected final String TEXT_50 = NL + "\t\t\t" + NL + "\t\t\t";
  protected final String TEXT_51 = ".ID AS id," + NL + "\t\t\t";
  protected final String TEXT_52 = ".STATUS AS status," + NL + "\t\t\t";
  protected final String TEXT_53 = ".CREATED AS created," + NL + "\t\t\t";
  protected final String TEXT_54 = ".CREATE_TIME AS createTime," + NL + "\t\t\t";
  protected final String TEXT_55 = ".UPDATED AS updated," + NL + "\t\t\t";
  protected final String TEXT_56 = ".UPDATE_TIME AS updateTime" + NL + "\t\tFROM" + NL + "\t\t\t";
  protected final String TEXT_57 = NL + "\t\tWHERE" + NL + "\t\t\t";
  protected final String TEXT_58 = ".STATUS = '1'" + NL + "\t\t<include refid=\"";
  protected final String TEXT_59 = ".mapWhereClause\"/>" + NL + "\t</select>" + NL + "\t<!-- add method count-->" + NL + "\t<select id=\"count\" resultClass=\"java.lang.Integer\" parameterClass=\"java.util.Map\">" + NL + " \t\tSELECT count(*) " + NL + "\t\tFROM" + NL + "\t\t\t";
  protected final String TEXT_60 = NL + "\t\tWHERE" + NL + "\t\t\t";
  protected final String TEXT_61 = ".STATUS = '1' " + NL + "\t\t<include refid=\"";
  protected final String TEXT_62 = ".mapWhereClause\"/>" + NL + "\t</select>" + NL + "</sqlMap>";
  protected final String TEXT_63 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
	Object[] o=(Object[])argument;
	String tableName = o[0].toString().toUpperCase();
	String classFullName = o[1].toString();
	String className = o[2].toString();
	List<String> fieldNames=(List<String>)o[3];
	List<String> DBFieldNames=(List<String>)o[4];
	

    stringBuffer.append(TEXT_1);
    stringBuffer.append(className );
    stringBuffer.append(TEXT_2);
    stringBuffer.append(tableName );
    stringBuffer.append(TEXT_3);
    stringBuffer.append(tableName );
    stringBuffer.append(TEXT_4);
    stringBuffer.append(tableName );
    stringBuffer.append(TEXT_5);
    stringBuffer.append(tableName );
    stringBuffer.append(TEXT_6);
    stringBuffer.append(tableName );
    stringBuffer.append(TEXT_7);
    stringBuffer.append(tableName );
    stringBuffer.append(TEXT_8);
    for(int i=0;i<DBFieldNames.size();i++){ 
			String DBFieldName=DBFieldNames.get(i);
			String fieldName = fieldNames.get(i);
			
    stringBuffer.append(TEXT_9);
    stringBuffer.append(fieldName );
    stringBuffer.append(TEXT_10);
    stringBuffer.append(tableName );
    stringBuffer.append(TEXT_11);
    stringBuffer.append(DBFieldName );
    stringBuffer.append(TEXT_12);
    stringBuffer.append(fieldName );
    stringBuffer.append(TEXT_13);
    }
    stringBuffer.append(TEXT_14);
    stringBuffer.append(classFullName );
    stringBuffer.append(TEXT_15);
    stringBuffer.append(tableName );
    stringBuffer.append(TEXT_16);
    for(int i=0;i<DBFieldNames.size();i++){ 
				String DBFieldName=DBFieldNames.get(i);
				
    stringBuffer.append(TEXT_17);
    stringBuffer.append(DBFieldName );
    stringBuffer.append(TEXT_18);
    }
    stringBuffer.append(TEXT_19);
    for(int i=0;i<DBFieldNames.size();i++){ 
				String fieldName = fieldNames.get(i);
				
    stringBuffer.append(TEXT_20);
    stringBuffer.append(fieldName );
    stringBuffer.append(TEXT_21);
    }
    stringBuffer.append(TEXT_22);
    stringBuffer.append(tableName );
    stringBuffer.append(TEXT_23);
    stringBuffer.append(classFullName );
    stringBuffer.append(TEXT_24);
    for(int i=0;i<DBFieldNames.size();i++){ 
			String DBFieldName=DBFieldNames.get(i);
			String fieldName = fieldNames.get(i);
			
    stringBuffer.append(TEXT_25);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_26);
    stringBuffer.append(DBFieldName );
    stringBuffer.append(TEXT_27);
    stringBuffer.append(fieldName );
    stringBuffer.append(TEXT_28);
    }
    stringBuffer.append(TEXT_29);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_36);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(classFullName );
    stringBuffer.append(TEXT_38);
    stringBuffer.append(tableName );
    stringBuffer.append(TEXT_39);
    for(int i=0;i<DBFieldNames.size();i++){ 
				String DBFieldName=DBFieldNames.get(i);
				String fieldName = fieldNames.get(i);
				
    stringBuffer.append(TEXT_40);
    stringBuffer.append(fieldName );
    stringBuffer.append(TEXT_41);
    stringBuffer.append(DBFieldName );
    stringBuffer.append(TEXT_42);
    stringBuffer.append(fieldName );
    stringBuffer.append(TEXT_43);
    }
    stringBuffer.append(TEXT_44);
    stringBuffer.append(classFullName);
    stringBuffer.append(TEXT_45);
    for(int i=0;i<DBFieldNames.size();i++){ 
				String DBFieldName=DBFieldNames.get(i);
				String fieldName = fieldNames.get(i);
				
    stringBuffer.append(TEXT_46);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(DBFieldName);
    stringBuffer.append(TEXT_48);
    stringBuffer.append(fieldName);
    stringBuffer.append(TEXT_49);
    }
    stringBuffer.append(TEXT_50);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_51);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_53);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_54);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_56);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_57);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_58);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_59);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(tableName);
    stringBuffer.append(TEXT_61);
    stringBuffer.append(className);
    stringBuffer.append(TEXT_62);
    stringBuffer.append(TEXT_63);
    return stringBuffer.toString();
  }
}
