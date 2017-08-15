package com.ay.jfds.sys.service;

import java.util.List;

import com.ay.framework.core.service.BaseService;
import com.ay.jfds.sys.dao.UploadFileDao;
import com.ay.jfds.sys.pojo.UploadFile;

public class UploadFileService extends BaseService<UploadFile,UploadFileDao>{

	/**
	 * 根据关联id获得所有的文件
	 * @param relationId
	 * @return
	 */
	public List<UploadFile> getFileListByRelationId(String relationId){
		List<UploadFile> list = this.dao.getFileListByRelationId(relationId);
		return list;
	}
}
