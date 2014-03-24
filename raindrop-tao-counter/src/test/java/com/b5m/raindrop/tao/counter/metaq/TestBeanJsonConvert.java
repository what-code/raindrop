package com.b5m.raindrop.tao.counter.metaq;

import java.io.IOException;

import junit.framework.Assert;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Test;

import com.b5m.raindrop.tao.counter.CounterBean;
import com.b5m.raindrop.tao.counter.CounterCategory;

public class TestBeanJsonConvert {

	@Test
	public void testToGoodsBean() throws JsonParseException, JsonMappingException, IOException {
		final String json = "{\"id\":\"100001\", \"source\":\"tao_web\", \"category\":\"Goods\"}";
		CounterBean bean = BeanJsonConvert.toCounterBean(json);
		Assert.assertEquals("100001", bean.getId());
		Assert.assertEquals("tao_web", bean.getSource());
		Assert.assertEquals(CounterCategory.Goods, bean.getCategory());
	}
	
	@Test
	public void testToJson() throws JsonGenerationException, JsonMappingException, IOException{
		final CounterBean bean = new CounterBean();
		bean.setId("100001");
		bean.setSource("tao_web");
		bean.setCategory(CounterCategory.Goods);
		String json = BeanJsonConvert.toJson(bean);
		Assert.assertEquals("{\"id\":\"100001\",\"source\":\"tao_web\",\"category\":\"Goods\"}", json);
	}

}
