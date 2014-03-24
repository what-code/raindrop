package com.b5m.raindrop.tao.db;

import java.sql.SQLException;

/**
 * 有关Ads的调用接口
 * @author jacky
 *
 */
public interface IAdsDao {

	/**
	 * 根据ads的id更新其点击数量
	 * @param adsId
	 * @param count
	 */
	public void updateAdsCount(String adsId, long count) throws SQLException;
}
