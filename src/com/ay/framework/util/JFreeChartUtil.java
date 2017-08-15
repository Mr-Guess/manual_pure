package com.ay.framework.util;

/** 
 * @Description 
 * @date 2012-10-24
 * @author WangXin
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.text.DecimalFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

public class JFreeChartUtil {
	/**
	 * 饼图2D
	 */
	public static final int pie2D = 1;
	/**
	 * 饼图3D
	 */
	public static final int pie3D = 2;
	/**
	 * 柱状图2D
	 */
	public static final int bar2D = 3;
	/**
	 * 柱状图3D
	 */
	public static final int bar3D = 4;
	/**
	 * 折线图
	 */
	public static final int line = 5;

	public static String getchartTypeString(int chartType) {
		String chartTypeStr = null;
		switch (chartType) {
		case JFreeChartUtil.pie2D:
			chartTypeStr = "2D饼图";
			break;
		case JFreeChartUtil.pie3D:
			chartTypeStr = "3D饼图";
			break;
		case JFreeChartUtil.bar2D:
			chartTypeStr = "2D柱状图";
			break;
		case JFreeChartUtil.bar3D:
			chartTypeStr = "3D柱状图";
			break;
		case JFreeChartUtil.line:
			chartTypeStr = "折线图";
			break;
		default:
			chartTypeStr = "其它图";
			break;
		}
		return chartTypeStr;

	}

	/**
	 * 柱状图,折线图 数据集 方法
	 * 
	 * @param rowKeys
	 * @param columnKeys
	 * @param data
	 * @return
	 * @return CategoryDataset
	 */
	public static CategoryDataset getCategoryDataset(String[] rowKeys,
			String[] columnKeys, Number[][] data) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				try {
					dataset.setValue(data[i][j], rowKeys[i], columnKeys[j]);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		}
		return dataset;
	}

	/**
	 * 饼图 数据集 方法
	 * 
	 * @param keys
	 * @param values
	 * @return
	 * @return PieDataset
	 */
	public static PieDataset getPieDataset(String[] keys, Number[] values) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (int i = 0; i < values.length; i++) {
			try {
				dataset.setValue(keys[i], values[i]);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return dataset;
	}

	/**
	 * 柱状图
	 * 
	 * @param title
	 * @param x
	 * @param y
	 * @param dataset
	 * @param charName
	 * @return
	 * @return JFreeChart
	 */
	public static JFreeChart createBarChart(String title, String x, String y,
			CategoryDataset dataset, String charName, boolean is3D) {
		JFreeChart chart = ChartFactory.createLineChart(title, x, y, dataset,
				PlotOrientation.VERTICAL, true, true, false);
		initChart(chart, title);
		CategoryPlot categoryPlot = chart.getCategoryPlot();
		NumberAxis numberaxis = (NumberAxis) categoryPlot.getRangeAxis();
		// 设置纵坐标的标题字体和大小
		numberaxis.setLabelFont(new Font("宋体", Font.PLAIN, 13));
		// 设置丛坐标的坐标值的字体颜色
		numberaxis.setLabelPaint(Color.BLACK);
		// 设置丛坐标的坐标轴标尺颜色
		numberaxis.setTickLabelPaint(Color.RED);
		//数据轴精度
		//numberaxis.setNumberFormatOverride(new DecimalFormat("#"));
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		// 获取横坐标
		CategoryAxis domainAxis = categoryPlot.getDomainAxis();
		// 设置横坐标的标题字体和大小
		domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 13));
		// 设置横坐标的坐标值的字体颜色
		domainAxis.setTickLabelPaint(Color.BLACK);
		// 设置横坐标的坐标值的字体
		domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 13));
		if (is3D) {
			// 在柱体的上面显示数据
			BarRenderer3D custombarrenderer3d = new BarRenderer3D();
			// 数据字体颜色
			custombarrenderer3d.setBaseItemLabelPaint(Color.BLACK);
			custombarrenderer3d
					.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			custombarrenderer3d.setBaseItemLabelsVisible(true);
			categoryPlot.setRenderer(custombarrenderer3d);
		} else {
			BarRenderer custombarrenderer = new BarRenderer();
			// 数据字体颜色
			custombarrenderer.setBaseItemLabelPaint(Color.BLACK);
			custombarrenderer
					.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			custombarrenderer.setBaseItemLabelsVisible(true);
			categoryPlot.setRenderer(custombarrenderer);
		}
		return chart;
	}

	/**
	 * 饼图
	 * 
	 * @param title
	 *            标题
	 * @param dataset
	 *            数据集
	 * @param legend
	 * @param tooltips
	 * @param urls
	 * @param is3D
	 *            是否3D
	 * @return
	 * @return JFreeChart
	 */
	public static JFreeChart createPieChart(String title, PieDataset dataset,
			boolean legend, boolean tooltips, boolean urls, boolean is3D) {
		if (is3D) {
			return create3DPieChart(title, dataset, legend, tooltips, urls);
		} else {
			return create2DPieChart(title, dataset, legend, tooltips, urls);
		}

	}
	/**
	 * 2D饼图
	 * @param title
	 * @param dataset
	 * @param legend
	 * @param tooltips
	 * @param urls
	 * @return
	 * @return JFreeChart
	 */
	private static JFreeChart create2DPieChart(String title,
			PieDataset dataset, boolean legend, boolean tooltips, boolean urls) {
		// 创建3D饼图
		JFreeChart chart = ChartFactory.createPieChart(title, dataset, legend,
				tooltips, urls);
		initChart(chart, title);
		// 得到2D饼图的plot对象
		PiePlot piePlot = (PiePlot) chart.getPlot();

		piePlot.setLabelFont((new Font("宋体", Font.PLAIN, 12)));
		// 设置扇区标签显示格式：关键字：值(百分比)
		piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}：{1}({2})"));
		// 设置扇区颜色
		// piePlot.setSectionPaint("test", new Color(160, 160, 255));
		// 设置扇区分离显示
		// piePlot.setExplodePercent("test", 0.2D);
		// 设置扇区边框不可见
		piePlot.setSectionOutlinesVisible(false);
		// 设置没有数据时显示的信息
		piePlot.setNoDataMessage("无数据");
		// 设置没有数据时显示的信息的字体
		piePlot.setNoDataMessageFont(new Font("宋体", Font.BOLD, 14));
		// 设置没有数据时显示的信息的颜色
		piePlot.setNoDataMessagePaint(Color.red);

		// 设置是否忽略0和null值
		piePlot.setIgnoreNullValues(true);
		piePlot.setIgnoreZeroValues(true);
		return chart;
	}
	/**
	 * 3D饼图
	 * @param title
	 * @param dataset
	 * @param legend
	 * @param tooltips
	 * @param urls
	 * @return
	 * @return JFreeChart
	 */
	private static JFreeChart create3DPieChart(String title,
			PieDataset dataset, boolean legend, boolean tooltips, boolean urls) {
		// 创建3D饼图
		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset,
				legend, tooltips, urls);
		initChart(chart, title);
		// 得到3D饼图的plot对象
		PiePlot3D piePlot = (PiePlot3D) chart.getPlot();
		// 设置旋转角度
		piePlot.setStartAngle(290);
		// 设置旋转方向
		piePlot.setDirection(Rotation.CLOCKWISE);
		// 设置透明度
		piePlot.setForegroundAlpha(0.5f);
		piePlot.setLabelFont((new Font("宋体", Font.PLAIN, 12)));
		return chart;
	}

	/**
	 * 折线图
	 * 
	 * @param title
	 * @param x
	 * @param y
	 * @param xyDataset
	 * @param charName
	 * @return
	 * @return JFreeChart
	 */
	public static JFreeChart createTimeXYChar(String title, String x, String y,
			CategoryDataset xyDataset, String charName) {
		JFreeChart chart = ChartFactory.createLineChart(title, x, y, xyDataset,
				PlotOrientation.VERTICAL, true, true, false);
		initChart(chart, title);
		CategoryPlot plot = chart.getCategoryPlot();
		CategoryAxis valueAxis = plot.getDomainAxis();
		// 设置x轴上面的字体
		valueAxis.setTickLabelFont(new Font("宋书", Font.ITALIC, 12));
		// 设置X轴的标题文字
		valueAxis.setLabelFont(new Font("黑体", Font.CENTER_BASELINE, 13));
		// 设置主标题
		// TextTitle subtitle = new TextTitle("日时间段访问量对比", new Font("宋体",
		// Font.BOLD, 12));
		// chart.addSubtitle(subtitle);
		NumberAxis numberAxis = (NumberAxis) plot.getRangeAxis();
		// 设置y轴上的字体
		numberAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 13));
		// 设置y轴上的标题字体
		numberAxis.setLabelFont(new Font("黑体", Font.CENTER_BASELINE, 13));
		// 设置面板字体
		Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
		chart.setBackgroundPaint(Color.WHITE);
		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		// x轴 // 分类轴网格是否可见
		categoryplot.setDomainGridlinesVisible(true);
		// y轴 //数据轴网格是否可见
		categoryplot.setRangeGridlinesVisible(true);
		categoryplot.setRangeGridlinePaint(Color.WHITE);// 虚线色彩
		categoryplot.setDomainGridlinePaint(Color.WHITE);// 虚线色彩
		categoryplot.setBackgroundPaint(Color.lightGray);
		// 设置轴和面板之间的距离
		// categoryplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
		CategoryAxis domainAxis = categoryplot.getDomainAxis();
		domainAxis.setLabelFont(labelFont);// 轴标题
		domainAxis.setTickLabelFont(labelFont);// 轴数值
		// domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(0.4));
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD); // 横轴上的
		// Lable
		// 45度倾斜
		// 设置距离图片左端距离
		domainAxis.setLowerMargin(0.0);
		// 设置距离图片右端距离
		domainAxis.setUpperMargin(0.0);
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		//数据轴精度
		//numberaxis.setNumberFormatOverride(new DecimalFormat("#0"));
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(true);
		// 获得renderer 注意这里是下嗍造型到lineandshaperenderer！！
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot
				.getRenderer();
		// XYLineAndShapeRenderer xylineandshaperenderer =
		// (XYLineAndShapeRenderer) categoryplot.getRenderer();//改变曲线颜色
		lineandshaperenderer.setBaseShapesVisible(true); // series 点（即数据点）可见
		lineandshaperenderer.setBaseLinesVisible(true); // series 点（即数据点）间有连线可见
		// 显示折点数据
		lineandshaperenderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		lineandshaperenderer.setBaseItemLabelsVisible(true);
		return chart;
	}

	/**
	 * 初始化图表，统一设置大小，和一部分共有的乱码问题
	 * 
	 * @param chart
	 * @param chartTitle
	 * @return void
	 */
	private static void initChart(JFreeChart chart, String chartTitle) {
		// 设置消除字体的锯齿渲染(解决中文问题)
		chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		chart.setTextAntiAlias(false);
		// chart.setBackgroundPaint(Color.RED);
		// 设置图标题的字体重新设置title
		Font font = new Font("宋体", Font.BOLD, 20);
		TextTitle title = new TextTitle(chartTitle);
		title.getBackgroundPaint();
		// 设置字体颜色
		title.setPaint(Color.BLACK);
		title.setFont(font);
		chart.setTitle(title);
		LegendTitle legend = chart.getLegend();
		if (legend != null) {
			// 这句代码解决了底部汉字乱码的问题
			legend.setItemFont(new Font("宋体", Font.PLAIN, 13));
		}
	}

}
