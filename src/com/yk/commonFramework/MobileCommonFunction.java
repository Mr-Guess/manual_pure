package com.yk.commonFramework;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.ay.framework.bean.OperateInfo;
import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.framework.core.utils.spring.SpringContextHolder;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.framework.util.BeanUtil;
import com.ay.netEasy.im.ConnectToApi;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.DefaultFontMapper.BaseFontParameters;
import com.yk.businesses.onlinCheckin.pojo.OnlinCheckin;
import com.yk.businesses.onlinCheckin.service.OnlinCheckinService;
import com.yk.businesses.reimburse.pojo.Reimburse;
import com.yk.businesses.reimburse.service.ReimburseService;
import com.yk.businesses.reimburseList.pojo.ReimburseList;
import com.yk.businesses.reimburseList.service.ReimburseListService;
import com.yk.commonFramework.paramObj.DownloadControl;
import com.yk.commonFramework.paramObj.FriendShip;
import com.yk.framecommon.CommonBackstage;
import com.yk.framecommon.frameRecord.pojo.FrameRecord;

/**
 * 手机端通用功能
 * @author yzbyp
 *
 */
public class MobileCommonFunction extends BaseAction{
	
	private FriendShip friendShip;
	private OnlinCheckin onlinCheckin;
	private String id;
	private File file;
	private String fileName;
	private String contentType;
	private String onTime;//上班打卡时间
	private String outTime;//下班打卡时间
	protected String path = this.getClass().getResource("").toString();
	protected String fileDir = path+"/pdfs/";
	
	// 版本号控制
	private String version;
	
