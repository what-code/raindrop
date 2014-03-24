package com.b5m.raindrop.collector.daemon;

import com.b5m.raindrop.collector.CollectorInfo;
import com.b5m.raindrop.collector.CollectorInitListener;
import com.b5m.raindrop.collector.DefaultCollectorInitListener;
import com.b5m.raindrop.collector.metrics.Metrics;

public class CollectorManagerHelper {

	private static Thread collectorThreadHandle;
	
	private static CollectorInitListener defaultListener = new DefaultCollectorInitListener();
	
	public static String hostname;
	
	public static String ipaddr;
	
	public static String port;
	
	public static void init(CollectorInitListener listener)
	{
		CollectorManager.getInstance();

		// 默认
		defaultListener.init();
		
		if (null != listener)
		{
			listener.init();
		}
		
		start();
	}
	
	private static void start()
	{
		collectorThreadHandle = new Thread()
		{
			public void run() {
				int i = 0;
				while (true)
				{
					//System.out.println("run test." + (i++));
					CollectorManager.getInstance().processCollector();
					
					if (Thread.interrupted())
					{
						break;
					}
				}
				
				System.out.println("run end.");
			}
		};
		collectorThreadHandle.start();
	}
	
	
	
	public static void SendMetrics(Metrics metrics)
	{
		CollectorManager.getInstance().addMetrics(metrics);
	}
	
	public static void SendCollectorInfo(CollectorInfo collectorInfo)
	{
		CollectorManager.getInstance().addCollectorInfo(collectorInfo);
	}
	
	public static void deInit()
	{
		collectorThreadHandle.interrupt();
	}
}
