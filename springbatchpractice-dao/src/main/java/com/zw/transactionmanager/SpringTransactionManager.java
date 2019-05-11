package com.zw.transactionmanager;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class SpringTransactionManager implements TransactionManager<TransactionStatus>{
	
	private PlatformTransactionManager transactionManager;
	private TransactionDefinition transactionDefinition;

	
	public SpringTransactionManager() {
		this.transactionDefinition = new DefaultTransactionDefinition();
	}

	public SpringTransactionManager(PlatformTransactionManager transactionManager) {
		this(transactionManager,new DefaultTransactionDefinition());
	}

	public SpringTransactionManager(PlatformTransactionManager transactionManager,
			TransactionDefinition transactionDefinition) {
		this.transactionManager = transactionManager;
		this.transactionDefinition = transactionDefinition;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void setTransactionDefinition(TransactionDefinition transactionDefinition) {
		this.transactionDefinition = transactionDefinition;
	}

	public TransactionStatus startTransaction() {
		return transactionManager.getTransaction(transactionDefinition);
	}

	public void commmitTransaction(TransactionStatus transactionStatus) {
		if(transactionStatus.isNewTransaction() && !transactionStatus.isCompleted()) {
			transactionManager.commit(transactionStatus);
		}
	}

	public void rollbackTransaction(TransactionStatus transactionStatus) {
		if(transactionStatus.isNewTransaction() && !transactionStatus.isCompleted()) {
			transactionManager.rollback(transactionStatus);
		}
	}

}
