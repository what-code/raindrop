package com.b5m.raindrop.cache.memcache;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.URLConnection;
import java.util.Properties;

public class PropertiesHelper {

	private Properties properties;

	public static Properties Load(String configFilename) {
		Properties props = new Properties();
		FileInputStream istream = null;
		try {
			istream = new FileInputStream(configFilename);
			props.load(istream);
			istream.close();
		} catch (Exception e) {
			if (e instanceof InterruptedIOException
					|| e instanceof InterruptedException) {
				Thread.currentThread().interrupt();
			}
			return null;
		} finally {
			if (istream != null) {
				try {
					istream.close();
				} catch (InterruptedIOException ignore) {
					Thread.currentThread().interrupt();
				} catch (Throwable ignore) {
				}

			}
		}
		return props;
	}

	public static Properties Load(java.net.URL configURL) {
		Properties props = new Properties();
		InputStream istream = null;
		URLConnection uConn = null;
		try {
			uConn = configURL.openConnection();
			uConn.setUseCaches(false);
			istream = uConn.getInputStream();
			props.load(istream);
		} catch (Exception e) {
			if (e instanceof InterruptedIOException
					|| e instanceof InterruptedException) {
				Thread.currentThread().interrupt();
			}

			return null;
		} finally {
			if (istream != null) {
				try {
					istream.close();
				} catch (InterruptedIOException ignore) {
					Thread.currentThread().interrupt();
				} catch (IOException ignore) {
				} catch (RuntimeException ignore) {
				}
			}
		}
		return props;
	}

	public PropertiesHelper(Properties properties) {
		this.properties = properties;
	}

	public String get(String key) {
		return properties.getProperty(key);
	}

	public String get(String key, String defaultVal) {
		String val = get(key);
		return isStringBlank(val) ? defaultVal : val;
	}

	public String findValue(String... keys) {
		for (String key : keys) {
			String value = get(key);
			if (!isStringBlank(value)) {
				return value;
			}
		}
		return null;
	}

	public boolean getBoolean(String key, boolean defaultVal) {
		String val = get(key);
		return isStringBlank(val) ? defaultVal : Boolean.parseBoolean(val);
	}

	public long getLong(String key, long defaultVal) {
		String val = get(key);
		return isStringBlank(val) ? defaultVal : Long.parseLong(val);
	}

	public int getInt(String key, int defaultVal) {
		return (int) getLong(key, defaultVal);
	}

	public double getDouble(String key, double defaultVal) {
		String val = get(key);
		return isStringBlank(val) ? defaultVal : Double.parseDouble(val);
	}

	public <T extends Enum<T>> T getEnum(String key, Class<T> type,
			T defaultValue) {
		String val = get(key);
		return isStringBlank(val) ? defaultValue : Enum.valueOf(type, val);
	}
	
	private boolean isStringBlank(String val){
		if(null == val || "".equals(val))
			return true;
		return false;
	}
}
