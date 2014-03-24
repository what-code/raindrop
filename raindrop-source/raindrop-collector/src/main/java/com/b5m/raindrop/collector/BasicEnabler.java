package com.b5m.raindrop.collector;

public class BasicEnabler implements Enabler {
	
	public boolean flag = true;
	
	public BasicEnabler()
	{
		flag = true;
	}
	
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return flag;
	}

	@Override
	public void enable(boolean flag) {
		// TODO Auto-generated method stub
		this.flag = flag;
	}

}
