package com.b5m.raindrop.cache.memcache.keyprovider;

import org.apache.commons.lang.StringUtils;

public class PrefixKeyProvider extends HashKeyProvider {

	public final String prefix;
	public static final String DEFAULT_PREFIX = "DEFAULT-";
	public static final String DEFAULT_PADDING = "--------";
	public static final int DEFAULT_PREFIX_LENGTH = 8;
	
	public PrefixKeyProvider(String prefix)
	{
		this.prefix = checkPrefix(prefix);
	}
	
	private String checkPrefix(String prefix)
	{
		if (StringUtils.isBlank(prefix)) {
			return DEFAULT_PREFIX;
		}
		
		int len = prefix.length();
		if (len >= DEFAULT_PREFIX_LENGTH)
		{
			return prefix.substring(0, DEFAULT_PREFIX_LENGTH);
		}
		else
		{
			int padLen = DEFAULT_PREFIX_LENGTH - len;
			return prefix + DEFAULT_PADDING.substring(0,padLen);
		}
	}
	
	@Override
	public String process(String key) {

		return prefix + super.process(key); 
	}


}
