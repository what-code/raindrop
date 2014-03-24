package com.b5m.raindrop.tao.db.template;

import java.sql.SQLException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.b5m.raindrop.tao.db.ITopDao;
import com.b5m.raindrop.tao.db.module.GoodsBean;

public class TestTopDao {
	private final JdbcConfig config;

	public TestTopDao(){
		org.apache.log4j.BasicConfigurator.configure();
		config = new JdbcConfig();
		config.setDriverClass("com.mysql.jdbc.Driver");
		config.setUrl("jdbc:mysql://10.10.99.207:3306/b5m_tao?autoReconnect=true&useUnicode=true&characterEncoding=UTF8&mysqlEncoding=utf8&zeroDateTimeBehavior=convertToNull");
		config.setUser("b5m");
		config.setPassword("izene123");
	}	
	
	@Test
	public void testQueryTopGoods() throws SQLException {
		TemplateDaoFactory factory = new TemplateDaoFactory(config);
		ITopDao topDao = factory.createTopDao();
		List<GoodsBean> goodsBeans = topDao.queryTopGoods();
		Assert.assertEquals(5, goodsBeans.size());
	}

}
