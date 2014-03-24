package com.b5m.raindrop.tao.client.metaq;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import com.taobao.metamorphosis.client.MetaClientConfig;
import com.taobao.metamorphosis.utils.ZkUtils.ZKConfig;

public class FactoryConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 530015467147869031L;

	private MetaClientConfig metaClientConfig;
	
	private String topicName;
	
	private Integer sendTimeout;
	
	private TimeUnit timeUnit;
	
	private IMessageBuilder messageBuilder;
	
	private final ZKConfig zkConfig;
	
	public FactoryConfig(String metaqZkUri){
		zkConfig = new ZKConfig();
		zkConfig.zkConnect = metaqZkUri;
	}

	public MetaClientConfig getMetaClientConfig() {
		if(null == metaClientConfig && null != zkConfig){
			metaClientConfig = new MetaClientConfig();
			metaClientConfig.setZkConfig(zkConfig);
		}
		return metaClientConfig;
	}

	public FactoryConfig setMetaClientConfig(MetaClientConfig metaClientConfig) {
		this.metaClientConfig = metaClientConfig;
		if(this.metaClientConfig.getZkConfig() == null){
			this.metaClientConfig.setZkConfig(zkConfig);
		}
		return this;
	}

	public String getTopicName() {
		return topicName;
	}

	public FactoryConfig setTopicName(String topicName) {
		this.topicName = topicName;
		return this;
	}

	public Integer getSendTimeout() {
		return sendTimeout;
	}

	public FactoryConfig setSendTimeout(Integer sendTimeout) {
		this.sendTimeout = sendTimeout;
		return this;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public FactoryConfig setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
		return this;
	}
	
	public IMessageBuilder getMessageBuilder() {
		return messageBuilder;
	}

	public FactoryConfig setMessageBuilder(IMessageBuilder messageBuilder) {
		this.messageBuilder = messageBuilder;
		return this;
	}

	public static FactoryConfig simpleBuild(String metaqZkUri, String topicName){
		return new FactoryConfig(metaqZkUri).setTopicName(topicName);
	}
}
