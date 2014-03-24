package com.b5m.raindrop.tao.db;

import java.sql.SQLException;
import java.util.List;

import com.b5m.raindrop.tao.db.module.GoodsBean;

/**
 * 有关Goods的调用的DAO层的接口
 * @author jacky
 *
 */
public interface IGoodsDao{

	/**
	 * 根据goods的id更新其点击数量
	 * @param goodsId
	 * @param count
	 */
	public void updateGoodsCount(String goodsId, long count) throws SQLException;
	
	/**
	 * 查询所有有效的商品，有效的商品体现在，货品数量>0，并且已经通过审核，并且当前的时间在商品的上架和
	 * 下架的时间之间,并且使用category进行排序
	 * @return
	 * @throws SQLException
	 */
	public List<GoodsBean> queryEffectiveGoods() throws SQLException;
}
