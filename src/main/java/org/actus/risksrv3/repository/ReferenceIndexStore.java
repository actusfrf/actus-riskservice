package org.actus.risksrv3.repository;

import org.actus.risksrv3.models.ReferenceIndex;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReferenceIndexStore 
         extends MongoRepository <ReferenceIndex,String> {
}
  