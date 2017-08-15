package com.ay.framework.core.dao.pagination;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 一览页面通用分页对象 添加了两个构造器： 1.内存分页。<br>
 * 所谓内存分页只是针对小数据量的简单封装，在处理分页时我们只针对结果集合进行分页。 2.数据库层分页。
 * 所谓数据层分页，只针对sql中实现最根本的分页，对于巨大数据量能
 * 
 * @author lushigai
 * @version 1.0
 * @Date:2012-9-10 修改人：lijie 修改时间：2013-1-21 修改内容：添加根据当前页与页面显示条数返回分页page对象
 */
@SuppressWarnings("all")
public class Page {
	/***/

	private Log log = LogFactory.getLog(this.getClass());
	/** 当前页码，从1开始 */
	private int currentPage = 0;
	/** 每页记录数,缺省值为20 */
	private int recPerPage = 10;
	/***/
	private String formName = null;
	/** 记录总数 */
	private int count = 0;
	/** 本页对象中的数据集合 */
	private Collection collection = null;
	/** 共有几页 */
	private int totalPage = 0;
	/** 本页起始记录号 */
	private int from = 0;
	/** 本页结束记录号 */
	private int to = 0;

	/**
	 * 默认构造form名称是 name=form1
	 */
	public Page() {
		this.formName = "form1";
	}

	/**
	 * 
	 * @param formName
	 *            表单name名称formName
	 */
	public Page(String formName) {
		this.formName = formName;
	}

	/**
	 * 获取一页信息
	 * 
	 * @return collection
	 */
	public Collection getOnePage() {
		return collection;
	}

	/**
	 * 计算分页信息
	 * 
	 * @param count
	 *            count
	 */
	private void calculatingPage(int count) {
		// 特殊情况下 当有记录但是没有页数还是0，这种情况我们就设置成第1页
		if (this.currentPage <= 0 && count > 0) {
			this.currentPage = 1;
			this.currentPage = 1;
		}
		this.count = count;
		// 如果总记录数大于0并且当前页大于0
		if (count > 0 && this.currentPage > 0) {
			// 获取总页数-> 总记录-1+分页单位 再除以 分页单位
			this.totalPage = (count - 1 + recPerPage) / recPerPage;
			// 如果当前页大于总页数，我们就换到第1页
			if (this.totalPage < this.currentPage) {
				this.currentPage = 1;
			}
			// 获取第一条开始
			this.from = recPerPage * (this.currentPage - 1);
			// 判断最后一个不能整除的时候。to直接取总数。
			if (recPerPage * this.currentPage > count) {
				this.to = count;
			} else {
				this.to = recPerPage * this.currentPage;
			}
		} else {
			this.from = 0;
			this.to = 0;
			this.currentPage = 0;
			this.totalPage = 0;
		}
	}

	/**
	 * 数据集合
	 * 
	 * @return Returns the collection.
	 */
	public Collection getCollection() {
		return collection;
	}

	/**
	 * 获取每页几条
	 * 
	 * @return int
	 */
	public int getRecPerPage() {
		return recPerPage;
	}

	/**
	 * 设置每页几条
	 * 
	 * @param recPerPage
	 *            recPerPage
	 */
	public void setRecPerPage(int recPerPage) {
		this.recPerPage = recPerPage;
	}

	/**
	 * 数据集合
	 * 
	 * @param collection
	 *            The collection to set.
	 */
	public void setCollection(Collection collection) {
		this.collection = collection;
	}

	/**
	 * 记录总数
	 * 
	 * @return Returns the count.
	 */
	public int getCount() {
		return count;
	}

	/**
	 * 记录总数
	 * 
	 * @param count
	 *            The count to set.
	 */
	public void setCount(int count) {
		this.count = count;
		// 计算分页
		this.calculatingPage(count);
	}

	/**
	 * 当前页码
	 * 
	 * @return Returns the currentPage.
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * 当前页码
	 * 
	 * @param currentPage
	 *            The currentPage to set.
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 每页记录数
	 * 
	 * @return Returns the prePageNum.
	 */
	public int getPrePageNum() {
		return recPerPage;
	}

	/**
	 * 每页记录数
	 * 
	 * @param prePageNum
	 *            The prePageNum to set.
	 */
	public void setPrePageNum(int prePageNum) {
		this.recPerPage = prePageNum;
	}

	/**
	 * 共几页
	 * 
	 * @return Returns the totalPage.
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * 共几页
	 * 
	 * @param totalPage
	 *            The totalPage to set.
	 */
	public void setTotalPage(int totalPage) {

		this.totalPage = totalPage;
	}

	/**
	 * 判断是否需要分页
	 * 
	 * @return 是否需要分页
	 */
	public boolean isMore() {
		return count > recPerPage;
	}

	/**
	 * 获取从第几条开始
	 * 
	 * @return Returns the from.
	 */
	public int getFrom() {
		return from;
	}

	/**
	 * 设置从第几条开始
	 * 
	 * @param from
	 *            The from to set.
	 */
	public void setFrom(int from) {
		this.from = from;
	}

	/**
	 * 获取第几条结束
	 * 
	 * @return Returns the to.
	 */
	public int getTo() {
		return to;
	}

	/**
	 * 设置到第几条记录结束
	 * 
	 * @param to
	 *            The to to set.
	 */
	public void setTo(int to) {
		this.to = to;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	/**
	 * 函数功能说明 lijie 2013-1-21 修改内容
	 * 
	 * @param page
	 *            当前页
	 * @param rows
	 *            rows 每页显示条数 
	 * @param @return   
	 * @return 分页对象   
	 * @throws
	 */
	public static Page getNewInstance(String page, String rows) {
		Page pageTemp = new Page();
		int currentpage = Integer.parseInt((page == null || page == "0") ? "1"
				: page);
		int number = Integer.parseInt((rows == null || rows == "0") ? "10"
				: rows);
		int start = (currentpage - 1) * number;
		pageTemp.setCurrentPage(currentpage);
		pageTemp.setRecPerPage(number);
		pageTemp.setFrom(start);
		return pageTemp;
	}
}
