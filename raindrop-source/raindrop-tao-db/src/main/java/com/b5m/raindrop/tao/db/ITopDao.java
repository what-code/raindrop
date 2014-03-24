package com.b5m.raindrop.tao.db;

import java.io.Closeable;
import java.sql.SQLException;
import java.util.List;

import com.b5m.raindrop.tao.db.module.GoodsBean;

/**
 * 查询排行榜的数据
 * @author jacky
 *
 */
public interface ITopDao{

	public List<GoodsBean> queryTopGoods() throws SQLException;
}
