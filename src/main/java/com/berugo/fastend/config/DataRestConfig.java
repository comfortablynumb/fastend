package com.berugo.fastend.config;

import com.berugo.fastend.model.Application;
import com.berugo.fastend.model.Client;
import com.berugo.fastend.model.ObjectType;
import com.berugo.fastend.repository.mongo.ApplicationRepository;
import com.berugo.fastend.repository.mongo.ClientRepository;
import com.berugo.fastend.repository.mongo.ObjectTypeRepository;
import com.berugo.fastend.validator.AbstractCreateOrUpdateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.List;

@Component
public class DataRestConfig implements RepositoryRestConfigurer {

    @Autowired
    private List<AbstractCreateOrUpdateValidator> createOrUpdateValidators;


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.withEntityLookup()
            .forRepository(ApplicationRepository.class)
            .withIdMapping(Application::getExternalId)
            .withLookup(ApplicationRepository::findByExternalId);

        config.withEntityLookup()
            .forRepository(ClientRepository.class)
            .withIdMapping(Client::getExternalId)
            .withLookup(ClientRepository::findByExternalId);

        config.withEntityLookup()
            .forRepository(ObjectTypeRepository.class)
            .withIdMapping(ObjectType::getExternalId)
            .withLookup(ObjectTypeRepository::findByExternalId);
    }

    @Override
    public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
        // Register all our validators

        for (final AbstractCreateOrUpdateValidator createOrUpdateValidator : createOrUpdateValidators) {
            validatingListener.addValidator("beforeCreate", createOrUpdateValidator);
            validatingListener.addValidator("beforeSave", createOrUpdateValidator);
        }
    }
}