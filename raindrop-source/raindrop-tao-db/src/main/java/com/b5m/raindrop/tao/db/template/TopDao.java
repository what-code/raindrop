package com.b5m.raindrop.tao.db.template;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;

import com.b5m.raindrop.tao.db.ITopDao;
import com.b5m.raindrop.tao.db.module.GoodsBean;

class TopDao extends AbstractDao implements ITopDao {

	public static final int limit = 5;
	
	/**
	 * 查询排行榜的商品
	 */
	public static final String SQL_QUERY_TOP_GOODS = "select g.id, g.name, g.imgurl, g.sales_price,  g.total_click, g.spread, g.click_url " +
			"FROM top_goods p left join tao_goods g on p.goods_id = g.id  " +
			"where now() between p.start_time and p.end_time and p.top_type<>'0' and g.stock > 0 and " +
			"g.up_status = '1' and g.checkstatus='1' order by p.sort desc limit " + limit;
	
	/**
	 * 如果排行榜的搜索结果的数量小于 {@value #limit}，那么从这个sql语句中获取剩下的商品数据，
	 * 这条SQL语句不是完整的SQL，里面有两个占位符号，需要替换掉
	 */
	public static final String SQL_QUERY_LEFT_TOP = "select id, name, imgurl, sales_price,  total_click, spread, click_url " +
			"from tao_goods where stock > 0 and up_status <> '0' and now() between b5m_list_time and b5m_delist_time and " +
			"checkstatus='1' and categoryid is not null and id not in (%1) order by (total_click - init_click) desc limit %2";
	
	public TopDao(DataSource ds) {
		super(ds);
	}
	
	private String[] extractGoodsIds(List<GoodsBean> beans){
		String[] ids = new String[beans.size()];
		int index = 0;
		for(GoodsBean bean : beans){
			ids[index++] = bean.getId().toString();
		}
		return ids;
	}
	
	private String generateLeftTopSql(String[] goodsIds, int limit){
		return SQL_QUERY_LEFT_TOP.replaceAll("%1", StringUtils.arrayToDelimitedString(goodsIds, ","))
					.replaceAll("%2", String.valueOf(limit));
	}

	@Override
	public List<GoodsBean> queryTopGoods() throws SQLException {
		List<GoodsBean> beans = _template.query(SQL_QUERY_TOP_GOODS, new RowMapper<GoodsBean>(){
			@Override
			public GoodsBean mapRow(ResultSet rs, int rowNum)throws SQLException {
				GoodsBean bean = new GoodsBean();
				bean.setId(rs.getInt("id"));
				bean.setName(rs.getString("name"));
				bean.setImgUrl(rs.getString("imgurl"));
				bean.setSalesPrice(rs.getFloat("sales_price"));
				bean.setTotalClick(rs.getInt("total_click"));
				bean.setSpread(rs.getString("spread"));
				bean.setClickUrl(rs.getString("click_url"));
				return bean;
			}
		});
		
		if(beans.size() >= limit){
			return beans;
		}
		
		beans.addAll(_template.query(generateLeftTopSql(extractGoodsIds(beans), limit - beans.size()), 
			new RowMapper<GoodsBean>(){
				@Override
				public GoodsBean mapRow(ResultSet rs, int rowNum)throws SQLException {
					GoodsBean bean = new GoodsBean();
					bean.setId(rs.getInt("id"));
					bean.setName(rs.getString("name"));
					bean.setImgUrl(rs.getString("imgurl"));
					bean.setSalesPrice(rs.getFloat("sales_price"));
					bean.setTotalClick(rs.getInt("total_click"));
					bean.setSpread(rs.getString("spread"));
					bean.setClickUrl(rs.getString("click_url"));
					return bean;
				}
		}));
		return beans;
	}
}
