package com.ay.framework.core.pojo;

import com.ay.framework.annotation.PersistenceIgnore;

/**
 * @Description
 * @date 2013-4-23
 * @author WangXin
 */
@SuppressWarnings("all")
public abstract class MapPojo extends BasePojo {
	@PersistenceIgnore
	private String hasMap;

	public String getHasMap() {
		return hasMap;
	}

	@PersistenceIgnore
	private String hasCamera;

	public String getHasCamera() {
		return hasCamera;
	}

	public void setHasCamera(String hasCamera) {
		this.hasCamera = hasCamera;
	}

	public void setHasMap(String hasMap) {
		this.hasMap = hasMap;
	}

	public abstract MapType getMapType();
}
