package com.berugo.quickend.repository.jpa;

import com.berugo.quickend.model.AbstractModel;
import com.berugo.quickend.repository.AbstractModelRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@ConditionalOnProperty(value = "quickend.storage.implementation", havingValue = "jpa")
@NoRepositoryBean
public interface AbstractJpaModelRepository<M extends AbstractModel> extends PagingAndSortingRepository<M, String>, AbstractModelRepository<M> {
}
