package org.actus.risksrv3.utils;

import java.util.Set;
import java.util.HashMap;
import java.time.LocalDateTime;


public class MultiMarketRiskModel implements MarketRiskModelProvider {
	
	HashMap<String,MarketRiskModelProvider> model = new HashMap<String,MarketRiskModelProvider>();
	
	public MultiMarketRiskModel() {
	}

	public Set<String> keys() {
		return model.keySet();
	}

	public void add(String symbol, MarketRiskModelProvider dimension) {
		model.put(symbol,dimension);
	}

	public double stateAt(String id, LocalDateTime time) {
		return model.get(id).stateAt(id,time);
	}
}
