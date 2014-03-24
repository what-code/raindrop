package com.b5m.raindrop.collector;

import com.b5m.raindrop.collector.daemon.CollectorManager;
import com.b5m.raindrop.collector.process.BasicCollectorInfoProcess;
import com.b5m.raindrop.collector.process.CycleCollectorInfoProcess;

public class TestInitListener implements CollectorInitListener {
	public static final String METRICS_NAME = "latency.test";
	public static final String METRICS_NAME2 = "latency.test2";
	@Override
	public void init() {
		// TODO Auto-generated method stub
		CollectorManager.getInstance().registerCollectorInfoProcess(
				METRICS_NAME, new CycleCollectorInfoProcess());

		CollectorManager.getInstance().registerCollectorInfoProcess(
				METRICS_NAME, new BasicCollectorInfoProcess());
	}
}
