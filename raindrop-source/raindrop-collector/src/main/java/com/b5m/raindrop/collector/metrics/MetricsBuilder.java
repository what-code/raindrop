package com.b5m.raindrop.collector.metrics;

public class MetricsBuilder {

	
	public static Metrics builder(long id, long timestamp, long value)
	{
		Metrics metrics = new Metrics();
/*		MetricsBaseInfo baseInfo = MetricsConfig.getMetricsBaseInfo(id);
		metrics.setId(id);
		metrics.setEnName(baseInfo.getEnName());
		metrics.setZhName(baseInfo.getZhName());
		metrics.setTimestamp(timestamp);
		metrics.setValue(value);*/
		
		return metrics;
	}
	
}
