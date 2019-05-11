package com.zw.dynamic.datasource;

public class DynamicDataSourceHolder {

	private static volatile ThreadLocal<String> holder = new ThreadLocal<String>();


	public static void setDataSource(String dataSource) {
		holder.set(dataSource);
	}
	
	public static String getDataSource() {
		return holder.get();
	}
	
}
