package com.ay.jfds.sys.service;

import java.util.List;

import com.ay.framework.core.service.BaseService;
import com.ay.jfds.sys.dao.SysParameterDao;
import com.ay.jfds.sys.pojo.SysParameter;

/**
 * @author PS 系统参数SERVICE层
 */
public class SysParameterService extends
		BaseService<SysParameter, SysParameterDao> {

    /**
     * 根据值找到 参数代码
     * @param code
     * @return
     */
	public SysParameter findValueByCode( String code) {
		return this.getDao().getValueByParaCode(code);
	}

}
