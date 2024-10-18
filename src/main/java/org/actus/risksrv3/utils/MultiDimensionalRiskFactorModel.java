package org.actus.risksrv3.utils;

import java.util.Set;
import java.util.HashMap;
import java.time.LocalDateTime;
import org.actus.states.StateSpace;


public class MultiDimensionalRiskFactorModel implements RiskFactorModelProvider {
	
	HashMap<String,RiskFactorModelProvider> model = new HashMap<String,RiskFactorModelProvider>();
	
	public MultiDimensionalRiskFactorModel() {
	}

	public Set<String> keys() {
		return model.keySet();
	}

	public void add(String symbol, RiskFactorModelProvider dimension) {
		model.put(symbol,dimension);
	}

	public double stateAt(String id, LocalDateTime time, StateSpace state) {
		return model.get(id).stateAt(id,time,state);
	}
}
