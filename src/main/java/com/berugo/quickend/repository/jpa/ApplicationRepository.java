package com.berugo.quickend.repository.jpa;

import com.berugo.quickend.model.Application;
import com.berugo.quickend.repository.BaseApplicationRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "application", path = "application")
public interface ApplicationRepository extends AbstractJpaModelRepository<Application>, BaseApplicationRepository {
}
