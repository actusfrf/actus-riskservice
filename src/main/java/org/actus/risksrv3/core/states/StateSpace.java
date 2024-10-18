/*
 * Copyright (C) 2016 - present by ACTUS Financial Research Foundation
 *
 * Please see distribution for license.
 */
package org.actus.risksrv3.core.states;

import org.actus.types.ContractPerformance;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * A data structure representing various states of a {@link ContractType}
 * <p>
 * The states of a {@link ContractType} contain important information used when evaluating 
 * {@link PayOffFunction}. Further, states themselves contain atomic analytical elements 
 * such as the nominal value or accrued interest for a lending instrument. On the other hand,
 * states are updated throughout an instrument's lifetime through evaluation of {@link StateTransitionFunction}
 * in the various {@link ContractEvent}s.
 */
public final class StateSpace {
    public double               accruedInterest;
    public double               accruedInterest2;
    public ContractPerformance  contractPerformance = ContractPerformance.PF;
    public double               exerciseAmount;
    public LocalDateTime        exerciseDate;
    public double               feeAccrued;
    public double               interestCalculationBaseAmount;
    public double               interestScalingMultiplier;
    public double               nextPrincipalRedemptionPayment;
    public double               nominalInterestRate;
    public double               nominalInterestRate2;
    public LocalDateTime        nonPerformingDate;
    public double               notionalPrincipal;
    public double               notionalPrincipal2;
    public double               notionalScalingMultiplier;
    public LocalDateTime        statusDate;
    public LocalDateTime        maturityDate;
    public LocalDateTime        terminationDate;
    public double               lastInterestPeriod;
    public boolean              boundaryCrossedFlag;
    public boolean              boundaryMonitoringFlag;
    public boolean              boundaryLeg1ActiveFlag;
    public boolean              boundaryLeg2ActiveFlag;

    /**
     * Returns a String-representation of all analytical elements
     * @return a single String containing all analytical elements
     */
    @Override
    public String toString(){
        StringJoiner joiner = new StringJoiner(" ");

        joiner.add(Double.toString(accruedInterest))
                .add(Double.toString(accruedInterest2))
                .add(contractPerformance.toString())
                .add(Double.toString(exerciseAmount))
                //add empty string if not set
                .add(Objects.isNull(exerciseDate) ? "" : exerciseDate.toString())
                .add(Double.toString(feeAccrued))
                .add(Double.toString(interestCalculationBaseAmount))
                .add(Double.toString(interestScalingMultiplier))
                .add(Double.toString(nextPrincipalRedemptionPayment))
                .add(Double.toString(nominalInterestRate))
                .add(Double.toString(nominalInterestRate2))
                //add empty string if not set
                .add(Objects.isNull(nonPerformingDate) ? "" : nonPerformingDate.toString())
                .add(Double.toString(notionalPrincipal))
                .add(Double.toString(notionalPrincipal2))
                .add(Double.toString(notionalScalingMultiplier))
                //add empty string if not set
                .add(Objects.isNull(statusDate) ? "" : statusDate.toString())
                .add(Objects.isNull(terminationDate) ? "" : terminationDate.toString())
                .add(Objects.isNull(maturityDate) ? "" : maturityDate.toString())
        ;

        return joiner.toString();
    }
    //creates copy of StateSpace
    public static StateSpace copyStateSpace(StateSpace original){
        StateSpace copy = new StateSpace();
        copy.accruedInterest = original.accruedInterest;
        copy.accruedInterest2 = original.accruedInterest2;
        copy.contractPerformance = original.contractPerformance;
        copy.exerciseAmount = original.exerciseAmount;
        copy.exerciseDate = original.exerciseDate;
        copy.feeAccrued = original.feeAccrued;
        copy.interestCalculationBaseAmount = original.interestCalculationBaseAmount;
        copy.interestScalingMultiplier = original.interestScalingMultiplier;
        copy.nextPrincipalRedemptionPayment = original.nextPrincipalRedemptionPayment;
        copy.nominalInterestRate = original.nominalInterestRate;
        copy.nominalInterestRate2 = original.nominalInterestRate2;
        copy.nonPerformingDate = original.nonPerformingDate;
        copy.notionalPrincipal = original.notionalPrincipal;
        copy.notionalPrincipal2 = original.notionalPrincipal2;
        copy.notionalScalingMultiplier = original.notionalScalingMultiplier;
        copy.statusDate = original.statusDate;
        copy.maturityDate = original.maturityDate;
        copy.terminationDate = original.terminationDate;
        copy.boundaryCrossedFlag = original.boundaryCrossedFlag;
        copy.boundaryMonitoringFlag = original.boundaryMonitoringFlag;
        copy.boundaryLeg1ActiveFlag = original.boundaryLeg1ActiveFlag;
        copy.boundaryLeg2ActiveFlag = original.boundaryLeg2ActiveFlag;
        return copy;

    }
}
