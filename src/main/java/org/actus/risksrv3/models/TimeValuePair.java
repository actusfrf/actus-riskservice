package org.actus.risksrv3.models;

public class TimeValuePair {
	// attributes
	private String time;    // ISO timeDate yyyy-mm-ddT00:00:00
	private Double value;   // Numeric value
	
	// null and useful constructors
	public TimeValuePair() {
    }
	public TimeValuePair(String time, Double value) {
        this.time  = time;
        this.value = value;
    }
	
	// get
    public String getTime() {
        return this.time;
    }
    public Double getValue() {
    	return this.value;
    }
    // set
    public void setTime(String time) {
        this.time = time ;
    }
    public void setValue(Double value) {
    	this.value = value;
    }

    public String toString() {
    	String str = "{ \"time\": \"" +
          this.time +  "\", \"value\": " +
          String.valueOf(this.value) + " }";
    	return(str);  
    }
}
