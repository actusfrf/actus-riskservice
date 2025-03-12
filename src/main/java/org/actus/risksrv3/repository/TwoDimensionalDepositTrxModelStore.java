package org.actus.risksrv3.repository;

import org.actus.risksrv3.models.TwoDimensionalDepositTrxModelData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TwoDimensionalDepositTrxModelStore 
      extends MongoRepository<TwoDimensionalDepositTrxModelData, String> {
}
