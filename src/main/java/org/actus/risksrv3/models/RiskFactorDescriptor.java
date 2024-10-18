package org.actus.risksrv3.models;

public class RiskFactorDescriptor {
	// attributes
	private String riskFactorID;     // key into RiskFactor store
	private String riskFactorType;   // extensible set of rf types (market, prepaymentModel) for now 
	
	public RiskFactorDescriptor() {	
	}
	
	public RiskFactorDescriptor(String riskFactorID, String riskFactorType) {
		this.riskFactorID   = riskFactorID;
		this.riskFactorType = riskFactorType;
	}
	
	public String getRiskFactorID() {
		return this.riskFactorID;
	}
	void setRiskFactorID(String riskFactorID) {
		this.riskFactorID = riskFactorID;
	}
	public String getRiskFactorType() {
		return this.riskFactorType;
	}
	void setRiskFactorType(String riskFactorType) {
		this.riskFactorType = riskFactorType;
	}
	
	public String toString() {
    	String str = "{ \"riskFactorID\": \"" +
          this.riskFactorID +  "\", \"riskFactorType\": " +
          this.riskFactorType + " }";
    	return(str);  
    }
}
