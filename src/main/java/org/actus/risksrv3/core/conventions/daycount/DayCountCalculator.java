/*
 * Copyright (C) 2016 - present by ACTUS Financial Research Foundation
 *
 * Please see distribution for license.
 */
package org.actus.risksrv3.core.conventions.daycount;

import org.actus.time.TimeAdjuster;
import org.actus.time.calendar.BusinessDayCalendarProvider;
import org.actus.risksrv3.core.util.StringUtils;

import java.time.LocalDateTime;

/**
 * Component for the calculation of time-periods between two time-instances
 * according to a {@link DayCountConventionProvider}
 */
public class DayCountCalculator {
    private DayCountConventionProvider convention;

    /**
     * Generic Constructor
     *
     * @param convention the {@link DayCountConventionProvider}
     * @return
     */
    public DayCountCalculator(DayCountConventionProvider convention) {
        this.convention = convention;
    }

    /**
     * Convenience Constructor
     * 
     * @param convention the {@link DayCountConventionProvider}
     * @param calendar the {@link BusinessDayCalendarProvider} to be used in case of {@code convention="B/252"}
     * @return
     */
    public DayCountCalculator(String convention, BusinessDayCalendarProvider calendar) {
        switch (convention) {
            case StringUtils.DayCountConvention_30E360:
                this.convention = new ThirtyEThreeSixty();
                break;
            case StringUtils.DayCountConvention_30E360ISDA:
                this.convention = new ThirtyEThreeSixtyISDA();
                break;
            case StringUtils.DayCountConvention_A360:
                this.convention = new ActualThreeSixty();
                break;
            case StringUtils.DayCountConvention_A365:
                this.convention = new ActualThreeSixtyFiveFixed();
                break;
            case StringUtils.DayCountConvention_AAISDA:
                this.convention = new ActualActualISDA();
                break;
            case StringUtils.DayCountConvention_B252:
                this.convention = new BusinessTwoFiftyTwo();
                // TODO: ((BusinessTwoFiftyTwo) this.convention).setCalendar(calendar);
                break;
            case StringUtils.DayCountConvention_A336:
            	this.convention = new ActualThreeThirtySix();
                break;
            case StringUtils.DayCountConvention_28336:
            	this.convention = new TwentyEightThreeThirtySix();
            	break;
        }
    }

    /**
     * Compute the number of days as a fraction of total number of days in the entire
     * reference year between two time-instances
     * <p>
     * Note, {@param startTime} and {@param endTime} times are rounded to the "full hour"
     * before calculating the day count fraction.
     * That is, timestamps with minutes in (0,29) are floored to the current full hour
     * while timestamps with minutes in (30,59) are ceiled to the next full hour.
     * 
     * @param startTime the start of the time period
     * @param endTime the end of the time period
     * @return the number of days as a fraction of total number of days in the entire
     * reference year between startTime and endTime
     */
    public double dayCountFraction(LocalDateTime startTime, LocalDateTime endTime) {
        return convention.dayCountFraction(TimeAdjuster.toFullHours(startTime), TimeAdjuster.toFullHours(endTime));
    }
}
