package com.ay.jfds.dev.service;

public class DataTypeDataTreeService {

	private static DataTypeDataTreeService instance = null;

	private DataTypeDataTreeService() {

	}

	private static DataTypeDataTreeService getInstance() {
		synchronized (DataTypeDataTreeService.class) {
			if (null == instance) {
				instance = new DataTypeDataTreeService();
			}
		}
		return instance;
	}

}
