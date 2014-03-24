package com.b5m.raindrop.collector;

import com.b5m.raindrop.collector.daemon.CollectorManager;
import com.b5m.raindrop.collector.process.CycleCollectorInfoProcess;

public class DefaultCollectorInitListener implements CollectorInitListener {

	@Override
	public void init() {
		// TODO Auto-generated method stub

		CollectorManager.getInstance()
				.registerCollectorInfoProcess(CollectorManager.MEMCACHED_GET,
						new CycleCollectorInfoProcess());

		CollectorManager.getInstance()
		.registerCollectorInfoProcess(CollectorManager.MEMCACHED_PUT,
				new CycleCollectorInfoProcess());
		
		CollectorManager.getInstance()
		.registerCollectorInfoProcess(CollectorManager.SF1_SEARCH,
				new CycleCollectorInfoProcess());
	}

}
