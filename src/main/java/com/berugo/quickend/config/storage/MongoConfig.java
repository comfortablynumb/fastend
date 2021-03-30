package com.berugo.quickend.config.storage;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories("com.berugo.quickend.repository.mongo")
@ConditionalOnProperty(name = "quickend.storage.implementation", havingValue = "mongo")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${quickend.storage.uri}")
    private String mongoConnectionString;

    @Override
    protected String getDatabaseName() {
        return "quickend";
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.berugo.quickend");
    }

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        super.configureClientSettings(builder);

        builder.applyConnectionString(new ConnectionString(mongoConnectionString));
    }
}
