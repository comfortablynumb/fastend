package com.berugo.quickend.config.datarest.storage;

import com.berugo.quickend.config.datarest.CommonDataRestConfig;
import com.berugo.quickend.model.Application;
import com.berugo.quickend.model.Client;
import com.berugo.quickend.model.ObjectType;
import com.berugo.quickend.repository.mongo.ApplicationRepository;
import com.berugo.quickend.repository.mongo.ClientRepository;
import com.berugo.quickend.repository.mongo.ObjectTypeRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@ConditionalOnProperty(name = "quickend.storage.implementation", havingValue = "mongo")
public class MongoDataRestConfig extends CommonDataRestConfig implements RepositoryRestConfigurer {
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
}
