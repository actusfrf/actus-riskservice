package org.actus.risksrv3.controllers;
import  org.actus.risksrv3.models.ReferenceIndex;
import  org.actus.risksrv3.models.Scenario;
import  org.actus.risksrv3.models.TwoDimensionalPrepaymentModelData;
import  org.actus.risksrv3.repository.ReferenceIndexStore;
import  org.actus.risksrv3.repository.ScenarioStore;
import  org.actus.risksrv3.repository.TwoDimensionalPrepaymentModelStore;
import  org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//Annotation 
@RestController
public class RiskDataManager {
	@Autowired
	private ReferenceIndexStore referenceIndexStore;	
	@Autowired
	private ScenarioStore scenarioStore;
	@Autowired
	private TwoDimensionalPrepaymentModelStore twoDimensionalPrepaymentModelStore;
	
	
	@PostMapping("/addReferenceIndex")
    public String saveReferenceIndex(@RequestBody ReferenceIndex referenceIndex){
        referenceIndexStore.save(referenceIndex);      
        return "ReferenceIndex added Successfully";
    }	
	// Path parameter id is ReferenceIndexID  i.e. riskFactorType == "ReferenceIndex" in any descriptor 
    @DeleteMapping("/deleteReferenceIndex/{id}")
    public String deleteReferenceIndex(@PathVariable String id){
        referenceIndexStore.deleteById(id);     
        return "ReferenceIndex deleted Successfully";
    }   
    @GetMapping("/findReferenceIndex/{id}")
    public Optional<ReferenceIndex> findReferenceIndex(@PathVariable String id) {
    	Optional<ReferenceIndex> rx = referenceIndexStore.findById(id);
    	// ArrayList<ReferenceIndex> rxs = new ArrayList<ReferenceIndex>();
    	// if (rx.isPresent()) { rxs.add(rx.get()); }
    	return rx;
    }
	
    @GetMapping("/findAllReferenceIndexes")
    public List<ReferenceIndex> getReferenceIndexes() {      
    	return referenceIndexStore.findAll();
    }
    
    // id is here a ScenarioID  i.e. riskFactorType == "Scenario" in any descriptor
	@PostMapping("/addScenario")
    public String saveScenario(@RequestBody Scenario scenario){
        scenarioStore.save(scenario);      
        return "Scenario added Successfully";
    }	
	// id is here a ScenarioID i.e. riskFactorType == "Scenario" in any descriptor
    @DeleteMapping("/deleteScenario/{id}")
    public String deleteScenario(@PathVariable String id){
        scenarioStore.deleteById(id);      
        return "Scenario Deleted Successfully";
    }
    @GetMapping("/findScenario/{id}")
    public Optional<Scenario> findScenario(@PathVariable String id) {
    	 return  scenarioStore.findById(id);
    }
    
    @GetMapping("/findAllScenarios")
    public List<Scenario> getScenarios() {
        return scenarioStore.findAll();
    }
    
    // Path Parameter id is here a TwoParameterPrepaymentModelID 
    //i.e.  a String riskFactorID  with associated riskFactorType == "TwoDimensionalPrepaymentModel" 
	@PostMapping("/addTwoDimensionalPrepaymentModel")
    public String saveTwoDimensionalPrepaymentModelData(
    		@RequestBody TwoDimensionalPrepaymentModelData twoDimensionalPrepaymentModelData){
        twoDimensionalPrepaymentModelStore.save(twoDimensionalPrepaymentModelData);      
        return "TwoDimensionalPrepayment model added successfully";
    }	
	// id is a TwoDimensionalPrepaymentModelID 
    @DeleteMapping("/deleteTwoDimensionalPrepaymentModel/{id}")
    public String deleteTwoDimensionalPrepaymentModel(@PathVariable String id){
        twoDimensionalPrepaymentModelStore.deleteById(id);      
        return "TwoDimensionalPrepaymentModel deleted Successfully";
    }
    @GetMapping("/findTwoDimensionalPrepaymentModel/{id}")
    public Optional<TwoDimensionalPrepaymentModelData> findTwoDimensionalPrepaymentModelData(@PathVariable String id) {
    	 return  twoDimensionalPrepaymentModelStore.findById(id);
    }
    
    @GetMapping("/findAllTwoDimensionalPrepaymentModels")
    public List<TwoDimensionalPrepaymentModelData> getTwoDimensionalPrepaymentModels() {
        return twoDimensionalPrepaymentModelStore.findAll();
    }
}
