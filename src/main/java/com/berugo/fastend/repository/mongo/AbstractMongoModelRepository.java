package com.berugo.fastend.repository.mongo;

import com.berugo.fastend.model.AbstractModel;
import com.berugo.fastend.repository.AbstractModelRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AbstractMongoModelRepository<M extends AbstractModel> extends MongoRepository<M, String>, AbstractModelRepository<M> {
}
