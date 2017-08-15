package com.ay.framework.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * HTML特殊字符过滤
 * @author WangXin
 *
 */
public class HTMLCharacterFilter implements Filter {

    public HTMLCharacterFilter() {
    }
	public void destroy() {
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		chain.doFilter(new HTMLCharacterRequest(req), response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
