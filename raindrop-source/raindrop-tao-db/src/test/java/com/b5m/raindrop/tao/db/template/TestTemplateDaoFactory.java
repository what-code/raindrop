package com.b5m.raindrop.tao.db.template;

import junit.framework.Assert;

import org.junit.Test;

public class TestTemplateDaoFactory {
	private final JdbcConfig config;

	public TestTemplateDaoFactory(){
		config = new JdbcConfig();
		config.setDriverClass("com.mysql.jdbc.Driver");
		config.setUrl("jdbc:mysql://10.10.99.207:3306/b5m_tao?autoReconnect=true&useUnicode=true&characterEncoding=UTF8&mysqlEncoding=utf8&zeroDateTimeBehavior=convertToNull");
		config.setUser("b5m");
		config.setPassword("izene123");
	}
	
	/**
	 * 测试{@link TemplateDaoFactory}能否被成功创建出来
	 */
	@Test
	public void testInstance() {		
		TemplateDaoFactory factory = new TemplateDaoFactory(config);
		Assert.assertNotNull(factory.getDataSource());
		Assert.assertNotNull(factory.getJdbcConfig());
	}

	/**
	 * 测试Datasource的链接状态
	 * @throws SQLException 
	 */
	/*@Test
	public void testConnection() throws SQLException{
		TemplateDaoFactory factory = new TemplateDaoFactory(config);
		DataSource ds = factory.getDatasource();
		Connection conn = ds.getConnection();
		for(int i = 0;i < 10;i++){
			Assert.assertEquals(true, conn == ds.getConnection());
		}
		conn.close();
		Assert.assertEquals(false, conn.isClosed());
		Assert.assertEquals(true, conn == ds.getConnection());
	}*/
}
