package com.b5m.raindrop.cache.memcache.keyprovider;

import org.apache.commons.codec.digest.DigestUtils;

import net.rubyeye.xmemcached.KeyProvider;

public class HashKeyProvider implements KeyProvider {
	@Override
	public String process(String key) {
		return DigestUtils.md5Hex(key);
	}

}
