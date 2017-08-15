package com.ay.test.bean.jackson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.ay.framework.bean.DataStore;
import com.ay.framework.bean.OperateInfo;
import com.ay.framework.core.utils.mapper.JsonMapper;
import com.ay.test.bean.User;

public class JacksonTest {
	@Test
	public void testDateStore2String() {
		DataStore<User> dataStore = new DataStore<User>();
		List<User> list = new ArrayList<User>();
		for (int i=1; i<=10; i++) {
			User user = new User();
			user.setCreateDate(new Date());
			user.setId(" id " + i);
			user.setPassword(" password " + i);
			user.setUsername(" username " + i);
			list.add(user);
		}
		
		dataStore.setTotal(10L);
		dataStore.setRows(list);
		System.out.println(new JsonMapper().toJson(dataStore));
	}
	
	@Test
	public void testOperateInfo2Json() {
		OperateInfo operateInfo = new OperateInfo();
		operateInfo.setOperateCallbackUrl("/admin/hello.action");
		operateInfo.setOperateCallbackUrlParam("user:zxy");
		operateInfo.setOperateCode("code");
		operateInfo.setOperateMessage("更新成功:转向hello.action");
		operateInfo.setOperateSuccess(true);
		System.out.println(new JsonMapper().toJson(operateInfo));
	}
}
