package org.actus.risksrv3.controllers;

public class RiskModelNotFoundException extends RuntimeException {
		RiskModelNotFoundException(String id) {
		    super("Could not find risk model with rfid= " + id + " in Risk service model store");
		  }

}
