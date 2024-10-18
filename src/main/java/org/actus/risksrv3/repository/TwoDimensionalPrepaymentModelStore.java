package org.actus.risksrv3.repository;

import org.actus.risksrv3.models.TwoDimensionalPrepaymentModelData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TwoDimensionalPrepaymentModelStore 
		 extends MongoRepository <TwoDimensionalPrepaymentModelData,String> {	
}
