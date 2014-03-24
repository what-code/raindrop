package com.b5m.raindrop.collector;

import com.b5m.raindrop.collector.daemon.CollectorManagerHelper;
import com.b5m.raindrop.collector.metrics.Metrics;
import com.b5m.raindrop.collector.metrics.MetricsBuilder;

public class LatencyCollecter extends BaseCollector {

	private long latency;
	
	public LatencyCollecter(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	public LatencyCollecter(String id, Enabler enabler) {
		super(id, enabler);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void commit() {

		super.commit();
		
		if (endTimestamp > beginTimestamp)
		{
			setLatency(endTimestamp - beginTimestamp);
		}
		else
		{
			setLatency(1);
		}
		
		if (enabler.isEnabled())
		{
			// TODO 发送
			CollectorManagerHelper.SendCollectorInfo(new CollectorInfo(beginTimestamp, id, latency));
		}
	}

	public long getLatency() {
		return latency;
	}


	public void setLatency(long latency) {
		this.latency = latency;
	}
	
}
