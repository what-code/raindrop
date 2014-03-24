package com.b5m.raindrop.collector.process;

public abstract class StatProcess {

	protected int times;
	
	protected long value;
	
	protected String name;
	
	public StatProcess(String name)
	{
		this.name = name;
	}
	
	public abstract void init();
	
	public abstract void gather(long value);
	
	public long getValue()
	{
		return value;
	}
	
	public long getTimes()
	{
		return times;
	}
	
	public String getName()
	{
		return name;
	}
	
}
