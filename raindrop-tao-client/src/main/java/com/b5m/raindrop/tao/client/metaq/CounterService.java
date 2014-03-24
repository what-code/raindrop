package com.b5m.raindrop.tao.client.metaq;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.b5m.raindrop.tao.client.ICounterService;
import com.b5m.raindrop.tao.client.IExceptionCallback;
import com.b5m.raindrop.tao.counter.CounterBean;
import com.b5m.raindrop.tao.counter.CounterCategory;
import com.b5m.raindrop.tao.counter.metaq.BeanJsonConvert;
import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.client.producer.SendMessageCallback;
import com.taobao.metamorphosis.client.producer.SendResult;

public class CounterService extends AbstractMetaqCompoment implements
		ICounterService {

	private final Logger logger = Logger.getLogger(this.getClass());

	public CounterService(MessageProducer producer) {
		super(producer);
	}

	protected void count(CounterBean bean, final IExceptionCallback callback) {
		Message message = null;
		if (null != super.messageBuilder) {
			message = messageBuilder.build(bean);
		} else {
			message = createDefaultMessage(bean);
		}

		final SendMessageCallback metaqCallback = new SendMessageCallback() {

			@Override
			public void onMessageSent(SendResult result) {
				if (result.isSuccess()) {
					if (logger.isDebugEnabled()) {
						logger.debug(new StringBuilder(
								"sent message success, partition:")
								.append(result.getPartition())
								.append(", offset:").append(result.getOffset())
								.toString());
					}
					return;
				}

				logger.error(result.getErrorMessage());
				callback.onException(new RuntimeException(result
						.getErrorMessage()));
			}

			@Override
			public void onException(Throwable e) {
				logger.error(e.getMessage(), e);
				callback.onException(e);
			}
		};

		if (sendTimeout != null) {
			producer.sendMessage(message, metaqCallback, sendTimeout,
					timeUnit == null ? TimeUnit.SECONDS : timeUnit);
			return;
		}

		producer.sendMessage(message, metaqCallback);
	}

	@Override
	public void countGoods(String goodsId, int count,
			IExceptionCallback callback) {
		// TODO GoodsBean的模型中应该要封装一个count计数属性
		CounterBean bean = new CounterBean();
		bean.setId(goodsId);
		bean.setSource(TaoClientMetaqFactory.DEFAULT_SOURCE);
		bean.setCategory(CounterCategory.Goods);
		count(bean, callback);
	}

	@Override
	public void clickGoods(String goodsId, IExceptionCallback callback) {
		countGoods(goodsId, 1, callback);
	}

	@Override
	public void countAds(String adsId, int count, IExceptionCallback callback) {
		// TODO GoodsBean的模型中应该要封装一个count计数属性
		CounterBean bean = new CounterBean();
		bean.setId(adsId);
		bean.setSource(TaoClientMetaqFactory.DEFAULT_SOURCE);
		bean.setCategory(CounterCategory.Ads);
		count(bean, callback);
	}
	
	@Override
	public void clickAds(String adsId, IExceptionCallback callback) {
		countAds(adsId, 1, callback);
	}

	@Override
	protected Message createDefaultMessage(Object bean) {
		try {
			return new Message(topicName, BeanJsonConvert.toJson(
					(CounterBean) bean).getBytes("UTF-8"));
		} catch (JsonGenerationException e) {
			logger.error(e.getMessage(), e);
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}
