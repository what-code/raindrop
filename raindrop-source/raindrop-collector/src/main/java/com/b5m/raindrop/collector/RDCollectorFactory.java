package com.b5m.raindrop.collector;


public class RDCollectorFactory implements CollectorFactory {

	public static Enabler enabler = new BasicEnabler();
	

	
	static
	{
		enabler.enable(true);
	}
	
	
	
	@Override
	public Collector CreateLatencyCollector(String id) {
		// TODO Auto-generated method stub
		Collector collector = new LatencyCollecter(id, enabler);
		//collector.setExtraInfo("testtest");
		return collector;
	}




}
