package com.yk.businesses.borrow.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;

import com.ay.framework.bean.DataStore;
import com.ay.framework.bean.OperateInfo;
import com.ay.framework.core.dao.pagination.Page;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.util.BeanUtil;
import com.ay.framework.util.EncodingHeaderUtil;
import com.yk.businesses.borrow.pojo.Borrow;
import com.yk.businesses.borrow.service.BorrowService;
import com.yk.framecommon.CommonFrameSet;
import com.yk.framecommon.commonDTO.StageDTO;
import com.yk.framecommon.frameRecord.pojo.FrameRecord;
import com.yk.framecommon.frameRecord.service.FrameRecordService;
import com.ay.framework.core.action.BaseAction;

@SuppressWarnings("all")
public class BorrowAction extends BaseAction {
	private BorrowService borrowService;
	
	private Borrow borrow;
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String id;
	private String ids;
	private String userId;
	
	/**
	 * 流程相关字段
	 */
	private FrameRecord frameRecord;
	protected final String FRAMENAME = "testFrame";
	private FrameRecordService frameRecordService;
	
	public void add() {
		OperateInfo operateInfo = new OperateInfo(true);
		try {
			StageDTO sd = CommonFrameSet.getFirst(FRAMENAME);
			borrow.setDoner(sd.getWhoDone());
			borrow.setStep(sd.getStepId());
			String within = borrow.getWithin();
			borrow.setWithin(within+","+sd.getWhoDone());
			borrowService.insert(borrow);
			operateInfo.setOperateMessage("添加成功");
			operateInfo.setOperateSuccess(true);
		} catch (Exception e) {
			operateInfo.setOperateMessage("添加失败");
			operateInfo.setOperateSuccess(false);
		}
		
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	
	
	/**
	 * 流程下一步
	 */
	public void goNext(){
		OperateInfo operateInfo = new OperateInfo();
		StageDTO sd = CommonFrameSet.goNext(borrow.getStep(), frameRecord);
		borrow.setDoner(sd.getWhoDone());
		borrow.setStep(sd.getStepId());
		String within = borrow.getWithin();
		borrow.setWithin(within+","+sd.getWhoDone());
		borrow.setStep(sd.getStepId());
		boolean flag = borrowService.update(borrow);
		if (flag) {
			operateInfo.setOperateMessage("更新成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("更新失败");
			operateInfo.setOperateSuccess(false);
		}
		
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	
	public void breakFrame(){
		OperateInfo operateInfo = new OperateInfo();
		StageDTO sd = CommonFrameSet.goNext(borrow.getStep(), frameRecord);
		borrow.setDoner(sd.getWhoDone());
		borrow.setStep(sd.getStepId());
		String within = borrow.getWithin();
		borrow.setWithin(within+","+sd.getWhoDone());
		borrow.setStep(sd.getStepId());
		borrow.setStatus("0");
		boolean flag = borrowService.update(borrow);
		if (flag) {
			operateInfo.setOperateMessage("更新成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("更新失败");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	
	public void regetFrame(){
		OperateInfo operateInfo = new OperateInfo();
		StageDTO sd = CommonFrameSet.goReget(borrow.getStep(), frameRecord);
		borrow.setDoner(sd.getWhoDone());
		borrow.setStep(sd.getStepId());
		String within = borrow.getWithin();
		borrow.setWithin(within+","+sd.getWhoDone());
		borrow.setStep(sd.getStepId());
		boolean flag = borrowService.update(borrow);
		if (flag) {
			operateInfo.setOperateMessage("更新成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("更新失败");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	
	public void update() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = borrowService.update(borrow);
		if (flag) {
			operateInfo.setOperateMessage("更新成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("更新失败");
			operateInfo.setOperateSuccess(false);
		}
		
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	
	public void delete() {
		OperateInfo operateInfo = new OperateInfo();
		boolean flag = false;
		if (ids.contains(",")) {
			String idsParam[] = ids.split(",");
			flag = borrowService.delete(idsParam);
		} else {
			flag = borrowService.delete(ids);
		}
		if (flag) {
			operateInfo.setOperateMessage("删除成功");
			operateInfo.setOperateSuccess(true);
		} else {
			operateInfo.setOperateMessage("删除失败");
			operateInfo.setOperateSuccess(false);
		}
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);		
	}
	
	public void list() {
		DataStore<Borrow> dataStore = new DataStore<Borrow>();
		Page pageTemp = new Page();
		//当前页 
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		//每页显示条数  
		int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		int start = (intPage -1) * number;
		pageTemp.setCurrentPage(intPage);
		pageTemp.setRecPerPage(number);
		pageTemp.setFrom(start);
		Map paramMap = BeanUtil.Bean2Map(borrow);
		Page resultPage = borrowService.pageQuery(paramMap, pageTemp);
		List<Borrow> resultList = (List<Borrow>) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		String json = new JsonMapper().toJson(dataStore);
		Struts2Utils.renderText(json);
	}
	
	public void myList() {
		DataStore<Borrow> dataStore = new DataStore<Borrow>();
		Page pageTemp = new Page();
		//当前页 
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		//每页显示条数  
		int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		int start = (intPage -1) * number;
		pageTemp.setCurrentPage(intPage);
		pageTemp.setRecPerPage(number);
		pageTemp.setFrom(start);
		Borrow b = new Borrow();
		b.setBorrowerId(userId);
		Map paramMap = BeanUtil.Bean2Map(b);
		Page resultPage = borrowService.pageQuery(paramMap, pageTemp);
		List<Borrow> resultList = (List<Borrow>) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		String json = new JsonMapper().toJson(dataStore);
		Struts2Utils.renderText(json);
	}
	
	public void judgeList() {
		DataStore<Borrow> dataStore = new DataStore<Borrow>();
		Page pageTemp = new Page();
		//当前页 
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		//每页显示条数  
		int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		int start = (intPage -1) * number;
		pageTemp.setCurrentPage(intPage);
		pageTemp.setRecPerPage(number);
		pageTemp.setFrom(start);
		Borrow b = new Borrow();
		b.setDoner(userId);
		Map paramMap = BeanUtil.Bean2Map(b);
		Page resultPage = borrowService.pageQuery(paramMap, pageTemp);
		List<Borrow> resultList = (List<Borrow>) resultPage.getCollection();
		dataStore.setTotal(new Long(resultPage.getCount()));
		dataStore.setRows(resultList);
		String json = new JsonMapper().toJson(dataStore);
		Struts2Utils.renderText(json);
	}
	
	public void getById() {
		FrameRecord fr = new FrameRecord();
		fr.setConcatData(id);
		Map paramMap = BeanUtil.Bean2Map(fr);
		List<FrameRecord> recordList = frameRecordService.findAll(paramMap);
		Borrow br = borrowService.getById(id);
		br.setRecordList(recordList);
		Struts2Utils.renderJson(br,EncodingHeaderUtil.HEADERENCODING,EncodingHeaderUtil.CACHEENCODING);
	}
	
	
public String exp(){
		String where = " where 1=1 ";
		 Map map = BeanUtil.Bean2Map(borrow);
		if (map != null){
			for (Object o : map.keySet()){
				where += " and " + o.toString() + " like '%" + map.get(o) + "%' ";
			}
		}
excelQuerySql = "		select * from sys_user" + where;
		System.out.println(excelQuerySql);
		excelSheetName = "测试名称";
		excelHeads = new String[]{ "名字1", "名字2", "名字3" };
		return "exp";
}	
    public Borrow getBorrow() {
		return borrow;
	}

	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}

	public BorrowService getBorrowService() {
		return borrowService;
	}

	public void setBorrowService(BorrowService borrowService) {
		this.borrowService = borrowService;
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

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}


	public FrameRecord getFrameRecord() {
		return frameRecord;
	}


	public void setFrameRecord(FrameRecord frameRecord) {
		this.frameRecord = frameRecord;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public FrameRecordService getFrameRecordService() {
		return frameRecordService;
	}


	public void setFrameRecordService(FrameRecordService frameRecordService) {
		this.frameRecordService = frameRecordService;
	}
	
	
	
}
