package com.b5m.raindrop.tao.client.metaq;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.b5m.raindrop.tao.client.ICounterService;
import com.b5m.raindrop.tao.client.IExceptionCallback;
import com.taobao.metamorphosis.exception.MetaClientException;

public class TestCounterService {
	private ICounterService service;
	
	public TestCounterService(){
		//org.apache.log4j.BasicConfigurator.configure();
	}
	public static final String TOPIC_NAME = "spout-test";
	
	@Before
	public void setUp() throws MetaClientException{
		/*TaoClientMetaqFactory factory = new TaoClientMetaqFactory(FactoryConfig.simpleBuild("10.10.100.1:12181", "test_tao_count")
				.setSendTimeout(10000));*/
		TaoClientMetaqFactory factory = new TaoClientMetaqFactory(FactoryConfig.simpleBuild("localhost:2181", TOPIC_NAME)
				.setSendTimeout(10000));
		service = factory.createCounterService();
	}
	
	@Test
	public void testClick() {
		service.clickGoods("43334", new IExceptionCallback() {
			
			@Override
			public void onException(Throwable t) {
				Assert.fail(t.getMessage());
			}
		});
		
		service.clickAds("12", new IExceptionCallback() {
			
			@Override
			public void onException(Throwable t) {
				Assert.fail(t.getMessage());
			}
		});
	}
	
	/**
	 * 压力测试
	 */
	@Test
	public void testLoading(){
		final int count = 100;
		String[] goodsids = new String[]{"58101", "58102", "58103", "58104", "58105", "58106", "58107", "58108"};
		Random r = new Random();
		final AtomicLong sendCount = new AtomicLong(0);
		for(int i = 0;i < count;i++){
			String goodsid = goodsids[Math.abs(r.nextInt()%(goodsids.length))];
			//System.out.println(">> click goodsid:" + goodsid);
			sendCount.incrementAndGet();
			service.clickGoods(goodsid, new IExceptionCallback() {
				
				@Override
				public void onException(Throwable t) {
					t.printStackTrace();
					System.out.println("Total send count:" + sendCount.decrementAndGet());
					throw new RuntimeException(t);
				}
			});
		}
		System.out.println("Total send count:" + sendCount.get());
	}
	
	@After
	public void tearDown() throws IOException{
		AbstractMetaqCompoment comp = (AbstractMetaqCompoment)service;
		comp.close();
	}

}
