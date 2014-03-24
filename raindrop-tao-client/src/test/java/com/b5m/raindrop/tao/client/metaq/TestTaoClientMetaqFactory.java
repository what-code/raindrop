package com.b5m.raindrop.tao.client.metaq;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.b5m.raindrop.tao.client.ICounterService;
import com.b5m.raindrop.tao.client.ITaoClientFactory;
import com.taobao.metamorphosis.exception.MetaClientException;

public class TestTaoClientMetaqFactory {
	private ITaoClientFactory factory;
	
	public TestTaoClientMetaqFactory(){
		org.apache.log4j.BasicConfigurator.configure();
	}
	
	@Before
	public void setUp() throws MetaClientException{
		factory = new TaoClientMetaqFactory(FactoryConfig.simpleBuild("localhost:2181", "spout-test"));
	}
	
	@Test
	public void testCreateCounterService() {
		ICounterService counterService = factory.createCounterService();
		Assert.assertEquals(true, counterService instanceof AbstractMetaqCompoment);
		AbstractMetaqCompoment compoment = (AbstractMetaqCompoment)counterService;
		Assert.assertNotNull(compoment.getProducer());
	}

}
