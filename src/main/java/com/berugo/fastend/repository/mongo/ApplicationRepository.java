package com.berugo.fastend.repository.mongo;

import com.berugo.fastend.model.Application;
import com.berugo.fastend.repository.BaseApplicationRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "application", path = "application")
public interface ApplicationRepository extends AbstractMongoModelRepository<Application>, BaseApplicationRepository {
}
