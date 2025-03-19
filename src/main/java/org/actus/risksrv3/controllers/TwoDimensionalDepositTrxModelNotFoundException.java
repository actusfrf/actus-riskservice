package org.actus.risksrv3.controllers;

public class TwoDimensionalDepositTrxModelNotFoundException extends RuntimeException {
	TwoDimensionalDepositTrxModelNotFoundException(String id) {
	    super("Could not find TwoDimensionalDepositTrxModel with rfid =  " + id);
	  }

}
