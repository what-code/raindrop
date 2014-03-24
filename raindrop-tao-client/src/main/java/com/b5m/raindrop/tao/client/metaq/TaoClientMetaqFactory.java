package com.b5m.raindrop.tao.client.metaq;

import java.util.concurrent.TimeUnit;

import com.b5m.raindrop.tao.client.ICounterService;
import com.b5m.raindrop.tao.client.ITaoClientFactory;
import com.taobao.metamorphosis.client.MessageSessionFactory;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.exception.MetaClientException;

public class TaoClientMetaqFactory implements ITaoClientFactory {
	
	public static final String DEFAULT_TOPIC_NAME = "spout-test"; 
	
	public static final String DEFAULT_SOURCE = "taoweb";
	
	private final FactoryConfig config;
	
	private MessageSessionFactory messageSessionFactory;

	public TaoClientMetaqFactory(FactoryConfig config) throws MetaClientException{
		this.config = config;
		messageSessionFactory = createMessageSessionFactory();
	}
	
	private MessageSessionFactory createMessageSessionFactory() throws MetaClientException{
		return new MetaMessageSessionFactory(config.getMetaClientConfig());
	}
	
	@Override
	public ICounterService createCounterService() {
		MessageProducer producer = messageSessionFactory.createProducer();
		producer.publish(config.getTopicName());
		CounterService service = new CounterService(producer);
		service.setMessageBuilder(config.getMessageBuilder());
		if(config.getSendTimeout() != null){
			service.setSendTimeout(config.getSendTimeout());
			service.setTimeUnit(config.getTimeUnit()==null?TimeUnit.SECONDS:config.getTimeUnit());
		}
		service.setTopicName(config.getTopicName());
		return service;
	}

}
