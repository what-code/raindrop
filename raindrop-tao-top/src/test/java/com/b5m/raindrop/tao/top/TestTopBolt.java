package com.b5m.raindrop.tao.top;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Tuple;

import com.b5m.raindrop.cache.memcache.MemcachedClientFactory;
import com.b5m.raindrop.cache.memcache.MemcachedClientWrapper;
import com.b5m.raindrop.cache.memcache.PropertiesHelper;
import com.b5m.raindrop.cache.memcache.XMemcachedClientFactory;
import com.b5m.raindrop.tao.db.module.GoodsBean;
import com.b5m.raindrop.tao.db.template.JdbcConfig;

public class TestTopBolt {
	private TopBolt topBolt;
	
	public TestTopBolt(){
		org.apache.log4j.BasicConfigurator.configure();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Before
	public void setUp(){
		topBolt = new TopBolt();
		Map stormConf = new HashMap();
		JdbcConfig config = new JdbcConfig();
		config.setDriverClass("com.mysql.jdbc.Driver");
		config.setUrl("jdbc:mysql://10.10.99.207:3306/b5m_tao?autoReconnect=true&useUnicode=true&characterEncoding=UTF8&mysqlEncoding=utf8&zeroDateTimeBehavior=convertToNull");
		config.setUser("b5m");
		config.setPassword("izene123");
		stormConf.put(TopBolt.KEY_JDBCCONFIG, config);
		
		topBolt.prepare(stormConf, Mockito.mock(TopologyContext.class), Mockito.mock(OutputCollector.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {
		topBolt.execute(Mockito.mock(Tuple.class));
		
		Properties props = PropertiesHelper.Load(this.getClass().getResource(TopBolt.DEFAULT_MEMCACHED_CONFIG));
		PropertiesHelper properties = new PropertiesHelper(props);	
		MemcachedClientFactory memFactory = new XMemcachedClientFactory(properties);
		MemcachedClientWrapper client = memFactory.createMemcachedClient("tao");
		List<GoodsBean> beans = (List<GoodsBean>)client.get(TopBolt.MEMCACHED_KEY_TOP);
		Assert.assertEquals(5, beans.size());
	}
	
	@After
	public void tearDown(){
		topBolt.cleanup();
	}

}
