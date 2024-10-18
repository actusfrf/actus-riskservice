package org.actus.risksrv3.controllers;

public class TwoDimensionalPrepaymentModelNotFoundException extends RuntimeException{
	TwoDimensionalPrepaymentModelNotFoundException(String id) {
	    super("Could not find TwoDimensionalPrepaymentModel with rfid =  " + id);
	  }
}
