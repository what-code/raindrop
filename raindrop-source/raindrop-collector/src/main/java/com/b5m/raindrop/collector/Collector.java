package com.b5m.raindrop.collector;

public interface Collector {

	public void beginCommit();
	
	public void commit();
	
	public String getCollectorName();
}
