package com.b5m.raindrop.storm.collector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.b5m.raindrop.storm.collector.mail.MailSenderInfo;
import com.b5m.raindrop.storm.collector.mail.SimpleMailSender;

public class testEmailSender {

	@Before
	public void setUp() {
		
	}
	
	@After
	public void tearDown()
	{
		
	}
	
	
	@Test
	public void testSender()
	{
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.gmail.com");
		mailInfo.setMailServerPort("465");
		mailInfo.setValidate(true);
		mailInfo.setUserName("noreply@b5m.com");
		mailInfo.setPassword("izene123");// 您的邮箱密码
		mailInfo.setFromAddress("noreply@b5m.com");
		mailInfo.setToAddress("libin.jin@b5m.com");
		
		String name = "test";
		String value = "value";
		
		String content = "test test test\n test test test\r\n ????? .... ";
		
		mailInfo.setSubject("WARN-" + name + "-" + value);
		mailInfo.setContent(content);
		// 这个类主要来发送邮件

		SimpleMailSender.sendTextMail(mailInfo);// 发送文体格式
		SimpleMailSender.sendHtmlMail(mailInfo);// 发送html格式
		
	}
}
