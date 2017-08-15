package com.ay.jfds.dev.service;

import java.sql.SQLException;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.stereotype.Component;

import com.ay.framework.core.service.BaseService;
import com.ay.jfds.dev.dao.DevStatisticsDao;
import com.ay.jfds.dev.pojo.DevStatistics;

/** 
 * @Description:
 * @date:2012-10-26
 * @author WangXin
 */
@Component
public class DevStatisticsService extends BaseService<DevStatistics, DevStatisticsDao> {
	public CategoryDataset getCategoryDataset(String sql,boolean transposed) throws SQLException{
		return getDao().getCategoryDataset(sql,transposed);
	}
	public PieDataset getPieDataset(String sql) throws SQLException{
		return getDao().getPieDataset(sql);
	}
}

