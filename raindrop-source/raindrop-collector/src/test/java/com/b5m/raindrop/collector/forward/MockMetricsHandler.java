package com.b5m.raindrop.collector.forward;

import com.b5m.raindrop.collector.MetricsHandler;
import com.b5m.raindrop.collector.metrics.Metrics;

public class MockMetricsHandler implements MetricsHandler {

	private long i = 0;
	@Override
	public void handlerMetrics(Metrics metrics) {
		// TODO Auto-generated method stub
		System.out.println("MockMetricsHandler: " + metrics);
		i++;
	}
	
	public long getCount()
	{
		return i;
	}

}
