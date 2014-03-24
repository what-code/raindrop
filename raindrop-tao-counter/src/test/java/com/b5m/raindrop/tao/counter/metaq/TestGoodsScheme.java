package com.b5m.raindrop.tao.counter.metaq;

import java.io.UnsupportedEncodingException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.b5m.raindrop.tao.counter.CounterBean;
import com.b5m.raindrop.tao.counter.CounterCategory;

public class TestGoodsScheme {

	@Test
	public void test() throws UnsupportedEncodingException {
		final String testString = "{\"id\":\"10001\", \"source\":\"tao_web\", \"category\":\"Goods\"}";
		CounterScheme scheme = new CounterScheme();
		List<Object> results = scheme.deserialize(testString.getBytes("UTF-8"));
		Assert.assertEquals(1, results.size());
		CounterBean bean = (CounterBean)results.get(0);
		Assert.assertEquals("10001", bean.getId());
		Assert.assertEquals("tao_web", bean.getSource());
		Assert.assertEquals(CounterCategory.Goods, bean.getCategory());
	}

}
