package com.yk.framecommon;

import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.dao.BaseDao;
import com.ay.framework.core.pojo.BasePojo;
import com.ay.framework.core.service.BaseService;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.jfds.sys.pojo.Department;
import com.ay.jfds.sys.pojo.User;
import com.ay.jfds.sys.service.DepartmentService;
import com.ay.jfds.sys.service.SysParameterService;
import com.ay.jfds.sys.service.UserService;
import com.yk.framecommon.commonDTO.StageDTO;
import com.yk.framecommon.frameControl.pojo.FrameControl;
import com.yk.framecommon.frameControl.service.FrameControlService;
import com.yk.framecommon.frameRecord.pojo.FrameRecord;
import com.yk.framecommon.frameRecord.service.FrameRecordService;

public class CommonFrameSet extends BaseAction{
	
	private String reId;
	private String stpId;
	private String fid;
	private String uid;
	 
	/**
	 * 线性流程获取下一步工作
	 * @param stepId
	 * @return
	 */
	
	public static StageDTO getNext(String stepId){
		FrameControlService fs = SpringContextHolder.getBean("frameControlService");
		FrameControl fc = fs.getNextFrame(stepId);
		return new StageDTO(fc.getDesinerId(), fc.getId());
	}
	
	public void getNext(){
		FrameControlService fs = SpringContextHolder.getBean("frameControlService");
		FrameControl fc = fs.getNextFrame(stpId);
		Struts2Utils.renderJson(fc);
	}
	/**
	 * 获取上一步工作
	 * @param stepId
	 * @return
	 */
	public static StageDTO getBack(String stepId){
		FrameControlService fs = SpringContextHolder.getBean("frameControlService");
		FrameControl fc = fs.getBackFrame(stepId);
		return new StageDTO(fc.getDesinerId(), fc.getId());
	}
	public void getBack(){
		FrameControlService fs = SpringContextHolder.getBean("frameControlService");
		FrameControl fc = fs.getBackFrame(stpId);
		Struts2Utils.renderJson(fc);
	}
	
	
	/**
	 * 获取第一步流程
	 * @param relationId
	 * @return
	 */
	public static StageDTO getFirst(String relationId){
		FrameControlService fs = SpringContextHolder.getBean("frameControlService");
		FrameControl fc = fs.getFirstFrame(relationId);
		return new StageDTO(fc.getDesinerId(), fc.getId());
	}
	
	public static StageDTO getFirst(String relationId,String userId){
		FrameControlService fs = SpringContextHolder.getBean("frameControlService");
		FrameControl fc = fs.getFirstFrame(relationId);
		StageDTO sd = null;
		if(fc.getDesinerId().equals("autoDesiner")){
			UserService us = SpringContextHolder.getBean("userService"); 
			User nowUser =us.getById(userId);
			Department beloneDept = us.getBelongDept(nowUser.getDeptId());
			User user = us.getDeptLeader(beloneDept.getId());
			if(user != null && user.getAccount().equals(nowUser.getAccount())){
				Department dept = us.getBelongDept(beloneDept.getId());
				user = us.getDeptLeader(dept.getId());
			}else if(user == null){
				user = nowUser;
			}
			sd = new StageDTO(user.getId(), fc.getId());
		}else{
			sd = new StageDTO(fc.getDesinerId(), fc.getId());
		}
		return sd;
	}
	
	
	/**
	 * 手机端获取第一步流程展示
	 */
	public void getFirst(){
		FrameControlService fs = SpringContextHolder.getBean("frameControlService");
		FrameControl fc = fs.getFirstFrame(reId);
		Struts2Utils.renderJson(fc);
	}
	
	public static StageDTO goNext(String stepId,FrameRecord fr){
		FrameControlService fs = SpringContextHolder.getBean("frameControlService");
		FrameControl fc = fs.getNextFrame(stepId);
		FrameRecordService frs = SpringContextHolder.getBean("frameRecordService");
		frs.insert(fr);
		if(fc != null){
			return new StageDTO(fc.getDesinerId(), fc.getId());
		}else{
			return new StageDTO("alreadyEnd","alreadyEnd");
		}
	}
	
	/**
	 * 获取当前流程所有节点
	 * 
	 */
	public static void getFrameSet(){
		
	}
	
	public static StageDTO goNext(String stepId,FrameRecord fr,String userId){
		FrameControlService fs = SpringContextHolder.getBean("frameControlService");
		FrameControl fc = fs.getNextFrame(stepId);
		FrameRecordService frs = SpringContextHolder.getBean("frameRecordService");
		frs.insert(fr);
		StageDTO sd = null;
		if(fc != null){
			if(fc.getDesinerId().equals("autoDesiner")){
				UserService us = SpringContextHolder.getBean("userService"); 
				User nowUser =us.getById(userId);
				Department beloneDept = us.getBelongDept(nowUser.getDeptId());
				User user = us.getDeptLeader(beloneDept.getId());
				if(user != null && user.getAccount().equals(nowUser.getAccount())){
					Department dept = us.getBelongDept(beloneDept.getId());
					user = us.getDeptLeader(dept.getId());
				}else if(user == null){
					user = nowUser;
				}
				sd = new StageDTO(user.getId(), fc.getId());
			}else{
				sd = new StageDTO(fc.getDesinerId(), fc.getId());
			}
			return sd;
		}else{
			return new StageDTO("alreadyEnd","alreadyEnd");
		}
	}
	
	public static StageDTO goReget(String stepId,FrameRecord fr){
		FrameRecordService frs = SpringContextHolder.getBean("frameRecordService");
		frs.insert(fr);
		return new StageDTO("alreadyreget","alreadyreget");
	}
	
	/**
	 * 获取流水号
	 * @return
	 */
	public static String getWaterNum(){
		SysParameterService sps = SpringContextHolder.getBean("sysParameterService");
		int it = sps.warterNum();
		String rtn = "";
		if(sps.setwarterNum()){
			if(it < 10){
				rtn = "00"+it;
			}else if(it<100 && it>=10){
				rtn = "0"+it;
			}else{
				rtn += it;
			}
		}
		return rtn; 
	}

	public String getReId() {
		return reId;
	}

	public void setReId(String reId) {
		this.reId = reId;
	}

	public String getStpId() {
		return stpId;
	}

	public void setStpId(String stpId) {
		this.stpId = stpId;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	
}
