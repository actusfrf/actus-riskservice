package org.actus.risksrv3.brm20;

import java.util.ArrayList;
import java.util.Arrays;

public class Array2DintTest {

	public static void main(String[] args) {
		int nrows = 3;
		int ncols = 4;
		// create the margin data 
		ArrayList<Double> rowMargins = new ArrayList<>(Arrays.asList ( 0.0, 0.5, 1.0 )) ; 
		ArrayList<Double> colMargins = new ArrayList<>(Arrays.asList(0.0, 0.33, 0.67, 1.0));
		// create the lookup table values 
		ArrayList<Integer> inNums = new ArrayList<Integer>();
		for (int i = 0; i < nrows * ncols; i++ ) {
			inNums.add(100 + i);
		}
		// System.out.println( "Length inNums = "+ inNums.size() + " value of inNums.get(0) = " + inNums.get(0));
		// System.out.println( "rowMargins [0] = " + rowMargins.get(0) + "rowMargins[1]= "+ rowMargins.get(1));
			
		// create  and initialize the 2Dint lookup table 
		Array2Dint array2Dint = new Array2Dint(rowMargins, colMargins, inNums);
		// test row margins stored 
		// System.out.println("Stored rowMarginVals[1] =  " + array2Dint.getRowMarginVal(1));
		
		System.out.println ("arr2Dint.get(0,0) = "+ array2Dint.get(0, 0) + "; array2Dint.get(0,1) = "+ array2Dint.get(0,1));
		System.out.println ("arr2Dint.get(1,0) = "+ array2Dint.get(1, 0) + "; array2Dint.get(1,1) = "+ array2Dint.get(1,1));
		
		System.out.println ("rowMarginVals[0] = " + array2Dint.getRowMarginVal(0) + "; rowMarginVals[1] = "+ array2Dint.getRowMarginVal(1));
		System.out.println ("colMarginVals[0] = " + array2Dint.getColMarginVal(0) + "; colMarginVals[1] = "+ array2Dint.getColMarginVal(1));
		
		// test with some lookup 
		System.out.println(" Lookup of (0.0, 0.0) returns: "+ array2Dint.lookup(0.0,0.0));
		System.out.println(" Lookup of (0.0, 0.34) returns: "+ array2Dint.lookup(0.0,0.34));
		System.out.println(" Lookup of (0.5, 0.0) returns: "+ array2Dint.lookup(0.5, 0.0));
		System.out.println(" Lookup of (1.1, 0.0) returns: "+ array2Dint.lookup(1.1, 0.0));
		System.out.println(" Lookup of (1.1, 0.34) returns: "+ array2Dint.lookup(1.1, 0.34));
		System.out.println(" Lookup of (-1.1, 0.34) returns: "+ array2Dint.lookup(-1.1, 0.34));
		System.out.println(" Lookup of (-0.1, 0.34) returns: "+ array2Dint.lookup(-0.1, 0.34));


	}

}
