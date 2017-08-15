package com.ay.framework.filter;

import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.ay.framework.exception.POIException;
import com.ay.framework.exception.SystemException;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @Description
 * @date 2012-11-9
 * @author WangXin
 */
@SuppressWarnings("serial")
public class ExceptionInterceptor extends AbstractInterceptor {
	private static Logger logger = LoggerFactory.getLogger(ExceptionInterceptor.class);
	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		String result = "";
		try {
			result = arg0.invoke();
		} catch (DataAccessException e) {
			logger.error(e.getMessage(),e);
			logger.error(e.getMessage(),e);
			throw new SystemException("数据库操作失败！",e);
		} catch (NullPointerException e) {
			logger.error(e.getMessage(),e);
			throw new SystemException("空指针异常！",e);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			throw new SystemException("IO读写异常！",e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(),e);
			throw new SystemException("指定的类不存在！",e);
		} catch (ArithmeticException e) {
			logger.error(e.getMessage(),e);
			throw new SystemException("数学运算异常！",e);
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.error(e.getMessage(),e);
			throw new SystemException("数组下标越界！",e);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(),e);
			throw new SystemException("调用方法的参数错误！",e);
		} catch (ClassCastException e) {
			logger.error(e.getMessage(),e);
			throw new SystemException("类型强制转换错误！",e);
		} catch (SecurityException e) {
			logger.error(e.getMessage(),e);
			throw new SystemException("违背安全原则异常！",e);
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new SystemException("操作数据库异常！"+e.getMessage(),e);
		} catch (NoSuchMethodError e) {
			logger.error(e.getMessage(),e);
			throw new SystemException("调用了未定义的方法！",e);
		} catch (InternalError e) {
			logger.error(e.getMessage(),e);
			throw new SystemException("Java虚拟机发生了内部错误！",e);
		} catch (POIException e) {
			logger.error(e.getMessage(),e);
			throw new SystemException(e.getMessage(),e);
		} catch (SystemException e) {
			logger.error(e.getMessage(),e);
			throw new SystemException(e.getMessage(),e);		
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SystemException("程序内部错误，操作失败！",e);
		}
		return result;
	}

}
