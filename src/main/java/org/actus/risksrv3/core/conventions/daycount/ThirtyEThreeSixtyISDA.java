/*
 * Copyright (C) 2016 - present by ACTUS Financial Research Foundation
 *
 * Please see distribution for license.
 */
package org.actus.risksrv3.core.conventions.daycount;

import java.time.LocalDateTime;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

/**
 * ISDA 30E/360-ISDA day count convention
 * <p>
 * This day count convention relies on three times; start and end time
 * of the day count period under consideration and maturity date of 
 * the financial instrument under consideration. The latter necessarily
 * has to be provided in order to compute day counts. 
 * 
 * @see http://www2.isda.org
 */
public class ThirtyEThreeSixtyISDA implements DayCountConventionProvider {
	private LocalDateTime maturityDate;
    
    /**
     * Set the maturity date considered when computing day counts
     * 
     * @param maturityDate the maturity date
     * @return
     */
	public void maturityDate(LocalDateTime maturityDate) {
		this.maturityDate = maturityDate;
	}
	
	@Override
	public double dayCount(LocalDateTime startTime, LocalDateTime endTime) {
		int d1 = startTime.getDayOfMonth();
		d1 = (d1 == startTime.with(lastDayOfMonth()).getDayOfMonth()) ? 
		30 : d1;
		int d2 = endTime.getDayOfMonth();
		d2 = (!(endTime.equals(maturityDate) && endTime.getMonth().getValue()==2) 
				&& d2 == endTime.with(lastDayOfMonth()).getDayOfMonth()) ? 30 : d2;
		double delD = d2 - d1;
		double delM = endTime.getMonth().getValue()
				- startTime.getMonth().getValue();
		double delY = endTime.getYear() - startTime.getYear();

		return ((360.0 * delY + 30.0 * delM + delD));
	}


	@Override
	public double dayCountFraction(LocalDateTime startTime,
			LocalDateTime endTime) {
		return (this.dayCount(startTime, endTime) / 360.0);
	}
}

