package com.berugo.quickend.repository.jpa;

import com.berugo.quickend.model.Client;
import com.berugo.quickend.repository.BaseClientRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "client", path = "client")
public interface ClientRepository extends AbstractJpaModelRepository<Client>, BaseClientRepository {
}
