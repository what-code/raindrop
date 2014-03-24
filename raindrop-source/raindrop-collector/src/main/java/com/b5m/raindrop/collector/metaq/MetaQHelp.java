package com.b5m.raindrop.collector.metaq;

import com.taobao.metamorphosis.client.MessageSessionFactory;
import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.consumer.ConsumerConfig;
import com.taobao.metamorphosis.client.consumer.MessageConsumer;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.exception.MetaClientException;
import com.taobao.metamorphosis.utils.ZkUtils.ZKConfig;

public class MetaQHelp {

	public static final String TOPIC = "b5m-collector-channel";
	public static final String ZOOKEEPER_SERVER = "10.10.100.1:12181,10.10.100.61:12181,10.10.100.62:12181";

	public static MetaClientConfig initMetaConfig() {
		final MetaClientConfig metaClientConfig = new MetaClientConfig();
		final ZKConfig zkConfig = new ZKConfig();
		zkConfig.zkConnect = ZOOKEEPER_SERVER;
		metaClientConfig.setZkConfig(zkConfig);
		return metaClientConfig;
	}

	public static MessageProducer buildMessageProducer() {
		MessageSessionFactory sessionFactory = null;
		try {
			sessionFactory = new MetaMessageSessionFactory(initMetaConfig());
		} catch (MetaClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		final MessageProducer producer = sessionFactory.createProducer();
		// publish topic
		final String topic = TOPIC;
		producer.publish(topic);

		return producer;
	}

	public static MessageConsumer buildMessageConsumer(String groupName) {
		MessageSessionFactory sessionFactory = null;
		try {
			sessionFactory = new MetaMessageSessionFactory(initMetaConfig());
		} catch (MetaClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		final MessageConsumer consumer = sessionFactory
				.createConsumer(new ConsumerConfig(groupName));

		return consumer;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
