package org.actus.risksrv3.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Optional;

import org.actus.risksrv3.core.attributes.ContractModel;
import org.actus.risksrv3.core.states.StateSpace;
import org.actus.risksrv3.models.BatchStartInput;
import org.actus.risksrv3.models.BehaviorStateAtInput;
import org.actus.risksrv3.models.CalloutData;
import org.actus.risksrv3.models.MarketData;
import org.actus.risksrv3.models.OldScenario;
import org.actus.risksrv3.models.Scenario;
import org.actus.risksrv3.models.TwoDimensionalPrepaymentModelData;
import org.actus.risksrv3.models.TwoDimensionalDepositTrxModelData;
import org.actus.risksrv3.models.ReferenceIndex;
import org.actus.risksrv3.models.RiskFactorDescriptor;
import org.actus.risksrv3.models.ScenarioDescriptor;
import org.actus.risksrv3.models.StateAtInput;
import org.actus.risksrv3.repository.ReferenceIndexStore;
import org.actus.risksrv3.repository.ScenarioStore;
import org.actus.risksrv3.repository.TwoDimensionalPrepaymentModelStore;
import org.actus.risksrv3.repository.TwoDimensionalDepositTrxModelStore;
import org.actus.risksrv3.utils.MultiBehaviorRiskModel;
import org.actus.risksrv3.utils.MultiMarketRiskModel;
import org.actus.risksrv3.utils.TimeSeriesModel;
import org.actus.risksrv3.utils.TwoDimensionalPrepaymentModel;
import org.actus.risksrv3.utils.TwoDimensionalDepositTrxModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//actus-riskservice version of the RiskObservation processing 

//Annotation 
@RestController
public class RiskObservationHandler {
	@Autowired
	private ReferenceIndexStore referenceIndexStore;
	@Autowired
	private ScenarioStore scenarioStore;
	@Autowired
	private TwoDimensionalPrepaymentModelStore twoDimensionalPrepaymentModelStore;
	@Autowired
	private TwoDimensionalDepositTrxModelStore twoDimensionalDepositTrxModelStore;

// local state attributes and objects 
// these are the state variables used for processing simulation requests 
	private String					currentScenarioID = null;
	private MultiMarketRiskModel    currentMarketModel;
	private MultiBehaviorRiskModel 	currentBehaviorModel;
	private HashSet<String>	        currentActivatedModels = new HashSet<String>();
	
// handler for /rf2/eventsBatch callout processing 	
	@GetMapping("/marketData/{scid}")
	MarketData  doMarketData (@PathVariable String scid) {
  	 	System.out.println("**** fnp200 entered /marketData/{scid} ; scid = " + scid);
		Optional<Scenario> oscn = this.scenarioStore.findById(scid); 
		List<ReferenceIndex>  rfxl = new ArrayList< ReferenceIndex> ();
		Scenario scn;
		if (oscn.isPresent()) 
			{  System.out.println("**** fnp201 found scenario" );
			   scn = oscn.get();
			   }
		else
			{ throw new ScenarioNotFoundException(scid);}
		List<RiskFactorDescriptor> rfdl = scn.getRiskFactorDescriptors();
		Set<String>  mocl  = new HashSet<> ();
		for (RiskFactorDescriptor rfd : rfdl ) {
			if (rfd.getRiskFactorType().equals("ReferenceIndex") ) {
				Optional<ReferenceIndex> orfx =  this.referenceIndexStore.findById(rfd.getRiskFactorID());
				ReferenceIndex rfx;
				if (orfx.isPresent()) 
					{ rfx = orfx.get(); 
					System.out.println("**** fnp202 found rfx ; rfxid = " + rfx.getRiskFactorID());
					}
				else
					{ throw new ReferenceIndexNotFoundException(rfd.getRiskFactorID());}
				rfxl.add(rfx);
				String moc = rfx.getMarketObjectCode();
				if (! mocl.contains(moc)) 
					{ mocl.add(moc); }
				else throw new DuplicateMOCTimeSeriesException(moc);
				}
			}
		 MarketData marketData = new MarketData(rfxl);
		 System.out.println("**** fnp203 returning marketData " + marketData.toString());
		 return  marketData;
	  }
	
// handlers for /rf2/scenarioSimulation initiated callout processing
	 
