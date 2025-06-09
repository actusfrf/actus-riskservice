package org.actus.risksrv3.models;

public class DepositWfeeTrx {
	// attributes
	private String 	time ;  		 // ISO timeDate yyyy-mm-ddT00:00:00
	private Double  depositAmount;
	private Double  transactionFeeAmount; 
	
	// null and useful constructors
	public DepositWfeeTrx() {
    }
	public DepositWfeeTrx( String time, Double depositAmount, Double transactionFeeAmount) {
		this.time = time;
		this.depositAmount = depositAmount;
		this.transactionFeeAmount = transactionFeeAmount;
	}
	
	// get
	public String getTime() {
		return this.time;
	}
	public Double getDepositAmount () {
		return this.depositAmount;
	}
	public Double  getTransactionFeeAmount() {
		return this.transactionFeeAmount;
	}

	//set
	public void setTime( String time) {
		this.time = time;
	}
	public void setDepositAmount ( Double depositAmount) {
		this.depositAmount = depositAmount;
	}
	public void setTransactionFeeAmount( Double transactionFeeAmount) {
		this.transactionFeeAmount = transactionFeeAmount;
	}
	
	//Serialization for diagnostics 
    public String toString() {
    	String str = 
    		"{ \"time\": \"" + this.time +  
    		"\", \"depositAmount\": " + String.valueOf(this.depositAmount) +  
    		", \"transactionFeeAmount\": " + String.valueOf(this.transactionFeeAmount) +
    		"}";
    	return(str);  
    }
}
