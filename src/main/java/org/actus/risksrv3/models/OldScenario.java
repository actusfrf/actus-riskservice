package org.actus.risksrv3.models;

import java.util.List;

public class OldScenario {
	// attributes
	private String scenarioID;
	private List<String> marketRiskFactorIDs;
	private List<String> prepaymentRiskFactorIDs;
	
	// null and useful constructors
	public OldScenario() {
    }
	
	public OldScenario(String scenarioID, List<String> marketRiskFactorIDs, 
			        List<String> prepaymentRiskFactorIDs) {
        this.scenarioID = scenarioID;
        this.marketRiskFactorIDs = marketRiskFactorIDs;
        this.prepaymentRiskFactorIDs = prepaymentRiskFactorIDs;
    }
	
	// get for each attribute
    public String getScenarioID() {
        return this.scenarioID;
    }
    public List<String> getMarketRiskFactorIDs(){
    	return this.marketRiskFactorIDs;
    }
	
    public List<String> getPrepaymentRiskFactorIDs(){
    	return this.prepaymentRiskFactorIDs;
    }
    
    // set for each attribute 
    public void setScenarioID(String scenarioID) {
        this.scenarioID = scenarioID;
    }
    public void setmarketRiskFactorIDs(List<String> marketRiskFactorIDs) {
    	this.marketRiskFactorIDs = marketRiskFactorIDs;
    }    
    public void setPrepaymentRiskFactorIDs(List<String> prepaymentRiskFactorIDs) {
    	this.prepaymentRiskFactorIDs = prepaymentRiskFactorIDs ;
    }
    
    public String toJSON() {
    	String outStr = "{\"scenarioID\": \""+scenarioID+"\", \"marketRiskFactorIDs\":[" ;
    	boolean first=true;
    	for (String rfid : this.marketRiskFactorIDs) {
    		if (!first) {
    	        outStr += ", ";
    			first = false;
    		} 
    		outStr+= "{\"riskFactorID\": \"" + rfid + "\"}";
    	}
    	outStr += "], \"prepaymentRiskFactorIDs\":[";
    	first = true;
    	for (String rfid : this.prepaymentRiskFactorIDs) {
    		if (!first) {
    	        outStr += ", ";
    			first = false;
    		} 
    		outStr+= "{\"riskFactorID\": \"" + rfid + "\"}";
    	}
    	outStr += "]}";
    	return(outStr);
    }
}
