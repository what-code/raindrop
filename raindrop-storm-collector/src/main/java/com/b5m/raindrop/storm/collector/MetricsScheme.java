package com.b5m.raindrop.storm.collector;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.b5m.raindrop.collector.metrics.Metrics;
import backtype.storm.spout.Scheme;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class MetricsScheme implements Scheme {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2749841911082510824L;

	@Override
	public List<Object> deserialize(byte[] ser) {
		try {
            return new Values(convert(new String(ser, "UTF-8")));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
	}

	@Override
	public Fields getOutputFields() {
		return new Fields("Metrics");
	}

	private Metrics convert(String str) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(new StringReader(str), Metrics.class);
	}


}
