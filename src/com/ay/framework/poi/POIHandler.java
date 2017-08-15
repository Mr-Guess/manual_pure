package com.ay.framework.poi;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description
 * @date 2012-11-5
 * @author WangXin
 */
public class POIHandler {
    private static Logger logger = LoggerFactory.getLogger(POIHandler.class);

    private POIHandler() {
    }

    public static void main(String[] args) throws Exception {
	List<String[]> list = new ArrayList<String[]>();
	String[][] data = { { "test1", "test2" }, { "1", "3" }, { "4", "4" },
		{ "55", "55", "55" } };
	for (String[] strings : data) {
	    list.add(strings);
	}
	list.add(null);
	for (String[] strings : data) {
	    list.add(strings);
	}
	save(list, "测试用的中文unicode", "gogogo");
	// read("workbook.xls");
    }
    /**
     * 通过html导出word
     * @param content
     * @return
     * @return POIFSFileSystem
     */
    public static POIFSFileSystem htmlToWord(String content) {
	byte b[] = content.getBytes();
	ByteArrayInputStream bais = new ByteArrayInputStream(b);
	POIFSFileSystem poifs = new POIFSFileSystem();
	DirectoryEntry directory = poifs.getRoot();
	try {
	directory.createDocument("WordDocument", bais);
	    bais.close();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return poifs;
    }

    public static Workbook save(List<String[]> list, String... sheetNames) {
	// create a new workbook
	Workbook wb = new HSSFWorkbook();
	// create a new sheet
	Sheet s = wb.createSheet();
	// declare a row object reference
	Row r = null;
	// declare a cell object reference
	Cell c = null;
	// create 3 cell styles
	CellStyle cs = wb.createCellStyle();
	DataFormat df = wb.createDataFormat();
	// create 2 fonts objects
	Font f = wb.createFont();
	// set font 1 to 12 point type
	f.setFontHeightInPoints((short) 12);
	// make it blue
	f.setColor((short) 0xc);
	// make it bold
	// arial is the default font
	f.setBoldweight(Font.BOLDWEIGHT_BOLD);
	// set cell stlye
	cs.setFont(f);
	// set the cell format
	cs.setDataFormat(df.getFormat("#,##0.0"));
	if (list != null) {
	    int rownum = (short) 0;
	    for (int i = 0; i < list.size(); i++) {
		String[] columns = list.get(i);
		if (columns == null) {
		    s = wb.createSheet();
		    rownum = (short) 0;
		    continue;
		}
		// create a row
		r = s.createRow(rownum);
		for (short cellnum = (short) 0; cellnum < columns.length; cellnum++) {
		    c = r.createCell(cellnum);
		    c.setCellValue(columns[cellnum]);
		    // c.setCellStyle(cs);
		}
		rownum++;
	    }
	}
	// set the sheet name in Unicode
	int sheetNumber = wb.getNumberOfSheets();
	for (int i = 0; i < sheetNames.length; i++) {
	    if (i < sheetNumber)
		wb.setSheetName(i, sheetNames[i]);
	}
	return wb;
    }

    private static HSSFWorkbook readFile(String filename) {
	HSSFWorkbook wb = null;
	try {
	    wb = new HSSFWorkbook(new FileInputStream(filename));
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return wb;
    }

    private static HSSFWorkbook readFile(InputStream fileInputStream) {
	HSSFWorkbook wb = null;
	try {
	    wb = new HSSFWorkbook(fileInputStream);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return wb;
    }

    public static List<String[]> read(InputStream fileInputStream) {
	List<String[]> list = new ArrayList<String[]>();
	HSSFWorkbook wb = readFile(fileInputStream);
	for (int k = 0; k < wb.getNumberOfSheets(); k++) {
	    HSSFSheet sheet = wb.getSheetAt(k);
	    int rows = sheet.getPhysicalNumberOfRows();
	    logger.info("Sheet " + k + " \"" + wb.getSheetName(k) + "\" has "
		    + rows + " row(s).");
	    for (int r = 0; r < rows; r++) {
		HSSFRow row = sheet.getRow(r);
		if (row == null) {
		    continue;
		}

		int cells = row.getPhysicalNumberOfCells();
		logger.info("\nROW " + row.getRowNum() + " has " + cells
			+ " cell(s).");
		String[] s = new String[cells];
		for (int i = 0; i < cells; i++) {
		    HSSFCell cell = row.getCell(i);
		    String value = null;

		    switch (cell.getCellType()) {
		    case HSSFCell.CELL_TYPE_FORMULA:
			// value = "" + cell.getCellFormula();
			try {
			    value = String.valueOf(cell.getNumericCellValue());
			} catch (IllegalStateException e) {
			    value = String.valueOf(cell
				    .getRichStringCellValue());
			}
			value = subZeroAndDot(value);
			break;
		    case HSSFCell.CELL_TYPE_NUMERIC:
			value = String.valueOf(cell.getNumericCellValue());
			value = subZeroAndDot(value);
			break;
		    case HSSFCell.CELL_TYPE_STRING:
			value = String.valueOf(cell.getRichStringCellValue());
			break;
		    default:
		    }
		    // logger.info("CELL col=" + cell.getColumnIndex() +
		    // " VALUE=" + value);
		    s[i] = value;
		}
		list.add(s);
	    }
	    // sheet之间用null隔开
	    list.add(null);
	}
	return list;
    }

    public static String subZeroAndDot(String s) {
	if (s.indexOf(".") > 0) {
	    s = s.replaceAll("0+?$", "");// 去掉多余的0
	    s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
	}
	return s;
    }

}
