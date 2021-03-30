package com.berugo.quickend.repository.jpa;

import com.berugo.quickend.model.ObjectType;
import com.berugo.quickend.repository.BaseObjectTypeRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "object_type", path = "object_type")
public interface ObjectTypeRepository extends AbstractJpaModelRepository<ObjectType>, BaseObjectTypeRepository {
}
