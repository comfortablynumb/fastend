package com.berugo.fastend.repository;

import com.berugo.fastend.model.AbstractModel;

import java.util.Optional;

public interface AbstractModelRepository<M extends AbstractModel> {
    Optional<M> findByExternalId(String externalId);
}
