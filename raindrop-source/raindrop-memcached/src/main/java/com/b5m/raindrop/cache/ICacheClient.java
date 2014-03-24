package com.b5m.raindrop.cache;

import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.exception.MemcachedException;

public interface ICacheClient {
	
	/**
	 * 从缓存中取值　
	 * 
	 * @param key
	 * @return
	 */
	public <T extends Object> T get(String key);

	/**
	 * 
	 * @param key
	 * @param timeOut
	 * @return
	 */
	public <T extends Object> T get(String key, int timeOut);

	/**
	 * 新增缓存item
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public <T extends Object> boolean put(String key, T value);

	/**
	 * 新增缓存item
	 * 
	 * @param key
	 * @param value
	 * @param expiredTime
	 * @return
	 */
	public <T extends Object> boolean put(String key, T value, int expiredTime);

	/**
	 * 删除缓存　
	 * 
	 * @param key
	 * @return
	 */
	public boolean delete(String key);
	
	/**
	 * 缓存内容超时时间重续　
	 * 
	 * @param key
	 * @return
	 */
	public boolean touch(String key);
	
	
	/**
	 * 取消连接　
	 * 
	 * @param 
	 * @return
	 */
	public void shutdown();
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public long incr(String key, long value) throws TimeoutException, InterruptedException, MemcachedException;

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 * @throws TimeoutException
	 * @throws InterruptedException
	 * @throws MemcachedException
	 */
	public long decr(String key, long value) throws TimeoutException, InterruptedException, MemcachedException;
	
	/**
	 * 设置默认过期时间　
	 * 
	 * @param expiredTime
	 */
	public void setDefalutExpiredTime(int expiredTime);

	/**
	 * 获取默认过期时间　
	 * 
	 * @return
	 */
	public int getDefalutExpiredTime();

	/**
	 * 设置默认超时时间　
	 * 
	 * @param timeOut
	 */
	public void setDefalutTimeOut(int timeOut);

	/**
	 * 获取默认超时时间
	 * 
	 * @return
	 */
	public int getDefalutTimeOut();
}