	  @PostMapping("/scenarioSimulationStart")
	  String doScenarioSimulationStart(@RequestBody ScenarioDescriptor scenarioDescriptor) {
		  currentScenarioID = scenarioDescriptor.getScenarioID();
		  // checkout the scenario using  scenarioID from the  input descriptor
		  Optional<Scenario> oscn = scenarioStore.findById(currentScenarioID);
		  Scenario scn;
		  if (oscn.isPresent()) {
			  scn = oscn.get(); 
			  System.out.println("**** fnp204 found scn ; scnid = " + scn.getScenarioID() + 
					  " descriptors= " + scn.getRiskFactorDescriptors().toString());
			 	}
		  else {
			  throw new ScenarioNotFoundException(currentScenarioID);
		  	  }

		  // Process the scenario to create MultiMarketRiskModel and multiBehaviorRiskModel
		  this.currentMarketModel = new MultiMarketRiskModel();
		  this.currentBehaviorModel = new MultiBehaviorRiskModel();
		  
		  // a scenario has a list of RiskFactorDescriptors
		  for (RiskFactorDescriptor rfd : scn.getRiskFactorDescriptors()) {
			  String rfxid = rfd.getRiskFactorID();
			  System.out.println("**** fnp2041 found rfid= " + rfxid + " rfd: " + rfd.toString() ); 
			  if (rfd.getRiskFactorType().equals("ReferenceIndex")) {
				  Optional<ReferenceIndex> orfx = referenceIndexStore.findById(rfxid);
				  ReferenceIndex rfx;
				  if (orfx.isPresent()) {
					  rfx = orfx.get();
					  System.out.println("**** fnp205 found rfx ; rfxid = " + rfxid);
					  this.currentMarketModel.add(rfx.getMarketObjectCode(), new TimeSeriesModel(rfx));	
				  }
				  else {
					  throw new ReferenceIndexNotFoundException(rfxid); 
					  }
			  }
			  else if (rfd.getRiskFactorType().equals("TwoDimensionalPrepaymentModel")) {
				  Optional<TwoDimensionalPrepaymentModelData> oppmd =
						  this.twoDimensionalPrepaymentModelStore.findById(rfxid);
				  TwoDimensionalPrepaymentModelData ppmd;
				  if (oppmd.isPresent()) {
					  ppmd = oppmd.get();
					  System.out.println("**** fnp206 found ppmd ; rfxid = " + rfxid);
					  TwoDimensionalPrepaymentModel ppm = 
								 new TwoDimensionalPrepaymentModel(rfxid, ppmd,this.currentMarketModel);
					  currentBehaviorModel.add(rfxid, ppm);
				  }
				  else  {
					  throw new TwoDimensionalPrepaymentModelNotFoundException(rfxid);
				  }
			  }  
			  else if (rfd.getRiskFactorType().equals("TwoDimensionalDepositTrxModel")) {
				  Optional<TwoDimensionalDepositTrxModelData> odxmd =
						  this.twoDimensionalDepositTrxModelStore.findById(rfxid);
				  TwoDimensionalDepositTrxModelData dxmd;
				  if (odxmd.isPresent()) {
					  dxmd = odxmd.get();
					  System.out.println("**** fnp207 found dxmd ; rfxid = " + rfxid);
					  TwoDimensionalDepositTrxModel dxm = 
								 new TwoDimensionalDepositTrxModel(rfxid, dxmd);
					  currentBehaviorModel.add(rfxid, dxm);
				  }
				  else  {
					  throw new TwoDimensionalDepositTrxModelNotFoundException(rfxid);
				  }
			  }  
			  
			  else {
				  System.out.println("**** fnp208 unrecognized rfType= " + rfd.getRiskFactorType() );
			  }
					  

		  }	
		  this.currentActivatedModels.clear();
		  String outstr = "** CurrentMarketModel initialized for scenario "+ currentScenarioID + "\n";
		  outstr += "keys are: " + this.currentMarketModel.keys().toString();
		  outstr += "** CurrentBehaviorModel also initialized with keys: " + 
		  this.currentBehaviorModel.keys().toString() + "\n";
		  return outstr;
	  }	
	  
