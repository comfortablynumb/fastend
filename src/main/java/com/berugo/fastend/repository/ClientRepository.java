package com.berugo.fastend.repository;

import com.berugo.fastend.model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "client", path = "client")
public interface ClientRepository extends MongoRepository<Client, String> {
}
