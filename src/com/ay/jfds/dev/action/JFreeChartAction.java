package com.ay.jfds.dev.action;

import java.sql.SQLException;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ay.framework.core.action.BaseAction;
import com.ay.framework.exception.SystemException;
import com.ay.framework.util.JFreeChartUtil;
import com.ay.jfds.dev.pojo.DevStatistics;
import com.ay.jfds.dev.service.DevStatisticsService;

/** 
 * @Description 
 * @date 2012-10-25
 * @author WangXin
 */
public class JFreeChartAction extends BaseAction {
	private static final Logger logger = LoggerFactory.getLogger(JFreeChartAction.class);
	private static final long serialVersionUID = 2759441828938398991L;
	private DevStatisticsService devStatisticsService;
	private JFreeChart chart;
	private String id;
	
	
	private String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("user_id");//从session中获取userId
	private String usertype = (String) SecurityUtils.getSubject().getSession().getAttribute("usertype");//从session中获取usertype
	
	private List<String> userIdList;
	
	private String getTableName(String sql) {
		if(!sql.contains("from"))
			return null;
		sql = sql.substring(sql.indexOf("from")+4).trim();
		if(sql.isEmpty())
			return null;
		if(sql.contains(" "))
			sql = sql.substring(0,sql.indexOf(" ")).trim();	
		if(sql.contains("\r\n"))
			sql = sql.substring(0,sql.indexOf("\r\n")).trim();	
		return sql;
	}
	private void appendAllUserId(StringBuilder sb, String tableName) {
		if(userIdList==null || userIdList.isEmpty()){
			sb.append(" and 1=2 ");
			return;
		}
		sb.append(" and ").append(tableName).append(".created in ( ");
		for (int i = 0;i<userIdList.size();i++) {
			String userIdStr = userIdList.get(i);
			if(i!=0)
				sb.append(",");
			sb.append("'").append(userIdStr).append("'");
		}
		sb.append(") ");
	}
	private String getConvertSQL(String sql,String tableName){
		sql = sql.toLowerCase();
		StringBuilder conditionSql = new StringBuilder();		
		appendAllUserId(conditionSql,tableName);
		StringBuilder sb = new StringBuilder();
		if(sql.contains("where")){
			addAfterWhere(sql,sb,tableName);
		}else{
			if(sql.contains("group by")){
				int i = sql.indexOf("group by");
				sb.append(sql.substring(0,i)).append(" where 1=1 ").append(conditionSql).append(" ");
				sql = sql.substring(i).trim();
				sb.append(sql);
			}else {
				if(sql.contains("order")){
					int i = sql.indexOf("order");
					sb.append(sql.substring(0,i)).append(" where 1=1 ").append(conditionSql).append(" ");
					sql = sql.substring(i).trim();
					sb.append(sql);
				}
			}
		}
		return sb.toString();
	}
	private String getConvertSQLBracket(String sql,String tableName){
		sql = sql.toLowerCase();
		String beforeBefore = "";
		if(sql.contains("count(")){
			int s1 = sql.indexOf("count(");
			String temp = sql.substring(s1);
			int s2 = temp.indexOf(")");
			beforeBefore = sql.substring(0,s1+s2+1);
			sql = sql.substring(s1+s2+1);
		}
		int before = sql.indexOf("(");
		int after = sql.indexOf(")");
		String beforeBracket = sql.substring(0,before);
		String afterBracket = sql.substring(after+1);
		sql = sql.substring(before+1,after);
		StringBuilder conditionSql = new StringBuilder();		
		appendAllUserId(conditionSql,tableName);
		StringBuilder sb = new StringBuilder();
		if(sql.contains("where")){
			addAfterWhere(sql,sb,tableName);
		}else{
			if(sql.contains("group by")){
				int i = sql.indexOf("group by");
				sb.append(sql.substring(0,i)).append(" where 1=1 ").append(conditionSql).append(" ");
				sql = sql.substring(i).trim();
				sb.append(sql);
			}else {
				if(sql.contains("order")){
					int i = sql.indexOf("order");
					sb.append(sql.substring(0,i)).append(" where 1=1 ").append(conditionSql).append(" ");
					sql = sql.substring(i).trim();
					sb.append(sql);
				}
			}
		}
		return beforeBefore + " " + beforeBracket + " (" + sb.toString() + ") " + afterBracket;
	}
	private void addAfterWhere(String sql,StringBuilder sb,String tableName){
		String tableNameTemp = getTableName(sql);
		if(tableNameTemp!=null){
			tableName = tableNameTemp;
		}
		StringBuilder conditionSql = new StringBuilder();
		appendAllUserId(conditionSql,tableName);
		if(sql.contains("where")){
			int i = sql.indexOf("where");
			sb.append(sql.substring(0,i)).append(" where 1=1 ").append(conditionSql).append(" and ");
			sql = sql.substring(i+5).trim();
			addAfterWhere(sql,sb,tableName);			
		}else{
			sb.append(sql);
		}
	}
    
