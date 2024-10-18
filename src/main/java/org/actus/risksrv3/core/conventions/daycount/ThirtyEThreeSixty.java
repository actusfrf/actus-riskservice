/*
 * Copyright (C) 2016 - present by ACTUS Financial Research Foundation
 *
 * Please see distribution for license.
 */
package org.actus.risksrv3.core.conventions.daycount;

import java.time.LocalDateTime;

/**
 * ISDA 30E/360 day count convention
 * 
 * @see http://www2.isda.org
 */
public class ThirtyEThreeSixty implements DayCountConventionProvider {
    
	@Override
	public double dayCount(LocalDateTime startTime, LocalDateTime endTime) {
		double d1 = (startTime.getDayOfMonth() == 31.0) ? 30.0 : startTime
				.getDayOfMonth();
		double d2 = (endTime.getDayOfMonth() == 31.0) ? 30.0 : endTime
				.getDayOfMonth();
		
		double delD = d2 - d1;
		double delM = endTime.getMonth().getValue()
				- startTime.getMonth().getValue();
		double delY = endTime.getYear() - startTime.getYear();

		return (360.0 * delY + 30.0 * delM + delD);
	}
    
	@Override
	public double dayCountFraction(LocalDateTime startTime, LocalDateTime endTime) {
		return (this.dayCount(startTime, endTime) / 360.0);
	}
}

