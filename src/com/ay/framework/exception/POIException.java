package com.ay.framework.exception;

/** 
 * @Description 
 * @date 2012-11-8
 * @author WangXin
 */
@SuppressWarnings("serial")
public class POIException extends Exception {

	public POIException() {
		super();
	}

	public POIException(String message, Throwable cause) {
		super(message, cause);
	}

	public POIException(String message) {
		super(message);
	}

	public POIException(Throwable cause) {
		super(cause);
	}
	
}

