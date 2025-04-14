package org.actus.risksrv3.utils;

import java.util.HashMap;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

import org.actus.risksrv3.core.states.StateSpace;
import org.actus.risksrv3.models.CalloutData;
import org.actus.risksrv3.core.attributes.ContractModel;

public class MultiBehaviorRiskModel implements BehaviorRiskModelProvider {

	HashMap<String,BehaviorRiskModelProvider> model = new HashMap<String,BehaviorRiskModelProvider>();
	
	public MultiBehaviorRiskModel() {
	}
	
	public Set<String> keys() {
		return model.keySet();
	}

	public void add(String symbol, BehaviorRiskModelProvider behaviorModel) {
		model.put(symbol,behaviorModel);
	}

	public double stateAt(String id, LocalDateTime time, StateSpace state) {
		System.out.println("** fnp061 in stateAt id = " + id );
		System.out.println("** fnp062 model.get(id) = " + model.get(id).toString());
		return model.get(id).stateAt(id, time, state);
	}
	
	public List<CalloutData> contractStart(ContractModel contractModel){
		// This method is now deprecated - but required for implements BehaviorRiskModelProvider
		// which is also deprecated and possible unnecessary 
		
		//Get list of activated models from the parsed contract terms 
		// Relay on the caller to have checked that all behaviorModelIDs in the contract are defined for the scenario 
		
		// For each activated model: call contractStart(), add callout/observations to list 
		// return final combined observations list 
		
		List<String> mdls = contractModel.getAs("prepaymentModels");	
		System.out.println("**** fnp060: mdls = <" + mdls + ">");				
		List<CalloutData> calloutData  = new ArrayList<CalloutData>();
		for (String mdl : mdls) {	   
			 calloutData.addAll(model.get(mdl).contractStart(contractModel)); 
		}
		return calloutData;			
	}	
	
	public List<CalloutData> modelContractStart(ContractModel contractModel, String modelID){
		return model.get(modelID).contractStart(contractModel);
	}
	
}
