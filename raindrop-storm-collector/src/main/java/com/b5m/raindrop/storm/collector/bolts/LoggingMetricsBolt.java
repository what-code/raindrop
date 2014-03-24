package com.b5m.raindrop.storm.collector.bolts;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Appender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

import com.b5m.raindrop.collector.metrics.Metrics;
import com.b5m.raindrop.storm.collector.utils.MetricsHelp;

public class LoggingMetricsBolt extends BaseRichBolt {
	private OutputCollector collector;
	
	private Map<String, Metrics> counter = new HashMap<String, Metrics>();
	
	private Logger logger =  null; 
	
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
		
		this.logger =  Logger.getLogger("com.b5m.collector"); 
		
		PatternLayout layout = new PatternLayout("%m%n");
		Appender appender;
		try {
			appender = new DailyRollingFileAppender(layout, "/home/storm/collector.log", "'.'yyyy-MM-dd");
			this.logger.addAppender(appender);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		Metrics metrics = (Metrics)input.getValue(0);

		String line = MetricsHelp.buildString(metrics);
		logger.info(line);
		collector.ack(input);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub

	}
}
