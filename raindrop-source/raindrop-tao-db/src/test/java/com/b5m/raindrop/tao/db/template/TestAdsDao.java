package com.b5m.raindrop.tao.db.template;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

public class TestAdsDao {
	private final JdbcConfig config;

	public TestAdsDao(){
		org.apache.log4j.BasicConfigurator.configure();
		config = new JdbcConfig();
		config.setDriverClass("com.mysql.jdbc.Driver");
		config.setUrl("jdbc:mysql://10.10.99.207:3306/b5m_tao?autoReconnect=true&useUnicode=true&characterEncoding=UTF8&mysqlEncoding=utf8&zeroDateTimeBehavior=convertToNull");
		config.setUser("b5m");
		config.setPassword("izene123");
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
	public void testUpdateAdsCount() throws SQLException {
		TemplateDaoFactory factory = new TemplateDaoFactory(config);
		AdsDao adsDao = (AdsDao)factory.createAdsDao();
		final String adsId = "14";
		final int click = 1;
		final String queryTotalClick = "select total_click from ad_spaces where id=" + adsId;
		final String queryTodayClick = "select click_number from ad_click where ad_id=" + adsId + " and predict='" + getNowDateString() + "'";
		int expectedTotalClick = getClickCount(adsDao.getJdbcTemplate(), queryTotalClick) + click;
		
		int expectedNowClick = getClickCount(adsDao.getJdbcTemplate(), queryTodayClick) + click;
		
		adsDao.updateAdsCount(adsId, click);

		Assert.assertEquals(expectedTotalClick, getClickCount(adsDao.getJdbcTemplate(), queryTotalClick));
		Assert.assertEquals(expectedNowClick, getClickCount(adsDao.getJdbcTemplate(), queryTodayClick));
	}

}
