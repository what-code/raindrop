package com.b5m.raindrop.tao.db.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.PreparedStatementCreator;

import com.b5m.raindrop.tao.db.IGoodsDao;
import com.b5m.raindrop.tao.db.module.GoodsBean;
import com.b5m.raindrop.tao.db.template.mapper.TaoGoodsMapper;

class GoodsDao extends AbstractDao implements IGoodsDao {

	/**
	 * 修改总点击量的表
	 */
	public static final String SQL_UPDATE_TOTAL_COUNT = "update tao_goods set total_click=total_click+? where id=?";
	
	/**
	 * 修改当天的点击量的表，4个占位符分别代表：goodsid, 点击量，时间，点击量
	 */
	public static final String SQL_UPDATE_TODAY_COUNT = "insert into today_click values(?, ?, ?) on duplicate key update click_number=click_number + ?";
	
	/**
	 * 查询所有在有效期内的商品数据,并且库存>0，而且被审核的数据
	 */
	public static final String SQL_QUERY_EFFECTIVE_GOODS = "select id, source_url, source_item, name, categoryid, imgurls, imgurl, source_price" +
			", sales_price, discount, postal, express_price, volume, stock, total_click, spread, userid, checkstatus, source" +
			" from tao_goods where (now() between b5m_list_time and b5m_delist_time)" +
			"and stock > 0 " +
			"and up_status = '1' ";
	
	/**
	 * 查询有效的商品信息
	 */
	/*public static final String SQL_QUERY_EFFECTIVE_GOODS = "select * from tao_goods where categoryid is not null " +
			" and up_status = '1' " +
			" and stock > 0  " +
			" and (postal=1 or source!='taobao') " +
			" and checkstatus='1' " +
			" and (now() between b5m_list_time and b5m_delist_time)  order by categoryid,total_click desc";*/
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	//private final DataSourceTransactionManager txManager;
	
	public GoodsDao(DataSource ds){
		super(ds);
		
	}
	
	@Override
	public void updateGoodsCount(String goodsId, long count) throws SQLException{
		final String __goodsId = goodsId;
		final int __count = (int)count;
		final java.sql.Date __now = new java.sql.Date(getNowDate().getTime());

		// 更新总的统计量
		int rowsAffected = _template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement pstat = con.prepareStatement(SQL_UPDATE_TOTAL_COUNT);
				pstat.setInt(1, __count);
				pstat.setString(2, __goodsId);
				return pstat;
			}
		});
		
		if(logger.isDebugEnabled()){
			logger.debug(new StringBuilder("updated table(tao_goods).goodsId(").append(__goodsId)
						.append(") for click count(").append(__count).append(") success. Affected rows: ")
						.append(rowsAffected).toString());
		}
		
		// 更新当天的统计量
		rowsAffected = _template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement pstat = con.prepareStatement(SQL_UPDATE_TODAY_COUNT);
				pstat.setString(1, __goodsId);
				pstat.setInt(2, __count);
				pstat.setDate(3, __now);
				pstat.setInt(4, __count);
				return pstat;
			}
		});
		
		if(logger.isDebugEnabled()){
			logger.debug(new StringBuilder("updated table(today_click).goodsId(").append(__goodsId)
						.append(") and date(").append(__now).append(") for click count(")
						.append(__count).append(") success. Affected rows: ")
						.append(rowsAffected).toString());
		}
	}

	@Override
	public List<GoodsBean> queryEffectiveGoods()
			throws SQLException {
		return _template.query(SQL_QUERY_EFFECTIVE_GOODS, new TaoGoodsMapper());
	}

	
}
