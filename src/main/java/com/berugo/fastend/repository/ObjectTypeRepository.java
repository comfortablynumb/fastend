package com.berugo.fastend.repository;

import com.berugo.fastend.model.ObjectType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "object_type", path = "object_type")
public interface ObjectTypeRepository extends MongoRepository<ObjectType, String> {
}
