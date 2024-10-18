package org.actus.risksrv3.core.conventions.daycount;

import java.time.LocalDateTime;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

/*
 * DayCountConvention 28/336
 * 
 */
public class TwentyEightThreeThirtySix implements DayCountConventionProvider {

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
		d1 = (d1 == startTime.with(lastDayOfMonth()).getDayOfMonth()) ? 28 : d1;
		int d2 = endTime.getDayOfMonth();
		d2 = (!(endTime.equals(maturityDate) || endTime.getMonth().getValue() == 2)
				&& d2 == startTime.with(lastDayOfMonth()).getDayOfMonth()) ? 28 : d2 >= 28 ? 28 : d2;
		double delD = d2 - d1;
		double delM = endTime.getMonth().getValue() - startTime.getMonth().getValue();
		double delY = endTime.getYear() - startTime.getYear();
		return ((336.0 * delY + 28.0 * delM + delD));
	}

	@Override
	public double dayCountFraction(LocalDateTime startTime, LocalDateTime endTime) {
		return (this.dayCount(startTime, endTime) / 336);
	}

}