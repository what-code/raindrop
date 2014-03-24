package com.b5m.raindrop.cache.memcache;

public class Config {

    public static final String PROP_PREFIX = "raindrop.memcached.";


	public static final int DEFAULT_READ_THREAD_COUNT = 0;
	public static final boolean DEFAULT_TCP_KEEPLIVE = true;

	public static final int DEFAULT_CONNECT_TIMEOUT = 3000;
	public static final String CONNECT_TIMEOUT_SECONDS = "connectTimeoutSeconds";
	public static final String PROP_CONNECT_TIMEOUT_SECONDS = PROP_PREFIX + CONNECT_TIMEOUT_SECONDS;

	public static final int DEFAULT_TCP_SEND_BUFF_SIZE = 128 * 1024;

	public static final boolean DEFAULT_TCP_NO_DELAY = true;

	public static final int DEFAULT_SESSION_READ_BUFF_SIZE = 256 * 1024;

	public static final int DEFAULT_TCP_RECV_BUFF_SIZE = 128 * 1024;

	public static final long DEFAULT_OP_TIMEOUT = 1000L;

	public static final int DEFAULT_CONNECTION_POOL_SIZE = 1;

	public static final int DEFAULT_SESSION_IDLE_TIMEOUT = 5000;

	public static final long DEFAULT_HEAL_SESSION_INTERVAL = 2000;
	
    private PropertiesHelper props;

    public Config(PropertiesHelper props) {
        this.props = props;
    }

    public int getConnectTimeoutSeconds(String cacheRegion) {
        int globalCacheTimeSeconds = props.getInt(PROP_CONNECT_TIMEOUT_SECONDS,
        		DEFAULT_CONNECT_TIMEOUT);
        return props.getInt(cacheRegionPrefix(cacheRegion) + CONNECT_TIMEOUT_SECONDS,
                globalCacheTimeSeconds);
    }

    private String cacheRegionPrefix(String cacheRegion) {
        return PROP_PREFIX + cacheRegion + ".";
    }

    public PropertiesHelper getPropertiesHelper() {
        return props;
    }
}
