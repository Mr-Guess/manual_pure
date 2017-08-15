package com.ay.jfds.dev.pojo;

import java.sql.Date;
import com.ay.framework.core.pojo.BasePojo;

/**
 * 
 * @author 软件工程部产品小组
 */
public class Emp extends BasePojo  { 
    private String name;
    private String password;
    private Integer age;
    private Date year;

    public void setName(String name){
    	this.name=name;
    }
    public String getName(){
    	return	this.name;
    }
    public void setPassword(String password){
    	this.password=password;
    }
    public String getPassword(){
    	return	this.password;
    }
    public void setAge(Integer age){
    	this.age=age;
    }
    public Integer getAge(){
    	return	this.age;
    }
    public void setYear(Date year){
    	this.year=year;
    }
    public Date getYear(){
    	return	this.year;
    }

}