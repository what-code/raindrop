package com.b5m.raindrop.tao.client.metaq;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.exception.MetaClientException;

/**
 * 基于metaq的通信的服务基类，提供和metaq通信的消息生产者的服务。
 * @author jacky
 *
 */
public abstract class AbstractMetaqCompoment implements Closeable{

	protected final MessageProducer producer;
	
	protected IMessageBuilder messageBuilder;
	
	protected Integer sendTimeout;
	
	protected TimeUnit timeUnit;
	
	protected String topicName;
	
	public AbstractMetaqCompoment(MessageProducer producer){
		this.producer = producer;
	}

	public MessageProducer getProducer() {
		return producer;
	}
	
	protected abstract Message createDefaultMessage(Object bean);
	
	public Message createMessage(Object bean){
		if(null == messageBuilder)
			return createDefaultMessage(bean);
		return messageBuilder.build(bean);
	}

	public IMessageBuilder getMessageBuilder() {
		return messageBuilder;
	}

	public void setMessageBuilder(IMessageBuilder messageBuilder) {
		this.messageBuilder = messageBuilder;
	}

	public Integer getSendTimeout() {
		return sendTimeout;
	}

	public void setSendTimeout(Integer sendTimeout) {
		this.sendTimeout = sendTimeout;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	
	@Override
	public void close() throws IOException{
		try {
			producer.shutdown();
		} catch (MetaClientException e) {
			throw new IOException(e);
		}
	}
}
