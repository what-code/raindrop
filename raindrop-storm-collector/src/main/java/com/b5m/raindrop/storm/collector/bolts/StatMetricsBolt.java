package com.b5m.raindrop.storm.collector.bolts;

import java.util.Map;

import com.b5m.raindrop.collector.metrics.Metrics;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class StatMetricsBolt extends BaseRichBolt {

	private OutputCollector collector;
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		Metrics metrics = (Metrics)input.getValue(0);
		
		if (metrics.getValue() > 90)
		{
			collector.emit(new Values(metrics.getName(), metrics.getValue(), metrics.getTimestamp(), metrics.toString()));
		}
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("name", "value", "timestamp", "content"));
	}

}
