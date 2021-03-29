package com.berugo.fastend.repository.mongo;

import com.berugo.fastend.model.Client;
import com.berugo.fastend.repository.BaseClientRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "client", path = "client")
public interface ClientRepository extends AbstractMongoModelRepository<Client>, BaseClientRepository {
}
