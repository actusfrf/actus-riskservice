package org.actus.risksrv3.utils;

import java.time.LocalDateTime;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.actus.risksrv3.core.conventions.daycount.DayCountCalculator;
import org.actus.risksrv3.core.states.StateSpace;
import org.actus.risksrv3.models.TwoDimensionalPrepaymentModelData;
import org.actus.risksrv3.models.CalloutData;

import org.actus.risksrv3.core.attributes.ContractModel;

public class TwoDimensionalPrepaymentModel implements BehaviorRiskModelProvider {
	String referenceRate;
	String riskFactorId;
	TimeSeries<Double,TimeSeries<Double,Double>> surface;
	MarketRiskModelProvider marketModel;
	DayCountCalculator dayCount;
	Map<String,Object> terms;
	LocalDateTime initialExchangeDate;
	List<String> prepaymentEventTimes;
	
	public TwoDimensionalPrepaymentModel() {
	}

	public TwoDimensionalPrepaymentModel 
	         (String riskFactorId, TwoDimensionalPrepaymentModelData data, MultiMarketRiskModel marketModel) {
		this.marketModel = marketModel;
		this.riskFactorId = riskFactorId;
		this.referenceRate = data.getReferenceRateId();
		this.dayCount = new DayCountCalculator("A360", null);
		this.surface = new TimeSeries<Double,TimeSeries<Double,Double>>();
		List<Double> dimension1Margins = data.getSurface().getMargins().get(0).getValues();
		Double[] dimension2Margins = data.getSurface().getMargins().get(1).getValues().stream().map(obs -> obs).toArray(Double[]::new);
		List<List<Double>> values = data.getSurface().getData();
		for(int i=0; i<values.size(); i++) {
			TimeSeries<Double,Double> dimensionSeries = new TimeSeries<Double,Double>();
			Double[] dimensionValues = values.get(i).stream().map(obs -> obs).toArray(Double[]::new);
			dimensionSeries.of(dimension2Margins,dimensionValues);
			surface.put(dimension1Margins.get(i),dimensionSeries);
		}
		this.prepaymentEventTimes = data.getPrepaymentEventTimes();
	}
	
	public Set<String> keys() {
		return Set.of(this.riskFactorId);
	}
	

	public double stateAt(String id, StateSpace states) {
System.out.println("****fnp009 TwoDimensionalPrepaymentModel stateAt() entered");  // fnp diagnostic jan 2023 
        LocalDateTime time = states.statusDate;
		double spread = states.nominalInterestRate - marketModel.stateAt(this.referenceRate,time);
                if ( spread <= -0.045 ) { spread = -0.045 ; }  
System.out.println("****fnp011  spread=<" + String.valueOf(spread) + ">; starting age computation");   	// fnp diagnostic jan 2023 	
	    	double age = dayCount.dayCountFraction(this.initialExchangeDate,states.statusDate);
System.out.println("****fnp012 age=<" + String.valueOf(age) + ">; starting surface lookup ");       	// fnp diagnostic jan 2023  		
		return surface.getValueFor(spread,1).getValueFor(age,1);
	}
	
	public List<CalloutData> contractStart (ContractModel contract) {
		// save initialExchangeDate of this contract 
		this.initialExchangeDate = contract.getAs("initialExchangeDate");
		// create an events list 
		List<CalloutData> cllds = new ArrayList<CalloutData>();
		for (String ppevd : this.prepaymentEventTimes) {
				 CalloutData clld = new  CalloutData(this.riskFactorId,ppevd);
				 cllds.add(clld);
			 }
		return cllds;	
	}	
	
}
