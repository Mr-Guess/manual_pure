package com.ay.jfds.sys.action;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ay.framework.bean.DataStore;
import com.ay.framework.bean.OperateInfo;
import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.shiro.SystemParameter;
import com.ay.framework.util.EncodingHeaderUtil;
import com.ay.jfds.sys.dto.ParaDTO;
import com.ay.jfds.sys.pojo.SysParameter;
import com.ay.jfds.sys.service.SysParameterService;

/**
 * 系统参数的ACTION
 * 
 * @author PS
 * 
 */
@SuppressWarnings("all")
public class SysParameterAction extends BaseAction {
	private String id;
	private String ids;
	private SysParameterService sysParameterService;
	private SysParameter sysParameter;
    private String page;
    private String rows;
    private String paraName;
    private String paraCode;
    private String paraValue;
    private ParaDTO paraDTO;
    private String[] paramFlagValues = new String[] {"login_limit_times", "login_limit_period", "login_limit_switch", "login_onlyone_switch", "login_authcode_digit", "login_authcode_type", "login_authcode_switch"};
    private static Logger logger = LoggerFactory.getLogger(SysParameter.class);
	/**
	 * 增加系统参数方法
	 */
	public void addSysParameter() {
		OperateInfo operateInfo = new OperateInfo();
		sysParameter.setCreated((String) SecurityUtils.getSubject()
				.getSession().getAttribute("user_id"));
		sysParameter.setCreateTime(new Date());
		try {
			sysParameterService.insert(sysParameter);
			operateInfo.setOperateMessage("添加系统参数成功");
			operateInfo.setOperateSuccess(true);
		} catch (Exception e) {
			operateInfo.setOperateSuccess(false);
			operateInfo.setOperateMessage("添加系统参数失败");
			e.printStackTrace();
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

	/**
	 * 更新系统参数
	 * @throws ClassNotFoundException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 */
	public void updateSysParameter() throws ClassNotFoundException, IllegalArgumentException, SecurityException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		OperateInfo operateInfo = new OperateInfo();
		for (String temp : paramFlagValues) {
			SysParameter upSysParameter = sysParameterService.findValueByCode(temp);
			String getMethodName = "get" + temp.substring(0, 1).toUpperCase() + temp.substring(1);
			Class<?> clazz = Class.forName("com.ay.jfds.sys.dto.ParaDTO");
			upSysParameter.setParaValue((String) clazz.getMethod(getMethodName, new Class<?>[]{}).invoke(paraDTO, new Object[]{}));
			upSysParameter.setStatus("1");
			boolean falg = sysParameterService.update(upSysParameter);
			if (falg) {
				operateInfo.setOperateSuccess(true);
				operateInfo.setOperateMessage("更新成功");
			} else {
				operateInfo.setOperateSuccess(false);
				operateInfo.setOperateMessage("更新失败");
			}
		}
		SystemParameter.clearValue();
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
		
	}
	
	 
	/**
	 * 删除
	 */
	public void deleteSysParameter() {
		OperateInfo operateInfo = new OperateInfo();
		boolean falg = sysParameterService.delete(sysParameter);
		if (falg) {
			operateInfo.setOperateSuccess(true);
			operateInfo.setOperateMessage("删除成功");
		} else {
			operateInfo.setOperateSuccess(false);
			operateInfo.setOperateMessage("删除失败");
		}
		Struts2Utils.renderJson(operateInfo, EncodingHeaderUtil.CACHEENCODING,
				EncodingHeaderUtil.HEADERENCODING);
	}
	
	/**
	 * 根据id 删除
	 */
	public void deleteSysParameterById() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = sysParameterService.delete(sysParameter);
		if (flag) {
			operateInfo.setOperateSuccess(true);
			operateInfo.setOperateMessage("删除成功");
		} else {
			operateInfo.setOperateSuccess(false);
			operateInfo.setOperateMessage("删除失败");
		}
		Struts2Utils.renderJson(operateInfo, EncodingHeaderUtil.CACHEENCODING,
				EncodingHeaderUtil.HEADERENCODING);
	}

	public void getById() {
		SysParameter sysParameter = null;
		sysParameter = sysParameterService.getById(id);
		String json = new JsonMapper().toJson(sysParameter);
		Struts2Utils.renderText(json);
	}

	public void getByIds() {
		String idsParam[] = ids.split("[ , ]");
		SysParameter sysParameter = null;
		sysParameter = sysParameterService.getById(idsParam);
		String json = new JsonMapper().toJson(sysParameter);
		Struts2Utils.renderText(json);
	}
	
	
    public void pageList(){
        DataStore<SysParameter> dataStore = new DataStore<SysParameter>();
        Page pageTemp = new Page();
        int intPage = Integer.parseInt((page == null || page == "0") ? "1"
                : page);
        // 每页显示条数 
        int number = Integer.parseInt((rows == null || rows == "0") ? "10"
                : rows);
        int start = (intPage - 1) * number;
        pageTemp.setCurrentPage(start);
        pageTemp.setRecPerPage(number);
        Map paramMap = new HashMap();
        paramMap.put("paraName", paraName);
        paramMap.put("paraCode", paraCode);
        paramMap.put("paraValue", paraValue);
        Page resultPage = sysParameterService.pageQuery(paramMap,pageTemp);
        List<SysParameter> resultList = (List<SysParameter>) resultPage.getCollection();
        dataStore.setTotal(new Long(resultPage.getCount()));
        dataStore.setRows(resultList);
       String json = new JsonMapper().toJson(dataStore);
       Struts2Utils.renderText(json);
    }
    /**
     * 获得参数
     */
    public void getAllObject() {
    	List<SysParameter> list = sysParameterService.findAll();
    	String jsonObj = new JsonMapper().toJson(list);
    	Collections.sort(list);
    	Struts2Utils.renderText(jsonObj);
    }

	/**
	 * 删除多个类型
	 */
	public void deleteByIds() {
		String idsParam[] = ids.split("[,]");
		OperateInfo operateInfo = new OperateInfo();
		boolean falg = sysParameterService.delete(idsParam);
		if (falg) {
			operateInfo.setOperateSuccess(true);
			operateInfo.setOperateMessage("删除成功");
		} else {
			operateInfo.setOperateSuccess(false);
			operateInfo.setOperateMessage("删除失败");
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SysParameterService getSysParameterService() {
		return sysParameterService;
	}

	public void setSysParameterService(SysParameterService sysParameterService) {
		this.sysParameterService = sysParameterService;
	}

	public SysParameter getSysParameter() {
		return sysParameter;
	}

	public void setSysParameter(SysParameter sysParameter) {
		this.sysParameter = sysParameter;
	}

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getParaName() {
        return paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    public String getParaCode() {
        return paraCode;
    }

    public void setParaCode(String paraCode) {
        this.paraCode = paraCode;
    }

    public String getParaValue() {
        return paraValue;
    }

    public void setParaValue(String paraValue) {
        this.paraValue = paraValue;
    }

	public ParaDTO getParaDTO() {
		return paraDTO;
	}

	public void setParaDTO(ParaDTO paraDTO) {
		this.paraDTO = paraDTO;
	}
    
   
}
