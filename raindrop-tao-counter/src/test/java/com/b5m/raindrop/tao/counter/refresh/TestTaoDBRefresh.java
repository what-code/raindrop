package com.b5m.raindrop.tao.counter.refresh;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

import backtype.storm.tuple.Tuple;

import com.b5m.raindrop.tao.db.template.JdbcConfig;
import com.b5m.raindrop.tao.db.template.TemplateDaoFactory;

public class TestTaoDBRefresh {

	private TaoGoodsDBRefresh refresh;
	
	private TemplateDaoFactory daoFactory;

	public TestTaoDBRefresh(){
		org.apache.log4j.BasicConfigurator.configure();
		JdbcConfig config = new JdbcConfig();
		config.setDriverClass("com.mysql.jdbc.Driver");
		config.setUrl("jdbc:mysql://10.10.99.207:3306/b5m_tao?autoReconnect=true&useUnicode=true&characterEncoding=UTF8&mysqlEncoding=utf8&zeroDateTimeBehavior=convertToNull");
		config.setUser("b5m");
		config.setPassword("izene123");
		daoFactory = new TemplateDaoFactory(config);
		refresh = new TaoGoodsDBRefresh(daoFactory);
	}
	
	@Test
	public void test() {
		Map<String, Long> counter = new HashMap<String, Long>();
		// 设置测试的统计数据
		counter.put("10001", 10L);
		counter.put("10002", 10L);
		counter.put("10003", 10L);
		counter.put("10004", 10L);
		counter.put("10005", 10L);
		counter.put("10006", 10L);
		counter.put("10007", 10L);
		
		refresh.refresh(counter, Mockito.mock(Tuple.class));
		
		Assert.assertEquals(false, counter.containsKey("10001"));
		Assert.assertEquals(false, counter.containsKey("10002"));
		Assert.assertEquals(false, counter.containsKey("10003"));
		Assert.assertEquals(false, counter.containsKey("10004"));
		Assert.assertEquals(false, counter.containsKey("10005"));
		Assert.assertEquals(false, counter.containsKey("10006"));
		Assert.assertEquals(false, counter.containsKey("10007"));
	}

	@After
	public void tearDown() throws IOException{
		if(null == daoFactory)
			return ;
		daoFactory.destroyDataSource();
	}
}
