package com.b5m.raindrop.tao.counter.refresh;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import backtype.storm.tuple.Tuple;

import com.b5m.raindrop.tao.counter.IRefreshHandler;
import com.b5m.raindrop.tao.db.IDaoFactory;

public abstract class AbstractDBRefresh implements IRefreshHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -144858825164247594L;

	protected transient IDaoFactory daoFactory;

	protected transient Logger logger;

	public AbstractDBRefresh(IDaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public IDaoFactory getDaoFactory() {
		return daoFactory;
	}

	protected Logger getLogger() {
		if (null == logger)
			logger = Logger.getLogger(this.getClass());
		return logger;
	}

	@Override
	public void refresh(Map<String, Long> counter, Tuple input) {
		// 如果dao更新某个商品的id的统计量成功，那么将这个商品的id添加到clearList中
		List<String> clearList = new ArrayList<String>(counter.size());
		Set<String> keySet = counter.keySet();
		if (getLogger().isInfoEnabled()) {
			getLogger().info(
					new StringBuilder("refreshed >> \n").append(counter)
							.toString());
		}
		for (String key : keySet) {
			try {
				Long count = counter.get(key);
				if (count == 0)
					continue;

				updateCount(key, count);
				clearList.add(key);
				if (getLogger().isDebugEnabled()) {
					getLogger().debug(
							new StringBuilder("refreshed ").append(getCounterName()).append("(")
									.append(key).append(") for count(")
									.append(count)
									.append(") into database success")
									.toString());
				}
			} catch (SQLException e) {
				getLogger().error(e.getMessage(), e);
			}
		}

		// 清零操作
		for (String clearKey : clearList) {
			// counter.put(clearKey, 0L);
			counter.remove(clearKey);
		}
	}
	
	/**
	 * 操作dao接口，将计数的数值保存到数据库中
	 * @param key
	 * @param count
	 * @throws SQLException
	 */
	protected abstract void updateCount(String key, Long count) throws SQLException;

	/**
	 * 返回计数器的名称，例如goods, ads等等
	 * @return
	 */
	protected abstract String getCounterName();
}
