package com.berugo.quickend.repository.mongo;

import com.berugo.quickend.model.AbstractModel;
import com.berugo.quickend.repository.AbstractModelRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@ConditionalOnProperty(value = "quickend.storage.implementation", havingValue = "mongo")
@NoRepositoryBean
public interface AbstractMongoModelRepository<M extends AbstractModel> extends MongoRepository<M, String>, AbstractModelRepository<M> {
}
