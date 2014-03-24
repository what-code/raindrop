package com.b5m.raindrop.tao.db.template;

import java.io.Serializable;
import java.util.Properties;

/**
 * 有关JDBC的配置
 * @author jacky
 *
 */
public class JdbcConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5545207910196657539L;

	private String driverClass;
	
	private String url;
	
	private String user;
	
	private String password;
	
	private Properties props;

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Properties getProps() {
		if(null == props)
			props = new Properties();
		return props;
	}

	public void setProps(Properties props) {
		this.props = props;
	}
	
}
