package org.actus.risksrv3.utils;

import java.util.Set;
import org.actus.risksrv3.models.ReferenceIndex;

import java.time.LocalDateTime;

public class TimeSeriesModel implements MarketRiskModelProvider {
	private String marketObjectCode;
	private TimeSeries<LocalDateTime,Double> timeSeries;
	
	public TimeSeriesModel(){
	}

	public TimeSeriesModel(ReferenceIndex referenceIndex) {
		this.marketObjectCode = referenceIndex.getMarketObjectCode();
		Double base = referenceIndex.getBase();
		LocalDateTime[] times = referenceIndex.getData().stream().map(obs -> LocalDateTime.parse(obs.getTime())).toArray(LocalDateTime[]::new);
        Double[] values = referenceIndex.getData().stream().map(obs -> 1/base*obs.getValue()).toArray(Double[]::new);  
        this.timeSeries = new TimeSeries<LocalDateTime,Double>();
        this.timeSeries.of(times,values);
	}	
	public Set<String> keys() {
		return Set.of(this.marketObjectCode);
	}
	public double stateAt(String id, LocalDateTime time ) {
		return timeSeries.getValueFor(time,1);
	}
}
