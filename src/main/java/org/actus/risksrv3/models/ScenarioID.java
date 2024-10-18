package org.actus.risksrv3.models;

public class ScenarioID {
	// attributes
	private String ScenarioID;
	
	// null and useful constructors
	public ScenarioID() {
    }
	public ScenarioID(String ScenarioID) {
        this.ScenarioID = ScenarioID;
    }
	
	// get
    public String getScenarioID() {
        return this.ScenarioID;
    }
    // set
    public void setScenarioID(String ScenarioID) {
        this.ScenarioID = ScenarioID;
    }
}
