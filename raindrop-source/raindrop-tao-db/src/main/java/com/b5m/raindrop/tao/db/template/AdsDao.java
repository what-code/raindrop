package com.b5m.raindrop.tao.db.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.b5m.raindrop.tao.db.IAdsDao;

public class AdsDao extends AbstractDao implements IAdsDao {

	/**
	 * 修改总点击量的表
	 */
	public static final String SQL_UPDATE_TOTAL_COUNT = "update ad_spaces set total_click=total_click+? where id=?";
	
	/**
	 * 修改当天的点击量的表，4个占位符分别代表：adsid, 点击量，时间，点击量
	 */
	public static final String SQL_UPDATE_TODAY_COUNT = "insert into ad_click values(?, ?, ?) on duplicate key update click_number=click_number + ?";

	private final Logger logger = Logger.getLogger(this.getClass());
	
	public AdsDao(DataSource ds) {
		super(ds);
	}

	@Override
	public void updateAdsCount(String adsId, long count) throws SQLException {
		final String __adsId = adsId;
		final int __count = (int)count;
		final java.sql.Date __now = new java.sql.Date(getNowDate().getTime());

		// 更新总的统计量
		int rowsAffected = _template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement pstat = con.prepareStatement(SQL_UPDATE_TOTAL_COUNT);
				pstat.setInt(1, __count);
				pstat.setString(2, __adsId);
				return pstat;
			}
		});
		
		if(logger.isDebugEnabled()){
			logger.debug(new StringBuilder("updated table(ad_space).adsId(").append(__adsId)
						.append(") for click count(").append(__count).append(") success. Affected rows: ")
						.append(rowsAffected).toString());
		}
		
		// 更新当天的统计量
		rowsAffected = _template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement pstat = con.prepareStatement(SQL_UPDATE_TODAY_COUNT);
				pstat.setString(1, __adsId);
				pstat.setInt(2, __count);
				pstat.setDate(3, __now);
				pstat.setInt(4, __count);
				return pstat;
			}
		});
		
		if(logger.isDebugEnabled()){
			logger.debug(new StringBuilder("updated table(ad_click).adsId(").append(__adsId)
						.append(") and date(").append(__now).append(") for click count(")
						.append(__count).append(") success. Affected rows: ")
						.append(rowsAffected).toString());
		}
	}

}
