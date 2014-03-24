package com.b5m.raindrop.tao.counter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.utils.Utils;

import com.b5m.raindrop.source.metaq.MetaSpout;
import com.b5m.raindrop.source.signal.SignalSpout;
import com.b5m.raindrop.tao.counter.metaq.CounterScheme;
import com.b5m.raindrop.tao.db.template.JdbcConfig;
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

public class TestCounter {
	public static final String TOPIC_NAME = "spout-test";
	
	private StormTopology topology;
	private LocalCluster cluster;
	private MetaSpout metaSpout;
	
	public TestCounter(){
		org.apache.log4j.PropertyConfigurator.configure(this.getClass().getResource("/com/b5m/raindrop/tao/counter/log4j.properties"));
	}
	
	static class TestRefreshHandler implements IRefreshHandler{

		/**
		 * 
		 */
		private static final long serialVersionUID = -3784873887505749641L;

		@Override
		public void refresh(Map<String, Long> counter, Tuple input) {
			String str = input.getStringByField("action");
			if("refreshCache".equals(str)){
				System.out.println("received flash");
			}
		}
	}
	
	@Before
	public void setUp(){
		topology = createStormTopology();
		cluster = new LocalCluster();
	}
	
	private MetaSpout createMetaSpout(){		
		ConsumerConfig consumerConfig = new ConsumerConfig("meta-test");
		MetaSpout spout = new MetaSpout(initMetaConfig(), consumerConfig, new CounterScheme());
		return spout;
	}
	
	private StormTopology createStormTopology(){
		TopologyBuilder builder = new TopologyBuilder();
		metaSpout = createMetaSpout();
		builder.setSpout("goodsid", metaSpout, 2); 
		builder.setSpout("signal", new SignalSpout(), 1);
		
		builder.setBolt("counter", new CounterBolt(new JdbcConfig()), 2)
			.fieldsGrouping("goodsid", new Fields("goodsid"))
			.allGrouping("signal", "signals");
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
		/*sb.append("{\"id\":\"100001\", \"source\":\"tao_web\", \"category\":\"Goods\"}\r\n");
		sb.append("{\"id\":\"100002\", \"source\":\"tao_web\", \"category\":\"Goods\"}\r\n");
		sb.append("{\"id\":\"100003\", \"source\":\"tao_web\", \"category\":\"Goods\"}\r\n");
		sb.append("{\"id\":\"100004\", \"source\":\"tao_web\", \"category\":\"Goods\"}\r\n");
		sb.append("{\"id\":\"100005\", \"source\":\"tao_web\", \"category\":\"Goods\"}\r\n");
		sb.append("{\"id\":\"100006\", \"source\":\"tao_web\", \"category\":\"Goods\"}\r\n");
		sb.append("{\"id\":\"100007\", \"source\":\"tao_web\", \"category\":\"Goods\"}\r\n");
		sb.append("{\"id\":\"100008\", \"source\":\"tao_web\", \"category\":\"Goods\"}\r\n");
		sb.append("{\"id\":\"100009\", \"source\":\"tao_web\", \"category\":\"Goods\"}\r\n");
		sb.append("{\"id\":\"100010\", \"source\":\"tao_web\", \"category\":\"Goods\"}\r\n");*/
		sb.append("{\"goodsId\":\"100001\", \"source\":\"tao_web\"}\r\n");
		sb.append("{\"goodsId\":\"100002\", \"source\":\"tao_web\"}\r\n");
		sb.append("{\"goodsId\":\"100003\", \"source\":\"tao_web\"}\r\n");
		sb.append("{\"goodsId\":\"100004\", \"source\":\"tao_web\"}\r\n");
		sb.append("{\"goodsId\":\"100005\", \"source\":\"tao_web\"}\r\n");
		sb.append("{\"goodsId\":\"100006\", \"source\":\"tao_web\"}\r\n");
		sb.append("{\"goodsId\":\"100007\", \"source\":\"tao_web\"}\r\n");
		sb.append("{\"goodsId\":\"100008\", \"source\":\"tao_web\"}\r\n");
		sb.append("{\"goodsId\":\"100009\", \"source\":\"tao_web\"}\r\n");
		sb.append("{\"goodsId\":\"100010\", \"source\":\"tao_web\"}\r\n");
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
	public void testSent() throws MetaClientException{
		producerMock();
	}
	
	@Test
	public void testSpout() throws MetaClientException {
		Config conf = new Config();
		conf.put(MetaSpout.TOPIC, TOPIC_NAME);
		final long interval = 2000;
		conf.put(SignalSpout.CONF_INTERVAL_ID, String.valueOf(interval));
        conf.setDebug(true);
		
        cluster.submitTopology("test", conf, topology);
        Utils.sleep(3000);
		// 发送消息到MetaQ服务中
		//producerMock();
        Utils.sleep(100000);
	}
	
	@After
	public void tearDown(){
        cluster.killTopology("test");
        cluster.shutdown();  
	}
}
