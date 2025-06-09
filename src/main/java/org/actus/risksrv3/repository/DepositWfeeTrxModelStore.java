package org.actus.risksrv3.repository;

import org.actus.risksrv3.models.DepositWfeeTrxModelData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepositWfeeTrxModelStore 
          extends MongoRepository<DepositWfeeTrxModelData, String> {
}


