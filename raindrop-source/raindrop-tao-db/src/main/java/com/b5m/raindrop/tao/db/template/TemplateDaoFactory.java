package com.b5m.raindrop.tao.db.template;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.b5m.raindrop.tao.db.IAdsDao;
import com.b5m.raindrop.tao.db.IDaoFactory;
import com.b5m.raindrop.tao.db.IGoodsDao;
import com.b5m.raindrop.tao.db.ITopDao;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 使用了{@link SingleConnectionDataSource}作为自己的链接池
 * @author jacky
 *
 */
public class TemplateDaoFactory implements IDaoFactory {
	
	public static final String CONFIG_MINPOOLSIZE = "pool.min";
	
	public static final String CONFIG_MAXPOOLSIZE = "pool.max";
	
	public static final String CONFIG_INITIALPOOLSIZE = "pool.init";
	
	public static final String CONFIG_MAXIDLETIME = "pool.max.idle";
	
	public static final String CONFIG_PREFERRED_TESTQUERY = "pool.testquery";
	
	public static final String CONFIG_IDLECONN_TESTPERIOD = "pool.idleconn.testperiod";

	private final JdbcConfig _config;
	
	private final ComboPooledDataSource _datasource;
	
	public TemplateDaoFactory(JdbcConfig config){
		this._config = config;
		_datasource = createDataSource(config);
	}
	
	private ComboPooledDataSource createDataSource(JdbcConfig config){
		/*DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setConnectionProperties(config.getProps());
		datasource.setDriverClassName(config.getDriverClass());
		datasource.setUrl(config.getUrl());
		datasource.setUsername(config.getUser());
		datasource.setPassword(config.getPassword());*/
		ComboPooledDataSource datasource = new ComboPooledDataSource();
		try {
			datasource.setDriverClass(config.getDriverClass());
			datasource.setJdbcUrl(config.getUrl());
			datasource.setUser(config.getUser());
			datasource.setPassword(config.getPassword());
			datasource.setMinPoolSize(Integer.parseInt(config.getProps().getProperty(CONFIG_MINPOOLSIZE, "1")));
			datasource.setMaxPoolSize(Integer.parseInt(config.getProps().getProperty(CONFIG_MAXPOOLSIZE, "1")));
			datasource.setInitialPoolSize(Integer.parseInt(config.getProps().getProperty(CONFIG_INITIALPOOLSIZE, "1")));
			datasource.setMaxIdleTime(Integer.parseInt(config.getProps().getProperty(CONFIG_MAXIDLETIME, "1800")));
			datasource.setPreferredTestQuery(config.getProps().getProperty(CONFIG_PREFERRED_TESTQUERY, "select count(*) from Dual"));
			datasource.setIdleConnectionTestPeriod(Integer.parseInt(config.getProps().getProperty(CONFIG_IDLECONN_TESTPERIOD, "1200")));
			
			return datasource;
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		}
	}
	
	public JdbcConfig getJdbcConfig() {
		return _config;
	}

	public DataSource getDataSource() {
		return _datasource;
	}

	@Override
	public IGoodsDao createGoodsDao() {
		return new GoodsDao(_datasource);
	}

	@Override
	public ITopDao createTopDao() {
		return new TopDao(_datasource);
	}

	@Override
	public IAdsDao createAdsDao() {
		return new AdsDao(_datasource);
	}

	@Override
	public void destroyDataSource() {
		if(null == _datasource)
			return ;
		_datasource.close();
	}

}
