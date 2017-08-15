package com.ay.jfds.core;

import java.util.List;

import com.ay.jfds.dev.dto.FormFieldDTO;

public class ChildListDTO {

    /** 表名 */
    private String tableName;
    
    private List<FormFieldDTO> formDTO;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<FormFieldDTO> getFormDTO() {
		return formDTO;
	}

	public void setFormDTO(List<FormFieldDTO> formDTO) {
		this.formDTO = formDTO;
	}
    
    

    
}
