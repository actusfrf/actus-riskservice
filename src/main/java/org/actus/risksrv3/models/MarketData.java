package org.actus.risksrv3.models;

import java.util.List;

public class MarketData {
	// attributes
	private List<ReferenceIndex> marketData;
	
	// null and useful constructors
	public MarketData() {
    }
	
	public MarketData(List<ReferenceIndex> marketData) {
        this.marketData = marketData;
    }
	// get and set for each attribute  
	public List<ReferenceIndex> getMarketData() {
		return this.marketData;
	}
	public void setMarketData(List<ReferenceIndex> marketData) {
		this.marketData = marketData;
	}
	// convert to string 
	public String toString() {
		String str = "{\"marketData\": [";
		boolean first = true;
		for (ReferenceIndex referenceIndex: this.marketData) {
			if (first) { 
				str += referenceIndex.toString();
				first = false;				
			}
			else 
				str += ", "+referenceIndex.toString();
		}
		str += 	"]}";
		return str;
	}
}
