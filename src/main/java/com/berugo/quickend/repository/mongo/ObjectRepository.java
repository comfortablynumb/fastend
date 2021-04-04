package com.berugo.quickend.repository.mongo;

import com.berugo.quickend.model.Object;
import com.berugo.quickend.repository.BaseObjectRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "object", path = "object")
public interface ObjectRepository extends AbstractMongoModelRepository<Object>, BaseObjectRepository {
}
