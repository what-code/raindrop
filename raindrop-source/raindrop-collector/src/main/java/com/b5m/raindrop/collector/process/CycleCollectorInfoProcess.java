package com.b5m.raindrop.collector.process;

import java.util.LinkedList;
import java.util.List;

import com.b5m.raindrop.collector.CollectorInfo;
import com.b5m.raindrop.collector.CollectorInfoProcess;
import com.b5m.raindrop.collector.metrics.Metrics;
import com.b5m.raindrop.collector.process.stat.AverageStatProcess;
import com.b5m.raindrop.collector.process.stat.MaxProcess;
import com.b5m.raindrop.collector.process.stat.MinProcess;

public class CycleCollectorInfoProcess implements CollectorInfoProcess {

	private static final long DEFAULT_CYCLE = 1000;

	private long cycle = DEFAULT_CYCLE;

	private long beginTime;

	private List<StatProcess> lstStatProcess = new LinkedList<StatProcess>();

	public CycleCollectorInfoProcess() {
		lstStatProcess.add(new MinProcess(".min"));
		lstStatProcess.add(new MaxProcess(".max"));
		lstStatProcess.add(new AverageStatProcess(".avg"));
	}

	public CycleCollectorInfoProcess(long cycle) {
		this.cycle = cycle;
	}

	@Override
	public List<Metrics> process(CollectorInfo collectorInfo) {
		// TODO Auto-generated method stub
		
		System.out.println(collectorInfo.toString());
		
		if (beginTime == 0) {
			beginTime = collectorInfo.getTimestamp();
			for (StatProcess statProcess : lstStatProcess) {
				statProcess.init();
			}
		}

		for (StatProcess statProcess : lstStatProcess) {
			statProcess.gather(collectorInfo.getValue());
		}

		if (collectorInfo.getTimestamp() - beginTime >= cycle) {
			
			// 若超出时间过长，取消结果
			if (collectorInfo.getTimestamp() - beginTime - collectorInfo.getValue() >= cycle*2)
			{
				beginTime = 0;
				return null;
			}
			List<Metrics> lstMetrics = new LinkedList<Metrics>();
			for (StatProcess statProcess : lstStatProcess) {
				Metrics metrics = new Metrics(collectorInfo.getMetricsName()+statProcess.getName(),
						statProcess.getValue(), collectorInfo.getTimestamp());
				// FIXME 修改成从全局变量中读取
				metrics.addMetricsTag("host", "test1");
				metrics.addMetricsTag("prod", "tao");
				lstMetrics.add(metrics);
			}
			
			beginTime = 0;
			return lstMetrics;
		}

		return null;
	}

	@Override
	public List<Metrics> passiveProcess() {
		// TODO Auto-generated method stub
		return null;
	}

}
