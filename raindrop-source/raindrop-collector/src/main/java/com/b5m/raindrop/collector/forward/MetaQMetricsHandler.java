package com.b5m.raindrop.collector.forward;

import com.alibaba.fastjson.JSON;
import com.b5m.raindrop.collector.MetricsHandler;
import com.b5m.raindrop.collector.metaq.MetaQHelp;
import com.b5m.raindrop.collector.metrics.Metrics;
import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.client.producer.SendResult;
import com.taobao.metamorphosis.exception.MetaClientException;

public class MetaQMetricsHandler implements MetricsHandler {

	private final MessageProducer messageProducer;

	public MetaQMetricsHandler(MessageProducer messageProducer) {
		this.messageProducer = messageProducer;
	}

	@Override
	public void handlerMetrics(Metrics metrics) {
		// TODO Auto-generated method stub
		System.out.println("handler metrics: " + metrics.toString());
		
		String jsonString = JSON.toJSONString(metrics);
		SendResult sendResult = null;
		try {
			sendResult = messageProducer.sendMessage(new Message(
					MetaQHelp.TOPIC, jsonString.getBytes()));
		} catch (MetaClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Send message exception, MetaClientException");
			return;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Send message exception, InterruptedException");
			return;
		}
		// check result
		if (!sendResult.isSuccess()) {
			System.err.println("Send message failed,error message:"
					+ sendResult.getErrorMessage());
		} else {
			System.out.println("Send message successfully,sent to "
					+ sendResult.getPartition());
		}

	}

}
