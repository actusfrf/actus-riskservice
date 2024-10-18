package org.actus.risksrv3.controllers;

public class ReferenceIndexNotFoundException extends RuntimeException{
	       ReferenceIndexNotFoundException(String id) {
	          super("Could not find reference index with id= " + id);
	  }
}

