package com.b5m.raindrop.tao.db.template;

import java.io.Closeable;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

/**
 * Dao的基类，将{@link DataSource}封装进来
 * @author jacky
 *
 */
public abstract class AbstractDao implements Closeable{
	protected final JdbcTemplate _template;
	
	public AbstractDao(DataSource ds){
		this._template = createJdbcTemplate(ds);
	}
	
	protected JdbcTemplate createJdbcTemplate(DataSource ds){
		return new JdbcTemplate(ds);
	}
	
	protected Date getNowDate(){
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	public JdbcTemplate getJdbcTemplate() {
		return _template;
	}

	@Override
	public void close() throws IOException {
		if(!(_template.getDataSource() instanceof SingleConnectionDataSource)){
			return ;
		}
		((SingleConnectionDataSource)_template.getDataSource()).destroy();
	}
}
