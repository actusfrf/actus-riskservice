package org.actus.risksrv3.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.actus.risksrv3.models.DepositWfeeTrx;
import org.actus.risksrv3.core.attributes.ContractModel;
import org.actus.risksrv3.core.states.StateSpace;
import org.actus.risksrv3.models.CalloutData;
import org.actus.risksrv3.models.ContractDepositWfeeTrxs;

public class DepositWfeeTrxModel implements BehaviorRiskModelProvider {

	public static final String CALLOUT_TYPE = "AFD";  // AbsoluteFundedDelta

	private String riskFactorID;
	private HashMap<String, List<DepositWfeeTrx>> contractTrxLists; 	// contractID -> trxList
	private HashMap<String, DepositWfeeTrx> currentContractTrxs; 		// date -> DepositWfeeTrx
	private String currentContractID;
	
	public DepositWfeeTrxModel() { }
		
    // At create time initialize contractTrxLists for hash fetch contract specific transactions 
	public DepositWfeeTrxModel(String riskFactorID, List<ContractDepositWfeeTrxs> contractDepositWfeeTrxs) {
    	this.riskFactorID = riskFactorID;
    	this.contractTrxLists = new HashMap<>();
    	for ( ContractDepositWfeeTrxs cdwt :  contractDepositWfeeTrxs ) {
    		 this.contractTrxLists.put( cdwt.getContractID(), cdwt.getDepositWfeeTrxs());
    	}
    } 
	
	// return singleton Set with riskFactorID as required by BehaviorRiskModelProvider
	public Set<String> keys() {
        return Set.of(this.riskFactorID);
		}
	
	// at contract start: lookup contractID and initialize hash fetch this contracts transactions
	// build list of calloutData in the same loop 
	
    public List<CalloutData> contractStart (ContractModel contract) {
		// save initialExchangeDate of this contract 
		this.currentContractID  = contract.getAs("contractID");
		List<DepositWfeeTrx> depositWfeeTrxs = this.contractTrxLists.get(this.currentContractID);
		// TBD ***  add check for not found case - throw exception no data this Deposit model for contractID 
		
		// save hash map for trxs for this contract by date - 
		// and create the list of callout times in same loop 
		this.currentContractTrxs = new HashMap<>();
		List<CalloutData> cllds = new ArrayList<CalloutData>();
		for ( DepositWfeeTrx  dwt : depositWfeeTrxs) {
			this.currentContractTrxs.put(dwt.getTime(), dwt);
			CalloutData clld = new  CalloutData(this.riskFactorID,dwt.getTime(), DepositWfeeTrxModel.CALLOUT_TYPE);
			cllds.add(clld);
		}
		return cllds;	
	}	
	
    // lookup the deposit/Withdrawal amount by date 
    // if negative, make more negative by also deducting transaction fee amount 
    // checking fof sufficient function happens in the actus-service AFD callout event logic 
    
    public double stateAt(String id, LocalDateTime time, StateSpace states) {
    	DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    	String timeS = time.format(formatter);
    	System.out.println("****fnp810 DepositWfeeTrxModel stateAt() entered at time " + String.valueOf(time)); 
    	System.out.println("****fnp820 formatted Time = " + timeS );

    // now simple lookup and add transaction fee on withdrawal
    // possible error for missing date ( actually this should not happen  TBD *** ? 
    
    	DepositWfeeTrx dwt = this.currentContractTrxs.get(timeS);
    	Double trxAmt = dwt.getDepositAmount();
    	if (dwt.getDepositAmount() < 0 ) trxAmt -= dwt.getTransactionFeeAmount();
    
    	return trxAmt;  
    }
    
}
