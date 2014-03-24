package com.b5m.raindrop.collector;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.b5m.raindrop.collector.daemon.CollectorManagerHelper;
import com.b5m.raindrop.collector.metrics.MetricsConfig;

public class TestSimluLatencyCollector {

	
	@Before
	public void setUp() {
		CollectorManagerHelper.init(new TestInitListener());
	}
	
	@After
	public void tearDown() {
		CollectorManagerHelper.deInit();
	}
	
	
	//@Test
	public void testLatencuyCollector()
	{
		RDCollectorFactory factory = new RDCollectorFactory();
		Collector collector = factory
				.CreateLatencyCollector("simluLe.latency.test");
		
		Random rand = new Random();
		int count = 100;
		for (int i = 0; i < count; i++) {
			collector.beginCommit();
			try {
				Thread.sleep(rand.nextInt(count-20)+20);
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
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
