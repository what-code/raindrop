package com.b5m.raindrop.storm.collector.bolts;

import java.util.Map;

import com.b5m.raindrop.collector.metrics.Metrics;
import com.b5m.raindrop.storm.collector.mail.MailSenderInfo;
import com.b5m.raindrop.storm.collector.mail.SimpleMailSender;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

public class EmailBolt extends BaseRichBolt {

	private MailSenderInfo mailInfo;

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.gmail.com");
		mailInfo.setMailServerPort("465");
		mailInfo.setValidate(true);
		mailInfo.setUserName("noreply@b5m.com");
		mailInfo.setPassword("izene123");// 您的邮箱密码
		mailInfo.setFromAddress("noreply@b5m.com");
		mailInfo.setToAddress("libin.jin@b5m.com");

		this.mailInfo = mailInfo;
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub

		String name = input.getString(0);
		long value = input.getLong(1);
		long timestamp = input.getLong(2);
		String content = input.getString(3);

		mailInfo.setSubject("WARN-" + name + "-" + value);
		mailInfo.setContent(content);
		// 这个类主要来发送邮件
		SimpleMailSender.sendTextMail(mailInfo);// 发送文体格式
		// SimpleMailSender.sendHtmlMail(mailInfo);// 发送html格式
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub

	}

}
