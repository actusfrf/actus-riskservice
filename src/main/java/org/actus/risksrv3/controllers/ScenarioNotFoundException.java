package org.actus.risksrv3.controllers;

public class ScenarioNotFoundException extends RuntimeException{
	ScenarioNotFoundException(String id) {
	    super("Could not find scenario " + id);
	  }
}
