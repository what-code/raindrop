package com.b5m.raindrop.tao.counter.refresh;

import java.sql.SQLException;

import com.b5m.raindrop.tao.db.IDaoFactory;
import com.b5m.raindrop.tao.db.IGoodsDao;

/**
 * 
 * @author jacky
 *
 */
public class TaoGoodsDBRefresh extends AbstractDBRefresh {
	
	private transient IGoodsDao goodsDao;
	
	private final String counterName = "goods";
	
	public TaoGoodsDBRefresh(IDaoFactory daoFactory){
		super(daoFactory);
	}
	
	public IGoodsDao getGoodsDao(){
		if(null == goodsDao)
			goodsDao = getDaoFactory().createGoodsDao();
		return goodsDao;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5706748896974290745L;

	@Override
	protected void updateCount(String key, Long count) throws SQLException {
		getGoodsDao().updateGoodsCount(key, count);
	}

	@Override
	protected String getCounterName() {
		return counterName;
	}

}
