package com.b5m.raindrop.collector.metrics;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class Metrics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5926031546549778315L;

	private String name;
	private Long value;
	private Long timestamp;
	
	private List<MetricsTag> tags = new LinkedList<MetricsTag>();
	
	public Metrics()
	{
		
	}

	public Metrics(String name, Long value, Long timestamp)
	{
		this.name = name;
		this.value = value;
		this.timestamp = timestamp;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Long getValue() {
		return value;
	}
	public void setValue(Long value) {
		this.value = value;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	
	private static final String Enter = "\r\n";
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder("Metrics: --------").append(Enter);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = formatter.format(timestamp);
		sb.append(" date: ").append(time).append(Enter);
		sb.append(" name: ").append(name).append(Enter);
		sb.append(" value: ").append(value).append(Enter);
		for (MetricsTag tag : tags)
		{
			sb.append(" ").append(tag).append(Enter);
		}
		return sb.toString();
	}
	
	public void addMetricsTag(MetricsTag tag)
	{
		tags.add(tag);
	}
	
	public void addMetricsTag(String name, String value)
	{
		tags.add(new MetricsTag(name, value));
	}

	public List<MetricsTag> getTags() {
		return tags;
	}

	public void setTags(List<MetricsTag> tags) {
		this.tags = tags;
	}
}
