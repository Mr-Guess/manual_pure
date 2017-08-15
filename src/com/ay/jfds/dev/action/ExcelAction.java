package com.ay.jfds.dev.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import com.ay.framework.core.action.BaseAction;
import com.ay.framework.exception.POIException;
import com.ay.framework.poi.POIHandler;
import com.ay.framework.util.JFreeChartUtil;
import com.ay.jfds.dev.dao.DataHandlerDao;
import com.ay.jfds.dev.pojo.DevStatistics;
import com.ay.jfds.dev.service.DevStatisticsService;

/**
 * @Description
 * @date 2012-10-25
 * @author WangXin
 */
public class ExcelAction extends BaseAction {
	private static final long serialVersionUID = -975504347066943020L;
	private InputStream excelOutFile;
	private String downloadFileName;
	
	
	private DataHandlerDao dataHandlerDao;

	@Override
	public String execute() throws Exception {
		return "exception";
	}

	public String exp() throws IOException {
//		List<String[]> list = new ArrayList<String[]>();
//		String[][] data = { { "test1", "test2" }, { "1", "3" }, { "4", "4" },
//				{ "55", "55", "55" } };
//		for (String[] strings : data) {
//			list.add(strings);
//		}
//		list.add(null);
//		for (String[] strings : data) {
//			list.add(strings);
//		}
		List<String[]> list = dataHandlerDao.getExcelData(excelQuerySql);
		if(list!=null && list.size()>0 && excelHeads!=null && excelHeads.length>0)
			list.set(0, excelHeads);
		Workbook wb=POIHandler.save(list, excelSheetName);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		wb.write(output);
		byte[] ba = output.toByteArray();
		excelOutFile = new ByteArrayInputStream(ba);
		return "exp";
	}
	
	public String imp() throws IOException, SQLException, POIException {
		List<String[]> list = POIHandler.read(new FileInputStream(excelFile));
		dataHandlerDao.insertExcelDate(excelInsertSql, list);
		return "imp";
	}

	

	public InputStream getExcelOutFile() {
		return excelOutFile;
	}
	public void setExcelOutFile(InputStream excelOutFile) {
		this.excelOutFile = excelOutFile;
	}

	public String getDownloadFileName() {
		if (downloadFileName == null || downloadFileName.isEmpty()) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			downloadFileName = (sf.format(new Date()).toString())
					+ ".xls";
		}
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public DataHandlerDao getDataHandlerDao() {
		return dataHandlerDao;
	}

	public void setDataHandlerDao(DataHandlerDao dataHandlerDao) {
		this.dataHandlerDao = dataHandlerDao;
	}
	
}
