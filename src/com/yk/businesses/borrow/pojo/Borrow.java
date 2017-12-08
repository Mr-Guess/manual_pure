package com.yk.businesses.borrow.pojo;

import java.util.List;

import com.ay.framework.annotation.ChineseName;
import com.ay.framework.core.pojo.BasePojo;
import com.yk.framecommon.frameRecord.pojo.FrameRecord;

//@ChineseName("借款申请")
public class Borrow extends BasePojo{

	@ChineseName("借款人")
	private String borrower;
	
	@ChineseName("借款人ID")
	private String borrowerId;
	
	@ChineseName("借款事由")
	private String reason;
	
	@ChineseName("借款金额")
	private String amount;
	
	@ChineseName("申请时间")
	private String prayTime;
	
	//流程控制相关
	private String doner;
	
	private String within;
	
	private String step;
	
	//流程名称
	private String frameTyle;
	//审批记录
	private List<FrameRecord> recordList;
	//accid
	private String accid;
	
	public String getAccid() {
		return accid;
	}

	public void setAccid(String accid) {
		this.accid = accid;
	}

	public String getFrameTyle() {
		return frameTyle;
	}

	public void setFrameTyle(String frameTyle) {
		this.frameTyle = frameTyle;
	}

	public List<FrameRecord> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<FrameRecord> recordList) {
		this.recordList = recordList;
	}

	public String getDoner() {
		return doner;
	}

	public void setDoner(String doner) {
		this.doner = doner;
	}

	public String getWithin() {
		return within;
	}

	public void setWithin(String within) {
		this.within = within;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public String getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(String borrowerId) {
		this.borrowerId = borrowerId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPrayTime() {
		return prayTime;
	}

	public void setPrayTime(String prayTime) {
		this.prayTime = prayTime;
	}
	
	
	
}
