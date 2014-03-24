package com.b5m.raindrop.collector.process.stat;

import com.b5m.raindrop.collector.process.StatProcess;

public class MinProcess extends StatProcess {

	public MinProcess(String name)
	{
		super(name);
	}
		
	@Override
	public void init() {
		// TODO Auto-generated method stub
		value = Long.MAX_VALUE;
	}

	@Override
	public void gather(long value) {
		// TODO Auto-generated method stub
		if (this.value > value)
		{
			this.value = value;
		}
	}
	
//	public long getValue()
//	{
//		return value;
//	}

}
