/*
 * Copyright (C) 2016 - present by ACTUS Financial Research Foundation
 *
 * Please see distribution for license.
 */
package org.actus.risksrv3.core.conventions.daycount;

import org.actus.time.calendar.BusinessDayCalendarProvider;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Business-252 day count convention
 * <p>
 * This day count convention relies on a {@link BusinessDayCalendarProvider} which necessarily
 * has to be provided in order to being able to computing day counts. 
 * 
 * @see
 */
public final class BusinessTwoFiftyTwo implements DayCountConventionProvider {
	private BusinessDayCalendarProvider calendar;

	public void setCalendar(BusinessDayCalendarProvider calendar) {
		this.calendar = calendar;
	}
	
	@Override
	public double dayCount(LocalDateTime startTime, LocalDateTime endTime) {
		LocalDateTime date = startTime;
		int daysCount = 0;
		for (int i = 0; i < ChronoUnit.DAYS.between(startTime, endTime); i++) {
			if (calendar.isBusinessDay(date)) {
				daysCount++;
			}
			date = date.plusDays(1);
		}
		return daysCount;
	}
	
	@Override
	public double dayCountFraction(LocalDateTime startTime,
			LocalDateTime endTime) {
		return this.dayCount(startTime, endTime) / 252.0;
	}
}
