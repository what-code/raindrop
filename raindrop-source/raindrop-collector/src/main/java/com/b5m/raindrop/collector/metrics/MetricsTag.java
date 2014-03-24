package com.b5m.raindrop.collector.metrics;

import java.io.Serializable;

public class MetricsTag implements Serializable {

	public static final String TAG_HOST = "host";
	public static final String TAG_IP = "ip";
	public static final String TAG_PORT = "port";
	public static final String TAG_PRODUCT = "product";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7218979591042181983L;

	private String name;
	private String value;
	
	public MetricsTag(String name, String value)
	{
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(name).append("=").append(value);
		return sb.toString();
	}
	
}
