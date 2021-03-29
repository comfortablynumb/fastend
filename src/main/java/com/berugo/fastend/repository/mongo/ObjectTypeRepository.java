package com.berugo.fastend.repository.mongo;

import com.berugo.fastend.model.ObjectType;
import com.berugo.fastend.repository.BaseObjectTypeRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "object_type", path = "object_type")
public interface ObjectTypeRepository extends AbstractMongoModelRepository<ObjectType>, BaseObjectTypeRepository {
}
