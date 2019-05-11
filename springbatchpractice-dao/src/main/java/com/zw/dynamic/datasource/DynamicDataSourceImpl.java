package com.zw.dynamic.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSourceImpl extends AbstractRoutingDataSource{

	/**
	 * 通过返回的object作为key
	 * 在父类的resolvedDataSources map中获取datasource
	 */
	
	@Override
	protected Object determineCurrentLookupKey() {
		// TODO Auto-generated method stub
		return DynamicDataSourceHolder.getDataSource();
	}

}
