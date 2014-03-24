package com.b5m.raindrop.tao.client.metaq;

import com.taobao.metamorphosis.Message;

/**
 * 将metaq的消息的生产操作的接口开放出来，以便外部应用定制处理
 * @author jacky
 *
 * @param <T>
 */
public interface IMessageBuilder {

	public Message build(Object source);
}
