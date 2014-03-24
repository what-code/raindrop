package com.b5m.raindrop.collector.metrics;

public class MetricsBaseInfo {

	private Long id;
	private String enName;
	private String zhName;
	
	
	public MetricsBaseInfo(Long id, String enName, String zhName)
	{
		this.setId(id);
		this.setEnName(enName);
		this.setZhName(zhName);
	}


	public String getZhName() {
		return zhName;
	}


	public void setZhName(String zhName) {
		this.zhName = zhName;
	}


	public String getEnName() {
		return enName;
	}


	public void setEnName(String enName) {
		this.enName = enName;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
}
