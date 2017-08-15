package com.ay.framework.core.action;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.ay.framework.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * BaseAction是Action的基类，继承ActionSupport，同时可以扩展一些通用的业务
 * 
 * @author <a href="mailto:fanwenqiang@nj.fiberhome.com.cn">AndyFan*</a>
 */
@SuppressWarnings("all")
public class BaseAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware, SessionAware {
	protected String excelQuerySql;
	protected String[] excelHeads;
	protected String excelSheetName;

	protected String excelInsertSql;
	protected File excelFile;

	protected String freemarkerPath;

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected Map<String, Object> session;
	protected Map<String, Object> valueMap = new HashMap<String, Object>();

	/**
	 * 根据传入的属性生成参数Map.
	 * 
	 * @param params
	 *            the params
	 * @return the map< string, object>
	 */
	public Map<String, Object> toParameterMap(String... params) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (String key : params) {
			try {
				Object value = BeanUtils.getProperty(this, key);
				map.put(key, value);
				if (value instanceof String) {
					map.put(key + "_like",
							"%"
									+ StringUtil.escapeSpecialCharacters(value
											.toString()) + "%");
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	public String getExcelQuerySql() {
		return excelQuerySql;
	}

	public void setExcelQuerySql(String excelQuerySql) {
		this.excelQuerySql = excelQuerySql;
	}

	public String getExcelInsertSql() {
		return excelInsertSql;
	}

	public void setExcelInsertSql(String excelInsertSql) {
		this.excelInsertSql = excelInsertSql;
	}

	public String[] getExcelHeads() {
		return excelHeads;
	}

	public void setExcelHeads(String[] excelHeads) {
		this.excelHeads = excelHeads;
	}

	public String getExcelSheetName() {
		return excelSheetName;
	}

	public void setExcelSheetName(String excelSheetName) {
		this.excelSheetName = excelSheetName;
	}

	public File getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	public String getFreemarkerPath() {
		return freemarkerPath;
	}

	public void setFreemarkerPath(String freemarkerPath) {
		this.freemarkerPath = freemarkerPath;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		response = arg0;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}

	public void setSession(Map<String, Object> arg0) {
		session = arg0;
	}

	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public HttpSession getSession() {
		return getRequest().getSession();
	}

	public ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	public String getRealyPath(String path) {
		return getServletContext().getRealPath(path);
	}

	public Map<String, Object> getValueMap() {
	    return valueMap;
	}
	public void setValueMap(Map<String, Object> valueMap) {
	    this.valueMap = valueMap;
	}
}
