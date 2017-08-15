package com.ay.framework.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ay.framework.filter.tomcat.TomcatServer;

/**
 * Servlet Filter implementation class CharEncodingFilter
 */
public class CharEncodingFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(CharEncodingFilter.class);
	private String encoding="UTF-8";
	public static String serverEncoding="ISO-8859-1";
    /**
     * Default constructor. 
     */
    public CharEncodingFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);  
		response.setCharacterEncoding(encoding);
        response.setContentType("text/html;charset=" + encoding);  
		HttpServletRequest req=(HttpServletRequest) request;
		String uri=req.getRequestURI();
        if (!uri.toLowerCase().endsWith("jsp") && req.getMethod().equalsIgnoreCase("get")) {
            Map paramMap = req.getParameterMap();  
            String[] queryStringArray = { "" };  
            if (req.getQueryString() != null) {  
                queryStringArray = req.getQueryString().split("&");  
            }  
            for (int i = 0; i < queryStringArray.length; i++) {  
                queryStringArray[i] = queryStringArray[i].replaceAll("(.*)=(.*)", "$1");  
            }  
            Set<String> keySet = paramMap.keySet();  
            for (String key : keySet) {  
                // check where param from  
                boolean isFromGet = false;  
                for (String paramFromGet : queryStringArray) {  
                    if (key.equals(paramFromGet)) {  
                        isFromGet = true;  
                    }  
                }
                if (!isFromGet) {  
                    continue;  
                }  
                String[] paramArray = (String[]) paramMap.get(key);  
                for (int i = 0; i < paramArray.length; i++) {  
                    paramArray[i] = new String(paramArray[i].getBytes(serverEncoding),encoding);  
                }  
            }
		}
        chain.doFilter(new CharacterEncodingRequest(req,serverEncoding), response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		Map<String, String> map = TomcatServer.getConnectorUriEncoding();
		String serv_encoding=null;
        for(Map.Entry<String, String> entry : map.entrySet()) {
            if(entry.getValue()!=null)
            	serv_encoding=entry.getValue();
        }
        if(serv_encoding==null)serv_encoding="ISO-8859-1";
        serverEncoding=serv_encoding;
        logger.info("serverEncoding:"+serverEncoding);
		String temp = fConfig.getInitParameter("encoding");
		if(temp!=null)
			encoding=temp;

	}

}
