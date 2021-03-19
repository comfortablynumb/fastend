package com.berugo.fastend.repository;

import com.berugo.fastend.model.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "application", path = "application")
public interface ApplicationRepository extends MongoRepository<Application, String> {
}
