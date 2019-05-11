package mapper.test;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.TransactionStatus;

import com.zw.dynamic.datasource.DataSourceAspect;
import com.zw.dynamic.datasource.DynamicDataSourceImpl;
import com.zw.mapper.business.UserMapper;
import com.zw.service.UserServiceImpl;
import com.zw.transactionmanager.DefaultUnitWork;
import com.zw.transactionmanager.SpringTransactionManager;
import com.zw.transactionmanager.TransactionManager;
import com.zw.vo.User;

public class TestMapper {

	@Test
	public void test() {
		ApplicationContext context = new FileSystemXmlApplicationContext("target/classes/config/start.xml");
//				new ClassPathXmlApplicationContext("classpath:/*.xml");
		
		DataSourceAspect as = context.getBean(DataSourceAspect.class);
		System.out.println(as);
		UserServiceImpl userService = context.getBean(UserServiceImpl.class);
		User user = userService.findUser(1);
		System.out.println(user.getName());		
		DynamicDataSourceImpl d = context.getBean(DynamicDataSourceImpl.class);
		System.out.println(d);
	}
	
	@Test
	public void testDynInsert() {
		ApplicationContext context = new FileSystemXmlApplicationContext("target/classes/config/start.xml");
//		new ClassPathXmlApplicationContext("classpath:/*.xml");

//		DataSourceAspect as = context.getBean(DataSourceAspect.class);
//		System.out.println(as);
		
		User user = new User();
		user.setId(4);
		user.setName("insert");
		UserServiceImpl userService = context.getBean(UserServiceImpl.class);
		userService.addUser(user);
//		DynamicDataSourceImpl d = context.getBean(DynamicDataSourceImpl.class);
//		System.out.println(d);
	}
	
	@Test
	public void testInsert() throws BeansException, SQLException {
		ApplicationContext context = new FileSystemXmlApplicationContext("target/classes/config/start.xml");
//				new ClassPathXmlApplicationContext("classpath:/*.xml");
		TransactionManager<TransactionStatus> tm = context.getBean(SpringTransactionManager.class);
		DefaultUnitWork work = new DefaultUnitWork(tm);
		
//		UserMapper userMapper = context.getBean(UserMapper.class);
		
		User user = new User();
		user.setId(5);
		user.setName("test rollback");
		UserServiceImpl userService = context.getBean(UserServiceImpl.class);
		
		// 在startTransaction的时候，应该是调用jdbc的setAutoCommit为0，如果在后面修改了数据源那么会怎样,此时查询的connection应该是slave数据源
		// 那么开启事务的connection是否还是master的一个connection
		/**
		 * spring的事务管理机制
		 * 会给每个线程的每个数据源绑定一个connectionHolder
		 * 在事务管理器里的datasource应该需要从动态数据源中获取当前的数据源
		 */
		work.startTransaction();
		
		Connection conn = DataSourceUtils.doGetConnection(context.getBean("dataSource", DataSource.class));
		
//		try {
			userService.findUser(5);
	    Connection conn_sla = DataSourceUtils.doGetConnection(context.getBean("dataSourceSlave1", DataSource.class));
//			throw new SQLException("test");
//		} catch (SQLException e) {
//			e.printStackTrace();
//			work.rollbackTransaction();
//		}
		
		work.commmitTransaction();
		
	}
	

	@Test
	public void testAopInsert() {
		//expression="execution(* com.zw.mapper.business.*.*(..))"
		// 好像是配置再mapper上的各种sql上，所以再报错也没有rollback
		ApplicationContext context = new FileSystemXmlApplicationContext("target/classes/config/datasource.xml");
//				new ClassPathXmlApplicationContext("classpath:/*.xml");
		TransactionManager<TransactionStatus> tm = context.getBean(SpringTransactionManager.class);
//		DefaultUnitWork work = new DefaultUnitWork(tm);
		
		UserMapper userMapper = context.getBean(UserMapper.class);
		
		User user = new User();
		user.setId(4);
		user.setName("xiaowww");
//		work.startTransaction();
		
		try {
			userMapper.addUser(user);
			throw new SQLException("test");
		} catch (SQLException e) {
			e.printStackTrace();
//			work.rollbackTransaction();
		}
		
//		work.commmitTransaction();
		
	}
}
