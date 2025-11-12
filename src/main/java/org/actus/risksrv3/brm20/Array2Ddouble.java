package org.actus.risksrv3.brm20;

import java.util.ArrayList;

public class Array2Ddouble {
	private
		int nrows ;
		int ncols ;
		Double [][] arr;
		Double[] rowMarginVals;
		Double[] colMarginVals;
	public Array2Ddouble() {
	}
	public Array2Ddouble( ArrayList<Double> rowMargins,
						  ArrayList<Double> colMargins, 
						  ArrayList<Double> values) {
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
		this.arr = new Double [nrows][ncols];

		System.out.println( "Arr2Ddouble: Length values = "+ values.size() + " value of values.get(0) = " + values.get(0));
		int t = 0;
		// Input for each row
		for (int i = 0; i < this.nrows && t< values.size(); i++) {
			// All rows will have the same column count ncols
			this.arr[i] = new Double[this.ncols];
			for (int j = 0; j < this.ncols ; j++) {
				this.arr[i][j] = values.get(t);
				t++;
			}
		}
	}
		public Double get (int i, int j) {
			return this.arr[i][j];
		}
		
	public Double getRowMarginVal(int i) {
		return this.rowMarginVals[i];
		}
		
	public Double getColMarginVal(int j) {
		return this.colMarginVals[j];
		}
		
	public Double lookup(Double rval, Double cval) {
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
