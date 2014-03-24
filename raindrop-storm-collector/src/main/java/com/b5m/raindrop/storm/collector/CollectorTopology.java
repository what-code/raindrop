package com.b5m.raindrop.storm.collector;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.Scheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

import com.b5m.raindrop.collector.metaq.MetaQHelp;
import com.b5m.raindrop.source.metaq.MetaSpout;
import com.b5m.raindrop.storm.collector.bolts.EmailBolt;
import com.b5m.raindrop.storm.collector.bolts.LoggingMetricsBolt;
import com.b5m.raindrop.storm.collector.bolts.MetricsBolt;
import com.b5m.raindrop.storm.collector.bolts.StatMetricsBolt;
import com.taobao.metamorphosis.client.consumer.ConsumerConfig;

public class CollectorTopology {

	private static void startSubmitMode(String topologyName) throws AlreadyAliveException, InvalidTopologyException {

		TopologyBuilder builder = new TopologyBuilder();

		MetaSpout metaSpout = createMetaSpout();
		builder.setSpout("Metrics-Spout", metaSpout, 2);
		
		Scheme scheme = new MetricsScheme();
		builder.setBolt("Metrics-Stat", new StatMetricsBolt(), 10).fieldsGrouping("Metrics-Spout", scheme.getOutputFields());
		builder.setBolt("Metrics-Logging", new LoggingMetricsBolt()).shuffleGrouping("Metrics-Spout");
		builder.setBolt("Mail-Alarm", new EmailBolt(),10).shuffleGrouping("Metrics-Stat");
		
		Config conf = new Config();
		conf.put("meta.topic", MetaQHelp.TOPIC);
		conf.setDebug(true);
		conf.setNumWorkers(5);
		
//		LocalCluster cluster = new LocalCluster();
//		cluster.submitTopology(topologyName, conf,
//				builder.createTopology());
//		
//		try {
//			TimeUnit.SECONDS.sleep(60);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		cluster.shutdown();
		StormSubmitter.submitTopology(topologyName, conf,
				builder.createTopology());
	}

	private static MetaSpout createMetaSpout() {
		ConsumerConfig consumerConfig = new ConsumerConfig(MetaQHelp.TOPIC);
		MetaSpout spout = new MetaSpout(MetaQHelp.initMetaConfig(),
				consumerConfig, new MetricsScheme());
		return spout;
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws InvalidTopologyException
	 * @throws AlreadyAliveException
	 */
	public static void main(String[] args)  {

		Logger.getLogger(CollectorTopology.class).info("test");
		
		try {
			startSubmitMode(args[0]);
		} catch (AlreadyAliveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidTopologyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
