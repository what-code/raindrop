package com.b5m.raindrop.collector.process;

import java.util.LinkedList;
import java.util.List;

import com.b5m.raindrop.collector.CollectorInfo;
import com.b5m.raindrop.collector.CollectorInfoProcess;
import com.b5m.raindrop.collector.metrics.Metrics;

public class BasicCollectorInfoProcess implements CollectorInfoProcess {

	@Override
	public List<Metrics> process(CollectorInfo collectorInfo) {
		// TODO Auto-generated method stub
		List<Metrics> lstMetrics = new LinkedList<Metrics>();
		lstMetrics.add(new Metrics(collectorInfo.getMetricsName(),
				collectorInfo.getValue(), collectorInfo.getTimestamp()));
		return lstMetrics;
	}

	@Override
	public List<Metrics> passiveProcess() {
		// TODO Auto-generated method stub
		return null;
	}

}
