package com.ay.test.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ay.framework.bean.DataStore;
import com.ay.framework.util.JsonUtil;

public class DataStoreTest {
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
		Assert.assertEquals(new Long(10L), dataStore.getTotal());
		Assert.assertEquals(10, dataStore.getRows().size());
	}
	
	@Test
	public void testDataStore2Json() {
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
		System.out.println(JsonUtil.object2json(dataStore));
		
	}
}
