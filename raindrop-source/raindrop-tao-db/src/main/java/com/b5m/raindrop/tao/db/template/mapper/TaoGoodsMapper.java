package com.b5m.raindrop.tao.db.template.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.b5m.raindrop.tao.db.module.GoodsBean;

public class TaoGoodsMapper implements RowMapper<GoodsBean> {

	@Override
	public GoodsBean mapRow(ResultSet rs, int rowNum) throws SQLException {
		GoodsBean bean = new GoodsBean();
		bean.setId(rs.getInt("id"));
		bean.setSourceUrl(rs.getString("source_url"));
		bean.setSourceItem(rs.getString("source_item"));
		bean.setName(rs.getString("name"));
		bean.setCategoryId(rs.getInt("categoryid"));
		bean.setImgUrls(rs.getString("imgurls"));
		bean.setImgUrl(rs.getString("imgurl"));
		bean.setSourcePrice(rs.getFloat("source_price"));
		bean.setSalesPrice(rs.getFloat("sales_price"));
		bean.setDiscount(rs.getFloat("discount"));
		bean.setPostal(rs.getInt("postal"));
		bean.setExpressPrice(rs.getFloat("express_price"));
		bean.setVolume(rs.getInt("volume"));
		bean.setStock(rs.getInt("stock"));
		bean.setTotalClick(rs.getInt("total_click"));
		bean.setSpread(rs.getString("spread"));
		bean.setUserId(rs.getInt("userid"));
		bean.setCheckStatus(rs.getInt("checkstatus"));
		bean.setSource(rs.getString("source"));
		return bean;
	}

}
