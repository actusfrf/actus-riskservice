package org.actus.risksrv3.time;
// CalloutScheduleFactory is a utility class with static  methods 
// callable from anywhere in risksrv3 - assist callut schedule generation
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Set;
import java.util.HashSet;

import org.actus.AttributeConversionException;

public final class CalloutScheduleFactory {
	private CalloutScheduleFactory() {
	}
	public static Set<LocalDateTime> createCalloutSchedule(LocalDateTime startTime, LocalDateTime endTime, 
													Period period) throws AttributeConversionException {
		Set<LocalDateTime> calloutTimes = new HashSet<>();
		Period increment = period;
		Integer counter = 1;  
		LocalDateTime newTime = startTime.plus(increment);
		while(newTime.isBefore(endTime)) {
			calloutTimes.add(newTime);
			counter++;
			increment= period.multipliedBy(counter);
			newTime = startTime.plus(increment);
		}
		return calloutTimes;
	}
}
