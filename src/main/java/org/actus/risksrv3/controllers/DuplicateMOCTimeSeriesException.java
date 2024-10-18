package org.actus.risksrv3.controllers;

public class DuplicateMOCTimeSeriesException extends RuntimeException{
	DuplicateMOCTimeSeriesException(String moc) {
	    super("Multiple projections for marketObjectCode " + moc);
	  }
}
