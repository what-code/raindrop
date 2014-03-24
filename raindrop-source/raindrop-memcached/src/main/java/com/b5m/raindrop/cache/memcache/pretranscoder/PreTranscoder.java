package com.b5m.raindrop.cache.memcache.pretranscoder;

public interface PreTranscoder<T> {

	String encode(T o);

	T decode(String value);
}
