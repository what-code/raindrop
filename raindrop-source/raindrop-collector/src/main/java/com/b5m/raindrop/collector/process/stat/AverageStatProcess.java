package com.b5m.raindrop.collector.process.stat;

import com.b5m.raindrop.collector.process.StatProcess;

public class AverageStatProcess extends StatProcess {
	
	public AverageStatProcess(String name)
	{
		super(name);
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		times = 0;
		value = 0;
	}

	@Override
	public void gather(long value) {
		// TODO Auto-generated method stub
		this.value += value;
		this.times++;
	}

	public long getValue() {
		// TODO Auto-generated method stub
		return (long)(value / times);
	}

	
}
