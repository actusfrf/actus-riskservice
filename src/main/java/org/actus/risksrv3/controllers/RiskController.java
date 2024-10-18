package org.actus.risksrv3.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.time.LocalDateTime;

import org.actus.risksrv3.models.BehaviorStateAtInput;
import org.actus.risksrv3.models.OldScenario;
// import org.actus.risksrv3.models.ScenarioID;
import org.actus.risksrv3.models.StateAtInput;
import org.actus.risksrv3.models.ReferenceIndex;
// import org.actus.risksrv3.models.RiskFactorID;
import org.actus.risksrv3.models.MarketData;
import org.actus.risksrv3.models.TwoDimensionalPrepaymentModelData;
import org.actus.risksrv3.models.CalloutData;
import org.actus.risksrv3.core.attributes.ContractModel;
import org.actus.risksrv3.models.BatchStartInput;

import org.actus.risksrv3.utils.MultiMarketRiskModel;
import org.actus.risksrv3.utils.MultiBehaviorRiskModel;
import org.actus.risksrv3.utils.TimeSeriesModel;
import org.actus.risksrv3.utils.TwoDimensionalPrepaymentModel;

//import org.actus.attributes.ContractModel;  -- make local copy for now 
import org.actus.risksrv3.core.states.StateSpace;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class RiskController {
	// (In memory) Stores for Scenarios, ReferenceIndexes and PrepaymentModels
	//  This is the risk data repository section or risksrv3
	private HashMap<String,OldScenario> scenarioStore = new HashMap<String, OldScenario>();
	private HashMap<String,ReferenceIndex> referenceIndexStore = 
			new HashMap<String, ReferenceIndex>() ;
	private HashMap<String, TwoDimensionalPrepaymentModelData> twoDimensionalPrepaymentModelStore = 
			new HashMap<String, TwoDimensionalPrepaymentModelData>();
	
	// these are the simulation request processing  rf2API  side of risksrv3 - state variables
	private MultiMarketRiskModel            currentMarketModel;
	private String							currentScenarioID = null;
	private MultiBehaviorRiskModel 			currentBehaviorModel;
	private HashSet<String>	                currentActivatedModels = new HashSet<String>();
   		
	@PostMapping("/mem/scenarios") 
    public Map<String, Object> createScenario(@RequestBody OldScenario newScenario) {
    	 String scenarioID = 			newScenario.getScenarioID();
    	// List<RiskFactorID> marketRiskFactorIDs = 	newScenario.getMarketRiskFactorIDs();
    	 this.scenarioStore.put(scenarioID, newScenario);
    	 System.out.println("**** Scenario with ID: "+scenarioID +  " added to scenario Store");
    	 System.out.println("***"+  newScenario.toJSON() ); 
    	 
    	 HashMap<String,Object> responseMap = new HashMap<>();
    	 responseMap.put("responseCode", "OK" );
    	 responseMap.put("responseMsg","Scenario with ID: "+ scenarioID +  " created") ;
    	 responseMap.put("responseData", newScenario.toJSON() ) ; 
         return responseMap;
    } 
	
    @GetMapping("/mem/scenarios")
     HashMap<String,Object> allScenarioIDs (){
    	Set<String> idList = this.scenarioStore.keySet();
   	 	System.out.println("**** List of all saved ScenarioIDs requested");
   	 	System.out.println("***"+ showIDs(idList)); 
   	 	
   	 	HashMap<String,Object> responseMap = new HashMap<>();
   	 	responseMap.put("responseCode", "OK" );
   	 	responseMap.put("responseMsg","List of all saved Scenario IDs requested") ;
   	 	responseMap.put("responseList", showIDs(idList)) ; 
        return responseMap;
    }
    
    public String showIDs(Set<String> idList) {
    	String outStr ="IDs are: < ";
    	for ( String id : idList ) {
    		outStr = outStr +"\"" + id + "\" ";     		
    	}
    	return outStr + ">";
    }
	
    @GetMapping("/mem/scenarioIDs")
    List<String> scenarioIDs (){
   	Set<String> idList = this.scenarioStore.keySet();
  	 	System.out.println("**** List of all saved ScenarioIDs requested");
  	 	System.out.println("***"+ showIDs(idList)); 
  	 	
  	 	//now build JSON List<ScenarioIDs>
  	 	List<String> outl = new ArrayList<>() ; 
  	 	for (String id : idList) {
  	 		outl.add(id);
  	 	}
  	 	return (outl);	 	 		
   }
   
	  // Single item
	  
	  @GetMapping("/mem/scenarios/{id}")
	  OldScenario  oneSc(@PathVariable String id) {
	     OldScenario scenario  = this.scenarioStore.get(id);
	     if (scenario != null) {	    	 	     
	        return scenario; 
	     }
	     else throw new ScenarioNotFoundException(id);
	  }
	  
	  @PostMapping("/mem/referenceIndex") 
	  public Map<String, Object> createReferenceIndex(@RequestBody ReferenceIndex newReferenceIndex) {
	   	 String rfid = 			newReferenceIndex.getRiskFactorID();
	   	 this.referenceIndexStore.put(rfid, newReferenceIndex);
	   	 System.out.println("**** Reference Index with ID: "+ rfid +  " added to reference Index Store");
	   	 System.out.println("***"+  newReferenceIndex.toString() ); 
	   	 
	   	 HashMap<String,Object> responseMap = new HashMap<>();
	     responseMap.put("responseCode", "OK" );
	     responseMap.put("responseMsg","ReferenceIndex with ID: "+ rfid +  " created") ;
	     responseMap.put("responseData", newReferenceIndex.toString() ) ; 
	     return responseMap;
	     } 
	  
	  @GetMapping("/mem/referenceIndexIDs")
	  List<String> riskFactorIDs (){
	  Set<String> idList = this.referenceIndexStore.keySet();
	     System.out.println("**** List of all saved ReferenceIndexIDs requested");
	  	 System.out.println("***"+ showIDs(idList)); 
	  	 	
	  	 //now build JSON List<RiskFactorIDs>
	  	 List<String> outl = new ArrayList<>() ; 
	  	 for (String id : idList) {
	  	 	outl.add(id);
	  	 	}
	  	 return (outl);	 	 		
	  }
	  
	  // previously called /batchEventsStart -- changed to this 
	  @PostMapping("/mem/scenarioSimulationBatchStart")
	  String doScenarioSimulationBatchStart(@RequestBody BatchStartInput batchStartInput) {
		  currentScenarioID = batchStartInput.getScenarioID();
		  OldScenario scn = this.scenarioStore.get(currentScenarioID); 
		  if (scn != null) {
			  // First create the MultiMarketRiskModel
			  this.currentMarketModel = new MultiMarketRiskModel();
			  for ( String rfxid : scn.getMarketRiskFactorIDs()) {
				  ReferenceIndex rfx = this.referenceIndexStore.get(rfxid);
				  if ( rfx != null) { 
					  this.currentMarketModel.add(rfx.getMarketObjectCode(), new TimeSeriesModel(rfx));				  				  
				  }
				  else 
					  throw new ReferenceIndexNotFoundException(rfxid);  
			  }	
			  // Next create and populate multiBehaviorModel but (do not activate anything now) 
			  this.currentBehaviorModel = new MultiBehaviorRiskModel();
			  for ( String rfxid : scn.getPrepaymentRiskFactorIDs()) {
				  TwoDimensionalPrepaymentModelData ppmd = 
				        this.twoDimensionalPrepaymentModelStore.get(rfxid);
				  if (ppmd != null) {
					  TwoDimensionalPrepaymentModel ppm = 
								 new TwoDimensionalPrepaymentModel(rfxid, ppmd,this.currentMarketModel);
					  currentBehaviorModel.add(rfxid, ppm);
				  }	
				  else throw new RiskModelNotFoundException(rfxid); 
			  }
		  } 
		  else throw new ScenarioNotFoundException(currentScenarioID);
		  
		  this.currentActivatedModels.clear();
		  String outstr = "** CurrentMarketModel initialized for scenario "+ currentScenarioID + "\n";
		  outstr += "key;s are: " + this.currentMarketModel.keys().toString();
		  outstr += "** CurrentBehaviorModel also initialized with keys: " + this.currentBehaviorModel.keys().toString() + "\n";
		  return outstr;
	  }
	  
	  @PostMapping("/mem/scenarioSimulationContractStart")
	  List<CalloutData> doScenarioSimulationContractStart(@RequestBody Map<String,Object> contract){	  		  
		  ContractModel contractModel = ContractModel.parse(contract);
		  
		  // the MultiBehaviorRiskModel will get list of models to activate from contractModel
		  // BUT we need to check here that all models referred to by the contract are in the scenario
		  this.currentActivatedModels.clear();
		  List<String> mdls = contractModel.getAs("prepaymentModels");
		  List<CalloutData> observations;
		  if (mdls != null ) {
			  for (String mdl : mdls) {
				  if ( currentBehaviorModel.keys().contains(mdl))
					  currentActivatedModels.add(mdl);
				  else
					  throw new RiskModelNotFoundException("*** modelID: " + mdl + " in scenario: " + currentScenarioID);
			  }
			  // MultiBehaviorRiskModel.contractStart will call activated models contractStart assuming all model ids checked 
			  observations = currentBehaviorModel.contractStart(contractModel); 
		  } else {
			  observations = new ArrayList<CalloutData>(); // if no prepayment models return an empty list of calloutData 			  
		  }
	      return observations;
	  }  	  
	  
	  @PostMapping("/mem/marketStateAt")
	  Double doMarketStateAt(@RequestBody StateAtInput stateAtInput) {
		  String id = stateAtInput.getId();
		  LocalDateTime time = stateAtInput.getTime();	
		  Double dval = this.currentMarketModel.stateAt(id, time);
		  System.out.println("**** fnp020: /marketStateAt id = "+id+" time= "
		     + time.toString() + " scenario= " + this.currentScenarioID +
		     " value= " + dval.toString()); 
		  return dval;
	  }
	  
	  @PostMapping("/mem/behaviorStateAt")
	  double doBehaviorStateAt(@RequestBody BehaviorStateAtInput behaviorStateAtInput) {
		  String mdlid = behaviorStateAtInput.getRiskFactorId();
		  System.out.println("**** fnp104: in  /behaviorStateAt id = "+ mdlid );
		  StateSpace state = behaviorStateAtInput.getStates();
		  double dval = this.currentBehaviorModel.stateAt(mdlid, state);
		  System.out.println("**** fnp105: /behavior id = " + mdlid + " statusDate= "
				     + state.statusDate.toString() + " nominalInterest= " 
				     + state.nominalInterestRate +   " value= " + dval );
		  return dval;
	  }
	  
	  @GetMapping("/mem/marketKeys") 
	  HashSet<String> doMarketKeys() {	
		  System.out.println("**** fnp080 in /marketKeys");
		  Set<String> kset = this.currentMarketModel.keys();
		  HashSet<String> hks = new HashSet<String>();
		  for (String ks : kset) {
			  hks.add(ks);
		  }
		  return hks;
	  }
	  
	  @GetMapping("/mem/activeScenario")
	  String doActiveScenario() {
		  System.out.println("**** fnp081 in /activeScenario");
		  String out;
		  if (currentScenarioID == null)  {
			  out = "No scenario currently active." ;
		  }
		  else { 
			  out = "Currently activeScenario: " + currentScenarioID + "\n" ;	
		  }
		  System.out.println("**** fnp083  out = " + out );
		  return out;	  
	  }
	  
	  @GetMapping("/mem/currentBehaviorKeys")
	  HashSet<String> doCurrentBehaviorKeys(){
		  System.out.println("**** fnp082 in /currentBehaviorKeys");
		  Set<String> kset = this.currentBehaviorModel.keys();
		  HashSet<String> hks = new HashSet<String>();
		  for (String ks :kset) {
			  hks.add(ks);
		  }
		  System.out.println("**** fnp082 in /currentBehaviorKeys");
		  return hks;
	  }
	  
	  @GetMapping("/mem/activeBehaviorKeys")
	  HashSet<String> doActiveBehaviorKeys(){
	      return this.currentActivatedModels;
	  }
	  
	  
	  	  
	  @GetMapping("/mem/referenceIndex/{id}")
	  ReferenceIndex  oneRfx (@PathVariable String id) {
	     ReferenceIndex rfx  = this.referenceIndexStore.get(id);
	     if (rfx != null) 	    	 	     
	          return rfx; 
	     else throw new ScenarioNotFoundException(id);
	  }	 
	  
	  @GetMapping("/mem/scenarioMarketData/{scid}")
	  List<ReferenceIndex> scenRfx (@PathVariable String scid) {
		// List<RiskFactorID> rfidl = this.scenarioStore.get(scid);
		 OldScenario scn = this.scenarioStore.get(scid); 
		 List<ReferenceIndex>  rfxl = new ArrayList< ReferenceIndex> ();
		 if (scn != null) {	
			 List<String> rfidl = scn.getMarketRiskFactorIDs();
			 Set<String>  mocl  = new HashSet<> ();
			 for (String rfid  : rfidl ) {
				  ReferenceIndex rfx =  this.referenceIndexStore.get(rfid);
				  if (rfx !=null) {
					  rfxl.add(rfx);
					  String moc = rfx.getMarketObjectCode();
					  if (! mocl.contains(moc)) {
						  mocl.add(moc);
						  }
					  else throw new DuplicateMOCTimeSeriesException(moc);
					  }
				  else throw new ReferenceIndexNotFoundException(rfid);
			 }
			  
		 }
		 else throw new ScenarioNotFoundException(scid);
		 return  rfxl;
	  }	   
	  
	  @GetMapping("/mem/marketData/{scid}")
	  MarketData  doMarketData (@PathVariable String scid) {
		 OldScenario scn = this.scenarioStore.get(scid); 
		 List<ReferenceIndex>  rfxl = new ArrayList< ReferenceIndex> ();
		 if (scn != null) {	
			 List<String> rfidl = scn.getMarketRiskFactorIDs();
			 Set<String>  mocl  = new HashSet<> ();
			 for (String rfid  : rfidl ) {
				  ReferenceIndex rfx =  this.referenceIndexStore.get(rfid);
				  if (rfx !=null) {
					  rfxl.add(rfx);
					  String moc = rfx.getMarketObjectCode();
					  if (! mocl.contains(moc)) {
						  mocl.add(moc);
						  }
					  else throw new DuplicateMOCTimeSeriesException(moc);
					  }
				  else throw new ReferenceIndexNotFoundException(rfid);
			 }
			  
		 }
		 else throw new ScenarioNotFoundException(scid);
		 MarketData marketData = new MarketData(rfxl);
		 return  marketData;
	  }	 
	  
	  // Request from a user to save a new prepayment model in the risk server 
	  @PostMapping("/mem/twoDimensionalPrepaymentModel")     
	  public Map<String, Object> createTwoDimensionalPrepaymentModel(@RequestBody TwoDimensionalPrepaymentModelData newTwoDimensionalPrepaymentModel) {
	   	 String rfid = 			newTwoDimensionalPrepaymentModel.getRiskFactorId();
	   	 this.twoDimensionalPrepaymentModelStore.put(rfid, newTwoDimensionalPrepaymentModel);
	   	 System.out.println("**** TwoDimensionalPrepaymentModel with ID: "+ rfid +  " added to twoDimensionalPrepaymentModelStore");
	   	 System.out.println("***"+  newTwoDimensionalPrepaymentModel.toString() ); 
	   	 
	   	 HashMap<String,Object> responseMap = new HashMap<>();
	     responseMap.put("responseCode", "OK" );
	     responseMap.put("responseMsg","TwoDimensionalPrepaymentModel with rfId: "+ rfid +  " created") ;
	     responseMap.put("responseData", newTwoDimensionalPrepaymentModel.toString() ) ; 
	     return responseMap;
	     } 
	  
}
