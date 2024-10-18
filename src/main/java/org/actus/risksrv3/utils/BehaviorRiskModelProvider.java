package org.actus.risksrv3.utils;

import java.util.Set;
import java.util.List;

import org.actus.risksrv3.core.states.StateSpace; 
import org.actus.risksrv3.core.attributes.ContractModel;
import org.actus.risksrv3.models.CalloutData;

public abstract interface  BehaviorRiskModelProvider {
	  /**
	   * Returns the set of unique risk factor IDs
	   * @return set of unique risk factor IDs
	   */
	  public Set<String> keys();
	  
	  public List<CalloutData> contractStart(ContractModel contract);
      
	  /**
	   * Returns the set of callout/observation times for a particular risk factor
	   * @returns set of CalloutData <model_ID, yyyy-mm-ddT00:00:00 > 
	   */
	  
	  /**
	   * Returns the state of a particular behavior risk factor at a future time
	   * 
	   * @param id identifier of the risk factor
	   * @param states the inner states of the contract as per @code{time} argument of the method
	   * @return double the state of the risk factor
	   */
	  
	  public double stateAt(String id, StateSpace states); 
}
