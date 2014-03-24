package com.b5m.raindrop.collector.metrics;

import java.util.HashMap;
import java.util.Map;

public class MetricsConfig {

/*	public static final long LATENCY_TAO_BEGIN = 1000L;

	public static final long LATENCY_TAO_HOME = 1001L;

	public static final long LATENCY_TAO_MYSQL = 2000L;

	public static final long LATENCY_TAO_CACHE = 3000L;

	public static final long LATENCY_TAO_CACHE_GET = 3001L;

	public static final long LATENCY_TAO_CACHE_PUT = 3002L;

	private static Map<Long, MetricsBaseInfo> mapMetricsBaseInfos = new HashMap<Long, MetricsBaseInfo>();

	private static final long LATENCY_TAO_UNKNOWN = 9999999999L;
	private static final MetricsBaseInfo unknownMetrics = new MetricsBaseInfo(LATENCY_TAO_UNKNOWN, "unknown", "未知");
	
	static {
		mapMetricsBaseInfos.put(LATENCY_TAO_CACHE_GET, new MetricsBaseInfo(
				LATENCY_TAO_CACHE_GET, "memcached.get", "从Memcached获取"));
		mapMetricsBaseInfos.put(LATENCY_TAO_CACHE_PUT, new MetricsBaseInfo(
				LATENCY_TAO_CACHE_GET, "memcached.put", "放置到Memcached"));
		
		mapMetricsBaseInfos.put(LATENCY_TAO_CACHE_PUT, new MetricsBaseInfo(
				LATENCY_TAO_HOME, "tao.home", "打开TAO主页"));
	}

	
	public static MetricsBaseInfo getMetricsBaseInfo(long id)
	{
		MetricsBaseInfo metrics = mapMetricsBaseInfos.get(id);
		if (null == metrics)
		{
			metrics = unknownMetrics;
		}
		return metrics;
	}*/
}
