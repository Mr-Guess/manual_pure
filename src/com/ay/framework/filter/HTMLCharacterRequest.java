package com.ay.framework.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/** 
 * @Description 
 * @date 2012-12-24
 * @author WangXin
 */
public class HTMLCharacterRequest extends HttpServletRequestWrapper {

	public HTMLCharacterRequest(HttpServletRequest request) {
		super(request);
	}
	@Override  
    public String getParameter(String name) {  
        return filter(super.getParameter(name));  
    }  
  
    /** 
     * 对特殊的html字符进行编码 
     *  
     * @param message 
     * @return 
     */  
    private String filter(String message) {  
  
        if (message == null)  
            return (null);  
  
        char content[] = new char[message.length()];  
        message.getChars(0, message.length(), content, 0);  
        StringBuilder result = new StringBuilder(content.length + 50);  
        for (int i = 0; i < content.length; i++) {  
            switch (content[i]) {  
            case '<':  
                result.append("&lt;");  
                break;  
            case '>':  
                result.append("&gt;");  
                break;  
            case '&':  
                result.append("&amp;");  
                break;  
            case '"':  
                result.append("&quot;");  
                break;  
            default:  
                result.append(content[i]);  
            }  
        }  
        return (result.toString());  
  
    }  
}

