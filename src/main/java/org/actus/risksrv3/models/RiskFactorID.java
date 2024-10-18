package org.actus.risksrv3.models;


public class RiskFactorID {
	// attributes
	private String riskFactorID;
	
	// null and useful constructors
	public RiskFactorID() {
    }
	public RiskFactorID(String riskFactorID) {
        this.riskFactorID = riskFactorID;
    }
	
	// get
    public String getRiskFactorID() {
        return this.riskFactorID;
    }
    // set
    public void setRiskFactorID(String riskFactorID) {
        this.riskFactorID = riskFactorID;
    }

    @Override
    public final String toString() {
    	return riskFactorID;
    }
}
