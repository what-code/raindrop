package com.b5m.raindrop.collector;

public interface CollectorFactory {

	public Collector CreateLatencyCollector(String id);
	
	
}
