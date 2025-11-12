package org.actus.risksrv3.brm20;

import java.util.ArrayList;

public class Array2Dint {
	private
		int nrows ;
		int ncols ;
	    Integer [][] arr;
	    Double[] rowMarginVals;
	    Double[] colMarginVals;
	public Array2Dint() {
	}
	public Array2Dint( ArrayList<Double> rowMargins,
					   ArrayList<Double> colMargins, 
					   ArrayList<Integer> values) {
		this.nrows = rowMargins.size();
		this.ncols = colMargins.size();
		this.rowMarginVals = new Double[this.nrows];
		this.colMarginVals = new Double[this.ncols];
		for (int i =0; i < this.nrows; i++ ) {
			this.rowMarginVals[i] = rowMargins.get(i); 
		}
		for (int j = 0; j < this.ncols; j++ ) {
			this.colMarginVals[j] = colMargins.get(j); 
		}
		this.arr = new Integer [nrows][ncols];
		
		System.out.println( "Arr2Dint: Length values = "+ values.size() + " value of values.get(0) = " + values.get(0));
		int t = 0;
		// Input for each row
        for (int i = 0; i < this.nrows && t< values.size(); i++) {
            // All rows will have the same column count ncols
            this.arr[i] = new Integer[this.ncols];
            for (int j = 0; j < this.ncols ; j++) {
                this.arr[i][j] = values.get(t);
                t++;
            }
        }
	}
	public Integer get (int i, int j) {
		return this.arr[i][j];
	}
	
	public Double getRowMarginVal(int i) {
		return this.rowMarginVals[i];
	}
	
	public Double getColMarginVal(int j) {
		return this.colMarginVals[j];
	}
	
	public Integer lookup(Double rval, Double cval) {
		int i = nrows -1 ;
		while ((i > 0) && (rval < this.rowMarginVals[i]) )
			{ i--; }
		
		int j = ncols - 1 ;
		while ((j > 0) && (cval < this.colMarginVals[j]) )
		{ j--; }
		
		return this.arr[i][j];
		
		// Since we are doing lookup nearest left margin value the first 
		// margin value is irrelevant  when looking up all values les than 
		// the second margin value will be indexed as first 
		
	}
}
