package org.actus.risksrv3.models;

import java.util.List;

public class ContractDepositWfeeTrxs {
	private String contractID;
	private List<DepositWfeeTrx>  depositWfeeTrxs;
	
	// null and useful constructors
	public ContractDepositWfeeTrxs() { }
	
	public ContractDepositWfeeTrxs( String contractID, List<DepositWfeeTrx> depositWfeeTrxs) {
		this.contractID = contractID;
		this.depositWfeeTrxs = depositWfeeTrxs;
	}
	
	// get
	public String getContractID() {
		return this.contractID;
	}
	
	public List<DepositWfeeTrx> getDepositWfeeTrxs ( ) {
		return this.depositWfeeTrxs;
	}
	
	//set 
	public void setContractID(String contractID) {
		this.contractID = contractID;
	}
	public void setDepositWfeeTrxs( List<DepositWfeeTrx> depositWfeeTrxs) {
		this.depositWfeeTrxs = depositWfeeTrxs;
	}
	
	//Serialization for diagnostics 
    public String toString() {
    	String str = 
    		"{ \"contractID\": \"" + this.contractID +  
    		"\", \"depositWfeeTrxs\": " + String.valueOf(this.depositWfeeTrxs) +  
          	"}";
    	return(str);  
    }

}
