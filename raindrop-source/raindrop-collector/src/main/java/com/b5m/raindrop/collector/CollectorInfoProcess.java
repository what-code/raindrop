package com.b5m.raindrop.collector;

import java.util.List;

import com.b5m.raindrop.collector.metrics.Metrics;

public interface CollectorInfoProcess {

	public List<Metrics> process(CollectorInfo collectorInfo);
	
	public List<Metrics> passiveProcess();
	
}
