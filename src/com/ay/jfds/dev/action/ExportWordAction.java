package com.ay.jfds.dev.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.ay.framework.core.action.BaseAction;
import com.ay.framework.filter.CharEncodingFilter;
import com.ay.framework.poi.POIHandler;

/**
 * @Description 导出word的Action
 * @date 2013-08-14
 * @author WangXin
 */
public class ExportWordAction extends BaseAction {
    private InputStream outFile;
    private String downloadFileName;
    /** 需要导出的html内容 */
    private String content;

    public String exp() throws IOException {
	POIFSFileSystem poifs = POIHandler.htmlToWord(content);
	ByteArrayOutputStream output = new ByteArrayOutputStream();
	poifs.writeFilesystem(output);
	byte[] ba = output.toByteArray();
	outFile = new ByteArrayInputStream(ba);
	return "exp";
    }

    public String getDownloadFileName() {
	if (downloadFileName == null || downloadFileName.isEmpty()) {
	    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	    downloadFileName = (sf.format(new Date()).toString()) + ".doc";
	}
	try {
	    downloadFileName = new String(downloadFileName.getBytes("UTF-8"), CharEncodingFilter.serverEncoding);
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
	return downloadFileName;
    }

    public void setDownloadFileName(String downloadFileName) {
	this.downloadFileName = downloadFileName;
    }

    public InputStream getOutFile() {
        return outFile;
    }

    public void setOutFile(InputStream outFile) {
        this.outFile = outFile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
