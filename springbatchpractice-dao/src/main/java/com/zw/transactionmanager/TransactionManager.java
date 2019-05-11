package com.zw.transactionmanager;

public interface TransactionManager<T> {

	T startTransaction() ;
	void commmitTransaction(T transactionStatus);
	void rollbackTransaction(T transactionStatus);
}
