package com.b5m.raindrop.cache.memcache;

public interface MemcachedClientFactory {

	/**
	 * 创建MemcachedClient的封装对象
	 * @return
	 * @throws Exception
	 */
	public MemcachedClientWrapper createMemcachedClient(String model) throws Exception;
	
}
