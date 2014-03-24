package com.b5m.raindrop.collector.daemon;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.b5m.raindrop.collector.CollectorInfo;
import com.b5m.raindrop.collector.CollectorInfoProcess;
import com.b5m.raindrop.collector.MetricsHandler;
import com.b5m.raindrop.collector.forward.MetaQMetricsHandler;
import com.b5m.raindrop.collector.metaq.MetaQHelp;
import com.b5m.raindrop.collector.metrics.Metrics;
import com.b5m.raindrop.collector.util.LinkedTransferQueue;
import com.taobao.metamorphosis.client.producer.MessageProducer;

public class CollectorManager {

	public static final String MEMCACHED_GET = "memcached.get";
	public static final String MEMCACHED_PUT = "memcached.put";
	public static final String SF1_SEARCH = "sf1.search";
	
	private static CollectorManager _instance;

	private transient LinkedTransferQueue<Metrics> metricsQueue;

	private transient LinkedTransferQueue<CollectorInfo> collectorInfoQueue;

	private MetricsHandler metricsHandler;
	
	private HashMap<String, List<CollectorInfoProcess>> mapCollectorInfoProcess = new HashMap<String, List<CollectorInfoProcess>>();
	

	private CollectorManager() {

		this.collectorInfoQueue = new LinkedTransferQueue<CollectorInfo>();
		this.metricsQueue = new LinkedTransferQueue<Metrics>();

		MessageProducer messageProducer = MetaQHelp.buildMessageProducer();
		this.metricsHandler = new MetaQMetricsHandler(messageProducer);
	}

	public void registerCollectorInfoProcess(String name, CollectorInfoProcess process)
	{
		List<CollectorInfoProcess> lstCollectorInfoProcess;
		if ( !mapCollectorInfoProcess.containsKey(name) )
		{
			lstCollectorInfoProcess = new LinkedList<CollectorInfoProcess>();
			mapCollectorInfoProcess.put(name, lstCollectorInfoProcess);
		}
		else
		{
			lstCollectorInfoProcess = mapCollectorInfoProcess.get(name);
		}
		lstCollectorInfoProcess.add(process);
	}
	
	public void registerMetricsHandler(MetricsHandler metricsHandler) {
		this.metricsHandler = metricsHandler;
	}

	public static CollectorManager getInstance() {
		if (null == _instance) {
			_instance = new CollectorManager();
		}
		return _instance;
	}

	public void addMetrics(Metrics metrics) {
		metricsQueue.offer(metrics);
	}

	public void addCollectorInfo(CollectorInfo collectorInfo) {
		collectorInfoQueue.offer(collectorInfo);
	}

	public void processCollector() {

		CollectorInfo collectorInfo = null;// = collectorInfoQueue.poll();
		try {
			collectorInfo = collectorInfoQueue.poll(20, TimeUnit.MICROSECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		if (collectorInfo == null)
		{
			return;
		}
		List<CollectorInfoProcess> lstCollectorInfoProcess = mapCollectorInfoProcess.get(collectorInfo.getMetricsName());
		if (null != lstCollectorInfoProcess)
		{
			for (CollectorInfoProcess collectorInfoProcess : lstCollectorInfoProcess)
			{
				List<Metrics> lstMetrics = collectorInfoProcess.process(collectorInfo);
				if (null == lstMetrics)
				{
					continue;
				}
				for (Metrics metrics: lstMetrics)
				{
					metricsHandler.handlerMetrics(metrics);
				}
			}
		}
		else
		{
			// TODO LOG.WARN
		}
	}
}
