package org.actus.risksrv3.models;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Scenario {
	// attributes
	private @Id String scenarioID;
	private  List<RiskFactorDescriptor> riskFactorDescriptors;
	
	public Scenario( ) {
	}
	public Scenario(String scenarioID, List<RiskFactorDescriptor> riskFactorDescriptors) {
		this.scenarioID = scenarioID;
		this.riskFactorDescriptors = riskFactorDescriptors;
	}
	
	public String getScenarioID( ) {
		return this.scenarioID;
	}
	public void setScenarioID(String scenarioID) {
		this.scenarioID = scenarioID;
	}
	public List<RiskFactorDescriptor> getRiskFactorDescriptors(){
		return this.riskFactorDescriptors;
	}
	void setRiskfactorDescriptors(List<RiskFactorDescriptor> riskFactorDescriptors) {
		this.riskFactorDescriptors = riskFactorDescriptors;
	}
	public String toString() {
    	String str = "{ \"scenarioID\": \"" + this.scenarioID + 
    			"\" , \"riskFactorDescriptors\" : [ " ; 
    			boolean first = true;
    			for (RiskFactorDescriptor rfd : this.riskFactorDescriptors) {
    				if (first) {
    					str +=  rfd.toString();
    					first = false;
    				}
    				else {
    					str += ", " + rfd.toString();
    				}
    			}
    			str += "]" ;
    			return(str);
    }
}
