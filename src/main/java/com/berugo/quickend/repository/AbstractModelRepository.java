package com.berugo.quickend.repository;

import com.berugo.quickend.model.AbstractModel;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface AbstractModelRepository<M extends AbstractModel> {
    Optional<M> findByExternalId(String externalId);
}
