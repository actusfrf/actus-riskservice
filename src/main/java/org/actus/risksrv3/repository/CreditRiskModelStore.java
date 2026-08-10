package org.actus.risksrv3.repository;

import org.actus.risksrv3.models.CreditRiskModelData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CreditRiskModelStore 
    extends MongoRepository <CreditRiskModelData,String> {	
}
