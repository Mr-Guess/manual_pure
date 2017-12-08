package com.yk.businesses.reimburse.action;

import java.text.SimpleDateFormat;
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
import com.ay.framework.util.StringUtil;
import com.yk.businesses.borrow.pojo.Borrow;
import com.yk.businesses.reimburse.pojo.Reimburse;
import com.yk.businesses.reimburse.service.ReimburseService;
import com.yk.framecommon.CommonFrameSet;
import com.yk.framecommon.commonDTO.StageDTO;
import com.yk.framecommon.frameRecord.pojo.FrameRecord;
import com.yk.framecommon.frameRecord.service.FrameRecordService;
import com.ay.framework.core.action.BaseAction;

@SuppressWarnings("all")
public class ReimburseAction extends BaseAction {
	private ReimburseService reimburseService;
	private Reimburse reimburse;
	private String page;
	private String rows;
	private String sort;
	private String order;
	private String id;
	private String ids;
	
	/**
	 * 流程相关字段
	 */
	private FrameRecord frameRecord;
	protected final String FRAMENAME = "reimburseFrame";
	private FrameRecordService frameRecordService;
	
	public void add() {
		//StageDTO sd = CommonFrameSet.getFirst(FRAMENAME);
		OperateInfo operateInfo = new OperateInfo(true);
//		reimburse.setDoner(sd.getWhoDone());
//		reimburse.setStep(sd.getStepId());
//		String within = reimburse.getWithin();
//		reimburse.setWithin(within+","+sd.getWhoDone());
		String uuid = StringUtil.getUUID();
		String warterNum = "GT";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		warterNum += sdf.format(new Date());
		warterNum += CommonFrameSet.getWaterNum();
		reimburse.setRemiurserJob(warterNum);
		reimburse.setId(uuid);
		try {
			reimburseService.insert(reimburse);
			operateInfo.setOperateCode(uuid);
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
	public void goFirtst(){
		OperateInfo operateInfo = new OperateInfo();
		StageDTO sd = CommonFrameSet.getFirst(FRAMENAME);
		reimburse.setDoner(sd.getWhoDone());
		reimburse.setStep(sd.getStepId());
		String within = reimburse.getWithin();
		reimburse.setWithin(within+","+sd.getWhoDone());
		reimburse.setStep(sd.getStepId());
		boolean flag = reimburseService.update(reimburse);
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
	
	/**
	 * 流程下一步
	 */
	public void goNext(){
		OperateInfo operateInfo = new OperateInfo();
		StageDTO sd = CommonFrameSet.goNext(reimburse.getStep(), frameRecord);
		reimburse.setDoner(sd.getWhoDone());
		reimburse.setStep(sd.getStepId());
		String within = reimburse.getWithin();
		reimburse.setWithin(within+","+sd.getWhoDone());
		reimburse.setStep(sd.getStepId());
		boolean flag = reimburseService.update(reimburse);
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
		StageDTO sd = CommonFrameSet.goNext(reimburse.getStep(), frameRecord);
		reimburse.setDoner(sd.getWhoDone());
		reimburse.setStep(sd.getStepId());
		String within = reimburse.getWithin();
		reimburse.setWithin(within+","+sd.getWhoDone());
		reimburse.setStep(sd.getStepId());
		reimburse.setStatus("0");
		boolean flag = reimburseService.update(reimburse);
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
		StageDTO sd = CommonFrameSet.goReget(reimburse.getStep(), frameRecord);
		reimburse.setDoner(sd.getWhoDone());
		reimburse.setStep(sd.getStepId());
		String within = reimburse.getWithin();
		reimburse.setWithin(within+","+sd.getWhoDone());
		reimburse.setStep(sd.getStepId());
		boolean flag = reimburseService.update(reimburse);
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
		boolean flag = reimburseService.update(reimburse);
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
			flag = reimburseService.delete(idsParam);
		} else {
			flag = reimburseService.delete(ids);
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
		DataStore<Reimburse> dataStore = new DataStore<Reimburse>();
		Page pageTemp = new Page();
		//当前页 
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		//每页显示条数  
		int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		int start = (intPage -1) * number;
		pageTemp.setCurrentPage(intPage);
		pageTemp.setRecPerPage(number);
		pageTemp.setFrom(start);
		Map paramMap = BeanUtil.Bean2Map(reimburse);
		Page resultPage = reimburseService.pageQuery(paramMap, pageTemp);
		List<Reimburse> resultList = (List<Reimburse>) resultPage.getCollection();
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
		Reimburse ri = reimburseService.getById(id);
		ri.setRecordList(recordList);
		Struts2Utils.renderJson(ri,EncodingHeaderUtil.HEADERENCODING,EncodingHeaderUtil.CACHEENCODING);		
	}
public String exp(){
		String where = " where 1=1 ";
		 Map map = BeanUtil.Bean2Map(reimburse);
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
    public Reimburse getReimburse() {
		return reimburse;
	}

	public void setReimburse(Reimburse reimburse) {
		this.reimburse = reimburse;
	}

	public ReimburseService getReimburseService() {
		return reimburseService;
	}

	public void setReimburseService(ReimburseService reimburseService) {
		this.reimburseService = reimburseService;
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

	public FrameRecordService getFrameRecordService() {
		return frameRecordService;
	}

	public void setFrameRecordService(FrameRecordService frameRecordService) {
		this.frameRecordService = frameRecordService;
	}
	
}
