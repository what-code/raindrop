package com.b5m.raindrop.cache.memcache.pretranscoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.Feature;

public class FastjsonPreTranscoder<T> implements PreTranscoder<T> {

	private Class<T> clazz;
	public FastjsonPreTranscoder(Class<T> clazz)
	{
		this.clazz = clazz;
	}
	
	@Override
	public String encode(T o) {
		// TODO Auto-generated method stub
		String jsonString = JSON.toJSONString(o);
		return jsonString;
	}

	@Override
	public T decode(String value) {

		try {
			T v = JSON.parseObject(value, clazz,
					Feature.InitStringFieldAsEmpty);
			return v;
		} catch (JSONException e) {
			;
		}
		return null;
	}



}