    @Override
    public String execute() throws Exception {    	
        System.out.println(id);
        return "exception";
    }
    public String randerChart() throws SQLException{
    	DevStatistics ds=devStatisticsService.getById(id);
    	if(ds == null) {
    		throw new SystemException("统计数据未设置");
    	}
    	String sql =ds.getSqlcommand();
    	if(usertype!=null && usertype.equals("2")){
    		if(sql==null ||sql.isEmpty())
    			throw new SQLException("请输入正确的SQL");
    		String tableName = getTableName(sql);
    		if(tableName == null)
    			throw new SQLException("请输入正确的SQL");    		
    		if(tableName.startsWith("bus")){
    			sql = getConvertSQL(sql,tableName);
    		}
    		if(tableName.contains("(")){
    			sql = getConvertSQLBracket(sql,tableName);
    		}
    	}
    	logger.info(sql);
    	int type=ds.getCharttype();
    	PieDataset pieDataset=null;
    	CategoryDataset categoryDataset=null;
    	switch (type) {
    	case JFreeChartUtil.pie2D:
    	case JFreeChartUtil.pie3D:
			pieDataset=devStatisticsService.getPieDataset(sql);
			break;
		case JFreeChartUtil.bar2D:
		case JFreeChartUtil.bar3D:
		case JFreeChartUtil.line:
			categoryDataset=devStatisticsService.getCategoryDataset(sql,ds.getTransposed());
			break;
		default:
			break;
		}
    	switch (type) {
    	case JFreeChartUtil.pie2D:
    		chart=JFreeChartUtil.createPieChart(ds.getCharttitle(), pieDataset, true, true, false,false);
    		break;
    	case JFreeChartUtil.pie3D:
    		chart=JFreeChartUtil.createPieChart(ds.getCharttitle(), pieDataset, true, true, false,true);
			break;
		case JFreeChartUtil.bar2D:
			chart=JFreeChartUtil.createBarChart(ds.getCharttitle(), ds.getXtitle(), ds.getYtitle(), categoryDataset,"", false);
			break;
		case JFreeChartUtil.bar3D:
			chart=JFreeChartUtil.createBarChart(ds.getCharttitle(), ds.getXtitle(), ds.getYtitle(), categoryDataset, "", true);
			break;
		case JFreeChartUtil.line:
			chart=JFreeChartUtil.createTimeXYChar(ds.getCharttitle(), ds.getXtitle(), ds.getYtitle(), categoryDataset, "");
			break;
		default:
			break;
		}
    	return SUCCESS;
    }

    public JFreeChart getChart() {
        return chart;
    }

    public void setChart(JFreeChart chart) {
        this.chart = chart;
    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public DevStatisticsService getDevStatisticsService() {
		return devStatisticsService;
	}
	public void setDevStatisticsService(DevStatisticsService devStatisticsService) {
		this.devStatisticsService = devStatisticsService;
	}
    public static void main(String[] args) {
    	String sql = " select  *   from fjsdao ";
    	if(!sql.contains("from"))
			return;
		sql = sql.substring(sql.indexOf("from")+4).trim();
		if(sql.contains(" "))
			sql = sql.substring(0,sql.indexOf(" ")).trim();	
		System.out.println(sql);
	}
}

