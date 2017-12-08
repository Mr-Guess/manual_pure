package com.ay.netEasy.push;

public class AttachMsg {
	//"{\"mesmodel\":\"reporting\", \"messageId\":\"9527\",\"mestitle\":\"你大爷\",\"mestext\":\"你大爷\"}"
	
	/**
	 * 重写构造函数，快速创建
	 * 
	 * @param mesmodel
	 * @param messageId
	 * @param mestitle
	 * @param mestext
	 */
	public AttachMsg(String mesmodel,String messageId,String mestitle,String mestext,int mesid){
		this.setMesmodel(mesmodel);
		this.setMessageId(messageId);
		this.setMestitle(mestitle);
		this.setMestext(mestext);
		this.setMesid(mesid);
	}
	private String mesmodel;
	private String messageId;
	private String mestitle;
	private String mestext;
	private int mesid;
	public int getMesid() {
		return mesid;
	}
	public void setMesid(int mesid) {
		this.mesid = mesid;
	}
	public String getMesmodel() {
		return mesmodel;
	}
	public void setMesmodel(String mesmodel) {
		this.mesmodel = mesmodel;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getMestitle() {
		return mestitle;
	}
	public void setMestitle(String mestitle) {
		this.mestitle = mestitle;
	}
	public String getMestext() {
		return mestext;
	}
	public void setMestext(String mestext) {
		this.mestext = mestext;
	}
	
	
	
}
