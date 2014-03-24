package com.b5m.raindrop.tao.top;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

import com.b5m.raindrop.cache.memcache.MemcachedClientFactory;
import com.b5m.raindrop.cache.memcache.MemcachedClientWrapper;
import com.b5m.raindrop.cache.memcache.PropertiesHelper;
import com.b5m.raindrop.cache.memcache.XMemcachedClientFactory;
import com.b5m.raindrop.tao.db.IDaoFactory;
import com.b5m.raindrop.tao.db.ITopDao;
import com.b5m.raindrop.tao.db.module.GoodsBean;
import com.b5m.raindrop.tao.db.template.AbstractDao;
import com.b5m.raindrop.tao.db.template.JdbcConfig;
import com.b5m.raindrop.tao.db.template.TemplateDaoFactory;

/**
 * 刷新排行榜的数据，排行榜的数据从{@link ITopDao}中获取，然后刷新到缓存中。
 * @author jacky
 *
 */
public class TopBolt extends BaseRichBolt {
	
	public static final String KEY_JDBCCONFIG = "tao.jdbc.config";
	
	public static final String KEY_MEMCACHED_CONFIG = "tao.memcached.config";
	
	public static final String KEY_MEMCACHED_EXPIRED_TIME = "tao.memcached.expired";
	
	public static final String KEY_MEMCACHED_RETRY_COUNT = "tao.memcached.retry";
	
	public static final String DEFAULT_MEMCACHED_CONFIG = "/raindrop/memcached.properties";
	
	public static final int DEFAULT_EXPIRED_TIME = 60 * 5;
	
	/**
	 * 如果访问memcached失败了，需要重试
	 */
	public static final int DEFAULT_RETRY_COUNT = 3;
	
	/**
	 * Tao的排行榜的缓存的Key
	 */
	public static final String MEMCACHED_KEY_TOP = "top";
	
	private OutputCollector _collector;
	/**
	 * 
	 */
	private static final long serialVersionUID = 7374740953387221369L;
	
	private JdbcConfig config;
	
	private transient ITopDao topDao;	

	private transient Logger log;
	
	private transient MemcachedClientWrapper memcachedClient;
	
	private int memcachedExpired = DEFAULT_EXPIRED_TIME;
	
	private int retryCountWhenMemcachedFail = DEFAULT_RETRY_COUNT;
	
	private String memcachedConfig = DEFAULT_MEMCACHED_CONFIG;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		if(!stormConf.containsKey(KEY_JDBCCONFIG)){
			throw new RuntimeException("can't find key:" + KEY_JDBCCONFIG);
		}
		log = Logger.getLogger(this.getClass());
		config = (JdbcConfig)stormConf.get(KEY_JDBCCONFIG);
		IDaoFactory factory  = new TemplateDaoFactory(config);
		topDao = factory.createTopDao();
		
		
		if(!stormConf.containsKey(KEY_MEMCACHED_CONFIG)){
			log.warn("can't find key:" + KEY_MEMCACHED_CONFIG + ", it would use default config:" + DEFAULT_MEMCACHED_CONFIG);
		}else{
			memcachedConfig = (String)stormConf.get(KEY_MEMCACHED_CONFIG);
		}
		Properties props = PropertiesHelper.Load(this.getClass().getResource(memcachedConfig));
		PropertiesHelper properties = new PropertiesHelper(props);	
		MemcachedClientFactory memFactory = new XMemcachedClientFactory(properties);
		try {
			memcachedClient = memFactory.createMemcachedClient("tao");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		
		// 设置有效期
		if(stormConf.containsKey(KEY_MEMCACHED_EXPIRED_TIME)){
			memcachedExpired = (Integer)stormConf.get(KEY_MEMCACHED_EXPIRED_TIME);
		}
		
		// 设置重试次数
		if(stormConf.containsKey(KEY_MEMCACHED_RETRY_COUNT)){
			retryCountWhenMemcachedFail = (Integer)stormConf.get(KEY_MEMCACHED_RETRY_COUNT);
		}
		this._collector = collector;
		log = Logger.getLogger(this.getClass());
	}
	
	private void refreshToMemcached(List<GoodsBean> beans){
		for(int i = 0;i <= retryCountWhenMemcachedFail;i++){
			if(memcachedClient.put(MEMCACHED_KEY_TOP, beans, memcachedExpired)){
				return ;
			}
			log.error("put key:" + MEMCACHED_KEY_TOP + " failure. retry #" + (i+1));
		}
	}

	@Override
	public void execute(Tuple input) {
		/*if("signals".equals(input.getSourceStreamId())){
			if(log.isDebugEnabled()){
				log.debug("received signals");
			}
			_collector.ack(input);
			return ;
		}*/
		try {
			refreshToMemcached(topDao.queryTopGoods());
			_collector.ack(input);
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			_collector.reportError(e);
			_collector.fail(input);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		
	} 
	
	@Override
    public void cleanup() {
		if(null == memcachedClient)
			return ;
		memcachedClient.shutdown();
		if(null == topDao || !(topDao instanceof AbstractDao))
			return ;
		try {
			((AbstractDao)topDao).close();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
    }   

}
