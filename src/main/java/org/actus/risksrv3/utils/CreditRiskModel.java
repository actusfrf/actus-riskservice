package org.actus.risksrv3.utils;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Objects;

import org.actus.risksrv3.core.attributes.ContractModel;
import org.actus.risksrv3.core.states.StateSpace;
import org.actus.risksrv3.models.CalloutData;
import org.actus.risksrv3.models.CreditRiskModelData;
import org.actus.risksrv3.time.CalloutScheduleFactory;
import org.springframework.data.annotation.Id;

public class CreditRiskModel implements BehaviorRiskModelProvider {
	
	public static final String CALLOUT_TYPE = "CDR";  // Credit Default riskMultiplicativeReductionDelta
	private String riskFactorId;
	private String calloutPeriod;     	// callout periodicity eg "P9M"
	private Double defaultProbability;	// probability of default on each callout 
	private String scenarioId;  		// want different default behavior per scenario
	private String statusDate;			// use to set periodic callout schedule
	private String maturityDate;        // use to set periodic callout behavior 
	private String contractId; 			// want different behavior per contract 

	public CreditRiskModel () {
		}
	
	public CreditRiskModel ( String riskFactorId, String calloutPeriod, Double defaultProbability) {
		this.riskFactorId  = riskFactorId;
		this.calloutPeriod = calloutPeriod;
		this.defaultProbability = defaultProbability;
	}
// this is the constructor which is used - riskFactorIdCreditRiskModelData as input 	
	public CreditRiskModel ( String riskFactorId, CreditRiskModelData crmdd, String scenarioId) {
		this.riskFactorId  = crmdd.getRiskFactorId();
		this.calloutPeriod = crmdd.getCalloutPeriod();
		this.defaultProbability = crmdd.getDefaultProbability();
		this.scenarioId = scenarioId;
	}
	
	public Set<String> keys() {
		return Set.of(this.riskFactorId);
	}
	
	public double stateAt(String id, LocalDateTime time, StateSpace states) {
		// stateAt will return 1.0 if default 0.0 if no default use: stateAt > 0.5 for boolean default
		Integer ih  = Objects.hashCode(time.toString()+ this.scenarioId + this.contractId ) ;
 	    double result =  (ih % 101) / 101.0 ;   		
		return result ;
	}
	
	public List<CalloutData> contractStart (ContractModel contract) {
		// create an events list 
		System.out.println("**** fnp501: contractStart rfid = "+ this.riskFactorId); 
		// pickup the status and maturity dates from contract
		this.statusDate = contract.getAs("statusDate");
		this.maturityDate = contract.getAs("maturityDate"); 
		this.contractId  = contract.getAs("contractID");  // now all set for behaviorStateAt()
		
		LocalDateTime startDate = LocalDateTime.parse(this.statusDate);
		LocalDateTime endDate = LocalDateTime.parse(this.maturityDate);		
		Period period = Period.parse(this.calloutPeriod);
		
		//  String s = "Response from cr model contract start";
		//  String startIso = "2026-08-04T00:00:00";
		//  String endIso = "2028-12-31T00:00:00";
		//  String  cycle = "P6M";
		//  LocalDateTime startDate = LocalDateTime.parse(startIso);
		//  LocalDateTime endDate = LocalDateTime.parse(endIso);
		//  Period period = Period.parse(cycle);
		//  s += "start = " + startDate + " end = "+ endDate + "period = "+ period + "\n";
		
		Set<LocalDateTime>  calloutDates = CalloutScheduleFactory.createCalloutSchedule(startDate, endDate, period);
 
		List<CalloutData> crclds = new ArrayList<CalloutData>();
		for (LocalDateTime calloutDate : calloutDates) {
			     String datestring = calloutDate.toString();
				 CalloutData crcd = new  CalloutData(this.riskFactorId, datestring, CreditRiskModel.CALLOUT_TYPE);
				 crclds.add(crcd);
		}
		return crclds;	
	}	
}