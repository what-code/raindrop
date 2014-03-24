package com.b5m.raindrop.tao.client;

/**
 * 此工厂用于创建相关的service接口
 * @author jacky
 *
 */
public interface ITaoClientFactory {

	/**
	 * 创建计数的服务
	 * @return
	 */
	public ICounterService createCounterService();
}
