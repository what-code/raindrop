package com.b5m.raindrop.collector;

import java.util.Random;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.b5m.raindrop.collector.daemon.CollectorManager;
import com.b5m.raindrop.collector.daemon.CollectorManagerHelper;
import com.b5m.raindrop.collector.forward.MockMetricsHandler;
import com.b5m.raindrop.collector.process.BasicCollectorInfoProcess;
import com.b5m.raindrop.collector.process.CycleCollectorInfoProcess;

public class TestLatencyCollector {

	private MockMetricsHandler handler;

	public static final String METRICS_NAME = "latency.test";
	public static final String METRICS_NAME2 = "latency.test2";

	public class TestListener implements CollectorInitListener {

		@Override
		public void init() {
			// TODO Auto-generated method stub
			CollectorManager.getInstance().registerCollectorInfoProcess(
					METRICS_NAME, new CycleCollectorInfoProcess());

			CollectorManager.getInstance().registerCollectorInfoProcess(
					METRICS_NAME2, new BasicCollectorInfoProcess());
		}

	}

	@Before
	public void setUp() {

		CollectorManagerHelper.init(new TestListener());
		handler = new MockMetricsHandler();
		CollectorManager.getInstance().registerMetricsHandler(handler);
	}

	//@Test
	public void testLatencyCollector() {
		RDCollectorFactory factory = new RDCollectorFactory();
		Collector collector = factory.CreateLatencyCollector(METRICS_NAME);
		Random rand = new Random();
		int count = 50;
		int maxSleep = 100;
		int minSlepp = 20;
		for (int i = 0; i < count; i++) {
			collector.beginCommit();
			try {
				Thread.sleep(rand.nextInt(maxSleep-minSlepp)+minSlepp);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			collector.commit();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Assert.assertEquals(count, handler.getCount());

	}

	@After
	public void tearDown() {
		CollectorManagerHelper.deInit();
	}
}
