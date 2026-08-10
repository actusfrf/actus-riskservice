package org.actus.risksrv3.controllers;

public class CreditRiskModelNotFoundException extends RuntimeException {
	CreditRiskModelNotFoundException(String id) {
		super("Could not find CreditRiskModel with rfid =  " + id);
	}
}