	  @PostMapping("/contractSimulationStart")
	  List<CalloutData> doContractSimulationStart(@RequestBody Map<String,Object> contract){	  		  
		  ContractModel contractModel = ContractModel.parse(contract);
		  
		  // the MultiBehaviorRiskModel will get list of models to activate from contractModel
		  // BUT we need to check here that all models referred to by the contract are in the scenario
		  this.currentActivatedModels.clear();
		  List<String> ppmdls = contractModel.getAs("prepaymentModels");
		  List<String> dwmdls = contractModel.getAs("depositTrxModels");
		  
		  // combine the two lists of model instance names 
		  List<String> mdls = new ArrayList<String>() ;
		  if (ppmdls != null) 
			  mdls.addAll(ppmdls);
		  if (dwmdls != null)
			  mdls.addAll(dwmdls);
		  // List<String> mdls = new ArrayList<String>(ppmdls);
		  // mdls.addAll(dwmdls);
		  
		  List<CalloutData> observations = new ArrayList<CalloutData>();
		  // Previously we checked for null as special case 
		  for (String mdl : mdls) {
			  if ( currentBehaviorModel.keys().contains(mdl))  {  // this model is found, is OK etc 
					  currentActivatedModels.add(mdl);
					  observations.addAll(currentBehaviorModel.modelContractStart(contractModel, mdl));
			  }
			  else
					  throw new RiskModelNotFoundException("*** modelID: " + mdl + " in scenario: " + currentScenarioID);
		  }
	      return observations;
	  }  	  

	  @PostMapping("/marketStateAt")
	  Double doMarketStateAt(@RequestBody StateAtInput stateAtInput) {
		  String id = stateAtInput.getId();
		  System.out.println("***fnp2071 looking for moc= " + id + " market Keys= " + currentMarketModel.keys().toString() );
		  LocalDateTime time = stateAtInput.getTime();	
		  Double dval = this.currentMarketModel.stateAt(id, time);
		  System.out.println("**** fnp207: /marketStateAt id = "+id+" time= "
		     + time.toString() + " scenario= " + this.currentScenarioID +
		     " value= " + dval.toString()); 
		  return dval;
	  }
	  
	  @PostMapping("/behaviorStateAt")
	  double doBehaviorStateAt(@RequestBody BehaviorStateAtInput behaviorStateAtInput) {
		  String mdlid = behaviorStateAtInput.getRiskFactorId();
		  System.out.println("**** fnp208: in  /behaviorStateAt id = "+ mdlid );
		  StateSpace state = behaviorStateAtInput.getStates();
		  double dval = this.currentBehaviorModel.stateAt(mdlid, state);
		  System.out.println("**** fnp209: /behavior id = " + mdlid + " statusDate= "
				     + state.statusDate.toString() + " nominalInterest= " 
				     + state.nominalInterestRate +   " value= " + dval );
		  return dval;
	  }

	  @GetMapping("/marketKeys") 
	  HashSet<String> doMarketKeys() {	
		  Set<String> kset = this.currentMarketModel.keys();
		  HashSet<String> hks = new HashSet<String>();
		  for (String ks : kset) {
			  hks.add(ks);
		  }
		  return hks;
	  }
	  
	  @GetMapping("/activeScenario")
	  String doActiveScenario() {
		  String out;
		  if (currentScenarioID == null)  {
			  out = "No scenario currently active." ;
		  }
		  else { 
			  out = "Currently activeScenario: " + currentScenarioID + "\n" ;	
		  }
		  return out;	  
	  }
	  
	  @GetMapping("/currentBehaviorKeys")
	  HashSet<String> doCurrentBehaviorKeys(){
		  Set<String> kset = this.currentBehaviorModel.keys();
		  HashSet<String> hks = new HashSet<String>();
		  for (String ks :kset) {
			  hks.add(ks);
		  }
		  System.out.println("**** fnp214 in /currentBehaviorKeys");
		  return hks;
	  }
	 
	  @GetMapping("/activeBehaviorKeys")
	  HashSet<String> doActiveBehaviorKeys(){
	      return this.currentActivatedModels;
	  }
	  
}
