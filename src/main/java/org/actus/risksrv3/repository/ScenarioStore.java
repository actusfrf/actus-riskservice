package org.actus.risksrv3.repository;

import org.actus.risksrv3.models.Scenario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScenarioStore 
         extends MongoRepository <Scenario,String> {
}
  