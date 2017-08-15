package com.ay.framework.filter;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @Description
 * @date 2012-10-30
 * @author WangXin
 */
public class CharacterEncodingRequest extends HttpServletRequestWrapper {
	private HttpServletRequest request = null;
	private String serverEncoding=null;

	public CharacterEncodingRequest(HttpServletRequest request,String serverEncoding) {
		super(request);
		this.request = request;
		this.serverEncoding=serverEncoding;
	}

	public String getParameter(String name) {
		String value = super.getParameter(name);
		if (value == null)
			return null;
		String method = request.getMethod();
		if ("get".equalsIgnoreCase(method)) {
			try {
				value = new String(value.getBytes(serverEncoding),
						request.getCharacterEncoding());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

}
