/*
 * Copyright (C) 2016 - present by ACTUS Financial Research Foundation
 *
 * Please see distribution for license.
 */
package org.actus.risksrv3.core.conventions.daycount;

import java.time.LocalDateTime;

/**
 * Component that represents a certain day count convention
 * <p>
 * Various conventions of how to account for different month and year lengths 
 * (in terms of number of days in a single month or year) exist. This component
 * represents a specific convention.
 * <p>
 * Make sure, new conventions implement this interface when adding to the library.
 */
public interface DayCountConventionProvider {
	
	/**
	 * Compute the number of days between two time-instances
	 * 
	 * @param startTime the start of the time period
	 * @param endTime the end of the time period
	 * @return the number of days between startTime and endTime
	 */
	public double dayCount(LocalDateTime startTime, LocalDateTime endTime);

	/**
	 * Compute the number of days as a fraction of total number of days in the entire
	 * reference year between two time-instances
	 * 
	 * @param startTime the start of the time period
	 * @param endTime the end of the time period
	 * @return the number of days as a fraction of total number of days in the entire
	 * reference year between startTime and endTime
	 */
	public double dayCountFraction(LocalDateTime startTime,
			LocalDateTime endTime);
}

