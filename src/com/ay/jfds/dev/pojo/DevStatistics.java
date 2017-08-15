package com.ay.jfds.dev.pojo;

import java.sql.Date;
import com.ay.framework.core.pojo.BasePojo;

/**
 * 
 * @author 软件工程部产品小组
 */
public class DevStatistics extends BasePojo  { 
    private String charttitle;
    private String xtitle;
    private String ytitle;
    private Integer charttype;
    private Boolean transposed;
    private String sqlcommand;

    public void setCharttitle(String charttitle){
    	this.charttitle=charttitle;
    }
    public String getCharttitle(){
    	return	this.charttitle;
    }
    public void setXtitle(String xtitle){
    	this.xtitle=xtitle;
    }
    public String getXtitle(){
    	return	this.xtitle;
    }
    public void setYtitle(String ytitle){
    	this.ytitle=ytitle;
    }
    public String getYtitle(){
    	return	this.ytitle;
    }
    public void setCharttype(Integer charttype){
    	this.charttype=charttype;
    }
    public Integer getCharttype(){
    	return	this.charttype;
    }
    public void setTransposed(Boolean transposed){
    	this.transposed=transposed;
    }
    public Boolean getTransposed(){
    	return	this.transposed;
    }
    public void setSqlcommand(String sqlcommand){
    	this.sqlcommand=sqlcommand;
    }
    public String getSqlcommand(){
    	return	this.sqlcommand;
    }

}