package com.b5m.raindrop.tao.counter.refresh;

import java.util.Map;

import backtype.storm.tuple.Tuple;

import com.b5m.raindrop.tao.counter.IRefreshHandler;

/**
 * 此刷新操作不做任何处理
 * @author jacky
 *
 */
public class NonOperateRefresh implements IRefreshHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2161751803189742556L;

	@Override
	public void refresh(Map<String, Long> counter, Tuple input) {
		
	}

}
