package com.zw.transactionmanager;

import org.springframework.transaction.TransactionStatus;

public class DefaultUnitWork {

	private final TransactionManager<TransactionStatus> transactionManager;
	private TransactionStatus transactionStatus;
	
	public DefaultUnitWork(TransactionManager<TransactionStatus> transactionManager) {
		super();
		this.transactionManager = transactionManager;
	}
	
	public void startTransaction() {
		transactionStatus =  (TransactionStatus) transactionManager.startTransaction();
	}

	public void commmitTransaction() {
		transactionManager.commmitTransaction(transactionStatus);
	}

	public void rollbackTransaction() {
		transactionManager.rollbackTransaction(transactionStatus);
	}
}
