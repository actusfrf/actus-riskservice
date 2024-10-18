package org.actus.risksrv3.utils;

import java.util.Set;
import java.time.LocalDateTime;



//FNP 1 sep 2024 - a version of RiskFactorModelProvider interface for risksrv3 
//doing market model callouts BUT NOT behavior model callouts 


public abstract interface MarketRiskModelProvider {
	
	  /**
	   * Returns the set of unique market risk factor IDs
	   * @return set of unique risk factor IDs
	   */
	  public Set<String> keys();
	  
	  /**
	   * Returns the state of a particular market risk factor at a future time
	   * 
	   * @param id market object code identifier of the risk factor
	   * @param time - the simulation time at which the marketObjectCode is to be evaluated for the current scenario 
	   * @return double the state of the market risk factor
	   */
	  // time is passed  explicitly for market stateAt( ) because no contract  state is passed 
//	  public double stateAt(String id, LocalDateTime time, StateSpace states, ContractModelProvider attributes);
	  
	  public double stateAt(String id, LocalDateTime time); 
}
