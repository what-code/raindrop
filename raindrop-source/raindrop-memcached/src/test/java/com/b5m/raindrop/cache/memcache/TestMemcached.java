package com.b5m.raindrop.cache.memcache;

import java.util.Properties;

import junit.framework.Assert;
import net.rubyeye.xmemcached.KeyProvider;

import org.junit.After;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.b5m.raindrop.cache.memcache.keyprovider.PrefixKeyProvider;
import com.b5m.raindrop.cache.memcache.pretranscoder.FastjsonPreTranscoder;
import com.b5m.raindrop.cache.memcache.pretranscoder.PreTranscoder;



public class TestMemcached {

	private MemcachedClientWrapper wrapper;
	
	public TestMemcached(){
		try {
			wrapper = initMemcachedClient("test");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private MemcachedClientWrapper initMemcachedClient(String model) throws Exception
	{
		Properties props = PropertiesHelper.Load(this.getClass().getResource("/raindrop/memcached.properties"));
		PropertiesHelper properties = new PropertiesHelper(props);	
		MemcachedClientFactory factory = new XMemcachedClientFactory(properties);
		return factory.createMemcachedClient(model);
	}
	
	//@Test
	public void testMemcached()  {
		try {
			String prefix = "test";
			MemcachedClientWrapper wrapper = initMemcachedClient(prefix);
			wrapper.put("abcde", "abcde");
			KeyProvider keyProvider = new PrefixKeyProvider(prefix);
			String key = keyProvider.process("abcde");
			System.out.println("key >> " + key);
			String value = wrapper.get("abcde");
			
			Assert.assertEquals(value, "abcde");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//wrapper.memcachedClient.s
	}
	
	
	//@Test
	public void testMemcached1()  {
		try {
			String prefix = "ttt";
			MemcachedClientWrapper wrapper = initMemcachedClient(prefix);
			Goods g = new Goods();
			wrapper.put("abcde", g);
			KeyProvider keyProvider = new PrefixKeyProvider(prefix);
			String key = keyProvider.process("abcde");
			System.out.println("key >> " + key);
			Goods value = wrapper.get("abcde");
			
			Assert.assertEquals(value.id, g.id);
			Assert.assertEquals(value.name, g.name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//wrapper.memcachedClient.s
	}
	
	private void testMem() throws Exception
	{
		String prefix = "fastjson";
		MemcachedClientWrapper wrapper = initMemcachedClient(prefix);
		Goods g = new Goods();
		//wrapper.put("abcde", g);
		KeyProvider keyProvider = new PrefixKeyProvider(prefix);
		String key = keyProvider.process("abcde");
		
		PreTranscoder transcoder1 = new FastjsonPreTranscoder(Goods.class);
		System.out.println("key >> " + key);
		for (int i=0; i<10000000; i++)
		{
			Goods value = wrapper.get("abcde", transcoder1);
		}
	}
	
	@Test
	public void testMemcached2()  {
		
		int threadNum = 3;
		for (int i=0;i < threadNum; i++)
		{
			new Thread()
			{
				public void run()
				{
					try {
						testMem();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("END.----");
				}
			}.start();
		}
		try {
			Thread.sleep(200000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	//@Test
	public void testMemcachedGetObject()
	{
		String prefix = "getTest";
		String key = "abcde";
		MemcachedClientWrapper wrapper = null;
		try {
			wrapper = initMemcachedClient(prefix);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Goods g = new Goods();
		KeyProvider keyProvider = new PrefixKeyProvider(prefix);
		String keyGen = keyProvider.process(key);
		System.out.println("keyGen >> " + keyGen);
		
		@SuppressWarnings({ "rawtypes" })
		PreTranscoder transcoder = new FastjsonPreTranscoder(GoodGoods.class);
		wrapper.put(key, g, transcoder);
		JSON.DEFAULT_GENERATE_FEATURE = 0;
		GoodGoods value = (GoodGoods)wrapper.get(key, transcoder );
		Assert.assertEquals(value.getId(), g.id);
		Assert.assertEquals(value.getName(), g.name);
		
		
		wrapper.put("string", "name lee");
		String vv = wrapper.get("string");
		Assert.assertEquals("name lee".equals(vv),true);
		
		wrapper.put("integer", new Integer(100));
		Integer a = wrapper.get("integer");
		Assert.assertEquals(new Integer(100).equals(a), true);
		
		Goods aaa = new Goods();
		wrapper.put("goods", new Goods());
		Goods bbb = wrapper.get("goods");
		Assert.assertEquals(aaa.getId(), bbb.getId());
		Assert.assertEquals(aaa.getName(), bbb.getName());
	}
	
	@SuppressWarnings("rawtypes")
	//@Test
	public void testFastjsonPreTranscoder()
	{
		String prefix = "fastjson";
		String key = "fastjson";
		MemcachedClientWrapper wrapper = null;
		try {
			wrapper = initMemcachedClient(prefix);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PreTranscoder<GoodGoods> transcoder2 = new FastjsonPreTranscoder<GoodGoods>(GoodGoods.class);
		transcoder2.decode("test");
		
		GoodGoods g = new GoodGoods();
		PreTranscoder transcoder = new FastjsonPreTranscoder(GoodGoods.class);
		wrapper.put(key, g, transcoder);
		
		PreTranscoder transcoder1 = new FastjsonPreTranscoder(Goods.class);
		Goods value = wrapper.get(key, transcoder1 );
		
		Assert.assertEquals(value.getId(), g.getId());
		Assert.assertEquals(value.getName(), g.getName());
	}
	
	@After
	public void tearDown(){

	}
}
