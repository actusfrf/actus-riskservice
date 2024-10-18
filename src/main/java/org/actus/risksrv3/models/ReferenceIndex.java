package org.actus.risksrv3.models;

import java.util.List;
import org.springframework.data.annotation.Id;

public class ReferenceIndex {
	// attributes
	@Id private String riskFactorID;
		private String marketObjectCode; 
		private Double base;
		private List<TimeValuePair> data;
		
		// null and useful constructors
		public ReferenceIndex() {
	    }
		
		public ReferenceIndex(String riskFactorID, String marketObjectCode, 
				Double base, List<TimeValuePair> data) {
	        this.riskFactorID = riskFactorID;
	        this.data = data;
	    }
		
		// get for each attribute
	    public String getRiskFactorID() {
	        return this.riskFactorID;
	    }
	    public String getMarketObjectCode() {
	    	return this.marketObjectCode;
	    }
	    public Double getBase() {
	    	return this.base;
	    }
	    public List<TimeValuePair> getData(){
	    	return this.data;
	    }
		
	    // set for each attribute 
	    public void setString(String riskFactorID) {
	        this.riskFactorID = riskFactorID;
	    }
	    public void setMarketObjectCode(String marketObjectCode) {
	    	this.marketObjectCode = marketObjectCode;
	    }
	    public void setBase(Double base) {
	    	this.base = base;
	    }
	    public void setData(List<TimeValuePair> data) {
	    	this.data = data;
	    }
	    public String toString() {
	    	String str = "{ \"riskFactorID\": \"" + this.riskFactorID + 
	    			"\" , \"marketObjectCode\" : \"" + this.marketObjectCode +
	    			"\" , \"base\" , \"" + this.base + "\" , \"data\": [ "  ;
	    			boolean first = true;
	    			for (TimeValuePair tv : this.data) {
	    				if (first) {
	    					str +=  tv.toString();
	    					first = false;
	    				}
	    				else {
	    					str += ", " + tv.toString();
	    				}
	    			}
	    			str += "]" ;
	    			return(str);
	    }
}
