package org.actus.risksrv3.utils;

import java.time.LocalDateTime;
import java.util.Set;

// FNP Aug 13th 2024 - a version of RiskFactorModelProvider for risksrv3 doing marketModel

import org.actus.states.StateSpace;

// there will be a local risksrv3 way of returning events as <moc,time>


public abstract interface  RiskFactorModelProvider {
	  /**
	   * Returns the set of unique risk factor IDs
	   * 
	   * @return set of unique risk factor IDs
	   */
	  public Set<String> keys();

	  /**
	   * Returns the set of event times for a particular risk factor
	   *
	   * The default implementation returns an empty set of events.
	   *
	   * @param attributes the attributes of the contract evaluating the events
	   * @return set of non-scheduled (contingent) contract events
	   */
//	  default public Set<ContractEvent> events(ContractModelProvider attributes) {
//	    return new HashSet<ContractEvent>();
//	  }
	  
	  /**
	   * Returns the state of a particular risk factor at a future time
	   * 
	   * @param id identifier of the risk factor
	   * @param time future time for which to return the risk factor's state
	   * @param states the inner states of the contract as per @code{time} argument of the method
	   * @param attributes the attributes of the contract evaluating the risk factor state
	   * @return double the state of the risk factor
	   */
//	  public double stateAt(String id, LocalDateTime time, StateSpace states, ContractModelProvider attributes);
	  public double stateAt(String id, LocalDateTime time, StateSpace states); 
}