	public void friendShips(){
		try {
			String rtn = ConnectToApi.friendShip(friendShip.getFromAccid(), friendShip.getToAccid(), friendShip.getType(), friendShip.getMessage());
			Struts2Utils.renderText(rtn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 在线打卡
	 */
	public void onlineCheckin() throws Exception{
		String ct  =  ServletActionContext.getRequest().getHeader("Content-Type");
		String realPath = ServletActionContext.getServletContext().getRealPath("/upload");
		OperateInfo operateInfo = new OperateInfo();
		FileOutputStream outputStream = null;
		FileInputStream inputStream = null;
		try {
			PrintWriter out = ServletActionContext.getResponse().getWriter();
			outputStream = new FileOutputStream(realPath);
			inputStream = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = inputStream.read(buffer)) > 0){
				outputStream.write(buffer,0,len);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			operateInfo.setOperateMessage("照片上传失败");
			operateInfo.setOperateSuccess(true);
		} finally {
			outputStream.close();
			inputStream.close();
		}
		
		OnlinCheckinService ocs = SpringContextHolder.getBean("onlinCheckinService");
		onlinCheckin.setFileName(fileName);
		onlinCheckin.setPicUrl(realPath);
		onlinCheckin.setContentType(ct);
		try {
			ocs.insert(onlinCheckin);
			operateInfo.setOperateMessage("打卡成功");
			operateInfo.setOperateSuccess(true);
		} catch (Exception e) {
			operateInfo.setOperateMessage("打卡失败");
			operateInfo.setOperateSuccess(false);
		}
		
		String json = new JsonMapper().toJson(operateInfo);
		Struts2Utils.renderText(json);
	}
	
	
	

	/**
	 * 获取当前服务端安卓版本
	 */
	public void getAndroidVersion(){
		
		//{"url":"http://192.168.205.33:8080/Hello/app_v3.0.1_Other_20150116.apk ","versionCode":2,"updateMessage":"版本更新信息"}
		DownloadControl dc = new DownloadControl();
		try {
			
			InputStream inputStream = new BufferedInputStream(new FileInputStream(path.substring(5, path.length())+"mobileParameter.properties"));
			Map<String, String> propMap = CommonBackstage.getProperties(inputStream);
			String serverVersion = propMap.get("androidVersion");
			String updateMessage = propMap.get("updateMessage");
			inputStream.close();
			dc.setUrl("/officialWebsite/apks/gt_beta.apk");
			dc.setVersionCode(serverVersion);
			dc.setUpdateMessage(updateMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			Struts2Utils.renderJson(dc);
		}
	}
	
	/**
	 * 获取打卡设置
	 */
	public void getSigninSetting(){
		Map<String, String> outMap = new HashMap<String ,String>();
		try {
			InputStream inputStream = new BufferedInputStream(new FileInputStream(path.substring(5, path.length())+"mobileParameter.properties"));
			Map<String, String> propMap = CommonBackstage.getProperties(inputStream);
			String onWorkTime = propMap.get("onWorkTime");
			String outWorkTime = propMap.get("outWorkTime");
			inputStream.close();
			outMap.put("onWorkTime", onWorkTime);
			outMap.put("outWorkTime", outWorkTime);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			Struts2Utils.renderJson(outMap);
		}
	}
	
	/**
	 * 设置系统参数
	 */
	public void setSinginSetting(){
		OperateInfo operateInfo = new OperateInfo();
		try {
			Properties propreties = new Properties();
			InputStream inputStream = new BufferedInputStream(new FileInputStream(path.substring(5, path.length())+"mobileParameter.properties"));
			propreties.load(inputStream);
			inputStream.close();
			//开始设置属性
			if(onTime != null && !onTime.equals("")){
				propreties.setProperty("onWorkTime", onTime);
			}
			if(outTime != null && !outTime.equals("")){
				propreties.setProperty("outWorkTime", outTime);
			}
			FileOutputStream fileOutputStream = new FileOutputStream(path.substring(5, path.length())+"mobileParameter.properties");
			propreties.store(fileOutputStream, "");
			operateInfo.setOperateMessage("修改成功");
			operateInfo.setOperateSuccess(true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			operateInfo.setOperateMessage("修改失败");
			operateInfo.setOperateSuccess(false);
		} finally {
			String json = new JsonMapper().toJson(operateInfo);
			Struts2Utils.renderText(json);
		}
	}
	
	/**
	 * 获取当前用户所拥有的菜单
	 */
	public void getUsersMenu(){
		// 读取nameID.txt文件中的NAMEID字段（key）对应值（value）并存储  
       List<String> list = new ArrayList<String>();
       BufferedReader breader;
       StringBuilder sb = new StringBuilder();
       try {
			breader = new BufferedReader(new FileReader(path.substring(5, path.length())+"userMenu.json"));
			String s = null;
			while((s = breader.readLine()) != null){
				sb.append(new String(s.getBytes(),"UTF-8"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			Struts2Utils.renderText(sb.toString());
		}
	}
	
	/**
	 * 获取打印发票列表PDF
	 */
	public void reimbursePDF() throws Exception{
		OutputStream os =response.getOutputStream();
		response.setContentType("application/pdf");
		ReimburseService rs = SpringContextHolder.getBean("reimburseService");
		ReimburseListService rls = SpringContextHolder.getBean("reimburseListService");
		//获取表中数据
		Reimburse ri = rs.getById(id);
		ReimburseList rlst = new ReimburseList();
		rlst.setRelateData(id);
		Map paramMap = BeanUtil.Bean2Map(rlst);
		List<ReimburseList> list = rls.findAll(paramMap);
		
		//编写PDF
		Document document = new Document(PageSize.A4, 50, 50, 50, 50);
		//设置pdfWriter
		PdfWriter writer = PdfWriter.getInstance(document, os);
		
		//编辑字体
		BaseFont bfChinese =  BaseFont.createFont("C:/WINDOWS/Fonts/MSYH.TTC,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		Font chineseFont24 = new Font(bfChinese,24,Font.BOLD);
		Font chineseFont18 = new Font(bfChinese,18,Font.BOLD);
		Font chineseFont16 = new Font(bfChinese,16,Font.BOLD);
		Font chineseFont14B = new Font(bfChinese,14,Font.BOLD);
		Font chineseFont14 = new Font(bfChinese,14,Font.NORMAL);
		Font chineseFont12B = new Font(bfChinese,12,Font.BOLD);
		Font chineseFont12 = new Font(bfChinese,12,Font.NORMAL);
		Font chineseFont11 = new Font(bfChinese,11,Font.NORMAL);
		Font chineseFont11I = new Font(bfChinese,11,Font.ITALIC);
		Font chineseFont11B = new Font(bfChinese,11,Font.BOLD);
		Font chineseFont10 = new Font(bfChinese,10,Font.NORMAL);
		
		document.addTitle("报销单明细表");  
		document.addAuthor("永坤科技");  
		document.addSubject("Subject@iText sample");  
		
		document.open();
		
		//写入PDF
		
		  
		//添加表格
		PdfPTable table = new PdfPTable(6);
		PdfPCell cell;
		Paragraph paragraph;
		
		//编辑表格
		cell = new PdfPCell();
		cell.setBorderWidthTop(0f);
		cell.setBorderWidthLeft(0f);
		cell.setBorderWidthRight(0f);
		paragraph = new Paragraph("报销单详情",chineseFont18);
		paragraph.setAlignment(1);
		cell.setPhrase(paragraph);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setFixedHeight(35f);
		cell.setColspan(6);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("报销单编号",chineseFont12B));
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setFixedHeight(25f);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(ri.getRemiurserJob(),chineseFont12));
		cell.setColspan(5);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setFixedHeight(25f);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("报销人",chineseFont12B));
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setFixedHeight(25f);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(ri.getReimurser(),chineseFont12));
		cell.setColspan(2);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setFixedHeight(25f);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("报销时间",chineseFont12B));
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setFixedHeight(25f);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(ri.getReimurseTime(),chineseFont12));
		cell.setFixedHeight(25f);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setColspan(2);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("报销金额",chineseFont12B));
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setFixedHeight(25f);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("￥ "+ri.getRemiurseHole(),chineseFont12));
		cell.setColspan(2);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setFixedHeight(25f);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("共附单据",chineseFont12B));
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setFixedHeight(25f);
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(ri.getDocs()+" 张",chineseFont12));
		cell.setColspan(2);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setFixedHeight(25f);
		table.addCell(cell);
		
		cell = new PdfPCell();
		paragraph = new Paragraph("发票详细",chineseFont12B);
		paragraph.setAlignment(1);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setPhrase(paragraph);
		cell.setColspan(6);
		cell.setFixedHeight(25f);
		table.addCell(cell);
		
		
		//此处开始为发票内容
		cell = new PdfPCell(new Phrase("编号",chineseFont12B));
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setFixedHeight(25f);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("发票类别",chineseFont12B));
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setFixedHeight(25f);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("金额",chineseFont12B));
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setFixedHeight(25f);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("发票时间",chineseFont12B));
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setFixedHeight(25f);
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("备注",chineseFont12B));
		cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
		cell.setColspan(2);
		table.addCell(cell);
		
		for (int i = 0; i < list.size(); i++) {
			ReimburseList rl = list.get(i);
			int j = i+1;
			//编号
			cell = new PdfPCell(new Phrase(j+""));
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cell.setFixedHeight(25f);
			table.addCell(cell);
			//发票类别
			cell = new PdfPCell(new Phrase(rl.getStateType(),chineseFont11));
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cell.setFixedHeight(25f);
			table.addCell(cell);
			//金额
			cell = new PdfPCell(new Phrase(rl.getAmount(),chineseFont11));
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cell.setFixedHeight(25f);
			table.addCell(cell);
			//发票时间
			cell = new PdfPCell(new Phrase(rl.getPaydate(),chineseFont11I));
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cell.setFixedHeight(25f);
			table.addCell(cell);
			//备注摘要
			cell = new PdfPCell(new Phrase(rl.getBz(),chineseFont11));
			cell.setColspan(2);
			cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
			cell.setFixedHeight(25f);
			table.addCell(cell);
		}
		
		//写入表格
		document.add(table);  
		
		//该关的都关了
		document.close();
		os.flush();
		os.close();
	}
	

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	public FriendShip getFriendShip() {
		return friendShip;
	}

	public void setFriendShip(FriendShip friendShip) {
		this.friendShip = friendShip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public OnlinCheckin getOnlinCheckin() {
		return onlinCheckin;
	}

	public void setOnlinCheckin(OnlinCheckin onlinCheckin) {
		this.onlinCheckin = onlinCheckin;
	}

	public String getOnTime() {
		return onTime;
	}

	public void setOnTime(String onTime) {
		this.onTime = onTime;
	}

	public String getOutTime() {
		return outTime;
	}

	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	
	
	
}
