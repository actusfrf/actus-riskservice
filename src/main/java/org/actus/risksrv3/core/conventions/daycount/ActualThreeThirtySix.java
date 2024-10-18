package org.actus.risksrv3.core.conventions.daycount;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * A/336 day count convention
 * 
 * @see
 */
public class ActualThreeThirtySix implements DayCountConventionProvider {

	@Override
	public double dayCount(LocalDateTime startTime, LocalDateTime endTime) {
		return ChronoUnit.DAYS.between(startTime, endTime);
	}

	@Override
	public double dayCountFraction(LocalDateTime startTime,
			LocalDateTime endTime) {
		return dayCount(startTime, endTime) / 336.0;
	}

}
