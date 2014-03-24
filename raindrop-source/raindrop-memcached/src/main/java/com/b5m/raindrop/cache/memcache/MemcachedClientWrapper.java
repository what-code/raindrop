package com.b5m.raindrop.cache.memcache;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.Feature;
import com.b5m.raindrop.cache.ICacheClient;
import com.b5m.raindrop.cache.memcache.pretranscoder.PreTranscoder;

public final class MemcachedClientWrapper implements ICacheClient {

	public final MemcachedClient memcachedClient;

	private final String OBJECT_TYPE_FALG = "@type";
	private int DEFALUT_TIMEOUT = 1000;
	private int DEFALUT_EXPIREDTIME = 60 * 60 * 2;

	public MemcachedClientWrapper(final MemcachedClient memcachedClient) {
		super();
		this.memcachedClient = memcachedClient;
	}
	
	public <T extends Object> boolean put(String key, T value, int exp, Class<T> clazz)
	{
		String jsonString = JSON.toJSONString(value);

		try {
			memcachedClient.setWithNoReply(key, exp, jsonString);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public <T extends Object> boolean put(String key, T value, Class<T> clazz)
	{
		return this.put(key, value, DEFALUT_EXPIREDTIME, clazz);
	}

	public <T extends Object> T get(String key, Class<T> clazz)
	{
		String value = null;
		try {
			value = memcachedClient.get(key);
		} catch (TimeoutException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MemcachedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (value == null) {
			return null;
		}
		
		try {
			T v = JSON.parseObject(value, clazz,
					Feature.InitStringFieldAsEmpty);
			return v;
		} catch (JSONException e) {
			;
		}
		return null;
	}
	

	public <T extends Object> T get(String key, PreTranscoder<T> transcoder) {
		T o = this.get(key, DEFALUT_TIMEOUT, transcoder);
		return o;
	}

	public <T extends Object> T get(String key, int timeOut,
			PreTranscoder<T> transcoder) {
		try {
			String value = memcachedClient.get(key, timeOut);
			if (value == null) {
				return null;
			}

			T v = transcoder.decode(value);
			return v;

		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public <T extends Object> T get(String key) {
		T o = this.get(key, DEFALUT_TIMEOUT);
		return o;
	}

	@Override
	public <T extends Object> T get(String key, int timeOut) {

		try {
			return memcachedClient.get(key, timeOut);
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private boolean isObject(String value) {
		// {"@type":"com.b5m.raindrop.cache.memcache.Goods","id":"goodsid","name":"商品"}
		// FIXME： 会存在一定的BUG
		if (value.indexOf(OBJECT_TYPE_FALG) > 0) {
			return true;
		}
		return false;
	}

	public <T extends Object> boolean put(String key, T value,
			PreTranscoder<T> transcoder) {

		return this.put(key, value, DEFALUT_EXPIREDTIME, transcoder);
	}

	public <T extends Object> boolean put(String key, T value, int expiredTime,
			PreTranscoder<T> transcoder) {

		try {

			String jsonString = transcoder.encode(value);

			memcachedClient.setWithNoReply(key, expiredTime, jsonString);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public <T extends Object> boolean put(String key, T value) {

		return this.put(key, value, DEFALUT_EXPIREDTIME);
	}

	@Override
	public <T extends Object> boolean put(String key, T value, int expiredTime) {
		if (value == null) {
			return false;
		}
		try {

			memcachedClient.setWithNoReply(key, expiredTime, value);

			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(String key) {
		try {
			memcachedClient.deleteWithNoReply(key);
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void shutdown() {
		try {
			memcachedClient.shutdown();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean touch(String key) {
		// TODO Auto-generated method stub
		try {
			memcachedClient.touch(key, getDefalutExpiredTime());
			return true;
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public long incr(String key, long value) throws TimeoutException,
			InterruptedException, MemcachedException {
		// TODO Auto-generated method stub
		return memcachedClient.incr(key, value);
	}

	@Override
	public long decr(String key, long value) throws TimeoutException,
			InterruptedException, MemcachedException {
		// TODO Auto-generated method stub
		return memcachedClient.decr(key, value);
	}

	@Override
	public void setDefalutExpiredTime(int expiredTime) {
		if (expiredTime >= 0) {
			DEFALUT_EXPIREDTIME = expiredTime;
		}
	}

	@Override
	public int getDefalutExpiredTime() {

		return DEFALUT_EXPIREDTIME;
	}

	@Override
	public void setDefalutTimeOut(int timeOut) {
		if (timeOut >= 0) {
			DEFALUT_TIMEOUT = timeOut;
		}
	}

	@Override
	public int getDefalutTimeOut() {
		return DEFALUT_TIMEOUT;
	}
}
