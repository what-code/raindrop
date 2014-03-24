package com.b5m.raindrop.collector;

public abstract class BaseCollector implements Collector {

	protected long beginTimestamp;
	
	protected long endTimestamp;
	
	protected String id;
	
	protected Enabler enabler;
	
	public BaseCollector(String id)
	{
		this.id = id;
		this.enabler = new BasicEnabler();
	}
	
	public BaseCollector(String id, Enabler enabler)
	{
		this.id = id;
		this.enabler = enabler;
	}
	
	@Override
	public void beginCommit() {
		// TODO Auto-generated method stub
		setBeginTimestamp(System.currentTimeMillis());
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub
		setEndTimestamp(System.currentTimeMillis());
	}

	public long getBeginTimestamp() {
		return beginTimestamp;
	}

	public void setBeginTimestamp(long beginTimestamp) {
		this.beginTimestamp = beginTimestamp;
	}

	public long getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(long endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getCollectorName()
	{
		return id;
	}
	
}
