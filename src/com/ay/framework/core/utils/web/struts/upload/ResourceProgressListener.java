package com.ay.framework.core.utils.web.struts.upload;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.ProgressListener;

import com.opensymphony.xwork2.ActionContext;

/**
 * 上传监听器
 * @author wgw
 * @date 2013-3-8
 */
public class ResourceProgressListener implements ProgressListener{

	public ResourceProgressListener(HttpServletRequest request) {  
    }  
  
	private long megaBytes = -1;
	
	
	/**
	 *  @param pByteRead:已读取的字节数，pContentLength:总共的字节数，pItems:文件的索引
	 */
	@Override
    public void update(long pBytesRead, long pContentLength, int pItems) { 
		//文件每传输100K监听一次
        long mBytes = pBytesRead / 100000;
        if (megaBytes == mBytes) {
            return;
        }
        megaBytes = mBytes;
        if (pContentLength == -1) {
            System.out.println("So far, " + pBytesRead/1000000 + "M have been read.");
        } else {
        	String process = ((pBytesRead * 100) / (pContentLength * 1)) + "";
        	if(mBytes==pContentLength/100000){
        		process = "100";
        	}
            ActionContext.getContext().getSession().put("currentUploadStatus", process);  
        }
    }  

}
