package com.ay.jfds.sys.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ay.framework.core.dao.BaseDao;
import com.ay.jfds.sys.pojo.UploadFile;

public class UploadFileDao extends BaseDao<UploadFile>{

	@Override
	public String getTableName() {
		return "TB_UPLOAD_FILE";
	}

	@Override
	public String getEntityName() {
		return "uploadFile";
	}

	/**
	 * 根据关联id获得所有的文件
	 * @param relationId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<UploadFile> getFileListByRelationId(String relationId) {
		Map map = new HashMap();
		map.put("relationId", relationId);
		List<UploadFile> list = (List<UploadFile>)getSqlMapClientTemplate().queryForList(getEntityName() +".findByRelationId", map);
		return list;
	}

	
}
