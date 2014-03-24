package com.b5m.raindrop.collector;

import java.text.SimpleDateFormat;

public class CollectorInfo {

	private String metricsName;
	private long timestamp;
	private long value;
	
	
	public CollectorInfo(long timestamp, String metricsName, long value)
	{
		this.timestamp = timestamp;
		this.metricsName = metricsName;
		this.value = value;
	}
	
	public String getMetricsName() {
		return metricsName;
	}
	public void setMetricsName(String metricsName) {
		this.metricsName = metricsName;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public long getValue() {
		return value;
	}
	public void setValue(long value) {
		this.value = value;
	}
	
	private static final String Enter = "\r\n";
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("CollectorInfo").append(Enter);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = formatter.format(timestamp);
		sb.append(" date: ").append(time).append(Enter);
		sb.append(" name: ").append(metricsName).append(Enter);
		sb.append(" value: ").append(value).append(Enter);
		
		return sb.toString();
	}
}
