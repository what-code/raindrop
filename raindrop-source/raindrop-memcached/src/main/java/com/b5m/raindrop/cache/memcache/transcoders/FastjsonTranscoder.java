package com.b5m.raindrop.cache.memcache.transcoders;

import net.rubyeye.xmemcached.transcoders.BaseSerializingTranscoder;
import net.rubyeye.xmemcached.transcoders.CachedData;
import net.rubyeye.xmemcached.transcoders.Transcoder;

public class FastjsonTranscoder extends BaseSerializingTranscoder implements
		Transcoder<Object> {

	// General flags
	public static final int SERIALIZED = 1;
	public static final int COMPRESSED = 2;
	public static final int SPECIAL_OBJECT = (1 << 8);
	

	@Override
	public CachedData encode(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object decode(CachedData d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPrimitiveAsString(boolean primitiveAsString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPackZeros(boolean packZeros) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPrimitiveAsString() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPackZeros() {
		// TODO Auto-generated method stub
		return false;
	}

}
