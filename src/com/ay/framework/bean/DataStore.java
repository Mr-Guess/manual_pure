package com.ay.framework.bean;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.common.collect.Lists;

/**
 * 供Struts 向客户端写成json数据
 * 
 * @author zxy
 *
 * @param <T>
 */
public class DataStore<T extends Serializable> {
	/** DataStore里的数据量 */
	private Long total;
	
	/** DataStore里的具体数据 */
	private List<T> rows = Lists.newArrayList();

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
