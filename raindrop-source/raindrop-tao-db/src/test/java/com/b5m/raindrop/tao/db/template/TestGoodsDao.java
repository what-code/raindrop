package com.b5m.raindrop.tao.db.template;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.b5m.raindrop.tao.db.module.GoodsBean;

public class TestGoodsDao {
	private final JdbcConfig config;
	private final GoodsDao goodsDao;

	public TestGoodsDao(){
		org.apache.log4j.BasicConfigurator.configure();
		config = new JdbcConfig();
		config.setDriverClass("com.mysql.jdbc.Driver");
		config.setUrl("jdbc:mysql://10.10.99.207:3306/b5m_tao?autoReconnect=true&useUnicode=true&characterEncoding=UTF8&mysqlEncoding=utf8&zeroDateTimeBehavior=convertToNull");
		config.setUser("b5m");
		config.setPassword("izene123");
		TemplateDaoFactory factory = new TemplateDaoFactory(config);
		goodsDao = (GoodsDao)factory.createGoodsDao();
	}	
	
	private int getClickCount(JdbcTemplate template, String sql){
		return template.query(sql, new ResultSetExtractor<Integer>(){
			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				if(!rs.next())
					return 0;
				return rs.getInt(1);
			}
		});
	}
	
	private String getNowDateString(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00.0");
		return format.format(new Date());
	}
	
	@Test
	public void testUpdateGoodsCount() throws SQLException {
		final String goodsId = "62465";
		final int click = 1;
		final String queryTotalClick = "select total_click from tao_goods where id=" + goodsId;
		final String queryTodayClick = "select click_number from today_click where goods_id=" + goodsId + " and predict='" + getNowDateString() + "'";
		int expectedTotalClick = getClickCount(goodsDao.getJdbcTemplate(), queryTotalClick) + click;
		
		int expectedNowClick = getClickCount(goodsDao.getJdbcTemplate(), queryTodayClick) + click;
		
		goodsDao.updateGoodsCount(goodsId, click);

		Assert.assertEquals(expectedTotalClick, getClickCount(goodsDao.getJdbcTemplate(), queryTotalClick));
		Assert.assertEquals(expectedNowClick, getClickCount(goodsDao.getJdbcTemplate(), queryTodayClick));
	}

	@Test
	public void testQueryEffectiveGoods() throws SQLException{
		List<GoodsBean> beans = goodsDao.queryEffectiveGoods();
		Assert.assertEquals(true, beans.size() > 0);
	}
}
