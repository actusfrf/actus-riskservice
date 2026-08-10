package org.actus.risksrv3.models;
import org.springframework.data.annotation.Id;

public class CreditRiskModelData {
	private @Id String riskFactorId;
	private String calloutPeriod;     	// callout periodicity eg "P9M"
	private Double defaultProbability;	// probability of default on each callout 
	
	public CreditRiskModelData() {	
	}	
	public CreditRiskModelData (String riskFactorId, String calloutPeriod, Double defaultProbability) {
		this.riskFactorId  = riskFactorId;
		this.calloutPeriod = calloutPeriod;
		this.defaultProbability = defaultProbability;
	}
	
	public String getRiskFactorId() {
		return this.riskFactorId;
	}
	public void putRiskFactorId(String riskFactorId) {
		this.riskFactorId = riskFactorId; 
	}
	
	public String getCalloutPeriod() {
		return this.calloutPeriod;
	}
	public void putCalloutPeriod(String calloutPeriod) {
		this.calloutPeriod = calloutPeriod;
	}
	
	public Double getDefaultProbability() {
		return this.defaultProbability ;
	}
	public void setDefaultProbability(Double defaultProbability) {
		this.defaultProbability = defaultProbability;
	}
	
	 @Override
	 public String toString() {
	    final StringBuilder sb = new StringBuilder("CreditRiskModelData{'");
	    sb.append("riskFactorId='").append(riskFactorId).append('\'');
	    sb.append(", calloutPeriod='").append(calloutPeriod).append('\'');
	    sb.append(", defaultProbability='").append(defaultProbability).append('\'');
	    sb.append('}');
	    return sb.toString();
	 }
}
