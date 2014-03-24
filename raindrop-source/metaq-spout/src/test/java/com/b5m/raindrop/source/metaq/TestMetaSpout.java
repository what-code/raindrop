package com.b5m.raindrop.source.metaq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.Assert;

import org.apache.log4j.Logger;
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
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.utils.Utils;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.MessageSessionFactory;
import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.consumer.ConsumerConfig;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.client.producer.SendMessageCallback;
import com.taobao.metamorphosis.client.producer.SendResult;
import com.taobao.metamorphosis.exception.MetaClientException;
import com.taobao.metamorphosis.utils.ZkUtils.ZKConfig;

public class TestMetaSpout {
	public static final String TOPIC_NAME = "spout-test";
	private final static Logger logger = Logger.getLogger(TestMetaSpout.class);
	
	private StormTopology topology;
	private LocalCluster cluster;
	private MetaSpout metaSpout;
	
	private static AtomicInteger counter;
	
	private int sentCount;
	
	public TestMetaSpout(){
		org.apache.log4j.PropertyConfigurator.configure(this.getClass().getResource("/com/b5m/raindrop/source/metaq/log4j.properties"));
	}
	
	@Before
	public void setUp(){
		topology = createStormTopology();
		cluster = new LocalCluster();
		counter = new AtomicInteger(0);
	}
	
	public static class TestBolt extends BaseRichBolt
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = -6324539823655459857L;
        OutputCollector _collector;

		@SuppressWarnings("rawtypes")
		@Override
		public void prepare(Map stormConf, TopologyContext context,
				OutputCollector collector) {
			_collector = collector;
		}

		@Override
		public void execute(Tuple input) {
			logger.info(new StringBuilder("TestBolt >> ").append(input.getString(0)).toString());
			counter.incrementAndGet();
			_collector.ack(input);
		}

		@Override
		public void declareOutputFields(OutputFieldsDeclarer declarer) {
			
		}
		
	}
	
	private MetaSpout createMetaSpout(){		
		ConsumerConfig consumerConfig = new ConsumerConfig("meta-test");
		MetaSpout spout = new MetaSpout(initMetaConfig(), consumerConfig, new StringScheme());
		return spout;
	}
	
	private StormTopology createStormTopology(){
		TopologyBuilder builder = new TopologyBuilder();
		metaSpout = createMetaSpout();
		builder.setSpout("goodsid", metaSpout, 2); 
		builder.setBolt("TestBolt", new TestBolt(), 2).fieldsGrouping("goodsid", new Fields("str"));
		return builder.createTopology();
	}
	
	private MetaClientConfig initMetaConfig()
	{
		MetaClientConfig metaClientConfig = new MetaClientConfig();
		//metaClientConfig.setServerUrl("localhost:2181");
		ZKConfig zkConfig = new ZKConfig();
		zkConfig.zkConnect = "localhost:2181";
		metaClientConfig.setZkConfig(zkConfig);
		return metaClientConfig;
	}
	
	private StringReader createStringReader(){
		StringBuilder sb = new StringBuilder();
		sb.append("100001\r\n");
		sb.append("100002\r\n");
		sb.append("100003\r\n");
		sb.append("100004\r\n");
		sb.append("100005\r\n");
		sb.append("100006\r\n");
		sb.append("100007\r\n");
		sb.append("100008\r\n");
		sb.append("100009\r\n");
		sb.append("100010\r\n");
		return new StringReader(sb.toString());
	}
	
	private void producerMock() throws MetaClientException
	{
		final MessageSessionFactory sessionFactory = new MetaMessageSessionFactory(initMetaConfig());

        final MessageProducer producer = sessionFactory.createProducer();

        producer.publish(TOPIC_NAME);

        final BufferedReader reader = new BufferedReader(createStringReader());
        String line = null;
        try {
			while ((line = reader.readLine()) != null) {
			    // send message
			    try {
			        producer.sendMessage(new Message(TOPIC_NAME, line.getBytes()), new SendMessageCallback() {

			            @Override
			            public void onMessageSent(final SendResult result) {
			                if (result.isSuccess()) {
			                    System.out.println("Send message successfully,sent to " + result.getPartition());
			                    sentCount++;
			                }
			                else {
			                    System.err.println("Send message failed,error message:" + result.getErrorMessage());
			                }

			            }


			            @Override
			            public void onException(final Throwable e) {
			                e.printStackTrace();

			            }
			        });

			    }
			    catch (final Exception e) {
			        e.printStackTrace();
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSpout() throws MetaClientException {
		Config conf = new Config();
		conf.put(MetaSpout.TOPIC, TOPIC_NAME);
        conf.setDebug(true);
		
        cluster.submitTopology("test", conf, topology);
        Utils.sleep(5000);
		// 发送消息到MetaQ服务中
		//producerMock();
        Utils.sleep(6000);
        Assert.assertEquals(sentCount, counter.get());
	}
	
	@After
	public void tearDown(){
        cluster.killTopology("test");
        cluster.shutdown();  
	}

}
