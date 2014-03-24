package com.b5m.raindrop.tao.counter.refresh;

import java.sql.SQLException;

import com.b5m.raindrop.tao.db.IAdsDao;
import com.b5m.raindrop.tao.db.IDaoFactory;

public class TaoAdsDBRefresh extends AbstractDBRefresh {
	
	private transient IAdsDao adsDao;
	
	private final String counterName = "ads";

	public TaoAdsDBRefresh(IDaoFactory daoFactory) {
		super(daoFactory);
	}
	
	public IAdsDao getAdsDao(){
		if(null == adsDao)
			adsDao = getDaoFactory().createAdsDao();
		return adsDao;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3321388074705352298L;

	@Override
	protected void updateCount(String key, Long count) throws SQLException {
		getAdsDao().updateAdsCount(key, count);
	}

	@Override
	protected String getCounterName() {
		return counterName;
	}

}
