package com.b5m.raindrop.storm.collector.utils;

import com.b5m.raindrop.collector.metrics.Metrics;

public class MetricsHelp {

	public static final String SPLIT_CHAR = ",";
	public static final String ENTRY = "\r\n";
	
	public static String buildString(final Metrics metrics)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(metrics.getTimestamp()).append(SPLIT_CHAR);
		sb.append(metrics.getName()).append(SPLIT_CHAR);
		sb.append(metrics.getValue());//.append(ENTRY);
		return sb.toString();
	}
	
}
