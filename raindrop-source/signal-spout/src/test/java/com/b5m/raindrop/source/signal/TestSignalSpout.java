package com.b5m.raindrop.source.signal;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import backtype.storm.utils.Utils;

public class TestSignalSpout {
	private LocalCluster cluster;
	private StormTopology topology;
	
	static class TestBolt extends BaseRichBolt{
		/**
		 * 
		 */
		private static final long serialVersionUID = -1L;
		OutputCollector _collector;
		@SuppressWarnings("rawtypes")
		@Override
		public void prepare(Map stormConf, TopologyContext context,
				OutputCollector collector) {
			this._collector = collector;
		}

		@Override
		public void execute(Tuple input) {
			if(!"signals".equals(input.getSourceStreamId())){
				return ;
			}

			String str = input.getStringByField("action");
			if("refreshCache".equals(str)){
				System.out.println("received flash");
			}
			
			_collector.ack(input);
		}

		@Override
		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			
		}
	}
	
	private StormTopology createStormTopoloy(){
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("signal-spout", new SignalSpout(), 1);
		builder.setBolt("signal-bolt", new TestBolt(), 1).allGrouping("signal-spout", "signals");
		
		return builder.createTopology();
	}
	
	@Before
	public void setUp(){
		topology = createStormTopoloy();
		cluster = new LocalCluster();		
	}
	
	@Test
	public void test() {
		Config conf = new Config();
		final long interval = 2000;
		conf.put(SignalSpout.CONF_INTERVAL_ID, String.valueOf(interval));
        conf.setDebug(true);
        
        cluster.submitTopology("test", conf, topology);
        Utils.sleep(10000);
	}
	
	@After
	public void tearDown(){
		cluster.shutdown();
	}

}
