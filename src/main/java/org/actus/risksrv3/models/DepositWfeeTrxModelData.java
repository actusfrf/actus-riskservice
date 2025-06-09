package org.actus.risksrv3.models;

import java.util.List;
import org.springframework.data.annotation.Id;

public class DepositWfeeTrxModelData {
    private @Id String riskFactorID;
    private List<ContractDepositWfeeTrxs> contractDepositWfeeTrxs;
    
    // null and useful constructors
    public DepositWfeeTrxModelData() {
    	
    }
    
    public DepositWfeeTrxModelData( 
    		String riskFactorID, 
    		List<ContractDepositWfeeTrxs> contractDepositWfeeTrxs) {
    	this.riskFactorID = riskFactorID;
    	this.contractDepositWfeeTrxs = contractDepositWfeeTrxs;
    }
    
    // get
    public String getRiskFactorID() {
        return this.riskFactorID;
    }
    public List<ContractDepositWfeeTrxs> getContractDepositWfeeTrxs(){
    	return this.contractDepositWfeeTrxs;
    }
    
    // set
    void setRiskFactorID(String riskFactorID ) {
    	this.riskFactorID = riskFactorID;
    }
    void setContractDepositWfeeTrxs( List<ContractDepositWfeeTrxs> contractDepositWfeeTrxs){
    	this.contractDepositWfeeTrxs = contractDepositWfeeTrxs;
    }
    
	//Serialization for diagnostics 
    public String toString() {
    	String str = 
    		"{ \"riskFactorID\": \"" + this.riskFactorID +  
    		"\", \"contractDepositWfeeTrxs\": " + String.valueOf(this.contractDepositWfeeTrxs) +  
          	"}";
    	return(str);  
    }
    
    
}
