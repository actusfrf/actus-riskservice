package org.actus.risksrv3.utils;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.actus.risksrv3.core.attributes.ContractModel;
import org.actus.risksrv3.core.states.StateSpace;
import org.actus.risksrv3.models.CalloutData;
import org.actus.risksrv3.models.TwoDimensionalDepositTrxModelData;

public class TwoDimensionalDepositTrxModel implements BehaviorRiskModelProvider  {
	String riskFactorId;
	TimeSeries<Double,TimeSeries<Double,Double>> surface;
	HashMap<String,Double> dimension1Index, dimension2Index;
	List<String> depositTrxEventTimes;
	String contractID;
	
	public TwoDimensionalDepositTrxModel() {
    }
	public TwoDimensionalDepositTrxModel(String riskFactorId, TwoDimensionalDepositTrxModelData data ) {
        this.riskFactorId = riskFactorId;
        this.surface = new TimeSeries<Double,TimeSeries<Double,Double>>();
        this.dimension1Index = new HashMap<String,Double>();
        this.dimension2Index = new HashMap<String,Double>();
        this.depositTrxEventTimes = data.getDepositTrxEventTimes();

        // Initialize arrays of labels first
        String[] dim1Labels = data.getLabelSurface().getLabelMargins().get(0).getValues().stream().map(obs->obs).toArray(String[]::new);
        String[] dim2Labels = data.getLabelSurface().getLabelMargins().get(1).getValues().stream().map(obs->obs).toArray(String[]::new);
        // For each dimension populate hash map and build list/array of numeric lookup margin value
        List<Double> dimension1Margins = new ArrayList<>();
        for(int i=0; i < dim1Labels.length ; i++ ) {
           this.dimension1Index.put(dim1Labels[i],Double.valueOf(i));
           dimension1Margins.add(Double.valueOf(i));
           }
        Double[ ] dimension2Margins = new Double[dim2Labels.length] ;
        for(int i=0; i < dim2Labels.length ; i++ ) {
           this.dimension2Index.put(dim2Labels[i],Double.valueOf(i));
           dimension2Margins[i] = Double.valueOf(i) ;
           }
        System.out.println("****fnp529 lookup date(0)= <" + dim2Labels[0] + "> .");   // fnp diagnostic mar 20253

		//      List<Double> dimension1Margins = data.getSurface().getMargins().get(0).getValues();
		//      Double[] dimension2Margins = data.getSurface().getMargins().get(1).getValues().stream().map(obs -> obs).toArray(Double[]::new);

        List<List<Double>> values = data.getLabelSurface().getData();
        for(int i=0; i < values.size(); i++) {
                TimeSeries<Double,Double> dimensionSeries = new TimeSeries<Double,Double>();
                Double[] dimensionValues = values.get(i).stream().map(obs -> obs).toArray(Double[]::new);
                dimensionSeries.of(dimension2Margins,dimensionValues);
                surface.put(dimension1Margins.get(i),dimensionSeries);
        		}
		}

	public Set<String> keys() {
        return Set.of(this.riskFactorId);
		}

    public double stateAt(String id, StateSpace states) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    LocalDateTime time = states.statusDate;
    String timeS = time.format(formatter);
    System.out.println("****fnp520 TwoDimensionalDepositTrxModel stateAt() entered");       // fnp diagnostic jan 2023

    //System.out.println("****fnp021 contractID = <" + contractID + "> time = <"+ String.valueOf(time)+">" ); // fnp diagnostic jan 2023

    System.out.println("****fnp521 contractID = <" + this.contractID + "> time = <" + timeS + ">" ); // fnp diagnostic jan 2023
    Double dim1x = this.dimension1Index.get(this.contractID);
    System.out.println("****fnp522 dim1x = <" + String.valueOf(dim1x) + ">");       // fnp diagnostic jan 2023
    Double dim2x = this.dimension2Index.get(timeS);

    //   Double dim2x = this.dimension2Index.get(String.valueOf(time));

    System.out.println("****fnp523 dim2x = <" + String.valueOf(dim2x) + ">");       // fnp diagnostic jan 2023
    Double trxAmt = surface.getValueFor(dim1x,1).getValueFor(dim2x,1);
    System.out.println("****fnp024 trxAmt = <" + String.valueOf(trxAmt) + ">");       // fnp diagnostic jan 2023
    return trxAmt;  // surface.getValueFor(spread,1).getValueFor(age,1);
    }

    public List<CalloutData> contractStart (ContractModel contract) {
		// save initialExchangeDate of this contract 
		this.contractID  = contract.getAs("contractID");
		// create an events list 
		List<CalloutData> cllds = new ArrayList<CalloutData>();
		for (String dtevd : this.depositTrxEventTimes) {
				 CalloutData clld = new  CalloutData(this.riskFactorId,dtevd);
				 cllds.add(clld);
			 }
		return cllds;	
	}	
	
}
