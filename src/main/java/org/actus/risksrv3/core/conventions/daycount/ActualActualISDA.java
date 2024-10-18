/*
 * Copyright (C) 2016 - present by ACTUS Financial Research Foundation
 *
 * Please see distribution for license.
 */
package org.actus.risksrv3.core.conventions.daycount;

import java.time.Year;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * ISDA A/A day count convention
 * 
 * @see http://www2.isda.org
 */
public final class ActualActualISDA implements DayCountConventionProvider {

	@Override
	public double dayCount(LocalDateTime startTime, LocalDateTime endTime) {
		return ChronoUnit.DAYS.between(startTime, endTime);
	}

	@Override
	public double dayCountFraction(LocalDateTime startTime,
			LocalDateTime endTime) {
		int y1 = startTime.getYear();
		int y2 = endTime.getYear();
        
		if (y1 == y2) {
			double basis = (Year.isLeap(y1)) ? 366.0 : 365.0;
			return (ChronoUnit.DAYS.between(startTime, endTime) / basis);
		}
		
		double firstBasis = (Year.isLeap(y1)) ? 366.0 : 365.0;
		double secondBasis = (Year.isLeap(y2)) ? 366.0 : 365.0;
		return ((ChronoUnit.DAYS.between(startTime, LocalDateTime.of(y1 + 1, 1, 1, 0, 0, 0, 0))) / firstBasis
				+ (ChronoUnit.DAYS.between(LocalDateTime.of(y2, 1, 1, 0, 0, 0, 0), endTime)) / secondBasis
				+ y2 - y1 - 1);
	}
}
