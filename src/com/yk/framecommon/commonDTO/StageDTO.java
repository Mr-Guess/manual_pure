package com.yk.framecommon.commonDTO;

public class StageDTO {
	
	public StageDTO(String whoDone,String stepId){
		this.whoDone = whoDone;
		this.stepId = stepId;
	}

	private String whoDone;
	
	private String stepId;

	public String getWhoDone() {
		return whoDone;
	}

	public void setWhoDone(String whoDone) {
		this.whoDone = whoDone;
	}

	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
	}
	
}
