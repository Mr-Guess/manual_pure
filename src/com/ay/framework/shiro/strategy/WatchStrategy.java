package com.ay.framework.shiro.strategy;

import java.util.Map;
import java.util.Set;

import org.apache.shiro.SecurityUtils;

import com.ay.framework.annotation.Strategy;
import com.ay.framework.shiro.SystemWatch;
import com.ay.jfds.sys.dto.UserDTO;


/** 
 * @Description 监控策略（用于系统的监控模块）
 * @date 2014年3月7日
 * @author WangXin
 */
@Strategy(open=false)
public class WatchStrategy extends SimpleStrategy {

	@Override
	public void doStrategy(StrategyParam param, StrategyChain chain) {
		String tableName = param.getTableName().toLowerCase();
		Map paramMap = param.getMap();
		String con = (String) paramMap.get("condition");
		UserDTO user = (UserDTO) SecurityUtils.getSubject().getSession().getAttribute("user");
		if(!user.getAccount().equals("administrator") && (con ==null || con.isEmpty())) {
			Set<String> orgIds = SystemWatch.findUserModuleOrgIds(tableName);
			StringBuilder condition = new StringBuilder("(1=2 ");
			for (String id : orgIds) {
				if(!id.equals("")){
					//condition.append(" or "+tableName.toLowerCase()+".ORG_TREE like '%"+id+"%' ");
					condition.append(" or MATCH ("+tableName.toLowerCase()+".ORG_TREE) AGAINST ('"+id+"' IN BOOLEAN MODE) ");
				}
			}
			condition.append(")");
			paramMap.put("condition", condition.toString());
		}
		String dept= (String)paramMap.get("deptTree");
		if(!user.getAccount().equals("administrator")&&(dept==null||dept.isEmpty())){
			String deptId=user.getDeptId();//获取当前用户的部门
			StringBuilder deptTree = new StringBuilder("");
			//deptTree.append(" tb_enterprise.DEPT like '%"+user.getDeptId()+"%' ");
			deptTree.append(" MATCH (tb_enterprise.DEPT) AGAINST ('"+user.getDeptId()+"' IN BOOLEAN MODE) ");
			String condition=(String) paramMap.get("condition");//获取condition
			paramMap.remove(condition);//移除
			condition=condition.substring(0,condition.length()-1);//去掉最后一个括号
			paramMap.put("condition", condition);//重新set
			paramMap.put("deptTree", deptTree.toString());
		}
		if(!user.getAccount().equals("administrator")){
			paramMap.put("endDataProg", " 1=1 )");
		}
		chain.doStrategy(param);
	}
}

